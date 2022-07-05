package io.github.akjo03.akjonav.model.elements.map.highway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.elements.base.way.AkjonavWay;
import io.github.akjo03.akjonav.model.elements.map.AkjonavMapElementBuilder;
import io.github.akjo03.akjonav.model.elements.map.AkjonavMapElementType;
import io.github.akjo03.akjonav.model.elements.reference.AkjonavElementReference;
import io.validly.Notification;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.Objects;

import static io.validly.NoteFirstValidator.valid;

@SuppressWarnings("unused")
public class AkjonavHighwayBuilder extends AkjonavMapElementBuilder<AkjonavHighway> {
	public AkjonavHighwayBuilder() { super(); }

	public AkjonavHighwayBuilder(BigInteger id) {
		super(id);
	}

	@Override
	protected AkjonavMapElementType getType() {
		return AkjonavMapElementType.HIGHWAY;
	}

	@Override
	@SuppressWarnings("DuplicatedCode")
	protected @NotNull Notification validateMapElement() {
		Notification notification = new Notification();

		valid(elementRefs, "AkjonavHighway.elementRefs", notification)
				.must(elementRefsP -> elementRefsP.stream().allMatch(elementRef -> {
					if (elementRef.getElementType().getTypeID().split(":")[0].equals("BaseElement")) {
						return elementRef.getElementType().getTypeID().split(":")[1].equals("WAY");
					} else if (elementRef.getElementType().getTypeID().split(":")[0].equals("MapElement")) {
						return elementRef.getElementType().getTypeID().split(":")[1].equals("HIGHWAY");
					} else {
						return false;
					}
				}), "Any element reference of a highway must either be a base element of type WAY or a map element of type HIGHWAY.")
				.must(elementRefsP -> {
					// The end point of any way or highway must be the same as the start point of the next way or highway.
					// This is to ensure that the highways are connected.
					boolean result = false;
					if (elementRefsP.size() == 1) {
						result = true;
					} else {
						for (int i = 0; i < elementRefsP.size() - 1; i++) {
							AkjonavElementReference currentElementRef = elementRefsP.get(i);
							AkjonavElementReference nextElementRef = null;
							if (i < elementRefsP.size() - 1) {
								nextElementRef = elementRefsP.get(i + 1);
							}
							if (nextElementRef != null) {
								switch (currentElementRef.getElementType().getTypeID()) {
									case "BaseElement:WAY" -> {
										AkjonavWay currentWay = (AkjonavWay) mapBuilderRef.getBaseElementByReference(currentElementRef);
										switch (nextElementRef.getElementType().getTypeID()) {
											case "BaseElement:WAY" -> {
												AkjonavWay nextWay = (AkjonavWay) mapBuilderRef.getBaseElementByReference(nextElementRef);
												AkjonavElementReference currentEndPoint1 = Objects.requireNonNull(currentWay).getEndNodeRef();
												AkjonavElementReference nextStartPoint1 = Objects.requireNonNull(nextWay).getStartNodeRef();
												result = currentEndPoint1.equals(nextStartPoint1);
											}
											case "MapElement:HIGHWAY" -> {
												AkjonavHighway nextHighway = (AkjonavHighway) mapBuilderRef.getMapElementByReference(nextElementRef);
												AkjonavElementReference currentEndPoint2 = Objects.requireNonNull(currentWay).getEndNodeRef();
												AkjonavElementReference nextStartPoint2 = Objects.requireNonNull(nextHighway).getStartNodeRef();
												result = currentEndPoint2.equals(nextStartPoint2);
											}
											default -> result = false;
										}
									}
									case "MapElement:HIGHWAY" -> {
										AkjonavHighway currentHighway = (AkjonavHighway) mapBuilderRef.getMapElementByReference(currentElementRef);
										switch (nextElementRef.getElementType().getTypeID()) {
											case "BaseElement:WAY" -> {
												AkjonavWay nextWay = (AkjonavWay) mapBuilderRef.getBaseElementByReference(nextElementRef);
												AkjonavElementReference currentEndPoint1 = Objects.requireNonNull(currentHighway).getEndNodeRef();
												AkjonavElementReference nextStartPoint1 = Objects.requireNonNull(nextWay).getStartNodeRef();
												result = currentEndPoint1.equals(nextStartPoint1);
											}
											case "MapElement:HIGHWAY" -> {
												AkjonavHighway nextHighway = (AkjonavHighway) mapBuilderRef.getMapElementByReference(nextElementRef);
												AkjonavElementReference currentEndPoint2 = Objects.requireNonNull(currentHighway).getEndNodeRef();
												AkjonavElementReference nextStartPoint2 = Objects.requireNonNull(nextHighway).getStartNodeRef();
												result = currentEndPoint2.equals(nextStartPoint2);
											}
											default -> result = false;
										}
									}
									default -> result = false;
								}
							}
						}
					}
					return result;
				}, "The end point of any way or highway must be the same as the start point of the next way or highway!");

		return notification;
	}

	@Override
	protected AkjonavHighway buildIt() {
		return new AkjonavHighway(elementID, elementRefs, mapBuilderRef);
	}

	@Override
	protected void fromSerialized(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper) {}
}
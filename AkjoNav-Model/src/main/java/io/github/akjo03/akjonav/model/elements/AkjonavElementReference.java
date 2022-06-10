package io.github.akjo03.akjonav.model.elements;

import io.github.akjo03.akjonav.model.map.AkjonavMap;
import io.github.akjo03.akjonav.model.map.AkjonavMapBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

@Getter
@RequiredArgsConstructor
@SuppressWarnings({"unused", "ClassCanBeRecord"})
public class AkjonavElementReference {
	private final AkjonavElementType elementType;
	private final BigInteger elementID;

	@Contract("_ -> new")
	public static @NotNull AkjonavElementReference of(@NotNull AkjonavElement<?> element) throws IllegalStateException {
		Class<?> callerClass = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();
		if (callerClass == null) {
			throw new IllegalStateException("AkjonavElementReference.of() called from outside of a class.");
		}
		if (!AkjonavMap.class.isAssignableFrom(callerClass) && !AkjonavMapBuilder.class.isAssignableFrom(callerClass)) {
			throw new IllegalStateException("AkjonavElementReference.of() can only be called from within an AkjonavMap or an AkjonavMapBuilder!");
		}
		return new AkjonavElementReference(element.getType(), element.getElementID());
	}

	@Override
	public String toString() {
		return "{type=" + elementType + ", id=" + elementID + "}";
	}
}
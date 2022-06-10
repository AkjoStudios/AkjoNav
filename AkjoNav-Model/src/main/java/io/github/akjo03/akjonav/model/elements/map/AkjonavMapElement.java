package io.github.akjo03.akjonav.model.elements.map;

import io.github.akjo03.akjonav.model.elements.AkjonavElement;
import io.github.akjo03.akjonav.model.elements.AkjonavElementReference;
import lombok.Getter;

import java.math.BigInteger;

@Getter
public abstract class AkjonavMapElement extends AkjonavElement<AkjonavMapElementType> {
	protected final AkjonavElementReference baseElement;

	protected AkjonavMapElement(BigInteger elementID, AkjonavMapElementType elementType, AkjonavElementReference baseElement) {
		super(elementID, elementType);
		this.baseElement = baseElement;
	}

	// TODO Add all the stuff
}
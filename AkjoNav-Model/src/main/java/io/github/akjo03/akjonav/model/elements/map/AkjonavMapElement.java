package io.github.akjo03.akjonav.model.elements.map;

import io.github.akjo03.akjonav.model.elements.AkjonavElement;
import lombok.Getter;

import java.math.BigInteger;

@Getter
public abstract class AkjonavMapElement extends AkjonavElement<AkjonavMapElementType> {
	protected AkjonavMapElement(BigInteger elementID, AkjonavMapElementType elementType) {
		super(elementID, elementType);
	}
}
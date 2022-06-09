package io.github.akjo03.akjonav.model.elements.base;

import io.github.akjo03.akjonav.model.elements.AkjonavElement;
import lombok.Getter;

import java.math.BigInteger;

@Getter
public abstract class AkjonavBaseElement extends AkjonavElement {
	protected AkjonavBaseElement(BigInteger elementID, AkjonavBaseElementType elementType) {
		super(elementID, elementType);
	}
}
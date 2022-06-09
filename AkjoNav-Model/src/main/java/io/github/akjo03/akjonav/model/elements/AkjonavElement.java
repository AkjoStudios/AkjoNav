package io.github.akjo03.akjonav.model.elements;

import io.github.akjo03.akjonav.model.util.builder.AkjonavBuildable;
import lombok.Getter;

import java.math.BigInteger;
import java.util.Objects;

@Getter
public abstract class AkjonavElement extends AkjonavBuildable {
	private final BigInteger elementID;

	AkjonavElement(BigInteger elementID, AkjonavElementType elementType) {
		super(elementType);
		this.elementID = elementID;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		AkjonavElement that = (AkjonavElement) o;
		return Objects.equals(elementID, that.elementID);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), elementID);
	}
}
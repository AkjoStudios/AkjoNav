package io.github.akjo03.akjonav.model.util.position;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuildable;
import io.github.akjo03.util.math.unit.units.length.Length;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Objects;

@SuppressWarnings("unused")
@Getter
public class AkjonavPosition extends AkjonavBuildable<AkjonavPositionType> {
	private final double latitude;
	private final double longitude;
	@Nullable private final Length altitude;

	AkjonavPosition(double latitude, double longitude, @Nullable Length altitude) {
		super(AkjonavPositionType.type);
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
	}

	@Override
	protected @NotNull ObjectNode serializeIt(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper) {
		objectNode.put("lat", latitude);
		objectNode.put("lon", longitude);
		if (altitude != null) {
			ObjectNode altitudeNode = objectMapper.createObjectNode();
			altitudeNode.put("value", altitude.getValue().toString());
			altitudeNode.put("unit", altitude.getUnit().toString());
			objectNode.set("alt", altitudeNode);
		}
		return objectNode;
	}

	@Override
	protected @NotNull String toObjectString() {
		return "{\"latitude\":" + latitude + ",\"longitude\":" + longitude + ",\"altitude\":" + (altitude != null ? altitude.toStringLocalizedWithAbbreviation(Locale.US) : "null").replace(" ", "") + "}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		AkjonavPosition position = (AkjonavPosition) o;
		return Double.compare(position.latitude, latitude) == 0 && Double.compare(position.longitude, longitude) == 0 && Objects.equals(altitude, position.altitude);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), latitude, longitude, altitude);
	}
}
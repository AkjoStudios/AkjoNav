package io.github.akjo03.akjonav.model.util.position;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuildable;
import io.github.akjo03.util.math.unit.units.length.Length;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

@Getter
public class AkjonavPosition extends AkjonavBuildable<AkjonavPositionType, AkjonavPosition, AkjonavPositionBuilder> {
	private final double latitude;
	private final double longitude;
	@Nullable private final Length altitude;

	protected AkjonavPosition(double latitude, double longitude, @Nullable Length altitude) {
		super(AkjonavPositionType.TYPE);
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
	}

	@Override
	protected @NotNull ObjectNode serializeIt(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper) {
		objectNode.put("latitude", latitude);
		objectNode.put("longitude", longitude);
		if (altitude != null) {
			ObjectNode altitudeNode = objectMapper.createObjectNode();
			altitudeNode.put("value", altitude.getValue());
			altitudeNode.put("unit", altitude.getUnit().toString());
			objectNode.set("altitude", altitudeNode);
		} else {
			objectNode.putNull("altitude");
		}
		return objectNode;
	}

	@Override
	protected @NotNull String toObjectString() {
		return "{latitude=" + latitude + ", longitude=" + longitude + ", altitude=" + (altitude != null ? altitude.toStringLocalizedWithAbbreviation(Locale.ENGLISH).replaceAll("\\s", "") : "null") + "}";
	}
}
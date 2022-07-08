package io.github.akjo03.akjonav.model.util.position;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuilder;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuilderContext;
import io.github.akjo03.util.math.Range;
import io.github.akjo03.util.math.unit.units.length.Length;
import io.github.akjo03.util.math.unit.units.length.LengthUnit;
import io.validly.Notification;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.validly.NoteFirstValidator.valid;

public class AkjonavPositionBuilder extends AkjonavBuilder<AkjonavPositionType, AkjonavPosition, AkjonavPositionBuilder> {
	private double latitude;
	private double longitude;
	@Nullable private Length altitude;

	public AkjonavPositionBuilder() { super(false); }

	public AkjonavPositionBuilder(double latitude, double longitude) {
		this();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public AkjonavPositionBuilder(double latitude, double longitude, @Nullable Length altitude) {
		this();
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
	}

	public AkjonavPositionBuilder setLatitude(double latitude) {
		this.latitude = latitude;
		return this;
	}

	public AkjonavPositionBuilder setLongitude(double longitude) {
		this.longitude = longitude;
		return this;
	}

	public AkjonavPositionBuilder setAltitude(@Nullable Length altitude) {
		this.altitude = altitude;
		return this;
	}

	@Override
	protected AkjonavPositionType getType() {
		return AkjonavPositionType.TYPE;
	}

	@Override
	protected AkjonavBuilderContext<AkjonavPositionType, AkjonavPosition, AkjonavPositionBuilder> getContext() {
		return null;
	}

	@Override
	protected AkjonavPosition buildIt() {
		return new AkjonavPosition(latitude, longitude, altitude);
	}

	@Override
	protected @NotNull Notification validateIt() {
		Notification notification = new Notification();

		valid(latitude, getValidationID("latitude"), notification)
				.must(latitude -> new Range<>(-90.0, 90.0).contains(latitude), "Latitude of an AkjonavPosition must be between -90 and 90 degrees!");

		valid(longitude, getValidationID("longitude"), notification)
				.must(longitude -> new Range<>(-180.0, 180.0).contains(longitude), "Longitude of an AkjonavPosition must be between -180 and 180 degrees!");

		return notification;
	}

	@Override
	protected void fromSerialized(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper) {
		this.latitude = objectNode.get("latitude").asDouble();
		this.longitude = objectNode.get("longitude").asDouble();
		if (objectNode.hasNonNull("altitude")) {
			JsonNode altitudeNode = objectNode.get("altitude");
			LengthUnit lengthUnit = LengthUnit.getUnit(altitudeNode.get("unit").asText());
			if (lengthUnit == null) {
				throw new IllegalArgumentException("Unknown length unit " + altitudeNode.get("unit").asText() + " for altitude of an AkjonavPosition!");
			}
			this.altitude = new Length(altitudeNode.get("value").asDouble(), lengthUnit);
		}
	}
}
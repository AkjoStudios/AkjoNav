package io.github.akjo03.akjonav.model.util.position;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuildableType;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuilder;
import io.github.akjo03.util.math.Range;
import io.github.akjo03.util.math.unit.units.length.Length;
import io.github.akjo03.util.math.unit.units.length.LengthUnit;
import io.validly.Notification;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.Objects;

import static io.validly.NoteFirstValidator.valid;

@SuppressWarnings("unused")
public class AkjonavPositionBuilder extends AkjonavBuilder<AkjonavPositionType, AkjonavPosition> {
	private Double latitude;
	private Double longitude;
	@Nullable private Length altitude = null;

	public AkjonavPositionBuilder() { super(); }

	public AkjonavPositionBuilder(@NotNull Double latitude, @NotNull Double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public AkjonavPositionBuilder(@NotNull Double latitude, @NotNull Double longitude, @NotNull Length altitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
	}

	public AkjonavPositionBuilder withLatitude(@NotNull Double latitude) {
		this.latitude = latitude;
		return this;
	}
	public AkjonavPositionBuilder withLongitude(@NotNull Double longitude) {
		this.longitude = longitude;
		return this;
	}
	public AkjonavPositionBuilder withAltitude(@Nullable Length altitude) {
		this.altitude = altitude;
		return this;
	}

	@Override
	protected AkjonavBuildableType getType() {
		return AkjonavPositionType.type;
	}

	@Override
	protected AkjonavPosition buildIt() {
		return new AkjonavPosition(latitude, longitude, altitude);
	}

	@Override
	protected @NotNull Notification validateIt() {
		Notification notification = new Notification();

		valid(latitude, "AkjonavPosition.latitude", notification)
				.mustNotBeNull("Latitude of a position cannot be null!")
				.must(latitudeP -> new Range<>(-90D, 90D).contains(latitudeP), "Latitude of a position must be between -90 and 90!");

		valid(longitude, "AkjonavPosition.longitude", notification)
				.mustNotBeNull("Longitude of a position cannot be null!")
				.must(longitudeP -> new Range<>(-180D, 180D).contains(longitudeP), "Longitude of a position must be between -180 and 180!");

		return notification;
	}

	@Override
	protected void fromSerialized(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper) {
		this.latitude = objectNode.get("lat").asDouble();
		this.longitude = objectNode.get("lon").asDouble();
		if (objectNode.has("alt")) {
			ObjectNode altitudeNode = (ObjectNode) objectNode.get("alt");
			altitude = new Length(new BigDecimal(altitudeNode.get("value").asText()), Objects.requireNonNull(LengthUnit.getUnit(altitudeNode.get("unit").asText())));
		}
	}
}
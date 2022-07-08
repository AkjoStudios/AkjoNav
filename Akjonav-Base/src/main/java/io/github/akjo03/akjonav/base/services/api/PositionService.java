package io.github.akjo03.akjonav.base.services.api;

import io.github.akjo03.akjonav.model.util.position.AkjonavPosition;
import io.github.akjo03.util.math.unit.units.length.Length;
import io.github.akjo03.util.math.unit.units.length.LengthUnit;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PositionService {
	public Length calculateDistance(@NotNull AkjonavPosition startPosition, @NotNull AkjonavPosition endPosition) {
		double r = 6371 * 1000; // Radius of the earth
		double phi1 = Math.toRadians(startPosition.getLatitude());
		double phi2 = Math.toRadians(endPosition.getLatitude());

		double deltaPhi = Math.toRadians(endPosition.getLatitude() - startPosition.getLatitude());
		double deltaLambda = Math.toRadians(endPosition.getLongitude() - startPosition.getLongitude());

		double a = Math.sin(deltaPhi / 2) * Math.sin(deltaPhi / 2) + Math.cos(phi1) * Math.cos(phi2) * Math.sin(deltaLambda / 2) * Math.sin(deltaLambda / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		double d = r * c;

		if (startPosition.getAltitude() != null && endPosition.getAltitude() != null) {
			double alt1m = startPosition.getAltitude().convertTo(LengthUnit.METRE).doubleValue();
			double alt2m = endPosition.getAltitude().convertTo(LengthUnit.METRE).doubleValue();

			d = Math.sqrt(Math.pow(d, 2) + Math.pow(alt2m - alt1m, 2));
		}

		return new Length(BigDecimal.valueOf(d), LengthUnit.METRE);
	}
}
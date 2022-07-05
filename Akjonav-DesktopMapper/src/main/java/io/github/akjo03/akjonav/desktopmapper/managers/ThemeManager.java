package io.github.akjo03.akjonav.desktopmapper.managers;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

public class ThemeManager {
	public static FlatLaf loadTheme(String theme) {
		return switch (theme) {
			case "dark" -> new FlatDarkLaf();
			case "light" -> new FlatLightLaf();
			default -> throw new IllegalArgumentException("Could not load theme with name " + theme + "!");
		};
	}
}
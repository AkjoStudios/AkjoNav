package io.github.akjo03.akjonav.desktopmapper.services;

import lombok.experimental.UtilityClass;

import java.awt.*;

@UtilityClass
public class WindowService {
	public static boolean isMaximized(int state) {
		return (state & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH;
	}
}
package io.github.akjo03.akjonav.desktopmapper;

import io.github.akjo03.akjonav.desktopmapper.constants.AkjonavDesktopMapperConstants;
import io.github.akjo03.akjonav.desktopmapper.controller.HomeController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Objects;

public class AkjonavDesktopMapperFxApp extends Application {
	private ConfigurableApplicationContext applicationContext;

	@Override
	public void init() {
		String[] args = getParameters().getRaw().toArray(new String[0]);

		this.applicationContext = new SpringApplicationBuilder()
				.sources(AkjonavDesktopMapperApp.class)
				.run(args);
	}

	@Override
	public void start(@NotNull Stage stage) throws Exception {
		FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
		Parent root = fxWeaver.loadView(HomeController.class);
		Scene scene = new Scene(root);
		scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/dark.css")).toExternalForm());
		stage.setScene(scene);
		stage.setTitle(AkjonavDesktopMapperConstants.APP_TITLE);
		stage.show();
	}

	@Override
	public void stop() {
		this.applicationContext.close();
		Platform.exit();
	}
}
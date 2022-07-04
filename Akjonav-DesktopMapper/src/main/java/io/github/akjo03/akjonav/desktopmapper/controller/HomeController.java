package io.github.akjo03.akjonav.desktopmapper.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;

@Controller
@FxmlView("home.fxml")
public class HomeController {
	@FXML
	private Label helloLabel;

	public void sayHello() {
		if (helloLabel.getText().isBlank()) {
			helloLabel.setText("Hello World!");
		} else {
			helloLabel.setText("");
		}
	}
}
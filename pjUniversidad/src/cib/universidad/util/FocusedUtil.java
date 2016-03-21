package cib.universidad.util;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FocusedUtil {

	public static void setFocusOnTextField(TextField textField){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				textField.requestFocus();
			}
		});
	}

	public static void setFocusOnTextField(TextArea textField){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				textField.requestFocus();
			}
		});
	}
}

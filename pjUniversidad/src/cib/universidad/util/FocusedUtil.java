package cib.universidad.util;

import javafx.application.Platform;
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

}

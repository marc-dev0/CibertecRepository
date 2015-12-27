package cib.universidad.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertUtil {

	private static Alert alert;

	/**
	 * Método que devuelve el mensaje correspondiente a la transacción realizada por el usuario
	 * @param estate Enviar si la transacción insert, delete o update ha sido exitosa. Evalúa el estado de la transacción:  1 Éxito, 0 Fallido
	 * @param typeTrx Enviar el tipo de transacción a ejecutar. 1 Insert, 2 Update o 3 Delete
	 * @return
	 */
	public static boolean showAlertMessage(int estate, int typeTrx){

		if(estate == 1){

			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Información");
			alert.setHeaderText("Éxito");

			if(typeTrx == 1){
				alert.setContentText("Registro satisfactorio");
				alert.show();
			} else if(typeTrx == 2){
				alert.setContentText("Actualización satisfactoria");
				alert.show();
			} else {
				alert.setContentText("Eliminación satisfactoria");
				alert.show();
			}
			return true;

		} else if(estate == 0){

			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Información");
			alert.setHeaderText("Error");
			alert.setContentText("Ha ocurrido un error. Inténtelo nuevamente por favor.");
			alert.show();
			return false;

		}

		return false;
	}

	public static boolean isTableViewIsEmpty(int index, int rowCount){
		alert = new Alert(AlertType.WARNING);
		alert.setTitle("Información");
		alert.setHeaderText("Advertencia");

		if(index == -1){
			alert.setContentText("No hay ningún registro seleccionado");
			alert.show();
			return true;

		} else if(rowCount == 0){
			alert.setContentText("No se han encontrado datos");
			alert.show();
			return true;
		} else
			return false;

	}

	public static void showMessageValidateInput(String errorMessage){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Información");
		alert.setHeaderText("Error");
		alert.setContentText(errorMessage);
		alert.show();

	}
}

package cib.universidad.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertUtil {

	private static Alert alert;

	/**
	 * M�todo que devuelve el mensaje correspondiente a la transacci�n realizada por el usuario
	 * @param estate Enviar si la transacci�n insert, delete o update ha sido exitosa. Eval�a el estado de la transacci�n:  1 �xito, 0 Fallido
	 * @param typeTrx Enviar el tipo de transacci�n a ejecutar. 1 Insert, 2 Update o 3 Delete
	 * @return
	 */
	public static boolean showAlertMessage(int estate, int typeTrx){

		if(estate == 1){

			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Informaci�n");
			alert.setHeaderText("�xito");

			if(typeTrx == 1){
				alert.setContentText("Registro satisfactorio");
				alert.show();
			} else if(typeTrx == 2){
				alert.setContentText("Actualizaci�n satisfactoria");
				alert.show();
			} else {
				alert.setContentText("Eliminaci�n satisfactoria");
				alert.show();
			}
			return true;

		} else if(estate == 0){

			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Informaci�n");
			alert.setHeaderText("Error");
			alert.setContentText("Ha ocurrido un error. Int�ntelo nuevamente por favor.");
			alert.show();
			return false;

		}

		return false;
	}

	public static boolean isTableViewIsEmpty(int index, int rowCount){
		alert = new Alert(AlertType.WARNING);
		alert.setTitle("Informaci�n");
		alert.setHeaderText("Advertencia");

		if(index == -1){
			alert.setContentText("No hay ning�n registro seleccionado");
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
		alert.setTitle("Informaci�n");
		alert.setHeaderText("Error");
		alert.setContentText(errorMessage);
		alert.show();

	}
}

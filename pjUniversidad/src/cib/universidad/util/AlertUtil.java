package cib.universidad.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertUtil {

	private static Alert alert;
	private static int count;

	/**
	 * M�todo que devuelve el mensaje correspondiente a la transacci�n realizada por el usuario
	 * @param estate Enviar si la transacci�n insert, delete o update ha sido exitosa. Eval�a el estado de la transacci�n:  1 �xito, 0 Fallido
	 * @param typeTrx Enviar el tipo de transacci�n a ejecutar. 1 Insert, 2 Update o 3 Delete
	 * @return
	 */
	public static boolean showAlertMessage(int estate, int typeTrx, boolean mode){

		if(estate == 1){

			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Información");
			alert.setHeaderText("Éxito");
			count++;
			
			if(mode){
				if(typeTrx == 1 && count == 5){
					alert.setContentText("Registro satisfactorio");
					alert.show();
				} else if(typeTrx == 2 && count == 5){
					alert.setContentText("Actualización satisfactoria");
					alert.show();
				} else if(typeTrx == 3 && count == 5){
					alert.setContentText("Eliminación satisfactoria");
					alert.show();
				}
				return true;
			}
			

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
			alert.setTitle("Informaci�n");
			alert.setHeaderText("Error");
			alert.setContentText("Ha ocurrido un error. Int�ntelo nuevamente por favor.");
			alert.show();
			return false;

		}

		return false;
	}
	public static boolean showAlertMessage(int typeTrx){
	
		if(typeTrx == 4){
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Información");
			alert.setHeaderText("Error con la base de datos");
			alert.setContentText("");
			alert.show();
		}
		return false;
	}

	public static boolean showAlertMessage(int estateTrx, int typeTrx, String contentText){
		
		if(typeTrx == 4){
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Información");
			alert.setHeaderText("Error con la base de datos");
			alert.setContentText(contentText);
			alert.show();
			return true;
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

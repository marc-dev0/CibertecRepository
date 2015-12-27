package cib.universidad.view;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.text.Caret;

import cib.universidad.MainApp;
import cib.universidad.connection.MySqlConnection;
import cib.universidad.model.Carrera;
import cib.universidad.model.Person;
import cib.universidad.util.AlertUtil;
import cib.universidad.util.ControlledScreen;
import cib.universidad.util.FocusedUtil;
import cib.universidad.util.ScreensController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class CarreraOverviewController implements ControlledScreen{

	@FXML private TableView<Carrera> carreraTable;
	@FXML private TableColumn<Carrera, Integer> idCodigoColumn;
	@FXML private TableColumn<Carrera, String> descripcionColumn;

	@FXML private TextField idCodigoField;
	@FXML private TextField descripcionField;
	@FXML private ComboBox<String> estadoComboBox;

	private Stage dialogStage;

	//MySqlConnection connection;

	//referencia a la aplicacion principal
	MainApp mainApp = new MainApp();
	private ScreensController myController;

	public CarreraOverviewController(){

	}

	@FXML
	private void initialize(){

		try {
			//Inicializa la tabla con las dow columnas
			idCodigoColumn.setCellValueFactory(cellData -> cellData.getValue().codigoCarreraProperty().asObject());
			descripcionColumn.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());

			FocusedUtil.setFocusOnTextField(descripcionField);

			//showCarreraDetails(null);

			carreraTable.getSelectionModel().selectedItemProperty().addListener(
					(observable, oldValue, newValue) -> showCarreraDetails(newValue));


			estadoComboBox.setItems(mainApp.getOptionsData());
			listarCarrera();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}


	private void showCarreraDetails(Carrera carrera) {
		String value = "";

		if(carrera != null){
			if(carrera.getEstado() == true)
				value = "Activo";
			else
				value = "Inactivo";

			idCodigoField.setText(Integer.toString(carrera.getCodigoCarrera()));
			descripcionField.setText(carrera.getDescripcion());
			estadoComboBox.setValue(value);
		}
	}

	public void setMainApp(MainApp mainApp){
//		this.mainApp = mainApp;

//		listarCarrera();
//		estadoComboBox.setItems(mainApp.getOptionsData());
	}

	private void listarCarrera(){

		//limpiar el TableView
		mainApp.getCarreraData().removeAll(mainApp.getCarreraData());

		try {
			Carrera.listarCarrera(MySqlConnection.connect(), mainApp.getCarreraData());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		carreraTable.setItems(mainApp.getCarreraData());
	}

	@FXML
	private void handleSave(){

		try {

			Carrera c = new Carrera();

			c.setDescripcion(descripcionField.getText());
			c.setEstado(getEstadoComboBoxAtBoolean());

			//verificar si hay algún error en el ingreso de datos
			if(isInputValid()){
				AlertUtil.showAlertMessage(c.guardarRegistro(MySqlConnection.connect()), 1);
				listarCarrera();

			} else{
				FocusedUtil.setFocusOnTextField(descripcionField);
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleUpdate(){

		int selectedIndex = carreraTable.getSelectionModel().getSelectedIndex();
		int rowCount = carreraTable.getItems().size();

		if(AlertUtil.isTableViewIsEmpty(selectedIndex, rowCount)){
			return;
		} else {
			try {
				Carrera carrera = new Carrera();
				carrera.setCodigoCarrera(Integer.parseInt(idCodigoField.getText()));
				carrera.setDescripcion(descripcionField.getText());
				carrera.setEstado(getEstadoComboBoxAtBoolean());

				int estadoTrx = carrera.actualizarCarrera(MySqlConnection.connect());
				AlertUtil.showAlertMessage(estadoTrx, 2);

				carreraTable.getItems().set(selectedIndex, carrera);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void handleDelete(){

		int selectedIndex = carreraTable.getSelectionModel().getSelectedIndex();
		int rowCount = carreraTable.getItems().size();

		if(AlertUtil.isTableViewIsEmpty(selectedIndex, rowCount)){
			return;

		} else {
			try {
				Carrera c = new Carrera();
			    c.setCodigoCarrera(carreraTable.getItems().get(selectedIndex).getCodigoCarrera());

			    //Elimina la fila seleccionada del TableView
			    carreraTable.getItems().remove(selectedIndex);

				AlertUtil.showAlertMessage(c.eliminarCarrera(MySqlConnection.connect()), 3);

			} catch (Exception e) {
				e.printStackTrace();

			}
		}

	}

	public boolean isInputValid(){
		String errorMessage = "";

		if(descripcionField.getText() == null || descripcionField.getText().length() == 0)
			errorMessage += "Descripción no válida";

		if(errorMessage.length() == 0)
			return true;
		else {
			AlertUtil.showMessageValidateInput(errorMessage);
			return false;
		}
	}

	private boolean getEstadoComboBoxAtBoolean(){
		boolean estado = false;
		if(estadoComboBox.getSelectionModel().getSelectedIndex() == 0)
			estado = true;
		return estado;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage =  dialogStage;

	}

	@Override
	public void setScreenParent(ScreensController screenPage) {
		this.myController = screenPage;

	}

	@FXML
	private void showRootLayout(ActionEvent event){
		myController.setScreen(MainApp.screen1ID);
	}

	@FXML
	private void handleExit(){
		System.exit(0);
	}



}

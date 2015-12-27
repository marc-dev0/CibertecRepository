package cib.universidad.view;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;


import cib.universidad.MainApp;
import cib.universidad.connection.MySqlConnection;
import cib.universidad.model.Method;
import cib.universidad.model.MethodType;
import cib.universidad.util.AlertUtil;
import cib.universidad.util.ControlledScreen;
import cib.universidad.util.FocusedUtil;
import cib.universidad.util.ScreensController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.stage.Stage;

public class MethodOverviewController implements ControlledScreen, Initializable{

	@FXML private TableView<Method> methodTable;
	@FXML private TableColumn<Method, Integer> idCodigoColumn;
	@FXML private TableColumn<Method, String> descripcionColumn;
	@FXML private TableColumn<Method, String> idMethodType;


	@FXML private TextField idCodigoField;
	@FXML private TextField descripcionField;
	@FXML private ComboBox<String> estadoComboBox;
	@FXML private ComboBox<MethodType> comboListMethodType;

	//referencia a la aplicacion principal
	MainApp mainApp = new MainApp();
	private ScreensController myController;

	public MethodOverviewController(){

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		idCodigoColumn.setCellValueFactory(cellData -> cellData.getValue().idMethodProperty().asObject());
		descripcionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		idMethodType.setCellValueFactory(cellData -> cellData.getValue().descriptionMethodTypeProperty());

		try {
			MethodType.getListMethodType(MySqlConnection.connect(), mainApp.getMethodTypeData());
			//Method.getListMethod(MySqlConnection.connect(), mainApp.getMethodData());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		methodTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> showMethodDetails(newValue));
		estadoComboBox.setItems(mainApp.getOptionsData());
		comboListMethodType.setItems(mainApp.getMethodTypeData());
		doListMethod();

	}

//	@FXML
//	private void initialize(){
//
//		try {
//			//Inicializa la tabla con las dow columnas
//			idCodigoColumn.setCellValueFactory(cellData -> cellData.getValue().codigoCarreraProperty().asObject());
//			descripcionColumn.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
//
//			FocusedUtil.setFocusOnTextField(descripcionField);
//
//			//showCarreraDetails(null);
//
//			methodTable.getSelectionModel().selectedItemProperty().addListener(
//					(observable, oldValue, newValue) -> showCarreraDetails(newValue));
//
//
//			estadoComboBox.setItems(mainApp.getOptionsData());
//			listarCarrera();
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//
//	}


	private void showMethodDetails(Method method) {
		String value = "";

		if(method != null){
			if(method.getEstate() == true)
				value = "Activo";
			else
				value = "Inactivo";

			idCodigoField.setText(Integer.toString(method.getIdMethod()));
			descripcionField.setText(method.getDescription());
			estadoComboBox.setValue(value);

//			methodTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
//
//
//				@Override
//				public void changed(ObservableValue observableValue, java.lang.Object oldValue, java.lang.Object newValue) {
//					TableViewSelectionModel selectionModel = methodTable.getSelectionModel();
//					ObservableList selectedCells = selectionModel.getSelectedCells();
//					TablePosition tablePosition = (TablePosition) selectedCells.get(0);
//					Object val = tablePosition.getTableColumn().getCellData(newValue);
//					System.out.println("" + val);
//
//				}
//			});

			//comboListMethodType.setValue(new MethodTypeConverter().fromString(va));

		}
	}

	public void setMainApp(MainApp mainApp){
//		this.mainApp = mainApp;

//		listarCarrera();
//		estadoComboBox.setItems(mainApp.getOptionsData());
	}

	private void doListMethod(){

		//limpiar el TableView
		mainApp.getMethodData().removeAll(mainApp.getMethodData());

		try {
			Method.getListMethod(MySqlConnection.connect(), mainApp.getMethodData());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		methodTable.setItems(mainApp.getMethodData());
	}

	@FXML
	private void handleSave(){

		try {

			Method c = new Method();
			c.setDescription(descripcionField.getText());
			c.setEstate(getEstadoComboBoxAtBoolean());
			c.setIdMethodType(comboListMethodType.getSelectionModel().getSelectedIndex()+1);

			//verificar si hay algún error en el ingreso de datos
			if(isInputValid()){
				AlertUtil.showAlertMessage(c.saveMethod(MySqlConnection.connect()), 1);
				doListMethod();

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

		int selectedIndex = methodTable.getSelectionModel().getSelectedIndex();
		int rowCount = methodTable.getItems().size();

		if(AlertUtil.isTableViewIsEmpty(selectedIndex, rowCount)){
			return;
		} else {
			try {
				Method method = new Method();
				method.setIdMethod(Integer.parseInt(idCodigoField.getText()));
				method.setDescription(descripcionField.getText());
				method.setEstate(getEstadoComboBoxAtBoolean());
				method.setIdMethodType(comboListMethodType.getItems().get(comboListMethodType.getSelectionModel().getSelectedIndex()).getIdMethodType());

				int estadoTrx = method.updateMethod(MySqlConnection.connect());
				AlertUtil.showAlertMessage(estadoTrx, 2);

				methodTable.getItems().set(selectedIndex, method);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void handleDelete(){

		int selectedIndex = methodTable.getSelectionModel().getSelectedIndex();
		int rowCount = methodTable.getItems().size();

		if(AlertUtil.isTableViewIsEmpty(selectedIndex, rowCount)){
			return;

		} else {
			try {
				Method method = new Method();
				method.setIdMethod(methodTable.getItems().get(selectedIndex).getIdMethod());

			    //Elimina la fila seleccionada del TableView
			    methodTable.getItems().remove(selectedIndex);

				AlertUtil.showAlertMessage(method.deleteMethod(MySqlConnection.connect()), 3);

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

package cib.universidad.view;

import java.sql.Connection;
import java.sql.SQLException;
import cib.universidad.MainApp;
import cib.universidad.connection.MySqlConnection;
import cib.universidad.model.MethodType;
import cib.universidad.model.Method;
import cib.universidad.util.AlertUtil;
import cib.universidad.util.ControlledScreen;
import cib.universidad.util.FocusedUtil;
import cib.universidad.util.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MethodTypeOverViewController implements ControlledScreen{

	@FXML private TableView<MethodType> methodTypeTable;
	@FXML private TableColumn<MethodType, Integer> idCodigoColumn;
	@FXML private TableColumn<MethodType, String> descripcionColumn;

	@FXML private TextField idCodigoField;
	@FXML private TextField descripcionField;

	private Stage dialogStage;

	//MySqlConnection connection;

	//referencia a la aplicacion principal
	MainApp mainApp = new MainApp();
	private ScreensController myController;

	public MethodTypeOverViewController(){

	}

	@FXML
	private void initialize(){

		try {
			//Inicializa la tabla con las dow columnas
			idCodigoColumn.setCellValueFactory(cellData -> cellData.getValue().idMethodTypeProperty().asObject());
			descripcionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

			FocusedUtil.setFocusOnTextField(descripcionField);

			//showMethodTypeDetails(null);

			methodTypeTable.getSelectionModel().selectedItemProperty().addListener(
					(observable, oldValue, newValue) -> showMethodTypeDetails(newValue));

			listarMethodType();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private void showMethodTypeDetails(MethodType methodType) {
		if(methodType!= null){
			idCodigoField.setText(Integer.toString(methodType.getIdMethodType()));
			descripcionField.setText(methodType.getDescription());
		}

	}

	public void setMainApp(MainApp mainApp){
//		this.mainApp = mainApp;

//		listarMethodType();
//		estadoComboBox.setItems(mainApp.getOptionsData());
	}

	private void listarMethodType(){

		//limpiar el TableView
		mainApp.getMethodTypeData().removeAll(mainApp.getMethodTypeData());

		try {
			MethodType.getListMethodType(MySqlConnection.connect(), mainApp.getMethodTypeData());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		methodTypeTable.setItems(mainApp.getMethodTypeData());
	}

	@FXML
	private void handleSave(){

		try {

			MethodType c = new MethodType();
			//c.setIdMethodType(Integer.parseInt(idCodigoField.getText()));
			c.setDescription(descripcionField.getText());


			//verificar si hay alg�n error en el ingreso de datos
			if(isInputValid()){
				AlertUtil.showAlertMessage(c.saveMethodType(MySqlConnection.connect()), 1);
				listarMethodType();

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

		int selectedIndex = methodTypeTable.getSelectionModel().getSelectedIndex();
		int rowCount = methodTypeTable.getItems().size();

		if(AlertUtil.isTableViewIsEmpty(selectedIndex, rowCount)){
			return;
		} else {
			try {
				MethodType MethodType = new MethodType();
				MethodType.setIdMethodType(Integer.parseInt(idCodigoField.getText()));
				MethodType.setDescription(descripcionField.getText());

				int estadoTrx = MethodType.updateMethodType(MySqlConnection.connect());
				AlertUtil.showAlertMessage(estadoTrx, 2);

				methodTypeTable.getItems().set(selectedIndex, MethodType);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void handleDelete(){

		int selectedIndex = methodTypeTable.getSelectionModel().getSelectedIndex();
		int rowCount = methodTypeTable.getItems().size();

		if(AlertUtil.isTableViewIsEmpty(selectedIndex, rowCount)){
			return;

		} else {
			try {
				MethodType c = new MethodType();
			    c.setIdMethodType(methodTypeTable.getItems().get(selectedIndex).getIdMethodType());

			    //Elimina la fila seleccionada del TableView
			    methodTypeTable.getItems().remove(selectedIndex);

				//AlertUtil.showAlertMessage(c.deleteMethodType(MySqlConnection.connect()), 3);
				c.deleteMethodType(MySqlConnection.connect());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public boolean isInputValid(){
		String errorMessage = "";

		if(descripcionField.getText() == null || descripcionField.getText().length() == 0)
			errorMessage += "Descripci�n no v�lida";

		if(errorMessage.length() == 0)
			return true;
		else {
			AlertUtil.showMessageValidateInput(errorMessage);
			return false;
		}
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


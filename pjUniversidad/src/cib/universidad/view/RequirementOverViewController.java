package cib.universidad.view;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import cib.universidad.MainApp;
import cib.universidad.connection.MySqlConnection;
import cib.universidad.model.Method;
import cib.universidad.model.Requirement;
import cib.universidad.util.AlertUtil;
import cib.universidad.util.ControlledScreen;
import cib.universidad.util.FocusedUtil;
import cib.universidad.util.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RequirementOverViewController implements Initializable, ControlledScreen {
	@FXML private TableView<Requirement> RequirementTable;
	@FXML private TableColumn<Requirement, Integer> idCodigoColumn;
	@FXML private TableColumn<Requirement, String> descripcionColumn;

	@FXML private TextField idCodigoField;
	@FXML private ComboBox<Method> methodComboBox;
	@FXML private TextArea descripcionField;

	//referencia a la aplicacion principal
	MainApp mainApp = new MainApp();
	private ScreensController myController;

	public RequirementOverViewController(){

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			//Inicializa la tabla con las dow columnas
			idCodigoColumn.setCellValueFactory(cellData -> cellData.getValue().idRequirementProperty().asObject());
			descripcionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

			FocusedUtil.setFocusOnTextField(descripcionField);

			//showRequirementDetails(null);

			RequirementTable.getSelectionModel().selectedItemProperty().addListener(
					(observable, oldValue, newValue) -> showRequirementDetails(newValue));

			fillRequirement();
			fillMethod();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void showRequirementDetails(Requirement Requirement) {
		String value = "";

		if(Requirement != null){

			idCodigoField.setText(Integer.toString(Requirement.getIdRequirement()));
			descripcionField.setText(Requirement.getDescription());

		}
	}

	public void setMainApp(MainApp mainApp){
//		this.mainApp = mainApp;

//		listarRequirement();
//		estadoComboBox.setItems(mainApp.getOptionsData());
	}

	private void fillRequirement(){

		//limpiar el TableView
		mainApp.getRequirementData().removeAll(mainApp.getRequirementData());

		try {
			Requirement.listarRequirement(MySqlConnection.connect(), mainApp.getRequirementData());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		RequirementTable.setItems(mainApp.getRequirementData());
	}

	private void fillMethod(){

		try {
			Method.getListMethod(MySqlConnection.connect(), mainApp.getMethodData());
			
			methodComboBox.setItems(mainApp.getMethodData());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	@FXML
	private void handleSave(){
		try {
			int index = methodComboBox.getSelectionModel().getSelectedIndex() ;
			Requirement r = new Requirement();

			r.setDescription(descripcionField.getText());
			r.setIdMethod(methodComboBox.getItems().get(index).getIdMethod());
			//verificar si hay alg�n error en el ingreso de datos
			if(isInputValid()){
				AlertUtil.showAlertMessage(r.guardarRegistro(MySqlConnection.connect()), 1);
				fillRequirement();

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
		int index = methodComboBox.getSelectionModel().getSelectedIndex() ;
		int selectedIndex = RequirementTable.getSelectionModel().getSelectedIndex();
		int rowCount = RequirementTable.getItems().size();

		if(AlertUtil.isTableViewIsEmpty(selectedIndex, rowCount)){
			return;
		} else {
			try {
				Requirement requirement = new Requirement();
				requirement.setIdRequirement(Integer.parseInt(idCodigoField.getText()));
				requirement.setDescription(descripcionField.getText());
				requirement.setIdMethod(methodComboBox.getItems().get(index).getIdMethod());
				int estadoTrx = requirement.actualizarRequirement(MySqlConnection.connect());
				AlertUtil.showAlertMessage(estadoTrx, 2);

				RequirementTable.getItems().set(selectedIndex, requirement);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void handleDelete(){

		int selectedIndex = RequirementTable.getSelectionModel().getSelectedIndex();
		int rowCount = RequirementTable.getItems().size();

		if(AlertUtil.isTableViewIsEmpty(selectedIndex, rowCount)){
			return;

		} else {
			try {
				Requirement c = new Requirement();
			    c.setIdRequirement(RequirementTable.getItems().get(selectedIndex).getIdRequirement());

			    //Elimina la fila seleccionada del TableView
			    RequirementTable.getItems().remove(selectedIndex);

				AlertUtil.showAlertMessage(c.eliminarRequirement(MySqlConnection.connect()), 3);

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

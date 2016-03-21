package cib.universidad.view;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import cib.universidad.MainApp;
import cib.universidad.connection.MySqlConnection;
import cib.universidad.model.Carrera;
import cib.universidad.model.College;
import cib.universidad.model.Departament;
import cib.universidad.model.District;
import cib.universidad.model.DocumentType;
import cib.universidad.model.Parent;
import cib.universidad.model.Person;
import cib.universidad.model.Postulant;
import cib.universidad.model.Province;
import cib.universidad.util.AlertUtil;
import cib.universidad.util.ControlledScreen;
import cib.universidad.util.FocusedUtil;
import cib.universidad.util.ScreensController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class PostulantOverViewController implements Initializable, ControlledScreen{
	private ScreensController myController;
	//Postulante
	@FXML private TextField namePostulantField;
	@FXML private TextField lastNamePostulantField;
	@FXML private TextField lastNameMotherPostulantField;
	@FXML private TextField dniField;
	@FXML private TextField telFijoPostulantField;
	@FXML private TextField telCelPostulantField;
	@FXML private TextField adressPostulantField;
	@FXML private TextField emailField;
	@FXML private DatePicker birthdatePostulantDatePicker;
	@FXML private ComboBox<Departament> departamentPostulantComboBox;
	@FXML private ComboBox<Province> provincePostulantComboBox;
	@FXML private ComboBox<District> districtPostulantComboBox;
	@FXML private ComboBox<DocumentType> documentTypeComboBox;
	@FXML private RadioButton malePostulantRadioButton;
	@FXML private RadioButton femalePostulantRadioButton;

	//Tabla
	@FXML private TableView<Postulant> postulantView;
	@FXML private TableColumn<Postulant, Integer> codeColumn;
	@FXML private TableColumn<Postulant, String> nameColumn;
	@FXML private TableColumn<Postulant, String> lastNameColumn;
	@FXML private TableColumn<Postulant, String> documentColumn;
	@FXML private TableColumn<Postulant, String> telFijoColumn;
	@FXML private TableColumn<Postulant, String> telCelularColumn;
	@FXML private TableColumn<Postulant, String> adressColumn;
	@FXML private TableColumn<Postulant, String> estateColumn;
	@FXML private TableColumn<Postulant, Date> birthdayDateColumn;


	private MainApp mainApp = new MainApp();

	@FXML final ToggleGroup group = new ToggleGroup();

	private String postulantSelectionSex;
	private String parentSelectionSex;

	@Override
	public void setScreenParent(ScreensController screenPage) {
		this.myController = screenPage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		malePostulantRadioButton.setToggleGroup(group);
		femalePostulantRadioButton.setToggleGroup(group);

		codeColumn.setCellValueFactory(cellData -> cellData.getValue().idPostulanteProperty().asObject());
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
		documentColumn.setCellValueFactory(cellData -> cellData.getValue().nroDocumentoProperty());
		telFijoColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
		telCelularColumn.setCellValueFactory(cellData -> cellData.getValue().cellNumberProperty());
		adressColumn.setCellValueFactory(cellData -> cellData.getValue().adressProperty());
		estateColumn.setCellValueFactory(cellData -> cellData.getValue().estateProperty());
		//birthdayDateColumn.setCellValueFactory(cellData -> cellData.getValue().getBirthDate());
		postulantView.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> showPostulantDetails(newValue));
		postulantView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		try
		{
			//personID = Person.getPersonID(MySqlConnection.getConnection());
			Departament.getListDepartament(MySqlConnection.getConnection(), mainApp.getDepartamentData());
			Carrera.listarCarrera(MySqlConnection.getConnection(), mainApp.getCarreraData());
			DocumentType.getListDocumentType(MySqlConnection.getConnection(), mainApp.getDocumentTypeData());
			Postulant.getListPostulant(MySqlConnection.getConnection(), mainApp.getPostulantData());

		} catch (SQLException e) {
			e.printStackTrace();
		}

			//LLenamos los departamentos a ambos combos
			departamentPostulantComboBox.setItems(mainApp.getDepartamentData());
			documentTypeComboBox.setItems(mainApp.getDocumentTypeData());
			postulantView.setItems(mainApp.getPostulantData());
			departamentPostulantComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Departament>() {
				@Override
				public void changed(ObservableValue<? extends Departament> observable, Departament oldValue,
						Departament newValue) {
					try {
						//Limpiamos los items de ambos ComboBox
						provincePostulantComboBox.getItems().clear();
						districtPostulantComboBox.getItems().clear();

						try {
							Province.getListProvince(MySqlConnection.connect(), mainApp.getProvinceData(), newValue.getIdDepartamento());
						} catch (SQLException e) {
							e.printStackTrace();
						}
						provincePostulantComboBox.setItems(mainApp.getProvinceData());
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
			});

			provincePostulantComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Province>() {
				@Override
				public void changed(ObservableValue<? extends Province> observable, Province oldValue,
						Province newValue) {
					try {

						try {
							//valida que solo liste el distrito cuando el item haya sido cambiado(Provincia) y no sea nulo
							if(newValue != null)
								District.getListDistrict(MySqlConnection.connect(), mainApp.getDistrictData(), newValue.getIdProvince());
						} catch (SQLException e) {
							System.out.println(e.getMessage());
						}

						districtPostulantComboBox.setItems(mainApp.getDistrictData());

					} catch (Exception e) {
						System.out.println(e.getMessage());
						e.printStackTrace();
					}
				}
			});

			group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

				@Override
				public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
					try {

						if(group.getSelectedToggle() != null){
							RadioButton chk = (RadioButton) newValue.getToggleGroup().getSelectedToggle();
							if(chk.getText().equals("Masculino")){
								postulantSelectionSex = "M";

							} else {
								postulantSelectionSex = "F";

							}
							//System.out.println(postulantSelectionSex + " " + parentSelectionSex );
						}

					} catch (Exception e2) {
						System.out.println(e2.getMessage());
					}

				}
			});
	}

	@FXML
	private void handleExit(){
		System.exit(0);
	}

	@FXML
	private void showRootLayout(ActionEvent event){
		myController.setScreen(MainApp.screen1ID);
	}

	@FXML
	private void handleSave() {
		savePostulant(new Postulant());
		postulantView.setItems(mainApp.getPostulantData());
	}

	@FXML
	private void handleDelete(){
		int selectedIndex = postulantView.getSelectionModel().getSelectedIndex();
		int rowCount = postulantView.getItems().size();

		if(AlertUtil.isTableViewIsEmpty(selectedIndex, rowCount)){
			return;

		} else {
			try {
				Postulant postulant = new Postulant();
				postulant.setIdPostulante(postulantView.getItems().get(selectedIndex).getIdPostulante());

			    //Elimina la fila seleccionada del TableView
			    postulantView.getItems().remove(selectedIndex);

				AlertUtil.showAlertMessage(postulant.deletePostulant(MySqlConnection.connect()), 3);

			} catch (Exception e) {
				e.printStackTrace();

			}
		}

	}

	private void savePostulant(Postulant postulant){
		Person person = new Person();
		savePerson(person, "Postulant");
		try {

			if(isInputValid()){
				int index = documentTypeComboBox.getSelectionModel().getSelectedIndex();
				int index1 = districtPostulantComboBox.getSelectionModel().getSelectedIndex();

				postulant.setIdDocumentType(documentTypeComboBox.getItems().get(index).getIdDocumentType());
				postulant.setNroDocumento(dniField.getText());
				postulant.setPhoneNumber(telFijoPostulantField.getText());
				postulant.setBirthDate(Date.valueOf(birthdatePostulantDatePicker.getValue()));
				postulant.setEmail(emailField.getText());
				postulant.setIdDistrict(districtPostulantComboBox.getItems().get(index1).getIdDistrict());
				postulant.setAdress(adressPostulantField.getText());
				postulant.setIdPersona(person.getPersonID(MySqlConnection.getConnection()));
				postulant.setEstate("E");
				AlertUtil.showAlertMessage(postulant.savePostulant(MySqlConnection.getConnection()), 1);

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void savePerson(Person person, String typePerson){
		try {
			//verificar si hay alg�n error en el ingreso de datos

			if(isInputValid()){
				if(typePerson.equals("Postulant")){
					person.setName(namePostulantField.getText());
					person.setLastName(lastNamePostulantField.getText());
					person.setLastNameMother(lastNameMotherPostulantField.getText());
					person.setCellNumber(telCelPostulantField.getText());
					person.setSex(postulantSelectionSex);
					AlertUtil.showAlertMessage(person.savePerson(MySqlConnection.connect()), 1);
				}

				//listarMethodType();

			} else{
				FocusedUtil.setFocusOnTextField(namePostulantField);
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showPostulantDetails(Postulant postulant) {
		boolean flagMale = false, flagFemale= false;

		if(postulant.getSex() == "M")
			flagMale = true;
		if(postulant.getSex() == "F")
			flagFemale = true;

			namePostulantField.setText(postulant.getName());
			lastNamePostulantField.setText(postulant.getLastName());
			lastNameMotherPostulantField.setText(postulant.getLastNameMother());
			dniField.setText(postulant.getNroDocumento());
			telFijoPostulantField.setText(postulant.getPhoneNumber());
			telCelPostulantField.setText(postulant.getCellNumber());
			emailField.setText(postulant.getEmail());
			adressPostulantField.setText(postulant.getAdress());

	}

	public boolean isInputValid(){
		String errorMessage = "";

		if(namePostulantField.getText() == null || namePostulantField.getText().length() == 0)
			errorMessage += "\nNombre del Postulante inv�lido";

		if(lastNamePostulantField.getText() == null || lastNamePostulantField.getText().length() == 0)
			errorMessage += "\nApellido Paterno del Postulante inv�lido";

//		if(lastNameMotherPostulantField.getText() == null || lastNameMotherPostulantField.getText().length() == 0)
//			errorMessage += "\nApellido Materno del Postulante inv�lido";

		if(dniField.getText() == null || dniField.getText().length() == 0)
			errorMessage += "\nDNI inv�lido";

//		if(telFijoPostulantField.getText() == null || telFijoPostulantField.getText().length() == 0)
//			errorMessage += "\nTel�fono Fijo inv�lido";

		if(telCelPostulantField.getText() == null || telCelPostulantField.getText().length() == 0)
			errorMessage += "\nTel�fono Celular inv�lido";

		if(birthdatePostulantDatePicker.getValue() == null)
			errorMessage += "\nSeleccione la Fecha de Nacimiento del Postulante";

		if(adressPostulantField.getText() == null || adressPostulantField.getText().length() == 0)
			errorMessage += "\nDireccion del Postulante inv�lido";

		if(emailField.getText() == null || emailField.getText().length() == 0)
			errorMessage += "\nCorreo electr�nico inv�lido";

		if(namePostulantField.getText() == null || namePostulantField.getText().length() == 0)
			errorMessage += "\nNombre del Postulante inv�lido";

		if(errorMessage.length() == 0)
			return true;
		else {
			AlertUtil.showMessageValidateInput(errorMessage);
			return false;
	}


	}
}
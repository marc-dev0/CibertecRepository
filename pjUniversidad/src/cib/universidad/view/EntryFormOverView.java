package cib.universidad.view;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import cib.universidad.MainApp;
import cib.universidad.connection.MySqlConnection;
import cib.universidad.model.Carrera;
import cib.universidad.model.College;
import cib.universidad.model.Departament;
import cib.universidad.model.District;
import cib.universidad.model.DocumentType;
import cib.universidad.model.EntryForm;
import cib.universidad.model.MethodType;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class EntryFormOverView implements ControlledScreen,Initializable {
	private int personID, count;
	private ScreensController myController;
	//Ficha Inscripci�n
	@FXML private ComboBox<Carrera> carreraComboBox;
	@FXML private DatePicker dateRegister;
	@FXML private TextArea remarksField;

	//Postulante
	//@FXML private TextField idCodigoField;
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

	//Parientes
	@FXML private TextField nameParentField;
	@FXML private TextField lastNameParentField;
	@FXML private TextField lastNameMotherParentField;
	@FXML private TextField telCelParentField;
	@FXML private RadioButton maleParentRadioButton;
	@FXML private RadioButton femaleParentRadioButton;
	@FXML private ComboBox<String> laborSituationComboBox;

	//ColegioProcedencia
	@FXML private ComboBox<Departament> departamentCollegeComboBox;
	@FXML private ComboBox<Province> provinceCollegeComboBox;
	@FXML private ComboBox<District> districtCollegeComboBox;
	@FXML private TextField collegeField;
	@FXML private DatePicker birthdateCollegeDatePicker;
	@FXML private TextField adressCollegeField;

	private MainApp mainApp = new MainApp();

	@FXML final ToggleGroup group = new ToggleGroup();
	@FXML final ToggleGroup groupParent = new ToggleGroup();

	private String postulantSelectionSex;
	private String parentSelectionSex;

	@Override
	public void setScreenParent(ScreensController screenPage) {
		this.myController = screenPage;
	}

	@FXML
	private void handleExit(){
		System.exit(0);
	}

	@FXML
	private void showRootLayout(ActionEvent event){
		myController.setScreen(MainApp.screen1ID);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		malePostulantRadioButton.setToggleGroup(group);
		femalePostulantRadioButton.setToggleGroup(group);
		maleParentRadioButton.setToggleGroup(groupParent);
		femaleParentRadioButton.setToggleGroup(groupParent);

		try
		{
			//personID = Person.getPersonID(MySqlConnection.getConnection());
			Departament.getListDepartament(MySqlConnection.getConnection(), mainApp.getDepartamentData());
			Carrera.listarCarrera(MySqlConnection.getConnection(), mainApp.getCarreraData());
			DocumentType.getListDocumentType(MySqlConnection.getConnection(), mainApp.getDocumentTypeData());

		} catch (SQLException e) {
			e.printStackTrace();
		}

			//LLenamos los departamentos a ambos combos
			departamentPostulantComboBox.setItems(mainApp.getDepartamentData());
			departamentCollegeComboBox.setItems(mainApp.getDepartamentData());
			laborSituationComboBox.setItems(mainApp.getLaborSituation());
			carreraComboBox.setItems(mainApp.getCarreraData());
			documentTypeComboBox.setItems(mainApp.getDocumentTypeData());

			//Realizamos el mostrado de distrito de acuerdo al departamento que haya sido seleccionado
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
			
			departamentCollegeComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Departament>() {
				@Override
				public void changed(ObservableValue<? extends Departament> observable, Departament oldValue,
						Departament newValue) {
					//Limpiamos los items de ambos ComboBox
					provinceCollegeComboBox.getItems().clear();
					districtCollegeComboBox.getItems().clear();
					try {
						Province.getListProvince(MySqlConnection.connect(), mainApp.getProvinceData(), newValue.getIdDepartamento());
					} catch (SQLException e) {
						e.printStackTrace();
					}
					provinceCollegeComboBox.setItems(mainApp.getProvinceData());
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
			provinceCollegeComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Province>() {

				@Override
				public void changed(ObservableValue<? extends Province> observable, Province oldValue,
						Province newValue) {
					try {
						//valida que solo liste el distrito cuando el item haya sido cambiado(Provincia) y no sea nulo
						if(newValue != null)
							District.getListDistrict(MySqlConnection.connect(), mainApp.getDistrictData(), newValue.getIdProvince());
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					}

					districtCollegeComboBox.setItems(mainApp.getDistrictData());

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
			groupParent.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

				@Override
				public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
					try {

						if(groupParent.getSelectedToggle() != null){
							RadioButton chk = (RadioButton) newValue.getToggleGroup().getSelectedToggle();
							if(chk.getText().equals("Masculino")){
								parentSelectionSex = "M";

							} else {
								parentSelectionSex = "F";

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
	private void handleSave(ActionEvent event){
		if(isInputValid()){
			savePostulant(new Postulant());
			saveParent(new Parent());
			saveCollege(new College());
			saveEntryForm(new EntryForm());
		}
	
	}

	@FXML 
	private void handleClean(ActionEvent event){
		
	}
	
	private void saveEntryForm(EntryForm entryForm) {

		try {

//			if(isInputValid()){
//				int index = carreraComboBox.getSelectionModel().getSelectedIndex();
				entryForm.setFechaRegistro(Date.valueOf(dateRegister.getValue()));
				entryForm.setEstate("R");
				entryForm.setObservacion(remarksField.getText());
				entryForm.setIdPostulante(Postulant.getPostulantID(MySqlConnection.getConnection()));
				entryForm.setIdColegio(College.getCollegeID(MySqlConnection.getConnection()));
				entryForm.setIdUsuario(1);
				AlertUtil.showAlertMessage(entryForm.saveEntryForm(MySqlConnection.getConnection()), 1, true);
//			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	private void saveCollege(College college){
		try {

//			if(isInputValid()){
				int index = districtPostulantComboBox.getSelectionModel().getSelectedIndex();
				college.setNameCollege(collegeField.getText());
				college.setEndDate(Date.valueOf(birthdateCollegeDatePicker.getValue()));
				college.setAdressCollege(adressCollegeField.getText());
				college.setIdDistrict(districtCollegeComboBox.getItems().get(index).getIdDistrict());
				college.setIdPostulant(Postulant.getPostulantID(MySqlConnection.getConnection()));
				AlertUtil.showAlertMessage(college.saveCollege(MySqlConnection.getConnection()), 1, true);
//			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void saveParent(Parent parent){
		Person person = new Person();
		savePerson(person, "Parent");

		try {

//			if(isInputValid()){
				parent.setLaborSituation(laborSituationComboBox.getSelectionModel().getSelectedItem().toString());
				parent.setIdPerson(person.getPersonID(MySqlConnection.getConnection()));

				AlertUtil.showAlertMessage(parent.savePostulant(MySqlConnection.getConnection()), 1, true);

//			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void savePostulant(Postulant postulant){
		Person person = new Person();
		savePerson(person, "Postulant");
		try {

//			if(isInputValid()){
				int index = documentTypeComboBox.getSelectionModel().getSelectedIndex();
				int index1 = districtPostulantComboBox.getSelectionModel().getSelectedIndex();
				int index2 = carreraComboBox.getSelectionModel().getSelectedIndex();
				
				postulant.setIdDocumentType(documentTypeComboBox.getItems().get(index).getIdDocumentType());
				postulant.setNroDocumento(dniField.getText());
				postulant.setPhoneNumber(telFijoPostulantField.getText());
				postulant.setBirthDate(Date.valueOf(birthdatePostulantDatePicker.getValue()));
				postulant.setEmail(emailField.getText());
				postulant.setIdDistrict(districtPostulantComboBox.getItems().get(index1).getIdDistrict());
				postulant.setAdress(adressPostulantField.getText());
				postulant.setIdPersona(person.getPersonID(MySqlConnection.getConnection()));
				postulant.setIdCareer(carreraComboBox.getItems().get(index2).getCodigoCarrera());
				postulant.setEstate("E");
				AlertUtil.showAlertMessage(postulant.savePostulant(MySqlConnection.getConnection()), 1, true);

//			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void savePerson(Person person, String typePerson){
		try {
			//verificar si hay alg�n error en el ingreso de datos

//			if(isInputValid()){
				//Person c = new Person();
				//c.setIdMethodType(Integer.parseInt(idCodigoField.getText()));
				//person.setIdPerson(person.getIdPerson());
				if(typePerson.equals("Postulant")){
					person.setName(namePostulantField.getText());
					person.setLastName(lastNamePostulantField.getText());
					person.setLastNameMother(lastNameMotherPostulantField.getText());
					person.setCellNumber(telCelPostulantField.getText());
					person.setSex(postulantSelectionSex);
					AlertUtil.showAlertMessage(person.savePerson(MySqlConnection.connect()), 1, true);
				} else if(typePerson.equals("Parent")){
					person.setName(nameParentField.getText());
					person.setLastName(lastNameParentField.getText());
					person.setLastNameMother(lastNameMotherParentField.getText());
					person.setCellNumber(telCelParentField.getText());
					person.setSex(parentSelectionSex);
					AlertUtil.showAlertMessage(person.savePerson(MySqlConnection.connect()), 1, true);
				} else {
					return;
				}

				//listarMethodType();

//			} else{
//				FocusedUtil.setFocusOnTextField(namePostulantField);
//				return;
//			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public boolean isInputValid(){
		String errorMessage = "";

		if(dateRegister.getValue() == null)
			errorMessage += "Seleccione la fecha de Registro, por favor.";

		if(carreraComboBox.getSelectionModel().getSelectedIndex() == -1)
			errorMessage += "\nSeleccione una carrera, por favor.";

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

		if(departamentPostulantComboBox.getSelectionModel().getSelectedIndex() == -1
				|| departamentCollegeComboBox.getSelectionModel().getSelectedIndex() == -1)
			errorMessage += "\nSeleccione un Departamento, por favor.";

		if(provincePostulantComboBox.getSelectionModel().getSelectedIndex() == -1
				|| provinceCollegeComboBox.getSelectionModel().getSelectedIndex() == -1)
			errorMessage += "\nSeleccione una Provincia, por favor.";

		if(districtPostulantComboBox.getSelectionModel().getSelectedIndex() == -1
				|| provinceCollegeComboBox.getSelectionModel().getSelectedIndex() == -1)
			errorMessage += "\nSeleccione un Distrito, por favor.";

		if(namePostulantField.getText() == null || namePostulantField.getText().length() == 0)
			errorMessage += "\nNombre del Postulante inv�lido";

		if(nameParentField.getText() == null || nameParentField.getText().length() == 0)
			errorMessage += "\nNombre del Apoderdo inv�lido";

		if(lastNameParentField.getText() == null || lastNameParentField.getText().length() == 0)
			errorMessage += "\nApellido Paterno del Apoderado inv�lido";

		if(laborSituationComboBox.getSelectionModel().getSelectedIndex() == -1)
			errorMessage += "\nSeleccione una situaci�n laboral, por favor.";

		if((!femalePostulantRadioButton.isSelected() && !malePostulantRadioButton.isSelected())
				|| (!femaleParentRadioButton.isSelected() && !maleParentRadioButton.isSelected()))
			errorMessage += "\nSeleccione el g�nero, por favor.";

		if(errorMessage.length() == 0)
			return true;
		else {
			AlertUtil.showMessageValidateInput(errorMessage);
			return false;
	}

//	@FXML
//	private void showProvince(ActionEvent event){
//		//mainApp.getProvinceData().removeAll(mainApp.getProvinceData());
//		try {
//			provincePostulantComboBox.getItems().clear();
//			int index = departamentPostulantComboBox.getSelectionModel().getSelectedIndex();
//			int idDepartament = departamentPostulantComboBox.getItems().get(index).getIdDepartamento();
//			try {
//				Province.getListProvince(MySqlConnection.connect(), mainApp.getProvinceData(), idDepartament);
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			provincePostulantComboBox.setItems(mainApp.getProvinceData());
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//
//	}
//
//	@FXML
//	private void showDistrict(ActionEvent event){
//
//		districtPostulantComboBox.getItems().clear();
//		int index = provincePostulantComboBox.getSelectionModel().getSelectedIndex();
//		int idProvince = provincePostulantComboBox.getItems().get(index).getIdProvince();
//
//		try {
//			District.getListDistrict(MySqlConnection.connect(), mainApp.getDistrictData(), idProvince);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//
//		districtPostulantComboBox.setItems(mainApp.getDistrictData());
//	}

	}
}

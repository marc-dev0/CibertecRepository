package cib.universidad.view;

import java.net.URL;
import java.util.ResourceBundle;

import cib.universidad.MainApp;
import cib.universidad.connection.MySqlConnection;
import cib.universidad.model.Carrera;
import cib.universidad.model.Postulant;
import cib.universidad.util.ControlledScreen;
import cib.universidad.util.ScreensController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class RecordNotesOverViewController implements Initializable, ControlledScreen{
	@FXML private TextField codeField;
	@FXML private TextField noteField;
	@FXML private TableView<Postulant> postulantTableView;
	@FXML private ComboBox<Carrera> carreraComboBox;

	@FXML private TableColumn<Postulant, Integer> codeColumn;
	@FXML private TableColumn<Postulant, String> nameColumn;
	@FXML private TableColumn<Postulant, String> lastNameColumn;
	@FXML private TableColumn<Postulant, String> estateColumn;

	private ScreensController myController;
	private MainApp mainApp = new MainApp();

	 public RecordNotesOverViewController() {

	 }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		codeColumn.setCellValueFactory(cellData -> cellData.getValue().idPostulanteProperty().asObject());
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
		estateColumn.setCellValueFactory(cellData -> cellData.getValue().estateProperty());
		try {
			//llenado de las carreras en arreglo de listas
			Carrera.listarCarrera(MySqlConnection.getConnection(), mainApp.getCarreraData());
			Postulant.getListPostulant(MySqlConnection.getConnection(), mainApp.getPostulantData());
		} catch (Exception e) {
			System.out.println("error"+ e.getMessage());
		}

		carreraComboBox.setItems(mainApp.getCarreraData());
		postulantTableView.setItems(mainApp.getPostulantData());
	}

	@Override
	public void setScreenParent(ScreensController screenPage) {
		this.myController = screenPage;

	}

}


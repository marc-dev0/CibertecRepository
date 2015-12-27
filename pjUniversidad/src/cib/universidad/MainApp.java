package cib.universidad;

import java.io.IOException;
import java.util.Observable;

import cib.universidad.model.Carrera;
import cib.universidad.model.Departament;
import cib.universidad.model.District;
import cib.universidad.model.DocumentType;
import cib.universidad.model.Method;
import cib.universidad.model.MethodType;
import cib.universidad.model.PaymentDetail;
import cib.universidad.model.Postulant;
import cib.universidad.model.Province;
import cib.universidad.util.ScreensController;
import cib.universidad.view.CarreraOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

	public static String screen1ID = "main";
	public static String screen1File = "view/RootLayoutv1.fxml";
	public static String screen2ID = "screen2";
	public static String screen2File = "view/CarreraOverView.fxml";
	public static String screen3ID = "screen3";
	public static String screen3File = "view/MethodOverview.fxml";
	public static String screen4ID = "screen4";
	public static String screen4File = "view/MethodTypeOverView.fxml";
	public static String screen5ID = "screen5";
	public static String screen5File = "view/EntryFormOverView.fxml";
	public static String screen6ID = "screen6";
	public static String screen6File = "view/PayFormOverView.fxml";
	public static String screen7ID = "screen7";
	public static String screen7File = "view/RecordNotes.fxml";
	public static String screen8ID = "screen8";
	public static String screen8File = "view/PostulantOverView.fxml";
	//This method is automatically called when the application is launched from within the main method
	//Stage -> BorderPane, Scenes.
	//Scenes -> AnchorPane, Textbox, UI elements.
	private Stage primaryStage;
	private BorderPane rootLayout;
	private Group root = new Group();


	private ObservableList<Carrera> carreraData = FXCollections.observableArrayList();
	private ObservableList<MethodType> methodTypeData = FXCollections.observableArrayList();
	private ObservableList<Method> methodData = FXCollections.observableArrayList();
	private ObservableList<Departament> departamentData = FXCollections.observableArrayList();
	private ObservableList<Province> provinceData = FXCollections.observableArrayList();
	private ObservableList<DocumentType> documentTypeData = FXCollections.observableArrayList();
	private ObservableList<District> districtData = FXCollections.observableArrayList();
	private ObservableList<PaymentDetail> paymentDetailData = FXCollections.observableArrayList();
	private ObservableList<Postulant> postulantData = FXCollections.observableArrayList();

	private ObservableList<String> options = FXCollections.observableArrayList(
			"Activo","Inactivo"
			);
	private ObservableList<String> laborSituation = FXCollections.observableArrayList(
			"Trabajando", "Sin empleo", "Negocio propio"
			);

	public static void main(String[] args) {
		launch(args);
	}

	public MainApp(){

	}
	/**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage(){
		return primaryStage;
	}

	public Group getGroup(){
		return root;
	}
	/**
	 * Returns the data as an observable list of Carrera
	 * @return
	 */
	public ObservableList<Carrera> getCarreraData() {
		return carreraData;
	}

	public ObservableList<MethodType> getMethodTypeData(){
		return methodTypeData;
	}

	public ObservableList<Method> getMethodData(){
		return methodData;
	}

	public ObservableList<Departament> getDepartamentData(){
		return departamentData;
	}

	public ObservableList<Province> getProvinceData(){
		return provinceData;
	}

	public ObservableList<District> getDistrictData(){
		return districtData;
	}

	public ObservableList<String> getOptionsData() {
		return options;
	}

	public ObservableList<String> getLaborSituation(){
		return laborSituation;
	}

	public ObservableList<DocumentType> getDocumentTypeData(){
		return documentTypeData;
	}

	public ObservableList<PaymentDetail> getPaymentDetail(){
		return paymentDetailData;
	}

	public ObservableList<Postulant> getPostulantData(){
		return postulantData;
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Examen Ordinario I");

		//initRootLayout();

		//load icon

		ScreensController mainContainer = new ScreensController();

		mainContainer.loadScreen(MainApp.screen1ID, MainApp.screen1File);
		mainContainer.loadScreen(MainApp.screen2ID, MainApp.screen2File);
		mainContainer.loadScreen(MainApp.screen3ID, MainApp.screen3File);
		mainContainer.loadScreen(MainApp.screen4ID, MainApp.screen4File);
		mainContainer.loadScreen(MainApp.screen5ID, MainApp.screen5File);
		mainContainer.loadScreen(MainApp.screen6ID, MainApp.screen6File);
		mainContainer.loadScreen(MainApp.screen7ID, MainApp.screen7File);
		mainContainer.loadScreen(MainApp.screen8ID, MainApp.screen8File);
		mainContainer.setScreen(MainApp.screen1ID);

		//Group root = new Group();
		root.getChildren().addAll(mainContainer);

		Scene scene = new Scene(root);
		this.primaryStage.setScene(scene);
		this.primaryStage.show();


		//primaryStage.setMaximized(true);

	}

	/**
	 * Initializes the root layout
	 */
	private void initRootLayout() {

		try {
			//Load root layout from fxml file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			//Show the scene containing the root layout
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setMaximized(true);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void showCarreraOverView(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/CarreraOverView.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Mantenimiento Carrera");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			//give the controller acces to the main app
			CarreraOverviewController controller = loader.getController();
			controller.setDialogStage(dialogStage);


			dialogStage.showAndWait();


			//dialogStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

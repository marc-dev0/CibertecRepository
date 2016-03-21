package cib.universidad.view;

import java.net.URL;
import java.util.ResourceBundle;

import cib.universidad.MainApp;
import cib.universidad.util.ControlledScreen;
import cib.universidad.util.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RootLayoutController implements ControlledScreen, Initializable{

	private MainApp mainApp;
	private ScreensController myController;

	@FXML private ImageView imageView;

	private void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
	}

	@FXML
	private void handleExit(){
		System.exit(0);
	}

	@FXML
	private void handleCarreraOveviewController(){
		mainApp.showCarreraOverView();
	}

	@Override
	public void setScreenParent(ScreensController screenPage) {
		this.myController = screenPage;

	}

	@FXML
	private void showCarreraOverView(ActionEvent event){
		myController.setScreen(MainApp.screen2ID);
	}

	@FXML
	private void showMethodOverView(ActionEvent event){
		myController.setScreen(MainApp.screen3ID);
	}

	@FXML
	private void showMethodTypeOverView(ActionEvent event){
		myController.setScreen(MainApp.screen4ID);
	}

	@FXML
	private void showEntryFormOverView(ActionEvent event){
		myController.setScreen(MainApp.screen5ID);
	}

	@FXML
	private void showPayFormOverView(ActionEvent event){
		myController.setScreen(MainApp.screen6ID);
	}

	@FXML
	private void showRecordNotes(ActionEvent event){
		myController.setScreen(MainApp.screen7ID);
	}

	@FXML
	private void showPostulantOverView(ActionEvent event){
		myController.setScreen(MainApp.screen8ID);
	}

	@FXML 
	private void showRequirementViewer(ActionEvent event){
		myController.setScreen(MainApp.screen9ID);
	}

	@FXML 
	private void showRequirementOverView(ActionEvent event){
		myController.setScreen(MainApp.screen10ID);
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {

			imageView.setImage(new Image(MainApp.class.getResourceAsStream("/uni.jpg")));
			//mainApp.getGroup().getChildren().addAll(imageView);
//			imageView.fitWidthProperty().bind(mainApp.getPrimaryStage().widthProperty());
//			imageView.fitHeightProperty().bind(mainApp.getPrimaryStage().heightProperty());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}


}

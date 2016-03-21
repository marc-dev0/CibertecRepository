package cib.universidad.view;

import java.beans.MethodDescriptor;
import java.net.URL;
import java.util.ResourceBundle;

import cib.universidad.MainApp;
import cib.universidad.connection.MySqlConnection;
import cib.universidad.model.Method;
import cib.universidad.model.MethodType;
import cib.universidad.model.Requirement;
import cib.universidad.util.ControlledScreen;
import cib.universidad.util.ScreensController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RequirementViewerController implements Initializable, ControlledScreen{
	
	private @FXML TableView<Requirement> requirementView;
	private @FXML TableColumn<Requirement, Integer> idRequirementColumn;
	private @FXML TableColumn<Requirement, String> descriptionColumn;
	
	private @FXML ComboBox<MethodType> methodTypeComboBox;
	private @FXML ComboBox<Method> methodComboBox;
	
	private MainApp mainApp = new MainApp();
	private ScreensController myController;
	
	public RequirementViewerController() {
	
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idRequirementColumn.setCellValueFactory(cellData -> cellData.getValue().idRequirementProperty().asObject());
		descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		
		try {
			
			MethodType.getListMethodType(MySqlConnection.getConnection(), mainApp.getMethodTypeData());
			
		} catch (Exception e) {
		System.out.println(e.getMessage());
			e.printStackTrace();
		}
		methodTypeComboBox.setItems(mainApp.getMethodTypeData());
		
		methodTypeComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MethodType>() {
			
			@Override
			public void changed(ObservableValue<? extends MethodType> observable, MethodType oldValue,
					MethodType newValue) {
				methodComboBox.getItems().clear();
				requirementView.getItems().clear();
				try {
					int idMethodType = newValue.getIdMethodType();
					Method.getListMethod(MySqlConnection.getConnection(), mainApp.getMethodData(), idMethodType);
				} catch (Exception e) {
					e.printStackTrace();
				}
				methodComboBox.setItems(mainApp.getMethodData());
			}
		});
		
		
		methodComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Method>() {

			@Override
			public void changed(ObservableValue<? extends Method> observable, Method oldValue, Method newValue) {
				requirementView.getItems().clear();
				
				try{
					int idMethod = 0;
					if(newValue != null){
						 idMethod = newValue.getIdMethod();	
					}
					
					
					Requirement.getListRequirementByIdMethod(MySqlConnection.getConnection(), mainApp.getRequirementData(), idMethod);
				} catch (Exception e){
					e.printStackTrace();
				}
				
				requirementView.setItems(mainApp.getRequirementData());
			}
		});
		
	}

	
	@Override
	public void setScreenParent(ScreensController screenPage) {
		this.myController = screenPage;
	}

	public void showRootLayout(){
		myController.setScreen(MainApp.screen1ID);
	
	}

	@FXML
	private void handleExit(){
		System.exit(0);
	}


}

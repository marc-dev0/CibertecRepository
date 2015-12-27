package cib.universidad.view;

import java.beans.FeatureDescriptor;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;

import cib.universidad.MainApp;
import cib.universidad.connection.MySqlConnection;
import cib.universidad.model.College;
import cib.universidad.model.Method;
import cib.universidad.model.PaymentDetail;
import cib.universidad.model.PaymentHeader;
import cib.universidad.util.AlertUtil;
import cib.universidad.util.ControlledScreen;
import cib.universidad.util.DecimalFormatUtil;
import cib.universidad.util.FocusedUtil;
import cib.universidad.util.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PayFormOverviewController implements Initializable, ControlledScreen{
	private ScreensController myController;
	final String INITIAL_VALUE = "1";

	@FXML private TableView<PaymentDetail> detailPaymentView;
	@FXML private TableColumn<PaymentDetail, Integer> itemColumn;
	@FXML private TableColumn<PaymentDetail, Integer> idMethod;
	@FXML private TableColumn<PaymentDetail, String> nameMethodColumn;
	@FXML private TableColumn<PaymentDetail, Integer> quantityColumn;
	@FXML private TableColumn<PaymentDetail, Double> amountColumn;

	@FXML private Spinner spinner;
	@FXML private TextField nameLastNameField;
	@FXML private DatePicker dateRegister;
	@FXML private ComboBox<Method> methodComboBox;
	@FXML private Label correlativeLabel;
	@FXML private Label totalLabel;
	@FXML private Button addButton;

	private MainApp mainApp = new MainApp();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, Integer.parseInt(INITIAL_VALUE)));

		try {

			itemColumn.setCellValueFactory(cellData -> cellData.getValue().itemProperty().asObject());
			idMethod.setCellValueFactory(cellData -> cellData.getValue().idMethodProperty().asObject());
			nameMethodColumn.setCellValueFactory(cellData -> cellData.getValue().getNameMethodProperty());
			quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
			amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());

			Method.getListMethod(MySqlConnection.getConnection(), mainApp.getMethodData());
			//PaymentDetail.getListDetailPayment(MySqlConnection.getConnection(), mainApp.getPaymentDetail());
			getNewSerial();

		} catch (Exception e) {
			System.out.println("Error en PayFormOverView: " + e.getMessage());
		}


		methodComboBox.setItems(mainApp.getMethodData());
		//detailPaymentView.setItems(mainApp.getPaymentDetail());
	}

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

	@FXML
	private void handleAdd(){
		int index = methodComboBox.getSelectionModel().getSelectedIndex();

		if(index != -1){

			int idMethod = methodComboBox.getItems().get(index).getIdMethod(), quantity = (int) spinner.getValue();
			double price = methodComboBox.getItems().get(index).getPriceMethod();

			PaymentDetail p = new PaymentDetail();
			p.setIdMethod(idMethod);
			p.setQuantity(quantity);
			p.setAmount(quantity * price);
			p.setCorrelative(Integer.parseInt(correlativeLabel.getText()));
			mainApp.getPaymentDetail().add(p);
			detailPaymentView.setItems(mainApp.getPaymentDetail());

			calculateTotal();

		} else {
			AlertUtil.showMessageValidateInput("Seleccione un procedimiento, por favor.");
		}

	}

	@FXML
	private void handleSave(){
		savePaymentHeader(new PaymentHeader());

	}

	@FXML
	private void handleCleanControls(){
		nameLastNameField.setText("");
		LocalDate date = LocalDate.now();
		dateRegister.setValue(date);
		methodComboBox.setValue(mainApp.getMethodData().get(0));
		spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, Integer.parseInt(INITIAL_VALUE)));
		totalLabel.setText("");
		detailPaymentView.getItems().clear();
		FocusedUtil.setFocusOnTextField(nameLastNameField);

	}

	private void savePaymentDetail(){
		try {
			if(isInputValid()){
				for (PaymentDetail paymentDetail : mainApp.getPaymentDetail()) {
					if(paymentDetail != null){
						new PaymentDetail().savePaymentDetail(MySqlConnection.getConnection(), paymentDetail);
					}
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void savePaymentHeader(PaymentHeader paymentHeader){
		try {

			if(isInputValid()){

				paymentHeader.setCorrelative(Integer.parseInt(correlativeLabel.getText()));
				paymentHeader.setName(nameLastNameField.getText());
				paymentHeader.setDateRegister(Date.valueOf(dateRegister.getValue()));
				paymentHeader.setTotal(Double.parseDouble(totalLabel.getText()));

				AlertUtil.showAlertMessage(paymentHeader.savePaymentHeader(MySqlConnection.getConnection()), 1);

				//Guarda el detalle del pago
				savePaymentDetail();

				//Limpia los controles
				handleCleanControls();

				//Continua el correlativo de la boleta
				getNewSerial();

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void getNewSerial() {
		try {
			correlativeLabel.setText(""+PaymentHeader.getSerialPayment(MySqlConnection.getConnection()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private void calculateTotal(){
		double suma = 0;
		for (PaymentDetail paymentDetail : mainApp.getPaymentDetail()) {
			if(paymentDetail != null){
				suma += paymentDetail.getAmount();
			}
		}
		totalLabel.setText(""+ suma);
	}


	public boolean isInputValid(){
		String errorMessage = "";

		if(nameLastNameField.getText() == null || nameLastNameField.getText().length() == 0)
			errorMessage += "Nombres y apellidos inválidos.";

		if(dateRegister.getValue() == null)
			errorMessage += "\nSeleccione la fecha de Registro, por favor.";

		if(errorMessage.length() == 0)
			return true;
		else {
			AlertUtil.showMessageValidateInput(errorMessage);
			return false;
	}

}

}

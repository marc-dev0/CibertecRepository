package cib.universidad.model;

/* Java Bean
* Clase: PaymentDetail  */
import javafx.beans.property.IntegerProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cib.universidad.connection.MySqlConnection;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleDoubleProperty;

public class PaymentDetail{
	private IntegerProperty item;
	private IntegerProperty correlative;
	private IntegerProperty quantity;
	private DoubleProperty amount;
	private IntegerProperty idMethod;
	private StringProperty nameMethod;
	private static int secuencial = 1;

	public PaymentDetail(Integer item, Integer correlative, Integer quantity, Double amount, Integer idMethod){
		this.item = new SimpleIntegerProperty(item);
		this.correlative = new SimpleIntegerProperty(correlative);
		this.quantity = new SimpleIntegerProperty(quantity);
		this.amount = new SimpleDoubleProperty(amount);
		this.idMethod = new SimpleIntegerProperty(idMethod);
	}

	public PaymentDetail(){
		this(secuencial, 0, 0, 0.0, 0);
		secuencial++;
	}

	public Integer getItem(){
		return item.get();
	}

	public void setItem(Integer item){
		this.item.set(item);
	}

	public Integer getCorrelative(){
		return correlative.get();
	}

	public void setCorrelative(Integer correlative){
		this.correlative.set(correlative);
	}

	public Integer getQuantity(){
		return quantity.get();
	}

	public void setQuantity(Integer quantity){
		this.quantity.set(quantity);
	}

	public Double getAmount(){
		return amount.get();
	}

	public void setAmount(Double amount){
		this.amount.set(amount);
	}

	public Integer getIdMethod(){
		return idMethod.get();
	}

	public void setIdMethod(Integer idMethod){
		this.idMethod.set(idMethod);
	}

	public StringProperty getNameMethodProperty(){
		try {
			nameMethod = Method.getNameMethodxID(MySqlConnection.getConnection(), this.idMethod.get());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return nameMethod;
	}

	public IntegerProperty itemProperty(){
		return item;
	}

	public IntegerProperty correlativeProperty(){
		return correlative;
	}

	public IntegerProperty quantityProperty(){
		return quantity;
	}

	public DoubleProperty amountProperty(){
		return amount;
	}

	public IntegerProperty idMethodProperty(){
		return idMethod;
	}


	public static void getListDetailPayment(Connection connection, ObservableList<PaymentDetail> list){
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select  correlativo, cantidad, importe, idProcedimiento from detalle_pago");
			while(rs.next()){
				PaymentDetail paymentDetail = new PaymentDetail();
				paymentDetail.setCorrelative(rs.getInt("correlativo"));
				paymentDetail.setQuantity(rs.getInt("cantidad"));
				paymentDetail.setAmount(rs.getDouble("importe"));
				paymentDetail.setIdMethod(rs.getInt("idProcedimiento"));
				list.add(paymentDetail);
			}
		} catch (Exception e) {
			System.out.println("Error en PaymentDetai de Model" + e.getMessage());
		}
	}

//	public static String getSerialPayment(Connection connection){
//		String correlative = "";
//		try {
//			Statement statement = connection.createStatement();
//			ResultSet rs = statement.executeQuery("select reverse(concat(max(correlativo)+1, '000000')) 'correlativo' from cabecera_pago");
//			while(rs.next()){
//				correlative =rs.getString("correlativo");
//			}
//			System.out.println(correlative);
//		} catch (Exception e) {
//			System.out.println("Error en PaymentDetail de Model" + e.getMessage());
//		}
//
//		return correlative;
//	}

	public int savePaymentDetail(Connection connection, PaymentDetail paymentDetail){
		int estate =0;
		try {
			PreparedStatement pstm = connection.prepareStatement("insert detalle_pago("
					+ "idProcedimiento, cantidad, importe, correlativo) VALUES("
					+ "?,?,?, ?);");

			pstm.setInt(1, paymentDetail.getIdMethod());
			pstm.setInt(2, paymentDetail.getQuantity());
			pstm.setDouble(3, paymentDetail.getAmount());
			pstm.setInt(4, paymentDetail.getCorrelative());

			estate = pstm.executeUpdate();

		} catch (Exception e) {
			System.out.println("Error en PaymentDetail..." + e.getMessage());
		}
		return estate;
	}

}
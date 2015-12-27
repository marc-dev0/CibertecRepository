package cib.universidad.model;

/* Java Bean
* Clase: PaymentHeader  */
import javafx.beans.property.IntegerProperty;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class PaymentHeader{
	private IntegerProperty correlative;
	private StringProperty name;
	private Date dateRegister;
	private DoubleProperty total;
	private IntegerProperty idUser;

	public PaymentHeader(Integer correlative, String name, Date dateRegister, Double total, Integer idUser){
		this.correlative = new SimpleIntegerProperty(correlative);
		this.name = new SimpleStringProperty(name);
		this.dateRegister = dateRegister;
		this.total = new SimpleDoubleProperty(total);
		this.idUser = new SimpleIntegerProperty(idUser);
	}

	public PaymentHeader(){
		this(0, null, null, 0.0, 0);
	}

	public Integer getCorrelative(){
		return correlative.get();
	}

	public void setCorrelative(Integer correlative){
		this.correlative.set(correlative);
	}

	public String getName(){
		return name.get();
	}

	public void setName(String name){
		this.name.set(name);
	}

	public Date getDateRegister(){
		return dateRegister;
	}

	public void setDateRegister(Date dateRegister){
		this.dateRegister = dateRegister;
	}

	public Double getTotal(){
		return total.get();
	}

	public void setTotal(Double total){
		this.total.set(total);
	}

	public Integer getIdUser(){
		return idUser.get();
	}

	public void setIdUser(Integer idUser){
		this.idUser.set(idUser);
	}

	public IntegerProperty correlativeProperty(){
		return correlative;
	}

	public StringProperty nameProperty(){
		return name;
	}

	public DoubleProperty totalProperty(){
		return total;
	}

	public IntegerProperty idUserProperty(){
		return idUser;
	}

	public int savePaymentHeader(Connection connection){
		int estate =0;
		try {
			PreparedStatement pstm = connection.prepareStatement("insert cabecera_pago("
					+ "correlativo, fechaRegistro, total, idUsuario) VALUES("
					+ "?, ?, ?, ?)");

			pstm.setInt(1, this.correlative.get());
			pstm.setDate(2, dateRegister);
			pstm.setDouble(3, this.total.get());
			pstm.setInt(4, this.idUser.get());

			estate = pstm.executeUpdate();

		} catch (Exception e) {
			System.out.println("Error en PaymentHeader..." + e.getMessage());
		}
		return estate;
	}

	public static String getSerialPayment(Connection connection){
		String correlative = "";
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select concat('000000', max(correlativo)+1) 'correlativo' from cabecera_pago;");
			while(rs.next()){
				correlative =rs.getString("correlativo");
			}
			System.out.println(correlative);
		} catch (Exception e) {
			System.out.println("Error en PaymentDetail de Model" + e.getMessage());
		}

		return correlative;
	}
}
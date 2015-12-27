
package cib.universidad.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cib.universidad.connection.MySqlConnection;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Method{
	private IntegerProperty idMethod;
	private StringProperty description;
	private StringProperty descriptionMethodType;
	private DoubleProperty priceMethod;
	private IntegerProperty idMethodType;
	private BooleanProperty estate;

	private int estateTrx;

	public Method(Integer idMethod, String description, Integer idMethodType, Double priceMethod, Boolean estate){
		this.idMethod = new SimpleIntegerProperty(idMethod);
		this.description = new SimpleStringProperty(description);
		this.priceMethod = new SimpleDoubleProperty(priceMethod);
		this.idMethodType = new SimpleIntegerProperty(idMethodType);
		this.estate = new SimpleBooleanProperty(estate);
	}

	public Method(){
		this(0, null, 0, 0.0, true);
	}

	public Integer getIdMethod(){
		return idMethod.get();
	}

	public void setIdMethod(Integer idMethod){
		this.idMethod = new SimpleIntegerProperty(idMethod);
	}

	public String getDescription(){
		return description.get();
	}

	public void setDescription(String description){
		this.description = new SimpleStringProperty(description);
	}

	public Integer getIdMethodType(){
		return idMethodType.get();
	}

	public void setIdMethodType(Integer idMethodType){
		this.idMethodType = new SimpleIntegerProperty(idMethodType);
	}

	public Double getPriceMethod(){
		return this.priceMethod.get();
	}

	public void setPriceMethod(Double priceMethod){
		this.priceMethod.set(priceMethod);
	}

	public Boolean getEstate(){
		return estate.get();
	}

	public void setEstate(Boolean estate){
		this.estate = new SimpleBooleanProperty(estate);
	}

	public IntegerProperty idMethodProperty(){
		return idMethod;
	}

	public StringProperty descriptionProperty(){
		return description;
	}

	public IntegerProperty idMethodTypeProperty(){
		return idMethodType;
	}

	public static StringProperty getNameMethodxID(Connection connection, int idMethod){
		String aux = "";
		StringProperty nameMethod = new SimpleStringProperty(aux);

		try {
			PreparedStatement pstm = connection.prepareStatement("select descripcion from procedimiento where idProcedimiento = ?");
			pstm.setInt(1, idMethod);

			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				aux = rs.getString("descripcion");
				nameMethod.set(aux);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return nameMethod;
	}

	public StringProperty descriptionMethodTypeProperty(){
		try {
			descriptionMethodType = MethodType.getMethodTypexId(MySqlConnection.connect(), this.idMethodType.get());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return descriptionMethodType;
	}

//	public StringProperty descriptionMethodTypeProperty(int codigo){
//		try {
//			descriptionMethodType = MethodType.getMethodTypexId(MySqlConnection.connect(), codigo);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return descriptionMethodType;
//	}

	public static void getListMethod(Connection connection, ObservableList<Method> list){
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT idProcedimiento, descripcion, estado, precio, idTipo_procedimiento"
												+ " FROM procedimiento");
			while(rs.next()){
				Method method = new Method();
				method.setIdMethod(rs.getInt("idProcedimiento"));
				method.setDescription(rs.getString("descripcion"));
				method.setEstate(rs.getBoolean("estado"));
				method.setPriceMethod(rs.getDouble("precio"));
				method.setIdMethodType(rs.getInt("idTipo_procedimiento"));
				list.add(method);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int saveMethod(Connection connection){

		try {
			PreparedStatement pstm = connection.prepareStatement("INSERT procedimiento (descripcion, estado, idTipo_procedimiento) values (?,?,?)");

			pstm.setString(1, this.description.get());
			pstm.setBoolean(2, this.estate.get());
			pstm.setInt(3, this.idMethodType.get());

			estateTrx = pstm.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return estateTrx;
	}

	public int updateMethod(Connection connection){

		try {
			PreparedStatement pstm = connection.prepareStatement("update procedimiento"
					+ " set descripcion = ?, estado = ?, idTipo_procedimiento = ? where idProcedimiento = ?");

			pstm.setString(1, this.description.get());
			pstm.setBoolean(2, this.estate.get());
			pstm.setInt(3, this.idMethodTypeProperty().get());
			pstm.setInt(4, this.idMethodProperty().get());

			estateTrx = pstm.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return estateTrx;
	}

	public int deleteMethod(Connection connection){

		try {
			PreparedStatement pstm = connection.prepareStatement("delete from procedimiento "
					+ "where idProcedimiento = ?");

			pstm.setInt(1, this.idMethod.get());

			estateTrx = pstm.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return estateTrx;
	}

	@Override
	public String toString(){
		return this.description.get();
	}

}
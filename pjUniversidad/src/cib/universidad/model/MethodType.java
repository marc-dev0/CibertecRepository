package cib.universidad.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cib.universidad.util.AlertUtil;
/* Java Bean
* Clase: MethodType  */
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MethodType{
	
	private IntegerProperty idMethodType;
	private StringProperty description;
	
	private int estateTrx;

	public MethodType(Integer idMethodType, String description){
		this.idMethodType = new SimpleIntegerProperty(idMethodType);
		this.description = new SimpleStringProperty(description);
	}

	public MethodType(){
		this(0, null);
	}
	public Integer getIdMethodType(){
		return idMethodType.get();
	}

	public void setIdMethodType(Integer idMethodType){
		this.idMethodType.set(idMethodType);
	}

	public String getDescription(){
		return description.get();
	}

	public void setDescription(String description){
		this.description.set(description);
	}

	public IntegerProperty idMethodTypeProperty(){
		return idMethodType;
	}

	public StringProperty descriptionProperty(){
		return description;
	}

	public static StringProperty getMethodTypexId(Connection connection, int idMethodType){
		String aux = "";
		StringProperty methodType = new SimpleStringProperty(aux);

		try {
			PreparedStatement pstm = connection.prepareStatement("select descripcion from tipo_procedimiento where idTipo_Procedimiento = ?");
			pstm.setInt(1, idMethodType);

			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				aux = rs.getString("descripcion");
				methodType.set(aux);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return methodType;
	}

	public static void getListMethodType(Connection connection, ObservableList<MethodType> list){
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT idTipo_procedimiento, descripcion FROM tipo_procedimiento");
			while(rs.next()){
				MethodType methodType = new MethodType();
				methodType.setIdMethodType(rs.getInt("idTipo_procedimiento"));
				methodType.setDescription(rs.getString("descripcion"));
				list.add(methodType);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int saveMethodType(Connection connection){

		try {
			PreparedStatement pstm = connection.prepareStatement("INSERT tipo_procedimiento (descripcion) values (?)");

			pstm.setString(1, this.description.get());

			estateTrx = pstm.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return estateTrx;
	}

	public int updateMethodType(Connection connection){

		try {
			PreparedStatement pstm = connection.prepareStatement("update tipo_procedimiento"
					+ " set descripcion = ? where idtipo_procedimiento = ?");

			pstm.setString(1, this.description.get());
			pstm.setInt(2, this.idMethodType.get());

			estateTrx = pstm.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return estateTrx;
	}

	public int deleteMethodType(Connection connection){

		try {
			PreparedStatement pstm = connection.prepareStatement("delete from tipo_procedimiento "
					+ "where idtipo_procedimiento = ?");

			pstm.setInt(1, this.idMethodType.get());

			estateTrx = pstm.executeUpdate();

		} catch (SQLException e){
			AlertUtil.showAlertMessage(estateTrx, 4, e.getMessage());

		}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		}

		return estateTrx;
	}

	@Override
	public String toString(){
		return description.get();
	}
}
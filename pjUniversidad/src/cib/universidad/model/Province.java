package cib.universidad.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cib.universidad.connection.MySqlConnection;
/* Java Bean
* Clase: Province  */
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Province{
	private IntegerProperty idProvince;
	private StringProperty description;
	private IntegerProperty idDepartament;

	public Province(Integer idProvince, String description, Integer idDepartament){
		this.idProvince = new SimpleIntegerProperty(idProvince);
		this.description = new SimpleStringProperty(description);
		this.idDepartament = new SimpleIntegerProperty(idDepartament);
	}

	public Province(){
		this(0, null, 0);
	}

	public Integer getIdProvince(){
		return idProvince.get();
	}

	public void setIdProvince(Integer idProvince){
		this.idProvince.set(idProvince);
	}

	public String getDescription(){
		return description.get();
	}

	public void setDescription(String description){
		this.description.set(description);
	}

	public Integer getIdDepartament(){
		return idDepartament.get();
	}

	public void setIdDepartament(Integer idDepartament){
		this.idDepartament.set(idDepartament);
	}

	public IntegerProperty idProvinceProperty(){
		return idProvince;
	}

	public StringProperty descriptionProperty(){
		return description;
	}

	public IntegerProperty idDepartamentProperty(){
		return idDepartament;
	}

	public static void getListProvince(Connection connection, ObservableList<Province> list, int idDepartament){
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			pstm = connection.prepareStatement("SELECT idProvincia, descripcion FROM Provincia where idDepartamento = ? "
					+ "order by descripcion");

			pstm.setInt(1, idDepartament);

			rs = pstm.executeQuery();

			while(rs.next()){
				Province province = new Province();
				province.setIdProvince(rs.getInt("idProvincia"));
				province.setDescription(rs.getString("descripcion"));
				list.add(province);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(rs!= null)
					rs.close();
				if(pstm != null)
					pstm.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
	}

	@Override
	public String toString(){
		return this.description.get();
	}

}
package cib.universidad.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/* Java Bean
* Clase: District  */
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class District{
	private IntegerProperty idDistrict;
	private StringProperty descripcion;
	private IntegerProperty idProvince;

	public District(Integer idDistrict, String descripcion, Integer idProvince){
		this.idDistrict = new SimpleIntegerProperty(idDistrict);
		this.descripcion = new SimpleStringProperty(descripcion);
		this.idProvince = new SimpleIntegerProperty(idProvince);
	}

	public District(){
		this(0, null, 0);
	}

	public Integer getIdDistrict(){
		return idDistrict.get();
	}

	public void setIdDistrict(Integer idDistrict){
		this.idDistrict.set(idDistrict);
	}

	public String getDescripcion(){
		return descripcion.get();
	}

	public void setDescripcion(String descripcion){
		this.descripcion.set(descripcion);
	}

	public Integer getIdProvince(){
		return idProvince.get();
	}

	public void setIdProvince(Integer idProvince){
		this.idProvince.set(idProvince);
	}

	public IntegerProperty idDistrictProperty(){
		return idDistrict;
	}

	public StringProperty descripcionProperty(){
		return descripcion;
	}

	public IntegerProperty idProvinceProperty(){
		return idProvince;
	}

	public static void getListDistrict(Connection connection, ObservableList<District> list, int idProvince){

		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {

			pstm = connection.prepareStatement("select idDistrito, descripcion from distrito where idProvincia = ?");

			pstm.setInt(1, idProvince);

			rs = pstm.executeQuery();

			while(rs.next()){
				District district = new District();
				district.setIdDistrict(rs.getInt("idDistrito"));
				district.setDescripcion(rs.getString("descripcion"));
				list.add(district);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public String toString(){
		return this.descripcion.get();
	}
}
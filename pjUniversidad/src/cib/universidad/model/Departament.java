package cib.universidad.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cib.universidad.util.AlertUtil;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Departament{
	private IntegerProperty idDepartamento;
	private StringProperty description;
	private int estateTrx;

	public Departament(Integer idDepartamento, String descripcion){
		this.idDepartamento = new SimpleIntegerProperty(idDepartamento);
		this.description = new SimpleStringProperty(descripcion);
	}

	public Departament(){
		this(0, null);
	}

	public Integer getIdDepartamento(){
		return idDepartamento.get();
	}

	public void setIdDepartamento(Integer idDepartamento){
		this.idDepartamento.set(idDepartamento);
	}

	public String getDescripcion(){
		return description.get();
	}

	public void setDescripcion(String descripcion){
		this.description.set(descripcion);;
	}

	public IntegerProperty idDepartamentoProperty(){
		return idDepartamento;
	}

	public StringProperty descripcionProperty(){
		return description;
	}

	public static void getListDepartament(Connection connection, ObservableList<Departament> list){
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery("SELECT idDepartamento, descripcion FROM departamento order by descripcion");
			while(rs.next()){
				Departament departament = new Departament();
				departament.setIdDepartamento(rs.getInt("idDepartamento"));
				departament.setDescripcion(rs.getString("descripcion"));
				list.add(departament);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(rs!= null)
					rs.close();
				if(statement != null)
					statement.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
	}

	public int saveMethod(Connection connection){

		try {
			PreparedStatement pstm = connection.prepareStatement("INSERT departamento"
					+ " (descripcion) values (?)");

			pstm.setString(1, this.description.get());

			estateTrx = pstm.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return estateTrx;
	}

	public int updateMethod(Connection connection){

		try {
			PreparedStatement pstm = connection.prepareStatement("update departamento"
					+ " set descripcion = ? where idDepartamento = ?");

			pstm.setString(1, this.description.get());
			pstm.setInt(2, this.getIdDepartamento());

			estateTrx = pstm.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return estateTrx;
	}

	public int deleteMethod(Connection connection){

		try {
			PreparedStatement pstm = connection.prepareStatement("delete from departamento "
					+ "where idDepartamento = ?");

			pstm.setInt(1, this.idDepartamento.get());

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
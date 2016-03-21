package cib.universidad.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/* Java Bean
* Clase: Requirement  */
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Requirement{
	private IntegerProperty idRequirement;
	private StringProperty description;
	private IntegerProperty idMethod;
	
	private int estateTrx;
	
	public Requirement(){
		this(0, null,0);
	}
	
	public Requirement(Integer idRequirement, String description, Integer idMethod){
		this.idRequirement = new SimpleIntegerProperty(idRequirement);
		this.description = new SimpleStringProperty(description);
		this.idMethod = new SimpleIntegerProperty(idMethod);
	}

	public Integer getIdRequirement(){
		return idRequirement.get();
	}

	public void setIdRequirement(Integer idRequirement){
		this.idRequirement.set(idRequirement);
	}

	public String getDescription(){
		return description.get();
	}

	public void setDescription(String description){
		this.description.set(description);
	}

	public Integer getIdMethod(){
		return idMethod.get();
	}
	
	public void setIdMethod(Integer idMethod){
		this.idMethod.set(idMethod);
	}
	
	public IntegerProperty idRequirementProperty(){
		return idRequirement;
	}

	public StringProperty descriptionProperty(){
		return description;
	}
	
	public IntegerProperty idMethod(){
		return idMethod;
	}

	public static void listarRequirement(Connection connection, ObservableList<Requirement> lista){
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT idRequisito, requisito, idProcedimiento FROM requisito");
			while(rs.next()){
				lista.add(new Requirement(rs.getInt("idRequisito"), rs.getString("requisito"), rs.getInt("idProcedimiento")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void getListRequirementByIdMethod(Connection connection, ObservableList<Requirement> list, int idMethod){
		try {
			PreparedStatement pstm = connection.prepareStatement("select idRequisito, requisito from requisito " + 
																"where idProcedimiento = ?");
			
			pstm.setInt(1, idMethod);
			
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next()){
				Requirement requirement = new Requirement();
				requirement.setIdRequirement(rs.getInt("idRequisito"));
				requirement.setDescription(rs.getString("requisito"));
				list.add(requirement);
			}
			
		} catch (SQLException e){
			e.printStackTrace();
		}
		
	}

	public int guardarRegistro(Connection connection){

		try {

			PreparedStatement pstm = connection.prepareStatement("INSERT requisito (requisito,idProcedimiento) values (?,?)");

			//que datos se guardaran en los parametros de la consulta
			pstm.setString(1, this.description.get());
			pstm.setInt(2, this.idMethod.get());

			estateTrx = pstm.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return estateTrx;
	}


	public int actualizarRequirement(Connection connection){

		try {
			PreparedStatement pstm = connection.prepareStatement("update requisito set requisito = ?, idProcedimiento = ? "
					+ "where idRequisito = ?");

			pstm.setString(1, this.description.get());
			pstm.setInt(2, this.idMethod.get());
			pstm.setInt(3, this.idRequirement.get());

			estateTrx = pstm.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return estateTrx;
	}

	public int eliminarRequirement(Connection connection){

		try {
			PreparedStatement pstm = connection.prepareStatement("delete from requisito where idrequisito = ?");

			pstm.setInt(1, this.idRequirement.get());

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
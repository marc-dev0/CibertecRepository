package cib.universidad.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/* Java Bean
* Clase: Parent  */
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Parent{
	private IntegerProperty idParent;
	private StringProperty laborSituation;
	private IntegerProperty idPerson;

	public Parent(Integer idParent, String laborSituation, Integer idPerson){
		this.idParent = new SimpleIntegerProperty(idParent);
		this.laborSituation = new SimpleStringProperty(laborSituation);
		this.idPerson = new SimpleIntegerProperty(idPerson);
	}

	public Parent(){
		this(0, null, 0);
	}

	public Integer getIdParent(){
		return idParent.get();
	}

	public void setIdParent(Integer idParent){
		this.idParent.set(idParent);
	}

	public String getLaborSituation(){
		return laborSituation.get();
	}

	public void setLaborSituation(String laborSituation){
		this.laborSituation.set(laborSituation);
	}

	public Integer getIdPerson(){
		return idPerson.get();
	}

	public void setIdPerson(Integer idPerson){
		this.idPerson.set(idPerson);
	}

	public IntegerProperty idParentProperty(){
		return idParent;
	}

	public StringProperty laborSituationProperty(){
		return laborSituation;
	}

	public IntegerProperty idPersonProperty(){
		return idPerson;
	}

	public int savePostulant(Connection connection){
		int estate =0;
		try {
			PreparedStatement pstm = connection.prepareStatement("INSERT apoderad "
					+ "(situacionLaboral, idPersona)"
					+ " VALUES(?,?)");

			pstm.setString(1, this.laborSituation.get());
			pstm.setInt(2, this.idPerson.get());

			estate = pstm.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return estate;
	}

	public int getParentID(Connection connection){

		int parentID = 0;
		try {
			PreparedStatement pstm = connection.prepareStatement("select max(idApoderado) 'idApoderado' from apoderad");

			ResultSet rs;
			rs = pstm.executeQuery();

			while(rs.next()){
				parentID = rs.getInt("idApoderado");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return parentID;
	}
}
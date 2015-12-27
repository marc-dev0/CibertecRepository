package cib.universidad.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/* Java Bean
* Clase: College  */
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class College{
	private IntegerProperty idCollege;
	private StringProperty nameCollege;
	private StringProperty adressCollege;
	private IntegerProperty idDistrict;
	private Date endDate;
	private IntegerProperty idPostulant;

	public College(Integer idCollege, String nameCollege, String adressCollege, Integer idDistrict, Date endDate, Integer idPostulant){
		this.idCollege = new SimpleIntegerProperty(idCollege);
		this.nameCollege = new SimpleStringProperty(nameCollege);
		this.adressCollege = new SimpleStringProperty(adressCollege);
		this.idDistrict = new SimpleIntegerProperty(idDistrict);
		this.endDate = endDate;
		this.idPostulant = new SimpleIntegerProperty(idPostulant);
	}

	public College(){
		this(0, null, null, 0, null, 0);
	}

	public Integer getIdCollege(){
		return idCollege.get();
	}

	public void setIdCollege(Integer idCollege){
		this.idCollege.set(idCollege);
	}

	public String getNameCollege(){
		return nameCollege.get();
	}

	public void setNameCollege(String nameCollege){
		this.nameCollege.set(nameCollege);
	}

	public String getAdressCollege(){
		return adressCollege.get();
	}

	public void setAdressCollege(String adressCollege){
		this.adressCollege.set(adressCollege);
	}

	public Integer getIdDistrict(){
		return idDistrict.get();
	}

	public void setIdDistrict(Integer idDistrict){
		this.idDistrict.set(idDistrict);
	}

	public Date getEndDate(){
		return endDate;
	}

	public void setEndDate(Date endDate){
		this.endDate = endDate;
	}

	public Integer getIdPostulant(){
		return idPostulant.get();
	}

	public void setIdPostulant(Integer idPostulant){
		this.idPostulant.set(idPostulant);
	}

	public IntegerProperty idCollegeProperty(){
		return idCollege;
	}

	public StringProperty nameCollegeProperty(){
		return nameCollege;
	}

	public StringProperty adressCollegeProperty(){
		return adressCollege;
	}

	public IntegerProperty idDistrictProperty(){
		return idDistrict;
	}

	public IntegerProperty idPerson(){
		return idPostulant;
	}



	public int saveCollege(Connection connection){
		int estate =0;
		try {
			PreparedStatement pstm = connection.prepareStatement("INSERT colegio_procedencia "
					+ "(nombre, direccion ,fechaFin , idDistrito, idPostulante)"
					+ " VALUES(?,?,?,?,?)");

			pstm.setString(1, this.nameCollege.get());
			pstm.setString(2, this.adressCollege.get());
			pstm.setDate(3, this.endDate);
			pstm.setInt(4, this.idDistrict.get());
			pstm.setInt(5, this.idPostulant.get());

			estate = pstm.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return estate;
	}

	public static int getCollegeID(Connection connection) {
		int collegeID = 0;
		try {
			PreparedStatement pstm = connection.prepareStatement("select max(idColegio_procedencia) 'idColegio' FROM colegio_procedencia");

			ResultSet rs;
			rs = pstm.executeQuery();

			while(rs.next()){
				collegeID = rs.getInt("idColegio");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return collegeID;
	}

}
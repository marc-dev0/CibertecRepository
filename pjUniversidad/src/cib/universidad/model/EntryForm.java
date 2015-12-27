package cib.universidad.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

/* Java Bean
* Clase: EntryForm  */
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EntryForm{
	private IntegerProperty idFichaInscripcion;
	private Date fechaRegistro;
	private StringProperty estate;
	private StringProperty observacion;
	private IntegerProperty idPostulante;
	private IntegerProperty idCarrera;
	private IntegerProperty idColegio;
	private IntegerProperty idUsuario;
	private int estateTrx;

	public EntryForm(Integer idFichaInscripcion, Date fechaRegistro, String estate, String observacion, Integer idPostulante, Integer idCarrera, Integer idColegio, Integer idUsuario){
		this.idFichaInscripcion = new SimpleIntegerProperty(idFichaInscripcion);
		this.fechaRegistro = fechaRegistro;
		this.estate = new SimpleStringProperty(estate);
		this.observacion = new SimpleStringProperty(observacion);
		this.idPostulante = new SimpleIntegerProperty(idPostulante);
		this.idCarrera = new SimpleIntegerProperty(idCarrera);
		this.idColegio = new SimpleIntegerProperty(idColegio);
		this.idUsuario = new SimpleIntegerProperty(idUsuario);
	}

	public EntryForm(){
		this(0, null, null, null, 0, 0, 0, 0);
	}

	public Integer getIdFichaInscripcion(){
		return idFichaInscripcion.get();
	}

	public void setIdFichaInscripcion(Integer idFichaInscripcion){
		this.idFichaInscripcion.set(idFichaInscripcion);
	}

	public Date getFechaRegistro(){
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro){
		this.fechaRegistro = fechaRegistro;
	}

	public String getEstate(){
		return estate.get();
	}

	public void setEstate(String estate){
		this.estate.set(estate);
	}

	public String getObservacion(){
		return observacion.get();
	}

	public void setObservacion(String observacion){
		this.observacion.set(observacion);
	}

	public Integer getIdPostulante(){
		return idPostulante.get();
	}

	public void setIdPostulante(Integer idPostulante){
		this.idPostulante.set(idPostulante);
	}

	public Integer getIdCarrera(){
		return idCarrera.get();
	}

	public void setIdCarrera(Integer idCarrera){
		this.idCarrera.set(idCarrera);
	}

	public Integer getIdColegio(){
		return idColegio.get();
	}

	public void setIdColegio(Integer idColegio){
		this.idColegio.set(idColegio);
	}

	public Integer getIdUsuario(){
		return idUsuario.get();
	}

	public void setIdUsuario(Integer idUsuario){
		this.idUsuario.set(idUsuario);
	}

	public IntegerProperty idFichaInscripcionProperty(){
		return idFichaInscripcion;
	}

	public StringProperty estateProperty(){
		return estate;
	}

	public StringProperty observacionProperty(){
		return observacion;
	}

	public IntegerProperty idPostulanteProperty(){
		return idPostulante;
	}

	public IntegerProperty idCarreraProperty(){
		return idCarrera;
	}

	public IntegerProperty idColegioProperty(){
		return idColegio;
	}

	public IntegerProperty idUsuarioProperty(){
		return idUsuario;
	}

	public int saveEntryForm(Connection connection){

		try {
			PreparedStatement pstm = connection.prepareStatement("insert ficha_inscripcion"
					+ "(fechaRegistro, estadoAutorizacion, obs, idPostulante, idCarrera, idColegio, idUsuario)"+
						"VALUES(?, ?, ?, ?,?,?,?)");

			pstm.setDate(1, this.fechaRegistro);
			pstm.setString(2, this.estate.get());
			pstm.setString(3, this.observacion.get());
			pstm.setInt(4, this.idPostulante.get());
			pstm.setInt(5, this.idCarrera.get());
			pstm.setInt(6, this.idColegio.get());
			pstm.setInt(7, this.idUsuario.get());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return estateTrx;
	}


}
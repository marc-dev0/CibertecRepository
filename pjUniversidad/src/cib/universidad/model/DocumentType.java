package cib.universidad.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cib.universidad.util.AlertUtil;
/* Java Bean
* Clase: DocumentType  */
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class DocumentType{
	private IntegerProperty idDocumentType;
	private StringProperty description;

	public DocumentType(Integer idDocumentType, String description){
		this.idDocumentType = new SimpleIntegerProperty(idDocumentType);
		this.description = new SimpleStringProperty(description);
	}

	public DocumentType(){
		this(0, null);
	}

	public Integer getIdDocumentType(){
		return idDocumentType.get();
	}

	public void setIdDocumentType(Integer idDocumentType){
		this.idDocumentType.set(idDocumentType);
	}

	public String getDescription(){
		return description.get();
	}

	public void setDescription(String description){
		this.description.set(description);
	}

	public IntegerProperty idDocumentTypeProperty(){
		return idDocumentType;
	}

	public StringProperty descriptionProperty(){
		return description;
	}

	public static void getListDocumentType(Connection connection, ObservableList<DocumentType> list){
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery("select idTipoDocumento, descripcion from tipo_documento");
			while(rs.next()){
				DocumentType documentType = new DocumentType();
				documentType.setIdDocumentType(rs.getInt("idTipoDocumento"));
				documentType.setDescription(rs.getString("descripcion"));
				list.add(documentType);
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

	@Override
	public String toString(){
		return this.description.get();
	}

}
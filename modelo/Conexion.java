package servicios.rctd.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Conexion {
	Connection conn = null;
	Statement statem;
	String error="";

	/**
    *clase conexion
    */
public Conexion (){
	
}
/*
* abre conexion a base de datos
*/
public boolean abrirConexion(){
	boolean finished=false;
	String Error22="";
try{
	Class.forName ("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
	try{
		    //conn = DriverManager.getConnection("jdbc:sqlserver://localhost//SqlExpress22:1433;DatabaseName=Servicios", "sa", "a5z8y1");
  		    conn = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS22:1433;DatabaseName=Servicios","sa", "a5z8y1");
		}
	catch (Exception e){	
			conn=null;
			this.error=e.getMessage()+"-->"+e.getStackTrace()[0];
		}
	if(conn==null){
		//this.error="no se logro conectar";
	}
	else{
		finished=true;
	}
}catch (Exception e)
{
e.getMessage();
 this.error=e.getMessage()+"-->"+e.getStackTrace()[0];
}
return finished;
  }

/**
*cierra la conexion a la base de datos
*/
public void cerrarConexion(){
   try{
	this.conn.close();
   }
   catch(Exception e){
	   
   }
}
/**
*genera el update de alguna consulta en especifico
*/
public boolean Update(String query) throws SQLException{
 boolean finished=false;
 try{
	if(this.abrirConexion()){
    	this.statem=this.conn.createStatement();
	    if(this.statem.executeUpdate(query)>0){
		  finished=true;
       	}
    	else{
	    	this.error="No se pudo ejecutar el Update.";
	    }
	}
	
	
 }catch (Exception e){
	this.error=e.getMessage()+"-->"+e.getStackTrace()[0]; 
    	
 }
 finally
 {
	 this.cerrarConexion();
 }
  return finished;
 
}
/**
*regresa solo un valor de alguana consulta especifica.
*/
public String RegresaScalar(String query){
	 String Valor="-1";
	 try{
		 if(this.abrirConexion()){
    		this.statem=this.conn.createStatement();
	    	ResultSet rs=this.statem.executeQuery(query);
		    while(rs.next()){
		       Valor=rs.getObject(1).toString();
		    }
		 }
			   
	 }catch (Exception e){
		this.error=e.getMessage()+"-->"+e.getStackTrace()[0]; 
	    	
	 }
	 finally{
		 this.cerrarConexion();
		 } 
	 return Valor;
	}
/**
*carga una consulta a un Resulset.
*/
public ResultSet Query(String query)
{
	ResultSet rs=null;
	try
	{
	  rs=this.conn.createStatement().executeQuery(query);
	}
	catch (Exception e) {
		rs=null;
		this.error="NO SE PUDO CARGAR EL QUERY SOLICITADO O NO ESTA ABIERTA LA CONEXION";
	}
	return rs;
}

/**
* genera un insert de un query en especifico.
*/
public boolean Insert(String query){
	 boolean finished=false;
	 try{
		 
		 if(this.abrirConexion())
		 {
			 this.statem=this.conn.createStatement();
	    	
    		if(this.statem.executeUpdate(query)>0){
		    	finished=true;
	    	}
    		else{
	    		this.error="No se pudo ejecutar el Query.";
    		}
		 }
		
	 }catch (Exception e){
		this.error=e.getMessage()+"-->"+e.getStackTrace()[0];
		
	 }
	 finally{
		 
	     this.cerrarConexion();	 
	 }
	 return finished;
	 
	}

    public Connection getConn() {
	       return conn;
    }
    
    public void setConn(Connection conn) {
	      this.conn = conn;
    }

    public String getError() {
		return error;
	}
}

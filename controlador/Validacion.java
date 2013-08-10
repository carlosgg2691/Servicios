package servicios.rctd.controlador;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Validacion {
	Calendar FechaValidada=Calendar.getInstance();
	/**
    *genera la validacion dela fecha.
    */
	public Calendar getFechaValidada() {
		return FechaValidada;
	}
	/**
    *Recibe la fecha validada
    */
	public void setFechaValidada(Calendar fechaValidada) {
		FechaValidada = fechaValidada;
	}
	
	String error="";
	/**
    *metodo que valida la ip
    */
	public boolean ValidaIP(String valor,String nombre)	{
		boolean finished=false;
		try{
			InetAddress ia=InetAddress.getByName(valor);
			if(ia.toString().equals("/"+valor))
			{
   			   finished=true;
			}
			else
			{
				this.error=nombre+" es incorrecto";				
			}
		}catch (Exception e){
			this.error=nombre+" es incorrecto";
		}	
		return finished;	
		
	}
	/**
    *valida un valor string
    */
public boolean ValidaString(String valor, String nombre){
	
	boolean finished=false;
	if (valor.replace(" ", "").length()==0){
		this.error=nombre+" es incorrecto";
	}else{
		finished=true;
	}
	return finished;
}
     /**
     *valida un tipo de valor entero
     */
public boolean ValidaEntero(String valor,String nombre){
	
	boolean finished=false;
	try{
		Integer.parseInt(valor);
		finished=true;
	}catch (Exception e){
		this.error=nombre+" es incorrecto";
	}	
	return finished;	
}
/**
*valida un tipo de valor doble
*/
public boolean ValidaDouble(String valor,String nombre){
	
	boolean finished=false;
	try{
		Double.parseDouble(valor);
		finished=true;
	}catch (Exception e){
		this.error=nombre+" es incorrecto";
	}	
	return finished;	
}

/**
*valida la fecha
*/
public boolean ValidaFecha(String valor,String nombre){
	
	boolean finished=false;
	try{
		SimpleDateFormat ffecha=new SimpleDateFormat("dd/MM/yyyy");
		this.FechaValidada.setTime(ffecha.parse(valor));
		if(ffecha.format(this.FechaValidada.getTime()).equals(valor)){
		   finished=true;
		}
		else{
			this.error=nombre+" es incorrecto";			
		}
	}catch (Exception e){
		this.error=nombre+" es incorrecto";
	}	
	return finished;	
}
/**
*convierte valor de la fecha
*/
public Calendar ConvertFechaSql(String valor){
	try{
		this.error="";
		SimpleDateFormat ffecha=new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		this.FechaValidada.setTime(ffecha.parse(valor));
	}catch (Exception e){
		this.error=" es incorrecto la conversion";
	}	
	return this.FechaValidada;	
}


/**
*obtiene la lista de errores
*/
public String getError() {
	return error;
}
}

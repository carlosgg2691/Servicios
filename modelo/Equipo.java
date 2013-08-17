package servicios.rctd.modelo;

import java.net.InetAddress;
import java.util.HashMap;

import servicios.rctd.controlador.Validacion;

public class Equipo {
	int idEquipo;
	String nombreEquipo;
    String tipoEquipo;
    String usuario;
    Area areaEquipo;
    InetAddress IP=null;
    String error="";
    /**
    *Constructor de la clase equipo.
    */
    public Equipo(int idEquipo,	String nombreEquipo,String tipoEquipo,String usuario,int idArea,InetAddress IP,HashMap<Integer, Area> hashAreas){
    	this.idEquipo=idEquipo;
    	this.nombreEquipo=nombreEquipo;
    	this.tipoEquipo=tipoEquipo;
    	this.usuario=usuario;
    	this.areaEquipo= hashAreas.get(idArea);
    	this.IP= IP;
    	
    }
    /**
    *metodo que valida string para la clase y carga la clase
    */
    public boolean Valida(String idEquipo,	String nombreEquipo,String tipoEquipo,String usuario,String idArea,String IP,HashMap<Integer, Area> hashAreas) throws Throwable{
    	   boolean finished=false;
    	   this.error="";
           Validacion[] valida=new Validacion[6];
           for(int x=0;x<valida.length;x++){valida[x]=new Validacion();}
           
           if(valida[0].ValidaEntero(idEquipo, " El id del equipo ")&&valida[1].ValidaString(nombreEquipo, "El nombre del equipo")&&valida[2].ValidaString(tipoEquipo, "El tipo de Equipo")&&
              valida[3].ValidaString(usuario, "El usuario ")&&valida[4].ValidaEntero(idArea, "El numero de Area")&&valida[5].ValidaIP(IP, "El ip de la computadora")){
        	   
               this.idEquipo=Integer.parseInt(idEquipo);
               this.nombreEquipo=nombreEquipo;
               this.tipoEquipo=tipoEquipo;
               this.usuario=usuario;
               this.areaEquipo=hashAreas.get(Integer.parseInt(idArea));
               this.IP= InetAddress.getByName(IP);
                 finished=true;
           }
           else{
        	   for(int x=0;x<valida.length;x++)
        	   {
        		   this.error=this.error+valida[x].getError();
        	   }
           }
    	   return finished;
    	}
    /**
    *convierte la clase equipo a string
    */
    public String ToString()
    {
    	return this.idEquipo+this.nombreEquipo+this.tipoEquipo+this.usuario+this.areaEquipo.getArea()+this.IP.toString();
    }
    
    /**
    *constructor default
    */
    public Equipo(){
    	
    }
    
    
    
    /**
    *ubicacion de los get y set de las propiedades
    */
	public int getIdEquipo() {
		return idEquipo;
	}
		public void setIdEquipo(int idEquipo) {
		this.idEquipo = idEquipo;
	}
	
	public String getNombreEquipo() {
		return nombreEquipo;
	}
	
	public void setNombreEquipo(String nommbreEquipo) {
		this.nombreEquipo = nommbreEquipo;
	}
	
	public String getTipoEquipo() {
		return tipoEquipo;
	}
	
	public void setTipoEquipo(String tipoEquipo) {
		this.tipoEquipo = tipoEquipo;
	}
     
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Area getArea() {
		return areaEquipo;
	}

	public void setArea(Area areaEquipo) {
		this.areaEquipo= areaEquipo;
	}

	public InetAddress getIP(){
		return IP;
	}

	public void setIP(InetAddress iP) {
		IP = iP;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}


}

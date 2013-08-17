package servicios.rctd.modelo;

import servicios.rctd.controlador.Validacion;



public class Area {
	//objeto idarea
	int IdArea;
	//objeto de Area
	String Area;
	//objeto de error
	String error="";

	/**
    *constructor para crear una Area nueva
    */
public Area(){
	
}
/**
*constructor para crear una Area y sus propiedades 
*/

public Area(int IdArea,String Area){
	
	this.IdArea=IdArea;
	this.Area=Area;
}
/**
*metodo para validar
*/

public boolean Valida(String IdArea,String Area)
{
    boolean finished=false;
    Validacion[] v=new Validacion[2];
    v[0]=new Validacion();
    v[1]=new Validacion();
    
    if(v[0].ValidaEntero(IdArea,"El Identificador del area ")&&v[1].ValidaString(Area, "El Area "))
    {
    	this.IdArea=Integer.parseInt(IdArea);
    	this.Area=Area;
    	finished=true;
    }
    else
    {
        this.error=v[0].getError()+"\n"+v[1].getError();
    }
    return finished;    
}

/**
*convierte la clase a string
*/
public String toString()
{
   return this.IdArea+this.Area;	
}

/**
*regresa id de area
*/
public int getIdArea() {
	return IdArea;
}
/**
*asigna el id area
*/
public void setIdArea(int idArea) {
	this.IdArea = idArea;
}
/**
*regresa el nombre del area
*/
public String getArea() {
	return Area;
}
/**
*asignar un nombre al area
*/
public void setArea(String area) {
	this.Area = area;
}
/**
* obtiene los errores de los metodos
*/
public String getError() {
	return error;
}
}


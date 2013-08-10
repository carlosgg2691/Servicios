package servicios.rctd.controlador;

//interface validacion
public interface Ivalidacion {

	/**
     *Interface la cual valida un IP 
     */
	public boolean ValidaIP(String valor,String nombre);
	/**
   *Interface la cual valida un tipo de dato String
   */
	public boolean ValidaString(String valor, String nombre);
	/**
   *Inyerface la cual valida un tipo de dato Entero
   */
	public boolean ValidaEntero(String valor,String nombre);
	/**
   *Iinterface la cual valida un tipo de dato Doble
   */
	public boolean ValidaDouble(String valor,String nombre);
	/**
   *Interface la cual valida un tipo de dato calendar en formato dd/mm/aaaa.
   */
	public boolean ValidaFecha(String valor,String nombre);
}

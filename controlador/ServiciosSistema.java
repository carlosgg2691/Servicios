package servicios.rctd.controlador;

import java.net.InetAddress;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;

import servicios.rctd.modelo.Area;
import servicios.rctd.modelo.Conexion;
import servicios.rctd.modelo.Equipo;
import servicios.rctd.modelo.EstadoServicio;
import servicios.rctd.modelo.Mailer;
import servicios.rctd.modelo.Servicio;
import servicios.rctd.modelo.Tecnico;
import servicios.rctd.modelo.TipoServicio;
import servicios.rctd.modelo.Usuario;

public class ServiciosSistema {
	String error="";
	Conexion conn=new Conexion();	
	private HashMap<Integer, Tecnico> hashTecnicos=new HashMap<Integer, Tecnico>();
	private HashMap<Integer, Area> hashAreas=new HashMap<Integer, Area>();
	private HashMap<Integer, TipoServicio> hashTipoServicios=new HashMap<Integer, TipoServicio>();
	private HashMap<Integer, Equipo> hashEquipo=new HashMap<Integer, Equipo>();
	private Servicio servicio=new Servicio();
	private String[] Fields;
	private String[][] Rows;
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	/**
    *contine la lista de los titulos de los campos.
    */
	public String[] getFields() {
		return Fields;
		
	}
	/**
    *Contiene la lista de los titulos de la columnas.
    */
	public String[][] getRows() {
		return Rows;
	}
	/**
    *Metodo que regresa el servicio actual.
    */
	public Servicio getServicio() {
		return servicio;
	}
	/**
    *Metodo que recibe un servicio.
    */
	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}
	/**
    *Regresa el hashmap del tipo de servicios.
    */
	public HashMap<Integer, TipoServicio> getHashTipoServicios() {
		return hashTipoServicios;
	}
	/**
    *Hashmap que recibe un tipo de servicio.
    */
	public void setHashTipoServicios(HashMap<Integer, TipoServicio> hashTipoServicios) {
		this.hashTipoServicios = hashTipoServicios;
	}
	/**
    *Valida si el numero de servicio existe en la base de datos
    */
	public boolean ExisteServicio(Servicio s){
		boolean finished=false;
		try {
			int Canti=Integer.parseInt(this.conn.RegresaScalar("SELECT     COUNT(id_servicio) AS canti FROM         Servicios WHERE     (id_servicio = "+s.getIdServicio()+")"));
			   if(Canti>0){
				   finished=true;
			   }
		} catch (Exception e) {
			this.error=e.getMessage()+"-->"+e.getStackTrace()[0];
		}
		
		return finished;
	}
	
	public boolean ExisteTecnico(Tecnico t)
	{
		boolean finished=false;
		try {
			int Canti=Integer.parseInt(this.conn.RegresaScalar("SELECT COUNT(*) AS CANTIDAD FROM EQUIPOS WHERE ID_EQUIPO="+t.getNumeroEmpleado()));
			   if(Canti>0){
				   finished=true;
			   }
		} catch (Exception e) {
			this.error=e.getMessage()+"-->"+e.getStackTrace()[0];
		}
		return finished;
	}
	
	public boolean ExisteEquipo(Equipo e)
	{
		boolean finished=false;
		try{
			  int Canti=Integer.parseInt(this.conn.RegresaScalar("SELECT COUNT(ID_EQUIPO) AS CUENTA FROM EQUIPOS WHERE ID_EQUIPO="+e.getIdEquipo()));
			   if(Canti>0){
				   finished=true;
			   }			  
		}
		catch (Exception e1) {
			this.error=e1.getMessage()+"-->"+e1.getStackTrace()[0];
		}
		return finished;
	}
	/**
    *Carga el field y el row con la consulta solicitada a la base de datos.  
    */
	public boolean CargarQuery(String Query)
	{
		boolean finished=false;
		try {
			   if(this.conn.abrirConexion()){
				     ResultSet rs=this.conn.Query(Query);
				     ResultSetMetaData rsd= rs.getMetaData();
				     this.Fields=new String[rsd.getColumnCount()];
				     
				     for(int x=0;x<rsd.getColumnCount();x++){
				    	 this.Fields[x]=rsd.getColumnLabel(x+1);
				     }
				     
				     int filas=0;
				     
				     while(rs.next()){
				    	 filas++;
				     }
				     
				     rs=this.conn.Query(Query);
				     
				     this.Rows=new String[filas][rsd.getColumnCount()];
				     
				     filas=0;
				     while(rs.next()){
				    	 for(int x=0;x<rsd.getColumnCount();x++){
				    	          this.Rows[filas][x]=rs.getObject(x+1).toString();
				    	 }
				    	 filas++;
				     }
				     finished=true;
			   }
			   else{
				   this.error="No se pudo abrir la conexion";
			   }
		} catch (Exception e) {
		   this.error=e.getMessage()+"-->"+e.getStackTrace()[0]+"-->"+this.conn.getError();
		}
		finally{
			this.conn.cerrarConexion();
		}
		
		
		
		return finished;
	}
	
	/**
    *carga el servicio en base a un numero de servicio.
    */
	public boolean CargaServicio(int idServicio){
		boolean finished=false;
		try{
			this.servicio=new Servicio();
			    String q="select * from servicios where id_servicio="+idServicio;
			    this.conn.abrirConexion();
			    ResultSet rs=this.conn.Query(q);
			    while(rs.next())
			    {
			       this.servicio.setEstadoServicio(StrToEstadoServicio(rs.getObject(10).toString()));
			       if(this.servicio.getEstadoServicio().equals(EstadoServicio.Solicitado)||this.servicio.getEstadoServicio().equals(EstadoServicio.Iniciado)||this.servicio.getEstadoServicio().equals(EstadoServicio.Terminado)){
	                  this.servicio.setIdServicio(Integer.parseInt(rs.getObject(1).toString()));
	                  System.out.println(this.servicio.getIdServicio());
	                  this.servicio.setFechaSolicitud(new Validacion().ConvertFechaSql(rs.getObject(2).toString()));
	                  this.servicio.setEquipo(this.getHashEquipo().get(Integer.parseInt(rs.getObject(3).toString())));
	                  this.servicio.setProblema(rs.getObject(4).toString());
	                  finished=true;
			       } 
			       if(this.servicio.getEstadoServicio().equals(EstadoServicio.Iniciado)||this.servicio.getEstadoServicio().equals(EstadoServicio.Terminado)){
			    	   finished=false;
			    	   this.servicio.setFechaInicial(new Validacion().ConvertFechaSql(rs.getObject(5).toString()));
			    	   this.servicio.setTecnico(this.hashTecnicos.get(Integer.parseInt(rs.getObject(6).toString())));
			    	   this.servicio.setTipo(this.hashTipoServicios.get(Integer.parseInt(rs.getObject(7).toString())));
			    	   finished=true;
			       }
			       if(this.servicio.getEstadoServicio().equals(EstadoServicio.Terminado)){
			    	   finished=false;
			    	   this.servicio.setFechaFinal(new Validacion().ConvertFechaSql(rs.getObject(8).toString()));
			    	   this.servicio.setSolucion(rs.getObject(9).toString());
			    	   finished=true;
			       } 
			    }
		}
		catch (Exception e) {
			this.error=e.getMessage()+"-->"+e.getStackTrace()[0]+"-->"+this.conn.getError();
		}
		finally{
			this.conn.cerrarConexion();
		}
		return finished;
	}
	
	public EstadoServicio StrToEstadoServicio(String esStr)
	{
		EstadoServicio es=null;
		if(esStr.equals("S")){
			es=EstadoServicio.Solicitado;
		}
		if(esStr.equals("I")){
			es= EstadoServicio.Iniciado;
		}
		if(esStr.equals("T")){
			es=EstadoServicio.Terminado;
		}
      return es;		
	}
	/**
    *convierte los estados en un string
    */
	public String EstadoServicioToStr(EstadoServicio es)
	{
		String esStr="";
		   switch (es) {
			case Solicitado:
				  esStr="S";
				break;
			case Iniciado:
				  esStr="I";
				break;
			case Terminado:
				  esStr="T";
				break;

		default:
			break;
		}
		
		return esStr;
	}
	/**
    * metodo que relaciona la base de datos con los servicios
    */
	
	public boolean InsUpdServicios(Servicio s,Usuario usuario){
		boolean finished=false;
		try {
			  switch (s.getEstadoServicio()) {
				case Solicitado:
					  if(this.ExisteServicio(s)){
						  this.error="No puedes Agregar un Servicio ya existente";
					  }
					  else{
						  String Q="insert into servicios(id_servicio,Fecha_Sol,Id_Equipo,problema,estado,Usuario) "+
                          " values("+s.getIdServicio()+",getdate(),"+s.getEquipo().getIdEquipo()+",'"+s.getProblema()+"','S','"+usuario.getUsuario()+"')";
						  if(this.conn.Insert(Q)){
							   finished=true;  
						  }
						  else{
							  this.error=this.conn.getError();
						  }
					  }
					break;
				case Iniciado:
                      if(this.ExisteServicio(s)){
                    	  String Q="update servicios set fecha_ini=getdate(),id_tecnico="+s.getTecnico().getNumeroEmpleado()+",id_tipo="+s.getTipo().getIdTipo()+",estado='I',Usuario='"+usuario.getUsuario()+"' where id_servicio="+s.getIdServicio();
						  if(this.conn.Update(Q)){
							   finished=true;  
						  }
						  else{
							  this.error=this.conn.getError();
						  }
                      }   
                      else{
                    	  this.error="Ha este servicio no lo puedes asignar un tecnico por que no existe.";
                      }
					break;
				case Terminado:
                    if(this.ExisteServicio(s)){
                  	      String Q="update servicios set Fecha_fin=getdate(),solucion='"+s.getSolucion()+"',estado='T',Usuario='"+usuario.getUsuario()+"' where id_servicio="+servicio.getIdServicio();
						  if(this.conn.Update(Q)){
							     finished=true;  
							     this.CargaServicio(s.getIdServicio());
							     System.out.println("Se esta trasladando el correo....");
							        Mailer mailer=new Mailer();
								    if(mailer.MandarCorreo(this.getServicio())){
								    	System.out.println("Se mando correctamente el correo");
								    }
								    else{
									    System.out.println(mailer.getError());
								    }
						  }
						  else{
  							     this.error=this.conn.getError();
						  }
                    }   
                    else{
                  	  this.error="Ha este servicio no lo puedes finalizar por que no existe.";
                    }
					break;
				default:
					this.error="EL ESTADO DEL SERVICIO NO ESVALIDO.";
					break;
				}   
			
		} catch (Exception e) {
			this.error=e.getMessage()+"-->"+e.getStackTrace()[0];
		}
		
		
		return finished;
	}
		
	
	/**
    *contructor de la clase
    */
    public ServiciosSistema()
    {
    	this.CargarAreas();
    	this.CargarTecnicos();
    	this.CargarTipoServicio();
    	this.CargarEquipo();
    	usuario=new Usuario();
    }
    /**
    *Metodo que se encarga de llenar hasArea.
    */
    
    public boolean CargarAreas()
	{
		boolean finished=false;
		Area a=new Area();
		try{
               if(this.conn.abrirConexion())
               {
            	   String Query="select * from Areas";
            	   ResultSet rs=this.conn.Query(Query);
            	   if(rs==null){
            		   this.error=this.conn.getError();
            	   }
            	   else{
            		   boolean Entro=false;
            		   while(rs.next()){
            			    a=new Area();
            			    a.setIdArea(Integer.parseInt(rs.getObject(1).toString()));
            			    a.setArea(rs.getObject(2).toString().trim());
            			    this.hashAreas.put(a.getIdArea(),a);
            			    Entro=true;
            		   }
            		   if(Entro)
            		   {
            			   finished=true;
            		   }
            		   else
            		   {
            			   this.error="NO EXISTE NINGUN REGISTRO";
            		   }
            	   }
               }
               else
               {
            	   this.error=this.conn.getError();
               }
		}
		catch(Exception e){
			this.error=e.getMessage()+"-->"+e.getStackTrace()[0]+"-->"+this.conn.getError();
		}
		finally{
    		 this.conn.cerrarConexion();
		}
		
		
		
		
		return finished;
	}
    /**
     *Metodo que se encarga de llenar Equipo.
     */
  
    public boolean CargarEquipo()
	{
		boolean finished=false;
 		hashEquipo=new HashMap<Integer, Equipo>();
		Equipo eq=new Equipo();
		try{
               if(this.conn.abrirConexion())
               {
            	   String Query="select * from Equipos";
            	   ResultSet rs=this.conn.Query(Query);
            	   if(rs==null){
            		   this.error=this.conn.getError();
            	   }
            	   else{
            		   boolean Entro=false;
            		   while(rs.next()){
            			 eq=new Equipo();
           			    eq.setIdEquipo(Integer.parseInt(rs.getObject(1).toString()));
           			    eq.setNombreEquipo(rs.getObject(2).toString());
           			    eq.setTipoEquipo(rs.getObject(3).toString());
           			    eq.setUsuario(rs.getObject(4).toString());
           			    eq.setArea(this.getHashAreas().get( Integer.parseInt(rs.getObject(5).toString())));
           			    InetAddress ia=InetAddress.getByName(rs.getObject(6).toString().trim());
           			    eq.setIP(ia);
           			    
           			    this.hashEquipo.put(eq.getIdEquipo(),eq);
           			    Entro=true;
            		   }
            		   if(Entro)
            		   {
            			   finished=true;
            		   }
            		   else
            		   {
            			   this.error="NO EXISTE NINGUN REGISTRO";
            		   }
            	   }
               }
               else
               {
            	   this.error=this.conn.getError();
               }
		}
		catch(Exception e){
			this.error=e.getMessage()+"-->"+e.getStackTrace()[0];
		}
		finally{
    		 this.conn.cerrarConexion();
		}
		
		
		
		
		return finished;
	}
    
    /**
     *Metodo que se encarga de llenar hasTipoServicio.
     */
  
    
    public boolean CargarTipoServicio()
	{
		boolean finished=false;
		TipoServicio t=new TipoServicio();
		try{
               if(this.conn.abrirConexion())
               {
            	   String Query="select * from Tipo_Servicios";
            	   ResultSet rs=this.conn.Query(Query);
            	   if(rs==null){
            		   this.error=this.conn.getError();
            	   }
            	   else{
            		   boolean Entro=false;
            		   while(rs.next()){
            			    t=new TipoServicio();
            			    t.setIdTipo(Integer.parseInt(rs.getObject(1).toString()));
            			    t.setTipo(rs.getObject(2).toString().trim());
            			    this.hashTipoServicios.put(t.getIdTipo(),t);
            			    Entro=true;
            		   }
            		   if(Entro)
            		   {
            			   finished=true;
            		   }
            		   else
            		   {
            			   this.error="NO EXISTE NINGUN REGISTRO";
            		   }
            	   }
               }
               else
               {
            	   this.error=this.conn.getError();
               }
		}
		catch(Exception e){
			this.error=e.getMessage()+"-->"+e.getStackTrace()[0];
		}
		finally{
    		 this.conn.cerrarConexion();
		}
		
		
		
		
		return finished;
	}
    /**
    *Metodo que se encarga de llenar hastecnicos.
    */

    public boolean CargarTecnicos()
	{
		boolean finished=false;
		Tecnico t=new Tecnico();
		try{
               if(this.conn.abrirConexion())
               {
            	   String Query="select * from tecnico";
            	   ResultSet rs=this.conn.Query(Query);
            	   if(rs==null){
            		   this.error=this.conn.getError();
            	   }
            	   else{
            		   boolean Entro=false;
            		   while(rs.next()){
            			    t=new Tecnico();
            			    t.setNumeroEmpleado(Integer.parseInt(rs.getObject(1).toString()));
            			    t.setNombre(rs.getObject(2).toString());
            			    t.setApellido1(rs.getObject(3).toString());
            			    t.setApellido2(rs.getObject(4).toString());
            			    t.setTelefono(rs.getObject(5).toString());
            			    t.setDireccion(rs.getObject(6).toString());
            			    t.setCorreo(rs.getObject(7).toString());
            			    this.hashTecnicos.put(t.getNumeroEmpleado(),t);
            			    Entro=true;
            		   }
            		   if(Entro)
            		   {
            			   finished=true;
            		   }
            		   else
            		   {
            			   this.error="NO EXISTE NINGUN REGISTRO";
            		   }
            	   }
               }
               else
               {
            	   this.error=this.conn.getError();
               }
		}
		catch(Exception e){
			this.error=e.getMessage()+"-->"+e.getStackTrace()[0];
		}
		finally{
    		 this.conn.cerrarConexion();
		}
		
		
		
		
		return finished;
	}
	
    /**
    *propiedad que regresa cualquier error que se genere dentro de los metodos
    */
	
	public String getError() {
		return error;
	}
	/**
    *propiedad que regresa la lista de tecnicos
    */
	
	public HashMap<Integer, Tecnico> getHashTecnicos() {
		return hashTecnicos;
	}
	/**
    *propiedada para asignar la lista de tecnicos
    */
    
	public void setHashTecnicos(HashMap<Integer, Tecnico> hashTecnicos) {
		this.hashTecnicos = hashTecnicos;
	}
	
	
	
	
	/**
    *metodo que inserta un tecnico a al base datos
    */
	
	public boolean InsertarEquipo(Equipo eq,Usuario usuario)
	{
		boolean finished=false;
		   try{
   			      String Ins="INSERT INTO EQUIPOS(id_equipo, nommbre_equipo, tipo_equipo, usuario, id_area, ip,usuario2)"+
                             "VALUES("+eq.getIdEquipo()+",'"+eq.getNombreEquipo()+"','"+eq.getTipoEquipo()+"','"+eq.getUsuario()+"',"+eq.getArea().getIdArea()+",'"+eq.getIP().toString().replace("/","")+"','"+usuario.getUsuario()+"')";
     			      
   			          if(this.conn.Insert(Ins))
     			      {
     			    	  finished=true;
     			      }
     			      else
     			      {
     			    	  this.error=this.conn.getError();
     			      }
		   }
		   catch (Exception e) {
			   this.error=e.getMessage()+"-->"+e.getStackTrace()[0]+this.conn.getError();
		}
		return finished;
	}
	/**
    *metodo que inserta un tecnico a al base datos
    */
		
	public boolean InsertarTecnico(Tecnico t,Usuario usuario){
		boolean finished=false;
		   try{
   			      String Ins="INSERT INTO TECNICO(Id_Tecnico, nombre, apellido1, apellido2, telefono, direccion, correo,usuario) "+
                  " VALUES("+t.getNumeroEmpleado()+",'"+t.getNombre()+"','"+t.getApellido1()+"','"+t.getApellido2()+"','"+t.getTelefono()+"','"+t.getDireccion()+"','"+t.getCorreo()+"','"+usuario.getUsuario()+"')";
   			          if(this.conn.Insert(Ins))
     			      {
     			    	  finished=true;
     			      }
     			      else
     			      {
     			    	  this.error=this.conn.getError();
     			      }
		   }
		   catch (Exception e) {
			   this.error=e.getMessage()+"-->"+e.getStackTrace()[0];
		}
		return finished;
	}
	

	/**
    *Inserta el valor de la area a la base detos
    */
	public boolean InsertarArea(Area a,Usuario usuario)
	{
		boolean finished=false;
		   try{
   			      String Ins="INSERT INTO AREAS(Id_Area, Area,Usuario)"+
                  " VALUES("+a.getIdArea()+",'"+a.getArea()+"','"+usuario.getUsuario()+"')";     			      
   			          if(this.conn.Insert(Ins))
     			      {
     			    	  finished=true;
     			      }
     			      else
     			      {
     			    	  this.error=this.conn.getError();
     			      }
		   }
		   catch (Exception e) {
			   this.error=e.getMessage()+"-->"+e.getStackTrace()[0];
		}
		return finished;
	}
	
	public boolean GetExisteArea(int IdArea){
		boolean finished=false;
		   String Query="SELECT     COUNT(id_area) AS Cantidad FROM         areas WHERE     (id_area = "+IdArea+")";
		   int Cantidad=Integer.parseInt(this.conn.RegresaScalar(Query));
		   if(Cantidad>0){
			   finished=true;
		   }
		return finished;
	}
	
	public boolean GetExisteEquipo(int IdEquipo){
		boolean finished=false;
		String Query="select count(id_equipo) as Cantidad from Equipos where id_equipo="+IdEquipo;
		int Cantidad=Integer.parseInt(this.conn.RegresaScalar(Query));
		if(Cantidad>0){
			finished=true;
		}
		return finished;
	}
	 public boolean GetByUsuario(String Usuario){
		 boolean finished=false;
		 try{
			 this.usuario=new Usuario();
			 int CantiUsuario=Integer.parseInt(conn.RegresaScalar("SELECT COUNT(USUARIO) AS CANTI FROM USUARIO WHERE USUARIO='"+Usuario+"'"));
			 if(CantiUsuario>0){
				 int ConfirmaPassword=1;
				 if(ConfirmaPassword>0){
					   if(this.conn.abrirConexion()){
						   String Query="SELECT * FROM USUARIO WHERE USUARIO='"+Usuario+"'";
						   ResultSet rs=this.conn.Query(Query);
						   boolean Entro=false;
						   while(rs.next()){
							   this.usuario.setUsuario(rs.getObject(1).toString());
							   System.out.println(this.usuario.getUsuario());
							   
							   this.usuario.setNombreUsuario(rs.getObject(2).toString());
							   
							   
							   this.usuario.setPassword(rs.getObject(3).toString());
							   
							   this.usuario.setAbcAreaInt(Integer.parseInt(rs.getObject(4).toString()));
							   System.out.println("Abc Area:"+this.usuario.getAbcArea().toString());
							   
							   this.usuario.setAbcEquipoInt(Integer.parseInt(rs.getObject(5).toString()));
							   System.out.println("Abc Equipo:"+this.usuario.getAbcEquipo().toString());
							   
							   this.usuario.setAbcTipoServicioInt(Integer.parseInt(rs.getObject(6).toString()));
							   this.usuario.setAbcTecnicoInt(Integer.parseInt(rs.getObject(7).toString()));
							   this.usuario.setControlServiciosInt(Integer.parseInt(rs.getObject(8).toString()));
							   this.usuario.setConsultasInt(Integer.parseInt(rs.getObject(9).toString()));
							   this.usuario.setAbcUsuariosInt(Integer.parseInt(rs.getObject(10).toString()));
							   Entro=true;
						   }
						   
						   if(Entro){
							   finished=true;
						   }
						   else{
							   this.error="NO PUDO CARGAR LOS DATOS DE LA BASE DE DATOS";
						   }
					   }
					   else{
						   this.error=conn.getError();
					   }
						   
				 }
				 else{
					  this.error="EL PASSWORD ES INCORRECTO.";
				 }
			 }
			 else{
				 this.error="EL USUARIO SOLICITADO NO EXISTE";
			 }
		 }
		 catch (Exception e) {
			   this.error=e.getMessage()+"-->"+e.getStackTrace()[0];
		 }
		 finally{
			 conn.cerrarConexion();
		 }
		 return finished;
	 }
	
	 public boolean GetByUsuario(String Usuario,String PassWord){
		 boolean finished=false;
		 try{
			 this.usuario=new Usuario();
			 int CantiUsuario=Integer.parseInt(conn.RegresaScalar("SELECT COUNT(USUARIO) AS CANTI FROM USUARIO WHERE USUARIO='"+Usuario+"'"));
			 if(CantiUsuario>0){
				 int ConfirmaPassword=Integer.parseInt(conn.RegresaScalar("SELECT COUNT(USUARIO) AS CANTI FROM USUARIO WHERE USUARIO='"+Usuario+"' AND PASSWORD='"+PassWord+"'"));
				 if(ConfirmaPassword>0){
					   if(this.conn.abrirConexion()){
						   
						   String Query="SELECT * FROM USUARIO WHERE USUARIO='"+Usuario+"' AND PASSWORD='"+PassWord+"'";
						   ResultSet rs=this.conn.Query(Query);
						   boolean Entro=false;

						   while(rs.next()){
							   this.usuario.setUsuario(rs.getObject(1).toString());
							   this.usuario.setNombreUsuario(rs.getObject(2).toString());
							   this.usuario.setPassword(rs.getObject(3).toString());
							   this.usuario.setAbcAreaInt(Integer.parseInt(rs.getObject(4).toString()));
							   this.usuario.setAbcEquipoInt(Integer.parseInt(rs.getObject(5).toString()));
							   this.usuario.setAbcTipoServicioInt(Integer.parseInt(rs.getObject(6).toString()));
							   this.usuario.setAbcTecnicoInt(Integer.parseInt(rs.getObject(7).toString()));
							   this.usuario.setControlServiciosInt(Integer.parseInt(rs.getObject(8).toString()));
							   this.usuario.setConsultasInt(Integer.parseInt(rs.getObject(9).toString()));
							   this.usuario.setAbcUsuariosInt(Integer.parseInt(rs.getObject(10).toString()));
							   Entro=true;
						   }
						   
						   if(Entro){
							   finished=true;
						   }
						   else{
							   this.error="NO PUDO CARGAR LOS DATOS DE LA BASE DE DATOS";
						   }
					   }
					   else{
						   this.error=conn.getError();
					   }
						   
				 }
				 else{
					  this.error="EL PASSWORD ES INCORRECTO.";
				 }
			 }
			 else{
				 this.error="EL USUARIO SOLICITADO NO EXISTE";
			 }
		 }
		 catch (Exception e) {
			   this.error=e.getMessage()+"-->"+e.getStackTrace()[0];
		 }
		 finally{
			 conn.cerrarConexion();
		 }
		 return finished;
	 }

	 public boolean UpdateUsuario(Usuario usuario,Usuario usuario2)
	  {
		  boolean finished=false;
		  try{
			  String Query="UPDATE USUARIO SET Nombre='"+usuario.getNombreUsuario()+"', Password='"+usuario.getPassword()+"',abcArea="+usuario.getAbcAreaInt()+","+
				  " abcEquipo="+usuario.getAbcEquipoInt()+", abcTipoServicio="+usuario.getAbcTipoServicioInt()+", abcTecnico="+usuario.getAbcTecnicoInt()+", ControlServicios="+usuario.getControlServiciosInt()+", Consultas="+usuario.getConsultasInt()+","+
				  " abcUsuarios="+usuario.getAbcUsuariosInt()+",Usuario2='"+usuario2.getUsuario()+"' WHERE USUARIO='"+usuario.getUsuario()+"'";
			  System.out.println(Query);
			  
			  if(conn.Update(Query)){
				   finished=true;
			  }
			  else{
				  this.error= conn.getError();
			  }
		  }
		  catch(Exception e){
			    this.error=conn.getError();  
		  }
		  finally{
			  conn.cerrarConexion();
		  }
		  
		  return finished;
	  }
	
	   public boolean InsertarUsuario(Usuario usuario,Usuario usuario2){
		   boolean finished=false;
		   try{
			   String Query="INSERT INTO USUARIO(Usuario, Nombre, Password, abcArea, abcEquipo, abcTipoServicio, abcTecnico, ControlServicios, Consultas, abcUsuarios,Usuario2)"+
			  " VALUES('"+usuario.getUsuario()+"','"+usuario.getNombreUsuario()+"','"+usuario.getPassword()+"',"+usuario.getAbcAreaInt()+
			  ","+usuario.getAbcEquipoInt()+","+usuario.getAbcTipoServicioInt()+","+usuario.getAbcTecnicoInt()+","+usuario.getControlServiciosInt()+","+usuario.getConsultasInt()+","+usuario.getAbcUsuariosInt()+",'"+usuario2.getUsuario()+"')";
			  
			   System.out.println(Query);
			  if(conn.Insert(Query)){
				    finished=true;
			  }
			  else{
				    this.error=conn.getError();  
			  }
		   }
		   catch (Exception e) {
			   this.error=e.getMessage()+"-->"+e.getStackTrace()[0];
		   }
		   finally{
			  conn.cerrarConexion(); 
		   }
		   return finished;
	   }
	   
	/**
    *carga el valor del tipo de servicio al area.
    */
	public boolean InsertarTipo(TipoServicio t,Usuario usuario)
	{
		boolean finished=false;
		   try{
   			      String Ins="INSERT INTO TIPO_SERVICIOS(Id_Tipo, tipo,Usuario)"+
                  " VALUES("+t.getIdTipo()+",'"+t.getTipo()+"','"+usuario.getUsuario()+"')";     			      
   			          if(this.conn.Insert(Ins))
     			      {
     			    	  finished=true;
     			      }
     			      else
     			      {
     			    	  this.error=this.conn.getError();
     			      }
		   }
		   catch (Exception e) {
			   this.error=e.getMessage()+"-->"+e.getStackTrace()[0];
		}
		return finished;
	}
	/**
    *obtiene el hash de las areas
    */
	public HashMap<Integer, Area> getHashAreas() {
		return hashAreas;
	}
	/**
    *recibe el hash de las areas
    */
	public void setHashAreas(HashMap<Integer, Area> hashAreas) {
		this.hashAreas = hashAreas;
	}
	/**
    *metodo que actualiza la informacion del tecnico en la base de datos
    */
	
	public boolean UpdateEquipo(Equipo eq,Usuario usuario)
	{
		boolean finished=false;
		   try{
   			      String Upd="UPDATE EQUIPOS SET nommbre_equipo='"+eq.getNombreEquipo()+"', tipo_equipo='"+eq.getTipoEquipo()+"',usuario='"+eq.getUsuario()+"', id_area="+eq.getArea().getIdArea()+", ip='"+eq.getIP().toString().replace("/","")+"',usuario2='"+usuario.getUsuario()+"' WHERE ID_EQUIPO="+eq.getIdEquipo();
   			          if(this.conn.Update(Upd))
     			      {
     			    	  finished=true;
     			      }
     			      else
     			      {
     			    	  this.error=this.conn.getError();
     			      }
		   }
		   catch (Exception e) {
			   this.error=e.getMessage()+"-->"+e.getStackTrace()[0];
		}
		return finished;
	}

	
	
	/**
    *metodo que actualiza la informacion del tecnico en la base de datos.
    */
	public boolean UpdateTecnico(Tecnico t,Usuario usuario)
	{
		boolean finished=false;
		   try{
   			      String Upd="UPDATE Tecnico set nombre='"+t.getNombre()+"', apellido1='"+t.getApellido1()+"', apellido2='"+t.getApellido2()+"', telefono='"+t.getTelefono()+"', direccion='"+t.getDireccion()+"', correo='"+t.getCorreo()+"',Usuario='"+usuario.getUsuario()+"' where id_tecnico="+t.getNumeroEmpleado();
   			          if(this.conn.Update(Upd))
     			      {
     			    	  finished=true;
     			      }
     			      else
     			      {
     			    	  this.error=this.conn.getError();
     			      }
		   }
		   catch (Exception e) {
			   this.error=e.getMessage()+"-->"+e.getStackTrace()[0];
		}
		return finished;
	}
	/**
    *metodo que actualiza la informacion de Area en la base de datos
    */
	
	public boolean UpdateArea(Area a,Usuario usuario)
	{
		boolean finished=false;
		   try{
   			      String Upd="UPDATE Areas set Area='"+a.getArea()+"',usuario='"+usuario.getUsuario()+"' where id_Area="+a.getIdArea();
   			          if(this.conn.Update(Upd))
     			      {
     			    	  finished=true;
     			      }
     			      else
     			      {
     			    	  this.error=this.conn.getError();
     			      }
		   }
		   catch (Exception e) {
			   this.error=e.getMessage()+"-->"+e.getStackTrace()[0];
		}
		return finished;
	}
	/**
    *metodo que actualiza la informacion de Tipo de servicios en la base de datos
    */
	
	public boolean UpdateTipoServicio(TipoServicio t,Usuario usuario)
	{
		
		boolean finished=false;
		   try{
   			      String Upd="UPDATE Tipo_Servicios set Tipo='"+t.getTipo()+"',Usuario='"+usuario.getUsuario()+"' where id_Tipo="+t.getIdTipo();
   			          if(this.conn.Update(Upd))
     			      {
     			    	  finished=true;
     			      }
     			      else
     			      {
     			    	  this.error=this.conn.getError();
     			      }
		   }
		   catch (Exception e) {
			   this.error=e.getMessage()+"-->"+e.getStackTrace()[0];
		}
		return finished;
	}
	
	/**
    *metodo que proporciona el identificador del Equipo.
    */
	
    public int GetIdEquipo()
    {
    	int id=0;
    	       id=Integer.parseInt(this.conn.RegresaScalar("SELECT     CASE WHEN MAX(id_equipo) IS NULL THEN 1 ELSE MAX(id_equipo) + 1 END AS Maxi FROM         equipos"));
    	return id;
    }
    /**
    *metodo que proporciona el identificador del servicio.
    */
    
    public Integer GetIdServicio()
    {
    	Integer id=0;
    	       id=Integer.parseInt(this.conn.RegresaScalar("SELECT     CASE WHEN MAX(id_servicio) IS NULL THEN 1 ELSE MAX(id_servicio) + 1 END AS Maxi FROM         Servicios"));
    	return id;
    }
	//metodo que proporciona el identificador del tecnico que sigue
    public int GetIdTecnico()
    {
    	int id=0;
    	       id=Integer.parseInt(this.conn.RegresaScalar("(SELECT     MAX(id_tecnico) + 1 AS Maxi FROM         Tecnico )"));
    	return id;
    }
    /**
    *metodo que proporciona el identificador del tecnico que sigue
    */
	
    public int GetIdArea()
    {
    	int id=0;
    	  id=Integer.parseInt(this.conn.RegresaScalar("(SELECT     MAX(id_Area) + 1 AS Maxi FROM         Areas )"));
    	return id;
    }
    /**
    *metodo que proporciona el identificador del Tipo de servicio que sigue
    */
  
    public int GetIdTipo()
    {
    	int id=0;
    	  id=Integer.parseInt(this.conn.RegresaScalar("(SELECT     MAX(id_Tipo) + 1 AS Maxi FROM         TIPO_SERVICIO )"));
    	return id;
    }


    /**
    *contiene el hash de  equipos
    */
	public void setHashEquipo(HashMap<Integer, Equipo> hashEquipo) {
		this.hashEquipo = hashEquipo;
	}
	/**
    *recibe le hash de equipos
    */
	public HashMap<Integer, Equipo> getHashEquipo(){
		return this.hashEquipo;
	}

}

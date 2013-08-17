package servicios.rctd.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
//import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.JButton;

import servicios.rctd.controlador.ServiciosSistema;
import servicios.rctd.controlador.Validacion;
import servicios.rctd.modelo.Area;
import servicios.rctd.modelo.Equipo;
import servicios.rctd.modelo.Usuario;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.net.InetAddress;
import java.util.Collection;

public class GUIEquipo {

	private JInternalFrame jfEquipo;
	private Usuario usuario;
	/**
    *get y set
    */
	public JInternalFrame getJfEquipo() {
		return jfEquipo;
	}

	public void setJfEquipo(JInternalFrame jfEquipo) {
		this.jfEquipo = jfEquipo;
	}
	private JTextField txtIdEquipo;
	private JTextField txtNombre;
	private JTextField txtTipoEquipo;
	private JTextField txtUsuario;
	private JTextField txtIpAdress;
	private JComboBox cmbArea; 
	private Equipo equipo=new Equipo();
	private ServiciosSistema ss=new ServiciosSistema();
	/**
	    *constructor default
	    */
	public GUIEquipo(Usuario usuario) {
		this.usuario=usuario;
	}
	/**
	 *da de alta todos los componentes en el JInternalFrame
	 */
	public void Inicializar() {
		jfEquipo = new JInternalFrame(); 
		jfEquipo.setResizable(false);
		jfEquipo.setTitle("Equipo");
		jfEquipo.setBounds(100, 100, 305, 355);
		jfEquipo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jfEquipo.getContentPane().setLayout(new GridLayout(7, 1, 0, 0));
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		jfEquipo.getContentPane().add(panel_1);
		
		JLabel lblNewLabel = new JLabel("");
		panel_1.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("      Id Equipo");
		panel_1.add(lblNewLabel_1);
		
		txtIdEquipo = new JTextField();
		txtIdEquipo.addKeyListener(new KeyAdapter() {
			
			public void keyPressed(KeyEvent key) {
			
				if(key.getKeyCode()==10){
					if(new Validacion().ValidaEntero(txtIdEquipo.getText(),"")){
                    	equipo=ss.getHashEquipo().get(Integer.parseInt(txtIdEquipo.getText()));
                    	if(equipo==null){
                    		Mensaje("El equipo solicitado no existe.");
                    	}
                    	else{
                    		    txtNombre.setText(equipo.getNombreEquipo());
                    		    txtTipoEquipo.setText(equipo.getTipoEquipo());
                    		    txtUsuario.setText(equipo.getUsuario());
                    		    txtIpAdress.setText(equipo.getIP().toString().replace("/",""));
                    		    cmbArea.setSelectedIndex(equipo.getArea().getIdArea()-1);
                    	}
                    }
                    else{
                         Mensaje("El id equipo debe ser entero");	
                    }
				
				}
			}
		});
		panel_1.add(txtIdEquipo);
		txtIdEquipo.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_2.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		jfEquipo.getContentPane().add(panel_2);
		
		JLabel lblNewLabel_2 = new JLabel("                Area");
		panel_2.add(lblNewLabel_2);
		
		cmbArea = new JComboBox();
		panel_2.add(cmbArea);
		
		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_3.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		jfEquipo.getContentPane().add(panel_3);
		
		JLabel lblNewLabel_3 = new JLabel("Nombre Equipo");
		panel_3.add(lblNewLabel_3);
		
		txtNombre = new JTextField();
		panel_3.add(txtNombre);
		txtNombre.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panel_4.getLayout();
		flowLayout_3.setAlignment(FlowLayout.LEFT);
		jfEquipo.getContentPane().add(panel_4);
		
		JLabel lblNewLabel_4 = new JLabel("      Tipo Equipo");
		panel_4.add(lblNewLabel_4);
		
		txtTipoEquipo = new JTextField();
		panel_4.add(txtTipoEquipo);
		txtTipoEquipo.setColumns(10);
		
		JPanel panel_5 = new JPanel();
		FlowLayout flowLayout_4 = (FlowLayout) panel_5.getLayout();
		flowLayout_4.setAlignment(FlowLayout.LEFT);
		jfEquipo.getContentPane().add(panel_5);
		
		JLabel lblNewLabel_5 = new JLabel("             Usuario");
		panel_5.add(lblNewLabel_5);
		
		txtUsuario = new JTextField();
		panel_5.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout_5 = (FlowLayout) panel.getLayout();
		flowLayout_5.setAlignment(FlowLayout.LEFT);
		jfEquipo.getContentPane().add(panel);
		
		JLabel lblNewLabel_6 = new JLabel("         IP Adress");
		panel.add(lblNewLabel_6);
		
		txtIpAdress = new JTextField();
		panel.add(txtIpAdress);
		txtIpAdress.setColumns(10);
		
		JPanel panel_6 = new JPanel();
		jfEquipo.getContentPane().add(panel_6);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(new Validacion().ValidaEntero(txtIdEquipo.getText(), "")){
					try {
						equipo=new Equipo();
						if(equipo.Valida(txtIdEquipo.getText(), txtNombre.getText(), txtTipoEquipo.getText(),txtUsuario.getText(),cmbArea.getSelectedItem().toString().split(":")[0],txtIpAdress.getText(), ss.getHashAreas()))
						{
							if(ss.ExisteEquipo(equipo)){
	                              if(ss.UpdateEquipo(equipo,usuario)){
	                            	  Mensaje("SE MODIFICARON LAS ALTAS DE EQUIPOS.");
	                              }
	                              else{
	                            	  Mensaje(ss.getError());
	                              }
	                              ss.CargarEquipo();
							}
							else{
	                              if(ss.InsertarEquipo(equipo,usuario)){
	                            	  Mensaje("SE DIO DE ALTA EL EQUIPO.");
	                              }
	                              else{
	                            	  Mensaje(ss.getError());
	                              }
	                              ss.CargarEquipo();
							}
						}
						else{

							Mensaje(equipo.getError());
						}
					} catch (Throwable e) {
						Mensaje(e.getMessage()+"-->"+e.getStackTrace()[0]);
						
					}
				}
				else{
					Mensaje("El id debe ser entero");
				}
				
			}
		});
		panel_6.add(btnAgregar);
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(new Validacion().ValidaEntero(txtIdEquipo.getText(),"")){
                    	equipo=ss.getHashEquipo().get(Integer.parseInt(txtIdEquipo.getText()));
                    	if(equipo==null){
                    		Mensaje("El equipo solicitado no existe.");
                    	}
                    	else{
                    		    txtNombre.setText(equipo.getNombreEquipo());
                    		    txtTipoEquipo.setText(equipo.getTipoEquipo());
                    		    txtUsuario.setText(equipo.getUsuario());
                    		    txtIpAdress.setText(equipo.getIP().toString().replace("/",""));
                    		    cmbArea.setSelectedIndex(equipo.getArea().getIdArea());
                    	}
                    }
                    else{
                         Mensaje("El id equipo debe ser entero");	
                    }
				
			}
		});
		panel_6.add(btnConsultar);
		
		JButton btnCerrar=new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					jfEquipo.setClosed(true);
				} catch (PropertyVetoException e) {
					e.printStackTrace();
				}
			}
		});
		
		panel_6.add(btnCerrar);

		for(Integer key:ss.getHashAreas().keySet())
		{
			Area a =ss.getHashAreas().get(key);
			cmbArea.addItem(a.getIdArea()+":"+a.getArea());
		}
	}
	/**
	    * mensaje que se muestra sobre el JInternalFrame
	    */
	private void Mensaje(String str)
	{
		JOptionPane.showMessageDialog(jfEquipo, str);
	}

}

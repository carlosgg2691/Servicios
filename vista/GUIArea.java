																																																																																																																																																																				package servicios.rctd.vista;


import javax.swing.JFrame;
import java.awt.GridLayout;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import servicios.rctd.controlador.ServiciosSistema;
import servicios.rctd.controlador.Validacion;
import servicios.rctd.modelo.Area;
import servicios.rctd.modelo.Usuario;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;

public class GUIArea {

	private JInternalFrame jfAreas;

	public JInternalFrame getJfAreas() {
		return jfAreas;
	}

	private JTextField txtArea;
	private Area area;
	private ServiciosSistema ss;
	private JTextField txtIdArea;
	
	public GUIArea(Usuario usuario) {
		area=new Area();
		ss=new ServiciosSistema();
		ss.setUsuario(usuario);
	}
	
	public void Inicializar() {
		jfAreas = new JInternalFrame();
		//jfAreas.setResizable(false);
		//jfAreas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jfAreas.setTitle("Areas");
		jfAreas.setBounds(100, 100, 284, 179);
		jfAreas.getContentPane().setLayout(new GridLayout(3,3));
		
		JPanel panel_1 = new JPanel();
		jfAreas.getContentPane().add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblNewLabel = new JLabel("  ID Area");
		panel_1.add(lblNewLabel);
		
		txtIdArea = new JTextField();
		txtIdArea.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent key) {
				
				if(key.getKeyCode()==10){
					
					if(new Validacion().ValidaEntero(txtIdArea.getText(), "El numero del Area "))
					{
					area=ss.getHashAreas().get(Integer.parseInt(txtIdArea.getText()));
					if(area==null){
						Mensaje("NO SE ENCONTRO ESE NUMERO DE AREA");
					}
					else{
						txtArea.setText(area.getArea());
					}
					}
					
				}
			}
		});
		panel_1.add(txtIdArea);
		txtIdArea.setColumns(4);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		jfAreas.getContentPane().add(panel_2);
		
		JLabel lblArea = new JLabel("      Area");
		panel_2.add(lblArea);
		
		txtArea = new JTextField();
		panel_2.add(txtArea);
		txtArea.setColumns(18);
		
		JPanel panel_3 = new JPanel();
		jfAreas.getContentPane().add(panel_3);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setText("Guardar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				area=new Area();
				if(area.Valida(txtIdArea.getText(),txtArea.getText()))
				{
				    if(ss.GetExisteArea(area.getIdArea())){
				       if(ss.UpdateArea(area,ss.getUsuario())){
					    	 Mensaje("EL AREA SE MODIFICO CORRECTAMENTE");
					    	 ss.CargarAreas();				    	   
				       }
				       else{
					    	 Mensaje(ss.getError());				    	   
				       }
				    }
				    else{
						if(ss.InsertarArea(area,ss.getUsuario()))
					     {
					    	 Mensaje("EL AREA SE INSERTO CORRECTAMENTE");
					    	 ss.CargarAreas();
					     }
					     else
					     {
					    	 Mensaje(ss.getError());
					     }
				    }
				}
				else
				{
					Mensaje(area.getError());
				}
			}
		});
		panel_3.add(btnAgregar);
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(new Validacion().ValidaEntero(txtIdArea.getText(), "El numero del Area "))
				{
				area=ss.getHashAreas().get(Integer.parseInt(txtIdArea.getText()));
				if(area==null){
					Mensaje("NO SE ENCONTRO ESE NUMERO DE AREA");
				}
				else{
					txtArea.setText(area.getArea());
				}
				}				
			}
		});
		panel_3.add(btnConsultar);
		
		
		JButton btnCerrar=new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					jfAreas.setClosed(true);
				} catch (PropertyVetoException e) {
					e.printStackTrace();
				}
			}
		});

		txtIdArea.setText("1");
	    panel_3.add(btnCerrar);	
   		
	}
	/**
	    * mensaje que se muestra sobre el JInternalFrame
	    */
	private void Mensaje(String str)
	{
		JOptionPane.showMessageDialog(jfAreas, str);
	}
      
}

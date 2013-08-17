package servicios.rctd.vista;


import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JTree;
import javax.swing.JTable;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.Color;

import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import servicios.rctd.controlador.ServiciosSistema;



public class GUIConsultas {

	private JInternalFrame jfConsultas;
	public JInternalFrame getJfConsultas() {
		return jfConsultas;
	}
	private JTable jtbConsulta;
	private JSplitPane splitPane;
	private JPanel panel_1;
    private ServiciosSistema ss=new ServiciosSistema();
	
	public GUIConsultas() {
	}

	/**
	    *da de alta todos los componentes en el JInternalFrame
	    */
	public void Inicializar() {
		jfConsultas = new JInternalFrame();
		jfConsultas.setBounds(100, 100, 524, 411);
		jfConsultas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jfConsultas.getContentPane().setLayout(new GridLayout(1, 1, 0, 0));
		
		panel_1 = new JPanel();
		jfConsultas.getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		splitPane = new JSplitPane();
		splitPane.setBackground(new Color(255, 255, 255));
		panel_1.add(splitPane);
		
		JTree trTiposServicio = new JTree();
		trTiposServicio.setBackground(new Color(144, 238, 144));
		splitPane.setLeftComponent(trTiposServicio);
		trTiposServicio.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent arg0) {

				try {
					if(arg0.getNewLeadSelectionPath().toString().indexOf("Areas")>0)
					{
					   if(ss.CargarQuery("select * from areas")){
						   jtbConsulta=new JTable(ss.getRows(),ss.getFields());
							JScrollPane jsp=new JScrollPane(jtbConsulta);
							splitPane.setRightComponent(jsp);	 
					   }
					   else{
						   Mensaje(ss.getError());
					   }
					   
					 }

					if(arg0.getNewLeadSelectionPath().toString().indexOf("Equipos")>0)
					{
						String Q="SELECT     equipos.id_equipo, equipos.nommbre_equipo, equipos.tipo_equipo, equipos.usuario, equipos.id_area, areas.Area, equipos.ip FROM         equipos INNER JOIN                       areas ON equipos.id_area = areas.id_area";
						
					   if(ss.CargarQuery(Q)){
						   jtbConsulta=new JTable(ss.getRows(),ss.getFields());
							JScrollPane jsp=new JScrollPane(jtbConsulta);
							splitPane.setRightComponent(jsp);	 
					   }
					   else{
						   Mensaje(ss.getError());
					   }
					   
					 }
					

					if(arg0.getNewLeadSelectionPath().toString().indexOf("Tecnico")>0)
					{
						String Q="SELECT * from tecnico";
						
					   if(ss.CargarQuery(Q)){
						   jtbConsulta=new JTable(ss.getRows(),ss.getFields());
							JScrollPane jsp=new JScrollPane(jtbConsulta);
							splitPane.setRightComponent(jsp);	 
					   }
					   else{
						   Mensaje(ss.getError());
					   }
					   
					 }

					if(arg0.getNewLeadSelectionPath().toString().indexOf("Tipos de Ser")>0)
					{
						String Q="SELECT * from tipo_servicios";
						
					   if(ss.CargarQuery(Q)){
						   jtbConsulta=new JTable(ss.getRows(),ss.getFields());
							JScrollPane jsp=new JScrollPane(jtbConsulta);
							splitPane.setRightComponent(jsp);	 
					   }
					   else{
						   Mensaje(ss.getError());
					   }
					   
					 }
					

					if(arg0.getNewLeadSelectionPath().toString().indexOf("Solicitados")>0)
					{
						String Q="SELECT     id_servicio, Fecha_sol, id_equipo, problema FROM         Servicios WHERE ESTADO='S'";
						
					   if(ss.CargarQuery(Q)){
						   jtbConsulta=new JTable(ss.getRows(),ss.getFields());
							JScrollPane jsp=new JScrollPane(jtbConsulta);
							splitPane.setRightComponent(jsp);	 
					   }
					   else{
						   Mensaje(ss.getError());
					   }
					   
					 }
					
					if(arg0.getNewLeadSelectionPath().toString().indexOf("Iniciados")>0)
					{
						String Q="SELECT     id_servicio, Fecha_sol, id_equipo,problema, Fecha_ini, id_tecnico, id_tipo FROM         Servicios WHERE ESTADO='I'";
						
					   if(ss.CargarQuery(Q)){
						   jtbConsulta=new JTable(ss.getRows(),ss.getFields());
							JScrollPane jsp=new JScrollPane(jtbConsulta);
							splitPane.setRightComponent(jsp);	 
					   }
					   else{
						   Mensaje(ss.getError());
					   }
					 }
					
					if(arg0.getNewLeadSelectionPath().toString().indexOf("Finalizados")>0)
					{
						String Q="SELECT     id_servicio, Fecha_sol, id_equipo,problema, Fecha_ini, id_tecnico, id_tipo, Fecha_fin, solucion  FROM         Servicios WHERE ESTADO='T'";
						
					   if(ss.CargarQuery(Q)){
						   jtbConsulta=new JTable(ss.getRows(),ss.getFields());
							JScrollPane jsp=new JScrollPane(jtbConsulta);
							splitPane.setRightComponent(jsp);	 
					   }
					   else{
						   Mensaje(ss.getError());
					   }
					   
					 }
					
					
				} catch (Exception e) {
					//Mensaje(e.getMessage()+"-->"+e.getStackTrace()[0]);
				}
			}
		});

		trTiposServicio.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("Control de Servicios") {
				{
					DefaultMutableTreeNode node_1;
					node_1 = new DefaultMutableTreeNode("Servicios");
						node_1.add(new DefaultMutableTreeNode("Solicitados"));
						node_1.add(new DefaultMutableTreeNode("Iniciados"));
						node_1.add(new DefaultMutableTreeNode("Finalizados"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("Catalogo");
						node_1.add(new DefaultMutableTreeNode("Areas"));
						node_1.add(new DefaultMutableTreeNode("Tipos de Servicio"));
						node_1.add(new DefaultMutableTreeNode("Tecnicos"));
						node_1.add(new DefaultMutableTreeNode("Equipos"));
					add(node_1);
				}
			}
		));
		
		jtbConsulta = new JTable();
		jtbConsulta.setBackground(new Color(255, 255, 255));
		jtbConsulta.setRowSelectionAllowed(false);
		jtbConsulta.setModel(new DefaultTableModel(
			new Object[][] {
				{"1", "2", "3", "4"},
			},
			new String[] {
				"Cabecera 1", "Cabecera 2", "Cabecera 3", "Cabecera"
			}
		));
		JScrollPane jsp=new JScrollPane(jtbConsulta);
			splitPane.setRightComponent(jsp);
	}
	/**
	 * mensaje que se muestra sobre el JInternalFrame
	 */
	private void Mensaje(String Str) {
        JOptionPane.showMessageDialog(jfConsultas,Str);		
}

}

package servicios.rctd.vista;


import java.awt.EventQueue;

import javax.swing.JFrame;

import javax.swing.JDesktopPane;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import java.awt.Component;

import javax.swing.JMenu;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import servicios.rctd.modelo.Conexion;
import servicios.rctd.modelo.Usuario;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
//ESTE ES EL DE MUESTRA
public class GUI {

	private JFrame jfGUI;
	public JFrame getJfGUI() {
		return jfGUI;
	}

	public void setJfGUI(JFrame jfGUI) {
		this.jfGUI = jfGUI;
	}
	static Usuario usuario;
	
	//       public static void main(String[] args) {
	//			try {
    //                GUI window = new GUI();
	//				window.getJfGUI().setVisible(true);
	//			    } catch (Exception e) {
	//				e.printStackTrace();
	//			    }
	//         }

	/**
    *constructor default	
	 * @wbp.parser.entryPoint
    */
	public GUI(Usuario usuario) {
		this.usuario=usuario;
		Inicializar();
	}
	/**
    *da de alta todos los componentes en el JInternalFrame
    */
	private void Inicializar() {
		jfGUI = new JFrame();
		final JDesktopPane desktop=new JDesktopPane();
		jfGUI.getContentPane().add(desktop);
		
		jfGUI.setTitle("PROGRAMA DE SERVICIOS");
		//jfGUI.setResizable(false);
		jfGUI.setBounds(400, 100, 550, 600);
		//jfGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		jfGUI.setJMenuBar(menuBar);
		
		JMenu mnCatalogos = new JMenu("Catalogos");
		menuBar.add(mnCatalogos);
			
		
		JMenuItem mntmAbcArea = new JMenuItem("ABC Area");
		mntmAbcArea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GUIArea gArea=new GUIArea(usuario);
				gArea.Inicializar();
				desktop.add(gArea.getJfAreas());
			    gArea.getJfAreas().setVisible(true);
			}
		});
		mnCatalogos.add(mntmAbcArea);
		
		JMenuItem mntmAbcEquipo = new JMenuItem("ABC Equipo");
		mntmAbcEquipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GUIEquipo gEquipo=new GUIEquipo(usuario);
				gEquipo.Inicializar();
				desktop.add(gEquipo.getJfEquipo());
				gEquipo.getJfEquipo().setVisible(true);
				
			}
		});
		mnCatalogos.add(mntmAbcEquipo);
		
		JMenuItem mntmAbcServicio = new JMenuItem("ABC Tipo Servicios");
		mntmAbcServicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GUITipoServicio gTipoServicio=new GUITipoServicio(usuario);
				gTipoServicio.Inicilizar();
				desktop.add(gTipoServicio.getJfTipoDeServicios());
				gTipoServicio.getJfTipoDeServicios().setVisible(true);
			}
		});
		
		mnCatalogos.add(mntmAbcServicio);
		
		JMenuItem mntmAbcTecnico = new JMenuItem("ABC Tecnico");
		mntmAbcTecnico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GUITecnico gTecnico=new GUITecnico(usuario);
				gTecnico.Inicializar();
				desktop.add(gTecnico.getJfTecnico());
				gTecnico.getJfTecnico().setVisible(true);
			}
		});
		mnCatalogos.add(mntmAbcTecnico);
		
		JMenu mnServicios = new JMenu("Servicios");
		menuBar.add(mnServicios);
		
		JMenuItem mntmControlDeServicios = new JMenuItem("Control de Servicios");
		mntmControlDeServicios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				        GUIServicio gServicio=new GUIServicio(usuario);
				        gServicio.Inicializar();
				        desktop.add(gServicio.getJfServicios());
				        gServicio.getJfServicios().setVisible(true);
			}
		});
		mnServicios.add(mntmControlDeServicios);

		JMenuItem mntmConsultas = new JMenuItem("Abrir Consultas");
		mntmConsultas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GUIConsultas gConsultas=new GUIConsultas();
				gConsultas.Inicializar();
				desktop.add(gConsultas.getJfConsultas());

				gConsultas.getJfConsultas().setVisible(true);
				try{
					gConsultas.getJfConsultas().setMaximum(true);
				}
				catch(Exception e){
					Mensaje(e.getMessage()+"-->"+e.getStackTrace()[0]);
				}
			}
		});
		
       JMenuItem mntmCerrarConsultas=new JMenuItem("Cerrar Consultas");
       mntmCerrarConsultas.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			 
			  desktop.removeAll();
			  jfGUI.getContentPane().removeAll();
			  jfGUI.getContentPane().add(desktop);
			  jfGUI.setExtendedState(1);
			  jfGUI.setExtendedState(0);
			  
		}
	});
		
		mnServicios.add(mntmConsultas);
		mnServicios.add(mntmCerrarConsultas);
		
		
           mntmAbcArea.setEnabled(usuario.getAbcArea());
           mntmAbcEquipo.setEnabled(usuario.getAbcEquipo());
		   mntmAbcTecnico.setEnabled(usuario.getAbcTecnico());
		   mntmAbcServicio.setEnabled(usuario.getAbcTipoServicio());
		   
		   JMenuItem mntmAbcUsuario = new JMenuItem("ABC Usuario");
		   mntmAbcUsuario.addActionListener(new ActionListener() {
		   	public void actionPerformed(ActionEvent arg0) {
		        GUIUsuario gUsuario=new GUIUsuario(usuario);
		        gUsuario.initialize();
		        desktop.add(gUsuario.getJfUsuarios());
		        gUsuario.getJfUsuarios().setVisible(true);		   		
		   	}
		   });
		   mntmAbcUsuario.addMouseListener(new MouseAdapter() {
		   	public void mouseClicked(MouseEvent arg0) {
		   		Mensaje("Entro al evento");
		        GUIUsuario gUsuario=new GUIUsuario(usuario);
		        gUsuario.initialize();
		        desktop.add(gUsuario.getJfUsuarios());
		        gUsuario.getJfUsuarios().setVisible(true);
		   	}
		   });
		   mnCatalogos.add(mntmAbcUsuario);
		   
		   mntmControlDeServicios.setEnabled(usuario.getControlServicios());
		   mntmConsultas.setEnabled(usuario.getConsultas());
		   mntmCerrarConsultas.setEnabled(usuario.getConsultas());
		   
		   JMenuItem mntmReporteDeBitacora = new JMenuItem("Reporte de Bitacora");
		   mntmReporteDeBitacora.addActionListener(new ActionListener() {
		   	public void actionPerformed(ActionEvent arg0) {
				//CODIGO ISERTADO
		   		String fileName= "C:\\Bitacora.jrxml";
				String outFileName ="C:\\TEST.PDF";
				HashMap hm=null;
				Connection conn = null;
				Conexion cnx = new Conexion();
				cnx.abrirConexion();
				conn = cnx.getConn();
				System.out.println("REPORTE");
				 try {
					 JasperReport reportes = JasperCompileManager.compileReport(fileName);
					 
			            // Fill the report using an empty data source
			            JasperPrint print = JasperFillManager.fillReport(reportes, hm, conn);
			            JasperViewer.viewReport(print,false);
			    		//HAST AQUI
			            
			            
			            /*System.out.println("REPORTE1");
			            
			            // Create a PDF exporter
			            JRExporter exporter = new JRPdfExporter();
			    		System.out.println("REPORTE2");
			            
			            // Configure the exporter (set output file name and print object)
			            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
			    		System.out.println("REPORTE3");
			            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			    		System.out.println("REPORTE4");
			            
			            // Export the PDF file
			            exporter.exportReport();
			    		System.out.println("REPORTE5");*/
			            
			        } catch (JRException e) {
			            e.printStackTrace();
			            System.exit(1);
			        } catch (Exception e) {
			            e.printStackTrace();
			            System.exit(1);
			        }

			}
			
		


		   		
		   		
		   		/*JasperReport report;
				try{
					Conexion con=new Conexion();
					if(con.abrirConexion()){
					File file=new File("C:\\Bitacora.jrxml");
					JasperDesign jasperDesign = JRXmlLoader.load(file);
					
					//report = (JasperReport)  JRLoader.loadObject("C:\\Bitacora.jasper");
					report = JasperCompileManager.compileReport(jasperDesign);
					//report = JasperCompileManager.compileReport("C://Bitacora.jasper");
					
					JasperPrint print= JasperFillManager.fillReport(report, null,con.getConn());
					String destFileNamePdf="C:\\RICARDO.PDF";
					JasperExportManager.exportReportToPdfFile(print, destFileNamePdf);
					}
					else{
						Mensaje(con.getError());
					}
				}
				catch (Exception e) {
					Mensaje(e.getMessage()+"-->"+e.getStackTrace()[0]);
				}*/

		   	
		   });
		   mnServicios.add(mntmReporteDeBitacora);
	}

	private void Mensaje(String Str) {
        JOptionPane.showMessageDialog(jfGUI,Str);		
}	
}

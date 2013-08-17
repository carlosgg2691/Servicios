package servicios.rctd.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import org.eclipse.wb.swing.FocusTraversalOnArray;

import servicios.rctd.controlador.ServiciosSistema;

import java.awt.Component;
import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPasswordField;

public class GUIPassword {
	private JFrame jfPassword;
	public JFrame getJfPassword() {
		return jfPassword;
	}

	public void setJfPassword(JFrame jfPassword) {
		this.jfPassword = jfPassword;
	}
	 
	private JTextField txtUsuario;
    private static ServiciosSistema ss;
    
    public static ServiciosSistema getSs() {
		return ss;
	}

    public static void setSs(ServiciosSistema ss) {
		GUIPassword.ss = ss;
	}

	private static boolean boolPassword;
	private JPasswordField txtPassword;
    
	public boolean getBoolPassword() {
		return boolPassword;
	}

	public void setBoolPassword(boolean boolPassword) {
		this.boolPassword = boolPassword;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					boolPassword=false;
					ss=new ServiciosSistema();
					GUIPassword window = new GUIPassword();
					window.jfPassword.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUIPassword() {
		initialize();
	}

	private void initialize() {
		jfPassword = new JFrame();
		jfPassword.setTitle("Servicios 2012");
		jfPassword.setBounds(100, 100, 396, 157);
		jfPassword.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel_2 = new JPanel();
		jfPassword.getContentPane().add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new GridLayout(3, 2, 0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3);
		
		JLabel lblNewLabel = new JLabel("Usuario:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_3.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_2.add(panel_1);
		
		txtUsuario = new JTextField();
		txtUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode()==13){
					txtPassword.setFocusable(true);
				}
			}
		});
		txtUsuario.setColumns(15);
		panel_1.add(txtUsuario);
		
		JPanel panel = new JPanel();
		panel_2.add(panel);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		panel.add(lblContrasea);
		
		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4);
		
		txtPassword = new JPasswordField();
		txtPassword.setColumns(15);
		panel_4.add(txtPassword);
		
		JPanel panel_5 = new JPanel();
		panel_2.add(panel_5);
		
		JButton btnNewButton = new JButton("Aceptar");
		btnNewButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				 if(ss.GetByUsuario(txtUsuario.getText(),txtPassword.getText())){
					   GUI ini=new GUI(ss.getUsuario());
					   ini.getJfGUI().setVisible(true);
					   jfPassword.setVisible(false);
				 }
				 else{
					   Mensaje(ss.getError());
				 }
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_5.add(btnNewButton);
		
		JPanel panel_6 = new JPanel();
		panel_2.add(panel_6);
		
		JButton btnNewButton_1 = new JButton("Cancelar");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				boolPassword=false;
				jfPassword.dispose();
			}
		});
		
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_6.add(btnNewButton_1);
	   txtUsuario.setFocusable(true);
	   jfPassword.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{jfPassword.getContentPane(), panel_2, panel_3, lblNewLabel, panel_1, txtUsuario, panel, lblContrasea, panel_4, panel_5, btnNewButton, panel_6, btnNewButton_1}));
	}

	private void Mensaje(String Str) {
        JOptionPane.showMessageDialog(jfPassword,Str);
}	
	
}

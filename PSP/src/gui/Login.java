package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import programa.Cliente;
import programa.Usuario;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {
	private JFrame frame;
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JTextField txtContrasena;
	Cliente cliente;
	Usuario usuario, enviarUsuario;
	public static String email;
	public static String contrasena;
	public static boolean cerrar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener((WindowListener) new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
		      // Se pide una confirmación antes de finalizar el programa
				int opcion = JOptionPane.showConfirmDialog(frame, 
					"¿Estás seguro que deseas cerrar sesión?",
					"Confirmación de cierre", 
					JOptionPane.YES_NO_OPTION, 
					JOptionPane.QUESTION_MESSAGE);
				if (opcion == JOptionPane.YES_OPTION) {
					cerrar = true;
					
					
					System.exit(0);
			

				}
			}
		});
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Iniciar Sesi\u00F3n");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNewLabel.setBounds(192, 40, 102, 20);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Usuario");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(68, 102, 45, 13);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Contrase\u00F1a");
		lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(68, 146, 74, 13);
		contentPane.add(lblNewLabel_2);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(154, 99, 179, 19);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		txtContrasena = new JTextField();
		txtContrasena.setColumns(10);
		txtContrasena.setBounds(154, 143, 179, 19);
		contentPane.add(txtContrasena);
		
		JLabel lblFalloInicioSesion = new JLabel("");
		lblFalloInicioSesion.setBounds(125, 185, 237, 13);
		contentPane.add(lblFalloInicioSesion);
		
		JButton btnIniciarSesion = new JButton("Iniciar");
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				email = txtUsuario.getText();
				contrasena = txtContrasena.getText();
				if(Cliente.cuentaGuardada != null) {
					dispose();
				}
				else {
					txtUsuario.setText("");
					txtContrasena.setText("");
					lblFalloInicioSesion.setText("Email o contraseña incorrecta");
				}
			}
		});
		btnIniciarSesion.setBounds(201, 214, 85, 21);
		contentPane.add(btnIniciarSesion);
		
	}
}

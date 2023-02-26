package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import programa.Usuario;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Menu extends JFrame {

	private JPanel contentPane;
	public static JTextField txtCantIngresar;
	public static JTextField txtCantTransferir;
	public static JTextField txtNumCuenta;
	public static JTextField txtConsultaSaldo;

	public static JLabel lblSaldo;
	public static JLabel lblCantidadATransferir;
	public static JLabel lblCantidaIngresar;
	public static JLabel lblTitulo;
	public static JLabel lblNumCuenta;

	public static JButton btnIngresarSaldo;
	public static JButton btnTransferirSaldo;
	public static JButton btnConsultarsaldo;
	public static JLabel lblNotificarOperacion;
	public static JButton btnCerrarSesion;

	/**
	 * Launch the application.

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	 */
	/**
	 * Create the frame.
	 */
	public Menu() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 550, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblTitulo = new JLabel("Operaciones en cuenta");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTitulo.setBounds(195, 40, 142, 13);
		contentPane.add(lblTitulo);

		btnIngresarSaldo = new JButton("Ingresar saldo");
		btnIngresarSaldo.setBounds(371, 95, 118, 21);
		contentPane.add(btnIngresarSaldo);

		lblCantidaIngresar = new JLabel("Cantidad a ingresar:");
		lblCantidaIngresar.setBounds(58, 99, 168, 17);
		contentPane.add(lblCantidaIngresar);

		txtCantIngresar = new JTextField();
		txtCantIngresar.setBounds(254, 96, 96, 19);
		contentPane.add(txtCantIngresar);
		txtCantIngresar.setColumns(10);

		lblCantidadATransferir = new JLabel("Cantidad a transferir a otra cuenta:");
		lblCantidadATransferir.setBounds(58, 145, 186, 17);
		contentPane.add(lblCantidadATransferir);

		txtCantTransferir = new JTextField();
		txtCantTransferir.setColumns(10);
		txtCantTransferir.setBounds(254, 142, 96, 19);
		contentPane.add(txtCantTransferir);

		btnTransferirSaldo = new JButton("Transferir saldo");
		btnTransferirSaldo.setBounds(371, 141, 118, 21);
		contentPane.add(btnTransferirSaldo);

		lblNumCuenta = new JLabel("Indicar # cuenta a transferir");
		lblNumCuenta.setBounds(58, 175, 186, 17);
		contentPane.add(lblNumCuenta);

		txtNumCuenta = new JTextField();
		txtNumCuenta.setColumns(10);
		txtNumCuenta.setBounds(254, 172, 96, 19);
		contentPane.add(txtNumCuenta);

		lblSaldo = new JLabel("Consultar el saldo de mi cuenta");
		lblSaldo.setBounds(58, 221, 186, 17);
		contentPane.add(lblSaldo);

		txtConsultaSaldo = new JTextField();
		txtConsultaSaldo.setColumns(10);
		txtConsultaSaldo.setBounds(254, 218, 96, 19);
		contentPane.add(txtConsultaSaldo);

		btnConsultarsaldo = new JButton("ConsultarSaldo");
		btnConsultarsaldo.setBounds(371, 217, 118, 21);
		contentPane.add(btnConsultarsaldo);

		lblNotificarOperacion = new JLabel("");
		lblNotificarOperacion.setBounds(58, 275, 262, 23);
		contentPane.add(lblNotificarOperacion);

		btnCerrarSesion = new JButton("Cerrar sesi\u00F3n");
		btnCerrarSesion.setBounds(371, 277, 118, 21);
		contentPane.add(btnCerrarSesion);
	}
}

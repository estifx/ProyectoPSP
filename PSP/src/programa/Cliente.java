package programa;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import entrada.Teclado;
import gui.Login;
import gui.Menu;

public class Cliente {
	static JFrame frame;
	public static boolean comprobar=false;
	public static boolean presionadoInicio, correo= false, password= false, cant= false, transferir= false, cuentaNum= false;
	public static Usuario usuarioGuardado;
	public static Cuenta cuentaGuardada= null;
	public static String email,contrasena;
	public static String cerrarLogin;
	public static Usuario usuarioInterfaz;
	public static String cerrar = "";
	public static int numCuenta=0,numCuentaDestino=0, opcion=0;
	public static double saldo=0;
	public static double cantidadAtransferir=0, cantidadIngresar=0;
	public static boolean botonpulsado = true;

	public static Login login = new Login();
	public static Menu menu = new Menu();


	public static void main(String[] args) throws IOException, ClassNotFoundException {
		System.setProperty("javax.net.ssl.trustStore", "./Certificados SSL/AlmacenClienteSSL"); 
		System.setProperty("javax.net.ssl.trustStorePassword", "741258");

		String mensaje, cadena="";
		String host = "localhost";
		int puerto = 60000;

		SSLSocketFactory sfact = (SSLSocketFactory) SSLSocketFactory.getDefault(); 
		SSLSocket cliente = (SSLSocket) sfact.createSocket(host, puerto);

		Transferencia transferencia;
		Object objeto = null;
		Object obj = null;
		Cuenta cuenta;


		//Objetos para comunicación con servidor
		ObjectInputStream objetoEntrada= null;
		ObjectOutputStream objetoSalida= null;

		System.out.println("*************Cliente Iniciado*************");


		objetoSalida = new ObjectOutputStream(cliente.getOutputStream());
		objetoEntrada = new ObjectInputStream(cliente.getInputStream());
		login.setVisible(true);

		while(cadena != null && !cadena.trim().equals("salir")) {

			objeto = menuPrincipal();
			if(objeto != null) {
				//envía al servidor
				objetoSalida.writeObject(objeto);

				//recibe del servidor
				try {
					objeto = (Object) objetoEntrada.readObject();
					if(objeto instanceof String) {
						mensaje = (String) objeto;
						if(mensaje.equals("salir")) {
							cadena = mensaje;
							//menu.dispose();
						}
						else if(mensaje.equals("ingresado")) {
							System.out.println("****Ingreso realizado con éxito****");
							Menu.lblNotificarOperacion.setText("Ingreso realizado con éxito");
							Menu.txtCantIngresar.setText("");
						}
						else if(mensaje.equals("no existe")) {
							System.out.println("****Email o contraseña incorrecta****");
							System.out.println("");
							Login.lblFalloInicioSesion.setText("Email o contraseña incorrecta");
							Login.txtUsuario.setText("");
							Login.txtContrasena.setText("");
						}
						else {
							System.out.println(mensaje);
							menu.txtConsultaSaldo.setText(mensaje);
						}
					}
					else if(objeto instanceof Cuenta) {
						sesionAbierta((Cuenta)objeto);
						if(comprobar) {
							cuentaGuardada =(Cuenta)objeto;
							System.out.println("Sesión abierta");
							login.dispose();
							menu.setVisible(true);
						}
					}

					else if(objeto instanceof Transferencia) {
						transferencia=(Transferencia) objeto;
						transferenciaRealizada(transferencia);
						menu.txtCantTransferir.setText("");
						menu.txtNumCuenta.setText("");
						menu.lblNotificarOperacion.setText("Transferencia realizada con éxito");
						System.out.println("Transferencia realizada con éxito");
					}
					if(cadena.equals("exit")) {
						objeto = cadena;
					}

				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			botonpulsado = true;
		}
		System.out.println("*************Cliente Finalizado*************");
		objetoEntrada.close();
		objetoSalida.close();
		cliente.close();
	}

	public static Object menuPrincipal() {	
		Object objetoEnviarHilo= null;
		Usuario usuario;

		while(botonpulsado) {
			if(cuentaGuardada == null) {
				login.txtUsuario.getDocument().addDocumentListener(new DocumentListener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						// Se llama a este método cuando se inserta texto en el campo de texto

						String texto = login.txtUsuario.getText();
						if (!texto.isEmpty()) {
							correo = true;
						}
					}
					@Override
					public void removeUpdate(DocumentEvent e) {
						// Se llama a este método cuando se elimina texto del campo de texto
					}
					@Override
					public void changedUpdate(DocumentEvent e) {
						// Este método no se utiliza en este ejemplo
					}
				});
				login.txtContrasena.getDocument().addDocumentListener(new DocumentListener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						// Se llama a este método cuando se inserta texto en el campo de texto
						String texto = login.txtContrasena.getText();
						if (!texto.equals("")) {
							// Se ha insertado una cadena en el campo de texto
							password = true;
						}
					}
					@Override
					public void removeUpdate(DocumentEvent e) {}

					@Override
					public void changedUpdate(DocumentEvent e) {}
				});
				if(correo && password) {
					Login.btnIniciarSesion.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(e.getSource()== login.btnIniciarSesion) {
								email = login.txtUsuario.getText();
								contrasena = login.txtContrasena.getText();
								presionadoInicio = true;
								botonpulsado = false;
							}
						}
					});
					if(presionadoInicio) {
						usuario = new Usuario(login.txtUsuario.getText(), login.txtContrasena.getText());
						objetoEnviarHilo = usuario;
					}
				}
				else {

					menu.btnCerrarSesion.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if(e.getSource()==menu.btnCerrarSesion) {
								//presionadoCerrar= true;
								opcion = 4;
								botonpulsado = false;
							}
						}
					});
					objetoEnviarHilo = opcionElegida(opcion);
				}
			}

			else {
				menu.txtCantIngresar.getDocument().addDocumentListener(new DocumentListener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						// Se llama a este método cuando se inserta texto en el campo de texto

						String texto = menu.txtCantIngresar.getText();
						cantidadIngresar=Double.parseDouble(menu.txtCantIngresar.getText()); 
						if (!texto.isEmpty()) {
							// Se ha insertado una cadena en el campo de texto
							cant = true;
						}
					}
					@Override
					public void removeUpdate(DocumentEvent e) {
						// Se llama a este método cuando se elimina texto del campo de texto
					}
					@Override
					public void changedUpdate(DocumentEvent e) {
						// Este método no se utiliza en este ejemplo
					}
				});
				menu.txtCantTransferir.getDocument().addDocumentListener(new DocumentListener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						// Se llama a este método cuando se inserta texto en el campo de texto

						String texto = menu.txtCantTransferir.getText();
						cantidadAtransferir =Double.parseDouble(menu.txtCantTransferir.getText());
						if (!texto.isEmpty()) {
							// Se ha insertado una cadena en el campo de texto
							transferir = true;
						}
					}
					@Override
					public void removeUpdate(DocumentEvent e) {
						// Se llama a este método cuando se elimina texto del campo de texto
					}
					@Override
					public void changedUpdate(DocumentEvent e) {
						// Este método no se utiliza en este ejemplo
					}
				});
				menu.txtNumCuenta.getDocument().addDocumentListener(new DocumentListener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						// Se llama a este método cuando se inserta texto en el campo de texto

						String texto = menu.txtNumCuenta.getText();
						numCuentaDestino =Integer.parseInt(menu.txtNumCuenta.getText());
						if (!texto.isEmpty()) {
							// Se ha insertado una cadena en el campo de texto
							cuentaNum = true;
						}
					}
					@Override
					public void removeUpdate(DocumentEvent e) {
						// Se llama a este método cuando se elimina texto del campo de texto
					}
					@Override
					public void changedUpdate(DocumentEvent e) {
						// Este método no se utiliza en este ejemplo
					}
				});


				if(cant) {
					menu.btnIngresarSaldo.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(e.getSource() == menu.btnIngresarSaldo) {
								opcion = 1;	
								botonpulsado = false;
							}
						}
					});
				}
				else if(transferir && cuentaNum) {
					menu.btnTransferirSaldo.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(e.getSource()==menu.btnTransferirSaldo) {
								opcion = 3;
								botonpulsado = false;
							}}});

				}
				else {
					menu.btnCerrarSesion.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if(e.getSource()==menu.btnCerrarSesion) {
								//presionadoCerrar= true;
								opcion = 4;
								botonpulsado = false;
							}
						}
					});
					menu.btnConsultarsaldo.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if(e.getSource()==menu.btnConsultarsaldo) {
								//presionadoCerrar= true;
								opcion = 2;
								botonpulsado = false;
							}
						}				
					});
				}
				objetoEnviarHilo = opcionElegida(opcion);
			}
		}
		return objetoEnviarHilo;
	}

	public static Object opcionElegida(int opcion) {
		Object objeto = null;
		Transferencia transferencia= null;
		Cuenta cuenta= null;
		if(opcion == 1) {
			numCuenta = cuentaGuardada.getNumCuenta();
			if(cantidadIngresar != 0 ) {
				cuenta = new Cuenta(numCuenta, cantidadIngresar);
				objeto = cuenta;
			}
		}
		else if(opcion == 2) {
			numCuenta = cuentaGuardada.getNumCuenta();
			cuenta = new Cuenta(numCuenta);
			objeto = cuenta;
		}
		else if(opcion == 3) {
			numCuenta = cuentaGuardada.getNumCuenta();
			Cuenta cuentaOrigen = new Cuenta(numCuenta);
			Cuenta cuentaDestino = new Cuenta(numCuentaDestino);
			transferencia = new Transferencia(cuentaOrigen, cuentaDestino,cantidadAtransferir);
			objeto= transferencia;
		}
		else if(opcion == 4) {

			objeto ="exit";
		}
		return objeto;
	}

	public static void sesionAbierta(Cuenta cuenta) {
		if(cuenta != null) {
			comprobar = true;
		}
	}

	public static void transferenciaRealizada(Transferencia transferencia) {
		Cuenta cuentaOrigen = null, cuentaDestino= null;
		double cantidad;
		if(transferencia != null) {
			System.out.println("Transferencia realizada con éxito.");
			cuentaOrigen= transferencia.getCuentaOrigen();
			cuentaDestino= transferencia.getCuentaDestino();
			cantidad= transferencia.getCantidadATransferir();
			System.out.println("Se ha realizado una transferencia de la cuenta # "
					+ cuentaOrigen.getNumCuenta()+ " a la cuenta # "
					+ cuentaDestino.getNumCuenta());
			cuentaGuardada= cuentaOrigen;
		}
		else {
			System.out.println("No se puede realizar la transferencia. Saldo insuficiente o cuenta destino incorrecta");
		}

	}
}
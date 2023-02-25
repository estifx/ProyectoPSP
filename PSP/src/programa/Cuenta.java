package programa;

import java.io.Serializable;

public class Cuenta implements Serializable {
	
	private int numCuenta;
	private double saldo;
	private Usuario usuario;
	
	public Cuenta(int numCuenta, double saldo, Usuario usuario) {
		this.numCuenta = numCuenta;
		this.saldo = saldo;
		this.usuario = usuario;
	}
	public Cuenta(int numCuenta, double saldo) {
		this.numCuenta = numCuenta;
		this.saldo = saldo;
	}

	public Cuenta(int numCuenta) {
		this.numCuenta = numCuenta;
	}
	@Override
	public String toString() {
		return "Cuenta \n [# de Cuenta:" + numCuenta + ", Saldo: " + saldo + ", Usuario:" 
	+ usuario.toString() + "]";
	}

	public int getNumCuenta() {
		return numCuenta;
	}

	public void setNumCuenta(int numCuenta) {
		this.numCuenta = numCuenta;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}

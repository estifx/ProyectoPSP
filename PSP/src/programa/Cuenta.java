package programa;

import java.io.Serializable;

public class Cuenta implements Serializable {
	
	private int numCuenta;
	private double saldo;
	private Usuario usuario;
	private MovimientosCuenta movCuenta;
	
	public Cuenta(int numCuenta, double saldo, Usuario usuario, MovimientosCuenta movCuenta) {
		this.numCuenta = numCuenta;
		this.saldo = saldo;
		this.usuario = usuario;
		this.movCuenta = movCuenta;
	}
	public Cuenta(int numCuenta, double saldo) {
		this.numCuenta = numCuenta;
		this.saldo = saldo;
	}

	@Override
	public String toString() {
		return "Cuenta \n [# de Cuenta:" + numCuenta + ", Saldo: " + saldo + ", Usuario:" 
	+ usuario.toString() + "\n Últimos movimientos en cuenta: \t\n "+ movCuenta.toString() +"]";
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

	public MovimientosCuenta getMovCuenta() {
		return movCuenta;
	}

	public void setMovCuenta(MovimientosCuenta movCuenta) {
		this.movCuenta = movCuenta;
	}

}


public class Cuenta {
	
	private int numCuenta;
	private double saldo;
	private Usuario usuario;
	
	public Cuenta(int numCuenta, double saldo, Usuario usuario) {
		this.numCuenta = numCuenta;
		this.saldo = saldo;
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Cuenta [numCuenta=" + numCuenta + ", saldo=" + saldo + ", usuario=" + usuario + "]";
	}
	
	
	

}

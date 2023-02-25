package programa;

public class Transferencia {
	private Usuario usuario;
	private int numCuenta;
	private Cuenta cuentaOrigen;
	private Cuenta cuentaDestino;
	private double cantidadATransferir;
	
	public Transferencia(Usuario usuario, int numCuenta, Cuenta cuentaOrigen, Cuenta cuentaDestino,
			double cantidadATransferir) {
		this.usuario = usuario;
		this.numCuenta = numCuenta;
		this.cuentaOrigen = cuentaOrigen;
		this.cuentaDestino = cuentaDestino;
		this.cantidadATransferir = cantidadATransferir;
	}
	
	public Transferencia(Cuenta cuentaOrigen, Cuenta cuentaDestino,double cantidadATransferir) {
		this.cuentaOrigen = cuentaOrigen;
		this.cuentaDestino = cuentaDestino;
		this.cantidadATransferir = cantidadATransferir;
	}
	public Transferencia(int numCuenta, double cantidadATransferir) {
		this.usuario = usuario;
		this.numCuenta = numCuenta;
		this.cantidadATransferir = cantidadATransferir;
	}
	
	
	@Override
	public String toString() {
		return "TransferenciaCuentas [usuario=" + usuario + ", numCuenta=" + numCuenta + ", cuentaOrigen="
				+ cuentaOrigen + ", cuentaDestino=" + cuentaDestino + ", cantidadATransferir=" + cantidadATransferir
				+ "]";
	}

	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Cuenta getCuentaOrigen() {
		return cuentaOrigen;
	}
	public void setCuentaOrigen(Cuenta cuentaOrigen) {
		this.cuentaOrigen = cuentaOrigen;
	}
	public Cuenta getCuentaDestino() {
		return cuentaDestino;
	}
	public void setCuentaDestino(Cuenta cuentaDestino) {
		this.cuentaDestino = cuentaDestino;
	}
	public double getCantidadATransferir() {
		return cantidadATransferir;
	}
	public void setCantidadATransferir(double cantidadATransferir) {
		this.cantidadATransferir = cantidadATransferir;
	}
	
	

}

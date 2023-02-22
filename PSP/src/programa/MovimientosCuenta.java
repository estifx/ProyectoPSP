package programa;

public class MovimientosCuenta {
	private Fecha fecha;
	private String concepto;
	private double importe;
	private Cuenta cuenta;
	
	
	public MovimientosCuenta(Fecha fecha, String concepto, double importe, Cuenta cuenta) {
		this.fecha = fecha;
		this.concepto = concepto;
		this.importe = importe;
		this.cuenta = cuenta;
	}


	@Override
	public String toString() {
		return "MovimientosCuenta [fecha=" + fecha + ", concepto=" + concepto + ", importe=" + importe + ", cuenta="
				+ cuenta + "]";
	}


}

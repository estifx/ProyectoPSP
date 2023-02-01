
import java.util.Random;


// Clase Fecha que contiene día, mes y año.
//@Embeddable otro tipo de clase que no necesita una clave primaria
//(clase embebida) para definir tipos compuestos(fecha, hora, tipos de datos compuestos)


public class Fecha implements Comparable<Fecha> {
	public static final String separador= "/";

	// atributos de una fecha
	private int dia;
	private int mes;
	private int agno;

	// Crea una fecha a partir de 3 parámetros.
	public Fecha(int dia, int mes, int agno) {
		this.dia = dia;
		this.mes = mes;
		this.agno = agno;
	}

	public Fecha(String fecha) {
		String []cadena = fecha.split(separador);
		this.dia = Integer.parseInt(cadena[0]);
		this.mes = Integer.parseInt(cadena[1]);
		this.agno = Integer.parseInt(cadena[2]);
	}
	// Crea una fecha por defecto de forma aleatoria.
	public Fecha() {
		Random aleatorio = new Random();
		this.agno = aleatorio.nextInt(2005-1995+1) + 1995; // entre 1995 y 2005
		this.mes = aleatorio.nextInt(12) + 1;              // entre 1 y 12
		int numeroDiasMes = obtenerNumeroDiasDeMes(this.mes, this.agno);
		this.dia = aleatorio.nextInt(numeroDiasMes) + 1;   // entre 1 y número de días de mes
	}

	// Devuelve verdadero si un año es bisiesto.
	// Devuelve falso en caso contrario.
	// Un año es bisiesto si cumple dos condiciones:
	// - El año es divisible entre 4.
	// - El año no es divisible entre 100 o el año es divisible entre 400.
	private static boolean esBisiesto(int agno) {
		boolean bisiesto = false;
		if (agno >= 1583) {
			if ((agno % 4 == 0) && (agno % 100 != 0 || agno % 400 == 0)) {
				bisiesto = true;
			}
		}
		return bisiesto;
	}

	// Devuelve el número de días que tiene un mes de un año:
	// - Febrero tiene 28 días si el año no es bisiesto.
	// - Febrero tiene 29 días si el año es bisiesto.
	// - Abril, Junio, Septiembre y Noviembre tienen 30 días.
	// - Enero, Marzo, Mayo, Julio, Agosto, Octubre y Diciembre tienen 31 días.
	private static int obtenerNumeroDiasDeMes(int mes, int agno) {
		int numeroDiasMes = 31;
		if (mes == 2) {
			if (esBisiesto(agno)) {
				numeroDiasMes = 29;
			} 
			else {
				numeroDiasMes = 28;
			}
		}
		else if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
			numeroDiasMes = 30;
		}
		return numeroDiasMes;
	}

	// Valida los datos de una fecha según las condiciones:
	// - Se sigue el calendario gregoriano, que se basa en años bisiestos y años no bisiestos.
	// - El año debe estar comprendido entre 1583 y 2999.
	// - El mes debe estar comprendido entre 1 y 12.
	// - El día debe estar comprendido entre 1 y el número de días que tiene el mes del año.
	// Devuelve verdadero si los datos de una fecha son válidos.
	// Devuelve falso en caso contrario.
	public static boolean esValida(int dia, int mes, int agno) {
		boolean fechaValida = true;
		if (agno < 1583 || agno > 2999) {
			fechaValida = false;
		}
		else if (mes < 1 || mes > 12) {
			fechaValida = false;
		}
		else if (dia < 1 || dia > obtenerNumeroDiasDeMes(mes, agno)) {
			fechaValida = false;
		}
		return fechaValida;
	}

	// Devuelve una cadena de caracteres con el estado de la fecha.
	// Utiliza el formato "DD/MM/AAAA".
	@Override
	public String toString() {
		return 
			String.format("%02d/%02d/%04d", this.dia, this.mes, this.agno);
	}

	// Compara esta fecha con otra fecha 
	// por año ascendente, mes ascendente y día ascendente.
	@Override
	public int compareTo(Fecha otra) {
		String estaFecha = String.format("%04d%02d%02d", 
		                                 this.agno, this.mes, this.dia);
		String otraFecha = String.format("%04d%02d%02d", 
		                                 otra.agno, otra.mes, otra.dia);
		return estaFecha.compareTo(otraFecha);
	}

	
}


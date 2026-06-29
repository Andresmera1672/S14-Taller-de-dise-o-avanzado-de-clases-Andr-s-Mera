package club;

/**
 * Clase que modela una factura en el club.
 */
public class Factura {

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /** Descripcion del consumo que genero esta factura. */
    private String concepto;

    /** Valor del consumo que genero la factura. */
    private double valor;

    /** Nombre de la persona que hizo el consumo que genero la factura. */
    private String nombre;

    // -----------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------

    /**
     * Construye un objeto factura asociado a un consumo de un socio o de un autorizado.
     * <b>post:</b> Se inicializaron los atributos con los valores dados.
     * @param pNombre Nombre de la persona que hizo el consumo. pNombre != null && pNombre != "".
     * @param pConcepto Concepto del consumo. pConcepto != null && pConcepto != "".
     * @param pValor Valor del consumo. pValor > 0.
     */
    public Factura(String pNombre, String pConcepto, double pValor) {
        nombre = pNombre;
        concepto = pConcepto;
        valor = pValor;
    }

    // -----------------------------------------------------------------
    // Metodos
    // -----------------------------------------------------------------

    /**
     * Retorna el concepto de la factura.
     * @return El concepto de la factura.
     */
    public String darConcepto() {
        return concepto;
    }

    /**
     * Retorna el valor de la factura.
     * @return El valor de la factura.
     */
    public double darValor() {
        return valor;
    }

    /**
     * Retorna el nombre que realizo el consumo que genero la factura.
     * @return El nombre en la factura.
     */
    public String darNombre() {
        return nombre;
    }

    /**
     * Retorna una cadena que representa a la factura.
     * @return Cadena con formato: concepto $valor (nombre).
     */
    @Override
    public String toString() {
        return concepto + "    $" + valor + "    (" + nombre + ")";
    }
}

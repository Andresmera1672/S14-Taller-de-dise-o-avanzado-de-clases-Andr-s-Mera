package excepciones;

/**
 * Excepcion lanzada cuando se intenta eliminar un autorizado que tiene una factura pendiente de pago.
 */
public class AutorizadoConFacturaPendienteException extends Exception {

    /**
     * Crea una excepcion indicando que el autorizado tiene facturas pendientes.
     * @param nombre Nombre del autorizado con factura pendiente.
     */
    public AutorizadoConFacturaPendienteException(String nombre) {
        super("El autorizado '" + nombre + "' tiene una factura pendiente de pago y no puede ser eliminado");
    }
}

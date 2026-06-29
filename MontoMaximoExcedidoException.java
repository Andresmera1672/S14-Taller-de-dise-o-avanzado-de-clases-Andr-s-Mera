package excepciones;

/**
 * Excepcion lanzada cuando el monto a agregar supera el maximo de fondos permitido para el tipo de suscripcion.
 */
public class MontoMaximoExcedidoException extends Exception {

    /**
     * Crea una excepcion indicando el limite de fondos superado.
     * @param montoMaximo Monto maximo segun el tipo de suscripcion.
     * @param tipo Tipo de suscripcion del socio.
     */
    public MontoMaximoExcedidoException(double montoMaximo, String tipo) {
        super("El monto excede el limite maximo para socios " + tipo + ". Maximo: $" + montoMaximo);
    }
}

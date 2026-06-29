package excepciones;

/**
 * Excepcion lanzada cuando el indice de factura indicado no existe en la lista del socio.
 */
public class FacturaNoExisteException extends Exception {

    /**
     * Crea una excepcion indicando que el indice de factura es invalido.
     * @param indice Indice que no existe.
     */
    public FacturaNoExisteException(int indice) {
        super("No existe una factura en el indice: " + indice);
    }
}

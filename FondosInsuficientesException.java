package excepciones;

/**
 * Excepcion lanzada cuando el socio no cuenta con fondos suficientes para realizar una operacion.
 */
public class FondosInsuficientesException extends Exception {

    /**
     * Crea una excepcion indicando que los fondos son insuficientes.
     * @param fondosActuales Fondos actuales del socio.
     * @param valorRequerido Valor requerido para la operacion.
     */
    public FondosInsuficientesException(double fondosActuales, double valorRequerido) {
        super("Fondos insuficientes. Disponibles: $" + fondosActuales + ", requeridos: $" + valorRequerido);
    }
}

package excepciones;

/**
 * Excepcion lanzada cuando se intenta agregar al propio socio como autorizado.
 */
public class AutorizadoNoEsSocioException extends Exception {

    /**
     * Crea una excepcion indicando que el socio no puede autorizarse a si mismo.
     * @param nombre Nombre del socio.
     */
    public AutorizadoNoEsSocioException(String nombre) {
        super("El socio '" + nombre + "' no puede agregarse a si mismo como autorizado");
    }
}

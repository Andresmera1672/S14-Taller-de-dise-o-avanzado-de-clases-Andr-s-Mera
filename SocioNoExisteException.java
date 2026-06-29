package excepciones;

/**
 * Excepcion lanzada cuando no se encuentra un socio con la cedula dada.
 */
public class SocioNoExisteException extends Exception {

    /**
     * Crea una excepcion con el mensaje indicado.
     * @param cedula Cedula del socio que no fue encontrado.
     */
    public SocioNoExisteException(String cedula) {
        super("No existe un socio con la cedula: " + cedula);
    }
}

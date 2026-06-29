package excepciones;

/**
 * Excepcion lanzada cuando se intenta afiliar un socio con una cedula ya registrada.
 */
public class SocioYaExisteException extends Exception {

    /**
     * Crea una excepcion con el mensaje indicado.
     * @param cedula Cedula del socio duplicado.
     */
    public SocioYaExisteException(String cedula) {
        super("Ya existe un socio con la cedula: " + cedula);
    }
}

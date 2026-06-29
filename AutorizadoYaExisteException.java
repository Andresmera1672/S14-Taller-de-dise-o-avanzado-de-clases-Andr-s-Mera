package excepciones;

/**
 * Excepcion lanzada cuando se intenta agregar un autorizado que ya existe en la lista del socio.
 */
public class AutorizadoYaExisteException extends Exception {

    /**
     * Crea una excepcion con el nombre del autorizado duplicado.
     * @param nombre Nombre del autorizado que ya existe.
     */
    public AutorizadoYaExisteException(String nombre) {
        super("El autorizado '" + nombre + "' ya esta registrado en la lista del socio");
    }
}

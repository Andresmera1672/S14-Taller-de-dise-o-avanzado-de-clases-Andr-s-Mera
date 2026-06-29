package excepciones;

/**
 * Excepcion lanzada cuando un socio no puede ser eliminado del club.
 */
public class SocioNoEliminableException extends Exception {

    /**
     * Crea una excepcion con el motivo especifico por el cual el socio no puede eliminarse.
     * @param motivo Descripcion del motivo de rechazo.
     */
    public SocioNoEliminableException(String motivo) {
        super("El socio no puede ser eliminado: " + motivo);
    }
}

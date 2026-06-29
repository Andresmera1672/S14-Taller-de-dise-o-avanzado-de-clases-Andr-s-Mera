package excepciones;

/**
 * Excepcion lanzada cuando se intenta afiliar un socio VIP y ya se alcanzo el maximo permitido.
 */
public class MaximoVIPAlcanzadoException extends Exception {

    /**
     * Crea una excepcion indicando que el cupo VIP esta lleno.
     * @param maximo Cantidad maxima de socios VIP permitida.
     */
    public MaximoVIPAlcanzadoException(int maximo) {
        super("El club no acepta mas socios VIP. Maximo permitido: " + maximo);
    }
}

package club;

import java.util.ArrayList;
import club.Socio.Tipo;
import excepciones.AutorizadoConFacturaPendienteException;
import excepciones.AutorizadoNoEsSocioException;
import excepciones.AutorizadoYaExisteException;
import excepciones.FacturaNoExisteException;
import excepciones.FondosInsuficientesException;
import excepciones.MaximoVIPAlcanzadoException;
import excepciones.MontoMaximoExcedidoException;
import excepciones.SocioNoEliminableException;
import excepciones.SocioNoExisteException;
import excepciones.SocioYaExisteException;

/**
 * Clase que modela un club social.
 */
public class Club {

    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /** Cantidad maxima de socios VIP que acepta el club. */
    public static final int MAXIMO_VIP = 3;

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /** Lista de socios del club. */
    private ArrayList<Socio> socios;

    // -----------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------

    /**
     * Crea un club con la lista de socios vacia.
     * <b>post:</b> Se inicializo la lista de socios.
     */
    public Club() {
        socios = new ArrayList<>();
    }

    // -----------------------------------------------------------------
    // Metodos de consulta
    // -----------------------------------------------------------------

    /**
     * Retorna la lista de socios afiliados al club.
     * @return Lista de socios.
     */
    public ArrayList<Socio> darSocios() {
        return socios;
    }

    /**
     * Busca y retorna el socio con la cedula dada.
     * @param pCedulaSocio Cedula del socio buscado. pCedulaSocio != null && pCedulaSocio != "".
     * @return El socio encontrado, o null si no existe.
     */
    public Socio buscarSocio(String pCedulaSocio) {
        for (Socio socio : socios) {
            if (socio.darCedula().equals(pCedulaSocio)) {
                return socio;
            }
        }
        return null;
    }

    /**
     * Cuenta la cantidad de socios VIP actualmente en el club.
     * @return Numero de socios VIP.
     */
    public int contarSociosVIP() {
        int conteo = 0;
        for (Socio socio : socios) {
            if (socio.darTipo() == Tipo.VIP) {
                conteo++;
            }
        }
        return conteo;
    }

    /**
     * Retorna la lista de autorizados del socio con la cedula dada, incluyendo al propio socio.
     * <b>pre:</b> El socio con la cedula dada existe.
     * @param pCedulaSocio Cedula del socio. pCedulaSocio != null && pCedulaSocio != "".
     * @return Lista con el nombre del socio y sus autorizados.
     * @throws SocioNoExisteException Si no existe un socio con la cedula dada.
     */
    public ArrayList<String> darAutorizadosSocio(String pCedulaSocio) throws SocioNoExisteException {
        Socio socio = obtenerSocioOLanzarExcepcion(pCedulaSocio);
        ArrayList<String> autorizados = new ArrayList<>();
        autorizados.add(socio.darNombre());
        autorizados.addAll(socio.darAutorizados());
        return autorizados;
    }

    /**
     * Retorna la lista de facturas del socio con la cedula dada.
     * <b>pre:</b> El socio con la cedula dada existe.
     * @param pCedulaSocio Cedula del socio. pCedulaSocio != null && pCedulaSocio != "".
     * @return Lista de facturas del socio.
     * @throws SocioNoExisteException Si no existe un socio con la cedula dada.
     */
    public ArrayList<Factura> darFacturasSocio(String pCedulaSocio) throws SocioNoExisteException {
        return obtenerSocioOLanzarExcepcion(pCedulaSocio).darFacturas();
    }

    // -----------------------------------------------------------------
    // Metodos de modificacion
    // -----------------------------------------------------------------

    /**
     * Afilia un nuevo socio al club.
     * <b>post:</b> Se agrego un nuevo socio con los datos dados si las validaciones son exitosas.
     * @param pCedula Cedula del socio a afiliar. pCedula != null && pCedula != "".
     * @param pNombre Nombre del socio a afiliar. pNombre != null && pNombre != "".
     * @param pTipo Tipo de suscripcion del socio. pTipo != null.
     * @throws SocioYaExisteException Si ya existe un socio con la misma cedula.
     * @throws MaximoVIPAlcanzadoException Si se intenta afiliar un socio VIP y ya se alcanzo el maximo.
     */
    public void afiliarSocio(String pCedula, String pNombre, Tipo pTipo)
            throws SocioYaExisteException, MaximoVIPAlcanzadoException {
        if (buscarSocio(pCedula) != null) {
            throw new SocioYaExisteException(pCedula);
        }
        if (pTipo == Tipo.VIP && contarSociosVIP() == MAXIMO_VIP) {
            throw new MaximoVIPAlcanzadoException(MAXIMO_VIP);
        }
        socios.add(new Socio(pCedula, pNombre, pTipo));
    }

    /**
     * Agrega una persona autorizada al socio con la cedula dada.
     * <b>pre:</b> El socio con la cedula dada existe.
     * <b>post:</b> Se agrego el autorizado si las condiciones son validas.
     * @param pCedulaSocio Cedula del socio. pCedulaSocio != null && pCedulaSocio != "".
     * @param pNombreAutorizado Nombre del autorizado a agregar. pNombreAutorizado != null.
     * @throws SocioNoExisteException Si no existe un socio con la cedula dada.
     * @throws AutorizadoNoEsSocioException Si se intenta agregar al propio socio como autorizado.
     * @throws FondosInsuficientesException Si el socio no tiene fondos para financiar el autorizado.
     * @throws AutorizadoYaExisteException Si el autorizado ya esta registrado.
     */
    public void agregarAutorizadoSocio(String pCedulaSocio, String pNombreAutorizado)
            throws SocioNoExisteException, AutorizadoNoEsSocioException,
                   FondosInsuficientesException, AutorizadoYaExisteException {
        Socio socio = obtenerSocioOLanzarExcepcion(pCedulaSocio);
        socio.agregarAutorizado(pNombreAutorizado);
    }

    /**
     * Elimina la persona autorizada con el nombre dado del socio con la cedula dada.
     * @param pCedulaSocio Cedula del socio. pCedulaSocio != null && pCedulaSocio != "".
     * @param pNombreAutorizado Nombre del autorizado a eliminar. pNombreAutorizado != null.
     * @throws SocioNoExisteException Si no existe un socio con la cedula dada.
     * @throws AutorizadoConFacturaPendienteException Si el autorizado tiene facturas pendientes.
     */
    public void eliminarAutorizadoSocio(String pCedulaSocio, String pNombreAutorizado)
            throws SocioNoExisteException, AutorizadoConFacturaPendienteException {
        Socio socio = obtenerSocioOLanzarExcepcion(pCedulaSocio);
        socio.eliminarAutorizado(pNombreAutorizado);
    }

    /**
     * Registra un consumo a nombre del socio o uno de sus autorizados.
     * <b>post:</b> Se agrego una nueva factura a la lista del socio.
     * @param pCedulaSocio Cedula del socio. pCedulaSocio != null && pCedulaSocio != "".
     * @param pNombreCliente Nombre de quien realiza el consumo. pNombreCliente != null.
     * @param pConcepto Descripcion del consumo. pConcepto != null && pConcepto != "".
     * @param pValor Valor del consumo. pValor >= 0.
     * @throws SocioNoExisteException Si no existe un socio con la cedula dada.
     * @throws FondosInsuficientesException Si el socio no tiene fondos para cubrir el consumo.
     */
    public void registrarConsumo(String pCedulaSocio, String pNombreCliente,
                                  String pConcepto, double pValor)
            throws SocioNoExisteException, FondosInsuficientesException {
        Socio socio = obtenerSocioOLanzarExcepcion(pCedulaSocio);
        socio.registrarConsumo(pNombreCliente, pConcepto, pValor);
    }

    /**
     * Realiza el pago de una factura del socio con la cedula dada.
     * <b>post:</b> Se borro la factura de la lista y se descontaron los fondos.
     * @param pCedulaSocio Cedula del socio. pCedulaSocio != null && pCedulaSocio != "".
     * @param pFacturaIndice Indice de la factura a pagar. pFacturaIndice >= 0.
     * @throws SocioNoExisteException Si no existe un socio con la cedula dada.
     * @throws FacturaNoExisteException Si el indice no corresponde a ninguna factura.
     * @throws FondosInsuficientesException Si el socio no tiene fondos para pagar la factura.
     */
    public void pagarFacturaSocio(String pCedulaSocio, int pFacturaIndice)
            throws SocioNoExisteException, FacturaNoExisteException, FondosInsuficientesException {
        Socio socio = obtenerSocioOLanzarExcepcion(pCedulaSocio);
        socio.pagarFactura(pFacturaIndice);
    }

    /**
     * Aumenta los fondos del socio con la cedula dada.
     * <b>post:</b> Los fondos del socio aumentaron si el monto es valido.
     * @param pCedulaSocio Cedula del socio. pCedulaSocio != null && pCedulaSocio != "".
     * @param pValor Valor a agregar. pValor >= 0.
     * @throws SocioNoExisteException Si no existe un socio con la cedula dada.
     * @throws MontoMaximoExcedidoException Si el monto supera el limite permitido para el tipo de socio.
     */
    public void aumentarFondosSocio(String pCedulaSocio, double pValor)
            throws SocioNoExisteException, MontoMaximoExcedidoException {
        Socio socio = obtenerSocioOLanzarExcepcion(pCedulaSocio);
        socio.aumentarFondos(pValor);
    }

    // -----------------------------------------------------------------
    // Metodos de extension (nuevos, requeridos por el enunciado)
    // -----------------------------------------------------------------

    /**
     * Calcula el total de consumos (facturas pendientes) del socio con la cedula dada.
     * Nota: retorna 0 cuando el socio no tiene consumos.
     * @param pCedula Cedula del socio. pCedula != null && pCedula != "".
     * @return Total de consumos del socio (suma de valores de facturas pendientes).
     * @throws SocioNoExisteException Si no existe un socio con la cedula dada.
     */
    public double totalConsumosSocio(String pCedula) throws SocioNoExisteException {
        Socio socio = obtenerSocioOLanzarExcepcion(pCedula);
        double total = 0;
        for (Factura factura : socio.darFacturas()) {
            total += factura.darValor();
        }
        return total;
    }

    /**
     * Evalua si el socio con la cedula dada puede ser eliminado del club.
     * Un socio puede eliminarse solo si:
     * - Existe en el club.
     * - No es de tipo VIP.
     * - No tiene facturas pendientes de pago.
     * - No tiene mas de un autorizado.
     *
     * @param pCedula Cedula del socio. pCedula != null && pCedula != "".
     * @return true si el socio puede ser eliminado.
     * @throws SocioNoExisteException Si no existe un socio con la cedula dada.
     * @throws SocioNoEliminableException Si el socio no puede ser eliminado (con el motivo especifico).
     */
    public boolean sePuedeEliminarSocio(String pCedula)
            throws SocioNoExisteException, SocioNoEliminableException {
        Socio socio = obtenerSocioOLanzarExcepcion(pCedula);

        if (socio.darTipo() == Tipo.VIP) {
            throw new SocioNoEliminableException("los socios VIP no pueden ser eliminados");
        }
        if (!socio.darFacturas().isEmpty()) {
            throw new SocioNoEliminableException("el socio tiene " + socio.darFacturas().size()
                    + " factura(s) pendiente(s) de pago");
        }
        if (socio.darAutorizados().size() > 1) {
            throw new SocioNoEliminableException("el socio tiene mas de un autorizado ("
                    + socio.darAutorizados().size() + ")");
        }
        return true;
    }

    // -----------------------------------------------------------------
    // Metodo auxiliar privado
    // -----------------------------------------------------------------

    /**
     * Busca el socio con la cedula dada y lanza una excepcion si no existe.
     * @param pCedula Cedula del socio. pCedula != null && pCedula != "".
     * @return El socio encontrado.
     * @throws SocioNoExisteException Si no existe un socio con la cedula dada.
     */
    private Socio obtenerSocioOLanzarExcepcion(String pCedula) throws SocioNoExisteException {
        Socio socio = buscarSocio(pCedula);
        if (socio == null) {
            throw new SocioNoExisteException(pCedula);
        }
        return socio;
    }
}

package club;

import java.util.ArrayList;
import excepciones.AutorizadoConFacturaPendienteException;
import excepciones.AutorizadoNoEsSocioException;
import excepciones.AutorizadoYaExisteException;
import excepciones.FacturaNoExisteException;
import excepciones.FondosInsuficientesException;
import excepciones.MontoMaximoExcedidoException;

/**
 * Clase que modela un socio del club.
 */
public class Socio {

    // -----------------------------------------------------------------
    // Enumeraciones
    // -----------------------------------------------------------------

    /**
     * Tipos de suscripcion disponibles en el club.
     */
    public enum Tipo {
        /** Suscripcion VIP. */
        VIP,
        /** Suscripcion Regular. */
        REGULAR
    }

    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /** Fondos iniciales para socios regulares. */
    public static final double FONDOS_INICIALES_REGULARES = 50;

    /** Fondos iniciales para socios VIP. */
    public static final double FONDOS_INICIALES_VIP = 100;

    /** Monto maximo de fondos para socios regulares. */
    public static final double MONTO_MAXIMO_REGULARES = 1000;

    /** Monto maximo de fondos para socios VIP. */
    public static final double MONTO_MAXIMO_VIP = 5000;

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /** Cedula del socio. */
    private String cedula;

    /** Nombre del socio. */
    private String nombre;

    /** Dinero disponible del socio. */
    private double fondos;

    /** Tipo de suscripcion del socio. */
    private Tipo tipoSubscripcion;

    /** Facturas pendientes de pago del socio. */
    private ArrayList<Factura> facturas;

    /** Nombres de personas autorizadas por el socio. */
    private ArrayList<String> autorizados;

    // -----------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------

    /**
     * Crea un socio del club.
     * <b>post:</b> Se inicializan los atributos, facturas y autorizados.
     * Los fondos se asignan segun el tipo de suscripcion.
     * @param pCedula Cedula del socio. pCedula != null && pCedula != "".
     * @param pNombre Nombre del socio. pNombre != null && pNombre != "".
     * @param pTipo Tipo de suscripcion. pTipo pertenece {Tipo.VIP, Tipo.REGULAR}.
     */
    public Socio(String pCedula, String pNombre, Tipo pTipo) {
        cedula = pCedula;
        nombre = pNombre;
        tipoSubscripcion = pTipo;

        fondos = (tipoSubscripcion == Tipo.VIP)
                ? FONDOS_INICIALES_VIP
                : FONDOS_INICIALES_REGULARES;

        facturas = new ArrayList<>();
        autorizados = new ArrayList<>();
    }

    // -----------------------------------------------------------------
    // Metodos de consulta
    // -----------------------------------------------------------------

    /**
     * Retorna el nombre del socio.
     * @return Nombre del socio.
     */
    public String darNombre() {
        return nombre;
    }

    /**
     * Retorna la cedula del socio.
     * @return Cedula del socio.
     */
    public String darCedula() {
        return cedula;
    }

    /**
     * Retorna los fondos disponibles del socio.
     * @return Fondos actuales del socio.
     */
    public double darFondos() {
        return fondos;
    }

    /**
     * Retorna el tipo de suscripcion del socio.
     * @return Tipo de suscripcion.
     */
    public Tipo darTipo() {
        return tipoSubscripcion;
    }

    /**
     * Retorna la lista de facturas pendientes de pago.
     * @return Lista de facturas del socio.
     */
    public ArrayList<Factura> darFacturas() {
        return facturas;
    }

    /**
     * Retorna la lista de autorizados del socio.
     * @return Lista de nombres de autorizados.
     */
    public ArrayList<String> darAutorizados() {
        return autorizados;
    }

    // -----------------------------------------------------------------
    // Metodos privados auxiliares
    // -----------------------------------------------------------------

    /**
     * Indica si ya existe un autorizado con el nombre dado.
     * @param pNombreAutorizado Nombre a verificar. pNombreAutorizado != null.
     * @return true si el autorizado ya existe, false en caso contrario.
     */
    private boolean existeAutorizado(String pNombreAutorizado) {
        for (String autorizado : autorizados) {
            if (autorizado.equals(pNombreAutorizado)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Indica si un autorizado tiene alguna factura pendiente a su nombre.
     * @param pNombreAutorizado Nombre del autorizado a verificar. pNombreAutorizado != null.
     * @return true si tiene facturas pendientes, false en caso contrario.
     */
    private boolean tieneFacturaAsociada(String pNombreAutorizado) {
        for (Factura factura : facturas) {
            if (factura.darNombre().equals(pNombreAutorizado)) {
                return true;
            }
        }
        return false;
    }

    // -----------------------------------------------------------------
    // Metodos de modificacion
    // -----------------------------------------------------------------

    /**
     * Aumenta los fondos disponibles del socio en el monto dado.
     * <b>post:</b> Los fondos del socio aumentaron si el monto es valido.
     * @param pFondos Valor a agregar. pFondos > 0.
     * @throws MontoMaximoExcedidoException Si al agregar el monto se supera el limite permitido.
     */
    public void aumentarFondos(double pFondos) throws MontoMaximoExcedidoException {
        double montoMaximo = (tipoSubscripcion == Tipo.VIP)
                ? MONTO_MAXIMO_VIP
                : MONTO_MAXIMO_REGULARES;
        String tipoNombre = (tipoSubscripcion == Tipo.VIP) ? "VIP" : "Regular";

        if (pFondos + fondos > montoMaximo) {
            throw new MontoMaximoExcedidoException(montoMaximo, tipoNombre);
        }

        fondos += pFondos;
    }

    /**
     * Registra un nuevo consumo para el socio o uno de sus autorizados.
     * <b>pre:</b> El nombre pertenece al socio o a la lista de autorizados.
     * <b>post:</b> Se agrego una nueva factura a la lista del socio.
     * @param pNombre Nombre de quien realiza el consumo. pNombre != null && pNombre != "".
     * @param pConcepto Descripcion del consumo. pConcepto != null && pConcepto != "".
     * @param pValor Valor del consumo. pValor >= 0.
     * @throws FondosInsuficientesException Si el socio no tiene fondos para cubrir el consumo.
     */
    public void registrarConsumo(String pNombre, String pConcepto, double pValor)
            throws FondosInsuficientesException {
        if (pValor > fondos) {
            throw new FondosInsuficientesException(fondos, pValor);
        }
        Factura nuevaFactura = new Factura(pNombre, pConcepto, pValor);
        facturas.add(nuevaFactura);
    }

    /**
     * Agrega una nueva persona autorizada al socio.
     * <b>pre:</b> La lista de autorizados ha sido inicializada.
     * <b>post:</b> Se agrego un nuevo autorizado si las condiciones son validas.
     * @param pNombreAutorizado Nombre de la persona a autorizar. pNombreAutorizado != null.
     * @throws AutorizadoNoEsSocioException Si se intenta agregar al propio socio como autorizado.
     * @throws FondosInsuficientesException Si el socio no tiene fondos para financiar un autorizado.
     * @throws AutorizadoYaExisteException Si el autorizado ya existe en la lista.
     */
    public void agregarAutorizado(String pNombreAutorizado)
            throws AutorizadoNoEsSocioException, FondosInsuficientesException, AutorizadoYaExisteException {
        if (pNombreAutorizado.equals(nombre)) {
            throw new AutorizadoNoEsSocioException(nombre);
        }
        if (fondos == 0) {
            throw new FondosInsuficientesException(fondos, 1);
        }
        if (existeAutorizado(pNombreAutorizado)) {
            throw new AutorizadoYaExisteException(pNombreAutorizado);
        }
        autorizados.add(pNombreAutorizado);
    }

    /**
     * Elimina el autorizado del socio con el nombre dado.
     * <b>pre:</b> La lista de autorizados ha sido inicializada.
     * <b>post:</b> Se elimino el autorizado si las condiciones son validas.
     * @param pNombreAutorizado Nombre del autorizado a eliminar. pNombreAutorizado != null.
     * @throws AutorizadoConFacturaPendienteException Si el autorizado tiene facturas pendientes.
     */
    public void eliminarAutorizado(String pNombreAutorizado)
            throws AutorizadoConFacturaPendienteException {
        if (tieneFacturaAsociada(pNombreAutorizado)) {
            throw new AutorizadoConFacturaPendienteException(pNombreAutorizado);
        }
        autorizados.removeIf(a -> a.equals(pNombreAutorizado));
    }

    /**
     * Paga la factura en el indice dado y la elimina de la lista.
     * <b>pre:</b> La lista de facturas ha sido inicializada.
     * <b>post:</b> Se borro la factura de la lista y se descontaron los fondos.
     * @param pIndiceFactura Posicion de la factura a pagar. pIndiceFactura >= 0.
     * @throws FacturaNoExisteException Si el indice dado no corresponde a ninguna factura.
     * @throws FondosInsuficientesException Si el socio no tiene fondos suficientes para pagar.
     */
    public void pagarFactura(int pIndiceFactura)
            throws FacturaNoExisteException, FondosInsuficientesException {
        if (pIndiceFactura < 0 || pIndiceFactura >= facturas.size()) {
            throw new FacturaNoExisteException(pIndiceFactura);
        }
        Factura factura = facturas.get(pIndiceFactura);
        if (factura.darValor() > fondos) {
            throw new FondosInsuficientesException(fondos, factura.darValor());
        }
        fondos -= factura.darValor();
        facturas.remove(pIndiceFactura);
    }

    /**
     * Retorna la cadena que representa al socio.
     * @return Cadena con formato: cedula - nombre.
     */
    @Override
    public String toString() {
        return cedula + " - " + nombre;
    }
}

package club;

import java.util.ArrayList;
import java.util.Scanner;
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
 * Clase principal de la aplicacion del club social.
 * Gestiona el menu de interaccion con el usuario y maneja las excepciones del negocio.
 */
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Club club = new Club();
        int opcion;

        do {
            mostrarMenu();
            System.out.print("Ingrese una opcion: ");
            opcion = leerEntero(sc);

            switch (opcion) {
                case 1: afiliarSocio(club, sc); break;
                case 2: agregarAutorizado(club, sc); break;
                case 3: pagarFactura(club, sc); break;
                case 4: registrarConsumo(club, sc); break;
                case 5: aumentarFondos(club, sc); break;
                case 6: totalConsumosMenu(club, sc); break;
                case 7: sePuedeEliminarMenu(club, sc); break;
                case 8: System.out.println("Hasta luego."); break;
                default: System.out.println("Opcion invalida. Intente de nuevo.");
            }

        } while (opcion != 8);

        sc.close();
    }

    // -----------------------------------------------------------------
    // Metodos del menu
    // -----------------------------------------------------------------

    private static void mostrarMenu() {
        System.out.println("\n===== CLUB SOCIAL =====");
        System.out.println("1. Afiliar un socio al club");
        System.out.println("2. Registrar persona autorizada por un socio");
        System.out.println("3. Pagar una factura");
        System.out.println("4. Registrar un consumo en la cuenta de un socio");
        System.out.println("5. Aumentar fondos de la cuenta de un socio");
        System.out.println("6. Consultar total de consumos de un socio");
        System.out.println("7. Verificar si un socio puede ser eliminado");
        System.out.println("8. Salir");
        System.out.println("=======================");
    }

    private static void afiliarSocio(Club club, Scanner sc) {
        System.out.print("Cedula: ");
        String cedula = sc.next();
        System.out.print("Nombre: ");
        String nombre = sc.next();
        System.out.print("Tipo (VIP / REGULAR): ");
        String tipoStr = sc.next().toUpperCase();

        Tipo tipo;
        try {
            tipo = Tipo.valueOf(tipoStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Tipo de suscripcion invalido. Use VIP o REGULAR.");
            return;
        }

        try {
            club.afiliarSocio(cedula, nombre, tipo);
            System.out.println("Socio afiliado exitosamente.");
        } catch (SocioYaExisteException | MaximoVIPAlcanzadoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void agregarAutorizado(Club club, Scanner sc) {
        System.out.print("Cedula del socio: ");
        String cedula = sc.next();
        System.out.print("Nombre del autorizado: ");
        String nombreAutorizado = sc.next();

        try {
            club.agregarAutorizadoSocio(cedula, nombreAutorizado);
            System.out.println("Autorizado agregado exitosamente.");
        } catch (SocioNoExisteException | AutorizadoNoEsSocioException
                 | FondosInsuficientesException | AutorizadoYaExisteException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void pagarFactura(Club club, Scanner sc) {
        System.out.print("Cedula del socio: ");
        String cedula = sc.next();

        try {
            ArrayList<Factura> facturas = club.darFacturasSocio(cedula);
            if (facturas.isEmpty()) {
                System.out.println("El socio no tiene facturas pendientes.");
                return;
            }
            System.out.println("Facturas pendientes:");
            for (int i = 0; i < facturas.size(); i++) {
                System.out.println("  [" + i + "] " + facturas.get(i));
            }
            System.out.print("Indice de factura a pagar: ");
            int indice = leerEntero(sc);
            club.pagarFacturaSocio(cedula, indice);
            System.out.println("Factura pagada exitosamente.");
        } catch (SocioNoExisteException | FacturaNoExisteException | FondosInsuficientesException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void registrarConsumo(Club club, Scanner sc) {
        System.out.print("Cedula del socio: ");
        String cedula = sc.next();
        System.out.print("Nombre de quien consume: ");
        String nombreCliente = sc.next();
        System.out.print("Concepto del consumo: ");
        String concepto = sc.next();
        System.out.print("Valor del consumo: ");
        double valor = leerDouble(sc);

        try {
            club.registrarConsumo(cedula, nombreCliente, concepto, valor);
            System.out.println("Consumo registrado exitosamente.");
        } catch (SocioNoExisteException | FondosInsuficientesException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void aumentarFondos(Club club, Scanner sc) {
        System.out.print("Cedula del socio: ");
        String cedula = sc.next();
        System.out.print("Monto a agregar: ");
        double monto = leerDouble(sc);

        try {
            club.aumentarFondosSocio(cedula, monto);
            System.out.println("Fondos actualizados exitosamente.");
        } catch (SocioNoExisteException | MontoMaximoExcedidoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void totalConsumosMenu(Club club, Scanner sc) {
        System.out.print("Cedula del socio: ");
        String cedula = sc.next();

        try {
            double total = club.totalConsumosSocio(cedula);
            System.out.println("Total de consumos del socio: $" + total);
        } catch (SocioNoExisteException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void sePuedeEliminarMenu(Club club, Scanner sc) {
        System.out.print("Cedula del socio: ");
        String cedula = sc.next();

        try {
            boolean resultado = club.sePuedeEliminarSocio(cedula);
            if (resultado) {
                System.out.println("El socio SI puede ser eliminado.");
            }
        } catch (SocioNoExisteException | SocioNoEliminableException e) {
            System.out.println("El socio NO puede ser eliminado: " + e.getMessage());
        }
    }

    // -----------------------------------------------------------------
    // Metodos auxiliares de lectura
    // -----------------------------------------------------------------

    private static int leerEntero(Scanner sc) {
        try {
            return Integer.parseInt(sc.next());
        } catch (NumberFormatException e) {
            System.out.println("Valor numerico invalido. Se usara 0.");
            return 0;
        }
    }

    private static double leerDouble(Scanner sc) {
        try {
            return Double.parseDouble(sc.next());
        } catch (NumberFormatException e) {
            System.out.println("Valor numerico invalido. Se usara 0.0.");
            return 0.0;
        }
    }
}

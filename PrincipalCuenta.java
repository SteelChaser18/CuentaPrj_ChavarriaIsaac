import java.util.ArrayList;
import java.util.Scanner;

public class PrincipalCuenta {

    private static ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
    private static Cuenta cuentaActual = null;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");
            procesarOpcion(opcion);
        } while (opcion != 10);

        System.out.println("Programa finalizado. ¡Hasta luego!");
        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n===== MENÚ PRINCIPAL - PrjCuenta =====");
        System.out.println("1. Crear cuenta");
        System.out.println("2. Conocer la cantidad de cuentas creadas");
        System.out.println("3. Listar cuentas");
        System.out.println("4. Seleccionar cuenta actual");
        System.out.println("5. Asignar el nombre del cuenta habiente (cuenta actual)");
        System.out.println("6. Depositar (cuenta actual)");
        System.out.println("7. Retirar (cuenta actual)");
        System.out.println("8. Consultar saldo (cuenta actual)");
        System.out.println("9. Consultar estado de la cuenta actual");
        System.out.println("10. Salir");
    }

    private static void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1: crearCuenta(); break;
            case 2: System.out.println("Cantidad total de cuentas creadas: " + Cuenta.getCantCuentasCreadas()); break;
            case 3: listarCuentas(); break;
            case 4: seleccionarCuentaActual(); break;
            case 5: asignarNombreCuentaHabiente(); break;
            case 6: depositar(); break;
            case 7: retirar(); break;
            case 8: consultarSaldo(); break;
            case 9: consultarEstado(); break;
            case 10: break;
            default: System.out.println("Opción inválida. Intente nuevamente.");
        }
    }

    private static void crearCuenta() {
        System.out.println("¿Con qué constructor desea crear la cuenta?");
        System.out.println("1. Con nombre y saldo inicial");
        System.out.println("2. Solo con saldo inicial (nombre pendiente)");
        int tipo = leerEntero("Opción: ");

        if (tipo == 1) {
            System.out.print("Nombre del cuenta habiente: ");
            String nombre = sc.nextLine();
            double saldo = leerDouble("Saldo inicial: ");
            Cuenta nueva = new Cuenta(nombre, saldo);
            cuentas.add(nueva);
            System.out.println("Cuenta creada: " + nueva.getCodCuenta());
        } else if (tipo == 2) {
            double saldo = leerDouble("Saldo inicial: ");
            Cuenta nueva = new Cuenta(saldo);
            cuentas.add(nueva);
            System.out.println("Cuenta creada: " + nueva.getCodCuenta() + " (nombre pendiente de asignar)");
        } else {
            System.out.println("Opción de constructor inválida. No se creó ninguna cuenta.");
        }
    }

    private static void listarCuentas() {
        if (cuentas.isEmpty()) {
            System.out.println("Aún no existen cuentas creadas.");
            return;
        }
        System.out.println("----- Listado de cuentas -----");
        for (Cuenta c : cuentas) {
            System.out.println(c.toString());
        }
    }

    private static void seleccionarCuentaActual() {
        if (cuentas.isEmpty()) {
            System.out.println("No existen cuentas creadas. No es posible seleccionar ninguna.");
            return;
        }
        System.out.print("Ingrese el código de la cuenta a seleccionar (ej: cta-1): ");
        String codigo = sc.nextLine();
        Cuenta encontrada = buscarPorCodigo(codigo);
        if (encontrada == null) {
            System.out.println("No existe ninguna cuenta con el código indicado.");
        } else {
            cuentaActual = encontrada;
            System.out.println("Cuenta actual seleccionada: " + cuentaActual.getCodCuenta());
        }
    }

    private static Cuenta buscarPorCodigo(String codigo) {
        for (Cuenta c : cuentas) {
            if (c.getCodCuenta().equalsIgnoreCase(codigo)) {
                return c;
            }
        }
        return null;
    }

    private static boolean hayCuentaActual() {
        if (cuentaActual == null) {
            System.out.println("Primero debe seleccionar una cuenta actual (opción 4).");
            return false;
        }
        return true;
    }

    private static void asignarNombreCuentaHabiente() {
        if (!hayCuentaActual()) return;
        System.out.print("Nuevo nombre del cuenta habiente: ");
        String nombre = sc.nextLine();
        cuentaActual.setNombreCuentaHabiente(nombre);
        System.out.println("Nombre asignado correctamente.");
    }

    private static void depositar() {
        if (!hayCuentaActual()) return;
        double monto = leerDouble("Monto a depositar: ");
        double nuevoSaldo = cuentaActual.depositar(monto);
        System.out.println("Depósito realizado. Saldo actualizado: " + nuevoSaldo);
    }

    private static void retirar() {
        if (!hayCuentaActual()) return;
        double saldoAntes = cuentaActual.getSaldo();
        double monto = leerDouble("Monto a retirar: ");
        double saldoDespues = cuentaActual.retirar(monto);
        if (monto <= 0) {
            System.out.println("Monto inválido. El retiro no fue realizado.");
        } else if (saldoDespues == saldoAntes) {
            System.out.println("Fondos insuficientes. El retiro no fue realizado.");
        } else {
            System.out.println("Retiro realizado. Saldo actualizado: " + saldoDespues);
        }
    }

    private static void consultarSaldo() {
        if (!hayCuentaActual()) return;
        System.out.println("Saldo actual: " + cuentaActual.getSaldo());
    }

    private static void consultarEstado() {
        if (!hayCuentaActual()) return;
        System.out.println(cuentaActual.toString());
    }

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextInt()) {
            System.out.println("Entrada inválida. Ingrese un número entero.");
            sc.next();
            System.out.print(mensaje);
        }
        int valor = sc.nextInt();
        sc.nextLine();
        return valor;
    }

    private static double leerDouble(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextDouble()) {
            System.out.println("Entrada inválida. Ingrese un número.");
            sc.next();
            System.out.print(mensaje);
        }
        double valor = sc.nextDouble();
        sc.nextLine();
        return valor;
    }
}
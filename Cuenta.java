import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Cuenta {

    private String codCuenta = "cta-";
    private double saldo;
    private String nombreCuentaHabiente;
    private String fechaCreacion;
    private int cantDepositosRealizados;
    private int cantRetirosExitosasRealizados;

    private static int cantCuentasCreadas = 0;

    public Cuenta(String nombreCuentaHabiente, double pSaldo) {
        cantCuentasCreadas++;
        codCuenta = codCuenta + cantCuentasCreadas;
        this.nombreCuentaHabiente = nombreCuentaHabiente;
        saldo = pSaldo;
        fechaCreacion = establecerFechaCreacion();
    }

    public Cuenta(double pSaldo) {
        cantCuentasCreadas++;
        codCuenta = codCuenta + cantCuentasCreadas;
        nombreCuentaHabiente = "(pendiente de asignar)";
        saldo = pSaldo;
        fechaCreacion = establecerFechaCreacion();
    }

    private String establecerFechaCreacion() {
        Date fecha = new Date(System.currentTimeMillis());
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        return formatoFecha.format(fecha);
    }

    public void setNombreCuentaHabiente(String pNombreCuentaHabiente) {
        nombreCuentaHabiente = pNombreCuentaHabiente;
    }

    public String getCodCuenta() {
        return codCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public double depositar(double monto) {
        if (monto > 0) {
            saldo = saldo + monto;
            cantDepositosRealizados++;
        }
        return saldo;
    }

    public double retirar(double monto) {
        if (validarRetiro(monto)) {
            saldo = saldo - monto;
            cantRetirosExitosasRealizados++;
        }
        return saldo;
    }

    private Boolean validarRetiro(double monto) {
        return monto > 0 && monto <= saldo;
    }

    public static int getCantCuentasCreadas() {
        return cantCuentasCreadas;
    }

    public String toString() {
        return "Cuenta [codCuenta=" + codCuenta
                + ", nombreCuentaHabiente=" + nombreCuentaHabiente
                + ", saldo=" + saldo
                + ", fechaCreacion=" + fechaCreacion
                + ", cantDepositosRealizados=" + cantDepositosRealizados
                + ", cantRetirosExitosasRealizados=" + cantRetirosExitosasRealizados
                + "]";
    }
}
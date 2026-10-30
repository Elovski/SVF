package proyectofinal;

import java.util.Scanner;

public class SistemaVentas {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        // Variables acumuladoras y contadoras para la Caja y Estadísticas
        int totalVentas = 0;
        int totalProductosVendidos = 0;
        double totalDineroVendido = 0;

        int ventasEfectivo = 0;
        int ventasTarjeta = 0;
        int ventasTransferencia = 0;

        double ventaMayor = 0;
        double ventaMenor = 0;

        int opcionMenu;

        // Bucle do-while principal del sistema
        do {
            System.out.println("\n========================================");
            System.out.println("  SISTEMA DE VENTAS Y FACTURACION BASICA");
            System.out.println("========================================");
            System.out.println("1. Registrar una venta");
            System.out.println("2. Consultar caja");
            System.out.println("3. Mostrar estadisticas");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opcion (1-4): ");
            opcionMenu = teclado.nextInt();

            // Evaluación de la opción del menú mediante switch
            switch (opcionMenu) {
                case 1:
                    System.out.println("\n--- REGISTRO DE VENTA ---");

                    // Validar que el número de productos sea mayor que cero
                    int cantProductos;
                    System.out.print("Ingrese la cantidad de productos a comprar: ");
                    cantProductos = teclado.nextInt();

                    while (cantProductos <= 0) {
                        System.out.println("Error: La cantidad de productos debe ser mayor que cero.");
                        System.out.print("Ingrese la cantidad de productos a comprar: ");
                        cantProductos = teclado.nextInt();
                    }

                    double subtotalVenta = 0;
                    int cantidadTotalProductosEstaVenta = 0;

                    // Ciclo for obligatorio para procesar los productos
                    for (int i = 1; i <= cantProductos; i++) {
                        System.out.println("\n-> Producto #" + i);
                        System.out.print("Codigo del producto: ");
                        String codigo = teclado.next();

                        teclado.nextLine(); // Limpiar el salto de línea del buffer
                        System.out.print("Nombre del producto: ");
                        String nombre = teclado.nextLine();

                        // Validación del precio unitario (> 0)
                        System.out.print("Precio unitario (Q): ");
                        double precio = teclado.nextDouble();
                        while (precio <= 0) {
                            System.out.println("Error: El precio debe ser mayor que cero.");
                            System.out.print("Precio unitario (Q): ");
                            precio = teclado.nextDouble();
                        }

                        // Validación de la cantidad de unidades (> 0)
                        System.out.print("Cantidad de unidades: ");
                        int cantidad = teclado.nextInt();
                        while (cantidad <= 0) {
                            System.out.println("Error: La cantidad debe ser mayor que cero.");
                            System.out.print("Cantidad de unidades: ");
                            cantidad = teclado.nextInt();
                        }

                        double subtotalProducto = precio * cantidad;
                        subtotalVenta += subtotalProducto;
                        cantidadTotalProductosEstaVenta += cantidad;
                    }

                    // Estructura if/else para la escala de descuentos
                    double porcentajeDescuento = 0;
                    if (subtotalVenta < 200) {
                        porcentajeDescuento = 0.0;
                    } else if (subtotalVenta <= 499.99) {
                        porcentajeDescuento = 0.05;
                    } else if (subtotalVenta <= 999.99) {
                        porcentajeDescuento = 0.08;
                    } else {
                        porcentajeDescuento = 0.10;
                    }

                    double montoDescuento = subtotalVenta * porcentajeDescuento;
                    double montoConDescuento = subtotalVenta - montoDescuento;
                    double iva = montoConDescuento * 0.12;
                    double totalVenta = montoConDescuento + iva;

                    // Selección de la Forma de Pago
                    System.out.println("\n--- FORMA DE PAGO ---");
                    System.out.println("1. Efectivo");
                    System.out.println("2. Tarjeta");
                    System.out.println("3. Transferencia");
                    System.out.print("Seleccione la forma de pago (1-3): ");
                    int formaPago = teclado.nextInt();

                    while (formaPago < 1 || formaPago > 3) {
                        System.out.println("Error: Opcion de pago invalida.");
                        System.out.print("Seleccione la forma de pago (1-3): ");
                        formaPago = teclado.nextInt();
                    }

                    String nombreFormaPago = "";
                    double efectivoRecibido = 0;
                    double cambio = 0;

                    // Estructura switch para procesar la forma de pago
                    switch (formaPago) {
                        case 1:
                            nombreFormaPago = "Efectivo";
                            ventasEfectivo++;
                            System.out.print("Monto en efectivo recibido: Q");
                            efectivoRecibido = teclado.nextDouble();

                            // Bucle while para validar efectivo suficiente
                            while (efectivoRecibido < totalVenta) {
                                System.out.println("Error: El efectivo recibido (Q" + efectivoRecibido + ") es insuficiente. El total es: Q" + totalVenta);
                                System.out.print("Ingrese un monto igual o mayor al total: Q");
                                efectivoRecibido = teclado.nextDouble();
                            }
                            cambio = efectivoRecibido - totalVenta;
                            break;

                        case 2:
                            nombreFormaPago = "Tarjeta";
                            ventasTarjeta++;
                            break;

                        case 3:
                            nombreFormaPago = "Transferencia";
                            ventasTransferencia++;
                            break;
                    }

                    // Actualizar contadores y acumuladores globales
                    totalVentas++;
                    totalProductosVendidos += cantidadTotalProductosEstaVenta;
                    totalDineroVendido += totalVenta;

                    // Determinación de la venta de mayor y menor valor
                    if (totalVentas == 1) {
                        ventaMayor = totalVenta;
                        ventaMenor = totalVenta;
                    } else {
                        if (totalVenta > ventaMayor) {
                            ventaMayor = totalVenta;
                        }
                        if (totalVenta < ventaMenor) {
                            ventaMenor = totalVenta;
                        }
                    }

                    // Imprimir Factura / Resumen
                    System.out.println("\n========================================");
                    System.out.println("           RESUMEN DE VENTA             ");
                    System.out.println("========================================");
                    System.out.println("Cantidad de productos comprados: " + cantidadTotalProductosEstaVenta);
                    System.out.println("Subtotal: Q" + subtotalVenta);
                    System.out.println("Descuento (" + (int)(porcentajeDescuento * 100) + "%): Q" + montoDescuento);
                    System.out.println("Monto con descuento: Q" + montoConDescuento);
                    System.out.println("IVA (12%): Q" + iva);
                    System.out.println("TOTAL A PAGAR: Q" + totalVenta);
                    System.out.println("Forma de pago: " + nombreFormaPago);
                    if (formaPago == 1) {
                        System.out.println("Efectivo recibido: Q" + efectivoRecibido);
                        System.out.println("Cambio a entregar: Q" + cambio);
                    }
                    System.out.println("========================================");
                    break;

                case 2:
                    System.out.println("\n========================================");
                    System.out.println("             CONSULTA DE CAJA           ");
                    System.out.println("========================================");
                    System.out.println("Ventas realizadas: " + totalVentas);
                    System.out.println("Productos vendidos: " + totalProductosVendidos);
                    System.out.println("Total vendido: Q" + totalDineroVendido);
                    System.out.println("Ventas en efectivo: " + ventasEfectivo);
                    System.out.println("Ventas con tarjeta: " + ventasTarjeta);
                    System.out.println("Ventas por transferencia: " + ventasTransferencia);
                    System.out.println("========================================");
                    break;

                case 3:
                    System.out.println("\n========================================");
                    System.out.println("               ESTADISTICAS             ");
                    System.out.println("========================================");
                    if (totalVentas == 0) {
                        System.out.println("No existen ventas registradas.");
                    } else {
                        double promedioVenta = totalDineroVendido / totalVentas;
                        System.out.println("Numero total de ventas: " + totalVentas);
                        System.out.println("Cantidad total de productos vendidos: " + totalProductosVendidos);
                        System.out.println("Total de dinero vendido: Q" + totalDineroVendido);
                        System.out.println("Promedio de dinero por venta: Q" + promedioVenta);
                        System.out.println("Cantidad de ventas en efectivo: " + ventasEfectivo);
                        System.out.println("Cantidad de ventas con tarjeta: " + ventasTarjeta);
                        System.out.println("Cantidad de ventas por transferencia: " + ventasTransferencia);
                        System.out.println("Venta de mayor valor: Q" + ventaMayor);
                        System.out.println("Venta de menor valor: Q" + ventaMenor);
                    }
                    System.out.println("========================================");
                    break;

                case 4:
                    System.out.println("\n¡Gracias por utilizar el sistema! Saliendo del programa...");
                    break;

                default:
                    System.out.println("Error: Opcion invalida en el menu principal. Por favor ingrese un numero entre 1 y 4.");
            }

        } while (opcionMenu != 4);

        teclado.close();
    }
}


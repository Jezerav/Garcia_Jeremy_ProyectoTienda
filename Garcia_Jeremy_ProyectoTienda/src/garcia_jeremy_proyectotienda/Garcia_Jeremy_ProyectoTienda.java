package Garcia_Jeremy_ProyectoTienda;

import java.util.Scanner;

public class Garcia_Jeremy_ProyectoTienda {

    public static void main(String[] args) {
        Scanner lea = new Scanner(System.in);
        lea.useDelimiter("\n");

        // Variables principales
        double caja = 0.00;
        double totalVentas = 0.00, totalCompras = 0.00;
        int numVentas = 0, numCompras = 0;
        boolean cajaCerrada = false;
        boolean primerApertura = true;
        double mayorGananciaVenta = 0.00, mayorGastoCompra = 0.00;
        double totalKilosAzucar = 0, totalKilosAvena = 0, totalKilosTrigo = 0, totalKilosMaiz = 0;

        int opcion;
        boolean cajaAbierta = false; // Control if caja is opened
        boolean hayProductos = false; // Control if products are bought

        // Detalles de la venta/compra actual
        String detallesTransaccion = "";
        double subtotalTransaccion = 0.0;

        // Proveedor actual
        String tipoProveedorActual = "";
        boolean hayCompras = false; // Controla si ya se hizo al menos una compra

        // Stock variables
        double stockAzucar = 0;
        double stockAvena = 0;
        double stockTrigo = 0;
        double stockMaiz = 0;

        do {
            // Mostrar el menú principal
            System.out.println("\n------ MENU TIENDA ------");
            System.out.println("1. Abrir Caja");
            System.out.println("2. Ventas");
            System.out.println("3. Compras");
            System.out.println("4. Reportes");
            System.out.println("5. Cierre de Caja");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = lea.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Error: Ingrese un número válido para la opción del menú.");
                lea.next(); // Consume the invalid input
                opcion = 0; // Invalid option, will loop again
                continue;
            }

            switch (opcion) {
                case 1: // Abrir Caja
                    if (cajaCerrada && !primerApertura) {
                        caja = (caja * 0.40); // Lo que queda despues del cierre
                        System.out.println("Caja abierta nuevamente. Contadores reiniciados.");
                        // Reiniciar contadores y acumuladores
                        totalVentas = 0.00;
                        totalCompras = 0.00;
                        numVentas = 0;
                        numCompras = 0;
                        totalKilosAzucar = 0;
                        totalKilosAvena = 0;
                        totalKilosTrigo = 0;
                        totalKilosMaiz = 0;
                        mayorGananciaVenta = 0.00;
                        mayorGastoCompra = 0.00;
                        cajaCerrada = false;
                        hayProductos = false;
                        tipoProveedorActual = "";
                        hayCompras = false;
                        cajaAbierta = true;
                        stockAzucar = 0;
                        stockAvena = 0;
                        stockTrigo = 0;
                        stockMaiz = 0;

                        System.out.println("Caja abierta con Lps. " + String.format("%.2f", caja));

                    } else {
                        System.out.print("Ingrese cantidad de efectivo a guardar en caja: Lps. ");
                        double efectivo = 0;
                        try {
                            efectivo = lea.nextDouble();
                        } catch (java.util.InputMismatchException e) {
                            System.out.println("Error: Ingrese un número válido para el efectivo.");
                            lea.next(); // Consume the invalid input
                            break;
                        }
                        if (efectivo > 0) {
                            caja += efectivo;
                            primerApertura = false;
                            cajaCerrada = false;
                            cajaAbierta = true;
                            System.out.println("Caja abierta con Lps. " + String.format("%.2f", caja));
                        } else {
                            System.out.println("Cantidad inválida.");
                        }
                    }
                    break;

                case 2: // Ventas
                    if (!cajaAbierta) {
                        System.out.println("Error: Debe abrir la caja primero (Opción 1).");
                        break;
                    }
                    if (!hayProductos) {
                        System.out.println("Error: No hay productos en existencia. Debe realizar una compra primero (Opción 3).");
                        break;
                    }
                    if (stockAzucar <= 0 && stockAvena <= 0 && stockTrigo <= 0 && stockMaiz <= 0) {
                        System.out.println("Error: El stock de todos los productos es insuficiente. Realice una compra (Opción 3).");
                        break;
                    }
                    if (cajaCerrada) {
                        System.out.println("La caja está cerrada. No se pueden realizar ventas.");
                        break;
                    }

                    System.out.print("Tipo de cliente (A/B/C): ");
                    String tipoCliente = lea.next().toUpperCase();
                    System.out.println("------------------");

                    boolean seguirVendiendo = true;
                    double subtotal = 0.00;

                    // Resetear detalles de la transaccion
                    detallesTransaccion = "";
                    subtotalTransaccion = 0.0;

                    while (seguirVendiendo) {
                        System.out.print("Ingrese código del producto a vender (1-Azúcar, 2-Avena, 3-Trigo, 4-Maíz): ");
                        int cod = 0;
                        try {
                            cod = lea.nextInt();
                        } catch (java.util.InputMismatchException e) {
                            System.out.println("Error: Ingrese un número válido para el código del producto.");
                            lea.next();
                            continue;
                        }
                        double precio = 0.00;
                        String nombreProducto = "";
                        boolean puedeComprar = false;
                        double kilos = 0.0;
                        boolean hayStockSuficienteProducto = false;

                        switch (cod) {
                            case 1:
                                nombreProducto = "Azúcar";
                                precio = 30.00;
                                puedeComprar = tipoCliente.equals("A") || tipoCliente.equals("B");
                                hayStockSuficienteProducto = stockAzucar > 0;
                                break;
                            case 2:
                                nombreProducto = "Avena";
                                precio = 25.00;
                                puedeComprar = tipoCliente.equals("A") || tipoCliente.equals("B");
                                hayStockSuficienteProducto = stockAvena > 0;
                                break;
                            case 3:
                                nombreProducto = "Trigo";
                                precio = 32.00;
                                puedeComprar = tipoCliente.equals("A") || tipoCliente.equals("B");
                                hayStockSuficienteProducto = stockTrigo > 0;
                                break;
                            case 4:
                                nombreProducto = "Maíz";
                                precio = 20.00;
                                puedeComprar = tipoCliente.equals("A") || tipoCliente.equals("C");
                                hayStockSuficienteProducto = stockMaiz > 0;
                                break;
                            default:
                                System.out.println("Código de producto inválido.");
                        }

                        if (!puedeComprar) {
                            System.out.println("Este cliente no puede comprar este producto.");
                        } else if (!hayStockSuficienteProducto) {
                            System.out.println("Stock insuficiente de " + nombreProducto + ".");
                        } else {
                            System.out.print("¿Cuántos kilogramos desea comprar?: ");
                            try {
                                kilos = lea.nextDouble();
                            } catch (java.util.InputMismatchException e) {
                                System.out.println("Error: Ingrese un número válido para los kilogramos.");
                                lea.next();
                                continue;
                            }

                            //Sufficient stock check
                            boolean haySuficienteStock = false;
                            switch (cod) {
                                case 1:
                                    haySuficienteStock = (stockAzucar >= kilos);
                                    break;
                                case 2:
                                    haySuficienteStock = (stockAvena >= kilos);
                                    break;
                                case 3:
                                    haySuficienteStock = (stockTrigo >= kilos);
                                    break;
                                case 4:
                                    haySuficienteStock = (stockMaiz >= kilos);
                                    break;
                            }

                            if (kilos > 0 && haySuficienteStock) {
                                double costoVenta = kilos * precio;
                                subtotal += costoVenta;
                                detallesTransaccion += String.format("%s - %.2f kg x Lps. %.2f = Lps. %.2f\n",
                                        nombreProducto, kilos, precio, costoVenta);
                                subtotalTransaccion += costoVenta;

                                //Update stock
                                switch (cod) {
                                    case 1:
                                        stockAzucar -= kilos;
                                        totalKilosAzucar += kilos;
                                        break;
                                    case 2:
                                        stockAvena -= kilos;
                                        totalKilosAvena += kilos;
                                        break;
                                    case 3:
                                        stockTrigo -= kilos;
                                        totalKilosTrigo += kilos;
                                        break;
                                    case 4:
                                        stockMaiz -= kilos;
                                        totalKilosMaiz += kilos;
                                        break;
                                }

                                System.out.println("Agregado: " + kilos + " kg de " + nombreProducto + " a Lps. " + String.format("%.2f", precio));
                                System.out.println("------------------");
                            } else if (kilos <= 0) {
                                System.out.println("Cantidad no válida.");
                            } else {
                                System.out.println("No hay suficiente stock de " + nombreProducto + " disponible.");
                            }
                        }
                        System.out.print("¿Desea comprar otro producto? (si/no): ");
                        String respuesta = lea.next();
                        seguirVendiendo = respuesta.equalsIgnoreCase("si");
                        System.out.println("------------------");
                    }

                    // Facturación
                    if (subtotal > 0) {
                        double descuento = 0.00;
                        if (subtotal >= 5000) {
                            descuento = subtotal * 0.10;
                        } else if (subtotal >= 1000) {
                            descuento = subtotal * 0.05;
                        }

                        double impuesto = (subtotal - descuento) * 0.07;
                        double total = subtotal - descuento + impuesto;

                        System.out.println("\n--- Factura ---");
                        System.out.println("Detalle de la compra:\n" + detallesTransaccion);
                        System.out.println("----------------");
                        System.out.println("Subtotal: Lps. " + String.format("%.2f", subtotalTransaccion));
                        System.out.println("Descuento: Lps. " + String.format("%.2f", descuento));
                        System.out.println("Impuesto (7%): Lps. " + String.format("%.2f", impuesto));
                        System.out.println("Total a pagar: Lps. " + String.format("%.2f", total));
                        System.out.println("----------------");

                        caja += total;
                        totalVentas += total;
                        numVentas++;
                        if (total > mayorGananciaVenta) {
                            mayorGananciaVenta = total;
                        }
                    }
                    break;

                case 3: // Compras
                    if (!cajaAbierta) {
                        System.out.println("Error: Debe abrir la caja primero (Opción 1).");
                        break;
                    }
                    if (cajaCerrada) {
                        System.out.println("La caja está cerrada. No se pueden realizar compras.");
                        break;
                    }

                    // Siempre permitir elegir el proveedor al entrar a compras
                    System.out.print("Tipo de proveedor (A/B/C): ");
                    String tipoProveedor = lea.next().toUpperCase();

                    //Basic validation of provider type
                    while (!tipoProveedor.equals("A") && !tipoProveedor.equals("B") && !tipoProveedor.equals("C")) {
                        System.out.println("Tipo de proveedor inválido. Debe ser A, B o C.");
                        System.out.print("Tipo de proveedor (A/B/C): ");
                        tipoProveedor = lea.next().toUpperCase();
                    }
                    tipoProveedorActual = tipoProveedor; // Actualizar el proveedor actual
                    hayCompras = true; // Marcar que se ha seleccionado un proveedor

                    boolean seguirComprandoProveedor = true;
                    while (seguirComprandoProveedor) {
                        System.out.print("Ingrese código del producto a comprar (1-Azúcar, 2-Avena, 3-Trigo, 4-Maíz): ");
                        int codCompra = 0;
                        try {
                            codCompra = lea.nextInt();
                        } catch (java.util.InputMismatchException e) {
                            System.out.println("Error: Ingrese un número válido para el código del producto.");
                            lea.next(); // Consume the invalid input
                            continue;
                        }
                        double precioCompra = 0.00;
                        String nombreProductoCompra = "";
                        boolean puedeProveer = false;
                        double kilosCompra = 0.0;
                        double totalCompra = 0.0;

                        // Resetear detalles de la transaccion
                        detallesTransaccion = "";
                        subtotalTransaccion = 0.0;

                        switch (codCompra) {
                            case 1: // Azúcar
                                nombreProductoCompra = "Azúcar";
                                precioCompra = 25.00;
                                puedeProveer = tipoProveedor.equals("A");
                                break;
                            case 2: // Avena
                                nombreProductoCompra = "Avena";
                                if (tipoProveedor.equals("B")) {
                                    precioCompra = 20.00;
                                } else if (tipoProveedor.equals("C")) {
                                    precioCompra = 22.00;
                                }
                                puedeProveer = tipoProveedor.equals("B") || tipoProveedor.equals("C");
                                break;
                            case 3: // Trigo
                                nombreProductoCompra = "Trigo";
                                precioCompra = 30.00;
                                puedeProveer = tipoProveedor.equals("B");
                                break;
                            case 4: // Maíz
                                nombreProductoCompra = "Maíz";
                                precioCompra = 18.00;
                                puedeProveer = tipoProveedor.equals("A");
                                break;
                            default:
                                System.out.println("Código de producto inválido.");
                        }

                        if (!puedeProveer) {
                            System.out.println("Proveedor no vende dicho producto.");
                            System.out.print("¿Desea intentar comprar otro producto de este proveedor? (si/no): ");
                            String respuestaIntentoNuevo = lea.next();
                            seguirComprandoProveedor = respuestaIntentoNuevo.equalsIgnoreCase("si");
                            continue; // Volver a preguntar el código del producto
                        } else {
                            System.out.print("¿Cuántos kilogramos desea comprar?: ");
                            try {
                                kilosCompra = lea.nextDouble();
                            } catch (java.util.InputMismatchException e) {
                                System.out.println("Error: Ingrese un número válido para los kilogramos.");
                                lea.next();
                                continue;
                            }

                            if (kilosCompra > 0) {
                                totalCompra = kilosCompra * precioCompra;

                                if (caja >= totalCompra) {
                                    caja -= totalCompra;
                                    totalCompras += totalCompra;
                                    numCompras++;
                                    if (totalCompra > mayorGastoCompra) {
                                        mayorGastoCompra = totalCompra;
                                    }

                                    detallesTransaccion += String.format("%s - %.2f kg x Lps. %.2f = Lps. %.2f\n",
                                            nombreProductoCompra, kilosCompra, precioCompra, totalCompra);

                                    subtotalTransaccion = totalCompra; // Solo 1 producto por compra

                                    if (codCompra == 1) {
                                        stockAzucar += kilosCompra;
                                        totalKilosAzucar += kilosCompra;
                                    } else if (codCompra == 2) {
                                        stockAvena += kilosCompra;
                                        totalKilosAvena += kilosCompra;
                                    } else if (codCompra == 3) {
                                        stockTrigo += kilosCompra;
                                        totalKilosTrigo += kilosCompra;
                                    } else if (codCompra == 4) {
                                        stockMaiz += kilosCompra;
                                        totalKilosMaiz += kilosCompra;
                                    }

                                    System.out.println("\n--- Factura de Compra ---");
                                    System.out.println("Detalle de la compra:\n" + detallesTransaccion);
                                    System.out.println("-------------------------");
                                    System.out.println("Total de la compra: Lps. " + String.format("%.2f", totalCompra));
                                    System.out.println("-------------------------");

                                    hayProductos = true; // Habilita Ventas
                                    seguirComprandoProveedor = false; // Se realizó la compra, salir del loop del proveedor
                                } else {
                                    System.out.println("No se puede pagar la compra. No hay suficiente efectivo en caja.");
                                    seguirComprandoProveedor = false; // No se pudo comprar, salir del loop del proveedor
                                }
                            } else {
                                System.out.println("Cantidad no válida.");
                                System.out.print("¿Desea intentar comprar otro producto de este proveedor? (si/no): ");
                                String respuestaIntentoNuevo = lea.next();
                                seguirComprandoProveedor = respuestaIntentoNuevo.equalsIgnoreCase("si");
                            }
                        }
                    }
                    break;

                case 4: // Reportes
                    if (!cajaAbierta) {
                        System.out.println("Error: Debe abrir la caja primero (Opción 1).");
                        break;
                    }

                    System.out.println("\n------ REPORTES ------");
                    System.out.println("Cantidad actual en caja: Lps. " + String.format("%.2f", caja));
                    System.out.println("Total de ventas: Lps. " + String.format("%.2f", totalVentas));
                    System.out.println("Total de compras: Lps. " + String.format("%.2f", totalCompras));
                    System.out.println("Número de ventas: " + numVentas);
                    System.out.println("Número de compras: " + numCompras);

                    double gananciaTotal = totalVentas - totalCompras;
                    System.out.println("Ganancia total: Lps. " + String.format("%.2f", gananciaTotal));

                    double promedioVenta = (numVentas > 0) ? totalVentas / numVentas : 0;
                    double promedioCompra = (numCompras > 0) ? totalCompras / numCompras : 0;
                    System.out.println("Promedio de venta: Lps. " + String.format("%.2f", promedioVenta));
                    System.out.println("Promedio de compra: Lps. " + String.format("%.2f", promedioCompra));

                    System.out.println("Venta con mayor ganancia: Lps. " + String.format("%.2f", mayorGananciaVenta));
                    System.out.println("Compra con mayor gasto: Lps. " + String.format("%.2f", mayorGastoCompra));

                    // Producto Estrella
                    double maxKilosVendidos = Math.max(Math.max(totalKilosAzucar, totalKilosAvena), Math.max(totalKilosTrigo, totalKilosMaiz));
                    String productoEstrella = "";

                    if (maxKilosVendidos > 0) {
                        if (maxKilosVendidos == totalKilosAzucar) {
                            productoEstrella = "Azúcar";
                        } else if (maxKilosVendidos == totalKilosAvena) {
                            productoEstrella = "Avena";
                        } else if (maxKilosVendidos == totalKilosTrigo) {
                            productoEstrella = "Trigo";
                        } else if (maxKilosVendidos == totalKilosMaiz) {
                            productoEstrella = "Maíz";
                        }

                        System.out.println("Producto estrella (más vendido): " + productoEstrella);
                    } else {
                        System.out.println("No hay ventas registradas para determinar el producto estrella.");
                    }

                    break;

                case 5: // Cierre de Caja
                    if (!cajaAbierta) {
                        System.out.println("Error: Debe abrir la caja primero (Opción 1).");
                        break;
                    }

                    System.out.println("\n------ CIERRE DE CAJA ------");
                    System.out.println("Ganancia del día: Lps. " + String.format("%.2f", (totalVentas - totalCompras)));
                    System.out.print("Ingrese el monto a depositar en el banco (máximo 60%): Lps. ");
                    double deposito = lea.nextDouble();
                    double maximoDeposito = caja * 0.60;

                    if (deposito <= maximoDeposito) {
                        caja -= deposito;
                        System.out.println("Se depositaron: Lps. " + String.format("%.2f", deposito) + " en el banco.");
                        System.out.println("Efectivo restante en caja: Lps. " + String.format("%.2f", caja));
                    } else {
                        System.out.println("Monto excede el máximo permitido (60% de Lps. " + String.format("%.2f", maximoDeposito) + ")");
                    }

                    cajaCerrada = true;
                    hayProductos = false;
                    cajaAbierta = false;
                    break;

                case 6: // Salir
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 6);
    }
}

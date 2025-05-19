package Garcia_Jeremy_ProyectoTienda;

import java.util.Scanner;

public class Garcia_Jeremy_ProyectoTienda {

    public static void main(String[] args) {
        Scanner lea = new Scanner(System.in);
        lea.useDelimiter("\n");

        // Variables principales
        double caja = 0.00; // Dinero actual en caja
        double totalVentas = 0.00, totalCompras = 0.00; // Acumuladores de ventas y compras
        int numVentas = 0, numCompras = 0; // Contadores de ventas y compras
        boolean cajaCerrada = false; // Indica si la caja está cerrada
        boolean primerApertura = true; // Controla si es la primera vez que se abre la caja
        double mayorGananciaVenta = 0.00, mayorGastoCompra = 0.00; // Para registrar la venta con mayor ganancia y la compra con mayor gasto
        double totalKilosAzucar = 0, totalKilosAvena = 0, totalKilosTrigo = 0, totalKilosMaiz = 0; // Acumuladores de kilos por tipo de producto

        int opcion; // Opción seleccionada en el menú
        boolean cajaAbierta = false; // Indica si la caja está abierta
        boolean hayProductos = false; // Indica si ya se han comprado productos

        // Detalles de la venta/compra actual
        String detallesTransaccion = ""; // Texto descriptivo de la transacción actual
        double subtotalTransaccion = 0.0; // Monto subtotal de la transacción actual

        // Proveedor actual
        String tipoProveedorActual = ""; // Tipo de proveedor de la última compra
        boolean hayCompras = false; // Indica si ya se hizo al menos una compra

        // Stock variables
        double stockAzucar = 0;
        double stockAvena = 0;
        double stockTrigo = 0;
        double stockMaiz = 0;

        do {
            // Mostrar el menú principal
            System.out.println("---------------------- MENU TIENDA ----------------------");
            System.out.println("< 1. Abrir Caja                                       >");
            System.out.println("< 2. Ventas                                          >");
            System.out.println("< 3. Compras                                         >");
            System.out.println("< 4. Reportes                                        >");
            System.out.println("< 5. Cierre de Caja                                  >");
            System.out.println("< 6. Salir                                           >");
            System.out.println("-------------------------------------------------------");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = lea.nextInt(); // Lectura de opción del usuario
            } catch (java.util.InputMismatchException e) {
                System.out.println("Error: Ingrese un número válido para la opción del menú.");
                lea.next(); // Consume el valor inválido
                opcion = 0; // Opción inválida para repetir ciclo
                continue;
            }

            switch (opcion) {
                case 1: // Abrir Caja
                    System.out.println("---------------------- Abrir Caja ----------------------");
                    if (cajaCerrada && !primerApertura) {
                        // Si la caja ya fue cerrada antes, se abre con el 40% del dinero restante
                        caja = (caja * 0.40); 
                        System.out.println("< Caja abierta nuevamente. Contadores reiniciados. >");

                        // Reiniciar contadores y acumuladores para nueva jornada
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

                        System.out.println("< Caja abierta con Lps. " + String.format("%.2f", caja) + "           >");

                    } else {
                        // Primera apertura o intento de abrir sin cierre previo
                        System.out.print("< Ingrese cantidad de efectivo a guardar en caja: Lps. ");
                        double efectivo = 0;
                        try {
                            efectivo = lea.nextDouble(); // Lectura del efectivo a ingresar
                        } catch (java.util.InputMismatchException e) {
                            System.out.println("< Error: Ingrese un número válido para el efectivo. >");
                            lea.next(); // Consume el valor inválido
                            break;
                        }
                        if (efectivo > 0) {
                            caja += efectivo;
                            primerApertura = false;
                            cajaCerrada = false;
                            cajaAbierta = true;
                            System.out.println("< Caja abierta con Lps. " + String.format("%.2f", caja) + "           >");
                        } else {
                           System.out.println("< Cantidad inválida.");
                        }
                    }
                    break;


                case 2: // Ventas
                    System.out.println("---------------------- Ventas ----------------------");

                    // Validaciones previas a la venta
                    if (!cajaAbierta) {
                        System.out.println("< Error: Debe abrir la caja primero (Opción 1).   >");
                        System.out.println("-------------------------------------------------------");
                        break;
                    }
                    if (!hayProductos) {
                        System.out.println("< Error: No hay productos en existencia. Debe    >");
                        System.out.println("< realizar una compra primero (Opción 3).       >");
                        System.out.println("-------------------------------------------------------");
                        break;
                    }
                    if (stockAzucar <= 0 && stockAvena <= 0 && stockTrigo <= 0 && stockMaiz <= 0) {
                        System.out.println("< Error: El stock de todos los productos es      >");
                        System.out.println("< insuficiente. Realice una compra (Opción 3).    >");
                        System.out.println("-------------------------------------------------------");
                        break;
                    }
                    if (cajaCerrada) {
                        System.out.println("< La caja está cerrada. No se pueden realizar ventas. >");
                        System.out.println("-------------------------------------------------------");
                        break;
                    }

                    // Selección del tipo de cliente
                    System.out.print("< Tipo de cliente (A/B/C): ");
                    String tipoCliente = lea.next().toUpperCase(); // Cliente puede ser A, B o C
                    System.out.println("< ------------------                           >");

                    boolean seguirVendiendo = true;
                    double subtotal = 0.00;

                    // Reiniciar detalles de transacción para nueva venta
                    detallesTransaccion = "";
                    subtotalTransaccion = 0.0;

                    // Ciclo para agregar productos a la venta
                    while (seguirVendiendo) {
                        System.out.print("< Ingrese código del producto a vender (1-Azúcar, 2-Avena, 3-Trigo, 4-Maíz): ");
                        int cod = 0;
                        try {
                            cod = lea.nextInt(); // Lectura del código de producto
                        } catch (java.util.InputMismatchException e) {
                            System.out.println("< Error: Ingrese un número válido para el código del producto. >");
                            lea.next();
                            continue;
                        }

                        // Inicialización de variables para validación y cálculo
                        double precio = 0.00;
                        String nombreProducto = "";
                        boolean puedeComprar = false;
                        double kilos = 0.0;
                        boolean hayStockSuficienteProducto = false;
                        boolean cantidadValida = false;
                        double kilosFinal = 0.0;

                        // Determinar si el cliente puede comprar el producto según tipo y disponibilidad
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
                                continue;
                        }

                        // Validaciones de permisos y stock
                        if (!puedeComprar) {
                            System.out.println("< Este cliente no puede comprar este producto.    >");
                            continue;
                        } else if (!hayStockSuficienteProducto) {
                            System.out.println("< Stock insuficiente de " + nombreProducto + ".        >");
                            continue;
                        }

                        // Solicitar cantidad en kilogramos
                        while (!cantidadValida) {
                            System.out.print("< ¿Cuántos kilogramos desea comprar de " + nombreProducto + "?: ");
                            try {
                                kilos = lea.nextDouble();
                                if (kilos > 0) {
                                    cantidadValida = true;
                                    kilosFinal = kilos;
                                } else {
                                    System.out.println("Cantidad no válida. Ingrese un valor mayor que cero.");
                                }
                            } catch (java.util.InputMismatchException e) {
                                System.out.println("Error: Ingrese un número válido para los kilogramos.");
                                lea.next();
                            }

                            // Validar si hay suficiente stock para la cantidad deseada
                            if (cantidadValida) {
                                boolean haySuficienteStock = false;
                                switch (cod) {
                                    case 1:
                                        haySuficienteStock = (stockAzucar >= kilosFinal);
                                        break;
                                    case 2:
                                        haySuficienteStock = (stockAvena >= kilosFinal);
                                        break;
                                    case 3:
                                        haySuficienteStock = (stockTrigo >= kilosFinal);
                                        break;
                                    case 4:
                                        haySuficienteStock = (stockMaiz >= kilosFinal);
                                        break;
                                }

                                if (haySuficienteStock) {
                                    // Calcular costo de la venta y actualizar totales
                                    double costoVenta = kilosFinal * precio;
                                    subtotal += costoVenta;
                                    detallesTransaccion += String.format("%s - %.2f kg x Lps. %.2f = Lps. %.2f\n",
                                            nombreProducto, kilosFinal, precio, costoVenta);
                                    subtotalTransaccion += costoVenta;

                                    // Actualizar stock y acumuladores por producto
                                    switch (cod) {
                                        case 1:
                                            stockAzucar -= kilosFinal;
                                            totalKilosAzucar += kilosFinal;
                                            break;
                                        case 2:
                                            stockAvena -= kilosFinal;
                                            totalKilosAvena += kilosFinal;
                                            break;
                                        case 3:
                                            stockTrigo -= kilosFinal;
                                            totalKilosTrigo += kilosFinal;
                                            break;
                                        case 4:
                                            stockMaiz -= kilosFinal;
                                            totalKilosMaiz += kilosFinal;
                                            break;
                                    }

                                    // Confirmación al usuario
                                    System.out.println("Agregado: " + kilosFinal + " kg de " + nombreProducto + " a Lps. " + String.format("%.2f", precio));
                                    System.out.println("------------------");
                                } else {
                                    System.out.println("No hay suficiente stock de " + nombreProducto + " disponible.");
                                    cantidadValida = false;
                                }
                            }
                        }

                        // Preguntar si desea seguir vendiendo
                        System.out.print("< ¿Desea comprar otro producto? (si/no): ");
                        String respuesta = lea.next();
                        seguirVendiendo = respuesta.equalsIgnoreCase("si");
                        System.out.println("< ------------------                           >");
                    }

                    // Calcular y mostrar la factura si hubo productos vendidos
                    if (subtotal > 0) {
                        double descuento = 0.00;
                        if (subtotal >= 5000) {
                            descuento = subtotal * 0.10; // 10% de descuento
                        } else if (subtotal >= 1000) {
                            descuento = subtotal * 0.05; // 5% de descuento
                        }

                        double impuesto = (subtotal - descuento) * 0.07; // 7% de impuesto
                        double total = subtotal - descuento + impuesto; // Total final a pagar

                        // Imprimir factura
                        System.out.println("---------------------- FACTURA ----------------------");
                        System.out.println("< Detalle de la compra:                          >");
                        System.out.print(detallesTransaccion);
                        System.out.println("< ---------------------------------------------- >");
                        System.out.println("< Subtotal: Lps. " + String.format("%.2f", subtotalTransaccion) + "                   >");
                        System.out.println("< Descuento: Lps. " + String.format("%.2f", descuento) + "                    >");
                        System.out.println("< Impuesto (7%): Lps. " + String.format("%.2f", impuesto) + "                 >");
                        System.out.println("< Total a pagar: Lps. " + String.format("%.2f", total) + "                  >");
                        System.out.println("-------------------------------------------------------");

                        // Actualizar caja y estadísticas
                        caja += total;
                        totalVentas += total;
                        numVentas++;
                        if (total > mayorGananciaVenta) {
                            mayorGananciaVenta = total;
                        }
                    }
                    break;


                case 3: // Compras
                    System.out.println("---------------------- Compras ----------------------");

                    // Validar si la caja está abierta
                    if (!cajaAbierta) {
                        System.out.println("Error: Debe abrir la caja primero (Opción 1).");
                        break;
                    }

                    // Verificar si la caja ya está cerrada
                    if (cajaCerrada) {
                        System.out.println("La caja está cerrada. No se pueden realizar compras.");
                        break;
                    }

                    // Siempre permitir elegir el proveedor al entrar a compras
                    System.out.print("Tipo de proveedor (A/B/C): ");
                    String tipoProveedor = lea.next().toUpperCase();

                    // Validación básica del tipo de proveedor
                    while (!tipoProveedor.equals("A") && !tipoProveedor.equals("B") && !tipoProveedor.equals("C")) {
                        System.out.println("Tipo de proveedor inválido. Debe ser A, B o C.");
                        System.out.print("Tipo de proveedor (A/B/C): ");
                        tipoProveedor = lea.next().toUpperCase();
                    }

                    tipoProveedorActual = tipoProveedor; // Guardar tipo de proveedor actual
                    hayCompras = true; // Marcar que ya se hicieron compras

                    boolean seguirComprandoProveedor = true;

                    // Inicia ciclo para realizar compras mientras el usuario lo desee
                    while (seguirComprandoProveedor) {
                        System.out.print("Ingrese código del producto a comprar (1-Azúcar, 2-Avena, 3-Trigo, 4-Maíz): ");
                        int codCompra = 0;

                        // Validar que el código ingresado sea numérico
                        try {
                            codCompra = lea.nextInt();
                        } catch (java.util.InputMismatchException e) {
                            System.out.println("Error: Ingrese un número válido para el código del producto.");
                            lea.next(); // Limpiar entrada inválida
                            continue; // Volver a solicitar código
                        }

                        // Variables para almacenar datos del producto y compra
                        double precioCompra = 0.00;
                        String nombreProductoCompra = "";
                        boolean puedeProveer = false;
                        double kilosCompra = 0.0;
                        double totalCompra = 0.0;
                        boolean cantidadValidaCompra = false;
                        double kilosCompraFinal = 0.0; // Cantidad válida final

                        // Resetear detalles de la transacción actual
                        detallesTransaccion = "";
                        subtotalTransaccion = 0.0;

                        // Determinar si el proveedor puede vender ese producto
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
                                continue; // Volver a pedir código de producto
                        }

                        // Verificar si el proveedor puede vender ese producto
                        if (!puedeProveer) {
                            System.out.println("Proveedor no vende dicho producto.");
                            System.out.print("¿Desea intentar comprar otro producto de este proveedor? (si/no): ");
                            String respuestaIntentoNuevo = lea.next();
                            seguirComprandoProveedor = respuestaIntentoNuevo.equalsIgnoreCase("si");
                            continue; // Volver al inicio del ciclo para pedir otro código
                        }

                        // Solicitar la cantidad a comprar y validarla
                        while (!cantidadValidaCompra) {
                            System.out.print("¿Cuántos kilogramos desea comprar de " + nombreProductoCompra + "?: ");
                            try {
                                kilosCompra = lea.nextDouble();
                                if (kilosCompra > 0) {
                                    cantidadValidaCompra = true;
                                    kilosCompraFinal = kilosCompra; // Guardar la cantidad válida
                                } else {
                                    System.out.println("Cantidad no válida. Ingrese un valor mayor que cero.");
                                }
                            } catch (java.util.InputMismatchException e) {
                                System.out.println("Error: Ingrese un número válido para los kilogramos.");
                                lea.next(); // Limpiar entrada inválida
                            }
                        }

                        // Si la cantidad fue válida, procesar la compra
                        if (cantidadValidaCompra) {
                            totalCompra = kilosCompraFinal * precioCompra;

                            // Verificar si hay suficiente dinero en caja
                            if (caja >= totalCompra) {
                                caja -= totalCompra; // Descontar de la caja
                                totalCompras += totalCompra;
                                numCompras++; // Incrementar contador de compras

                                // Verificar si es el mayor gasto en una compra
                                if (totalCompra > mayorGastoCompra) {
                                    mayorGastoCompra = totalCompra;
                                }

                                // Armar el detalle de la factura
                                detallesTransaccion += String.format("%s - %.2f kg x Lps. %.2f = Lps. %.2f\n",
                                        nombreProductoCompra, kilosCompraFinal, precioCompra, totalCompra);
                                subtotalTransaccion = totalCompra; // Solo 1 producto por compra

                                // Actualizar el stock y acumulados según el producto
                                if (codCompra == 1) {
                                    stockAzucar += kilosCompraFinal;
                                    totalKilosAzucar += kilosCompraFinal;
                                } else if (codCompra == 2) {
                                    stockAvena += kilosCompraFinal;
                                    totalKilosAvena += kilosCompraFinal;
                                } else if (codCompra == 3) {
                                    stockTrigo += kilosCompraFinal;
                                    totalKilosTrigo += kilosCompraFinal;
                                } else if (codCompra == 4) {
                                    stockMaiz += kilosCompraFinal;
                                    totalKilosMaiz += kilosCompraFinal;
                                }

                                // Mostrar factura de compra
                                System.out.println("\n--- Factura de Compra ---");
                                System.out.println("Detalle de la compra:\n" + detallesTransaccion);
                                System.out.println("-------------------------");
                                System.out.println("Total de la compra: Lps. " + String.format("%.2f", totalCompra));
                                System.out.println("-------------------------");

                                hayProductos = true; // Permite habilitar opción de ventas
                                seguirComprandoProveedor = false; // Salir del ciclo porque ya compró
                            } else {
                                System.out.println("No se puede pagar la compra. No hay suficiente efectivo en caja.");
                                seguirComprandoProveedor = false; // Salir porque no se pudo comprar
                            }
                        }
                    }
                    break;


                    case 4: // Reportes
                        System.out.println("---------------------- Reportes ----------------------");

                        // Verificar si la caja ha sido abierta; si no, no se pueden generar reportes
                        if (!cajaAbierta) {
                            System.out.println("< Error: La caja debe estar abierta para generar reportes. >");
                        } else {
                            // Encabezado del reporte
                            System.out.println("<                                                      >");
                            System.out.println("< ----------- RESUMEN DE REPORTES ----------- >");
                            System.out.println("<                                                      >");

                            // Estado actual del efectivo en caja
                            System.out.println("< ------------- Estado de Caja ------------- >");
                            System.out.println("< Efectivo en caja: Lps. " + String.format("%.2f", caja) + "              >");
                            System.out.println("< ----------------------------------------- >");
                            System.out.println("<                                                      >");

                            // Resumen de transacciones: ventas y compras
                            System.out.println("< ------- Total de Ventas y Compras ------- >");
                            System.out.println("< Total de ventas: Lps. " + String.format("%.2f", totalVentas) + " (" + numVentas + " transacciones)>");        
                            System.out.println("< Total de compras: Lps. " + String.format("%.2f", totalCompras) + " (" + numCompras + " transacciones)>");
                            System.out.println("< ----------------------------------------- >");
                            System.out.println("<                                                      >");

                            // Producto Estrella: el que más kilogramos se ha vendido
                            System.out.println("< ---- Producto Estrella (Más Vendido) ---- >");

                            // Mapa para almacenar total de kilos vendidos por producto
                            java.util.HashMap<String, Double> ventasPorProducto = new java.util.HashMap<>();
                            ventasPorProducto.put("Azúcar", totalKilosAzucar);
                            ventasPorProducto.put("Avena", totalKilosAvena);
                            ventasPorProducto.put("Trigo", totalKilosTrigo);
                            ventasPorProducto.put("Maíz", totalKilosMaiz);

                            // Buscar la mayor cantidad de kilos vendidos entre todos los productos
                            double maxKilosVendidos = 0;
                            for (double kilos : ventasPorProducto.values()) {
                                if (kilos > maxKilosVendidos) {
                                    maxKilosVendidos = kilos;
                                }
                            }

                            // Lista para almacenar los productos que tengan ese máximo de kilos vendidos
                            java.util.ArrayList<String> productosEstrella = new java.util.ArrayList<>();
                            for (java.util.Map.Entry<String, Double> entry : ventasPorProducto.entrySet()) {
                                if (entry.getValue() == maxKilosVendidos && maxKilosVendidos > 0) {
                                    productosEstrella.add(entry.getKey());
                                }
                            }

                            // Mostrar los productos estrella si los hay
                            if (!productosEstrella.isEmpty()) {
                                System.out.println("< Producto(s) estrella (con " + String.format("%.2f", maxKilosVendidos) + " kg vendidos):>");
                                for (String producto : productosEstrella) {
                                    double cantidadVendida = ventasPorProducto.get(producto);

                                    // Asignar número de veces vendido (aunque este dato parece mal interpretado)
                                    int vecesVendido = 0;
                                    if (producto.equals("Azúcar")) vecesVendido = (int) totalKilosAzucar;
                                    else if (producto.equals("Avena")) vecesVendido = (int) totalKilosAvena;
                                    else if (producto.equals("Trigo")) vecesVendido = (int) totalKilosTrigo;
                                    else if (producto.equals("Maíz")) vecesVendido = (int) totalKilosMaiz;

                                    System.out.println("< - " + producto + ": " + String.format("%.2f", cantidadVendida) + " kg vendidos (Vendido " + vecesVendido + " veces)>");
                                }
                            } else {
                                // Si no se han hecho ventas
                                System.out.println("< No se han realizado ventas aún.           >");
                            }
                            System.out.println("< ----------------------------------------- >");
                            System.out.println("<                                                      >");

                            // Mostrar cuál fue la mayor ganancia en una sola venta y el mayor gasto en una sola compra
                            System.out.println("< ----- Mayor Venta y Mayor Compra ------ >");
                            System.out.println("< Mayor ganancia en una venta: Lps. " + String.format("%.2f", mayorGananciaVenta) + "      >");
                            System.out.println("< Mayor gasto en una compra: Lps. " + String.format("%.2f", mayorGastoCompra) + "       >");
                            System.out.println("< ----------------------------------------- >");
                            System.out.println("<                                                      >");

                            // Mostrar el stock actual de cada producto
                            System.out.println("< -------- Stock de Productos -------- >");
                            System.out.println("< Stock de Azúcar: " + String.format("%.2f", stockAzucar) + " kg                    >");
                            System.out.println("< Stock de Avena: " + String.format("%.2f", stockAvena) + " kg                     >");
                            System.out.println("< Stock de Trigo: " + String.format("%.2f", stockTrigo) + " kg                     >");
                            System.out.println("< Stock de Maíz: " + String.format("%.2f", stockMaiz) + " kg                      >");
                            System.out.println("< ----------------------------------------- >");
                            System.out.println("<                                                      >");
                        }

                        // Final del bloque de reportes
                        System.out.println("-------------------------------------------------------");
                        break;

                    case 5: // Cierre de Caja
                        // Encabezado del reporte de cierre
                        System.out.println("---------------------- Cierre de Caja ----------------------");

                        // Verifica si la caja ya está cerrada o nunca fue abierta
                        if (!cajaAbierta) {
                            System.out.println("< Error: La caja ya está cerrada o no se ha abierto. >");
                            System.out.println("-------------------------------------------------------");
                            break; // Sale del case si no se puede cerrar
                        }

                        // Si la caja está abierta, se procede con el cierre
                        System.out.println("<                                                      >");
                        System.out.println("< --------------- Cierre de Caja --------------- >");
                        System.out.println("<                                                      >");

                        // Muestra el efectivo actual que hay en la caja
                        System.out.println("< Efectivo actual en caja: Lps. " + String.format("%.2f", caja) + "          >");

                        // Calcula el 40% del efectivo, que se guarda
                        double cuarentaPorciento = caja * 0.40;
                        System.out.println("< El 40% (lo que se guarda): Lps. " + String.format("%.2f", cuarentaPorciento) + "     >");

                        // El 60% restante se considera la ganancia del día
                        System.out.println("< El 60% (ganancia del día): Lps. " + String.format("%.2f", (caja - cuarentaPorciento)) + " >");

                        System.out.println("<                                                      >");
                        System.out.println("< ---------------- Caja Cerrada ---------------- >");
                        System.out.println("<                                                      >");

                        // Marca la caja como cerrada y ya no disponible para más transacciones
                        cajaCerrada = true;
                        cajaAbierta = false;

                        // Mensaje final de confirmación
                        System.out.println("< Caja cerrada.                                    >");
                        System.out.println("<                                                      >");
                        System.out.println("-------------------------------------------------------");
                        break;

                case 6: // Salir
                    System.out.println("----------------------- Salir -----------------------");
                    System.out.println("<                                                       >");
                    System.out.println("< ¡Gracias por usar el sistema de la tienda!          >");
                    System.out.println("<                                                       >");
                    System.out.println("-------------------------------------------------------");
                    break; //Salir del programa

                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción del menú.");
            }

        } while (opcion != 6);
    }
}
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
  opcion = lea.nextInt();

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

  System.out.println("Caja abierta con Lps. " + String.format("%.2f", caja));

  } else {
  System.out.print("Ingrese cantidad de efectivo a guardar en caja: Lps. ");
  double efectivo = lea.nextDouble();
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
  if (cajaCerrada) {
  System.out.println("La caja está cerrada. No se pueden realizar ventas.");
  break;
  }

  System.out.print("Tipo de cliente (A/B/C): ");
  String tipoCliente = lea.next().toUpperCase();
  boolean seguirVendiendo = true;
  double subtotal = 0.00;

  // Resetear detalles de la transaccion
  detallesTransaccion = "";
  subtotalTransaccion = 0.0;

  while (seguirVendiendo) {
  System.out.print("Ingrese código del producto a vender (1-Azúcar, 2-Avena, 3-Trigo, 4-Maíz): ");
  int cod = lea.nextInt();
  double precio = 0.00;
  String nombreProducto = "";
  boolean puedeComprar = false;
  double kilos = 0.0; // Declarar kilos aquí

  switch (cod) {
  case 1: // Azúcar
  nombreProducto = "Azúcar";
  precio = 30.00;
  puedeComprar = tipoCliente.equals("A") || tipoCliente.equals("B");
  break;
  case 2: // Avena
  nombreProducto = "Avena";
  precio = 25.00;
  puedeComprar = tipoCliente.equals("A") || tipoCliente.equals("B");
  break;
  case 3: // Trigo
  nombreProducto = "Trigo";
  precio = 32.00;
  puedeComprar = tipoCliente.equals("A") || tipoCliente.equals("B");
  break;
  case 4: // Maíz
  nombreProducto = "Maíz";
  precio = 20.00;
  puedeComprar = tipoCliente.equals("A") || tipoCliente.equals("C");
  break;
  default:
  System.out.println("Código de producto inválido.");
  }

  if (!puedeComprar) {
  System.out.println("Este cliente no puede comprar este producto.");
  } else {
  System.out.print("¿Cuántos kilogramos desea comprar?: ");
  kilos = lea.nextDouble();
  if (kilos > 0) {
  double totalProducto = kilos * precio;
  subtotal += totalProducto;

  // Acumular detalles para la factura
  detallesTransaccion += String.format("%s - %.2f kg x Lps. %.2f = Lps. %.2f\n",
  nombreProducto, kilos, precio, totalProducto);

  subtotalTransaccion += totalProducto; // Update subtotalTransaccion

  if (cod == 1)
  totalKilosAzucar += kilos;
  if (cod == 2)
  totalKilosAvena += kilos;
  if (cod == 3)
  totalKilosTrigo += kilos;
  if (cod == 4)
  totalKilosMaiz += kilos;

  System.out.println("Agregado: " + kilos + " kg de " + nombreProducto + " a Lps. " + precio);

  } else {
  System.out.println("Cantidad no válida.");
  }
  }

  System.out.print("¿Desea comprar otro producto? (si/no): ");
  String respuesta = lea.next();
  seguirVendiendo = respuesta.equalsIgnoreCase("si");

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
  System.out.println("Detalle de la compra:\n" + detallesTransaccion); // Mostrar detalles
  System.out.println("----------------");
  System.out.println("Subtotal: Lps. " + String.format("%.2f", subtotalTransaccion)); // Usar subtotalTransaccion
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

  // Si ya hay compras, no se pide de nuevo el proveedor
  if (!hayCompras) {
  System.out.print("Tipo de proveedor (A/B/C): ");
  tipoProveedorActual = lea.next().toUpperCase();
  hayCompras = true;
  } else {
  System.out.println("Proveedor actual: " + tipoProveedorActual);
  }

  System.out.print("Ingrese código del producto a comprar (1-Azúcar, 2-Avena, 3-Trigo, 4-Maíz): ");
  int cod = lea.nextInt();
  double precioCompra = 0.00;
  String nombreProducto = "";
  boolean puedeProveer = false;

  // Resetear detalles de la transaccion
  detallesTransaccion = "";
  subtotalTransaccion = 0.0;

  switch (cod) {
  case 1: // Azúcar
  nombreProducto = "Azúcar";
  precioCompra = 25.00;
  puedeProveer = tipoProveedorActual.equals("A");
  break;
  case 2: // Avena
  nombreProducto = "Avena";
  if (tipoProveedorActual.equals("B")) {
  precioCompra = 20.00;
  } else if (tipoProveedorActual.equals("C")) {
  precioCompra = 22.00;
  }
  puedeProveer = tipoProveedorActual.equals("B") || tipoProveedorActual.equals("C");
  break;
  case 3: // Trigo
  nombreProducto = "Trigo";
  precioCompra = 30.00;
  puedeProveer = tipoProveedorActual.equals("B");
  break;
  case 4: // Maíz
  nombreProducto = "Maíz";
  precioCompra = 18.00;
  puedeProveer = tipoProveedorActual.equals("A");
  break;
  default:
  System.out.println("Código de producto inválido.");
  }

  if (!puedeProveer) {
  System.out.println("Proveedor no vende dicho producto.");
  } else {
  System.out.print("¿Cuántos kilogramos desea comprar?: ");
  double kilos = lea.nextDouble();

  if (kilos > 0) {
  double totalCompra = kilos * precioCompra;

  if (caja >= totalCompra) {
  caja -= totalCompra;
  totalCompras += totalCompra;
  numCompras++;
  if (totalCompra > mayorGastoCompra) {
  mayorGastoCompra = totalCompra;
  }

  detallesTransaccion += String.format("%s - %.2f kg x Lps. %.2f = Lps. %.2f\n",
  nombreProducto, kilos, precioCompra, totalCompra);

  subtotalTransaccion = totalCompra; // Solo 1 producto por compra

  if (cod == 1)
  totalKilosAzucar += kilos;
  if (cod == 2)
  totalKilosAvena += kilos;
  if (cod == 3)
  totalKilosTrigo += kilos;
  if (cod == 4)
  totalKilosMaiz += kilos;

  System.out.println("Compra realizada de " + kilos + " kg de " + nombreProducto + " por Lps. " + String.format("%.2f", totalCompra));
  hayProductos = true; // Habilita Ventas
  } else {
  System.out.println("No se puede pagar la compra. No hay suficiente efectivo en caja.");
  }
  } else {
  System.out.println("Cantidad no válida.");
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
  if (maxKilosVendidos == totalKilosAzucar)
  productoEstrella = "Azúcar";
  else if (maxKilosVendidos == totalKilosAvena)
  productoEstrella = "Avena";
  else if (maxKilosVendidos == totalKilosTrigo)
  productoEstrella = "Trigo";
  else if (maxKilosVendidos == totalKilosMaiz)
  productoEstrella = "Maíz";

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
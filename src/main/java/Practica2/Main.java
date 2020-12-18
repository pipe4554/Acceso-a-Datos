package Practica2;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static Scanner kb = new Scanner(System.in);
	static Connection connection = null;
	static Statement statement = null;

	public static void main(String[] args) {

		try {
			connection = crearConexion();
		} catch (SQLException e) {
			System.out.println("No nos hemos podido conectar a la bbdd");
			e.printStackTrace();
		}
		if (connection != null) {

			try {
				statement = connection.createStatement();

			} catch (SQLException e) {
				System.out.println("La conexion esta cerrada");
				e.printStackTrace();
			}

			try {
				ResultSet resultSet = statement.executeQuery("select * from empleado");

				while (resultSet.next()) {
					String codigoEmpleado = resultSet.getString("codigo_empleado");
					String nombre = resultSet.getString("nombre");
				}
				Menu();
				connection.close();
			} catch (Exception e) {
				System.out.println("Fallo en la base de datos");
			}

		}

	}

	static Connection crearConexion() throws SQLException {
		Connection c;
		c = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/jardineria?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
				"root", "4DM1n4DM1n");
		return c;

	}

	public static void Menu() {
		int opcion = -1;

		while (opcion != 0) {
			try {
				System.out.println(
						"Introduzca la opción deseada:\n 1.Añadir un cliente. \n 2.Mostrar un cliente. \n 3.Mostrar todos los clientes."
								+ "\n 4.Buscar un cliente. \n 5.Editar un productor. \n 0.Salir del programa");
				opcion = kb.nextInt();
				kb.nextLine();
				switch (opcion) {
				case 1:
					addClient();
					break;
				case 2:
					showClient();
					break;
				case 3:
					showAllClients();
					break;
				case 4:
					searchClient();
					break;
				case 5:
					editProduct();
					break;
				case 0:
					System.out.println("Cerrando el programa");
					System.exit(0);
				default:
					System.out.println("El valor no coincide con las opciones dadas");
					break;
				}

			} catch (Exception e) {
				System.out.println("Has introducido un valor incorrecto");
			}
		}

	}
	
	public static void addClient() {
		// -------------------------------------------------------------------    Añadir un cliente

		System.out.println("Introduzca los datos del cliente:");
		
		Cliente client = new Cliente(); //cliente vacio para archivar temporalmente los datos del cliente
		try {
			System.out.println("Introduzca el Código de cliente");
			client.setCodigo_cliente(kb.nextInt());
			kb.nextLine();
			System.out.println("Introduzca el nombre de cliente");
			client.setNombre(kb.nextLine());
			System.out.println("Introduzca el nombre de contacto");
			client.setNombre_contacto(kb.nextLine());
			System.out.println("Introduzca el apellido de contacto");
			client.setApellido_contacto(kb.nextLine());
			System.out.println("Introduzca el telefono");
			client.setTelefono(kb.nextLine());
			System.out.println("Introduzca el numero de fax");
			client.setFax(kb.nextLine());
			System.out.println("Introduzca el la dirección 1");
			client.setLinea_direccion1(kb.nextLine());
			System.out.println("Introduzca el la dirección 2");
			client.setLinea_direccion2(kb.nextLine());
			System.out.println("Introduzca la ciudad");
			client.setCiudad(kb.nextLine());
			System.out.println("Introduzca la región");
			client.setRegion(kb.nextLine());
			System.out.println("Introduzca el país");
			client.setPais(kb.nextLine());
			System.out.println("Introduzca el código postal");
			client.setCodigo_postal(kb.nextLine());
			System.out.println("Introduzca el código de empleado representante de ventas");
			client.setCodigo_empleado_rep_ventas(kb.nextInt());
			System.out.println("Introduzca el límite de crédito");
			client.setLimite_credito(kb.nextDouble());

			System.out.println(client.toString() + "se ha guardado");

			String sentenciaSql = "INSERT INTO cliente (codigo_cliente, nombre_cliente, nombre_contacto, apellido_contacto, telefono, fax, linea_direccion1, linea_direccion2, ciudad, region, pais, codigo_postal, codigo_empleado_rep_ventas, limite_credito) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement sentencia = null;
			
			try {
				sentencia = connection.prepareStatement(sentenciaSql);

				sentencia.setInt(1, client.getCodigo_cliente());
				sentencia.setString(2, client.getNombre());
				sentencia.setString(3, client.getNombre_contacto());
				sentencia.setString(4, client.getApellido_contacto());
				sentencia.setString(5, client.getTelefono());
				sentencia.setString(6, client.getFax());
				sentencia.setString(7, client.getLinea_direccion1());
				sentencia.setString(8, client.getLinea_direccion2());
				sentencia.setString(9, client.getCiudad());
				sentencia.setString(10, client.getRegion());
				sentencia.setString(11, client.getPais());
				sentencia.setString(12, client.getCodigo_postal());
				sentencia.setInt(13, client.getCodigo_empleado_rep_ventas());
				sentencia.setDouble(14, client.getLimite_credito());
				
				sentencia.executeUpdate();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			} finally {
				if (sentencia != null)
					try {
						sentencia.close();
					} catch (SQLException sqle) {
						sqle.printStackTrace();
					}
			}

		} catch (Exception e) {
			System.out.println("Has introducido un valor erroneo,volviendo al menu");
		}

	}

	public static void showClient() {
		// -------------------------------------------------------------------------- mostrar un cliente determinado
		
		int codigo_cliente;
		System.out.println("Introduzca el código del cliente");
		
		codigo_cliente = kb.nextInt();
		kb.nextLine();

		String sentenciaSql = "SELECT * FROM cliente WHERE codigo_cliente = ?";
		PreparedStatement sentencia = null;
		ResultSet resultado = null;

		try {
			sentencia = connection.prepareStatement(sentenciaSql);
			
			sentencia.setInt(1, codigo_cliente);

			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		try {
			
			resultado = sentencia.executeQuery();

			while (resultado.next()) {
				printCliente(resultado);
			}
		} catch (Exception sqle) {
			
			sqle.printStackTrace();
		} finally {
			if (sentencia != null)
				try {
					sentencia.close();
					resultado.close();
				} catch (SQLException sqle) {
					sqle.printStackTrace();
				}
		}

	}

	public static void showAllClients() {
		
		// ---------------------------------------------- mostrar todos los clientes de la lista clientes 

		System.out.println("Mostrando todos los clientes.\n-----------------------------");
		// SELECT * FROM cliente
		String sentenciaSql = "SELECT * FROM cliente";
		PreparedStatement sentencia = null;
		ResultSet resultado = null;

		try {
			sentencia = connection.prepareStatement(sentenciaSql);
			resultado = sentencia.executeQuery();

			while (resultado.next()) {
				printCliente(resultado);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			if (sentencia != null)
				try {
					sentencia.close();
					resultado.close();
				} catch (SQLException sqle) {
					sqle.printStackTrace();
				}
		}

	}

	public static void searchClient() {
		// ---------------------------------------------------------------------------- buscar un cliente de la tabla clientes
		
		String nombre_cliente;
		
		System.out.println("Introduzca el nombre del cliente.\n-----------------------------");
		
		nombre_cliente = kb.nextLine();
		
		String sentenciaSql = "SELECT * FROM cliente WHERE nombre_cliente LIKE '%" + nombre_cliente + "%'";
		PreparedStatement sentencia = null;
		ResultSet resultado = null;

		try {
			sentencia = connection.prepareStatement(sentenciaSql);
			resultado = sentencia.executeQuery();

			while (resultado.next()) {
				printCliente(resultado);
			}
		} catch (Exception e) {
			System.out.println("Ha ocurrido un fallo en el programa");
			e.printStackTrace();
		} finally {
			if (sentencia != null)
				try {
					sentencia.close();
					resultado.close();
				} catch (Exception e) {
					System.out.println("Ha ocurrido un fallo en el programa");
					e.printStackTrace();
				}
		}

	}


	public static void editProduct() {
		
		// Editar un producto
		Producto prod = new Producto();
		
		System.out.println("Introduzca el código del producto.");
		prod.setCodigo_producto(kb.nextLine());
		System.out.println("Introduce los nuevos datos del producto. \n Introduce el nombre");
		prod.setNombre(kb.nextLine());
		System.out.println("Introduce la gama");
		prod.setGama(kb.nextLine());
		System.out.println("Introduce las dimensiones");
		prod.setDimensiones(kb.nextLine());
		System.out.println("Introduce el proveedor");
		prod.setProveedor(kb.nextLine());
		System.out.println("Introduce la descripción");
		prod.setDescripcion(kb.nextLine());
		System.out.println("Introduce la cantidad en stock");
		prod.setCantidad_en_stock(kb.nextLine());
		System.out.println("Introduce el precio de venta");
		prod.setPrecio_venta(kb.nextDouble());
		System.out.println("Introduce el precio del proveedor");
		prod.setPrecio_proveedor(kb.nextDouble());
		
		

		String sentenciaSql = "UPDATE producto SET nombre = ?, gama = ?, dimensiones = ?, proveedor = ?, descripcion = ?, cantidad_en_stock = ?, precio_venta = ?, precio_proveedor = ?" + "WHERE nombre = ?";
		PreparedStatement sentencia = null;

		try {
			sentencia = connection.prepareStatement(sentenciaSql);
			
			sentencia.setString(1, prod.getNombre());
			sentencia.setString(2, prod.getGama());
			sentencia.setString(3, prod.getDimensiones());
			sentencia.setString(4, prod.getProveedor());
			sentencia.setString(5, prod.getDescripcion());
			sentencia.setString(6, prod.getCantidad_en_stock());
			sentencia.setDouble(7, prod.getPrecio_venta());
			sentencia.setDouble(8, prod.getPrecio_proveedor());
			sentencia.setString(9, prod.getCodigo_producto());
			
			sentencia.executeUpdate();
			
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			if (sentencia != null)
				try {
					sentencia.close();
				} catch (Exception e) {
					System.out.println("Ha ocurrido un fallo en el programa");
					e.printStackTrace();
				}
		}

	}
	
	private static void printCliente(ResultSet resultado) throws SQLException {
		System.out.println("Código de cliente: " + resultado.getInt(1));
		System.out.println("Nombre de cliente: " + resultado.getString(2));
		System.out.println("Nombre de contacto: " + resultado.getString(3));
		System.out.println("Apellido de contacto: " + resultado.getString(4));
		System.out.println("Teléfono: " + resultado.getString(5));
		System.out.println("Fax: " + resultado.getString(6));
		System.out.println("Dirección 1: " + resultado.getString(7));
		System.out.println("Dirección: " + resultado.getString(8));
		System.out.println("Ciudad: " + resultado.getString(9));
		System.out.println("Región: " + resultado.getString(10));
		System.out.println("País: " + resultado.getString(11));
		System.out.println("Código postal: " + resultado.getString(12));
		System.out.println("código de empleado representante de ventas: " + resultado.getInt(13));
		System.out.println("Límite de crédito: " + resultado.getFloat(14));
		System.out.println("---------------------------------------------------------------");
	}

}
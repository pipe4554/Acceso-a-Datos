package Practica2;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static Scanner kb = new Scanner(System.in);

	public static void main(String[] args) {
		Connection connection = null;
		try {
			connection = crearConexion();
		} catch (SQLException e) {
			System.out.println("No nos hemos podido conectar a la bbdd");
			e.printStackTrace();
		}
		if (connection != null) {
			Statement statement = null;
			try {
				statement = connection.createStatement();
				System.out.println("statement");

			} catch (SQLException e) {
				System.out.println("La conexion esta cerrada");
				e.printStackTrace();
			}

			try {
				ResultSet resultSet = statement.executeQuery("select * from empleado");
				System.out.println("Result set");

				while (resultSet.next()) {
					String codigoEmpleado = resultSet.getString("codigo_empleado");
					String nombre = resultSet.getString("nombre");
				}
				Menu();
				connection.close();
			} catch (Exception e) {
				// TODO: handle exception
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
		int opcion = 0;
		System.out.println(
				"Introduzca la opción deseada:\n 1.Añadir un cliente. \n 2.Mostrar un cliente. \n 3.Mostrar todos los clientes."
						+ "\n 4.Buscar un cliente. \n 5.Editar un productor.");
		opcion = kb.nextInt();

		while (opcion != 0) {
			try {
				opcion = kb.nextInt();
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
		// Añadir un cliente

		System.out.println("Introduzca los datos del cliente:");

		Cliente client = new Cliente();
		boolean confirmacion = false;
		
		while (confirmacion = true) {
			try {
				System.out.println("Introduzca el Código de cliente");
				client.setCodigo_cliente(kb.nextInt());
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

				System.out.println(client.toString());
				
			} catch (Exception e) {
				System.out.println("Has introducido un valor erroneo, introduce todos los datos de nuevo");
			}
		}

		

	}

	public static void showClient() {
		// mostrar un cliente determinado
	}

	public static void showAllClients() {
		// mostrar todos los clientes de la lista clientes
	}

	public static void searchClient() {
		// buscar un cliente de la tabla clientes
	}

	public static void editProduct() {
		// Editar un producto
	}

}

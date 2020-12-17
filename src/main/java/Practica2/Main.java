package Practica2;

import java.sql.*;
import java.util.Scanner;

public class Main {

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

	public void Menu() {
		int opcion = 0;
		Scanner kb = new Scanner(System.in);
		while (opcion != 0) {
			try {
				opcion = kb.nextInt();
				switch (opcion) {
				case 1:addClient();break;
				case 2:showClient();break;
				case 3:showAllClients();break;
				case 4:searchClient();break;
				case 5:editProduct();break;

				default:break;
				}
				
			} catch (Exception e) {
				System.out.println("Has introducido un valor incorrecto");
			}
		}

	}
	
	public void addClient() {
		//añadir un cliente
		System.out.println("Introduzca los datos del cliente");
		
	}
	
	public void showClient() {
		//mostrar un cliente determinado
	}
	
	public void showAllClients() {
		//mostrar todos los clientes de la lista clientes
	}
	
	public void searchClient() {
		//buscar un cliente de la tabla clientes
	}
	
	public void editProduct() {
		//Editar un producto 
	}

}

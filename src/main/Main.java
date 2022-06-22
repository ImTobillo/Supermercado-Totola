package main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import productos.*;
import usuarios.*;
import mercado.*;
import excepciones.*;
import carrito.*;

public class Main {

	static Scanner teclado;
	static Mercado mercadoTotola;

	public static void main(String[] args) {
		teclado = new Scanner(System.in);
		
		crearArchivo();
		
		mercadoTotola = new Mercado("Mercado TOTOLA");
		
		try {
			Gerente gerente = new Gerente("Gerardo", "Messi", 45, "21569784", "gerardoMessi", "12345678", "Libre", 0);
			mercadoTotola.agregarUsuario(gerente);
		} catch (UsuarioIncorrectoException e) {
			System.out.println(e.getMessage());
		}

		menuInicial();

		teclado.close();
	}

	public static void crearArchivo()
	{	
		try {
			FileOutputStream fileOS_ListaUsuarios = new FileOutputStream("ListaUsuarios,bin");
			ObjectOutputStream objectOS_ListaUsuarios = new ObjectOutputStream(fileOS_ListaUsuarios);
			
			
			
		} catch (NotSerializableException e) {
			System.out.println(e.getMessage().concat("NO SE HA IMPLEMENTADO LA INTERFAZ SERIALIZABLE"));
		}
		catch (IOException e) {
			System.out.println(e.getMessage().concat("HUBO UN ERROR EN LA ESCRITURA."));
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void menuInicial() {
		int repetir = 1, opcion = 1;

		while (repetir == 1) {
			try {
				System.out.println(
						"�DESEA INGRESAR O REGISTRARSE?\n" + "	   1  	   - INGRESAR\n" + "OTRO N�MERO - REGISTRARSE");

				opcion = teclado.nextInt();
				teclado.nextLine();

				if (opcion == 1)
					ingresar();
				else
					registrarse(1);

				System.out.println("�DESEA SEGUIR EN EL MEN� INICIAL?\n" + "     1      - S�\n" + "OTRO N�MERO - NO");
				repetir = teclado.nextInt();
				teclado.nextLine();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static void ingresar() {
		int tipoUsuario = 1, opcion = 0, repetir = 1;
		boolean flag = true;

		while (flag) {
			try {
				System.out.println("�QU� TIPO DE USUARIO USA?\n" + "	   1 	  - CLIENTE\n"
						+ "	   2 	  - CAJERO\n" + "	   3 	  - REPOSITOR\n" + "	   4	  - SEGURIDAD\n"
						+ "	   5 	  - GERENTE");
				tipoUsuario = teclado.nextInt();
				teclado.nextLine();
				
				if (tipoUsuario >= 1 && tipoUsuario <= 5) {
					System.out.println("INGRESE NOMBRE DE USUARIO: ");
					String nombreUsuario = teclado.nextLine();
					System.out.println("INGRESE CONTRASE�A: ");
					String contrase�a = teclado.nextLine();

					switch (tipoUsuario) {
					case 1:
						Cliente cliente = (Cliente) mercadoTotola.login(nombreUsuario, contrase�a);

						if (cliente != null) {
							flag = false;
							menuCliente();
						}
						break;
					case 2:
						Cajero cajero = (Cajero) mercadoTotola.login(nombreUsuario, contrase�a);

						if (cajero != null) {
							flag = false;
							System.out.println(cajero.toString());
						}

						break;
					case 3:
						Repositor repositor = (Repositor) mercadoTotola.login(nombreUsuario, contrase�a);

						if (repositor != null) {
							flag = false;
							repetir = 1;

							while (repetir == 1) {
								System.out.println(
										"QUE DESEA HACER: 1 - VER SUS DATOS \n 2 - REPONER PRODUCTOS \nOTRO NUMERO - AGREGAR NUEVOS PRODUCTOS");
								opcion = teclado.nextInt();
								teclado.nextLine();

								if (opcion == 1) {
									System.out.println(repositor.toString());
								} else if (opcion == 2) {

									System.out.println(mercadoTotola.mostrarTodosLosProductos());

									boolean flagA = false;
									String nombre = " ";

									while (!flagA) {

										System.out.println("Ingrese el producto a reponer");
										nombre = teclado.nextLine();
										flagA = mercadoTotola.existeOno(nombre);
									}

									System.out.println("Que cantidad quiere reponer?");
									int cant = teclado.nextInt();
									teclado.nextLine();
									mercadoTotola.reponer(nombre, cant);

								} else {
									boolean flagB = false;
									String nombreProd = "";

									System.out.println("Que tipo de producto desea agregar?\n" + "1- Congelado\n"
											+ "2- Alimento cultivado\n" + "3- Limpieza\n");
									int tipo = teclado.nextInt();
									teclado.nextLine();

									while (!flagB) {
										System.out.println("Ingrese el nombre del producto");
										nombreProd = teclado.nextLine();

										if (!mercadoTotola.existeOno(nombreProd))
											flagB = true;
										else
											System.out.println("El producto ya existe.");
									}

									System.out.println("INGRESE EL PRECIO: ");
									float precio = teclado.nextFloat();
									teclado.nextLine();
									System.out.println("INGRESAR STOCK: ");
									int stock = teclado.nextInt();
									teclado.nextLine();
									System.out.println("INGRESAR ID: ");
									int id = teclado.nextInt();
									teclado.nextLine();

									if (tipo == 1) {
										System.out.println("INGRESE TEMPERATURA ADECUADA: ");
										float tempAdecuada = teclado.nextFloat();
										teclado.nextLine();
										Congelado nuevo = new Congelado(nombreProd, precio, stock, id, tempAdecuada);
										mercadoTotola.agregarProd(nuevo, 1);
									} else if (tipo == 2) {
										System.out.println("INGRESE EL TIPO DE ALIMENTO CULTIVADO: ");
										String tipoAlimentoC = teclado.nextLine();
										AlimentoCultivado nuevo = new AlimentoCultivado(nombreProd, precio, stock, id,
												tipoAlimentoC);
										mercadoTotola.agregarProd(nuevo, 2);
									} else {
										System.out.println("INGRESE TIPO DE ASEO: ");
										String tipoAseo = teclado.nextLine();
										Limpieza nuevo = new Limpieza(nombreProd, precio, stock, id, tipoAseo);
										mercadoTotola.agregarProd(nuevo, 3);
									}
								}

								System.out.println("�DESEA SEGUIR EN EL MEN� DE REPOSITOR?\n" + "     1      - S�\n"
										+ "OTRO N�MERO - NO");

								repetir = teclado.nextInt();
								teclado.nextLine();
							}

						}

						break;
					case 4:
						Seguridad seguridad = (Seguridad) mercadoTotola.login(nombreUsuario, contrase�a);

						if (seguridad != null) {
							flag = false;
							System.out.println(seguridad.toString());
						}

						break;
					case 5: // gerente
						Gerente gerente = (Gerente) mercadoTotola.login(nombreUsuario, contrase�a);

						if (gerente != null) {
							flag = false;
							int opcionGerente;
							repetir = 1;

							while (repetir == 1) {
								System.out.println("INGRESE UNA OPCION: \n" + "1 - AGREGAR EMPLEADO\n"
										+ "2 - MODIFICAR SUELDO A EMPLEADO\n" + "3 - DESPEDIR EMPLEADO.");
								opcionGerente = teclado.nextInt();
								teclado.nextLine();

								if (opcionGerente == 1) {
									System.out.println("�QU� TIPO DE EMPLEADO DESEA AGREGAR?\n" + "1 - CAJERO\n"
											+ "2 - REPOSITOR\n" + "3 - SEGURIDAD");
									int tipoEmpleadoAAgregar = teclado.nextInt();
									teclado.nextLine();

									registrarse(tipoEmpleadoAAgregar + 1);

								} else if (opcionGerente == 2) {
									Empleado empEncontrado = null;
									String nombreEmp;

									System.out.println(mercadoTotola.mostrarEmpleados());
									System.out.println("INGRESE NOMBRE DE USUARIO DEL EMPLEADO A MODIFICAR SUELDO: ");
									nombreEmp = teclado.nextLine();
									empEncontrado = mercadoTotola.buscarEmpleado(nombreEmp);

									while (empEncontrado == null) {
										System.out.println("EL NOMBRE DE USUARIO NO EXISTE. INGRESE OTRO:");
										nombreEmp = teclado.nextLine();
										empEncontrado = mercadoTotola.buscarEmpleado(nombreEmp);
									}
									
									System.out.println(
											"INGRESE HORAS TRABAJADAS DE " + empEncontrado.getNombreYApellido());
									int hsTrabajadas = teclado.nextInt();
									empEncontrado.setHorasTrabajadas(hsTrabajadas);
									teclado.nextLine();

									System.out.println(
											"INGRESE HORAS EXTRAS TRABAJADAS DE " + empEncontrado.getNombreYApellido());
									int hsExtrasTrabajadas = teclado.nextInt();
									empEncontrado.setHorasExtras(hsExtrasTrabajadas);
									teclado.nextLine();

								} else {
									System.out.println(mercadoTotola.mostrarEmpleados());
									System.out.println("INGRESE NOMBRE DE USUARIO DEL EMPLEADO A DESPEDIR: ");
									String nombreEmp = teclado.nextLine();

									while (!mercadoTotola.eliminarEmpleado(nombreEmp)) {
										System.out.println(
												"EL USUARIO NO EXISTE. INGRESE SU NOMBRE DE USUARIO CORRECTAMENTE: ");
										nombreEmp = teclado.nextLine();
									}

									System.out.println(mercadoTotola.mostrarEmpleados());

								}

								System.out.println(
										"�DESEA SEGUIR EN EL MEN�?\n" + "     1      - S�\n" + "OTRO N�MERO - NO");

								repetir = teclado.nextInt();
								teclado.nextLine();
							}
						}

						break;
					default:
						break;
					}
				}

			} catch (UsuarioIncorrectoException e) {
				System.out.println(e.getMessage());
			} catch (Contrase�aIncorrectaException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static void menuCliente() {
		int repetir = 1, opcion, cantUnidades;
		Producto prodPedido = null;
		Carrito carrito = new Carrito();

		while (repetir == 1) {
			try {
				System.out.println("INGRESE UNA OPCI�N:\n" + "1 - VER LISTADO DE PRODUCTOS.\n"
						+ "2 - BUSCAR PRODUCTO.\n" + "3 - VER CARRITO.\n" + "4 - SALIR DEL MEN�");

				opcion = teclado.nextInt();
				teclado.nextLine();

				switch (opcion) {
				case 1:
					System.out.println(mercadoTotola.mostrarTodosLosProductos());
					;

					break;
				case 2:
					String nomProducto;
					System.out.println("INGRESE EL NOMBRE DEL PRODUCTO:");
					nomProducto = teclado.nextLine();
					prodPedido = mercadoTotola.retornarProducto(nomProducto);

					while (prodPedido == null) {
						System.out.println("EL PRODUCTO NO EXISTE. INGRESE NUEVAMENTE: ");
						nomProducto = teclado.nextLine();
						prodPedido = mercadoTotola.retornarProducto(nomProducto);
					}

					System.out.println("�QU� CANTIDAD PRECISA DEL PRODUCTO?");
					cantUnidades = teclado.nextInt();
					teclado.nextLine();

					carrito.agregarACarrito(
							new ItemPedido(prodPedido.getNombre(), prodPedido.getPrecio(), cantUnidades));

					break;
				case 3:
					System.out.println(carrito.mostrarCarrito());

					break;
				case 4:
					repetir = 0;
					break;
				default:
					break;
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}

	public static void registrarse(int tipoARegistrar) // 1 - Cliente / 2 - Cajero / 3 - Repositor / 4 - Seguridad
	{
		try {
			System.out.println("INGRESE NOMBRE: ");
			String nombre = teclado.nextLine();
			System.out.println("INGRESE APELLIDO: ");
			String apellido = teclado.nextLine();
			System.out.println("INGRESE EDAD: ");
			int edad = teclado.nextInt();
			while (edad < 18) {
				System.out.println("NO SE PUEDEN REGISTRAR MENORES DE 18 A�OS.");
				edad = teclado.nextInt();
			}
			teclado.nextLine();
			System.out.println("INGRESE DNI: ");
			String dni = teclado.nextLine();
			System.out.println("INGRESE NOMBRE DE USUARIO");
			String nombreUsuario = teclado.nextLine();
			while (mercadoTotola.existeUsuario(nombreUsuario)) {
				System.out.println("USUARIO YA UTILIZADO. PRUEBE CON OTRO.");
				nombreUsuario = teclado.nextLine();
			}
			System.out.println("INGRESE UNA CONTRASE�A DE MINIMO 8 CARACTERES:");
			String contrase�a = teclado.nextLine();
			while (contrase�a.length() < 8) {
				System.out.println("DEBE INGRESAR UNA CONTRASE�A MAYOR O IGUAL A 8 CARACTERES");
				contrase�a = teclado.nextLine();
			}

			if (tipoARegistrar == 1) {

				mercadoTotola.agregarUsuario(new Cliente(nombre, apellido, edad, dni, nombreUsuario, contrase�a));
			} else {

				System.out.println("INGRESE EL HORARIO EJ:(00:00 A 00:01):");
				String horario = teclado.nextLine();
				System.out.println("INGRESE CUANTO COBRARA POR HORA:");
				float pagoPorHora = teclado.nextFloat();
				teclado.nextLine();

				if (tipoARegistrar == 2) {

					System.out.println("ASIGNELE UNA CAJA:");
					int caja = teclado.nextInt();
					teclado.nextLine();

					mercadoTotola.agregarUsuario(new Cajero(nombre, apellido, edad, dni, nombreUsuario, contrase�a,
							horario, caja, pagoPorHora));

				} else if (tipoARegistrar == 3) {
					mercadoTotola.agregarUsuario(new Repositor(nombre, apellido, edad, dni, nombreUsuario, contrase�a,
							horario, pagoPorHora));
				} else {
					System.out.println("QUE ZONA VIGILARA:");
					String zonaAVigilar = teclado.nextLine();

					mercadoTotola.agregarUsuario(new Seguridad(nombre, apellido, edad, dni, nombreUsuario, contrase�a,
							horario, zonaAVigilar, pagoPorHora));
				}
			}

		} catch (UsuarioIncorrectoException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

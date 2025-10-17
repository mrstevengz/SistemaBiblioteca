// java
package run;

import config.JPAUtil;
import dao.daoAutor;
import dao.daoCategoria;
import dao.daoLibro;
import entities.Autor;
import entities.Categoria;
import entities.Libro;
import jakarta.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();
        daoAutor autorDAO = new daoAutor(em);
        daoCategoria categoriaDAO = new daoCategoria(em);
        daoLibro libroDAO = new daoLibro(em);

        boolean running = true;
        while (running) {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Autores");
            System.out.println("2. Libros");
            System.out.println("3. Categorías");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            String opt = sc.nextLine().trim();
            switch (opt) {
                case "1" -> menuAutores(autorDAO);
                case "2" -> menuLibros(libroDAO, autorDAO, categoriaDAO);
                case "3" -> menuCategorias(categoriaDAO);
                case "0" -> running = false;
                default -> System.out.println("Opción no válida.");
            }
        }

        em.close();
        JPAUtil.close();
        sc.close();
        System.out.println("Aplicación finalizada.");
    }

    //Menu de los autores java 
    private static void menuAutores(daoAutor autorDAO) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Autores ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Actualizar");
            System.out.println("5. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            switch (sc.nextLine().trim()) {
                case "1" -> listarAutores(autorDAO);
                case "2" -> crearAutor(autorDAO);
                case "3" -> buscarAutor(autorDAO);
                case "4" -> actualizarAutor(autorDAO);
                case "5" -> eliminarAutor(autorDAO);
                case "0" -> salir = true;
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private static void listarAutores(daoAutor autorDAO) {
        List<Autor> lista = autorDAO.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay autores.");
            return;
        }
        lista.forEach(a -> System.out.println(a.getId() + " - " + a.getNombre() + " (" + a.getNacionalidad() + ")"));
    }

    private static void crearAutor(daoAutor autorDAO) {
        try {
            Autor a = new Autor();
            System.out.print("Nombre: ");
            a.setNombre(sc.nextLine().trim());
            System.out.print("Nacionalidad: ");
            a.setNacionalidad(sc.nextLine().trim());
            System.out.print("Fecha nacimiento (yyyy-MM-dd): ");
            String f = sc.nextLine().trim();
            a.setFechanacimiento(sdf.parse(f));
            autorDAO.guardar(a);
            System.out.println("Autor creado con ID: " + a.getId());
        } catch (ParseException e) {
            System.out.println("Formato de fecha inválido.");
        }
    }

    private static void buscarAutor(daoAutor autorDAO) {
        System.out.print("ID: ");
        int id = parseIntInput();
        Autor a = autorDAO.buscarPorId(id);
        if (a == null) System.out.println("Autor no encontrado.");
        else System.out.println(a.getId() + " - " + a.getNombre() + " - " + a.getNacionalidad());
    }

    private static void actualizarAutor(daoAutor autorDAO) {
        System.out.print("ID a actualizar: ");
        int id = parseIntInput();
        Autor existente = autorDAO.buscarPorId(id);
        if (existente == null) {
            System.out.println("Autor no encontrado.");
            return;
        }
        try {
            System.out.print("Nuevo nombre (" + existente.getNombre() + "): ");
            String nombre = sc.nextLine().trim();
            if (!nombre.isEmpty()) existente.setNombre(nombre);
            System.out.print("Nueva nacionalidad (" + existente.getNacionalidad() + "): ");
            String nac = sc.nextLine().trim();
            if (!nac.isEmpty()) existente.setNacionalidad(nac);
            System.out.print("Nueva fecha nacimiento (yyyy-MM-dd) o vacío para mantener: ");
            String f = sc.nextLine().trim();
            if (!f.isEmpty()) existente.setFechanacimiento(sdf.parse(f));
            autorDAO.actualizar(id, existente);
            System.out.println("Autor actualizado.");
        } catch (ParseException e) {
            System.out.println("Formato de fecha inválido.");
        }
    }

    private static void eliminarAutor(daoAutor autorDAO) {
        System.out.print("ID a eliminar: ");
        int id = parseIntInput();
        autorDAO.eliminar(id);
        System.out.println("Operación eliminar completada.");
    }

    private static void menuCategorias(daoCategoria categoriaDAO) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Categorías ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Actualizar");
            System.out.println("5. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            switch (sc.nextLine().trim()) {
                case "1" -> listarCategorias(categoriaDAO);
                case "2" -> crearCategoria(categoriaDAO);
                case "3" -> buscarCategoria(categoriaDAO);
                case "4" -> actualizarCategoria(categoriaDAO);
                case "5" -> eliminarCategoria(categoriaDAO);
                case "0" -> salir = true;
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private static void listarCategorias(daoCategoria categoriaDAO) {
        List<Categoria> lista = categoriaDAO.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay categorías.");
            return;
        }
        lista.forEach(c -> System.out.println(c.getId() + " - " + c.getNombre()));
    }

    private static void crearCategoria(daoCategoria categoriaDAO) {
        Categoria c = new Categoria();
        System.out.print("Nombre categoría: ");
        c.setNombre(sc.nextLine().trim());
        categoriaDAO.guardar(c);
        System.out.println("Categoría creada con ID: " + c.getId());
    }

    private static void buscarCategoria(daoCategoria categoriaDAO) {
        System.out.print("ID: ");
        int id = parseIntInput();
        Categoria c = categoriaDAO.buscarPorId(id);
        if (c == null) System.out.println("Categoría no encontrada.");
        else System.out.println(c.getId() + " - " + c.getNombre());
    }

    private static void actualizarCategoria(daoCategoria categoriaDAO) {
        System.out.print("ID a actualizar: ");
        int id = parseIntInput();
        Categoria existente = categoriaDAO.buscarPorId(id);
        if (existente == null) {
            System.out.println("Categoría no encontrada.");
            return;
        }
        System.out.print("Nuevo nombre (" + existente.getNombre() + "): ");
        String nombre = sc.nextLine().trim();
        if (!nombre.isEmpty()) existente.setNombre(nombre);
        categoriaDAO.actualizar(id, existente);
        System.out.println("Categoría actualizada.");
    }

    private static void eliminarCategoria(daoCategoria categoriaDAO) {
        System.out.print("ID a eliminar: ");
        int id = parseIntInput();
        categoriaDAO.eliminar(id);
        System.out.println("Operación eliminar completada.");
    }

    private static void menuLibros(daoLibro libroDAO, daoAutor autorDAO, daoCategoria categoriaDAO) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Libros ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Actualizar");
            System.out.println("5. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            switch (sc.nextLine().trim()) {
                case "1" -> listarLibros(libroDAO);
                case "2" -> crearLibro(libroDAO, autorDAO, categoriaDAO);
                case "3" -> buscarLibro(libroDAO);
                case "4" -> actualizarLibro(libroDAO, autorDAO, categoriaDAO);
                case "5" -> eliminarLibro(libroDAO);
                case "0" -> salir = true;
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private static void listarLibros(daoLibro libroDAO) {
        List<Libro> lista = libroDAO.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay libros.");
            return;
        }
        lista.forEach(l -> System.out.println(l.toString()));
    }

    private static void crearLibro(daoLibro libroDAO, daoAutor autorDAO, daoCategoria categoriaDAO) {
        try {
            Libro l = new Libro();
            System.out.print("Título: ");
            l.setTitulo(sc.nextLine().trim());
            System.out.print("Fecha publicación (yyyy-MM-dd): ");
            String f = sc.nextLine().trim();
            l.setAnno(sdf.parse(f));

            // Asociar autor
            listarAutores(autorDAO);
            System.out.print("ID autor (o vacío para ninguno): ");
            String autorIdStr = sc.nextLine().trim();
            if (!autorIdStr.isEmpty()) {
                int aid = Integer.parseInt(autorIdStr);
                Autor a = autorDAO.buscarPorId(aid);
                l.setAutor(a);
            }

            // Asociar categorías (ids separados por coma)
            listarCategorias(categoriaDAO);
            System.out.print("IDs categorías separados por coma (o vacío): ");
            String cats = sc.nextLine().trim();
            if (!cats.isEmpty()) {
                Set<Categoria> set = new HashSet<>();
                String[] parts = cats.split(",");
                for (String p : parts) {
                    try {
                        int cid = Integer.parseInt(p.trim());
                        Categoria c = categoriaDAO.buscarPorId(cid);
                        if (c != null) set.add(c);
                    } catch (NumberFormatException ignored) {}
                }
                l.setCategorias(set);
            }

            libroDAO.guardar(l);
            System.out.println("Libro creado con ID: " + l.getId());
        } catch (ParseException e) {
            System.out.println("Formato de fecha inválido.");
        }
    }

    private static void buscarLibro(daoLibro libroDAO) {
        System.out.print("ID: ");
        int id = parseIntInput();
        Libro l = libroDAO.buscarPorId(id);
        if (l == null) System.out.println("Libro no encontrado.");
        else System.out.println(l);
    }

    private static void actualizarLibro(daoLibro libroDAO, daoAutor autorDAO, daoCategoria categoriaDAO) {
        System.out.print("ID a actualizar: ");
        int id = parseIntInput();
        Libro existente = libroDAO.buscarPorId(id);
        if (existente == null) {
            System.out.println("Libro no encontrado.");
            return;
        }
        try {
            System.out.print("Nuevo título (" + existente.getTitulo() + "): ");
            String t = sc.nextLine().trim();
            if (!t.isEmpty()) existente.setTitulo(t);
            System.out.print("Nueva fecha publicación (yyyy-MM-dd) o vacío: ");
            String f = sc.nextLine().trim();
            if (!f.isEmpty()) existente.setAnno(sdf.parse(f));

            System.out.print("Cambiar autor? (s/n): ");
            if ("s".equalsIgnoreCase(sc.nextLine().trim())) {
                listarAutores(autorDAO);
                System.out.print("ID autor (o vacío para ninguno): ");
                String aid = sc.nextLine().trim();
                if (!aid.isEmpty()) existente.setAutor(autorDAO.buscarPorId(Integer.parseInt(aid)));
                else existente.setAutor(null);
            }

            System.out.print("Cambiar categorías? (s/n): ");
            if ("s".equalsIgnoreCase(sc.nextLine().trim())) {
                listarCategorias(categoriaDAO);
                System.out.print("IDs categorías separados por coma (o vacío): ");
                String cats = sc.nextLine().trim();
                if (cats.isEmpty()) existente.setCategorias(null);
                else {
                    Set<Categoria> set = new HashSet<>();
                    for (String p : cats.split(",")) {
                        try {
                            Categoria c = categoriaDAO.buscarPorId(Integer.parseInt(p.trim()));
                            if (c != null) set.add(c);
                        } catch (NumberFormatException ignored) {}
                    }
                    existente.setCategorias(set);
                }
            }

            libroDAO.actualizar(id, existente);
            System.out.println("Libro actualizado.");
        } catch (ParseException e) {
            System.out.println("Formato de fecha inválido.");
        }
    }

    private static void eliminarLibro(daoLibro libroDAO) {
        System.out.print("ID a eliminar: ");
        int id = parseIntInput();
        libroDAO.eliminar(id);
        System.out.println("Operación eliminar completada.");
    }

    private static int parseIntInput() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (Exception e) {
            return -1;
        }
    }
}

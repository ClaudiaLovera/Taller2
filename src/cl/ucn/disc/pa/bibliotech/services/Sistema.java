/*
 * Copyright (c) 2023. Programacion Avanzada, DISC, UCN.
 */
package cl.ucn.disc.pa.bibliotech.services;

import cl.ucn.disc.pa.bibliotech.model.Libro;
import cl.ucn.disc.pa.bibliotech.model.Socio;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.princeton.cs.stdlib.StdIn;
import edu.princeton.cs.stdlib.StdOut;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The Sistema.
 *
 * @author Programacion Avanzada.
 */
public final class Sistema {

    /**
     * Procesador de JSON.
     */
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * The list of Socios.
     */
    private Socio[] socios;

    /**
     * The list of Libros.
     */
    private Libro[] libros;

    /**
     * Socio en el sistema.
     */
    private Socio socio;

    /**
     * The Sistema.
     */
    public Sistema() throws IOException {

        // no hay socio logeado.
        this.socios = new Socio[10];
        this.libros = new Libro[10];
        this.socio = null;

        // carga de los socios y libros.
        try {
            this.cargarInformacion();
        } catch (FileNotFoundException ex) {
            // no se encontraron datos, se agregar los por defecto.

            // creo un socio
            this.socios = Utils.append(this.socios, new Socio("John", "Doe", "john.doe@ucn.cl", 1, "john123"));

            // creo un libro y lo agrego al arreglo de libros.
            this.libros = Utils.append(this.libros, new Libro("1491910771", "Head First Java: A Brain-Friendly Guide", " Kathy Sierra", "Programming Languages"));

            // creo otro libro y lo agrego al arreglo de libros.
            this.libros = Utils.append(this.libros, new Libro("1491910771", "Effective Java", "Joshua Bloch", "Programming Languages"));

        } finally {
            // guardo la informacion.
            this.guardarInformacion();
        }

    }

    /**
     * Activa (inicia sesion) de un socio en el sistema.
     *
     * @param numeroDeSocio a utilizar.
     * @param contrasenia   a validar.
     */
    public void iniciarSession(final int numeroDeSocio, final String contrasenia) {

        // el numero de socio siempre es positivo.
        if (numeroDeSocio <= 0) {
            throw new IllegalArgumentException("El numero de socio no es valido!");
        }

        // TODO: buscar el socio dado su numero.
        else if (obtenerDatosSocioLogeado().equals(numeroDeSocio)) {
            if (socio.getContrasenia().equals(contrasenia)) {
                return ; //Aquí debe retornar al objeto socio si el ingreso es exitoso
            }
        }nuevoUsuario(numeroDeSocio,contrasenia);



    }

    /**
     * Cierra la session del Socio.
     */
    public void cerrarSession() {
        this.socio = null;
    }

    /**
     * Metodo que mueve un libro de los disponibles y lo ingresa a un Socio.
     *
     * @param isbn del libro a prestar.
     */
    public void realizarPrestamoLibro(final String isbn) throws IOException {
        // el socio debe estar activo.
        if (this.socio == null) {
            throw new IllegalArgumentException("Socio no se ha logeado!");
        }

        // busco el libro.
        Libro libro = this.buscarLibro(isbn);

        // si no lo encontre, lo informo.
        if (libro == null) {
            throw new IllegalArgumentException("Libro con isbn " + isbn + " no existe o no se encuentra disponible.");
        }

        // agrego el libro al socio.
        this.socio.agregarLibro(libro);

        //Elimino el libro de la lista de libro disponibles
        if (libro.getCantCopiasDisponibles() < 0) {
            throw new IllegalArgumentException("No hay copias disponibles de este libro.");
        }

        // se actualiza la informacion de los archivos
        this.guardarInformacion();

    }

    /**
     * Obtiene un String que representa el listado completo de libros disponibles.
     *
     * @return the String con la informacion de los libros disponibles.
     */
    public String obtegerCatalogoLibros() {

        StringBuilder sb = new StringBuilder();
        for (Libro libro : this.libros) {
            sb.append("Titulo    : ").append(libro.getTitulo()).append("\n");
            sb.append("Autor     : ").append(libro.getAutor()).append("\n");
            sb.append("ISBN      : ").append(libro.getIsbn()).append("\n");
            sb.append("Categoria : ").append(libro.getCategoria()).append("\n");
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * Metodo que busca un libro en los libros disponibles.
     *
     * @param isbn a buscar.
     * @return el libro o null si no fue encontrado.l
     */
    private Libro buscarLibro(final String isbn) {
        // recorro el arreglo de libros.
        for (Libro libro : this.libros) {
            // si lo encontre, retorno el libro.
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        // no lo encontre, retorno null.
        return null;
    }

    /**
     * Lee los archivos libros.json y socios.json.
     *
     * @throws FileNotFoundException si alguno de los archivos no se encuentra.
     */
    private void cargarInformacion() throws FileNotFoundException {

        // trato de leer los socios y los libros desde el archivo.
        this.socios = GSON.fromJson(new FileReader("socios.json"), Socio[].class);
        this.libros = GSON.fromJson(new FileReader("libros.json"), Libro[].class);
    }

    /**
     * Guarda los arreglos libros y socios en los archivos libros.json y socios.json.
     *
     * @throws IOException en caso de algun error.
     */
    private void guardarInformacion() throws IOException {

        // guardo los socios.
        try (FileWriter writer = new FileWriter("socios.json")) {
            GSON.toJson(this.socios, writer);
        }

        // guardo los libros.
        try (FileWriter writer = new FileWriter("libros.json")) {
            GSON.toJson(this.libros, writer);
        }

    }

    public String obtenerDatosSocioLogeado() {
        if (this.socio == null) {
            throw new IllegalArgumentException("No hay un Socio logeado");
        }

        return "Nombre: " + this.socio.getNombreCompleto() + "\n"
                + "Correo Electronico: " + this.socio.getCorreoElectronico();
    }

    public void nuevoUsuario(int numeroDeSocio, String contrasenia){

            StdOut.println("Ingrese su nombre: ");
            String nombre = StdIn.readLine();
            StdOut.println("Ingrese su apellido: ");
            String apellido = StdIn.readLine();
            StdOut.println("Ingrese su correo electronico: ");
            String correo = StdIn.readLine();

            socio.agregarSocio(nombre,apellido,correo,numeroDeSocio,contrasenia);



    }
    public void editarNombre() {
        if (this.socio.getNumeroDeSocio() == socio.getNumeroDeSocio()) {
            StdOut.println("Ingrese el nuevo nombre: ");
            String nuevoNombre = StdIn.readString();
            this.socio.setNombre(nuevoNombre);
        }else{
            StdOut.println("El usuario no se encuentra disponible");
        }
    }

    public void editarApellido(){
        if (this.socio.getNumeroDeSocio() == socio.getNumeroDeSocio()) {
            StdOut.println("Ingrese el nuevo apellido: ");
            String nuevoApellido = StdIn.readString();
            this.socio.setApellido(nuevoApellido);
        }else{
            StdOut.println("El usuario no se encuentra disponible");
        }


    }

    public void cambiarContrasenia(){
        if (this.socio.getNumeroDeSocio() == socio.getNumeroDeSocio()) {
            StdOut.println("Ingrese la nueva contrasenia: ");
            String nuevaContrasenia= StdIn.readString();
            this.socio.setContrasenia(nuevaContrasenia);
        }else{
            StdOut.println("El usuario no se encuentra disponible");
        }
    }

    public void editarCorreo(){
        if (this.socio.getNumeroDeSocio() == socio.getNumeroDeSocio()) {
            StdOut.println("Ingrese el nuevo correo: ");
            String nuevoCorreo= StdIn.readString();
            this.socio.setCorreoElectronico(nuevoCorreo);
        }else{
            StdOut.println("El usuario no se encuentra disponible");
        }
    }
    public void calificarLibro(int numeroDeSocio, String isbn, int calificacion) {
        socio = socios.getClass(numeroDeSocio);
        Libro libros = this.buscarLibro(isbn);

        if (this.libros != null && this.socio != null) {
            if (socio.agregarLibro(isbn)) {
                libros.agregarCalifiacion(calificacion);
                StdOut.println("Libro calificado con exito");

            } else {
                StdOut.println("El libro no se encuentra en libreria");
            }

        } else {
            StdOut.println("El dato ingresado no es valido");
        }
    }
}



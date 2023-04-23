/*
 * Copyright (c) 2023. Programacion Avanzada, DISC, UCN.
 */

package cl.ucn.disc.pa.bibliotech.model;

import cl.ucn.disc.pa.bibliotech.services.Utils;

/**
 * Clase que representa a un Socio.
 *
 * @author Programacion Avanzada.
 */
public final class Socio {

    /**
     * Numero maximo de libros que puede tener el Socio.
     */
    private static final int NUMERO_LIBROS_MAXIMO = 9;

    /**
     * Nombre del socio.
     */
    private String nombre;

    /**
     * Apellido del socio.
     */
    private String apellido;

    /**
     * Email del socio.
     */
    private String correoElectronico;

    /**
     * Numero del socio.
     */
    private int numeroDeSocio;

    /**
     * Contrasenia del socio.
     */
    private String contrasenia;

    /**
     * Libros que el Socio tiene en prestamo (maximo 10).
     */
    private Libro[] librosEnPrestamo = new Libro[10];

    private Socio socio;

    /**
     * The Constructor.
     *
     * @param nombre            del socio.
     * @param apellido          del socio.
     * @param correoElectronico del socio.
     * @param numeroDeSocio     del socio.
     * @param contrasenia       del socio.
     */
    public Socio(String nombre, String apellido, String correoElectronico, int numeroDeSocio, String contrasenia) {

        //Se valida el ingreso del nombre: Que no sea vacio y que se desprecien mayúsculas y minúsculas
        //También se valida que no se ingrese un número
        if (nombre == null || nombre.equalsIgnoreCase(nombre) || !nombre.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("Ingrese un nombre");
        }
        this.nombre = nombre;


        //Se valida lo mismo que en el nombre del socio
        if (apellido == null || apellido.equalsIgnoreCase(apellido) || !apellido.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("Ingreso erroneo");
        }
        this.apellido = apellido;

        // metodo estatico para validacion de email.
        Utils.validarEmail(correoElectronico);
        this.correoElectronico = correoElectronico;


        //Se valida que el ingreso no sea vacio
        try {
            this.numeroDeSocio = numeroDeSocio;

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Solo se permiten el ingreso de números");
        }

        //Se valida el ingreso vacio y se desprecian mayúsculas y minúsculas
        if (contrasenia == null || contrasenia.equalsIgnoreCase(contrasenia)) {
            throw new IllegalArgumentException("Ingrese una contraseña correcta");
        }
        this.contrasenia = contrasenia;
    }

    /**
     * @return el nombre del Socio.
     */
    public String getNombre() {

        return this.nombre;
    }

    /**
     * @return el apellido del Socio.
     */
    public String getApellido() {

        return this.apellido;
    }

    /**
     * @return el nombre completo del Socio.
     */
    public String getNombreCompleto() {

        return this.nombre + " " + this.apellido;
    }

    /**
     * @return el correo electronico del Socio.
     */
    public String getCorreoElectronico() {

        return this.correoElectronico;
    }

    /**
     * @return el numero del Socio.
     */
    public int getNumeroDeSocio() {

        return this.numeroDeSocio;
    }

    /**
     * @return la contrasenia del Socio.
     */
    public String getContrasenia() {
        return this.contrasenia;
    }

    /**
     * Agrega un libro en prestamo al Socio.
     *
     * @param libro a agregar.
     */
    public void agregarLibro(final Libro libro) {
        // validacion
        if (this.librosEnPrestamo.length == NUMERO_LIBROS_MAXIMO) {
            throw new IllegalArgumentException("El Socio ya tiene la maxima cantidad de libros en prestamo: " + NUMERO_LIBROS_MAXIMO);
        }
        // agrego el libro
        Utils.append(this.librosEnPrestamo, libro);
    }

    public void agregarSocio(String nombre, String apellido, String correoElectronico, int numeroDeSocio, String contrasenia){

        socio = new Socio(nombre,apellido,correoElectronico,numeroDeSocio,contrasenia);

    }

    /**
     * Edita la contrasenia del socio.
     *
     * @param contrasenia a editar
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * Edita el nombre del socio
     * @param nombre del socio a cambiar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Edita el apellido del socio
     * @param apellido del socio a cambiar
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Edita el correo electronico del socio
     * @param correoElectronico del socio a cambiar
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
}

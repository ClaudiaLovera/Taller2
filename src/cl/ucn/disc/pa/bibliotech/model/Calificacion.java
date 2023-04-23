package cl.ucn.disc.pa.bibliotech.model;

public class Calificacion {

    private Socio socio;
    private int estrellas;

    public Calificacion(Socio socio, int estrellas) {
        this.socio = socio;
        this.estrellas = estrellas;

    }

    public Socio getSocio() {
        return socio;

    }

    public int getEstrellas() {
        return estrellas;
    }
}

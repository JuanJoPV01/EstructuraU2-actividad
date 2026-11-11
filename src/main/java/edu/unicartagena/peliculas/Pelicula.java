package edu.unicartagena.peliculas;

public final class Pelicula {

    private final String titulo;
    private final int anio;
    private final int duracion;
    private final double calificacion;

    public Pelicula(String titulo, int anio, int duracion, double calificacion) {
        this.titulo = titulo;
        this.anio = anio;
        this.duracion = duracion;
        this.calificacion = calificacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getAnio() {
        return anio;
    }

    public int getDuracion() {
        return duracion;
    }

    public double getCalificacion() {
        return calificacion;
    }

    @Override
    public String toString() {
        return titulo + " (" + anio + ") - " + duracion + " min - " + calificacion;
    }
}

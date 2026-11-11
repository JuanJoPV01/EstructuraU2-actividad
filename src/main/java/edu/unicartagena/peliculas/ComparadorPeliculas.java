package edu.unicartagena.peliculas;

import java.util.Comparator;

public final class ComparadorPeliculas implements Comparator<Pelicula> {

    private long comparaciones;

    public long getComparaciones() {
        return comparaciones;
    }

    public void reiniciarComparaciones() {
        comparaciones = 0;
    }

    // Mismo criterio del ordenamiento manual: calificacion
    // descendente, ano descendente y titulo ascendente.
    @Override
    public int compare(Pelicula a, Pelicula b) {
        comparaciones++;

        int porCalificacion = Double.compare(b.getCalificacion(), a.getCalificacion());
        if (porCalificacion != 0) {
            return porCalificacion;
        }

        int porAnio = Integer.compare(b.getAnio(), a.getAnio());
        if (porAnio != 0) {
            return porAnio;
        }

        return a.getTitulo().compareTo(b.getTitulo());
    }
}

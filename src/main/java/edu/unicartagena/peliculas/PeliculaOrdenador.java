package edu.unicartagena.peliculas;

public final class PeliculaOrdenador {

    // Criterio del ejercicio: calificacion descendente, ano descendente
    // y titulo ascendente como desempate final.
    public int comparar(Pelicula a, Pelicula b) {
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

    public boolean estaOrdenada(Pelicula[] peliculas) {
        if (peliculas == null || peliculas.length < 2) {
            return true;
        }
        for (int i = 1; i < peliculas.length; i++) {
            if (comparar(peliculas[i - 1], peliculas[i]) > 0) {
                return false;
            }
        }
        return true;
    }
}

package edu.unicartagena.peliculas;

public final class PeliculaOrdenador {

    private long comparaciones;
    private long intercambios;

    public long getComparaciones() {
        return comparaciones;
    }

    public long getIntercambios() {
        return intercambios;
    }

    public void reiniciarContadores() {
        comparaciones = 0;
        intercambios = 0;
    }

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

    // Seleccion: en cada pasada busca la mejor pelicula restante segun
    // el criterio y la ubica al inicio de la zona sin ordenar.
    public void ordenar(Pelicula[] peliculas) {
        if (peliculas == null || peliculas.length < 2) {
            return;
        }
        for (int i = 0; i < peliculas.length - 1; i++) {
            int mejor = i;
            for (int j = i + 1; j < peliculas.length; j++) {
                comparaciones++;
                if (comparar(peliculas[j], peliculas[mejor]) < 0) {
                    mejor = j;
                }
            }
            if (mejor != i) {
                Pelicula temporal = peliculas[i];
                peliculas[i] = peliculas[mejor];
                peliculas[mejor] = temporal;
                intercambios++;
            }
        }
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

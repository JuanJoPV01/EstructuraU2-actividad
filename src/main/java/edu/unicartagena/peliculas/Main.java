package edu.unicartagena.peliculas;

import java.util.Arrays;

public final class Main {

    private static final Pelicula[] CATALOGO = {
        new Pelicula("Pulp Fiction", 1994, 154, 8.9),
        new Pelicula("El Padrino", 1972, 175, 9.2),
        new Pelicula("Coco", 2017, 105, 8.4),
        new Pelicula("El Caballero de la Noche", 2008, 152, 9.0),
        new Pelicula("La Lista de Schindler", 1993, 195, 8.9),
        new Pelicula("Forrest Gump", 1994, 142, 8.5),
        new Pelicula("El Padrino: Parte II", 1974, 202, 9.0),
        new Pelicula("Interestelar", 2014, 169, 8.6),
        new Pelicula("El Rey León", 1994, 88, 8.5),
        new Pelicula("El Retorno del Rey", 2003, 201, 8.9),
        new Pelicula("Cadena Perpetua", 1994, 142, 9.3),
        new Pelicula("Dunkerque", 2017, 106, 8.4)
    };

    private Main() {
    }

    public static void main(String[] args) {
        System.out.println("=== CLASIFICACION DE PELICULAS ===");
        System.out.println("Peliculas del catalogo: " + CATALOGO.length);

        PeliculaOrdenador ordenador = new PeliculaOrdenador();
        mostrarCatalogo("Catalogo original", CATALOGO);
        System.out.println("Esta ordenado? (antes)   : " + ordenador.estaOrdenada(CATALOGO));

        Pelicula[] rankingManual = Arrays.copyOf(CATALOGO, CATALOGO.length);
        ordenador.ordenar(rankingManual);
        mostrarCatalogo("Ordenamiento manual (seleccion)", rankingManual);
        System.out.println("Esta ordenado? (despues) : " + ordenador.estaOrdenada(rankingManual));
        System.out.println("Comparaciones realizadas : " + ordenador.getComparaciones());
        System.out.println("Intercambios realizados  : " + ordenador.getIntercambios());

        ComparadorPeliculas comparador = new ComparadorPeliculas();
        Pelicula[] rankingComparator = Arrays.copyOf(CATALOGO, CATALOGO.length);
        Arrays.sort(rankingComparator, comparador);
        mostrarCatalogo("Ordenamiento con Comparator", rankingComparator);

        System.out.println();
        System.out.println("Ambos ordenamientos coinciden  : "
                + coinciden(rankingManual, rankingComparator));
        System.out.println("Comparaciones con Comparator  : " + comparador.getComparaciones());
        System.out.println("Comparaciones con seleccion   : " + ordenador.getComparaciones());
    }

    private static void mostrarCatalogo(String titulo, Pelicula[] peliculas) {
        System.out.println();
        System.out.println("--- " + titulo + " ---");
        for (int i = 0; i < peliculas.length; i++) {
            System.out.println(String.format("%2d. %s", i + 1, peliculas[i]));
        }
    }

    private static boolean coinciden(Pelicula[] primeras, Pelicula[] segundas) {
        if (primeras.length != segundas.length) {
            return false;
        }
        for (int i = 0; i < primeras.length; i++) {
            if (primeras[i] != segundas[i]) {
                return false;
            }
        }
        return true;
    }
}

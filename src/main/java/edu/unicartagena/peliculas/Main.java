package edu.unicartagena.peliculas;

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
        System.out.println();

        System.out.println("--- Catalogo original ---");
        for (int i = 0; i < CATALOGO.length; i++) {
            System.out.println(String.format("%2d. %s", i + 1, CATALOGO[i]));
        }

        PeliculaOrdenador ordenador = new PeliculaOrdenador();
        System.out.println();
        System.out.println("Catalogo ordenado?  : " + ordenador.estaOrdenada(CATALOGO));
        System.out.println("Mejor del catalogo  : " + buscarMejor(ordenador, CATALOGO));
    }

    private static Pelicula buscarMejor(PeliculaOrdenador ordenador, Pelicula[] peliculas) {
        Pelicula mejor = peliculas[0];
        for (int i = 1; i < peliculas.length; i++) {
            if (ordenador.comparar(peliculas[i], mejor) < 0) {
                mejor = peliculas[i];
            }
        }
        return mejor;
    }
}

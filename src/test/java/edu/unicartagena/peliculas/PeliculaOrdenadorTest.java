package edu.unicartagena.peliculas;

import java.util.Arrays;
import java.util.Random;

public final class PeliculaOrdenadorTest {

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

    private static final String[] RANKING_ESPERADO = {
        "Cadena Perpetua", "El Padrino", "El Caballero de la Noche",
        "El Padrino: Parte II", "El Retorno del Rey", "Pulp Fiction",
        "La Lista de Schindler", "Interestelar", "El Rey León",
        "Forrest Gump", "Coco", "Dunkerque"
    };

    private static int pruebas;
    private static int fallidas;

    private PeliculaOrdenadorTest() {
    }

    public static void main(String[] args) {
        probarComparar();
        probarCriterioCalificacion();
        probarCriterioAnio();
        probarCriterioTitulo();
        probarOrdenamientoCompleto();
        probarContadores();
        probarOrdenInverso();
        probarCasosBorde();
        probarComparator();
        probarCatalogoGrande();

        System.out.println();
        if (fallidas == 0) {
            System.out.println("OK: " + pruebas + " pruebas ejecutadas correctamente.");
        } else {
            System.out.println("FALLARON " + fallidas + " de " + pruebas + " pruebas.");
            System.exit(1);
        }
    }

    private static void probarComparar() {
        PeliculaOrdenador ordenador = new PeliculaOrdenador();
        Pelicula cadena = new Pelicula("Cadena Perpetua", 1994, 142, 9.3);
        Pelicula interestelar = new Pelicula("Interestelar", 2014, 169, 8.6);
        Pelicula caballero = new Pelicula("El Caballero de la Noche", 2008, 152, 9.0);
        Pelicula padrinoII = new Pelicula("El Padrino: Parte II", 1974, 202, 9.0);
        Pelicula coco = new Pelicula("Coco", 2017, 105, 8.4);
        Pelicula dunkerque = new Pelicula("Dunkerque", 2017, 106, 8.4);

        assertTrue("calificacion mayor va antes",
                ordenador.comparar(cadena, interestelar) < 0);
        assertTrue("calificacion menor va despues",
                ordenador.comparar(interestelar, cadena) > 0);
        assertTrue("empate de calificacion: ano mayor va antes",
                ordenador.comparar(caballero, padrinoII) < 0);
        assertTrue("empate de calificacion: ano menor va despues",
                ordenador.comparar(padrinoII, caballero) > 0);
        assertTrue("empate de calificacion y ano: titulo menor va antes",
                ordenador.comparar(coco, dunkerque) < 0);
        assertTrue("empate de calificacion y ano: titulo mayor va despues",
                ordenador.comparar(dunkerque, coco) > 0);
        assertEquals("una pelicula comparada consigo misma da cero", 0,
                ordenador.comparar(cadena, cadena));
    }

    private static void probarCriterioCalificacion() {
        PeliculaOrdenador ordenador = new PeliculaOrdenador();
        Pelicula[] ranking = copiarCatalogo();
        ordenador.ordenar(ranking);

        assertEquals("la mejor calificacion queda de primera", "Cadena Perpetua",
                tituloEn(ranking, 0));
        assertEquals("la segunda mejor calificacion queda de segunda", "El Padrino",
                tituloEn(ranking, 1));
        assertEquals("la pelicula de 8.6 queda despues del grupo de 8.9", "Interestelar",
                tituloEn(ranking, 7));
        assertEquals("la peor calificacion queda de ultima", "Dunkerque",
                tituloEn(ranking, 11));
    }

    private static void probarCriterioAnio() {
        PeliculaOrdenador ordenador = new PeliculaOrdenador();
        Pelicula[] ranking = copiarCatalogo();
        ordenador.ordenar(ranking);

        assertEquals("empate de 9.0: la mas reciente (2008) primero", "El Caballero de la Noche",
                tituloEn(ranking, 2));
        assertEquals("empate de 9.0: la mas antigua (1974) despues", "El Padrino: Parte II",
                tituloEn(ranking, 3));
        assertEquals("empate de 8.9: 2003 antes que 1994", "El Retorno del Rey",
                tituloEn(ranking, 4));
        assertEquals("empate de 8.9: 1994 antes que 1993", "Pulp Fiction",
                tituloEn(ranking, 5));
        assertEquals("empate de 8.9: la de 1993 cierra el grupo", "La Lista de Schindler",
                tituloEn(ranking, 6));
    }

    private static void probarCriterioTitulo() {
        PeliculaOrdenador ordenador = new PeliculaOrdenador();
        Pelicula[] ranking = copiarCatalogo();
        ordenador.ordenar(ranking);

        assertEquals("empate de 8.5 en 1994: titulo menor primero", "El Rey León",
                tituloEn(ranking, 8));
        assertEquals("empate de 8.5 en 1994: titulo mayor despues", "Forrest Gump",
                tituloEn(ranking, 9));
        assertEquals("empate de 8.4 en 2017: titulo menor primero", "Coco",
                tituloEn(ranking, 10));
        assertEquals("empate de 8.4 en 2017: titulo mayor despues", "Dunkerque",
                tituloEn(ranking, 11));
    }

    private static void probarOrdenamientoCompleto() {
        PeliculaOrdenador ordenador = new PeliculaOrdenador();
        Pelicula[] ranking = copiarCatalogo();
        ordenador.ordenar(ranking);

        assertMismoOrden("ranking completo del catalogo", RANKING_ESPERADO, ranking);
        assertTrue("el ranking queda ordenado", ordenador.estaOrdenada(ranking));
        assertTrue("el catalogo original no esta ordenado", !ordenador.estaOrdenada(CATALOGO));
        assertEquals("el catalogo original conserva su primera pelicula", "Pulp Fiction",
                CATALOGO[0].getTitulo());
    }

    private static void probarContadores() {
        PeliculaOrdenador ordenador = new PeliculaOrdenador();
        Pelicula[] ranking = copiarCatalogo();
        ordenador.ordenar(ranking);

        assertEquals("comparaciones del ordenamiento manual", 66,
                ordenador.getComparaciones());
        assertEquals("intercambios del ordenamiento manual", 7,
                ordenador.getIntercambios());

        ordenador.reiniciarContadores();
        ordenador.ordenar(ranking);
        assertEquals("comparaciones con el arreglo ya ordenado", 66,
                ordenador.getComparaciones());
        assertEquals("intercambios con el arreglo ya ordenado", 0,
                ordenador.getIntercambios());
    }

    private static void probarOrdenInverso() {
        PeliculaOrdenador ordenador = new PeliculaOrdenador();
        Pelicula[] ranking = copiarCatalogo();
        ordenador.ordenar(ranking);

        Pelicula[] inverso = new Pelicula[ranking.length];
        for (int i = 0; i < ranking.length; i++) {
            inverso[i] = ranking[ranking.length - 1 - i];
        }

        ordenador.reiniciarContadores();
        ordenador.ordenar(inverso);

        assertMismoOrden("ranking desde el orden inverso", RANKING_ESPERADO, inverso);
        assertEquals("intercambios desde el orden inverso", 6, ordenador.getIntercambios());
    }

    private static void probarCasosBorde() {
        PeliculaOrdenador ordenador = new PeliculaOrdenador();

        ordenador.ordenar(null);
        assertTrue("null se considera ordenado", ordenador.estaOrdenada(null));

        Pelicula[] vacio = new Pelicula[0];
        ordenador.ordenar(vacio);
        assertEquals("el arreglo vacio conserva su tamano", 0, vacio.length);

        Pelicula[] unica = {new Pelicula("Titanic", 1997, 195, 7.9)};
        ordenador.ordenar(unica);
        assertEquals("la pelicula unica se mantiene", "Titanic", unica[0].getTitulo());

        Pelicula primera = new Pelicula("Versiones", 2020, 100, 7.5);
        Pelicula segunda = new Pelicula("Versiones", 2020, 130, 7.5);
        Pelicula[] equivalentes = {primera, segunda};
        ordenador.reiniciarContadores();
        ordenador.ordenar(equivalentes);
        assertEquals("peliculas equivalentes conservan su orden (100 min primero)",
                100, equivalentes[0].getDuracion());
        assertEquals("peliculas equivalentes conservan su orden (130 min despues)",
                130, equivalentes[1].getDuracion());
        assertEquals("peliculas equivalentes no generan intercambios", 0,
                ordenador.getIntercambios());

        Pelicula[] par = {
            new Pelicula("Mala", 2000, 90, 5.0),
            new Pelicula("Buena", 2001, 100, 9.0)
        };
        ordenador.ordenar(par);
        assertEquals("par desordenado: la mejor queda primero", "Buena", par[0].getTitulo());
        assertEquals("par desordenado: la peor queda despues", "Mala", par[1].getTitulo());
        assertEquals("intercambio unico para el par", 1, ordenador.getIntercambios());
    }

    private static void probarComparator() {
        PeliculaOrdenador ordenador = new PeliculaOrdenador();
        Pelicula[] rankingManual = copiarCatalogo();
        ordenador.ordenar(rankingManual);

        ComparadorPeliculas comparador = new ComparadorPeliculas();
        Pelicula[] rankingComparator = copiarCatalogo();
        Arrays.sort(rankingComparator, comparador);

        assertMismoOrden("el comparator produce el mismo ranking", RANKING_ESPERADO,
                rankingComparator);
        assertTrue("ambos ordenamientos coinciden pelicula por pelicula",
                coincidenPorReferencia(rankingManual, rankingComparator));
        assertTrue("el comparator hace menos comparaciones que la seleccion manual",
                comparador.getComparaciones() < ordenador.getComparaciones());
    }

    private static void probarCatalogoGrande() {
        PeliculaOrdenador ordenador = new PeliculaOrdenador();
        Random aleatorio = new Random(19);

        Pelicula[] peliculas = new Pelicula[1_000];
        for (int i = 0; i < peliculas.length; i++) {
            peliculas[i] = new Pelicula("Pelicula " + i,
                    1980 + aleatorio.nextInt(45),
                    80 + aleatorio.nextInt(120),
                    1.0 + aleatorio.nextInt(90) / 10.0);
        }
        Pelicula[] respaldo = Arrays.copyOf(peliculas, peliculas.length);

        ordenador.ordenar(peliculas);
        assertTrue("el catalogo grande queda ordenado", ordenador.estaOrdenada(peliculas));
        assertEquals("comparaciones manuales en 1.000 peliculas", 499_500,
                ordenador.getComparaciones());

        ComparadorPeliculas comparador = new ComparadorPeliculas();
        Arrays.sort(respaldo, comparador);
        assertTrue("arrays.sort coincide con el ordenamiento manual en el catalogo grande",
                coincidenPorReferencia(peliculas, respaldo));
        assertTrue("el comparator hace menos comparaciones en el catalogo grande",
                comparador.getComparaciones() < ordenador.getComparaciones());
    }

    private static Pelicula[] copiarCatalogo() {
        return Arrays.copyOf(CATALOGO, CATALOGO.length);
    }

    private static String tituloEn(Pelicula[] peliculas, int posicion) {
        return peliculas[posicion].getTitulo();
    }

    private static boolean coincidenPorReferencia(Pelicula[] primeras, Pelicula[] segundas) {
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

    private static void assertEquals(String nombre, int esperado, int obtenido) {
        pruebas++;
        if (esperado != obtenido) {
            fallidas++;
            System.out.println("FALLO: " + nombre + " -> esperado " + esperado
                    + ", obtenido " + obtenido);
        }
    }

    private static void assertEquals(String nombre, long esperado, long obtenido) {
        pruebas++;
        if (esperado != obtenido) {
            fallidas++;
            System.out.println("FALLO: " + nombre + " -> esperado " + esperado
                    + ", obtenido " + obtenido);
        }
    }

    private static void assertEquals(String nombre, String esperado, String obtenido) {
        pruebas++;
        if (!esperado.equals(obtenido)) {
            fallidas++;
            System.out.println("FALLO: " + nombre + " -> esperado '" + esperado
                    + "', obtenido '" + obtenido + "'");
        }
    }

    private static void assertTrue(String nombre, boolean condicion) {
        pruebas++;
        if (!condicion) {
            fallidas++;
            System.out.println("FALLO: " + nombre);
        }
    }

    private static void assertMismoOrden(String nombre, String[] titulosEsperados,
            Pelicula[] obtenido) {
        pruebas++;
        if (titulosEsperados.length != obtenido.length) {
            fallidas++;
            System.out.println("FALLO: " + nombre + " -> se esperaban " + titulosEsperados.length
                    + " peliculas y hubo " + obtenido.length);
            return;
        }
        for (int i = 0; i < titulosEsperados.length; i++) {
            if (!titulosEsperados[i].equals(obtenido[i].getTitulo())) {
                fallidas++;
                System.out.println("FALLO: " + nombre + " -> en la posicion " + (i + 1)
                        + " se esperaba '" + titulosEsperados[i] + "' y hubo '"
                        + obtenido[i].getTitulo() + "'");
                return;
            }
        }
    }
}

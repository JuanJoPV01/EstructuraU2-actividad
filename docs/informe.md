# Informe de desarrollo - Ejercicio 19: Clasificación de películas

**Asignatura:** Estructuras de Datos
**Programa:** Ingeniería de Software
**Tutor:** John Carlos Arrieta Arrieta
**Universidad:** Universidad de Cartagena
**Autor:** Juan Puello

---

## 1. Introducción

El presente informe describe la solución del ejercicio 19 del taller
práctico de la Unidad 3, orientado a los algoritmos de ordenamiento. El
problema consiste en crear objetos `Pelicula` con título, año, duración
y calificación, y ordenar un catálogo de películas con un criterio
multinivel: calificación descendente, año descendente en caso de
empate y título ascendente como desempate final. Posteriormente se
pide implementar el mismo criterio utilizando `Comparator`.

La solución se implementó en Java 17 sin dependencias externas. El
ordenamiento principal se escribió manualmente, sin apoyarse en
`Arrays.sort`, `Collections.sort` ni otros mecanismos automáticos del
lenguaje, y se complementó con la versión basada en `Comparator` que
exige el enunciado.

## 2. Objetivos

- Modelar películas como objetos con cuatro atributos.
- Definir un criterio de comparación multinivel y justificarlo.
- Implementar manualmente un algoritmo de ordenamiento sobre el
  arreglo de objetos.
- Contabilizar las comparaciones y los intercambios realizados.
- Implementar el mismo criterio con la interfaz `Comparator`.
- Verificar que ambos ordenamientos producen el mismo ranking.
- Validar el funcionamiento con valores repetidos y casos borde.
- Documentar la complejidad temporal de los algoritmos utilizados.

## 3. Metodología y uso de Git

El desarrollo se llevó a cabo de forma progresiva en la rama
`desarrollo-ejercicio`, partiendo de la rama principal `main`. Cada
avance funcional se acompañó de una compilación y una ejecución de
prueba antes de realizar el commit. Al finalizar, el trabajo se
integró en `main`.

Historial de commits:

```text
chore: inicializar repositorio del ejercicio
feat: crear estructura inicial del ejercicio
feat: implementar criterio de comparacion multinivel
feat: implementar ordenamiento manual por seleccion
feat: agregar ordenamiento del mismo criterio con comparator
test: agregar casos de prueba del ordenamiento y criterios
refactor: organizar la salida del programa en metodos
docs: agregar instrucciones y analisis de complejidad al README
docs: agregar informe de desarrollo del ejercicio
```

## 4. Desarrollo de la solución

### 4.1 Estructura

El proyecto se organizó en cuatro clases principales:

- `Pelicula`: modelo inmutable con título, año, duración y
  calificación.
- `PeliculaOrdenador`: criterio de comparación multinivel,
  ordenamiento manual por selección y contadores de comparaciones e
  intercambios.
- `ComparadorPeliculas`: el mismo criterio implementando la interfaz
  `java.util.Comparator`.
- `Main`: ejecuta una demostración por consola con un catálogo de 12
  películas con empates preparados.

Las pruebas se encuentran en `PeliculaOrdenadorTest` y se ejecutan
como un programa Java independiente, sin JUnit.

### 4.2 Datos de ejemplo

El catálogo contiene 12 películas con empates deliberados para
ejercitar los tres niveles del criterio:

```java
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
```

Tres películas comparten calificación 8.9 (años 2003, 1994 y 1993),
dos comparten 9.0 (2008 y 1974), dos comparten 8.5 en el mismo año
(1994) y dos comparten 8.4 en el mismo año (2017).

### 4.3 Criterio de ordenamiento

El corazón de la solución es un método que decide, dadas dos películas,
cuál debe ir antes:

```java
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
```

Un resultado negativo indica que `a` va antes que `b`, un positivo que
va después y `0` que son equivalentes. Los dos primeros niveles
invertimos los operandos (`b` antes que `a` dentro de `compare`) para
lograr el orden descendente; el tercero usa `compareTo` directamente
porque el título se ordena ascendente.

La justificación de los atributos es la siguiente: la **calificación**
es la medida directa de la calidad y por eso encabeza el ranking; el
**año** desempata prefiriendo la película más reciente; el **título**
garantiza un resultado determinista ante empates completos. La
**duración** no participa en el criterio porque no altera el puesto de
una película en un ranking.

### 4.4 Ordenamiento manual por selección

El ordenamiento pedido se implementó con **selección**: en cada pasada
se busca la mejor película de la zona sin ordenar y se intercambia con
la primera posición de esa zona.

```java
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
```

Se eligió selección porque encaja de forma natural con la idea de un
ranking (en cada paso se selecciona la mejor película restante), hace
a lo sumo `n - 1` intercambios y permite contabilizar con claridad
comparaciones e intercambios, como piden las indicaciones del taller.

### 4.5 Ordenamiento con `Comparator`

`ComparadorPeliculas` implementa la interfaz `Comparator<Pelicula>`
replicando exactamente el mismo criterio, y cuenta sus propias
comparaciones para poder compararla con la versión manual:

```java
ComparadorPeliculas comparador = new ComparadorPeliculas();
Pelicula[] rankingComparator = Arrays.copyOf(CATALOGO, CATALOGO.length);
Arrays.sort(rankingComparator, comparador);
```

Al terminar, el programa verifica con `coinciden` que ambas
implementaciones producen exactamente el mismo orden, película por
película.

## 5. Pruebas y resultados

Se implementaron 46 verificaciones que cubren el signo del comparador
para cada nivel del criterio, el ranking completo del catálogo, los
contadores de comparaciones e intercambios, arreglos ya ordenados e
invertidos, casos borde (`null`, vacío, una sola película, películas
equivalentes) y el ordenamiento de un catálogo de 1.000 películas
aleatorias.

Resultado de la ejecución:

```text
OK: 46 pruebas ejecutadas correctamente.
```

Resultado de la demostración con el catálogo de 12 películas:

| Métrica | Selección (manual) | `Arrays.sort` + `Comparator` |
|---|---|---|
| Comparaciones | 66 | 29 |
| Intercambios | 7 | (interno de TimSort) |
| Ranking | correcto | idéntico al manual |

En el catálogo grande de 1.000 películas aleatorias, la selección
realizó exactamente 499.500 comparaciones (`n(n-1)/2`), mientras que
`Arrays.sort` con el `Comparator` necesitó 8.702 comparaciones. Esto
evidencia la diferencia entre O(n²) y O(n log n).

## 6. Análisis de complejidad

| Operación | Complejidad temporal | Espacio adicional |
|---|---|---|
| `comparar` (criterio multinivel) | O(1) | O(1) |
| Ordenamiento por selección | O(n²) en todos los casos | O(1) |
| `Arrays.sort` con `Comparator` (TimSort) | O(n log n) | O(n) |

El ordenamiento por selección siempre realiza `n(n-1)/2` comparaciones:
no distingue un arreglo ya ordenado de uno desordenado, tal como se
comprobó en las pruebas (66 comparaciones en ambos escenarios). Su
ventaja es el número reducido de intercambios (a lo sumo `n - 1`).
TimSort, además de ser O(n log n), es estable y aprovecha runs ya
ordenados, por lo que en el catálogo pequeño requirió menos de la
mitad de las comparaciones de la selección y 57 veces menos en el
catálogo de 1.000 películas.

## 7. Conclusiones

- Un criterio multinivel se implementa limpiamente con una función de
  comparación que retorna negativo/positivo/cero; tanto el algoritmo
  manual como `Comparator` respetan ese mismo contrato.
- El ordenamiento manual evidenció que la selección mantiene su costo
  O(n²) sin importar el estado inicial: hizo las mismas 66
  comparaciones con el catálogo ordenado y desordenado, aunque 0
  intercambios frente a 7.
- El contraste experimental de contadores (66 frente a 29 y 499.500
  frente a 8.702) hace tangible la diferencia entre O(n²) y
  O(n log n) sin necesidad de medir tiempos.
- Los empates por calificación, año y título quedaron resueltos de
  forma determinista, y el caso de películas equivalentes en los tres
  criterios conservó el orden original.

Entre las dificultades encontradas destacan dos. Primero, la
consola de Windows mostraba mal los títulos con tildes ("El Rey
León"); se resolvió compilando con `-encoding UTF-8`, ejecutando con
`-Dfile.encoding=UTF-8` y configurando la consola en los scripts de
PowerShell. Segundo, `String.compareTo` ordena por código Unicode, por
lo que caracteres como `Ñ` quedan después de la `Z`; para el
desempate por título de este catálogo el efecto no altera el
resultado, pero es un detalle a considerar si se exigiera orden
alfabético español estricto.

## 8. Referencias

- Material del taller: *Estructura de datos - Unidad 2: Algoritmos de
  ordenamiento*.
- Apuntes de la asignatura Estructuras de Datos, Universidad de
  Cartagena.
- Documentación oficial de Java 17: `java.util.Comparator`,
  `java.util.Arrays`, `java.lang.Comparable`.

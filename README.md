# Ejercicio 19 - Clasificación de películas

Solución del **ejercicio 19** del taller práctico de la Unidad 3 de
**Estructuras de Datos**: crear objetos `Pelicula` con título, año,
duración y calificación, y ordenarlos con un criterio multinivel
implementado manualmente y luego con `Comparator`.

- **Programa:** Ingeniería de Software
- **Asignatura:** Estructuras de Datos
- **Tutor:** John Carlos Arrieta Arrieta
- **Universidad:** Universidad de Cartagena
- **Autor:** Juan Puello

## Descripción del ejercicio

El programa administra un catálogo de 12 películas y las clasifica
según el criterio:

1. Por **calificación descendente** (mejor calificadas primero).
2. En caso de empate, por **año descendente** (más recientes primero).
3. Finalmente, por **título ascendente** como desempate.

El criterio se implementa dos veces:

- De forma **manual**, con un ordenamiento por selección escrito desde
  cero, contando comparaciones e intercambios.
- Con la interfaz **`Comparator`** de Java, usando el mismo criterio,
  para verificar que ambos caminos producen exactamente el mismo
  ranking.

Ninguna parte del ejercicio se limita a `Arrays.sort`: la versión
manual no utiliza ningún mecanismo automático del lenguaje.

## Estructura del proyecto

```text
clasificacion-peliculas/
├── src/
│   ├── main/java/edu/unicartagena/peliculas/
│   │   ├── Pelicula.java            # Modelo: titulo, anio, duracion, calificacion
│   │   ├── PeliculaOrdenador.java   # Criterio multinivel y ordenamiento manual
│   │   ├── ComparadorPeliculas.java # Mismo criterio implementando Comparator
│   │   └── Main.java                # Demostración por consola
│   └── test/java/edu/unicartagena/peliculas/
│       └── PeliculaOrdenadorTest.java  # 46 casos de prueba
├── ejecutar.ps1                    # Compila y ejecuta la demostración
├── probar.ps1                      # Compila y ejecuta las pruebas
├── docs/
│   └── informe.md                  # Informe de desarrollo (exportable a PDF)
├── .gitignore
└── README.md
```

## Requisitos

- **JDK 17** o superior (`java` y `javac` disponibles en el `PATH`).
- PowerShell 5.1 o superior (incluido en Windows) para los scripts.

## Cómo ejecutar

### Demostración

```powershell
.\ejecutar.ps1
```

### Pruebas

```powershell
.\probar.ps1
```

Si PowerShell bloquea la ejecución de scripts, se puede usar:

```powershell
powershell -ExecutionPolicy Bypass -File .\ejecutar.ps1
powershell -ExecutionPolicy Bypass -File .\probar.ps1
```

### Compilación manual

```powershell
javac -encoding UTF-8 -d out/main (Get-ChildItem -Recurse src/main/java/*.java).FullName
java -Dfile.encoding=UTF-8 -cp out/main edu.unicartagena.peliculas.Main
```

## Justificación del criterio de ordenamiento

- **Calificación** es el criterio principal porque es la medida directa
  de qué tan buena es una película: en un ranking, la de mayor
  calificación debe ocupar el primer puesto.
- **Año** actúa como primer desempate en orden descendente porque,
  ante dos películas igual de buenas, se prefiere la más reciente.
- **Título** es el desempate final en orden ascendente: garantiza que
  el resultado sea determinista aunque dos películas empaten en
  calificación y año.
- **Duración** no se usa como criterio porque no cambia el puesto de
  una película dentro de un ranking; se conserva solo como información
  informativa del catálogo.

## Algoritmos implementados

### 1. Criterio multinivel (`comparar`)

`PeliculaOrdenador.comparar(a, b)` retorna un negativo cuando `a` debe
ir antes que `b`, un positivo cuando debe ir después y `0` cuando son
equivalentes. Primero compara calificación en orden inverso
(`Double.compare(b, a)`), luego año en orden inverso
(`Integer.compare(b, a)`) y finalmente título con `compareTo`, que ya
es ascendente. El mismo contrato respeta la clase `ComparadorPeliculas`.

### 2. Ordenamiento manual por selección

En cada pasada se busca la mejor película restante según el criterio
(`comparar(...) < 0`) y se ubica al inicio de la zona sin ordenar
mediante un intercambio. Es sencillo de explicar, hace a lo sumo
`n - 1` intercambios y su número de comparaciones es siempre
`n(n-1)/2`, sin importar el estado inicial del arreglo. Se contabilizan
las comparaciones y los intercambios realizados.

### 3. Ordenamiento con `Comparator`

`ComparadorPeliculas` implementa `java.util.Comparator<Pelicula>` con
el mismo criterio y también cuenta sus comparaciones. `Arrays.sort`
utiliza internamente TimSort, un ordenamiento estable de complejidad
O(n log n), lo que permite contrastar el costo del algoritmo manual
frente al mecanismo de la biblioteca estándar.

## Complejidad temporal

| Ordenamiento | Algoritmo | Comparaciones | Complejidad |
|---|---|---|---|
| Manual | Selección | n(n-1)/2 siempre | O(n²) |
| Con `Comparator` | TimSort de `Arrays.sort` | O(n log n) | O(n log n) |

Evidencia experimental con los contadores del programa:

| Tamaño del catálogo | Selección (manual) | `Arrays.sort` + `Comparator` |
|---|---|---|
| 12 películas | 66 comparaciones, 7 intercambios | 29 comparaciones |
| 1.000 películas aleatorias | 499.500 comparaciones | 8.702 comparaciones |

Con 1.000 películas la selección requiere 57 veces más comparaciones
que TimSort, y la diferencia crece con el tamaño del arreglo: la
selección es cuadrática mientras TimSort crece de forma casi lineal
(O(n log n)). El ordenamiento manual fue elegido por su claridad
didáctica, no por su rendimiento.

## Ejemplo de salida

```text
=== CLASIFICACION DE PELICULAS ===
Peliculas del catalogo: 12

--- Catalogo original ---
 1. Pulp Fiction (1994) - 154 min - 8.9
 2. El Padrino (1972) - 175 min - 9.2
 3. Coco (2017) - 105 min - 8.4
 4. El Caballero de la Noche (2008) - 152 min - 9.0
 5. La Lista de Schindler (1993) - 195 min - 8.9
 6. Forrest Gump (1994) - 142 min - 8.5
 7. El Padrino: Parte II (1974) - 202 min - 9.0
 8. Interestelar (2014) - 169 min - 8.6
 9. El Rey León (1994) - 88 min - 8.5
10. El Retorno del Rey (2003) - 201 min - 8.9
11. Cadena Perpetua (1994) - 142 min - 9.3
12. Dunkerque (2017) - 106 min - 8.4
Esta ordenado? (antes)   : false

--- Ordenamiento manual (seleccion) ---
 1. Cadena Perpetua (1994) - 142 min - 9.3
 2. El Padrino (1972) - 175 min - 9.2
 3. El Caballero de la Noche (2008) - 152 min - 9.0
 4. El Padrino: Parte II (1974) - 202 min - 9.0
 5. El Retorno del Rey (2003) - 201 min - 8.9
 6. Pulp Fiction (1994) - 154 min - 8.9
 7. La Lista de Schindler (1993) - 195 min - 8.9
 8. Interestelar (2014) - 169 min - 8.6
 9. El Rey León (1994) - 88 min - 8.5
10. Forrest Gump (1994) - 142 min - 8.5
11. Coco (2017) - 105 min - 8.4
12. Dunkerque (2017) - 106 min - 8.4
Esta ordenado? (despues) : true
Comparaciones realizadas : 66
Intercambios realizados  : 7

--- Ordenamiento con Comparator ---
 1. Cadena Perpetua (1994) - 142 min - 9.3
 2. El Padrino (1972) - 175 min - 9.2
 3. El Caballero de la Noche (2008) - 152 min - 9.0
 4. El Padrino: Parte II (1974) - 202 min - 9.0
 5. El Retorno del Rey (2003) - 201 min - 8.9
 6. Pulp Fiction (1994) - 154 min - 8.9
 7. La Lista de Schindler (1993) - 195 min - 8.9
 8. Interestelar (2014) - 169 min - 8.6
 9. El Rey León (1994) - 88 min - 8.5
10. Forrest Gump (1994) - 142 min - 8.5
11. Coco (2017) - 105 min - 8.4
12. Dunkerque (2017) - 106 min - 8.4

Ambos ordenamientos coinciden  : true
Comparaciones con Comparator  : 29
Comparaciones con seleccion   : 66
```

El catálogo incluye empates preparados a propósito: tres películas con
calificación 8.9 (años 2003, 1994 y 1993), dos con 9.0 (2008 y 1974),
dos con 8.5 del año 1994 y dos con 8.4 del año 2017, de modo que los
tres niveles del criterio se ejercitan en la demostración.

## Pruebas

`probar.ps1` ejecuta 46 verificaciones agrupadas en:

- Signo del comparador multinivel (`comparar`) para cada criterio.
- Criterio de calificación descendente en el ranking.
- Desempate por año descendente en los grupos de 9.0 y 8.9.
- Desempate por título ascendente en los grupos de 8.5 y 8.4.
- Ranking completo contra el orden esperado.
- Contadores: 66 comparaciones y 7 intercambios con el catálogo, y el
  mismo comportamiento con el arreglo ya ordenado y en orden inverso.
- Casos borde: `null`, arreglo vacío, una sola película, películas
  equivalentes en los tres criterios y pares desordenados.
- `Comparator` con `Arrays.sort`: mismo ranking, película por
  película, y menos comparaciones que la selección.
- Catálogo de 1.000 películas aleatorias: queda ordenado, la selección
  hace exactamente 499.500 comparaciones y `Arrays.sort` coincide con
  el ordenamiento manual.

Resultado esperado:

```text
OK: 46 pruebas ejecutadas correctamente.
```

## Flujo de Git utilizado

El desarrollo se realizó de forma progresiva en la rama
`desarrollo-ejercicio` y luego se integró en `main`:

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

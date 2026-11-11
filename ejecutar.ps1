$ErrorActionPreference = "Stop"
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
$raiz = Split-Path -Parent $MyInvocation.MyCommand.Path
$salida = Join-Path $raiz "out/main"
New-Item -ItemType Directory -Force -Path $salida | Out-Null

$fuentes = Get-ChildItem -Path (Join-Path $raiz "src/main/java") -Recurse -Filter *.java |
    ForEach-Object { $_.FullName }

javac -encoding UTF-8 -d $salida $fuentes
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

java "-Dfile.encoding=UTF-8" -cp $salida edu.unicartagena.peliculas.Main @args
exit $LASTEXITCODE

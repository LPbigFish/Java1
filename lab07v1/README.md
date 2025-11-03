# Java 1 - 7. cvičení v1

## Projekt
Z vašeho projektu z minulého cvičení nebo z projektu s řešením minulého
cvičení (<https://gitlab.vsb.cz/jez04-vyuka/java1/labs/lab07v1.git>)

## ResourceManager
Vytvořte novou třídu `ResourceManager`, která se stará o načítání a ukládání obrázků.
Pokud někde je třeba načíst obrázek, použije se `ResourceManager`, který vrátí obrázek,
pokud je již načten, nebo obrázek načte, uloží a vrátí.

Třída bude mít:

- privátní statickou kolekci obrázků typu `Map`. Vyberte vhodnou implementaci generického typu.
- metodu `public static Image getImage(Class<?> clazz, String name)`. Metoda otestuje,
  zda obrázek s požadovaným názvem již je v mapě obrázků. Pokud ne, obrázek se načte,
  uloží a vrátí. Pokud ano, obrázek se získá z mapy a vrátí.
- Vytvořte generickou metodu `getRandomElement` s parametrem kolekce (vyberte vhodný typ).
  Metoda vybere náhodný prvek z kolekce a vrátí jej jako stejný typ, který je uložen
  uvnitř kolekce (generická metoda).

V `BulletAnimated` a `Ufo` použijte `ResourceManager` k získání náhodného obrázku na základě
názvů v statické proměnné `names`

## Ufo Spawner
Vytvořte třídu `UfoSpawner`, která implementuje rozhraní `WorldEntity`. Nechte metodu
`draw` prázdnou a v metodě `simulate` generujte nové ufo v náhodných intervalech.
Použijte metodu `World.add`.

Nezapomeňte vložit `UfoSpawner` jako prvek světa (v konstruktoru světa vytvořte instanci `UfoSpawner` a
vložte ji do kolekce `entities`)

## Třídění entit světa
Aby byly všechny objekty viditelné, měly by být vykreslovány ve správném pořadí. Nejprve by měly být vykresleny ufo.
Jako poslední se vykreslují všechny kulky, aby zůstaly nahoře.
Všechny velké UFO by měly být vykresleny jako první a menší (jak šířkou, tak výškou) jako poslední, aby zůstaly viditelné.

Ve třídě `Ufo` vytvořte metody `getWidth()` a `getHeight()`.

Vytvořte v `World` instanční proměnnou (field) typu `Comparator<DrawableSimulable>`. V konstruktoru vytvořte
implementaci`Comparator` pro třídění `DrawableSimulable` v kolekci `entities`, která zajistí pořadí vykreslování objektů,
jak je popsáno výše.

## Způsob odevzdání
Projekt odevzdejte do systému Kelvin (právě jste zde). Nahrajte zde celý adresář **src** a soubor **pom.xml**
dle níže zobrazeného odkazu na video.

Následně bude projekt zkompilován a provedou se Unit Testy. Vzhledem k povaze projektu a prozatímnímu
testovacímu využití systému Kelvin v předmětu Java 1 v případě selhání nezoufejte.

Jedná se o pomocný test, vše bude ještě hodnoceno ručně. Důležité je nahrát soubory aby bylo možno vše vyhodnotit
a provést analýzu na plagiáty. Věřím, že je to je formalita a všichni z Vás tvoří vlastní kód.

[Podrobný popis odevzdání do Kelvinu](https://swi.cs.vsb.cz/jezek/student-information/Java1/kelvin-submision.html)

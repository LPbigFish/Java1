# Java 1 - 12. cvičení v1

## Projekt
Z vašeho projektu z minulého cvičení nebo z projektu s řešením minulého
cvičení (<https://gitlab.vsb.cz/jez04-vyuka/java1/labs/lab12v1.git>)

## Změny
- Do rozhraní `DrawableSimulable` přidána metoda `Rectangle2D getBoundingBox();` Doimplementováno kde je třeba.
- V rozhraní `Collisionable` změněna metoda `intersect` na `boolean intersect(Collisionable another);`
- Do třídy `Ufo` přidána metoda `setPositionOfMiddle(Point2D position)` 

## Použití Stream.generate
Místo smyčky `for` v konstruktoru použijte `Stream.generate` k vytvoření několika UFO na začátku.

## Generic classes
Vytvořte třídu pro skupinu objektů, abyste je mohli přesouvat společně jako formaci. Použijte ji k vytvoření "vlny" nepřátel.

### třída Formation:
- je abstraktní
- dědí/rozšiřuje `WorldEntity`, implementuje `Collisionable`
- generický parametr `T` je nastaven aby povolil (bounds) jen potomky z `DrawableSimulable`
- mé kolekci (list) `entitiesInFormation` typů `T`
- má pozici a rychlost (`Point2D`)  - pozice a world je už v `WorldEntity`
- má konstruktor `public Formation(World world, Point2D position, T... entities)`
- metoda `drawInternal` volá metody `draw` na všechny prvky kolekce `entitiesInFormation`
- definuje abstraktní metodu `public abstract void simulateElements(double delta)`
- metoda `simulate` posouvá pozici na základě rychlosti a volá metodu `simulateElements`
- metoda `getBoundingBox` vrací obdelník, který je spojením všech obdelníků získaných od prvků `entitiesInFormation`
- vytvořte metodu `public List<Collisionable> getCollisionableEntities()`
- metoda `hitBy` koliduje všechny prvky vrácené pomocí metody `getCollisionableEntities`

## Formace rotujících Ufo
Vytvořte třídu `RotatingUfoFormation` jako rozšíření `Formation`.

- použijte konstruktor `public RotatingUfoFormation(World world, Point2D position, Ufo... entities)`
- metoda `simulateElements` vezme první ufo a dá ho do středu a ostatní budou rotovat kolem něj. Například takto:
  ````java
        angle = angle + rotationSpeed * delta;
        if(entitiesInFormation.isEmpty()){
            return;
        }
        Ufo middle = entitiesInFormation.getFirst();
        middle.setPositionOfMiddle(position);
        int rotCount = entitiesInFormation.size()-1;
        double radius = 100;
        for (int i = 1; i < entitiesInFormation.size(); i++) {
            Ufo ufo = entitiesInFormation.get(i);
            double currentAngle = Math.toRadians(angle + 360/rotCount*(i-1));
            Point2D ufoPosition = position.add(Math.cos(currentAngle)*radius, Math.sin(currentAngle)*radius);
            ufo.setPositionOfMiddle(ufoPosition);
        }
  ````
Použijte třídu `RotatingUfoFormation` ve `World`.

## Odstraňování entit z forrmací
Přidejte `public boolean remove(DrawableSimulable entity)` do třídy `Formation`.
A vyřešte odstraňování ve třídě `World` a třídě `Bullet`.


## Způsob odevzdání
Projekt odevzdejte do systému Kelvin (právě jste zde). Nahrajte zde celý adresář **src** a soubor **pom.xml**
dle níže zobrazeného odkazu na video.

Následně bude projekt zkompilován a provedou se Unit Testy. Vzhledem k povaze projektu a prozatímnímu
testovacímu využití systému Kelvin v předmětu Java 1 v případě selhání nezoufejte.

Jedná se o pomocný test, vše bude ještě hodnoceno ručně. Důležité je nahrát soubory aby bylo možno vše vyhodnotit
a provést analýzu na plagiáty. Věřím, že je to je formalita a všichni z Vás tvoří vlastní kód.

[Podrobný popis odevzdání do Kelvinu](https://swi.cs.vsb.cz/jezek/student-information/Java1/kelvin-submision.html)

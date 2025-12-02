package lab.regexp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Parser {

    public static void main(String[] args) {
        System.out.println("Starting Java programing test - part Parser.");
        Parser parser = new Parser();
        String fileContent = """
            Toto je zkušební text.
            Něco jako   tab a	tab.
            Další(ano) část.

            ()
            jako jako ano,ano
            """;
        for (String word : parser.splitToWorlds(fileContent)) {
            System.out.println("'" + word + "'");
        }
        String numbers = """
            Toto je zkušební text.
            7 9.5
            700 -987.012
            Něco jako   tab a	ta11b.
            Další(ano) čá23.8st.

            ()
            jako jako ano,ano
            """;
        for (Float f : parser.findNumbers(numbers)) {
            System.out.println(f);
        }
    }

    /**
     * Metoda rozdělí content na jednotlivá slova podle:
     * mezer, tabulátorů, závorek, teček a konců řádků
     * Slova se převedou na malé znaky a vrátí se v kolekci bez duplicit.
     */
    public Collection<String> splitToWorlds(String content) {
        content = content.trim();
        return Stream.of(content.split("[ .,()\n\t]")).filter(x -> !x.isEmpty()).toList();
    }

    /**
     * Metoda najde v textu desetiná čísla a vrátí je jako kolekci.
     */
    public List<Float> findNumbers(String text) {
        final Pattern p = Pattern.compile("[-\\d]*\\.\\d+");
        final Matcher m = p.matcher(text);
        return m.results().filter(x -> {
            try {
                Float.parseFloat(x.group());
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }).map(x -> Float.valueOf(x.group())).toList();
    }
}

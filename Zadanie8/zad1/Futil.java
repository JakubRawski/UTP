package zad1;


import sun.text.normalizer.UTF16;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.*;

public class Futil {
    //kodowanie cp1250
    private static String codeFIle = "cp1250";
    public static void processDir(String src, String res) {
        //odczyt
        Path path = Paths.get(src);
        //zapis
        Path resFile = Paths.get(res);
        //set potrzebny do znajdywania unikalnych sciezek
        Set<Path> unique = new HashSet<Path>();
        try (Stream<Path> walk = Files.walk(path)) {
            //filtruje pliki z zawartosci foldera
            List<String> lines = walk.filter(Files::isRegularFile)
                    //sprawdza czy ten plik byl czytany, jak nie, zwraca Liste linijek pliku
                    .flatMap(name -> {
                        //konwertuje nazwe sciezki na pelna sciezke
                        Path p =path.resolve(name);
                        if(unique.add(p)){
                            //sprawdza czy do setu mozna dodac nowa sciezke
                            try {
                                //zwraca w streamie liste linijek ktore byly w pliku .txt
                                return readLine(path.resolve(name)).stream();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        else {
                            //jak nie, pomijamy plik
                            return Stream.empty();
                        }
                        //zbiera output do listy
                    }).collect(Collectors.toList());
            //zapisuje do podanej sciezki w kodowaniu cp1250
            Files.write(resFile, lines, StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //konwertuje zawartosc podana w sciezce do listy linijek
    private static List<String> readLine(Path file) throws IOException {
        return Files.readAllLines(file, Charset.forName(codeFIle));
    }

}

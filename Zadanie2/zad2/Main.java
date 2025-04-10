/**
 *
 *  @author Rawski Jakub S30532
 *
 */

package zad2;


/*<-- niezbędne importy */
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {
    //System.out.println("zadanie 2"); //check ktory plik
    // Lista destynacji: port_wylotu port_przylotu cena_EUR 
    List<String> dest = Arrays.asList(
      "bleble bleble 2000",
      "WAW HAV 1200",
      "xxx yyy 789",
      "WAW DPS 2000",
      "WAW HKT 1000"
    );
    double ratePLNvsEUR = 4.30;
    //KOCHAM fakt ze moge skopiowac zadanie wczesniejsze zmieniajac ListCreator na stream
    List<String> result = dest.stream().
            filter(nazwa -> {
              String[] parts = nazwa.split(" ");//rozdziela na czesci by lawteij wyszukac fragmenty
              return parts[0].equals("WAW"); //czy 1 czesc jest rowna WAW
            }).
            map( cena -> {
              String[] parts = cena.split(" "); //rozdziela na czesci by lawteij wyszukac fragmenty
              //WAZNE zmienione xrate dla ratePLNvsEUR z powodu zadania
              String endname = "to "+ parts[1]+" - price in PLN: "+ Double.parseDouble(parts[2]) * ratePLNvsEUR; //tworzy napis
              endname = endname.substring(0,endname.length()-2); //usuwa te .0 z zadania
              //TODO jak sie SDKP wysypie to dac Integer.parseInteger
              return endname;
            }).collect(Collectors.toList());

    /*<-- tu należy dopisać fragment
     * przy czym nie wolno używać żadnych własnych klas, jak np. ListCreator
     * ani też żadnych własnych interfejsów
     * Podpowiedź: należy użyć strumieni
     */
    for (String r : result) System.out.println(r);
  }
}

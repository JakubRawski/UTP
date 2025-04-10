/**
 *
 *  @author Rawski Jakub S30532
 *
 */

package zad1;


import java.util.*;

public class Main {

  static List<String> getPricesInPLN(List<String> destinations, double xrate) {
    return ListCreator.collectFrom(destinations)
                       .when(  /*<-- lambda wyrażenie
                                *  selekcja wylotów z Warszawy (zaczynających się od WAW)
                                */
                               nazwa -> {
                                String[] parts = nazwa.split(" ");//rozdziela na czesci by lawteij wyszukac fragmenty
                                return parts[0].equals("WAW"); //czy 1 czesc jest rowna WAW
                               }
                        )
                       .mapEvery( /*<-- lambda wyrażenie
                                   *  wyliczenie ceny przelotu w PLN
                                   *  i stworzenie wynikowego napisu
                                   */
                               cena -> {
                                 String[] parts = cena.split(" "); //rozdziela na czesci by lawteij wyszukac fragmenty
                                 String endname = "to "+ parts[1]+" - price in PLN: "+ Double.parseDouble(parts[2]) * xrate; //tworzy napis
                                 endname = endname.substring(0,endname.length()-2); //usuwa te .0 z zadania
                                   //TODO jak sie SDKP wysypie to dac Integer.parseInteger
                                 return endname;
                               }
                        );
  }

  public static void main(String[] args) {
      //System.out.println("zadanie 1"); //check ktory plik
    // Lista destynacji: port_wylotu port_przylotu cena_EUR 
    List<String> dest = Arrays.asList(
      "bleble bleble 2000",
      "WAW HAV 1200",
      "xxx yyy 789",
      "WAW DPS 2000",
      "WAW HKT 1000"
    );
      double ratePLNvsEUR = 4.30;
    List<String> result = getPricesInPLN(dest, ratePLNvsEUR);
    for (String r : result) System.out.println(r);
  }
}

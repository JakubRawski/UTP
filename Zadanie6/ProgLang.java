/**
 *
 *  @author Rawski Jakub S30532
 *
 */
package zad1;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProgLang {

    public HashMap<String, ArrayList<String>> programmersLang; //Key -> Jezyk programowania
    public HashMap<String, ArrayList<String>> programmersSurnames; //Key -> Nazwiska
    public ProgLang(String src) throws IOException {
        this.programmersLang = new LinkedHashMap<>();
        Files.readAllLines(Paths.get(src)).forEach(line -> { //czytanie z pliku
            String keyMap = line.split("\t")[0]; //nasz klucz dla 1 linikji
            String[] keyValue = line.substring(keyMap.length()+1).split("\t"); /*rozdzielamy klucz od wartosci, +1 by uzunac tabulacje (zgadza sie, ma ona 1 znak : '\t')
            */
            programmersLang.put(keyMap, new ArrayList<>(Arrays.stream(Arrays.copyOfRange(keyValue,0,keyValue.length)).collect(Collectors.toCollection(LinkedHashSet::new))));
            //tworzenie programmersLang gdzie Klucz to keyMap a wartosci to zawartosc keyValue
        });
        this.programmersSurnames = new LinkedHashMap<>();
        programmersLang.forEach((key, value) -> { //na postawie programmersLang tworzymy programmersSurnames (nie ma sensu 2 razy plik czytac)
            value.forEach(newValue -> {
                if(programmersSurnames.containsKey(newValue)){ //sprawdzamy czy klucz (nazwisko) zostalo wczejsniej dodane
                    ArrayList<String> checkValue = programmersSurnames.get(newValue);
                    if(!checkValue.contains(key)){ //zabezpiecza przed duplikatami
                        programmersSurnames.get(newValue).add(key);
                    }
                }
                else{ //gdy jeszcze nazwiska nie bylo
                    programmersSurnames.put(newValue, new ArrayList<>(Collections.singleton(key)));
                }
            });
        });
    }
    //gettery
    public HashMap<String, ArrayList<String>> getLangsMap(){
        return programmersLang;
    }

    public HashMap<String, ArrayList<String>> getProgsMap(){
        return programmersSurnames;
    }
    //metody pomocnicze
    public <K,V> Map<K,V> sorted(Map<K,V> mapa, Comparator<Map.Entry<K,V>> function){
        Map<K, V> res = mapa.entrySet().stream().sorted(function).collect(Collectors.toMap(
                Map.Entry::getKey,Map.Entry::getValue, //tworzy nowa linkedmape posortowana wg function
                (key,value) -> key, LinkedHashMap::new));
        return res;
    }

    public <K,V> Map<K,V> filtered(Map<K,V> mapa, Predicate<Map.Entry<K,V>> function){
        Map<K, V> res = mapa.entrySet().stream().filter(function).collect(Collectors.toMap(
                Map.Entry::getKey,Map.Entry::getValue, //tworzy nowa linkedmape przefiltrowana wg function
                (key,value) -> key, LinkedHashMap::new));
        return res;
    }
    //gettery z funkcjami "edytujacymi"
    public Map<String,ArrayList<String>> getLangsMapSortedByNumOfProgs(){ //nazwa funkcji mowi sama za siebie
        Map<String,ArrayList<String>> res = this.sorted(programmersLang,Map.Entry.<String,ArrayList<String>>comparingByValue(Collections.reverseOrder(Comparator.comparingInt(ArrayList::size))));
        return res;
    }

    public Map<String,ArrayList<String>> getProgsMapSortedByNumOfLangs(){ //nazwa funkcji mowi sama za siebie
        Map<String,ArrayList<String>> res = this.sorted(programmersSurnames,Map.Entry.<String,ArrayList<String>>comparingByValue(Collections.reverseOrder(Comparator.comparingInt(ArrayList::size))).thenComparing(Map.Entry.comparingByKey()));
        return res;
    }

    public Map<String,ArrayList<String>> getProgsMapForNumOfLangsGreaterThan(int n){ //nazwa funkcji mowi sama za siebie
        Map<String,ArrayList<String>> res = this.filtered(programmersSurnames, val -> val.getValue().size() > n);
        return res;
    }


}

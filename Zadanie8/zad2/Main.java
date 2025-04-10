/**
 *
 *  @author Rawski Jakub S30532
 *
 */

package zad2;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.stream.*;

public class Main {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(new URL("http://wiki.puzzlers.org/pub/wordlists/unixdict.txt").openStream()));
    LinkedHashMap<String, List<String>> anagramMap = br.lines().collect(Collectors.groupingBy(
            (word) -> {
              char[] wordToChar = word.toCharArray();
              Arrays.sort(wordToChar);
              return  String.valueOf(wordToChar);
            }, () -> {
              LinkedHashMap<String, List<String>> res = new LinkedHashMap<>();
              return res;
            },Collectors.toList()
    ));
    int max = anagramMap.values().stream().map(List::size).max(Integer::compare).get();
    anagramMap.values().stream().filter(size -> size.size() == max).forEach(list -> {
        String res = String.join(" ", list);
        System.out.println(res);
    });
  }

}

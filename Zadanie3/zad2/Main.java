/**
 *
 *  @author Rawski Jakub S30532
 *
 */

package zad2;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*<--
 *  niezbędne importy
 */
public class Main {
  public static void main(String[] args) {
    /*<--
     *  definicja operacji w postaci lambda-wyrażeń:
     *  - flines - zwraca listę wierszy z pliku tekstowego
     *  - join - łączy napisy z listy (zwraca napis połączonych ze sobą elementów listy napisów)
     *  - collectInts - zwraca listę liczb całkowitych zawartych w napisie
     *  - sum - zwraca sumę elmentów listy liczb całkowitych
     */

    Function<String,List<String>> flines = (String path) -> { //flines
      ArrayList<String> res = new ArrayList<>();
      try{
        BufferedReader BR = new BufferedReader(new FileReader(path));
        String linia = "";
        linia = BR.readLine();
        while (linia != null){
          res.add(linia);
          linia = BR.readLine();
        }
        BR.close(); //zabeczpieczenie
      } catch (FileNotFoundException e) {
          throw new RuntimeException(e);
      } catch (IOException e) {
          throw new RuntimeException(e);
      }
      return res;
    };

    Function<List<String>,String> join = (List<String> res) -> { //join
      String fin = "";
      for(String s : res){  //sumuje napis
        fin += s; //nie chce mi sie zmieniac na bufferedReader jak podpowiada Intellij
      }
      return fin;
    };

    Function<String,List<Integer>> collectInts = (String str) ->{ //collectInts
      Pattern p = Pattern.compile("[0-9]+"); //lapie prawidlowe liczby
      Matcher m = p.matcher(str);
      ArrayList<Integer> res = new ArrayList<>();
      while (m.find()){
        res.add(Integer.parseInt(m.group()));
      }
      return res;
    };

    Function<List<Integer>,Integer> sum = (List<Integer> num) -> {
      int countNum = 0;
      for (Integer in : num){
        countNum += in;
      }
      return countNum;
    };
    //    Warszawa 100 Kielce 200 Szczecin 300 kopiowac jako argumenty
    String fname = System.getProperty("user.home") + "/LamComFile.txt";
    InputConverter<String> fileConv = new InputConverter<>(fname);
    List<String> lines = fileConv.convertBy(flines);
    String text = fileConv.convertBy(flines, join);
    List<Integer> ints = fileConv.convertBy(flines, join, collectInts);
    Integer sumints = fileConv.convertBy(flines, join, collectInts, sum);

    System.out.println(lines);
    System.out.println(text);
    System.out.println(ints);
    System.out.println(sumints);

    List<String> arglist = Arrays.asList(args);
    InputConverter<List<String>> slistConv = new InputConverter<>(arglist);  
    sumints = slistConv.convertBy(join, collectInts, sum);
    System.out.println(sumints);

  }
}

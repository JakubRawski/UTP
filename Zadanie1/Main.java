/**
 *
 *  @author Rawski Jakub S30532
 *
 */

package zad1;



import java.util.*;

//import static zad1.ListCreator.collectFrom; nie trzeba po listCreator


public class Main {
  public Main() {
    List<Integer> src1 = Arrays.asList(1, 7, 9, 11, 12);/*<-- tu dopisać inicjację elementów listy */
    System.out.println(test1(src1));

    List<String> src2 = Arrays.asList("a", "zzzz", "vvvvvvv" );/*<-- tu dopisać inicjację elementów listy */
    System.out.println(test2(src2));
  }

  public List<Integer> test1(List<Integer> src) {
    Selector <Integer> sel = new Selector<Integer>()  {

      @Override
      public boolean select(Integer element) {
        if(element < 10) return true;
        else return false;
      }
    }; /*<-- definicja selektora; bez lambda-wyrażeń; nazwa zmiennej sel */
    Mapper <Integer,Integer> map = new Mapper<Integer,Integer> () {
      @Override
      public Integer map(Integer element) {
        return element + 10;
      }
    }; /*<-- definicja mappera; bez lambda-wyrażeń; nazwa zmiennej map */
    return   /*<-- zwrot wyniku
      uzyskanego przez wywołanie statycznej metody klasy ListCreator:
     */  ListCreator.collectFrom(src).when(sel).mapEvery(map);
  }

  public List<Integer> test2(List<String> src) {
    Selector <String> sel= new Selector<String>() {
      @Override
      public boolean select(String element) {
        if(element.length() > 3) return true;
        else return false;
      }
    };/*<-- definicja selektora; bez lambda-wyrażeń; nazwa zmiennej sel */
    Mapper <String,Integer> map = new Mapper<String, Integer>() {
      @Override
      public Integer map(String element) {
        return element.length() + 10;
      }
    };  /*<-- definicja mappera; bez lambda-wyrażeń; nazwa zmiennej map */
    return   /*<-- zwrot wyniku
      uzyskanego przez wywołanie statycznej metody klasy ListCreator:
     */  ListCreator.collectFrom(src).when(sel).mapEvery(map);
  }

  public static void main(String[] args) {
    new Main();
  }
}

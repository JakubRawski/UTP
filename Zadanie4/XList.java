/**
 *
 *  @author Rawski Jakub S30532
 *
 */

package zad1;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
//import java.util.List;//za duzo metod do dodania ktore nie sa potzrebne

public class XList <E>extends ArrayList<E> {

    @SafeVarargs //E...
    public XList(E... el) {
        super(Arrays.asList(el));
    }

    public XList(Collection<E> prevList) {
        super(prevList);
    }

    @SafeVarargs  //E...
    public static <E> XList<E> of(E... el) {
        return new XList<>(el);
    }

    public static <E> XList<E> of(Collection<E> prevList) {
        return new XList<>(prevList);
    }

    public static XList<String> charsOf(String napis) {
        XList<String> res = new XList<>(); //nowa lista
        char[] listaZnakow = napis.toCharArray(); //konwersja na znaki
        String charAsString;
        for (char znak : listaZnakow) {
            charAsString = String.valueOf(znak); //zmienianie char na string
            res.add(charAsString); //wpisywanie
        }
        return res;
    }


    public static XList<String> tokensOf(String napis, String sep) {
        XList<String> resS = new XList<>(Arrays.asList(napis.split(sep)));
        return resS;
    }

    public static XList<String> tokensOf(String napis) {
        //XList<String> resR = new XList<>(Arrays.asList(napis.split(" ")));
        //return ResR
        return tokensOf(napis, " "); //polimorfizm istnieje
    }

    public XList<E> union(Collection<E> mergeList) {
        XList<E> mergedList = new XList<>(this);
        mergedList.addAll(mergeList); //return zwraca true....
        //return  mergedList.addAll(mergeList);//nie zadziala
        return mergedList; //zmuszony przez addAll osobny return
    }

    @SafeVarargs  //E...
    public final XList<E> union(E... el) {
        return union(Arrays.asList(el));
    }

    public XList<E> diff(Collection<E> checkList) {
        XList<E> checkedList = new XList<>(checkList); //konwersja kolekcji na XList
        XList<E> res = new XList<>(this); //praca na duplikacie by nie pomieszać z iteratorem
        for (E diffEl : checkedList) { //dla kazdego elementu z checkList
            for (E priorEl : this) { //sprawdzamy kazdy element z tablicy docelowej
                if (diffEl.equals(priorEl)) { // == sie wywala na obiektach, porownuje zawartosc
                    res.remove(priorEl);
                }
            }
        }
        return res;
    }

    public XList<E> unique() {
        HashSet<E> uniqueCheck = new LinkedHashSet<>(this);
        //musi byc LinkedHAshSet bo SDKP sie czepia jak jest to zwykly set
        return new XList<>(uniqueCheck);
    }

    private <T> XList<XList<T>> combineRek(List<XList<T>> res) {
        XList<XList<T>> endList = new XList<>();
        if (res.isEmpty()) { //warunek graniczny od tylu

            endList.add(new XList<>());
            return endList;
        }
        for (T element : res.get(res.size() - 1)) {
            List<XList<T>> finRes = res.subList(0, res.size() - 1); //bierzemy fragment
            XList<XList<T>> temp = combineRek(finRes); //rekunrencja
            for (XList<T> el : temp) {
                el.add(element);

            }
            endList.addAll(temp);
        }
        return endList;
    }

    public <T> XList<XList<T>> combine() {
        XList<XList<T>> Queue = new XList<>(); //Queue jest po to by nie bylo java.lang.ClassCastException
        for (List<T> t : (List<List<T>>) this) {
            Queue.add(XList.of(t)); //dodajemy do kolejki elementy na ktoych bedziemy operowac
        }
        return this.combineRek(Queue);
    }


    /*
    // jak ma do najmniejszego elementu sortowac
    public <T> XList<XList<E>> combine(){
        XList<List<E>> Queue = new XList<>();
        XList<XList<E>> res = new XList<>();
        for(List<E> element : (List<XList<E>>) this){
            Queue.add(XList.of(element));
        }
        combineRek( (XList<List<E>>) Queue, res, new XList<>(), 0);
        return res;
    }

    private void combineRek(XList<List<E>> lista, XList<XList<E>> res, XList<E> currINt, int depth){
        if (depth == lista.size()){
            res.add(new XList<>(currINt));
            return;
        }
        for (E item : lista.get(depth)){
            currINt.add(item);
            combineRek(lista, res, currINt, depth + 1);
            currINt.remove(currINt.size() - 1);
        }
    }
    */
    public <T> XList<T> collect(Function<E, T> fun) {
        XList<T> res = new XList<>(); //ta tablica bedzie miec te elementy ktore fun zaakceptuje
        for (E element : this) {
            res.add(fun.apply(element)); //funckja akceptuje

        }
        return res;
    }

    public String join() { //yay, polimorfizm
        return join("");
    }

    public String join(String sep) {
        String res = "";
        for (E element : this) {
            res += (element.toString() + sep); //dodajemy do stringa elementy z tablicy
        }
        res = res.substring(0, res.length() - sep.length()); //usuwamy nadmiarowy separator
        return res;

    }

    public void forEachWithIndex(BiConsumer<E, Integer> cons) { //biConsumer moze przyjac 2 argumenty
        for (int i = 0; i < this.size(); i++) {
            cons.accept(this.get(i), i); //this.get(i) -> wartosc, i -> index
        }
    }

}

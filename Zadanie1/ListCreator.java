/**
 *
 *  @author Rawski Jakub S30532
 *
 */

package zad1;


import java.util.ArrayList;
import java.util.List;

public class ListCreator <E> extends ArrayList<E> { // Uwaga: klasa musi być sparametrtyzowana
    //ArrayList<E> mainLista;
    //extend zrobic zamiast pola bo cierpie dajac mianLista. zamiast this
    public ListCreator(List<E> lista) {
        for (E ele : lista){ //lecimy po petli o dodajemy elementy
            this.add(ele);
        }
    }

    public static <E> ListCreator<E> collectFrom(List<E> lista) {
        ListCreator<E> creLista = new ListCreator<>(lista); //konwertuje loste na listCreator
        return creLista;

    }

    public ListCreator<E> when(Selector<E> selektor){
        ArrayList<E> newList = new ArrayList<>(); //by nie wywalilo ConcurrentModificationException
        for (E ele : this){
            if(selektor.select(ele)){
                newList.add(ele);
            }
        }
        this.clear();
        for (E ele : newList){ //lecimy po petli o dodajemy elementy
            this.add(ele);
        }
        return this;
    }
    public <V> ListCreator<V> mapEvery(Mapper<E,V> mapper){ //V przyjmuje, E zwraca
        ArrayList<V> mappedList = new ArrayList<>();
        for (E ele : this){
            mappedList.add(mapper.map(ele)); //dodaje po edycji do nowej listy
        }
        return new ListCreator<>(mappedList);
    };

}

package zad1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class  ListCreator <E> extends ArrayList<E> { // Uwaga: klasa musi być sparametrtyzowana
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

    public  ListCreator<E> when(Function<E,Boolean> selektor){
        ArrayList<E> newList = new ArrayList<>(); //by nie wywalilo ConcurrentModificationException
        for (E ele : this){
            if(selektor.apply(ele)){
                newList.add(ele);
            }
        }
        this.clear();
        for (E ele : newList){ //lecimy po petli o dodajemy elementy
            this.add(ele);
        }
        return this;
    }
    public <V> ListCreator<V> mapEvery(Function<E,V> mapper){ //V przyjmuje, E zwraca
        ArrayList<V> mappedList = new ArrayList<>();
        for (E ele : this){
            mappedList.add(mapper.apply(ele)); //dodaje po edycji do nowej listy
        }
        return new ListCreator<>(mappedList);
    };

}

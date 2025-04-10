package zad1;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Maybe<T> {

    private T wartosc = null; //na poczatku nie ma nic

    public Maybe(T takenWartosc){
        this.wartosc = takenWartosc;
    }
    public static <T> Maybe<T> of(T newWartosc){
        return new Maybe(newWartosc);
    }
    public void ifPresent(Consumer c){
        if(wartosc != null){ //sprawdza czy nie jest nullem
            c.accept(wartosc);
        }
    }


    public <E> Maybe<E> map(Function<T,E> f){
            if(wartosc != null){
                return new Maybe<>(f.apply(wartosc)); //sprawdza czy nie jest nullem
            }
            return new Maybe<>(null); //wiemy ze to null
    }


    public T get(){
        if(wartosc == null){ //sprawdzamy czy to null
            throw new NoSuchElementException("maybe is empty");
        }
        return wartosc; //wiemy ze nie jest to null
    }
    public boolean isPresent(){
        if(wartosc == null) return false;
        return true;
    }

    public T orElse(T def){
        if(wartosc == null){
            return def;
        }
        return wartosc;
    }
    public Maybe<T> filter(Predicate<T> p){
        if(wartosc == null || p.test(wartosc)){
            return this;
        }
        return new Maybe<>(null);
    }

    @Override
    public String toString() {
        if(wartosc == null){
            return "Maybe is empty";
        }
        return "Maybe has value "+wartosc;
    }
}

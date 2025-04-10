package zad2;

import java.util.function.Function;

public class InputConverter<T> {
    public T inData;
    public InputConverter(T wartosc){
        this.inData = wartosc;
    }
    public <E> E convertBy(Function... f){
        Object currInData = this.inData; //generyki nie dzialaja poniewaz .apply zwraca Object
        for(Function i : f){
            currInData = i.apply(currInData);
        }
        return (E) currInData;
    }

}

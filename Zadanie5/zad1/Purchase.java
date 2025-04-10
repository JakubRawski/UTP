/**
 *
 *  @author Rawski Jakub S30532
 *
 */

package zad1;


import com.sun.xml.internal.bind.v2.model.core.ID;

import java.util.IllegalFormatConversionException;
import java.util.IllegalFormatWidthException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Purchase {

    public String idKlienta; //0
    public String nazwisko; //1
    public String nazwaTowaru; //2
    public double cena; //3, chce .0 na koniec
    public double ilosc; //4, chce .0 na koniec




    public Purchase(String src){
        String[] container = src.split(";");
        if(container.length != 5){ //gdy pseudo csv nie pasuje do wzorca
            throw new IllegalArgumentException("Powinno byc 5 argumentow, jest inaczej");
        }
        this.idKlienta = container[0];
        Pattern p = Pattern.compile("c[0-9]{5}$");
        Matcher m = p.matcher(idKlienta);
        if(!m.find()){ //gdy wartosc ID nie jest zgodna z REGEXEM
            throw new IllegalArgumentException("Identyfikator klienta ma postać\n" +
                    "cNNNNN\n" +
                    "gdzie N cyfra ze zbioru [0...9]\n" +
                    "np.\n" +
                    "c00001;Kowalski Jan;bułka;2;100");
        }
        this.nazwisko = container[1];
        this.nazwaTowaru = container[2];
        this.cena = Double.parseDouble(container[3]);
        this.ilosc = Double.parseDouble(container[4]);
    }

    @Override
    public String toString() {
        String res = idKlienta + ";" +
                nazwisko + ";" +
                nazwaTowaru + ";" +
                cena + ";" +
                ilosc;
        return res;
    }

    public String getidKlienta() {
        return idKlienta;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getNazwaTowaru() {
        return nazwaTowaru;
    } //dodalo z automatu

    public double getCena() {
        return cena;
    }

    public double getIlosc() {
        return ilosc;
    }
    public double getFullPrice() {
        double res = this.getCena() * this.getIlosc();
        return res;
    }
}

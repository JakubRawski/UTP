/**
 *
 *  @author Rawski Jakub S30532
 *
 */

package zad1;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Calc{
    public String doCalc(String cmd){
        try{
            //dynamiczna precyzja (max 7 znakow po przecinku)
            MathContext maxC = new MathContext(7);
            //nasze mozliwe operacje
            String avOperators = "+-*/";
            //rozdzielamy na 3 czesci
            String[] el = cmd.trim().split("\\s+");
            //1 liczba
            BigDecimal fir = new BigDecimal(el[0]);
            //znak
            String operator = el[1];
            //2 liczba
            BigDecimal sec = new BigDecimal(el[2]);
            //tablica "niewiadomoczego" 4x2 zawierajaca KAZDY mozliwy wynik
            //kocham fakt ze kazdy typ zlozony w Javie dziedziczy po Object
            //jestem swiadomy istnienia mapy choc ona wymusza bardziej zlozone operacje
            //tzn. extrakcja kluczy/wartosci
            Object[][] possibilities = new Object[][]{
                    {"+", fir.add(sec)}, //dodwanie
                    {"-", fir.subtract(sec)}, //odejmowanie
                    {"*", fir.multiply(sec)}, //mnozenie
                    {"/", fir.divide(sec, maxC)} //dzielenie, dokladnosc 7 wg maxC
            };
            //Rzutujemy poniewaz big decimal nie moze byc tablica, nawet jesli wynikiem jest 1 komorka
            //szukmya indexu zawierajacy szukany znak
            //potem pobiera drugi element wiersza (albo kolumny w zalezosci jak patrzymy na tablice) zwracajac komorke 1x1 z wynikiem
            BigDecimal res = (BigDecimal) possibilities[avOperators.indexOf(operator)][1];
            return res.toString();
            //zle argumenty/dzielenie przez 0/ cokolwiek innego niezgodnego z zalozeniami
        } catch (Exception exp) {
            return "Invalid command to calc";
        }
    }
}  

/**
 *
 *  @author Rawski Jakub S30532
 *
 */

package zad2;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Anagrams {
    ArrayList<String> listaSlow = new ArrayList<>();

    public Anagrams(String allwords){ //to samo co w zad 1
        try {
            Scanner scan = new Scanner(new File(allwords));
            for(String slowo; scan.hasNext();){
                slowo = scan.next(); //bierzemy nastepny element po spacji
                listaSlow.add(slowo);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ArrayList<String>> getSortedByAnQty(){
        String singleWord = ""; //element z obiektu
        ArrayList<String> container = new ArrayList<>(this.listaSlow); //kopia
        ArrayList<String> anagram = new ArrayList<>(); //lista anagramow ktore tam dodamy
        ArrayList<ArrayList<String>> res = new ArrayList<>();
        while (!container.isEmpty()){ //dopoki w kopii mamy niesprawdzone elementy
            singleWord = container.get(0); //bierzemy 1 element
            container.remove(0); //usuwamy z kopii, poniewaz bedziemy go zaraz porownywac
            anagram.add(singleWord); //dodajemy go jako "wzorzec"
            for(int i = 0; i < container.size();i++){
                if(Anagram(singleWord, container.get(i))){
                    String ana = container.get(i);  //dodajemy do tablicy z pasujacym wzorccem
                    container.remove(i);
                    anagram.add(ana);
                    i--;
                }
            }
            res.add(anagram); //dodajemy gotowa liste anagramow
            anagram = new ArrayList<>(); //resetujemy liste dla nowej (Niech zyje GarbageCollector)

        }

        res.sort((f,s) -> s.size()-f.size()); //sortujemy tabele dlugosciowo
        return res;
    }

    public boolean Anagram(String fWord, String sWord){
        char[] fWordToChar = fWord.toCharArray();
        char[] sWordToChar = sWord.toCharArray();
        Arrays.sort(fWordToChar);
        Arrays.sort(sWordToChar); //konwertujemy oba na tablice czarow i sortujemy
        if(fWordToChar.length == sWordToChar.length){ //jesli sa tej samej dlugosci:
            for(int i = 0 ; i < fWordToChar.length; i++){ //sprawdzamy czy KAZDY char w fWord[] jest symetryczny do sWord[]
                if(fWordToChar[i] != sWordToChar[i]) return false;
            }
            return true;
        }
        return false; //tablica miala rozne dlugosci
    }

    public String getAnagramsFor(String an){
        ArrayList<String> res = new ArrayList<>();
        for(String element : listaSlow){
            if(Anagram(an,element) && !element.equals(an)){ //czy jest to anagram ORAZ sa to rozne slowa
                res.add(element);
            }
        }
        String finRes = an + ": " + res;
        return finRes;
    }
}  

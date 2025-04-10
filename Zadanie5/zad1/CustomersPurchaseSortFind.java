/**
 *
 *  @author Rawski Jakub S30532
 *
 */

package zad1;


import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class CustomersPurchaseSortFind {

    private ArrayList<Purchase> lista = new ArrayList<>();

    public CustomersPurchaseSortFind(){ //deklaracja nowej arrayListy jest w samym obiekcie
        super();
    }
    public void readFile(String path){
        try{
            Scanner scan = new Scanner(new File(path));
            for(String linia; scan.hasNext();){
                linia = scan.nextLine();
                lista.add(new Purchase(linia));
            }
        }catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }

    }

    public void showSortedBy(String enumerate){
        System.out.println(enumerate); //1 linijka, dla kazdego jest to samo
        switch (enumerate){
            case "Nazwiska" : byNazwisko(); break;
            case "Koszty" : byFullCena(); break;
            default: System.out.println("nie zaimplementowane"); break;
        }
        System.out.println(""); //spacja
    }

    public void byFullCena(){
        ArrayList<Purchase> res = new ArrayList<>(lista); //kopia
        res.sort(Comparator.comparing(Purchase::getFullPrice).reversed().thenComparing(Purchase::getidKlienta));
        for(Purchase element : res){
            System.out.println(element + " (koszt: "+ element.getFullPrice() + ")");
        }
    }
    public void byNazwisko(){
        ArrayList<Purchase> res = new ArrayList<>(lista); //kopia
        res.sort(Comparator.comparing(Purchase::getNazwisko).thenComparing(Purchase::getidKlienta));
        for(Purchase element : res){
            System.out.println(element);
        }
    }
    public void showPurchaseFor(String idCurrKlienta){
        System.out.println("Klient " + idCurrKlienta);
        for (Purchase element : lista){
            if(element.getidKlienta().equals(idCurrKlienta)){
                System.out.println(element);
            }
        }
        System.out.println(""); //spacja
    }
}

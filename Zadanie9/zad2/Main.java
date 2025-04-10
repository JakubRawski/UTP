/**
 *
 *  @author Rawski Jakub S30532
 *
 */

package zad2;


import zad1.State;
import zad1.StringTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

  public static void main(String[] args) {
    ExecutorService exeS = Executors.newFixedThreadPool(2);
    Magazyn mag = new Magazyn();
    String path = "../Towary.txt";
    exeS.execute(() -> {
      ArrayList<String> lista = new ArrayList<>();
      try {
        Scanner scan = new Scanner(new File(path));
        for(String linia; scan.hasNext();){
          linia = scan.nextLine();
          lista.add(linia);
        }
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      for(String linia : lista){
        String[] seperate = linia.split(" ");
        int id = Integer.parseInt(seperate[0]);
        double waga = Double.parseDouble(seperate[1]);
        mag.addWare(new Towar(id,waga));
        if(mag.getSize() % 200 == 0 && !mag.isEmpty()){
          System.out.println("utworzono " + mag.getSize() + " obiektów");
        }
      }
      mag.finish();

    });

    exeS.execute(() ->{
      int rozmiar = 0;
      double waga = 0;
      boolean start = true;
      while (!mag.getFin() || (rozmiar != mag.getSize() || start )){
        start = false;
        if(rozmiar != mag.getSize()){
          ArrayList<Towar> towars = mag.getFragmentOfZawartosc(rozmiar, mag.getSize());
          for(Towar ware : towars){
            waga += ware.getWagaTowaru();
            rozmiar+=1;
            if(rozmiar % 100 == 0 && rozmiar != 0){
              System.out.println("policzono wage " + rozmiar + " towarów");
            }
          }
        }
      }
      System.out.println(waga);
    });
    exeS.shutdown();


  }
}

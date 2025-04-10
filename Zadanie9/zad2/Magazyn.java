package zad2;

import java.util.ArrayList;

public class Magazyn {
    public ArrayList<Towar> zawartosc ;
    public boolean fin;

    public Magazyn(){
        this.fin = false;
        this.zawartosc = new ArrayList<>();
    }
    public synchronized boolean isEmpty(){
        return zawartosc.isEmpty();
    }
    public synchronized int getSize(){
        return zawartosc.size();
    }
    public synchronized void addWare(Towar ware){
        zawartosc.add(ware);
    }
    public synchronized boolean getFin(){
        return fin;
    }

    public synchronized  void finish(){
        fin = true;
    }
    public synchronized ArrayList<Towar> getZawartosc(){
        return zawartosc;
    }
    public synchronized ArrayList<Towar> getFragmentOfZawartosc(int start, int end){
        ArrayList<Towar> fragment = new ArrayList<>(zawartosc.subList(start,end));
        return fragment;
    }

}

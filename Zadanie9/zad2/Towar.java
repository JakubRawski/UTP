package zad2;

public class Towar {
    public int idTowaru;
    public double wagaTowaru;

    public Towar(int id, double waga){
        this.wagaTowaru = waga;
        this.idTowaru = id;
    }

    public int getIdTowaru() {
        return idTowaru;
    }

    public void setIdTowaru(int idTowaru) {
        this.idTowaru = idTowaru;
    }

    public double getWagaTowaru() {
        return wagaTowaru;
    }

    public void setWagaTowaru(double wagaTowaru) {
        this.wagaTowaru = wagaTowaru;
    }
}

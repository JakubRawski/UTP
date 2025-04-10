package zad1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class Database{
    private String url;
    private TravelData travelData;

    public Database(String url, TravelData travelData){
        this.url = url;
        this.travelData = travelData;
    }

    public void create(){
        try (Connection conn = DriverManager.getConnection(url)){
            try (Statement stmt = conn.createStatement()){
                stmt.execute("CREATE TABLE Offers (" +
                        "id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                        "Country VARCHAR(255), " +
                        "Departure DATE, " +
                        "ReturnDate DATE, " +
                        "Place VARCHAR(255), " +
                        "Price VARCHAR(255), " +
                        "Currency VARCHAR(10))");
            }catch (SQLException e){
                if(!e.getSQLState().equals("X0Y32")){//znalezione w necie
                    throw e;
                }
            }

            List<String> locales = Arrays.asList("pl_PL", "en_GB");
            for (String locale : locales){
                List<String> offers = travelData.getOffersDescriptionsList(locale, "yyyy-MM-dd");
                for (String offer : offers){
                    // //t sie wysypuje
                    String[] parts = offer.split(" ");
                    //PRZEPRASZAM

                    if(parts[0].equals("UNITED")){
                        List<String> tmp = new ArrayList<String>(Arrays.asList(parts));
                        tmp.remove(1);
                        tmp.set(0, "UNITED STATES");
                       parts = tmp.toArray(new String[parts.length]);
                    }


                    try (PreparedStatement pstmt = conn.prepareStatement(
                            "INSERT INTO Offers (Country, Departure, ReturnDate, Place, Price, Currency) VALUES (?, ?, ?, ?, ?, ?)")) {

                        pstmt.setString(1, parts[0]); //Country
                        pstmt.setDate(2, parseDate(parts[1])); //Departure
                        pstmt.setDate(3, parseDate(parts[2])); //ReturnDate
                        pstmt.setString(4, parts[3]); //Place
                        pstmt.setString(5, parts[4]); //Price
                        pstmt.setString(6, parts[5]); //Currency

                        pstmt.executeUpdate();
                    }
                }
            }
        }catch (SQLException e){
                throw new RuntimeException();
        }
    }
    private Date parseDate(String dateStr){
        try{
            return Date.valueOf(dateStr);//Wymaga formatu YYYY-MM-DD
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    public void showGui(){
        JFrame frame = new JFrame("Travel Offers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        String[] columns = {"Country", "Departure", "ReturnDate", "Place", "Price", "Currency"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        try (Connection conn = DriverManager.getConnection(url)){
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Offers");
            while (rs.next()){
                model.addRow(new Object[]{
                        rs.getString("Country"),
                        rs.getString("Departure"),
                        rs.getString("ReturnDate"),
                        rs.getString("Place"),
                        rs.getString("Price"),
                        rs.getString("Currency")
                });
            }
        }catch (SQLException e){
            throw new RuntimeException();
        }
        JTable table = new JTable(model);
        frame.add(new JScrollPane(table));
        frame.setVisible(true);
    }


}

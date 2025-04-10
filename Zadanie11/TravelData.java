package zad1;

import java.io.*;
import java.text.*;
import java.util.*;

public class TravelData{
    private File dataDir;

    public TravelData(File dataDir) {
        this.dataDir = dataDir;
    }

    public List<String> getOffersDescriptionsList(String loc, String dateFormat){
        List<String> descriptions = new ArrayList<>();
        Locale locale = Locale.forLanguageTag(loc.replace('_', '-'));
        DateFormat df = new SimpleDateFormat(dateFormat, locale);

        try{
            for (File file : Objects.requireNonNull(dataDir.listFiles())){
                try (BufferedReader reader = new BufferedReader(new FileReader(file))){
                    String line = "";
                    while ((line = reader.readLine()) != null){
                        String[] parts = line.split("\t");
                        String country = translateCountry(parts[1], locale);
                        String departure = formatDate(parts[2], "yyyy-MM-dd", df);
                        String returnDate = formatDate(parts[3], "yyyy-MM-dd", df);
                        String place = translatePlace(parts[4], locale);
                        String price = formatPrice(parts[5], locale);

                        descriptions.add(String.format("%s %s %s %s %s %s",
                                country, departure, returnDate, place, price, parts[6]));
                    }
                }
            }
        }catch (Exception e){
            throw new RuntimeException();
        }
        return descriptions;
    }
    private String formatDate(String date, String inputFormat, DateFormat outputFormat){
        try{
            DateFormat inputFormatter = new SimpleDateFormat(inputFormat);
            return outputFormat.format(inputFormatter.parse(date));
        }catch (ParseException e) {
            //jakbyn cos nie poszlo z konwersja
            return date;
        }
    }

    private String formatPrice(String price, Locale locale){
        try{
            NumberFormat format = NumberFormat.getNumberInstance(locale);
            double parsedPrice = NumberFormat.getNumberInstance(Locale.forLanguageTag(locale.toLanguageTag())).parse(price).doubleValue();
            return format.format(parsedPrice);
        }catch (ParseException e){
            //jakbyn cos nie poszlo z konwersja
            return price;
        }
    }
    private String translateCountry(String country, Locale locale){
        Locale sourceLocale = Locale.forLanguageTag(locale.toLanguageTag());
        return new Locale("", country).getDisplayCountry(sourceLocale);
    }

    private String translatePlace(String place, Locale locale){
        ResourceBundle bundle = ResourceBundle.getBundle("zad1.resources.places", locale);
        return bundle.getString(place.toLowerCase());
    }


}


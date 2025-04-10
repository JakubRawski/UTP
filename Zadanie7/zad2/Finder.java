/**
 *
 *  @author Rawski Jakub S30532
 *
 */

package zad2;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Finder {
    public ArrayList<String> ifContener = new ArrayList<>();
    public ArrayList<String> phraseContener = new ArrayList<>();
    public Finder(String src) throws IOException {

        Scanner scan = new Scanner(Paths.get(src)); //do odczytu pliku
        for(String linia; scan.hasNext();){
            linia = scan.nextLine();
            this.phraseContener.add(linia); //zapis linii
        }
        ifContener = whiteSpaceRemoval(phraseContener);
        ifContener = commentsRemoval(ifContener);
        phraseContener = ifContener;
        ifContener = avoidIfInStringStatement(ifContener);
    }


    public ArrayList<String> commentsRemoval(ArrayList<String> code){
        ArrayList<String> res = new ArrayList<>();
        int deep = 0;
        for (String linia : code){
            if(linia.length() < 2) continue;
            String start = linia.substring(0,2);
            switch (start){
                case "//": break;
                case "/*": deep++; break;
                case "*/": deep--; break;
                default: {
                    if(deep == 0){
                        res.add(linia);
                    }
                }
            }

        }
        return res;
    }

    public ArrayList<String> whiteSpaceRemoval(ArrayList<String> code){
        ArrayList<String> res = new ArrayList<>();
        String checkIfEmpty = "";
        for(String linia : code){
            checkIfEmpty = linia;
            if(!checkIfEmpty.trim().isEmpty()){
                res.add(checkIfEmpty.trim());
            }
        }
        return res;
    }


    public ArrayList<String> avoidIfInStringStatement(ArrayList<java.lang.String> code){
        ArrayList<String> res = new ArrayList<>();
        boolean ifInString = false;
        String checkIfInStatement = "";
        for(String linia : code){
            for(char c : linia.toCharArray()){
                if(c == 34) ifInString = !ifInString; //kod ASCII " to 34
                if(!ifInString) checkIfInStatement += c;
            }
            res.add(checkIfInStatement);
            checkIfInStatement = "";
        }
        return res;
    }
    public int getIfCount() {
        int counter = 0;
        Pattern p = Pattern.compile("if\\s*\\(.+?\\)");
        Matcher m;
        for (String linia : ifContener){
            m = p.matcher(linia);
            while (m.find()){
                linia = linia.substring(m.end());
                m = p.matcher(linia);
                counter++;
            }
        }
        return counter;
    }
    public int getStringCount(String phrase) {
        int counter = 0;
        Pattern p = Pattern.compile(phrase);
        Matcher m;
        for (String linia : phraseContener){
            m = p.matcher(linia);
            while (m.find()){
                linia = linia.replaceFirst(phrase,"");
                m = p.matcher(linia);
                counter++;
            }
        }
        return counter;
    }



}

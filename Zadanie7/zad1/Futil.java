/**
 *
 *  @author Rawski Jakub S30532
 *
 */
package zad1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;

public class Futil implements FileVisitor<Path> {
    private BufferedWriter bw; //zapisuje zawartosc kazdego pliku
    private String codeFIle = "cp1250"; //kodowanie pliku
    private String url; //sciezka lokalna
    public BufferedWriter getBw(){
        return this.bw;
    }
    public Futil(String src) throws IOException {
        url = "./"+ src;
        bw = Files.newBufferedWriter(Paths.get(url), StandardCharsets.UTF_8);
        //podpinamy bw do sciezki poczatkowej
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE; //nic nie robi przed wejsciem
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE; //nic nie robi gdy nie znajdzie pliku ktorego nie wiedzial
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE; //nic nie robi po wejsciem
    }
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Scanner scan = new Scanner(file,codeFIle); //do odczytu 1 pliku
        for(String linia; scan.hasNext();){
            linia = scan.nextLine();
            this.bw.write(linia + "\n"); //zapis
        }
        return FileVisitResult.CONTINUE;
    }
    public static void processDir(String dir, String res){
        try{
            Futil f = new Futil(res);  //tworzymy nasz obiekt
            Files.walkFileTree(Paths.get(dir),f); //przechodzimy do szukania po dzieciach i folderze pocatkowyn
            f.getBw().close(); //zamykanie lacza
        }catch (IOException ignored){
            throw  new RuntimeException();
        }
    }
}

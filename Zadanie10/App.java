package zad1;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.*;

public class App extends JFrame{
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private ExecutorService exeS;
    private List<Future<String>> tasks = new ArrayList<>();
    private JList<String> taskList =new JList<>();


    public App(){
        this.setTitle("Zadania");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        exeS = Executors.newFixedThreadPool(3);  //Do 3 zadań ZWIEKSZ JAK COS NIE GRA
        taskList = new JList<>(listModel);
        //elementy panelu
        JScrollPane scroll = new JScrollPane(taskList);
        JPanel Panel = new JPanel();
        Panel.setLayout(new FlowLayout());
        JButton startButton = new JButton("Nowe zadanie");
        JButton cancelButton = new JButton("Usun zadanie");
        JButton resultButton = new JButton("Check");

        //Uruchamiania zadania
        startButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                newTask();
            }
        });

        //Anulowania zadania
        cancelButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                cancelTask();
            }
        });

        //Sprawdzanie zadania
        resultButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                showTaskResult();
            }
        });
        //Dodawanie guzikow

        Panel.add(startButton);
        Panel.add(cancelButton);
        Panel.add(resultButton);

        //GUI
        this.setLayout(new BorderLayout());
        this.add(scroll, BorderLayout.CENTER);
        this.add(Panel, BorderLayout.SOUTH);
    }
    //Tworzenie nowego zadania
    private void newTask(){
        Callable<String> task = () -> {
            Thread.sleep(4000);  // Symulacja 4-sekundowego zadania
            return "Zadanie zakończone!";
        };
        Future<String> future = exeS.submit(task);
        tasks.add(future);
        listModel.addElement("Zadanie " + tasks.size());

    }
    //Usuwanie zadania
    private void cancelTask(){
        int selectedIndex = taskList.getSelectedIndex();
        //Daziala gdy element jest zazanaczony
        if(selectedIndex != -1){
            Future<String> future = tasks.get(selectedIndex);
            if(!future.isDone()){
                future.cancel(true);
                listModel.set(selectedIndex, "Zadanie  anulowane (stary numer: " + (selectedIndex+1) + ")");
            }
        }
    }
    //Sprawdzanie zadania
    private void showTaskResult(){
        int selectedIndex = taskList.getSelectedIndex();
        //Daziala gdy element jest zazanaczony
        if(selectedIndex != -1){
            Future<String> future = tasks.get(selectedIndex);
            if(future.isDone()){
                try {
                    String result = future.get();
                    JOptionPane.showMessageDialog(this, "Wynik zadania: " + result);
                    if(future.isDone()){
                        listModel.set(selectedIndex, "Zadanie " +(selectedIndex+1)+" ukonczone");
                    }
                }catch(InterruptedException | ExecutionException e){
                    JOptionPane.showMessageDialog(this, "Bład.");
                    //nigdy nie powinno sie poawic
                }
            }else{
                JOptionPane.showMessageDialog(this, "Zadanie w trakcie pracy.");
            }
        }
    }
}

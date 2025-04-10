/**
 *
 *  @author Rawski Jakub S30532
 *
 */

package zad1;


import javax.swing.*;

public class Main {

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable(){
      @Override
      public void run(){
        App application = new App();
        //by wyswietlilo
        application.setVisible(true);
      }
    });
  }
}

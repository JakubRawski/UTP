/**
 *
 *  @author Rawski Jakub S30532
 *
 */

package zad1;


public class Main {
  public static void main(String[] args) throws InterruptedException {
    StringTask task = new StringTask("A", 70000);
    System.out.println("Task " + task.getState());
    task.start();
    if (args.length > 0 && args[0].equals("abort")) {
    Thread thr = new Thread(() ->{
      try {
        Thread.sleep(1000); //1 sekunda
        task.abort();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    });

    thr.start();

    /*<- tu zapisać kod  przerywający działanie tasku po sekundzie 
         i uruchomić go w odrębnym wątku
    */
    }
    while (!task.isDone()) {
      Thread.sleep(500);
      switch(task.getState()) {
        case RUNNING: System.out.print("R."); break;
        case ABORTED: System.out.println(" ... aborted."); break;
        case READY: System.out.println(" ... ready."); break;
        default: System.out.println("unknown state");
      }

    }
    System.out.println("Task " + task.getState());
    System.out.println(task.getResult().length());
  }
}

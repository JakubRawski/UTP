package zad1;

public class StringTask implements Runnable{
    private String string;
    private int count;
    private volatile String res = "";
    private Thread thr;
    private volatile State state;

    public StringTask(String string, int count){
        this.string = string;
        this.count = count;
        this.state = State.CREATED;
        this.thr = new Thread(this);
    }
    public boolean isDone(){
        boolean r = (state == State.READY || state == State.ABORTED);
        return r;
    }
    public State getState(){
        return state;
    }
    public String getResult(){
        return res;
    }
    public void start() {
        thr.start();
    }

    public void abort(){
        thr.interrupt();
    }



    @Override
    public void run() {
        state = State.RUNNING;
        for(int i =0; i < count; i++){
            this.res = this.res + string;
            if(Thread.interrupted()){
                state = State.ABORTED;
                return;
            }

        }
        state= State.READY;
    }
}

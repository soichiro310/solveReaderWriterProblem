public class Semaphore {
    private int n;

    public Semaphore(int n){
        this.n = n;
    }
    
    public synchronized void Signal(){
        n++;
        notify();
    }
    
    public synchronized void Wait() throws InterruptedException{   
        //nが0の間，スレッドをブロック状態に移行させる
        while(n == 0){
            wait(); 
        }
        n--;
    }
}

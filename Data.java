class Data {
    private final char[] buffer;
    private Semaphore db = new Semaphore(1);
    private Semaphore mutex = new Semaphore(1);
    private int rc = 0;

    public Data(int size) {
        this.buffer = new char[size];
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = '*';
        }
    }
    public char[] read() throws InterruptedException {
        mutex.Wait();   //wait
        rc++;
        if(rc == 1)
            db.Wait();
        mutex.Signal();   //signal
        char[] getBuf = doRead();
        mutex.Wait();
        rc--;
        if(rc == 0)
            db.Signal();
        mutex.Signal();
        return getBuf;
    }
    public void write(char c) throws InterruptedException {
        db.Wait();
        doWrite(c);
        db.Signal();
    }

    private char[] doRead() {
        char[] newbuf = new char[buffer.length];
        for (int i = 0; i < buffer.length; i++) {
            newbuf[i] = buffer[i];
        }
        slowly();
        return newbuf;
    }
    private void doWrite(char c) {
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = c;
            slowly();
        }
    }
    private void slowly() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
    }
}

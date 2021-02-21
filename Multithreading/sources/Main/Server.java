package Main;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;


public class Server implements Runnable {


    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private volatile boolean running = false;
    private boolean runningThreads;
    private int ID;

    public void setRunningThreads(boolean runningThreads) {
        this.runningThreads = runningThreads;
    }

    /**
     *
     * @param ID ID of the server, used to identify the server when printing
     * @param numberOfClients the maximum number of clients that will pass through the queue during our simulation. Used
     *                         to initialize the BlockingQueue such that it will not overflow.
     */
    public Server(int ID, int numberOfClients) {
        this.waitingPeriod = new AtomicInteger(0);
        this.tasks = new LinkedBlockingQueue<Task>(numberOfClients);
        this.ID = ID;
    }

    /**
     * We add a new task in the queue, increment the waiting period by the processing time of that client and set the client's
     * waiting time.
     * @param newTask The task that is to be added tot the tail of our queue
     */
    public void addTask(Task newTask) {
        this.tasks.add(newTask);
        this.waitingPeriod.addAndGet(newTask.getProcessingTime());
        newTask.setWaitingTime(this.waitingPeriod.intValue());

    }


    private void pauseThread() {
        this.running = false;
    }


    private void resumeThread() {
        this.running = true;
    }

    /**
     * The thread performs the task of the queue whenever there are people in the Queue. When there are no clients to be
     * processed, the thread is paused. The thread becomes closed when runningThreads becomes false. This flag is set by
     * our main simulation Thread in the SimulationManager class.
     *
     */
    @Override
    public void run() throws NullPointerException{
        while (this.runningThreads) {
            if (!this.tasks.isEmpty()) {
                resumeThread();
            }
            while (running && runningThreads) {
                try {
                    int timeToProcess = this.tasks.peek().getProcessingTime();
                    Thread.sleep(1000);
                    if (timeToProcess == 1) {
                        this.tasks.poll();
                    } else {
                        this.tasks.peek().setProcessingTime(timeToProcess - 1);
                    }
                    this.waitingPeriod.addAndGet(-1);

                    if (this.tasks.isEmpty()) {
                        pauseThread();
                    }
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public BlockingQueue<Task> getTasks() {
        return tasks;
    }


    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }


    public int getID() {
        return ID;
    }


}

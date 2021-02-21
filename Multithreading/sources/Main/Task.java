package Main;

public class Task implements Comparable<Task>{


    private int ID;
    private int arrivalTime;
    private int processingTime;
    private int waitingTime;

    /**
     * The task class represents our client
     * @param ID the ID of the client
     * @param arrivalTime the arrival time of the client
     * @param processingTime the amount of time our client will spend at the head of our Queue
     */
    public Task( int ID,
                 int arrivalTime,
                 int processingTime) {

        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.processingTime = processingTime;
        this.waitingTime = 0;

    }


    public int getID() {
        return ID;
    }


    public int getArrivalTime() {
        return arrivalTime;
    }


    public int getProcessingTime() {
        return processingTime;
    }


    public int getWaitingTime() {
        return waitingTime;
    }


    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }


    public void setID(int ID) {
        this.ID = ID;
    }


    public void setProcessingTime(int processingTime) {
        this.processingTime = processingTime;
    }


    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }


    @Override
    public int compareTo(Task o) {
        return this.arrivalTime - o.arrivalTime;
    }


}

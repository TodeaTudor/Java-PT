package Main;

import Comparators.IDComparator;

import java.io.*;
import java.util.*;

import static java.lang.System.exit;


public class SimulationManager implements Runnable{

    public int timeLimit;
    public int minProcessingTime;
    public int maxProcessingTime;
    public int minArrivalTime;
    public int maxArrivalTime;
    public int numberOfServers;
    public int numberOfClients;
    private Scheduler scheduler;
    private List<Task> generatedTasks;
    private String outputFile;

    /**
     * We initialize the scheduler through this constructor and we call the method that generates the clients
     * @param inputArray
     * @param outputFile
     */
    public SimulationManager(int[] inputArray, String outputFile) {
        this.numberOfClients = inputArray[0];
        this.numberOfServers = inputArray[1];
        this.timeLimit = inputArray[2];
        this.minArrivalTime = inputArray[3];
        this.maxArrivalTime = inputArray[4];
        this.minProcessingTime = inputArray[5];
        this.maxProcessingTime = inputArray[6];
        this.outputFile = outputFile;

        generateNRandomTasks();

        this.scheduler = new Scheduler(this.numberOfServers, this.numberOfClients);
    }


    public void setGeneratedTasks(List<Task> generatedTasks) {
        this.generatedTasks = generatedTasks;
    }


    /**
     * We generate the random attributes for the clients with the constraints that have been read from the input file
     */
    private void generateNRandomTasks() {

        Random rand = new Random();
        List<Task> tasks = new ArrayList();
        for (int i = 0; i < this.numberOfClients; i++) {
            Task aux = new Task( i,
                       rand.nextInt(this.maxArrivalTime - this.minArrivalTime) + this.minArrivalTime,
                    rand.nextInt(this.maxProcessingTime - this.minProcessingTime) + this.minProcessingTime);
            tasks.add(aux);
        }

        Collections.sort(tasks);
        int i = 1;
        for (Task aux: tasks) {
            aux.setID(i++);
        }

        setGeneratedTasks(tasks);
    }

    /**
     * We print to the output file the state of the queue at each step, as it is required
     * @param servers
     * @param tasks
     * @param currentTime
     * @throws IOException
     */
    private void outputToFile(List<Server> servers, List<Task> tasks, int currentTime) throws IOException {
        FileWriter fw = new FileWriter(this.outputFile, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw);
        out.println("Time: " + currentTime);
        out.append("Waiting List: ");
        for (Task it: tasks) {
            out.write("(" + it.getID() + "," + it.getArrivalTime() + "," + it.getProcessingTime() + ") ");
        }
        out.append('\n');

        Collections.sort(this.scheduler.getServers(), new IDComparator());
        for (Server it: servers) {
            out.append("Queue " + it.getID() + ":");
            if (it.getTasks().isEmpty()) {
                out.append(" closed\n");
            }else {
                for (Task tIt: it.getTasks()) {
                    out.append("(" + tIt.getID() + "," + tIt.getArrivalTime() + "," + tIt.getProcessingTime() + ") ");
                }
                out.append("\n");
            }
        }
        out.append('\n');
        out.close();
    }

    /**
     * Our program runs until there are no more waiting clients and the queues are empty or we have reached the end of the
     * simulation time. This method checks the first condition.
     * @return
     */
    private boolean checkRunning() {
        boolean flag = false;
        for (Server it: this.scheduler.getServers()) {
            if(!it.getTasks().isEmpty()) {
                flag = true;
            }
        }
        if (!this.generatedTasks.isEmpty()) {
            flag = true;
        }
        return flag;
    }

    /**
     *
     * We take the waiting time of each client(after it has been processed) and compute the averate waiting time
     */
    private void printAverageWaitingTime(ArrayList<Task> tasks) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(this.outputFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            out.println("Average Waiting Time: ");
            int totalWaitingTime = 0;
            for (Task it: tasks) {
                totalWaitingTime += it.getWaitingTime();
            }
            out.println((float)totalWaitingTime/(float)this.numberOfClients);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The thread that maintains the simulation. From this thread we dispatch clients to queues with the help of the Scheduler
     * class. We print the state of the simulation at the time currentTime, put the Thread to sleep for one second and continue.
     * Once the running flag has been triggered, we close the Servers, print the average time and end the Thread, which in
     * turn ends our program.
     */
    @Override
    public void run() {
        int currentTime = 0;
        ArrayList<Task> removed = new ArrayList<>();
        boolean running = true;
        while (running) {
            try {
                ArrayList<Task> toRemove = new ArrayList<>();
                for (Task it: this.generatedTasks) {
                    if (it.getArrivalTime() == currentTime) {
                        this.scheduler.dispatchTask(it);
                        toRemove.add(it);
                        removed.add(it);
                    }
                }
                this.generatedTasks.removeAll(toRemove);
                outputToFile(this.scheduler.getServers(), this.generatedTasks, currentTime);
                currentTime++;
                Thread.sleep(1000);
                if ((currentTime == this.timeLimit) || !checkRunning()) {
                    running = false;
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }

        }
        this.scheduler.setRunningThreads(false);
        printAverageWaitingTime(removed);
    }


    /**
     * We read the information from the input file as presented in our requirements and start the SimulationManager thread
     * @param args The input and output file
     * @throws IOException
     */
    public static void main(String args[]) throws IOException {

        File in = new File(args[0]);
        Scanner inScanner = new Scanner(in);
        int[] inputArray = new int[7];
        int i = 0;

        while (inScanner.hasNextLine()) {
            String toSplit = inScanner.nextLine();
            for (String val: toSplit.split(",")) {
                inputArray[i++] = Integer.parseInt(val);
            }
        }

        // we make sure the output file(if it exists) is empty.
        PrintWriter pw = new PrintWriter(args[1]);
        pw.close();
        SimulationManager sm = new SimulationManager(inputArray, args[1]);
        Thread t = new Thread(sm);
        t.start();


    }
}

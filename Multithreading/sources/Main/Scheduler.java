package Main;

import Comparators.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class Scheduler {


    private List<Server> servers;
    private int maxNoServers;

    /**
     * We create maxNoServers threads that represent the Servers
     * @param maxNoServers The number of queues we will have
     * @param numberOfClients The number of clients that will pass through the Servers during our simulation
     */
    public Scheduler(int maxNoServers, int numberOfClients) {

        this.maxNoServers = maxNoServers;
        this.servers = new ArrayList<Server>();

        for (int i = 1; i <= this.maxNoServers; i++) {
            Server aux = new Server(i, numberOfClients);
            this.servers.add(aux);
            aux.setRunningThreads(true);
            Thread t = new Thread(aux);
            t.start();

        }
    }


    /**
     * We sort the queues by waiting time and dispatch a client to the server with the lowest waiting time
     * @param t The task that is added to the queue
     */
    public void dispatchTask(Task t) {
        Collections.sort(servers, new WaitingTimeComparator());
        this.servers.get(0).addTask(t);
    }


    public List<Server> getServers() {
        return servers;
    }

    /**
     * This method stops all the Server threads by triggering the runningThreads flag
     * @param runningThreads The flag that stops the Servers
     */
    public void setRunningThreads(boolean runningThreads) {
        for(Server it: this.servers) {
            it.setRunningThreads(runningThreads);
        }
    }
}

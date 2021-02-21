package Comparators;
import Main.Server;

import java.util.Comparator;

public class WaitingTimeComparator implements Comparator<Server> {
    @Override
    public int compare(Server o1, Server o2) {
        return o1.getWaitingPeriod().intValue() - o2.getWaitingPeriod().intValue();
    }
}

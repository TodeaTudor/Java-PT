package Comparators;
import Main.Server;

import java.util.Comparator;
public class IDComparator implements Comparator<Server> {
    @Override
    public int compare(Server o1, Server o2) {
        return o1.getID() - o2.getID();
    }
}

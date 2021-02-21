package Tasks;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Task6 {

    /**
     * Checks if 90% of the occurrences of an activity last less than 5 minutes.
     * @param shortActivitiesCount
     * @param activitiesCount
     * @param entry
     * @return
     */
    private boolean lessThan5Minutes(HashMap<String, Integer> shortActivitiesCount, HashMap<String, Integer> activitiesCount, String entry) {
        try {
            return shortActivitiesCount.get(entry) >= (int)(0.9 * activitiesCount.get(entry));
        }catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Prints the activities that last less than 5 minutes in more than 90% of the time
     * @param list
     * @throws IOException
     */
    private void printTask6(List<String> list) throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileWriter("Task_6.txt"));
        list.forEach(printWriter::println);
        printWriter.close();
    }

    /**
     * Computes the number of less than 5 minute occurrences of each activity and adds it to a list if 90% or more of those
     * occurrences are that short
     * @throws IOException
     */
    public Task6() throws IOException {

        HashMap<String, Integer> shortActivitiesCount = new HashMap<>();

        Task3.activityNames.forEach(activityName ->
                Task1.activities
                        .stream()
                        .filter(activity -> activity.getActivity_label().equals(activityName) && Task5.toSeconds.apply(activity) < 300)
                        .forEach(activity -> {
                            try {
                                shortActivitiesCount.put(activityName, shortActivitiesCount.get(activityName) + 1);
                            }catch (NullPointerException e) {
                                shortActivitiesCount.put(activityName, 1);
                            }
                        }));

        List<String> shortActivities = Task3.activityCount.keySet()
                .stream()
                .filter(entry -> lessThan5Minutes(shortActivitiesCount, Task3.activityCount, entry))
                .collect(Collectors.toList());

        printTask6(shortActivities);
    }
}

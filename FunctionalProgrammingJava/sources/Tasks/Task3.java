package Tasks;

import Tasks.Task1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Task3 {

    public static Set<String> activityNames = new HashSet<>();
    public static HashMap<String, Integer> activityCount = new HashMap<>();

    /**
     * Prints the name of the activity and the number of occurrences
     * @throws IOException
     */
    private void printTask3 () throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileWriter("Task_3.txt"));
        activityCount.keySet()
                .forEach(entry -> printWriter.println(entry + " " + activityCount.get(entry)));
        printWriter.close();
    }

    /**
     * Maps each activity to the number of occurrences
     * @throws IOException
     */
    public Task3() throws IOException {
        Task1.activities.forEach(activity -> activityNames.add(activity.getActivity_label()));
        activityNames.forEach(activityName ->
            Task1.activities
                    .stream()
                    .filter(activity -> activity.getActivity_label().equals(activityName))
                    .forEach(activity -> {
                        try {
                            activityCount.put(activityName, activityCount.get(activityName) + 1);
                        }catch (NullPointerException e) {
                            activityCount.put(activityName, 1);
                        }
                    }));

        printTask3();
    }
}

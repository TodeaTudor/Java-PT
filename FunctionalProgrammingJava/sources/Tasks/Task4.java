package Tasks;

import Model.MonitoredData;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Task4 {

    Predicate<MonitoredData> equalsDay(int day) {
        return (activity) -> activity.getStart_time().getDate() == day;
    }

    /**
     * Prints the tasks and the number of their daily occurrences throughout the time span of our data
     * @param dailyActivities
     * @throws IOException
     */
    private void printTask4(HashMap<Integer, HashMap<String, Integer>> dailyActivities) throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileWriter("Task_4.txt"));
        dailyActivities.keySet()
                .forEach(entry -> {
                    printWriter.println("Day: " +  entry + " " + "Month: " + Task2.date.get(entry) + '\n');
                    dailyActivities.get(entry).keySet()
                            .forEach(item -> printWriter.println(item + " " + dailyActivities.get(entry).get(item)));
                    printWriter.println();
                });
        printWriter.close();
    }

    /**
     * Counts the number of occurrences of each activity for every individual day
     * @throws IOException
     */
    public Task4() throws IOException {
        HashMap<String, Integer> dailyActivityCount = new HashMap<>();
        HashMap<Integer, HashMap<String, Integer>> dailyActivities = new HashMap<>();
        Task2.date
                .entrySet()
                .stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList())
                .forEach(day -> {
                    dailyActivityCount.clear();
                    Task1.activities
                            .stream()
                            .filter(equalsDay(day))
                            .forEach(activity -> {
                                try {
                                    dailyActivityCount.put(activity.getActivity_label(), dailyActivityCount.get(activity.getActivity_label()) + 1);
                                }catch (NullPointerException e) {
                                    dailyActivityCount.put(activity.getActivity_label(), 1);
                                }
                            });
                    HashMap<String, Integer> dailyActivityCopy = new HashMap<>(dailyActivityCount);
                    dailyActivities.put(day, dailyActivityCopy);
                });
        printTask4(dailyActivities);
    }
}

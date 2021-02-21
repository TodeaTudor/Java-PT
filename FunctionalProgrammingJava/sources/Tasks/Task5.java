package Tasks;

import Model.MonitoredData;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.function.Function;

public class Task5 {

    /**
     * Converts the duration of an activity to seconds
     */
    public static Function<MonitoredData, Integer> toSeconds = data -> {
        int startSeconds = data.getStart_time().getHours()*3600 + data.getStart_time().getMinutes()*60 + data.getStart_time().getSeconds();
        int endSeconds = data.getEnd_time().getHours()*3600 + data.getEnd_time().getMinutes()*60 + data.getEnd_time().getSeconds();
        int finalTime = endSeconds - startSeconds;

        if (finalTime < 0) {
            finalTime += 24 * 3600;
        }

        return finalTime;
    };


    Function<Integer, Integer> getHours = data -> data / 3600;
    Function<Integer, Integer> getMinutes = data -> (data%3600) / 60;
    Function<Integer, Integer> getSeconds = data -> (data%3600) % 60;

    /**
     * Prints each activity and the total duration
     * @param activitiesDuration
     * @throws IOException
     */
    private void printTask5(HashMap<String, Integer> activitiesDuration) throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileWriter("Task_5.txt"));
        activitiesDuration.keySet()
                .forEach(entry ->  {
                    printWriter.println(entry + ": " + getHours.apply(activitiesDuration.get(entry)) + " Hours " +
                                        getMinutes.apply(activitiesDuration.get(entry)) + " Minutes " +
                                        getSeconds.apply(activitiesDuration.get(entry)) + " Seconds");
                });
        printWriter.close();
    }

    /**
     * Computes the duration of each activity throughout the time span of the provided data
     * @throws IOException
     */
    public Task5() throws IOException {
        HashMap<String, Integer> activitiesDuration = new HashMap<>();

        Task3.activityNames.forEach(activityName ->
                Task1.activities
                        .stream()
                        .filter(activity -> activity.getActivity_label().equals(activityName))
                        .forEach(activity -> {
                            try {
                                activitiesDuration.put(activityName, activitiesDuration.get(activity.getActivity_label()) + toSeconds.apply(activity));
                            }catch (NullPointerException e) {
                                activitiesDuration.put(activityName, toSeconds.apply(activity));
                            }
                        }));

        printTask5(activitiesDuration);
    }
}

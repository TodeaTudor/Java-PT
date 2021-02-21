package Tasks;

import Model.MonitoredData;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Task1 {

    public static List<MonitoredData> activities = new ArrayList<>();

    /**
     * Prints the activities as MonitoredData objects for the first task
     * @throws IOException
     */
    private void printTask1() throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileWriter("Task_1.txt"));
        activities.forEach(activity -> printWriter.println(activity.toString()));
        printWriter.close();
    }

    /**
     * Parses the activities from the input an created MonitoredData objects
     * @throws IOException
     */
    public Task1() throws IOException {
        Stream<String> fileStream =  Files.lines(Paths.get("Activities.txt"));
        List<String> list = fileStream.collect(Collectors.toList());

        list.forEach(item -> {
            try {
                activities.add(new MonitoredData(item));
            } catch (ParseException e) {
                System.out.println("Invalid date");
            }
        });

        printTask1();

    }

}

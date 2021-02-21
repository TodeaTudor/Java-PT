package Tasks;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class Task2 {

    public static Map<Integer, Integer> date = new HashMap<>();

    /**
     * Prints the number of distinct days, as required for the second task
     * @throws IOException
     */
    private void printTask2() throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileWriter("Task_2.txt"));
        printWriter.println("Number of distinct days is: " + date.size());
        printWriter.close();
    }

    /**
     * Computes the number of distinct days in the file
     * @throws IOException
     */
    public Task2() throws IOException {
         date = Task1.activities
                .stream()
                .collect(Collectors.toMap(k -> k.getStart_time().getDate(), v -> v.getStart_time().getMonth(), (oldV, newV) -> oldV));

        printTask2();
    }
}

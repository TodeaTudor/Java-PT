package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The only Model class of the project
 */
public class MonitoredData {
    private Date start_time;
    private Date end_time;
    private String activity_label;

    /**
     * We parse each line of the input and create the respective MonitoredData object
     * @param data A single line taken from the input
     * @throws ParseException
     */
    public MonitoredData(String data) throws ParseException {
        String[] tokens = data.split("\t\t");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        this.activity_label = tokens[2].replace("\t", "");
        try {
            this.start_time = format.parse(tokens[0]);
            this.end_time = format.parse(tokens[1]);
        }catch(ParseException e) {
            System.out.println("Invalid date");
        }
    }

    public Date getStart_time() {
        return start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public String getActivity_label() {
        return activity_label;
    }

    @Override
    public String toString() {
        return "start_time = " + start_time +
                ", end_time = " + end_time +
                ", activity_label = " + activity_label;
    }
}

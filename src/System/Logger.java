package System;
import AssociationAssets.Event;
import com.sun.xml.internal.bind.v2.TODO;
import javafx.util.Pair;

import java.io.*;
import java.sql.Struct;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * All system operations are kept in this class for tracking and error tracking
 * Singleton
 * @ Written by Yuval Ben Eliezer
 */
public class Logger {

    List<String> actionLog = new ArrayList<>();
    List<String> errorLog = new ArrayList<>();
    List<Pair<Integer,List<Event>>> report = new ArrayList<>();
    private static Logger single_instance = null;

    public static Logger getInstance()
    {
        if (single_instance == null)
            single_instance = new Logger();

        return single_instance;
    }
    private Logger()
    {

    }

    /**
     *  Through the Log main referee can generate reports on games
     * @param gid - game id
     * @param events - List of events that happened in a game
     * @param pathToSave
     */
    public void exportReport(int gid, List<Event> events, String pathToSave) {
        if(events!= null){
            this.report.add(new Pair<>(gid,events));
            File toSave = new File(pathToSave);
            try {
                PrintWriter writer = new PrintWriter(toSave);
                StringBuilder sb = new StringBuilder();
                sb.append("id,");
                sb.append(',');
                sb.append("Event Type, ");
                sb.append(',');
                sb.append("Description");
                sb.append('\n');
                for (Event event:events) {
                    sb.append("1");
                    sb.append(',');
                    sb.append(event.getEventType().name()+ ", ");
                    sb.append(',');
                    sb.append(event.getDescription() + ", ");
                    sb.append(',');
                    sb.append('\n');
                }
                writer.write(sb.toString());
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
        }
/*
    public void givenDataArray_whenConvertToCSV_thenOutputCreated(String gid) throws IOException {
        File csvOutputFile = new File("" + gid);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            report.get(Integer.parseInt(gid).).stream().map(this::convertToCSV).forEach(pw::println);
        }
        assertTrue(csvOutputFile.exists());
    }

    public String convertToCSV(String[] data) {
        return Stream.of(data).map(this::escapeSpecialCharacters).collect(Collectors.joining(","));
    }

    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

 */
    /**
     * Using this function you can add actions that occurred in the system to the logger
     * @param action - The action we want to add
     */
    public void addActionToLogger(String action){
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        getActionLog().add(date + " " + time + ": " + action);
    }

    /**
     * Using this function you can add errors that occurred in the system to the error logger
     * @param action - The action we want to add
     */
    public void addErrorToLogger(String action){
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        getErrorActionLog().add(date + " " + time + ": " + action);
    }

    private Collection<String> getErrorActionLog() {
        return errorLog;
    }

    public List<String> getActionLog() {
        return actionLog;
    }

    public List<String> getErrorLog() {
        return errorLog;
    }

    public void WriteActionLoggerToFile(File filepath) {
        try {

            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(actionLog);
            objectOut.close();
            fileOut.close();
            System.out.println("The Action Logger was successfully written to a file");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void WriteErrorLoggerToFile(File filepath) {
        try {

            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(errorLog);
            objectOut.close();
            fileOut.close();
            System.out.println("The Error Logger was successfully written to a file");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

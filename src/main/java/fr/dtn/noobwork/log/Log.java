package fr.dtn.noobwork.log;

import fr.dtn.noobwork.io.DoubleOutputStream;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;

public class Log {
    private static boolean init = false;

    private static File directory;
    private static final PrintStream console = System.out;
    private static final PrintStream trace = System.err;

    public static void setDirectory(File directory) {
        Log.directory = directory;
        init = true;
        Log.info("Directory changed to : '" + directory.getPath() + "'");
    }

    public static void setDirectory(String path) { Log.setDirectory(new File(path)); }

    static String getPrefix() {
        LocalDateTime time = LocalDateTime.now();

        String thread = Thread.currentThread().getName();
        String caller = new Exception().getStackTrace()[3].getClassName();
        String method = Thread.currentThread().getStackTrace()[4].getMethodName();

        return "[" + time.getHour() + ":" +
                time.getMinute() + ":" +
                time.getSecond() + "][" +
                thread + "][" +
                caller + "#" +
                method + "]: ";
    }

    static File getFile() throws IOException{
        if(directory == null || (!directory.exists() && !directory.mkdir()))
            throw new IOException("Logs directory does not exists and can't be made");

        LocalDateTime time = LocalDateTime.now();
        String path = time.getYear() + "." + time.getMonthValue() + "." + time.getDayOfMonth();
        return new File(directory, path + ".log");
    }

    static void setPrint() {
        try{
            System.setOut(new PrintStream(new DoubleOutputStream(getFile(), console)));
            System.setErr(new PrintStream(new DoubleOutputStream(getFile(), trace)));
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    static void print(String type, PrintStream stream, Object o) {
        if(!init) {
            throw new RuntimeException("No log directory has been chosen, JLL is not able to log");
        }

        setPrint();
        stream.println('[' + type + ']' + getPrefix() + o);
    }

    public static void info(Object o) { print("?", System.out, o); }
    public static void warn(Object o) { print("#", System.out, o); }
    public static void error(Object o) { print("!", System.err, o); }
}
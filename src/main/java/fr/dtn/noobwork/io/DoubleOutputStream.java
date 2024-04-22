package fr.dtn.noobwork.io;

import java.io.*;

public class DoubleOutputStream extends OutputStream {
    private final File file;
    private final PrintStream print;

    public DoubleOutputStream(File file, PrintStream print){
        this.file = file;
        this.print = print;
    }

    @Override public void write(int b) throws IOException {
        FileWriter write = new FileWriter(file, true);
        BufferedWriter writer = new BufferedWriter(write);

        print.write(b);
        writer.write(b);

        writer.close();
        write.close();
    }
}
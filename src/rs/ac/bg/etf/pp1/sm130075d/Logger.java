package rs.ac.bg.etf.pp1.sm130075d;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by avax94 on 8.2.17..
 */
public class Logger {
    PrintStream os = null;
    public Logger(boolean toFile, String name) {
        try {
            if(toFile) {
                Path p = Paths.get(name + ".log");

                if (Files.exists(p)) {
                    name = name + System.currentTimeMillis();
                }

                name = name + ".log";
                os = new PrintStream(new BufferedOutputStream(new FileOutputStream(name)));
            } else {
                os = System.out;

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void log(String s) {
        os.println(s);
        os.flush();
    }

    public void error(String s) {
        System.err.println(s);
        os.flush();
    }
}

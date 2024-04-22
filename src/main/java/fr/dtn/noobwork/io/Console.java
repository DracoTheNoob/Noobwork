package fr.dtn.noobwork.io;

import java.io.File;

public class Console {
    public static String clickablePath(String path) {
        return "file:///" + path
                .replace('\\', '/')
                .replace(" ", "%20");
    }

    public static String clickablePath(File file) {
        return clickablePath(file.getPath());
    }
}
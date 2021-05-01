package com.minesec.service.core;

import java.io.*;

public class IOUtils {

    public static void writeFile(String data, String file) throws IOException {
        File f = new File(file);
        OutputStream out = null;
        out = new FileOutputStream(f);
        out.write(data.getBytes());
        out.flush();
        out.close();
    }

    public static String readFile(String path) throws IOException {

        FileInputStream in=new FileInputStream(path);

        int size=in.available();

        byte[] buffer=new byte[size];

        in.read(buffer);

        in.close();

        return new String(buffer);
    }
}

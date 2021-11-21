package com.luxoft.webapp.server;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ResponseWriter {
    public String writeSuccessRepsonse(String content, BufferedWriter writer) {
        try {
            writer.write("HTTP/1.1 200 OK");
            writer.newLine();
            writer.newLine();

            File file = new File(content);
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] buf = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buf)) > 0) {
                String s = new String(buf, StandardCharsets.UTF_8);
                writer.write(s);
            }
        }catch (IOException ex){
            return "500";
        }
        return "200";
    }
}

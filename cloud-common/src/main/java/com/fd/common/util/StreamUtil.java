package com.fd.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * 字节流
 * 字符流
 */
@Slf4j
public class StreamUtil {

    static int bufferSize = 8192;

    public static String stream(InputStream in,String encoding) throws IOException {
        Reader reader = (encoding == null) ? new InputStreamReader(in) : new InputStreamReader(in,
                encoding);
        StringWriter out = new StringWriter();
        char[] buffer = new char[bufferSize];
        int amount;

        while ((amount = reader.read(buffer)) >= 0) {
            out.write(buffer, 0, amount);
        }
        return out.toString();
    }

    public static String stream(byte[] bytes,String encoding) throws IOException {
        return stream(new ByteArrayInputStream(bytes),encoding);
    }

    public static void main(String[] args) throws IOException {
        String str = "哈哈qaz";
    }

}

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

    public static byte[] streamBytes(InputStream in,boolean end) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[bufferSize];
        try{
            int amount;
            while ((amount = in.read(buffer)) >= 0) {
                bos.write(buffer, 0, amount);
            }
            return bos.toByteArray();
        }catch (Exception e){
            log.error("streamBytes exception{}",e);
        } finally {
            try {
                if(end){
                  in.close();
                }
            } catch (IOException e) {
            }
        }
        throw new RuntimeException("streamBytes exception");
    }

    public static void main(String[] args) throws IOException {
        String str = "哈哈qaz";
    }

}

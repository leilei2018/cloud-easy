package com.fd.common.util.encry;

import com.fd.common.util.StreamUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64Util {
    static final String encoding = "UTF-8";
    static final Base64.Encoder encoder = Base64.getEncoder();
    static final Base64.Decoder decoder = Base64.getDecoder();

    public static String encode(byte[] bytes){
        String encodedText = encoder.encodeToString(bytes);
        return encodedText;
    }

    public static String encode(String text){
        final byte[] textByte;
        try {
            textByte = text.getBytes(encoding);
            return encode(textByte);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String decodeString(String text){
        try {
            byte[] decode = decodeBytes(text);
            return new String(decode, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    public static byte[] decodeBytes(String text){
        final byte[] textByte;
        try {
            textByte = text.getBytes(encoding);
            byte[] decode = decoder.decode(textByte);
            return decode;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws IOException {
        String src = "qads";
        String encode = Base64Util.encode(src);
        System.out.println(encode);
        byte[] bytes = Base64Util.decodeBytes(encode);
        System.out.println(StreamUtil.stream(bytes,encoding));
    }
}

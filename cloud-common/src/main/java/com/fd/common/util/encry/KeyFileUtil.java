package com.fd.common.util.encry;


import com.fd.common.enums.KeyType;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.security.Key;

@Slf4j
public class KeyFileUtil {

    static final String RSA_ALGORITHM = "RSA";
    static final String OS_NAME = "Linux";
    static final String LinuxPATH = "/usr/local/fpay";
    static final String WindowPATH = "g:/fpay/";
    static final String path = OS_NAME.equals(System.getProperty("os.name"))?LinuxPATH:WindowPATH;
    /**
     * 初始化，生成一对密钥对，然后写入到文件
     */
    public static void initWrite(){
        RSAKeyFactory.KeyMap keyPairs = RSAKeyFactory.createKeyPairs(1024);
        writeKey2File(keyPairs.getPrivateKey(),KeyType.privateKey);
        writeKey2File(keyPairs.getPublicKey(),KeyType.publicKey);
        log.info(">>>>>>>>>>>>>>生成一对密钥对，然后写入到文件success<<<<<<<<<<<<<<<");
    }

    public static void main(String[] args) {
        initWrite();
    }

    public static void writeKey2File(Key key, KeyType keyType){
        keyType.check(key);
        byte[] encoded = key.getEncoded();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(path,keyType.getFileName()));
            fos.write(encoded);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static byte[] readKeyDataFromFile(KeyType keyType,String keyPath){
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(keyPath,keyType.getFileName()));
            byte[] encoded = new byte[1024];
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int amount;
            while ((amount = fis.read(encoded)) >= 0) {
                bos.write(encoded,0,amount);
            }
            byte[] bytes = bos.toByteArray();
            return bytes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if ( fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}

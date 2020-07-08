package com.fd.common.util.encry;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 *#新建一个jks库（也就是.keystore）
 * keytool -genkeypair -alias yushan -keypass yushan -keyalg RSA -keysize 1024 -validity 1
 *  -keystore g:/fpay/keystore/yushan.jks -storepass 123456 -dname "CN=hepan, OU=wljs, O=zfb, L=gd, ST=sz, C=sz"
 *
 * # 导出证书cert  -rfc 以可读的方式导出公钥证书
 * keytool -exportcert -alias yushan -keystore g:/fpay/keystore/yushan.jks  -storepass 123456 -file g:/fpay/keystore/c1.cer
 *
 *
 * #将生成的jks,转换为行业标准格式 PKCS12（输出文件，继续以jks,或者以pfx都可以）要保证密码保持一致，才能转换成功
 * keytool -importkeystore
 * -srckeystore g:/fpay/keystore/yushan.jks  -srcalias yushan-srcstorepass 123456  -srckeypass yushan
 * -destkeystore g:/fpay/keystore/yushan_pfx.pfx -deststoretype pkcs12 -deststorepass 123456 -destkeypass yushan
 *
 *
 * #注意，有提示
 * PKCS12 密钥库不支持其他存储和密钥口令。正在忽略用户指定的-destkeypass值，所以keypass被忽略，就等于storepass
 */
public class KeyToolUtil implements Serializable {

    //keytool -genkeypair  -keyalg "RSA"  -validity 1 -keystore e:/yushan.keystore
    public static Certificate readCer(String certPath){
        try {
            InputStream is = new FileInputStream(certPath);
            CertificateFactory cf = CertificateFactory.getInstance("x509");
            Certificate certificate = cf.generateCertificate(is);
            return certificate;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("read cert error");
    }

    public static void main(String[] args) throws KeyStoreException, FileNotFoundException {
        //jks  => java key store,就是java利用keytool生成的.keystore文件（包含密钥对，或者受信任的证书，即公钥）
        KeyStore ks = KeyStore.getInstance("PKCS12");
        //KeyStore ks = KeyStore.getInstance("PKCS12");
        char[] storepass = "123456".toCharArray(); //对应 -storepass
        char[] keypass = "123456".toCharArray(); //对应 -keypass  默认不输入，就等于storepass
        FileInputStream fis = new FileInputStream("g:/fpay/keystore/yushan_pfx.pfx");
        try {
            ks.load(fis, storepass);
            String alias = "yushan";
            KeyStore.ProtectionParameter protParam = new KeyStore.PasswordProtection(keypass);

            //同样，这样也可以直接获取到私钥key
            RSAPrivateKey pk = (RSAPrivateKey) (ks.getKey(alias, storepass));

            //从keystore的 -genkeypair 别名中，获取私钥key
            KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry)ks.getEntry(alias, protParam);
            RSAPrivateKey privateKey = (RSAPrivateKey)pkEntry.getPrivateKey();

            Certificate cert = ks.getCertificate(alias);
            RSAPublicKey publicKey = (RSAPublicKey)cert.getPublicKey();

            Certificate certificate = readCer("g:/fpay/keystore/c1.cer");
            RSAPublicKey publicKey1 = (RSAPublicKey)cert.getPublicKey();
            String data = "aagg";
            String s = RSAUtil.privateEncrypt(data, pk);
            String s2 = RSAUtil.publicDecrypt(s, publicKey1);
            System.out.println(s2);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            e.printStackTrace();
        }

    }
}

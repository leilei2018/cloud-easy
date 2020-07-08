package com.fd.common.util.encry;

import com.fd.common.enums.KeyType;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Signature 类介绍
 * java 1.6api介绍:
 *
 * 此类用来为应用程序提供数字签名算法功能,数字签名用于确保数字数据的验证和完整性
 */
public class SignatureUtil {
    /**
     * Signature的用法
     * 数字签名
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static String sign(String str, PrivateKey key) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException{
//初始化 MD5withRSA
        Signature signature = Signature.getInstance("MD5withRSA");
//使用私钥
        signature.initSign(key);
//需要签名或校验的数据
        signature.update(str.getBytes());
        return Base64Util.encode(signature.sign());//进行数字签名
    }
    /**
     * 数字校验，验签失败或者成功
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static boolean verifyMethod(String str, String sign, PublicKey key) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(key);
        signature.update(str.getBytes());
        return signature.verify(Base64Util.decodeBytes(sign));
    }


    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        RSAPrivateKey privateKey = RSAKeyFactory.getPrivateKeyByFile(KeyType.privateKey, KeyFileUtil.path);
        RSAPublicKey publicKey = RSAKeyFactory.getPublicKeyByFile(KeyType.publicKey, KeyFileUtil.path);
        String str = "a=1&b=2&c=3";

        String sign = sign(str, privateKey);
        System.out.println(sign);
        String sign2 = sign("a=1&b=2&c=4", privateKey);

        //服务方
        String reqStr = "a=1&b=2&c=3";
        boolean b = verifyMethod(reqStr, sign, publicKey);
        System.out.println(b);
    }
}

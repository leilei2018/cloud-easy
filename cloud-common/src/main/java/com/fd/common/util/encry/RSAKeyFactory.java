package com.fd.common.util.encry;


import com.fd.common.enums.KeyType;
import lombok.extern.slf4j.Slf4j;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
@Slf4j
public class RSAKeyFactory {

    static final String RSA_ALGORITHM = "RSA";
    static final String PUBLIC_KEY = "publicKey";
    static final String PRIVATE_KEY = "privateKey";

    static class KeyMap extends HashMap<String,Key>{
        public Key putPublicKey(Key value) {
            return super.put(PUBLIC_KEY, value);
        }
        public Key putPrivateKey(Key value) {
            return super.put(PRIVATE_KEY, value);
        }
        public Key getPublicKey() {
            return super.get(PUBLIC_KEY);
        }
        public Key getPrivateKey() {
            return super.get(PRIVATE_KEY);
        }
    }

    public static KeyMap createKeyPairs(int keySize){
        //为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg;
        try{
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        }catch(NoSuchAlgorithmException e){
            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
        }

        //初始化KeyPairGenerator对象,密钥长度
        kpg.initialize(keySize);
        //生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        //得到公钥
        Key publicKey = keyPair.getPublic();
        //得到私钥
        Key privateKey = keyPair.getPrivate();
        KeyMap keyPairMap = new KeyMap();
        keyPairMap.putPrivateKey(privateKey);
        keyPairMap.putPublicKey(publicKey);
        return keyPairMap;
    }

    /**
     * 关于秘钥长度，建议1024位以上
     * https://blog.csdn.net/zhaoem82/article/details/102477824
     * @param keySize
     * @return
     */
    public static Map<String, String> createKeys(int keySize){
        //为RSA算法创建一个KeyPairGenerator对象
        KeyMap keyPairs = createKeyPairs(keySize);
        String publicKeyStr =  Base64Util.encode(keyPairs.getPublicKey().getEncoded());
        //得到私钥
        String privateKeyStr =  Base64Util.encode(keyPairs.getPrivateKey().getEncoded());
        Map<String, String> keyPairMap = new HashMap<String, String>();
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKey", privateKeyStr);

        return keyPairMap;
    }

    public static void main(String[] args) {
        Map<String, String> keys = createKeys(1024);
        System.out.println(keys);
    }

    /**
     * 得到私钥
     * @param privateKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKeyByString(String privateKey)  {
        //通过PKCS#8编码的Key指令获得私钥对象
        byte[] bytes = Base64Util.decodeBytes(privateKey);
        return getPrivateKey(bytes);
    }

    /**
     * 根据原始的privateKey（byte[]）获取私钥对象
     * @param privateKeyData
     * @return
     */
    public static RSAPrivateKey getPrivateKey(byte[] privateKeyData)  {
        //通过PKCS#8编码的Key指令获得私钥对象
        try {
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyData);
            KeyFactory keyFactory  = KeyFactory.getInstance(RSA_ALGORITHM);
            RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
            return key;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static RSAPrivateKey getPrivateKeyByFile( KeyType keyType,String keyPath)  {
        //通过PKCS#8编码的Key指令获得私钥对象
        byte[] bytes = KeyFileUtil.readKeyDataFromFile(keyType, keyPath);
        RSAPrivateKey privateKey = getPrivateKey(bytes);
        keyType.check(privateKey);
        return privateKey;
    }

    /**
     * 得到公钥
     * @param publicKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String publicKey)  {
        //通过X509编码的Key指令获得公钥对象
        byte[] bytes = Base64Util.decodeBytes(publicKey);
        return getPublicKey(bytes);
    }

    public static RSAPublicKey getPublicKey(byte[] publicKeyData) {
        //通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = null;
        try {
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyData);
            keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
            return key;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static RSAPublicKey getPublicKeyByFile( KeyType keyType,String keyPath)  {
        //通过PKCS#8编码的Key指令获得私钥对象
        byte[] bytes = KeyFileUtil.readKeyDataFromFile(keyType, keyPath);
        RSAPublicKey publicKey = getPublicKey(bytes);
        keyType.check(publicKey);
        return publicKey;
    }
}

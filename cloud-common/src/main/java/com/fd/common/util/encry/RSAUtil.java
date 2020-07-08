package com.fd.common.util.encry;




import com.fd.common.enums.KeyType;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

/**
 * RSA非对称加密，由一个公钥和私钥组成，并且唯一
 * 消息a=1&b=2&c=3
 * 先将请求body，转换为MD5信息摘要byte[],然后根据私钥加密，成sign,发送到B方。
 *
 * 1：如果有抓包工具，伪造sign,或者修改，更新sign, 公钥解密就会报错的。SignatureException
 *     （如果签名是伪造的【黑客利用自己的秘钥加密的，这样伪造的就会报错】，则解密会出现错误，因为公私钥是唯一的一对）
 * 2：解密之后的摘要，会和重新生成的参数摘要比较，如果不相等，则代表数据篡改
 */
public class RSAUtil {
    static final String RSA_ALGORITHM = "RSA";
    public static final String CHARSET = "UTF-8";

    public static String privateEncrypt(String data, KeyType keyType,String keyPath){
        byte[] bytes = KeyFileUtil.readKeyDataFromFile(keyType, keyPath);
        RSAPrivateKey privateKey = RSAKeyFactory.getPrivateKey(bytes);
        keyType.check(privateKey);
        return privateEncrypt(data,privateKey);
    }
    public static String privateEncryptWithByte(byte[] data, KeyType keyType,String keyPath){
        byte[] bytes = KeyFileUtil.readKeyDataFromFile(keyType, keyPath);
        RSAPrivateKey privateKey = RSAKeyFactory.getPrivateKey(bytes);
        keyType.check(privateKey);
        return privateEncryptWithByte(data,privateKey);
    }


    public static String getSignature( String data,KeyType keyType,String keyPath){
        Signature sign;
        String res = "";
        try {
            byte[] bytes = KeyFileUtil.readKeyDataFromFile(keyType, keyPath);
            RSAPrivateKey privateKey = RSAKeyFactory.getPrivateKey(bytes);
            sign = Signature.getInstance("NONEwithRSA");
            sign.initSign(privateKey);
            sign.update(data.getBytes());
            byte[] signSequ = sign.sign();
            res = Base64Util.encode(signSequ);
        }catch(NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SignatureException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }


    public static String publicDecrypt(String data, KeyType keyType,String keyPath){
        byte[] bytes = KeyFileUtil.readKeyDataFromFile(keyType, keyPath);
        RSAPublicKey publicKey = RSAKeyFactory.getPublicKey(bytes);
        keyType.check(publicKey);
        return publicDecrypt(data,publicKey);
    }

    public static void main(String[] args) {
        String path = KeyFileUtil.path;
        String password = "123456";
        String s = privateEncrypt(password, KeyType.privateKey, path);
        String sother = privateEncrypt("qwer", KeyType.privateKey, path);


        System.out.println(s);
        byte[] bytes = MD5Util.md5(password);
        System.out.println(privateEncryptWithByte(bytes, KeyType.privateKey, path));


        System.out.println(getSignature(password,KeyType.privateKey, path));

        String des = publicDecrypt(sother, KeyType.publicKey, path);
        System.out.println("deencry="+des);
    }

    /**
     * 私钥加密
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateEncrypt(String data, RSAPrivateKey privateKey){
        try{
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            byte[] bytes = rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), privateKey.getModulus().bitLength());
            return Base64Util.encode(bytes);
        }catch(Exception e){
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }
    public static String privateEncryptWithByte(byte[]  data, RSAPrivateKey privateKey){
        try{
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] bytes = rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data, privateKey.getModulus().bitLength());
            return Base64Util.encode(bytes);
        }catch(Exception e){
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }


    /**
     * 公钥解密
     * @param data
     * @param publicKey
     * @return
     */

    public static String publicDecrypt(String data, RSAPublicKey publicKey){
        try{
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64Util.decodeBytes(data), publicKey.getModulus().bitLength())
                    , CHARSET);
        }catch(Exception e){
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize){
        if(true){
            try {
                return  cipher.doFinal(datas);
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            }
        }

        int maxBlock = 0;
        if(opmode == Cipher.DECRYPT_MODE){
            maxBlock = keySize / 8;
        }else{
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream
                out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try{
            while(datas.length > offSet){
                if(datas.length-offSet > maxBlock){
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                }else{
                    buff = cipher.doFinal(datas, offSet, datas.length-offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        }catch(Exception e){
            throw new RuntimeException("加解密阀值为["+maxBlock+"]的数据时发生异常", e);
        }
        byte[] resultDatas = out.toByteArray();
        return resultDatas;
    }

}

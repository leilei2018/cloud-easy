package com.fd.common.util;

import com.fd.common.util.encry.MD5Util;
import com.ulisesbocchio.jasyptspringboot.encryptor.DefaultLazyEncryptor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.core.env.StandardEnvironment;

public class JasyptUtil {
    public static void main(String[] args) {
        String encode = MD5Util.md5Encrypt32Upper(RandomUtils.nextBytes(8));
        System.out.println(encode);

        StandardEnvironment env = new StandardEnvironment();
        env.getSystemProperties().put("jasypt.encryptor.password",encode);
        DefaultLazyEncryptor p = new DefaultLazyEncryptor(env);
        String encrypt = p.encrypt("123456");
        System.out.println(encrypt);
    }
}

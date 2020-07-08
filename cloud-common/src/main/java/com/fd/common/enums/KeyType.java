package com.fd.common.enums;

import com.fd.common.constant.RSAConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;

@AllArgsConstructor
@Getter
public enum KeyType {
    publicKey(RSAConstants.publicKeyFile){
        @Override
        public void check(Key key) {
            if (!(key instanceof PublicKey)){
                throw new RuntimeException("当前key不是一个publicKey");
            }
        }
    }, privateKey(RSAConstants.privateKeyFile){
        @Override
        public void check(Key key) {
            if (!(key instanceof PrivateKey)){
                throw new RuntimeException("当前key不是一个privateKey");
            }
        }
    };
    String fileName;

    public abstract void check(Key key);
}

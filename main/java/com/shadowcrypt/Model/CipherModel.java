package com.shadowcrypt.Model;

import static com.shadowcrypt.EnumUtility.TYPE.ENCRYPTION;

import com.shadowcrypt.Model.Factory.CipherFactory;
import com.shadowcrypt.EnumUtility.TYPE;
import com.shadowcrypt.EnumUtility.CIPHER_TYPE;

// @DP Builder Pattern
public class CipherModel {
    private final Cipher cipher;
    public CipherModel(Builder builder){
        cipher = builder.build_cipher;
    }

    public String encrypt(String msg, Object key){
        return cipher.begun_process(ENCRYPTION, msg, key);
    }

    public String decrypt(String msg, Object key){
        return cipher.begun_process(TYPE.DECRYPTION, msg, key);
    }

    public static class Builder{
        private Cipher build_cipher;
        public Builder setCipherType(CIPHER_TYPE cipherType){
            build_cipher = CipherFactory.getCipher(cipherType);
            return this;
        }
        public CipherModel build(){
            return new CipherModel(this);
        }
    }
}

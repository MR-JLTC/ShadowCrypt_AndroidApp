package com.shadowcrypt.Model.Factory;

import com.shadowcrypt.EnumUtility.CIPHER_TYPE;
import com.shadowcrypt.Model.Cipher;
import com.shadowcrypt.Model.Formula_Based_Impl.ShiftCipher;
import com.shadowcrypt.Model.Formula_Based_Impl.ShiftCipher_ASCII;
import com.shadowcrypt.Model.Formula_Based_Impl.VernamCipher_ASCII;
import com.shadowcrypt.Model.Formula_Based_Impl.VigenereCipher;
import com.shadowcrypt.Model.Formula_Based_Impl.VigenereCipher_ASCII;

public class CipherFactory {
    public static Cipher getCipher(CIPHER_TYPE type){
        return switch(type){
            case SHIFT_CIPHER -> new ShiftCipher();
            case VIGENERE_CIPHER -> new VigenereCipher();
            case SHIFT_CIPHER_ASCII -> new ShiftCipher_ASCII();
            case VIGENERE_CIPHER_ASCII -> new VigenereCipher_ASCII();
            case VERNAM_CIPHER -> new VernamCipher_ASCII();
        };
    }
}

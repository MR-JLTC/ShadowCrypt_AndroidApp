package com.shadowcrypt.Model.Formula_Based_Impl;

import com.shadowcrypt.Model.Cipher;
import com.shadowcrypt.EnumUtility.TYPE;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 *
 * @author MR_JLTC
 */
public class VernamCipher_ASCII implements Cipher{
    private final ArrayList<Integer> shift_keys;
    private final int based = 95;

    public VernamCipher_ASCII(){shift_keys=new ArrayList<>();}

    
    @Override
    public String begun_process(TYPE process, String msg, Object key) {
        shift_keys.clear();
        initKey(key);
        return switch(process){
            case ENCRYPTION -> this.encrypt(msg);
            case DECRYPTION -> this.decrypt(msg);
        };
    }
    
    private String encrypt(String msg){
        return XORProcessor(msg);
    }
    
    private String decrypt(String enc_msg){
        return XORProcessor(enc_msg);
    }
    
    private String XORProcessor(String msg){
        char[] letters = msg.toCharArray();
        if(isKeyLengthGreater(letters.length)) modifyKeys();
        for(int index=0; index<letters.length; index++){
            letters[index] = toChar(toDecimal(letters[index]) ^ (shift_keys.get(0)));
            shift_keys.add(shift_keys.remove(0));
        }
        return charArrayConcatenator(letters);
    }
    
    private int toDecimal(char c){
        return (int ) c;
    }
    
    private char toChar(int rs){
        return (char) rs;
    }
    
    private String charArrayConcatenator(char[] chars){
        StringBuilder msg= new StringBuilder();
        for(char c: chars){
            msg.append(c);
        }
        sensitiveDataWiper(chars);
        return msg.toString();
    }
    
    private void initKey(Object key){
        if(key instanceof String key_s){
            for(char k : key_s.toCharArray()){
                shift_keys.add(toDecimal(k) % 26);
            }
        }else throw new InputMismatchException("Key argument is invalid");
    }
    
    private int lengthGap=0;
    private int textLength=0;
    private boolean isKeyLengthGreater(int textlength){
        int keylength=0;
        keylength = shift_keys.size();
        textLength = textlength;
        lengthGap = keylength-textLength;
        textlength=0;//to refresh the value
        return keylength>textLength;
    }
    
    private void modifyKeys(){
        int sumOfExtraKeys=0;
        int iteration=0;
        for(int reverseIndex=shift_keys.size()-1; reverseIndex>=0; reverseIndex--){
            iteration++;
            sumOfExtraKeys+=shift_keys.get(reverseIndex);
            shift_keys.remove(reverseIndex);
            if(iteration==lengthGap) break;
        }
        int additionalKey = sumOfExtraKeys/textLength;
        for(int index=0; index<shift_keys.size(); index++){
            shift_keys.set(index, (shift_keys.get(index) + additionalKey) % based);
        }
        lengthGap=0;
    }
    
    //PREVENTS MEMORY LEAK
    private void sensitiveDataWiper(char[] ref_ar){
        Arrays.fill(ref_ar, '\0');// clear sensitive data and reference, to give hint to the Garbage_Collector
    }
}

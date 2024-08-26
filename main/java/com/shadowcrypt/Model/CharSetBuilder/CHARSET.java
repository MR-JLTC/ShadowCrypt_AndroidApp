package com.shadowcrypt.Model.CharSetBuilder;

import java.util.ArrayList;

/**
 *
 * @author MR_JLTC
 * @SingletonType
 */
public enum CHARSET {
    INSTANCE;
    
    public final ArrayList<Character> getASCII_CHARS(){
        return ASCII_CHAR_GENERATOR();
    }
    
    private ArrayList<Character> ASCII_CHAR_GENERATOR(){
        final ArrayList<Character> charset = new ArrayList<>();
        for(int i=32;i<127;i++) charset.add((char) i);
        return charset;
    }
    
    public final char[] getALPHABETS(){
        return ALPHABET_CHAR_GENERATOR();
    }
    
    private char[] ALPHABET_CHAR_GENERATOR(){
        return new char[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z'
        };
    }
}

package com.shadowcrypt.Model;


import com.shadowcrypt.EnumUtility.TYPE;

/**
 *
 * @author MR.JLTC
 */
@FunctionalInterface
public interface Cipher {
    String begun_process(TYPE process, String msg, Object key);
}

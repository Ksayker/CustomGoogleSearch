package ksayker.customgooglesearch.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author ksayker
 * @version 0.0.1
 * @since 25.02.17
 */
public class UtilHasher {
    public static String md5(String s){
        String result = null;
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("md5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuilder hex = new StringBuilder();
            for (int i = 0; i < messageDigest.length; i++) {
                hex.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
            result = hex.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }
}

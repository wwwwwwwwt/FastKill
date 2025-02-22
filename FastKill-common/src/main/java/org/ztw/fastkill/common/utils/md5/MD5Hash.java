package org.ztw.fastkill.common.utils.md5;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Hash {

    public static String md5Java(String message) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes("UTF-8"));

            //converting byte array to Hexadecimal String
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }

            digest = sb.toString();

        } catch (UnsupportedEncodingException ex) {
            //Logger.getLogger(StringReplace.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            //Logger.getLogger(StringReplace.class.getName()).log(Level.SEVERE, null, ex);
        }
        return digest;
    }
}

package edu.nuist.qdb.similarcheckor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class String2MD5 {
   
    public static String MD5(String sourceStr, boolean is16) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
            
            if(is16) return result.substring(8, 24);
            return result;
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
            return "";
        }
    }
}
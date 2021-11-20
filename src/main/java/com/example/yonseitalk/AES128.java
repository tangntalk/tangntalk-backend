package com.example.yonseitalk;

import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class AES128 {

    public static String getAES128_Encode(String data) {
        try {
            String secretKey = "0123456789abcdef"; //비밀키 선언 16바이트

            byte ivBytes[] = new byte[16]; //AES128비트 암호화에서 16바이트는 변할 수 없다
            Arrays.fill(ivBytes, (byte)0x00); //배열에 초기값 0으로 삽입 실시

            byte textBytes[] = data.getBytes("UTF-8");

            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            SecretKeySpec newKey = new SecretKeySpec(secretKey.getBytes("UTF-8"),"AES");
            Cipher cipher = null;
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);

            Encoder encoder = Base64.getEncoder(); //base64로 다시 포맷해서 인코딩 실시 (경우에 따라 아파치 base64 필요)
            return encoder.encodeToString(cipher.doFinal(textBytes));
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    public static String getAES128_Decode(String data) {
        try {
            String secretKey = "0123456789abcdef"; //비밀키 선언 16바이트

            byte ivBytes[] = new byte[16]; //AES128비트 암호화에서 16바이트는 변할 수 없다
            Arrays.fill(ivBytes, (byte)0x00); //배열에 초기값 0으로 삽입 실시

            Decoder decoder = Base64.getDecoder(); //base64로 다시 포맷해서 디코딩 실시 (경우에 따라 아파치 base64 필요)
            byte textBytes[] = decoder.decode(data);

            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            SecretKeySpec newKey = new SecretKeySpec(secretKey.getBytes("UTF-8"),"AES");
            Cipher cipher = null;
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);

            return new String(cipher.doFinal(textBytes),"UTF-8");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
}

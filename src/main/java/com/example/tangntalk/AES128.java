package com.example.tangntalk;

import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;


public class AES128 implements AttributeConverter<String, String> {

    private static Cipher cipher;
    private static SecretKeySpec key;

    public AES128() throws Exception{
        String secretKey = "0123456789abcdef";
        cipher = cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        key = new SecretKeySpec(secretKey.getBytes("UTF-8"),"AES");

    }

    @Override
    public String convertToDatabaseColumn(String s){
        try {
            byte ivBytes[] = new byte[16]; //AES128비트 암호화에서 16바이트는 변할 수 없다
            Arrays.fill(ivBytes, (byte)0x00); //배열에 초기값 0으로 삽입 실시
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(ivBytes));
            return Base64.getEncoder().encodeToString(cipher.doFinal(s.getBytes("UTF-8")));
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @Override
    public String convertToEntityAttribute(String s) {
        try {
            byte ivBytes[] = new byte[16]; //AES128비트 암호화에서 16바이트는 변할 수 없다
            Arrays.fill(ivBytes, (byte)0x00); //배열에 초기값 0으로 삽입 실시
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivBytes));
            return new String(cipher.doFinal(Base64.getDecoder().decode(s)),"UTF-8");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
}

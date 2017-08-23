import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

/**
 * 加密解密工具类
 * 
 */

public class EncryptUtil {


    /**
     * base64 加密
     * @return
     * @return String
     * @throws UnsupportedEncodingException
     */
    public static String encodeBase64(String str) {
        return new String(Base64.encodeBase64(str.getBytes()));
    }

    /**
     * base64 解密
     * @return
     * @return String
     * @throws UnsupportedEncodingException
     */
    public static String decodeBase64(String str) {
        return new String(Base64.decodeBase64(str));
    }

    public static String encodeSMS4(String plaintext, byte[] key) {
        if (plaintext == null || plaintext.equals("")) {
            return null;
        }
        for (int i = plaintext.getBytes().length % 16; i < 16; i++) {
            plaintext += '\0';
        }

        return new String(
                Base64.encodeBase64(encodeSMS4(plaintext.getBytes(), key)));
    }

    /**
     * 不限明文长度的SMS4加密
     * 
     * @param plaintext
     * @param key
     * @return
     */
    private static byte[] encodeSMS4(byte[] plaintext, byte[] key) {
        byte[] ciphertext = new byte[plaintext.length];

        int k = 0;
        int plainLen = plaintext.length;
        while (k + 16 <= plainLen) {
            byte[] cellPlain = new byte[16];
            for (int i = 0; i < 16; i++) {
                cellPlain[i] = plaintext[k + i];
            }
            byte[] cellCipher = encode16(cellPlain, key);
            for (int i = 0; i < cellCipher.length; i++) {
                ciphertext[k + i] = cellCipher[i];
            }

            k += 16;
        }

        return ciphertext;
    }
    
    public static String encodeSms4ForString(String value,String key){
    	   if (value == null || value.equals("")) {
               return null;
           }
           for (int i = value.getBytes().length % 16; i < 16; i++) {
        	   value += '\0';
           }
           byte [] ss = encodeSMS4(value.getBytes(),key.getBytes());
           StringBuffer sb = new StringBuffer();
           for(int i=0;i<ss.length;i++){
        	   sb.append(ss[i]);
        	   sb.append(",");
           }
           String result = sb.toString();
    	return result.substring(0, result.length()-1);
    }

    /**
     * 不限明文长度的SMS4解密
     * 
     * @param ciphertext
     * @param key
     * @return
     */
    public static byte[] decodeSMS4(byte[] ciphertext, byte[] key) {
        byte[] plaintext = new byte[ciphertext.length];

        int k = 0;
        int cipherLen = ciphertext.length;
        while (k + 16 <= cipherLen) {
            byte[] cellCipher = new byte[16];
            for (int i = 0; i < 16; i++) {
                cellCipher[i] = ciphertext[k + i];
            }
            byte[] cellPlain = decode16(cellCipher, key);
            for (int i = 0; i < cellPlain.length; i++) {
                plaintext[k + i] = cellPlain[i];
            }

            k += 16;
        }

        return plaintext;
    }

    /**
     * 解密，获得明文字符串
     * @param ciphertext
     * @param key
     * @return
     */
    public static String decodeSMS4toString(String ciphertext, byte[] key) {
        byte[] encoded = Base64.decodeBase64(ciphertext);
        byte[] plaintext = new byte[encoded.length];
        plaintext = decodeSMS4(encoded, key);
        return new String(plaintext);
    }

    /**
     * 解密，获得明文字符串
     * @param ciphertext
     * @param key
     * @return
     */
    public static String decodeSMS4toString(byte[] ciphertext, byte[] key) {
        byte[] plaintext = new byte[ciphertext.length];
        plaintext = decodeSMS4(ciphertext, key);
        return new String(plaintext);
    }

    private static final int ENCRYPT = 1;
    private static final int DECRYPT = 0;

    /**
     * 只加密16位明文
     * 
     * @param plaintext
     * @param key
     * @return
     */
    private static byte[] encode16(byte[] plaintext, byte[] key) {
        byte[] cipher = new byte[16];
        SMS4 sm4 = new SMS4();
        sm4.sms4(plaintext, 16, key, cipher, ENCRYPT);

        return cipher;
    }

    /**
     * 只解密16位密文
     * 
     * @param plaintext
     * @param key
     * @return
     */
    private static byte[] decode16(byte[] ciphertext, byte[] key) {
        byte[] plain = new byte[16];
        SMS4 sm4 = new SMS4();
        sm4.sms4(ciphertext, 16, key, plain, DECRYPT);

        return plain;
    }
}

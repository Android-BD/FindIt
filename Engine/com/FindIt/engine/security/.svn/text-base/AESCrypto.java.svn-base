/*************Source********
 * - There are 3 types of known (to us) encryption algorithms available 
 *   in Android:
 *   o RSA (Needs public key for encryption and private key for decryption)
 *   o AES (Simplest in implementation; needs only one key)
 *   o MD5 Hash (Very Difficult to decrypt encrypted data)
 *   
 * - AES encryption algorithm is used because it is stronger than RSA  and it is 
 *   widely used in the industry
 *   
 * - As there is only one correct way of doing encryption,
 *   majority of the code was taken from following available snippet in online community:
 *   http://www.androidsnippets.org/snippets/39/
 */

package com.FindIt.engine.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESCrypto 
{
	private final static String KEY_SEED = "FindIt496";
	
	public static String encrypt(String textToEncrypt) throws Exception 
	{  
        byte[] rawKey = getRawKey(KEY_SEED.getBytes());  
        byte[] result = encrypt(rawKey, textToEncrypt.getBytes());  
        return Base64.encodeBytes(result);
    } 
	
	public static String decrypt(String encryptedText) throws Exception 
	{  
        byte[] rawKey = getRawKey(KEY_SEED.getBytes());  
        byte[] enc = Base64.decode(encryptedText);  
        byte[] result = decrypt(rawKey, enc);  
        return new String(result);  
    }  
	
	private static byte[] getRawKey(byte[] seed) throws NoSuchAlgorithmException 
	{  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");  
        sr.setSeed(seed);  
        kgen.init(128, sr);
        SecretKey skey = kgen.generateKey();  
        byte[] raw = skey.getEncoded();  
        return raw; 
	}
	
	private static byte[] encrypt(byte[] rawKey, byte[] clearText) throws Exception 
	{  
        SecretKeySpec skeySpec = new SecretKeySpec(rawKey, "AES");  
        Cipher cipher = Cipher.getInstance("AES");  
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);  
        byte[] encrypted = cipher.doFinal(clearText);  
        return encrypted;  
    }
	
	private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {  
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
        Cipher cipher = Cipher.getInstance("AES");  
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);  
        byte[] decrypted = cipher.doFinal(encrypted);  
        return decrypted;  
    }  
}

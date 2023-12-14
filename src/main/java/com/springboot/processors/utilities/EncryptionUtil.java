package com.springboot.processors.utilities;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EncryptionUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(EncryptionUtil.class);
	
	public static KeyPair generateRSAKeyPair() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    public static byte[] encryptRSA(String plainText, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(plainText.getBytes());
    }

    public static String decryptRSA(byte[] cipherText, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(cipherText);
        return new String(decryptedBytes);
    }

    public static byte[] generateAESKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256); // You can adjust the key size based on your needs
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    public static byte[] encryptAES(String plainText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return cipher.doFinal(plainText.getBytes());
    }

    public static String decryptAES(byte[] cipherText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedBytes = cipher.doFinal(cipherText);
        return new String(decryptedBytes);
    }

    
    public static void main(String[] args) {
        try {
            // Generate RSA Key Pair
            KeyPair rsaKeyPair = generateRSAKeyPair();
            PublicKey rsaPublicKey = rsaKeyPair.getPublic();
            PrivateKey rsaPrivateKey = rsaKeyPair.getPrivate();

            // Generate AES Key
            byte[] aesKey = generateAESKey();

            // Example: Encrypting and Decrypting a message using RSA and AES
            String originalMessage = "Hello, Hybrid Encryption!";
            byte[] encryptedWithRSA = encryptRSA(originalMessage, rsaPublicKey);
            byte[] encryptedWithAES = encryptAES(originalMessage, new SecretKeySpec(aesKey, "AES"));

            // Transmit the encrypted message and AES key to the other party...

            // Decrypting at the other party using RSA and AES
            String decryptedWithRSA = decryptRSA(encryptedWithRSA, rsaPrivateKey);
            String decryptedWithAES = decryptAES(encryptedWithAES, new SecretKeySpec(aesKey, "AES"));

            System.out.println("Original Message: " + originalMessage);
            System.out.println("Encrypted with RSA: " + new String(encryptedWithRSA));
            System.out.println("Encrypted with AES: " + new String(encryptedWithAES));
            System.out.println("Decrypted with RSA: " + decryptedWithRSA);
            System.out.println("Decrypted with AES: " + decryptedWithAES);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

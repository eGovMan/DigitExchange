package org.digit.exchange.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.*;
import java.util.Base64;

public class CryptoUtil {

    private static final String ASYMMETRIC_ALGO = "RSA";
    private static final String SYMMETRIC_ALGO = "AES";
    private static final String SIGNATURE_ALGO = "SHA256withRSA";

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ASYMMETRIC_ALGO);
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    public static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(SYMMETRIC_ALGO);
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    public static String sign(String data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_ALGO);
        signature.initSign(privateKey);
        signature.update(data.getBytes());
        return Base64.getEncoder().encodeToString(signature.sign());
    }

    public static boolean verify(String data, String signature, PublicKey publicKey) throws Exception {
        Signature sig = Signature.getInstance(SIGNATURE_ALGO);
        sig.initVerify(publicKey);
        sig.update(data.getBytes());
        return sig.verify(Base64.getDecoder().decode(signature));
    }

    public static String encrypt(String data, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(SYMMETRIC_ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String encryptedData, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(SYMMETRIC_ALGO);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(original);
    }
}


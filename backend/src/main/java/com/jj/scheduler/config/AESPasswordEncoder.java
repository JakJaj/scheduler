package com.jj.scheduler.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class AESPasswordEncoder implements PasswordEncoder {
    @Value("${aes.secret.key}")
    private String secretKey;

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String KEY_ALGORITHM = "AES";

    @Override
    public String encode(CharSequence rawPassword) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);

            byte[] iv = new byte[16];
            new SecureRandom().nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] encrypted = cipher.doFinal(rawPassword.toString().getBytes());

            byte[] encryptedWithIv = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, encryptedWithIv, 0, iv.length);
            System.arraycopy(encrypted, 0, encryptedWithIv, iv.length, encrypted.length);

            return Base64.getEncoder().encodeToString(encryptedWithIv);
        } catch (Exception e) {
            throw new RuntimeException("Error encoding password", e);
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        try {
            String decrypted = decrypt(encodedPassword);
            return rawPassword.toString().equals(decrypted);
        } catch (Exception e) {
            return false;
        }
    }

    public String decrypt(String encryptedPassword) throws Exception {
        byte[] encryptedWithIv = Base64.getDecoder().decode(encryptedPassword);

        byte[] iv = new byte[16];
        byte[] encrypted = new byte[encryptedWithIv.length - 16];
        System.arraycopy(encryptedWithIv, 0, iv, 0, 16);
        System.arraycopy(encryptedWithIv, 16, encrypted, 0, encrypted.length);

        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] decrypted = cipher.doFinal(encrypted);

        return new String(decrypted);
    }
}


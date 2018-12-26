package com.evil.cbs.service.impl;

import com.evil.cbs.service.BlowfishCryptoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

@Service
public class BlowfishCryptoServiceImpl implements BlowfishCryptoService {

    @Value("${blowfish.secret.key}")
    private String encryptionKey;

    @Override
    public String encrypt(String dataToEncrypt) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(encryptionKey.getBytes(), "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] bytes = cipher.doFinal(dataToEncrypt.getBytes());
            String encrypted = DatatypeConverter.printHexBinary(bytes);
            return encrypted;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public String decrypt(String dataToDecrypt) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(encryptionKey.getBytes(), "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] bytes = DatatypeConverter.parseHexBinary(dataToDecrypt);
            return new String(cipher.doFinal(bytes));
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}

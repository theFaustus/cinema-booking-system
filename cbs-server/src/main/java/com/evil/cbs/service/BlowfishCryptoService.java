package com.evil.cbs.service;

public interface BlowfishCryptoService {
    String encrypt(String dataToEncrypt);
    String decrypt(String dataToDecrypt);
}
package io;

public interface IEncrypter {
    String encrypt(String content);
    String decrypt(String encrypted);
}

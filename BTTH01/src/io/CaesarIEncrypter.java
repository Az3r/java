package io;

/**
 * Convert a String by shifting each character by a specific value, this encryter is used for English alphabet only
 */
public class CaesarIEncrypter implements IEncrypter {
    private int offset = 1;

    public CaesarIEncrypter(int shift) {
        this.offset = shift;
    }

    @Override
    public String encrypt(String content) {
        StringBuilder builder = new StringBuilder(content.length());
        for (int i = 0; i < content.length(); i++) {
            builder.append(encrypt(content.charAt(i), offset));
        }
        delegate
        return builder.toString();
    }

    @Override
    public String decrypt(String encrypted) {
        StringBuilder builder = new StringBuilder(encrypted.length());
        for (int i = 0; i < encrypted.length(); i++) {
            builder.append(decrypt(encrypted.charAt(i), offset));
        }
        return builder.toString();
    }

    private static int encrypt(int ch, int offset) {
        int encrypted = (toSchemeValue(ch) + offset) % 26;
        return toASCII(encrypted);
    }

    private static int decrypt(int ch, int offset) {
        int decrypted = encrypt(ch, 26 - offset);
        return toASCII(decrypted);
    }

    protected static int toSchemeValue(int asciiValue) {
        return Character.toUpperCase(asciiValue) - 'A';
    }

    protected static int toASCII(int schemeValue) {
        return Character.toUpperCase(schemeValue) + 'A';
    }
}

package io;

public class VigenereEncrypter implements IEncrypter {

    private String keyword;

    public VigenereEncrypter(String keyword) {
        if (keyword == null) throw new NullPointerException("keyword");
        this.keyword = keyword;
    }

    @Override
    public String encrypt(String content) {
        StringBuilder builder = new StringBuilder(content.length());
        for (int i = 0; i < content.length(); i++) {
            builder.append(encrypt(content.charAt(i), keyword.charAt(i)));
        }
        return builder.toString();
    }

    @Override
    public String decrypt(String encrypted) {
        StringBuilder builder = new StringBuilder(encrypted.length());
        for (int i = 0; i < encrypted.length(); i++) {
            builder.append(decrypt(keyword.charAt(i), encrypted.charAt(i)));
        }
        return builder.toString();
    }

    private static int encrypt(int letter, int key) {
        int schemeValueRow = toSchemeValue(letter);
        int schemeValueCol = toSchemeValue(key);


        int encrypted = (schemeValueRow + schemeValueCol) % 26;
        return toASCII(encrypted);
    }

    private static int decrypt(int key, int cipher) {
        int schemeValueKey = toSchemeValue(key);
        int schemeValueCipher = toSchemeValue(cipher);

        if (schemeValueCipher < schemeValueKey) schemeValueCipher += toSchemeValue('Z');
        int decrypted = (schemeValueCipher - schemeValueKey) % 26;
        return toASCII(decrypted);
    }

    protected static int toSchemeValue(int asciiValue) {
        return Character.toUpperCase(asciiValue) - 'A';
    }

    protected static int toASCII(int schemeValue) {
        return Character.toUpperCase(schemeValue) + 'A';
    }
}

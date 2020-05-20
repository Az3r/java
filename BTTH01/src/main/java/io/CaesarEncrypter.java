package io;

/**
 * Convert a String by shifting each character by a specific value, this encryter is used for English alphabet only
 */
public class CaesarEncrypter implements ICeasarEncrypter {
    private int shifter = 1;

    public CaesarEncrypter(int shift) {
        this.shifter = shift;
    }

    @Override
    public String encrypt(String content) {
        if (content == null) return null;
        StringBuilder builder = new StringBuilder(content.length());
        for (int i = 0; i < content.length(); i++) {
            int encrypted = encrypt(content.charAt(i), shifter);
            builder.append(Character.toChars(encrypted));
        }
        return builder.toString();
    }

    @Override
    public String decrypt(String encrypted) {
        if (encrypted == null) return null;
        StringBuilder builder = new StringBuilder(encrypted.length());
        for (int i = 0; i < encrypted.length(); i++) {
            int decrypted = decrypt(encrypted.charAt(i), shifter);
            builder.append(Character.toChars(decrypted));
        }
        return builder.toString();
    }

    @Override
    public int getShifter() {
        return shifter;
    }


    /**
     * encrypt a character using Caesar cipher
     *
     * @param codepoint the character to be encrypted
     * @param shifter   shift value used in Caesar cipher
     * @return encrypted codepoint if it is an ASCII letter [A-Za-z], else return original codepoint
     */
    private static int encrypt(int codepoint, int shifter) {
        if (!isAsciiLetter(codepoint)) return codepoint;
        boolean isLowerCase = Character.isLowerCase(codepoint);


        // convert codepoint to caeser value and encrypt it
        int caeserValue = toCaeserValue(codepoint);
        int encryptedCaeser = 26 + (caeserValue + shifter) % 26;

        // convert back to its ascii value
        int encryptedASCII = toASCIIValue(encryptedCaeser);

        // lower-case if needed
        return isLowerCase ? Character.toLowerCase(encryptedASCII) : encryptedASCII;
    }

    /**
     * decrypt a character using Caesar cipher
     *
     * @param codepoint the character to be decrypted
     * @param shifter   shift value used in Caesar cipher
     * @return decrypted codepoint if it is an ASCII letter [A-Za-z], else return original codepoint
     */

    private static int decrypt(int codepoint, int shifter) {

        if (!isAsciiLetter(codepoint)) return codepoint;
        boolean isLowerCase = Character.isLowerCase(codepoint);

        // convert codepoint to caeser value and encrypt it
        int caeserValue = toCaeserValue(codepoint);
        int decryptedCaeser = 26 + (caeserValue - shifter) % 26;

        int decryptedASCII = toASCIIValue(decryptedCaeser);
        return isLowerCase ? Character.toLowerCase(decryptedASCII) : decryptedASCII;
    }


    private static boolean isAsciiLetter(int codepoint) {
        return (codepoint >= 'A' && codepoint <= 'Z') || (codepoint >= 'a' && codepoint <= 'z');
    }

    /**
     * convert an upper-case ASCII letter to Caeser scheme value
     *
     * @param ascii if argument is lower-case, convert it to upper-case first
     * @throws IllegalArgumentException if ascii is not an ASCII letter
     */
    protected static int toCaeserValue(int ascii) throws IllegalArgumentException {
        if (!isAsciiLetter(ascii)) throw new IllegalArgumentException(String.format("ascii is %d", ascii));
        int uppercase = Character.toUpperCase(ascii);
        return uppercase - 'A';
    }

    /**
     * convert a Caeser value to its equivalent upper-case ASCII letter
     *
     * @param caeser if argument is lower-case, convert it to upper-case first
     * @throws IllegalArgumentException if caeser is negative
     */
    protected static int toASCIIValue(int caeser) throws IllegalArgumentException {
        if (caeser < 0) throw new IllegalArgumentException(String.format("caeser is %d", caeser));
        return caeser % 26 + 'A';
    }


}

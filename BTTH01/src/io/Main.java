package io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Main {

    public static void main(String[] args) {
        // read content from InputStream

        // ask what type of encrypter to be used

        // encrypt content

        // ask where to export encrypted content
    }


    public static void write(final OutputStream ostream, final String content) throws IOException {
        ostream.write(content.getBytes());
    }
    public static String read(final InputStream istream) throws  IOException {
        return new String(istream.readAllBytes());
    }
    public static String encrypt(final IEncrypter IEncrypter, String content) {
        return IEncrypter.encrypt(content);
    }
}

package io;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CaesarEncrypterTest {

    private static Stream<Arguments> EncryptedArgumentProvider() {
        return Stream.of(
                Arguments.of(null, -1, null),
                Arguments.of(null, 13, null),
                Arguments.of("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", 7, "hijklmnopqrstuvwxyzabcdefgHIJKLMNOPQRSTUVWXYZABCDEFG"),
                Arguments.of("HELLO, MY NAME IS JEFT", -1, "GDKKN, LX MZLD HR IDES"),
                Arguments.of("HELLO, MY NAME IS JEFT", 1, "IFMMP, NZ OBNF JT KFGU"),
                Arguments.of("CHÚ CỪU ĐI LẠC GIỮA BẦY SÓI", 5, "HMÚ HỪZ ĐN QẠH LNỮF GẦD XÓN"),
                Arguments.of("cHú cỪu đI LạC GiỮa bẦy sÓi", 5, "hMú hỪz đN QạH LnỮf gẦd xÓn"),
                Arguments.of("123456789/*-+.!@#$%^&*(){}:|<>?", 5, "123456789/*-+.!@#$%^&*(){}:|<>?")
        );
    }

    private static Stream<Arguments> DecryptedArgumentProvider() {
        return Stream.of(
                Arguments.of(null, -5, null),
                Arguments.of(null, 5, null),
                Arguments.of("uvwxyzabcdefghijklmnopqrstUVWXYZABCDEFGHIJKLMNOPQRST", 20, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"),
                Arguments.of("Hfjxfw Hnumjw: Jshtij fsi Ijhtij Tsqnsj", 5, "Caesar Cipher: Encode and Decode Online"),
                Arguments.of("Xvznvm Xdkczm: Zixjyz viy Yzxjyz Jigdiz", -5, "Caesar Cipher: Encode and Decode Online"),
                Arguments.of("Jáuo đồun cắun cẻ roôun tộa unườp xbh sạp", 7, "Cánh đồng vắng vẻ không một người qua lại"),
                Arguments.of("123456789/*-+.!@#$%^&*(){}:|<>?", 5, "123456789/*-+.!@#$%^&*(){}:|<>?")
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -3, -5, -7, 9, 4, 2, 3, 43, 12})
    void getOffset(int expected) {
        CaesarEncrypter encrypter = new CaesarEncrypter(expected);
        assertEquals(expected, encrypter.getShifter());
    }

    @ParameterizedTest
    @MethodSource("EncryptedArgumentProvider")
    void encrypt(String input, int shifter, String expected) {
        CaesarEncrypter encrypter = new CaesarEncrypter(shifter);
        assertEquals(expected, encrypter.encrypt(input));
    }

    @ParameterizedTest
    @MethodSource("DecryptedArgumentProvider")
    void decrypt(String input, int shifter, String expected) {
        CaesarEncrypter encrypter = new CaesarEncrypter(shifter);
        assertEquals(expected, encrypter.decrypt(input));
    }


}
package io.github.desmonhak.Utils;

public class StringOperations {


    public static final String  ascii_letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String  ascii_lowercase = "abcdefghijklmnopqrstuvwxyz";
    public static final String  ascii_uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String  digits = "0123456789";
    public static final String  hexdigits = "0123456789abcdefABCDEF";
    public static final String  octdigits = "01234567";
    public static final String  printable = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ \t\n\r\u000B\u000C";
    public static final String  punctuation = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
    public static final String  whitespace = " \t\n\r\u000B\u000C";

    public static final String[] __all__ = {
            ascii_letters, ascii_lowercase, ascii_uppercase, digits, hexdigits,
            octdigits, printable, punctuation, whitespace
    };

}

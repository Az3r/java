package io;

import picocli.CommandLine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "encrypter",
        version = "1.0",
        mixinStandardHelpOptions = true,
        usageHelpWidth = 150
)
public class CommandLineProgram implements Callable<Integer> {

    private static final String CAESAR_ALGORITHM = "caesar";
    private static final String ROT13_ALGORITHM = "rot13";

    private String content;
    private IEncrypter encrypter;

    @CommandLine.Parameters(index = "0", description = "the input string")
    String input;
    @CommandLine.Option(names = "-rf", description = "treat input string as a file path and read it's content")
    boolean isReadFromFile;
    @CommandLine.Option(names = "-d", description = "decrypt input string instead of encrypting")
    boolean isDecrypt;
    @CommandLine.Option(names = "-wf", description = "write output to a file")
    File outputFile;
    @CommandLine.Option(names = {"-a", "--algorithm"}, required = true, arity = "1..2", description = "either caesar or rot13, provide shift value if caesar is specified")
    String[] algorithm;

    @Override
    public Integer call() throws Exception {
        content = input;
        if (input.startsWith("q")) return -1;
        if (isReadFromFile) content = Files.readString(Path.of(input));
        if (ROT13_ALGORITHM.equals(algorithm[0])) encrypter = new ROT13Encrypter();
        else if (CAESAR_ALGORITHM.equals(algorithm[0])) {
            if (algorithm.length == 2) encrypter = new CaesarEncrypter(Integer.parseInt(algorithm[1]));
            else throw new IllegalArgumentException("missing shift value for caesar algorithm");
        } else throw new IllegalArgumentException("algorithm is " + algorithm[0]);


        String output = (isDecrypt) ? encrypter.decrypt(content) : encrypter.encrypt(content);
        if (outputFile != null) {
            new FileOutputStream(outputFile).write(String.format("%s%n", output).getBytes());
        } else System.out.println(String.format("%s", output));

        return 0;
    }

    public static void main(String[] args) {
        System.out.println("Usage examples:");
        System.out.println("abcdef -a rot13 -> use ROT13 algorithm to encrypt 'abcdef' and write output to console");
        System.out.println("abcdef -a rot13 -d -> use ROT13 algorithm to decrypt 'abcdef' and write output to console");
        System.out.println("abcdef -a rot13 demo.txt-> use ROT13 algorithm to encrypt 'abcdef' and write output to file named test.txt in current working folder");
        System.out.println("demo.txt -rf -a caesar 10 -> read content from a file 'demo.txt' using CAESAR algorithm with shift value 10, output is written to console");
        System.out.println("Type --help for more information");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String[] params = scanner.nextLine().split("\\s+");
            int resultCode = new CommandLine(new CommandLineProgram())
                    .execute(params);
            if (resultCode < 0) break;
        }

        scanner.close();
    }
}

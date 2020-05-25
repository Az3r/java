package io;

import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "encryter-app",
        version = "1.0",
        description = "encrypt and decrypt strings, allow user to read input as well as write output from either file or console")
public class CommandLineProgram implements Callable<Integer> {

    private static final String HELP_MESSAGE = "<input-string | -i path/to/file> [-o path/to/file] [-d]. Where:\n" +
            "-o : specify that output string should be written to a file specified by a path, otherwise the string will be written to console\n" +
            "-i : specify that program should read input from file specified by a path\n" +
            "-d : specify that program should decrypt input string. (default is encrypting)";

    private static final String EXAMPLE_MESSAGE = "";


    @CommandLine.Option(names = "-rf", description = "treat input string as a file path and read it's content")
    boolean isReadFromFile;
    @CommandLine.Option(names = "-d", description = "decrypt input string instead of encrypting")
    boolean isDecrypt;
    @CommandLine.Option(names = "-wf", description = "write output to a file")
    File ouputFile;


    public static String getInput(String arg1, String arg2) throws IOException {
        if ("-i".equals(arg1)) {
            Path fPath = Path.of(arg2.strip());
            return Files.readString(fPath);
        }
        if (arg1.startsWith("-o") || arg1.startsWith("-d")) throw new IllegalArgumentException("input not found");
        return arg1;
    }


    @Override
    public Integer call() throws Exception {
        return null;
    }

    public static void main(String[] args) {
        System.out.println(HELP_MESSAGE);
        System.out.println(EXAMPLE_MESSAGE);
        Scanner scanner = new Scanner(System.in);
        String[] params = scanner.nextLine().split("\\s+");
        int resultCode = new CommandLine(new CommandLineProgram()).execute(params);
        scanner.close();
        System.exit(resultCode);
    }
}

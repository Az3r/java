import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public final class Theater {
    private Ticket[][] seats;
    private int totalPrices;
    private int totalSoldSeatPrices;
    private int totalSeats;
    private int soldSeats;

    public Theater() {
    }


    public boolean purchase(int row, int seat) {
        Ticket ticket = getTicket(row, seat);
        if (ticket == null) return false;

        // update info
        ++soldSeats;
        totalSoldSeatPrices += ticket.price;
        seats[row][seat].isSold = true;
        return true;
    }

    public boolean refund(int row, int seat) {
        Ticket ticket = getTicket(row, seat);
        if (ticket == null) return false;

        // update info
        --soldSeats;
        totalSoldSeatPrices -= ticket.price;
        seats[row][seat].isSold = false;
        return true;
    }

    public int getTotalSoldSeatPrices() {
        return totalSoldSeatPrices;
    }

    public int getAvailableSeat() {
        return totalSeats - soldSeats;
    }

    public int getAvailableSeat(int row) {
        if (row < 0 || row >= seats.length) return -1;
        return (int) Arrays.stream(seats[row]).filter(ticket -> !ticket.isSold).count();
    }

    public int getSoldSeats() {
        return soldSeats;
    }

    public int getSoldSeats(int row) {
        if (row < 0 || row >= seats.length) return -1;
        return (int) Arrays.stream(seats[row]).filter(ticket -> ticket.isSold).count();
    }

    public boolean isSeatEmpty(int row, int seat) {
        Ticket ticket = getTicket(row, seat);
        return ticket != null && ticket.isSold;
    }

    /**
     * @return ticket's price for a seat at specific positon or -1 if there is no such seat
     */
    public int getTicketPrice(int row, int seat) {
        Ticket ticket = getTicket(row, seat);
        return ticket == null ? -1 : ticket.price;
    }

    /**
     * @return null if no such seat exists
     * @apiNote index start at 0
     */
    public Ticket getTicket(int row, int seat) {
        if (row < 0 || seat < 0) return null;
        if (row >= seats.length || seat >= seats[row].length) return null;
        return seats[row][seat];
    }

    /**
     * Attemp to read data from file. If an error happens while reading data, the original state is guranteed to be unchanged.
     * <br>First line: <b>R S</b> where R is number of rows and S is number of seats for each row.
     * <br>Next R line: <b>S integer Ni</b> where Ni is the price of seat at position i, index start at 1.
     * <br>After that if you want to pre-assign some seats in theater:
     * <br>First line: <b>N</b> where N is the number of pre-assignment seats.
     * <br>Next N line: <b>R S</b> where R and S are seat position [R][S].
     * <br>For example see <b>input.txt</b> file.
     */
    public boolean readFromFile(File f) {
        try {
            FileInputStream stream = new FileInputStream(f);
            Scanner fscanner = new Scanner(stream);
            Ticket[][] output = new Ticket[fscanner.nextInt()][fscanner.nextInt()];
            for (int row = 0; row < output.length; ++row) {
                for (int seat = 0; seat < output[row].length; ++seat) {
                    output[row][seat] = new Ticket(fscanner.nextInt(), false);
                }
            }

            // check if there are pre-assignment seats
            if (fscanner.hasNextInt()) {
                int N = fscanner.nextInt();
                for (int i = 0; i < N; ++i) {
                    output[fscanner.nextInt() - 1][fscanner.nextInt() - 1].isSold = true;
                }
            }


            System.out.println("Read from file successfully");
            fscanner.close();
            seats = output;
            analyze();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println(String.format("File not found: %s", f.getAbsoluteFile().toString()));
            return false;
        } catch (NoSuchElementException e1) {
            System.out.println("Invalid file format");
            return false;
        }
    }

    /**
     * Read data from console.
     * <br>First line: <b>R S</b> where R is number of rows and S is number of seats for each row.
     * <br>Next R line: <b>S integer Ni</b> where Ni is the price of seat at position i, index start at 1.
     * <br>For example see <b>input.txt</b> file.
     */
    public boolean readFromConsole(Scanner scanner) {
        System.out.println("=".repeat(50));
        System.out.println("* First line: R S where R is number of rows and S is number of seats for each row");
        System.out.println("* Next R line: S integer Ni where Ni is the price of seat at position i (1<= i <= S)");
        System.out.println("* For example see input.txt file");
        System.out.println("=".repeat(50));

        try {
            Ticket[][] output = new Ticket[scanner.nextInt()][scanner.nextInt()];
            for (int row = 0; row < output.length; ++row) {
                for (int seat = 0; seat < output[row].length; ++seat) {
                    output[row][seat] = new Ticket(scanner.nextInt(), false);
                }
            }
            seats = output;
            analyze();
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("Invalid input!");
            return false;
        }
    }

    public void printSeatingChart() {
        if (seats == null) return;

        int padding = 4;
        int maxDigits = (int) Math.log10(seats.length) + 1;
        int maxColumnSize = String.format("row %d%s", seats.length, " ".repeat(padding)).length();
        int middle = maxColumnSize + 2 * (seats[0].length / 2) - 1;

        // print header
        System.out.println(String.format("%s%s", " ".repeat(middle), "Seats"));

        // print seat indices
        System.out.print(" ".repeat(maxColumnSize));
        for (int i = 0; i < seats[0].length; ++i) {
            System.out.print(i + 1);
            System.out.print(' ');
        }
        System.out.println();

        // print seats of each row
        for (int r = 0; r < seats.length; ++r) {

            int nDigit = (int) Math.log10(r + 1) + 1;
            System.out.print(String.format("Row %d%s", r + 1, " ".repeat(padding + maxDigits - nDigit)));

            for (int c = 0; c < seats[r].length; ++c) {
                System.out.print(String.format("%s%s", seats[r][c].isSold ? "*" : "#", " ".repeat((int) Math.log10(c + 1) + 1)));
            }
            System.out.println();

        }


    }

    private void analyze() {
        if (seats == null) return;

        totalPrices = 0;
        totalSoldSeatPrices = 0;
        totalSeats = 0;
        soldSeats = 0;

        for (int i = 0; i < seats.length; ++i) {
            totalSeats += seats[i].length;
            soldSeats += getSoldSeats(i);
            Arrays.stream(seats[i]).forEach(ticket -> {
                totalPrices += ticket.price;
                totalSoldSeatPrices += ticket.isSold ? ticket.price : 0;
            });
        }

    }
}

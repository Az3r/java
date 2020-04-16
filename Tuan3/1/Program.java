import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;

public final class Program {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // setup
        Player player = null;
        Type[] challengeTypes = new Type[] { CashChallenger.class, IntChallenger.class, StrengthChallenger.class };
        ArrayList<IChallenger> challengers = new ArrayList<>();

        // input guard
        System.out.print("Enter number of gates: ");
        int N = scanner.nextInt();
        System.out.println("1. Business gate");
        System.out.println("2. Academic gate");
        System.out.println("3. Power gate");
        System.out.println(String.format(
                "For the next %d line, each line enter t v where t is challenge type and v is challenge's difficulty", N));
        for (int i = 0; i < N; ++i) {
            int t = scanner.nextInt();
            int v = scanner.nextInt();
            challengers.add(IChallenger.Factory.create(challengeTypes[t - 1], v));
        }

        // input player's stats
        System.out.println("Enter c i s where c, i, s is player's cash, intelligence and strength, respectively:");
        int cash = scanner.nextInt();
        int intelligence = scanner.nextInt();
        int strength = scanner.nextInt();
        player = new Player(cash, strength, intelligence);

        boolean success = true;
        int gate = -1;
        for (int i = 0; i < N; ++i) {
            success = challengers.get(i).challenge(player);
            if (!success) {
                gate = i + 1;
                break;
            }
        }
        System.out.println(success ? "The prince has saved the princess" : ("The prince has failed at gate " + gate));
        scanner.close();
    }
}

public final class CashChallenger implements IChallenger {
    private int cash;
    private int amount;

    public CashChallenger(int cash, int amount) {
        this.cash = cash;
        this.amount = amount;
    }

    @Override
    public boolean challenge(Player player) throws NullPointerException {
        if (player == null)
            throw new NullPointerException("Player must not be null");
        player.cash -= this.cash * this.amount;
        return player.cash >= 0;
    }

}
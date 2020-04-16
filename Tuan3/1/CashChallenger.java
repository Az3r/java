
public final class CashChallenger implements IChallenger {
    private int cash;

    public CashChallenger(int cash) {
        this.cash = cash;
    }

    @Override
    public boolean challenge(Player player) throws NullPointerException {
        if (player == null)
            throw new NullPointerException("Player must not be null");
        player.cash -= this.cash;
        return player.cash >= 0;
    }

}
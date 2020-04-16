public final class IntChallenger implements IChallenger {
    private int intelligence;

    public IntChallenger(int intelligence) {
        this.intelligence = intelligence;
    }

    @Override
    public boolean challenge(Player player) throws NullPointerException {
        if (player == null)
            throw new NullPointerException("Player must not be null");
        return player.intelligence >= this.intelligence;
    }
}
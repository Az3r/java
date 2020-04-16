
public final class StrengthChallenger implements IChallenger{
    private int strength;
    public StrengthChallenger(int strength) {
        this.strength = strength;
    }

    @Override
    public boolean challenge(Player player) throws NullPointerException {
        if(player == null) throw new NullPointerException("Player must not be null");
        return player.strength >= this.strength;
    }
}
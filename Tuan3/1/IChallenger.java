import java.lang.reflect.Type;

public interface IChallenger {
    boolean challenge(Player player) throws NullPointerException;

    public final static class Factory {
        private Factory() {}
        public static IChallenger create(final Type challengerType, final int value) {
            if (challengerType.equals(IntChallenger.class))
                return new IntChallenger(value);
            else if (challengerType.equals(CashChallenger.class))
                return new CashChallenger(value);
            else if (challengerType.equals(StrengthChallenger.class))
                return new StrengthChallenger(value);
            return null;
        }
    }
}

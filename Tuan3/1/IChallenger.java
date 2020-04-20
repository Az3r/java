import java.lang.reflect.Type;

public interface IChallenger {
    boolean challenge(Player player) throws NullPointerException;

    public final static class Factory {
        private Factory() {
        }

        /**
         *
         * @param challengerType
         * @param value
         * @param amount this parameter is only used if the type of challenger is CashChallenger
         * @return
         */
        public static IChallenger create(final Type challengerType, final int value, final Integer amount) {
            if (challengerType.equals(IntChallenger.class))
                return new IntChallenger(value);
            else if (challengerType.equals(CashChallenger.class) && amount != null)
                return new CashChallenger(value, amount);
            else if (challengerType.equals(StrengthChallenger.class))
                return new StrengthChallenger(value);
            return null;
        }

        public static IChallenger create(final Type challengerType, final int value) {
            return create(challengerType, value, null);
        }
    }
}

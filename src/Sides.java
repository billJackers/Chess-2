public enum Sides {
    BLUE,
    RED;

    public static Sides invertSide(Sides side) {
        switch (side){
            case RED -> {
                return BLUE;
            }
            case BLUE -> {
                return RED;
            }
        }
        return null;  // wont ever reach here but the compiler wants this :l
    }
}

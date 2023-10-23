public class OffByN implements CharacterComparator {
    private int offset;

    public OffByN(int x) {
        offset = x;
    }
    @Override
    public boolean equalChars(char x, char y) {
        int diff = Math.abs(x - y);
        return diff == offset;
    }
}

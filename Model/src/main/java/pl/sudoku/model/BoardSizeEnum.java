package pl.sudoku.model;

public enum BoardSizeEnum {
    SMALL(4),
    CLASSIC(9),
    LARGE(16);

    /**
     * Number of fields in sudoku board row.
     */
    private final int size;

    /**
     * Constructor for BoardSizeEnum enum.
     * @param size desired board size
     */
    BoardSizeEnum(int size) {
        this.size = size;
    }

    /**
     * Returns size of sudoku board.
     *
     * @return size of board
     */
    public int getSize() {
        return size;
    }
}

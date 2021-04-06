package pl.sudoku.model;

public final class SudokuRow extends SudokuFieldGroup implements Cloneable {
    /**
     * Ctor for sudoku row.
     *
     * @param fields sudoku fields row consists of
     */
    public SudokuRow(final SudokuField[] fields) {
        super(fields);
    }

    /**
     * Checks if two rows are the same.
     * Checks if values in the rows are the same
     * (then returns true) but returns false also when
     * given object is a different class, null or has
     * different size (for future development).
     *
     * @param o object to compare
     * @return true if content of rows is the same, otherwise return false
     */
    @Override
    public boolean equals(final Object o) {

        if (!super.equals(o)) {
            return false;
        }

        return o.getClass() == getClass();
    }

    /**
     * Generates hash code of SudokuRow object.
     * Depends only on content of the group.
     * Hash of SudokuBox or SudokuColumn with the same content
     * is the same.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Returns a shallow copy of this SudokuRow.
     *
     * @return shallow copy
     */
    @Override
    public SudokuRow clone() throws CloneNotSupportedException {
        return (SudokuRow) super.clone();
    }
}

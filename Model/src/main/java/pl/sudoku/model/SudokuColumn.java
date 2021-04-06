package pl.sudoku.model;

public final class SudokuColumn extends SudokuFieldGroup implements Cloneable {
    /**
     * Ctor for sudoku column.
     *
     * @param fields group to be merged into column
     */
    public SudokuColumn(final SudokuField[] fields) {
        super(fields);
    }

    /**
     * Checks if two columns are the same.
     * Checks if values in the columns are the same
     * (then returns true) but returns false also when
     * given object is a different class, null or has
     * different size (for future development).
     *
     * @param o object to compare
     * @return true if content of columns is the same, otherwise return false
     */
    @Override
    public boolean equals(final Object o) {

        if (!super.equals(o)) {
            return false;
        }

        return o.getClass() == getClass();
    }

    /**
     * Generates hash code of SudokuColumn object.
     * Depends only on content of the group.
     * Hash of SudokuBox or SudokuRow with the same content
     * is the same.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Returns a shallow copy of this SudokuColumn.
     *
     * @return shallow copy
     */
    @Override
    public SudokuColumn clone() throws CloneNotSupportedException {
        return (SudokuColumn) super.clone();
    }

}

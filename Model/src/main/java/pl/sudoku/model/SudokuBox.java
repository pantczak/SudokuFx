package pl.sudoku.model;

public final class SudokuBox extends SudokuFieldGroup implements Cloneable {
    /**
     * Ctor for sudoku Box.
     *
     * @param fields group to be merged as a Box
     */
    public SudokuBox(final SudokuField[] fields) {
        super(fields);
    }

    /**
     * Checks if two boxes are the same.
     * Checks if values in the boxes are the same
     * (then returns true) but returns false also when
     * given object is a different class, null or has
     * different size (for future development).
     *
     * @param o object to compare
     * @return true if content of boxes is the same, otherwise return false
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
     * Hash of SudokuBox or SudokuBox with the same content
     * is the same.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Returns a shallow copy of this SudokuBox.
     *
     * @return shallow copy
     */
    @Override
    public SudokuBox clone() throws CloneNotSupportedException {
        return (SudokuBox) super.clone();
    }
}

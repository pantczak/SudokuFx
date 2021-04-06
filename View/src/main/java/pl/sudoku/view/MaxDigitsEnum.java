package pl.sudoku.view;

public enum MaxDigitsEnum {
    ONE(1),
    TWO(2);

    /**
     * Number of digits in TextField representing sudoku field.
     */
    private final int digits;

    /**
     * Constructor for MaxDigitsEnum.
     * @param digits number of digits
     */
    MaxDigitsEnum(int digits) {
        this.digits = digits;
    }

    /**
     * Returns max number of digits in TextField representing sudoku field.
     *
     * @return max number of digits
     */
    public int getDigits() {
        return digits;
    }
}
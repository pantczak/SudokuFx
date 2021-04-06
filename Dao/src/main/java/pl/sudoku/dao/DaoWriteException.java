package pl.sudoku.dao;


import java.io.IOException;

public class DaoWriteException extends IOException {
    /**
     * Constructor for DaoWriteException.
     *
     * @param cause of exception
     */
    public DaoWriteException(Throwable cause) {
        super(cause);
    }
}

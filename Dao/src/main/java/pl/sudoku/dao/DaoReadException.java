package pl.sudoku.dao;


import java.io.IOException;

public class DaoReadException extends IOException {
    /**
     * Constructor for DaoReadException.
     *
     * @param cause of exception
     */
    public DaoReadException(Throwable cause) {
        super(cause);
    }
}

package pl.sudoku.dao;

public interface Dao<T> extends AutoCloseable {
    /**
     * Read method for Dao interface.
     *
     * @return object read from file
     * @throws DaoReadException if error while reading happens
     */
    T read() throws DaoReadException;


    /**
     * Write method for Dao interface.
     *
     * @param obj to be written to file
     * @throws DaoWriteException if error while writing happens
     */
    void write(T obj) throws DaoWriteException;
}


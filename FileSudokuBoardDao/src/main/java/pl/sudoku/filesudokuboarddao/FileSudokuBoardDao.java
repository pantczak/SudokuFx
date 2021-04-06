package pl.sudoku.filesudokuboarddao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import pl.sudoku.dao.Dao;
import pl.sudoku.model.SudokuBoard;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {

    /**
     * String representing file with extension.
     */
    private final String fileName;

    /**
     * Constructor for FileSudokuBoardDao class.
     *
     * @param fileString file for IO operations
     */
    public FileSudokuBoardDao(final String fileString) {
        this.fileName = fileString;
    }

    /**
     * Implementation of Dao interface read method.
     * Uses try-with-resources instruction.
     *
     * @return SudokuBoard obj read from file
     * @throws FileDaoReadException if error while reading from file happens
     */
    @Override
    public SudokuBoard read() throws FileDaoReadException {
        SudokuBoard sudokuBoard;
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             ObjectInputStream objectInputStream =
                     new ObjectInputStream(fileInputStream)) {
            sudokuBoard = (SudokuBoard) objectInputStream.readObject();
        } catch (ClassNotFoundException | IOException cause) {
            throw new FileDaoReadException(cause);
        }
        return sudokuBoard;
    }

    /**
     * Implementation of Dao interface write method.
     * Uses try-with-resources instruction.
     *
     * @param obj SudokuBoard to be serialized
     * @throws FileDaoWriteException if error while writing to file happens
     */
    @Override
    public void write(final SudokuBoard obj) throws FileDaoWriteException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName);
             ObjectOutputStream objectOutputStream =
                     new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(obj);
        } catch (IOException cause) {
            throw new FileDaoWriteException(cause);
        }
    }

    /**
     * Implementation of AutoCloseable.close() method.
     *
     * @throws Exception if error occurred when closing resources
     */
    @Override
    public void close() throws Exception {

    }

    /**
     * Deprecated finalize method for emergency closing streams.
     *
     * @throws Throwable exception if unable to close resources
     */
    @Deprecated(since = "9")
    @Override
    public void finalize() throws Throwable {
        super.finalize();
    }
}

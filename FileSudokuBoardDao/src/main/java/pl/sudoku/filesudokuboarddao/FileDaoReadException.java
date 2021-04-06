package pl.sudoku.filesudokuboarddao;

import java.util.ResourceBundle;
import pl.sudoku.dao.DaoReadException;

public class FileDaoReadException extends DaoReadException {

    ResourceBundle resourceBundle
            = ResourceBundle.getBundle("pl.sudoku.filesudokuboarddao.fileDaoExceptions");


    /**
     * Constructor for FileDaoReadException.
     * @param cause of exception
     */
    public FileDaoReadException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getLocalizedMessage() {
        return resourceBundle.getString("ReadExceptionMessage");
    }
}

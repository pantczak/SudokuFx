package pl.sudoku.filesudokuboarddao;

import java.util.ResourceBundle;
import pl.sudoku.dao.DaoWriteException;

public class FileDaoWriteException extends DaoWriteException {

    ResourceBundle resourceBundle
            = ResourceBundle.getBundle("pl.sudoku.filesudokuboarddao.fileDaoExceptions");

    /**
     * Constructor for FileDaoWriteException.
     * @param cause of exception
     */
    public FileDaoWriteException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getLocalizedMessage() {
        return resourceBundle.getString("WriteExceptionMessage");
    }
}

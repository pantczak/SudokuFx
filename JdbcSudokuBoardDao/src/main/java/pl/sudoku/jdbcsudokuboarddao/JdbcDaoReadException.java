package pl.sudoku.jdbcsudokuboarddao;

import java.util.ResourceBundle;
import pl.sudoku.dao.DaoReadException;

public class JdbcDaoReadException extends DaoReadException {

    ResourceBundle resourceBundle
            = ResourceBundle.getBundle("jdbcDaoExceptions");


    /**
     * Constructor for JdbcDaoReadException.
     * @param cause of exception
     */
    public JdbcDaoReadException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getLocalizedMessage() {
        return resourceBundle.getString("JdbcReadExceptionMessage");
    }
}

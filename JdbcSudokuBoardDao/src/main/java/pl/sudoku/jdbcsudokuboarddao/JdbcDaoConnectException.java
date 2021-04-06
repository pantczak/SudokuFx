package pl.sudoku.jdbcsudokuboarddao;

import java.util.ResourceBundle;

public class JdbcDaoConnectException extends Exception {
    ResourceBundle resourceBundle
            = ResourceBundle.getBundle("jdbcDaoExceptions");


    /**
     * Constructor for JdbcDaoConnectException.
     * @param cause of exception
     */
    public JdbcDaoConnectException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getLocalizedMessage() {
        return resourceBundle.getString("JdbcConnectExceptionMessage");
    }
}

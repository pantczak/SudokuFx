package pl.sudoku.jdbcsudokuboarddao;

import org.junit.Test;

import java.io.IOException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class ExceptionsTest {

    @Test
    public void readException_ShouldReturnProper_LocalizedMessage() {
        Locale.setDefault(new Locale("en"));
        assertEquals(new JdbcDaoReadException(new IOException()).getLocalizedMessage(), "SudokuBoard object could not be read from database");
    }

    @Test
    public void writeException_ShouldReturnProper_LocalizedMessage() {
        Locale.setDefault(new Locale("en"));
        assertEquals(new JdbcDaoWriteException(new IOException()).getLocalizedMessage(), "SudokuBoard object could not be written to database");
    }

    @Test
    public void ConnectException_ShouldReturnProper_LocalizedMessage() {
        Locale.setDefault(new Locale("en"));
        assertEquals(new JdbcDaoConnectException(new IOException()).getLocalizedMessage(), "Failed to connect to Sudoku database");
    }

    @Test
    public void DeleteException_ShouldReturnProper_LocalizedMessage() {
        Locale.setDefault(new Locale("en"));
        assertEquals(new JdbcDaoDeleteException(new IOException()).getLocalizedMessage(), "SudokuBoard object could not be deleted from database");
    }
}

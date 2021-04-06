package pl.sudoku.jdbcsudokuboarddao;

import org.junit.Test;
import pl.sudoku.model.BacktrackingSudokuSolver;
import pl.sudoku.model.BoardSizeEnum;
import pl.sudoku.model.SudokuBoard;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcSudokuBoardDaoTest {

    @Test
    public void read_SaveBoard_ShouldLoadCorrectly() {
        try (JdbcSudokuBoardDao jdbcSudokuBoardDao =
                     new JdbcSudokuBoardDao("Test21537")) {
            SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver(),
                    BoardSizeEnum.CLASSIC);
            sudokuBoard.set(0, 0, 1);
            jdbcSudokuBoardDao.write(sudokuBoard);
            SudokuBoard read = jdbcSudokuBoardDao.read();
            jdbcSudokuBoardDao.delete();
            assertEquals(sudokuBoard, read);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void read_SudokuBoard_fromWrongSave_ShouldThrowException() {
        try (JdbcSudokuBoardDao jdbcSudokuBoardDao = new JdbcSudokuBoardDao("WrongName")) {
            assertThrows(JdbcDaoReadException.class, jdbcSudokuBoardDao::read);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Write_SudokuBoard_toWrongSave_ShouldThrowException() {
        try (JdbcSudokuBoardDao jdbcSudokuBoardDao = new JdbcSudokuBoardDao("WrongWrite5")) {
            SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver(),
                    BoardSizeEnum.CLASSIC);
            sudokuBoard.set(0, 0, 1);
            jdbcSudokuBoardDao.write(sudokuBoard);
            assertThrows(JdbcDaoWriteException.class, () -> jdbcSudokuBoardDao.write(sudokuBoard));
            jdbcSudokuBoardDao.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void Update_SudokuBoard_toWrongSave_ShouldThrowException() {
        try (JdbcSudokuBoardDao jdbcSudokuBoardDao = new JdbcSudokuBoardDao("Update5")) {
            SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver(),
                    BoardSizeEnum.CLASSIC);
            sudokuBoard.set(0, 0, 1);
            jdbcSudokuBoardDao.write(sudokuBoard);
            assertDoesNotThrow(() -> jdbcSudokuBoardDao.update(sudokuBoard));
            jdbcSudokuBoardDao.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void Delete_SudokuBoard_fromWrongSave_ShouldThrowException() {
        try (JdbcSudokuBoardDao jdbcSudokuBoardDao = new JdbcSudokuBoardDao("WrongDelete")) {
            assertThrows(JdbcDaoDeleteException.class, jdbcSudokuBoardDao::delete);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void GetSave_SudokuBoard_shouldReturnProperSave() {
        try (JdbcSudokuBoardDao jdbcSudokuBoardDao = new JdbcSudokuBoardDao("exampleSave")) {
            assertEquals("exampleSave",jdbcSudokuBoardDao.getSaveName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void SetSave_SudokuBoard_shouldSetProperSave(){
        try (JdbcSudokuBoardDao jdbcSudokuBoardDao = new JdbcSudokuBoardDao("exampleSave")) {
            jdbcSudokuBoardDao.setSaveName("newExampleSave");
            assertEquals("newExampleSave",jdbcSudokuBoardDao.getSaveName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readAvailable_SudokuBoard_shouldContainSavedBoard(){
        try (JdbcSudokuBoardDao jdbcSudokuBoardDao = new JdbcSudokuBoardDao("ListRead1")) {
            SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver(),
                    BoardSizeEnum.CLASSIC);
            sudokuBoard.set(0, 0, 6);
            jdbcSudokuBoardDao.write(sudokuBoard);
            assertTrue(jdbcSudokuBoardDao.readAvailable().contains("ListRead1"));
            jdbcSudokuBoardDao.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
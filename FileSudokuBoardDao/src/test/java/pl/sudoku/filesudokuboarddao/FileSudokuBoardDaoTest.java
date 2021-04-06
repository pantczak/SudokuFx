package pl.sudoku.filesudokuboarddao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.sudoku.dao.Dao;
import pl.sudoku.model.BacktrackingSudokuSolver;
import pl.sudoku.model.BoardSizeEnum;
import pl.sudoku.model.SudokuBoard;
import java.lang.reflect.Method;

class FileSudokuBoardDaoTest {

    @Test
    public void read_write_SerializeAndDeserializeSudokuBoard_ShouldNotChange() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC);
        SudokuBoard sudokuBoard2;

        try (Dao<SudokuBoard> fileSudokuBoardDao = SudokuBoardDaoFactory.getFileDao("properFile")) {
            fileSudokuBoardDao.write(sudokuBoard);
            sudokuBoard2 = fileSudokuBoardDao.read();
            Assertions.assertEquals(sudokuBoard, sudokuBoard2);
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void read_FromNonExistentFile_ShouldThrowException() {
        Dao<SudokuBoard> fileSudokuBoardDao = SudokuBoardDaoFactory.getFileDao("nonExistent");
        Assertions.assertThrows(FileDaoReadException.class, fileSudokuBoardDao::read);
    }

    @Test
    public void write_ToImproperFile_ShouldThrowException() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC);
        Dao<SudokuBoard> fileSudokuBoardDao = SudokuBoardDaoFactory.getFileDao("/:;:");
        Assertions.assertThrows(FileDaoWriteException.class,
                () -> fileSudokuBoardDao.write(sudokuBoard));
    }

    @Test
    public void finalize_ReflectMethodAddInvoke_ShouldNotThrowAnyException() throws NoSuchMethodException {
        Dao<SudokuBoard> fileSudokuBoardDao = SudokuBoardDaoFactory.getFileDao("Test");

        Method finalize = fileSudokuBoardDao.getClass().getDeclaredMethod("finalize");
        finalize.setAccessible(true);

        Assertions.assertDoesNotThrow(() -> finalize.invoke(fileSudokuBoardDao));
    }
}
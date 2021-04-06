package pl.sudoku.filesudokuboarddao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SudokuBoardDaoFactoryTest {

    @Test
    public void getFileDao_ShouldNotReturnNull() {
        Assertions.assertNotNull(SudokuBoardDaoFactory.getFileDao("testfile.txt"));
    }
}
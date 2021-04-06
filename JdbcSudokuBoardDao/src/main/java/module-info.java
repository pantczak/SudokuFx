 open module pl.sudoku.jdbcsudokuboarddao {
    requires pl.sudoku.model;
    requires pl.sudoku.dao;
    requires java.sql;
    requires org.apache.commons.lang3;
    requires com.microsoft.sqlserver.jdbc;

    exports pl.sudoku.jdbcsudokuboarddao;
}
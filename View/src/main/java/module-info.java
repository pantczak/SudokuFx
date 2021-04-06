open module pl.sudoku.view {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires transitive pl.sudoku.filesudokuboarddao;
    requires transitive pl.sudoku.dao;
    requires transitive pl.sudoku.fxmodel;
    requires pl.sudoku.jdbcsudokuboarddao;
    requires java.desktop;
    requires slf4j.jdk14;
    requires slf4j.api;
    requires org.apache.commons.lang3;

    exports pl.sudoku.view;
    exports pl.sudoku.view.bundles;
}
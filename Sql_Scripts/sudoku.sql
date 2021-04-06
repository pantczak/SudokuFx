CREATE DATABASE Sudoku;

USE Sudoku;

CREATE table SudokuBoards(
    savename varchar(15) NOT NULL,
    board varbinary(max),
    PRIMARY KEY (savename)
);

INSERT INTO SudokuBoards VALUES ('Tester Baz', 7312);

SELECT * FROM SudokuBoards;

DELETE FROM SudokuBoards WHERE savename = 'Tester Baz';

SELECT * FROM SudokuBoards;

DROP TABLE SudokuBoards;


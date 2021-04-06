package pl.sudoku.jdbcsudokuboarddao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import pl.sudoku.dao.Dao;
import pl.sudoku.dao.DaoReadException;
import pl.sudoku.dao.DaoWriteException;
import pl.sudoku.model.SudokuBoard;


public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {

    /**
     * Connection session with database.
     * Established once per class lifetime.
     */
    private final Connection connection;

    /**
     * String containing name of game save to be stored in database.
     */
    private String saveName;

    /**
     * Method connecting Dao to database.
     *
     * @param saveName string representing SudokuBoard Save
     * @throws JdbcDaoConnectException if unable to connect to database
     */
    public JdbcSudokuBoardDao(String saveName) throws JdbcDaoConnectException {
        this.saveName = saveName;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;"
                    + "databaseName=Sudoku;", "SA", "Password.2020");
            connection.setAutoCommit(false);
        } catch (ClassNotFoundException | SQLException e) {
            throw new JdbcDaoConnectException(e);
        }
    }

    /**
     * Method reading SudokuBoard save from database.
     *
     * @throws JdbcDaoReadException if database is unavailable or n of rows read from database != 1
     */
    @Override
    public SudokuBoard read() throws DaoReadException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT board FROM "
                + "SudokuBoards WHERE savename = ?");
        ) {
            preparedStatement.setString(1, saveName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                connection.commit();
                if (!resultSet.next()) {
                    throw new JdbcDaoReadException(new SQLException());
                }

                byte[] input = resultSet.getBytes(1);

                try (ByteArrayInputStream bais = new ByteArrayInputStream(input);
                     ObjectInputStream ois = new ObjectInputStream(bais)) {

                    return (SudokuBoard) ois.readObject();
                }
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new JdbcDaoReadException(e);
        }
    }

    /**
     * Method writing SudokuBoard save to database.
     *
     * @param obj SudokuBoard object to save in database
     * @throws JdbcDaoWriteException if database is unavailable or rows affected by write != 1
     */
    @Override
    public void write(SudokuBoard obj) throws DaoWriteException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO "
                + "SudokuBoards VALUES (?,?)")) {
            preparedStatement.setString(1, saveName);

            try (ByteArrayOutputStream boos = new ByteArrayOutputStream();
                 ObjectOutputStream oos = new ObjectOutputStream(boos)) {

                oos.writeObject(obj);
                preparedStatement.setBytes(2, boos.toByteArray());

                int affectedRows = preparedStatement.executeUpdate();
                connection.commit();

                if (affectedRows != 1) {
                    throw new JdbcDaoWriteException(new SQLException());
                }
            }

        } catch (SQLException | IOException e) {
            throw new JdbcDaoWriteException(e);
        }
    }

    /**
     * Method deleting SudokuBoard save from database.
     *
     * @throws JdbcDaoDeleteException if database is unavailable or rows affected by delete != 1
     */
    public void delete() throws JdbcDaoDeleteException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM "
                + "SudokuBoards WHERE savename = ?")) {
            preparedStatement.setString(1, saveName);

            int affectedRows = preparedStatement.executeUpdate();
            connection.commit();

            if (affectedRows != 1) {
                throw new JdbcDaoDeleteException(new SQLException());
            }
        } catch (SQLException e) {
            throw new JdbcDaoDeleteException(e);
        }
    }

    /**
     * Method updating SudokuBoard save in database.
     *
     * @throws JdbcDaoWriteException if database is unavailable or rows affected by update != 1
     */
    public void update(SudokuBoard obj) throws DaoWriteException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE "
                + "SudokuBoards SET board = ? WHERE savename = ?")) {
            preparedStatement.setString(2, saveName);

            try (ByteArrayOutputStream boos = new ByteArrayOutputStream();
                 ObjectOutputStream oos = new ObjectOutputStream(boos)) {

                oos.writeObject(obj);
                preparedStatement.setBytes(1, boos.toByteArray());

                int affectedRows = preparedStatement.executeUpdate();
                connection.commit();

                if (affectedRows != 1) {
                    throw new JdbcDaoWriteException(new SQLException());
                }
            }

        } catch (SQLException | IOException e) {
            throw new JdbcDaoWriteException(e);
        }
    }

    /**
     * Method returning ArrayList of saves.
     *
     * @return ArrayList consisting of saveNames
     * @throws JdbcDaoReadException if unable to access data form database
     */
    public ArrayList<String> readAvailable() throws JdbcDaoReadException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT savename FROM SudokuBoards");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            connection.commit();
            ArrayList<String> available = new ArrayList<String>();

            while (resultSet.next()) {
                available.add(resultSet.getString(1));
            }

            return available;
        } catch (SQLException e) {
            throw new JdbcDaoReadException(e);
        }
    }


    @Override
    public void close() throws Exception {
        connection.close();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        connection.close();
    }

    /**
     * Getter for fileName field.
     *
     * @return fileName string -> file with extension
     */
    public String getSaveName() {
        return saveName;
    }

    /**
     * Setter for fileName field.
     *
     * @param saveName new fileName
     */
    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }
}

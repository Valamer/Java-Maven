import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseManipulations {
    public static Connection connection;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Hillel";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public DataBaseManipulations() {

        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Connection established.");
        } catch (SQLException e) {
            System.err.println("Failed to establish connection to DB.");
            e.printStackTrace();
        }
    }

    public void clearGames() {
        String sql = "DELETE FROM Games";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void addGame(Game game) {
        String sql = "INSERT INTO Games (name, release_date, rating, cost, description) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, game.getName());
            statement.setDate(2, new java.sql.Date(game.getReleaseDate().getTime()));
            statement.setFloat(3, game.getRating());
            statement.setDouble(4, game.getCost());
            statement.setString(5, game.getDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteGame(int id) {
        String sql = "DELETE FROM Games WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Game searchGameByName(String name) {
        String sql = "SELECT * FROM Games WHERE name LIKE ?";
        List<Game> games = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                games.add(extractGameFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games.isEmpty() ? null : games.get(0);
    }

    public Game searchGameById(int id) {
        String sql = "SELECT * FROM Games WHERE id = ?";
        Game game = null;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                game = extractGameFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return game;
    }

    public List<Game> filterGamesByCost(double minCost, double maxCost) {
        String sql = "SELECT * FROM Games WHERE cost BETWEEN ? AND ?";
        List<Game> games = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, minCost);
            pstmt.setDouble(2, maxCost);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                games.add(extractGameFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

    public List<Game> filterGamesByRating(float minRating) {
        String sql = "SELECT * FROM Games WHERE rating >= ?";
        List<Game> games = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setFloat(1, minRating);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                games.add(extractGameFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

    public List<Game> getAllGamesSortedByCreationDate() {
        String sql = "SELECT * FROM Games ORDER BY creation_date DESC";
        List<Game> games = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                games.add(extractGameFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

    public List<Game> getAllGames() {
        String sql = "SELECT * FROM Games";
        List<Game> games = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                games.add(extractGameFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

    public Game extractGameFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        Date releaseDate = rs.getDate("release_date");
        float rating = rs.getFloat("rating");
        double cost = rs.getDouble("cost");
        String description = rs.getString("description");
        Date creationDate = rs.getDate("creation_date");

        return new Game(id, name, releaseDate, rating, cost, description, creationDate);
    }
}

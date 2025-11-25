package lab.score;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.h2.tools.Server;

public class ScoreRepository {

    private static Server server = null;
    private static Connection connection;

    private static Connection getConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection("jdbc:h2:file:./score-db:scoreDB");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void init() {
        try {
            Connection connection = getConnection();
            if (connection == null) {
                System.err.println("Failed to connect to database.");
                return;
            }
            try (Statement s = connection.createStatement()) {
                s.execute("""
                        CREATE TABLE IF NOT EXISTS Scores (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            points integer
                        );
                    """);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void save(Score score) throws ScoreException {
        Connection c = getConnection();
        if (c == null) {
            System.err.println("Failed to connect to database.");
            return;
        }
        try (PreparedStatement s = c.prepareStatement("INSERT INTO Scores (name, points) VALUES (?, ?)")) {
            s.setString(1, score.getNickName());
            s.setInt(2, score.getScore());
            s.executeUpdate();
        } catch (Exception e) {
            throw new ScoreException("Failed to save score", e);
        }
    }

    public static void save(List<Score> scores) throws ScoreException {
        Connection c = getConnection();
        if (c == null) {
            System.err.println("Failed to connect to database.");
            return;
        }
        try (PreparedStatement s = c.prepareStatement("INSERT INTO Scores (name, points) VALUES (?, ?)")) {
            for (Score score : scores) {
                    s.clearParameters();
                    s.setString(1, score.getNickName());
                    s.setInt(2, score.getScore());
                    s.addBatch();
            }
            s.executeBatch();
        } catch (SQLException e) {
            throw new ScoreException("Failed to save score", e);
        }
    }

    public static List<Score> load() throws ScoreException {
        List<Score> scores = new ArrayList<>();
        Connection c = getConnection();
        if (c == null) {
            System.err.println("Failed to connect to database.");
            return Collections.emptyList();
        }
        try (Statement s = c.createStatement()) {
            ResultSet rs = s.executeQuery("SELECT * FROM Scores");
            while (rs.next()) {
                scores.add(new Score(rs.getLong("id"), rs.getString("name"), rs.getInt("points")));
            }
        } catch (SQLException e) {
            throw new ScoreException("Failed to load score", e);
        }

        return scores;
    }

    public static void delete(Score score) throws ScoreException {
        Connection c = getConnection();
        if (c == null) {
            System.err.println("Failed to connect to database.");
            return;
        }
        if (score == null) {
            System.err.println("Failed to delete score from database. Score is null");
            return;
        }
        try (PreparedStatement s = c.prepareStatement("DELETE FROM Scores WHERE id = ?")) {
            s.setLong(1, score.getId());
            s.executeUpdate();
        } catch (SQLException e) {
            throw new ScoreException("Failed to delete score", e);
        }
    }

    public static void startDBWebServer() {
        // Start HTTP server for access H2 DB for look inside
        Path h2ServerProperties = Paths.get(System.getProperty("user.home"), ".h2.server.properties");
        try {
            Files.writeString(h2ServerProperties, "0=Generic H2 (Embedded)|org.h2.Driver|jdbc\\:h2\\:file\\:./score-db\\:scoreDB|",
                StandardOpenOption.CREATE_NEW);
        } catch (IOException e) {
            System.out.println("File " + h2ServerProperties + " probably exists.");
        }
        stopDBWebServer();
        try {
            server = Server.createWebServer();
            System.out.println(server.getURL());
            server.start();
            System.out.println("DB Web server started!");
        } catch (SQLException e) {
            System.out.println("Cannot create DB web server.");
            e.printStackTrace();
        }
    }

    public static void stopDBWebServer() {
        // Stop HTTP server for access H2 DB
        if (server != null) {
            System.out.println("Ending DB web server BYE.");
            server.stop();
        }
    }

    private static void waitForKeyPress() {
        System.out.println("Waitnig for Key press (ENTER)");
        try {
            System.in.read();
        } catch (IOException e) {
            System.out.println("Cannot read input from keyboard.");
            e.printStackTrace();
        }
    }
}

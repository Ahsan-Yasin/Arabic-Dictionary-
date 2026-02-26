package DAOLayer;

import Config.Connector.DBConnection;
import DTOLayer.PatternDTO;
import DTOLayer.WordDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatternDAO {

    // Add Pattern
    public int addPattern(PatternDTO pattern) {
        String query = "INSERT INTO Patterns (word_id, pattern_name) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, pattern.getWordId());
            stmt.setString(2, pattern.getPatternName());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public String getPatternId(String patternText) {
        String query = "SELECT id FROM patterns WHERE pattern_name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, patternText);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return String.valueOf(rs.getInt("id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // or "" if you prefer
    }
    // Search patterns by text (partial match)
    public List<PatternDTO> getPatternsByText(String text) {
        List<PatternDTO> list = new ArrayList<>();
        String query = "SELECT * FROM Patterns WHERE pattern_name LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + text + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(mapPattern(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public PatternDTO getPatternByExactText(String text) {
        String query = "SELECT * FROM Patterns WHERE pattern_name = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, text);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return mapPattern(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Get by ID
    public PatternDTO getPatternById(int id) {
        String query = "SELECT * FROM Patterns WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return mapPattern(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get patterns for a word
    public List<PatternDTO> getPatternsByWordId(int wordId) {
        List<PatternDTO> list = new ArrayList<>();
        String query = "SELECT * FROM Patterns WHERE word_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, wordId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) list.add(mapPattern(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get all patterns
    public List<PatternDTO> getAllPatterns() {
        List<PatternDTO> list = new ArrayList<>();
        String query = "SELECT * FROM Patterns";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) list.add(mapPattern(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Update
    public boolean updatePattern(PatternDTO pattern) {
        String query = "UPDATE Patterns SET pattern_name=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, pattern.getPatternName());

            stmt.setInt(2, pattern.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<WordDTO> getWordsByPatternName(String patternName) {
        List<WordDTO> list = new ArrayList<>();

        String query =
                "SELECT w.* FROM Words w " +
                        "JOIN Patterns p ON w.id = p.word_id " +
                        "WHERE p.pattern_name = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, patternName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new WordDTO(
                        rs.getInt("pattern_id")   ,
                        rs.getInt("id"),
                        rs.getString("arabic_form"),
                        rs.getString("base_form"),
                        rs.getString("urdu_meaning"),
                        rs.getString("part_of_speech"),
                        rs.getInt("root_id")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Delete
    public boolean deletePattern(int id) {
        String query = "DELETE FROM Patterns WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private PatternDTO mapPattern(ResultSet rs) throws SQLException {
        return new PatternDTO(
                rs.getInt("id"),
                rs.getInt("word_id"),
                rs.getString("pattern_name")

        );
    }
}

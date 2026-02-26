package DAOLayer;


import Config.Connector.DBConnection;
import DTOLayer.ExampleDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExampleDAO {

    public int addExample(ExampleDTO example) {
        String query = "INSERT INTO Examples (id ,  word_id, sentence, source, reference) VALUES (?,?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, example.getId());
            stmt.setInt(2, example.getWordId());
            stmt.setString(3, example.getSentence());
            stmt.setString(4, example.getSource());
            stmt.setString(5, example.getReference());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
                return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public ExampleDTO getExampleById(int id) {
        String query = "SELECT * FROM Examples WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return mapResultSetToExample(rs);

        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<ExampleDTO> getExamplesByWordId(int wordId) {
        List<ExampleDTO> examples = new ArrayList<>();
        String query = "SELECT * FROM Examples WHERE word_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, wordId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) examples.add(mapResultSetToExample(rs));

        } catch (SQLException e) { e.printStackTrace(); }
        return examples;
    }

    public boolean updateExample(ExampleDTO example) {
        String query = "UPDATE Examples SET sentence=?, source=?, reference=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, example.getSentence());
            stmt.setString(2, example.getSource());
            stmt.setString(3, example.getReference());
            stmt.setInt(4, example.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean deleteExample(int id) {
        String query = "DELETE FROM Examples WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private ExampleDTO mapResultSetToExample(ResultSet rs) throws SQLException {
        return new ExampleDTO(
                rs.getInt("id"),
                rs.getInt("word_id"),
                rs.getString("sentence"),
                rs.getString("source"),
                rs.getString("reference")
        );
    }
    // Get all examples
    public List<ExampleDTO> getAllExamples() {
        List<ExampleDTO> examples = new ArrayList<>();
        String query = "SELECT * FROM Examples";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) examples.add(mapResultSetToExample(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return examples;
    }

    // Search examples by text (partial match in sentence)
    public List<ExampleDTO> getExamplesByText(String text) {
        List<ExampleDTO> examples = new ArrayList<>();
        String query = "SELECT * FROM Examples WHERE sentence LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + text + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) examples.add(mapResultSetToExample(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return examples;
    }

}


package DAOLayer;

import Config.Connector.DBConnection;
import DTOLayer.LemmaDTO;
import DTOLayer.LemmaDTO;
import DTOLayer.WordDTO;

import java.sql.*;
        import java.util.ArrayList;
import java.util.List;

public class LemmatizationDAO {

    // Add Lemma
    public int addLemma(LemmaDTO lemma) {
        String query = "INSERT INTO Lemmatization (word_id, lemma_text, part_of_speech) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, lemma.getWordId());
            stmt.setString(2, lemma.getLemmaText());
            stmt.setString(3, lemma.getPartOfSpeech());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Get by ID
    public LemmaDTO getLemmaById(int id) {
        String query = "SELECT * FROM Lemmatization WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return mapLemma(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get lemmas of a word
    public List<LemmaDTO> getLemmasByWordId(int wordId) {
        List<LemmaDTO> list = new ArrayList<>();
        String query = "SELECT * FROM Lemmatization WHERE word_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, wordId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) list.add(mapLemma(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get all lemmas
    public List<LemmaDTO> getAllLemmas() {
        List<LemmaDTO> list = new ArrayList<>();
        String query = "SELECT * FROM Lemmatization";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) list.add(mapLemma(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Update
    public boolean updateLemma(LemmaDTO lemma) {
        String query = "UPDATE Lemmatization SET lemma_text=?, part_of_speech=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, lemma.getLemmaText());
            stmt.setString(2, lemma.getPartOfSpeech());
            stmt.setInt(3, lemma.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete
    public boolean deleteLemma(int id) {
        String query = "DELETE FROM Lemmatization WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private LemmaDTO mapLemma(ResultSet rs) throws SQLException {
        return new LemmaDTO(
                rs.getInt("id"),
                rs.getInt("word_id"),
                rs.getString("lemma_text"),
                rs.getString("part_of_speech")
        );
    }
    public List<WordDTO> getWordsByLemma(String lemmaText) {
        List<WordDTO> words = new ArrayList<>();


        String query = "SELECT w.* FROM words w " +
                "JOIN Lemmatization l ON w.id = l.word_id " +
                "WHERE l.lemma_text = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, lemmaText);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                words.add(new WordDTO(
                        rs.getInt("pattern_id")  ,
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
        return words;
    }

}

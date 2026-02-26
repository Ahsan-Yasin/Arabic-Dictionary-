package DAOLayer;

import Config.Connector.DBConnection;
import DTOLayer.WordDTO;
import DTOLayer.ExampleDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WordDAO {

    private final ExampleDAO exampleDAO = new ExampleDAO();

    // Add Word
    public int addWord(WordDTO word) {
        String query = "INSERT INTO Words (arabic_form, base_form, urdu_meaning, part_of_speech, root_id, pattern_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, word.getArabicForm());
            stmt.setString(2, word.getBaseForm());
            stmt.setString(3, word.getUrduMeaning());
            stmt.setString(4, word.getPartOfSpeech());
            stmt.setObject(5, word.getRootId() == 0 ? null : word.getRootId());
            stmt.setObject(6, word.getPatternId() == 0 ? null : word.getPatternId()); // new pattern_id
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int wordId = rs.getInt(1);
                return wordId;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    // In your WordDAO
    public int getWordIdByArabicForm(String arabicForm) {
        String query = "SELECT id FROM words WHERE arabic_form = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, arabicForm);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (SQLException e) { e.printStackTrace(); }
        return -1; // not found
    }

    public List<WordDTO> getWordsBySubstring(String substring) {
        List<WordDTO> words = new ArrayList<>();
        String query = "SELECT * FROM Words WHERE arabic_form LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + substring + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                WordDTO word = mapWord(rs);
                words.add(word);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return words;
    }

    public WordDTO getWordById(int id) {
        WordDTO word = null;
        String query = "SELECT * FROM Words WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                word = mapWord(rs);
                word.setExamples(exampleDAO.getExamplesByWordId(id));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return word;
    }

    public WordDTO getWordByBaseForm(String baseForm) {
        WordDTO word = null;
        String query = "SELECT * FROM Words WHERE base_form = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, baseForm);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                word = mapWord(rs);
                word.setExamples(exampleDAO.getExamplesByWordId(word.getId()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return word;
    }

    public WordDTO getWordByUrduMeaning(String urdu) {
        WordDTO word = null;
        String query = "SELECT * FROM Words WHERE urdu_meaning = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, urdu);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                word = mapWord(rs);
                word.setExamples(exampleDAO.getExamplesByWordId(word.getId()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return word;
    }

    public List<WordDTO> getAllWords() {
        List<WordDTO> words = new ArrayList<>();
        String query = "SELECT * FROM Words";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                WordDTO word = mapWord(rs);
                word.setExamples(exampleDAO.getExamplesByWordId(word.getId()));
                words.add(word);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return words;
    }

    public List<WordDTO> getWordsByRoot(int rootId) {
        List<WordDTO> words = new ArrayList<>();
        String query = "SELECT * FROM Words WHERE root_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, rootId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                WordDTO word = mapWord(rs);
                word.setExamples(exampleDAO.getExamplesByWordId(word.getId()));
                words.add(word);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return words;
    }

    public boolean updateWord(WordDTO word) {
        String query = "UPDATE Words SET arabic_form=?, base_form=?, urdu_meaning=?, part_of_speech=?, root_id=?, pattern_id=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, word.getArabicForm());
            stmt.setString(2, word.getBaseForm());
            stmt.setString(3, word.getUrduMeaning());
            stmt.setString(4, word.getPartOfSpeech());
            stmt.setObject(5, word.getRootId() == 0 ? null : word.getRootId());
            stmt.setObject(6, word.getPatternId() == 0 ? null : word.getPatternId());
            stmt.setInt(7, word.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean deleteWord(int id) {

        String deletepattern = "DELETE FROM patterns WHERE word_id = ?";
        String deleteWord = "DELETE FROM words WHERE id = ?";

        try (Connection conn = DBConnection.getConnection()) {

            try (PreparedStatement stmt1 = conn.prepareStatement(deletepattern)) {
                stmt1.setInt(1, id);
                stmt1.executeUpdate();
            }

            try (PreparedStatement stmt2 = conn.prepareStatement(deleteWord)) {
                stmt2.setInt(1, id);
                return stmt2.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteWordByBaseForm(String baseForm) {
        String query = "DELETE FROM Words WHERE base_form = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, baseForm);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private WordDTO mapWord(ResultSet rs) throws SQLException {
        return new WordDTO(
                rs.getInt("pattern_id"),   // pid
                rs.getInt("id"),           // id
                rs.getString("arabic_form"),
                rs.getString("base_form"),
                rs.getString("urdu_meaning"),
                rs.getString("part_of_speech"),
                rs.getInt("root_id")
        );
    }


}

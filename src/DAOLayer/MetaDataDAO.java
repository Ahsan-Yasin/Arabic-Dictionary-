package DAOLayer;

import Config.Connector.DBConnection;
import DTOLayer.IMetaData;
import DTOLayer.NounMetaDataDTO;
import DTOLayer.VerbMetaDataDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MetaDataDAO {

    public void addMetaData(IMetaData metaData) {
        if (metaData instanceof NounMetaDataDTO) addNoun((NounMetaDataDTO) metaData);
        else if (metaData instanceof VerbMetaDataDTO) addVerb((VerbMetaDataDTO) metaData);
    }

    private void addNoun(NounMetaDataDTO noun) {
        String query = "INSERT INTO NounMetadata (word_id, gender, number, grammatical_case) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, noun.getWordId());
            stmt.setString(2, noun.getGender());
            stmt.setString(3, noun.getNumber());
            stmt.setString(4, noun.getGrammaticalCase());
            stmt.executeUpdate();

        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void addVerb(VerbMetaDataDTO verb) {
        String query = "INSERT INTO VerbMetadata (word_id, verb_form, tense, transitivity) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, verb.getWordId());
            stmt.setString(2, verb.getVerbForm());
            stmt.setString(3, verb.getTense());
            stmt.setString(4, verb.getTransitivity());
            stmt.executeUpdate();

        } catch (SQLException e) { e.printStackTrace(); }
    }

    public NounMetaDataDTO getNounMetaDataByWordId(int wordId) {
        String query = "SELECT * FROM NounMetadata WHERE word_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, wordId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new NounMetaDataDTO(
                        rs.getInt("word_id"),
                        rs.getString("gender"),
                        rs.getString("number"),
                        rs.getString("grammatical_case")
                );
            }

        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public VerbMetaDataDTO getVerbMetaDataByWordId(int wordId) {
        String query = "SELECT * FROM VerbMetadata WHERE word_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, wordId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new VerbMetaDataDTO(
                        rs.getInt("word_id"),
                        rs.getString("verb_form"),
                        rs.getString("tense"),
                        rs.getString("transitivity")
                );
            }

        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean updateNounMetaData(NounMetaDataDTO noun) {
        String query = "UPDATE NounMetadata SET gender=?, number=?, grammatical_case=? WHERE word_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, noun.getGender());
            stmt.setString(2, noun.getNumber());
            stmt.setString(3, noun.getGrammaticalCase());
            stmt.setInt(4, noun.getWordId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean updateVerbMetaData(VerbMetaDataDTO verb) {
        String query = "UPDATE VerbMetadata SET verb_form=?, tense=?, transitivity=? WHERE word_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, verb.getVerbForm());
            stmt.setString(2, verb.getTense());
            stmt.setString(3, verb.getTransitivity());
            stmt.setInt(4, verb.getWordId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }



    public List<NounMetaDataDTO> getAllNounMetaData() {
        List<NounMetaDataDTO> list = new ArrayList<>();
        String query = "SELECT * FROM NounMetadata";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new NounMetaDataDTO(
                        rs.getInt("word_id"),
                        rs.getString("gender"),
                        rs.getString("number"),
                        rs.getString("grammatical_case")
                ));
            }

        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<VerbMetaDataDTO> getAllVerbMetaData() {
        List<VerbMetaDataDTO> list = new ArrayList<>();
        String query = "SELECT * FROM VerbMetadata";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new VerbMetaDataDTO(
                        rs.getInt("word_id"),
                        rs.getString("verb_form"),
                        rs.getString("tense"),
                        rs.getString("transitivity")
                ));
            }

        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }


    public boolean DeleteMetaDataVerb(int id) {
        String query = "DELETE FROM VerbMetadata WHERE word_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true  ;
        } catch (SQLException e) { e.printStackTrace(); }
        return false ;
    }

    public boolean deleteMetaDataNoun(int id) {
        String query = "DELETE FROM NounMetadata WHERE word_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true  ;
        } catch (SQLException e) { e.printStackTrace(); }
        return false ;
    }


    // ------------------ Added Safe Methods ------------------

    // Check if a word exists in the words table
    public boolean wordExists(int wordId) {
        String query = "SELECT id FROM words WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, wordId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Safe version of addNoun
    public boolean safeAddNoun(NounMetaDataDTO noun) {
        if (!wordExists(noun.getWordId())) {
            System.err.println("Cannot add noun metadata: word_id " + noun.getWordId() + " does not exist!");
            return false;
        }
        addNoun(noun);
        return true;
    }

    // Safe version of addVerb
    public boolean safeAddVerb(VerbMetaDataDTO verb) {
        if (!wordExists(verb.getWordId())) {
            System.err.println("Cannot add verb metadata: word_id " + verb.getWordId() + " does not exist!");
            return false;
        }
        addVerb(verb);
        return true;
    }

    // Safe delete for nouns
    public boolean safeDeleteNoun(int wordId) {
        String query = "DELETE FROM NounMetadata WHERE word_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, wordId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Safe delete for verbs
    public boolean safeDeleteVerb(int wordId) {
        String query = "DELETE FROM VerbMetadata WHERE word_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, wordId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

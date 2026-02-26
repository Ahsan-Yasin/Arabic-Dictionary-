package DAOLayer;

import Config.Connector.DBConnection;
import DTOLayer.SegmentationDTO;
import DTOLayer.WordDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SegmentationDAO {

    // Add Segmentation
    public int addSegmentation(SegmentationDTO seg) {
        String query = "INSERT INTO Segmentation (word_id, prefix, stem, suffix, full_segmentation, source) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, seg.getWordId());
            stmt.setString(2, seg.getPrefix());
            stmt.setString(3, seg.getStem());
            stmt.setString(4, seg.getSuffix());
            stmt.setString(5, seg.getFullSegmentation());
            stmt.setString(6, seg.getSource());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Get Segmentation by ID
    public SegmentationDTO getSegmentationById(int id) {
        String query = "SELECT * FROM Segmentation WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return mapSegmentation(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all segmentations for a word
    public List<SegmentationDTO> getSegmentationsByWordId(int wordId) {
        List<SegmentationDTO> list = new ArrayList<>();
        String query = "SELECT * FROM Segmentation WHERE word_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, wordId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) list.add(mapSegmentation(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get all segmentations
    public List<SegmentationDTO> getAllSegmentations() {
        List<SegmentationDTO> list = new ArrayList<>();
        String query = "SELECT * FROM Segmentation";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) list.add(mapSegmentation(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Update
    public boolean updateSegmentation(SegmentationDTO seg) {
        String query = "UPDATE Segmentation SET prefix=?, stem=?, suffix=?, full_segmentation=?, source=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, seg.getPrefix());
            stmt.setString(2, seg.getStem());
            stmt.setString(3, seg.getSuffix());
            stmt.setString(4, seg.getFullSegmentation());
            stmt.setString(5, seg.getSource());
            stmt.setInt(6, seg.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete
    public boolean deleteSegmentation(int id) {
        String query = "DELETE FROM Segmentation WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // helper
    private SegmentationDTO mapSegmentation(ResultSet rs) throws SQLException {
        return new SegmentationDTO(
                rs.getInt("id"),
                rs.getInt("word_id"),
                rs.getString("prefix"),
                rs.getString("stem"),
                rs.getString("suffix"),
                rs.getString("full_segmentation"),
                rs.getString("source")
        );
    }
    public List<WordDTO> getWordsBySegment(String segmentPart) {
        List<WordDTO> words = new ArrayList<>();


        String query = "SELECT w.* FROM words w " +
                "JOIN Segmentation s ON w.id = s.word_id " +
                "WHERE s.prefix = ? OR s.stem = ? OR s.suffix = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, segmentPart);
            stmt.setString(2, segmentPart);
            stmt.setString(3, segmentPart);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                words.add(new WordDTO(
                        rs.getInt("pattern_id"),
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

package DAOLayer;

import Config.Connector.DBConnection;
import DTOLayer.RootDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RootDAO {

    public int addRoot(  String rootLetters   ) {
        String query = "INSERT INTO Roots ( root_letters) VALUES ( ? )";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, rootLetters);
            //stmt.setInt(2, id );
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
                return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public RootDTO getRoot(int id) {
        String query = "SELECT * FROM Roots WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return new RootDTO(rs.getInt("id"), rs.getString("root_letters"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public int getRootId(String s) {
        String query = "SELECT id FROM roots WHERE root_letters = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, s);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // not found
    }
    public List<RootDTO> getRootsByText(String text) {
        List<RootDTO> roots = new ArrayList<>();
        String query = "SELECT * FROM Roots WHERE root_letters LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + text + "%");  // partial match
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                roots.add(new RootDTO(rs.getInt("id"), rs.getString("root_letters")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roots;
    }


    public List<RootDTO> getAllRoots() {
        List<RootDTO> roots = new ArrayList<>();
        String query = "SELECT * FROM Roots";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                roots.add(new RootDTO(rs.getInt("id"), rs.getString("root_letters")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roots;
    }

    public boolean updateRoot(int id, String newLetters) {
        String query = "UPDATE Roots SET root_letters = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newLetters);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteRoot(int id) {
        String query = "DELETE FROM Roots WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

package student;

import rs.ac.bg.etf.sab.operations.UsersOperations;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class jo210476_UsersOperations implements UsersOperations {

    @Override
    public Integer addUser(String s) {
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("insert into Korisnik(KorisnickoIme) values (?);", Statement.RETURN_GENERATED_KEYS);
        ){
            stmt.setString(1, s);
            if(stmt.executeUpdate()>0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if(rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Integer updateUser(Integer integer, String s) {
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("update Korisnik set KorisnickoIme = ? where IdK = ?;");
        ){
            stmt.setString(1, s);
            stmt.setInt(2, integer);
            if(stmt.executeUpdate()>0) {
                return integer;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Integer removeUser(Integer integer) {
        Connection conn = DB.getInstance().getConnection();
        try {
            conn.setAutoCommit(false);

            PreparedStatement deleteClanListe = conn.prepareStatement("DELETE FROM ClanListe WHERE IdK = ?");
            deleteClanListe.setInt(1, integer);
            deleteClanListe.executeUpdate();

            PreparedStatement deleteOcene = conn.prepareStatement("DELETE FROM Ocena WHERE IdK = ?");
            deleteOcene.setInt(1, integer);
            deleteOcene.executeUpdate();

            PreparedStatement deleteKorisnik = conn.prepareStatement("DELETE FROM Korisnik WHERE IdK = ?");
            deleteKorisnik.setInt(1, integer);
            if(deleteKorisnik.executeUpdate() > 0) {
                conn.commit();
                return integer;
            }

            conn.rollback();
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            System.err.println(e.getMessage());
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
        }
        return null;
    }

    @Override
    public boolean doesUserExist(String s) {
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("select 1 from Korisnik where KorisnickoIme = ?;");
        ){
            stmt.setString(1, s);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    @Override
    public Integer getUserId(String s) {
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("select IdK from Korisnik where KorisnickoIme = ?;");
        ){
            stmt.setString(1, s);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return rs.getInt("IdK");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Integer> getAllUserIds() {
        List<Integer> list = new ArrayList<>();
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("select IdK from Korisnik;");
        ){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                list.add(rs.getInt("IdK"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    @Override
    public List<Integer> getRecommendedMoviesFromFavoriteGenres(Integer integer) {
        String sql = "{call FP_Preporuci_Filmove(?)}";
        List<Integer> list = new ArrayList<>();
        try(
                Connection conn = DB.getInstance().getConnection();
                CallableStatement stmt = conn.prepareCall(sql);
        ){
            stmt.setInt(1, integer);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                list.add(rs.getInt("IdF"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    @Override
    public Integer getRewards(Integer integer) {
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("select BrNagrada from Korisnik where IdK = ?;");
        ){
            stmt.setInt(1, integer);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return rs.getInt("BrNagrada");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }

    @Override
    public List<String> getThematicSpecializations(Integer integer) {
        List<String> list = new ArrayList<>();
        try (
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT Opis FROM FN_Specijalizacija(?)");
        ) {
            stmt.setInt(1, integer);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("Opis"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return list;
    }

    @Override
    public String getUserDescription(Integer integer) {
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("select Opis from Korisnik where IdK = ?;");
        ){
            stmt.setInt(1, integer);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return rs.getString("Opis");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return "undefined";
    }
}

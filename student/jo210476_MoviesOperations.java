package student;

import rs.ac.bg.etf.sab.operations.MoviesOperations;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class jo210476_MoviesOperations implements MoviesOperations {
    @Override
    public Integer addMovie(String s, Integer integer, String s1) {
        Connection conn = DB.getInstance().getConnection();
        try{
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Film(Naslov, Reziser) VALUES (?, ?)",Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, s);
            stmt.setString(2, s1);
            if(stmt.executeUpdate() > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if(rs.next()) {
                    int idF = rs.getInt(1);
                    PreparedStatement stmt1 = conn.prepareStatement("INSERT INTO Pripada(IdF, IdZ) VALUES (?, ?)");
                    stmt1.setInt(1, idF);
                    stmt1.setInt(2, integer);
                    if(stmt1.executeUpdate() > 0) {
                        conn.commit();
                        return idF;
                    }
                }
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
    public Integer updateMovieTitle(Integer integer, String s) {
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("update Film set Naslov = ? where IdF = ?;");
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
    public Integer addGenreToMovie(Integer integer, Integer integer1) {
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO Pripada(IdF, IdZ) VALUES (?, ?)");
        ){
            stmt.setInt(1, integer);
            stmt.setInt(2, integer1);
            if(stmt.executeUpdate()>0) {
                return integer;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Integer removeGenreFromMovie(Integer integer, Integer integer1) {
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement deletePripada = conn.prepareStatement("DELETE FROM Pripada WHERE IdF = ? AND IdZ = ?");
        ){
            deletePripada.setInt(1, integer);
            deletePripada.setInt(2, integer1);

            if(deletePripada.executeUpdate() > 0) {
                return integer;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Integer updateMovieDirector(Integer integer, String s) {
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("update Film set Reziser = ? where IdF = ?;");
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
    public Integer removeMovie(Integer integer) {
        Connection conn = DB.getInstance().getConnection();
        try {
            conn.setAutoCommit(false);

            PreparedStatement deletePripada = conn.prepareStatement("DELETE FROM Pripada WHERE IdF = ?");
            deletePripada.setInt(1, integer);
            deletePripada.executeUpdate();

            PreparedStatement deleteImaOznaku = conn.prepareStatement("DELETE FROM ImaOznaku WHERE IdF = ?");
            deleteImaOznaku.setInt(1, integer);
            deleteImaOznaku.executeUpdate();

            PreparedStatement deleteClanListe = conn.prepareStatement("DELETE FROM ClanListe WHERE IdF = ?");
            deleteClanListe.setInt(1, integer);
            deleteClanListe.executeUpdate();

            PreparedStatement deleteOcene = conn.prepareStatement("DELETE FROM Ocena WHERE IdF = ?");
            deleteOcene.setInt(1, integer);
            deleteOcene.executeUpdate();

            PreparedStatement deleteFilm = conn.prepareStatement("DELETE FROM Film WHERE IdF = ?");
            deleteFilm.setInt(1, integer);
            if(deleteFilm.executeUpdate() > 0) {
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
    public List<Integer> getMovieIds(String s, String s1) {
        List<Integer> list = new ArrayList<>();
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("select IdF from Film where Naslov = ? and Reziser = ? ;");
        ){
            stmt.setString(1, s);
            stmt.setString(2, s1);
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
    public List<Integer> getAllMovieIds() {
        List<Integer> list = new ArrayList<>();
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("select IdF from Film;");
        ){
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
    public List<Integer> getMovieIdsByGenre(Integer integer) {
        List<Integer> list = new ArrayList<>();
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("select IdF from Pripada where IdZ = ?;");
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
    public List<Integer> getGenreIdsForMovie(Integer integer) {
        List<Integer> list = new ArrayList<>();
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("select IdZ from Pripada where IdF = ?;");
        ){
            stmt.setInt(1, integer);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                list.add(rs.getInt("IdZ"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    @Override
    public List<Integer> getMovieIdsByDirector(String s) {
        List<Integer> list = new ArrayList<>();
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("select IdF from Film where Reziser = ?;");
        ){
            stmt.setString(1, s);
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
    public String getMovieTrend(Integer integer) {
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("select Status from Film where IdF = ?;");
        ){
            stmt.setInt(1, integer);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return rs.getString("Status");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}

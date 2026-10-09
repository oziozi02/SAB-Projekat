package student;

import rs.ac.bg.etf.sab.operations.WatchlistsOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class jo210476_WatchlistsOperations implements WatchlistsOperations {
    @Override
    public boolean addMovieToWatchlist(Integer integer, Integer integer1) {
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("insert into ClanListe(IdK,IdF) values (?,?);");
        ){
            stmt.setInt(1, integer);
            stmt.setInt(2, integer1);
            if(stmt.executeUpdate()>0) return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean removeMovieFromWatchlist(Integer integer, Integer integer1) {
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("delete from ClanListe where IdK = ? and IdF = ?;");
        ){
            stmt.setInt(1, integer);
            stmt.setInt(2, integer1);
            if(stmt.executeUpdate()>0) return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean isMovieInWatchlist(Integer integer, Integer integer1) {
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("select 1 from ClanListe where IdK = ? and IdF = ?;");
        ){
            stmt.setInt(1, integer);
            stmt.setInt(2, integer1);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    @Override
    public List<Integer> getMoviesInWatchlist(Integer integer) {
        List<Integer> list = new ArrayList<>();
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("select IdF from ClanListe where IdK = ?;");
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
    public List<Integer> getUsersWithMovieInWatchlist(Integer integer) {
        List<Integer> list = new ArrayList<>();
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("select IdK from ClanListe where IdF = ?;");
        ){
            stmt.setInt(1, integer);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                list.add(rs.getInt("IdK"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }
}

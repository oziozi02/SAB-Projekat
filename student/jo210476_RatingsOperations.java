package student;

import rs.ac.bg.etf.sab.operations.RatingsOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class jo210476_RatingsOperations implements RatingsOperations {

    @Override
    public boolean addRating(Integer integer, Integer integer1, Integer integer2) {
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("insert into Ocena(IdK,IdF,Ocena) values (?,?,?);");
        ){
            stmt.setInt(1, integer);
            stmt.setInt(2, integer1);
            stmt.setInt(3, integer2);
            if(stmt.executeUpdate()>0) return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateRating(Integer integer, Integer integer1, Integer integer2) {
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("update Ocena set Ocena = ? where IdK = ? and IdF = ?;");
        ){
            stmt.setInt(1, integer2);
            stmt.setInt(2, integer);
            stmt.setInt(3, integer1);
            if(stmt.executeUpdate()>0) return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean removeRating(Integer integer, Integer integer1) {
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("delete from Ocena where IdK = ? and IdF = ?;");
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
    public Integer getRating(Integer integer, Integer integer1) {
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("select Ocena from Ocena where IdK = ? and IdF = ?;");
        ){
            stmt.setInt(1, integer);
            stmt.setInt(2, integer1);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getInt("Ocena");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Integer> getRatedMoviesByUser(Integer integer) {
        List<Integer> list = new ArrayList<>();
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("select IdF from Ocena where IdK = ?;");
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
    public List<Integer> getUsersWhoRatedMovie(Integer integer) {
        List<Integer> list = new ArrayList<>();
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("select IdK from Ocena where IdF = ?;");
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

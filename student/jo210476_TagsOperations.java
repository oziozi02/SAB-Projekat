package student;

import rs.ac.bg.etf.sab.operations.TagsOperations;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class jo210476_TagsOperations implements TagsOperations {

    @Override
    public Integer addTag(Integer integer, String s) {
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("insert into ImaOznaku(IdF,Opis) values (?,?);");
        ){
            stmt.setInt(1, integer);
            stmt.setString(2, s);
            if(stmt.executeUpdate()>0) return integer;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Integer removeTag(Integer integer, String s) {
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("delete from ImaOznaku where IdF = ? and Opis = ?;");
        ){
            stmt.setInt(1, integer);
            stmt.setString(2, s);
            if(stmt.executeUpdate()>0) return integer;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public int removeAllTagsForMovie(Integer integer) {
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("delete from ImaOznaku where IdF = ?;");
        ){
            stmt.setInt(1, integer);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }

    @Override
    public boolean hasTag(Integer integer, String s) {
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("select 1 from ImaOznaku where IdF = ? and Opis = ?;");
        ){
            stmt.setInt(1, integer);
            stmt.setString(2, s);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    @Override
    public List<String> getTagsForMovie(Integer integer) {
        List<String> list = new ArrayList<>();
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("select Opis from ImaOznaku where IdF = ?;");
        ){
            stmt.setInt(1, integer);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                list.add(rs.getString("Opis"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    @Override
    public List<Integer> getMovieIdsByTag(String s) {
        List<Integer> list = new ArrayList<>();
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("select IdF from ImaOznaku where Opis = ?;");
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
    public List<String> getAllTags() {
        List<String> list = new ArrayList<>();
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("select distinct Opis from ImaOznaku;");
        ){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                list.add(rs.getString("Opis"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }
}

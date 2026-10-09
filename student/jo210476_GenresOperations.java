package student;

import rs.ac.bg.etf.sab.operations.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class jo210476_GenresOperations implements GenresOperations {

    @Override
    public Integer addGenre(String s) {
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("insert into Zanr(Naziv) values (?);", Statement.RETURN_GENERATED_KEYS);
        ){
            stmt.setString(1, s);
            if(stmt.executeUpdate()>0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Integer updateGenre(Integer integer, String s) {
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("update Zanr set Naziv = ? where IdZ = ?;");
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
    public Integer removeGenre(Integer integer) {
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement deletePripada = conn.prepareStatement("DELETE FROM Pripada WHERE IdZ = ?");
                PreparedStatement deleteZanr = conn.prepareStatement("DELETE FROM Zanr WHERE IdZ = ?");
        ){
            deletePripada.setInt(1, integer);
            deletePripada.executeUpdate();

            deleteZanr.setInt(1, integer);
            if(deleteZanr.executeUpdate() > 0) {
                return integer;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean doesGenreExist(String s) {
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("select 1 from Zanr where Naziv = ?;");
        ){
            stmt.setString(1, s);
            ResultSet rs = stmt.executeQuery();
            return (rs.next());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    @Override
    public Integer getGenreId(String s) {
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("select IdZ from Zanr where Naziv = ?;");
        ){
            stmt.setString(1, s);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return rs.getInt("IdZ");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Integer> getAllGenreIds() {
        List<Integer> list = new ArrayList<>();
        try(
                Connection conn = DB.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement("select IdZ from Zanr;");
        ){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                list.add(rs.getInt("IdZ"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }
}

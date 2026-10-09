package student;

import rs.ac.bg.etf.sab.operations.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class jo210476_GeneralOperations implements GeneralOperations {

    @Override
    public void eraseAll() {
        try(
            Connection conn = DB.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ){
            stmt.execute("DELETE FROM ImaOznaku");
            stmt.execute("DELETE FROM ClanListe");
            stmt.execute("DELETE FROM Ocena");
            stmt.execute("DELETE FROM Pripada");
            stmt.execute("DELETE FROM Film");
            stmt.execute("DELETE FROM Zanr");
            stmt.execute("DELETE FROM Korisnik");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}

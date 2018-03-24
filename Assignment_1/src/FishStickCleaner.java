import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/* File: FishStickCleaner.java
 * Author: John Devan Ferguson
 * Date: Jan, 24th 2018
 * Description: Objects to clean fish sticks before eating them for dinner. Hah, no, just for cleaning the database.
 * 						Would like some fish sticks though.
 */
public class FishStickCleaner {
	
	private final String connectionString = "jdbc:mysql://localhost/assignment1";
	private final String username = "assignment1";
	private final String password = "password";

	private Connection con = null;
	
	public FishStickCleaner(){
		openConnection();
	}

	public void deleteAllFishSticks() {
		PreparedStatement pstmt = null;
		try{
			if(con == null || con.isClosed()) {
				System.out.println("Cannot delete records, no connection or connection closed");
			}

			pstmt = con.prepareStatement(
					"TRUNCATE TABLE fishsticks");
			pstmt.executeUpdate();
			closeConnection();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			try{ if(pstmt != null){ pstmt.close(); }}
			catch(SQLException ex){System.out.println(ex.getMessage());}
		}
	}
	
	private void openConnection(){
		try{
			if(con != null){
				System.out.println("Cannot create new connection, one exists already");
			}
			else{
				con = DriverManager.getConnection(connectionString, username, password);
			}
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
	private void closeConnection() {
		try{ if(con != null){ con.close(); }}
		catch(SQLException ex){System.out.println(ex.getMessage());}
	}
	
}

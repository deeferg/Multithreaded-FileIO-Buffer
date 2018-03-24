import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/* File: FishStick.java
 * Author: John Devan Ferguson
 * Date: Jan, 24th 2018
 * Description: Makes sure that code is prepared for the database, need to get the data from the buffer.
 *				Gets data from the buffer using the read index
 */

public class Consumer implements Runnable{
	

	private Connection con = null;
	private final String connectionString = "jdbc:mysql://localhost/assignment1";
	private final String username = "assignment1";
	private final String password = "password";
	private final Buffer buffer;
	private int recordsInserted = 0;
	
	public Consumer(Buffer buffer){
		this.buffer = buffer;
	}

	@Override
	public void run() {
		openConnection();
		
		try{
			FishStick fishstick = null;//Begin with null fishStick object to modify with blockingGet
			do{
				fishstick = buffer.blockingGet();//Get fishStick from buffer
				if(fishstick.getRecordNumber() == -1){
					buffer.blockingPut(fishstick);
					break;
				}
				insertFishStick(fishstick);//Don't insert dummy into database
				recordsInserted++;
				if(recordsInserted % 100 == 0) {
					System.out.printf("%d records inserted%n", recordsInserted);
				}
			
			}while(!fishstick.getLastRecord());
		}
		catch(InterruptedException ex){
			Thread.currentThread().interrupt();
		}
		
		closeConnection();
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
	
	public int getRecordsInserted(){
		
		return recordsInserted;
	}

	private void closeConnection() {
		try{ if(con != null){ con.close(); }}
		catch(SQLException ex){System.out.println(ex.getMessage());}
	}
	
	public void insertFishStick(FishStick fishstick) {
		PreparedStatement pstmt = null;
		try{
			if(con == null || con.isClosed()) {
				System.out.println("Cannot insert records, no connection or connection closed");
			}


			pstmt = con.prepareStatement(
					"INSERT INTO FishSticks (recordnumber, omega, lambda, uuid) " +
					"VALUES(?, ?, ?, ?)");
			pstmt.setInt(   1, fishstick.getRecordNumber());
			pstmt.setString(2, fishstick.getOmega());
			pstmt.setString(3, fishstick.getLambda());	
			pstmt.setString(4, fishstick.getUUID());
			pstmt.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			try{ if(pstmt != null){ pstmt.close(); }}
			catch(SQLException ex){System.out.println(ex.getMessage());}
		}
	}

	

}

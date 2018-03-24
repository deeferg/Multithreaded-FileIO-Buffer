/* File: Buffer.java
 * Author: John Devan Ferguson
 * Date: Jan, 24th 2018
 * Description: Parses the data coming from the CSV file for the database
 */

public interface Buffer {

	public void blockingPut(FishStick fishstick) throws InterruptedException;
	
	public FishStick blockingGet()throws InterruptedException;
	
}

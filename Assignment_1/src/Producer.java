import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/* File: Producer.java
 * Author: John Devan Ferguson
 * Date: Jan, 24th 2018
 * Description: Prepares and arranges the information for the buffer
 *				Produces data into the buffer using the Write Index
 */

public class Producer implements Runnable{
	
	private Scanner fishstickScanner = null;
	private Buffer buffer;
	private int recordsRead;
	
		
	public Producer(Buffer buffer){
		openFile();
		this.buffer = buffer;
	}

	@Override
	public void run() {
		try{
			while(fishstickScanner.hasNext()){//Run while there is another value to be inserted from scanner
				FishStick fishstick = getFishStick();
				buffer.blockingPut(fishstick);//Insert new fishStick into database
				recordsRead++;//Increase records read value
				if(recordsRead % 100 == 0) {
					System.out.printf("%d records read%n", recordsRead);
			}
			}
			FishStick fishstickDummy = new FishStick();//Once scanner doesn't have next value, enter final dummy value into the end of buffer
			fishstickDummy.setLastRecord(true);
			fishstickDummy.setRecordNumber(-1);
			buffer.blockingPut(fishstickDummy);
		}
		catch(InterruptedException ex){
			Thread.currentThread().interrupt();
		}
		closeFile();
	}
	
	public int getRecordsRead(){
		
		return recordsRead;
	}
	
	private void openFile() {
		try {
			fishstickScanner = new Scanner(new FileReader(new File("DataSet18W_100000.csv")));
		}
		catch(IOException ex){
			System.out.println("Problem opening file: "
					+ ex.getMessage());
		}
	}

	private void closeFile() {
		try {
			if(fishstickScanner != null) {fishstickScanner.close();}
		}
		catch(Exception ex) {
			System.out.println("Problem closing file: "
					+ ex.getMessage());
		}
	}
	
	private FishStick getFishStick(){
	
			String line = fishstickScanner.nextLine(); // read raw data
			String[] fields = line.split(","); // split on delimiter
			FishStick fishstick = new FishStick();
			fishstick.setRecordNumber(Integer.parseInt(fields[0]));
			fishstick.setOmega(fields[1]);
			fishstick.setLambda(fields[2]);
			fishstick.setUUID(fields[3]);
			
		return fishstick;
	}
	
}

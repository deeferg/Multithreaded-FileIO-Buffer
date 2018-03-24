/* File: CircularBuffer.java
 * Author: John Devan Ferguson
 * Date: Jan, 24th 2018
 * Description: Write and read index for a circular buffer, one after the other.
 */

//Write and read index
public class CircularBuffer implements Buffer{
	
	private int MAX_BUFFER_SIZE = 100;
	private FishStick[] buffer = new FishStick[MAX_BUFFER_SIZE];
	public int writeIndex = 0;//buffer[writeIndex] will take in the fishStick. Must be incremented after run
	public int readIndex = 0;//buffer[readIndex] will add fishstick to database. Must be incremented after run
	public int availability = 0;//Must check that buffer is available(?) Must be decremented after readIndex is run. In producer, and availability == maxBufferSize, you must give the command wait();

	@Override
	public synchronized void blockingPut(FishStick fishstick) throws InterruptedException{
		while(availability == MAX_BUFFER_SIZE){
		//	System.out.println("Buffer is full. Producer waits.%n");
			wait();
		}
		
		buffer[writeIndex] = fishstick;//Set new buffer value
		
		writeIndex = (writeIndex + 1) % MAX_BUFFER_SIZE;
		++availability;
		notifyAll();
		
		
	}

	@Override
	public synchronized FishStick blockingGet() throws InterruptedException{
		
		while(availability == 0){
			wait();//wait until a buffer cell is filled
		}
			
		FishStick fishstick = buffer[readIndex];
		readIndex = (readIndex + 1) % MAX_BUFFER_SIZE;
		--availability;
		notifyAll();
		return fishstick;
		
		
		
	}
	


}

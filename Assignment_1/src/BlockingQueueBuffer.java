/* File: BlockingQueueBuffer.java
 * Author: John Devan Ferguson
 * Date: Jan, 24th 2018
 * Description: Blocking Queue implementing the buffer interface to thread for the Producer and Consumer. 
 * 					Will halt Consumer til Producer is ready..
 */

import java.util.concurrent.ArrayBlockingQueue;

public class BlockingQueueBuffer implements Buffer{
	
	private final ArrayBlockingQueue<FishStick> buffer;
	
	public BlockingQueueBuffer(){
		buffer = new ArrayBlockingQueue<FishStick>(1);
	}

	@Override
	public void blockingPut(FishStick fishstick)throws InterruptedException {
		buffer.put(fishstick);
	}

	@Override
	public FishStick blockingGet() throws InterruptedException{
		FishStick fishStick = buffer.take();
		//return buffer.take();
		return fishStick;
	}


}

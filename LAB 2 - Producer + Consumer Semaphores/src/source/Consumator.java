package source;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Consumator implements Runnable{

	static Random rand = new Random(System.currentTimeMillis());
	private static Queue<Integer> q;
	private main ProducatorConsumator;
	private Semaphore semFull;
	private Semaphore semFree;

	
	Consumator(Queue<Integer> q, main ProducatorConsumator, Semaphore semFull, Semaphore semFree){
		this.semFull = semFull;
		this.semFree = semFree;
		this.q = q;
		this.ProducatorConsumator = ProducatorConsumator;
	}
	
	private static synchronized void removeFromQueue(String name){
		    
	    	int numar = q.remove();
	        System.out.println("Am scos: " + numar + ", threadul: " + name);
	    
	}

	
	@Override
	public void run() {
    	
    	while(true){
    		
    		try{
    			
    			synchronized(q){
    				
        			while(q.size() == 0){
        			
        				synchronized(ProducatorConsumator){
        					ProducatorConsumator.wait();
        				}
        			}
    			}
    			
    			semFull.release();
    			
    			removeFromQueue("consumator");
    			
    			semFree.acquire();
    			
    			Thread.sleep(1000);
    			
    			synchronized(ProducatorConsumator){
    				ProducatorConsumator.notify();
    			}
    			
    			
    			
    		} catch (Exception e){
    			
    			e.printStackTrace();
    			
    		}
    	
    	}
   
   }
}

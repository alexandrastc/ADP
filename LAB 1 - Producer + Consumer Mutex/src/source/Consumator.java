package source;

import java.util.Queue;
import java.util.Random;

public class Consumator implements Runnable{

	static Random rand = new Random(System.currentTimeMillis());
	private static Queue<Integer> q;
	private main ProducatorConsumator;

	
	Consumator(Queue<Integer> q, main ProducatorConsumator){
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
        					main.ProducatorConsumator.wait();
        				}
        			}
    			}
    			
    			removeFromQueue("consumator");
    			
    			Thread.sleep(1000);
    			
    			synchronized(ProducatorConsumator){
    				main.ProducatorConsumator.notify();
    			}
    			
    			
    			
    		} catch (Exception e){
    			
    			e.printStackTrace();
    			
    		}
    	
    	}
   
   }
}

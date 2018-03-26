package source;

import java.util.Queue;
import java.util.Random;

public class Consumator implements Runnable{

	private static Queue<Integer> q;
	private main ProducatorConsumator;
	private Object condProd;
	private Object condCons;

	
	Consumator(Queue<Integer> q, main ProducatorConsumator, Object condProd, Object condCons){
		this.q = q;
		this.ProducatorConsumator = ProducatorConsumator;
		this.condProd = condProd;
		this.condCons = condCons;
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
        			
        				synchronized(main.condCons){
        					main.condCons.wait();
        				}
        			}
    			}
    			
    			removeFromQueue("consumator");
    			
    			Thread.sleep(1000);
    			
    			synchronized(main.condProd){
    				main.condProd.notify();
    			}
    			
    			
    			
    		} catch (Exception e){
    			
    			e.printStackTrace();
    			
    		}
    	
    	}
   
   }
}

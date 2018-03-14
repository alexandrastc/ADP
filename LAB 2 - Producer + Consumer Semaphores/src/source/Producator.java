package source;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Producator implements Runnable{

	static Random rand = new Random(System.currentTimeMillis());
	private static Queue<Integer> q;
	private main ProducatorConsumator;
	private int MAX_SIZE;
	private Semaphore semFull;
	private Semaphore semFree;
	
	Producator(Queue<Integer> q, main ProducatorConsumator, int MAX_SIZE, Semaphore semFull, Semaphore semFree){
		this.q = q;
		this.semFull = semFull;
		this.semFree = semFree;
		this.ProducatorConsumator = ProducatorConsumator;
		this.MAX_SIZE = MAX_SIZE;
	}
	
	private static synchronized void addToQueue(String name){
    	
    	int numar = rand.nextInt(100) + 1;
        q.add(numar);
        System.out.println("Am adaugat: " + numar + ", threadul: " + name);
    
    }
	
	@Override
	public void run() {
    	
    	while(true){

    		try{
    			
    			synchronized(q){
    				
        			while(q.size() == MAX_SIZE){
        			
        				synchronized(ProducatorConsumator){
        					ProducatorConsumator.wait();
        				}
        			}
    			}
    			
    			semFree.release();

    			addToQueue("producator");
    			
    			semFull.acquire();
    			
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

package source;

import java.util.Queue;
import java.util.Random;

public class Producator implements Runnable{

	static Random rand = new Random(System.currentTimeMillis());
	private static Queue<Integer> q;
	private main ProducatorConsumator;
	private int MAX_SIZE;	
	
	private Object condProd;
	private Object condCons;
	
	Producator(Queue<Integer> q, main ProducatorConsumator, int MAX_SIZE, Object condProd, Object condCons){
		this.q = q;
		this.ProducatorConsumator = ProducatorConsumator;
		this.MAX_SIZE = MAX_SIZE;
		this.condProd = condProd;
		this.condCons = condCons;
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
        			
        				synchronized(main.condProd){
        					main.condProd.wait();
        				}
        			}
    			}
    			
    			addToQueue("producator");
    			
    			Thread.sleep(1000);
    			
    			synchronized(main.condCons){
    				main.condCons.notify();
    			}
    			
    			
    			
    		} catch (Exception e){
    			
    			e.printStackTrace();
    			
    		}
    	
    	}
    }

}

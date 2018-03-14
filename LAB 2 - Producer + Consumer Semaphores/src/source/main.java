package source;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class main {
	
	public static main ProducatorConsumator = null;
	
    private static Queue<Integer> q = new LinkedList<Integer>();
    
    static Random rand = new Random(System.currentTimeMillis());
    
    static Semaphore semFree, semFull;
    
    static private int MAX_SIZE = 10;
    
    public static void main(String[] args) {
    	
    	ProducatorConsumator = new main();
    
    }
    
    main(){
    	
    	semFree = new Semaphore(MAX_SIZE);
    	semFull = new Semaphore(MAX_SIZE);
    	
    	Producator producator = new Producator(q,this,MAX_SIZE, semFull, semFree);
    	Consumator consumator = new Consumator(q,this, semFull, semFree);
    	
    	Producator producator2 = new Producator(q,this,MAX_SIZE, semFull, semFree);
    	Consumator consumator2 = new Consumator(q,this, semFull, semFree);
    	
    	new Thread(producator).start();
    	new Thread(consumator).start();
    	
    	new Thread(producator2).start();
    	new Thread(consumator2).start();
      
    }

} 
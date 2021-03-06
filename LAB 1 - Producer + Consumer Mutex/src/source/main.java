package source;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class main {
	
	public static main ProducatorConsumator = null;
	
    private static Queue<Integer> q = new LinkedList<Integer>();
    
    static Random rand = new Random(System.currentTimeMillis());
    
    static private int MAX_SIZE = 10;
    
    public static void main(String[] args) {
    	
    	ProducatorConsumator = new main();
    
    }
    
    main(){
    	
    	Producator producator = new Producator(q, this, MAX_SIZE);
    	Consumator consumator = new Consumator(q, this);
        new Thread(producator).start();
        new Thread(consumator).start();
    
    }

} 
import java.util.LinkedList;
import java.util.*;

/**
 * @author vivi3
 * Represents a router in the network, which is ultimately a queue
 */
public class Router extends ArrayList<Packet> { 
	
    ArrayList<Packet> queue; 
    int size = 0;
    
    /**
     */
    public Router() {
    	queue = new ArrayList<Packet>();
    }
    
    /**
     * adds a new Packet to the end of the router buffer.
     * @param p
     */
    public void enqueue(Packet p) {
    	super.add(p);
    	size++;
    }
    
    
    /**
     * Removes the first Packet in the router buffer.
     * @return the packet that is being removed
     */
    public Packet dequeue() {
    	size--;
    	return super.remove(0);
    }
    
    /**
     * Returns, but does not remove the first Packet in the router buffer.
     * @return first packet
     */
    public Packet peek() {
    	return super.get(0);
    }
    
    /** 
     * @return the number of Packets that are in the router buffer.
     */
    public int size() {
    	return size;
    }
    
    /** 
     * @return whether the router buffer is empty or not.
     */
    public boolean isEmpty() {
    	if (size == 0) 
    		return true;
    	else
    		return false;
    }
    
    /** 
     * @return a String representation of the router buffer
     */
    public String toString() {
    	String str = "{";
    	for (Packet p : this) {
        	str += p.toString();
    	}
    	
    	return str + "}";
    }
    
    /**
     * Find the router with the most free buffer space (contains least Packets)
     * @param intRouters
     * @return index of router
     */
    public int sendPacketTo(ArrayList<Router> intRouters) {
		
    	int min = intRouters.get(0).size;
    	int index = 0;

    	for (int i = 1; i < intRouters.size(); i++) {
    		if (intRouters.get(i).size < min) {
    			min = intRouters.get(i).size;
    		
    			index = i;
    		}	
    	}
    	return index;
    }
}

/* Vivian Lam
 * ID: 111549991
 * vivian.lam@stonybrook.edu
 * Homework 4
 * CSE214, R11 (Reed Gantz)
 */ 

/**
 * @author vivi3
 *  Represents a packet that will be sent through the network. 
 */
public class Packet {
	public static int packetCount = 0;
	
	public static int getPacketCount() {
		return packetCount;
	}

	public static void setPacketCount(int packetCount) {
		Packet.packetCount = packetCount;
	}

	int id;
	int packetSize;
	int timeArrive;
	int timeToDest;
	int initialTime;
	
	/**
	 * Empty constuctor for packet
	 */
	public Packet() {
		
	}
	
	/**
	 * Constructor that takes packetSize as a parameter
	 * @param packetSize
	 */
	public Packet(int packetSize) {
		this.id = ++packetCount;
		this.packetSize = packetSize;
		this.timeArrive = 1; 
		this.timeToDest = (packetSize / 100);
		
	}

	/**
	 * Accessor method for id
	 * @return id, a unique identifier for the packet. 
	 * This will be systematically determined by using packetCount.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Mutator method for id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Accessor method for packetSize
	 * @return the size of the packet being sent. 
	 */
	public int getPacketSize() {
		return packetSize;
	}
	
	/**
	 * Mutator method for packetSize
	 * @param packetSize
	 */
	public void setPacketSize(int packetSize) {
		this.packetSize = packetSize;
	}
	
	/**
	 * Accessor method for timeArrive
	 * @return the time this Packet is created should be recorded in this variable
	 */
	public int getTimeArrive() {
		return timeArrive;
	}
	
	/**
	 * Mutator method for timeArrive
	 * @param timeArrive 
	 */
	public void setTimeArrive(int timeArrive) {
		this.timeArrive = timeArrive;
	}
	
	/**
	 * Accesor method for timeToDest
	 * @return this variable contains the number of simulation units that it takes
	 *  for a packet to arrive at the destination router.
	 */
	public int getTimeToDest() {
		return timeToDest;
	}

	/**
	 * Mutator method for timeToDest
	 * @param timeToDest
	 */
	public void setTimeToDest(int timeToDest) {
		this.timeToDest = timeToDest;
	}
	
	/** 
	 * @return a string representation of packet 
	 */
	public String toString() {
		return "[" + id + ", " + timeArrive + ", " + timeToDest + "]"; 
	}
}

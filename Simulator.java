import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

/* Vivian Lam
 * ID: 111549991
 * vivian.lam@stonybrook.edu
 * Homework 4
 * CSE214, R11 (Reed Gantz)
 */ 

public class Simulator {
	
	Router dispatcher;
	Collection<Router> routers;
	int totalServiceTime;
	int totalPacketsArrived;
	int packetsDropped;
	double arrivalProb;
	int minPacketSize;
	int maxPacketSize;
	int numIntRouters;
	int maxBufferSize;
	int duration;
	int bandwidth;
	int packetCount = 1;
	int timeCount = 1;
	int dropCount = 0;				
	int countDest = 0;
	Router destination;
	ArrayList<Router> intRouters;


	
	public static final int MAX_PACKETS = 3;

	/**
	 * Runs the simulator as described in the specs
	 * @return calculate and return the average time each packet spends within the network.
	 * @throws Exception
	 */
	public double simulate() throws Exception {
		int count = 0;
		intRouters = new ArrayList<Router>();
		Router destination = new Router();
		
		for (int i = 0; i < numIntRouters; i++) {
			intRouters.add(i, new Router());
			if (i == numIntRouters) {
				break;
			}
		}

		while(duration != 0) {
			
			System.out.println("\nTime: " + timeCount);
			
			makePacket();
						
			while (!dispatcher.isEmpty()) {
				int d = dispatcher.sendPacketTo(intRouters);
				Packet p = dispatcher.dequeue();
				
				
				// NOTE
				if (intRouters.get(d).size + 1 <= maxBufferSize) {
					intRouters.get(d).enqueue(p);
					System.out.println("Packet " + p.id + " sent to Router " + (d+1));
				}
				
				else if (intRouters.get(d).size >= maxBufferSize) {
					for (Router r : intRouters) {
							if (r.size >= maxBufferSize) {
								System.out.println("Network is congested. Packet " + p.id + " is dropped" );
								dropCount++;
								break;
							}
						}
					}
				}		
					
			duration--;
			timeCount++;
			
			// NOTE
			int countAmountReach0 = 0;
			
			for (Router r : intRouters) {
				Packet p = new Packet();
				if (!r.isEmpty()) {
					if (r.get(0).getTimeToDest() == 0 && countAmountReach0 < bandwidth) {
						p = r.dequeue();
						destination.enqueue(p);
						if (!r.isEmpty()) {
						r.get(0).setTimeToDest(++(r.get(0).timeToDest));
						}
						totalServiceTime += ((timeCount - p.getTimeArrive()) -1);
						System.out.println("Packet " + p.getId() + " has successfully reached its destination: +"  
						+ ((timeCount - p.getTimeArrive()) - 1));
						countDest++;
						countAmountReach0++;
					//	r.get(0).setTimeToDest(++(r.get(0).timeToDest));
					}
				}
			}
			
			for (Router r : intRouters) {
				Packet p = new Packet();
				if (!r.isEmpty()) {
					if (r.get(0).getTimeToDest() != 0) {
						r.get(0).setTimeToDest(--(r.get(0).timeToDest));
					}
				
				if (r.get(0).getTimeToDest() == 0) {
					countAmountReach0++;
				} 
				
				if (countAmountReach0 <= bandwidth) {
					if (r.get(0).timeToDest == 0) {
						p = r.dequeue();
						destination.enqueue(p);
						totalServiceTime += ((timeCount - p.getTimeArrive()) - 1);
						System.out.println("Packet " + p.getId() + " has successfully reached its destination: +"  
						+ ((timeCount - p.getTimeArrive()) - 1));
						countDest++;
					}
				}
				}
			}
			
			for (int i = 0; i < intRouters.size(); i++) {
				System.out.print("R" + (i+1) + ": ");
				System.out.println(intRouters.get(i).toString());
				
			}
					
				
			}		
		
		double avg = 0;
		System.out.println();
		System.out.println("Simulation ending...");
		System.out.println("Total service time: " + totalServiceTime);
		System.out.println("Total Packets Served: " + countDest);
		
		if (!destination.isEmpty()) {
			avg = ((double) totalServiceTime) / (int)countDest;
		}
		
		System.out.printf("Average service time per packet: %.2f", avg); 
		System.out.println();
		System.out.println("Total packets dropped: " + dropCount);
		
		Packet p = new Packet();
		p.setPacketCount(0);
		
		return 0;

	}
	
	/**
	 * helper method that can generate a random number between minVal and maxVal
	 * @param minVal 
	 * @param maxVal
	 * @return random integer between these values
	 */
	private int randInt(int minVal, int maxVal) {
		return minVal + ((int) (Math.random() * maxVal));
	}	
	
	/**
	 * Creates new packets with each duration 
	 */
	public void makePacket() {
		dispatcher = new Router();
		int count = 0;
		for (int i = 0; i < MAX_PACKETS; i++) {
			if (Math.random() < arrivalProb) {
				int size = randInt(minPacketSize, maxPacketSize);
				Packet p = new Packet(size);
				p.setTimeArrive(timeCount);
				if (dispatcher.size <= 3) {
					dispatcher.enqueue(p);
					System.out.println("Packet " + packetCount + " arrives at dispatcher with size " + size);
					packetCount++;
				}
				
				if (count <= numIntRouters && (p.getId() <= numIntRouters)) {
					p.setTimeToDest(p.getTimeToDest() + 1);
					count++;
				}
			}
		}	
		
	}
	
	public static void main(String[] args) {
		
		String ans;
		
		do {
			
		Simulator s = new Simulator();
		Scanner input = new Scanner(System.in);
		System.out.println();
		System.out.print("Do you want to start the simulation? (y/n):  ");
		ans = input.next();
		
		
		if (ans.equals("y")) {
			System.out.println();
			System.out.println("Starting simulator...");
			System.out.println();
			
			System.out.print("Enter the number of Intermediate routers: ");
			s.numIntRouters = input.nextInt();
			System.out.print("Enter the arrival probability of a packet: ");
			s.arrivalProb = input.nextDouble();
			System.out.print("Enter the maximum buffer size of a router: ");
			s.maxBufferSize = input.nextInt();
			System.out.print("Enter the minimum size of a packet: ");
			s.minPacketSize = input.nextInt();
			System.out.print("Enter the maximum size of a packet: ");
			s.maxPacketSize = input.nextInt();
			System.out.print("Enter the bandwidth size: ");
			s.bandwidth = input.nextInt();
			System.out.print("Enter the simulation duration: ");
			s.duration = input.nextInt(); 
			
			if (s.arrivalProb < 0 || s.arrivalProb > 1 || s.minPacketSize < 0 || s.bandwidth < 0 || s.duration < 0) {
				System.out.println("No Simulation.");
				return;
			}
						
			try {
				s.simulate();
			} catch (Exception ex) {
				System.out.println("Error. Please try again.");
			}
			
		}
		
		if (ans.equals("n")) {
			System.out.println();
			System.out.println("Program terminating gracefully...");
			input.close();
		}
		
		} while (!ans.equals("n"));
	}
}

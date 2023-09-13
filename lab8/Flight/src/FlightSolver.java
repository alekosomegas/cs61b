package Flight.src;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

public class FlightSolver {
	PriorityQueue<Flight> flightPriorityQueue;
	ArrayList<Flight> flightArrayList;
	
	FlightSolver(ArrayList<Flight> flightArrayList) {
		this.flightArrayList = flightArrayList;
		Comparator<Object> comparator = (a, b) -> {
			Flight fb = (Flight)b;
			Flight fa = (Flight)a;
			return fb.startTime - fa.startTime;
		};
		flightPriorityQueue = new PriorityQueue<>(flightArrayList.size(), comparator);
		flightPriorityQueue.addAll(flightArrayList);
		// sort by start time
//		flightArrayList.sort(comparator);
	}
	
	public int solve() {
		int maxPass = 0;
		for (Flight flight : flightPriorityQueue) {
			Flight first = flightPriorityQueue.peek();
			if (flight.startTime <= first.endTime) {
				maxPass += flight.passengers;
			} else {
				while (flight.startTime > flightPriorityQueue.peek().endTime) {
					flightPriorityQueue.poll();
					maxPass -= first.passengers;
				}
			}
		}
		return maxPass;
	}
	
	public int solveArray() {
		int maxPassengers = 0;
		for (Flight flight : flightArrayList) {
			int start = flight.startTime;
			int end = flight.startTime;
			int currPass = flight.passengers;
			for (Flight other : flightArrayList) {
				if (flight == other) continue;
				if ((other.startTime >= start && other.startTime <= end) ||
						(other.endTime >= start && other.endTime <= end)) {
					currPass += other.passengers;
				}
			}
			if (currPass > maxPassengers) maxPassengers = currPass;
		}
		return maxPassengers;
	}
	
	public class Flight {
		int startTime;
		int endTime;
		int passengers;
		
		Flight(int startTime, int endTime, int passengers) {
			this.startTime = startTime;
			this.endTime = endTime;
			this.passengers = passengers;
		}
	}
}

//Michael Cho
//CSC 236-64
//Lab 1.1

public class ParkingTicketSimulator {
	public static void main(String []args){

	//ParkedCar instance
	ParkedCar pc = new ParkedCar("Volkswagon","1972","Red","147RHZM",60);

	//ParkingMeter instance
	ParkingMeter pm = new ParkingMeter(60);

	//PoliceOfficer instance
	PoliceOfficer po = new PoliceOfficer("Joe Friday","4788");

	//Difference between MinutesParked, MinutesPurchased passed into PoliceOfficer class

	int patrol2=po.patrol(pc.getMinutesParked(), pm.getMinutesPurchased());

	//ParkingTicket instance
	ParkingTicket pt = new ParkingTicket(pc.toString(),po.toString(),patrol2);

	//Print Car and Officer Data, as well as potential issuance of ticket
	System.out.println(pt.getCar());
	System.out.println();
	System.out.println(pt.getOfficer());
	System.out.println();
	System.out.println(pt.toString());
	System.out.println();

	}

}
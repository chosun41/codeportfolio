//Michael Cho
//CSC 236-64
//Lab 5.1

import java.util.Random;

public class MCCAirport
{
	//instances for the runway, take off queue, and landing queue
	//runway can only handle one plane at a time
	public Runway runway1=new Runway();
	public ArrayQueue<Airline> takeoffqueue=new ArrayQueue<Airline>();
	public ArrayQueue<Airline> landingqueue=new ArrayQueue<Airline>();

	//final variables for random comparison, minutes simulated, and to indicate how long runway occupied
	public final double LANDING_TIME=3;
	public final double TAKE_OFF_TIME=2;
	public final double LANDING_RATE=10;
	public final double TAKE_OFF_RATE=10;
	public final int ITERATIONS=1440;

	//variables for reporting on airport statistics
	public double totallandedqueuelength=0;
	public double totaltakeoffqueuelength=0;
	public double totallandedwaittime=0;
	public double totaltakeoffwaittime=0;
	public double totallanded=0;
	public double totaltakenoff=0;

	//random number generator to compare against landing and take off rate
	Random generator=new Random(System.currentTimeMillis());

	//simulate method that is called in the main
	public void simulate()
	{

		//we are going to run simulation for 1440 minutes which is value of ITERATION
		for(double i=0; i<ITERATIONS; i++)
		{

			//count size of queues at start of iteration
			totallandedqueuelength+=landingqueue.count();
			totaltakeoffqueuelength+=takeoffqueue.count();

			//generate random numbers to determine addition to queue
			//number b/t 0 and 1 when you use nextDouble()
			//landing rate and take off rate divided by 60 to convert to minutes b/t 0 and 1
			//please notice that Airline object created with the time it entered the queue -
			//this obviates the need to update every queue element with every second for the
			//waiting period.
			if(generator.nextDouble()<LANDING_RATE/60)
			{
				landingqueue.enqueue(new Airline(i));
			}

			if(generator.nextDouble()<TAKE_OFF_RATE/60)
			{
				takeoffqueue.enqueue(new Airline(i));
			}

			//determine if we can land or take off plane by checking if runway empty
			//check landing queue first and then take off queue
			//record queue waittime (difference between current minute i and when plane entered queue)
			//through getentertime method, enqueue plane onto runway, and then set runway time
			//to the corresponding final LANDING_TIME/TAKE_OFF_RATE value
			//finally dequeue the plane from their landing/take off queue
			if(runway1.getrunwayqueue().isemptyqueue())
			{
				if(!landingqueue.isemptyqueue())
				{
						Airline p=landingqueue.front();
						totallanded++;
						totallandedwaittime+=(i-p.getentertime());
						runway1.getrunwayqueue().enqueue(p);
						runway1.setrunwaytime(LANDING_TIME);
						landingqueue.dequeue();
				}
				else if(!takeoffqueue.isemptyqueue())
				{
						Airline q=takeoffqueue.front();
						totaltakenoff++;
						totaltakeoffwaittime+=(i-q.getentertime());
						runway1.getrunwayqueue().enqueue(q);
						runway1.setrunwaytime(TAKE_OFF_TIME);
						takeoffqueue.dequeue();
				}
			}

			//check if it's runwaytime is 0, which means a plane has finished landing or taking off.
			//depending on plane type, either totallanded or totaltakenoff will be incremented
			//for airport stats and then runway will be dequeued.
			//if runway not 0, we will update the runwaytime (decrementing by 1)
			else
			{
				if(runway1.getrunwaytime()>0)
				{
					runway1.updaterunway();
					if(runway1.getrunwaytime()==0)
					{
							runway1.getrunwayqueue().dequeue();
					}

				}
			}

		}
	}

	//print out stats:
	//we only record queue time when planes land or take off on the runway
	//planes that are still in queue by the end of the simulation are not counted
	//averge queue time= total queue wait time/total # of planes landed/taken off
	//average queue length = total queue length at each iteration/total # of iterations (minutes)

	public void stats()
	{
		System.out.printf("Average landing queue length: %.6f\n", totallandedqueuelength/ITERATIONS);
		System.out.printf("Average take off queue length: %.6f\n", totaltakeoffqueuelength/ITERATIONS);
		System.out.printf("Average landing queue time: %.6f\n", totallandedwaittime/totallanded);
		System.out.printf("Average take off queue time: %.6f\n", totaltakeoffwaittime/totaltakenoff);
	}

	//main
	//simulate airport and print out stats
	//have to create an instance of MCCAirport as it is non static
	public static void main(String [] args)
	{
		MCCAirport airport=new MCCAirport();
		airport.simulate();
		airport.stats();
	}
}
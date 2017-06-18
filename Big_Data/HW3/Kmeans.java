import java.io.*;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.filecache.*;


public class Kmeans extends Configured implements Tool{

    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {

    	//arraylist to hold the cluster centroids
		private	ArrayList<ArrayList<Double>> clusters = new ArrayList<ArrayList<Double>>();		
		
		@Override
		public void configure(JobConf job){
				
				try{
					//load cache file and reader
					Path[] localFiles = new Path[0];
					localFiles = DistributedCache.getLocalCacheFiles(job);
					BufferedReader fileIn = new BufferedReader (new FileReader(localFiles[0].toString()));

					//line holds each line of the cache file
					String line;

					try{
						
						while((line = fileIn.readLine()) != null){
							ArrayList<Double> cluster = new ArrayList<Double>();
							
							// read in each line input with space delimiter and add them to the arraylist of doubles
							StringTokenizer sepLine = new StringTokenizer(line, " ");
							
							//Converts each parameter to double and adds it to cluster ArrayList
							while(sepLine.hasMoreTokens()){
								cluster.add(Double.parseDouble(sepLine.nextToken()));
							}
							
							//cluster ArrayList is added to clusters list of lists	
							clusters.add(cluster);
						}
					}

					finally{
						fileIn.close();
					}
				}

				catch(IOException e){
					System.out.println("Input file not found");
				}
		}

	
		//Key is cluster number, value is DoubleWritable ArrayList
		public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {	    
			
			//convert line to string and then split into tokens with space delimiter
	    	StringTokenizer line = new StringTokenizer(value.toString(), " ");
	    	
	    	//convert tokens to double and store in array 
	    	ArrayList<Double> values = new ArrayList<Double>();
			

	    	while (line.hasMoreTokens()){
	    		values.add(Double.parseDouble(line.nextToken()));
	    	}

	    	
	    	//keeps track of cluster with best Euclidean Distance
	    	int bestclust=0;
	    	double nearestdist= Double.MAX_VALUE;

	    	//cycles thru individual cluster centroids

			for(int i=0; i<clusters.size(); i++){
				
				double dist=0;
	    	
				//pulls individual centroid
				ArrayList<Double> centroid = new ArrayList<Double>(clusters.get(i));
				
				//calculates euclidean distance between the individual centroid and observation
				for(int j=0; j<values.size(); j++){
					double error = centroid.get(j)-values.get(j);
	    			double d = error*error;
	    			dist = dist + d;
				}
					
				//if the dist is better than the current nearestdist than change nearest value and assign to that cluster
				if(dist<nearestdist){
				   nearestdist=dist;
				   bestclust=i;   
				}
			}

			//make index of centroid into a string
	    	String bestcluststr = Integer.toString(bestclust);
	    	
	    	//write the observation into string with space delimiter
	    	String vector = values.get(0).toString();
		
			for (int i=1; i<values.size(); i++){
				vector+= " "+values.get(i).toString();
			}

	    	output.collect(new Text(bestcluststr), new Text(vector)); 
		}
    }
	
   public static class Reduce extends MapReduceBase implements Reducer<Text, Text, Text, NullWritable> {
	
		public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, NullWritable> output, Reporter reporter) throws IOException {

	    	//running total of vector values, automatically intialized to 0 for each index
	    	ArrayList<Double> sum = new ArrayList<Double>();
	    	
	    	//counter for number of observations for each cluster key
	    	double count = 0;

	    	//cycle through the iterator
	    	while (values.hasNext()) {
	    		
	    		ArrayList<Double> observation = new ArrayList<Double>();
	    		
				//assign individual observation
				String line = values.next().toString();
	    		String [] token = line.split(" ");
	    		
    			//convert tokens to double and store in double array 
    			for (int i=0; i<token.length; i++){
    				observation.add(Double.parseDouble(token[i]));
    			}
	    		
	    		//need to initialize the empty sum arraylist at start, else update the running total
	    		if(count==0){
	    			
	    			for(int i=0; i<observation.size(); i++){
	    				sum.add(observation.get(i));
	    			}
				}
	    		else{
					
	    			for(int i=0; i<observation.size(); i++){
	    							
    					double newsum = sum.get(i)+observation.get(i);
    					sum.set(i, newsum);
	    			}
	    		}
	    		
	    		//update counter
	    		count++;
	    	}

	    	//compute new centroid values

	    	for(int i=0; i<sum.size(); i++){
	    		
	    		Double centroid = sum.get(i)/count;
	    		sum.set(i,centroid);
	    	}
			
	    	//write the observation into stringt with space delimiter
	    	
	    	
	    	//write double to text with space delimiter
	    	String centroidstr = sum.get(0).toString();;
	    	
    		for(int i=1; i<sum.size(); i++){
    			centroidstr+= " "+ sum.get(i).toString();
    		}

	    	//output new cluster information --> needs to overwrite cached cluster data.
	    	output.collect(new Text(centroidstr), NullWritable.get());
		}
 	}


    public int run(String[] args) throws Exception{
    	
		JobConf conf = new JobConf(getConf(), Kmeans.class); ///
		conf.setJobName("Kmeans");

		DistributedCache.addCacheFile(new Path("/user/mcho/centroids.txt").toUri(), conf);
		
		conf.setMapOutputValueClass(Text.class);
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(NullWritable.class); ///

		conf.setMapperClass(Map.class);
		conf.setReducerClass(Reduce.class);
		conf.set("mapreduce.textoutputformat.separator", "");

/*		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);*/

		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));

		JobClient.runJob(conf);
		return 0;
    }

    public static void main(String[] args) throws Exception 
    {
		int res = ToolRunner.run(new Configuration(), new Kmeans(), args);///
		System.exit(res);
    }
}

import java.io.*;
import java.util.Iterator;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class Exercise4 extends Configured implements Tool {
	
	// map class only captures artist and duration in key value pair
    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, DoubleWritable> {

    	public void map(LongWritable key, Text value, OutputCollector<Text, DoubleWritable> output, Reporter reporter) throws IOException {
    		String line = value.toString();
    		String[] splitcols=line.split(",");
    		String artist=splitcols[2];
    		double duration=Double.parseDouble(splitcols[3]);
    		output.collect(new Text(artist),new DoubleWritable(duration));
    		}
    }
    
    // partition class tells where to direct the map output based on 1st letter of key
    // divide alphabet in 5 blocks abcde|fghij|klmno|pqrst|uvwxyz
    //compare to tells us which block it falls into returning negative if letter comes before the first
    //letter of the block after it
	public static class Partition extends MapReduceBase implements Partitioner<Text, DoubleWritable> {

		
		public int getPartition(Text key, DoubleWritable value, int numReduceTasks) {    		

			String artist=key.toString();
			String firstletter = artist.substring(0,1).toLowerCase();

			if(firstletter.compareTo("f")<0){
				return 0;
			}
			else if(firstletter.compareTo("k")<0) {
				return 1;
			} 
			else if (firstletter.compareTo("p")<0) {
				return 2;
			} 
			else if (firstletter.compareTo("u")<0) {
				return 3;
			} else {
				return 4;
			}
		}
	}
    
    // replaces double min value with next if it is greater than current maxdur value
    public static class Reduce extends MapReduceBase implements Reducer<Text, DoubleWritable, Text,DoubleWritable> {

    	public void reduce(Text key, Iterator<DoubleWritable> values, OutputCollector<Text, DoubleWritable> output, Reporter reporter) throws IOException {
		
			double maxdur = Double.MIN_VALUE;
			
			while(values.hasNext()) {
				double next = values.next().get();
				if(next > maxdur) {
					maxdur = next;
				}    
			}
			
			output.collect(key, new DoubleWritable(maxdur));
    	}
    }
    
    //5 reducers
    public int run(String[] args) throws Exception {
    	
	JobConf conf = new JobConf(getConf(), Exercise4.class);
	conf.setJobName("Exercise4");
	
	conf.setOutputKeyClass(Text.class);
	conf.setOutputValueClass(DoubleWritable.class);
	conf.setNumReduceTasks(5);
	conf.setMapperClass(Map.class);
	conf.setPartitionerClass(Partition.class);
	conf.setCombinerClass(Reduce.class);
	conf.setReducerClass(Reduce.class);

	FileInputFormat.setInputPaths(conf, new Path(args[0]));
	FileOutputFormat.setOutputPath(conf, new Path(args[1]));
	
	JobClient.runJob(conf);
	return 0;
    }
    
    public static void main(String[] args) throws Exception {
	int res = ToolRunner.run(new Exercise4(), args);
	System.exit(res);
    }
}

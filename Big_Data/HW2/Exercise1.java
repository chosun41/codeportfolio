import java.io.*;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.mapred.lib.MultipleInputs;

public class Exercise1 extends Configured implements Tool {
	
	//two mappers one for each gram file
	//splits files according to multiple spaces and extracts the correct column 
	//only takes in correct year values using regex for digits
	
	// for unigram file
    public static class Map1 extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {


    	public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
    		String line = value.toString();
    		String[] splitcols=line.split("\\s+");
	    
    		String word=splitcols[0];
    		String year=splitcols[1];
    		String novol=splitcols[3];

    		if(year.matches("^[0-9]{4}$")){
    			if(word.toLowerCase().contains("nu")){
    				String concatkey=year + "," + "nu" + ",";
            		output.collect(new Text(concatkey),new Text(novol));
    			}
    			 if(word.toLowerCase().contains("chi")){
    				String concatkey=year + "," + "chi" + ",";
            		output.collect(new Text(concatkey),new Text(novol));
    			}
    			 if(word.toLowerCase().contains("haw")){
    				String concatkey=year + "," + "haw" + ",";
            		output.collect(new Text(concatkey),new Text(novol));
    			}
    		}

    	}
    }
    
    
	// for the bigram file
    public static class Map2 extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {


    	public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
    		String line = value.toString();
    		String[] splitcols=line.split("\\s+");
	    
    		String word1=splitcols[0];
    		String word2=splitcols[1];
    		String year=splitcols[2];
    		String novol=splitcols[4];
	    
    		if(year.matches("^[0-9]{4}$")){
    			if(word1.toLowerCase().contains("nu")){
    				String concatkey=year + "," + "nu" + ",";
    				output.collect(new Text(concatkey),new Text(novol));
    			}
    			if(word2.toLowerCase().contains("nu")){
    				String concatkey=year + "," + "nu" + ",";
    				output.collect(new Text(concatkey),new Text(novol));
    			}
    			if(word1.toLowerCase().contains("chi")){
    				String concatkey=year + "," + "chi" + ",";
    				output.collect(new Text(concatkey),new Text(novol));
    			}
    			if(word2.toLowerCase().contains("chi")){
    				String concatkey=year + "," + "chi" + ",";
    				output.collect(new Text(concatkey),new Text(novol));
    			}
    			if(word1.toLowerCase().contains("haw")){
    				String concatkey=year + "," + "haw" + ",";
    				output.collect(new Text(concatkey),new Text(novol));
    			}
    			if(word2.toLowerCase().contains("haw")){
    				String concatkey=year + "," + "haw" + ",";
    				output.collect(new Text(concatkey),new Text(novol));
    			}
    		}

    	}
    }
    
    // intermediate combiner that takes key and summarizes count and vol
    public static class AvgCombiner implements Reducer<Text,Text, Text, Text> {


		public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {

			double sum = 0;
			double count = 0;

			while (values.hasNext()) {

				Text current = values.next();
					 
				sum += Double.parseDouble(current.toString());
				count ++;
			}

				// set value information to sum of # volumes and count
			String sumcount= Double.toString(sum) + ", " + Double.toString(count);

			output.collect(key, new Text(sumcount));

		}
		
		public void configure(JobConf arg0) {
			// TODO Auto-generated method stub

		}

		public void close() throws IOException {
			// TODO Auto-generated method stub

		}

	}


    // reduce class
    // sums up volume and count and creates averages by dividing volume by count
    public static class Reduce extends MapReduceBase implements Reducer<Text, Text, Text,DoubleWritable> {

    	public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, DoubleWritable> output, Reporter reporter) throws IOException {
		
    		double totvol=0;
    		double totcount= 0;
	    
    		while (values.hasNext()) {
    			
				Text current = values.next();
				String[] tokens = current.toString().split(",");

				// get values to sum and add to count
				totvol += Double.parseDouble(tokens[0]);
				totcount += Double.parseDouble(tokens[1]);

    		}
	    
    		output.collect(key, new DoubleWritable(totvol/totcount));
    	}
    }

    // takes in multiple inputs one for each file
    // everything is done in one job
    
    public int run(String[] args) throws Exception {
    	
	JobConf conf = new JobConf(getConf(), Exercise1.class);
	conf.setJobName("Exercise1");

	MultipleInputs.addInputPath(conf,new Path(args[0]),TextInputFormat.class,Map1.class);
	MultipleInputs.addInputPath(conf,new Path(args[1]),TextInputFormat.class,Map2.class);
	conf.setCombinerClass(AvgCombiner.class);
	conf.setReducerClass(Reduce.class);
	conf.setOutputKeyClass(Text.class);
	conf.setOutputValueClass(Text.class);
	conf.setOutputFormat(TextOutputFormat.class);

	FileOutputFormat.setOutputPath(conf, new Path(args[2]));
	
	JobClient.runJob(conf);
	return 0;
    }
    
    public static void main(String[] args) throws Exception {
	int res = ToolRunner.run(new Exercise1(), args);
	System.exit(res);
    }
}

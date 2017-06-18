import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.mapred.lib.MultipleInputs;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class Exercise2 extends Configured implements Tool {

	public static class Map1 extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {

		public void configure(JobConf job) {}

		public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {		
			String[] line = value.toString().split("\\s+");
			double vol = Double.parseDouble(line[3]);
			double volsq = vol*vol;
			output.collect(new Text("stdev"), new Text(volsq+","+vol));
		}
	}
	
	public static class Map2 extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {

		public void configure(JobConf job) {}

		public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {			
			String[] line = value.toString().split("\\s+");
			double vol = Double.parseDouble(line[4]);
			double volsq = vol*vol;
			output.collect(new Text("stdev"), new Text(volsq+","+vol));
		}
	}
	
	public static class SumCombiner extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

	    public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
	    	double sqsum = 0.0, sum = 0.0, count = 0.0;

	    	while (values.hasNext()) {
	    		Text pair = values.next();
				String[] tokens = pair.toString().split(",");
	    		count++;
	    		sqsum += Double.parseDouble(tokens[0]); 
	    		sum += Double.parseDouble(tokens[1]);
	    	}
            output.collect(key, new Text(Double.toString(sqsum)+","+Double.toString(sum)+","+Double.toString(count)));
	    }
	}
	
	public static class Reduce extends MapReduceBase implements Reducer<Text, Text, Text, DoubleWritable> {

		public void configure(JobConf job) {
		}
		public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, DoubleWritable> output, Reporter reporter) throws IOException {
			double sqsum = 0.0;
			double sum = 0.0;
		    double count = 0.0;
		    while(values.hasNext()) {
		    	Text triple = values.next();
				String[] tokens = triple.toString().split(",");
				count += Double.parseDouble(tokens[2]);
			    sum += Double.parseDouble(tokens[1]);
			    sqsum += Double.parseDouble(tokens[0]);
		    }
		    double stdev = Math.sqrt((sqsum-count*Math.pow(sum/count, 2))/count);		    
			
		    output.collect(key, new DoubleWritable(stdev));	
		}
	}
	
	public int run(String[] args) throws Exception {
		JobConf conf = new JobConf(getConf(), Exercise2.class);
		conf.setJobName("Exercise2");

		conf.setMapOutputKeyClass(Text.class);
	    conf.setMapOutputValueClass(Text.class);
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(DoubleWritable.class);

		conf.setNumReduceTasks(1);
		conf.setCombinerClass(SumCombiner.class);
		conf.setReducerClass(Reduce.class);

		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);

		MultipleInputs.addInputPath(conf, new Path(args[0]),TextInputFormat.class,Map1.class);
		MultipleInputs.addInputPath(conf, new Path(args[1]),TextInputFormat.class,Map2.class);
		FileOutputFormat.setOutputPath(conf, new Path(args[2]));

		JobClient.runJob(conf);
		return 0;
	}
	
	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new Exercise2(), args);
		System.exit(res);
	}
}

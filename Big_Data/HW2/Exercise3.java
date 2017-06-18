import java.io.*;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class Exercise3 extends Configured implements Tool {
	
	// only 1 map class no need for reducer since we already have desired output from mapper
	// only takes in year string if it matches 4 numbers and if integer year is between 2000 and 2010 inclusive
    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, NullWritable> {

    	public void map(LongWritable key, Text value, OutputCollector<Text, NullWritable> output, Reporter reporter) throws IOException {
    		String line = value.toString();
    		String[] splitcols=line.split(",");
    		String songttl=splitcols[1];
    		String artist=splitcols[2];
    		String duration=splitcols[3];
    		String yearstr =splitcols[165];

    		if(yearstr.matches("^[0-9]{4}$")){
    			int yearnum=Integer.parseInt(yearstr);
    			if(yearnum>=2000 && yearnum<=2010){
    				String concatkey=songttl + "," + artist + "," + duration;
            		output.collect(new Text(concatkey),NullWritable.get());
    			}
    		}
    	}

    }
    
	public static class Reduce extends MapReduceBase implements Reducer<Text, NullWritable, Text, NullWritable> {

		public void reduce(Text key, Iterator<NullWritable> values,OutputCollector<Text, NullWritable> output, Reporter reporter) throws IOException {

			output.collect(key, NullWritable.get());
		}
	}

    
    public int run(String[] args) throws Exception {
    	
	JobConf conf = new JobConf(getConf(), Exercise3.class);
	conf.setJobName("Exercise3");
	
	conf.setInputFormat(TextInputFormat.class);
	conf.setOutputFormat(TextOutputFormat.class);
	
	conf.setOutputKeyClass(Text.class);
	conf.setOutputValueClass(NullWritable.class);
	
	conf.setMapperClass(Map.class);
	conf.setCombinerClass(Reduce.class);
	conf.setReducerClass(Reduce.class);

	FileInputFormat.setInputPaths(conf, new Path(args[0]));
	FileOutputFormat.setOutputPath(conf, new Path(args[1]));
	
	JobClient.runJob(conf);
	return 0;
    }
    
    public static void main(String[] args) throws Exception {
	int res = ToolRunner.run(new Exercise3(), args);
	System.exit(res);
    }
}

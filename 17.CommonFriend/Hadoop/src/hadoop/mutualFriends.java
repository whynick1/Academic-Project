package hadoop;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;


public class mutualFriends {
	public static class Map extends Mapper<LongWritable, Text, Text, Text>
	{	
		private Text map_key = new Text();
		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
	        String userid1 = context.getConfiguration().get("usernameid1");
	        String userid2 = context.getConfiguration().get("usernameid2");
	        
	        String[] mydata = value.toString().split("\t");
	        if(mydata[0].equals(userid1) || mydata[0].equals(userid2))
	        {
				String[] FriendList = mydata[1].toString().split(",");
				
				for (String data : FriendList) {
					if(mydata[0].compareTo(data) <= 0)
						map_key.set(mydata[0]+","+data);
					else
						map_key.set(data+","+mydata[0]);
	
					context.write(map_key, new Text(mydata[1]));
				}
	        }
		}
	}

	public static class Reduce extends Reducer<Text,Text,Text,Text>
	{	
		@Override
		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {	
	        String userid1 = context.getConfiguration().get("usernameid1");
	        String userid2 = context.getConfiguration().get("usernameid2");
	        String[] inKey = key.toString().split(",");
			
	        if( (inKey[0].equals(userid1) && inKey[1].equals(userid2)) || 
	        		(inKey[0].equals(userid2) && inKey[1].equals(userid1)) )
	        {
				HashMap<String, Integer> map = new HashMap<String, Integer>();
				for (Text val : values) {
					String[] mydata = val.toString().split(",");
					for (String word : mydata) {
						if (map.containsKey(word)) {
							map.put(word, map.get(word) + 1);
						} else { 
							map.put(word, 1);
						}
					}
				}
				
				Iterator<String> it = map.keySet().iterator();  
				String tmpStr = "";
				while(it.hasNext()) {  
					String keyword = (String)it.next();  
					if(map.get(keyword) > 1){
						if(tmpStr.equals(""))
							tmpStr = keyword;
						else
							tmpStr = tmpStr +","+ keyword;
					}
				}		
				context.write(key, new Text(tmpStr));
	        }
		}
	}
	
	public static int run(String[] args) throws Exception {
		Configuration conf = new Configuration();
        conf.set("usernameid1", args[2]);	
        conf.set("usernameid2", args[3]);	

		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

		if (otherArgs.length != 4) {
			System.err.println("Usage: CommonFriendList <in> <out> <userid1> <userid2>");
			System.exit(2);
		}
		Job job = new Job(conf, "mutualFriends");
		job.setJarByClass(mutualFriends.class);
		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);
		
		
		job.setOutputKeyClass(Text.class);		
		job.setOutputValueClass(Text.class);		
		
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));	
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		
		return (job.waitForCompletion(true) ? 0 : 1);
	}
	
	public static void main(String[] args) throws Exception {
		int res = run(args);
		System.exit(res);
	}

}

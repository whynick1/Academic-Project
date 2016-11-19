package hadoop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;


public class inMemoryJoin {
	
	private static final String OUTPUT_PATH = "intermediate";
	
	public static class Map1 extends Mapper<LongWritable, Text, Text, Text>
	{	
		private Text map_key = new Text();
		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
	        String userid1 = context.getConfiguration().get("usernameid1");
	        String userid2 = context.getConfiguration().get("usernameid2");
	        
	        String[] mydata = value.toString().split("\t");
			if(mydata.length == 1){
				context.write(new Text(userid1+","+userid2), new Text(""));
				return;
			}
			
	        if(mydata[0].equals(userid1) || mydata[0].equals(userid2)) {
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

	public static class Reduce1 extends Reducer<Text,Text,Text,Text>
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
							map.put(word, 1);	// set the initial value to one
						}
					}
				}
				
				Iterator it = map.keySet().iterator();  
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
	
	public static class Map2 extends Mapper<LongWritable, Text, Text, Text> {
		private String tempData = null;

		@Override
		protected void setup(Context context) throws IOException, InterruptedException {
			super.setup(context);
	        URI[] cacheFile = context.getCacheFiles();

	        FileSystem fs = FileSystem.get(URI.create("hdfs://cshadoop1/user/hxw150830"), context.getConfiguration());
	        FSDataInputStream in = null;
	        in = fs.open(new Path(cacheFile[0].getPath()));
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
	            
	        String s = bufferedReader.readLine();
	        bufferedReader.close();
        
	        String[] mydata = s.split("\t");
	  
	        if(mydata.length <= 1)
	        	tempData = "@";
	        else
	        	tempData = mydata[1];
	        //conf.set(data[0], s);
		}
		
		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String[] mydata = value.toString().split(",");
			String tmp = tempData;
			if(tmp.equals("@")){
				context.write(new Text("999"), new Text(""));
				return;
			}
			String[] FriendsList = tmp.split(","); 
			
			//context.write(new Text("999"), new Text("tmp = " + tmp));
			
			for (String data : FriendsList) {
				//context.write(new Text("999"), new Text("mydata[0] = " + mydata[0] + " data = " + data));
				if(mydata[0].equals(data))
				{
					context.write(new Text("999"), new Text(mydata[1]+":"+mydata[9])); //you can use any other number, rather than 999
					break;
				}
			}
		}
	}
	
	
	
	public static class Reduce2 extends Reducer<Text,Text,Text,Text>
	{
		@Override
		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			
	        String userid1 = context.getConfiguration().get("usernameid1");
	        String userid2 = context.getConfiguration().get("usernameid2");
	        String str = "";
			for (Text val : values) {
				if(str.equals(""))
					str = val.toString();
				else
					str = str +", "+ val.toString();
			}        
	        context.write(new Text(userid1+" "+userid2), new Text("["+str+"]"));
		}
	}
	
	public static int run(String[] args) throws Exception {
		Configuration conf = new Configuration();
        conf.set("usernameid1", args[3]);
        conf.set("usernameid2", args[4]);
        
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length != 5) {
			System.err.println("Usage: inMemoryJoin <in1> <in2> <out> <userid1> <userid2>");
			System.exit(2);
		}
		
		Job job = new Job(conf, "SearchCommonFriends");
		job.setJarByClass(inMemoryJoin.class);
		job.setMapperClass(Map1.class);
		job.setReducerClass(Reduce1.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH)); 
		job.waitForCompletion(true);

		Job job2 = new Job(conf, "getNameBirthday");
		job2.setJarByClass(inMemoryJoin.class);
		job2.setMapperClass(Map2.class);
		job2.setReducerClass(Reduce2.class);
		
		job2.setOutputKeyClass(Text.class);
		job2.setOutputValueClass(Text.class);
		
		FileInputFormat.addInputPath(job2, new Path(otherArgs[1]));	
		FileOutputFormat.setOutputPath(job2, new Path(otherArgs[2]));
		
		///////////////////		
		//分布式缓存要存储的文件路径
		String cachePath = "hdfs://cshadoop1/user/hxw150830/intermediate/part-r-00000";
		
		//向分布式缓存中添加文件
		job2.addCacheFile(new Path(cachePath).toUri());
	
		return (job2.waitForCompletion(true) ? 0 : 1);
	}
	
	
	public static void main(String[] args) throws Exception {
		int res = run(args);
		System.exit(res);
	}

}

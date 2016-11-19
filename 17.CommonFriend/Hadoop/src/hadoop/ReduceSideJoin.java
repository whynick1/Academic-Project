package hadoop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;


public class ReduceSideJoin {
	
	public static class Map extends Mapper<LongWritable, Text, Text, Text> {
		java.util.Map<String, String> hm = new java.util.HashMap<String, String>();
		
		@Override
		protected void setup(Context context) throws IOException, InterruptedException {
			super.setup(context);
	        URI[] cacheFile = context.getCacheFiles();

	        FileSystem fs = FileSystem.get(URI.create("hdfs://cshadoop1/user/hxw150830"), context.getConfiguration());
	        FSDataInputStream in = null;
	        in = fs.open(new Path(cacheFile[0].getPath()));
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
	        
	        String temp = bufferedReader.readLine();
	        while (temp != null) {
	        	StringTokenizer st = new StringTokenizer(temp, ",");
	        	String id = st.nextToken();
	        	hm.put(id, temp);
	        	temp = bufferedReader.readLine();
	        }
	        
	        bufferedReader.close();
		}
		
		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
	        String[] data = value.toString().split("\t");	
	        if(data.length == 1)
	        	return;

			String[] FriendList = data[1].toString().split(",");	

			int maxAge = 0; String ageMaxUserID = "";
			for (String userid : FriendList)
			{
				//String userInfo = context.getConfiguration().get(userid);
				String userInfo = hm.get(userid);
				String[] userInfoList = userInfo.split(",");
				String[] ageTmp = userInfoList[9].split("/");
				int age = Integer.parseInt(ageTmp[2])*10000 + Integer.parseInt(ageTmp[0])*100 + Integer.parseInt(ageTmp[1]);
				
				if(maxAge < age){
					maxAge = age;
					ageMaxUserID = userid;
				}
			}
			context.write(new Text("SAME_KEY"), new Text(data[0]+","+ageMaxUserID+","+maxAge)); 
		}
	}
	
	public static class Reduce extends Reducer<Text,Text,Text,Text> {	
		java.util.Map<String, String> hm = new java.util.HashMap<String, String>();
	
		@Override
		protected void setup(Context context) throws IOException, InterruptedException {
			super.setup(context);
	        URI[] cacheFile = context.getCacheFiles();
	
	        FileSystem fs = FileSystem.get(URI.create("hdfs://cshadoop1/user/hxw150830"), context.getConfiguration());
	        FSDataInputStream in = null;
	        in = fs.open(new Path(cacheFile[0].getPath()));
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
	        
	        String temp = bufferedReader.readLine();
	        while (temp != null) {
	        	StringTokenizer st = new StringTokenizer(temp, ",");
	        	String id = st.nextToken();
	        	hm.put(id, temp);
	        	temp = bufferedReader.readLine();
	        }
	        
	        bufferedReader.close();
		}
		
		public class User{
			String userid;
			String ageMaxUserid;	
			int age;
			User(String id1, String id2, int ageNum) {
				userid = id1;
				ageMaxUserid = id2;
				age = ageNum; 
			}
		}
		
		@Override
		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {	
			Comparator<User> compare;
			compare = new Comparator<User>() { 
				public int compare(User o1, User o2)  {
					return o1.age - o2.age; 
				}
			};

			PriorityQueue<User> heap = new PriorityQueue<User>(15000, compare); 

			for (Text val : values) {
				String[] dataTmp = val.toString().split(",");
				User user = new User(dataTmp[0],dataTmp[1],Integer.parseInt(dataTmp[2]));
				heap.add(user); 
			}
			
			for(int i=0; i<10; i++){
				User user = heap.poll();		
				//String userInfo = context.getConfiguration().get(user.userid);
				String userInfo = hm.get(user.userid);
				
				String[] userInfoList = userInfo.split(",");
				
				//String ageMaxFriendInfo = context.getConfiguration().get(user.ageMaxUserid);
				String ageMaxFriendInfo = hm.get(user.ageMaxUserid);
				
				String[] friendInfoList = ageMaxFriendInfo.split(",");
				
				String strTmp = userInfoList[3] + userInfoList[4] + userInfoList[5] + friendInfoList[9]; //
				
				context.write(new Text(user.userid), new Text(strTmp));
			}	
		}
	}
	
	public static int run(String[] args) throws Exception {  
		Configuration conf = new Configuration();
		
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length != 3) {
			System.err.println("Usage: ReduceSideJoin <input1> <input2> <output>");
			System.exit(2);
		}

		Job job = new Job(conf, "ReduceSideJoin");
		job.setJarByClass(ReduceSideJoin.class);
		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);
		
		job.setOutputKeyClass(Text.class);	
		job.setOutputValueClass(Text.class); 	
		
		//分布式缓存要存储的文件路径
		String cachePath = "hdfs://cshadoop1/user/hxw150830/input2";
				
		//向分布式缓存中添加文件
		job.addCacheFile(new Path(cachePath).toUri());
		
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));
		
		return job.waitForCompletion(true) ? 0 : 1;
    }
	
	public static void main(String[] args) throws Exception {
		int res = run(args);
		System.exit(res);
	}
}

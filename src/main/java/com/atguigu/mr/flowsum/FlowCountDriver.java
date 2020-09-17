package com.atguigu.mr.flowsum;

import com.atguigu.mr.wordcount.WordCountReducer;
import com.atguigu.mr.wordcount.WordcountMapper;
import com.sun.tools.javac.comp.Flow;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class FlowCountDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        //1.获取Job对象
        Job job = Job.getInstance(conf);

        //2.设置jar存储位置
        job.setJarByClass(FlowCountDriver.class);

        //3.关联Map和Reduce类
        job.setMapperClass(FlowcountMapper.class);
        job.setReducerClass(FlowCountReducer.class);

        //4.设置Mapper阶段输出数据的key和value
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);

        //5.设置最终输出的key和value
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        job.setPartitionerClass(ProvincePartitioner.class);

        job.setNumReduceTasks(5);
        //6.设置输入路径和输出路径
        FileInputFormat.setInputPaths(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));

        //7.提交job
        boolean result = job.waitForCompletion(true);

        System.exit(result ? 0:1);

    }
}

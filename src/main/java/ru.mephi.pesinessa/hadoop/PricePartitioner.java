package ru.mephi.pesinessa.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Partitioner;

public class PricePartitioner implements Partitioner<IntWritable, IntWritable> {

    /**
     * Get the partition number for a given key
     * @param key Key of the processed record
     * @param value Value of the processed record
     * @param numReduceTasks Total number of partitions
     * @return Partition number
     */
    @Override
    public int getPartition(IntWritable key, IntWritable value, int numReduceTasks) {
        return(key.hashCode() & Integer.MAX_VALUE) % numReduceTasks;
    }

    /**
     * Initializes a new instance from a JobConf.
     * @param conf Configuration of the job
     */
    @Override
    public void configure(JobConf conf) {
    }
}


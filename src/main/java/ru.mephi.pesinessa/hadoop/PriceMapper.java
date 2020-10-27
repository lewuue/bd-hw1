package ru.mephi.pesinessa.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class PriceMapper extends MapReduceBase implements Mapper<LongWritable, Text, IntWritable, IntWritable> {

    private static Integer MIN_BID_PRICE = 250;
    private static IntWritable ONE = new IntWritable(1);

    /**
     * Transform input records into a intermediate records
     * @param key Key of the processed record
     * @param value Value of the processed record
     * @param output Collector of output data
     * @param reporter Reporter of progress and update counters, status information etc.
     */
    @Override
    public void map(LongWritable key, Text value, OutputCollector<IntWritable, IntWritable> output, Reporter reporter) {
        try {
            String[] inputData = value.toString().split("\\t");

            // Фильтруем записи по значению поля bindingPrice
            if(Integer.parseInt(inputData[19]) > MIN_BID_PRICE) {
                output.collect(new IntWritable(Integer.parseInt(inputData[7])), ONE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


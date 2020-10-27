package ru.mephi.pesinessa.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PriceReducer extends MapReduceBase implements Reducer<IntWritable, IntWritable, Text, IntWritable> {

    private static Map<Integer, String> cityMapCache = new HashMap<>();

    /**
     * Reduce the processed records
     * @param key Key of the processed records
     * @param values Values of the processed records
     * @param output Collector of output data
     * @param reporter Reporter of progress and update counters, status information etc.
     */
    public void reduce(IntWritable key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) {
        try {
            int sum = 0;
            while (values.hasNext()) {
                sum += values.next().get();
            }

            // Получаем наименование города по id из кэша
            String city = cityMapCache.get(key.get());
            if(city == null) {
                city = key.toString();
            }

            output.collect(new Text(city), new IntWritable(sum));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Setup before reducing: init distribution cache
     * @param conf Configuration of the job
     */
    @Override
    public void configure(JobConf conf) {
        try {
            File cachedFile = new File("city");
            if(cachedFile.exists()) {
                BufferedReader brReader = new BufferedReader(new FileReader(cachedFile));
                String strLineRead = "";
                while ((strLineRead = brReader.readLine()) != null) {
                    String[] cityMappings = strLineRead.split("\\s+");
                    cityMapCache.put(Integer.parseInt(cityMappings[0].trim()), cityMappings[1].trim());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}

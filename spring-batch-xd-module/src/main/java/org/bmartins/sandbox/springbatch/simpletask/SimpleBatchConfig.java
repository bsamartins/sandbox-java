package org.bmartins.sandbox.springbatch.simpletask;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class SimpleBatchConfig {

	@Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;
    
    @Bean
    public Job job() {
        return jobs.get("myJob")
        		.start(step1())
        		.next(step2())
        		.build();
    }
    
    @Bean
//    protected Step step1(ItemReader<Person> reader, ItemProcessor<Person, Person> processor, ItemWriter<Person> writer) {
    protected Step step1() {
        return steps.get("step1")
            .<Person, Person> chunk(10)
//            .reader(reader)
//            .processor(processor)
//            .writer(writer)
            .build();
    }

    @Bean
//    protected Step step2(Tasklet tasklet) {
    protected Step step2() {
        return steps.get("step2")
        	.chunk(10)
//            	.tasklet(tasklet)
        	.build();
    }
}

package com.example.testdebeziumpr.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
//    private final EventService customerService;
//    private final CustomerService customerService;
//    САМАЯ БОЛЬШАЯ ПРОБЛЕМА:
//    нужно придумать как


    @Bean
    public Flow flow() {
        return (Flow) new FlowBuilder<SimpleStepBuilder>("flow")
                .start(decider())
                .on("*").end()
                .build();
    }

    /*@Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .incrementer(new RunIdIncrementer())
                .start(flow()) // Используем flow
                .from(decider()).on("step1").to(step1())
                .from(decider()).on("step2").to(step2())
                .from(decider()).on("step3").to(step3())
                .end()
                .build();
    }*/
    @Bean
    public Job job() {

        //выглядит конечно как полная жопа, но хотя бы работает..
        return jobBuilderFactory.get("job")
                .incrementer(new RunIdIncrementer())
                .start(flow())
                .from(decider()).on("step1").to(step1())
                .next(step2())
                .next(step3())
                .from(decider()).on("step2").to(step2())
                .next(step3())
                .from(decider()).on("step3").to(step3())
                .end()
                .build();
    }


    @Bean
    public JobExecutionDecider decider() {
        return (jobExecution, stepExecution) -> {
            String startStep = jobExecution.getJobParameters().getString("startStep");
//            logger.info("Decider: startStep = {}", startStep);

            if ("step2".equals(startStep)) {
                System.out.println("Decider: starting from step2");
                return new FlowExecutionStatus("step2");
            } else if ("step3".equals(startStep)) {
                System.out.println("Decider: starting from step3");
                return new FlowExecutionStatus("step3");
            } else {
                System.out.println("Decider: starting from step1 (default)");
                return new FlowExecutionStatus("step1");
            }
        };
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("Step 1");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("Step 2");
//                    customerService.getMessage();
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("Step 3");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}

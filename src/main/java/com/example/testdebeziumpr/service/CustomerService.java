package com.example.testdebeziumpr.service;

import com.example.testdebeziumpr.repository.CustomerRepository;
import io.debezium.data.Envelope;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerService implements EventService{

    private final CustomerRepository customerRepository;
    private final JobLauncher jobLauncher;
    private final Job job;

    //    @Transactional
    public void replicateData(Map<String, Object> customerData, Envelope.Operation operation) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        /*final ObjectMapper mapper = new ObjectMapper();
//        String param = String.format("%s.id = %s", customerData.get("schema").toString() + ".\"" + customerData.get("table")+"\"", customerData.get("id"));
//        log.info("{}", param);
        String param = null;
        if (customerData.get("schema").equals("dbz") && customerData.get("table").equals("z#supply")) {
            param = String.format(" and c.id = %s", customerData.get("id"));
        }
        System.out.println(param);
        customerRepository.updateTable(param);*/
        runJob("step2");
    }

    public void runJob(String step) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("startStep", step)
                .addLong("timestamp", System.currentTimeMillis()) // Добавляем временную метку
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(job, jobParameters);

        System.out.println("Job completed with status " + jobExecution.getStatus());
    }

    @Override
    public  void getMessage(){
        System.out.println("message from customer");
    }


}
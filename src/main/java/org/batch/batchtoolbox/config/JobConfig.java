package org.batch.batchtoolbox.config;

import org.batch.batchtoolbox.batch.listener.JobCompletionNotificationListener;
import org.batch.batchtoolbox.batch.processor.BeneficiaryItemProcessor;
import org.batch.batchtoolbox.batch.reader.BeneficiaryItemReader;
import org.batch.batchtoolbox.batch.writer.BeneficiaryItemWriter;
import org.batch.batchtoolbox.bo.Beneficiary;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager; // Exact name match with bean
    private final BeneficiaryItemReader beneficiaryItemReader;
    private final BeneficiaryItemProcessor beneficiaryItemProcessor;
    private final BeneficiaryItemWriter beneficiaryItemWriter;

    @Value("${batch.output.path}")
    private String outputFilePath;

    public JobConfig(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            BeneficiaryItemReader beneficiaryItemReader,
            BeneficiaryItemProcessor beneficiaryItemProcessor,
            BeneficiaryItemWriter beneficiaryItemWriter) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.beneficiaryItemReader = beneficiaryItemReader;
        this.beneficiaryItemProcessor = beneficiaryItemProcessor;
        this.beneficiaryItemWriter = beneficiaryItemWriter;
    }

    @Bean
    public Job exportBeneficiaryJob(JobCompletionNotificationListener listener, Step step1) {
        return new JobBuilder("exportBeneficiaryJob", jobRepository)
                .listener(listener)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1() throws Exception {
        return new StepBuilder("step1", jobRepository)
                .<Beneficiary, Beneficiary>chunk(10, transactionManager)
                .reader(beneficiaryItemReader.reader())
                .processor(beneficiaryItemProcessor)
                .writer(beneficiaryItemWriter.writer(outputFilePath))
                .build();
    }
}
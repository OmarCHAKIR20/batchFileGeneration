package org.batch.batchtoolbox.config;

import org.batch.batchtoolbox.utils.FileUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobLauncherConfig {

    private final JobLauncher jobLauncher;
    private final Job exportBeneficiaryJob;
    private final FileUtils fileUtils;

    @Value("${batch.output.path}")
    private String outputFilePath;

    public JobLauncherConfig(JobLauncher jobLauncher, Job exportBeneficiaryJob, FileUtils fileUtils) {
        this.jobLauncher = jobLauncher;
        this.exportBeneficiaryJob = exportBeneficiaryJob;
        this.fileUtils = fileUtils;
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            fileUtils.ensureDirectoryExists(outputFilePath);

            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(exportBeneficiaryJob, jobParameters);
        };
    }
}
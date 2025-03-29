package org.batch.batchtoolbox.batch.writer;

import org.batch.batchtoolbox.bo.Beneficiary;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class BeneficiaryItemWriter {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public FlatFileItemWriter<Beneficiary> writer(String outputPath) {
        DelimitedLineAggregator<Beneficiary> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter("#");

        lineAggregator.setFieldExtractor(beneficiary -> new Object[]{
                beneficiary.getId(),
                beneficiary.getState(),
                beneficiary.getLastName(),
                beneficiary.getFirstName(),
                beneficiary.getDepositDate() != null ? dateFormat.format(beneficiary.getDepositDate()) : "",
                beneficiary.getBirthDate() != null ? dateFormat.format(beneficiary.getBirthDate()) : "",
                beneficiary.getGenre(),
                beneficiary.getBeneficiaryAddress(),
                beneficiary.getBeneficiaryCityCode(),
                beneficiary.getSector(),
                beneficiary.getStudiesPeriod(),
                beneficiary.getSchoolName()
        });

        return new FlatFileItemWriterBuilder<Beneficiary>()
                .name("beneficiaryWriter")
                .resource(new FileSystemResource(outputPath))
                .lineAggregator(lineAggregator)
                .build();
    }
}
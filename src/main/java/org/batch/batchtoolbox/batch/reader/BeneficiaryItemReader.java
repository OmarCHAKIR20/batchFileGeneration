package org.batch.batchtoolbox.batch.reader;

import org.batch.batchtoolbox.bo.Beneficiary;
import org.batch.batchtoolbox.dao.BeneficiaryRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class BeneficiaryItemReader {

    private final BeneficiaryRepository repository;

    public BeneficiaryItemReader(BeneficiaryRepository repository) {
        this.repository = repository;
    }

    public ItemReader<Beneficiary> reader() {
        Map<String, Sort.Direction> sortMap = new HashMap<>();
        sortMap.put("depositDate", Sort.Direction.DESC);

        return new RepositoryItemReaderBuilder<Beneficiary>()
                .name("beneficiaryReader")
                .repository(repository)
                .methodName("findActiveBeneficiariesByField")
                .pageSize(100)
                .sorts(sortMap)
                .arguments(Arrays.asList("ACTIVE", "Computer Science"))
                .build();
    }
}

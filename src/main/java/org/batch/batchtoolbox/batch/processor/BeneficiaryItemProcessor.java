package org.batch.batchtoolbox.batch.processor;

import org.batch.batchtoolbox.bo.Beneficiary;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class BeneficiaryItemProcessor implements ItemProcessor<Beneficiary, Beneficiary> {

    @Override
    public Beneficiary process(Beneficiary beneficiary) {
        // You can transform or filter data here if needed
        // This example just passes the beneficiary through
        return beneficiary;
    }
}
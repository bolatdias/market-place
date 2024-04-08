package com.example.marketplace.config;

import com.example.marketplace.batch.CustomProductItemWriter;
import com.example.marketplace.model.Category;
import com.example.marketplace.model.Product;

import com.example.marketplace.payload.ProductBatch;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.JsonLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {


    private String inputProducts = "input/products.json";


    @Autowired
    private DataSource dataSource;

    @Bean
    public JsonItemReader<ProductBatch> reader() {
        return new JsonItemReaderBuilder<ProductBatch>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(ProductBatch.class))
                .name("jsonItemReader")
                .resource(new ClassPathResource(inputProducts))
                .build();

    }

    @Bean
    public ItemProcessor<ProductBatch, ProductBatch> processor() {
        return product -> {
            // Any logic
            return product;
        };
    }


    @Bean
    public ItemWriter<ProductBatch> writer() {
        return new CustomProductItemWriter(dataSource);
    }


    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step", jobRepository)
                .<ProductBatch, ProductBatch>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job job(JobRepository jobRepository, @Qualifier("step1") Step step1) {
        return new JobBuilder("job", jobRepository)
                .start(step1)
                .build();
    }
}

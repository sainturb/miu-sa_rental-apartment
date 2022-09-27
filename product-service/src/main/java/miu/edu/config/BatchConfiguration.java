package miu.edu.config;

import lombok.RequiredArgsConstructor;
import miu.edu.models.Product;
import miu.edu.models.ProductDTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfiguration {
    public final JobBuilderFactory jobBuilderFactory;
    public final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;
    @Bean
    public FlatFileItemReader<ProductDTO> reader() {
        return new FlatFileItemReaderBuilder<ProductDTO>()
                .name("productItemReader")
                .resource(new ClassPathResource("airbnb.csv"))
                .linesToSkip(1)
                .delimited()
                .names("id","total_occupancy","total_bedrooms","total_bathrooms","home_type","summary","address","has_tv","has_kitchen","has_air_con","has_heating","has_internet","price","owner_id","latitude","longitude","available_from","available_until")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<ProductDTO>() {{
                    setTargetType(ProductDTO.class);
                }})
                .build();
    }

    @Bean
    public ProductProcessor processor() {
        return new ProductProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Product> writer() {
        return new JdbcBatchItemWriterBuilder<Product>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO product (id,total_occupancy,total_bedrooms,total_bathrooms,home_type,summary,address,has_tv,has_kitchen,has_air_con,has_heating,has_internet,price,owner_id,latitude,longitude,available_from,available_until,rating) VALUES (:id,:totalOccupancy,:totalBedrooms,:totalBathrooms,:homeType,:summary,:address,:hasTv,:hasKitchen,:hasAirCon,:hasHeating,:hasInternet,:price,:ownerId,:latitude,:longitude,:availableFrom,:availableUntil,5) ON CONFLICT DO NOTHING")
                .dataSource(dataSource)
                .build();
    }

    @Bean(name="importData")
    public Job importDataJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importDataJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Product> writer) {
        return stepBuilderFactory.get("step1")
                .<ProductDTO, Product> chunk(1000)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }


}
package com.infybuzz.config;


import com.infybuzz.model.StudentCsv;
import com.infybuzz.model.StudentXml;
import com.infybuzz.writer.FirstItemWriterXml;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.sql.SQLOutput;

@Configuration
public class XMLJobs {
    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    FirstItemWriterXml firstItemWriterXml;

    @StepScope
   // @Bean
    public boolean setFileResource(@Value("#{jobParameters['inputFile']}") FileSystemResource fileSystemResource){
        String file = fileSystemResource.getFilename();
        String substr = file.substring(file.length()-3);
        Boolean isXml = false;
        if(substr.equals("xml")){
            isXml = true;
        }
        System.out.println(substr+"jvdjvdsjsbdsjf");
        return isXml;
    }

    @Bean
    public Job XmlChunkJob(){

        return jobBuilderFactory.get("XML Chunk Jobs")
                .incrementer((new RunIdIncrementer()))
                .start(XMlStep())
                .build();

    }
    public Step XMlStep(){
        return stepBuilderFactory.get("First Chunk Step")
                .<StudentXml, StudentXml>chunk(3)
                .reader(staxEventItemReaderXml(null))
                //.reader(jsonItemReader(null))
                //.reader(staxEventItemReader(null))
                //.processor(firstItemProcessor)
                .writer(firstItemWriterXml)
                .build();
    }
    @StepScope
    @Bean
    public StaxEventItemReader<StudentXml> staxEventItemReaderXml(
            @Value("#{jobParameters['input']}") FileSystemResource fileSystemResource
    )  {

        //String file = fileSystemResource.getFilename();
        //if(!file.substring(file.length()-3,file.length()).equals("xml")){
         //   System.out.println("file is not in xml format so not running xml reader");
            //throw new Exception("file not XML");
          //  return null;
        //}
       // Boolean checking = setFileResource(null);

        StaxEventItemReader<StudentXml> staxEventItemReader = new StaxEventItemReader<StudentXml>();
        staxEventItemReader.setResource(fileSystemResource);
        staxEventItemReader.setFragmentRootElementName("student");
        staxEventItemReader.setUnmarshaller(new Jaxb2Marshaller() {
            {
                setClassesToBeBound(StudentXml.class);
            }
        });
        return staxEventItemReader;
    }
}

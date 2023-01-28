package com.infybuzz.writer;

import com.infybuzz.model.StudentCsv;
import com.infybuzz.model.StudentXml;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FirstItemWriterXml implements ItemWriter<StudentXml> {

	@Override
	public void write(List<? extends StudentXml> items) throws Exception {

		System.out.println("Inside XML Item Writer");
		items.stream().forEach(System.out::println);
	}

}

package com.infybuzz.writer;

import java.util.List;

import com.infybuzz.model.StudentCsv;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.infybuzz.model.StudentXml;

@Component
public class FirstItemWriter implements ItemWriter<StudentCsv> {

	@Override
	public void write(List<? extends StudentCsv> items) throws Exception {
		System.out.println("Inside FlatFile Item Writer");
		items.stream().forEach(System.out::println);
	}

}

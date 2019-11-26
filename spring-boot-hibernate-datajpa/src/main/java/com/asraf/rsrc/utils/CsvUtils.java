package com.asraf.rsrc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.simpleflatmapper.csv.CsvParser;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public final class CsvUtils {
	private static final CsvMapper CSV_MAPPPER = new CsvMapper();

	public static <TObject> List<TObject> getObjectWithHeader(Class<TObject> clazz, MultipartFile file)
			throws IOException {
		String content = new String(file.getBytes());
		List<TObject> allValues = new ArrayList<>();
		CsvParser.mapTo(clazz).stream(content).forEach(row -> allValues.add(row));
		return allValues;
	}

	public static <TObject> List<TObject> getObjectWithHeader(Class<TObject> clazz, InputStream stream)
			throws IOException {
		CsvSchema schema = CSV_MAPPPER.schemaFor(clazz).withHeader().withColumnReordering(true);
		ObjectReader reader = CSV_MAPPPER.readerFor(clazz).with(schema);
		return reader.<TObject>readValues(stream).readAll();
	}
}

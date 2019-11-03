package com.asraf.rsrc.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.simpleflatmapper.csv.CsvParser;
import org.springframework.web.multipart.MultipartFile;

public final class CsvUtils {
	public static <TObject> List<TObject> getObjectWithHeader(MultipartFile file, Class<TObject> clazz)
			throws IOException {
		String content = new String(file.getBytes());
		List<TObject> allValues = new ArrayList<>();
		CsvParser.mapTo(clazz).stream(content).forEach(row -> allValues.add(row));
		return allValues;
	}
}

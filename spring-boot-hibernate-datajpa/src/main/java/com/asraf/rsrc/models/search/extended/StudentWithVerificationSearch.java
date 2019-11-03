package com.asraf.rsrc.models.search.extended;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.asraf.rsrc.models.search.StudentSearch;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StudentWithVerificationSearch extends StudentSearch {
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate creationTime;
}

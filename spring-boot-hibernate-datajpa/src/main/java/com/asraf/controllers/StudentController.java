package com.asraf.controllers;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.asraf.dtos.mapper.StudentMapper;
import com.asraf.dtos.request.entities.StudentRequestDto;
import com.asraf.dtos.response.entities.StudentResponseDto;
import com.asraf.entities.Student;
import com.asraf.models.pathvariable.ColumnPathvariable;
import com.asraf.models.search.StudentSearch;
import com.asraf.models.search.extended.StudentWithVerificationSearch;
import com.asraf.services.StudentService;
import com.asraf.services.email.EmailMessageBuilder;
import com.asraf.services.email.EmailSenderService;
import com.asraf.templates.HelloUserTemplate;

@RestController
@RequestMapping("/students")
public class StudentController {

	private StudentService studentService;
	private StudentMapper studentMappper;
	private EmailSenderService emailSenderService;
	private HelloUserTemplate helloUserTemplate;

	@Autowired
	public StudentController(StudentService studentService, StudentMapper studentMappper, EmailSenderService emailSenderService,
			HelloUserTemplate helloUserTemplate) {
		this.studentMappper = studentMappper;
		this.studentService = studentService;
		this.emailSenderService = emailSenderService;
		this.helloUserTemplate = helloUserTemplate;
	}

	@GetMapping("")
	public List<StudentResponseDto> getAll() {
		List<StudentResponseDto> response = studentMappper.getResponseDtos(this.studentService.getAll());
		return response;
	}

	@GetMapping("/get-by-email/{email:.+}")
	public StudentResponseDto getByEmail(@PathVariable String email) {
		Student student = studentService.getByEmail(email);
		return studentMappper.getResponseDto(student);
	}

	@GetMapping("/get-by-name/{name}")
	public List<StudentResponseDto> getByName(@PathVariable String name) {
		List<Student> students = studentService.getByNameContains(name);
		return studentMappper.getResponseDtos(students);
	}

	@GetMapping("/search-crud")
	public List<StudentResponseDto> getBySearchCrud(StudentSearch searchItem) {
		List<Student> students = studentService.getBySearchCrud(searchItem);
		return studentMappper.getResponseDtos(students);
	}

	/**
	 * @SampleUrl /students/search-crud-pageable?name=asraf&email=ahmed@test.com&page=0&size=4&sort=name,asc&sort=email,desc
	 * @param searchItem
	 * @param pageable
	 * @return
	 */
	@GetMapping("/search-crud-pageable")
	public Page<StudentResponseDto> getBySearchCrudPageable(StudentSearch searchItem, Pageable pageable) {
		Page<Student> pagedStudent = studentService.getBySearchCrudPageable(searchItem, pageable);
		return studentMappper.getResponseDtos(pagedStudent);
	}

	/**
	 * @SampleUrl /students/search-join-pageable?name=rats&email=ratul@test.com&creationTime=21-05-2018&page=0&size=2&sort=name,asc&sort=email,desc
	 * @param searchItem
	 * @param pageable
	 * @return
	 */
	@GetMapping("/search-join-pageable")
	public Page<StudentResponseDto> getBySearchJoinPageable(StudentWithVerificationSearch searchItem, Pageable pageable) {
		Page<Student> pagedStudent = this.studentService.getBySearchIntoJoiningTablePageable(searchItem, pageable);
		return studentMappper.getResponseDtos(pagedStudent);
	}

	/**
	 * @SampleUrl /students/query?search=(name==rats*;id>1,name==ratul);id=in=(2,3,4,5,6)&page=0&size=2&sort=name,asc&sort=email,desc
	 * @param search
	 * @param pageable
	 * @return
	 */
	@GetMapping("/query")
	public Page<StudentResponseDto> getByQuery(String search, Pageable pageable) {
		Page<Student> students = studentService.getByQuery(search, pageable);
		return studentMappper.getResponseDtos(students);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public StudentResponseDto create(@Valid @RequestBody StudentRequestDto requestDto) {
		Student student = studentMappper.getEntity(requestDto);
		studentService.save(student);
		return studentMappper.getResponseDto(student);
	}

	@DeleteMapping("/{id}")
	public StudentResponseDto delete(@PathVariable long id) {
		Student student = studentService.getById(id);
		StudentResponseDto response = studentMappper.getResponseDto(student);
		studentService.delete(student);
		return response;
	}

	@PutMapping("/{id}")
	public StudentResponseDto update(@PathVariable long id, @Valid @RequestBody StudentRequestDto requestDto) {
		Student student = studentService.getById(id);
		studentMappper.loadEntity(requestDto, student);
		studentService.save(student);
		return studentMappper.getResponseDto(student);
	}

	@GetMapping("/({columnName},{columnType})/distinct")
	public Page<Object> getByDistinctColumn(ColumnPathvariable columnPathvariable, Pageable pageable) {
		return studentService.getByDistinctColumn(columnPathvariable.getColumnName(),
				columnPathvariable.getColumnTypeEnum(), pageable);
	}

	@PostMapping("/send-email")
	public void sendEmail(@RequestBody @Valid StudentRequestDto requestDto)
			throws MessagingException, UnsupportedEncodingException {
		InternetAddress replyTo = new InternetAddress("noreply@auth.com", "no-reply");
		String subject = "Hello " + requestDto.getName();
		String body = helloUserTemplate.createTemplate(requestDto.getName());
		EmailMessageBuilder emailMessageBuilder = EmailMessageBuilder.builder().emailSubject(subject).emailBody(body)
				.isHtml(true).emailReplyTo(replyTo).emailFrom(null).build()
				.addEmailTo(requestDto.getEmail(), requestDto.getName()).buildMail(emailSenderService.getMimeMessage());
		helloUserTemplate.loadInlineImages(emailMessageBuilder);
		emailSenderService.send();
	}

}

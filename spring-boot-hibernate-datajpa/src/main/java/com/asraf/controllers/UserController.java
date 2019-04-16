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

import com.asraf.dtos.mapper.UserMapper;
import com.asraf.dtos.request.entities.UserRequestDto;
import com.asraf.dtos.response.entities.UserResponseDto;
import com.asraf.entities.UserEntity;
import com.asraf.models.pathvariable.ColumnPathvariable;
import com.asraf.models.search.UserSearch;
import com.asraf.models.search.extended.UserWithVerificationSearch;
import com.asraf.services.UserService;
import com.asraf.services.email.EmailMessageBuilder;
import com.asraf.services.email.EmailSenderService;
import com.asraf.templates.HelloUserTemplate;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserService userService;
	private UserMapper userMappper;
	private EmailSenderService emailSenderService;
	private HelloUserTemplate helloUserTemplate;

	@Autowired
	public UserController(UserService userService, UserMapper userMappper, EmailSenderService emailSenderService,
			HelloUserTemplate helloUserTemplate) {
		this.userMappper = userMappper;
		this.userService = userService;
		this.emailSenderService = emailSenderService;
		this.helloUserTemplate = helloUserTemplate;
	}

	@GetMapping("")
	public List<UserResponseDto> getAll() {
		List<UserResponseDto> response = userMappper.getResponseDtos(this.userService.getAll());
		return response;
	}

	@GetMapping("/get-by-email/{email:.+}")
	public UserResponseDto getByEmail(@PathVariable String email) {
		UserEntity user = userService.getByEmail(email);
		return userMappper.getResponseDto(user);
	}

	@GetMapping("/get-by-name/{name}")
	public List<UserResponseDto> getByName(@PathVariable String name) {
		List<UserEntity> users = userService.getByNameContains(name);
		return userMappper.getResponseDtos(users);
	}

	@GetMapping("/search-crud")
	public List<UserResponseDto> getBySearchCrud(UserSearch searchItem) {
		List<UserEntity> users = userService.getBySearchCrud(searchItem);
		return userMappper.getResponseDtos(users);
	}

	/**
	 * @SampleUrl /users/search-crud-pageable?name=asraf&email=ahmed@test.com&page=0&size=4&sort=name,asc&sort=email,desc
	 * @param searchItem
	 * @param pageable
	 * @return
	 */
	@GetMapping("/search-crud-pageable")
	public Page<UserResponseDto> getBySearchCrudPageable(UserSearch searchItem, Pageable pageable) {
		Page<UserEntity> pagedUser = userService.getBySearchCrudPageable(searchItem, pageable);
		return userMappper.getResponseDtos(pagedUser);
	}

	/**
	 * @SampleUrl /users/search-join-pageable?name=rats&email=ratul@test.com&creationTime=21-05-2018&page=0&size=2&sort=name,asc&sort=email,desc
	 * @param searchItem
	 * @param pageable
	 * @return
	 */
	@GetMapping("/search-join-pageable")
	public Page<UserResponseDto> getBySearchJoinPageable(UserWithVerificationSearch searchItem, Pageable pageable) {
		Page<UserEntity> pagedUser = this.userService.getBySearchIntoJoiningTablePageable(searchItem, pageable);
		return userMappper.getResponseDtos(pagedUser);
	}

	/**
	 * @SampleUrl /users/query?search=(name==rats*;id>1,name==ratul);id=in=(2,3,4,5,6)&page=0&size=2&sort=name,asc&sort=email,desc
	 * @param search
	 * @param pageable
	 * @return
	 */
	@GetMapping("/query")
	public Page<UserResponseDto> getByQuery(String search, Pageable pageable) {
		Page<UserEntity> users = userService.getByQuery(search, pageable);
		return userMappper.getResponseDtos(users);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public UserResponseDto create(@Valid @RequestBody UserRequestDto requestDto) {
		UserEntity user = userMappper.getEntity(requestDto);
		userService.save(user);
		return userMappper.getResponseDto(user);
	}

	@DeleteMapping("/{id}")
	public UserResponseDto delete(@PathVariable long id) {
		UserEntity user = userService.getById(id);
		UserResponseDto response = userMappper.getResponseDto(user);
		userService.delete(user);
		return response;
	}

	@PutMapping("/{id}")
	public UserResponseDto update(@PathVariable long id, @Valid @RequestBody UserRequestDto requestDto) {
		UserEntity user = userService.getById(id);
		userMappper.loadEntity(requestDto, user);
		userService.save(user);
		return userMappper.getResponseDto(user);
	}

	@GetMapping("/({columnName},{columnType})/distinct")
	public Page<Object> getByDistinctColumn(ColumnPathvariable columnPathvariable, Pageable pageable) {
		return userService.getByDistinctColumn(columnPathvariable.getColumnName(),
				columnPathvariable.getColumnTypeEnum(), pageable);
	}

	@PostMapping("/send-email")
	public void sendEmail(@RequestBody @Valid UserRequestDto requestDto)
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

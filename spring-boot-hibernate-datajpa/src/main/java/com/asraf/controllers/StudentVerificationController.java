package com.asraf.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
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

import com.asraf.dtos.mapper.StudentVerificationMapper;
import com.asraf.dtos.request.entities.StudentVerificationRequestDto;
import com.asraf.dtos.response.requestdto.RequestBodyResponseDto;
import com.asraf.dtos.response.requestdto.RequestDataCollectionResponseDto;
import com.asraf.entities.StudentVerification;
import com.asraf.resources.assemblers.entities.StudentVerificationResourceAssembler;
import com.asraf.resources.entities.StudentVerificationResource;
import com.asraf.services.StudentVerificationService;

@RestController
@RequestMapping("/student-verifications")
public class StudentVerificationController extends BaseController {

	private final StudentVerificationService studentVerificationService;
	private final StudentVerificationMapper studentVerificationMappper;
	private final StudentVerificationResourceAssembler studentVerificationResourceAssembler;

	@Autowired
	public StudentVerificationController(StudentVerificationService studentVerificationService,
			StudentVerificationMapper studentVerificationMappper,
			StudentVerificationResourceAssembler studentVerificationResourceAssembler) {
		this.studentVerificationMappper = studentVerificationMappper;
		this.studentVerificationService = studentVerificationService;
		this.studentVerificationResourceAssembler = studentVerificationResourceAssembler;
	}

	@GetMapping("")
	public PagedResources<StudentVerificationResource> getByQuery(String search, Pageable pageable,
			PagedResourcesAssembler<StudentVerification> pagedAssembler) {
		Page<StudentVerification> studentVerifications = studentVerificationService.getByQuery(search, pageable);
		return pagedAssembler.toResource(studentVerifications, this.studentVerificationResourceAssembler);
	}

	@GetMapping("/all")
	public List<StudentVerificationResource> getAll() {
		List<StudentVerification> studentVerifications = (List<StudentVerification>) this.studentVerificationService.getAll();
		return this.studentVerificationResourceAssembler.toResources(studentVerifications);
	}

	@GetMapping("/{id}")
	public StudentVerificationResource getById(@PathVariable long id) {
		StudentVerification studentVerification = studentVerificationService.getById(id);
		return this.studentVerificationResourceAssembler.toResource(studentVerification);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public StudentVerificationResource create(@Valid @RequestBody StudentVerificationRequestDto requestDto) {
		StudentVerification studentVerification = studentVerificationMappper.getEntity(requestDto);
		studentVerificationService.save(studentVerification);
		return this.studentVerificationResourceAssembler.toResource(studentVerification);
	}

	@DeleteMapping("/{id}")
	public StudentVerificationResource delete(@PathVariable long id) {
		StudentVerification studentVerification = studentVerificationService.getById(id);
		StudentVerificationResource response = this.studentVerificationResourceAssembler.toResource(studentVerification)
				.forDeletion();
		studentVerificationService.delete(studentVerification);
		return response;
	}

	@PutMapping("/{id}")
	public StudentVerificationResource update(@PathVariable long id,
			@Valid @RequestBody StudentVerificationRequestDto requestDto) {
		StudentVerification studentVerification = studentVerificationService.getById(id);
		studentVerificationMappper.loadEntity(requestDto, studentVerification);
		studentVerificationService.save(studentVerification);
		return this.studentVerificationResourceAssembler.toResource(studentVerification);
	}

	@GetMapping("/requests")
	public RequestDataCollectionResponseDto getRequests() {
		RequestDataCollectionResponseDto requestDataCollection = new RequestDataCollectionResponseDto();
		this.addRequestDataOfPost(requestDataCollection);
		return requestDataCollection;
	}

	private StudentVerificationController addRequestDataOfPost(RequestDataCollectionResponseDto requestDataCollection) {
		RequestBodyResponseDto<StudentVerificationRequestDto> requestBody = new RequestBodyResponseDto<StudentVerificationRequestDto>(
				StudentVerificationRequestDto.class);
		URI uri = linkTo(methodOn(StudentVerificationController.class).create(null)).toUri();
		requestDataCollection.addRequest(uri, org.springframework.http.HttpMethod.POST, requestBody);
		return this;
	}

}

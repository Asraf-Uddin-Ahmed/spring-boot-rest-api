package com.asraf.controllers;

import java.time.Duration;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asraf.aop.SecuredByRole;
import com.asraf.constants.RoleAuthority;
import com.asraf.dtos.response.entities.StudentResponseDto;
import com.asraf.resources.main.MainResource;
import com.asraf.services.sse.ScheduledService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("")
public class MainController {

	@Autowired
	private ScheduledService scheduledService;

	@GetMapping("")
	public MainResource getAllLinks() {
		return new MainResource();
	}

	@GetMapping(value = "/main/{id}", produces = { "application/asraf.v1+json", "application/asraf.v1+xml" })
	public StudentResponseDto getById(@PathVariable("id") long id) {
		StudentResponseDto response = new StudentResponseDto();
		response.setId(id);
		response.setName("getById Version #1");
		return response;
	}

	@GetMapping(value = "/main/{id}", produces = { "application/asraf.v2+json", "application/asraf.v2+xml" })
	public StudentResponseDto getByIdV2(@PathVariable("id") long id) {
		StudentResponseDto response = new StudentResponseDto();
		response.setId(id);
		response.setName("getById Version #2");
		return response;
	}

	@PostMapping("/main")
	public String create() {
		return "create";
	}

	@PutMapping("/main")
	public String update() {
		return "update";
	}

	@SecuredByRole({RoleAuthority.ADMIN})
	@DeleteMapping("/main/{id}")
	public String delete(@PathVariable("id") String id) {
		return "delete -> " + id;
	}

	@GetMapping(path = "/main/stream-flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<StudentResponseDto> streamFlux() {
		System.err.println("FLUX");
		return Flux.interval(Duration.ofSeconds(3)).map(sequence -> {
			System.err.println("Flux - " + LocalTime.now().toString());
			StudentResponseDto response = new StudentResponseDto();
			response.setId(sequence);
			response.setName("ব্যবহারকারীর");
			return response;
		});
	}

	@GetMapping(value = "/main/stream-sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<StudentResponseDto> streamEvents() {
		System.err.println("SSE");
		return scheduledService.getInfiniteUserResponseDtos();
	}

}

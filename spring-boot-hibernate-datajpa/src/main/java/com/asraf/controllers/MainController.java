package com.asraf.controllers;

import java.time.Duration;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asraf.dtos.response.entities.UserResponseDto;
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

	@GetMapping("/main/{id}")
	public String getById(@PathVariable("id") String id) {
		return "getById -> " + id;
	}

	@PostMapping("/main")
	public String create() {
		return "create";
	}

	@PutMapping("/main")
	public String update() {
		return "update";
	}

	@DeleteMapping("/main/{id}")
	public String delete(@PathVariable("id") String id) {
		return "delete -> " + id;
	}

	@GetMapping(path = "/main/stream-flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<UserResponseDto> streamFlux() {
		System.err.println("FLUX");
		return Flux.interval(Duration.ofSeconds(1)).map(sequence -> {
			System.err.println("Flux - " + LocalTime.now().toString());
			UserResponseDto response = new UserResponseDto();
			response.setId(sequence);
			response.setName("ব্যবহারকারীর");
			return response;
		});
	}

	@GetMapping(value = "/main/stream-sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<UserResponseDto> streamEvents() {
		System.err.println("SSE");
		return scheduledService.getInfiniteUserResponseDtos();
	}

}

package com.asraf.services.sse;

import java.time.LocalTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.asraf.dtos.response.entities.UserResponseDto;

import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

@Component
public class ScheduledService {

	private final EmitterProcessor<UserResponseDto> emitter;

	public ScheduledService() {
		emitter = EmitterProcessor.create();
	}

	public Flux<UserResponseDto> getInfiniteUserResponseDtos() {
		return emitter.log();
	}

	@Scheduled(fixedRate = 5000)
	void timerHandler() {
		System.err.println("SSE scheduler - " + LocalTime.now().toString());
		try {
			UserResponseDto response = new UserResponseDto();
			response.setName("ব্যবহারকারীর");
			emitter.onNext(response);
		} catch (Exception e) {
			emitter.onError(e);
		}
	}
}

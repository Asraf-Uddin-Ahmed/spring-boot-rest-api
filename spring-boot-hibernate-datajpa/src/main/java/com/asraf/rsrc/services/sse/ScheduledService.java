package com.asraf.rsrc.services.sse;

import java.time.LocalTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.asraf.rsrc.dtos.response.entities.StudentResponseDto;

import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

@Component
public class ScheduledService {

	private final EmitterProcessor<StudentResponseDto> emitter;

	public ScheduledService() {
		emitter = EmitterProcessor.create();
	}

	public Flux<StudentResponseDto> getInfiniteUserResponseDtos() {
		return emitter.log();
	}

	@Scheduled(fixedRate = 5000)
	void timerHandler() {
		System.err.println("SSE scheduler - " + LocalTime.now().toString());
		try {
			StudentResponseDto response = new StudentResponseDto();
			response.setName("ব্যবহারকারীর");
			emitter.onNext(response);
		} catch (Exception e) {
			emitter.onError(e);
		}
	}
}

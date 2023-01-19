package edu.dal.eauction.eventManagement.dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class EventCreationDto {
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	public EventCreationDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EventCreationDto(LocalDateTime startTime, LocalDateTime endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}


	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "EventDto [startTime=" + startTime + ", endTime=" + endTime + "]";
	}

}

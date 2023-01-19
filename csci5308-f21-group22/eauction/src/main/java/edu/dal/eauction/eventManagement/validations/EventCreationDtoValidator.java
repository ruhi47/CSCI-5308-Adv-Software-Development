package edu.dal.eauction.eventManagement.validations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventCreationDtoValidator {

	public EventCreationDtoValidator() {

	}

	LocalDateTime startTime = null;
	private List<String> errors = new ArrayList<>();

	public List<String> validateStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
		LocalDateTime now = LocalDateTime.now();
		if (startTime.toString().isBlank()) {
			errors.add("Please enter start date and time for the bidding event");
		} else if (startTime.compareTo(now) < 0) {
			errors.add("Please enter future start event date and time");
		}
		return errors;
	}

	public List<String> validateEndTime(LocalDateTime endTime) {
		LocalDateTime now = LocalDateTime.now();
		if (endTime.toString().isBlank()) {
			errors.add("Please enter start date and time for the bidding event");
		} else if (endTime.compareTo(now) < 0) {
			errors.add("Please enter future start event date and time");
		} else if (endTime.compareTo(startTime) < 0) {
			errors.add("The end date and time should be ahead of the start date and time");
		}
		return errors;
	}
}

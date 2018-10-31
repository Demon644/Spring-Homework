package ua.logos.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ErrorDTO {

	private String message;
	private LocalDateTime timestamp;

	public ErrorDTO(String message) {
		this.message = message;
		this.timestamp = LocalDateTime.now();
	}

}

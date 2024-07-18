package com.paymybuddyapp.paymybuddy.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Connection {
	
	@EmbeddedId
	private ConnectionID id;
	
	@Embeddable
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ConnectionID implements Serializable {
		private Long userid;
		private Long connectionId;
	}
	
}

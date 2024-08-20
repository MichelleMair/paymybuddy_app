package com.paymybuddyapp.paymybuddy.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

	@ManyToOne
	@JoinColumn(name = "userid", insertable = false, updatable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "connectionId", insertable = false, updatable = false)
	private User connection;

}

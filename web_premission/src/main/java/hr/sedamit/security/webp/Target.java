package hr.sedamit.security.webp;

import lombok.Data;

@Data
public class Target {
	private String name;
	private Long id;
	
	private String ownerId;

	private String externalId;
	
}

package io.github.akjo03.akjonav.base.model;

import io.github.akjo03.akjonav.base.util.DocumentID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RestUser {
	@DocumentID
	private String id;
	private String username;
	private String password;
	private List<String> roles;
}
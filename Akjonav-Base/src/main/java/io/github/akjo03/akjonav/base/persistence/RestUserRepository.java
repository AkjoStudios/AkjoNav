package io.github.akjo03.akjonav.base.persistence;

import com.google.cloud.firestore.Firestore;
import io.github.akjo03.akjonav.base.model.RestUser;
import io.github.akjo03.util.logging.v2.Logger;
import io.github.akjo03.util.logging.v2.LoggerManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public class RestUserRepository extends AbstractFirestoreRepository<RestUser> {
	private static final Logger LOGGER = LoggerManager.getLogger(RestUserRepository.class);

	protected RestUserRepository(@NotNull Firestore firestore) {
		super(firestore, "RestUser");
	}

	public @Nullable RestUser getByUsername(@NotNull String username) {
		return retrieveAll().stream()
				.filter(user -> user.getUsername().equals(username))
				.findFirst()
				.orElse(null);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
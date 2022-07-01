package io.github.akjo03.akjonav.base.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirestoreConfig {
	@Bean
	public Firestore getFirestore(@Value("${firebase.credential.path}") String credentialPath) throws IOException {
		FileInputStream serviceAccount = new FileInputStream(credentialPath);
		GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
		FirestoreOptions options = FirestoreOptions.newBuilder()
				.setCredentials(credentials)
				.build();
		return options.getService();
	}
}
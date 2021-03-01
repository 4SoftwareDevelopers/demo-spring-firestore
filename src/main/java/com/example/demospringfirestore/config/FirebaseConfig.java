package com.example.demospringfirestore.config;

import java.io.FileInputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

@Configuration
public class FirebaseConfig {

	@Bean
	public Firestore firestore() throws Exception {

		FileInputStream serviceAccount = new FileInputStream("./firebase-account-info.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

		FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);
		
		return FirestoreClient.getFirestore(firebaseApp);

	}

}

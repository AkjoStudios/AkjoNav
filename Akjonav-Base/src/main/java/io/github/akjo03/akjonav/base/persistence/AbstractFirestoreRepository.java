package io.github.akjo03.akjonav.base.persistence;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import io.github.akjo03.akjonav.base.util.DocumentID;
import io.github.akjo03.util.logging.v2.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.concurrent.ExecutionException;

@SuppressWarnings("unused")
public abstract class AbstractFirestoreRepository<T> {
	private final CollectionReference collectionReference;
	private final String collectionName;
	private final Class<T> parameterizedType;

	protected AbstractFirestoreRepository(@NotNull Firestore firestore, @NotNull String collectionName) {
		this.collectionReference = firestore.collection(collectionName);
		this.collectionName = collectionName;
		this.parameterizedType = getParameterizedType();
	}

	protected abstract Logger getLogger();

	public boolean save(T document) {
		String documentID = getDocumentID(document);
		ApiFuture<WriteResult> resultApiFuture = collectionReference.document(documentID).set(document);

		try {
			WriteResult result = resultApiFuture.get();
			getLogger().info("Saved document " + collectionName + "-" + documentID + " at " + result.getUpdateTime());
			return true;
		} catch (InterruptedException | ExecutionException e) {
			getLogger().error("Failed to save document " + collectionName + "-" + documentID + "!", e);
			return false;
		}
	}

	public boolean delete(T document) {
		String documentID = getDocumentID(document);
		ApiFuture<WriteResult> resultApiFuture = collectionReference.document(documentID).delete();

		try {
			WriteResult result = resultApiFuture.get();
			getLogger().info("Deleted document " + collectionName + "-" + documentID + " at " + result.getUpdateTime());
			return true;
		} catch (InterruptedException | ExecutionException e) {
			getLogger().error("Failed to delete document " + collectionName + "-" + documentID + "!", e);
			return false;
		}
	}

	public Optional<T> retrieve(String documentID) {
		DocumentReference documentReference = collectionReference.document(documentID);
		ApiFuture<DocumentSnapshot> resultApiFuture = documentReference.get();

		try {
			DocumentSnapshot documentSnapshot = resultApiFuture.get();
			if (documentSnapshot.exists()) {
				return Optional.ofNullable(documentSnapshot.toObject(parameterizedType));
			}
		} catch (InterruptedException | ExecutionException e) {
			getLogger().error("Failed to retrieve document " + collectionName + "-" + documentID + "!", e);
		}

		return Optional.empty();
	}

	public List<T> retrieveAll() {
		ApiFuture<QuerySnapshot> resultApiFuture = collectionReference.get();

		try {
			List<QueryDocumentSnapshot> queryDocumentSnapshots = resultApiFuture.get().getDocuments();

			List<T> queryResults = new ArrayList<>();
			for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
				String documentID = queryDocumentSnapshot.getId();
				T document = queryDocumentSnapshot.toObject(parameterizedType);
				Arrays.stream(getParameterizedType().getDeclaredFields()).toList().forEach(field -> {
					if (field.isAnnotationPresent(DocumentID.class)) {
						field.setAccessible(true);
						try {
							field.set(document, documentID);
						} catch (IllegalAccessException e) {
							getLogger().error("Failed to set document ID for retrieved element with id " + documentID + "!", e);
						}
					}
				});
				queryResults.add(document);
			}
			return queryResults;
		} catch (InterruptedException | ExecutionException e) {
			getLogger().error("Failed to retrieve all documents for " + collectionName + "!", e);
		}

		return Collections.emptyList();
	}

	@SuppressWarnings("unchecked")
	private Class<T> getParameterizedType() {
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class<T>) type.getActualTypeArguments()[0];
	}

	protected String getDocumentID(@NotNull T entity) {
		Object key;
		Class<?> clazz = entity.getClass();
		do {
			key = getKeyFromFields(clazz, entity);
			clazz = clazz.getSuperclass();
		} while (key == null && clazz != null);

		if (key == null) {
			return UUID.randomUUID().toString();
		}
		return String.valueOf(key);
	}

	private Object getKeyFromFields(@NotNull Class<?> clazz, Object t) {
		return Arrays.stream(clazz.getDeclaredFields())
				.filter(field -> field.isAnnotationPresent(DocumentID.class))
				.findFirst()
				.map(field -> getValue(t, field))
				.orElse(null);
	}

	private @Nullable Object getValue(Object t, @NotNull Field field) {
		field.setAccessible(true);
		try {
			return field.get(t);
		} catch (IllegalAccessException e) {
			getLogger().error("Error on getting documentID for key: " + field.getName(), e);
			return null;
		}
	}

	protected CollectionReference getCollectionReference() {
		return collectionReference;
	}

	protected Class<T> getType() {
		return this.parameterizedType;
	}
}
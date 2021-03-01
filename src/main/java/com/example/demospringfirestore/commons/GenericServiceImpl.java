package com.example.demospringfirestore.commons;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

public abstract class GenericServiceImpl<I, O> implements GenericServiceAPI<I, O> {

	public Class<O> clazz;

	@SuppressWarnings("unchecked")
	public GenericServiceImpl() {
		this.clazz = ((Class<O>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1]);
	}

	@Override
	public String save(I entity) throws Exception {
		return this.save(entity, null);
	}

	@Override
	public String save(I entity, String id) throws Exception {
		if (id == null || id.length() == 0) {
			return getCollection().add(entity).get().getId();
		}

		DocumentReference reference = getCollection().document(id);
		reference.set(entity);
		return reference.getId();
	}

	@Override
	public void delete(String id) throws Exception {
		getCollection().document(id).delete().get();
	}

	@Override
	public O get(String id) throws Exception {
		DocumentReference ref = getCollection().document(id);
		ApiFuture<DocumentSnapshot> futureDoc = ref.get();
		DocumentSnapshot document = futureDoc.get();
		if (document.exists()) {
			O object = document.toObject(clazz);
			PropertyUtils.setProperty(object, "id", document.getId());
			return object;
		}
		return null;
	}

	@Override
	public List<O> getAll() throws Exception {
		List<O> result = new ArrayList<O>();
		ApiFuture<QuerySnapshot> query = getCollection().get();
		List<QueryDocumentSnapshot> documents = query.get().getDocuments();
		for (QueryDocumentSnapshot doc : documents) {
			O object = doc.toObject(clazz);
			PropertyUtils.setProperty(object, "id", doc.getId());
			result.add(object);
		}
		return result;
	}

	@Override
	public Map<String, Object> getAsMap(String id) throws Exception {
		DocumentReference reference = getCollection().document(id);
		ApiFuture<DocumentSnapshot> futureDoc = reference.get();
		DocumentSnapshot document = futureDoc.get();
		if (document.exists()) {
			return document.getData();
		}
		return null;
	}

	public abstract CollectionReference getCollection();
}

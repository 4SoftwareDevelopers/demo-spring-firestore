package com.example.demospringfirestore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demospringfirestore.commons.GenericServiceImpl;
import com.example.demospringfirestore.dto.PersonaDTO;
import com.example.demospringfirestore.model.Persona;
import com.example.demospringfirestore.service.api.PersonaServiceAPI;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;

@Service
public class PersonaServiceImpl extends GenericServiceImpl<Persona, PersonaDTO> implements PersonaServiceAPI {
	
	@Autowired
	private Firestore firestore;

	@Override
	public CollectionReference getCollection() {
		return firestore.collection("personas");
	}

}

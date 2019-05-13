package com.netcracker.vacations.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.AbstractDataStore;
import com.google.api.client.util.store.DataStoreFactory;
import com.netcracker.vacations.domain.GoogleCredential;
import com.netcracker.vacations.repository.GoogleCredentialRepository;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class MySqlCredentialsDataStore<V extends Serializable> extends AbstractDataStore<V> {

    private GoogleCredentialRepository googleCredentialRepository;

    /**
     * @param dataStoreFactory data store factory
     * @param id               data store ID
     */
    protected MySqlCredentialsDataStore(DataStoreFactory dataStoreFactory, String id,
                                        GoogleCredentialRepository googleCredentialRepository) {
        super(dataStoreFactory, id);
        this.googleCredentialRepository = googleCredentialRepository;
    }


    @Override
    public Set<String> keySet() throws IOException {
        Iterable<GoogleCredential> list = googleCredentialRepository.findAll();
        Set<String> result = new HashSet<>();
        for (GoogleCredential credential : list) {
            result.add(credential.getValueId());
        }
        return Collections.unmodifiableSet(result);
    }

    @Override
    public Collection<V> values() throws IOException {
        Iterable<GoogleCredential> list = googleCredentialRepository.findAll();
        List<V> result = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for (GoogleCredential credential : list) {
//            result.add(mapper.readValue(credential.getValue(), new TypeReference<V>(){}));
            result.add((V) mapper.readValue(credential.getValue(), StoredCredential.class));
        }
        return Collections.unmodifiableList(result);
    }

    @Override
    public V get(String key) throws IOException {
        if (key == null) {
            return null;
        }
        GoogleCredential googleCredential = googleCredentialRepository.findAllByValueId(key).get(0);
        if (googleCredential == null) return null;
        String jsonStr = googleCredential.getValue();
        ObjectMapper mapper = new ObjectMapper();
        StoredCredential storedCredential = mapper.readValue(jsonStr, StoredCredential.class);
        return (V) storedCredential;
    }

    @Override
    public com.google.api.client.util.store.DataStore<V> set(String key, V value) throws IOException {
        GoogleCredential googleCredential = new GoogleCredential();
        googleCredential.setValueId(key);
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(value);
        googleCredential.setValue(jsonStr);
        googleCredentialRepository.save(googleCredential);
        return this;
    }

    @Override
    public com.google.api.client.util.store.DataStore<V> clear() throws IOException {
        googleCredentialRepository.deleteAll();
        return this;
    }

    @Override
    public com.google.api.client.util.store.DataStore<V> delete(String key) throws IOException {
        googleCredentialRepository.deleteById(key);
        return this;
    }
}

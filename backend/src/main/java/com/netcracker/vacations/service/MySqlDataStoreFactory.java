package com.netcracker.vacations.service;

import com.google.api.client.util.store.AbstractDataStoreFactory;
import com.google.api.client.util.store.DataStore;
import com.netcracker.vacations.repository.GoogleCredentialRepository;

import java.io.IOException;
import java.io.Serializable;

public class MySqlDataStoreFactory extends AbstractDataStoreFactory {

    private GoogleCredentialRepository googleCredentialRepository;

    public MySqlDataStoreFactory(GoogleCredentialRepository googleCredentialRepository) {
        this.googleCredentialRepository = googleCredentialRepository;
    }

    @Override
    protected <V extends Serializable> DataStore<V> createDataStore(String id) throws IOException {
        return new MySqlCredentialsDataStore<>(this, id, googleCredentialRepository);
    }
}

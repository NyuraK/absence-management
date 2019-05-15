package com.netcracker.vacations.service;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.AbstractDataStoreFactory;
import com.google.api.client.util.store.DataStore;
import com.netcracker.vacations.repository.GoogleCredentialRepository;

import java.io.IOException;
import java.io.Serializable;

public class MySqlDataStoreFactory extends AbstractDataStoreFactory {

    private GoogleCredentialRepository googleCredentialRepository;

    MySqlDataStoreFactory(GoogleCredentialRepository googleCredentialRepository) {
        this.googleCredentialRepository = googleCredentialRepository;
    }

    @Override
    protected  DataStore<StoredCredential> createDataStore(String id) {
        return new MySqlCredentialsDataStore(this, id, googleCredentialRepository);
    }
}

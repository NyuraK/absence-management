package com.netcracker.vacations.repository;

import com.netcracker.vacations.domain.GoogleCredential;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GoogleCredentialRepository extends CrudRepository<GoogleCredential, String> {
    List<GoogleCredential> findAllByValueId(String id);
}

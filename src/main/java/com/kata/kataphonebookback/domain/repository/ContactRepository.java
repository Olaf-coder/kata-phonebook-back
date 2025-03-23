package com.kata.kataphonebookback.domain.repository;

import com.kata.kataphonebookback.domain.model.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("com.kata.kataphonebookback.domain.repository.ContactRepository")
public interface ContactRepository extends JpaRepository<ContactEntity, Long> {
    List<ContactEntity> findAll();

    ContactEntity getReferenceById(@Param("id") Long id);

    void deleteById(@Param("id") Long id);

}

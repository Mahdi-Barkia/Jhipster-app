package com.isi.app.service;

import com.isi.app.domain.Instructor;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Instructor}.
 */
public interface InstructorService {

    /**
     * Save a instructor.
     *
     * @param instructor the entity to save.
     * @return the persisted entity.
     */
    Instructor save(Instructor instructor);

    /**
     * Get all the instructors.
     *
     * @return the list of entities.
     */
    List<Instructor> findAll();


    /**
     * Get the "id" instructor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Instructor> findOne(Long id);

    /**
     * Delete the "id" instructor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

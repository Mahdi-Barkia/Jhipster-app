package com.isi.app.service.impl;

import com.isi.app.service.InstructorService;
import com.isi.app.domain.Instructor;
import com.isi.app.repository.InstructorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Instructor}.
 */
@Service
@Transactional
public class InstructorServiceImpl implements InstructorService {

    private final Logger log = LoggerFactory.getLogger(InstructorServiceImpl.class);

    private final InstructorRepository instructorRepository;

    public InstructorServiceImpl(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @Override
    public Instructor save(Instructor instructor) {
        log.debug("Request to save Instructor : {}", instructor);
        return instructorRepository.save(instructor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Instructor> findAll() {
        log.debug("Request to get all Instructors");
        return instructorRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Instructor> findOne(Long id) {
        log.debug("Request to get Instructor : {}", id);
        return instructorRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Instructor : {}", id);
        instructorRepository.deleteById(id);
    }
}

package com.isi.app.web.rest;

import com.isi.app.SoaAppApp;
import com.isi.app.domain.Instructor;
import com.isi.app.domain.Course;
import com.isi.app.repository.InstructorRepository;
import com.isi.app.service.InstructorService;
import com.isi.app.service.dto.InstructorCriteria;
import com.isi.app.service.InstructorQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link InstructorResource} REST controller.
 */
@SpringBootTest(classes = SoaAppApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InstructorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 18;
    private static final Integer UPDATED_AGE = 19;
    private static final Integer SMALLER_AGE = 18 - 1;

    private static final String DEFAULT_SKILLS = "AAAAAAAAAA";
    private static final String UPDATED_SKILLS = "BBBBBBBBBB";

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private InstructorQueryService instructorQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInstructorMockMvc;

    private Instructor instructor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Instructor createEntity(EntityManager em) {
        Instructor instructor = new Instructor()
            .name(DEFAULT_NAME)
            .age(DEFAULT_AGE)
            .skills(DEFAULT_SKILLS);
        return instructor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Instructor createUpdatedEntity(EntityManager em) {
        Instructor instructor = new Instructor()
            .name(UPDATED_NAME)
            .age(UPDATED_AGE)
            .skills(UPDATED_SKILLS);
        return instructor;
    }

    @BeforeEach
    public void initTest() {
        instructor = createEntity(em);
    }

    @Test
    @Transactional
    public void createInstructor() throws Exception {
        int databaseSizeBeforeCreate = instructorRepository.findAll().size();
        // Create the Instructor
        restInstructorMockMvc.perform(post("/api/instructors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instructor)))
            .andExpect(status().isCreated());

        // Validate the Instructor in the database
        List<Instructor> instructorList = instructorRepository.findAll();
        assertThat(instructorList).hasSize(databaseSizeBeforeCreate + 1);
        Instructor testInstructor = instructorList.get(instructorList.size() - 1);
        assertThat(testInstructor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInstructor.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testInstructor.getSkills()).isEqualTo(DEFAULT_SKILLS);
    }

    @Test
    @Transactional
    public void createInstructorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = instructorRepository.findAll().size();

        // Create the Instructor with an existing ID
        instructor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstructorMockMvc.perform(post("/api/instructors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instructor)))
            .andExpect(status().isBadRequest());

        // Validate the Instructor in the database
        List<Instructor> instructorList = instructorRepository.findAll();
        assertThat(instructorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instructorRepository.findAll().size();
        // set the field null
        instructor.setName(null);

        // Create the Instructor, which fails.


        restInstructorMockMvc.perform(post("/api/instructors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instructor)))
            .andExpect(status().isBadRequest());

        List<Instructor> instructorList = instructorRepository.findAll();
        assertThat(instructorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = instructorRepository.findAll().size();
        // set the field null
        instructor.setAge(null);

        // Create the Instructor, which fails.


        restInstructorMockMvc.perform(post("/api/instructors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instructor)))
            .andExpect(status().isBadRequest());

        List<Instructor> instructorList = instructorRepository.findAll();
        assertThat(instructorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSkillsIsRequired() throws Exception {
        int databaseSizeBeforeTest = instructorRepository.findAll().size();
        // set the field null
        instructor.setSkills(null);

        // Create the Instructor, which fails.


        restInstructorMockMvc.perform(post("/api/instructors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instructor)))
            .andExpect(status().isBadRequest());

        List<Instructor> instructorList = instructorRepository.findAll();
        assertThat(instructorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstructors() throws Exception {
        // Initialize the database
        instructorRepository.saveAndFlush(instructor);

        // Get all the instructorList
        restInstructorMockMvc.perform(get("/api/instructors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instructor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].skills").value(hasItem(DEFAULT_SKILLS)));
    }
    
    @Test
    @Transactional
    public void getInstructor() throws Exception {
        // Initialize the database
        instructorRepository.saveAndFlush(instructor);

        // Get the instructor
        restInstructorMockMvc.perform(get("/api/instructors/{id}", instructor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(instructor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.skills").value(DEFAULT_SKILLS));
    }


    @Test
    @Transactional
    public void getInstructorsByIdFiltering() throws Exception {
        // Initialize the database
        instructorRepository.saveAndFlush(instructor);

        Long id = instructor.getId();

        defaultInstructorShouldBeFound("id.equals=" + id);
        defaultInstructorShouldNotBeFound("id.notEquals=" + id);

        defaultInstructorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInstructorShouldNotBeFound("id.greaterThan=" + id);

        defaultInstructorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInstructorShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllInstructorsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        instructorRepository.saveAndFlush(instructor);

        // Get all the instructorList where name equals to DEFAULT_NAME
        defaultInstructorShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the instructorList where name equals to UPDATED_NAME
        defaultInstructorShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllInstructorsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        instructorRepository.saveAndFlush(instructor);

        // Get all the instructorList where name not equals to DEFAULT_NAME
        defaultInstructorShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the instructorList where name not equals to UPDATED_NAME
        defaultInstructorShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllInstructorsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        instructorRepository.saveAndFlush(instructor);

        // Get all the instructorList where name in DEFAULT_NAME or UPDATED_NAME
        defaultInstructorShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the instructorList where name equals to UPDATED_NAME
        defaultInstructorShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllInstructorsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        instructorRepository.saveAndFlush(instructor);

        // Get all the instructorList where name is not null
        defaultInstructorShouldBeFound("name.specified=true");

        // Get all the instructorList where name is null
        defaultInstructorShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllInstructorsByNameContainsSomething() throws Exception {
        // Initialize the database
        instructorRepository.saveAndFlush(instructor);

        // Get all the instructorList where name contains DEFAULT_NAME
        defaultInstructorShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the instructorList where name contains UPDATED_NAME
        defaultInstructorShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllInstructorsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        instructorRepository.saveAndFlush(instructor);

        // Get all the instructorList where name does not contain DEFAULT_NAME
        defaultInstructorShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the instructorList where name does not contain UPDATED_NAME
        defaultInstructorShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllInstructorsByAgeIsEqualToSomething() throws Exception {
        // Initialize the database
        instructorRepository.saveAndFlush(instructor);

        // Get all the instructorList where age equals to DEFAULT_AGE
        defaultInstructorShouldBeFound("age.equals=" + DEFAULT_AGE);

        // Get all the instructorList where age equals to UPDATED_AGE
        defaultInstructorShouldNotBeFound("age.equals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllInstructorsByAgeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        instructorRepository.saveAndFlush(instructor);

        // Get all the instructorList where age not equals to DEFAULT_AGE
        defaultInstructorShouldNotBeFound("age.notEquals=" + DEFAULT_AGE);

        // Get all the instructorList where age not equals to UPDATED_AGE
        defaultInstructorShouldBeFound("age.notEquals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllInstructorsByAgeIsInShouldWork() throws Exception {
        // Initialize the database
        instructorRepository.saveAndFlush(instructor);

        // Get all the instructorList where age in DEFAULT_AGE or UPDATED_AGE
        defaultInstructorShouldBeFound("age.in=" + DEFAULT_AGE + "," + UPDATED_AGE);

        // Get all the instructorList where age equals to UPDATED_AGE
        defaultInstructorShouldNotBeFound("age.in=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllInstructorsByAgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        instructorRepository.saveAndFlush(instructor);

        // Get all the instructorList where age is not null
        defaultInstructorShouldBeFound("age.specified=true");

        // Get all the instructorList where age is null
        defaultInstructorShouldNotBeFound("age.specified=false");
    }

    @Test
    @Transactional
    public void getAllInstructorsByAgeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        instructorRepository.saveAndFlush(instructor);

        // Get all the instructorList where age is greater than or equal to DEFAULT_AGE
        defaultInstructorShouldBeFound("age.greaterThanOrEqual=" + DEFAULT_AGE);

        // Get all the instructorList where age is greater than or equal to (DEFAULT_AGE + 1)
        defaultInstructorShouldNotBeFound("age.greaterThanOrEqual=" + (DEFAULT_AGE + 1));
    }

    @Test
    @Transactional
    public void getAllInstructorsByAgeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        instructorRepository.saveAndFlush(instructor);

        // Get all the instructorList where age is less than or equal to DEFAULT_AGE
        defaultInstructorShouldBeFound("age.lessThanOrEqual=" + DEFAULT_AGE);

        // Get all the instructorList where age is less than or equal to SMALLER_AGE
        defaultInstructorShouldNotBeFound("age.lessThanOrEqual=" + SMALLER_AGE);
    }

    @Test
    @Transactional
    public void getAllInstructorsByAgeIsLessThanSomething() throws Exception {
        // Initialize the database
        instructorRepository.saveAndFlush(instructor);

        // Get all the instructorList where age is less than DEFAULT_AGE
        defaultInstructorShouldNotBeFound("age.lessThan=" + DEFAULT_AGE);

        // Get all the instructorList where age is less than (DEFAULT_AGE + 1)
        defaultInstructorShouldBeFound("age.lessThan=" + (DEFAULT_AGE + 1));
    }

    @Test
    @Transactional
    public void getAllInstructorsByAgeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        instructorRepository.saveAndFlush(instructor);

        // Get all the instructorList where age is greater than DEFAULT_AGE
        defaultInstructorShouldNotBeFound("age.greaterThan=" + DEFAULT_AGE);

        // Get all the instructorList where age is greater than SMALLER_AGE
        defaultInstructorShouldBeFound("age.greaterThan=" + SMALLER_AGE);
    }


    @Test
    @Transactional
    public void getAllInstructorsBySkillsIsEqualToSomething() throws Exception {
        // Initialize the database
        instructorRepository.saveAndFlush(instructor);

        // Get all the instructorList where skills equals to DEFAULT_SKILLS
        defaultInstructorShouldBeFound("skills.equals=" + DEFAULT_SKILLS);

        // Get all the instructorList where skills equals to UPDATED_SKILLS
        defaultInstructorShouldNotBeFound("skills.equals=" + UPDATED_SKILLS);
    }

    @Test
    @Transactional
    public void getAllInstructorsBySkillsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        instructorRepository.saveAndFlush(instructor);

        // Get all the instructorList where skills not equals to DEFAULT_SKILLS
        defaultInstructorShouldNotBeFound("skills.notEquals=" + DEFAULT_SKILLS);

        // Get all the instructorList where skills not equals to UPDATED_SKILLS
        defaultInstructorShouldBeFound("skills.notEquals=" + UPDATED_SKILLS);
    }

    @Test
    @Transactional
    public void getAllInstructorsBySkillsIsInShouldWork() throws Exception {
        // Initialize the database
        instructorRepository.saveAndFlush(instructor);

        // Get all the instructorList where skills in DEFAULT_SKILLS or UPDATED_SKILLS
        defaultInstructorShouldBeFound("skills.in=" + DEFAULT_SKILLS + "," + UPDATED_SKILLS);

        // Get all the instructorList where skills equals to UPDATED_SKILLS
        defaultInstructorShouldNotBeFound("skills.in=" + UPDATED_SKILLS);
    }

    @Test
    @Transactional
    public void getAllInstructorsBySkillsIsNullOrNotNull() throws Exception {
        // Initialize the database
        instructorRepository.saveAndFlush(instructor);

        // Get all the instructorList where skills is not null
        defaultInstructorShouldBeFound("skills.specified=true");

        // Get all the instructorList where skills is null
        defaultInstructorShouldNotBeFound("skills.specified=false");
    }
                @Test
    @Transactional
    public void getAllInstructorsBySkillsContainsSomething() throws Exception {
        // Initialize the database
        instructorRepository.saveAndFlush(instructor);

        // Get all the instructorList where skills contains DEFAULT_SKILLS
        defaultInstructorShouldBeFound("skills.contains=" + DEFAULT_SKILLS);

        // Get all the instructorList where skills contains UPDATED_SKILLS
        defaultInstructorShouldNotBeFound("skills.contains=" + UPDATED_SKILLS);
    }

    @Test
    @Transactional
    public void getAllInstructorsBySkillsNotContainsSomething() throws Exception {
        // Initialize the database
        instructorRepository.saveAndFlush(instructor);

        // Get all the instructorList where skills does not contain DEFAULT_SKILLS
        defaultInstructorShouldNotBeFound("skills.doesNotContain=" + DEFAULT_SKILLS);

        // Get all the instructorList where skills does not contain UPDATED_SKILLS
        defaultInstructorShouldBeFound("skills.doesNotContain=" + UPDATED_SKILLS);
    }


    @Test
    @Transactional
    public void getAllInstructorsByCoursesIsEqualToSomething() throws Exception {
        // Initialize the database
        instructorRepository.saveAndFlush(instructor);
        Course courses = CourseResourceIT.createEntity(em);
        em.persist(courses);
        em.flush();
        instructor.addCourses(courses);
        instructorRepository.saveAndFlush(instructor);
        Long coursesId = courses.getId();

        // Get all the instructorList where courses equals to coursesId
        defaultInstructorShouldBeFound("coursesId.equals=" + coursesId);

        // Get all the instructorList where courses equals to coursesId + 1
        defaultInstructorShouldNotBeFound("coursesId.equals=" + (coursesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInstructorShouldBeFound(String filter) throws Exception {
        restInstructorMockMvc.perform(get("/api/instructors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instructor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].skills").value(hasItem(DEFAULT_SKILLS)));

        // Check, that the count call also returns 1
        restInstructorMockMvc.perform(get("/api/instructors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInstructorShouldNotBeFound(String filter) throws Exception {
        restInstructorMockMvc.perform(get("/api/instructors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInstructorMockMvc.perform(get("/api/instructors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingInstructor() throws Exception {
        // Get the instructor
        restInstructorMockMvc.perform(get("/api/instructors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstructor() throws Exception {
        // Initialize the database
        instructorService.save(instructor);

        int databaseSizeBeforeUpdate = instructorRepository.findAll().size();

        // Update the instructor
        Instructor updatedInstructor = instructorRepository.findById(instructor.getId()).get();
        // Disconnect from session so that the updates on updatedInstructor are not directly saved in db
        em.detach(updatedInstructor);
        updatedInstructor
            .name(UPDATED_NAME)
            .age(UPDATED_AGE)
            .skills(UPDATED_SKILLS);

        restInstructorMockMvc.perform(put("/api/instructors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInstructor)))
            .andExpect(status().isOk());

        // Validate the Instructor in the database
        List<Instructor> instructorList = instructorRepository.findAll();
        assertThat(instructorList).hasSize(databaseSizeBeforeUpdate);
        Instructor testInstructor = instructorList.get(instructorList.size() - 1);
        assertThat(testInstructor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInstructor.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testInstructor.getSkills()).isEqualTo(UPDATED_SKILLS);
    }

    @Test
    @Transactional
    public void updateNonExistingInstructor() throws Exception {
        int databaseSizeBeforeUpdate = instructorRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstructorMockMvc.perform(put("/api/instructors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instructor)))
            .andExpect(status().isBadRequest());

        // Validate the Instructor in the database
        List<Instructor> instructorList = instructorRepository.findAll();
        assertThat(instructorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInstructor() throws Exception {
        // Initialize the database
        instructorService.save(instructor);

        int databaseSizeBeforeDelete = instructorRepository.findAll().size();

        // Delete the instructor
        restInstructorMockMvc.perform(delete("/api/instructors/{id}", instructor.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Instructor> instructorList = instructorRepository.findAll();
        assertThat(instructorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

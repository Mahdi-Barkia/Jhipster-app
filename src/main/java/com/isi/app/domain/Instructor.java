package com.isi.app.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Instructor.
 */
@Entity
@Table(name = "instructor")
public class Instructor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Min(value = 18)
    @Max(value = 65)
    @Column(name = "age", nullable = false)
    private Integer age;

    @NotNull
    @Column(name = "skills", nullable = false)
    private String skills;

    @OneToMany(mappedBy = "tutor")
    private Set<Course> courses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Instructor name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public Instructor age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSkills() {
        return skills;
    }

    public Instructor skills(String skills) {
        this.skills = skills;
        return this;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public Instructor courses(Set<Course> courses) {
        this.courses = courses;
        return this;
    }

    public Instructor addCourses(Course course) {
        this.courses.add(course);
        course.setTutor(this);
        return this;
    }

    public Instructor removeCourses(Course course) {
        this.courses.remove(course);
        course.setTutor(null);
        return this;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Instructor)) {
            return false;
        }
        return id != null && id.equals(((Instructor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Instructor{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", age=" + getAge() +
            ", skills='" + getSkills() + "'" +
            "}";
    }
}

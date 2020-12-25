package com.isi.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.isi.app.domain.Instructor} entity. This class is used
 * in {@link com.isi.app.web.rest.InstructorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /instructors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InstructorCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private IntegerFilter age;

    private StringFilter skills;

    private LongFilter coursesId;

    public InstructorCriteria() {
    }

    public InstructorCriteria(InstructorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.age = other.age == null ? null : other.age.copy();
        this.skills = other.skills == null ? null : other.skills.copy();
        this.coursesId = other.coursesId == null ? null : other.coursesId.copy();
    }

    @Override
    public InstructorCriteria copy() {
        return new InstructorCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public IntegerFilter getAge() {
        return age;
    }

    public void setAge(IntegerFilter age) {
        this.age = age;
    }

    public StringFilter getSkills() {
        return skills;
    }

    public void setSkills(StringFilter skills) {
        this.skills = skills;
    }

    public LongFilter getCoursesId() {
        return coursesId;
    }

    public void setCoursesId(LongFilter coursesId) {
        this.coursesId = coursesId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InstructorCriteria that = (InstructorCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(age, that.age) &&
            Objects.equals(skills, that.skills) &&
            Objects.equals(coursesId, that.coursesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        age,
        skills,
        coursesId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InstructorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (age != null ? "age=" + age + ", " : "") +
                (skills != null ? "skills=" + skills + ", " : "") +
                (coursesId != null ? "coursesId=" + coursesId + ", " : "") +
            "}";
    }

}

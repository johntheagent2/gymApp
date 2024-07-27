package com.example.gymapp.entity.criteria;

import com.example.gymapp.enumeration.ProgramType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.StringFilter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class TrainingProgramCriteria implements Criteria {

    private StringFilter title;
    private UsernameFilter userName;
    private ProgramTypeFilter programType;
    private LocalDateFilter startDate;
    private LocalDateFilter currentDate;
    private Boolean deleted;


    public TrainingProgramCriteria(TrainingProgramCriteria other) {
        this.title = other.title == null ? null : other.title.copy();
        this.userName = other.userName == null ? null : other.userName.copy();
        this.programType = other.programType == null ? null : other.programType.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.currentDate = other.currentDate == null ? null : other.currentDate.copy();
        this.deleted = other.deleted;
    }

    @Override
    public Criteria copy() {
        return new TrainingProgramCriteria(this);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        TrainingProgramCriteria that = (TrainingProgramCriteria) object;
        return Objects.equals(title, that.title)
                && Objects.equals(userName, that.userName)
                && Objects.equals(programType, that.programType)
                && Objects.equals(deleted, that.deleted)
                && Objects.equals(startDate, that.startDate)
                && Objects.equals(currentDate, that.currentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, userName, programType, deleted, startDate);
    }

    public static class UsernameFilter extends Filter<String> {

        private static final long serialVersionUID = 1L;

        public UsernameFilter() {
        }

        public UsernameFilter(UsernameFilter filter) {
            super(filter);
        }

        @Override
        public UsernameFilter copy() {
            return new UsernameFilter(this);
        }
    }

    public static class ProgramTypeFilter extends Filter<ProgramType> {

        private static final long serialVersionUID = 1L;

        public ProgramTypeFilter() {
        }

        public ProgramTypeFilter(ProgramTypeFilter filter) {
            super(filter);
        }

        @Override
        public ProgramTypeFilter copy() {
            return new ProgramTypeFilter(this);
        }
    }
}


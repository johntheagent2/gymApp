//package com.example.gymapp.entity.criteria;
//
//import com.example.gymapp.enumeration.TrackingType;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import tech.jhipster.service.Criteria;
//import tech.jhipster.service.filter.BooleanFilter;
//import tech.jhipster.service.filter.Filter;
//import tech.jhipster.service.filter.LocalDateFilter;
//import tech.jhipster.service.filter.StringFilter;
//
//import java.util.Objects;
//
//@Getter
//@Setter
//@NoArgsConstructor
//public class ProgressCriteria implements Criteria {
//
//    private StringFilter name;
//    private StatusFilter status;
//    private LocalDateFilter createdDate;
//    private Boolean distinct;
//
//    public ProgressCriteria(ProgressCriteria other) {
//        this.name = other.name == null ? null : other.name.copy();
//        this.status = other.status == null ? null : other.status.copy();
//        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
//        this.distinct = other.distinct;
//    }
//
//    @Override
//    public Criteria copy() {
//        return new ProgressCriteria(this);
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        if (this == object) return true;
//        if (object == null || getClass() != object.getClass()) return false;
//        ProgressCriteria that = (ProgressCriteria) object;
//        return Objects.equals(name, that.name)
//                && Objects.equals(status, that.status)
//                && Objects.equals(distinct, that.distinct)
//                && Objects.equals(createdDate, that.createdDate);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(name, status, distinct);
//    }
//
//    public static class StatusFilter extends Filter<TrackingType> {
//
//        private static final long serialVersionUID = 1L;
//
//        public StatusFilter() {
//        }
//
//        public StatusFilter(StatusFilter filter) {
//            super(filter);
//        }
//
//        @Override
//        public StatusFilter copy() {
//            return new StatusFilter(this);
//        }
//    }
//}
//

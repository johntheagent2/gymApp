//package com.example.gymapp.service.implement;
//
//import com.example.gymapp.dto.response.LatestProgressResponse;
//import com.example.gymapp.entity.Progression;
//import com.example.gymapp.entity.Progression_;
//import com.example.gymapp.entity.criteria.ProgressCriteria;
//import com.example.gymapp.repository.ProgressionRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.awt.print.Pageable;
//
//@AllArgsConstructor
//@Service
//public class GiftQueryService {
//
//    private final ProgressionRepository progressionRepository;
//
//    @Transactional(readOnly = true)
//    public Page<LatestProgressResponse> getAllGift(Pageable page) {
//        ProgressCriteria criteria = new ProgressCriteria();
//        return getBookResponses(criteria, page);
//    }
//
//    @Transactional(readOnly = true)
//    public Page<LatestProgressResponse> getAvailableGift(Pageable page) {
//        ProgressCriteria criteria = new ProgressCriteria();
//        criteria.setStatus((ProgressCriteria.StatusFilter) new ProgressCriteria.StatusFilter().setNotEquals(ProgressCriteria.DELETED));
//        return getBookResponses(criteria, page);
//    }
//
//    @Transactional(readOnly = true)
//    public Page<LatestProgressResponse> findByCriteria(ProgressCriteria criteria, Pageable page) {
//        return getBookResponses(criteria, page);
//    }
//
//    private Page<LatestProgressResponse> getBookResponses(ProgressCriteria criteria, Pageable page) {
//        final Specification<Progression> specification = createSpecification(criteria);
//        Page<Progression> books = progressionRepository.findAll(specification, page);
//        return books.map(book -> LatestProgressResponse.builder()
//                .value(book.getValue())
//                .trackingType(book.getTrackingType())
//                .build());
//    }
//
//    private Specification<Progression> createSpecification(ProgressCriteria criteria) {
//        Specification<Progression> specification = Specification.where(null);
//        if (criteria != null) {
//            // This has to be called first, because the distinct method returns null
//            if (criteria.getDistinct() != null) {
//                specification = specification.and(distinct(criteria.getDistinct()));
//            }
//            if (criteria.getName() != null) {
//                specification = specification.and(buildStringSpecification(criteria.getName(), Progression_.user.getName()));
//            }
//            if (criteria.getStatus() != null) {
//                specification = specification.and(buildSpecification(criteria.getStatus(), Gift_.status));
//            }
//        }
//        return specification;
//    }
//}

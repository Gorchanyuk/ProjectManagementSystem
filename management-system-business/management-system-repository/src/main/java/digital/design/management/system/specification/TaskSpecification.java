package digital.design.management.system.specification;

import digital.design.management.system.dto.task.TaskFilterDTO;
import digital.design.management.system.entity.Task;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class TaskSpecification {

    public static Specification<Task> getSpecification(TaskFilterDTO dto) {

        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();


            if (!ObjectUtils.isEmpty(dto.getName()))
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + dto.getName().trim().toLowerCase() + "%"));
            if (!ObjectUtils.isEmpty(dto.getAuthor()))
                predicates.add(criteriaBuilder.equal(root.get("author").get("uid"), dto.getAuthor()));
            if (!ObjectUtils.isEmpty(dto.getTaskPerformer()))
                predicates.add(criteriaBuilder.equal(root.get("taskPerformer").get("uid"), dto.getTaskPerformer()));
            if (!ObjectUtils.isEmpty(dto.getDateOfCreatedStart()))
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dateOfCreated"), dto.getDateOfCreatedStart()));
            if (!ObjectUtils.isEmpty(dto.getDateOfCreatedEnd()))
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dateOfCreated"), dto.getDateOfCreatedEnd()));
            if (!ObjectUtils.isEmpty(dto.getDeadlineStart()))
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("deadline"), dto.getDeadlineStart()));
            if (!ObjectUtils.isEmpty(dto.getDeadlineEnd()))
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("deadline"), dto.getDeadlineEnd()));
            if (!ObjectUtils.isEmpty(dto.getStatus())) {
                List<Predicate> statusPredicate = new ArrayList<>();
                dto.getStatus().forEach(status -> statusPredicate.add(criteriaBuilder.equal(root.get("status"), status)));
                predicates.add(criteriaBuilder.or(statusPredicate.toArray(Predicate[]::new)));
            }

            if (CollectionUtils.isEmpty(predicates))
                return query.where().getRestriction();
            else
                return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).getRestriction();
        });
    }
}

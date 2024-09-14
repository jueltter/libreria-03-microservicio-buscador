package ec.com.samagua.libreria03microserviciobuscador.act3_repositorios_utils;

import ec.com.samagua.libreria03microserviciobuscador.act1_entidades.Libro;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.LinkedList;
import java.util.List;

public class SearchCriteria implements Specification<Libro> {

    private final List<SearchStatement> list = new LinkedList<>();

    public void add(SearchStatement criteria) {
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<Libro> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = list.stream().map(
                obj -> {
                    return switch (obj.getOperation()) {
                        case GREATER_THAN ->
                                criteriaBuilder.greaterThan(root.get(obj.getKey()), obj.getValue().toString());
                        case LESS_THAN -> criteriaBuilder.lessThan(root.get(obj.getKey()), obj.getValue().toString());
                        case GREATER_THAN_EQUAL ->
                                criteriaBuilder.greaterThanOrEqualTo(root.get(obj.getKey()), obj.getValue().toString());
                        case LESS_THAN_EQUAL ->
                                criteriaBuilder.lessThanOrEqualTo(root.get(obj.getKey()), obj.getValue().toString());
                        case NOT_EQUAL -> criteriaBuilder.notEqual(root.get(obj.getKey()), obj.getValue());
                        case EQUAL -> criteriaBuilder.equal(root.get(obj.getKey()), obj.getValue());
                        case MATCH ->
                                criteriaBuilder.like(criteriaBuilder.lower(root.get(obj.getKey())), "%" + obj.getValue().toString().toLowerCase() + "%");
                        case MATCH_END ->
                                criteriaBuilder.like(criteriaBuilder.lower(root.get(obj.getKey())), obj.getValue().toString().toLowerCase() + "%");
                        case IS_MEMBER -> {
                            Expression<String> userExpression = root.get(obj.getKey());
                            yield userExpression.in((List<Long>) obj.getValue());}
                    };

                }

        ).toList();

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}

package br.com.gumillanf.cooperativa.commons.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

import static java.util.Objects.isNull;

public enum SearchOperation {

    // @formatter:off
    EQUALS(":EQ:", (b, k, v) -> b.equal(k, v.get(0))),
    LIKE(":LK:", (b, k, v) -> b.like(k, b.literal("%" + v.get(0) + "%"))),
    LESS_EQUAL_THAN(":LET:", (b, k, v) -> b.lessThanOrEqualTo(k, v.get(0))),
    GREATER_EQUAL_THAN(":GET:", (b, k, v) -> b.greaterThanOrEqualTo(k, v.get(0))),
    GREATER_THAN(":GT:", (b, k, v) -> b.greaterThan(k, v.get(0))),
    LESS_THAN(":LT:", (b, k, v) -> b.lessThan(k, v.get(0))),
    IN(":IN:", (b, k, v) -> k.in(v)),
    BETWEEN(":BT:", (b, k, v) -> b.between(k, v.get(0), v.get(1))),
    NEGATION(":N:", (b, k, v) -> b.notEqual(k, v.get(0)));
    // @formatter:on

    private final String operationName;

    private final FilterPredicateFunction operation;

    SearchOperation(String operationName, FilterPredicateFunction operation) {
        this.operationName = operationName;
        this.operation = operation;
    }

    public String getOperationName() {
        return operationName;
    }

    public Predicate build(CriteriaBuilder builder, Root<?> entity, String key, List<Comparable> arguments) {
        String[] keys = key.split("\\.");
        Path path = null;
        for (int i = 0; i < keys.length; i++) {
            if (isNull(path)) {
                path = entity.get(keys[i]);
            }
            else {
                path = path.get(keys[i]);
            }
        }
        return operation.predicate(builder, path, arguments);
    }

    @FunctionalInterface
    interface FilterPredicateFunction {

        Predicate predicate(CriteriaBuilder builder, Path key, List<Comparable> value);

    }

}

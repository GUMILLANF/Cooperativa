package br.com.gumillanf.cooperativa.commons.criteria;

import lombok.*;

import java.util.Arrays;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchCriteria {

    private String key;

    private SearchOperation operation;

    private boolean isOrOperation;

    private boolean ignoreIfNull;

    private List<Comparable> arguments;

    public static SearchCriteria and(String key, SearchOperation operation, Comparable... arguments) {
        return SearchCriteria.builder().key(key).operation(operation).isOrOperation(false).ignoreIfNull(true)
                .arguments(Arrays.asList(arguments)).build();
    }

    public static SearchCriteria or(String key, SearchOperation operation, Comparable... arguments) {
        return SearchCriteria.builder().key(key).operation(operation).isOrOperation(true).ignoreIfNull(true)
                .arguments(Arrays.asList(arguments)).build();
    }

    public SearchCriteria notIgnoreIfNull() {
        this.ignoreIfNull = false;
        return this;
    }

}

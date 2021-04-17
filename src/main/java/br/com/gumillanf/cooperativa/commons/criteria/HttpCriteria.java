package br.com.gumillanf.cooperativa.commons.criteria;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.List;

import static java.util.Objects.nonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class HttpCriteria implements Serializable {

    @Nullable
    @Builder.Default
    private Integer page = 0;

    @Nullable
    @Builder.Default
    private Integer size = 25;

    private List<String> filter;

    public Integer getPage() {
        return nonNull(page) ? page : 0;
    }

    public Integer getSize() {
        return nonNull(size) ? size : 25;
    }

    public List<String> getFilter() {
        return filter;
    }

}

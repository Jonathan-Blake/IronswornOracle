package graph.search;

import java.util.LinkedList;

public class CriteriaBuilder<T> {
    final LinkedList<Criteria<T>> criteria;

    CriteriaBuilder() {
        this.criteria = new LinkedList<>();
    }

    public CriteriaBuilder<T> orElse(Criteria<T> newCriteria) {
        this.criteria.add(newCriteria);
        return this;
    }

    public Criteria<T> build() {
        Criteria<T> ret;
        ret = new BasicCriteria<>(this.criteria.get(this.criteria.size() - 1), null);
        for (int j = this.criteria.size() - 2; j >= 0; j--) {
            Criteria<T> nxt = this.criteria.get(j);
            while (nxt.orElse() != null) {
                ret = new BasicCriteria<>(nxt, nxt.orElse());
                nxt = nxt.orElse();
            }
            ret = new BasicCriteria<>(nxt, ret);
        }
        return ret;
    }
}

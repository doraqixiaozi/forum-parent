package entity;

import lombok.Data;
import java.util.List;

/**
 * @Auther: 几米
 * @Date: 2019/5/4 20:46
 * @Description: PageResult
 */
@Data
public class PageResult<T> {
    private Long total;
    private List<T> rows;

    public PageResult() {
    }

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }
}

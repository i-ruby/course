package work.iruby.course.common;

import lombok.Data;

@Data
public class PageData<T> {
    private Integer totalPage;
    private Integer pageSize;
    private Integer pageNum;
    private T data;

}

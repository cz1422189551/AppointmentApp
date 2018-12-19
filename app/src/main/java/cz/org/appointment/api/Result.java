package cz.org.appointment.api;


import java.util.List;

public class Result<T> {

    //一页的大小
    private int pageSize;

    //当前页
    private int pageNumber;

    //总数
    private long total;

    //当前页的实际记录数
    private int pageCount;

    //总页数
    private long totalPage;

    private List<T> result;

    public Result(List<T> result) {
        this.result = result;
        if (result != null) total = result.size();
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}

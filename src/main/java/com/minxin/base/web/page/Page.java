package com.minxin.base.web.page;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by todd on 2016/11/25.
 * @author todd
 * @param <E>
 */
public class Page<E> {
    /**
     * 当前页
     */
    private int curPage = 1;

    /**
     * 每页记录数
     */
    private int pageSizes = 10;

    /**
     *
     */
    private int totalRecords = 0;

    /**
     *
     */
    private int totalPages = 0;

    /**
     *
     */
    private List<E> viewJsonData;

    public Page() {
        this.curPage = 1;
        this.pageSizes = 10;
        this.totalRecords = 0;
        this.totalPages = 0;
        this.viewJsonData = new ArrayList<>();
    }

    public int getCurPage() {
        return curPage;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<E> getViewJsonData() {
        return viewJsonData;
    }

    /**
     * 设置当前页码，从页面得到
     * @param curPage 当前页码
     */
    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    /**
     * 设置每页数量，从页面得到
     * @param pageSizes 每页数据量
     */
    public void setPageSizes(int pageSizes) {
        this.pageSizes = pageSizes;
    }

    /**
     *
     * @return 返回每页记录数
     */
    public int getPageSizes() {
        return this.pageSizes;
    }

    /**
     * 计算总记录数
     * @param totalRecords 总记录数
     */
    private void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    /**
     * 总页数
     */
    private void setTotalPages() {
        int result = totalRecords / pageSizes;
        this.totalPages = (totalRecords % pageSizes == 0) ? result : (result + 1);
    }

    /**
     *
     * @return 返回当前查询页的起始位置
     */
    private int getIndex() {
        return pageSizes * (curPage - 1);
    }

    /**
     *
     * @return 返回是否有前一页
     */
    public boolean hasPrevious() {
        return curPage > 1;
    }

    /**
     *
     * @return 返回是否有后一页
     */
    public boolean hasNext() {
        return curPage < totalPages;
    }

    public void setViewJsonData(List<E> viewJsonData) {
        this.viewJsonData = viewJsonData;
    }

    /**
     * 通过查询到的总记录数，计算页数，当前页记录的起始位置
     * @param totalRecords 符合条件的总记录数
     * @return 返回当前页的起始位置
     */
    public int calcuteIndex(int totalRecords) {
        this.setTotalRecords(totalRecords);
        this.setTotalPages();
        return this.getIndex();
    }
}

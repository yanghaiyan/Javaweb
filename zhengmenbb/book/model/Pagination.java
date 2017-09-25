package com.zhengmenbb.book.model;

import java.util.HashMap;
import java.util.Map;

import com.zhengmenbb.book.util.PropertyUtil;

public class Pagination {

    private static final String KEY_PAGE_SIZE = "pagination.pageSize";

    private int totalCount;
    private int pageSize;
    private int pageCount;
    private int currentPage;
    private int offset;

    private Map<String, Object> parameterMap = new HashMap<String, Object>();


    public Map<String, Object> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String, Object> parameterMap) {
        if (parameterMap == null) {
            parameterMap = new HashMap<String, Object>();
        }
        this.parameterMap = parameterMap;
    }

    public void addParameter(String key, Object value) {
        this.parameterMap.put(key, value);
    }



    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        if (pageSize == 0) {
            String size = PropertyUtil.getProperty(KEY_PAGE_SIZE);
            pageSize = Integer.parseInt(size);
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        if (totalCount < 1) {
            pageCount = 0;
            return pageCount;
        }
        pageCount = (totalCount - 1) / getPageSize() + 1;

        return pageCount;
    }

    public int getCurrentPage() {
        if (currentPage < 1) {
            currentPage = 1;
        }
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getOffset() {
        offset = (getCurrentPage() - 1) * getPageSize();
        return offset;

    }

    public boolean isFirstPage() {
        if (this.currentPage <= 1) {
            return true;
        }
        return false;
    }

    public boolean isLastPage() {
        if (this.currentPage >= this.getPageCount()) {
            return true;
        }
        return false;
    }


}

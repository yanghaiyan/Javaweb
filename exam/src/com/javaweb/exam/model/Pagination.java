package com.javaweb.exam.model;

import java.util.HashMap;
import java.util.Map;

public class Pagination {
    private int totalCount; // all data count
    private int pageSize; //all page count
    private int pageCount; // now page count
    private int currentPage; //
    private int offset; //

    private Map<String,Object> paramterMap = new HashMap<String, Object>();

    public Map<String, Object> getParamterMap() {
        return paramterMap;
    }

    public void setParamterMap(Map<String, Object> paramterMap) {
        if (paramterMap == null) {
            paramterMap = new HashMap<String, Object>();
        }
        this.paramterMap = paramterMap;
    }

    public void addParamterMap(String key, Object value) {
        this.addParamterMap(key, value);
    }
    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalcount) {
        this.totalCount = totalcount;
        setPageCount();
    }

    public int getPageSize() {
        if (this.pageSize == 0) {
            pageSize = 10;
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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
        offset = (getCurrentPage() -1) * getPageSize();
        return offset;

    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount() {
        if (totalCount < 1) {
            this.pageCount =0;
        }
        this.pageCount = (totalCount - 1) / getPageSize() + 1;
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

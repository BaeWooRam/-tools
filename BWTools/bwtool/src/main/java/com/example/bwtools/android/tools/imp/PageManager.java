package com.example.bwtools.android.tools.imp;

import com.example.bwtools.android.tools.interfaces.PageMangerImp;

public abstract class PageManager implements PageMangerImp {
    public final int INIT_PAGE_NOW = 0;
    public final int INIT_PAGE_TOTAL = -1;
    protected int PageNow;
    protected int PageTotal;
    protected boolean isPageEnd;

    public PageManager() {
        resetPage();
    }

    public PageManager(int PageNow, int PageTotal) {
        this.PageNow = PageNow;
        this.PageTotal = PageTotal;
        this.isPageEnd = false;
    }

    public int getPageNow() {
        return PageNow;
    }

    public void setPageNow(int pageNow) {
        PageNow = pageNow;
    }

    public int getPageTotal() {
        return PageTotal;
    }

    public void setPageTotal(int pageTotal) {
        PageTotal = pageTotal;
    }

    public boolean isPageEnd() {
        return isPageEnd;
    }

    public void setPageEnd(boolean pageEnd) {
        isPageEnd = pageEnd;
    }

    public void resetPage(){
        this.PageNow = INIT_PAGE_NOW;
        this.PageTotal = INIT_PAGE_TOTAL;
        this.isPageEnd = false;
    }


}

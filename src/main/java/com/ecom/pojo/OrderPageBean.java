package com.ecom.pojo;

import java.util.List;

/**
* 自定义:根据bootstrap-table参数定制的Bean，拥有搜索、单列排序以及分页功能。
* @search:  搜索的内容
* @sort:    排序的列名(只取第一个，使用MYSql的简单排序，例：select * from table order by value1 desc)
* @order:   排序的顺序，有 asc（升序） 和 desc（降序） 两种
* @total:   分页内容的总数目
* @offset:  当前页的偏移值（不是页数）
* @limit:   每页显示数目
* @List:    当前页面的内容（总数不等于total，而是等于limit）
* */
public class OrderPageBean<T>{

    private String search;
    private String sort;
    private String order;
    private int total;
    private int offset;
    private int limit;
    private List<T> list;

    public OrderPageBean() {
        search = "";
        sort = "";
        order = "asc";
        total = 0;
        offset = 0;
        limit = 20;
        list = null;
    }

    public OrderPageBean(String search, String sort, String order, int offset, int limit) {
        this.search = search;
        this.sort = sort;
        this.order = order;
        this.offset = offset;
        this.limit = limit;
        this.total = 0;
        this.list = null;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}

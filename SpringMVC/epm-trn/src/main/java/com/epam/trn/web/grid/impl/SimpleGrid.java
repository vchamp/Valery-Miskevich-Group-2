package com.epam.trn.web.grid.impl;

import java.io.Serializable;
import java.util.List;

import com.epam.trn.web.grid.Grid;

public class SimpleGrid<T> implements Serializable, Grid<T> {

	private static final long serialVersionUID = -1588030058563583602L;
	private Integer records;
	private Integer total;
	private Integer page;
	private List<T> rows;

	public SimpleGrid(List<T> rows) {
		setRows(rows);
	}
	
	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}

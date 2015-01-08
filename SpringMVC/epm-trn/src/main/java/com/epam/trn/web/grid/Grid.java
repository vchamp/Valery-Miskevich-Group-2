package com.epam.trn.web.grid;

import java.util.List;

public interface Grid<T> {

	public Integer getRecords();//total records
	public Integer getTotal();//total pages
	public Integer getPage();//current page
	public List<T> getRows();//rows for current page
}

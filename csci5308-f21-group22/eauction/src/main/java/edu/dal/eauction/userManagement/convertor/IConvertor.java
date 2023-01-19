package edu.dal.eauction.userManagement.convertor;

public interface IConvertor<F, T> {
	
	public T convert(F object);

}

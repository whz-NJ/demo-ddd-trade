/* 
 * Created by 2019年1月29日
 */
package com.demo2.trade.query.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.demo2.support.entity.ResultSet;
import com.demo2.support.service.impl.QueryServiceImpl;
import com.demo2.trade.entity.Product;
import com.demo2.trade.entity.Supplier;
import com.demo2.trade.service.SupplierService;

/**
 * The implement of the query service for products.
 * @author fangang
 */
public class ProductQueryServiceImpl extends QueryServiceImpl {
	@Autowired
	private SupplierService supplierService;

	@Override
	protected ResultSet afterQuery(Map<String, Object> params,
			ResultSet resultSet) {
		@SuppressWarnings("unchecked")
		List<Product> list = (List<Product>)resultSet.getData();
		
		List<Long> listOfIds = new ArrayList<>();
		for(Product product : list) {
			Long supplierId = product.getSupplierId();
			listOfIds.add(supplierId);
			//Supplier supplier = supplierService.loadSupplier(supplierId);
			//product.setSupplier(supplier);
		}
		List<Supplier> listOfSuppliers = supplierService.loadSuppliers(listOfIds);
		
		Map<Object, Supplier> mapOfSupplier = new HashMap<>();
		for(Supplier supplier : listOfSuppliers) {
			mapOfSupplier.put(supplier.getId(), supplier);
		}
		
		for(Product product : list) {
			Long supplierId = product.getSupplierId();
			Supplier supplier = mapOfSupplier.get(supplierId);
			product.setSupplier(supplier);
		}
		
		resultSet.setData(list);
		return resultSet;
	}
	
}

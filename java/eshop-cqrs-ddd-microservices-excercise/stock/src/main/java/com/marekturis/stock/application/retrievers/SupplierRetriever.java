package com.marekturis.stock.application.retrievers;

import com.marekturis.common.application.authorization.Authorize;
import com.marekturis.common.application.authorization.CustomAuthorize;
import com.marekturis.common.application.authorization.BasicAuthorizable;
import com.marekturis.common.domain.RoleTypes;
import com.marekturis.stock.infrastructure.readdb.retrievers.JDBCSupplierRetriever;
import com.marekturis.stock.infrastructure.readdb.retrievers.dtos.ProductSuppliedBySupplierDto;
import com.marekturis.stock.infrastructure.readdb.retrievers.dtos.SupplierDto;

import javax.inject.Named;
import java.util.List;

/**
 * @author Marek Turis
 */
@Named
public class SupplierRetriever {

	private JDBCSupplierRetriever supplierRetriever;

	public SupplierRetriever(JDBCSupplierRetriever supplierRetriever) {
		this.supplierRetriever = supplierRetriever;
	}

	@Authorize(RoleTypes.SALESMAN)
	public List<SupplierDto> getAllSuppliers(BasicAuthorizable basicAuthorizable) {
		return supplierRetriever.getAllSuppliers();
	}

	@Authorize(RoleTypes.SALESMAN)
	public List<ProductSuppliedBySupplierDto> getProductsSuppliedBySupplier(int supplierId, BasicAuthorizable basicAuthorizable) {
		return supplierRetriever.getProductsSuppliedBySupplier(supplierId);
	}
}

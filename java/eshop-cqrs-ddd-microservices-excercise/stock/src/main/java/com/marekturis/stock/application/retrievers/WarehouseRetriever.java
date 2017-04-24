package com.marekturis.stock.application.retrievers;

import com.marekturis.common.application.authorization.Authorize;
import com.marekturis.common.application.authorization.CustomAuthorize;
import com.marekturis.common.application.authorization.BasicAuthorizable;
import com.marekturis.common.domain.RoleTypes;
import com.marekturis.stock.infrastructure.readdb.retrievers.JDBCWarehouseRetriever;
import com.marekturis.stock.infrastructure.readdb.retrievers.dtos.ProductInWarehouseDto;
import com.marekturis.stock.infrastructure.readdb.retrievers.dtos.WarehouseDto;

import javax.inject.Named;
import java.util.List;

/**
 * @author Marek Turis
 */
@Named
public class WarehouseRetriever {

	private JDBCWarehouseRetriever warehouseRetriever;

	public WarehouseRetriever(JDBCWarehouseRetriever warehouseRetriever) {
		this.warehouseRetriever = warehouseRetriever;
	}

	@Authorize(RoleTypes.SALESMAN)
	public List<WarehouseDto> getAllWarehouses(BasicAuthorizable basicAuthorizable) {
		return warehouseRetriever.getAllWarehouses();
	}

	@Authorize(RoleTypes.SALESMAN)
	public List<ProductInWarehouseDto> getAllProductsInWarehouse(int warehouseId, BasicAuthorizable basicAuthorizable) {
		return warehouseRetriever.getAllProductsInWarehouse(warehouseId);
	}
}

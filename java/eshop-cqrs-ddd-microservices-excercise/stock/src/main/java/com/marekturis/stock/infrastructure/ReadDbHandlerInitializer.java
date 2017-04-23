package com.marekturis.stock.infrastructure;

import com.marekturis.common.domain.event.EventPublisher;
import com.marekturis.stock.infrastructure.readdb.handlers.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;

/**
 * @author Marek Turis
 */
@Named
public class ReadDbHandlerInitializer implements InitializingBean {

	@Autowired
	private EventPublisher eventPublisher;

	@Autowired
	private SupplierCreatedHandler supplierCreatedHandler;

	@Autowired
	private NewProductOfferedBySupplierHandler newProductOfferedBySupplierHandler;

	@Autowired
	private OfferedProductRemovedFromSupplierHandler offeredProductRemovedFromSupplierHandler;

	@Autowired
	private WarehouseCreatedHandler warehouseCreatedHandler;

	@Autowired
	private NewProductAddedToWarehouseHandler newProductAddedToWarehouseHandler;

	@Autowired
	private ProductAmmountInWarehouseDecreasedHandler productAmmountInWarehouseDecreasedHandler;

	@Autowired
	private ProductAmmountInWarehouseIncreasedHandler productAmmountInWarehouseIncreasedHandler;

	@Override
	public void afterPropertiesSet() throws Exception {
		registerHandlers();
	}

	private void registerHandlers() {
		eventPublisher.registerHandler(supplierCreatedHandler);
		eventPublisher.registerHandler(newProductOfferedBySupplierHandler);
		eventPublisher.registerHandler(offeredProductRemovedFromSupplierHandler);
		eventPublisher.registerHandler(warehouseCreatedHandler);
		eventPublisher.registerHandler(newProductAddedToWarehouseHandler);
		eventPublisher.registerHandler(productAmmountInWarehouseIncreasedHandler);
		eventPublisher.registerHandler(productAmmountInWarehouseDecreasedHandler);
	}
}

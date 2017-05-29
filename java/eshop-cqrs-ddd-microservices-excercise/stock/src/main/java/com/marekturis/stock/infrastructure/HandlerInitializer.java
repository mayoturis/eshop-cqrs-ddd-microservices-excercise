package com.marekturis.stock.infrastructure;

import com.marekturis.common.domain.event.EventPublisher;
import com.marekturis.stock.application.eventhandlers.RequestWarehouseProductCreationHandler;
import com.marekturis.stock.application.eventhandlers.WarehouseProductCreatedHandler;
import com.marekturis.stock.application.eventhandlers.WarehouseProductCreationTimedOutHandler;
import com.marekturis.stock.infrastructure.readdb.handlers.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;

/**
 * @author Marek Turis
 */
@Named
public class HandlerInitializer implements InitializingBean {

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

	@Autowired
	private RequestWarehouseProductCreationHandler requestWarehouseProductCreationHandler;

	@Autowired
	private WarehouseProductCreationTimedOutHandler warehouseProductCreationTimedOutHandler;

	@Autowired
	private WarehouseProductCreatedHandler warehouseProductCreatedHandler;

	@Autowired
	private ProductAssociatedWithWarehouseHandler productAssociatedWithWarehouseHandler;

	@Override
	public void afterPropertiesSet() throws Exception {
		registerReadDbHandlers();
		registerOtherHandlers();
	}

	private void registerReadDbHandlers() {
		eventPublisher.registerHandler(supplierCreatedHandler);
		eventPublisher.registerHandler(newProductOfferedBySupplierHandler);
		eventPublisher.registerHandler(offeredProductRemovedFromSupplierHandler);
		eventPublisher.registerHandler(warehouseCreatedHandler);
		eventPublisher.registerHandler(newProductAddedToWarehouseHandler);
		eventPublisher.registerHandler(productAmmountInWarehouseIncreasedHandler);
		eventPublisher.registerHandler(productAmmountInWarehouseDecreasedHandler);
		eventPublisher.registerHandler(productAssociatedWithWarehouseHandler);
	}

	private void registerOtherHandlers() {
		eventPublisher.registerHandler(requestWarehouseProductCreationHandler);
		eventPublisher.registerHandler(warehouseProductCreationTimedOutHandler);
		eventPublisher.registerHandler(warehouseProductCreatedHandler);
	}
}

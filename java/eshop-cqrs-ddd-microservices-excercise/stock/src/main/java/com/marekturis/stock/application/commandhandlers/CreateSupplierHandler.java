package com.marekturis.stock.application.commandhandlers;

import com.marekturis.common.application.authorization.CustomAuthorize;
import com.marekturis.common.application.command.CommandHandler;
import com.marekturis.common.application.transaction.Transactional;
import com.marekturis.common.application.validation.BasicValidator;
import com.marekturis.common.domain.RoleTypes;
import com.marekturis.common.domain.event.EventPublisher;
import com.marekturis.stock.application.commands.CreateSupplier;
import com.marekturis.stock.domain.supplier.Supplier;
import com.marekturis.stock.domain.supplier.SupplierCreated;
import com.marekturis.stock.domain.supplier.SupplierRepository;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Marek Turis
 */
@Named
public class CreateSupplierHandler implements CommandHandler<CreateSupplier> {

	private SupplierRepository supplierRepository;

	private EventPublisher eventPublisher;

	private BasicValidator basicValidator = new BasicValidator();

	@Inject
	public CreateSupplierHandler(SupplierRepository supplierRepository, EventPublisher eventPublisher) {
		this.supplierRepository = supplierRepository;
		this.eventPublisher = eventPublisher;
	}

	@Transactional
	@CustomAuthorize(RoleTypes.ADMIN)
	public void handle(CreateSupplier command) {
		basicValidator.notEmpty(command.name());

		int identity = supplierRepository.generateIdentity();
		Supplier supplier = new Supplier(
				command.name(),
				identity);

		supplierRepository.add(supplier);

		eventPublisher.publish(new SupplierCreated(
				identity,
				command.name()
		));
	}
}

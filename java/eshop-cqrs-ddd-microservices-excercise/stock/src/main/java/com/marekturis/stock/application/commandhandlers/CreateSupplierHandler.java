package com.marekturis.stock.application.commandhandlers;

import com.marekturis.common.application.authorization.Authorize;
import com.marekturis.common.application.command.CommandHandler;
import com.marekturis.common.application.transaction.Transactional;
import com.marekturis.common.domain.RoleTypes;
import com.marekturis.stock.application.commands.CreateSupplier;
import com.marekturis.stock.domain.supplier.Supplier;
import com.marekturis.stock.domain.supplier.SupplierRepository;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Marek Turis
 */
@Named
public class CreateSupplierHandler implements CommandHandler<CreateSupplier> {

	private SupplierRepository supplierRepository;

	@Inject
	public CreateSupplierHandler(SupplierRepository supplierRepository) {
		this.supplierRepository = supplierRepository;
	}

	@Transactional
	@Authorize(RoleTypes.ADMIN)
	public void handle(CreateSupplier command) {
		Supplier supplier = new Supplier(
				command.name(),
				supplierRepository.generateIdentity());

		supplierRepository.add(supplier);
	}
}

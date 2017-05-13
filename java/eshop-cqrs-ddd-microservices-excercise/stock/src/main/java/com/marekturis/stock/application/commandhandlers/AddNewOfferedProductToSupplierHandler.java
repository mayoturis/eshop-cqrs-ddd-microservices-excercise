package com.marekturis.stock.application.commandhandlers;

import com.marekturis.common.application.authorization.CustomAuthorize;
import com.marekturis.common.application.command.CommandHandler;
import com.marekturis.common.application.transaction.Transactional;
import com.marekturis.common.application.validation.BasicValidator;
import com.marekturis.common.application.validation.CommandValidator;
import com.marekturis.common.application.validation.ValidationException;
import com.marekturis.common.domain.RoleTypes;
import com.marekturis.stock.application.commands.AddNewOfferedProductToSupplier;
import com.marekturis.stock.domain.supplier.Supplier;
import com.marekturis.stock.domain.supplier.SupplierRepository;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Marek Turis
 */
@Named
public class AddNewOfferedProductToSupplierHandler implements CommandHandler<AddNewOfferedProductToSupplier> {

	private SupplierRepository supplierRepository;
	private CommandValidator<AddNewOfferedProductToSupplier> commandValidator = new AddNewOfferedProductToSupplierValidator();

	@Inject
	public AddNewOfferedProductToSupplierHandler(SupplierRepository supplierRepository) {
		this.supplierRepository = supplierRepository;
	}


	@Transactional
	@CustomAuthorize(RoleTypes.SALESMAN)
	public void handle(AddNewOfferedProductToSupplier command) {
		commandValidator.validate(command);

		Supplier supplier = supplierRepository.getById(command.getSupplierId());

		if (supplier == null) {
			throw new ValidationException("Supplier with given id doesn't exist");
		}

		supplier.addOfferedProduct(command.getProductId(), command.getPrice());
	}

	private class AddNewOfferedProductToSupplierValidator implements CommandValidator<AddNewOfferedProductToSupplier> {

		private BasicValidator basicValidator = new BasicValidator();

		@Override
		public void validate(AddNewOfferedProductToSupplier command) {
			basicValidator.moreThanZero(command.getProductId());
			basicValidator.moreThanZero(command.getPrice(), "Price");
		}
	}
}

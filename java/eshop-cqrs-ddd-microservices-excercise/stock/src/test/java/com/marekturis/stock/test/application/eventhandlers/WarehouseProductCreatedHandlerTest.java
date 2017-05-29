package com.marekturis.stock.test.application.eventhandlers;

import com.marekturis.common.application.command.Command;
import com.marekturis.common.application.command.CommandDispatcher;
import com.marekturis.common.domain.event.ParsableEvent;
import com.marekturis.common.domain.process.Process;
import com.marekturis.common.domain.process.ProcessRepository;
import com.marekturis.stock.application.commands.AssociateProductWithWarehouse;
import com.marekturis.stock.application.eventhandlers.WarehouseProductCreatedHandler;
import com.marekturis.stock.application.process.WarehouseProductCreationProcess;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Marek Turis
 */
@RunWith(MockitoJUnitRunner.class)
public class WarehouseProductCreatedHandlerTest {

	private static final int PRODUCT_ID = 55;
	private static final int WAREHOUSE_ID = 125;
	private static final String PROCESS_ID = "someId";

	@Mock
	private ProcessRepository processRepository;

	@Mock
	private CommandDispatcher commandDispatcher;

	@Mock
	private ParsableEvent event;

	@Mock
	WarehouseProductCreationProcess process;

	private WarehouseProductCreatedHandler uut;


	@Before
	public void setUp() {
		uut = new WarehouseProductCreatedHandler(processRepository, commandDispatcher);

		when(event.getInt("productId")).thenReturn(PRODUCT_ID);
		when(event.getString("processId")).thenReturn(PROCESS_ID);
		when(process.getWarehouseId()).thenReturn(WAREHOUSE_ID);
	}

	@Test
	public void testWhenProcessIsNotFoundNothingHappens() {
		when(processRepository.getProcessById(PROCESS_ID)).thenReturn(null);

		uut.handle(event);

		verify(commandDispatcher, never()).dispatch(any(Command.class));
		verify(processRepository, never()).removeProcess(any(Process.class));
	}

	@Test
	public void testCorrectCommandIsDispatched() {
		when(processRepository.getProcessById(PROCESS_ID)).thenReturn(process);

		uut.handle(event);

		ArgumentCaptor<AssociateProductWithWarehouse> commandCaptor = ArgumentCaptor.forClass(AssociateProductWithWarehouse.class);
		verify(commandDispatcher, times(1)).dispatch(commandCaptor.capture());
		AssociateProductWithWarehouse process = commandCaptor.getValue();

		Assert.assertEquals(WAREHOUSE_ID, process.getWarehouseId());
		Assert.assertEquals(PRODUCT_ID, process.getProductId());
	}

	@Test
	public void testProcessIsRemoved() {
		when(processRepository.getProcessById(PROCESS_ID)).thenReturn(process);

		uut.handle(event);

		verify(processRepository, times(1)).removeProcess(process);
	}
}

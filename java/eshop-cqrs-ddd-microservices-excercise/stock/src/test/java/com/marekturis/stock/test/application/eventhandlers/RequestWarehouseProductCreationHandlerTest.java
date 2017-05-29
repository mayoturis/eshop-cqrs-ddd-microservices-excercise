package com.marekturis.stock.test.application.eventhandlers;

import com.marekturis.common.domain.event.EventPublisher;
import com.marekturis.common.domain.event.ParsableEvent;
import com.marekturis.common.domain.process.Process;
import com.marekturis.common.domain.process.ProcessRepository;
import com.marekturis.stock.application.eventhandlers.RequestWarehouseProductCreationHandler;
import com.marekturis.stock.application.process.WarehouseProductCreationProcess;
import com.marekturis.stock.domain.warehouse.WarehouseProductCreationRequested;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/**
 * @author Marek Turis
 */
@RunWith(MockitoJUnitRunner.class)
public class RequestWarehouseProductCreationHandlerTest {

	private static final int WAREHOUSE_ID = 5;
	private static final String WAREHOUSE_LOCATION = "Leuven";

	@Mock
	private ProcessRepository processRepository;

	@Mock
	private EventPublisher eventPublisher;

	@Mock
	private ParsableEvent event;

	private RequestWarehouseProductCreationHandler uut;


	@Before
	public void setUp() {
		uut = new RequestWarehouseProductCreationHandler(processRepository, eventPublisher);

		when(event.getInt("id")).thenReturn(WAREHOUSE_ID);
		when(event.getString("location")).thenReturn(WAREHOUSE_LOCATION);
	}

	@Test
	public void testCorrectProcessIsAddedToRepository() {
		uut.handle(event);

		ArgumentCaptor<Process> processCaptor = ArgumentCaptor.forClass(Process.class);
		verify(processRepository, times(1)).addProcess(processCaptor.capture());

		Assert.assertTrue(processCaptor.getValue() instanceof WarehouseProductCreationProcess);
		WarehouseProductCreationProcess process = (WarehouseProductCreationProcess) processCaptor.getValue();
		Assert.assertEquals(WAREHOUSE_ID, process.getWarehouseId());
		Assert.assertEquals(WAREHOUSE_LOCATION, process.getWarehouseLocation());
	}

	@Test
	public void testCorrectEventIsPublished() {
		uut.handle(event);

		ArgumentCaptor<Process> processCaptor = ArgumentCaptor.forClass(Process.class);
		verify(processRepository).addProcess(processCaptor.capture());
		WarehouseProductCreationProcess process = (WarehouseProductCreationProcess) processCaptor.getValue();

		ArgumentCaptor<WarehouseProductCreationRequested> eventCaptor = ArgumentCaptor.forClass(WarehouseProductCreationRequested.class);
		verify(eventPublisher, times(1)).publish(eventCaptor.capture());
	 	WarehouseProductCreationRequested event = eventCaptor.getValue();

		Assert.assertEquals(process.getId(), event.getProcessId());
		Assert.assertEquals(WAREHOUSE_ID, event.getWarehouseId());
		Assert.assertEquals(WAREHOUSE_LOCATION, event.getLocation());
	}
}

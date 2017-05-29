package com.marekturis.stock.test.application.eventhandlers;

import com.marekturis.common.domain.event.Event;
import com.marekturis.common.domain.event.EventPublisher;
import com.marekturis.common.domain.event.ParsableEvent;
import com.marekturis.common.domain.process.Process;
import com.marekturis.common.domain.process.ProcessRepository;
import com.marekturis.stock.application.eventhandlers.RequestWarehouseProductCreationHandler;
import com.marekturis.stock.application.eventhandlers.WarehouseProductCreationTimedOutHandler;
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
public class WarehouseProductCreationTimedOutHandlerTest {

	private static final String PROCESS_ID = "processId";
	private static final int WAREHOUSE_ID = 5;
	private static final String WAREHOUSE_LOCATION = "Leuven";

	@Mock
	private EventPublisher eventPublisher;

	@Mock
	private ParsableEvent event;

	private WarehouseProductCreationTimedOutHandler uut;


	@Before
	public void setUp() {
		uut = new WarehouseProductCreationTimedOutHandler(eventPublisher);

		when(event.getString("processId")).thenReturn(PROCESS_ID);
		when(event.getInt("warehouseId")).thenReturn(WAREHOUSE_ID);
		when(event.getString("location")).thenReturn(WAREHOUSE_LOCATION);
	}

	@Test
	public void testIfTotallyTimedOutNothingHappens() {
		when(event.getBoolean("totallyTimedOut")).thenReturn(true);

		uut.handle(event);

		verify(eventPublisher, never()).publish(any(Event.class));
	}

	@Test
	public void testCorrectEventIsPublished() {
		uut.handle(event);

		ArgumentCaptor<WarehouseProductCreationRequested> eventCaptor = ArgumentCaptor.forClass(WarehouseProductCreationRequested.class);
		verify(eventPublisher, times(1)).publish(eventCaptor.capture());
		WarehouseProductCreationRequested event = eventCaptor.getValue();

		Assert.assertEquals(PROCESS_ID, event.getProcessId());
		Assert.assertEquals(WAREHOUSE_ID, event.getWarehouseId());
		Assert.assertEquals(WAREHOUSE_LOCATION, event.getLocation());
	}
}

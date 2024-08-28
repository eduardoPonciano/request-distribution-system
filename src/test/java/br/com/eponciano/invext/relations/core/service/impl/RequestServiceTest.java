package br.com.eponciano.invext.relations.core.service.impl;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.eponciano.invext.relations.core.model.Request;
import br.com.eponciano.invext.relations.core.model.RequestUpdate;
import br.com.eponciano.invext.relations.core.model.SectorEnum;
import br.com.eponciano.invext.relations.core.model.StatusRequestEnum;
import br.com.eponciano.invext.relations.core.storage.RequestsInMemoryStorage;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {

	@InjectMocks
	private RequestServiceImpl service;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
//		requestsStorage = mock(RequestsSingletonStorage.class);
	}

	@Test
	public void testGetAll() {
		try (MockedStatic<RequestsInMemoryStorage> mockedStatic = mockStatic(RequestsInMemoryStorage.class)) {
			List<Request> requests = Arrays.asList(new Request(), new Request());
			when(RequestsInMemoryStorage.getByFilter(SectorEnum.CARD, null, StatusRequestEnum.PENDENTE))
					.thenReturn(requests);
	
			List<Request> result = service.getAll(SectorEnum.CARD, null, StatusRequestEnum.PENDENTE);
	
			assertEquals(2, result.size());
			mockedStatic.verify(() ->RequestsInMemoryStorage.getByFilter(SectorEnum.CARD, null, StatusRequestEnum.PENDENTE));
		}
	}

	@Test
	public void testGetById_Found() {
		try (MockedStatic<RequestsInMemoryStorage> mockedStatic = mockStatic(RequestsInMemoryStorage.class)) {
			Request request = new Request();
			UUID id = UUID.randomUUID();
			when(RequestsInMemoryStorage.getById(id)).thenReturn(request);
	
			Request result = service.getById(id.toString());
	
			assertEquals(request, result);
			mockedStatic.verify(() ->RequestsInMemoryStorage.getById(id));
		}
	}

	@Test
	public void testGetById_NotFound() {
		UUID id = UUID.randomUUID();
		try (MockedStatic<RequestsInMemoryStorage> mockedStatic = mockStatic(RequestsInMemoryStorage.class)) {
			mockedStatic.when(() ->RequestsInMemoryStorage.getById(id)).thenReturn(null);
	
			Request result = service.getById(id.toString());
	
			assertNull(result);
			mockedStatic.verify(() ->RequestsInMemoryStorage.getById(id));
		}
	}

	@Test
	public void testSave() {
		try (MockedStatic<RequestsInMemoryStorage> mockedStatic = mockStatic(RequestsInMemoryStorage.class)) {
			Request request = new Request();
	
			service.save(request);
	
			mockedStatic.verify(() ->RequestsInMemoryStorage.add(request));
		}
	}

	@Test
	public void testUpdate_Found() throws Exception {
		try (MockedStatic<RequestsInMemoryStorage> mockedStatic = mockStatic(RequestsInMemoryStorage.class)) {
			Request existingRequest = new Request();
			RequestUpdate updatedRequest = new RequestUpdate();
			UUID id = UUID.randomUUID();
	
			when(RequestsInMemoryStorage.getById(id)).thenReturn(existingRequest);
			when(RequestsInMemoryStorage.update(id, existingRequest)).thenReturn(true);
	
			boolean result = service.update(id.toString(), updatedRequest);
	
			assertTrue(result);
			assertEquals(updatedRequest.getIdAtendente(), existingRequest.getIdAtendente());
			assertEquals(updatedRequest.getStatus(), existingRequest.getStatus());
			mockedStatic.verify(() ->RequestsInMemoryStorage.update(id, existingRequest));
		}
	}

	@Test
	public void testDeleteById_Found() {
		try (MockedStatic<RequestsInMemoryStorage> mockedStatic = mockStatic(RequestsInMemoryStorage.class)) {
			UUID id = UUID.randomUUID();
			when(RequestsInMemoryStorage.remove(id)).thenReturn(true);
	
			boolean result = service.deleteById(id.toString());
	
			assertTrue(result);
			mockedStatic.verify(() ->RequestsInMemoryStorage.remove(id));
		}
	}
}

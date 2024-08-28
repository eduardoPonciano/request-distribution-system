package br.com.eponciano.invext.relations.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.eponciano.invext.relations.core.model.Attendant;
import br.com.eponciano.invext.relations.core.model.SectorEnum;
import br.com.eponciano.invext.relations.core.storage.AttendantInMemoryStorage;

@ExtendWith(MockitoExtension.class)
public class AttendantServiceTest {

    @InjectMocks
    private AttendantServiceImpl service;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
		try (MockedStatic<AttendantInMemoryStorage> mockedStatic = mockStatic(AttendantInMemoryStorage.class)) {
	        List<Attendant> attendants = Arrays.asList(new Attendant(), new Attendant());
	        when(AttendantInMemoryStorage.getByFilter(any(SectorEnum.class))).thenReturn(attendants);
	
	        List<Attendant> result = service.getAll(SectorEnum.CARD);
	
	        assertEquals(2, result.size());
	        mockedStatic.verify(() ->AttendantInMemoryStorage.getByFilter(SectorEnum.CARD));
        }
    }

    @Test
    public void testGetById_Found() {
		try (MockedStatic<AttendantInMemoryStorage> mockedStatic = mockStatic(AttendantInMemoryStorage.class)) {
	        Attendant attendant = new Attendant();
	        UUID id = UUID.randomUUID();
	        when(AttendantInMemoryStorage.getById(id)).thenReturn(attendant);
	
	        Attendant result = service.getById(id.toString());
	
	        assertEquals(attendant, result);
	        mockedStatic.verify(() ->AttendantInMemoryStorage.getById(id));
        }
    }

    @Test
    public void testGetById_NotFound() {
		try (MockedStatic<AttendantInMemoryStorage> mockedStatic = mockStatic(AttendantInMemoryStorage.class)) {
			UUID id = UUID.randomUUID();
			when(AttendantInMemoryStorage.getById(id)).thenReturn(null);

	        Attendant result = service.getById(id.toString());
	
	        assertNull(result);
	        mockedStatic.verify(() ->AttendantInMemoryStorage.getById(id));
        }
    }

    @Test
    public void testSave() {
		try (MockedStatic<AttendantInMemoryStorage> mockedStatic = mockStatic(AttendantInMemoryStorage.class)) {
	        Attendant attendant = new Attendant();
	
	        service.save(attendant);
	
	        mockedStatic.verify(() ->AttendantInMemoryStorage.add(attendant));
        }
    }

    @Test
    public void testUpdate_Found() {
		try (MockedStatic<AttendantInMemoryStorage> mockedStatic = mockStatic(AttendantInMemoryStorage.class)) {
	        Attendant attendant = new Attendant();
	        UUID id = UUID.randomUUID();
	        when(AttendantInMemoryStorage.update(id, attendant)).thenReturn(true);
	
	        boolean result = service.update(id.toString(), attendant);
	
	        assertTrue(result);
	        mockedStatic.verify(() ->AttendantInMemoryStorage.update(id, attendant));
        }
    }

    @Test
    public void testDeleteById_Found() {
		try (MockedStatic<AttendantInMemoryStorage> mockedStatic = mockStatic(AttendantInMemoryStorage.class)) {
	        UUID id = UUID.randomUUID();
	        when(AttendantInMemoryStorage.remove(id)).thenReturn(true);
	
	        boolean result = service.deleteById(id.toString());
	
	        assertTrue(result);
	        mockedStatic.verify(() ->AttendantInMemoryStorage.remove(id));
        }
    }
}


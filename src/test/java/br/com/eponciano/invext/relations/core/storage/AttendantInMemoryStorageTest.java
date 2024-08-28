package br.com.eponciano.invext.relations.core.storage;
import static org.junit.jupiter.api.Assertions.*;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import br.com.eponciano.invext.relations.core.model.Attendant;
import br.com.eponciano.invext.relations.core.model.SectorEnum;

public class AttendantInMemoryStorageTest {

//    private  AttendantInMemoryStorage repository;
//
//    @BeforeEach
//    public void setUp() {
//        repository = new AttendantInMemoryStorage();
//    }

    @Test
    public void testAddAttendant() {
        Attendant attendant = new Attendant(UUID.randomUUID(), "John Doe", SectorEnum.CARD);
        AttendantInMemoryStorage.add(attendant);

        Attendant retrieved = AttendantInMemoryStorage.getById(attendant.getId());
        assertEquals(attendant, retrieved, "The added attendant should be retrievable by its ID.");
    }

    @Test
    public void testGetById_Found() {
        UUID id = UUID.randomUUID();
        Attendant attendant = new Attendant(id, "John Doe", SectorEnum.CARD);
        AttendantInMemoryStorage.add(attendant);

        Attendant retrieved = AttendantInMemoryStorage.getById(id);
        assertNotNull(retrieved, "Attendant should be found by ID.");
        assertEquals("John Doe", retrieved.getNome());
    }

    @Test
    public void testGetById_NotFound() {
        UUID id = UUID.randomUUID();

        Attendant retrieved = AttendantInMemoryStorage.getById(id);
        assertNull(retrieved, "Attendant with the given ID should not be found.");
    }

    @Test
    public void testGetByFilter() {
        Attendant attendant1 = new Attendant(UUID.randomUUID(), "John Doe", SectorEnum.CARD);
        Attendant attendant2 = new Attendant(UUID.randomUUID(), "Jane Doe", SectorEnum.CREDIT);
        AttendantInMemoryStorage.add(attendant1);
        AttendantInMemoryStorage.add(attendant2);

        List<Attendant> filteredList = AttendantInMemoryStorage.getByFilter(SectorEnum.CARD);
        assertEquals(1, filteredList.size(), "There should be exactly one attendant in the CARTOES sector.");
        assertEquals("John Doe", filteredList.get(0).getNome());
    }

    @Test
    public void testRemoveAttendant() {
        UUID id = UUID.randomUUID();
        Attendant attendant = new Attendant(id, "John Doe", SectorEnum.CARD);
        AttendantInMemoryStorage.add(attendant);

        boolean removed = AttendantInMemoryStorage.remove(id);
        assertTrue(removed, "Attendant should be removed successfully.");

        Attendant retrieved = AttendantInMemoryStorage.getById(id);
        assertNull(retrieved, "Removed attendant should not be found.");
    }

    @Test
    public void testUpdateAttendant() {
        UUID id = UUID.randomUUID();
        Attendant attendant = new Attendant(id, "John Doe", SectorEnum.CARD);
        AttendantInMemoryStorage.add(attendant);

        Attendant updatedAttendant = new Attendant(id, "John Smith", SectorEnum.CARD);
        boolean updated = AttendantInMemoryStorage.update(id, updatedAttendant);

        assertTrue(updated, "Attendant should be updated successfully.");
        Attendant retrieved = AttendantInMemoryStorage.getById(id);
        assertEquals("John Smith", retrieved.getNome(), "Attendant's name should be updated.");
    }

    @Test
    public void testUpdateAttendant_NotFound() {
        UUID id = UUID.randomUUID();
        Attendant updatedAttendant = new Attendant(id, "John Smith", SectorEnum.CARD);

        boolean updated = AttendantInMemoryStorage.update(id, updatedAttendant);
        assertFalse(updated, "Update should fail if the attendant is not found.");
    }
}


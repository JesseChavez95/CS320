package ContactService;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContactServiceTest {
    private ContactService service;
    private Contact contact;

    @BeforeEach
    void setUp() {
        service = new ContactService();
        contact = new Contact("1", "Jesse", "Chavez", "1234567890", "123 Main");
    }

    @Test
    void testAddContact() {
        service.addContact(contact);
        assertEquals(1, service.getContactCount());
        assertEquals(contact, service.getContact("1"));
    }

    @Test
    void testCannotAddNullOrDuplicateContact() {
        assertThrows(IllegalArgumentException.class, () -> service.addContact(null));
        service.addContact(contact);
        Contact duplicate = new Contact("1", "John", "Smith", "0987654321", "456 Oak");
        assertThrows(IllegalArgumentException.class, () -> service.addContact(duplicate));
    }

    @Test
    void testDeleteContact() {
        service.addContact(contact);
        service.deleteContact("1");
        assertEquals(0, service.getContactCount());
        assertThrows(IllegalArgumentException.class, () -> service.getContact("1"));
    }

    @Test
    void testDeleteMissingContactThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> service.deleteContact("missing"));
    }

    @Test
    void testUpdateContactFields() {
        service.addContact(contact);
        service.updateFirstName("1", "John");
        service.updateLastName("1", "Smith");
        service.updatePhone("1", "0987654321");
        service.updateAddress("1", "456 Oak");

        Contact updated = service.getContact("1");
        assertEquals("John", updated.getFirstName());
        assertEquals("Smith", updated.getLastName());
        assertEquals("0987654321", updated.getPhone());
        assertEquals("456 Oak", updated.getAddress());
    }

    @Test
    void testUpdateMissingContactThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> service.updateFirstName("missing", "John"));
        assertThrows(IllegalArgumentException.class, () -> service.updateLastName("missing", "Smith"));
        assertThrows(IllegalArgumentException.class, () -> service.updatePhone("missing", "0987654321"));
        assertThrows(IllegalArgumentException.class, () -> service.updateAddress("missing", "456 Oak"));
    }
}
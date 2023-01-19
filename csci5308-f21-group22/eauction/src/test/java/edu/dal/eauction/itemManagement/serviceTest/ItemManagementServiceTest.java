package edu.dal.eauction.itemManagement.serviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.dal.eauction.itemManagement.entities.Item;
import edu.dal.eauction.itemManagement.service.IItemManagementService;
import edu.dal.eauction.userManagement.exception.UserAlreadyExistsException;

@ExtendWith(MockitoExtension.class)
public class ItemManagementServiceTest {
	
	@Mock
	private IItemManagementService mockItemService;
	@Mock
	private Item mockItem;
	
	@Test
	public void checkAddItem() throws UserAlreadyExistsException {
		when(mockItemService.itemAddition(mockItem)).thenReturn(true);
		assertTrue(mockItemService.itemAddition(mockItem));
	}
	
	@Test
	public void checkUpdateItem() throws UserAlreadyExistsException {
		when(mockItemService.itemUpdation(mockItem)).thenReturn(true);
		assertTrue(mockItemService.itemUpdation(mockItem));
	}

}

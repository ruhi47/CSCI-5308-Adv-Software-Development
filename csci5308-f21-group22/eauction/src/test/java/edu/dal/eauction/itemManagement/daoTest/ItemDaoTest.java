package edu.dal.eauction.itemManagement.daoTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import edu.dal.eauction.itemManagement.dao.ItemDao;
import edu.dal.eauction.itemManagement.entities.Item;

@ExtendWith(MockitoExtension.class)
public class ItemDaoTest {
	
	@Mock
	private ItemDao mockItemDao;
	@Mock
	private Item mockItem;
	
	@Test
	public void checkCreateItem() {
		when(mockItemDao.create(mockItem)).thenReturn(Integer.valueOf(1));
		assertEquals(mockItemDao.create(mockItem), Integer.valueOf(1));
	}
	
	@Test
	public void checkReadItem() {
		when(mockItemDao.read(mockItem.getItemID())).thenReturn(mockItem);
		assertNotNull(mockItemDao.read(mockItem.getItemID()));
	}
	
	@Test
	public void checkReadAllItems() {
		List<Item> items = Arrays.asList(mockItem);
		when(mockItemDao.read()).thenReturn(items);
		assertNotNull(mockItemDao.read());	
	}
	
	@Test
	public void checkItemCreatedByUser() {
		List<Item> items = Arrays.asList(mockItem);
		when(mockItemDao.createdByUser(mockItem.getUserID())).thenReturn(items);
		assertNotNull(mockItemDao.createdByUser(mockItem.getUserID()));
	}
	
	@Test
	public void checkItemNotCreatedByUser() {
		List<Item> items = Arrays.asList(mockItem);
		when(mockItemDao.readOther(mockItem.getUserID())).thenReturn(items);
		assertNotNull(mockItemDao.readOther(mockItem.getUserID()));
	}
	
}

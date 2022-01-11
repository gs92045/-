package hello.itemservice.domain.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Repository
public class ItemRepository {
	private static final Map<Long,Item> store = new HashMap<>();
	private static long seq = 0L;
	
	public Item save(Item item) {
		item.setId(++seq);
		store.put(item.getId(), item);
		return item;
	}
	
	public Item findById(Long id) {
		return store.get(id);
	}
	
	public List<Item> findAll() {
		return new ArrayList<Item>(store.values());
	}
	
	public void update(Long id,Item updateParam) {
		
		log.info("repo update = {} " , updateParam);
		Item findItem = findById(id);
		findItem.setId(updateParam.getId());
		findItem.setItemName(updateParam.getItemName());
		findItem.setPrice(updateParam.getPrice());
		findItem.setQuantity(updateParam.getQuantity());
		findItem.setDeliveryCode(updateParam.getDeliveryCode());
		findItem.setOpen(updateParam.getOpen());
		findItem.setRegions(updateParam.getRegions());
		findItem.setItemType(updateParam.getItemType());
		
	}
	
	public void clearStore() {
		store.clear();
	}
}

package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    private final static Map<Long, Item> itemStore = new HashMap<>();
    private static Long sequence = 0L;

    public Item save(Item item) {
        item.setId(++sequence);
        itemStore.put(sequence, item);
        return item;
    }

    public void update(Long itemId, Item updateItem) {
        Item savedItem = itemStore.get(itemId);
        savedItem.setItemName(updateItem.getItemName());
        savedItem.setPrice(updateItem.getPrice());
        savedItem.setQuantity(updateItem.getQuantity());
    }

    public Item findById(Long itemId) {
        return itemStore.get(itemId);
    }

    public List<Item> findAll() {
        return new ArrayList<>(itemStore.values());
    }

    public void clearItemStore() {
        itemStore.clear();
    }
}

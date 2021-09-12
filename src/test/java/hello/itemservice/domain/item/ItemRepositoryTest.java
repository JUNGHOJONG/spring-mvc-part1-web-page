package hello.itemservice.domain.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearItemStore();
    }

    @Test
    void saveTest() {
        // GIVEN
        Item item = new Item("itemA", 10000, 10);

        itemRepository.save(item);

        // WHEN
        Item savedItem = itemRepository.findById(item.getId());

        // THEN
        assertThat(savedItem).isEqualTo(item);
    }

    @Test
    void findAllTest() {
        // GIVEN
        Item item1 = new Item("itemA", 10000, 10);
        Item item2 = new Item("itemB", 20000, 30);

        itemRepository.save(item1);
        itemRepository.save(item2);

        // WHEN
        List<Item> savedItems = itemRepository.findAll();

        // THEN
        assertThat(savedItems).contains(item1, item2);
    }

    @Test
    void updateTest() {
        // GIVEN
        Item item = new Item("itemA", 10000, 10);
        Item savedItem = itemRepository.save(item);

        // WHEN
        Item item2 = new Item("itemB", 20000, 30);
        itemRepository.update(savedItem.getId(), item2);
        Item updateItem = itemRepository.findById(savedItem.getId());

        // THEN
        assertThat(updateItem.getItemName()).isEqualTo(item2.getItemName());
        assertThat(updateItem.getPrice()).isEqualTo(item2.getPrice());
        assertThat(updateItem.getQuantity()).isEqualTo(item2.getQuantity());
    }

}
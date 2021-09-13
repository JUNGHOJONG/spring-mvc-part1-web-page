package hello.itemservice.web.item.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/basic/items")
@Controller
public class BasicItemController {

    private final ItemRepository itemRepository;

    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 30000, 30));
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "/basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);

        model.addAttribute("item", item);

        return "/basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "/basic/addForm";
    }

    @PostMapping("/add")
    public String addItem(@RequestParam String itemName,
                          @RequestParam Integer price,
                          @RequestParam Integer quantity,
                          Model model) {
        log.info("item={}, price={}, quantity={}", itemName, price, quantity);
        // 저장하고 내부 호출
        Item item = new Item(itemName, price, quantity);
        Item savedItem = itemRepository.save(item);

        model.addAttribute("item", savedItem);

        return "/basic/item";
    }

    @GetMapping("/edit/{itemId}")
    public String editForm(@PathVariable Long itemId, Model model) {
        log.info("itemId={}", itemId);
        Item item = itemRepository.findById(itemId);

        model.addAttribute("item", item);

        return "/basic/editForm";
    }

    @PostMapping("/edit/{itemId}")
    public String editItem(@PathVariable Long itemId,
                           @RequestParam String itemName,
                           @RequestParam Integer price,
                           @RequestParam Integer quantity,
                           Model model) {
        Item item = itemRepository.findById(itemId);

        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        model.addAttribute("item", item);

        return "/basic/item";
    }
}

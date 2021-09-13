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

    /**
     * model.addAttribute("item", item) 자동 추가
     */
//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item) {
        itemRepository.save(item);

        return "/basic/item";
    }

    /**
     * model.addAttribute("item", item) 자동 추가
     * 생략시 model에 저장되는 name은 클래스명 첫글자만 소문자로 등록 Item -> item
     * 추천!!!
     */
//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {
        itemRepository.save(item);

        return "/basic/item";
    }

    /**
     * @ModelAttribute 자체 생략 가능
     * model.addAttribute(item) 자동 추가
     */
//    @PostMapping("/add")
    public String addItemV4(Item item) {
        itemRepository.save(item);

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
    public String editItem(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);

        return "/basic/item";
    }
}

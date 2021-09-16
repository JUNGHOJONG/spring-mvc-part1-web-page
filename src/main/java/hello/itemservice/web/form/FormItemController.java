package hello.itemservice.web.form;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/basic/items")
@Controller
public class FormItemController {

    private final ItemRepository itemRepository;

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
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "/basic/addForm";
    }

    /**
     * model.addAttribute("item", item) 자동 추가
     * 생략시 model에 저장되는 name은 클래스명 첫글자만 소문자로 등록 Item -> item
     */
    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        log.debug("open={}, itemName={}, itemPrice={}, itemQuantity={}",item.getOpen(), item.getItemName(), item.getPrice(), item.getQuantity());
        Item savedItem = itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/edit/{itemId}")
    public String editForm(@PathVariable Long itemId, Model model) {
        log.debug("itemId={}", itemId);
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

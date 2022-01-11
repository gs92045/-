package hello.itemservice.web.item.basic;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hello.itemservice.domain.item.DeliveryCode;
import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.ItemType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * add���� - addForm ���� form ��ü�� ���� �޴� ����� ���� @ModelAttribute�� Ư¡�� ���ø��� 
 * 
 * 
 * @author kodin
 *
 */

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
@Slf4j
public class BasicItemController {
	private final ItemRepository repo;
	
	@GetMapping
	public String items(Model model) {
		List<Item> items = repo.findAll();
		model.addAttribute("items",items);
		return "/basic/items";
	}
	
	@GetMapping("/{itemId}")
	public String item(@PathVariable Long itemId, Model model) {
		Item item = repo.findById(itemId);
		model.addAttribute("item", item);
		return "basic/item";
	}
	
	//th:object�� ������Ʈ�� ���� ���޹޾ƾ� ������ �����ϴ�
	@GetMapping("/add")
	public String addForm(Model model) {
		model.addAttribute("item",new Item());
		return "basic/addForm";
	}
	
	
	@PostMapping("/add")
	public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
		//_open�� open�� ���� üũ�Ͽ� üũ�ڽ��� üũ�Ѵ�. 
		//�̸��� �޶� �翬�� �ǰ���?
		//�ش� üũ�ڽ��� on���� ���۵����� �����Ϳ� ���� true���� ��ȯ�ȴٰ� �Ѵ�.
		log.info("open = {} ",item.getOpen());
		
		Item savedItem = repo.save(item);
		redirectAttributes.addAttribute("itemId",savedItem.getId());
		redirectAttributes.addAttribute("status",true);
		return "redirect:/basic/items/{itemId}";
	}
		
	
	//���� ��ư Ŭ����
	@GetMapping("/{itemId}/edit")
	public String editForm(@PathVariable Long itemId , Model model) {
		Item item = repo.findById(itemId);
		log.info("controller edit = {} ",item);
		
		model.addAttribute("item",item);
		return "basic/editForm";
	}
	
	//���� ������ �����ϱ� ��ư Ŭ����
	@PostMapping("/{itemId}/edit")
	public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
		repo.update(itemId, item);
		return "redirect:/basic/items/{itemId}";
	}
	
	//ModelAttribute�� Ư���� �������� �ش� �޼ҵ带 ���� ���� ��� ���� �� �ִ�.
	@ModelAttribute("regions")
	public Map<String,String> regions(){
		Map<String,String> regions = new LinkedHashMap<>();
		regions.put("SEOUL","����");
		regions.put("BUSAN","�λ�");
		regions.put("JEJU","����");
		return regions;
	}
	
	@ModelAttribute("itemTypes")
	public ItemType[] itemTypes() {
		return ItemType.values();
	}
	
	@ModelAttribute("deliveryCodes")
	public List<DeliveryCode> deliveryCodes() {
		List<DeliveryCode> deliveryCodes = new ArrayList<>();
		deliveryCodes.add(new DeliveryCode("FAST","���� ���"));
		deliveryCodes.add(new DeliveryCode("NORMAL","�Ϲ� ���"));
		deliveryCodes.add(new DeliveryCode("SLOW","���� ���"));
		
		return deliveryCodes;
	}

	
	
	@PostConstruct
	public void init() {
		repo.save(new Item("testA",10000,10));
		repo.save(new Item("testB",15000,20));
	}
	
}

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
 * add매핑 - addForm 에서 form 객체를 전달 받는 방식의 과정 @ModelAttribute의 특징을 떠올리자 
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
	
	//th:object는 오브젝트를 만들어서 전달받아야 매핑이 가능하다
	@GetMapping("/add")
	public String addForm(Model model) {
		model.addAttribute("item",new Item());
		return "basic/addForm";
	}
	
	
	@PostMapping("/add")
	public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
		//_open과 open의 값을 체크하여 체크박스를 체크한다. 
		//이름이 달라도 당연히 되겠지?
		//해당 체크박스는 on으로 전송되지만 컨버터에 의해 true값이 반환된다고 한다.
		log.info("open = {} ",item.getOpen());
		
		Item savedItem = repo.save(item);
		redirectAttributes.addAttribute("itemId",savedItem.getId());
		redirectAttributes.addAttribute("status",true);
		return "redirect:/basic/items/{itemId}";
	}
		
	
	//수정 버튼 클릭시
	@GetMapping("/{itemId}/edit")
	public String editForm(@PathVariable Long itemId , Model model) {
		Item item = repo.findById(itemId);
		log.info("controller edit = {} ",item);
		
		model.addAttribute("item",item);
		return "basic/editForm";
	}
	
	//수정 폼에서 수정하기 버튼 클릭시
	@PostMapping("/{itemId}/edit")
	public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
		repo.update(itemId, item);
		return "redirect:/basic/items/{itemId}";
	}
	
	//ModelAttribute의 특별한 사용법으로 해당 메소드를 통해 값을 담아 보낼 수 있다.
	@ModelAttribute("regions")
	public Map<String,String> regions(){
		Map<String,String> regions = new LinkedHashMap<>();
		regions.put("SEOUL","서울");
		regions.put("BUSAN","부산");
		regions.put("JEJU","제주");
		return regions;
	}
	
	@ModelAttribute("itemTypes")
	public ItemType[] itemTypes() {
		return ItemType.values();
	}
	
	@ModelAttribute("deliveryCodes")
	public List<DeliveryCode> deliveryCodes() {
		List<DeliveryCode> deliveryCodes = new ArrayList<>();
		deliveryCodes.add(new DeliveryCode("FAST","빠른 배송"));
		deliveryCodes.add(new DeliveryCode("NORMAL","일반 배송"));
		deliveryCodes.add(new DeliveryCode("SLOW","느린 배송"));
		
		return deliveryCodes;
	}

	
	
	@PostConstruct
	public void init() {
		repo.save(new Item("testA",10000,10));
		repo.save(new Item("testB",15000,20));
	}
	
}

package jp.co.example.ecommerce_c.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ecommerce_c.domain.Item;
import jp.co.example.ecommerce_c.domain.Order;
import jp.co.example.ecommerce_c.domain.OrderItem;
import jp.co.example.ecommerce_c.domain.OrderTopping;
import jp.co.example.ecommerce_c.domain.Topping;
import jp.co.example.ecommerce_c.form.OrderItemForm;
import jp.co.example.ecommerce_c.repository.ItemRepository;
import jp.co.example.ecommerce_c.repository.OrderItemRepository;
import jp.co.example.ecommerce_c.repository.OrderRepository;
import jp.co.example.ecommerce_c.repository.OrderToppingRepository;
import jp.co.example.ecommerce_c.repository.ToppingRepository;

/**
 * ログインしていないユーザがショッピングカートの操作をするサービス.
 * 
 * @author koichi.nagata
 *
 */
@Service
@Transactional
public class GuestShoppingCartService {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private OrderToppingRepository orderToppingRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private ToppingRepository toppingRepository;


	/**
	 * ユーザIDに結びついたorderがない場合に、新しくorderを作る.
	 * 
	 * @param userId ユーザID
	 * @return 新しく作ったオーダー
	 */
	public Order createNewOrder(Integer userId) {
		Order order = new Order();
		order.setUserId(userId);
		orderRepository.insert(order);
		order = orderRepository.findByUserId(userId);
//		List<OrderItem> orderItemList = new ArrayList<>();
//		order.setOrderItemList(orderItemList);
		System.out.println(order.getId());
		return order;
	}

	/**
	 * ピザをショッピングカートに追加する.
	 * 
	 * @param form    フォーム
	 * @param orderId オーダーID
	 * @param userId  ユーザID
	 */
	public void insert(OrderItemForm form, Integer orderId, Integer userId) {
		Order order = new Order();
		if (orderId == null) {
			order = createNewOrder(userId);
		}else {
			order = orderRepository.findById(orderId);
		}
		orderId = order.getId();
		OrderItem pizza = new OrderItem();
		pizza.setOrderId(orderId);
		pizza.setItemId(Integer.parseInt(form.getItemId()));
		pizza.setQuantity(Integer.parseInt(form.getQuantity()));
		pizza.setSize(form.getSize().charAt(0));
		pizza.setId(orderItemRepository.insert(pizza));
		Item item = itemRepository.load(pizza.getItemId());
		pizza.setItem(item);
		List<OrderTopping> orderToppingList = new ArrayList<>();
		if (form.getOrderToppingList() != null) {
			for (Integer toppingId : form.getOrderToppingList()) {
				OrderTopping topping = new OrderTopping();
				topping.setOrderItemId(pizza.getId());
				topping.setToppingId(toppingId);
				orderToppingRepository.insert(topping);
				orderToppingList.add(topping);
			}
		}
		pizza.setOrderToppingList(orderToppingList);
		if (order.getOrderItemList() == null) {
			order.setOrderItemList(new ArrayList<OrderItem>());
		}
		order.setTotalPrice(order.getTotalPrice() + pizza.getSubTotal());
		orderRepository.update(order);
		order.getOrderItemList().add(pizza);
	}

	/**
	 * カート内の商品の一覧を表示する.
	 * 
	 * @param userId  ユーザID
	 * @param orderId オーダーID
	 * @return 注文内容(order)
	 */
	public Order showOrder(Integer userId, Integer orderId) {
		Order order = new Order();
		if (orderId == null) {
			order = createNewOrder(userId);
		}else {
			order = orderRepository.findById(orderId);
		}
		orderId = order.getId();
		List<OrderItem> orderItemList = orderItemRepository.findById(orderId);
		for (OrderItem pizza : orderItemList) {
			Item item = itemRepository.load(pizza.getItemId());
			pizza.setItem(item);
			List<OrderTopping> orderToppingList = orderToppingRepository.findById(pizza.getId());
			for (OrderTopping orderTopping : orderToppingList) {
				Topping topping = toppingRepository.load(orderTopping.getToppingId());
				orderTopping.setTopping(topping);
			}
			pizza.setOrderToppingList(orderToppingList);
		}
		order.setOrderItemList(orderItemList);
		return order;
	}

	/**
	 * カート内の商品を削除する.
	 * 
	 * @param orderItemId カートから削除するピザのID
	 * @param orderId     削除するピザを含んだオーダーのID
	 */
	public void deleteOrderItem(Integer orderItemId, Integer orderId) {
		Order order = orderRepository.findById(orderId);
		OrderItem orderItem = orderItemRepository.load(orderItemId);
		Item item = itemRepository.load(orderItem.getItemId());
		List<OrderTopping> orderToppingList = orderToppingRepository.findById(orderItemId);
		orderItem.setItem(item);
		if (orderToppingList == null) {
			orderToppingList = new ArrayList<>();
		}
		orderItem.setOrderToppingList(orderToppingList);
		order.setTotalPrice(order.getTotalPrice() - orderItem.getSubTotal());
		orderRepository.update(order);
		orderToppingRepository.deleteById(orderItemId);
		orderItemRepository.deleteById(orderItemId);
	}

}

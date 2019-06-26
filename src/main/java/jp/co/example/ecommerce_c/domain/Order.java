package jp.co.example.ecommerce_c.domain;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * オーダー内容のドメイン.
 * 
 * @author koichi.nagata
 *
 */
public class Order {
	/** オーダーID */
	private Integer id;
	/** 注文者ID */
	private Integer userId;
	/** 注文の状態 */
	private Integer status;
	/** 合計金額 */
	private Integer totalPrice;
	/** 注文日 */
	private Date orderDate;
	/** 宛先名 */
	private String destinationName;
	/** 宛先メールアドレス */
	private String destinationEmail;
	/** 宛先郵便番号 */
	private String destinationZipcode;
	/** 宛先住所 */
	private String destinationAddress;
	/** 宛先電話番号 */
	private String destinationTel;
	/** 配達時間 */
	private Timestamp deliveryTime;
	/** 支払方法 */
	private Integer paymentMethod;
	/** ユーザー */
	private User user;
	/** オーダーリスト */
	private List<OrderItem> orderItemList;
	/** 送料 */
	private Integer cost;

	@Override
	public String toString() {
		return "Order [id=" + id + ", userId=" + userId + ", status=" + status + ", totalPrice=" + totalPrice
				+ ", orderDate=" + orderDate + ", destinationName=" + destinationName + ", destinationEmail="
				+ destinationEmail + ", destinationZipcode=" + destinationZipcode + ", destinationAddress="
				+ destinationAddress + ", destinationTel=" + destinationTel + ", deliveryTime=" + deliveryTime
				+ ", paymentMethod=" + paymentMethod + ", user=" + user + ", orderItemList=" + orderItemList + ", cost="
				+ cost + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getDestinationEmail() {
		return destinationEmail;
	}

	public void setDestinationEmail(String destinationEmail) {
		this.destinationEmail = destinationEmail;
	}

	public String getDestinationZipcode() {
		return destinationZipcode;
	}

	public void setDestinationZipcode(String destinationZipcode) {
		this.destinationZipcode = destinationZipcode;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public String getDestinationTel() {
		return destinationTel;
	}

	public void setDestinationTel(String destinationTel) {
		this.destinationTel = destinationTel;
	}

	public Timestamp getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Timestamp deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public final Integer getCost() {
		return cost;
	}

	public final void setCost(Integer cost) {
		this.cost = cost;
	}

	/**
	 * 消費税のゲッター.
	 * 
	 * 未実装
	 * 
	 * @return 消費税
	 */
	public int getTax() {
		return (int) (getTotalPrice() * 0.08);
	}

	/**
	 * 消費税別の合計金額のゲッター.
	 * 
	 * 未実装
	 * 
	 * @return 合計金額
	 */
	public int getCalcTotalPrice() {
		return getTotalPrice() + getTax();
	}

	/**
	 * 支払方法によって注文の状態を変更
	 * 
	 */
	public void setStutasByPaymentMethod() {
		if (this.paymentMethod == 1) {
<<<<<<< Updated upstream
			this.setStatus(1);
		}else if(this.paymentMethod==2){
			this.setStatus(2);
		}else {
=======
			this.setStatus(OrderStatus.NOT_PAID);
		} else if (this.paymentMethod == 2) {
			this.setStatus(OrderStatus.PAID);
		} else {
>>>>>>> Stashed changes
			System.out.println("error:status代入");
		}
	}
}

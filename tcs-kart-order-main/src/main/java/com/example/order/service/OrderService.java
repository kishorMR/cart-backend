package com.example.order.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.order.bean.OrderBean;
import com.example.order.bean.OrderItemBean;
import com.example.order.bean.SendingMail;
import com.example.order.entity.Cart;
import com.example.order.entity.CartItem;
import com.example.order.entity.OrderItem;
import com.example.order.entity.Orders;
import com.example.order.entity.Product;
import com.example.order.entity.User;
import com.example.order.exception.*;
import com.example.order.repository.CartItemRepository;
import com.example.order.repository.CartRepository;
import com.example.order.repository.OrderItemRepository;
import com.example.order.repository.OrderRepository;
import com.example.order.repository.ProductRepository;
import com.example.order.repository.UserRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepo;

	@Autowired
	ProductRepository productPepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	OrderItemRepository itemRepo;

	@Autowired
	CartRepository cartRepo;

	@Autowired
	CartItemRepository cartItemRepo;

	@Autowired
	private JavaMailSender javaMailSender;

	public Orders PlaceOrder(OrderBean orderBean) {
		User user = userRepo.findByEmail(orderBean.getEmail());
		if (user == null) {
			throw new UserNotFoundException();
		}

		Orders order = new Orders();
		order.setUser(user);
		order.setAddress(orderBean.getAddress());
		order.setStatus("PENDING");
		LocalDate currentDate = LocalDate.now();
		order.setCreatedDate(currentDate);

		List<OrderItem> items = new ArrayList<>();
		Double TotalPrice = 0.0;
		for (OrderItemBean dto : orderBean.getOrderItemsBean()) {
			Product product = productPepo.findById(dto.getProductId())
					.orElseThrow(() -> new ProductNotFoundException());
			if (product.getQuantity() < dto.getQuantity()) {
				this.AlertMail(product);
				throw new NoEnoughQuantityException();
			}
		}
		ArrayList<String> ProductName = new ArrayList<>();
		for (OrderItemBean dto : orderBean.getOrderItemsBean()) {

			Product product = productPepo.findById(dto.getProductId())
					.orElseThrow(() -> new ProductNotFoundException());
			product.setQuantity(product.getQuantity() - dto.getQuantity());
			if (product.getQuantity() < 5) {
				this.AlertMail(product);
			}
			ProductName.add(product.getProductName());
			OrderItem item = new OrderItem();
			item.setOrders(order);
			item.setProduct(product);
			item.setQuantity(dto.getQuantity());
			item.setPrice(product.getPrice() * dto.getQuantity());
			TotalPrice += item.getPrice();
			productPepo.save(product);
			items.add(item);
		}

		order.setTotalAmount(TotalPrice);
		order.setOrderItems(items);
		SendingMail mailContent = new SendingMail();
		String recipient = user.getEmail();
		mailContent.setRecipient(recipient);
		String subject = "Order Confirmation - Thank you for your purchase!";
		mailContent.setSubject(subject);
		StringBuilder msgBody = new StringBuilder();
		Orders orderConfrim = orderRepo.save(order);
		msgBody.append("Hi ").append(user.getName()).append(",\n\n").append(
				"We’re excited to let you know that your order from TCSKart has been successfully placed! 🎉\n\n")
				.append("🧾 Here’s a quick summary of your order:\n").append("🆔 Order Number : ")
				.append(orderConfrim.getOrderId()).append("\n").append("📅 Placed On    : ")
				.append(order.getCreatedDate()).append("\n").append("💰 Total Paid   : ₹")
				.append(order.getTotalAmount()).append("\n").append("🏠 Shipping To  : ").append(order.getAddress())
				.append("\n\n").append("🛍️ Items in Your Order:\n");

		for (OrderItem item : items) {
			for (String a : ProductName) {
				msgBody.append("- ").append(a).append(" × ").append(item.getQuantity()).append(" = ₹")
						.append(item.getPrice()).append("\n");
			}
		}

		msgBody.append("\nWe will notify you once your order is shipped.\n")
				.append("If you have any questions, contact us at support@tcskart.com.\n\n")
				.append("Thanks,\nTCSKart Team");

		String finalMsgBody = msgBody.toString();
		mailContent.setMessageBody(finalMsgBody);
		String output = this.received(mailContent);
		return orderConfrim;
	}

	public List<Orders> getOrdersByUserEmail(String email) {
		User user = userRepo.findByEmail(email);
		if (user == null) {
			throw new UserNotFoundException();
		}
		List<Orders> myOrders = orderRepo.findByUserEmail(email);
		if (myOrders.size() == 0) {
			throw new OrdersNotFoundException();
		}
		return myOrders;
	}

	public List<Orders> getallorders() {
		List<Orders> myOrders = orderRepo.findAll();
		if (myOrders.size() == 0) {
			throw new OrdersNotFoundException();
		}
		return myOrders;
	}

	public String updateStatus(Long id, String status) {
		Orders order = orderRepo.findById(id).orElseThrow(() -> new OrdersNotFoundException());
		order.setStatus(status);
		orderRepo.save(order);
		return "Sucessfully Update";
	}

	public String received(SendingMail mailContent) {
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();

			mailMessage.setFrom("rcbtcskart@gmail.com");
			mailMessage.setTo(mailContent.getRecipient());
			mailMessage.setText(mailContent.getMessageBody());
			mailMessage.setSubject(mailContent.getSubject());
			javaMailSender.send(mailMessage);
			return "Mail Sent Successfully...";
		}

		catch (Exception e) {
			return "Error while Sending Mail";
		}
	}

	public Orders cartMoveToOrder(String mail, String address) {
		User user = userRepo.findByEmail(mail);
		if (user == null) {
			throw new UserNotFoundException();
		}
		Cart cart = cartRepo.findByUserEmail(mail);
		if (cart == null) {
			throw new OrdersNotFoundException();
		}
		OrderBean order = new OrderBean();
		order.setEmail(mail);
		order.setAddress(address);
		List<OrderItemBean> OrderItems = new ArrayList<>();
		for (CartItem a : cart.getCartItems()) {
			OrderItemBean item = new OrderItemBean();
			Product product = a.getProduct();
			item.setProductId(product.getProductId());
			item.setQuantity(a.getQuantity());
			OrderItems.add(item);
		}
		order.setOrderItemsBean(OrderItems);
		Orders orderReturn = this.PlaceOrder(order);
		this.removeAllItemsFromCart(cart.getCartId());
		return orderReturn;
	}

	public void AlertMail(Product product) {
		SendingMail mail = new SendingMail();
		mail.setRecipient("gurukiranm2933@gmail.com");
		mail.setSubject("Low Stock Alert: " + product.getProductName());

		String message = "Dear Admin,\n\n" + "The product \"" + product.getProductName() + "\" (ID: "
				+ product.getProductId() + ") " + "is running low on stock.\n" + "Current available quantity: "
				+ product.getQuantity() + "\n\n" + "Please take necessary action.\n\n" + "Regards,\n"
				+ "TCSkart Inventory System";

		mail.setMessageBody(message);

		this.received(mail);
	}

	public void removeAllItemsFromCart(Long cartId) {

		List<CartItem> cartItems = cartItemRepo.findByCartId(cartId);

		for (CartItem cartItem : cartItems) {
			Product product = cartItem.getProduct();
			product.setQuantity(product.getQuantity() + cartItem.getQuantity());
			productPepo.save(product);
		}

		cartItemRepo.deleteById(cartId);
	}
}

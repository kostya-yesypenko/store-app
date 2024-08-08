package com.kostik.store.domain;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import lombok.Data;

@Data
public class Cart {
	private Long userId;

	private Set<Order> orders;

	public Cart() {
		this.orders = new LinkedHashSet<>();
	}

	// if already exist order with same user and product then simply update the quantity, else add order
	public void addOrder(Order order) {
		var existingOrder = getOrder(order);
		if (existingOrder == null) {
			this.orders.add(order);
		} else {
			existingOrder.addQuantity(order.getQty());
		}
	}

	private Order getOrder(Order order) {
		for (var o : orders) {
			if (Objects.equals(o, order)) {
				return o;
			}
		}
		return null;
	}

	public void removeOrder(Order order) {
		orders.remove(order);
	}
}

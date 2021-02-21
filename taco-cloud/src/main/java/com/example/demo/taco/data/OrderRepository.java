package com.example.demo.taco.data;

import com.example.demo.taco.model.Order;

public interface OrderRepository {

	public Order saveOrder(Order order);
}

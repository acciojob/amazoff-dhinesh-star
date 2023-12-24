package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    public String addOrder(Order order){
        String response = orderRepository.addOrder(order);
        return response;
    }

    public String addPartner(DeliveryPartner deliveryPartner){
        String response = orderRepository.addPartner(deliveryPartner);
        return response;
    }

    public String addOrderPartnerPair(String orderId, String partnerId){
        String response = orderRepository.addOrderPartnerPair(orderId,partnerId);
        return response;
    }

    public Order getOrderById(String orderId){
        Order order = orderRepository.getOrderById(orderId);
        return order;
    }

    public DeliveryPartner getPartnerById(String partnerId){
        DeliveryPartner deliveryPartner = orderRepository.getPartnerById(partnerId);
        return deliveryPartner;
    }

    public Integer getOrderCountByPartnerId(String partnerId){
        Integer orderCount= orderRepository.getOrderByPartnerId(partnerId).size();
        return orderCount;
    }
    public List<String> getOrdersByPartnerId(String partnerId){
        List<Order> orderList = orderRepository.getOrderByPartnerId(partnerId);
        List<String> orderIdList = new ArrayList<>();
        for(Order order:orderList){
            orderIdList.add(order.getId());
        }
        return orderIdList;
    }
    public List<String> getAllOrders(){
        List<Order> orderList = orderRepository.getAllOrders();
        List<String> orderIdList = new ArrayList<>();
        for(Order order:orderList){
            orderIdList.add(order.getId());
        }
        return orderIdList;
    }
    public Integer getCountOfUnassignedOrders(){
        List<Order> orderList = orderRepository.getAllOrders();
        HashSet<String> assignedOrder = orderRepository.getAssignOrder();
        Integer count=0;
        for(Order order:orderList){
            if(assignedOrder.contains(order.getId())==false) count++;
        }
        return count;
    }
    public Integer getOrdersLeftAfterGivenTimeByPartnerId(int time, String partnerId){
        ArrayList<Order> orderList = orderRepository.getOrderByPartnerId(partnerId);
        List<String> listOfOrderLeftUnDelivery = new ArrayList<>();
        for(Order order:orderList){
            if(time>order.getDeliveryTime()) listOfOrderLeftUnDelivery.add(order.getId());
        }
        return listOfOrderLeftUnDelivery.size();
    }
    public int getLastDeliveryTimeByPartnerId(String partnerId){
        ArrayList<Order> deliveryList = orderRepository.getOrderByPartnerId(partnerId);
        int timeInMins=Integer.MIN_VALUE;
        for(Order order:deliveryList){
            timeInMins = Math.max(timeInMins,order.getDeliveryTime());
        }
        return timeInMins;
    }
    public String deletePartnerById(String partnerId){
        String response = orderRepository.deletePartnerById(partnerId);
        return response;
    }
    public String deleteOrderById(String orderId){
        String rep = orderRepository.deleteOrderById(orderId);
        return rep;
    }
}

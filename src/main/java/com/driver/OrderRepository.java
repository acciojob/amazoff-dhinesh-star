package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

@Repository
public class OrderRepository {
    HashMap<String,Order> orderDB=new HashMap<>();
    HashMap<String,DeliveryPartner> deliveryPartnerDB=new HashMap<>();
    HashMap<String, ArrayList<Order>> deliveryPartnerWithOrderDB= new HashMap<>();
    HashMap<String, String> deliveryPartnerFindingWithOrderIdDB = new HashMap<>();
    HashSet<String> assignOrder = new HashSet<>();
    public HashSet<String> getAssignOrder() {
        return assignOrder;
    }
    public String addOrder(Order order){
        orderDB.put(order.getId(),order);
        return "Order successfully added";
    }

    public String addPartner(DeliveryPartner deliveryPartner){
        deliveryPartnerDB.put(deliveryPartner.getId(),deliveryPartner);
        return "DeliveryPartner successfully added";
    }

    public String addOrderPartnerPair(String orderId, String partnerId){
        Order order = orderDB.get(orderId);
        ArrayList<Order> list = deliveryPartnerWithOrderDB.getOrDefault(partnerId,new ArrayList<>());
        list.add(order);
        assignOrder.add(order.getId());
        deliveryPartnerWithOrderDB.put(orderId,list);
        deliveryPartnerFindingWithOrderIdDB.put(orderId,partnerId);
        DeliveryPartner dp = deliveryPartnerDB.get(partnerId);
        dp.setNumberOfOrders(list.size());
        return "Added Successfully the partner with order";
    }
    public Order getOrderById(String orderId){
        Order order = orderDB.get(orderId);
        return order;
    }
    public DeliveryPartner getPartnerById(String partnerId){
        DeliveryPartner deliveryPartner = deliveryPartnerDB.get(partnerId);
        return deliveryPartner;
    }

    public ArrayList<Order> getOrderByPartnerId(String partnerId){
        System.out.println("deliveryPartnerWithOrderDB:- "+deliveryPartnerWithOrderDB); ///Added by me
        ArrayList<Order> orderList = deliveryPartnerWithOrderDB.get(partnerId);
        return orderList;
    }
    public ArrayList<Order> getAllOrders(){
        ArrayList<Order> orderList = new ArrayList<>();
        for(String orderId:orderDB.keySet()){
            orderList.add(orderDB.get(orderId));
        }
        return orderList;
    }
    public String deletePartnerById(String partnerId){
        deliveryPartnerDB.remove(partnerId);
        ArrayList<Order> listOrder = deliveryPartnerWithOrderDB.get(partnerId);
        for(Order order:listOrder){
            assignOrder.remove(order.getId());
        }
        return "Remove Successfully";
    }
    public String deleteOrderById(String orderId){
        Order order = orderDB.get(orderId);
        String deliveryPartner = deliveryPartnerFindingWithOrderIdDB.get(orderId);
        ArrayList<Order> orderList = deliveryPartnerWithOrderDB.get(deliveryPartner);
        if(orderList!=null) {
            orderList.remove(order);
            deliveryPartnerWithOrderDB.put(deliveryPartner, orderList);
        }
        return "Removed Successfully";
    }
}

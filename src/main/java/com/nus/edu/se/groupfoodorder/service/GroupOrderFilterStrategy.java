package com.nus.edu.se.groupfoodorder.service;

import com.nus.edu.se.groupfoodorder.dto.GroupFoodOrderList;

import java.util.List;

public interface GroupOrderFilterStrategy {
    List<GroupFoodOrderList> filter(List<GroupFoodOrderList> orders, String criteria);
}

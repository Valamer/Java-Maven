package com.example.glovo.mapper;

import com.example.glovo.dto.OrderDTO;

import com.example.glovo.entity.ItemEntity;
import com.example.glovo.entity.OrderEntity;

public class OrderMapper {

    public static OrderDTO toDTO(OrderEntity order) {
        return OrderDTO.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .itemsId(order.getItems().stream().map(ItemEntity::getId).toList())
                .build();
    }

    public static OrderEntity toEntity(OrderDTO dto) {
        return OrderEntity.builder()
                .id(dto.getId())
                .orderNumber(dto.getOrderNumber())
                .build();
    }
}

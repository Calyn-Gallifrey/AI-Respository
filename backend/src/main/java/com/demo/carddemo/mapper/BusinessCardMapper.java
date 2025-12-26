package com.demo.carddemo.mapper;

import com.demo.carddemo.model.BusinessCard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BusinessCardMapper {
    List<BusinessCard> findByUserId(Long userId);
    BusinessCard findById(Long id);
}

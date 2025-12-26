package com.demo.carddemo.mapper;

import com.demo.carddemo.model.BusinessCardSetting;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BusinessCardSettingMapper {
    List<BusinessCardSetting> findByCardId(Long cardId);
    void insertSetting(BusinessCardSetting setting);
    void updateSetting(BusinessCardSetting setting);
    BusinessCardSetting findByCardIdAndKey(Long cardId, String settingKey);
}

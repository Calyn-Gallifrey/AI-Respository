package com.codex.platform.mapper;

import com.codex.platform.domain.WelcomeMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface WelcomeMessageMapper {

    @Select("SELECT id, content FROM welcome_message ORDER BY id LIMIT 1")
    WelcomeMessage findTopMessage();
}

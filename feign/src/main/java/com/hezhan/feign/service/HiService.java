package com.hezhan.feign.service;

import com.hezhan.feign.entity.Hi;

public interface HiService {
    String get(Hi hi);

    String testHi();
}

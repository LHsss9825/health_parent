package com.qf.service;

import com.qf.entity.Result;

import java.util.Map;

public interface OrderService {
    Result submit(Map map) throws Exception;
}

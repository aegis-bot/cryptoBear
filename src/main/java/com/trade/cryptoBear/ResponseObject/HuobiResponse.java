package com.trade.cryptoBear.ResponseObject;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.ResponseBody;

import com.trade.cryptoBear.upstreamObjects.HuobiObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ResponseBody
public class HuobiResponse {
    private ArrayList<HuobiObject> data;
    // Getters and setters
}

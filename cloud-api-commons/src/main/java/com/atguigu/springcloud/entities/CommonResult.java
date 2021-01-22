package com.atguigu.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
* @author: guojd
* @date: 2021/1/7
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T>{

    private Integer code;
    private String message;
    private T data;

    public CommonResult(Integer code, String message)
    {
        this(code,message,null);
    }
}

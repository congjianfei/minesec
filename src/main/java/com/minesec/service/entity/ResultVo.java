package com.minesec.service.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultVo<T> {
    private String msg;
    private String msgCode;
    private T result;

    public static<T> ResultVo createOkResult(T result){
        ResultVo<T> tResultVo = new ResultVo<>();
        tResultVo.setMsg("ok");
        tResultVo.setMsgCode("1000");
        tResultVo.setResult(result);
        return tResultVo;
    }
    public static ResultVo createErrorResult(String msg,String msgCode){
        ResultVo tResultVo = new ResultVo<>();
        tResultVo.setMsg(msg);
        tResultVo.setMsgCode(msgCode);
        return tResultVo;
    }
    public static ResultVo createErrorResult(String[] msgArray){
        if (msgArray != null && msgArray.length >1){
            ResultVo tResultVo = new ResultVo<>();
            tResultVo.setMsg(msgArray[0]);
            tResultVo.setMsgCode(msgArray[1]);
            return tResultVo;
        }
        ResultVo tResultVo = new ResultVo<>();
        tResultVo.setMsg("未知错误");
        tResultVo.setMsgCode("9999");
        return tResultVo;
    }
    public static ResultVo createErrorResult(){
        ResultVo tResultVo = new ResultVo<>();
        tResultVo.setMsg("未知错误");
        tResultVo.setMsgCode("9999");
        return tResultVo;
    }
}

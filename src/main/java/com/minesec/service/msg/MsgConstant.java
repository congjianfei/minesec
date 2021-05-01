package com.minesec.service.msg;

public class MsgConstant {
    public interface ResultMsg{
        String[] EMPTY_USER ={"user is not exit","3002"};
        String[] ADD_UPDATE_USER ={"add or update user error","3003"};
        String[] VERIFY_ERROR={"The certificate is not present within the request","3000"};
        String[] VERIFY_CTR_ERROR={"This certificate is not a valid one","3001"};
        String[] VERIFY_CUSTOMER_ERROR={"customer id not match","-1000"};
        String[] VERIFY_SDK_MATCH_ERROR={"access control denied","-1001"};
    }
    public interface Constant{
        long MAX_EXPIRE_TIME = 1000*60*60*36;
        String VERSION="1.0";
    }
}

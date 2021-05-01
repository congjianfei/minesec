package com.minesec.service.dao;

import lombok.Data;

import java.util.Date;

@Data
public class UserDeviceInfoDao {
    private String userId;
    private boolean root;
    private boolean safetyNetAttestationAvailable;
    private boolean safetyNetAttestationTrusted;
    private boolean basicIntegrity;
    private boolean ctsProfileMatch;
    private Date createTime;
    private Date updateTime;
}

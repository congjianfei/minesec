package com.minesec.service.controller;

import com.minesec.service.aspect.AdditionalCertificateValidations;
import com.minesec.service.aspect.LogCertificate;
import com.minesec.service.aspect.LogClientType;
import com.minesec.service.core.Mineutils;
import com.minesec.service.dao.CustomerInfoDao;
import com.minesec.service.dao.CywallInfoDao;
import com.minesec.service.dao.SdkInfoDao;
import com.minesec.service.dao.UserDeviceInfoDao;
import com.minesec.service.entity.ResultVo;
import com.minesec.service.entity.SdkRegisterReqVo;
import com.minesec.service.entity.SdkRegisterResVo;
import com.minesec.service.msg.MsgConstant;
import com.minesec.service.service.SdkOtherInfoService;
import com.minesec.service.service.SdkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api("sdk模块")
@RestController
@Slf4j
public class SdkController {
    @Autowired
    private SdkService sdkService;
    @Autowired
    private SdkOtherInfoService sdkOtherInfoService;

    @RequestMapping(value = "sdk/api/test",method = RequestMethod.POST)
    public String testRequest(){
        log.info("testRequest:====");
        return "hello";
    }
    @LogClientType
    @LogCertificate(detailed = true)
    @AdditionalCertificateValidations()
    @ApiOperation("register注册接口")
    @RequestMapping(value = "sdk/api/register",method = RequestMethod.POST)
    public ResultVo registerSdk(@RequestBody SdkRegisterReqVo sdkRegisterVo) {
        log.info("registerSdk:===="+sdkRegisterVo.toString());
        SdkInfoDao sdkInfoDao = new SdkInfoDao();
//        int result = -1;
        /**
         * step2 通过sdkId给csr
         */
        if (StringUtils.hasText(sdkRegisterVo.getId())){
            sdkInfoDao.setSdkId(sdkRegisterVo.getId());
            sdkInfoDao.setCustomerId(sdkRegisterVo.getCustomerid());
            sdkInfoDao.setCywallId(sdkRegisterVo.getCywallid());
            sdkInfoDao.setPkN(sdkRegisterVo.getPk().getN());
            sdkInfoDao.setPkE(sdkRegisterVo.getPk().getE());
            sdkInfoDao.setTimeStamp(sdkRegisterVo.getTimestamp());

            if (StringUtils.hasText(sdkRegisterVo.getCustomerid())){
                CustomerInfoDao customerInfoDao = new CustomerInfoDao();
                customerInfoDao.setCustomerId(sdkRegisterVo.getCustomerid());
                sdkOtherInfoService.updateCustomerInfo(customerInfoDao);
            }
            if (StringUtils.hasText(sdkRegisterVo.getCywallid())){
                CywallInfoDao cywallInfoDao = new CywallInfoDao();
                cywallInfoDao.setCywallId(sdkRegisterVo.getCywallid());
                sdkOtherInfoService.updateCywallInfo(cywallInfoDao);
            }
            String cert = Mineutils.genCertWithStream(sdkRegisterVo.getCsr());
            int result = sdkService.updateSdkInfo(sdkInfoDao);
            if (result>0){
                SdkRegisterResVo resultSdkRegisterVo = new SdkRegisterResVo();
                resultSdkRegisterVo.setId(sdkInfoDao.getSdkId());
                //todo 需要设置cert证书
                resultSdkRegisterVo.setCert(cert);
                return ResultVo.createOkResult(resultSdkRegisterVo);
            }else{
                return ResultVo.createErrorResult(MsgConstant.ResultMsg.ADD_UPDATE_USER);
            }
        }else{
            /**
             * step1 注册用户，并且插入各种信息，返回客户端sdkid
             */
            String uuId = UUID.randomUUID().toString();
            sdkInfoDao.setSdkId(uuId);

            sdkInfoDao.setCustomerId(sdkRegisterVo.getCustomerid());
            sdkInfoDao.setCywallId(sdkRegisterVo.getCywallid());
            sdkInfoDao.setPkN(sdkRegisterVo.getPk().getN());
            sdkInfoDao.setPkE(sdkRegisterVo.getPk().getE());
            sdkInfoDao.setTimeStamp(sdkRegisterVo.getTimestamp());
            sdkInfoDao.setExpired(MsgConstant.Constant.MAX_EXPIRE_TIME);
            sdkInfoDao.setVersion(MsgConstant.Constant.VERSION);
            sdkInfoDao.setUserId(uuId);

            if (StringUtils.hasText(sdkRegisterVo.getCustomerid())){
                CustomerInfoDao customerInfoDao = new CustomerInfoDao();
                customerInfoDao.setCustomerId(sdkRegisterVo.getCustomerid());
                sdkOtherInfoService.insertCustomerInfo(customerInfoDao);
            }
            if (StringUtils.hasText(sdkRegisterVo.getCywallid())){
                CywallInfoDao cywallInfoDao = new CywallInfoDao();
                cywallInfoDao.setCywallId(sdkRegisterVo.getCywallid());
                sdkOtherInfoService.insertCywallInfo(cywallInfoDao);
            }
            UserDeviceInfoDao userDeviceInfoDao = new UserDeviceInfoDao();
            userDeviceInfoDao.setUserId(uuId);
            userDeviceInfoDao.setRoot(false);
            userDeviceInfoDao.setSafetyNetAttestationAvailable(true);
            userDeviceInfoDao.setSafetyNetAttestationTrusted(true);
            userDeviceInfoDao.setBasicIntegrity(true);
            userDeviceInfoDao.setCtsProfileMatch(true);
            sdkOtherInfoService.insertUserDeviceInfo(userDeviceInfoDao);

            int result = sdkService.registerSdkInfo(sdkInfoDao);
            if (result>0){
                SdkRegisterResVo resultSdkRegisterVo = new SdkRegisterResVo();
                resultSdkRegisterVo.setId(sdkInfoDao.getSdkId());
                //todo 需要设置cert证书
                resultSdkRegisterVo.setCert("");
                return ResultVo.createOkResult(resultSdkRegisterVo);
            }else{
                return ResultVo.createErrorResult(MsgConstant.ResultMsg.ADD_UPDATE_USER);
            }
        }
    }
    @LogClientType
    @LogCertificate(detailed = true)
    @AdditionalCertificateValidations()
    @ApiOperation(value = "sdk信息请求接口",notes = "根据sdk的id和customerid获取注册的sdk信息")
    @RequestMapping(value = "/sp/api/info/{customerid}/{skid}",method = RequestMethod.GET)
    public ResultVo sdkInfo(@PathVariable("customerid")String customerId,@PathVariable("skid")String sdkId){
        if (StringUtils.hasText(customerId) && StringUtils.hasText(sdkId)){
            SdkInfoDao sdkInfoDao = sdkService.querySdkInfoById(customerId,sdkId);
            if (sdkInfoDao != null){
                if (sdkInfoDao.isSdkState()){
                    sdkInfoDao.setState("VALID");
                }else{
                    sdkInfoDao.setState("NOT VALID");
                }
                return ResultVo.createOkResult(sdkInfoDao);
            }else{
                return ResultVo.createErrorResult(MsgConstant.ResultMsg.EMPTY_USER);
            }
        }
        return ResultVo.createErrorResult();
    }
}

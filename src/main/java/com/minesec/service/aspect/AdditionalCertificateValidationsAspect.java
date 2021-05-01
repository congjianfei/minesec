package com.minesec.service.aspect;

import com.minesec.service.dao.CustomerInfoDao;
import com.minesec.service.dao.SdkInfoDao;
import com.minesec.service.entity.ResultVo;
import com.minesec.service.msg.MsgConstant;
import com.minesec.service.service.SdkOtherInfoService;
import com.minesec.service.service.SdkService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.security.auth.x500.X500Principal;
import javax.servlet.http.HttpServletRequest;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Aspect
@Configuration
@EnableAspectJAutoProxy
public class AdditionalCertificateValidationsAspect {
//    MongoOperations mongoOps = new AppConfig().mongoTemplate();

    @Autowired
    private SdkService sdkService;
    @Autowired
    private SdkOtherInfoService sdkOtherInfoService;

    public final String defaultCommonName  = "Client";

    private static final Logger LOGGER = LogManager.getLogger(AdditionalCertificateValidationsAspect.class);
    private static final String KEY_CERTIFICATE_ATTRIBUTE = "javax.servlet.request.X509Certificate";
    private static final Pattern COMMON_NAME_PATTERN = Pattern.compile("(?<=CN=)(.*?)(?=,)");

    @Around("@annotation(certificateValidations)")
    public Object validate(ProceedingJoinPoint joinPoint,
                           AdditionalCertificateValidations certificateValidations) throws Throwable {

        if (!getCertificatesFromRequest().isPresent()) {
            LOGGER.error("Skipping common name validation because certificate is not present within the request");
//            return joinPoint.proceed();
            ResultVo resultVo = new ResultVo();
            resultVo.setMsgCode(MsgConstant.ResultMsg.VERIFY_ERROR[1]);
            resultVo.setMsg(MsgConstant.ResultMsg.VERIFY_ERROR[0]);
            return resultVo;
        }

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String uri = request.getRequestURI();
        String customerid = null;
        if(uri.contains("sp")) {
            customerid = uri.split("/")[4];
        }

        List<String> notAllowedCommonNames = Arrays.asList(certificateValidations.notAllowedCommonNames());
//
        List<String> allowedCommonNames = new ArrayList<>();
        allowedCommonNames.add(defaultCommonName);
        List<CustomerInfoDao> infoDaoList = sdkOtherInfoService.findAllCustomerId();
//        List<SdkInfoVo> sdkInfos = mongoOps.findAll(SDKInfo.class);
        for (CustomerInfoDao customerInfoDao: infoDaoList) {
            if(StringUtils.hasText(customerInfoDao.getCustomerId())) {
                allowedCommonNames.add(customerInfoDao.getCustomerId());
            }
        }

        Optional<String> allowedCommonName = getCommonNameFromCertificate()
                .filter(commonName -> allowedCommonNames.isEmpty() || allowedCommonNames.contains(commonName))
                .filter(commonName -> notAllowedCommonNames.isEmpty() || !notAllowedCommonNames.contains(commonName));

        if (allowedCommonName.isPresent()) {
            LOGGER.info("Customer id: " + customerid);
            if(customerid != null) {
                String sdkId = allowedCommonName.get();
//                SdkInfoDao sdkInfoDao = sdkService.querySdkInfoById(sdkId);//根据customerid和sdkid查询是否有匹配数据
//                if (sdkInfoDao != null && customerid.equals(sdkInfoDao.getCustomerId())){
                    return joinPoint.proceed();
//                }
//                if (!customerid.equals(sdkInfoDao.getCustomerId()){
//                    ResultVo resultVo = new ResultVo();
//                    resultVo.setMsgCode(MsgConstant.ResultMsg.VERIFY_SDK_MATCH_ERROR[1]);
//                    resultVo.setMsg(MsgConstant.ResultMsg.VERIFY_SDK_MATCH_ERROR[0]);
//                    return resultVo;
//                }
//
//                SDKInfo sdkInfo = mongoOps.findOne(query(where("uuid").is(uuid)), SDKInfo.class);
//                if(sdkInfo.getUserid().equals(customerid)) {
//                    return joinPoint.proceed();
//                }
//                LOGGER.error("LC:// access denied!" + customerid);
//                return new SPRes("-1001", "access control denied!");

//                ResultVo resultVo = new ResultVo();
//                resultVo.setMsgCode(MsgConstant.ResultMsg.VERIFY_SDK_MATCH_ERROR[1]);
//                resultVo.setMsg(MsgConstant.ResultMsg.VERIFY_SDK_MATCH_ERROR[0]);
//                return resultVo;
            }
            return joinPoint.proceed();
        } else {
            LOGGER.error("LC:// not valid!");
            ResultVo resultVo = new ResultVo();
            resultVo.setMsgCode(MsgConstant.ResultMsg.VERIFY_CTR_ERROR[1]);
            resultVo.setMsg(MsgConstant.ResultMsg.VERIFY_CTR_ERROR[0]);
            return resultVo;
        }
    }

    private Optional<String> getCommonNameFromCertificate() {
        return getCertificatesFromRequest()
                .map(Arrays::stream)
                .flatMap(Stream::findFirst)
                .map(X509Certificate::getSubjectX500Principal)
                .map(X500Principal::getName)
                .flatMap(this::getCommonName);
    }

    private Optional<X509Certificate[]> getCertificatesFromRequest() {
        return Optional.ofNullable((X509Certificate[]) ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest()
                .getAttribute(KEY_CERTIFICATE_ATTRIBUTE));
    }

    private Optional<String> getCommonName(String subjectDistinguishedName) {
        Matcher matcher = COMMON_NAME_PATTERN.matcher(subjectDistinguishedName);

        if (matcher.find()) {
            return Optional.of(matcher.group());
        } else {
            return Optional.empty();
        }
    }

}

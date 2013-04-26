/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2013 All Rights Reserved.
 */
package com.alipay.ardp;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 字段黑名单，目前元数据对于导入的字段不能识别
 * 
 * @author ji.zhangyj
 * @version $Id: BlackListFields.java, v 0.1 2013-4-26 下午2:43:41 ji.zhangyj Exp $
 */
public class BlacklistFields {
    private static final String[] blacklist = new String[] { "AccountNo", "ActiveKey", "Address",
            "AgentCard", "AgentName", "AgreementNo", "AlipayProductCode", "Area", "AreaSize",
            "AuditState", "AuthId", "Background", "BankCardAlias", "BankCardNo", "BankCardNoType",
            "BankCardType", "BindMobile", "Birthday", "BizPoolManagers", "BizType", "BlackType",
            "BusinessMode", "CardAliasDefault", "CardAliasName", "CardControlFlag", "CardNo",
            "CardUniqueNo", "Cell", "CertAddress", "CertBizFrom", "CertFileUrl", "CertFrom",
            "CertNo", "CertType", "CertValidBeginDate", "CertValidEndDate", "CertifyInfo",
            "ChannelType", "City", "ContactName", "CorpCard", "Corporation", "Country",
            "CreateOperator", "Creator", "CustomerGroup", "CustomerId", "CustomerName",
            "CustomerShortName", "DefaultCardAlias", "Department", "DisplayAlias", "District",
            "Education", "ElementKey", "ElementValue", "Email", "EmailCardAlias", "EmployeeId",
            "EmployeeNumber", "EnablePayment", "EnumDbValue", "EnumInsName", "EnumValue",
            "ExternalId", "Fax", "GmtActive", "GmtCancel", "GmtCertInvalid", "GmtCertValid",
            "GmtCreate", "GmtDisCard", "GmtEffect", "GmtEnd", "GmtForbidden", "GmtModified",
            "GmtRegisterDate", "GmtSign", "GmtUnsign", "GmtVerified", "HavanaId", "IM",
            "IndustoryPool", "IndustryType", "InstId", "InstName", "Internet", "IsAdminOperator",
            "IsCanLogon", "IsCertified", "IsGlobalUser", "IsHidden", "IsVerified", "IwTradeLeve",
            "IwTradeLevel", "Key", "KeyName", "LastModifiedOperator", "LogonId", "LogonPassword",
            "Memo", "MerchantCardNo", "MerchantCertificateId", "MerchantContactId",
            "MerchantExtId", "MerchantId", "MerchantWebId", "Mobile", "MobileCardAlias",
            "Modifier", "Msn", "Name", "National", "NewEmail", "NewLogonId", "OpPassword",
            "OperatorId", "OperatorName", "OperatorNickName", "OrgName", "OutCustomerId",
            "OutSubCardNo", "OuterAlias", "PartnerId", "PaymentPassword", "Phone", "Position",
            "ProdCode", "Profession", "PropKey", "PropValue", "Province", "QQ", "RealName",
            "RegisterFrom", "RegisteredCapital", "SceneCode", "SignChannelApiCode", "SignId",
            "SignIntend", "SignRelationId", "Source", "SourceId", "SourceType", "State", "Status",
            "SubCardNo", "SubCardPwd", "SubCardType", "SwitchCorePropId", "SwitchId", "Type",
            "UsageRelationId", "UserId", "UserName", "Value", "Wangwang", "WebAddress", "WebName",
            "Zip"                          };
    
    public static boolean in(String fieldName){
        Set<String> set = new HashSet<String>(Arrays.asList(blacklist));
        return set.contains(fieldName);
    }

}

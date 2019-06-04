package com.yodoo.plugin

public class UploadToPGYERExtension {
    /**
     * (必填)值为debug、release等buildTypes
     */
    String uploadBuildType
    /**
     *  (必填)API Key
     */
    String pgyerApiKey
    /**
     * (选填)应用安装方式，值为(1,2,3，4)。1：公开，2：密码安装，3：邀请安装，4：回答问题安装。默认为1公开
     */
    int buildInstallType = 1
    /**
     * (选填) 设置App安装密码，如果不想设置密码，请传空字符串，或不传。
     */
    String buildPassword = ''
    /**
     * (选填) 版本更新描述，请传空字符串，或不传。
     */
    String buildUpdateDescription = ''
    /**
     * (选填) 应用名称
     */
    String buildName = ''
    /**
     * (选填)如果安装方式为回答问题安装，填写安装问题
     */
    String buildInstallQuestoin = ''
    /**
     * (选填)如果安装方式为回答问题安装，填写安装问题的答案。
     */
    String buildInstallAnswer = ""
    /**
     * (选填)是否设置安装有效期，值为：1 设置有效时间， 2 长期有效，如果不填写不修改上一次的设置
     */
    int buildInstallDate = 2
    /**
     * (选填)安装有效期开始时间，字符串型，如：2018-01-01
     */
    String buildInstallStartDate = ''
    /**
     * (选填)安装有效期结束时间，字符串型，如：2018-12-31
     */
    String buildInstallEndDate = ''
    /**
     *  上传完成后，是否打开app下载页面，默认打开
     */
    boolean isOpenWeb=true
}
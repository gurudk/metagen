/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2013 All Rights Reserved.
 */
package com.alipay.ardp;

/**
 * ��������һ������mvn���jar����Ԫ���ݿ���ɨ��jar���������Դ�ļ�
 * 
 * @author ji.zhangyj
 * @version $Id: Jar.java, v 0.1 2013-4-25 ����2:33:36 ji.zhangyj Exp $
 */
public class Jar {
    private String groupId;
    private String artifactId;
    private String version;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}

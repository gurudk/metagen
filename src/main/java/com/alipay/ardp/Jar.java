/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2013 All Rights Reserved.
 */
package com.alipay.ardp;

/**
 * 用来描述一个本地mvn库的jar包，元数据可以扫描jar包配置里的源文件
 * 
 * @author ji.zhangyj
 * @version $Id: Jar.java, v 0.1 2013-4-25 下午2:33:36 ji.zhangyj Exp $
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

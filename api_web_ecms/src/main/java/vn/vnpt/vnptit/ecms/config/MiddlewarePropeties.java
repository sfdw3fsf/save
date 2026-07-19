package vn.vnpt.vnptit.ecms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "middleware")
public class MiddlewarePropeties {
    public enum SystemType {
        IDG, ECS,
        SMARTCLOUD_INTERNAL, SMARTCLOUD_EXTERNAL,
        VCENTER,
        PRIVATE_SMARTCLOUD,
    }
    private Vcenter vcenter;
    private IDG idg;
    private SmartCloudInternal smartCloudInternal;
    private SmartCloudExternal smartCloudExternal;
    private ECS ecs;
    private PrivateSmartCloudAdmin privateSmartCloudAdmin;

    // propertities binding
    public static class Vcenter {
        private String baseUrl;
        private String privateKey;
        private String appName;
        public String getBaseUrl() {
            return baseUrl;
        }
        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }
        public String getPrivateKey() {
            return privateKey;
        }
        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }
        public String getAppName() {
            return appName;
        }
        public void setAppName(String appName) {
            this.appName = appName;
        }
    }

    public static class IDG {
        private String baseUrl;
        private String xApiKey;
        private String xUserEmail;

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public String getxApiKey() {
            return xApiKey;
        }

        public void setxApiKey(String xApiKey) {
            this.xApiKey = xApiKey;
        }

        public String getxUserEmail() {
            return xUserEmail;
        }

        public void setxUserEmail(String xUserEmail) {
            this.xUserEmail = xUserEmail;
        }
    }

    public static class SmartCloudInternal {
        private String baseUrl;
        private String xApiKey;
        private String xUserEmail;

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public String getxApiKey() {
            return xApiKey;
        }

        public void setxApiKey(String xApiKey) {
            this.xApiKey = xApiKey;
        }

        public String getxUserEmail() {
            return xUserEmail;
        }

        public void setxUserEmail(String xUserEmail) {
            this.xUserEmail = xUserEmail;
        }
    }

    public static class SmartCloudExternal {
        private String baseUrl;
        private String domain;
        private String ip;
        private String settingKey;

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getSettingKey() {
            return settingKey;
        }

        public void setSettingKey(String settingKey) {
            this.settingKey = settingKey;
        }
    }

    public static class ECS {
        private String baseUrl;
        private String username;
        private String password;

        public String getBaseUrl() {
            return baseUrl;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class PrivateSmartCloudAdmin {
        private String apiCloud;
        private String appPortal;
        private String consoleCloud;
        private String identityCloud;
        private String ip;
        private String protocol;
        private String settingKey;

        public String getApiCloud() {
            return String.format("%s://%s", protocol, apiCloud);
        }

        public void setApiCloud(String apiCloud) {
            this.apiCloud = apiCloud;
        }

        public String getAppPortal() {
            return String.format("%s://%s", protocol, appPortal);
        }

        public void setAppPortal(String appPortal) {
            this.appPortal = appPortal;
        }

        public String getConsoleCloud() {
            return String.format("%s://%s", protocol, consoleCloud);
        }

        public void setConsoleCloud(String consoleCloud) {
            this.consoleCloud = consoleCloud;
        }

        public String getIdentityCloud() {
            return String.format("%s://%s", protocol, identityCloud);
        }

        public void setIdentityCloud(String identityCloud) {
            this.identityCloud = identityCloud;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getProtocol() {
            return protocol;
        }

        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }

        public String getSettingKey() {
            return settingKey;
        }

        public void setSettingKey(String settingKey) {
            this.settingKey = settingKey;
        }
    }



    // GETTER - SETTER
    public Vcenter getVcenter() {
        return vcenter;
    }

    public void setVcenter(Vcenter vcenter) {
        this.vcenter = vcenter;
    }

    public IDG getIdg() {
        return idg;
    }

    public void setIdg(IDG idg) {
        this.idg = idg;
    }

    public SmartCloudInternal getSmartCloudInternal() {
        return smartCloudInternal;
    }

    public void setSmartCloudInternal(SmartCloudInternal smartCloudInternal) {
        this.smartCloudInternal = smartCloudInternal;
    }

    public SmartCloudExternal getSmartCloudExternal() {
        return smartCloudExternal;
    }

    public void setSmartCloudExternal(SmartCloudExternal smartCloudExternal) {
        this.smartCloudExternal = smartCloudExternal;
    }

    public ECS getEcs() {
        return ecs;
    }

    public void setEcs(ECS ecs) {
        this.ecs = ecs;
    }

    public PrivateSmartCloudAdmin getPrivateSmartCloudAdmin() {
        return privateSmartCloudAdmin;
    }

    public void setPrivateSmartCloudAdmin(PrivateSmartCloudAdmin privateSmartCloudAdmin) {
        this.privateSmartCloudAdmin = privateSmartCloudAdmin;
    }
}

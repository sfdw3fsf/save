package vn.vnpt.vnptit.ecms.dto.danhmuc;

public class ChangePortMegaVNNDtoIn {
    public class VisaLogin{
        private String userId;
        private String password;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
    private String account;
    private Object [] serviceObj;
    private VisaLogin visaLogin;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Object[] getServiceObj() {
        return serviceObj;
    }

    public void setServiceObj(Object[] serviceObj) {
        this.serviceObj = serviceObj;
    }

    public VisaLogin getVisaLogin() {
        return visaLogin;
    }

    public void setVisaLogin(VisaLogin visaLogin) {
        this.visaLogin = visaLogin;
    }
}

package vn.vnptit.sapi.oneapp.properties;

public class DataSourceConfig {
    private String poolType; //: hikari
    private String username;//: ccs_sync
    private String password;//: Qg7JO3Y7nzG55mc4jnu+aBmM9bv2wuL5
    private String url;
    //: jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=10.156.5.40)(PORT=1521))(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=vnpccbs.vnp.vn)))
    private int maxPoolSize;//: 10
    private int maxStatements;//: 10
    private int maxConnReuseTIme;//: 300
    private int maxConnReuseCount;//: 100
    private int abandonedConnTimeoutSecond;//: 30
    private int connWaitTimeoutSecond;//: 30
    private int inactiveConnTimeoutSecond;//: 10
    private int queryTimeoutSecond = 0;
    private int maxLifetime = 180000;
    private int minimumIdle;

    public int getMinimumIdle() {
        return minimumIdle;
    }

    public void setMinimumIdle(int minimumIdle) {
        this.minimumIdle = minimumIdle;
    }

    public int getMaxLifetime() {
        return maxLifetime;
    }

    public void setMaxLifetime(int maxLifetime) {
        this.maxLifetime = maxLifetime;
    }

    public String getPoolType() {
        return poolType;
    }

    public void setPoolType(String poolType) {
        this.poolType = poolType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getMaxStatements() {
        return maxStatements;
    }

    public void setMaxStatements(int maxStatements) {
        this.maxStatements = maxStatements;
    }

    public int getMaxConnReuseTIme() {
        return maxConnReuseTIme;
    }

    public void setMaxConnReuseTIme(int maxConnReuseTIme) {
        this.maxConnReuseTIme = maxConnReuseTIme;
    }

    public int getMaxConnReuseCount() {
        return maxConnReuseCount;
    }

    public void setMaxConnReuseCount(int maxConnReuseCount) {
        this.maxConnReuseCount = maxConnReuseCount;
    }

    public int getAbandonedConnTimeoutSecond() {
        return abandonedConnTimeoutSecond;
    }

    public void setAbandonedConnTimeoutSecond(int abandonedConnTimeoutSecond) {
        this.abandonedConnTimeoutSecond = abandonedConnTimeoutSecond;
    }

    public int getConnWaitTimeoutSecond() {
        return connWaitTimeoutSecond;
    }

    public void setConnWaitTimeoutSecond(int connWaitTimeoutSecond) {
        this.connWaitTimeoutSecond = connWaitTimeoutSecond;
    }

    public int getInactiveConnTimeoutSecond() {
        return inactiveConnTimeoutSecond;
    }

    public void setInactiveConnTimeoutSecond(int inactiveConnTimeoutSecond) {
        this.inactiveConnTimeoutSecond = inactiveConnTimeoutSecond;
    }

    public int getQueryTimeoutSecond() {
        return queryTimeoutSecond;
    }

    public void setQueryTimeoutSecond(int queryTimeoutSecond) {
        this.queryTimeoutSecond = queryTimeoutSecond;
    }
}

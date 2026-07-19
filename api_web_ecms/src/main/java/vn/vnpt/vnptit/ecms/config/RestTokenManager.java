package vn.vnpt.vnptit.ecms.config;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;
import vn.vnpt.exceptions.RetryableException;
import vn.vnpt.vnptit.ecms.config.MiddlewarePropeties.SystemType;

import javax.annotation.PostConstruct;
import java.sql.Types;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class RestTokenManager {
    private final Map<String, String> tokens = new ConcurrentHashMap<>();
    private final Map<String, ReentrantLock> loginLocks = new ConcurrentHashMap<>();

    private final JdbcTemplate jdbcTemplate;
    private final MiddlewarePropeties config;

    @PostConstruct
    public void init() {
        this.refreshSessionIfNeeded(SystemType.PRIVATE_SMARTCLOUD);
        this.refreshSessionIfNeeded(SystemType.SMARTCLOUD_EXTERNAL);
    }

    public RestTokenManager(JdbcTemplate jdbcTemplate,
                            MiddlewarePropeties config) {
        this.jdbcTemplate = jdbcTemplate;
        this.config = config;
    }

    public String getToken(SystemType systemType) {
        if (this.tokens.get(systemType.name()) != null) return this.tokens.get(systemType.name());
        this.refreshSessionIfNeeded(systemType);

        if (this.tokens.get(systemType.name()) == null) throw new RetryableException("Token not found!!!");

        return tokens.get(systemType.name());
    }

    public void forceRefresh(SystemType systemType) {
        this.tokens.remove(systemType.name());
    }

    private void refreshSessionIfNeeded(SystemType systemType) {
        ReentrantLock lock = this.loginLocks.computeIfAbsent(systemType.name(), k -> new ReentrantLock());
        if (!lock.tryLock()) {
            return; // thread khác đang login
        }
        try {
            if (this.tokens.get(systemType.name()) != null) return;
            String newToken;
            if (systemType.equals(SystemType.PRIVATE_SMARTCLOUD)||systemType.equals(SystemType.SMARTCLOUD_EXTERNAL)) {
                newToken = this.getCauHinh(systemType);
            } else {
                newToken = this.login(systemType);
            }
            if (newToken != null) this.tokens.put(systemType.name(), newToken);
        } finally {
            lock.unlock();
        }
    }

    private String login(SystemType systemType) {
//        HttpHeaders headers = new HttpHeaders();
//        if (MiddlewarePropeties.SystemType.SMARTCLOUD_EXTERNAL.name().equals(systemType)) {
//            headers.setBasicAuth(
//                    this.config.getSmartCloudExternal().getUsername(),
//                    this.config.getSmartCloudExternal().getPassword(),
//                    StandardCharsets.UTF_8);
//        }
//
//        HttpEntity<Void> entity = new HttpEntity<>(headers);
//        ResponseEntity<String> response = this.restTemplate.exchange(
//                this.config.getSmartCloudExternal().getBaseUrl() + "/api/login",
//                HttpMethod.POST,
//                entity,
//                String.class);
//        String token = response.getBody();

        // API trả về token có thể có dấu ngoặc kép bao quanh, cần loại bỏ
//        if (token != null) token = token.replace("\"", "").trim();
//        return token;
        return "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6ImF0K2p3dCJ9.eyJpc3MiOiJodHRwczovL2lkZW50aXR5LWJjYS5jbG91ZC52bnB0LnZuLyIsImV4cCI6MTc3NTk5NDk5MSwiaWF0IjoxNzY5OTk1MDUxLCJqdGkiOiI4YzQ5M2Q1Ny02NjdiLTQxNTAtYWEyMi1mN2Q2ODNkOWZmODIiLCJyb2xlIjoiS2hhY2hIYW5nIiwib2lfcHJzdCI6InN3YWdnZXItY2xpZW50Iiwib2lfYXVfaWQiOiI4NzBmMjQ3OC1lMDFmLTQzZjEtODIwNi05OTU1MGY5MTIyOGMiLCJzdWIiOiIwNjQ3Mjc0NS02N2U2LTQ3ZmEtYjE4MC1lYmE3YjI5ZTYyM2IiLCJnaXZlbl9uYW1lIjoiVHVuZyIsImN1c3RvbWVyX2lkIjo0MiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvbmFtZWlkZW50aWZpZXIiOiIwNjQ3Mjc0NS02N2U2LTQ3ZmEtYjE4MC1lYmE3YjI5ZTYyM2IiLCJlbWFpbCI6InR1bmd0YWJjYUB5b3BtYWlsLmNvbSIsIm5hbWUiOiJ0dW5ndGFiY2FAeW9wbWFpbC5jb20iLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiJLaGFjaEhhbmciLCJjbGllbnRfaWQiOiJzd2FnZ2VyLWNsaWVudCIsIm9pX3Rrbl9pZCI6ImUyMDZkNDNmLTNlM2MtNDE0NS05ZTIzLThkODFmMzY2M2MzYSJ9.FkWndpM508rlSxzuH3uId8oSnzTPcMevkZmaxoTYGS8";
    }

    private String getCauHinh(SystemType systemType) {
        String key;
        if (systemType.equals(SystemType.SMARTCLOUD_EXTERNAL)) {
            key = config.getSmartCloudExternal().getSettingKey();
        } else if (systemType.equals(SystemType.PRIVATE_SMARTCLOUD)) {
            key = config.getPrivateSmartCloudAdmin().getSettingKey();
        } else {
            return "";
        }
        SimpleJdbcCall jdbcCallDuplicate = new SimpleJdbcCall(this.jdbcTemplate)
                .withSchemaName("qltn")
                .withFunctionName("fn_lay_cauhinh_java_byname");
        MapSqlParameterSource ins = new MapSqlParameterSource().addValue("p_name", key, Types.NVARCHAR);
        String pathTemplate = jdbcCallDuplicate.executeFunction(String.class, ins);
        return pathTemplate;
    }
}

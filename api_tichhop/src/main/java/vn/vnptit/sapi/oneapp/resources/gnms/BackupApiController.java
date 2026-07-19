package vn.vnptit.sapi.oneapp.resources.gnms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@Controller
@RequestMapping("${openapi.oneBssGnms.base-path:/nms/rest}")
public class BackupApiController implements BackupApi {

    private final BackupApiDelegate delegate;

    public BackupApiController(@org.springframework.beans.factory.annotation.Autowired(required = false) BackupApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new BackupApiDelegate() {});
    }

    @Override
    public BackupApiDelegate getDelegate() {
        return delegate;
    }

}

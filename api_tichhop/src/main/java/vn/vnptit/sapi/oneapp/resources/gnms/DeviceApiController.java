package vn.vnptit.sapi.oneapp.resources.gnms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@Controller
@RequestMapping("${openapi.oneBssGnms.base-path:/nms/rest}")
public class DeviceApiController implements DeviceApi {

    private final DeviceApiDelegate delegate;

    public DeviceApiController(@org.springframework.beans.factory.annotation.Autowired(required = false) DeviceApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new DeviceApiDelegate() {});
    }

    @Override
    public DeviceApiDelegate getDelegate() {
        return delegate;
    }

}

package vn.vnptit.sapi.oneapp.resources.gnms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@Controller
@RequestMapping("${openapi.oneBssGnms.base-path:/nms/rest}")
public class Gw040HApiController implements Gw040HApi {

    private final Gw040HApiDelegate delegate;

    public Gw040HApiController(@org.springframework.beans.factory.annotation.Autowired(required = false) Gw040HApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new Gw040HApiDelegate() {});
    }

    @Override
    public Gw040HApiDelegate getDelegate() {
        return delegate;
    }

}

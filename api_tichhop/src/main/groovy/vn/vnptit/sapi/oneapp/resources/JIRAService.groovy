package vn.vnptit.sapi.oneapp.resources

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import io.swagger.model.BaseResponse
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.multipart.MultipartFile
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.jira.*
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine
import vn.vnptit.sapi.oneapp.util.FakeSuccessCommonResponse
import vn.vnptit.sapi.oneapp.util.GenericApi
import vn.vnptit.sapi.oneapp.util.MessageUtils

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import java.text.SimpleDateFormat

@RestController
@RequestMapping("jira")
class JIRAService {
    static String attrType = "@xsi:type"
    static String attrItemType = "@soapenc:arrayType"
    static String attrHref = "@href"
    static String attrNull = "@xsi:nil"

    static ThreadLocal<Gson> gson = ThreadLocal.withInitial { new GsonBuilder().serializeNulls().create() }

    @Autowired
    LoggingManaged loggingManaged
    @Autowired
    OracleManaged oracleManaged
    @Autowired
    HttpManaged httpManaged

    @Autowired
    GenericApi genericApi
    @Autowired
    NativeWebRequest request

    @Value("\${spring.httpservices.jira.url}")
    String jiraUrl

    @Value("\${spring.httpservices.jira.username}")
    String jiraUsername

    @Value("\${spring.httpservices.jira.password}")
    String jiraPassword

    @PostMapping("/login")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse login(@Valid @RequestBody Account body) {
        try {
            String url = jiraUrl + "/auth/1/session"
            String resp = httpManaged.post(["url": url], ["Content-Type": "application/json;charset=UTF-8"], JsonOutput.toJson(body))
            def res = new JsonSlurper().parseText(resp)
            if (res.loginInfo == null)
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: res.errorMessages, data: res)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/add_Issue_Watcher")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    //@FakeSuccessCommonResponse
    CommonResponse add_Issue_Watcher(@Valid @RequestBody Add_Issue_Watcher body) {
        try {
            String path = "/api/2/issue/$body.issueKeyOrID/watchers"
            String resp = post(path, body.login, JsonOutput.toJson(body.watcher))
            def res = new JsonSlurper().parseText(resp)
            if (res.errorMessages != null || res.errorMessage != null)
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "", data: res)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/get_Issue_Watchers")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse get_Issue_Watchers(@Valid @RequestBody Get_Issue_Watcher body) {
        try {
            String path = "/api/2/issue/$body.issueKeyOrID/watchers"
            String resp = get(path, body.login)
            def res = new JsonSlurper().parseText(resp)
            if (res.errorMessages != null || res.errorMessage != null)
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "", data: res)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/add_Issue_Participant")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    //@FakeSuccessCommonResponse
    CommonResponse Add_Issue_Participant(@Valid @RequestBody Add_Issue_Participant body) {
        try {
            String path = "/servicedeskapi/request/$body.issueKeyOrID/participant"
            def req = [usernames: [body.participant]]
            String resp = post(path, body.login, JsonOutput.toJson(req))
            def res = new JsonSlurper().parseText(resp)
            if (res.errorMessages != null || res.errorMessage != null)
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "", data: res)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/get_Issue_Info")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse get_Issue_Info(@Valid @RequestBody Get_Issue_Watcher body) {
        try {
            String path = "/api/2/issue/$body.issueKeyOrID"
            String resp = get(path, body.login)
            def res = new JsonSlurper().parseText(resp)
            if (res.errorMessages != null || res.errorMessage != null)
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "", data: res)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/set_Issue_Comment")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse set_Issue_Comment(@Valid @RequestBody Set_Issue_Comment body) {
        try {
            String path = "/api/2/issue/$body.issueKeyOrID/comment"
            def req = [body: body.comment]
            String resp = post(path, body.login, JsonOutput.toJson(req))
            def res = new JsonSlurper().parseText(resp)
            if (res.errorMessages != null || res.errorMessage != null)
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "", data: res)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/get_transition")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse get_transition(@Valid @RequestBody Get_Issue_Watcher body) {
        try {
            String path = "/api/2/issue/$body.issueKeyOrID/transitions"
            String resp = get(path, body.login)
            def res = new JsonSlurper().parseText(resp)
            if (res.errorMessages != null || res.errorMessage != null)
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "", data: res)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/get_List_Issue")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse get_List_Issue(@Valid @RequestBody Get_List_Issue body) {
        try {
            String path = "/api/2/search?fields=key,status&jql=issuekey%20%20in%20($body.list)"
            String resp = get(path, body.login)
            def res = new JsonSlurper().parseText(resp)
            if (res.errorMessages != null || res.errorMessage != null)
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "", data: res)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/add_Issue_Attachments")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    //@FakeSuccessCommonResponse
    CommonResponse add_Issue_Attachments(@RequestParam("issueKeyOrID") String issueKeyOrID, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("files") MultipartFile[] files) {
        try {
            String path = "/api/2/issue/$issueKeyOrID/attachments"
            if (files.size() > 0){
                def resp = postFile(path, new Account(username, password), files[0])
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: new JsonSlurper().parseText(resp.body().string()))
            }
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception ex) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/add_Issue_Transitions")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    //@FakeSuccessCommonResponse
    CommonResponse add_Issue_Transitions(@Valid @RequestBody Add_Issue_Transitions body) {
        try {
            String resolution = "";
            if (body.name_transitions.toLowerCase() == "cancelled") {
                resolution = "Cancelled";
            } else if (body.name_transitions.toLowerCase() == "resolved") {
                resolution = "Done";
            }
            if (body.kieu == 1) // ->Closed cần đánh giá
            {
                def data = [
                        transition: [id: body.transitions],
                        update    : [comment: [[add: [body: body.comment]]]],
                        fields    : [customfield_10502: [value: "9 điểm"], resolution: [name: "Done"]]
                ]
                String path = "/api/2/issue/$body.issueKeyOrID/transitions"
                String resp = postAddIssueTransitions(path, body.login, JsonOutput.toJson(data))
                def res = new JsonSlurper().parseText(resp)
                if (res.errorMessages != null && res.errorMessages.size() > 0)
                    return new CommonResponse(errorCode: 1, faultCode: "", faultString: "", data: res)
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res)
            } else if (body.kieu == 2) // ->Comment -> khi đổi trạng thái
            {
                if (resolution != "") {
                    def data = [
                            transition: [id: body.transitions],
                            update    : [comment: [[add: [body: body.comment]]]],
                            fields    : [resolution: [name: resolution]]
                    ]

                    String path = "/api/2/issue/$body.issueKeyOrID/transitions"
                    String resp = postAddIssueTransitions(path, body.login, JsonOutput.toJson(data))
                    def res = new JsonSlurper().parseText(resp)
                    if (res.errorMessages != null && res.errorMessages.size() > 0)
                        return new CommonResponse(errorCode: 1, faultCode: "", faultString: "", data: res)
                    return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res)
                } else {
                    def data = [
                            transition: [id: body.transitions],
                            update    : [comment: [[add: [body: body.comment]]]]
                    ]
                    String path = "/api/2/issue/$body.issueKeyOrID/transitions"
                    String resp = postAddIssueTransitions(path, body.login, JsonOutput.toJson(data))
                    def res = new JsonSlurper().parseText(resp)
                    if (res.errorMessages != null && res.errorMessages.size() > 0)
                        return new CommonResponse(errorCode: 1, faultCode: "", faultString: "", data: res)
                    return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res)
                }

            } else {
                if (resolution != "") {
                    def data = [
                            transition: [id: body.transitions],
                            fields    : [resolution: [name: resolution]]
                    ]
                    String path = "/api/2/issue/$body.issueKeyOrID/transitions"
                    String resp = postAddIssueTransitions(path, body.login, JsonOutput.toJson(data))
                    def res = new JsonSlurper().parseText(resp)
                    if (res.errorMessages != null && res.errorMessages.size() > 0)
                        return new CommonResponse(errorCode: 1, faultCode: "", faultString: "", data: res)
                    return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res)
                } else {
                    def data = [
                            transition: [id: body.transitions]
                    ]
                    String path = "/api/2/issue/$body.issueKeyOrID/transitions"
                    String resp = postAddIssueTransitions(path, body.login, JsonOutput.toJson(data))
                    def res = new JsonSlurper().parseText(resp)
                    if (res.errorMessages != null && res.errorMessages.size() > 0)
                        return new CommonResponse(errorCode: 1, faultCode: "", faultString: "", data: res)
                    return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res)
                }

            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/get_project")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse get_project(@Valid @RequestBody GET_PROJECT body) {
        try {
            String path = "/api/2/project/$body.projectidorkey"
            String resp = get(path, body.login)
            def res = new JsonSlurper().parseText(resp)
            if (res.errorMessages != null || res.errorMessage != null)
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "", data: res)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Get_IssueTypes_V2")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse Get_IssueTypes_V2(@Valid @RequestBody JiraBaseRequest body) {
        try {
            String path = "/api/2/issuetype"
            String resp = get(path, body.login)
            def res = new JsonSlurper().parseText(resp)
            if (res != null && res instanceof List)
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res)
            return new CommonResponse(errorCode: 1, faultCode: "", faultString: "", data: res)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Creat_Issue")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    //@FakeSuccessCommonResponse
    CommonResponse Creat_Issue(@Valid @RequestBody Creat_Issue body) {
        try {
            String path = "/api/2/issue"
            def data = [
                    fields    : [
                            project: [
                                    id: body.project
                            ],
                            issuetype: [
                                    id: body.issuetype
                            ],
                            summary: body.summary,
                            description: body.description,
                            customfield_10328: [
                                    id: body.customfield_10328_id,
                                    child: [
                                            id:body.customfield_10328_child_id
                                    ]
                            ],
                            customfield_10329: [
                                    id: body.customfield_10329_id
                            ],
                            duedate: body.duedate,
                            labels: body.labels
                    ]
            ]
            if (body.customfield_12601_id) {
                data.fields.customfield_12601 = [ id: body.customfield_12601_id ]
            }
            String resp = post(path, body.login, JsonOutput.toJson(data))
            def res = new JsonSlurper().parseText(resp)
            if (res.errorMessages != null || res.errorMessage != null)
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "", data: res)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    Response postFile(String path, Account account, MultipartFile file) {
        if (account == null || account.username == null || account.username == "") {
            account = new Account(jiraUsername, jiraPassword)
        }
        def authString = "${account.username}:${account.password}".getBytes().encodeBase64().toString()

        OkHttpClient client = new OkHttpClient().newBuilder().build();

        okhttp3.RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getOriginalFilename(),
                        okhttp3.RequestBody.create(file.getBytes(), okhttp3.MediaType.parse("application/data")))
                .build();

        String url = jiraUrl + path
        Request request = new Request.Builder()
                .addHeader("X-Atlassian-Token", "nocheck")
                .addHeader("Authorization", "Basic ${authString}".toString())
                .url(url)
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        return response
    }

    def postAddIssueTransitions(String path, Account account, String body) {
        if (account == null) {
            account = new Account(jiraUsername, jiraPassword)
        }
        def authString = "${account.username}:${account.password}".getBytes().encodeBase64().toString()
        String url = jiraUrl + path
        Map<String, String> headers = [:]
        headers.put("Content-Type", "application/json;charset=UTF-8")
        headers.put("Authorization", "Basic ${authString}".toString())
        String resp = httpManaged.post(["url": url], headers, body)
        if (resp == null || resp == "") {
            resp = "{ \"errorMessages\": [], \"errors\": {} }"
        }
        resp
    }

    def post(String path, Account account, String body) {
        if (account == null) {
            account = new Account(jiraUsername, jiraPassword)
        }
        def authString = "${account.username}:${account.password}".getBytes().encodeBase64().toString()
        String url = jiraUrl + path
        Map<String, String> headers = [:]
        headers.put("Content-Type", "application/json;charset=UTF-8")
        headers.put("Authorization", "Basic ${authString}".toString())
        String resp = httpManaged.post(["url": url], headers, body)
        if (resp == null || resp == "")
            throw new Exception("Lỗi gọi JIRA")
        resp
    }

    def get(String path, Account account) {
        if (account == null) {
            account = new Account(jiraUsername, jiraPassword)
        }
        def authString = "${account.username}:${account.password}".getBytes().encodeBase64().toString()
        String url = jiraUrl + path
        Map<String, String> headers = [:]
        headers.put("Content-Type", "application/json;charset=UTF-8")
        headers.put("Authorization", "Basic ${authString}".toString())
        String resp = httpManaged.get(url, headers)
        if (resp == null || resp == "")
            throw new Exception("Lỗi gọi JIRA")
        resp
    }
}

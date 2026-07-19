package vn.vnptit.sapi.oneapp.models.jira

import com.fasterxml.jackson.annotation.JsonProperty

class Add_Issue_Watcher extends JiraBaseRequest{
    @JsonProperty("issueKeyOrID")
    String issueKeyOrID
    @JsonProperty("watcher")
    String watcher
}

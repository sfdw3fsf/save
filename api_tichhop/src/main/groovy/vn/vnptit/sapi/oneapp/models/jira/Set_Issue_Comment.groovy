package vn.vnptit.sapi.oneapp.models.jira

import com.fasterxml.jackson.annotation.JsonProperty

class Set_Issue_Comment extends JiraBaseRequest{
    @JsonProperty("issueKeyOrID")
    String issueKeyOrID
    @JsonProperty("comment")
    String comment
}

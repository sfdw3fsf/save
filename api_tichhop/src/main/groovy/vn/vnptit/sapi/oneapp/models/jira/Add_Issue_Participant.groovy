package vn.vnptit.sapi.oneapp.models.jira

import com.fasterxml.jackson.annotation.JsonProperty

class Add_Issue_Participant extends JiraBaseRequest{
    @JsonProperty("issueKeyOrID")
    String issueKeyOrID
    @JsonProperty("participant")
    String participant
}

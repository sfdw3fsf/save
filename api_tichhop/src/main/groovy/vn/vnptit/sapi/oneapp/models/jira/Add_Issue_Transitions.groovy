package vn.vnptit.sapi.oneapp.models.jira

import com.fasterxml.jackson.annotation.JsonProperty

class Add_Issue_Transitions extends JiraBaseRequest{
    @JsonProperty("issueKeyOrID")
    String issueKeyOrID
    @JsonProperty("transitions")
    String transitions
    @JsonProperty("vkieu")
    Long kieu
    @JsonProperty("comment")
    String comment
    @JsonProperty("name_transitions")
    String name_transitions
}

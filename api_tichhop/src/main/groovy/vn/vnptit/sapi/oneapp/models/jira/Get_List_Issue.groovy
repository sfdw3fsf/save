package vn.vnptit.sapi.oneapp.models.jira

import com.fasterxml.jackson.annotation.JsonProperty

class Get_List_Issue extends JiraBaseRequest {
    @JsonProperty("list")
    String list
}

class GET_PROJECT extends JiraBaseRequest {
    @JsonProperty("projectidorkey")
    String projectidorkey
}

class GET_ISSUETYPES_V2 extends JiraBaseRequest {
}

class Creat_Issue extends JiraBaseRequest {
    @JsonProperty("project")
    String project
    @JsonProperty("issuetype")
    String issuetype
    @JsonProperty("reporter")
    String reporter
    @JsonProperty("summary")
    String summary
    @JsonProperty("description")
    String description
    @JsonProperty("customfield_14300_id")
    String customfield_14300_id
    @JsonProperty("customfield_14300_child_id")
    String customfield_14300_child_id
    @JsonProperty("customfield_10328_id")
    String customfield_10328_id
    @JsonProperty("customfield_10328_child_id")
    String customfield_10328_child_id
    @JsonProperty("customfield_10329_id")
    String customfield_10329_id
    @JsonProperty("customfield_12601_id")
    String customfield_12601_id
    @JsonProperty("duedate")
    String duedate
    @JsonProperty("labels")
    List labels
}

package vn.vnptit.sapi.oneapp.models.jira


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class JiraBaseRequest {
    @JsonProperty("login")
    Account login
}

@JsonIgnoreProperties(["metaClass"])
class Account {
    @JsonProperty("username")
    String username
    @JsonProperty("password")
    String password

    Account() {
    }

    Account(String username, String password) {
        this.username = username
        this.password = password
    }
}
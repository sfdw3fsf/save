let root = {};
export default {
  setupRoot: function (_root) {
    root = _root
  },
  async Get_Issue_Info(id) {
    return await root.context.post("/tichhop/jira/get_Issue_Info", {
      issueKeyOrID: id,
      // login: {
      //   password: jira_username,
      //   username: jira_password
      // }
    })
  },
  async Get_Issue_Watchers(id) {
    return await root.context.post("/tichhop/jira/get_Issue_Watchers", {
      issueKeyOrID: id,
      // login: {
      //   password: jira_username,
      //   username: jira_password
      // },
    })
  },
  async Find_Employees_By_EmailOrPhone(email) {
    let rs = await root.context.get("/tichhop/hrm/hrm/employees/get_info_nhanvien_by_email", {email: email})
    return rs.data
  },
  async Set_Issue_Comment(id, comment) {
    return await root.context.post("/tichhop/jira/set_Issue_Comment", {
      comment: comment,
      issueKeyOrID: id,
      // login: {
      //   password: jira_username,
      //   username: jira_password
      // },
    })
  },
  async Add_Issue_Watcher(id, watcher) {
    return await root.context.post("/tichhop/jira/add_Issue_Watcher", {
      issueKeyOrID: id,
      watcher: watcher,
      // login: {
      //   password: jira_username,
      //   username: jira_password
      // },
    })
  },
  async Add_Issue_Attachments(id, jira_username, jira_password, file) {
    let formData = new FormData()
    formData.append("files",file)
    formData.append("issueKeyOrID", id)
    formData.append("username", jira_username)
    formData.append("password", jira_password)
    return await root.context.post("/tichhop/jira/add_Issue_Attachments", formData)
  },
  async Get_transition(id) {
    return await root.context.post("/tichhop/jira/get_transition", {
      issueKeyOrID: id,
      // login: {
      //   password: jira_username,
      //   username: jira_password
      // },
    })
  },
  async Add_Issue_Transitions(id, transitions, vkieu, comment, name_transitions) {
    return await root.context.post("/tichhop/jira/add_Issue_Transitions", {
      comment:comment,
      issueKeyOrID: id,
      // login: {
      //   password: jira_username,
      //   username: jira_password
      // },
      name_transitions: name_transitions,
      transitions: transitions,
      vkieu: vkieu
    })
  },
   async Add_Issue_Participant(id, participant) {
     return await root.context.post("/tichhop/jira/add_Issue_Participant", {
       issueKeyOrID: id,
       // login: {
       //   password: jira_username,
       //   username: jira_password
       // },
       participant: participant,
     })
   },
  async Get_List_Issue(keys) {
    return await root.context.post("/tichhop/jira/get_List_Issue", {
      list: keys,
      // login: {
      //   password: jira_username,
      //   username: jira_password
      // },
    })
  }
}

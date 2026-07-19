export default {
  getDSIssue: (axios,data) => axios.post('/web-thicong/giaoviec-vip/sp_lay_ds_issue',data),
  Get_Issue_Info: (axios,data) => axios.post('/tichhop/jira/get_Issue_Info',data),
  Get_TrangThai_Jira: (axios) => axios.post('/web-thicong/giaoviec-vip/lay_ds_trangthai_jira',{}),
  Get_Issue_Watchers: (axios,data) => axios.post('/tichhop/jira/get_Issue_Watchers',data), 
  Get_Issue_Comment: (axios,data) => axios.post('/tichhop/jira/set_Issue_Comment',data), 
  Map_CommentToHDTB: (axios,data) => axios.post('/web-thicong/giaoviec-vip/insert_jira_hdtb_comment_n',data),
  Add_Issue_Attachment: (axios,data) => axios.post('/tichhop/jira/add_Issue_Attachments',data, {headers: {'Content-Type': 'multipart/form-data'}}),
  Add_Issue_Participant: (axios,data) => axios.post('/tichhop/jira/add_Issue_Participant',data),
  Find_Employees_By_EmailOrPhone: (axios,data) => axios.get(`/tichhop/hrm/hrm/employees/get_info_nhanvien_by_email?email=${data}`),
  Add_Issue_Watcher: (axios,data) => axios.post('/tichhop/jira/add_Issue_Watcher',data),
}
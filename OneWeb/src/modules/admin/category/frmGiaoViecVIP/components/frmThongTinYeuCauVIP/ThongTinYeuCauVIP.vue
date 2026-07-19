<template src="./template.html"> </template>

<script>
import api from './API'
import Vue from 'vue'
import { DateTimePickerPlugin } from '@syncfusion/ej2-vue-calendars'
import moment from 'moment'
Vue.use(DateTimePickerPlugin)
const initData = {
  txtTieuDe: '',
  txtMoTa: '',
  cbbTrangThai: {
    value: null,
    options: []
  },
  dtpNgayHetHan: new Date(),
  dtpNgayHT: new Date(),
  txtLoaiYeuCau: '',
  txtReporter: '',
  txtAssignee: '',
  txtParticipant: '',
  txtMessage: '',
  txtAddWatcher: ''
}
export default {
  data() {
    return {
      phanvung_id: null,
      hdtb_id: null,
      tbDSYeuCau: {
        visible: true,
        data: [],
        columns: [
          { fieldName: 'key', headerText: 'Số phiếu', allowFiltering: true },
          { fieldName: 'ngay_cn', headerText: 'Ngày CN', allowFiltering: true },
          { fieldName: 'nguoi_cn', headerText: 'Người tạo', allowFiltering: true },
          { fieldName: 'url', headerText: 'URL', template: this.URLTemplate(this) }
        ]
      },
      tbComment: {
        data: [],
        columns: [
          { fieldName: 'created', headerText: 'Ngày tạo', allowFiltering: true },
          { fieldName: 'author', headerText: 'Người tạo', allowFiltering: true },
          { fieldName: 'body', headerText: 'Nội dung', allowFiltering: true },
          { fieldName: 'name', headerText: 'User', allowFiltering: true }
        ]
      },
      tbFileDinhKem: {
        data: [],
        columns: [
          { fieldName: 'created', headerText: 'Ngày tạo', allowFiltering: true },
          { fieldName: 'filename', headerText: 'Tên file', allowFiltering: true },
          { fieldName: 'url', headerText: 'URL', template: this.URLTemplate(this) },
          { fieldName: 'author', headerText: 'Người tạo', allowFiltering: true }
        ]
      },
      tbWatchers: {
        data: [],
        columns: [
          { fieldName: 'displayName', headerText: 'Tên', allowFiltering: true },
          { fieldName: 'emailAddress', headerText: 'Email', allowFiltering: true }
        ]
      },
      fileUpload: null,
      issueSelected: null,
      formData: JSON.parse(JSON.stringify(initData)),
      vkey_ht: null,
      may_cn: null,
    }
  },
  methods: {
    URLTemplate(parent) {
      return function() {
        return {
          template: {
            template: `
          <div>
          <a target="_blank" :href="data.url">{{ data.url }}</a>
          </div>
        `,
            data() {
              return {
                data: {}
              }
            }
          }
        }
      }
    },
    resetFileUpload() {},
    async gvIssue_FocusedRowChanged(data) {
      this.issueSelected = data.data
      if (data.data) await this.NAP_TT_JIRA(data.data.key)
      else this.CLEAR()
    },
    CLEAR() {
      this.tbDSYeuCau.data = []
      this.tbComment.data = []
      this.tbFileDinhKem.data = []
      this.tbWatchers.data = []
      this.vkey_ht = null
      this.formData = JSON.parse(JSON.stringify(initData))
    },
    async NAP_TT_JIRA(key) {
      try {
        this.loading(true)
        this.vkey_ht = key;
        let res = await api.Get_Issue_Info(this.axios, { issueKeyOrID: key })
        const data = res.data.data
        const id = data.id
        const projectID = data.fields.project.id
        this.formData.txtTieuDe = key + ': ' + data.fields.summary
        this.formData.txtMoTa = data.fields.description
        this.formData.dtpNgayHetHan = moment(data.fields.duedate).toDate()
        this.formData.dtpNgayHT = moment(data.fields.resolutiondate).toDate()
        this.formData.txtLoaiYeuCau = data.fields.issuetype.name
        this.formData.txtReporter = data.fields.reporter.displayName
        if (data.fields.assignee) {
          if (data.fields.assignee.name) {
            this.formData.txtAssignee = data.fields.assignee.name
          } else this.formData.txtAssignee = ''
        } else this.formData.txtAssignee = ''
        if (data.fields.status.name) {
          let trangthai = this.formData.cbbTrangThai.options.find(
            (x) =>
              x.text
                .toLowerCase()
                .replaceAll(' ', '')
                .replaceAll('-', '') ==
              data.fields.status.name
                .toLowerCase()
                .replaceAll(' ', '')
                .replaceAll('-', '')
          )
          if (trangthai) {
            this.formData.cbbTrangThai.value = trangthai.id
            this.trangthai_iss = trangthai.ID
          }
        }
        this.tbComment.data = []
        if (data.fields.comment.comments.length) {
          for (let item of data.fields.comment.comments) {
            let row = {}
            row.id = item.id
            row.body = item.body
            row.created = moment(item.created).format('DD/MM/YYYY HH:mm:ss')
            row.updated = moment(item.updated)
            row.author = item.author.displayName
            row.name = item.author.name
            this.tbComment.data.push(row)
          }
        }
        this.tbFileDinhKem.data = []
        if (data.fields.attachment.length) {
          for (let item of data.fields.attachment) {
            let row = {}
            row.id = item.id
            row.url = item.content
            row.created = moment(item.created).format('DD/MM/YYYY HH:mm:ss')
            row.filename = item.filename
            row.author = item.author.displayName
            row.name = item.author.name
            this.tbFileDinhKem.data.push(row)
          }
        }
        console.log(data.fields.attachment);
        this.tbWatchers.data = []
        res = await api.Get_Issue_Watchers(this.axios, { issueKeyOrID: key })
        if(res.data)
          this.tbWatchers.data = res.data.data.watchers;
      } catch (error) {
      } finally {
        this.loading(false)
      }
    },
    async btnThemFile_Click(){
      if(!this.vkey_ht){
        this.$toast.error('Không lấy được key yêu cầu')  
        return
      }
      if(!this.fileUpload){
        this.$toast.error('Chưa chọn file')  
        return
      }
      try {
        this.loading(true)
        const formData = new FormData()
        formData.append('issueKeyOrID', this.vkey_ht)
        formData.append('files', this.fileUpload)
        formData.append('username', '')
        formData.append('password', '')
        let res = await api.Add_Issue_Attachment(this.axios, formData)
        if (res.data.errorCode != 0) {
            this.$toast.error("Lỗi: " + faultString)
            return
        }
        this.$toast.success('Thêm file thành công !')
        this.fileUpload = null
        this.NAP_TT_JIRA(this.vkey_ht)
      } catch (error) {
        
      } finally {
        this.loading(false) 
      }
    },
    async COMMENT_ISSUES(){
      if (!this.vkey_ht) {
        this.$toast.error('Không có ID !')  
        return
      }
      if(!this.formData.txtMessage.trim()){
        this.$toast.error('Không có nội dung !')  
        return
      }
      try {
        this.loading(true)
        let res = await api.Get_Issue_Comment(this.axios, {
          issueKeyOrID: this.vkey_ht,
          comment: this.formData.txtMessage.trim()
        })
        if (res.data.data && res.data.data.errorMessages && res.data.data.errorMessages.length) {
          for (let error of res.data.data.errorMessages) {
            if (error.includes("permission"))
              this.$toast.error("Bạn không có quyền Comment Issue này!")
            else this.$toast.error("Lỗi comment " + error)
          }
          return
        }
        if(res.data.errorCode != 0)
          this.$toast.error("Lỗi comment " + res.data.faultString)
        else{
          let resMap = await api.Map_CommentToHDTB(this.axios,{
            phanvung_id: this.phanvung_id*1,
            hdtb_id: this.issueSelected.hdtb_id,
            issue_id:this.issueSelected.issue_id,
            key: this.vkey_ht,
            ngay_cn: moment(new Date()).format('DD/MM/yyyy'),
            nguoi_cn:  this.$root.token.getUserName(),
            may_cn: this.may_cn,
            comment_jr: this.formData.txtMessage.trim(),
            comment_id: res.data.data.id*1 
          })
          console.log(resMap);
        //   if (resMap.data.error_code != "BSS-00000000" || !rsMap.data.data) {
        //   this.$toast.error("Có lỗi khi cập nhật yêu cầu vào hợp đồng trên ĐHSX: " + resMap.data.message_detail)
        //   return
        // }
          if(resMap.data.error_code == 'BSS-00000000'){
            this.$toast.success('Comment thành công !')
          this.formData.txtMessage = ''
          this.NAP_TT_JIRA(this.vkey_ht)
          }
          else{
            this.$toast.error("Có lỗi khi cập nhật yêu cầu vào hợp đồng trên ĐHSX: " + resMap.data.message_detail)
          }
        }
      } catch (error) {
        
      } finally {
        this.loading(false)
      }
    },
    async btnParticipant_ButtonClick() {
      if (!this.formData.txtParticipant) {
        this.$toast.error("Bạn chưa nhập tài khoản Jira")
        return
      }
      let res = await api.Add_Issue_Participant(this.axios,{
        issueKeyOrID: this.vkey_ht,
        participant: this.formData.txtParticipant
      })
      if (res.data.errorCode != 0) {
        this.$toast("Lỗi: " + res.faultString)
        return
      }
      this.$toast.success("Thêm thành công")
      this.formData.txtParticipant = ''
    },
    async btnAssignee_ButtonClick() {
      if(!this.formData.txtAssignee){
        this.$toast.error("Không tìm thấy tài khoản")
        return
      }
      let res = await api.Find_Employees_By_EmailOrPhone(this.axios,this.formData.txtAssignee);
      if(res.data.data.result.length>0){
        let dt = res.data.data.result.records[0];
        this.$toast.info("Tên nhân viên : " + dt.name + '.\n' +
              "Đơn vị : " + dt.department + '.\n' +
              "Công việc : " + dt.job_name + '.\n' +
              "Số điện thoại : " + dt.mobile_phone + '.\n')
      }
      else this.$toast.error("Lỗi xảy ra khi lấy thông tin nhân viên")
    },
    async btnAddWatcher_ButtonClick(){
      try {
        this.loading(true)
        if(!this.formData.txtAddWatcher){
          this.$toast.error("Bạn chưa nhập thông tin người theo dõi")
          return
        }
        let res = await api.Add_Issue_Watcher(this.axios,{
          issueKeyOrID: this.vkey_ht,
          watcher: this.formData.txtAddWatcher.trim()
        })
        if (res.data.errorCode != 0) {
          this.$toast.error("Lỗi: " + res.data.faultString)
          return
        }
        this.$toast.success("Thêm thành công")
        this.formData.txtAddWatcher = ''
        this.NAP_TT_JIRA(this.vkey_ht)
      } catch (error) {
        
      } finally {
        this.loading(false) 
      }
    },
    btnFileBack_click(){
      this.fileUpload = null
    },
    showModal() {
      this.$refs.ThongTinYeuCauVIP.show()
    },
    async handleShowModal() {
      //load ccbTrangthai
      let res = await api.Get_TrangThai_Jira(this.axios)
      this.formData.cbbTrangThai.options = res.data.data.map((x) => {
        return {
          id: x.ttjr_id,
          text: x.trangthai_jira
        }
      })
      this.may_cn = await this.$root.token.getMachine()
      console.log(this.may_cn);
      //just for test
      //disable when deploy
      // this.tbDSYeuCau.data.push({
      //   hdtb_id: 21695543,
      //   issue_id: 2760776,
      //   key: 'IT360-898770',
      //   may_cn: 'null',
      //   ngay_cn: '2023-08-24 23:59:08',
      //   nguoi_cn: 'admin_hcm',
      //   url: 'https://cntt.vnpt.vn/browse/IT360-898770'
      // })

      //code for deplop
      if(!this.hdtb_id){
        this.$toast.error('Không có thông tin hợp đồng !')
        return
      }
      res = await api.getDSIssue(this.axios,{
        p_phanvung_id: this.phanvung_id,
        p_hdtb_id: this.hdtb_id
      })
      if(res.data.error_code == 'BSS-00000204')
        this.$toast.error('Không có thông tin phiếu yêu cầu !')
      else if(res.data.error_code == 'BSS-00000000')
        this.tbDSYeuCau.data = res.data.data
      else
        this.$toast.error('Xảy ra lỗi khi lấy danh sách phiếu yêu cầu!')
    },
    hideModal() {
      this.CLEAR()
      this.$refs.ThongTinYeuCauVIP.hide()
    }
  }
}
</script>

<style lang="scss" scoped src="./style.scss"></style>

<template src="./index.html"></template>

<script>
import Vue from "vue";
import { DateTimePickerPlugin } from "@syncfusion/ej2-vue-calendars";
import { MultiSelectPlugin, CheckBoxSelection } from "@syncfusion/ej2-vue-dropdowns"
import JiraService from "./Service/JiraService";
import DateTimeLib from "../../../../utils/DateTimeLib";
import NhapText from './formNhapText/formNhapText'
import BieuMau from './QuanLyBieuMau/QuanLyBieuMau'
Vue.use(DateTimePickerPlugin);
export default {
  name: "QuanLyIssue",
  components: { NhapText, BieuMau },
  provide: {
    multiselect: [CheckBoxSelection]
  },
  props: {},
  data: function () {
    return {
      showDataSearch: false,
      dataSearch: [],
      chkNgay: false,
      isDisableNgay: true,
      checkboxLoai: 1,
      dsTrangThaiBaoHong: [],
      trangThaiBaoHongId: null,
      maThueBao: null,
      dtTuNgay: new Date(),
      dtDenNgay: new Date(),
      trangThaiHDId: null,
      trangThaiId: null,
      dsTrangThai: [],
      dsTrangThaiHD: [{ id: 1, trangthai: 'Chưa hoàn thiện' }, { id: 2, trangthai: 'Đã hoàn thiện' }, {
        id: 3,
        trangthai: 'Thoái trả'
      }],
      dsHopDong: [],
      dsYeuCau: [],
      dsWatchers: [],
      dsFile: [],
      dsComment: [],

      totalRowDsHopDong: 0,
      paging_dsHopDong: {
        pNo: 1,
        pSize: 10,
        pNoBefore: 1,
      },
      isPaging: true,

      // totalRowDsYeuCau: 0,
      // paging_dsYeuCau: {
      //   pNo: 1,
      //   pSize: 10,
      //   pNoBefore: 1,
      // },

      isEnableHuyDongBo: true,
      isEnableDongBo: true,
      // tungay: null,
      // denngay: null,
      vkey_ht: null,
      vid_ht: null,
      vproject_id: null,
      trangthai_iss: null, // trang thai đã lưu trươc đó
      cboTrangThai: null,
      txtSummary: null,
      txtDescription: null,
      txtReporter: null,
      txtIssuseType: null,
      dtpResolved: null,
      dtpDueDate: null,
      txtAssignee: null,
      txtComment: null,
      txtAddWatcher: null,
      fileUpload: null,
      // grcComment:null,
      // grcFileUp:null,
      dsTrangThaiIssue: null,
      // ds_comment.Clear();
      // ds_file.Clear();
      // grcWatchers:null,
      // lstWatchers.Clear();
      txtParicipant: "",

      waterMark: 'Chọn ngày',
      dateVal: new Date(),
      dateFormat: 'dd/MM/yyyy',
      datetimeFormat: "dd/MM/yyyy HH:mm",

      issueSelected: null,

      quet_tudong: true,
      dongbo: false,
      processJira: {
        Minimum: 0,
        Maximum: 100,
        value: 0,
      },
      spTimer: 10,
      layoutJira: 'Đồng bộ Jira',

      URLTemplate: function () {
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
            },
          }
        }
      }
    }
  },
  computed: {},
  methods: {
    async frmQL_Issue_Load() {
      this.dtTuNgay = new Date(Date.now() - 7 * 24 * 60 * 60 * 1000) // 7 days before
      this.dtDenNgay = new Date()
      await this.getTrangThaiBaoHong()
      await this.getTrangThaiJira()
      this.isEnableHuyDongBo = false
      if (this.$auth.getUserName() == "admin")
        this.quet_tudong = false
      if (this.quet_tudong) {
        this.bgwAuto_DoWork()
      }
    },
    getTrangThaiBaoHong: async function () {
      let res = await this.$root.context.get('web-baohong/tientrinhit360/lay_danhmuc_trangthai_baohong')
      this.dsTrangThaiBaoHong = res.data ? res.data : []
    },
    getTrangThaiJira: async function () {
      this.processJira.Maximum = 0
      let res = await this.$root.context.get('web-hopdong/QuanLyIssue/lay_ds_trangthai_jira')
      this.dsTrangThai = res.data
    },
    layThongTinClick: async function () {
      this.dsYeuCau = []
      this.CLEAR()
      //console.log(this.dtTuNgay)
      // this.tungay = this.dtTuNgay ? this.dtTuNgay.split('-') : "";
      // this.denngay = this.dtDenNgay ? this.dtDenNgay.split('-') : "";
      // if (this.tungay) {
      //   this.tungay = this.tungay.reverse();
      //   this.tungay = this.tungay.join('/');
      // }
      // if (this.denngay) {
      //   this.denngay = this.denngay.reverse();
      //   this.denngay = this.denngay.join('/');
      // }
      await this.updateDsHopDongCount(this.trangThaiId, this.trangThaiHDId, this.maThueBao, DateTimeLib.toDateDisplay(this.dtTuNgay), DateTimeLib.toDateDisplay(this.dtDenNgay), 0)
      // let data = {
      //   P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
      //   P_NGUOI_CN: this.$root.token.getUserName(),
      //   P_TRANGTHAI_JR_ID: this.trangThaiId ? this.trangThaiId.join(",") : null,
      //   P_TRANGTHAI_HD_ID: this.trangThaiHDId ? this.trangThaiHDId.join(",") : null,
      //   P_MA_THUEBAO: this.maThueBao,
      //   P_TUNGAY: this.tungay && this.isDisableNgay == false ? this.tungay : null,
      //   P_DENNGAY: this.denngay && this.isDisableNgay == false ? this.denngay : null,
      //   P_KIEU: 0,
      //   P_PAGE_NUM: 0,
      //   P_PAGE_SIZE: 10,
      //   PROCEDURE_NAME: 'CSS.PKG_QUANLY_ISSUE.SP_DS_HOPDONG_SEL'
      // }
      // console.log('data', data)
      // let res = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
      // this.dsHopDong = res.data
    },
    btnHuyBo_Click() {
      console.log("huy dongbo")
      if (this.dongbo) {
        this.dongbo = false
        this.isEnableDongBo = true
        this.isEnableHuyDongBo = false
        this.$root.toastSuccess("Hủy đồng bộ thành công")
      }
    },
    btnBieuMau_Click() {
      this.$bvModal.show("BieuMau")
    },
    async updateDsHopDongCount(trangthai_id, trangthaihd_id, ma_tb, tungay, denngay, kieu) {
      try {
        this.loading(true)
        let api   = "/web-hopdong/QuanLyIssue/lay-ds-hopdong-paging"
        let param = {
            TRANGTHAI_JR_ID: trangthai_id ? trangthai_id.join(",") : null,
            TRANGTHAI_HD_ID: trangthaihd_id ? trangthaihd_id.join(",") : null,
            MA_THUEBAO: ma_tb,
            TUNGAY: tungay && this.isDisableNgay == false ? tungay : null,
            DENNGAY: denngay && this.isDisableNgay == false ? denngay : null,
            KIEU: kieu,
            PAGE_NUM: 0,
            PAGE_SIZE: 1,
          }
        if(this.checkboxLoai == 2) {
          api   = "/web-baohong/tientrinhit360/lay_danhsach_baohong_sel"
          param = {
            TRANGTHAI_JR_ID: trangthai_id ? trangthai_id.join(",") : null,
            P_TRANGTHAI_BH_ID: this.trangThaiBaoHongId ? this.trangThaiBaoHongId.join(",") : null,
            MA_THUEBAO: ma_tb,
            TUNGAY: tungay && this.isDisableNgay == false ? tungay : null,
            DENNGAY: denngay && this.isDisableNgay == false ? denngay : null,
            KIEU: kieu,
            PAGE_NUM: 0,
            PAGE_SIZE: 1,
          }
        }
        await this.$root.context.post(
          api,
          param).then(res => {
            if (this.totalRowDsHopDong == res.data.allItems) this.$refs.gridDsHD.reloadCurrentPage()
            this.totalRowDsHopDong = res.data.allItems > 0 ? res.data.allItems : 0;
            if (this.totalRowDsHopDong == 0) {
              this.loading(false)
              this.dsHopDong = []
              this.$root.toastError("Không có dữ liệu, vui lòng thử lại.")
            }
          })
      } catch (e) {
        this.$root.toastError("Không có dữ liệu, vui lòng thử lại. " + e.massage)
        this.loading(false)
      } finally {
      }
    },
    async layDsHopDong(trangthai_id, trangthaihd_id, ma_tb, tungay, denngay, kieu, pageIndex, pageSize) {
      try {
        await this.$root.context.post(
          "/web-hopdong/QuanLyIssue/lay-ds-hopdong-paging",
          {
            TRANGTHAI_JR_ID: trangthai_id ? trangthai_id.join(",") : null,
            TRANGTHAI_HD_ID: trangthaihd_id ? trangthaihd_id.join(",") : null,
            MA_THUEBAO: ma_tb,
            TUNGAY: tungay && this.isDisableNgay == false ? tungay : null,
            DENNGAY: denngay && this.isDisableNgay == false ? denngay : null,
            KIEU: kieu,
            PAGE_NUM: pageIndex,
            PAGE_SIZE: pageSize,
          }).then(res => {
            if (this.totalRowDsHopDong != res.data.allItems) {
              this.totalRowDsHopDong = res.data.allItems
              return
            }
            if (res.data.pageItems.length > 0) {
              this.dsHopDong = res.data.pageItems
            } else {
              this.dsHopDong = []
              this.$root.toastError("Không có dữ liệu, vui lòng thử lại.")
            }
          })
      } catch (e) {
        console.log(e)
        this.$root.toastError("Có lỗi xảy ra khi tải danh sách hợp đồng")
      }
    },
    async layDsBaoHongCanDongBo(trangthai_id, trangthaibh_id, ma_tb, tungay, denngay, kieu, pageIndex, pageSize) {
      let rs = await this.$root.context.post(
        "/web-baohong/tientrinhit360/lay_danhsach_baohong_sel",
        {
          TRANGTHAI_JR_ID: trangthai_id ? trangthai_id.join(",") : null,
          P_TRANGTHAI_BH_ID: trangthaibh_id ? trangthaibh_id.join(",") : null,
          MA_THUEBAO: ma_tb,
          TUNGAY: tungay && this.isDisableNgay == false ? tungay : null,
          DENNGAY: denngay && this.isDisableNgay == false ? denngay : null,
          KIEU: kieu,
          PAGE_NUM: pageIndex,
          PAGE_SIZE: pageSize,
        })
      return rs.data.pageItems
    },
    async layDsHopDongCanDongBo(trangthai_id, trangthaihd_id, ma_tb, tungay, denngay, kieu, pageIndex, pageSize) {
      let rs = await this.$root.context.post(
        "/web-hopdong/QuanLyIssue/lay-ds-hopdong-paging",
        {
          TRANGTHAI_JR_ID: trangthai_id ? trangthai_id.join(",") : null,
          TRANGTHAI_HD_ID: trangthaihd_id ? trangthaihd_id.join(",") : null,
          MA_THUEBAO: ma_tb,
          TUNGAY: tungay && this.isDisableNgay == false ? tungay : null,
          DENNGAY: denngay && this.isDisableNgay == false ? denngay : null,
          KIEU: kieu,
          PAGE_NUM: pageIndex,
          PAGE_SIZE: pageSize,
        })
      return rs.data.pageItems
    },
    async layDsBaoHong(baohong_id) {
      let rs
      try {
        rs = await this.$root.context.get("web-baohong/tientrinhit360/lay_danhsach_issues_baohong_sel", { p_baohong_id: baohong_id })
        return rs.data
      } catch (e) {
        this.$root.toastError("Có lỗi khi lấy thông tin issues" + e.response.data.message_detail)
        return []
      }
    },
    async layDsIssues(hdtb_id) {
      let rs
      try {
        rs = await this.$root.context.get("web-hopdong/QuanLyIssue/lay-ds-issues", { hdtb_id: hdtb_id })
        return rs.data
      } catch (e) {
        this.$root.toastError("Có lỗi khi lấy thông tin issues" + e.response.data.message_detail)
        return []
      }
    },
    dsHopDong_PageChanged: async function (args) {
      this.paging_dsHopDong.pSize = args.pageSize;
      this.paging_dsHopDong.pNo = args.pageIndex;
      try {
        this.loading(true);
        let tu_ngay = this.dtTuNgay && this.isDisableNgay == false ? DateTimeLib.toDateDisplay(this.dtTuNgay) : null,
          den_ngay = this.dtDenNgay && this.isDisableNgay == false ? DateTimeLib.toDateDisplay(this.dtDenNgay) : null
        await this.layDsHopDong(this.trangThaiId, this.trangThaiHDId, this.maThueBao, tu_ngay, den_ngay, 0,
          this.paging_dsHopDong.pNo, this.paging_dsHopDong.pSize)
      } catch (error) {

      } finally {
        this.loading(false);
      }
    },

    gridDsHopDong_rowSelected: async function (data) {
      this.dsYeuCau = []
      try {
        this.loading(true)
        if(this.checkboxLoai == 1) {
          this.dsYeuCau = await this.layDsIssues(data.hdtb_id)
        } else {
          this.dsYeuCau = await this.layDsBaoHong(data.baohong_id)
        }
      } catch (e) {

      } finally {
        this.loading(false)
      }
    },

    gridDsYeuCau_rowSelected: async function (data) {
      this.issueSelected = data
      if (data)
        await this.NAP_TT_JIRA(data.key)
      else this.CLEAR()
    },
    gridDsWatchers_rowSelected: async function (data) {
    },
    gridDsFile_rowSelected: async function (data) {
    },
    gridDsComment_rowSelected: async function (data) {
    },
    async NAP_TT_JIRA(id) {
      this.CLEAR()
      try {
        this.loading(true)
        await this.NAP_DS_ISSUES(id)
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    CLEAR: function () {
      this.vkey_ht = ""
      this.vid_ht = ""
      this.vproject_id = ""
      this.txtSummary = ""
      this.txtDescription = ""
      this.txtReporter = ""
      this.txtIssuseType = ""
      this.dtpResolved = null
      this.dtpDueDate = null
      this.dsComment = []
      this.dsFile = []
      this.trangthai_iss = null
      // ds_comment.Clear();
      // ds_file.Clear();
      this.dsWatchers = []
      // lstWatchers.Clear();
      this.txtParicipant = ""
    },
    async NAP_DS_ISSUES(id) {
      try {
        let ds_Infor = await JiraService.Get_Issue_Info(id)
        let dsWatchers = await JiraService.Get_Issue_Watchers(id)
        if (ds_Infor.data) {
          if (ds_Infor.data.errorMessages && ds_Infor.data.errorMessages.length) {
            for (let error of ds_Infor.data.errorMessages) {
              if (error.includes("permission"))
                this.$root.toastError("Bạn không có quyền Jira để xem nội dung này!")
            }
            return
          } // else this.$root.toastError(ds_Infor.faultString)
          // if (ds_Infor.data.errorMessages && ds_Infor.errorMessages.includes('permission')) {
          //   this.$root.toastError("Bạn không có quyền Jira để xem nội dung này!")
          // }
          // this.$root.toastError(ds_Infor.faultString)
        }
        this.vkey_ht = ds_Infor.data.key
        this.vid_ht = ds_Infor.data.id
        this.vproject_id = ds_Infor.data.fields.project.id
        let dt = DateTimeLib.toDateDisplay(ds_Infor.data.fields.duedate)
        this.NAP_ISSUES_INFOR(ds_Infor.data)
        if (ds_Infor.data.fields.comment.comments.length) {
          for (let item of ds_Infor.data.fields.comment.comments) {
            let row = {}
            row.id = item.id
            row.body = item.body
            row.created = this.ddmmyyyyhhmiss(item.created)
            row.updated = this.ddmmyyyyhhmiss(item.updated)
            row.author = item.author.displayName
            row.name = item.author.name
            this.dsComment.unshift(row)
          }
        }
        if (ds_Infor.data.fields.attachment.length) {
          for (let item of ds_Infor.data.fields.attachment) {
            let row = {}
            row.id = item.id
            row.content = item.content
            row.created = this.ddmmyyyyhhmiss(item.created)
            row.filename = item.filename
            row.author = item.author.displayName
            row.name = item.author.name
            this.dsFile.unshift(row)
          }
        }
        this.dsWatchers = dsWatchers.data.watchers
      } catch (e) {
        console.log(e)
      }
    },
    async NAP_ISSUES_INFOR(data) {
      this.txtSummary = data.key + ": " + data.fields.summary
      this.txtDescription = data.fields.description
      this.txtReporter = data.fields.reporter.name
      this.txtIssuseType = data.fields.issuetype.name
      this.dtpDueDate = DateTimeLib.toDateDisplay(data.fields.duedate)
      if (data.fields.status.name) {
        let trangthai = this.dsTrangThai.find(x =>
          x.ID.toLowerCase().replaceAll(' ', '').replaceAll('-', '') == data.fields.status.name.toLowerCase().replaceAll(' ', '').replaceAll('-', ''))
        if (trangthai) {
          this.cboTrangThai = trangthai.ID
          this.trangthai_iss = trangthai.ID
        }
      }
      if (data.fields.assignee) {
        if (data.fields.assignee.name) {
          this.txtAssignee = data.fields.assignee.name
        } else this.txtAssignee = ""
      } else this.txtAssignee = ""
      if (data.fields.resolutiondate)
        this.dtpResolved = DateTimeLib.toDateTimeDisplay(data.fields.resolutiondate)
    },
    async btnAssignee_ButtonClick() {
      try {
        this.loading(true)
        if (this.txtAssignee) {
          let ds = await JiraService.Find_Employees_By_EmailOrPhone(this.txtAssignee.trim())
          if (ds && ds.result.length) {
            let data = ds.result.records[0]
            this.$root.toastInfo("Tên nhân viên : " + data.name + '.\n' +
              "Đơn vị : " + data.department + '.\n' +
              "Công việc : " + data.job_name + '.\n' +
              "Số điện thoại : " + data.mobile_phone + '.\n')
          } else {
            this.$root.toastError("Lỗi xảy ra khi lấy thông tin nhân viên")
          }
        }
      } catch (e) {

      } finally {
        this.loading(false)
      }
    },
    async vbtnSend_Click() {
      await this.COMMENT_ISSUES()
    },
    async txtAddWatcher_ButtonClick() {
      try {
        this.loading(true)
        if (!this.txtAddWatcher) {
          this.$root.toastError("Bạn chưa nhập thông tin người theo dõi")
          return
        }
        let kq = await JiraService.Add_Issue_Watcher(this.vkey_ht, (this.txtAddWatcher + '').trim())
        if (kq.data && kq.data.errorMessages && kq.data.errorMessages.length) {
          for (let error of kq.data.errorMessages)
            this.$root.toastError("Lỗi: " + error)
          return
        }
        if (kq.errorCode != 0) {
          this.$root.toastError("Lỗi " + kq.faultString)
          return
        }
        let dsWatchers = await JiraService.Get_Issue_Watchers(this.vkey_ht)
        if (dsWatchers.data.errorMessages && dsWatchers.data.errorMessages.length) {
          for (let error of dsWatchers.data.errorMessages) {
            if (error.includes("permission")) {
              this.$root.toastError("Bạn không có quyền Jira để xem nội dung này!")
              return
            }
            this.$root.toastError("Lỗi: " + error)
          }
        }
        this.dsWatchers = dsWatchers.data.watchers
        this.txtAddWatcher = ""
        this.$root.toastSuccess("Thêm watcher thành công")
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    async COMMENT_ISSUES() {
      if (!this.vkey_ht) {
        this.$root.toastError("Không lấy được ID")
        return
      }
      try {
        this.loading(true)
        let kq = await JiraService.Set_Issue_Comment(this.vkey_ht, (this.txtComment + '').trim())
        if (kq.data && kq.data.errorMessages && kq.data.errorMessages.length) {
          for (let error of kq.data.errorMessages) {
            if (error.includes("permission"))
              this.$root.toastError("Bạn không có quyền Comment Issue này!")
            else this.$root.toastError("Lỗi comment " + error)
          }
          return
        }
        if (kq.errorCode != 0) {
          this.$root.toastError("Lỗi " + kq.faultString)
          return
        }
        let rsMap = await this.Map_CommentToHDTB(
          this.issueSelected.hdtb_id, this.issueSelected.issue_id, this.issueSelected.key, kq.data.id,
          (this.txtComment + '').trim(), this.$auth.getUserName(), this.$auth.getUserName())
        if (rsMap.error_code != "BSS-00000000" || !rsMap.data) {
          this.$root.toastError("Có lỗi khi cập nhật yêu cầu vào hợp đồng trên ĐHSX: " + rsMap.message_detail)
        }
        this.dsComment.unshift({
          id: kq.data.id,
          body: kq.data.body,
          created: this.ddmmyyyyhhmiss(kq.data.created),
          updated: this.ddmmyyyyhhmiss(kq.data.updated),
          author: kq.data.author.displayName,
          name: kq.data.author.name
        })
        this.txtComment = ""
        this.$root.toastSuccess("Comment Issue thành công !")
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    /**
     * Thêm mới comment
     * @return {Promise<void>}
     * @constructor
     */
    async Map_CommentToHDTB(HDTB_ID, ISSUE_ID, KEY, COMMENT_ID, COMMENT_JR, NGUOI_CN, MAY_CN) {
      return await this.$root.context.post("web-hopdong/QuanLyIssue/themmoi-jira-hdtb-comment", {
        HDTB_ID: HDTB_ID,
        ISSUE_ID: ISSUE_ID,
        KEY: KEY,
        COMMENT_ID: COMMENT_ID,
        COMMENT_JR: COMMENT_JR,
        NGUOI_CN: NGUOI_CN,
        MAY_CN: MAY_CN
      })
    },
    async btnThemFile_Click() {
      if (!this.vkey_ht) {
        this.$root.toastError("Không lấy được key yêu cầu")
        return
      }
      if (!this.fileUpload) {
        this.$root.toastError("Chưa chọn file upload")
        return
      }
      try {
        this.loading(true)
        let kq = await JiraService.Add_Issue_Attachments(this.vkey_ht, "", "", this.fileUpload)
        if (kq.data && kq.data.errorMessages && kq.data.errorMessages.length) {
          for (let error of kq.data.errorMessages)
            this.$root.toastError("Lỗi: " + error)
          return
        }
        if (kq.errorCode != 0) {
          this.$root.toastError("Lỗi " + kq.faultString)
          return
        }
        let row = {}
        row.id = kq.data[0].id
        row.content = kq.data[0].content
        row.created = this.ddmmyyyyhhmiss(kq.data[0].created)
        row.filename = kq.data[0].filename
        row.author = kq.data[0].author.displayName
        row.name = kq.data[0].author.name
        this.dsFile.unshift(row)
        this.fileUpload = null
        this.$root.toastSuccess("Thêm file thành công")
      } catch (e) {
        console.log(e)
        this.$root.toastError("Có lỗi khi upload file ")
      } finally {
        this.loading(false)
      }
    },
    async btnCapNhat_Click() {
      if (this.trangthai_iss != this.cboTrangThai) {
        let answer = false
        await this.$bvModal.msgBoxConfirm(`Bạn có muốn cập nhật trạng thái yêu cầu từ ${this.trangthai_iss} -> ${this.cboTrangThai} không?`, {
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy',
        }).then(async v => {
          if (v) {
            answer = true
          }
          else {
            answer = false
          }
        })
        if (!answer)
          return
        let chon = this.dsTrangThai.find(i => i.ID == this.cboTrangThai)
        if (!chon) {
          this.$root.toatError("Không lấy được ID trạng thái JIRA ")
          return
        }
        let name = chon.ID,
          trangthai = chon.TTJR_ID
        if (trangthai == -1) {
          this.$root.toastError("Không thể mở về trạng thái " + this.cboTrangThai)
          this.cboTrangThai = this.trangthai_iss
          return
        }
        try {
          this.loading(true)
          let trangthai_id = -999, thongbao = ""
          let tk = await JiraService.Get_transition(this.issueSelected.key)
          if (tk.data && tk.data.errorMessages && tk.data.errorMessages.length) {
            for (let error of tk.data.errorMessages) {
              this.$root.toastError("Không lấy được trạng thái phiếu " + this.cboTrangThai + " " + error)
            }
            this.cboTrangThai = this.trangthai_iss
            return
          }
          if (tk.errorCode != 0) {
            this.$root.toastError("Lỗi: " + tk.faultString)
            return
          }
          let kieu = 0, cls_iss = false, transaction_name = ""
          for (let t of tk.data.transitions) {
            if (t.name.toLowerCase() == name.toLowerCase() || t.name.toLowerCase() == "close issue") {
              if (name.toLowerCase() == "closed" || name.toLowerCase() == "close issue") {
                cls_iss = true
                kieu = 1
              }
              if (kieu != 1 && this.txtIssuseType.toLowerCase() == "change request")
                kieu = 2
              trangthai_id = t.id
              transaction_name = t.name
            }
            thongbao += t.name + ", "
          }
          thongbao = thongbao.trim().slice(0, -1)
          if (trangthai_id == -999) {
            this.$root.toastError("Trạng thái phiếu chỉ được chuyển sang các trạng thái " + thongbao)
            return
          }
          // kieu = 2
          let cmt = ""
          if (kieu == 1 || kieu == 2) {
            this.loading(false)
            let noidung = cls_iss ? "Nhập nội dung đóng phiếu" : "Nhập nội dung phiếu";
            let rs = await this.$refs.formNhapText.show(noidung)
            if (rs.ok) {
              cmt = rs.data
            } else return
          }
          this.loading(true)
          // console.log("cmt:",cmt)
          // transaction_name = "Chỉnh duedate"
          // trangthai_id = 301
          let kq = await JiraService.Add_Issue_Transitions(this.issueSelected.key, trangthai_id, kieu, cmt, transaction_name)
          if (kq.data && kq.data.errorMessages && kq.data.errorMessages.length) {
            for (let error of kq.data.errorMessages)
              this.$root.toastError("Không thực hiện chuyển trạng thái : " + error)
            return
          }
          if (kq.errorCode != 0) {
            if (kq.faultString.includes("Internal server error"))
              this.$root.toastError("Không được thực hiện chuyển sang trạng thái : " + this.cboTrangThai)
            else this.$root.toastError("Không thực hiện chuyển trạng thái " + kq.faultString)
            return
          }
          // capnhat
          let issueID = this.issueSelected.issue_id
          let rs = await this.CapNhatJiraGiaoPhieuIssue(issueID, trangthai_id)
          if (rs.data) {
            this.$root.toastSuccess("Chuyển trạng thái IT360 thành công !")
          } else this.$root.toastError("Cập nhật trạng thái jira thất bại")
        } catch (e) {
          console.log(e)
          this.$root.toastError("Có lỗi khi cập nhật trạng thái yêu cầu : " + JSON.parse(e))
        } finally {
          this.loading(false)
        }
      }
    },
    async tsbtnDongBo_Click() {
      if (!this.dsHopDong || !this.dsHopDong.length) {
        this.$root.toastError("Không có danh sách hợp đồng để thực hiện")
        return
      }
      if (!this.dongbo) {
        this.processJira.value = 0
        this.dongbo = true
        this.bgwThread_DoWork()
        this.isEnableDongBo = false
        this.isEnableHuyDongBo = true
      }
    },
    async bgwThread_DoWork() {
      try {
        while (this.dongbo) {
          try {
            let jump = 50;
            let dich = 0;
            let sl = 0;
            let idkey = "";
            let pageIndex = 0;
            this.processJira.Minimum = 0
            this.processJira.Maximum = this.totalRowDsHopDong
            this.layoutJira = "Đang đồng bộ"
            if (this.totalRowDsHopDong == 0)
              return
            if (!this.dongbo) break // huy dong bo
            // dich = sl + jump
            // get ds jira theo jump
            let tu_ngay = this.dtTuNgay && this.isDisableNgay == false ? DateTimeLib.toDateDisplay(this.dtTuNgay) : null,
              den_ngay = this.dtDenNgay && this.isDisableNgay == false ? DateTimeLib.toDateDisplay(this.dtDenNgay) : null
            let dt
            let json_list
            let ds_jira
            if(this.checkboxLoai == 1) {
              dt = await this.layDsHopDongCanDongBo(this.trangThaiId, this.trangThaiHDId, this.maThueBao, tu_ngay, den_ngay, 0,
              pageIndex, jump)
            } else {
              dt = await this.layDsBaoHongCanDongBo(this.trangThaiId, this.trangThaiBaoHongId , this.maThueBao, tu_ngay, den_ngay, 0,
              pageIndex, jump)
            }
            if(this.checkboxLoai == 1) {
              json_list = dt.map(i => ({ HDTB_ID: i.hdtb_id }))
              ds_jira = await this.LayDsJiraTheoHDTB(json_list)
            } else {
              json_list = dt.map(i => ({ BAOHONG_ID: i.baohong_id }))
              ds_jira = await this.LayDsJiraTheoHDBH(JSON.stringify(json_list))
            }
            if (ds_jira.length == 0) {
              return;
            }
            this.processJira.Minimum = 0
            this.processJira.Maximum = ds_jira.length;
            this.layoutJira = "Đang đồng bộ"
            // if (dich >= this.totalRowDsHopDong)
            //   dich = this.totalRowDsHopDong
            while (sl <= ds_jira.length - 1) {
              dich = sl + jump
              if (dich >= ds_jira.length) {
                dich = ds_jira.length;
              }
              idkey = ""
              for (let dem = 0; dem < dich; dem++) {
                this.processJira.value = dem + 1;
                idkey = idkey + ds_jira[dem].key + ","
              }
              sl += jump
              pageIndex++
              let kq = await JiraService.Get_List_Issue(idkey.slice(0, -1))
              if (kq.data && kq.data.errorMessages && kq.data.errorMessages.length) {
                for (let error of kq.data.errorMessages) {
                  this.$root.toastError("Không lấy được danh sách issue: " + error)
                }
                this.layoutJira = "Đang đồng bộ (Có lỗi)"
              }
              if (kq.errorCode != 0) {
                this.$root.toastError("Lỗi: " + kq.faultString)
                this.layoutJira = "Đang đồng bộ (Có lỗi)"
              }
              let jsonlist = []
              if (kq.data.issues && kq.data.issues.length) {
                jsonlist = kq.data.issues.map(i => ({
                  key: i.key,
                  status: i.fields.status.name
                }))
              }
              let tt = await this.$root.context.post("web-hopdong/QuanLyIssue/dongbo-jira", jsonlist)
              if (tt.data == 0)
                this.$root.toastError("Lỗi đồng bộ jira")
            }
          } catch (e) {
            console.log(e)
            this.$root.toastError("Đang đồng bộ (Có lỗi)")
            this.layoutJira = "Đang đồng bộ (Có lỗi)"
          } finally {
            this.layoutJira = "Đồng bộ Jira"
            this.$root.toastSuccess("Đồng bộ xong")
            this.dongbo = false
            this.isEnableHuyDongBo = false
            this.isEnableDongBo = true
          }

        }
      } catch (e) {

      } finally {
        // await this.layThongTinClick()
      }
    },
    bgwAuto_DoWork() {
      //TODO: cần tạo job worker schedule để chạy đồng bộ jira trên server
      // this.$root.toastError("Tính năng tự động đồng bộ - đang phát triển")
    },
    async LayDsJiraTheoHDBH(dsHDBH_ID) {
      let rs = await this.$root.context.post("web-baohong/tientrinhit360/lay_danhsach_issues_baohong_list", {
        p_js_baohong: dsHDBH_ID
      })
      return rs.data
    },
    async LayDsJiraTheoHDTB(dsHDTB_ID) {
      let rs = await this.$root.context.post("web-hopdong/QuanLyIssue/get-jira-issues-by-hdtb-list", dsHDTB_ID)
      return rs.data
    },
    async CapNhatJiraGiaoPhieuIssue(issueID, trangthai_id) {
      return await this.$root.context.post(`web-hopdong/QuanLyIssue/capnhat-trangthai-issue-jira?TTJR_ID=${trangthai_id}&ISSUE_ID=${issueID}`)
    },
    async btnParicipant_ButtonClick() {
      if (!this.txtParicipant) {
        this.$root.toastError("Bạn chưa nhập tài khoản Jira")
        return
      }
      let kq = await JiraService.Add_Issue_Participant(this.issueSelected.key, this.txtParicipant)
      if (kq.data && kq.data.errorMessages && kq.data.errorMessages.length) {
        for (let error of kq.data.errorMessages) {
          this.$root.toastError("Lỗi: " + error)
        }
        return
      }
      if (kq.errorCode != 0) {
        this.$root.toastError("Lỗi: " + kq.faultString)
        return
      }
      this.$root.toastSuccess("Thêm thành công")
      this.txtParicipant = ""
    },
    gridDsHopDong_queryCellInfo: function (args) {
      switch (args.column.field) {
        case 'ma_tb':
          args.cell.style.color = 'blue'
          args.cell.style.fontWeight = 'bold'
          break
        case 'ma_gd':
          args.cell.style.color = 'darkred'
          args.cell.style.fontWeight = 'bold'
      }
    },
    gridDsYeuCau_queryCellInfo: function (args) {
      switch (args.column.field) {
        case 'key':
          args.cell.style.color = 'blue'
          args.cell.style.fontWeight = 'bold'
          break
      }
    },
    ddmmyyyyhhmiss(date) {
      if (!date) return ''
      let data = this.getDateElements(date)
      return `${data.d}/${data.M}/${data.y} ${data.h}:${data.m}:${data.s}`
    },
    ddmmyyyy(date) {
      if (!date) return ''
      let data = this.getDateElements(date)
      return `${data.d}/${data.M}/${data.y}`
    },
    getDateElements(date) {

      date = new Date(date)

      let day = date.getDate()
      day = day > 9 ? day : '0' + day

      let month = date.getMonth() + 1
      month = month > 9 ? month : '0' + month

      let hour = date.getHours()
      hour = hour > 9 ? hour : '0' + hour

      let min = date.getMinutes()
      min = min > 9 ? min : '0' + min

      let second = date.getSeconds()
      second = second > 9 ? second : '0' + second

      return {
        y: date.getFullYear(),
        M: month,
        d: day,
        h: hour,
        m: min,
        s: second
      }
    },
    resetFileUpload() {
      this.fileUpload = null
    },
    changeDataSearchByDate() {
      if (this.chkNgay) {
        let isExist = false
        for (let i = 0; i < this.dataSearch.length; i++) {
          if (this.dataSearch[i].toString().includes("Ngày lập HĐ:")) {
            isExist = true
            this.dataSearch[i] = "Ngày lập HĐ:" + this.ddmmyyyy(this.dtTuNgay) + "đến " + this.ddmmyyyy(this.dtDenNgay)
            break
          }
        }
        if (!isExist) {
          this.dataSearch.push("Ngày lập HĐ:" + this.ddmmyyyy(this.dtTuNgay) + "đến " + this.ddmmyyyy(this.dtDenNgay))
        }
      } else {
        this.dataSearch = this.dataSearch.filter(e => !e.toString().includes("Ngày lập HĐ:"))
      }
    }
  },
  mounted: async function () {
  },
  async created() {
    JiraService.setupRoot(this.$root)
    await this.frmQL_Issue_Load()
  },
  watch: {
    chkNgay: function (value) {
      this.isDisableNgay = !value
      this.changeDataSearchByDate()
    },
    dtTuNgay: function (tuNgay) {
      this.changeDataSearchByDate()
    },
    dtDenNgay: function (denNgay) {
      this.changeDataSearchByDate()
    },
    trangThaiId: function (value) {
      if (value && value.length > 0) {
        let isExist = this.dataSearch.some(x => x.toString() == "Trạng thái")
        if (!isExist) {
          this.dataSearch.push("Trạng thái")
        }
      } else {
        this.dataSearch = this.dataSearch.filter(e => !e.toString().includes("Trạng thái"))
      }
    },
    trangThaiHDId: function (value) {
      if (value && value.length > 0) {
        let isExist = this.dataSearch.some(x => x.toString() == "Trạng thái HĐ")
        if (!isExist) {
          this.dataSearch.push("Trạng thái HĐ")
        }
      } else {
        this.dataSearch = this.dataSearch.filter(e => !e.toString().includes("Trạng thái HĐ"))
      }
    },
    trangThaiBaoHongId: function (value) {
      if (value && value.length > 0) {
        let isExist = this.dataSearch.some(x => x.toString() == "Trạng thái BH")
        if (!isExist) {
          this.dataSearch.push("Trạng thái BH")
        }
      } else {
        this.dataSearch = this.dataSearch.filter(e => !e.toString().includes("Trạng thái BH"))
      }
    },
    maThueBao: function (value) {
      if (value) {
        let isExist = false
        for (let i = 0; i < this.dataSearch.length; i++) {
          if (this.dataSearch[i].toString().includes("Mã thuê bao:")) {
            isExist = true
            this.dataSearch[i] = "Mã thuê bao:" + value
            break
          }
        }
        if (!isExist) {
          this.dataSearch.push("Mã thuê bao:" + value)
        }
      } else {
        this.dataSearch = this.dataSearch.filter(e => !e.toString().includes("Mã thuê bao:"))
      }
    },
    // fileUpload: async function (value) {
    //   await this.buttonReadFile_Click();
    // },
  }
}
</script>

<style>
/*a.disable-btn {*/
/*  pointer-events: none;*/
/*  cursor: default;*/
/*  color: #6c757d !important;*/
/*}*/

.disabled a {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>

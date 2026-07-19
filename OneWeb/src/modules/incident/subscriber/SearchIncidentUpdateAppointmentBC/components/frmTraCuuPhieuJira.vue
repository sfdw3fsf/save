<template>
  <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogObjTraCuuPhieuJira" :position="position" :close="closeDialog" :header="'Thông tin phiếu IT360'" showCloseIcon="true" width="80%" target="#app .main-wrapper">
    <div>
      <div class="nav tabs tab-over">
        <a href="#" class="active">Thông tin gửi yêu cầu IT360</a>
      </div>
      <div class="tab-content">
        <div class="table-content">
          <DataGrid
            v-bind:columns="grvIssues.headers"
            :enabledSelectFirstRow="true"
            v-bind:dataSource="grvIssues.data"
            @selectedItemsChanged="grvIssues_selectedItemsChanged"
            @rowClicked="grvIssues_FocusedRowChanged"
            :showColumnCheckbox="false"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
            ref="gridgrvIssues"
          >
          </DataGrid>
        </div>
        <div class="row mart20">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w90">Tiêu đề</div>
              <div class="value">
                <textarea v-model="txtSummary" name="" class="form-control" style="height: 160px; resize: none" id="" cols="30" rows="10"></textarea>
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">Trạng thái</div>
              <div class="value">
                <SelectExt v-model="cboTrangThai" :dataSource="cboTrangThaiList" dataTextField="trangthai_jira" dataValueField="id"></SelectExt>
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">Ngày hết hạn</div>
              <div class="value">
                <date-picker :format="dateFormat" :value-type="dateFormat" :time-title-format="dateFormat" v-model="dtpDueDate" :type="typeFormat">
                  <template #icon-calendar>
                    <span class="icon one-calendar"></span>
                  </template>
                </date-picker>
                <!-- <div class="input-icon-right">
                  <input type="text" class="form-control" />
                  <span class="icon one-calendar"></span>
                </div> -->
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">Ngày HT</div>
              <div class="value">
                <date-picker :format="dateFormat" :value-type="dateFormat" :time-title-format="dateFormat" v-model="dtpResolved" :type="typeFormat">
                  <template #icon-calendar>
                    <span class="icon one-calendar"></span>
                  </template>
                </date-picker>
                <!-- <div class="input-icon-right">
                  <input type="text" class="form-control" />
                  <span class="icon one-calendar"></span>
                </div> -->
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">Loại yêu cầu</div>
              <div class="value">
                <input v-model="txtIssuseType" type="text" class="form-control" />
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">Reporter</div>
              <div class="value">
                <input v-model="txtReporter" type="text" class="form-control" />
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">Assignee</div>
              <div class="value">
                <div class="input-icon-right">
                  <input type="text" v-model="btnAssignee" class="form-control" />
                  <span class="icon -ap icon-more_horiz"></span>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w90">Mô tả</div>
              <div class="value">
                <textarea v-model="txtDescription" name="" class="form-control" style="height: 370px; resize: none" id="" cols="30" rows="10"></textarea>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>
<script>
import api from '../Api'
import frmApi from './PopUpApi.js'
import moment from 'moment'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
export default {
  name: 'frmTraCuuPhieuJira',
  components: { moment, DatePicker, api, frmApi },
  props: {
    params: {
      type: Object,
      default: function () {
        return null
      }
    }
  },
  watch: {
    Loading: function (val, oldval) {
      this.$root.showLoading(val)
    }
  },
  data() {
    return {
      Loading: false,
      target: '#app', //  .main-wrapper-payment
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      dateFormat: 'DD/MM/YYYY',
      dateFormatMonth: 'MM/YYYY',
      typeFormat: 'date',
      typeFormatMonth: 'month',
      khachhang_id: 0,
      vkey_ht: '',
      vid_ht: '',
      vproject_id: '',
      trangthai_iss: '',
      ma_kh: '',
      //Dungpa - ngay 31/07/2013
      chapnhanTT: '',
      thanhtoan_id: '',
      ma_tt: '',
      ma_hd: '',
      ma_tb: '',
      diachi_ld: '',
      chapnhan: '',
      dichvuvt_id: 0,
      kieu: 0,
      l_thuebao_id: '',
      vsvlan: '',
      vcvlan: '',
      vsite_id: '',
      vthuebao_id: 0,
      vphieu_id: -1,
      grvIssues: {
        headers: [
          {
            fieldName: 'key',
            headerText: 'ID phiếu',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ngay_cn',
            headerText: 'Ngày CN',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nguoi_cn',
            headerText: 'Người tạo',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'url',
            headerText: 'URL',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        data: [],
        selectedItem: {},
        selectedItems: []
      },
      txtSummary: '',
      cboTrangThai: '',
      cboTrangThaiList: [],
      dtpDueDate: '',
      dtpResolved: '',
      txtIssuseType: '',
      txtReporter: '',
      btnAssignee: '',
      txtDescription: ''
    }
  },
  methods: {
    openDialog(phieu_id) {
      try {
        this.CLEAR()
        this.grvIssues.data = []
        this.vphieu_id = phieu_id
        this.getDataFromProp()
        Promise.all([this.frmTraCuuPhieuJira_Load()])
          .then((r) => {})
          .catch((err) => {
            console.log(err)
          })
          .finally(() => {})
      } catch (err) {
      } finally {
        this.$refs.dialogObjTraCuuPhieuJira.show()
      }
    },
    getDataFromProp() {
      if (this.params != null) {
        let keys = Object.keys(this.params)
        for (const k of keys) {
          console.log('getDataFromProp:', k, ',value:', this.params[k])
          if (this.params[k] != null && this.params[k] != undefined) this[k] = this.params[k]
        }
      }
    },
    async frmTraCuuPhieuJira_Load() {
      this.CREATE_TABLE_CMT()
      this.LOAD_COMBO()
      this.NAP_DS_ISSUES()
    },
    async CREATE_TABLE_CMT() {},
    async LOAD_COMBO() {
      await this.sp_lay_trangthai_jira()
    },
    async NAP_DS_ISSUES() {
      await this.sp_lay_ds_jira_theo_phieuid()
    },
    async NAP_DS_ISSUES_ID(id) {
      try {
        this.CLEAR()
        let data = await this.get_issue_info(id)
        if (data == null) return
        console.log('NAP_DS_ISSUES_ID', data)
        this.vkey_ht = data.key
        this.vid_ht = data.id
        this.txtSummary = this.vkey_ht + ': ' + data.fields.summary
        this.txtDescription = data.fields.description
        this.txtReporter = data.fields.reporter.name
        this.vproject_id = data.fields.project.id
        this.txtIssuseType = data.fields.issuetype.name
        if (data.fields.duedate != null && data.fields.duedate != '') this.dtpDueDate = moment(data.fields.duedate, 'YYYY-MM-DD').format('DD/MM/YYYY')
        if (data.fields.status.name != null && data.fields.status.name != '') {
          this.cboTrangThai = data.fields.status.name
          this.trangthai_iss = data.fields.status.name
        }
        if (data.fields.assignee != null) {
          if (data.fields.assignee.name != null) this.btnAssignee = data.fields.assignee.name
          else this.btnAssignee = ''
        } else this.btnAssignee = ''
        if (data.fields.resolutiondate != null && data.fields.resolutiondate != '') this.dtpResolved = moment(data.fields.resolutiondate, 'YYYY-MM-DD').format('DD/MM/YYYY')
      } catch (err) {
        this.ShowError(`${err.message}`)
      }
    },
    CLEAR() {
      this.vkey_ht = ''
      this.vid_ht = ''
      this.vproject_id = ''
      this.txtSummary = ''
      this.txtDescription = ''
      this.txtReporter = ''
      this.txtIssuseType = ''
      this.dtpResolved = null
      this.dtpDueDate = null
      this.trangthai_iss = ''
      this.cboTrangThai = null
      this.btnAssignee = ''
    },
    grvIssues_selectedItemsChanged(items) {
      this.grvIssues.selectedItems = items
    },
    async grvIssues_FocusedRowChanged(i, item) {
      try {
        this.Loading = true
        this.grvIssues.selectedItem = item
        await this.NAP_DS_ISSUES_ID(item.key)
      } catch (err) {
        this.ShowError(`${err.message}`)
      } finally {
        this.Loading = false
      }
    },
    async get_issue_info(key) {
      let input = { issueKeyOrID: key }
      //let input = { issueKeyOrID: 'IT360-434098' }
      let response = await frmApi.get_issue_info(this.axios, input)
      //console.log('get_issue_info1', response.data)
      if (response.data.errorCode != '0') {
        this.ShowError(`${response.data.errorCode}: ${response.data.faultString}`)
        return null
      }
      //console.log('get_issue_info2', response.data.data)
      return response.data.data
    },
    async sp_lay_ds_jira_theo_phieuid() {
      try {
        let input = { phieuid: this.vphieu_id } //this.vphieu_id
        let data = this.GetData(await frmApi.sp_lay_ds_jira_theo_phieuid(this.axios, input))
        this.grvIssues.data = data
        if (data != null && data.length > 0) {
          await this.grvIssues_FocusedRowChanged(0, data[0])
        }
        return data
      } catch (err) {
        this.ShowError(`Có lỗi khi lấy thông tin issuse: ${err.message}`)
      }
    },
    async sp_lay_trangthai_jira() {
      let input = {}
      let data = this.GetData(await frmApi.sp_lay_trangthai_jira(this.axios, input))
      this.cboTrangThaiList = data
      return data
    },
    closeDialog() {},
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    ShowAlert: function (msg) {
      this.$toast.warning(msg)
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg)
    },
    ShowError: function (msg) {
      this.$toast.error(msg)
    }
  }
}
</script>
<template>
  <div class="modal-content popup-box">
    <div class="list-actions-top">
      <ul class="list">
        <li @click="onBtnSyncClicked" :class="type ? 'non-ative' : ''">
          <a> <span class="icon one-file-attach"></span>Lấy DS</a>
        </li>
        <li @click="onBtnSearchClicked" :class="type ? 'non-ative' : ''">
          <a> <span class="icon one-search"></span>Tìm kiếm </a>
        </li>
        <li @click="onBtnSyncJiraClicked" :class="type ? 'non-ative' : ''">
          <a> <span class="icon one-sync1"></span>Đồng bộ Jira</a>
        </li>
        <li @click="onBtnSyncJiraCancelClicked" :class="jiraListSyncedCount == 0 && jiraListSyncTotal == 0 ? 'non-ative' : ''">
          <a> <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy đồng bộ </a>
        </li>
        <li @click="onBtnExportFileClicked" :class="resultGrid.dataSource.length == 0 ? 'non-ative' : ''">
          <a> <span class="icon one-xlsx-import"></span>Xuất File </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div v-if="jiraListSyncedCount != 0 && jiraListSyncTotal != 0" class="box-form">
        <div class="info-row">
          <div class="key nowrap">Đồng bộ Jira</div>
          <div class="value">
            <!--            <span class="btn btn-main fullw">{{this.processJira.value}}/{{ this.processJira.Maximum }}</span>-->
            <div id="progressBar" class="progress-bar progress-bar-striped progress-bar-animated" :style="{ width: (jiraListSyncedCount / jiraListSyncTotal) * 100 + '%' }">{{ jiraSynced.length }}/{{ jiraTotal.length }}</div>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">Mã thuê bao</div>
              <div class="value">
                <input type="text" v-model="vma_tb" ref="txtDienThoai" class="form-control" title="Số điện thoại không được ít hơn 10 số và phải bắt đầu bằng 0" />
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">Mã giao dịch</div>
              <div class="value">
                <div class="input-icon-right">
                  <input type="text" v-model="vma_gd" ref="txtFax" pattern="^0\d{9,}$" class="form-control" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">Mã phiếu Jira</div>
              <div class="value">
                <input type="text" v-model="vma_jira" ref="txtEmail" class="form-control" />
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">Ngày tạo</div>
              <div class="value">
                <div class="input-icon-right">
                  <!-- <input type="text" v-model="txtDonVi" class="form-control"/> -->
                  <vue-date format="DD/MM/YYYY" type="datetime" v-model="vngay_tao"></vue-date>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="box-form marb0">
        <div class="legend-title">Danh sách yêu cầu</div>
        <div class="table-content">
          <div>
            <DataGrid ref="resultGrid" :columns="resultGrid.columns" :dataSource="resultGrid.dataSource" height="400px" gridLines="Both" :enable-paging-server="false" :allowPaging="true" :showFilter="false"></DataGrid>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import XLSX from 'xlsx'
import API from './../API'
export default {
  name: 'IT360Information',
  data() {
    return {
      type: false,
      resultGrid: {
        columns: [
          {
            fieldName: 'ma_tb',
            headerText: 'Số máy/Acc',
            allowFiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'ma_gd',
            headerText: 'Mã GD',
            allowFiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'key',
            headerText: 'Số phiếu JIRA',
            allowFiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'ten_tb',
            headerText: 'Tên TB',
            allowFiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'loaihinh_tb',
            headerText: 'Loại hình',
            allowfiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'ngay_tao',
            headerText: 'Ngày tạo',
            allowfiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'ttjira',
            headerText: 'TT Jira',
            allowfiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'trangthai_hd',
            headerText: 'Trạng thái HĐ',
            allowfiltering: true,
            allowSorting: true,
            width: '100'
          }
        ],
        dataSource: []
      },
      vma_tb: null,
      vma_gd: null,
      vma_jira: null,
      vngay_tao: null,
      jiraListSyncedCount: 0,
      jiraListSyncTotal: 0,
      jiraSynced: [],
      jiraTotal: [],
      jiraTotalCanceled: false
    }
  },
  async mounted() {
    console.log('mounted')
    await this.onLoadData()
  },
  methods: {
    async onBtnSyncClicked() {
      this.loading(true)
      this.vma_tb = null
      this.vma_gd = null
      this.vma_jira = null
      this.vngay_tao = null
      var data = await API.sp_tracuu_phieu_issue(this.axios, {
        vma_tb: this.vma_tb,
        vma_gd: this.vma_gd,
        vma_jira: this.vma_jira,
        vngay_tao: this.vngay_tao
      })
      this.resultGrid.dataSource = data.data.data
      this.loading(false)
    },
    async onBtnSearchClicked() {
      await this.onLoadData()
    },
    async onBtnSyncJiraClicked() {
      await this.syncJira()
    },
    onBtnSyncJiraCancelClicked() {
      this.jiraTotalCanceled = true
    },
    onBtnExportFileClicked() {
      if (this.resultGrid.dataSource.length > 0) {
        this.loading(true)
        var exportData = []
        exportData = this.resultGrid.dataSource.map((e) => {
          var exportEntity = {}
          this.resultGrid.columns.map((x) => {
            exportEntity[x.fieldName] = e[x.fieldName]
          })
          return exportEntity
        })
        if (exportData.length > 0) {
          let worksheet = XLSX.utils.json_to_sheet(exportData)
          let workbook = XLSX.utils.book_new()
          XLSX.utils.book_append_sheet(workbook, worksheet, 'Dữ liệu')
          XLSX.writeFile(workbook, 'export.xlsx')
        } else {
          // this.$toast.warning('Không có dữ liệu để xuất file"!')
        }
        this.loading(false)
      }
    },
    async onLoadData() {
      this.loading(true)
      var data = await API.sp_tracuu_phieu_issue(this.axios, {
        vma_tb: this.vma_tb,
        vma_gd: this.vma_gd,
        vma_jira: this.vma_jira,
        vngay_tao: this.vngay_tao
      })
      this.resultGrid.dataSource = data.data.data
      if (this.resultGrid.dataSource.length == 0) {
        this.$toast.warning('Không tìm thấy thông tin phiếu giao đến"!')
      }
      this.loading(false)
      console.log('data')
      console.log(data)
    },
    async syncJira() {
      this.resetSync()
      var hdtbList = this.resultGrid.dataSource.map((e) => {
        return { HDTB_ID: e.hdtb_id }
      })
      console.log(this.hdtbList)
      var jiraKeyList = (await API.get_jira_issues_by_hdtb_list(this.axios, hdtbList)).data.data
      this.jiraTotal = jiraKeyList.map((e) => e)

      var jiraKeyDivided = [],
        size = 40

      while (jiraKeyList.length > 0) jiraKeyDivided.push(jiraKeyList.splice(0, size))
      console.log(jiraKeyDivided)

      console.log(jiraKeyList)

      var jiraWillSyncList = []

      this.jiraListSyncTotal = jiraKeyDivided.length

      for (var i = 0; i < jiraKeyDivided.length; i++) {
        var listKeyString = jiraKeyDivided[i]
          .map((e) => {
            return e.key
          })
          .join(',')
        var jiraIssueList = (await API.get_List_Issue(this.axios, { list: listKeyString })).data.data

        var outputList = jiraIssueList.issues.map((e) => {
          return {
            key: e.key,
            status: e.fields.status.name
          }
        })
        console.log(outputList)
        var jiraSyncedResult = await API.dongbo_jira(this.axios, outputList)
        this.jiraListSyncedCount += 1
        if (this.jiraListSyncedCount == this.jiraListSyncTotal) {
          setTimeout(() => {
            this.resetSync()
          }, 2000)

          this.$root.toastSuccess('Đồng bộ thành công')
        }
        if (this.jiraTotalCanceled == true) {
          setTimeout(() => {
            this.resetSync()
          }, 2000)
          this.$root.toastSuccess('Hủy đồng bộ thành công')
          break
        }
        this.jiraSynced.push(...outputList)
      }
      console.log(jiraWillSyncList)
    },
    resetSync() {
      this.jiraTotalCanceled = false
      this.jiraListSyncedCount = 0
      this.jiraListSyncTotal = 0
      this.jiraSynced = []
      this.jiraTotal = []
    }
  }
}
</script>
<style scoped>
.non-ative a {
  color: #d3d3d3 !important;
}
</style>

<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :isModal="true"
    :animationSettings='animationSettings'
    ref="dlgGuiEmail"
    :header='"Gửi email nhắc nợ khách hàng"'
    showCloseIcon=true
    width='90%'
    height='700'
    :target='target'
    :close="onDialogClose"
    :open="onDialogOpen"
    >
    <div class="modal-content popup-box">
      <div class="list-actions-top">
          <ul class="list">
              <li>
                  <a href="#" v-on:click.prevent="getDanhSach()">
                      <span class="icon design_bullet-list-67 nc-icon-glyph"></span> Danh sách
                  </a>
              </li>

              <li>
                  <a href="#"  v-on:click.prevent="exportExcel()">
                      <span class="icon one-xlsx-import"></span> Xuất Excel
                  </a>
              </li>
              <li>
                  <a href="#"  v-on:click.prevent="sendEmail()">
                      <span class="icon one-email"></span> Gửi email
                  </a>
              </li>
          </ul>
      </div>
      <div class="popup-body">
          <div class="box-form">
              <div class="legend-title">Thông tin Mail Server</div>
              <div class="row">
                  <div class="col-sm-4 col-12">
                      <div class="info-row">
                          <div class="key w90">Server</div>
                          <div class="value">
                            <ejs-dropdownlist :change="onChangeServerMail" :allowFiltering="false" :dataSource='params.p_mailserver_options' :fields="config.fieldsMailServer" v-model = "params.p_mailserver" :popupWidth=250 :placeholder="'Chọn mail server'"></ejs-dropdownlist>
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-2 col-12">
                      <div class="info-row">
                          <div class="key w60">Port</div>
                          <div class="value">
                              <input type="text" class="form-control" v-model="params.p_port">
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-6 col-12">
                      <div class="info-row">
                          <div class="key w90">Message</div>
                          <div class="value">
                            <ejs-dropdownlist :allowFiltering="true" :dataSource='params.p_message_options' :fields="config.fieldsMessages" v-model = "params.p_message" :popupWidth=250 :placeholder="'Chọn message'"></ejs-dropdownlist>
                          </div>
                      </div>
                  </div>
              </div>
              <div class="row">
                  <div class="col-sm-6 col-12">
                      <div class="info-row">
                          <div class="key w90">Username</div>
                          <div class="value">
                              <input type="text" class="form-control" v-model="params.p_username">
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-6 col-12">
                      <div class="info-row">
                          <div class="key w90">Trạng thái gửi</div>
                          <div class="value">
                             <ejs-dropdownlist :allowFiltering="true" :dataSource='params.p_status_options' :fields="config.fieldsStatus" v-model = "params.p_status" :popupWidth=250 :placeholder="'Chọn trạng thái'"></ejs-dropdownlist>
                          </div>
                      </div>
                  </div>
              </div>
              <div class="row">
                  <div class="col-sm-6 col-12">
                      <div class="info-row">
                          <div class="key w90">Password</div>
                          <div class="value">
                              <input type="text" class="form-control" v-model="params.p_password">
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-6 col-12">
                      <div class="info-row">
                          <div class="key w90">Tiêu đề<span class="required">*</span></div>
                          <div class="value">
                              <input type="text" class="form-control" v-model="params.p_title">
                          </div>
                      </div>
                  </div>
              </div>

          </div>
          <div class="box-form">
              <div class="legend-title">
                  Danh sách gửi Mail
              </div>
              <div class="table-content">
                <DataGrid
                  v-bind:columns="columns"
                  v-bind:dataSource="results"
                  :showColumnCheckbox="true"
                  :selectionSettings="selectionOptions"
                  :showFilter="true"
                  :allowPaging="true"
                  :enablePagingServer="false"
                  :allowExcelExport="true"
                  :totalRecords="pageinfo.totalElement"
                  :pageSize="pageinfo.maxSize"
                  :pageIndex="pageinfo.page"
                  :key="keyDSTB"
                  ref="gridDSTB"
                  >
                </DataGrid>
              </div>
          </div>
      </div>
  </div>
  </ejs-dialog>
</template>
<script>
import Vue from "vue"
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import TaoNhacNoAPI from '../../api/TaoDanhSachNhacNo'
import { currency } from '@/filters/currency'

Vue.use(DialogPlugin)

import {
    //DichVuVienThong
  } from "@/const/enums"

 export default Vue.extend({
    components : {
    },
    props: {
      lantao_nn: {
        type: Number
      },
      kycuoc: {
        type: String
      }
    },
    computed : {
    },
    async created () {

    },
    mounted ()
    {
    },
    destroyed () {
    },
    data () {
      return {
        footerSum: function () {
          return  { template : Vue.component('sumTemplate', {
              template: `<span>{{data.Sum}}</span>`,
              data () {return { data: {}};}
              })
            }
        },
        footerCount: function () {
          return  { template : Vue.component('countTemplate', {
              template: `<span>{{data.Count}}</span>`,
              data () {return { data: {}};}
              })
            }
        },
        sumTemplate: function() {
          return { template: Vue.component('sumTemplate', {
              template: `<input type="text" class="form-control tright" :value="data.Sum" :disabled="true" style="font-weight:bold;margin: 7px 0px 7px 0px;" />`,
              data: function () {return {data: {data: {}}};}
            })
          }
        },
        countTemplate: function() {
          return { template: Vue.component('countTemplate', {
              template: `<input type="text" class="form-control tright" :value="data.Count" :disabled="true" style="font-weight:bold;margin: 7px 0px 7px 0px;" />`,
              data: function () {return {data: {data: {}}};}
            })
          }
        },
        customTemplate: function() {
          return  { template : Vue.component('footerTemplate', {
              template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="font-weight:bold;margin: 7px 0px 7px 0px;" />`,
              data () { return { data: {data: {}}}; }
            })
          }
        },
        config: {
          fieldsMailServer : {text: 'SERVER', value: 'MAILSV_ID' },
          fieldsMessages : {text: 'TEN_MESSAGE', value: 'MESSAGE_ID' },
          fieldsStatus : {text: 'text', value: 'id' },
        },
        tab_index: 1,
        selectionOptions: { type: 'Multiple', checkboxOnly: true},
        columns: [
          {fieldName: 'MA_TB', headerText: 'Số máy/Acc', isPrimaryKey: true, allowFiltering: true},
          {fieldName: 'MA_TT', headerText: 'Mã TT', allowFiltering: true},
          {fieldName: 'LOAIHINH_TB', headerText: 'Loại hình', allowFiltering: true},
          {fieldName: 'TEN_TB', headerText: 'Tên thuê bao', allowFiltering: true},
          {fieldName: 'SOTIENNO', headerText: 'Số tiền', allowFiltering: true, format:'N0', type: 'number'},
          {fieldName: 'EMAIL', headerText: 'Email', allowFiltering: true},
          {fieldName: 'TEN_STATUS', headerText: 'Trạng thái mail', allowFiltering: true},
          {fieldName: 'LOI', headerText: 'Lỗi', allowFiltering: true},
        ],
        sumColumnsKyHD: [
          {field: 'CHUKYNO', type: 'Count', format: 'N0', footerTemplate: this.footerCount},
          {field: 'TONGNO', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
          {field: 'TAMTHU', type: 'Sum', format: 'N0', footerTemplate: this.footerSum}
        ],
        columnsTB: [
          {fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowFiltering: true},
          {fieldName: 'TRANGTHAI_TB', headerText: 'Trạng thái', allowFiltering: true},
          {fieldName: 'THUCNO', headerText: 'Còn nợ', allowFiltering: true, format:'N0', type: 'number'},
        ],
        sumColumnsTB: [
          {field: 'THUCNO', type: 'Sum', format: 'N0', footerTemplate: this.footerSum}
        ],
        params: {
          p_mailserver_options: [],
          p_mailserver: null,
          p_message_options: [],
          p_message: null,
          p_username: null,
          p_password: null,
          p_title: null,
          p_status_options: [
            { id: 1, text :"Tất cả"},
            { id: 2, text :"Gửi thành công"},
            { id: 3, text :"Gửi lỗi"},
            { id: 4, text :"Chưa gửi"}],
          p_status: 1
        },
        results : [],
        selectedRows: [],
        pageinfo : {
          page: 0,
          maxSize: 10,
          totalElement: 0,
          totalPages: 0,
          sort: null,
          propertiesSort: null,
          currentRow: 0
        },
        keyDSTB: 1,
        target: '.main-wrapper',
        animationSettings: { effect: 'Zoom' }
      }
    },
    methods : {
      customAggregate() {
        return currency(this.selectedTotal) + '/' + currency(this.total);
      },
      async getDSMailServer() {
        try{
          const results = await TaoNhacNoAPI.getDSMailServer(this.axios, null)
          return results
        }catch(err) {
          //console.log(err)
          this.$toast.error(err.data.message_detail)
        }
      },
      async getDSMessage(id) {
        try{
          const results = await TaoNhacNoAPI.getDSMessage(this.axios, id)
          return results
        }catch(err) {
          //console.log(err)
          this.$toast.error(err.data.message_detail)
        }
      },
      async getDSNhacNoEMail() {
        this.loading(true)
        try{
          let postData = {
            kyCuoc: this.kycuoc,
            lanTaoNNId: this.lantao_nn,
            status: this.params.p_status
          }
          const results = await TaoNhacNoAPI.getDSNhacNoEMail(this.axios, postData)
          return results
        }catch(err) {
          //console.log(err)
          this.$toast.error(err.data.message_detail)
        } finally
        {
          this.loading(false)
        }
      },
      async getDanhSach(){
        this.results = []
        this.pageinfo.page = 0
        this.pageinfo.totalElement = 0
        
        let res = await this.getDSNhacNoEMail()
        if(res.data.error_code ==='BSS-00000000'
          && res.data.data !== undefined
          && res.data.data.length > 0)
        {
          this.keyDSTB++
          this.results = res.data.data
          this.pageinfo.totalElement = res.data.data.length
        }

      },
      exportExcel(){
        if(this.results.length <= 0)
        {
          this.$toast.error('Không có số liệu để xuất file.')
          return
        }
        let excelExportProperties = {
            dataSource: this.results
        };
        this.$refs.gridDSTB.excelExport(excelExportProperties)
      },
      async sendEmail(){
        let selected = this.$refs.gridDSTB.getSelectedRecords()

        if(selected.length <= 0)
        {
          this.$toast.error('Phải chọn khách hàng để gửi mail thông báo nợ !')
          return
        }



        let ret = this.params.p_mailserver_options.filter(x => x.MAILSV_ID === this.params.p_mailserver)

        if(ret !== null && ret.length > 0)
        {
          this.params.p_port = ret[0].PORT
          this.params.p_username = ret[0].USERNAME
          this.params.p_password = ret[0].PASSWORD
        } else {
          this.$toast.error('Không tìm thấy thông tin mail server.')
          return
        }

        if(this.params.p_title === null || this.params.p_title.trim() === null)
        {
          this.$toast.error('Trường tiêu đề bắt buộc nhập.')
          return
        }

        //let count = 0
        /*
        let promises = []

        selected.forEach((item, index) => {

          let postData = {
            mailServerId: ret[0].MAILSV_ID,
            messageId: this.params.p_message,
            tieuDe: this.params.p_title.trim(),
            to: item.EMAIL,
            lanTaoId: 1,
            thueBaoId: item.THUEBAO_ID
          }

          promises.push(this.send(postData))

        })

        this.loading(true)
        const res = Promise.all(promises)
        this.loading(false)

        this.$toast.success('Đã gửi thành công ' + res.filter(x => x === true).length + '/' + res.length)
        */
         let postData = {
            mailServerId: ret[0].MAILSV_ID,
            messageId: this.params.p_message,
            tieuDe: this.params.p_title.trim(),
            lanTaoId: 1,
            ds: []
          }
         selected.forEach((item, index) => {
           postData.ds.push({
             toMail: item.EMAIL,
             thueBaoId: item.THUEBAO_ID
           })
        })
        const res = await this.send(postData)
        if(res)
          this.$toast.success('Đã gửi thành công')
      },
      async send(postData){
        try
        {
          const res = await await TaoNhacNoAPI.sendEMail(this.axios, postData)
          const data = await res.dta
          if(res.error_code === 'BSS-00000000')
            return true
          else
            return false
        }catch(error){
          this.$toast.error(error.data.message_detail)
          return false
        }
      },
      async openDialog() {
        // load danh sách srever
        let res = await this.getDSMailServer()
        if(res.data.error_code ==='BSS-00000000'
          && res.data.data !== undefined
          && res.data.data.length > 0)
        {
          this.params.p_mailserver_options = res.data.data
          this.params.p_mailserver = res.data.data[0].MAILSV_ID
        }

        res = await this.getDSMessage(null)
        if(res.data.error_code ==='BSS-00000000'
          && res.data.data !== undefined
          && res.data.data.length > 0)
        {
          this.params.p_message_options = res.data.data
          this.params.p_message = res.data.data[0].MESSAGE_ID
        }

        this.$refs.dlgGuiEmail.show()
      },
      closeDialog() {
        this.$refs.dlgGuiEmail.hide()
      },
      onDialogClose() {
      },

      onDialogOpen() {
        this.results = []
      },
      clearForm() {
      },
      clearResult() {
      },
      onChangeServerMail(e){
        if(e !== null)
        {
          let ret = this.params.p_mailserver_options.filter(x => x.MAILSV_ID === e.value)
          if(ret !== null && ret.length > 0)
          {
            this.params.p_port = ret[0].PORT
            this.params.p_username = ret[0].USERNAME
            this.params.p_password = ret[0].PASSWORD
          }
        } else
        {
          this.params.p_port = null
          this.params.p_username = null
          this.params.p_password = null
        }

      }
    },
    watch : {
      /*
      lantao_nn : {
        handler: function(new_value, old_value) {
          this.params.p_lantao_nn_id = new_value
        }
      },
      /*
      kycuoc : {
        handler: function(new_value, old_value) {
          this.params.p_kycuoc = new_value
        }
      },
      */
    }
  })
</script>

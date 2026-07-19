<template>
    <div class="box-form">
        <div class="legend-title">Thông tin khách hàng</div>
        <div class="row">
            <div class="col-sm-6 col-12">
                <div class="row">
                    <div class="col-sm-7 col-12">
                        <div class="info-row">
                            <vue-select
                                v-model="selectedServiceId"
                                :required="false"
                                :labelWidth="'100'"
                                :disable="false"
                                :options="services"
                                :valueField="'DICHVUVT_ID'"
                                :labelField="'TEN_DVVT'"
                                label="Dịch vụ VT"
                                :checkbox="false"
                                v-on:input="onSelectedService"
                            ></vue-select>
                        </div>
                    </div>
                    <div class="col-sm-5 col-12">
                        <div class="info-row">
                            <vue-input
                            :class="{ 'error-input' : isTransactionCodeError}"
                            v-model="transactionCode"
                            :labelWidth="'90'"
                            label="Mã giao dịch"
                            :checkbox="false"
                            @keydown.enter.native="timKiemMaGiaoDich"
                            >
                            </vue-input>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-7 col-12">
                        <div class="info-row">
                            <vue-select
                                v-model="selectedProcedureId"
                                :required="false"
                                :labelWidth="'100'"
                                :disable="false"
                                :options="procedures"
                                :valueField="'quytrinh_id'"
                                :labelField="'quytrinh'"
                                label="Quy trình"
                                :checkbox="false"
                                v-on:input="onSelectedProcedure"
                            ></vue-select>
                        </div>
                    </div>
                    <div class="col-sm-5 col-12">
                        <div class="info-row">
                            <vue-input
                            v-model="ma_tb"
                            :labelWidth="'90'"
                            label="Số máy/Acc"
                            :checkbox="false"
                            >
                            </vue-input>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-7 col-12">
                        <div class="info-row">
                            <vue-select
                                v-model="selectedSubcriberTypeId"
                                :required="false"
                                :labelWidth="'100'"
                                :disable="false"
                                :options="subcriberTypes"
                                :valueField="'LOAITB_ID'"
                                :labelField="'LOAIHINH_TB'"
                                label="Loại hình TB"
                                :checkbox="false"
                                v-on:input="onSelectedSubcriber"
                            ></vue-select>
                        </div>
                    </div>
                    <div class="col-sm-5 col-12">
                        <div class="info-row">
                            <vue-input
                            :class="{ 'error-input' : isWorkTypeError}"
                            v-model="workType"
                            :labelWidth="'90'"
                            label="Kiểu LĐ"
                            :checkbox="false"
                            >
                            </vue-input>
                        </div>
                    </div>
                </div>
                <div class="legend-title mart20">Vật tư trang cấp</div>                
                <DataGrid v-bind:columns="dsVatTu.header" v-bind:dataSource="dsVatTu.list"
                    :enable-paging-server="false" :allowPaging="false" :showFilter="true"
                    ref="gridVatTu" :showColumnCheckbox="false">
                </DataGrid>
                
                <div class="legend-title mart20">Thông tin hoàn công</div>
                <div class="row">
                    <div class="col-sm-7 col-12">
                        <div class="info-row">
                            <vue-date
                            ref="dateTH"
                            label="Ngày TH"
                            v-model="selectedDateTH"
                            :labelWidth="'100'"
                            :required="false"
                            :checkbox="true"
                            ></vue-date>
                        </div>
                    </div>
                    <div class="col-sm-5 col-12">
                        <div class="info-row">
                            <vue-date
                            ref="dateGiveWork"
                            label="Ngày GV"
                            v-model="selectedDateGiveWork"
                            :labelWidth="'90'"
                            :required="false"
                            :checkbox="true"
                            ></vue-date>
                        </div>
                    </div>
                </div>
                <div class="info-row">
                    <vue-select
                        v-model="selectedPersonGiveWorkId"
                        :required="false"
                        :labelWidth="'100'"
                        :disable="false"
                        :options="personGiveWorks"
                        :valueField="'ID'"
                        :labelField="'NAME'"
                        label="Người giao việc"
                        :checkbox="false"
                        v-on:input="onSelectedPersonGiveWork"
                    ></vue-select>
                </div>
                <div class="info-row">
                    <vue-input
                    :class="{ 'error-input' : isContentGiveError}"
                    v-model="contentGive"
                    :labelWidth="'100'"
                    label="Nội dung giao"
                    :checkbox="false"
                    >
                    </vue-input>
                </div>
                <div class="info-row">
                    <vue-input
                    :class="{ 'error-input' : isNoteError}"
                    v-model="note"
                    :labelWidth="'100'"
                    label="Ghi chú"
                    :checkbox="false"
                    >
                    </vue-input>
                </div>
            </div>
            <div class="col-sm-6 col-12">
                <div class="info-row">
                    <vue-input
                    :class="{ 'error-input' : isSubcriberNameError}"
                    v-model="subcriberName"
                    :labelWidth="'90'"
                    label="Tên thuê bao"
                    :checkbox="false"
                    >
                    </vue-input>
                </div>
                <div class="info-row">
                    <vue-input
                    :class="{ 'error-input' : isWorkerAddressError}"
                    v-model="workerAddress"
                    :labelWidth="'90'"
                    label="Địa chỉ LĐ"
                    :checkbox="false"
                    >
                    </vue-input>
                </div>
                <div class="info-row">
                    <vue-input
                    :class="{ 'error-input' : isSubcriberAddressError}"
                    v-model="subcriberAddress"
                    :labelWidth="'90'"
                    label="Địa chỉ TB"
                    :checkbox="false"
                    >
                    </vue-input>
                </div>
                <div class="legend-title mart20">Danh sách nhân viên thực hiện</div>
                <DataGrid
                    ref="grcDanhSach"
                    :columns="table.columns"
                    :dataSource="employees"
                    :enable-paging-server="false"
                    :showColumnCheckbox="false"
                    :allowPaging="true"
                    :loading="true"
                    :showFilter="true"
                    @rowSelected="onSelectedEmployee"
                >
                </DataGrid>
            </div>
        </div>
    </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import api from '../api/index'
import axios from 'axios'
import * as moment from 'moment'
export default {
  data () {
    return {
      services: [],
      selectedServiceId: null,
      selectedProcedureId: null,
      procedures: [],
      ma_tb: null,
      machineNumbers: [],
      selectedSubcriberTypeId: null,
      subcriberTypes: [],
      selectedPersonGiveWorkId: null,
      personGiveWorks: [],
      isTransactionCodeError: false,
      transactionCode: null,
      isWorkTypeError: false,
      workType: null,
      isContentGiveError: false,
      contentGive: null,
      isNoteError: false,
      note: null,
      isSubcriberNameError: false,
      subcriberName: null,
      isWorkerAddressError: false,
      workerAddress: null,
      isSubcriberAddressError: false,
      subcriberAddress: null,
      selectedDateTH: null,
      selectedDateGiveWork: null,
      table: {
        loading: false,
        columns: [{
          fieldName: 'ten_nv',
          headerText: 'Tên nhân viên'
        }, {
          fieldName: 'so_dt',
          headerText: 'Điện thoại'
        }, {
          fieldName: 'nhiemvu',
          headerText: 'Nhiệm vụ'
        }, {
          fieldName: 'ghichu',
          headerText: 'Ghi chú'
        }]
      },
      employees: [],
      dsVatTu: {
          list: [
          ],
          header: [ 
              {fieldName: 'loai_tbi', headerText: 'Tên thiết bị', allowFiltering: true}, 
              {fieldName: 'soluong', headerText: 'Số lượng', allowFiltering: true}, 
          ],
          value: {}
      },
    }
  },
  props: ['inputServices', 'inputSubcriberTypes', 'inputProcedures', 'inputPersonGiveWorks', 'inputEmployees', 'inputIsNewCard', 'inputSelectedSubcriber', 'dsloaihd_id', 'tthd_id'],
  watch: {
    inputServices: function (newValue, oldValue) {
      this.services = newValue
    },
    inputProcedures: function () {
      this.procedures = this.inputProcedures
      this.selectedProcedureId = this.procedures.length > 0 ? this.procedures[0].quytrinh_id : 0
    },
    inputPersonGiveWorks: function () {
      this.personGiveWorks = this.inputPersonGiveWorks
      this.selectedPersonGiveWorkId = this.$root.token.getNhanVienID()
    },
    selectedPersonGiveWorkId: function () {
      this.setSelectedPersonGiveWorkId(this.selectedPersonGiveWorkId)
    },
    inputEmployees: function () {
      this.employees = this.inputEmployees
    },
    getIsNewCard: async function () {
      this.$root.showLoading(true)
      try {
        await this.loadChangedWireSubcriberList()
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    inputSelectedSubcriber: async function () {
      console.log(this.inputSelectedSubcriber)
      console.log('this.inputSelectedSubcriber')
      this.subcriberName = this.inputSelectedSubcriber.ten_tb
      this.workerAddress = this.inputSelectedSubcriber.diachi_ld
      this.subcriberAddress = this.inputSelectedSubcriber.diachi_tb
      this.transactionCode = this.inputSelectedSubcriber.ma_gd
      this.selectedServiceId = this.inputSelectedSubcriber.dichvuvt_id
      this.selectedSubcriberTypeId = this.inputSelectedSubcriber.loaitb_id
      this.ma_tb = this.inputSelectedSubcriber.ma_tb
      this.workType = this.inputSelectedSubcriber.ten_kieuld
      this.contentGive = this.inputSelectedSubcriber.nd_giao
      this.note = this.inputSelectedSubcriber.ghichu_tb
      this.selectedDateGiveWork = this.inputSelectedSubcriber.ngaygiao
      this.selectedDateTH = moment(this.inputSelectedSubcriber.ngay_ht).format('DD/MM/YYYY HH:mm:ss')

      await this.loadDSVatTu(this.inputSelectedSubcriber.hdkh_id,this.inputSelectedSubcriber.hdtb_id)
    },
    selectedDateGiveWork: function () {
      this.setSelectedDateGiveWork(this.selectedDateGiveWork)
    },
    selectedProcedureId: async function () {
      await this.onSelectedProcedure(this.selectedProcedureId)
    },
    services: function (newValue, oldValue) {
      this.selectedServiceId = newValue.length > 0 ? newValue[0].DICHVUVT_ID : 0
      this.onSelectedService(this.selectedServiceId)
    },
    getSubcriberTypes: function (newValue, oldValue) {
      this.subcriberTypes = newValue.filter(i => i.DICHVUVT_ID.toString() === this.selectedServiceId.toString())
      this.selectedSubcriberTypeId = this.subcriberTypes.length > 0 ? this.subcriberTypes[0].LOAITB_ID : 0
    },    
  },
  computed: {
    ...mapGetters('installReplaceRecallWifiCommon', [
      'getIsNewCard',
      'getDirections',
      'getSubcriberTypes'
    ])
  },
  methods: {
    ...mapActions('installReplaceRecallWifiCommon', [
      'loadProcedures',
      'loadChangeWireSubcribers',
      'loadDirectionList',
      'setSelectedPersonGiveWorkId',
      'setDirections',
      'setSelectedDateGiveWork'
    ]),
    onSelectedService: async function (e) {
      this.$root.showLoading(true)
      try {
        this.filterSubcriberTypes()

        await this.loadProcedures({
          dichvuvt_id: this.selectedServiceId === null ? 0 : this.selectedServiceId,
          dsloaihd_id: this.dsloaihd_id,
          kieu: 0,
          phanvung_id: this.$root.token.getPhanVungID(),
          tthd_id: this.tthd_id
        })

        await this.loadChangedWireSubcriberList()
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    filterSubcriberTypes: function () {
      this.subcriberTypes = this.getSubcriberTypes.filter(i => i.DICHVUVT_ID.toString() === this.selectedServiceId.toString())
      this.selectedSubcriberTypeId = this.subcriberTypes.length > 0 ? this.subcriberTypes[0].LOAITB_ID : 0
    },
    onSelectedProcedure: async function (e) {
      this.$root.showLoading(true)
      try {
        if (e) {
          const resDir = await api.getDirectionList(axios, {
            quytrinh_id: e,
            tthd_id: this.tthd_id
          })

          this.setDirections(resDir.data.data)

          const resDataControls = await api.getDataControls(axios, {
            ID: resDir.data.data.length > 0 ? resDir.data.data[0].luong_id : 0
          })

          this.$emit('inputDataControls', resDataControls.data.data)

          if (resDir.data.data.length < 0) {
            this.$toast.error('Không tìm thấy luồng quy trình nghiệp vụ này. Bạn hãy liên hệ Admin kiểm tra lại')
          }
        }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    loadChangedWireSubcriberList: async function () {
      let loaiId = 0
      if (!this.getIsNewCard) {
        loaiId = 1
      }

      await this.loadChangeWireSubcribers({
        vdichvuvt_id: this.selectedServiceId === null ? 0 : this.selectedServiceId,
        vhuonggiao_id: this.getDirections.length > 0 ? this.getDirections[0].huonggiao_id : 0,
        vkieu_id: 0,
        vloai_id: loaiId,
        vma_gd: '', // this.transactionCode === null ? 0 : this.transactionCode,
        vnhanvien_id: this.$root.token.getNhanVienID(),
        vtthd_id: this.tthd_id
      })
    },
    onSelectedEmployee: function (e) {

    },
    onSelectedMachineNumber: function (e) {

    },
    onSelectedSubcriber: function (e) {

    },
    onSelectedPersonGiveWork: function (e) {

    },
    timKiemMaGiaoDich() {
      this.$emit('timKiemMaGiaoDich', this.transactionCode)
    },
    async loadDSVatTu(vhdkh_id, vhdtb_id){        
      try{
          let params = {
              "vphanvung_id": this.$root.token.getPhanVungID(),
              "vhdkh_id": vhdkh_id,
              "vhdtb_id": vhdtb_id, 
              "vloai":"2"
          }
          console.log('params')
          console.log(params)
          await api.lay_phuluc_vattu_capmienphi(this.axios, params).then(response => {
              
              if (response && response.data && response.data.error_code === 'BSS-00000000') {
                  this.dsVatTu.list = response.data.data.rs1
              }
              else{
                  this.$toast.error(response.data.message)
              }
          })
      } catch(e){
          this.$toast.error(e.data.message)            
      }
    },
  },
  mounted: async function () {
    this.selectedDateTH = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
    this.selectedDateGiveWork = this.selectedDateTH    
    this.$emit('finishedMounted', true)
  }
}
</script>

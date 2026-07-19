<template>
  <div class="box-form">
    <div class="legend-title">Thông tin kho</div>
    <div class="info-row">
      <div class="key w120">Mã kho <span class="required">*</span></div>
      <div class="value">
        <div class="row">
          <div class="col-sm-2 -store-code-custom">
            <input
              :disabled="true"
              v-model="prefixStoreCode"
              type="text"
              class="form-control"
            />
          </div>
          <div class="col-sm-10 -store-code-custom">
            <div class="info-row">
              <vue-input
                :class="{ 'error-input': isStoreCodeError }"
                id="input-store-code"
                v-model="storeCode"
                :labelWidth="'0'"
                ref="storeCode"
                :checkbox="false"
              >
              </vue-input>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="info-row">
      <vue-input
        :class="{ 'error-input': isStoreNameError }"
        v-model="storeName"
        :labelWidth="'120'"
        ref="storeName"
        label="Tên kho"
        :required="true"
        :checkbox="false"
      >
      </vue-input>
    </div>
    <div class="info-row">
      <vue-select
        :class="{ 'error-input': isStoreTypeError }"
        v-model="selectedStoreTypeId"
        :required="true"
        :labelWidth="'120'"
        :disable="false"
        :options="getStoreTypes"
        :valueField="'LOAIKHO_ID'"
        :labelField="'LOAI_KHO_NAME'"
        id="store-type-selected"
        label="Loại kho"
        :checkbox="false"
        v-on:input="onSelectedStoreType"
      ></vue-select>
    </div>
    <div class="info-row">
      <vue-select
        :class="{ 'error-input': isParentStoreError }"
        v-model="selectedParentStoreId"
        :required="true"
        :labelWidth="'120'"
        :disable="false"
        :options="dsKhoCha"
        :valueField="'kho_id'"
        :labelField="'ma_kho_ten_kho'"
        id="parent-store-selected"
        label="Kho cha"
        :checkbox="false"
        v-on:input="onSelectedParentStore"
      ></vue-select>
    </div>
<!--    <div class="info-row">-->
<!--      <vue-select-->
<!--        :class="{ 'error-input': isDepartmentError }"-->
<!--        v-model="selectedDepartmentId"-->
<!--        :required="true"-->
<!--        :labelWidth="'120'"-->
<!--        :disable="false"-->
<!--        :options="getDepartmentsCustomName"-->
<!--        :valueField="'DONVI_ID'"-->
<!--        :labelField="'TEN_DV'"-->
<!--        id="department-selected"-->
<!--        label="Đơn vị chủ quản"-->
<!--        @search="query => search = query"-->
<!--        :checkbox="false"-->
<!--        v-on:input="onSelectedDepartment"-->
<!--      ></vue-select>-->

<!--    </div>-->
    <div class="info-row">
      <div class="key w120">
        Đơn vị chủ quản
      </div>
      <div class="value">
        <div class="select-custom">
          <Select2Ext :options='options'
                      v-model='selectedDepartmentId'
                      :url='url'
                      :dataValueField ="'donvi_id'"
                      :dataTextField = "'ma_dv+ten_dv+ten_dv_cha'"
                      v-on:input="onSelectedDepartment"
          />
        </div>
      </div>
      </div>
    <div class="info-row">
      <div class="key w120">Số thứ tự</div>
      <div class="value">
        <div class="row">
          <div class="col-sm-2 -p-custom">
            <vue-input
              v-model="ordinalNumber"
              :labelWidth="'0'"
              ref="ordinalNumber"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-10">
            <div class="info-row">
              <vue-select
                v-model="selectedEmployeeId"
                :required="false"
                :labelWidth="'110'"
                :disable="false"
                :options="dsThuKho"
                :valueField="'NHANVIEN_ID'"
                :labelField="'MA_TEN'"
                id="employee-selected"
                label="Thủ kho"
                :checkbox="false"
                v-on:input="onSelectedEmployee"
              ></vue-select>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="info-row">
      <div class="key w120">Số ĐT</div>
      <div class="value">
        <div class="input-more-button">
          <button class="btn btn-second">
            84
          </button>
          <!-- <input v-model="phone" type="text" class="form-control "> -->
          <vue-input
            v-model="phone"
            :labelWidth="'0'"
            ref="phone"
            :checkbox="false"
          >
          </vue-input>
        </div>
      </div>
    </div>
    <div class="info-row">
      <div class="key w120"></div>
      <div class="value">
        <div class="list-checks">
          <div class="item">
            <div class="check-action">
              <input v-model="status" type="checkbox" class="check" />
              <span class="name">Hoạt động</span>
            </div>
          </div>
          <div class="item">
            <div class="check-action">
              <input v-model="isUseInvoice" type="checkbox" class="check" />
              <span class="name">Dùng cho chứng từ</span>
            </div>
          </div>
          <div class="item">
            <div class="check-action">
              <input
                v-model="isManageInventory"
                type="checkbox"
                class="check"
              />
              <span class="name">Quản lý tồn</span>
            </div>
          </div>
          <div class="item">
            <div class="check-action">
              <input v-model="isSync" type="checkbox" class="check" />
              <span class="name">Đồng bộ</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="info-row">
      <vue-input
        v-model="storeAccount"
        :labelWidth="'120'"
        ref="storeAccount"
        label="Tài khoản kho"
        :checkbox="false"
      >
      </vue-input>
    </div>
    <div class="info-row">
      <div class="key w120">Mã CSHT</div>
      <div class="input-more-button -right">
        <button class="btn btn-main" @click="clearCSHT">
          <span class="-ap icon-close"></span>
        </button>
        <div class="select-custom">
          <div @click="toggleCshtPopup">
            <vue-input v-model="codeCSHT"></vue-input>
          </div>
          <div v-show="isShowCshtPopup" class="box-form csht-popup">
            <div class="info-row">
              <vue-input
                v-model="searchCodeCSHT"
                :labelWidth="'100'"
                label="Tìm mã CSHT"
                @keydown.enter.native="txtMaCSHT_KeyPress"
              ></vue-input>
            </div>
            <div class="info-row">
              <vue-input
                v-model="searchNameCSHT"
                :labelWidth="'100'"
                label="Tìm tên CSHT"
                @keydown.enter.native="txtTenCSHT_KeyPress"
              ></vue-input>
            </div>
            <div class="info-row">
              <div class="input-container" style="position: relative;">
                <button
                  class="btn btn-main"
                  style="float: right; background: var(--bs-blue); color: white;"
                  @click="searchCSHT"
                >
                  Tìm kiếm
                </button>
              </div>
            </div>
            <div class="info-row">
              <grid-view
                class="-table-CSHT"
                :datalist="CSHT"
                :checkheader="false"
                :headers="configCSHT"
                @recordChange="recordChangeCSHT"
              ></grid-view>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="info-row">
      <!-- <div class="key">Mã ĐV Fmis</div>
            <div class="value">
                <input type="text" class="form-control ">
            </div> -->
      <vue-input
        v-model="codeFmis"
        :labelWidth="'120'"
        label="Mã ĐV Fmis"
        :checkbox="false"
      >
      </vue-input>
    </div>
    <div class="info-row">
      <div class="key w120">Ghi chú</div>
      <div class="value">
        <textarea
          v-model="note"
          name=""
          class="form-control"
          style="height: 100px;resize: none;"
        ></textarea>
      </div>
    </div>
    <div v-if="isShowCshtPopup" class="outside" v-on:click="away"></div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex"
import gridView from "@/components/Shared/gridview.vue"
import api from '../api/index'
import axios from 'axios'
import ComboboxGrid from '@/components/Controls/ComboboxGrid'
import Select2Ext from '../components/Select2Ext'
export default {
  components: {
    gridView,ComboboxGrid,Select2Ext
  },
  data () {
    return {
      selectChuQuan: null,
      getDepartmentsCustomName:[],
      options: [],
      url:"https://api-onebss.vnpt.vn/web-quantri/danhmuc-chung/ADMIN_DONVI_V3",

      selectedStoreTypeId: null,
      selectedDepartmentId: null,
      selectedParentStoreId: null,
      dsKhoCha: [],
      selectedEmployeeId: null,
      dsThuKho: [],
      prefixStoreCode: "",
      storeCode: "",
      storeName: "",
      ordinalNumber: "",
      codeFmis:"",
      phone: "",
      status: null,
      isUseInvoice: null,
      isManageInventory: null,
      isSync: null,
      storeAccount: null,
      codeCSHT: null,
      tagFrm: "",
      note: "",
      isShowCshtPopup: false,
      isStoreCodeError: false,
      isStoreNameError: false,
      isStoreTypeError: false,
      isDepartmentError: false,
      isParentStoreError: false,
      CSHT: [],
      configCSHT: [
        {
          name: "stt",
          text: "STT"
        },
        {
          name: "ma_csht",
          text: "Mã CSHT"
        },
        {
          name: "ten_csht",
          text: "Tên CSHT"
        }
      ],
      searchNameCSHT: "",
      searchCodeCSHT: ""
    }
  },
  props: {
    storeInfo: Object,
    isEnableAddNew: Boolean,
    CSHTList: Array
  },
  computed: {
    ...mapGetters("storesCommon", [
      "getStoreTypes",
      "getStores",
      "getDepartments",
      // "getDepartmentsCustomName",
      "getDepartmentById",
      "getSelectedStore"
    ]),
    ...mapGetters("employeesCommon", ["getEmployees"])
  },
  watch: {
    selectChuQuan: function(){
      this.selectedDepartmentId = this.selectChuQuan;
    },
    storeInfo: async function () {
      console.log('storeInfo')
      console.log(this.storeInfo)
      this.prefixStoreCode = this.storeInfo.prefixStoreCode
      this.storeCode = this.storeInfo.storeCode
      this.storeName = this.storeInfo.storeName
      this.selectedStoreTypeId = this.storeInfo.selectedStoreTypeId
      this.selectedParentStoreId = this.storeInfo.selectedParentStoreId
      this.selectedDepartmentId = this.storeInfo.selectedDepartmentId

      // await this.onSelectedDepartment(this.selectedDepartmentId, this.storeInfo.selectedEmployeeId)

      this.ordinalNumber = this.storeInfo.ordinalNumber
      this.codeFmis = this.storeInfo.codeFmis;
      this.selectedEmployeeId = this.storeInfo.selectedEmployeeId
      this.phone = this.storeInfo.phone
      this.status = this.storeInfo.status
      this.isUseInvoice = this.storeInfo.isUseInvoice
      this.isManageInventory = this.storeInfo.isManageInventory
      this.isSync = this.storeInfo.isSync
      this.codeCSHT = this.storeInfo.codeCSHT
      this.storeAccount = this.storeInfo.storeAccount
      this.note = this.storeInfo.note
    },
    getSelectedStore: async function (newValue, oldeValue) {
      console.log('getSelectedStore')
      console.log(newValue)
      console.log('selected store 0000000')
      const storeCodeArr = newValue.ma_kho.split(".")
      let vma_kho = newValue.ma_kho
      this.loading(true)
      // tiền tố mã kho
      let tiento = ''
      const selectedStoreType = this.getStoreTypes.find(
        i => i.LOAIKHO_ID === newValue.loaikho_id && i.PREFIX === 1
      )

      if (selectedStoreType) {
        this.prefixStoreCode = selectedStoreType.MA_LOAI

        if (storeCodeArr.length > 1) {
          if (storeCodeArr[0] !== tiento) {
            tiento = storeCodeArr[0]
          }

          vma_kho = ''
          for (let i = 1; i < storeCodeArr.length; i++) {
            if (i === 0 || storeCodeArr[i].toString() === '') { continue }
            vma_kho += storeCodeArr[i] + '.'
          }
          vma_kho = vma_kho.replace(/\.$/, '')
        }
      }
      this.prefixStoreCode = tiento
      console.log('prefixStoreCode', this.prefixStoreCode)

      this.storeCode = vma_kho

      this.storeName = newValue.ten_kho
      this.selectedStoreTypeId = newValue.loaikho_id
      this.selectedParentStoreId = newValue.kho_cha_id
      this.selectedDepartmentId = newValue.donvi_id
      //fix toi uu
      this.selectedEmployeeId = newValue.thukho_id
      console.log('selected store 0000001')
      await this.onSelectedDepartment(this.selectedDepartmentId, newValue.thukho_id)
      this.ordinalNumber = newValue.thutu
      // this.selectedEmployeeId = newValue.thukho_id
      this.phone = newValue.so_dt
      this.status = newValue.trangthai
      this.isUseInvoice = newValue.chungtu
      this.isManageInventory = newValue.tonkho
      this.isSync = newValue.dongbo
      this.storeAccount = newValue.ma_tk
      this.note = newValue.ghichu

      this.dsKhoCha = this.getStores.filter(i => i.kho_id !== newValue.kho_id).map(m => {
        m.ma_kho_ten_kho = `${m.ma_kho} - ${m.ten_kho}`
        return m
      })

      // get CSHT by store
      this.loading(true)
      const resGetCSHTByStore = await api.getCSHTByStore(axios, {
        kho_id: newValue.kho_id
      })
      console.log('selected store 0000002')
      if (resGetCSHTByStore.data && resGetCSHTByStore.data.data && resGetCSHTByStore.data.data.length > 0) {
        this.codeCSHT = resGetCSHTByStore.data.data[0].ma_csht
        this.codeFmis = resGetCSHTByStore.data.data[0].ma_dv_fmis
        this.$emit('onSelect', newValue)
      }
      //todo test: move from store  list


      //this.loading(false)
    },
    selectedDepartmentId: async function () {
      this.emitData()
    },
    selectedStoreTypeId: function (newValue, oldValue) {
      this.emitData()
      this.setSelectedStoreTypeId(newValue)
      this.onSelectedStoreType(newValue)
    },
    isManageInventory: function () {
      this.emitData()
    },
    status: function (newValue, oldValue) {
      this.emitData()
    },
    storeCode: function () {
      this.emitData()
    },
    prefixStoreCode: function () {
      this.emitData()
    },
    storeName: function () {
      this.emitData()
    },
    ordinalNumber: function () {
      this.emitData()
    },
    codeFims: function () {
      this.emitData()
    },
    isSync: function () {
      this.emitData()
    },
    isUseInvoice: function () {
      this.emitData()
    },
    selectedParentStoreId: function () {
      this.emitData()
    },
    CSHTList: function (newValue, oldValue) {
      this.CSHT = newValue
    },
    codeCSHT: function (newValue, oldValue) {
      this.emitData()
    },
    codeFmis: function (newValue, oldValue) {
      this.emitData()
    },
    note: function (newValue, oldValue) {
      this.emitData()
    },
    getStores: function (newValue, oldeValue) {
      this.dsKhoCha = newValue.map(m => {
        m.ma_kho_ten_kho = `${m.ma_kho} - ${m.ten_kho}`
        return m
      })
    },
    getEmployees: function (newValue, oldValue) {
      this.dsThuKho = newValue.map(i => {
        i.MA_TEN = i.ten_nv//`${i.MA_NV} - ${i.TEN_NV}`
        i.NHANVIEN_ID = i.nhanvien_id
        return i
      })
    },
    selectedEmployeeId: function (newValue, oldValue) {
      this.emitData()
    }
  },
  methods: {
    ...mapActions("storesCommon", [
      "loadStoreTypes",
      "loadDepartments",
      "setSelectedDepartment",
      "setSelectedStoreTypeId",
      "loadDepartmentsNotAdded",
      "loadDepartmentsAdded",
      "loadCSHT",
      "setSelectedCSHT",
      "setStoresBelongAccount"
    ]),
    ...mapActions("employeesCommon", [
      "loadEmployees",
      "loadEmployeesNotAdded",
      "loadEmployeesAdded"
    ]),
    onSelectedStoreType: function (id) {
      this.prefixStoreCode = ''

      const selectedStoreType = this.getStoreTypes.find(
        i => i.LOAIKHO_ID === id && i.PREFIX === 1
      )

      if (selectedStoreType) {
        this.prefixStoreCode = selectedStoreType.MA_LOAI
      }
    },
    loadDepartmentsData: async function (selectedDepartment) {
      try {
        let kho_id = -1
        if (this.getSelectedStore && this.getSelectedStore.kho_id) kho_id = this.getSelectedStore.kho_id

        await this.loadDepartmentsNotAdded({
          kho_id: kho_id
        })

        await this.loadDepartmentsAdded({
          kho_id: kho_id
          // phanvung_id: selectedDepartment.PHANVUNG_ID
        })
      } catch (error) {
        console.log(error)
        this.$toast.error(error.data.message_detail)
      }
    },
    onSelectedDepartment: async function (id, selectedEmployeeId) {
      console.log('selectedEmployeeId', selectedEmployeeId, id)
      console.log('selected store 0000003')
      //this.$root.showLoading(true)
      try {
        const selectedDepartment = this.getDepartmentById(id)
        await this.setSelectedDepartment(selectedDepartment)
        if (selectedDepartment) {
          await this.loadEmployees({
            donvi_id: selectedDepartment.DONVI_ID,
            kho_id:this.getSelectedStore.kho_id,
            phanvung_id: selectedDepartment.PHANVUNG_ID
          })

          if (selectedEmployeeId) { this.selectedEmployeeId = selectedEmployeeId } else {
            this.selectedEmployeeId = null
          }

          const res = await api.getStoresBelongAccount(axios, {
            nhanvien_id: this.$root.token.getNhanVienID(),
            phanvung_id:
              selectedDepartment === null || selectedDepartment === undefined
                ? 0
                : selectedDepartment.PHANVUNG_ID
          })

          if (res.data && res.data.data && res.data.data.length > 0) {
            this.setStoresBelongAccount(res.data.data)
          }

          if (this.isEnableAddNew) {
            await this.loadDepartmentsData(selectedDepartment)
          }

          this.$emit('selectedDepartment')
        }
      } catch (error) {
        console.log(error)
        this.$root.showLoading(false)
        if (error.data && error.data.message_detail) {
          this.$toast.error(error.data.message_detail)
        } else this.$toast.error(error.toString())

      }
      console.log('selected store 0000004')
      //this.$root.showLoading(false)
    },
    onSelectedParentStore: function (id) {},
    onSelectedEmployee: function (id) {},
    emitData: function () {
      this.$emit("input", {
        selectedStoreTypeId: this.selectedStoreTypeId,
        selectedDepartmentId: this.selectedDepartmentId,
        selectedParentStoreId: this.selectedParentStoreId,
        selectedEmployeeId: this.selectedEmployeeId,
        prefixStoreCode: this.prefixStoreCode,
        storeCode:
          this.storeCode === null || this.storeCode === undefined
            ? ""
            : this.storeCode.trim(),
        storeName:
          this.storeName === null || this.storeName === undefined
            ? ""
            : this.storeName.trim(),
        ordinalNumber: this.ordinalNumber,
        phone:
          this.phone === null || this.phone === undefined
            ? ""
            : this.phone.toString().trim(),
        status: this.status ? 1 : 0,
        isUseInvoice: this.isUseInvoice ? 1 : 0,
        isManageInventory: this.isManageInventory ? 1 : 0,
        isSync: this.isSync ? 1 : 0,
        storeAccount: this.storeAccount,
        tagFrm:
          this.tagFrm === null || this.tagFrm === undefined
            ? ""
            : this.tagFrm.trim(),
        note:
          this.note === null || this.note === undefined ? "" : this.note.trim(),
        codeCSHT: this.codeCSHT,
        codeFmis:this.codeFmis
      })
    },
    toggleCshtPopup: function () {
      this.isShowCshtPopup = !this.isShowCshtPopup
    },
    away: function () {
      this.isShowCshtPopup = false
    },
    recordChangeCSHT: function (data) {
      this.setSelectedCSHT(data)
      // this.isShowCshtPopup = false
      this.codeCSHT = data !== undefined && data !== null ? data.ma_csht : ""
    },
    clearCSHT: function () {
      this.codeCSHT = null
      this.setSelectedCSHT({})
    },
    txtMaCSHT_KeyPress: function (e) {
      this.searchCSHT()
    },
    txtTenCSHT_KeyPress: function (e) {
      this.searchCSHT()
    },
    searchCSHT: async function () {
      this.$root.showLoading(true)
      try {
        const searchCode =
          this.searchCodeCSHT.length === 0 ? "" : `['${this.searchCodeCSHT}']`
        await this.loadCSHT({
          ma_tinh: `["${this.$root.token.getMaTinh()}"]`,
          ma_csht: searchCode,
          name: this.searchNameCSHT,
          rims_status: null,
          page_index: null,
          page_size: null
        })
      } catch (error) {
        console.log(error.toString())
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    }
  },
  mounted: async function () {
    this.$root.showLoading(true)
    try {
      console.log('created')
      await this.loadStoreTypes()
      //await this.loadDepartments()
      this.selectedDepartmentId =
        this.getDepartments.length > 0 ? this.getDepartments[0].DONVI_ID : 0
      await this.onSelectedDepartment(this.selectedDepartmentId)
      await this.loadCSHT({
        ma_tinh: `["${this.$root.token.getMaTinh()}"]`,
        ma_csht: this.searchCodeCSHT,
        name: this.searchNameCSHT,
        rims_status: null,
        page_index: null,
        page_size: null
      })

      this.$parent.setButton(0)
    } catch (error) {
      console.log(error)
      this.$toast.error(error.toString())
    }
    this.$root.showLoading(false)
  }
}
</script>

<style scoped>
.-p-custom {
  padding: 0 3px;
}
.-store-code-custom {
  padding: 0 7px;
}

.csht-popup {
  position: absolute;
  left: 5px;
  z-index: 2;
}

.outside {
  width: 100vw;
  height: 100vh;
  position: fixed;
  top: 0px;
  left: 0px;
  background: #e9ecef;
  opacity: 0.5;
  z-index: 1;
}

.-table-CSHT {
}
</style>

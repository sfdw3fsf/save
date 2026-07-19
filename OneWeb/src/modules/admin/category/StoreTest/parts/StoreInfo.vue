<template>
    <div class="box-form">
        <div class="legend-title">Thông tin kho</div>
        <div class="info-row">
            <div class="key">Mã kho</div>
            <div class="value">
                <div class="row">
                    <div class="col-sm-2 -store-code-custom">
                        <input :disabled="true" v-model="prefixStoreCode" type="text" class="form-control">
                    </div>
                    <div class="col-sm-10 -store-code-custom">
                        <div class="info-row">
                            <vue-input
                            :class="{ 'error-input' : isStoreCodeError }"
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
              :class="{ 'error-input' : isStoreNameError}"
              v-model="storeName"
              :labelWidth="'110'"
              ref="storeName"
              label="Tên kho"
              :checkbox="false"
            >
            </vue-input>
        </div>
        <div class="info-row">
            <vue-select
              :class="{ 'error-input' : isStoreTypeError }"
                v-model="selectedStoreTypeId"
                :required="false"
                :labelWidth="'110'"
                :disable="false"
                :options="getStoreTypes"
                :valueField="'LOAIKHO_ID'"
                :labelField="'LOAI_KHO'"
                id="store-type-selected"
                label="Loại kho"
                :checkbox="false"
                v-on:input="onSelectedStoreType"
            ></vue-select>
        </div>
        <div class="info-row">
            <vue-select
              :class="{ 'error-input' : isParentStoreError }"
              v-model="selectedParentStoreId"
              :required="false"
              :labelWidth="'110'"
              :disable="false"
              :options="getStores"
              :valueField="'kho_id'"
              :labelField="'ten_kho'"
              id="parent-store-selected"
              label="Kho cha"
              :checkbox="false"
              v-on:input="onSelectedParentStore"
            ></vue-select>
        </div>
        <div class="info-row">
            <vue-select
              :class="{ 'error-input' : isDepartmentError }"
              v-model="selectedDepartmentId"
              :required="false"
              :labelWidth="'110'"
              :disable="false"
              :options="getDepartmentsCustomName"
              :valueField="'DONVI_ID'"
              :labelField="'TEN_DV'"
              id="department-selected"
              label="Đơn vị chủ quản"
              :checkbox="false"
              v-on:input="onSelectedDepartment"
            ></vue-select>
        </div>
        <div class="info-row">
            <div class="key">Số thứ tự</div>
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
                                :options="getEmployees"
                                :valueField="'NHANVIEN_ID'"
                                :labelField="'TEN_NV'"
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
            <div class="key">Số ĐT</div>
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
            <div class="key"></div>
            <div class="value">
                <div class="list-checks">
                    <div class="item">
                        <div class="check-action">
                            <input v-model="status" type="checkbox" class="check">
                            <span class="name">Hoạt động</span>
                        </div>
                    </div>
                    <div class="item">
                        <div class="check-action">
                            <input v-model="isUseInvoice" type="checkbox" class="check">
                            <span class="name">Dùng cho chứng từ</span>
                        </div>
                    </div>
                    <div class="item">
                        <div class="check-action">
                            <input v-model="isManageInventory" type="checkbox" class="check">
                            <span class="name">Quản lý tồn</span>
                        </div>
                    </div>
                    <div class="item">
                        <div class="check-action">
                            <input v-model="isSync" type="checkbox" class="check">
                            <span class="name">Đồng bộ</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="info-row">
            <!-- <div class="key">Tài khoản kho</div>
            <div class="value">
                <input v-model="storeAccount" type="text" class="form-control ">
            </div> -->
            <vue-input
                v-model="storeAccount"
                :labelWidth="'110'"
                ref="storeAccount"
                label="Tài khoản kho"
                :checkbox="false"
            >
            </vue-input>
        </div>
        <div class="info-row">
            <div class="key">Mã CSHT</div>
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
                        label="Tìm mã CSHT"></vue-input>
                    </div>
                    <div class="info-row">
                      <vue-input
                        v-model="searchNameCSHT"
                        :labelWidth="'100'"
                        label="Tìm tên CSHT"></vue-input>
                    </div>
                    <div class="info-row">
                      <div class="input-container" style="position: relative;">
                        <button class="btn btn-main"
                        style="float: right; background: var(--bs-blue); color: white;"
                        @click="searchCSHT">
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
                      @recordChange="recordChangeCSHT"></grid-view>
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
                v-model="tagFrm"
                :labelWidth="'110'"
                label="Mã ĐV Fmis"
                :checkbox="false"
            >
            </vue-input>
        </div>
        <div class="info-row">
            <div class="key">Ghi chú</div>
            <div class="value">
                <textarea v-model="note" name="" class="form-control" style="height: 100px;resize: none;"></textarea>
            </div>
        </div>
        <div v-if="isShowCshtPopup" class="outside" v-on:click="away"></div>
    </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import gridView from '@/components/Shared/gridview.vue'
export default {
  components: {
    gridView
  },
  data () {
    return {
      selectedStoreTypeId: null,
      selectedDepartmentId: null,
      selectedParentStoreId: null,
      selectedEmployeeId: null,
      prefixStoreCode: '',
      storeCode: '',
      storeName: '',
      ordinalNumber: '',
      phone: '',
      status: null,
      isUseInvoice: null,
      isManageInventory: null,
      isSync: null,
      storeAccount: null,
      codeCSHT: null,
      tagFrm: '',
      note: '',
      isShowCshtPopup: false,
      isStoreCodeError: false,
      isStoreNameError: false,
      isStoreTypeError: false,
      isDepartmentError: false,
      isParentStoreError: false,
      CSHT: [],
      configCSHT: [{
        name: 'stt',
        text: 'STT'
      }, {
        name: 'ma_csht',
        text: 'Mã CSHT'
      }, {
        name: 'ten_csht',
        text: 'Tên CSHT'
      }],
      searchNameCSHT: '',
      searchCodeCSHT: ''
    }
  },
  props: {
    storeInfo: Object,
    isEnableAddNew: Boolean,
    CSHTList: Array
  },
  computed: {
    ...mapGetters('storesCommon', [
      'getStoreTypes',
      'getStores',
      'getDepartments',
      'getDepartmentsCustomName',
      'getDepartmentById',
      'getSelectedStore',
      'getAccountId'
    ]),
    ...mapGetters('employeesCommon', [
      'getEmployees'
    ])
  },
  watch: {
    storeInfo: function () {
      this.prefixStoreCode = this.storeInfo.prefixStoreCode
      this.storeCode = this.storeInfo.storeCode
      this.storeName = this.storeInfo.storeName
      this.selectedStoreTypeId = this.storeInfo.selectedStoreTypeId
      this.selectedParentStoreId = this.storeInfo.selectedParentStoreId
      this.selectedDepartmentId = this.storeInfo.selectedDepartmentId
      this.ordinalNumber = this.storeInfo.ordinalNumber
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
    getSelectedStore: function () {
      const storeCodeArr = this.getSelectedStore.ma_kho.split('.')
      let storeCodeTemp = ''
      if (storeCodeArr.length > 1) {
        for (let i = 0; i < storeCodeArr.length; i++) {
          storeCodeTemp += storeCodeArr[i]
        }
      } else storeCodeTemp = storeCodeArr[0]

      this.prefixStoreCode = storeCodeArr.length > 1 ? storeCodeArr[0] : ''
      this.storeCode = storeCodeTemp
      this.storeName = this.getSelectedStore.ten_kho
      this.selectedStoreTypeId = this.getSelectedStore.loaikho_id
      this.selectedParentStoreId = this.getSelectedStore.kho_cha_id
      this.selectedDepartmentId = this.getSelectedStore.donvi_id
      this.ordinalNumber = this.getSelectedStore.thutu
      this.selectedEmployeeId = this.getSelectedStore.thukho_id
      this.phone = this.getSelectedStore.so_dt
      this.status = this.getSelectedStore.trangthai
      this.isUseInvoice = this.getSelectedStore.chungtu
      this.isManageInventory = this.getSelectedStore.tonkho
      this.isSync = this.getSelectedStore.dongbo
      this.storeAccount = this.getSelectedStore.ma_tk
      this.note = this.getSelectedStore.ghichu
    },
    selectedDepartmentId: async function () {
      await this.onSelectedDepartment(this.selectedDepartmentId)
    },
    selectedStoreTypeId: function () {
      this.setSelectedStoreTypeId(this.selectedStoreTypeId)
    },
    isManageInventory: function () {
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
    isSync: function () {
      this.emitData()
    },
    isUseInvoice: function () {
      this.emitData()
    },
    CSHTList: function () {
      this.CSHT = this.CSHTList
    }
  },
  methods: {
    ...mapActions('storesCommon', [
      'loadStoreTypes',
      'loadDepartments',
      'setSelectedDepartment',
      'setSelectedStoreTypeId',
      'loadStoresBelongAccount',
      'loadDepartmentsNotAdded',
      'loadDepartmentsAdded',
      'loadCSHT',
      'setSelectedCSHT'
    ]),
    ...mapActions('employeesCommon', [
      'loadEmployees',
      'loadEmployeesNotAdded',
      'loadEmployeesAdded'
    ]),
    onSelectedStoreType: function (id) {

    },
    loadDepartmentsData: async function (selectedDepartment) {
      try {
        await this.loadDepartmentsNotAdded({
          kho_id: this.getSelectedStore.kho_id
        })

        await this.loadDepartmentsAdded({
          kho_id: this.getSelectedStore.kho_id,
          phanvung_id: selectedDepartment.PHANVUNG_ID
        })
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
    },
    onSelectedDepartment: async function (id) {
      this.$root.showLoading(true)
      try {
        const selectedDepartment = this.getDepartmentById(id)
        await this.setSelectedDepartment(selectedDepartment)
        if (selectedDepartment) {
          await this.loadEmployees({
            donvi_id: selectedDepartment.DONVI_ID,
            phanvung_id: selectedDepartment.PHANVUNG_ID
          })

          this.selectedEmployeeId = this.getEmployees.length > 0 ? this.getEmployees[0].NHANVIEN_ID : 0
          await this.loadStoresBelongAccount({
            nhanvien_id: this.getAccountId,
            phanvung_id: (selectedDepartment === null || selectedDepartment === undefined) ? 0 : selectedDepartment.PHANVUNG_ID
          })

          if (this.getSelectedStore && this.getSelectedStore.kho_id) {
            await this.loadEmployeesNotAdded({
              donvi_id: selectedDepartment.DONVI_ID,
              kho_id: this.getSelectedStore.kho_id,
              phanvung_id: (selectedDepartment === null || selectedDepartment === undefined) ? 0 : selectedDepartment.PHANVUNG_ID
            })
            await this.loadEmployeesAdded({
              kho_id: this.getSelectedStore.kho_id
            })
          }

          if (this.isEnableAddNew) {
            await this.loadDepartmentsData(selectedDepartment)
          }
        }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }

      this.$root.showLoading(false)
    },
    onSelectedParentStore: function (id) {

    },
    onSelectedEmployee: function (id) {

    },
    emitData: function () {
      this.$emit('input', {
        selectedStoreTypeId: this.selectedStoreTypeId,
        selectedDepartmentId: this.selectedDepartmentId,
        selectedParentStoreId: this.selectedParentStoreId,
        selectedEmployeeId: this.selectedEmployeeId,
        prefixStoreCode: this.prefixStoreCode,
        storeCode: (this.storeCode === null || this.storeCode === undefined) ? '' : this.storeCode.trim(),
        storeName: (this.storeName === null || this.storeName === undefined) ? '' : this.storeName.trim(),
        ordinalNumber: this.ordinalNumber,
        phone: (this.phone === null || this.phone === undefined) ? '' : this.phone.toString().trim(),
        status: this.status ? 1 : 0,
        isUseInvoice: this.isUseInvoice ? 1 : 0,
        isManageInventory: this.isManageInventory ? 1 : 0,
        isSync: this.isSync ? 1 : 0,
        storeAccount: this.storeAccount,
        tagFrm: (this.tagFrm === null || this.tagFrm === undefined) ? '' : this.tagFrm.trim(),
        note: (this.note === null || this.note === undefined) ? '' : this.note.trim(),
        codeCSHT: this.codeCSHT
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
      this.isShowCshtPopup = false
      this.codeCSHT = (data !== undefined && data !== null) ? data.ma_csht : ''
    },
    clearCSHT: function () {
      this.codeCSHT = null
      this.setSelectedCSHT({})
    },
    searchCSHT: async function () {
      this.$root.showLoading(true)
      try {
        const searchCode = this.searchCodeCSHT.length === 0 ? '' : `['${this.searchCodeCSHT}']`
        await this.loadCSHT({
          ma_tinh: '',
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
  created: async function () {
    this.$root.showLoading(true)
    try {
      await this.loadStoreTypes()
      await this.loadDepartments({
        donvi_cha_id: this.$root.token.getDonViID()
      })
      this.selectedDepartmentId = this.getDepartments.length > 0 ? this.getDepartments[0].DONVI_ID : 0
      await this.loadCSHT({
        ma_tinh: '',
        ma_csht: this.searchCodeCSHT,
        name: this.searchNameCSHT,
        rims_status: null,
        page_index: null,
        page_size: null
      })
    } catch (error) {
      console.log(error)
      this.$toast.error(error.toString())
    }
    this.$root.showLoading(false)
  }
}
</script>

<style scoped>
.-p-custom{
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

.-table-CSHT{
  max-width: 314px;
}
</style>

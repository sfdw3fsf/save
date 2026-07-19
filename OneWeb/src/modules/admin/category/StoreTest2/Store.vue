<template>
    <div>
        <!-- <div class="breadcrumb-top">
            <div class="main-title">Quản lý danh mục kho</div>
            <ul class="breadcrumb">
                <li class="breadcrumb-item"><a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ
                    </a></li>
                <li class="breadcrumb-item"><a href="#">Lập hợp đồng</a></li>
                <li class="breadcrumb-item active">Lắp đặt mới</li>
            </ul>
        </div> -->

        <breadcrumb />
        <div class="list-actions-top">
            <ul class="list">
                <li :class="{ '-disabled': !isEnableAddNew }">
                    <a href="javascript:void(0)" @click="addNew">
                        <span class="icon one-file-plus"></span>Nhập mới
                    </a>
                </li>
                <li :class="{ '-disabled': !isEnableSave }">
                    <a href="javascript:void(0)" @click="save">
                        <span class="icon one-save"></span>Ghi lại
                    </a>
                </li>
                <li :class="{ '-disabled': !isEnableCancel }">
                    <a href="javascript:void(0)" @click="cancel">
                        <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Huỷ
                    </a>
                </li>
                <li :class="{ '-disabled': !isEnableDelete }">
                    <a href="javascript:void(0)" @click="deleteStore">
                        <span class="icon one-trash"></span>Xoá
                    </a>
                </li>
              <li>
                <a href="javascript:void(0)">
                  <vue-excel-xlsx
                    :data="json_data_sample"
                    :columns="json_fields_sample"
                    :file-name="'importStoreSample'"
                    :file-type="'xlsx'"
                    :sheet-name="'sheet1'"
                    class="btn"
                  >
                    <span class="icon one-xlsx-import" ></span>File mẫu
                  </vue-excel-xlsx>
                </a>
              </li>
              <li>
                  <a href="javascript:void(0)" @click="nhapExcel">
                      <span class="icon one-circle-question"></span>Nhập excel
                  </a>
              </li>
              <li>
                  <a href="javascript:void(0)">
                      <span class="icon one-circle-question"></span>Trợ giúp
                  </a>
              </li>
            </ul>
            <input
              ref="inputImportExcelFile"
              style="display: none"
              type="file"
              class="fileSelect"
              @change="fileChange($event)"
              accept=".xls*"
            />
        </div>
      <div id="content11" style="display: none; padding : 10px;">
        <store-list :storesData="storesData" @onSelect="onSelectStore"></store-list>
      </div>
      <div id="content12" style="display: none; padding : 10px;">
        <store-info ref="storeInfo"
                    :storeInfo="storeInfo"
                    :isEnableAddNew="isEnableAddNew"
                    :CSHTList="getCSHT"
                    @input="onDataChange"
                    @selectedDepartment="OnSelectedDepartment"
                    @onSelect="onSelectStore"
        ></store-info>
      </div>
      <div id="content13" style="display: none; padding : 10px;">
        <ejs-splitter id='splitter13' orientation = 'Vertical' class='splitterContent' ref="splitterObj13" :separatorSize=1 height='700px'>
          <e-panes>
            <e-pane size="45%" min="20%" content='#content131'></e-pane>
            <e-pane size="55%" min="20%" content='#content132'></e-pane>
          </e-panes>

        </ejs-splitter>


      </div>
      <div id="content131" style="display: none; padding : 10px;">
        <stores></stores>
      </div>
      <div id="content132" style="display: none; padding : 10px;">
        <employees ref='employees'></employees>
      </div>
      <div id="content14" style="display: none; padding : 10px;">
        <purposes></purposes>
      </div>
      <div class="grid-stack-box" id='contentSplitter3'>
        <ejs-splitter id='splitter3' class='splitterContent' ref="splitterObj3" :separatorSize=1 width='100%'>
          <e-panes>
            <e-pane size="40%" min="20%" content='#content12'></e-pane>
            <e-pane size="60%" min="20%" content='#content13'></e-pane>
          </e-panes>

        </ejs-splitter>
      </div>
      <div class="grid-stack-box" id='contentSplitter2'>
        <ejs-splitter id='splitter2' orientation = 'Vertical' class='splitterContent' ref="splitterObj2" :separatorSize=1 width='100%' height='1000px'>
          <e-panes>
            <e-pane size="70%" min="30%" content='#contentSplitter3'></e-pane>
            <e-pane size="30%" min="20%" content='#content14'></e-pane>
          </e-panes>

        </ejs-splitter>
      </div>
        <div class="page-content">
          <div class="grid-stack-box">
            <ejs-splitter id='splitter' class='splitterContent' ref="splitterObj" :separatorSize=1 width='100%'>
              <e-panes>
                <e-pane size="20%" min="20%" content='#content11'></e-pane>
                <e-pane size="80%" min="20%" content='#contentSplitter2'></e-pane>
              </e-panes>

            </ejs-splitter>
          </div>
<!--          <div class="row">-->
<!--                <div class="col-sm-2 col-12">-->
<!--                    <store-list :storesData="storesData" @onSelect="onSelectStore"></store-list>-->
<!--                </div>-->
<!--                <div class="col-sm-10 col-12">-->
<!--                    <div class="row">-->
<!--                        <div class="col-sm-6 col-12">-->
<!--                          <store-info ref="storeInfo"-->
<!--                          :storeInfo="storeInfo"-->
<!--                          :isEnableAddNew="isEnableAddNew"-->
<!--                          :CSHTList="getCSHT"-->
<!--                          @input="onDataChange"-->
<!--                          @selectedDepartment="OnSelectedDepartment"-->
<!--                          @onSelect="onSelectStore"-->
<!--                          ></store-info>-->
<!--                        </div>-->
<!--                        <div class="col-sm-6 col-12">-->
<!--                            <stores></stores>-->
<!--                            <employees ref='employees'></employees>-->
<!--                        </div>-->
<!--                    </div>-->
<!--                    <purposes></purposes>-->
<!--                </div>-->
<!--            </div>-->
        </div>
    </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import mixinStore from './mixin/index'
import breadcrumb from '@/components/breadcrumb'
import api from './api/index'
import axios from 'axios'
import VueExcelXlsx from "vue-excel-xlsx"
import Vue from "vue"
import XLSX from "xlsx"
Vue.use(VueExcelXlsx)

export default {
  components: {
    StoreList: () => import('./parts/StoreList.vue'),
    StoreInfo: () => import('./parts/StoreInfo.vue'),
    stores: () => import('./parts/Departments.vue'),
    Employees: () => import('./parts/Employees.vue'),
    Purposes: () => import('./parts/Purposes.vue'),
    breadcrumb,
  },
  data () {
    return {
      isEnableAddNew: true,
      isEnableDelete: true,
      isEnableSave: true,
      isEnableCancel: true,
      storeInfo: {
        selectedStoreTypeId: null,
        selectedDepartmentId: null,
        selectedParentStoreId: null,
        selectedEmployeeId: null,
        prefixStoreCode: '',
        storeCode: '',
        storeName: '',
        ordinalNumber: '',
        phone: '',
        status: 0,
        isUseInvoice: 0,
        isManageInventory: 0,
        isSync: 0,
        storeAccount: null,
        tagFrm: '',
        note: '',
        codeCSHT: '',
        codeFmis:'',
      },
      storesData: [],
      json_meta: [
        [{
          "key": "charset",
          "value": "utf-8"
        }]
      ],
      json_fields_sample: [ {
        field: 'MA_KHO',
        label: 'MA_KHO'
      }, {
        field: 'THUTU',
        label: 'THUTU'
      }, {
        field: 'TEN_KHO',
        label: 'TEN_KHO'
      }, {
        field: 'KHO_CHA_ID',
        label: 'KHO_CHA_ID'
      }, {
        field: 'LOAIKHO_ID',
        label: 'LOAIKHO_ID'
      }, {
        field: 'DONVI_ID',
        label: 'DONVI_ID'
      }, {
        field: 'CHUNGTU',
        label: 'CHUNGTU'
      }, {
        field: 'TRANGTHAI',
        label: 'TRANGTHAI'
      }, {
        field: 'GHICHU',
        label: 'GHICHU'
      }, {
        field: 'TONKHO',
        label: 'TONKHO'
      }, {
        field: 'SUDUNG',
        label: 'SUDUNG'
      }, {
        field: 'SO_DT',
        label: 'SO_DT'
      }, {
        field: 'DONGBO',
        label: 'DONGBO'
      }, {
        field: 'MA_TK',
        label: 'MA_TK'
      }, {
        field: 'MA_CSHT',
        label: 'MA_CSHT'
      }, {
        field: 'MA_DV_FMIS',
        label: 'MA_DV_FMIS'
      }, {
        field: 'THUKHO_ID',
        label: 'THUKHO_ID'
      }],
      json_data_sample: [
        {
          LOAIKHO_ID: 110,
          DONVI_ID: 293342,
          KHO_CHA_ID: 1,
          THUKHO_ID: 411761,
          MA_KHO: 'Don.test.abcd',
          TEN_KHO: 'Don test abcd',
          THUTU: 0,
          SO_DT: '',
          TRANGTHAI: 0,
          CHUNGTU: 0,
          TONKHO: 1,
          DONGBO: 0,
          GHICHU: '',
          MA_CSHT: '',
          MA_TK: '',
          MA_DV_FMIS: '',
          SUDUNG: ''
        }
      ]
    }
  },
  computed: {
    ...mapGetters('storesCommon', [
      'getDepartments',
      'getStores',
      'getSelectedStore',
      'getCSHT',
      'getSelectedDepartment',
      'getStoreTypes'
    ])
  },
  mixins: [mixinStore],
  methods: {
    ...mapActions('storesCommon', [
      'resetDepartments',
      'checkDataBeforeSave',
      'saveStoreRecord',
      'checkDataBeforeDelete',
      'loadStores',
      'loadDepartmentsNotAdded',
      'loadDepartmentsAdded',
      'setSelectedStore'
    ]),
    ...mapActions('purposesCommon', [
      'resetPurposes',
      'loadPurposeNotAdded',
      'loadPurposeAdded'
    ]),
    ...mapActions('employeesCommon', [
      'resetEmployees',
      'loadEmployeesNotAdded',
      'loadEmployeesAdded'
    ]),
    addNew: function () {
      this.setButton(1)
    },
    onDataChange: async function (data) {
      console.log('onDataChange')
      console.log(data)
      this.storeInfo = data
      this.storeInfo.tagFrm = this.$route.query.tag
    },
    save: async function () {
      this.$root.showLoading(true)
      try {
        if (this.isValidForm()) {
          const updatedStoreDataResult = await this.saveStoreRecord({
            isEnableAddNew: this.isEnableAddNew,
            accountName: this.$root.context.user.getTenNhanVien(),
            storeInfo: this.storeInfo,
            nhanvien_id: this.$root.token.getNhanVienID()
          })

          if (updatedStoreDataResult.isSuccess) {
            updatedStoreDataResult.warnings.forEach(i => this.$toast.success(i))
            const stores = await this.loadStores()
            const nodes = this.buildTreeFromList(stores, 'ten_kho', 'kho_id', 'kho_cha_id')
            const roots = [{
              id: '-1',
              name: 'Root',
              children: nodes
            }]
            this.storesData = Array.from(roots)
          } else {
            updatedStoreDataResult.warnings.forEach(i => this.$toast.error(i))
          }
        }
      } catch (error) {
        console.log(error)
        if (error.data && error.data.message_detail) {
          this.$toast.error(error.data.message_detail)
        } else this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    cancel: function () {
      this.setButton(0)
    },
    deleteStore: async function () {

      const cf = await this.$bvModal.msgBoxConfirm(`Bạn có muốn xóa dữ liệu không ?`,{okTitle: "Có",cancelTitle: "Không"});
      if (!cf) return;
      this.$root.showLoading(true)
      try {
        const resCheckDataResult = await this.checkDataBeforeDelete()
        if (resCheckDataResult.isSuccess) {
          const resDeleteStore = await api.deleteSelectedStore(axios, {
            kho_id: this.getSelectedStore.kho_id
          })

          if (resDeleteStore.data.message_detail) {
            throw resDeleteStore.data.message_detail
          }

          this.$toast.success('Đã xóa kho thành công !')
          this.setButton(0)

          // reload stores
          const stores = await this.loadStores()
          const nodes = this.buildTreeFromList(stores, 'ten_kho', 'kho_id', 'kho_cha_id')
          const roots = [{
            id: '-1',
            name: 'Root',
            children: nodes
          }]
          this.storesData = Array.from(roots)

          // reset data
          this.resetStoreInfo()
        } else {
          resCheckDataResult.warnings.forEach(i => this.$toast.error(i))
        }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    isValidForm: function () {
      // general validate
      const isAddNew = !this.isEnableAddNew

      if (!this.storeInfo.storeCode || this.storeInfo.storeCode.length === 0) {
        this.$toast.error('Mã kho không được để trống !')
        this.$refs.storeInfo.isStoreCodeError = true
        return false
      } else { this.$refs.storeInfo.isStoreCodeError = false }

      // Kiểm tra loại kho
      if (this.storeInfo.selectedStoreTypeId) {
        const storType = this.getStoreTypes.find(i => i.LOAIKHO_ID === this.storeInfo.selectedStoreTypeId && i.PREFIX === 1)
        if (storType && !this.storeInfo.isManageInventory) {
          this.$toast.error('Đối với loại kho của tập đoàn quy định bạn phải chọn quản lý tồn kho !')
          return false
        }
      }

      const store = this.getStores.find(i => i.ma_kho === this.storeInfo.storeCode)
      if (isAddNew && store) {
        this.$toast.error('Mã kho đã tồn tại !')
        this.$refs.storeInfo.isStoreCodeError = true
        return false
      } else { this.$refs.storeInfo.isStoreCodeError = false }

      if (!this.storeInfo.storeName || this.storeInfo.storeName.length === 0) {
        this.$toast.error('Bạn chưa nhập tên Kho')
        this.$refs.storeInfo.isStoreNameError = true
        return false
      } else { this.$refs.storeInfo.isStoreNameError = false }

      if (this.tagFrm === '1' && !this.storeInfo.selectedStoreTypeId) {
        this.$toast.error('Bạn phải chọn loại kho !')
        this.$refs.storeInfo.isStoreTypeError = true
        return false
      } else { this.$refs.storeInfo.isStoreTypeError = false }

      if (this.tagFrm === '1' && !this.storeInfo.selectedDepartmentId) {
        this.$toast.error('Bạn phải chọn đơn vị !')
        this.$refs.storeInfo.isDepartmentError = true
        return false
      } else { this.$refs.storeInfo.isDepartmentError = false }

      if (this.tagFrm === '1' && (this.storeInfo.selectedParentStoreId === null || this.storeInfo.selectedParentStoreId === undefined)) {
        this.$toast.error('Bạn phải chọn kho cha !')
        this.$refs.storeInfo.isParentStoreError = true
        return false
      } else { this.$refs.storeInfo.isParentStoreError = false }

      return true
    },
    onSelectStore: async function (selectedStore) {
      try {
        console.log('onSelectStore000')
        this.$root.showLoading(true)

        if (!selectedStore || selectedStore.kho_id.toString() === '-1') {
          this.isEnableSave = false
          this.isEnableDelete = false
          this.isEnableCancel = false
        } else {
          this.isEnableAddNew = true
          this.isEnableSave = true
          this.isEnableDelete = true
          this.isEnableCancel = true
        }

        await this.loadDepartmentsData(selectedStore)
        await this.loadEmployeesData(selectedStore)
        await this.reloadEmployeesData()
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    resetStoreInfo: async function () {
      const storeCodeArr = this.getSelectedStore.ma_kho.split('.')
      let storeCodeTemp = ''
      if (storeCodeArr.length > 1) {
        for (let i = 0; i < storeCodeArr.length; i++) {
          storeCodeTemp += storeCodeArr[i]
        }
      } else storeCodeTemp = storeCodeArr[0]

      this.storeInfo.selectedStoreTypeId = this.getSelectedStore.loaikho_id
      this.storeInfo.selectedDepartmentId = this.getSelectedStore.donvi_id
      this.storeInfo.selectedParentStoreId = this.getSelectedStore.kho_cha_id
      this.storeInfo.selectedEmployeeId = this.getSelectedStore.thukho_id
      this.storeInfo.prefixStoreCode = storeCodeArr.length > 1 ? storeCodeArr[0] : ''
      this.storeInfo.storeCode = storeCodeTemp
      this.storeInfo.storeName = this.getSelectedStore.ten_kho
      this.storeInfo.ordinalNumber = this.getSelectedStore.thutu
      this.storeInfo.phone = this.getSelectedStore.so_dt
      this.storeInfo.status = this.getSelectedStore.trangthai
      this.storeInfo.isUseInvoice = this.getSelectedStore.chungtu
      this.storeInfo.isManageInventory = this.getSelectedStore.tonkho
      this.storeInfo.isSync = this.getSelectedStore.dongbo
      this.storeInfo.storeAccount = this.getSelectedStore.ma_tk
      this.storeInfo.tagFrm = this.$route.query.tag
      this.storeInfo.codeFmis = this.getSelectedStore.codeFmis
      this.storeInfo.note = this.getSelectedStore.ghichu

      // this.resetStores()
      // this.resetPurposes()
      // this.resetEmployees()

      await this.loadDepartmentsData(this.getSelectedStore)
      await this.loadEmployeesData(this.getSelectedStore)
      await this.reloadEmployeesData()
    },
    loadDepartmentsData: async function (selectedStore) {
      try {
        let kho_id = -1
        if (selectedStore && selectedStore.kho_id) kho_id = selectedStore.kho_id

        await this.loadDepartmentsNotAdded({
          kho_id: kho_id
        })

        await this.loadDepartmentsAdded({
          kho_id: kho_id
          // phanvung_id: (this.getSelectedDepartment === null || this.getSelectedDepartment === undefined) ? 0 : this.getSelectedDepartment.PHANVUNG_ID
        })
      } catch (error) {
        console.log(error)
        this.$toast.error(error.data.message_detail)
      }
    },
    loadEmployeesData: async function (selectedStore) {
      try {
        if (!selectedStore) return

        await this.loadPurposeNotAdded({
          kho_id: selectedStore.kho_id,
          phanvung_id: (this.getSelectedDepartment === null || this.getSelectedDepartment === undefined) ? 0 : this.getSelectedDepartment.PHANVUNG_ID
        })

        await this.loadPurposeAdded({
          kho_id: selectedStore.kho_id,
          phanvung_id: (this.getSelectedDepartment === null || this.getSelectedDepartment === undefined) ? 0 : this.getSelectedDepartment.PHANVUNG_ID
        })
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
    },
    reloadEmployeesData: async function () {
      try {
        // load all
        // load all
        //this.$refs.employees.selectedDepartmentNotAddedId = -1
        if (this.$refs.employees.selectedDepartmentNotAddedId != -1){
          const donvi_id = this.$refs.employees.selectedDepartmentNotAddedId
          await this.loadEmployeesNotAdded({
            donvi_id: donvi_id,
            kho_id: this.getSelectedStore.kho_id,
            phanvung_id: (this.getSelectedDepartment === null || this.getSelectedDepartment === undefined) ? 0 : this.getSelectedDepartment.PHANVUNG_ID
          })
        }
        await this.loadEmployeesAdded({
          kho_id: this.getSelectedStore.kho_id
        })
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
    },
    OnSelectedDepartment: async function () {
      //this.loading(true)
      try {
        await this.reloadEmployeesData()
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      //this.loading(false)
    },
    setButton: function (kieu) {
      this.isEnableAddNew = false
      this.isEnableDelete = false
      this.isEnableSave = false
      this.isEnableCancel = false

      switch (kieu) {
        case -1:
          this.isEnableSave = true
          this.isEnableCancel = true
          break
        case 0:
          this.isEnableAddNew = true
          this.clear()
          break
        case 1:
          this.isEnableSave = true
          this.isEnableCancel = true
          this.clear()
          break
        case 2:
          this.isEnableAddNew = true
          this.isEnableDelete = true
          this.clear()
          break
        case 3:
          this.isEnableAddNew = true
          this.isEnableDelete = true
          this.isEnableSave = true
          this.isEnableCancel = true
          break
      }
    },
    clear: function () {
      this.$refs.storeInfo.prefixStoreCode = ''
      this.$refs.storeInfo.storeCode = ''
      this.$refs.storeInfo.storeName = ''
      this.$refs.storeInfo.note = ''
      this.$refs.storeInfo.selectedDepartmentId = null
      this.$refs.storeInfo.selectedEmployeeId = null
      this.$refs.storeInfo.selectedParentStoreId = null
      this.$refs.storeInfo.selectedStoreTypeId = null
      this.$refs.storeInfo.isUseInvoice = false
      this.$refs.storeInfo.status = false
      this.$refs.storeInfo.storeAccount = ''
      this.$refs.storeInfo.codeCSHT = ''
      this.$refs.storeInfo.codeFmis = ''
      this.$refs.storeInfo.ordinalNumber = ''
      this.resetPurposes()
      this.resetEmployees()
      this.resetDepartments()
    },
    nhapExcel: async function () {
      const isConfirm = await this.$bvModal
        .msgBoxConfirm(
          `Cấu trúc file gồm sheet1: {MA_KHO, THUTU, TEN_KHO, KHO_CHA_ID, LOAIKHO_ID, DONVI_ID, CHUNGTU, TRANGTHAI, GHICHU, TONKHO,SUDUNG,SO_DT,DONGBO,MA_TK,MA_CSHT,MA_DV_FMIS,THUKHO_ID}`,
          {
            title: '',
            size: 'lg',
            okTitle: 'Đồng ý',
            cancelTitle: 'Không đồng ý',
            centered: true
          }
        )

      if (isConfirm) { this.$refs.inputImportExcelFile.click() }
    },
    fileChange (event) {
      const currentDataImportExcelFile = []
      const file = event.target.files ? event.target.files[0] : null
      if (file) {
        const reader = new FileReader()

        reader.onload = async (e) => {
          this.loading(true)
          try {
            /* Parse data */
            const bstr = e.target.result
            const wb = XLSX.read(bstr, { type: "binary" })
            /* Get first worksheet */
            const wsname = wb.SheetNames[0]
            const ws = wb.Sheets[wsname]
            /* Convert array of arrays */
            const data = XLSX.utils.sheet_to_json(ws, { header: 1 })
            console.log('import excel file', data)

            const fields = []
            for (let i = 0; i < data.length; i++) {
              if (i === 0) {
                data[i].forEach((f) => {
                  fields.push(f)
                })

                if (fields.length < 17) {
                  const errorText = 'File excel gồm 16 trường: MA_KHO, THUTU, TEN_KHO, KHO_CHA_ID, LOAIKHO_ID, DONVI_ID, CHUNGTU, TRANGTHAI, GHICHU, TONKHO,SUDUNG,SO_DT,DONGBO,MA_TK,MA_CSHT,MA_DV_FMIS,THUKHO_ID'
                  throw errorText
                }

                console.log('fields', fields)

                if (!fields.find(i => i === 'MA_KHO')) {
                  const errorText = 'Tên cột trong file không đúng định dạng : MA_KHO'
                  throw errorText
                }
                if (!fields.find(i => i === 'TEN_KHO')) {
                  const errorText = 'Tên cột trong file không đúng định dạng : TEN_KHO'
                  throw errorText
                }
                if (!fields.find(i => i === 'LOAIKHO_ID')) {
                  const errorText = 'Tên cột trong file không đúng định dạng : LOAIKHO_ID'
                  throw errorText
                }
                if (!fields.find(i => i === 'KHO_CHA_ID')) {
                  const errorText = 'Tên cột trong file không đúng định dạng : KHO_CHA_ID'
                  throw errorText
                }
                if (!fields.find(i => i === 'DONVI_ID')) {
                  const errorText = 'Tên cột trong file không đúng định dạng : DONVI_ID'
                  throw errorText
                }
                if (!fields.find(i => i === 'THUTU')) {
                  const errorText = 'Tên cột trong file không đúng định dạng : THUTU'
                  throw errorText
                }
                if (!fields.find(i => i === 'THUKHO_ID')) {
                  const errorText = 'Tên cột trong file không đúng định dạng : THUKHO_ID'
                  throw errorText
                }
                if (!fields.find(i => i === 'SO_DT')) {
                  const errorText = 'Tên cột trong file không đúng định dạng : SO_DT'
                  throw errorText
                }
                if (!fields.find(i => i === 'TRANGTHAI')) {
                  const errorText = 'Tên cột trong file không đúng định dạng : TRANGTHAI'
                  throw errorText
                }
                if (!fields.find(i => i === 'CHUNGTU')) {
                  const errorText = 'Tên cột trong file không đúng định dạng : CHUNGTU'
                  throw errorText
                }
                if (!fields.find(i => i === 'TONKHO')) {
                  const errorText = 'Tên cột trong file không đúng định dạng : TONKHO'
                  throw errorText
                }
                if (!fields.find(i => i === 'SUDUNG')) {
                  const errorText = 'Tên cột trong file không đúng định dạng : SUDUNG'
                  throw errorText
                }
                if (!fields.find(i => i === 'DONGBO')) {
                  const errorText = 'Tên cột trong file không đúng định dạng : DONGBO'
                  throw errorText
                }
                if (!fields.find(i => i === 'MA_CSHT')) {
                  const errorText = 'Tên cột trong file không đúng định dạng : MA_CSHT'
                  throw errorText
                }
                if (!fields.find(i => i === 'MA_TK')) {
                  const errorText = 'Tên cột trong file không đúng định dạng : MA_TK'
                  throw errorText
                }
                if (!fields.find(i => i === 'GHICHU')) {
                  const errorText = 'Tên cột trong file không đúng định dạng : GHICHU'
                  throw errorText
                }
                if (!fields.find(i => i === 'MA_DV_FMIS')) {
                  const errorText = 'Tên cột trong file không đúng định dạng : MA_DV_FMIS'
                  throw errorText
                }
              } else {
                try {
                  const row = {}
                  for (let j = 0; j < fields.length; j++) {
                    row[`${fields[j]}`] = data[i][j]
                  }
                  currentDataImportExcelFile.push(row)
                } catch (error) {
                  console.log(error)
                  if (error.data && error.data.message_detail) {
                    this.$toast.error(error.data.message_detail)
                  } else this.$toast.error(error.toString())
                }
              }
            }

            console.log('currentDataImportExcelFile', currentDataImportExcelFile)
            const resInsertStores = await api.insertStoresFromExcelFile(axios, { vds: JSON.stringify(currentDataImportExcelFile) })
            if (resInsertStores.data.message_detail) throw resInsertStores.data.message_detail

            this.$toast.success('Thêm mới kho thành công !')

            const stores = await this.loadStores()
            const nodes = this.buildTreeFromList(stores, 'ten_kho', 'kho_id', 'kho_cha_id')
            const roots = [{
              id: '-1',
              name: 'Root',
              children: nodes
            }]
            this.storesData = Array.from(roots)
          } catch (error) {
            console.log(error)
            if (error.data && error.data.message_detail) {
              this.$toast.error(error.data.message_detail)
            } else this.$toast.error(error.toString())
          }
          this.loading(false)
        }

        reader.readAsBinaryString(file)
      }
    }
  },
  watch: {
    getSelectedStore: function () {
      this.isEnableDelete = true
      this.isEnableAddNew = true
    }
  },
  mounted: async function () {
    this.$root.showLoading(true)
    this.tagFrm = this.$route.query.tag
    try {
      let data = null
      if (this.tagFrm == "1"){
        data = {
          nhanvien_id: this.$root.token.getNhanVienID(),
          phanvung_id: this.$root.token.getPhanVungID()
        }
      }
      const stores = await this.loadStores(data)
      //const stores = await this.loadStores()
      const nodes = this.buildTreeFromList(stores, 'ten_kho', 'kho_id', 'kho_cha_id')
      const roots = [{
        id: '-1',
        name: 'Root',
        children: nodes
      }]
      this.storesData = Array.from(roots)
    } catch (error) {
      console.log(error)
      if (error.data && error.data.message_detail) {
        this.$toast.error(error.data.message_detail)
      } else this.$toast.error(error.toString())
    }
    this.$root.showLoading(false)
  }
}
</script>

<style>
.-disabled{
    pointer-events: none;
    cursor: default;
}
.-disabled:hover{
    background: unset;
}
.-disabled > a {
    color: gray !important;
}
.-disabled > a > span {
    color: gray;
}

.list-actions-top .list>li .btn {
    display: block;
    padding: 2px 15px;
    color: #0176FF;
    text-decoration: none;
    font-weight: 600;
}
.page-content {
  padding: 16px 16px 16px 10px;
}
.e-splitter.e-splitter-horizontal .e-pane.e-pane-horizontal, .e-splitter.e-splitter-vertical .e-pane.e-pane-horizontal {

  padding: 0px!important;
}
</style>

<template>
    <store-list :storesData="storesData" @onSelect="onSelectStore"></store-list>
    <!-- <tree-view
              :options="temp_storesData"
              :labelField="'name'"
              :valueField="'id'"></tree-view> -->
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import mixinStore from './mixin/index'
import TreeView from './parts/tree/TreeView'
export default {
  components: {
    StoreList: () => import('./parts/StoreList.vue'),
    StoreInfo: () => import('./parts/StoreInfo.vue'),
    stores: () => import('./parts/Departments.vue'),
    Employees: () => import('./parts/Employees.vue'),
    Purposes: () => import('./parts/Purposes.vue'),
    TreeView
  },
  data () {
    return {
      temp_storesData: [{
        id: 1,
        isOpen: true,
        name: "Root",
        children: [{
          id: 2,
          isOpen: true,
          name: "Child"
        }]
      }],
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
        note: ''
      },
      storesData: []
    }
  },
  computed: {
    ...mapGetters('storesCommon', [
      'getStoreTypes',
      'getDepartments',
      'getStores',
      'getSelectedStore',
      'getCSHT',
      'getSelectedDepartment'
    ])
  },
  mixins: [mixinStore],
  methods: {
    ...mapActions('storesCommon', [
      'resetStores',
      'checkDataBeforeSave',
      'saveStoreRecord',
      'checkDataBeforeDelete',
      'setAccountId',
      'deleteSelectedStore',
      'loadStores',
      'loadDepartmentsNotAdded',
      'loadDepartmentsAdded'
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
      this.isEnableAddNew = false
      this.isEnableDelete = false
      this.isEnableCancel = true
      this.resetStoreInfo()
    },
    onDataChange: function (data) {
      this.storeInfo = data
    },
    save: async function () {
      this.$root.showLoading(true)
      try {
        if (this.isValidForm()) {
          const updatedStoreDataResult = await this.saveStoreRecord({
            isEnableAddNew: this.isEnableAddNew,
            accountName: this.$root.context.user.getTenNhanVien(),
            storeInfo: this.storeInfo
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
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    cancel: function () {
      this.isEnableAddNew = true
      this.isEnableSave = false
      this.isEnableDelete = false
      this.isEnableCancel = false

      this.resetStoreInfo()
    },
    deleteStore: async function () {
      this.$root.showLoading(true)
      try {
        const resCheckDataResult = await this.checkDataBeforeDelete()
        if (resCheckDataResult.isSuccess) {
          await this.deleteSelectedStore()
          this.$toast.success('Đã xóa kho thành công !')

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

      if (!this.storeInfo.isManageInventory) {
        this.$toast.error('Đối với loại kho của tập đoàn quy định bạn phải chọn quản lý tồn kho !')
        return false
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

      if (!this.storeInfo.selectedStoreTypeId) {
        this.$toast.error('Bạn phải chọn loại kho !')
        this.$refs.storeInfo.isStoreTypeError = true
        return false
      } else { this.$refs.storeInfo.isStoreTypeError = false }

      if (!this.storeInfo.selectedDepartmentId) {
        this.$toast.error('Bạn phải chọn đơn vị !')
        this.$refs.storeInfo.isDepartmentError = true
        return false
      } else { this.$refs.storeInfo.isDepartmentError = false }

      if (!this.storeInfo.selectedParentStoreId) {
        this.$toast.error('Bạn phải chọn kho cha !')
        this.$refs.storeInfo.isParentStoreError = true
        return false
      } else { this.$refs.storeInfo.isParentStoreError = false }

      return true
    },
    onSelectStore: async function (selectedStore) {
      try {
        this.$root.showLoading(true)
        this.isEnableAddNew = true
        this.isEnableSave = true
        this.isEnableDelete = true
        this.isEnableCancel = true

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

      this.storeInfo = {
        selectedStoreTypeId: this.getSelectedStore.loaikho_id,
        selectedDepartmentId: this.getSelectedStore.donvi_id,
        selectedParentStoreId: this.getSelectedStore.kho_cha_id,
        selectedEmployeeId: this.getSelectedStore.thukho_id,
        prefixStoreCode: storeCodeArr.length > 1 ? storeCodeArr[0] : '',
        storeCode: storeCodeTemp,
        storeName: this.getSelectedStore.ten_kho,
        ordinalNumber: this.getSelectedStore.thutu,
        phone: this.getSelectedStore.so_dt,
        status: this.getSelectedStore.trangthai,
        isUseInvoice: this.getSelectedStore.chungtu,
        isManageInventory: this.getSelectedStore.tonkho,
        isSync: this.getSelectedStore.dongbo,
        storeAccount: this.getSelectedStore.ma_tk,
        tagFrm: '',
        note: this.getSelectedStore.ghichu
      }
      // this.resetStores()
      // this.resetPurposes()
      // this.resetEmployees()

      await this.loadDepartmentsData(this.getSelectedStore)
      await this.loadEmployeesData(this.getSelectedStore)
      await this.reloadEmployeesData()
    },
    loadDepartmentsData: async function (selectedStore) {
      try {
        if (!selectedStore) return

        await this.loadDepartmentsNotAdded({
          kho_id: selectedStore.kho_id
        })

        await this.loadDepartmentsAdded({
          kho_id: selectedStore.kho_id,
          phanvung_id: (this.getSelectedDepartment === null || this.getSelectedDepartment === undefined) ? 0 : this.getSelectedDepartment.PHANVUNG_ID
        })
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
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
        await this.loadEmployeesNotAdded({
          donvi_id: (this.getSelectedDepartment === null || this.getSelectedDepartment === undefined) ? 0 : this.getSelectedDepartment.DONVI_ID,
          kho_id: this.getSelectedStore.kho_id,
          phanvung_id: (this.getSelectedDepartment === null || this.getSelectedDepartment === undefined) ? 0 : this.getSelectedDepartment.PHANVUNG_ID
        })
        await this.loadEmployeesAdded({
          kho_id: this.getSelectedStore.kho_id
        })
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
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
    try {
      this.setAccountId(this.$root.token.getNhanVienID())

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
      this.$toast.error(error.toString())
    }
    this.$root.showLoading(false)
  }
}
</script>

<style scoped>
.-disabled{
    pointer-events: none;
    cursor: default;
}
.-disabled:hover{
    color: #0176FF;
    background: unset;
}
</style>

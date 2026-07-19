import api from '../../../modules/admin/category/Store/api'

export const actions = {
  async loadStoreTypes ({ state, commit, rootState, rootGetters }, data) {
    const res = await api.getStoreTypes(this._vm.axios, data)
    if (res.data && res.data.data && res.data.data.length > 0) {
      commit('setStoreTypes', res.data.data)
    }
  },
  async loadCSHT ({ state, commit, rootState, rootGetters }, data) {
    const res = await api.getCSHT(this._vm.axios, data)
    if (res.data.data) {
      const cshtData = res.data.data
      const csht = []
      for (let i = 0; i < cshtData.data.length; i++) {
        for (let j = 0; j < cshtData.data[i].json_station.length; j++) {
          csht.push(cshtData.data[i].json_station[j])
        }
      }
      commit('setCSHT', csht)
    }
  },
  async loadStores ({ state, commit, rootState, rootGetters }, data) {
    console.log(data)
    let res = null
    if (data && data.phanvung_id && data.nhanvien_id){
      res = await api.getStoresBelongAccount(this._vm.axios,data)
    }
    else {
      res = await api.getStores(this._vm.axios, data)
    }

    if (res.data && res.data.data && res.data.data.length > 0) {
      commit('setStores', res.data.data)
      return res.data.data
    }
  },
  async setStoresBelongAccount ({ state, commit, rootState, rootGetters }, data) {
    commit('setStoresBelongAccount', data)
  },
  async loadDepartments ({ state, commit, rootState, rootGetters }, data) {
    const res = await api.getDepartments(this._vm.axios, data)
    console.log('loadDepartments kaka', res)
    commit('setDepartments', Object.freeze(res.data.data))
    // if (res.data && res.data.data && res.data.data.length > 0) {
    //   commit('setDepartments', res.data.data)
    // }
  },
  setSelectedStore ({ state, commit, rootState, rootGetters }, selectedStore) {
    commit('setSelectedStore', selectedStore)
  },
  setSelectedDepartment ({ state, commit, rootState, rootGetters }, selectedDepartment) {
    commit('setSelectedDepartment', selectedDepartment)
  },
  setSelectedStoreTypeId ({ state, commit, rootState, rootGetters }, selectedStoreTypeId) {
    commit('setSelectedStoreTypeId', selectedStoreTypeId)
  },
  async loadDepartmentsNotAdded ({ state, commit, rootState, rootGetters }, data) {
    const res = await api.getDepartmentsNotAdded(this._vm.axios, data)
    commit('setDepartmentsNotAdded', res.data.data)
  },
  async loadDepartmentsAdded ({ state, commit, rootState, rootGetters }, data) {
    const res = await api.getDepartmentsAdded(this._vm.axios, data)
    commit('setDepartmentsAdded', res.data.data)
  },
  addDepartmentsToDepartmentsAdded ({ state, commit, rootState, rootGetters }, data) {
    commit('addDepartmentsToDepartmentsAdded', data)
  },
  removeDepartmentsFromDepartmentsAdded ({ state, commit, rootState, rootGetters }, data) {
    commit('removeDepartmentsFromDepartmentsAdded', data)
  },
  resetDepartments ({ state, commit, rootState, rootGetters }, data) {
    commit('resetDepartments', data)
  },
  async checkDataBeforeSave ({ state, commit, rootState, rootGetters }, data) {
    // define result
    const result = {
      isSuccess: true,
      warnings: []
    }
    return result
  },
  async saveStoreRecord ({ state, commit, rootState, rootGetters }, data) {
    const result = {
      isSuccess: true,
      warnings: []
    }

    const MA_KHO = data.storeInfo.prefixStoreCode !== '' &&
    data.storeInfo.prefixStoreCode !== undefined &&
    data.storeInfo.prefixStoreCode !== null
      ? `${data.storeInfo.prefixStoreCode}.${data.storeInfo.storeCode}` : data.storeInfo.storeCode

    const selectedDepartment = state.stores.departments.find(i => i.DONVI_ID !== null && i.DONVI_ID !== undefined &&
      data.storeInfo.selectedDepartmentId !== null && data.storeInfo.selectedDepartmentId !== undefined &&
      i.DONVI_ID.toString() === data.storeInfo.selectedDepartmentId.toString())
    if (!selectedDepartment) {
      result.isSuccess = false
      result.warnings.push('Đơn vị không tồn tại trong danh sách!')
      return result
    }

    if (!data.isEnableAddNew) {
      // add new store
      const resInsert = await api.checkAndInsertStore(this._vm.axios, {
        donvi_id: data.storeInfo.selectedDepartmentId,
        js_kho: {
          CHUNGTU: data.storeInfo.isUseInvoice,
          DONGBO: data.storeInfo.isSync,
          DONVI_ID: data.storeInfo.selectedDepartmentId,
          GHICHU: data.storeInfo.note,
          IP_CN: '',
          KHO_CHA_ID: data.storeInfo.selectedParentStoreId,
          KHO_ID: -1,
          LOAIKHO_ID: data.storeInfo.selectedStoreTypeId,
          MAY_CN: '',
          MA_CSHT: data.storeInfo.codeCSHT,
          MA_DV_FMIS: data.storeInfo.codeFmis,
          MA_KHO: MA_KHO,
          MA_TK: data.storeInfo.storeAccount,
          NGAY_CN: '',
          NGUOI_CN: data.accountName,
          SO_DT: data.storeInfo.phone,
          TEN_KHO: data.storeInfo.storeName,
          THUKHO_ID: data.storeInfo.selectedEmployeeId,
          THUTU: data.storeInfo.ordinalNumber,
          TONKHO: data.storeInfo.isManageInventory,
          TRANGTHAI: data.storeInfo.status,
          chungtu: data.storeInfo.isUseInvoice,
          dongbo: data.storeInfo.isSync,
          donvi_ID: data.storeInfo.selectedDepartmentId,
          ghichu: data.storeInfo.note,
          ip_CN: '',
          kho_CHA_ID: data.storeInfo.selectedParentStoreId,
          kho_ID: -1,
          loaikho_ID: data.storeInfo.selectedStoreTypeId,
          ma_CSHT: data.storeInfo.codeCSHT,
          ma_DV_FMIS: data.storeInfo.tagFrm,
          ma_KHO: MA_KHO,
          may_CN: '',
          ngay_CN: '',
          nguoi_CN: data.accountName,
          so_DT: data.storeInfo.phone,
          ten_KHO: data.storeInfo.storeName,
          thukho_ID: data.storeInfo.selectedEmployeeId,
          thutu: data.storeInfo.ordinalNumber,
          tonkho: data.storeInfo.isManageInventory,
          trangthai: data.storeInfo.status,
          ma_dv_fmis: data.storeInfo.codeFmis,
          DOITAC_ID:data.storeInfo.doitac_id
        },
        makho: data.storeInfo.storeCode,
        nhanvien_id: data.nhanvien_id,
        phanvung_id: selectedDepartment.PHANVUNG_ID,
        vdongbo: data.storeInfo.isSync,
        vkho_id: -1,
        vloaikho_id: data.storeInfo.selectedStoreTypeId,
        vtonkho: data.storeInfo.isManageInventory
      })

      if (resInsert.data.data.toString() !== '1') {
        result.isSuccess = false
        result.warnings.push(resInsert.data.data)
        return result
      }
    } else {
      console.log('state.stores.selectedStore', state.stores.selectedStore)
      let store=state.stores.selectedStore
      // update store
      const resUpdate = await api.updateModifiedStore(this._vm.axios, {
        cbodvichuquan: data.storeInfo.selectedDepartmentId,
        dvi_id: store.donvi_id?store.donvi_id:0,
        js_kho: {
          CHUNGTU: data.storeInfo.isUseInvoice,
          DONGBO: data.storeInfo.isSync,
          DONVI_ID: data.storeInfo.selectedDepartmentId,
          GHICHU: data.storeInfo.note,
          IP_CN: '',
          KHO_CHA_ID: data.storeInfo.selectedParentStoreId,
          KHO_ID: state.stores.selectedStore.kho_id,
          LOAIKHO_ID: data.storeInfo.selectedStoreTypeId,
          MAY_CN: '',
          MA_CSHT: data.storeInfo.codeCSHT,
          MA_DV_FMIS: data.storeInfo.tagFrm,
          MA_KHO: MA_KHO,
          MA_TK: data.storeInfo.storeAccount,
          NGAY_CN: '',
          NGUOI_CN: data.accountName,
          SO_DT: data.storeInfo.phone,
          TEN_KHO: data.storeInfo.storeName,
          THUKHO_ID: data.storeInfo.selectedEmployeeId,
          THUTU: data.storeInfo.ordinalNumber,
          TONKHO: data.storeInfo.isManageInventory,
          TRANGTHAI: data.storeInfo.status,
          chungtu: data.storeInfo.isUseInvoice,
          dongbo: data.storeInfo.isSync,
          donvi_ID: data.storeInfo.selectedDepartmentId,
          ghichu: data.storeInfo.note,
          ip_CN: '',
          kho_CHA_ID: data.storeInfo.selectedParentStoreId,
          kho_ID: state.stores.selectedStore.kho_id,
          loaikho_ID: data.storeInfo.selectedStoreTypeId,
          ma_CSHT: data.storeInfo.codeCSHT,
          MA_DV_FMIS: data.storeInfo.codeFmis,
          ma_KHO: MA_KHO,
          may_CN: '',
          ngay_CN: '',
          nguoi_CN: data.accountName,
          so_DT: data.storeInfo.phone,
          ten_KHO: data.storeInfo.storeName,
          thukho_ID: data.storeInfo.selectedEmployeeId,
          thutu: data.storeInfo.ordinalNumber,
          tonkho: data.storeInfo.isManageInventory,
          trangthai: data.storeInfo.status,
          ma_dv_fmis: data.storeInfo.codeFmis,
          DOITAC_ID:data.storeInfo.doitac_id
        },
        kho_id: state.stores.selectedStore.kho_id,
        khocha_moi: data.storeInfo.selectedParentStoreId,
        loaikho_id: data.storeInfo.selectedStoreTypeId,
        t_that: data.storeInfo.isManageInventory,
        tk_id: state.stores.selectedStore.tonkho,
        tonkho: data.storeInfo.isManageInventory
      })

      if (resUpdate.data.data.toString() !== '1') {
        result.isSuccess = false
        result.warnings.push(resUpdate.data.data)
        return result
      }
    }

    result.isSuccess = true
    result.warnings.push('Cập nhật dữ liệu kho thành công !')
    return result
  },
  async checkDataBeforeDelete ({ state, commit, rootState, rootGetters }, data) {
    // define result
    const result = {
      isSuccess: true,
      warnings: []
    }

    const resEmployeesAdded = await api.getEmployeesAdded(this._vm.axios, {
      kho_id: state.stores.selectedStore.kho_id
    })
    if (resEmployeesAdded.data.data.length > 0) {
      result.isSuccess = false
      result.warnings.push('Bạn phải xóa nhân viên đã gán trước khi xóa')
      return result
    }

    const resPurposesAdded = await api.getPurposesAdded(this._vm.axios, {
      kho_id: state.stores.selectedStore.kho_id,
      phanvung_id: (state.stores.selectedDepartment === null || state.stores.selectedDepartment === undefined)
        ? 0 : state.stores.selectedDepartment.PHANVUNG_ID
    })
    if (resPurposesAdded.data.data.length > 0) {
      result.isSuccess = false
      result.warnings.push('Bạn phải xóa mục đích đã gán trước khi xóa')
      return result
    }

    const resDepartmentAdded = await api.getDepartmentsAdded(this._vm.axios, {
      kho_id: state.stores.selectedStore.kho_id,
      phanvung_id: (state.stores.selectedDepartment === null || state.stores.selectedDepartment === undefined)
        ? 0 : state.stores.selectedDepartment.PHANVUNG_ID
    })
    if (resDepartmentAdded.data.data.length > 0) {
      result.isSuccess = false
      result.warnings.push('Bạn phải xóa đơn vị đã gán trước khi xóa')
      return result
    }

    const resCheckBeforeDelete = await api.checkBeforeDeleteStore(this._vm.axios, {
      phanvung_id: state.stores.selectedDepartment.PHANVUNG_ID,
      vkho_id: state.stores.selectedStore.kho_id
    })

    if (resCheckBeforeDelete.data.data.toString() === '1') {
      return result
    } else {
      result.isSuccess = false
      result.warnings.push(`Có lỗi khi xoá kho ${resCheckBeforeDelete.data.data}`)
      return result
    }
  },
  async insertEmployeesToStore ({ state, commit, rootState, rootGetters }, data) {
    await api.insertEmployeesToStore(this._vm.axios, {
      js_nv_kho: JSON.stringify(data),
      kho_id: state.stores.selectedStore.kho_id,
      phanvung_id: (state.stores.selectedDepartment === null || state.stores.selectedDepartment === undefined)
        ? 0 : state.stores.selectedDepartment.PHANVUNG_ID
    })
  },
  async deleteEmployeesFromStore ({ state, commit, rootState, rootGetters }, data) {
    await api.deleteEmployeesFromStore(this._vm.axios, {
      js_nv_kho: JSON.stringify(data),
      kho_id: state.stores.selectedStore.kho_id,
      phanvung_id: (state.stores.selectedDepartment === null || state.stores.selectedDepartment === undefined)
        ? 0 : state.stores.selectedDepartment.PHANVUNG_ID
    })
  },
  async insertDepartmentsToStore ({ state, commit, rootState, rootGetters }, data) {
    await api.insertDepartmentsToStore(this._vm.axios, {
      js_dv_kho: JSON.stringify(data),
      kho_id: state.stores.selectedStore.kho_id,
      phanvung_id: (state.stores.selectedDepartment === null || state.stores.selectedDepartment === undefined)
        ? 0 : state.stores.selectedDepartment.PHANVUNG_ID
    })
  },
  async deleteDepartmentsFromStore ({ state, commit, rootState, rootGetters }, data) {
    await api.deleteDepartmentsFromStore(this._vm.axios, {
      js_dv_kho: JSON.stringify(data),
      kho_id: state.stores.selectedStore.kho_id,
      phanvung_id: (state.stores.selectedDepartment === null || state.stores.selectedDepartment === undefined)
        ? 0 : state.stores.selectedDepartment.PHANVUNG_ID
    })
  },
  async insertPurposesToStore ({ state, commit, rootState, rootGetters }, data) {
    await api.insertPurposesToStore(this._vm.axios, {
      js_md_kho: JSON.stringify(data),
      phanvung_id: (state.stores.selectedDepartment === null || state.stores.selectedDepartment === undefined)
        ? 0 : state.stores.selectedDepartment.PHANVUNG_ID
    })
  },
  async deletePurposesFromStore ({ state, commit, rootState, rootGetters }, data) {
    await api.deletePurposesFromStore(this._vm.axios, {
      js_md_kho: JSON.stringify(data),
      phanvung_id: (state.stores.selectedDepartment === null || state.stores.selectedDepartment === undefined)
        ? 0 : state.stores.selectedDepartment.PHANVUNG_ID
    })
  },
  setSelectedCSHT ({ state, commit, rootState, rootGetters }, data) {
    commit('setSelectedCSHT', data)
  }
}

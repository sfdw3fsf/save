import api from '@/modules/admin/subscriber/ChangeFreeIPInfo/api/index'

export const actions = {
  clearSubcriberInfo ({ state, commit, rootState, rootGetters }, data) {
    commit('clearSubcriberInfo', data)
  },
  async setSubcriberInfo ({ state, commit, rootState, rootGetters }, data) {
    const resFindSubcriber = await api.findSubcriber(this._vm.axios, data)

    try {
      if (resFindSubcriber.data.data.length > 0) {
        const resSubcriberInfor = await api.getSubcriberInfor(this._vm.axios, {
          in_khachhang_id: resFindSubcriber.data.data[0].khachhang_id
        })

        if (resSubcriberInfor.data.data.length > 0) {
          const index = resSubcriberInfor.data.data.findIndex(x=>x.ma_tb == data.ma_tb)
          if (index>=0) commit('setSubcriberInfo', resSubcriberInfor.data.data[index])
          else commit('setSubcriberInfo', resSubcriberInfor.data.data[0])          
          const resSubcriberMoreInfor = await api.getSubcriberMoreInfor(this._vm.axios, {
            in_thuebao_id: resSubcriberInfor.data.data[0].thuebao_id
          })
          if (resSubcriberMoreInfor.data.data.length > 0) {
            commit('setSubcriberMoreInfo', resSubcriberMoreInfor.data.data[0])
          }
        }
      } else {
        this._vm.$toast.error('Không tìm thấy thông tin! Bạn hãy nhập lại thông tin tìm kiếm')
      }
    } catch (error) {
      throw error
    }
  },
  async submitChangeSubcriber ({ state, commit, rootState, rootGetters }, data) {
    let totalFee = state.subcriberInfo.totalFee
    if (state.subcriberInfo.totalFee) {
      totalFee = state.subcriberInfo.totalFee
    } else { totalFee = null }

    let pricePerMB = state.subcriberInfo.pricePerMB
    if (state.subcriberInfo.pricePerMB) {
      pricePerMB = state.subcriberInfo.pricePerMB
    } else { pricePerMB = null }

    let freeFee = state.subcriberInfo.freeFee
    if (state.subcriberInfo.freeFee) {
      freeFee = state.subcriberInfo.freeFee
    } else { freeFee = null }

    let subcriberFee = state.subcriberInfo.subcriberFee
    if (state.subcriberInfo.subcriberFee) {
      subcriberFee = state.subcriberInfo.subcriberFee
    } else { subcriberFee = null }

    let packageFee = state.subcriberInfo.packageFee
    if (state.subcriberInfo.packageFee) {

    } else { packageFee = null }

    let pricePerEmail = state.subcriberInfo.pricePerEmail
    if (state.subcriberInfo.pricePerEmail) {
      pricePerEmail = state.subcriberInfo.pricePerEmail
    } else { pricePerEmail = null }

    let capacityFree = state.subcriberInfo.capacityFree
    if (state.subcriberInfo.capacityFree) {
      capacityFree = state.subcriberInfo.capacityFree
    } else { capacityFree = null }

    let ipStaticFree = state.subcriberInfo.ipStaticFree
    if (state.subcriberInfo.ipStaticFree) {
      ipStaticFree = state.subcriberInfo.ipStaticFree
    } else { ipStaticFree = null }

    let dateDecrease = state.subcriberInfo.dateDecrease
    if (state.subcriberInfo.dateDecrease) {
      dateDecrease = state.subcriberInfo.dateDecrease
    } else { dateDecrease = null }

    let decreaseEmailStartDate = state.subcriberInfo.decreaseEmailStartDate
    if (state.subcriberInfo.decreaseEmailStartDate) {
      decreaseEmailStartDate = state.subcriberInfo.decreaseEmailStartDate
    } else { decreaseEmailStartDate = null }

    let dateDecreaseIp = state.subcriberInfo.dateDecreaseIp
    if (state.subcriberInfo.dateDecreaseIp) {
      dateDecreaseIp = state.subcriberInfo.dateDecreaseIp
    } else { dateDecreaseIp = null }

    let emailQtyMoreRent = state.subcriberInfo.emailQtyMoreRent
    if (state.subcriberInfo.emailQtyMoreRent) {
      emailQtyMoreRent = state.subcriberInfo.emailQtyMoreRent
    } else { emailQtyMoreRent = null }

    let monthsApplyDiscount = state.subcriberInfo.monthsApplyDiscount
    if (state.subcriberInfo.monthsApplyDiscount) {
      monthsApplyDiscount = state.subcriberInfo.monthsApplyDiscount
    } else { monthsApplyDiscount = null }

    let emailDecreaseTime = state.subcriberInfo.emailDecreaseTime
    if (state.subcriberInfo.emailDecreaseTime) {
      emailDecreaseTime = state.subcriberInfo.emailDecreaseTime
    } else { emailDecreaseTime = null }

    let monthsIpDecrease = state.subcriberInfo.monthsIpDecrease
    if (state.subcriberInfo.monthsIpDecrease) {
      monthsIpDecrease = state.subcriberInfo.monthsIpDecrease
    } else { monthsIpDecrease = null }

    let discount = state.subcriberInfo.discount
    if (state.subcriberInfo.discount) {
      discount = state.subcriberInfo.discount
    } else { discount = null }

    let discountRatio = state.subcriberInfo.discountRatio
    if (state.subcriberInfo.discountRatio) {
      discountRatio = state.subcriberInfo.discountRatio
    } else { discountRatio = null }

    let emailDecreaseRatio = state.subcriberInfo.emailDecreaseRatio
    if (state.subcriberInfo.emailDecreaseRatio) {
      emailDecreaseRatio = state.subcriberInfo.emailDecreaseRatio
    } else { emailDecreaseRatio = null }

    let ipDecreaseRatio = state.subcriberInfo.ipDecreaseRatio
    if (state.subcriberInfo.ipDecreaseRatio) {
      ipDecreaseRatio = state.subcriberInfo.ipDecreaseRatio
    } else { ipDecreaseRatio = null }

    const billingInforData = {
      accountModel: {
        accountName: state.subcriberInfo.subcriberCode
      },
      note: state.subcriberInfo.note,
      billingInfo:
      `cuoctb:${subcriberFee};` +
      `cuockh:${totalFee};` +
      `cuoctg:${packageFee};` +
      `tlgiam:${discountRatio};` +
      `ngiam:${dateDecrease};` +
      `thgiam:${monthsApplyDiscount};` +
      `mienip:${ipStaticFree};` +
      `slemail:${emailQtyMoreRent};` +
      `dgemail:${pricePerEmail};` +
      `thgiamemail:${emailDecreaseTime};` +
      `ngiamemail:${decreaseEmailStartDate};` +
      `tlgiamemail:${emailDecreaseRatio};` +
      `tlgiamip:${ipDecreaseRatio};` +
      `ngiamip:${dateDecreaseIp};` +
      `thgiamip:${monthsIpDecrease};` +
      `cuocmb:${pricePerMB};` +
      `mbmphi:${capacityFree};` +
      `cuocmphi:${freeFee};` +
      `tiengiam:${discount};`
      // 'cuockh:33;cuocmb:10;cuocmphi:11;cuoctb:1000000;cuoctg:650000;dgemail:1;mbmphi:1;mienip:0;ngiam:10/11/2021;ngiamemail:10/11/2021;ngiamip:10/11/2021;slemail:1;thgiam:11;thgiamemail:1;thgiamip:11;tiengiam:12;tlgiam:11;tlgiamemail:1;tlgiamip:11',
    }

    const feeData = {
      tt_tinhcuoc: billingInforData.billingInfo,
      thuebao_id: state.subcriberInfo.subCriberId
    }

    const resBillingInfor = await api.submitChangeSubcriberBillingInfo(this._vm.axios, billingInforData)
    const successMessage = `Thay đổi thông tin tính cước cho thuê bao ${state.subcriberInfo.subcriberCode} thành công!`
    if (resBillingInfor.data.errorCode === 0) {
      await api.submitChangeSubcriberFee(this._vm.axios, feeData)
      this._vm.$toast.success(successMessage)
    } else {
      this._vm.$toast.error(resBillingInfor.data.faultString)
    }
  },
  setSubcriberCode ({ state, commit, rootState, rootGetters }, data) {
    commit('setSubcriberCode', data)
  },
  setSubcriberTypes ({ state, commit, rootState, rootGetters }, data) {
    commit('setSubcriberTypes', data)
  },
  setAddress ({ state, commit, rootState, rootGetters }, address) {
    commit('setAddress', address)
  },
  setSubcriberName ({ state, commit, rootState, rootGetters }, subcriberName) {
    commit('setSubcriberName', subcriberName)
  },
  setSelectedSubcriberType ({ state, commit, rootState, rootGetters }, selectedSubcriberType) {
    commit('setSelectedSubcriberType', selectedSubcriberType)
  },
  setSubcriberFee ({ state, commit, rootState, rootGetters }, subcriberFee) {
    commit('setSubcriberFee', subcriberFee)
  },
  setTotalFee ({ state, commit, rootState, rootGetters }, totalFee) {
    commit('setTotalFee', totalFee)
  },
  setIpStaticFree ({ state, commit, rootState, rootGetters }, ipStaticFree) {
    commit('setIpStaticFree', ipStaticFree)
  },
  setDateDecrease ({ state, commit, rootState, rootGetters }, dateDecrease) {
    commit('setDateDecrease', dateDecrease)
  },
  setDateDecreaseIp ({ state, commit, rootState, rootGetters }, dateDecreaseIp) {
    commit('setDateDecreaseIp', dateDecreaseIp)
  },
  setEmailQtyMoreRent ({ state, commit, rootState, rootGetters }, emailQtyMoreRent) {
    commit('setEmailQtyMoreRent', emailQtyMoreRent)
  },
  setEmailDecreaseRatio ({ state, commit, rootState, rootGetters }, emailDecreaseRatio) {
    commit('setEmailDecreaseRatio', emailDecreaseRatio)
  },
  setEmailDecreaseTime ({ state, commit, rootState, rootGetters }, emailDecreaseTime) {
    commit('setEmailDecreaseTime', emailDecreaseTime)
  },
  setCapacityFree ({ state, commit, rootState, rootGetters }, capacityFree) {
    commit('setCapacityFree', capacityFree)
  },
  setDiscount ({ state, commit, rootState, rootGetters }, discount) {
    commit('setDiscount', discount)
  },
  setPackageFee ({ state, commit, rootState, rootGetters }, packageFee) {
    commit('setPackageFee', packageFee)
  },
  setDiscountRatio ({ state, commit, rootState, rootGetters }, discountRatio) {
    commit('setDiscountRatio', discountRatio)
  },
  setMonthsApplyDiscount ({ state, commit, rootState, rootGetters }, monthsApplyDiscount) {
    commit('setMonthsApplyDiscount', monthsApplyDiscount)
  },
  setIpDecreaseRatio ({ state, commit, rootState, rootGetters }, ipDecreaseRatio) {
    commit('setIpDecreaseRatio', ipDecreaseRatio)
  },
  setMonthsIpDecrease ({ state, commit, rootState, rootGetters }, monthsIpDecrease) {
    commit('setMonthsIpDecrease', monthsIpDecrease)
  },
  setPricePerEmail ({ state, commit, rootState, rootGetters }, pricePerEmail) {
    commit('setPricePerEmail', pricePerEmail)
  },
  setDecreaseEmailStartDate ({ state, commit, rootState, rootGetters }, decreaseEmailStartDate) {
    commit('setDecreaseEmailStartDate', decreaseEmailStartDate)
  },
  setPricePerMB ({ state, commit, rootState, rootGetters }, pricePerMB) {
    commit('setPricePerMB', pricePerMB)
  },
  setFreeFee ({ state, commit, rootState, rootGetters }, freeFee) {
    commit('setFreeFee', freeFee)
  },
  setNote ({ state, commit, rootState, rootGetters }, note) {
    commit('setNote', note)
  }
}

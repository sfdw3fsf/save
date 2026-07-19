export const mutations = {
  clearSubcriberInfo (state, data) {
    // general information
    state.subcriberInfo.subcriberName = ''
    state.subcriberInfo.address = ''
    state.subcriberInfo.selectedSubcriberType = 0
    state.subcriberInfo.selectedOldSpeed = 0
    state.subcriberInfo.subCriberId = 0
    state.subcriberInfo.subcriberCode = null

    // fee information
    state.subcriberInfo.subcriberFee = null
    state.subcriberInfo.totalFee = null
    state.subcriberInfo.dateDecrease = ''
    state.subcriberInfo.momentFormat = ''
    state.subcriberInfo.ipStaticFree = ''
    state.subcriberInfo.dateDecreaseIp = ''
    state.subcriberInfo.emailQtyMoreRent = null
    state.subcriberInfo.emailDecreaseRatio = null
    state.subcriberInfo.emailDecreaseTime = null
    state.subcriberInfo.capacityFree = null
    state.subcriberInfo.discount = null
    state.subcriberInfo.packageFee = null
    state.subcriberInfo.discountRatio = null
    state.subcriberInfo.monthsApplyDiscount = null
    state.subcriberInfo.ipDecreaseRatio = null
    state.subcriberInfo.monthsIpDecrease = null
    state.subcriberInfo.pricePerEmail = null
    state.subcriberInfo.decreaseEmailStartDate = ''
    state.subcriberInfo.pricePerMB = null
    state.subcriberInfo.freeFee = null
    state.subcriberInfo.note = null
  },
  setSubcriberInfo (state, data) {
    state.subcriberInfo.subCriberId = data.thuebao_id
    state.subcriberInfo.subcriberName = data.ten_tb
    state.subcriberInfo.address = data.diachi_tb
    state.subcriberInfo.selectedSubcriberType = data.loaitb_id
    // state.subcriberInfo.subcriberFee = data.cuoc_tb
    state.subcriberInfo.note = data.ghichu
  },
  setSubcriberMoreInfo (state, data) {
    if (data.thongtin_tc) {
      const subcriberInforString = data.thongtin_tc.trim()
      const subcriberInforOptimized = subcriberInforString.replace(' ', '')
      subcriberInforOptimized.split(';').forEach((s, i) => {
        const sText = String(s)
        const sTextData = sText.substring(sText.indexOf(':') + 1)
        switch (i) {
          case 0 :
            state.subcriberInfo.subcriberFee = sTextData
            break
          case 1 :
            state.subcriberInfo.totalFee = sTextData
            break
          case 2 :
            state.subcriberInfo.packageFee = sTextData
            break
          case 3 :
            state.subcriberInfo.discountRatio = sTextData
            break
          case 4 :
            state.subcriberInfo.dateDecrease = sTextData
            break
          case 5 :
            state.subcriberInfo.monthsApplyDiscount = sTextData
            break
          case 6 :
            state.subcriberInfo.ipStaticFree = sTextData
            break
          case 7 :
            state.subcriberInfo.emailQtyMoreRent = sTextData
            break
          case 8 :
            state.subcriberInfo.pricePerEmail = sTextData
            break
          case 9 :
            state.subcriberInfo.emailDecreaseRatio = sTextData
            break
          case 10 :
            state.subcriberInfo.decreaseEmailStartDate = sTextData
            break
          case 11 :
            state.subcriberInfo.emailDecreaseTime = sTextData
            break
          case 12 :
            state.subcriberInfo.ipDecreaseRatio = sTextData
            break
          case 13 :
            state.subcriberInfo.dateDecreaseIp = sTextData
            break
          case 14 :
            state.subcriberInfo.monthsIpDecrease = sTextData
            break
          case 15 :
            state.subcriberInfo.pricePerMB = sTextData
            break
          case 16 :
            state.subcriberInfo.capacityFree = sTextData
            break
          case 17 :
            state.subcriberInfo.freeFee = sTextData
            break
          case 18 :
            state.subcriberInfo.discount = sTextData
            break
        }
      })
    }
  },
  setSubcriberCode (state, subcriberCode) {
    state.subcriberInfo.subcriberCode = subcriberCode
  },
  setSubcriberTypes (state, loadedSubcriberTypeList) {
    state.subcriberInfo.subcriberTypes = []
    for (let i = 0; i < loadedSubcriberTypeList.length; i++) {
      state.subcriberInfo.subcriberTypes.push(
        {
          text: loadedSubcriberTypeList[i].LOAIHINH_TB,
          value: loadedSubcriberTypeList[i].LOAITB_ID
        }
      )
    }
  },
  setAddress (state, address) {
    state.subcriberInfo.address = address
  },
  setSubcriberName (state, subcriberName) {
    state.subcriberInfo.subcriberName = subcriberName
  },
  setSelectedSubcriberType (state, selectedSubcriberType) {
    state.subcriberInfo.selectedSubcriberType = selectedSubcriberType
  },
  setSubcriberFee (state, subcriberFee) {
    state.subcriberInfo.subcriberFee = subcriberFee
  },
  setTotalFee (state, totalFee) {
    state.subcriberInfo.totalFee = totalFee
  },
  setIpStaticFree (state, ipStaticFree) {
    state.subcriberInfo.ipStaticFree = ipStaticFree
  },
  setDateDecrease (state, dateDecrease) {
    state.subcriberInfo.dateDecrease = dateDecrease
  },
  setDateDecreaseIp (state, dateDecreaseIp) {
    state.subcriberInfo.dateDecreaseIp = dateDecreaseIp
  },
  setEmailQtyMoreRent (state, emailQtyMoreRent) {
    state.subcriberInfo.emailQtyMoreRent = emailQtyMoreRent
  },
  setEmailDecreaseRatio (state, emailDecreaseRatio) {
    state.subcriberInfo.emailDecreaseRatio = emailDecreaseRatio
  },
  setEmailDecreaseTime (state, emailDecreaseTime) {
    state.subcriberInfo.emailDecreaseTime = emailDecreaseTime
  },
  setCapacityFree (state, capacityFree) {
    state.subcriberInfo.capacityFree = capacityFree
  },
  setDiscount (state, discount) {
    state.subcriberInfo.discount = discount
  },
  setPackageFee (state, packageFee) {
    state.subcriberInfo.packageFee = packageFee
  },
  setDiscountRatio (state, discountRatio) {
    state.subcriberInfo.discountRatio = discountRatio
  },
  setMonthsApplyDiscount (state, monthsApplyDiscount) {
    state.subcriberInfo.monthsApplyDiscount = monthsApplyDiscount
  },
  setIpDecreaseRatio (state, ipDecreaseRatio) {
    state.subcriberInfo.ipDecreaseRatio = ipDecreaseRatio
  },
  setMonthsIpDecrease (state, monthsIpDecrease) {
    state.subcriberInfo.monthsIpDecrease = monthsIpDecrease
  },
  setPricePerEmail (state, pricePerEmail) {
    state.subcriberInfo.pricePerEmail = pricePerEmail
  },
  setDecreaseEmailStartDate (state, decreaseEmailStartDate) {
    state.subcriberInfo.decreaseEmailStartDate = decreaseEmailStartDate
  },
  setPricePerMB (state, pricePerMB) {
    state.subcriberInfo.pricePerMB = pricePerMB
  },
  setFreeFee (state, freeFee) {
    state.subcriberInfo.freeFee = freeFee
  },
  setNote (state, note) {
    state.subcriberInfo.note = note
  }
}

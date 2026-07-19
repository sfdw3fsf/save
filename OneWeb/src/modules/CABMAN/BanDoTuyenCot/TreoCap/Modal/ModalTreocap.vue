<template src="./ModalTreocap.html"></template>

<script>
export default {
  name: 'ModalTreocap',
  props: ['modalId', 'dataCot'],

  computed: {

  },
  watch: {
    'txtQuery': function (val) {
      if (val.length > 2) {
        this.goi_nhac_timkiem()
      }
    }
  },
  methods: {
    focusMyElement: async function () {
      try {
        this.dsTempData = []
        this.dsCap = []
        this.dsCapDaTreo = []
        this.dsCapChuaTreo = []
        this.dscapDaTreoId = []
        this.dscapChuaTreoId = []
        this.dataSelected.TTVT = null
        this.dataSelected.ToQL = null
        this.dataSelected.TongDai = null
        this.dataSelected.NhomCap = 'Cáp quang'
        this.dataSelected.CapGoc = null

        this.$root.showLoading(true)
        await this.getTTVT()
        await this.getNhomCap()
        this.cotid = this.dataCot
        await this.chitietCot()
        await this.getdsCapDaTreo()
      } catch (er) {} finally {
        this.$root.showLoading(false)
      }
    },
    getTTVT: async function () {
      try {
        var rs = await this.$root.context.get(
          '/web-cabman/bando_tuyencot/dsttvt_theonv'
        )
        this.dsTTVT = rs.data
        this.dataSelected.TTVT = this.dsTTVT[0].DONVI_ID
        this.getToQL()
      } catch (error) {
      } finally {
      }
    },
    getToQL: async function () {
      try {
        var rs = await this.$root.context.get(
          '/web-cabman/bando_tuyencot/ds_to_kythuat_theonv/' +
            this.dataSelected.TTVT
        )
        this.dsToQL = rs.data
        this.dataSelected.ToQL = this.dsToQL[0].DONVI_ID
        this.getTongDai()
      } catch (error) {
      } finally {
      }
    },
    getTongDai: async function () {
      try {
        var rs = await this.$root.context.get(
          '/web-cabman/bando_tuyencot/ds_tram_tong_dai/' +
            this.dataSelected.ToQL
        )
        this.dsTongDai = rs.data
        this.dataSelected.TongDai = this.dsTongDai[0].DONVI_ID
        this.getCapGoc()
      } catch (error) {
      } finally {
      }
    },
    getNhomCap: async function () {
      try {
        var rs = await this.$root.context.get(
          '/web-cabman/bando_tuyencot/ds_loai_nhom_cap'
        )
        console.log('nhóm', rs.data)
        this.dsNhomCap = rs.data
      } catch (error) {
      } finally {
      }
    },
    getCapGoc: async function () {
      try {
        let loai_cap = this.dataSelected.NhomCap == 'Cáp quang' ? 1 : 0
        var rs = await this.$root.context.get(
          '/web-cabman/bando_tuyencot/ds_cap_goc',
          { loai_nhom_cap: loai_cap, tong_dai_id: this.dataSelected.TongDai }
        )
        this.dsCapGoc = rs.data
      } catch (error) {
      } finally {
      }
    },
    getdsCapDaTreo: async function () {
      try {
        var rs = await this.$root.context.get(
          '/web-cabman/bando_tuyencot/ds_cap_da_treo/' + this.cotid
        )
        this.dsCapDaTreo = rs.data
      } catch (error) {
      } finally {
      }
    },
    getdsCapChuaTreo: async function () {
      try {
        var rs = await this.$root.context.post(
          '/web-cabman/bando_tuyencot/ds_cap_chua_treo',
          { cot_id: this.cotid, cap_goc_id: this.dataSelected.CapGoc }
        )
        this.dsCapChuaTreo = rs.data
      } catch (error) {
      } finally {
      }
    },
    chitietCot: async function () {
      try {
        var rs = await this.$root.context.get(
          '/web-cabman/bando_tuyencot/chitiet_cot_theo_id/' + this.cotid
        )
        rs.data
        this.tenCot = rs.data.TEN_COT
        this.diaChi = rs.data.DIACHI
        this.slCap = rs.data.SO_CAPTREO
      } catch (error) {
      } finally {
      }
    },
    chonCaptheoKyhieu: async function () {
      if (this.txtQuery != '') {
        this.$root.showLoading(true)
        try {
          var rs = await this.$root.context.post(
            '/web-cabman/bando_tuyencot/ds_cap_da_treo_theoky_hieu',
            {
              cot_id: this.cotid,
              kyhieu: this.txtQuery
            }
          )
          this.dsCapDaTreo = rs.data
        } catch (error) {
        } finally {
          this.$root.showLoading(false)
        }
      } else {
        this.$toast.warning('Chưa nhập ký hiệu tìm kiếm')
      }
    },
    onChangeTTVT: async function () {
      await this.getToQL()
    },
    onChangeToQL: async function () {
      await this.getTongDai()
    },
    onChangeTongDai: async function () {
      await this.getCapGoc()
    },
    onChangeNhomCap: async function () {
      await this.getCapGoc()
    },
    onChangeCapGoc: async function () {
      await this.getdsCapChuaTreo()
    },

    selectChuaTreo_selectedItemsChanged: function (dataList) {
      this.dscapChuaTreoId = dataList
      console.log(dataList)
    },
    gridChuaTreo_selectedRowChanged: function (dataList) {

    },
    selectDaTreo_selectedItemsChanged: function (dataList) {
      this.dscapDaTreoId = dataList
      console.log(dataList)
    },
    gridDaTreo_selectedRowChanged: function (dataList) {

    },
    goCap: async function () {
      try {
        this.$root.showLoading(true)
        let dsTemp = [...this.dsCapChuaTreo]
        for (let i = 0; i < this.dscapDaTreoId.length; i++) {
          let cap = this.dsCapDaTreo.filter(
            (x) => x.CAP_ID == this.dscapDaTreoId[i]
          )[0]
          this.dsCapDaTreo = this.dsCapDaTreo.filter(
            (x) => x.CAP_ID != this.dscapDaTreoId[i]
          )
          let data = {
            CAP_ID: cap.CAP_ID,
            TEN_CAP: cap.TEN_CAP,
            KYHIEU: cap.KYHIEU,
            ISCHECK: '0'
          }
          dsTemp.push(data)
        }
        this.dsCapChuaTreo = dsTemp
      } catch (error) {
        console.log('🚀 ~ file: ModalTreocap.vue:209 ~ error:', error)
        this.$toast.error('Có lỗi xảy ra')
      } finally {
        this.$root.showLoading(false)
      }
    },
    treoCap: async function () {
      try {
        this.$root.showLoading(true)
        let dsTemp = [...this.dsCapDaTreo]
        for (let i = 0; i < this.dscapChuaTreoId.length; i++) {
          let cap = this.dsCapChuaTreo.filter(
            (x) => x.CAP_ID == this.dscapChuaTreoId[i]
          )[0]
          this.dsCapChuaTreo = this.dsCapChuaTreo.filter(
            (x) => x.CAP_ID != this.dscapChuaTreoId[i]
          )
          let data = {
            CAP_ID: cap.CAP_ID,
            TEN_CAP: cap.TEN_CAP,
            KYHIEU: cap.KYHIEU,
            ISCHECK: '0'
          }
          dsTemp.push(data)
        }
        this.dsCapDaTreo = dsTemp
      } catch (error) {
        console.log('🚀 ~ file: ModalTreocap.vue:235 ~ error:', error)
        this.$toast.error('Có lỗi xảy ra')
      } finally {
        this.$root.showLoading(false)
      }
    },
    capNhat: async function () {
      try {
        this.$root.showLoading(true)
        var rs_xoa = await this.$root.context.post(
          '/web-cabman/bando_tuyencot/xoa_cap_theo_cot/' + this.cotid
        )
        if (rs_xoa.error_code == 'BSS-00000000') {
          if (this.dsCapDaTreo != null && this.dsCapDaTreo.length > 0) {
            for (let i = 0; i < this.dsCapDaTreo.length; i++) {
              let data = {
                cap_id: this.dsCapDaTreo[i].CAP_ID,
                cot_id: this.cotid
              }
              var rs = await this.$root.context.post(
                '/web-cabman/bando_tuyencot/treo_cap/',
                data
              )
            }
            this.$toast.success('Gán thành công')
          }
        } else {
          this.$toast.error('Có lỗi xảy ra')
        }
      } catch (error) {
        console.log('🚀 ~ file: ModalTreocap.vue:264 ~ error:', error)
        this.$toast.error('Có lỗi xảy ra')
      } finally {
        this.$root.showLoading(false)
      }
    },
    timkiem: function (e) {
      if (e.key == 'Enter') {
        this.goi_nhac_timkiem()
      }
      if (e.key == 'ArrowDown') {
        this.showGoiNhac = false
        this.selectedCap++
        let cap = this.dsCap[this.selectedCap]
        this.selectCap(cap, this.selectedCap)
      }
      if (e.key == 'ArrowUp') {
        this.showGoiNhac = false
        this.selectedCap--
        let cap = this.dsCap[this.selectedCap]
        this.selectCap(cap, this.selectedCap)
      }
    },
    goi_nhac_timkiem: async function () {
      try {
        this.sleep(500)
        this.$root.showLoading(true)
        if (this.txtQuery == '' || this.txtQuery.length <= 2) return
        var rs = await this.$root.context.get(
          '/web-cabman/bando_tuyencot/goinhac_timkiem', {query: this.txtQuery}
        )
        this.dsTempData = rs.data
        this.mapTemptoCap()
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    thong_tin_cap: async function (cap_id) {
      try {
        var rs = await this.$root.context.get(
          '/web-cabman/bando_tuyencot/thong_tin_cap/' + cap_id
        )
        return rs.data
      } catch (error) {
      } finally {
      }
    },
    mapTemptoCap: async function () {
      let dsCap = []
      for (let i = 0; i < this.dsTempData.length; i++) {
        let cap = await this.thong_tin_cap(this.dsTempData[i].SEARCH_QUERY.split('|')[1])
        if (cap != null) { dsCap.push(cap) }
      }
      this.dsCap = dsCap
      this.showGoiNhac = true
    },
    selectCap: function (cap, index) {
      this.selectedCap = index
      this.showGoiNhac = false
      let dsTem = [...this.dsCapDaTreo]
      if (this.dsCapDaTreo.filter(x => x.CAP_ID == cap.CAP_ID).length == 0) {
        dsTem.push(cap)
        this.dsCapDaTreo = dsTem
      } else { this.$root.toastError('Cáp đã tồn tại trong danh sách!') }
    },
    isSelected: function (i) {
      return i === this.selectedCap
    },
    search_mousedown: function () {
      if (this.dsCap != null && this.dsCap.length > 0 && !this.showGoiNhac) { this.showGoiNhac = true }
    },
    sleep: function (ms) {
      return new Promise((resolve) => {
        setTimeout(resolve, ms)
      })
    },
    show: function () {
    	this.showGoiNhac = true
    },
    hide: function () {
    //	console.log('hide')
    	this.showGoiNhac = false
    },
    closeEvent: function () {
    //  console.log('close event called')
      this.hide()
    }
  },
  data: function () {
    return {
      fieldsCap: { text: 'loai_cap' },
      cotid: null,
      dsCapDaTreo: [],
      dsCapChuaTreo: [],
      dscapDaTreoId: [],
      dscapChuaTreoId: [],
      dsCapGoc: [],
      tenCot: '',
      diaChi: '',
      slCap: 0,
      dsTTVT: [],
      dsToQL: [],
      dsTongDai: [],
      dsNhomCap: [],
      dataSelected: {
        TTVT: null,
        ToQL: null,
        TongDai: null,
        NhomCap: 'Cáp quang',
        CapGoc: null
      },
      kyhieu: '',
      txtQuery: '',
      dsTempData: [],
      dsCap: [],
      selectedCap: 0,
      showGoiNhac: false
    }
  },
  directives: {
    'click-outside': {
      bind: function (el, binding, vNode) {
        // Provided expression must evaluate to a function.
        if (typeof binding.value !== 'function') {
        	const compName = vNode.context.name
          let warn = `[Vue-click-outside:] provided expression '${binding.expression}' is not a function, but has to be`
          if (compName) { warn += `Found in component '${compName}'` }

          console.warn(warn)
        }
        // Define Handler and cache it on the element
        const bubble = binding.modifiers.bubble
        const handler = (e) => {
          if (bubble || (!el.contains(e.target) && el !== e.target)) {
          	binding.value(e)
          }
        }
        el.__vueClickOutside__ = handler

        // add Event Listeners
        document.addEventListener('click', handler)
      },

      unbind: function (el, binding) {
        // Remove Event Listeners
        document.removeEventListener('click', el.__vueClickOutside__)
        el.__vueClickOutside__ = null
      }
    }
  }
}
</script>
<style>
div#popupTCTTTB___BV_modal_outer_ {
  z-index: 10005 !important;
  margin-top: 100px;
}
.dashboard-header {
  z-index: 9 !important;
}
a.disabled {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>

<template src="./ModalTreocap.html"></template>
<script>
export default {
  name: 'ModalChuiCap',
  props: ['modalId', 'data'],
  created: async function () {
  },

  computed: {},
  methods: {
    focusMyElement: async function () {
      this.capId = this.data.CAP_ID
      this.dataSelected.TTVT = null
      this.dataSelected.ToQL = null
      this.dataSelected.tuyenCotId = null
      this.dsCotChuaTreo = []
      await this.getTTVT()
      await this.getdsCotDaTreo()
    },
    getTTVT: async function () {
      try {
        let rs = await this.$root.context.get(
          '/web-cabman/BanDoTuyenCong/ds-ttvt-theo-nhanvien'
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
        let rs = await this.$root.context.get(
          '/web-cabman/BanDoTuyenCong/ds-to-kythuat-theo-nhanvien/' +
                        this.dataSelected.TTVT
        )
        this.dsToQL = rs.data
        this.dataSelected.ToQL = this.dsToQL[0].DONVI_ID
        this.getTuyenCot()
      } catch (error) {
      } finally {
      }
    },
    getTuyenCot: async function () {
      try {
        let rs = await this.$root.context.get(
          '/web-cabman/bando_tuyencot/ds_tuyencot/' + this.dataSelected.ToQL
        )
        this.dsTuyenCot = rs.data
        this.dataSelected.tuyenCotId = this.dsTuyenCot[0].TUYENCOT_ID
        this.getdsCotChuaTreo()
      } catch (error) {
      } finally {
      }
    },

    getdsCotDaTreo: async function () {
      try {
        let rs = await this.$root.context.get(
          '/web-cabman/mang-truyen-dan/lay-danh-sach-cot-da-treo?capId=' + this.capId
        )
        this.dsCotDaTreo = rs.data
      } catch (error) {
      } finally {
      }
    },
    getdsCotChuaTreo: async function () {
      try {
        let rs = await this.$root.context.get(
          '/web-cabman/mang-truyen-dan/lay-danh-sach-cot-chua-treo',
          { capId: this.capId, tuyenCotId: this.dataSelected.tuyenCotId }
        )
        this.dsCotChuaTreo = rs.data
        console.log('🚀 ~ file: ModalTreocap.vue ~ line 75 ~ this.dsCotChuaTreo', this.dsCotChuaTreo)
      } catch (error) {
      } finally {
      }
    },

    onChangeTTVT: async function () {
      await this.getToQL()
    },
    onChangeToQL: async function () {
      await this.getTuyenCot()
    },
    onChangeTuyen: async function () {
      await this.getdsCotChuaTreo()
    },

    selectChuaChui_selectedItemsChanged: function (dataList) {
      this.dsCotChuaTreoId = dataList
    },
    gridChuaChui_selectedRowChanged: function (dataList) {

    },
    selectDaChui_selectedItemsChanged: function (dataList) {
      this.dsCotDaTreoId = dataList
    },
    gridDaChui_selectedRowChanged: function (dataList) {

    },
    goCap: async function () {
      try {
        let dsTam = JSON.parse(JSON.stringify(this.dsCotChuaTreo))
        this.$root.showLoading(true)
        for (let i = 0; i < this.dsCotDaTreoId.length; i++) {
          let cot = this.dsCotDaTreo.filter(
            (x) => x.COT_ID == this.dsCotDaTreoId[i]
          )[0]
          this.dsCotDaTreo = this.dsCotDaTreo.filter(
            (x) => x.COT_ID != this.dsCotDaTreoId[i]
          )
          let data = {
            TEN_COT: cot.TEN_COT,
            COT_ID: cot.COT_ID,
            DIACHI: cot.DIACHI,
            ISCHECK: '0',
            TONGCAP: cot.TONGCAP,
            TENCHUQUAN: cot.TENCHUQUAN
          }
          dsTam.push(data)
        }
        this.dsCotChuaTreo = dsTam
        this.isDisableBtnCapNhat = false
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    treoCap: async function () {
      try {
        let dsTam = JSON.parse(JSON.stringify(this.dsCotDaTreo))
        this.$root.showLoading(true)

        for (let i = 0; i < this.dsCotChuaTreoId.length; i++) {
          let cot = this.dsCotChuaTreo.filter(
            (x) => x.COT_ID == this.dsCotChuaTreoId[i]
          )[0]
          this.dsCotChuaTreo = this.dsCotChuaTreo.filter(
            (x) => x.COT_ID != this.dsCotChuaTreoId[i]
          )
          let data = {
            TEN_COT: cot.TEN_COT,
            COT_ID: cot.COT_ID,
            DIACHI: cot.DIACHI,
            ISCHECK: '0',
            TONGCAP: cot.TONGCAP,
            TENCHUQUAN: cot.TENCHUQUAN
          }
          dsTam.push(data)
        }
        this.dsCotDaTreo = dsTam
        this.isDisableBtnCapNhat = false
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    capNhat: async function () {
      try {
        this.$root.showLoading(true)
        let dsCot = []
        for (let i = 0; i < this.dsCotDaTreo.length; i++) {
          let item = {cotId: this.dsCotDaTreo[i].COT_ID}
          dsCot.push(item)
        }
        let data = {
          dsCot: dsCot,
          capId: this.capId,
          loaiCapId: -1
        }
        let rs = await this.$root.context.post(
          '/web-cabman/mang-truyen-dan/treo-cap-len-cot/', data
        )
        if (rs.error_code == 'BSS-00000000') {
          this.$toast.success('Gán thành công')
        }
      } catch (error) {
        this.$toast.error('Có lỗi xảy ra trong khi lưu: ' + error.response.message)
      } finally {
        this.$root.showLoading(false)
      }
    }
  },
  data: function () {
    return {
      capId: null,
      dsCotDaTreo: [],
      dsCotChuaTreo: [],
      dsTTVT: [],
      dsToQL: [],
      dsTuyenCot: [],
      isDisableBtnCapNhat: true,
      dataSelected: {
        TTVT: null,
        ToQL: null,
        tuyenCotId: null
      }
    }
  }
}
</script>
<style>
  .grid-stack-box {
    background-color: transparent;
  }
  div#popupTCTTTB___BV_modal_outer_ {
    z-index: 10 !important;
    margin-top: 100px;
  }
  .dashboard-header {
    z-index: 9 !important;
  }
  a.disable-btn {
    pointer-events: none;
    cursor: default;
    color: #6c757d !important;
  }
</style>

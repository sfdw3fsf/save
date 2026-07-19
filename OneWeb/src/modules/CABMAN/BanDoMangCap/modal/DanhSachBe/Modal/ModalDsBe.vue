<template src="./ModalDsBe.html"></template>
<script>
export default {
  name: 'ModalDsBe',
  props: ['modalId'],
  computed: {},
  created: async function () {
    await this.getTTVT()
  },
  watch: {
    isTuyen: function (val) {
      if (val) { this.onChangeToQL() }
    }
  },
  methods: {
    focusMyElement: async function () {
      try {
        this.$root.showLoading(true)
        await this.getTTVT()
      } catch (er) {} finally {
        this.$root.showLoading(false)
      }
    },
    getTTVT: async function () {
      try {
        let rs = await this.$root.context.get(
          '/web-cabman/BanDoTuyenCong/ds-ttvt-theo-nhanvien'
        )
        this.dsTTVT = rs.data
      } catch (error) {
      } finally {
      }
    },
    getToQL: async function () {
      try {
        let rs = await this.$root.context.get(
          '/web-cabman/BanDoTuyenCong/ds-to-kythuat-theo-nhanvien/' +
                    this.dataSelected.ttvt
        )
        this.dsToQL = rs.data
      } catch (error) {
      } finally {
      }
    },
    getdsTuyenCong: async function () {
      try {
        let rs = await this.$root.context.get(
          '/web-cabman/BanDoTuyenCong/ds-tuyen-cong/' +
                    this.dataSelected.toql
        )
        this.dsTuyenCong = rs.data
      } catch (error) {
      } finally {
      }
    },
    onChangeTTVT: async function () {
      await this.getToQL()
    },
    onChangeToQL: async function () {
      await this.getdsTuyenCong()
    },
    grid_selectedRowChanged: async function (dataItem) {
      if (dataItem) {
        this.beId = dataItem.BECAP_ID
        this.beSelected = dataItem
      }
    },
    timKiem: async function () {
      try {
        this.$root.showLoading(true)
        let data = {
          'tuyencb_id': null,
          'to_quanly_id': null,
          'chuoi_timkiem': this.querys
        }
        let rs = await this.$root.context.post(
          '/web-cabman/tracuu/ds-be-cap', data
        )
        this.dsBe = rs.data
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    tracuuBe: async function () {
      try {
        this.$root.showLoading(true)
        let data = {
          'tuyencb_id': this.dataSelected.tuyen_id,
          'to_quanly_id': this.dataSelected.toql,
          'chuoi_timkiem': ''
        }
        let rs = await this.$root.context.post(
          '/web-cabman/tracuu/ds-be-cap', data
        )
        this.dsBe = rs.data
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    onChangeTuyen: async function () {

    },
    chonBe: async function () {
      this.$emit('xacnhan', this.beSelected)
      this.$bvModal.hide(this.modalId)
    },

    huybo: async function () {
      this.beId = null
    }

  },
  data: function () {
    return {
      dsTTVT: [],
      dsToQL: [],
      dsTuyenCong: [],
      dataSelected: {
        ttvt: 0,
        toql: null,
        tuyen_id: null
      },
      dsBe: [],
      isTuyen: false,
      querys: '',
      beId: 0,
      beSelected: null
    }
  }
}
</script>
<style>
  .dashboard-header {
    z-index: 9 !important;
  }
  a.disabled {
    pointer-events: none;
    cursor: default;
    color: #6c757d !important;
  }
</style>

/* eslint-disable camelcase */
<template src="./index.template.html"></template>
<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import { getterName, actionName, statePropertyName } from './store'

import ModalQLCot from '../BanDoTuyenCot/QLCot/Modal'

export default {
  components: { ModalQLCot },
  name: 'DanhSachCotCap',
  computed: {
    ...mapState('cabman/danhsachCotCap', statePropertyName),
    ...mapState('cabman', ['danhsachCotCap']),
    ...mapGetters('cabman/danhsachCotCap', getterName)
  },
  methods: {
    ...mapActions('cabman/danhsachCotCap', actionName),
    grid_selectedRowChanged () {},
    grid_onCommandClicked (name, dataItem) {
      console.log('grid_onCommandClicked', dataItem)
      this.dataSend.cot_id = dataItem.COT_ID
      this.dataSend.tuyencot_id = dataItem.TUYENCOT_ID
      this.dataSend.ten_tuyen = '[' + dataItem.TUYENCOT_ID + '] ' + dataItem.TUYEN_COT
      this.$bvModal.show('popupQLCot')
    },
    selectTUYENCOT (select) {
      console.log('selectTUYENCOT', select)
      this.data.tuyen_cot.tuyen_cot = select.text
    },
    loadDsTTVT () {
      this.loading(true)
      this.layDsTTVT().then(() => {
        if (this.dsTTVT && this.dsTTVT.length > 0) {
          this.data.donvi_id = this.dsTTVT[0].DONVI_ID
        } else {
          this.data.donvi_id = null
        }
      })
      this.loading(false)
    },
    dataBound: function () {
      this.$refs.grid.autoFitColumns()
    },
    commandClick: function (args) {
      if (args.target.classList.contains('custombutton')) {
        console.log(args)
        alert(JSON.stringify(args.rowData))
      }
    },
    async timkiemCot () {
      let query = this.data.query.trim()
      if (query) {
        await this.getCot2(query)
        this.message = 'Danh sách cột thỏa mãn điều kiện tìm kiếm'
        if (this.dsC.length === 0) {
          this.data.query = ''
          this.$root.$toast.error('Không tìm thấy thông tin')
        }
      }
    },
    onChangeDonVi () {

    },
    onChangeToQuanLy () {

    },
    onChangeTuyenCot (event) {
      console.log('onChangeTuyenCot', event)
    },
    async getCot (donvi_id, tuyencot_id) {
      try {
        this.loading(true)
        this.data.query = ''
        await this.layDsCot({donvi_id, tuyencot_id})
        if (tuyencot_id === null) {
          this.message = 'Danh sách cột cáp chưa xác định tuyến'
        } else {
          this.message = 'Danh sách cột thuộc tuyến: ' + this.data.tuyen_cot.tuyen_cot
        }
      } catch (err) {
        this.$root.$toast.error('Lỗi tra cứu thông tin cột cáp')
      } finally {
        this.loading(false)
      }
    },
    async getCot2 (query) {
      try {
        this.loading(true)
        await this.tracuuCot(query)
      } catch (err) {
        console.log(err)
      } finally {
        this.loading(false)
      }
    }
  },
  created () {
    this.clearState()
    this.createApiManager()
  },
  data () {
    return {
      dataSend: Object,
      data: {
        donvi_id: null,
        toql_id: null,
        tuyen_cot: {
          tuyencot_id: null,
          tuyen_cot: ''
        },
        query: '',
        checkedTuyenCot: true
      },
      message: 'Danh sách cột thuộc tuyến:'
    }
  },
  async mounted () {
    this.loadDsTTVT()
  },
  watch: {
    'data.donvi_id': function (val) {
      if (val) {
        this.clearStateDsC()
        this.layDsTOQL(this.data.donvi_id).then(() => {
          if (this.dsTOQL.length > 0) {
            this.data.toql_id = this.dsTOQL[0].DONVI_ID
          }
        })
      }
    },
    'data.toql_id': function (val) {
      if (val) {
        this.clearStateDsC()
        this.clearStateDsTC()

        this.dataSend.dvql = {
          id: val,
          name: ''
        }

        this.layDsTuyenCot(this.data.toql_id).then(() => {
          if (this.dsTC.length > 0) {
            this.data.tuyen_cot.tuyencot_id = this.dsTC[0].TUYENCOT_ID
            this.data.tuyen_cot.tuyen_cot = this.dsTC[0].TUYEN_COT
          } else {
            this.data.tuyen_cot.tuyencot_id = null
            this.data.tuyen_cot.tuyen_cot = null
            this.message = 'Danh sách cột'
          }
        })
        if (!this.data.checkedTuyenCot) {
          this.getCot(this.data.toql_id, null).then(() => {
            this.message = 'Danh sách cột cáp chưa xác định tuyến'
          })
        }
      }
    },
    'data.tuyen_cot.tuyencot_id': function (val) {
      if (val) {
        this.clearStateDsC()
        if (this.data.checkedTuyenCot) {
          this.getCot(this.data.toql_id, this.data.tuyen_cot.tuyencot_id).then(() => {
          })
        }
      }
    },
    'data.checkedTuyenCot': function (val) {
      this.clearStateDsC()
      if (val) {
        this.getCot(this.data.toql_id, this.data.tuyen_cot.tuyencot_id).then(() => {
        })
      } else {
        this.getCot(this.data.toql_id, null).then(() => {
        })
      }
    }
  }
}
</script>

<style>

</style>

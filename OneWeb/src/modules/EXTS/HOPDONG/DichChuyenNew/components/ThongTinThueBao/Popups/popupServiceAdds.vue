<template>
  <div class="modal-content popup-box">
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="closeForm"> <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Chấp nhận </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="table-content">
          <ejs-grid ref="grid" :dataSource="ds_dichvuGTGT.list" locale="vi-VN" :allowSorting="true" :allowFiltering="true" :allowPaging="true" :pageSettings="{ pageSize: 10, pageSizes: [10, 50, 100, 200] }" :rowSelected="rowselect" :rowDeselected="rowdeselect" height="400">
            <e-columns>
              <e-column field="STT" headerText="STT" headerTextAlign="center" textAlign="Center" width="60"></e-column>
              <e-column field="MA_DVGT" headerText="Mã dịch vụ" headerTextAlign="center" textAlign="Left" width="120"></e-column>
              <e-column field="TEN_DVGT" headerText="Tên dịch vụ" headerTextAlign="center" width="250"></e-column>
              <e-column type="checkbox" field="DANGKY" headerText="Đăng ký" headerTextAlign="center" textAlign="Center" displayAsCheckBox="true" width="50" format="C2"></e-column>
              <e-column field="CUOC_LD" format="N" class="cuoclapdat" :customAttributes="{ class: 'cuoclapdat' }" headerText="Cước lắp đặt" headerTextAlign="center" textAlign="right" width="105"></e-column>
              <e-column field="VAT_LD" format="N" class="vatlapdat" :customAttributes="{ class: 'vatlapdat' }" headerText="Vat lắp đặt" headerTextAlign="center" textAlign="right" width="105"></e-column>
              <e-column field="TIEN" format="N" class="tongtien" :customAttributes="{ class: 'tongtien' }" headerText="Tổng tiền" headerTextAlign="center" textAlign="right" width="105"></e-column>
              <e-column field="CUOC_SD" format="N" headerText="Phí duy trì" headerTextAlign="center" textAlign="right" width="105"></e-column>
              <e-column field="VAT_SD" format="N" headerText="Vat duy trì" headerTextAlign="center" textAlign="right" width="105"></e-column>
              <e-column field="NOIDUNG" headerText="Nội dung/Số lượng" headerTextAlign="center" width="105"></e-column>
              <e-column field="" format="N" headerText="Số lượng" headerTextAlign="center" textAlign="right" width="100"></e-column>
              <e-column field="BLOCK_TIEP" format="N" headerText="Block" headerTextAlign="center" textAlign="right" width="105"></e-column>
              <e-column field="GIA_BLOCK_TIEP" format="N" headerText="Giá Block" headerTextAlign="center" textAlign="right" width="105"></e-column>
              <e-column field="VAT_BLOCK_TIEP" format="N" headerText="Vat Block" headerTextAlign="center" textAlign="right" width="105"></e-column>
              <e-column field="GHICHU" headerText="Ghi chú" headerTextAlign="center" width="200"></e-column>
            </e-columns>
            <e-aggregates>
              <e-aggregate>
                <e-columns>
                  <e-column columnName="CUOC_SD" format="N" type="Custom" :customAggregate="tienAggregateFn"></e-column>
                  <e-column columnName="VAT_SD" format="N" type="Custom" :customAggregate="vatAggregateFn"></e-column>
                  <e-column columnName="GIA_BLOCK_TIEP" format="N" type="Custom" :customAggregate="giablkAggregateFn"></e-column>
                  <e-column columnName="VAT_BLOCK_TIEP" format="N" type="Custom" :customAggregate="vatblkAggregateFn"></e-column>
                </e-columns>
              </e-aggregate>
            </e-aggregates>
          </ejs-grid>
        </div>
      </div>
    </div>
  </div>
</template>
<style>
input.disabled,
select.disabled,
.disabled input,
.disabled select,
.disabled /deep/ .select2-selection {
  background-color: #eee;
  color: lightgray;
}
.e-grid .e-gridheader .e-sortfilter .e-headercelldiv,
.e-grid .e-gridheader .e-stackedheadercelldiv,
.e-grid .e-gridheader .e-headercell .e-headercelldiv.e-headerchkcelldiv {
  padding: 0 5px 0 5px !important;
}
@import '@/node_modules/@syncfusion/ej2-vue-grids/styles/material.css';
.e-grid .e-rowcell.cuoclapdat,
.cuoclapdat {
  font-weight: bold;
  color: blue;
}
.e-grid .e-rowcell.vatlapdat,
.vatlapdat {
  font-weight: bold;
  color: cadetblue;
}
.e-grid .e-rowcell.tongtien,
.tongtien {
  font-weight: bold;
  color: red;
}
</style>
<script>
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import { L10n, setCulture } from '@syncfusion/ej2-base'
import { GridPlugin, Page, Filter, GridComponent, Sort, Grid, Aggregate } from '@syncfusion/ej2-vue-grids'
import { required, minLength, integer, maxValue } from 'vuelidate/lib/validators'

L10n.load({
  'vi-VN': {
    grid: {
      EmptyRecord: 'Không có bản ghi nào để hiển thị ',
      Item: 'Bản ghi',
      Items: 'Bản ghi',
      FilterbarTitle: ''
    }
  }
})

export default {
  name: 'popupServiceAdds',
  components: { required, minLength, integer, maxValue, DatePicker, L10n, setCulture, GridPlugin, Page, Sort, Filter, GridComponent, Grid, Aggregate },
  props: ['dulieu'],
  lines: 'Both',
  watch: {
    'ds_dichvuGTGT.list': {
      handler: function() {
        console.log('🚀 ~ file: popupServiceAdds.vue ~ line 90 ~ ds_dichvuGTGT.list', this.ds_dichvuGTGT.list)
        this.$forceUpdate()
      },
      deep: true
    }
  },
  data() {
    return {
      initialSort: {
        columns: [
          { field: 'MA_DVGT', direction: 'Ascending' },
          { field: 'TEN_DVGT', direction: 'Descending' }
        ]
      },
      lvDvgt: [],
      ds_dichvuGTGT: {
        list: [],
        header: [],
        value: {},
        isEnabled: true,
        checked: []
      },
      success_code: 'BSS-00000000',
      loaitb_id: '0',
      hdtb_id: '0',
      linhvuc_id: '0',
      tocdo_id: '0',
      dichvudangky: [],
      rowchon: [],
      rowchon0: [],
      phiduytri: 0,
      vatduytri: 0,
      giablock: 0,
      vatblock: 0
    }
  },
  provide: {
    grid: [Sort, Page, Filter, Aggregate]
  },

  async mounted() {
    this.loaitb_id = this.dulieu._loaitb_id ? this.dulieu._loaitb_id : 0
    this.hdtb_id = this.dulieu._hdtb_id ? this.dulieu._hdtb_id : 0
    this.linhvuc_id = this.dulieu._linhvuc_id ? this.dulieu._linhvuc_id : 0
    this.tocdo_id = this.dulieu._tocdo_id ? this.dulieu._tocdo_id : 0
    this.lvDvgt = this.dulieu.lvDvgt
    await this.GetDsDVGT()
  },
  computed: {},
  methods: {
    tienAggregateFn: function(d) {
      return this.TinhTongAggr(d, 'CUOC_SD')
    },
    vatAggregateFn: function(d) {
      return this.TinhTongAggr(d, 'VAT_SD')
    },
    giablkAggregateFn: function(d) {
      return this.TinhTongAggr(d, 'GIA_BLOCK_TIEP')
    },
    vatblkAggregateFn: function(d) {
      return this.TinhTongAggr(d, 'VAT_BLOCK_TIEP')
    },
    TinhTongAggr: function(d, e) {
      var fdata = d.result.filter((item) => item.DANGKY == 1)
      return fdata && fdata.length > 0 ? fdata.map((x) => x[e]).reduce((a, b) => a + b) : 0
    },
    formatPrice(value) {
      let val = (value / 1).toFixed(0).replace('.', ',')
      return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.')
    },

    rowselect() {
      var grid = this.$refs.grid
      this.rowchon0 = this.rowchon
      this.rowchon = grid.getSelectedRecords()
      this.phiduytri = 0
      this.vatduytri = 0
      this.giablock = 0
      this.vatblock = 0
      var self = this
      this.rowchon.forEach(function(item) {
        self.ds_dichvuGTGT.list.filter((x) => x.DICHVUGT_ID == item.DICHVUGT_ID)[0].DANGKY = 1
        self.phiduytri = self.phiduytri + (item.CUOC_SD == '' ? 0 : item.CUOC_SD)
        self.vatduytri = self.vatduytri + (item.VAT_SD == '' ? 0 : item.VAT_SD)
        self.giablock = self.giablock + (item.GIA_BLOCK_TIEP == '' ? 0 : item.GIA_BLOCK_TIEP)
        self.vatblock = self.vatblock + (item.VAT_BLOCK_TIEP == '' ? 0 : item.VAT_BLOCK_TIEP)
      })
    },
    rowdeselect() {
      var grid = this.$refs.grid
      this.rowchon0 = this.rowchon
      this.rowchon = grid.getSelectedRecords()
      for (var item of this.rowchon0) {
        if (this.rowchon.filter((x) => x.DICHVUGT_ID == item.DICHVUGT_ID).length <= 0) {
          this.ds_dichvuGTGT.list.filter((x) => x.DICHVUGT_ID == item.DICHVUGT_ID)[0].DANGKY = 0
          this.phiduytri = this.phiduytri - (item.CUOC_SD == '' ? 0 : item.CUOC_SD)
          this.vatduytri = this.vatduytri - (item.VAT_SD == '' ? 0 : item.VAT_SD)
          this.giablock = this.giablock - (item.GIA_BLOCK_TIEP == '' ? 0 : item.GIA_BLOCK_TIEP)
          this.vatblock = this.vatblock - (item.VAT_BLOCK_TIEP == '' ? 0 : item.VAT_BLOCK_TIEP)
        }
      }
    },
    closeForm() {
      this.dichvudangky.forEach(function(item1) {
        if (item1.DANGKY === '1') {
          item1.HUY = 1
          item1.DANGKY = 0
        }
      })
      console.log('🚀 ~ file: popupServiceAdds.vue ~ line 205 ~ this.ds_dichvuGTGT.list', this.ds_dichvuGTGT.list)
      var self = this
      this.ds_dichvuGTGT.list
        .filter((x) => x.DANGKY === 1)
        .forEach(function(item1) {
          self.dichvudangky.filter((x) => x.DICHVUGT_ID === item1.DICHVUGT_ID)[0].DANGKY = 1
          self.dichvudangky.filter((x) => x.DICHVUGT_ID === item1.DICHVUGT_ID)[0].HUY = 0
        })

      const newArray = [...this.dichvudangky].map((item) => {
        item.dangKy = item.DANGKY
        item.dichVuGtId = item.DICHVUGT_ID
        item.cuocLd = item.CUOC_LD
        item.vatLd = item.VAT_LD
        item.noiDung = item.NOIDUNG
        item.huy = item.HUY
        item.sua = item.SUA
        item.ten_dvgt = item.TEN_DVGT
        item.tenDvGt = item.TEN_DVGT
        return item
      })

      this.$emit('form-close', newArray)
    },

    ShowAlert(mesage, val) {
      if (val === 0) {
        this.$toast.success(mesage)
      } else if (val === 1) {
        this.$toast.warning(mesage)
      } else if (val === 2) {
        this.$toast.error(mesage)
      }
    },
    async fn_lay_ds_dvgt_macdinh(input) {
      try {
        var rs = await this.$root.context.post('web-hopdong/chuyendich/fn_lay_ds_dvgt_macdinh', input)
        return rs.data
      } catch (error) {
        return []
      }
    },
    GetDsDVGT: async function() {
      try {
        this.loading(true)
        var inputstring = { LOAITB_ID: this.loaitb_id, HDTB_ID: this.hdtb_id, 'LINHVUC_ID:': this.linhvuc_id, I_TOCDO_ID: this.tocdo_id }
        var input = {}
        input.ds_para = JSON.stringify(inputstring)
        var result = await this.fn_lay_ds_dvgt_macdinh(input)
        console.log('🚀 ~ file: popupServiceAdds.vue ~ line 234 ~ data', result)
        if (result) {
          this.ds_dichvuGTGT.list = JSON.parse(result).RESULT.DS_TB_DICHVU
          this.dichvudangky = JSON.parse(result).RESULT.DS_TB_DICHVU
          if (this.ds_dichvuGTGT.list && this.ds_dichvuGTGT.list.length > 0 && this.lvDvgt && this.lvDvgt.length > 0) {
            for (var item1 of this.lvDvgt) {
              for (var item2 of this.ds_dichvuGTGT.list) {
                if (item1.dichVuGtId === item2.DICHVUGT_ID) {
                  console.log('🚀 ~ file: popupServiceAdds.vue ~ line 248 ~ item1.dichVuGtId', item1.dichVuGtId)
                  item2.DANGKY = 1
                  break
                }
              }
            }
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: popupServiceAdds.vue ~ line 263 ~ error', error)
      } finally {
        this.loading(false)
      }
    }
  }
}
</script>

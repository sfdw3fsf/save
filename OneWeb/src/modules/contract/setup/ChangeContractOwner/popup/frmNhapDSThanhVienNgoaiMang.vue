<template>
  <ejs-dialog target=".main-wrapper" :position="{ X: 'center', Y: 'top' }" :visible="false" ref="frmNhapDSThanhVienNgoaiMang" width="50%" id="frmNhapDSThanhVienNgoaiMang_form" :showCloseIcon="false" :header="headerTemplate" style="z-index=1000">
    <div class="popup-content">
      <div class="list-actions-top">
        <ul class="list">
          <!-- <li class="font-weight-bold ml-3" v-show="kieuld_id != KIEULD_DANGKY">Danh sách số thành viên</li> -->
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="row" v-if="view_only == false">
            <div class="col-sm-9">
              <div class="row" style="align-items:center;">
                <div class="col-sm-6">
                  <div class="row" style="display: flex; ">
                    <div style="width: 40%">SĐT ngoại mạng</div>
                    <div style="width: 60%">
                      <input v-model="so_tb" type="text" class="form-control" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6">
                  <div class="row" style="display: flex; ">
                    <div style="width: 40%">Nhà mạng</div>
                    <div style="width: 60%">
                      <select2 placeholder="Chọn nhà mạng" :options="list_nhamang" id="input-datalist" v-model="doitac_id" class="select-custom" />
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="col-sm-3">
              <div class="btn-group" role="group">
                <button type="button" class="btn btn-light border" @click="btnGhiLai_Click">
                  Ghi lại
                </button>
                <button type="button" @click="btnXoa_Click" class="btn btn-light border">
                  Xóa
                </button>
                <button @click="chooseFiles()" type="button" class="btn btn-light border">
                  Import Excel
                </button>
                <!-- <button type="button" v-show="false" @click="btnHoanThanh_Click" class="btn btn-light border">
                  Hoàn thành
                </button> -->
                <input ref="fileUpload" type="file" @click="$refs.fileUpload.value = ''" @change="onChange" hidden />
              </div>
            </div>
          </div>
          <p style="margin-bottom: -5px; margin-top:10px; margin-left: 20px; color: blue; font-weight: 500;">SL Mobifone: {{ soluong_mobi }}; SL Viettel: {{ soluong_viettel }}</p>
        </div>
      </div>

      <div class="box-form">
        <DataGrid
          ref="gvDanhSach"
          v-bind:columns="tableCollumns"
          v-bind:dataSource="main_datasource"
          :showColumnCheckbox="true"
          :showFilter="true"
          :allowPaging="true"
          :enablePagingServer="false"
          :enabledSelectFirstRow="false"
          @checkBoxChange="checkBoxGanChange"
          @rowSelected="DanhSachRowSelected"
          @rowDeselected="DanhSachRowDeselected"
        >
        </DataGrid>
      </div>
    </div>
  </ejs-dialog>
</template>
<script>
import Select2Ext from '@/modules/contract/setup/DeclareLandline/components/Select2Ext'
// import DataGridCustom from '@/modules/contract/setup/DeclareLandline/components/DataGridCustom'
import commonFn from '@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions'
import Vue from 'vue'
import XLSX from 'xlsx'
import ComboboxGrid2 from '@/components/Controls/ComboboxGrid2'

import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import { L10n, setCulture } from '@syncfusion/ej2-base'
import { GridPlugin, Page, GridComponent, Sort, Grid, Aggregate, Edit, Resize, Group, CommandColumn, ExcelExport, Freeze, Toolbar } from '@syncfusion/ej2-vue-grids'
import { required, minLength, integer, maxValue } from 'vuelidate/lib/validators'
import moment from 'moment'
import { BIconArrowReturnLeft } from 'bootstrap-vue'

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

var headerTemplateVue = Vue.component('demo1', {
  template:
    '<span><span class="title font-weight-bold" id="frmNhapDSThanhVienNgoaiMangTitle">Danh sách số thành viên</span><button id="closeButton2" class="e-dlg-closeicon-btn e-control e-btn e-lib e-flat e-icon-btn" type="button" title="Close" aria-label="Close"><span class="e-btn-icon e-icon-dlg-close e-icons"></span></button></span>',
  data() {
    return {
      data: {}
    }
  }
})

export default {
  components: {
    Select2Ext,
    required,
    minLength,
    integer,
    maxValue,
    DatePicker,
    L10n,
    setCulture,
    GridPlugin,
    Page,
    Sort,
    GridComponent,
    Grid,
    Aggregate,
    ComboboxGrid2
  },
  // KIEULD_DANGKY: 19585,

  data() {
    return {
      initialSort: {
        columns: [
          { field: 'HDTB_CT_ID', direction: 'Ascending' }
          // { field: 'TEN_DVGT', direction: 'Descending' }
        ]
      },
      headerTemplate: function() {
        return { template: headerTemplateVue }
      },

      tableCollumns: [
        {
          fieldName: 'HDTB_SUB_ID',
          headerText: 'ID',
          allowFiltering: true,
          allowSorting: false,
          width: 80
        },
        {
          fieldName: 'SO_DICH',
          headerText: 'Số thuê bao',
          allowFiltering: true,
          allowSorting: false,
          width: 100
        },
        {
          fieldName: 'TELCO_NAME',
          headerText: 'Nhà mạng',
          allowFiltering: true,
          allowSorting: false,
          width: 100
        },
        {
          fieldName: 'NGAY_BD',
          headerText: 'Ngày cập nhật',
          allowFiltering: true,
          allowSorting: false,
          width: 100
        },
        {
          fieldName: 'DIACHI_LD',
          headerText: 'Nội dung',
          allowFiltering: true,
          allowSorting: false,
          width: 100
        }
      ],
      so_tb: null,
      donvikg_id: null,
      doituong: null,
      hdtb_id: 0,
      tdan: {},
      dt: [],
      file: null,
      hdtb_ct_id: null,
      sodt_khonghople: '',
      main_datasource: [],
      kieuld_id: null,
      KIEULD_DANGKY: 19585,
      KIEULD_HUY: 19587,
      list_stk_cd: [],
      so_tb_selected: null,
      doitac_id: null,
      list_nhamang: [],
      yeucauhuy: true,
      tthd_id: null,
      soluong_viettel: null,
      soluong_mobi: null,
      tinh_id: null,
      view_only: false,
      thuebao_id: 0,
      ds_so_tb_ngoaimang: [],
      ds_chon: [],
      list_kieuyc: [{ id: 'Yêu cầu hủy', text: 'Yêu cầu hủy' }],
      kieu_yc: null
    }
  },

  provide: {
    grid: [Sort, Resize, Group, Freeze, CommandColumn, ExcelExport, Edit, Aggregate, Toolbar]
  },
  watch: {
    main_datasource: {
      handler: function() {
        this.$forceUpdate()
      },
      deep: true
    }
  },
  // props: ['_ds_hdtbct'],
  mounted() {},
  methods: {
    load: function() {
      this.$refs.grid.ej2Instances.element.addEventListener('mouseup', function(e) {
        var instance = this.ej2_instances[0]
        if (e.target.classList.contains('e-rowcell')) {
          if (instance.isEdit) instance.endEdit()
          let index = parseInt(e.target.getAttribute('Index'))
          instance.selectRow(index)
          instance.startEdit()
        }
      })
      this.$refs.grid.ej2Instances.element.addEventListener('change', function(e) {
        if (e.target.classList.contains('e-checkbox')) {
          var instance = this.ej2_instances[0]
          if (instance.isEdit) {
            instance.editModule.batchSave()
            instance.endEdit()
          }
        }
      })
    },
    ShowDialog() {
      this.$nextTick(() => {
        this.frmNhapDSThanhVienNgoaiMang_Load()
        document.getElementById('closeButton2').addEventListener('click', () => {
          this.closeForm()
        })
        this.$refs.frmNhapDSThanhVienNgoaiMang.show()
      })
    },

    closeForm() {
      console.log('vào close form')
      this.$emit('frmNhapDSThanhVienNgoaiMang_close', false)
      this.$refs.frmNhapDSThanhVienNgoaiMang.hide()
    },
    async frmNhapDSThanhVienNgoaiMang_Load() {
      return new Promise(async (resole, reject) => {
        if (!this.tinh_id) this.tinh_id = this.$root.token.getPhanVungID()

        if (this.view_only) {
          if (this.thuebao_id != 0) {
            let rs = await this.axios.post('/web-hopdong/quanly_khdn_mnp/sp_lay_ds_dl_nghiepvu', {
              P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
              p_nghiepvu: 'LAY_DULIEU_DB_CNTT_SUB',
              p_ds_para: JSON.stringify({ HDTB_ID: this.hdtb_id, TINH_ID: this.tinh_id, THUEBAO_ID: this.thuebao_id })
            })
            this.main_datasource = rs.data && rs.data.data ? this.UpperCasePropertyList(rs.data.data) : []
            console.log('✅ Load data: ', this.main_datasource)

            const req = {
              P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
              p_nghiepvu: 'LAY_DULIEU_DB_CNTT',
              p_ds_para: JSON.stringify({ THUEBAO_ID: this.thuebao_id, TINH_ID: this.tinh_id })
            }
            console.log('Load cntt')
            const db_cntt = (await this.axios.post('/web-hopdong/quanly_khdn_mnp/sp_lay_ds_dl_nghiepvu', req)).data.data[0]
            this.soluong_mobi = Number(db_cntt['linhvuc_vms_id'])
            this.soluong_viettel = Number(db_cntt['linhvuc_vtl_id'])

            return
          }
        }

        let rs = await this.axios.post('/web-hopdong/quanly_khdn_mnp/sp_lay_ds_dl_nghiepvu', {
          P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
          p_nghiepvu: 'LAY_DULIEU_HDTB_CNTT_SUB',
          p_ds_para: JSON.stringify({ HDTB_ID: this.hdtb_id, TINH_ID: this.tinh_id })
        })

        this.main_datasource = rs.data && rs.data.data ? this.UpperCasePropertyList(rs.data.data) : []
        console.log('✅ Load data: ', this.main_datasource)

        const req = {
          P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
          p_nghiepvu: 'LAY_DULIEU_CNTT',
          p_ds_para: JSON.stringify({ HDTB_ID: this.hdtb_id, TINH_ID: this.tinh_id })
        }
        const hdtb_cntt = (await this.axios.post('/web-hopdong/quanly_khdn_mnp/sp_lay_ds_dl_nghiepvu', req)).data.data[0]
        this.donvikg_id = hdtb_cntt['donvikg_id']

        this.kieuld_id = Number(hdtb_cntt['kieuld_id'])
        this.tthd_id = Number(hdtb_cntt['tthd_id'])
        if (this.kieuld_id == 19587 || this.kieuld_id == 19598 ) {
          this.view_only = true
        }

        this.soluong_mobi = Number(hdtb_cntt['linhvuc_vms_id'])
        this.soluong_viettel = Number(hdtb_cntt['linhvuc_vtl_id'])

        this.list_nhamang = [
          { id: 2, text: 'Mobiphone' },
          { id: 3, text: 'Viettel' }
        ]

        this.main_datasource = this.main_datasource

        resole(true)
      })
    },

    selectItem(item) {
      let selected = item.data
      console.log(item)
      this.hdtb_ct_id = selected['HDTB_CT_ID']
      // this.so_tb_selected = selected['SO_DICH']
    },

    DanhSachRowDeselected(args) {
      console.log('DanhSachRowDeselected', args)
      if (!args.isHeaderCheckboxClicked && args.isInteracted) {
        console.log('DanhSachRowDeselected', args)
        const index = this.main_datasource.findIndex((x) => x.HDTB_SUB_ID == args.data.HDTB_SUB_ID)
        this.main_datasource[index].ischeck = '0'
      }
    },

    DanhSachRowSelected(args) {
      if (!args.isHeaderCheckboxClicked && args.isInteracted) {
        // this.DanhSachRowSelected(args.data)
        console.log('DanhSachRowSelected', args)
        if (Array.isArray(args.data)) {
          for (let i = 0; i < args.data.length; i++) {
            const index = this.main_datasource.findIndex((x) => x.HDTB_SUB_ID == args.data[i].HDTB_SUB_ID)
            this.main_datasource[index].ischeck = '1'
          }
        } else {
          const index = this.main_datasource.findIndex((x) => x.HDTB_SUB_ID == args.data.HDTB_SUB_ID)
          this.main_datasource[index].ischeck = '1'
        }
      }
    },
    checkBoxGanChange(args) {
      if (args.target.className == 'e-checkselectall') {
        // click check/uncheck all

        let dsFilter = this.$refs.gvDanhSach.filteredDataSource || []

        console.log('checkBoxGanChange dsFilter', dsFilter)

        for (let i = 0; i < dsFilter.length; i++) {
          const index = this.main_datasource.findIndex((x) => x.HDTB_SUB_ID == dsFilter[i].HDTB_SUB_ID)
          if (index > -1) {
            if (args.checked) {
              this.main_datasource[index].ischeck = '1'
            } else {
              this.main_datasource[index].ischeck = '0'
            }
          } else {
            this.main_datasource[index].ischeck = '0'
          }
        }
        console.log('checkBoxGanChange', this.main_datasource)
      }
    },

    ConvertLower(arr) {
      return arr.map(function(item) {
        return Object.fromEntries(Object.entries(item).map(([k, v]) => [k.toLowerCase(), v]))
      })
    },

    ConvertUpper(arr) {
      return arr.map(function(item) {
        return Object.fromEntries(Object.entries(item).map(([k, v]) => [k.toUpperCase(), v]))
      })
    },
    UpperCasePropertyList(obj) {
      return obj.map(function(item) {
        for (var key in item) {
          var upper = key.toUpperCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },

    async btnGhiLai_Click() {
      if (!this.so_tb) {
        this.$toast.error('Chưa nhập số điện thoại, vui lòng nhập số điện thoại trước khi ghi lại')
        return
      }
      //"/(84|0[3|5|7|8|9])+([0-9]{8})\b/g"
      let isphone = this.so_tb.match('(84)+([0-9]{9,10})')
      if (!isphone) {
        this.$toast.error('Số điện thoại không đúng định dạng 84xxxxxxxxx. Hãy kiểm tra lại.')
        return
      }

      if (this.main_datasource.some((x) => x.SO_DT_CT == this.so_tb) && this.so_tb != '') {
        this.$toast.error('Số điện thoại đã tồn tại.')
        return
      }

      let response_kt_trasau = await this.axios.post('/ccbs/tracuu/app_tracuu_tt_thuebao', { so_tb: this.so_tb })
      if (response_kt_trasau.data.data && response_kt_trasau.data.data.length != 0) {
        this.$toast.error('Số thuê bao phải là số ngoại mạng.')
        this.sodt_khonghople += vso_dt + ';'
        return
      }

      let hdtb_ct = {
        HDTB_ID: this.hdtb_id,
        SO_DICH: this.so_tb,
        LOAIHINH_TB: 358,
        NGAY_BD: moment().format('DD/MM/YYYY, HH:mm:ss'),
        DIACHI_LD: 'Đăng ký ngoại mạng',
        DOITAC_ID: this.doitac_id
      }

      console.log(hdtb_ct)

      this.main_datasource.push(hdtb_ct)

      if (this.hdtb_id != 0) {
        this.loading(true)
        const req = {
          P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
          p_nghiepvu: 'CAPNHAT_HDTB_CNTT_SUB_COMMON',
          p_ds_para: JSON.stringify({ vhdtb_id: this.hdtb_id, vds_hdtb_cntt_sub: JSON.stringify(this.main_datasource), TINH_ID: this.tinh_id })
        }

        const kq = await this.axios.post('/web-hopdong/quanly_khdn_mnp/sp_lay_ds_dl_nghiepvu', req)
        if(kq.data.error_code != "BSS-00000000") {
          await this.frmNhapDSThanhVienNgoaiMang_Load()
          this.$toast.error(`Số thuê bao ${this.so_tb} đã được sử dụng`)
          this.loading(false)
          return
        }
        await this.frmNhapDSThanhVienNgoaiMang_Load()
        this.loading(false)

        this.$toast.success('Ghi lại thành công')
      }

      this.so_tb = ''
    },

    async btnXoa_Click() {
      console.log(this.main_datasource)
      if (this.main_datasource.length <= 0) {
        this.$toast.error('Không tồn tại số điện thoại trong danh sách để xóa')
        return
      }
      console.log('Deleting : ' + this.so_tb_selected)
      this.main_datasource = this.main_datasource.filter((x) => x.ischeck != 1)
      // this._ds_hdtbct = this.main_datasource
      if (this.hdtb_id != 0) {
        const req = {
          P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
          p_nghiepvu: 'CAPNHAT_HDTB_CNTT_SUB_COMMON',
          p_ds_para: JSON.stringify({ vhdtb_id: this.hdtb_id, vds_hdtb_cntt_sub: JSON.stringify(this.main_datasource), TINH_ID: this.tinh_id })
        }
        await this.axios.post('/web-hopdong/quanly_khdn_mnp/sp_lay_ds_dl_nghiepvu', req)
        await this.frmNhapDSThanhVienNgoaiMang_Load()
        this.loading(false)

        this.$toast.success('Xóa thành công')
      }
    },

    chooseFiles() {
      this.$refs.fileUpload.click()
    },
    onChange(event) {
      console.log(event)
      this.loading(true)
      try {
        this.file = event.target.files ? event.target.files[0] : null

        if (this.file) {
          const reader = new FileReader()

          reader.onload = async (e) => {
            /* Parse data */
            const bstr = e.target.result
            const wb = XLSX.read(bstr, { type: 'binary' })
            /* Get first worksheet */
            const wsname = wb.SheetNames[0]
            const ws = wb.Sheets[wsname]
            /* Convert array of arrays */
            const data = XLSX.utils.sheet_to_json(ws, { header: 1 })
            this.sodt_khonghople = ''
            console.log(data)
            data.map(async (item) => {
              await this.ThemSoDT(item[0], item[1])
            })
            if (this.hdtb_id != 0) {
              const req = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                p_nghiepvu: 'CAPNHAT_HDTB_CNTT_SUB_COMMON',
                p_ds_para: JSON.stringify({ vhdtb_id: this.hdtb_id, vds_hdtb_cntt_sub: JSON.stringify(this.main_datasource), TINH_ID: this.tinh_id })
              }
              await this.axios.post('/web-hopdong/quanly_khdn_mnp/sp_lay_ds_dl_nghiepvu', req)
            }
            await this.frmNhapDSThanhVienNgoaiMang_Load()
            if (this.sodt_khonghople.length > 1) this.$toast.warning(this.sodt_khonghople + ' không hợp lệ')
            else this.$toast.success('Import thành công')
          }

          reader.readAsBinaryString(this.file)
        }
      } catch (err) {
        console.log(err)
      }
      this.loading(false)
    },
    async ThemSoDT(vso_dt, nhamang) {
      let vdoitac_id = 0
      if (
        nhamang == 2 ||
        nhamang
          .toString()
          .trim()
          .toLowerCase() == 'mobi' ||
        nhamang
          .toString()
          .trim()
          .toLowerCase() == 'mobifone'
      ) {
        vdoitac_id = 2
      } else if (
        nhamang == 3 ||
        nhamang
          .toString()
          .trim()
          .toLowerCase() == 'viettel' ||
        nhamang
          .toString()
          .trim()
          .toLowerCase() == 'vietel'
      ) {
        vdoitac_id = 3
      }

      let isphone = vso_dt.toString().match('(84)+([0-9]{9,10})')
      if (!isphone) {
        this.sodt_khonghople += vso_dt + ';'
        return
      }

      let hdtb_ct = {
        HDTB_ID: this.hdtb_id,
        SO_DICH: vso_dt,
        LOAIHINH_TB: 358,
        NGAY_BD: moment().format('DD/MM/YYYY, HH:mm:ss'),
        DIACHI_LD: 'Đăng ký ngoại mạng',
        DOITAC_ID: vdoitac_id
      }
      this.main_datasource.push(hdtb_ct)

      vso_dt = ''
      // await this.frmNhapDSThanhVienNgoaiMang_Load()
    }
  }
}

document.addEventListener(
  'DOMContentLoaded',
  (e) => {
    $('#input-datalist').autocomplete()
  },
  false
)
</script>
<style>
#frmNhapDSThanhVienNgoaiMang_form_title {
  width: 100% !important;
  font-weight: bold;
}

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

.e-grid .e-rowcell.vat,
.e-grid .e-summarycell.vat,
.vat {
  font-weight: bold;
  color: darkred;
}
.e-grid .e-rowcell.tien,
.e-grid .e-summarycell.tien,
.tien {
  font-weight: bold;
  color: red;
}
.e-input-group-icon {
  display: none !important;
}
</style>

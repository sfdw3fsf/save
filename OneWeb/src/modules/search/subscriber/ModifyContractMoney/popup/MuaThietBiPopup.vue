<template>
  <b-modal ref="muaThietBiRef" size="xl" hide-header hide-header-close hide-footer body-class="p-0" no-close-on-backdrop @show="frmCTMuaTBI_Load">
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span> Mua thiết bị</div>
        <div class="close -ap icon-close" @click="tsbtnThoat_Click"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#" @click.prevent="tsbtnChapNhan_Click"> <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp nhận </a>
          </li>
          <li>
            <a href="#" @click.prevent="tsbtnThoat_Click"> <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Thoát </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="table-content">
            <DataGridEdit
              ref="gridInner"
              :columns="dgrDS.headers"
              :dataSource="dgrDS.dataSources"
              :showFilter="true"
              :editSettings="dgrDS.editSettings"
              :showColumnPointer="false"
              :enable-paging-server="false"
              :allowPaging="true"
              :useAggregates="true"
              :aggregates="dgrDSAggregates"
              @actionComplete="actionComplete"
            >
            </DataGridEdit>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
import api from '../api'
import { TextBox } from '@syncfusion/ej2-inputs'
import DataGridEdit from '../../../../Payment/SelfManageEinvoice/components/DataGrid'
import Vue from 'vue'
import { LoaiHinhTB } from '@/const/enums'

let tienTraTruocObj, tienTraTruocElm, tienObj, tienElm, soLuongObj, soLuongElm
let selectedItem = {}

export default {
  name: 'MuaThietBiPopup',
  components: { DataGridEdit },
  props: {
    dsCTM_TBI: {
      type: Array,
      default: () => []
    },
    loaitb_id: {
      type: Number,
      default: 0
    },
    tocdo_id: {
      type: Number,
      default: 0
    },
    _sl_cha: {
      type: Number,
      default: 0
    },
    sDSChiTietKM_ID: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      dgrDS: {
        headers: [
          {
            fieldName: 'ma_tbi',
            headerText: 'Mã thiết bị',
            width: 100,
            type: 'string',
            headerTextAlign: 'center',
            textAlign: 'Left',
            allowFiltering: true,
            allowSorting: true,
            allowEditing: false
          },
          {
            fieldName: 'loai_tbi',
            headerText: 'Tên thiết bị',
            width: 100,
            type: 'string',
            headerTextAlign: 'center',
            textAlign: 'Left',
            allowFiltering: true,
            allowSorting: true,
            allowEditing: false
          },
          {
            fieldName: 'soluong',
            headerText: 'Số lượng',
            width: 100,
            headerTextAlign: 'center',
            textAlign: 'Right',
            allowFiltering: true,
            allowSorting: true,
            allowEditing: true,
            edit: {
              create: () => {
                soLuongElm = document.createElement('input')
                return soLuongElm
              },
              read: () => {
                return soLuongObj.value
              },
              destroy: () => {
                soLuongObj.destroy()
              },
              write: (args) => {
                const that = this
                soLuongObj = new TextBox({
                  value: args.rowData[args.column.field],
                  input: function (args) {
                    const formEle = document.getElementById('grid').querySelector('form').ej2_instances[0]
                    formEle.getInputElement('serial').value = ''
                    const soLuongValue = +formEle.getInputElement('soluong').value
                    formEle.getInputElement('serial').disabled = soLuongValue !== 1
                    const found = that.dgrDS.dataSources.find((elm) => elm.loaitbi_id === selectedItem.loaitbi_id)
                    if (found) found.soluong = soLuongValue
                  }
                })
                soLuongObj.appendTo(soLuongElm)
              }
            }
          },
          {
            fieldName: 'dangky',
            headerText: 'Mua',
            width: 100,
            headerTextAlign: 'center',
            textAlign: 'center',
            type: 'boolean',
            displayAsCheckBox: true,
            editType: 'booleanedit',
            template: this.getColumnDangKy(this)
          },
          {
            fieldName: 'serial',
            headerText: 'Serial',
            width: 100,
            type: 'string',
            headerTextAlign: 'center',
            textAlign: 'Left',
            allowFiltering: true,
            allowSorting: true,
            allowEditing: true
          },
          {
            fieldName: 'tien',
            headerText: 'Tiền',
            width: 100,
            headerTextAlign: 'center',
            textAlign: 'Right',
            type: 'number',
            format: 'N0',
            allowFiltering: true,
            allowSorting: true,
            allowEditing: true,
            edit: {
              create: () => {
                tienElm = document.createElement('input')
                return tienElm
              },
              read: () => {
                return tienObj.value
              },
              destroy: () => {
                tienObj.destroy()
              },
              write: (args) => {
                const that = this
                tienObj = new TextBox({
                  value: args.rowData[args.column.field],
                  input: function (args) {
                    const formEle = document.getElementById('grid').querySelector('form').ej2_instances[0]
                    const tienValue = +formEle.getInputElement('tien').value
                    const vatValue = Math.round(tienValue / 10)
                    const vatEle = formEle.getInputElement('vat')
                    vatEle.value = vatValue
                    const found = that.dgrDS.dataSources.find((elm) => elm.loaitbi_id === selectedItem.loaitbi_id)
                    if (found) {
                      found.tien = tienValue
                      found.vat = vatValue
                    }
                  }
                })
                tienObj.appendTo(tienElm)
              }
            }
          },
          {
            fieldName: 'vat',
            headerText: 'Vat',
            width: 100,
            headerTextAlign: 'center',
            textAlign: 'Right',
            type: 'number',
            format: 'N0',
            allowFiltering: true,
            allowSorting: true,
            allowEditing: true
          },
          {
            fieldName: 'tien_tratruoc',
            headerText: 'Tiền trả trước',
            width: 100,
            headerTextAlign: 'center',
            textAlign: 'Right',
            type: 'number',
            format: 'N0',
            allowFiltering: true,
            allowSorting: true,
            allowEditing: true,
            edit: null
          },
          {
            fieldName: 'vat_tratruoc',
            headerText: 'Vat trả trước',
            width: 100,
            headerTextAlign: 'center',
            textAlign: 'Right',
            type: 'number',
            format: 'N0',
            allowFiltering: true,
            allowSorting: true,
            allowEditing: true
          },
          {
            fieldName: 'tien_km',
            headerText: 'Tiền KM',
            width: 100,
            headerTextAlign: 'center',
            textAlign: 'Right',
            type: 'number',
            format: 'N0',
            allowFiltering: true,
            allowSorting: true,
            allowEditing: false
          },
          {
            fieldName: 'vat_km',
            headerText: 'Vat KM',
            width: 100,
            headerTextAlign: 'center',
            textAlign: 'Right',
            type: 'number',
            format: 'N0',
            allowFiltering: true,
            allowSorting: true,
            allowEditing: false
          },
          {
            fieldName: 'sl_cha',
            headerText: 'SL cha',
            width: 100,
            headerTextAlign: 'center',
            textAlign: 'Right',
            allowFiltering: true,
            allowSorting: true,
            allowEditing: false
          },
          {
            fieldName: 'block_tiep',
            headerText: 'Block',
            width: 100,
            headerTextAlign: 'center',
            textAlign: 'Right',
            type: 'number',
            format: 'N0',
            allowFiltering: true,
            allowSorting: true,
            allowEditing: false
          },
          {
            fieldName: 'gia_block_tiep',
            headerText: 'Giá block',
            width: 100,
            headerTextAlign: 'center',
            textAlign: 'Right',
            type: 'number',
            format: 'N0',
            allowFiltering: true,
            allowSorting: true,
            allowEditing: false
          },
          {
            fieldName: 'vat_block_tiep',
            headerText: 'Vat block',
            width: 100,
            headerTextAlign: 'center',
            textAlign: 'Right',
            type: 'number',
            format: 'N0',
            allowFiltering: true,
            allowSorting: true,
            allowEditing: false
          }
        ],
        dataSources: [],
        editSettings: { allowEditing: true, mode: 'Normal' }
      },
      customTemplate: function () {
        return {
          template: Vue.component('footerTemplate', {
            template: `<input type="text" class="form-control tright" v-model="data.Custom" disabled style="margin: 7px 0px 7px 0px;" />`,
            data() {
              return { data: { data: {} } }
            }
          })
        }
      }
    }
  },
  computed: {
    dgrDSAggregates() {
      return [
        {
          field: 'tien',
          type: 'Custom',
          format: 'N0',
          footerTemplate: this.customTemplate,
          customAggregateFn: this.tienAggregateFn
        },
        {
          field: 'vat',
          type: 'Custom',
          format: 'N0',
          footerTemplate: this.customTemplate,
          customAggregateFn: this.vatAggregateFn
        },
        {
          field: 'gia_block_tiep',
          type: 'Custom',
          format: 'N0',
          footerTemplate: this.customTemplate,
          customAggregateFn: this.giablkAggregateFn
        },
        {
          field: 'vat_block_tiep',
          type: 'Custom',
          format: 'N0',
          footerTemplate: this.customTemplate,
          customAggregateFn: this.vatblkAggregateFn
        }
      ]
    }
  },
  methods: {
    async frmCTMuaTBI_Load() {
      this.loading(true)
      try {
        const dt = this.loaitb_id === 292 ? await this.getDanhSachTBiByTocDo() : await this.getDanhSachTbi()
        for (const element of dt) {
          if (this._sl_cha !== 0 && element.block_tiep.toString() && element.block_tiep.toString() !== '0') {
            element.sl_cha = this._sl_cha
          }
          for (const tbi of this.dsCTM_TBI) {
            if (element.loaitbi_id.toString() === tbi.loaitbi_id.toString()) {
              element.dangky = 1
              element.tien = +tbi.tien
              element.vat = +tbi.vat
              element.soluong = +tbi.soluong
              element.tien_tratruoc = +tbi.tien_tratruoc
              element.vat_tratruoc = +tbi.vat_tratruoc
              element.tien_km = +tbi.tien_km
              element.vat_km = +tbi.vat_km
              element.serial = tbi.serial
              element.block_dau = tbi.block_dau.toString() ? +tbi.block_dau : 0
              element.block_tiep = tbi.block_tiep.toString() ? +tbi.block_tiep : 0
              element.gia_block_tiep = tbi.gia_block_tiep.toString() ? +tbi.gia_block_tiep : 0
              element.vat_block_tiep = tbi.vat_block_tiep.toString() ? +tbi.vat_block_tiep : 0
              if (!tbi.sl_cha.toString()) element.sl_cha = 0
              else if (!tbi.block_dau.toString() || tbi.block_dau.toString() === '0') element.sl_cha = +tbi.sl_cha
              else if (this._sl_cha !== 0) element.sl_cha = this._sl_cha
              else element.sl_cha = +tbi.sl_cha
            }
          }
        }
        this.dgrDS.dataSources = dt
        await this.ChangeCell_ReadOnly()
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async getDanhSachTBiByTocDo() {
      try {
        const response = await api.getDanhSachTBiByTocDo(this.axios, { vloaitb_id: this.loaitb_id, vtocdo_id: this.tocdo_id })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách thiết bị')
        return []
      }
    },
    async getDanhSachTbi() {
      try {
        const response = await api.getDanhSachTbi(this.axios, { vloaitb_id: this.loaitb_id })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách thiết bị')
        return []
      }
    },
    async ChangeCell_ReadOnly() {
      const tienTraTruocEdit = {
        create: () => {
          tienTraTruocElm = document.createElement('input')
          return tienTraTruocElm
        },
        read: () => {
          return tienTraTruocObj.value
        },
        destroy: () => {
          tienTraTruocObj.destroy()
        },
        write: (args) => {
          const that = this
          tienTraTruocObj = new TextBox({
            value: args.rowData[args.column.field],
            input: function (args) {
              const formEle = document.getElementById('grid').querySelector('form').ej2_instances[0]
              const tienTraTruocValue = +formEle.getInputElement('tien_tratruoc').value
              const vatTraTruocEle = formEle.getInputElement('vat_tratruoc')
              const vatTraTruocValue = Math.round(tienTraTruocValue / 10)
              vatTraTruocEle.value = vatTraTruocValue
              const found = that.dgrDS.dataSources.find((elm) => elm.loaitbi_id === selectedItem.loaitbi_id)
              if (found) {
                found.tien_tratruoc = tienTraTruocValue
                found.vat_tratruoc = vatTraTruocValue
              }
            }
          })
          tienTraTruocObj.appendTo(tienTraTruocElm)
        }
      }
      if (!this.sDSChiTietKM_ID) this.sDSChiTietKM_ID = '-1'
      for (const element of this.dgrDS.dataSources) {
        const countDtTmp = await this.countCTKMTraGopTbi(this.sDSChiTietKM_ID, element.loaitbi_id, 1)
        element.tien_tratruoc = 0
        element.vat_tratruoc = 0
        if (+countDtTmp === 0) {
          this.setColumnAllowEdit('tien_tratruoc', false)
          this.setTienTraTruocColumnEdit(null)
        } else {
          this.setColumnAllowEdit('tien_tratruoc', true)
          this.setTienTraTruocColumnEdit(tienTraTruocEdit)
        }
        this.setColumnAllowEdit('vat_tratruoc', +countDtTmp !== 0)
        this.setColumnAllowEdit('serial', element.soluong.toString() === '1')
      }
    },
    async countCTKMTraGopTbi(param, param1, type) {
      try {
        const response = await api.countCTKMTraGopTbi(this.axios, { param, param1, type })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : ''
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin trả góp thiết bị')
        return ''
      }
    },
    setColumnAllowEdit(fieldName, allowEditing) {
      this.dgrDS.headers.find((header) => header.fieldName === fieldName).allowEditing = allowEditing
    },
    setTienTraTruocColumnEdit(edit) {
      this.dgrDS.headers.find((header) => header.fieldName === 'tien_tratruoc').edit = edit
    },
    getColumnDangKy(parent) {
      return function () {
        return {
          template: {
            template: `
              <div class="check-action">
                <input
                  type="checkbox"
                  class="check"
                  v-model="data.dangky"
                />
                <span class="name"></span>
              </div>
            `,
            data() {
              return {
                parent: parent,
                data: {}
              }
            },
            watch: {
              'data.dangky'(newVal) {
                const tbi = this.parent.dgrDS.dataSources.find((item) => item.loaitbi_id === this.data.loaitbi_id)
                tbi.dangky = newVal ? 1 : 0
                const gridObj = this.parent.$refs.gridInner.$refs.grid.ej2Instances
                gridObj.aggregateModule.refresh(selectedItem)
              }
            }
          }
        }
      }
    },
    actionComplete(args) {
      if (args.requestType === 'beginEdit') {
        selectedItem = {}
        selectedItem = args.rowData
        const serialElement = args.form[4].ej2_instances[0]
        serialElement.element.disabled = args.rowData.soluong.toString() !== '1'
      } else if (args.requestType === 'save') {
        this.dgrDS.dataSources = [...this.dgrDS.dataSources]
      }
    },
    showModal() {
      this.$refs.muaThietBiRef.show()
    },
    tienAggregateFn(d) {
      return this.tinhTongAggr(d, 'tien')
    },
    vatAggregateFn(d) {
      return this.tinhTongAggr(d, 'vat')
    },
    giablkAggregateFn(d) {
      return this.tinhTongAggr(d, 'gia_block_tiep')
    },
    vatblkAggregateFn(d) {
      return this.tinhTongAggr(d, 'vat_block_tiep')
    },
    tinhTongAggr(d, e) {
      const items = Array.isArray(d) ? d : d.result
      const fdata = items.filter((item) => item.dangky === 1)
      if (fdata && fdata.length > 0) {
        if (e === 'tien' || e === 'vat') return fdata.map((x) => x[e] * x.soluong).reduce((a, b) => a + b, 0)
        else if (e === 'gia_block_tiep') {
          let tienTbi = 0
          for (const element of fdata) {
            const vsoluong = +element.soluong
            const vslCha = +element.sl_cha
            if (vslCha !== -1) {
              if (element.block_tiep.toString() && element.gia_block_tiep.toString() && element.gia_block_tiep.toString() !== '0') {
                const blockDau = +element.block_dau
                if (vslCha > blockDau) {
                  const vslConLai = vslCha - blockDau
                  const t2 = vslConLai % +element.block_tiep
                  const vslBl = t2 > 0 ? vslConLai / +element.block_tiep + 1 : vslConLai / +element.block_tiep
                  const tienBlock = vslBl * +element.gia_block_tiep
                  tienTbi += tienBlock * vsoluong
                }
              }
            }
          }
          return tienTbi
        } else {
          let vatTbi = 0
          for (const element of fdata) {
            const vsoluong = +element.soluong
            const vslCha = +element.sl_cha
            if (vslCha !== -1) {
              if (element.block_tiep.toString() && element.vat_block_tiep.toString() && element.vat_block_tiep.toString() !== '0') {
                const blockDau = +element.block_dau
                if (vslCha > blockDau) {
                  const vslConLai = vslCha - blockDau
                  const t3 = vslConLai % +element.block_tiep
                  const rslBl = t3 > 0 ? vslConLai / +element.block_tiep + 1 : vslConLai / +element.block_tiep
                  const vatBlock = rslBl * +element.vat_block_tiep
                  vatTbi += vatBlock * vsoluong
                }
              }
            }
          }
          return vatTbi
        }
      } else return 0
    },
    tsbtnChapNhan_Click() {
      const dsTbi = []
      for (let i = 0; i < this.dgrDS.dataSources.length; i++) {
        const element = this.dgrDS.dataSources[i]
        if (element.dangky.toString() === '1') {
          if ((this.loaitb_id === LoaiHinhTB.WIFI_MESH || this.loaitb_id === LoaiHinhTB.INDOOR_CAM || this.loaitb_id === LoaiHinhTB.OUTDOOR_CAM) && +element.soluong > 1) {
            this.$root.$toast.warning('Loại hình thuê bao chỉ cho phép mua 01 thiết bị')
            break
          }
          if (+element.tien_tratruoc > +element.tien - +element.tien_km) {
            this.$root.$toast.warning(`Tiền trả trước tại dòng ${i + 1} lớn hơn tiền thiết bị!`)
            break
          }
          
          const tbi = {
            loaitbi_id: +element.loaitbi_id,
            soluong: +element.soluong,
            hdtb_id: i,
            tien: element.tien.toString() ? +element.tien : 0,
            vat: element.vat.toString() ? +element.vat : 0,
            tien_km: +element.tien_km,
            vat_km: +element.vat_km,
            serial: element.serial ? element.serial.toString() : '',
            tien_tratruoc: +element.tien_tratruoc,
            vat_tratruoc: +element.vat_tratruoc,
            tien_tragop: 0,
            vat_tragop: 0,
            tyle_vat: +element.tyle_vat,
            tyle_vat_id: +element.tyle_vat_id,
            sl_cha: +element.sl_cha,
            block_dau: +element.block_dau,
            block_tiep: +element.block_tiep,
            gia_block_tiep: +element.gia_block_tiep,
            vat_block_tiep: +element.vat_block_tiep,
            tien_thue: 0,
            tong_tien: 0,
            tong_thue: 0
          }
          dsTbi.push(tbi)
        }
      }
      //console.log(dsTbi)
      this.$emit('form-close', dsTbi)      
      this.tsbtnThoat_Click()
    },
    tsbtnThoat_Click() {
      this.$refs.muaThietBiRef.hide()
    }
  }
}
</script>

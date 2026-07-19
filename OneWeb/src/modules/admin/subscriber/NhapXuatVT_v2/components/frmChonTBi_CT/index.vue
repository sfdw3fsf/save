<template>
  <div class="">
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a :class="{ disabled: !btnXacNhanEnabled }" class="pointer" @click="btnLayTT"> <span class="icon one-search"></span> Lấy TT </a>
        </li>
        <li :class="{ disabled: !btnXacNhanEnabled }">
          <a class="pointer" @click="btnXacNhan"> <span class="icon one-save"></span> Xác nhận </a>
        </li>
        <li v-show="btnShowHuyAndHC">
          <a class="pointer" @click="btnHuyXN"> <span class="icon one-trash"></span> Hủy bỏ </a>
        </li>
        <li v-show="btnShowHuyAndHC">
          <a class="pointer" @click="btnHoanCong"> <span class="icon one-file-search"></span> Hoàn Công </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="legend-title mart10">Danh sách vật tư chứng từ</div>
        <DataGrid
          ref="gridVatTu"
          :columns="[
            { fieldName: 'LOHANG', headerText: 'Lô hàng', allowFiltering: true, width: 130 },
            { fieldName: 'MA_VT', headerText: 'Mã vật tư', allowFiltering: true, isPrimaryKey: true, width: 130 },
            { fieldName: 'TEN_VT', headerText: 'Tên vật tư', allowFiltering: true, width: 250 },
            { fieldName: 'DVI_TINH', headerText: 'ĐVT', allowFiltering: true, width: 80 },
            { fieldName: 'SOLUONG', headerText: 'Số lượng', allowFiltering: true, width: 130, format: 'N0', columnType: 'numeric', type: 'number' },
            { fieldName: 'SL_CHON', headerText: 'SL Đã chọn', allowFiltering: true, width: 130, format: 'N0', columnType: 'numeric', type: 'number' },
            { fieldName: 'DONGIA', headerText: 'Đơn giá', allowFiltering: true, width: 130, format: 'N0', columnType: 'numeric', type: 'number' },
            { fieldName: 'TIEN', headerText: 'Thành tiền', width: 130, allowEditing: true, format: 'N0', type: 'number', columnType: 'numeric', editType: 'numericedit' },
            { fieldName: 'SOTHANG_PB', headerText: 'Số tháng PB', allowFiltering: true, width: 130, format: 'N0', columnType: 'numeric', type: 'number', editType: 'numericedit', allowEditing: true },
            { fieldName: 'LOAI_TBI', headerText: 'Loại thiết bị', allowFiltering: true, width: 130 },
            { fieldName: 'HAN_BH', headerText: 'Hạn BH', allowFiltering: true, width: 130 },
            { fieldName: 'MA_HD', headerText: 'Mã HĐ', allowFiltering: true, width: 130 },
            { fieldName: 'DUAN', headerText: 'Dự án', allowFiltering: true, width: 130 },
            { fieldName: 'MA_TH', headerText: 'Đợt TH', allowFiltering: true, width: 130 },
            { fieldName: 'GHICHU_HD', headerText: 'Ghi chú HĐ', allowFiltering: true, width: 130 }
          ]"
          :dataSource="gridVatTu.dataSources"
          :allowPaging="true"
          :enablePagingServer="false"
          :showFilter="false"
          :enabledSelectFirstRow="false"
        ></DataGrid>
      </div>
      <div class="box-form">
        <div class="legend-title mart10">
          Danh sách thiết bị tạm ứng
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w30 nowrap">Từ công trình</div>
              <div class="value">
                <SelectExt ref="cboMucDich" v-model="cboCongTrinhValue" :dataSource="cboCongTrinh" dataTextField="TEN_CT" dataValueField="CONGTRINH_ID" />
              </div>
            </div>
          </div>
        </div>
        <DataGrid
          ref="gridDSVTGiao"
          :columns="columnsHeaderVTGCPT"
          :dataSource="gridDSVTGiao.dataSources"
          dataKeyField="TUVT_ID"
          :editSettings="{ allowEditing: true, mode: 'Batch' }"
          :allowPaging="true"
          :enablePagingServer="false"
          :showFilter="false"
          @rowSelected="selectedRowGridDSVTGiao"
          @cellSaved="editGridDSVTGiao"
          :enabledSelectFirstRow="false"
        ></DataGrid>
      </div>
    </div>
  </div>
</template>

<script>
import API from './ChonThietBiCT.js'
import _ from 'lodash'

export default {
  components: {},
  props: {
    vchungtu_id: {
      type: [Number, String],
      required: true
    },
    vcongtrinh_id: {
      type: [Number, String],
      required: true
    },
    vkho_id: {
      type: [Number, String],
      required: true
    }
  },
  data() {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      xacnhan: false,
      // vcongtrinh_id: '',
      bqlda: false, // nếu là điều chỉnh bqlda thì = true (menu nhapxuat v2)
      cboCongTrinh: [],
      cboCongTrinhValue: '',
      checkAllVTGiao: false,
      btnShowHuyAndHC: false,
      btnXacNhanEnabled: false,

      gridVatTu: {
        dataSources: [],
        selectingItem: null,
        selectedItems: []
      },
      gridDSVTGiao: {
        dataSources: [],
        selectingItem: null,
        selectedItems: []
      }
    }
  },
  watch: {
    checkAllVTGiao(value) {}
  },
  computed: {
    columnsHeaderVTGCPT() {
      return [
        { fieldName: 'NGAY_HT', headerText: 'Ngày CT', allowFiltering: true, width: 130 },
        { fieldName: 'NGAYGIAO', headerText: 'Ngày cấp', allowFiltering: true, width: 130 },
        { fieldName: 'TEN_VT', headerText: 'Tên vật tư', allowFiltering: true, width: 220 },
        { fieldName: 'SERIAL', headerText: 'Serial', allowFiltering: true, width: 130 },
        { fieldName: 'SL_TON', headerText: 'Số lượng', allowFiltering: true, type: 'number', format: 'N3', width: 100 },
        { fieldName: 'DONGIA', headerText: 'Đơn giá', allowFiltering: true, width: 130, format: 'N0', type: 'number' },

        { fieldName: 'ISCHECK', headerText: '', allowSorting: false, allowFiltering: false, textAlign: 'Center', allowEditing: false, width: 40, template: this.getCheckboxTemplate(this, 'ISCHECK', 'gridDSVTGiao'), headerTemplate: this.columnsCheckboxAllVTGiao(this) },

        { fieldName: 'LUONGCAP', headerText: 'SL cấp', allowFiltering: true, allowEditing: this.btnXacNhanEnabled, editType: 'numericedit', type: 'number', format: 'N3', width: 100 },

        { fieldName: 'LOHANG', headerText: 'Lô hàng', allowFiltering: true, width: 130 },
        { fieldName: 'DVI_TINH', headerText: 'ĐVT', allowFiltering: true, width: 130 },
        { fieldName: 'MA_TBI', headerText: 'Mã Tbị', allowFiltering: true, width: 130 },
        { fieldName: 'SO_PHIEU', headerText: 'Số phiếu', allowFiltering: true, width: 130 },
        { fieldName: 'GHICHU', headerText: 'Ghi chú', allowFiltering: true, width: 130 },
        { fieldName: 'TEN_CT', headerText: 'Công trình', allowFiltering: true, width: 130 },
        { fieldName: 'TUVT_ID', headerText: '', visible: false, isPrimaryKey: true }
      ]
    }
  },

  methods: {
    reset() {
      this.xacnhan = false
      this.bqlda = false
      this.cboCongTrinh = []
      this.cboCongTrinhValue = ''
      this.checkAllVTGiao = false
      //design
      this.gridVatTu = {
        dataSources: [],
        selectingItem: null,
        selectedItems: []
      }
      this.gridDSVTGiao = {
        dataSources: [],
        selectingItem: null,
        selectedItems: []
      }
      this.btnShowHuyAndHC = false
      this.btnXacNhanEnabled = false
    },
    btnLayTT() {
      this.reset()
      this.NAP_DANHSACH()
    },
    btnXacNhan() {
      //   const dtVatTu = this.gridVatTu.dataSources
      if (!this.gridDSVTGiao.dataSources.length) {
        return this.$toast.error('Không có thông tin vật tư để xác nhận')
      }
      if (!this.gridVatTu.dataSources.length) {
        return this.$toast.error('Không có thông tin vật tư chứng từ không thể xác nhận')
      }
      const dsVatTuGiaoSelected = this.gridDSVTGiao.dataSources.filter((e) => e.ISCHECK == 1)

      if (!dsVatTuGiaoSelected.length) {
        this.$toast.error('Bạn chưa chọn thiết bị để xác nhận')
        return
      }

      //Group
      const groupedData = _.groupBy(dsVatTuGiaoSelected, (item) => `${item.VATTU_ID}-${item.LOHANG}`)

      const tempVTG = _.mapValues(groupedData, (group) => _.sumBy(group, 'LUONGCAP'))

      this.gridVatTu.dataSources.forEach((item) => {
        const key = `${item.VATTU_ID}-${item.LOHANG}`;
        const sldc = tempVTG[key] || 0;
        if(sldc >= item.SOLUONG ){
          item.SL_CHON = item.SOLUONG  
        }else{
          item.SL_CHON = sldc;
        }
      })

      this.gridDSVTGiao.dataSources.forEach((vtg) => {
        const vattu  = this.gridVatTu.dataSources.find(e=> e.VATTU_ID == vtg.VATTU_ID );
        if(vattu?.LOHANG == vtg.LOHANG){
          vtg.CTCT_CT_ID = vattu.CTCT_ID
        }
      })

      this.btnXacNhanEnabled = false
      this.btnShowHuyAndHC = true
  
      // this.$refs.gridVatTu.grid.refreshColumns()
      // this.$refs.gridDSVTGiao.grid.refreshColumns()
    },
    btnHuyXN() {

      this.gridVatTu.dataSources.forEach((item) => {
        item.SL_CHON =  "";  
      })


      this.btnXacNhanEnabled = true;
      
      this.btnShowHuyAndHC = false;
    },
    async btnHoanCong() {
      console.log('btnHoanCong')
      if (this.gridVatTu.dataSources.length <= 0) return
      if (this.gridVatTu.dataSources.filter((e) => e.SL_CHON != e.SOLUONG).length > 0) {
        this.$toast.error('Số lượng bạn chọn chưa khớp')
        return
      }
      if (this.gridVatTu.dataSources.filter((e) => e.SL_CHON == 0).length > 0) {
        this.$toast.error('Bạn chưa chọn hết vật tư')
        return
      }
      try {
        this.loading(true)
        let data = {
          khoId: this.vkho_id,
          dsVt: this.gridDSVTGiao.dataSources,
          congTrinhId: this.vcongtrinh_id,
          chungTuId: this.vchungtu_id
        }

        let response = await API.postDieuChuyenVTCT(this.axios, data)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          if (response.data.data == 1) {
            this.xacnhan = true
            this.$emit('closed', true)
            this.$toast.success('Chon Thiet Bi Thanh Cong')
            this.$refs.dlgChonTBCT.hide()
            return
          }
          this.$toast.error(`${response.data.data}`)
        }
      } catch (error) {
        this.$toast.error(`${error.data.message_detail}`)
      } finally {
        this.loading(false)
      }
    },

    async NAP_DANHSACH() {
      try {
        this.loading(true)
        this.btnXacNhanEnabled = true
        this.btnShowHuyAndHC = false
        let response = await API.getDSVTChungTu(this.axios, {
          chungTuId: this.vchungtu_id
        })
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
          this.gridVatTu.dataSources = response.data.data
          let ds = this.gridVatTu.dataSources.map((e) => {
            return {
              LOHANG: e.LOHANG,
              VATTU_ID: e.VATTU_ID
            }
          })
          const data = {
            khoId: this.vkho_id,
            ds: ds,
            congTrinhId: this.vcongtrinh_id,
            nhanVienId: this.$auth.getNhanVienID(),
            bqlDa: this.bqlda
          }
          let responsea = await API.postDSVTGiao(this.axios, data)
          if (responsea.data.error_code === 'BSS-00000000' && responsea.data.data !== undefined) {
            // responsea.data.data.forEach((e, index) => {
            //   e['STT'] = index
            // })

            this.gridDSVTGiao.dataSources = responsea.data.data
            this.NAP_COMBO_CT(this.gridDSVTGiao.dataSources)
          }
        }
      } catch (error) {
        this.$toast.error(`${error.data.message_detail}`)
        console.log(error)
      } finally {
        this.loading(false)
      }
    },
    NAP_COMBO_CT(ds) {
      let distinctCT = _.uniqBy(ds, 'CONGTRINH_ID')
      const arrayUniqueByKey = [...new Map(distinctCT.map((item) => [item['CONGTRINH_ID'], item])).values()]
      this.cboCongTrinh = arrayUniqueByKey
    },
    cboCongTrinhSelectChanged(value) {
      if (value) {
        this.cboCongTrinhValue = value.CONGTRINH_ID
      }
    },
    selectedRowGridDSVTGiao(value) {
      if (value?.data) {
        this.gridDSVTGiao.selectingItem = value
        this.cboCongTrinhValue = value.data['CONGTRINH_ID']
      }
    },

    async editGridDSVTGiao(arg) {
      let gridDSVTGiao = this.$refs.gridDSVTGiao

      let changes = gridDSVTGiao.getBatchChanges()

      if (changes.changedRecords.length == 0) {
        return
      }
      const itemChange = changes.changedRecords[0]
      if (arg.columnName == 'LUONGCAP') {
        if (itemChange.LUONGCAP > itemChange.SL_TON) {
          changes.changedRecords[0].LUONGCAP = changes.changedRecords[0].SL_TON
          this.$toast.warning('Số lượng cấp không được lớn hơn số lượng hiện tại!')
        } else if (itemChange.LUONGCAP < 0) {
          changes.changedRecords[0].LUONGCAP = 0
          changes.changedRecords[0].ISCHECK = 0

          this.$toast.warning('Số lượng cấp không được âm!')
        } else if (itemChange.LUONGCAP == 0) {
          changes.changedRecords[0].ISCHECK = 0
        } else {
          changes.changedRecords[0].ISCHECK = 1
        }
      }
      //check số nhập vào có lớn hơn số lượng vật tư không
      const vatTu = this.gridVatTu.dataSources.find(vt => vt.VATTU_ID == itemChange.VATTU_ID &&  vt.LOHANG == itemChange.LOHANG);

      if(itemChange.LUONGCAP >= vatTu?.SOLUONG ){
        changes.changedRecords[0].LUONGCAP = vatTu?.SOLUONG;
      }
      
      gridDSVTGiao.batchUpdate(changes);
      gridDSVTGiao.grid.refresh()
    },
    getCheckboxTemplate(parent, field, grid) {
      return function() {
        return {
          template: {
            template: `<div class="template_checkbox"> <input type="checkbox" @change="change" :checked="data[field] == 1" :disabled="isDisable"> </div>`,
            data: function() {
              return { data: {}, field }
            },
            methods: {
              change(event) {
                const dataUpdate = parent.$refs[grid].dataSource[this.data.index]
                dataUpdate[field] = event.target.checked ? '1' : '0'
                if (event.target.checked == 1) {
                  const vatTu = parent.gridVatTu.dataSources.find(e => e.VATTU_ID == dataUpdate.VATTU_ID &&  e.LOHANG == dataUpdate.LOHANG);
                
                  if(vatTu?.SOLUONG >= dataUpdate['SL_TON']){
                    // dataUpdate['LUONGCAP'] = dataUpdate['SL_TON']
                  }else{
                    dataUpdate.LUONGCAP = vatTu.SOLUONG;
                    // dataUpdate['LUONGCAP'] = vatTu.SOLUONG;
                  }
                 
                } else {
                  dataUpdate.LUONGCAP = 0
                  // dataUpdate['LUONGCAP'] = 0
                }
         
                parent.$refs[grid].grid.refresh()
              }
            },
            computed: {
              //cho disable ko cho sửa
              isDisable() {
                return !parent.btnXacNhanEnabled
              }
            }
          }
        }
      }
    },
    columnsCheckboxAllVTGiao(parent) {
      return function() {
        return {
          template: {
            template: `<div class="template_checkbox"> <input type="checkbox"  :checked="checkAllVTGiao"  @change="change" :disabled="isDisable"> </div>`,

            methods: {
              change(event) {
                if (event.target.checked) {
                  parent.$refs.gridDSVTGiao.dataSource.forEach((e) => {
                    if (e.SL_TON > 0) {
                      const vatTu = parent.gridVatTu.dataSources.find(vt => vt.VATTU_ID == e.VATTU_ID &&  vt.LOHANG == e.LOHANG);
                      if(vatTu?.SOLUONG >= e.SL_TON){
                        e.ISCHECK = 1
                        e.LUONGCAP = e.SL_TON
                      }else{
                        e.ISCHECK = 1
                        e.LUONGCAP = vatTu.SOLUONG
                      }
             
                    }
                  })
                } else {
                  parent.$refs.gridDSVTGiao.dataSource.forEach((e) => {
                    if (e.SL_TON > 0) {
                      e.ISCHECK = 0
                      e.LUONGCAP = 0
                    }
                  })
                }
                parent.checkAllVTGiao = event.target.checked
              }
            },

            computed: {
              isDisable() {
                return !parent.btnXacNhanEnabled
              },
              checkAllVTGiao() {
                return parent.checkAllVTGiao
              }
            }
          }
        }
      }
    }
  },
  async mounted() {
    this.cboCongTrinhValue = this.vcongtrinh_id
    await this.NAP_DANHSACH()
  }
}
</script>

<style scoped>
.template_input input[type='number'] {
  width: 100px;
  height: 20px;
}
.highlight {
  background-color: yellow;
  /* Other styles */
}
</style>

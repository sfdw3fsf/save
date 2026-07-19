<template>
  <div class="box-form">
    <div class="legend-title">
      Thông tin thuê bao khách hàng
    </div>
    <div class="nav tab-over tabs">
      <a href="#tb-tra-truoc-trong-ky" data-toggle="tab" style="min-width: 200px !important;" @click="tab_active = 1" :class="tab_active == 1 ? 'minw0 active' : 'minw0'">D/S TB trả trước cần tư vấn</a>

      <a href="#tb-tra-truoc-lech-ky" data-toggle="tab" style="min-width: 200px !important;" @click="tab_active = 2" :class="tab_active == 2 ? 'minw0 active' : 'minw0'">DS TB trả sau</a>
      <a href="#hddc-chua-thanh-toan" data-toggle="tab" style="min-width: 200px !important;" @click="tab_active = 3" :class="tab_active == 3 ? 'minw0 active' : 'minw0'">D/S HDDC chưa thanh toán</a>
    </div>
    <div class="tab-content">
      <div :class="tab_active == 1 ? 'tab-pane active fullh' : 'tab-pane fullh'" id="tb-tra-truoc-trong-ky">
        <TDataGrid
          ref="refGridTBTraTruoc"
          :columns="gridTB_TraTruoc.columns"
          :dataSource="gridTB_TraTruoc.dataSources"
          :enable-paging-server="false"
          :showFilter="true"
          :allowGrouping="true"
          :showColumnCheckbox="true"
          :enabledSelectFirstRow="true"
          panelDataHeight="420px"
          @rowSelected="onSelectRow_DSTB"
        />
      </div>
      <div :class="tab_active == 2 ? 'tab-pane active fullh' : 'tab-pane fullh'" id="tb-tra-truoc-lech-ky">
        <!-- DS TB đặt cọc trả sau -->
        <TDataGrid :dataSource="gridTB_TraSau.dataSources" 
        :columns="gridTB_TraSau.columns" 
        ref="refGridTBTraSau" 
        :allowSorting="true" 
        :enablePagingServer="false" 
        panelDataHeight="420px" 
        :showColumnCheckbox="true" 
        @rowSelected="onSelectRow_DSTB" 
        :allowGrouping="true" />
      </div>
      <div :class="tab_active == 3 ? 'tab-pane active fullh' : 'tab-pane fullh'" id="hddc-chua-thanh-toan">
        <!-- D/S HDDC chua thanh toan -->

        <TDataGrid
          :dataSource="gridTB_HDDC_ChuaThanhToan.dataSources"
          :columns="gridTB_HDDC_ChuaThanhToan.columns"
          ref="refGridTBHDDCChuaThanhToan"
          :allowSorting="true"
          :enablePagingServer="false"
          panelDataHeight="420px"
          @queryCellInfo="dsTBCTT_QueryCellInfo"
          :allowGrouping="true"
          :contextMenuItems="gridTB_HDDC_ChuaThanhToan.contextMenuItems"
          @contextMenuClick="onContextMenuClick"
          :showColumnCheckbox="false"
        
        />
      </div>
    </div>
    <!-- <QRCodeDialog ref="QRcodeComponent" /> -->
  </div>
</template>

<script>
import api from '../../../API'
import apiHelper from '../../../api.helper'
import Vue from 'vue'
import VueQRCodeComponent from 'vue-qrcode-component'
import TDataGrid from './DataGrid'
import _ from 'lodash'
import util from '../../../util'
// import QRCodeDialog from '../../QRcodePopup/index.vue'

export default {
  components: {
    TDataGrid
    // QRCodeDialog
  },
  name: 'ThongTinTBKH',
  props: {

    sdt_kh: {
      type: String,
      default: '',
      require: true
    },
    phanvung_kh_id: {
      type: Number,
      default: 0
    },
    //kh_id do media truyen sang
    customer_id: {
      type: Number,
      default: 0
    },
    cbKqOBValue: {
      type: Number,
      default: 0
    }
  },

  data() {
    return {
      khachhang_ids: "",
      gridTB_TraTruoc: {
        columns: [
          { fieldName: 'MA_KH', headerText: '', isGroupedColumn: true, captionTemplate: this.captionTB_TraTruocemplate(this) },
          { fieldName: 'GROUP_GOID', headerText: '', isGroupedColumn: true, captionTemplate: this.captionTB_TraTruocemplate(this) },
          { fieldName: 'THANG_KTDC', headerText: 'Tháng KTĐC', width: '100px' },
          { fieldName: 'MA_TB', headerText: 'Số máy/Acc', width: '130px' },
          { fieldName: 'TEN_TB', headerText: 'Tên TB', width: '150px' },
          { fieldName: 'MA_TT', headerText: 'Mã thanh toán', width: '150px' },
          { fieldName: 'SDT_TT', headerText: 'SĐT TT', width: '150px' },
          { fieldName: 'DIACHI_LD', headerText: 'ĐC lắp đặt', width: '150px' },
          { fieldName: 'SDT_KH', headerText: 'SĐT KH', width: '100px' },
          { fieldName: 'LOAIHINH_TB', headerText: 'Loại hình TB', width: '120px' },
          { fieldName: 'TOCDO', headerText: 'Tốc độ', width: '120px' },
          { fieldName: 'TEN_GOI', headerText: 'Gói', width: '130px' },
          { fieldName: 'CHI_TIET_TT', headerText: 'Chi tiết TT', width: '120px' },
          { fieldName: 'HUONG_DC', headerText: 'Số tháng', width: '120px' },
          { fieldName: 'NGAY_BDDC', headerText: 'Ngày BDĐC', width: '120px' },
          { fieldName: 'NGAY_KTDC', headerText: 'Ngày KTĐC', width: '120px' },
          { fieldName: 'TIEN_DC_DISPLAY', headerText: 'Tiền ĐC', width: '120px' },
          { fieldName: 'CUOC_TS', headerText: 'Cước TS', width: '120px' },
          { fieldName: 'TT_GIAHAN', headerText: 'TT gia hạn', width: '130px', template: this.giaHanTemplate() },
          { fieldName: 'KHUYEN_NGHI', headerText: 'Khuyến nghị', width: '120px' },
          { fieldName: 'TU_VAN', headerText: 'Tư vấn', width: '120px' }
        ],
        dataSources: []
      },
      gridTB_TraSau: {
        columns: [
          { fieldName: 'MA_KH', headerText: '', isGroupedColumn: true, captionTemplate: this.captionTB_TraSauTemplate(this) },
          { fieldName: 'GOI_ID', headerText: '', isGroupedColumn: true, captionTemplate: this.captionTB_TraSauTemplate(this) },
          { fieldName: 'MA_TB', headerText: 'Số máy/Acc', width: '130px' },
          { fieldName: 'TEN_TB', headerText: 'Tên TB', width: '150px' },
          { fieldName: 'MA_TT', headerText: 'Mã TT', width: '150px' },
          { fieldName: 'SDT_TT', headerText: 'Số ĐT TT', width: '150px' },
          { fieldName: 'DIACHI_LD', headerText: 'Địa chỉ LĐ', width: '150px' },
          { fieldName: 'SDT_KH', headerText: 'Số ĐT KH', width: '150px' },
          { fieldName: 'LOAIHINH_TB', headerText: 'Loại hình TB', width: '120px' },
          { fieldName: 'TOCDO', headerText: 'Tốc độ', width: '120px' },
          { fieldName: 'CUOC_TS', headerText: 'Cước TS', width: '120px' },
          { fieldName: 'KHUYEN_NGHI', headerText: 'Khuyến nghị', width: '120px' },
          { fieldName: 'TU_VAN', headerText: 'Tư vấn', width: '120px' }
        ],
        dataSources: []
      },

      gridTB_HDDC_ChuaThanhToan: {
        columns: [
          { fieldName: 'MA_KH', headerText: '', isGroupedColumn: true, captionTemplate: this.captionTB_ChuaTTTemplate(this) },
          { fieldName: 'MA_TT', headerText: '', isGroupedColumn: true, captionTemplate: this.captionTB_ChuaTTTemplate(this) },
          { fieldName: 'MA_TB', headerText: 'Số máy/Acc', width: '130px' },
          { fieldName: 'TEN_TB', headerText: 'Tên TB', width: '150px' },
          { fieldName: 'MA_GD', headerText: 'Mã giao dịch', width: '130px' },
          { fieldName: 'DIACHI_LD', headerText: 'Địa chỉ ld', width: '250px' },
          { fieldName: 'NGAYHEN_TT', headerText: 'Ngày hẹn TT', width: '120px' },
          { fieldName: 'KQDUYET_HEN', headerText: 'Kết quả duyệt hẹn', width: '80px' },
          { fieldName: 'SONGAY_QH', headerText: 'Số ngày qúa hẹn', width: '80px' },
          { fieldName: '', headerText: 'Link QRCode', width: '220px', template: this.qrCodeTemplate(this) },

          { fieldName: '', headerText: 'Chuyển đặt cọc', width: '100px', template: this.buttonRedirectTemplate(this) }
        ],
        dataSources: [],
        contextMenuItems: [{ text: 'chuyyển phiếu đặt cọc', target: '.e-content', id: 'ctx-menu-giao-phieu' }]
      },
      tab_active: 1
    }
  },
  methods: {
    async getListThueBao_TraTruoc() {
      try {
        if (!this.sdt_kh) return
        this.loading(true)
        this.gridTB_TraTruoc.dataSources = []
        let response = await api.getListTBTraTruoc(this.axios, this.sdt_kh, this.phanvung_kh_id)

        const data = apiHelper.getDataFromResponseApiReturnArray(response)
        this.gridTB_TraTruoc.dataSources = data.map((item, index) => {
          return {
            ...item,
            TIEN_DC_DISPLAY: item.TIEN_DC.toLocaleString('vi-VN'),
            GROUP_GOID: `${item.GOI_ID}_${item.NHOMTB_ID}`,
          }
        })

        if (!this.gridTB_TraTruoc.dataSources.length) {
          this.$toast.warning(`Không tìm thấy các thuê bao sẽ kết thúc đặt cọc trong vòng 44 ngày của sđt ${this.sdt_kh}`)
        }


        const kh_ids = this.gridTB_TraTruoc.dataSources.map((item) => item.KHACHHANG_ID).join(',')
        this.$emit('thue-bao-tra-truoc-change', { kh_ids: kh_ids })
        this.khachhang_ids = kh_ids;


      } catch (error) {
        console.log(error)
      }finally {
        this.loading(false)
      }

    },
    async getListThueBao_TraSau() {
      try {
        this.loading(true)
        this.gridTB_TraSau.dataSources = []
        let res = await api.getListTBTraSau(this.axios, this.khachhang_ids)
        this.gridTB_TraSau.dataSources = apiHelper.getDataFromResponseApiReturnArray(res)
        if (!this.gridTB_TraSau.dataSources.length) {
          this.$toast.warning(`Không tìm thấy thông tin thuê bao trả sau`)
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },
    async getListDS_HD_Chua_ThanhToan() {
      try {
        this.loading(true)
        this.gridTB_HDDC_ChuaThanhToan.dataSources = []
        let res = await api.getListTBChuaHT(this.axios, this.khachhang_ids)
        this.gridTB_HDDC_ChuaThanhToan.dataSources = apiHelper.getDataFromResponseApiReturnArray(res)

       
      } catch (error) {
        console.log(error)
      }finally {
        this.loading(false)
      }
    },

    onSelectRow_DSTB(data) {
      if (!data?.data) return
      this.$emit('on-selecting-item', data)
      // this.khachhang_id = data.data?.KHACHHANG_ID
    },
    getListThueBaoSlected() {
      if (this.tab_active == 1) {
        return this.$refs.refGridTBTraTruoc.grid.getSelectedRecords()
      }
      if (this.tab_active == 2) {
        return this.$refs.refGridTBTraSau.grid.getSelectedRecords()
      }
      if (this.tab_active == 3) {
        return this.$refs.refGridTBHDDCChuaThanhToan.grid.getSelectedRecords()
      }
      return []
    },



    buttonRedirectTemplate(parent) {
      return function() {
        return {
          template: {
            template: `<div class="text-center"><button class="btn btn-main pad4 lh14 w40 btn-primary" @click="btnRedirectClick" disable="!isActiveBtn" >
                            <i class="fa fa-arrow-right"></i>
                        </button></div>`,
            data: function() {
              return { data: {} }
            },
            methods: {
              btnRedirectClick() {
                // console.log("data", this.data);
                parent.handleHDDCNotPaymentClick(this.data)
              }
            },
            computed: {
              isActiveBtn() {
                if ((this.data != undefined) & (this.data.SONGAY_QH >= 3)) return true
                return false
              }
            }
          }
        }
      }
    },
    qrCodeTemplate(parent) {
      return function() {
        return {
          template: {
            template: `<div class="text-center"><a @click="showQRCodeLink" >
                                    <span style="color: blue"> {{data.LINK_QR}}</span>
                                    </a></div>`,
            components: {},

            data: function() {
              return { data: {} }
            },
            methods: {
              showQRCodeLink() {
                console.log('qrLink', this.qrLink)
                if (!this.qrLink) {
                  parent.$toast.warning('Không có link QRCode')
                  return
                }
                window.open(this.qrLink, 'blank')
                // parent.$refs.QRcodeComponent.openDialog(this.qrLink);
              }
            },
            computed: {
              qrLink() {
                return this.data.LINK_QR || ''
              }
            }
          }
        }
      }
    },
    giaHanTemplate() {
      return function() {
        return {
          template: {
            template: `<div class="text-center" :style=giaHanStyle> {{giaHanText}}</div>`,
            components: {},

            data: function() {
              return { data: {} }
            },
            methods: {},
            computed: {
              giaHanText() {
                if (this.data.TT_GIAHAN == 0) return 'Không có chính sách trả trước nào được áp dụng cho thuê bao'
                if (this.data.TT_GIAHAN == 1) return 'Được phép gia hạn chính sách cũ'
                if (this.data.TT_GIAHAN == 2) return 'Phải thay đổi sang chính sách trả trước khác do chính sách cũ không còn được phép áp dụng'
              },
              giaHanStyle() {
                if (this.data.TT_GIAHAN == 1) return ''
                if (this.data.TT_GIAHAN == 2 || this.data.TT_GIAHAN == 0) return 'color: red'
              }
            }
          }
        }
      }
    },

    async onContextMenuClick(args) {
      if (args.item.id === 'ctx-menu-giao-phieu') {
        console.log('giao phieu')
      }
    },

    dsTBCTT_QueryCellInfo(args) {
      const { data } = args

    //   console.log(data.SONGAY_QH)
      if (data && data.SONGAY_QH >= 3) {
        args.cell.style.color = 'red'
        args.cell.style.fontWeight = 'bold'
      }
    },
    handleHDDCNotPaymentClick(data) {
      const query = { ma_gd: data.MA_GD }
      const routeData = this.$router.resolve({
        name: 'CapNhatThueBaoDatCoc2',
        query: query,
        path: '/ext-hopdong/Cap-Nhat-Thue-Bao-Dat-Coc-v2'
      })
      window.open(routeData.href, '_blank')
    },



    captionTB_TraTruocemplate: function(parent) {
      return function() {
        return {
          template: {
            template: `<div>{{captionValue}}</div>`,
            data: function() {
              return { data: {} }
            },
            computed: {
              captionValue: function(value) {
                const key = value.data.key

                const field = value.data.field

                if (field == 'MA_KH') {
                  const { items, count } = value.data
                  const tbTraTruoc = items[0]?.items[0]

                  return `Mã KH: ${tbTraTruoc.MA_KH} - ${tbTraTruoc.TEN_KH} - ${tbTraTruoc.SDT_KH} - ${tbTraTruoc.DIACHI_KH}`
                }

                if (field == 'GROUP_GOID') {
                  const { items, count } = value.data
                  const tbTraTruoc = items[0]
                  if (tbTraTruoc['GOI_ID']) {
                    const totalDc = items.reduce((total, item) => total + (Number(item?.TIEN_DC) || 0), 0).toLocaleString('vi-VN')

                    const sumTIEN_TG = items.reduce((total, item) => total + (Number(item?.TIEN_TG) || 0), 0).toLocaleString('vi-VN')

                    return `Gói đa dịch vụ: ${tbTraTruoc.TEN_GOI} - ${tbTraTruoc.HUONG_DC} tháng - Tiền trả trước: ${totalDc} VNĐ - Tiền trả hàng tháng khi không tham gia trả trước ${sumTIEN_TG} VNĐ - ${count} Bản ghi`
                  }

                  return `Mã TT: ${tbTraTruoc.MA_TT} - ${tbTraTruoc.TEN_TT} -  ${tbTraTruoc.SDT_TT} - ${tbTraTruoc.DIACHI_LD} - ${count} Bản ghi`
                }
              }
            },
            methods: {}
          }
        }
      }
    },

    captionTB_TraSauTemplate: function(parent) {
      return function() {
        return {
          template: {
            template: `<div>{{captionValue}}</div>`,
            data: function() {
              return { data: {} }
            },
            computed: {
              captionValue: function(value) {
                const key = value.data.key

                const field = value.data.field

                if (field == 'MA_KH') {
                  const { items, count } = value.data
                  const tbTraTruoc = items[0]?.items[0]

                  return `${tbTraTruoc.MA_KH} - ${tbTraTruoc.TEN_KH} - ${tbTraTruoc.SDT_KH} - ${tbTraTruoc.DIACHI_KH}`
                }

                if (field == 'GOI_ID') {
                  const { items, count } = value.data
                  const tbTraTruoc = items[0]
                  if (tbTraTruoc['GOI_ID']) {
                    const sumTIEN_TG = items
                      .reduce((total, item) => {
                        return total + Number(item.TIEN_TG || 0)
                      }, 0)
                      .toLocaleString('vi-VN')
                    return `Gói đa dịch vụ: ${tbTraTruoc.TEN_GOI} - Tiền trả hàng tháng khi không tham gia trả trước ${sumTIEN_TG} VNĐ - ${count} Bản ghi`
                  }

                  return `${tbTraTruoc.MA_TT} - ${tbTraTruoc.TEN_TT} -  ${tbTraTruoc.SDT_TT} - ${tbTraTruoc.DIACHI_LD} - ${count} Bản ghi`
                }
              }
            },
            methods: {}
          }
        }
      }
    },
    captionTB_ChuaTTTemplate: function(parent) {
      return function() {
        return {
          template: {
            template: `<div>{{captionValue}}</div>`,
            data: function() {
              return { data: {} }
            },
            computed: {
              captionValue: function(value) {
                const key = value.data.key

                const field = value.data.field

                if (field == 'MA_KH') {
                  const { items, count } = value.data
                  const item = items[0]?.items[0]

                  return `Mã KH: ${item.MA_KH} - ${item.TEN_KH} - ${item.SDT_KH} - ${item.DIACHI_KH}`
                }

                if (field == 'MA_TT') {
                  const { items, count } = value.data
                  const thueBao = items[0]
                  return `Mã TT: ${thueBao.MA_TT} - ${thueBao.TEN_TT} -  ${thueBao.SDT_TT} - ${thueBao.DIACHI_LD} - ${count} Bản ghi`
                }
              }
            },
            methods: {}
          }
        }
      }
    }
  },
  watch: {
    async sdt_kh(val) {
      if (val) {
        this.loading(true)
        await Promise.all([this.getListThueBao_TraTruoc()])
          .then(() => {
            this.loading(false)
            console.log('get danh sach TB success')
          })
          .catch((err) => {
            this.loading(false)
            console.log(err)
          })
      }
    },
    tab_active: {
      async handler(val) {
        if (!val) return
        this.$emit('tab-active', val)
        if (val == 1) {
          await this.getListThueBao_TraTruoc()
        } else if (val == 2) {
          await this.getListThueBao_TraSau()
        } else if (val == 3) {
          await this.getListDS_HD_Chua_ThanhToan()
        }
      },
      immediate: true
    },
    cbKqOBValue:{
      async handler(val){
        if(this.tab_active !== 1) return;
        this.$refs.refGridTBTraTruoc.flagSelectedRowIndexes = [];
        console.log('cbKqOBValue', val);
        if(val == 9 || val == 14){
          this.$refs.refGridTBTraTruoc.flagSelectedRowIndexes =  util.getListIndexItemSelected(this.gridTB_TraTruoc.dataSources);
        }
        this.$refs.refGridTBTraTruoc.refresh();
    },
    immediate: true
  }
  
  },
  computed: {}
}
</script>
<style scoped></style>

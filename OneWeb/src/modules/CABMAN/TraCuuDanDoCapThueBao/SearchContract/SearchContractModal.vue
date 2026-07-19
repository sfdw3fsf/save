<template>
  <b-modal
    ref="popupSearchContract"
    size="xl"
    body-class="p-0"
    hide-header
    hide-header-close
    @show="handleShowModal"
    hide-footer>
    <div class="modal-content popup-box">
        <div class="popup-header">
            <div class="title"><span class="icon one-notepad"></span>Tra cứu hợp đồng, phụ lục</div>
            <div class="close -ap icon-close" @click="hideModal"></div>
        </div>
        <ActionTop :actions="actions" @onActionClick="onActionClick"/>
        <div class="popup-body">
            <ThongTinTimKiem ref="thongtintimkiem" />
            <div class="box-form">
                <div class="legend-title"><span  @click="clickShowHideHD" class="fa icon" :class="{'fa-angle-up':expanded.hop_dong, 'fa-angle-down':!expanded.hop_dong,}"></span> Danh sách hợp đồng/phụ lục khách hàng</div>
                <DataGrid
                    :columns="columnsHD"
                    :dataSource="dataSourcesHD"
                    :showColumnCheckbox="false"
                    :enable-paging-server="false"
                    v-show="expanded.hop_dong"
                    :allowPaging="true"
                    :showFilter="true"
                    :enabledSelectFirstRow="true"
                    @selectedItemsChanged="gridRowsSelected"/>
            </div>
            <div class="box-form">
                <div class="legend-title"><span @click="clickShowHideTT" class="fa icon" :class="{'fa-angle-up':expanded.thanhtoan, 'fa-angle-down':!expanded.thanhtoan,}"></span>  Danh sách thanh toán</div>
                <DataGrid
                    :columns="columnsTT"
                    :dataSource="dataSourcesTT"
                    :showColumnCheckbox="false"
                    :enable-paging-server="false"
                    v-show="expanded.thanhtoan"
                    :allowPaging="true"
                    :showFilter="true"
                    :enabledSelectFirstRow="true"/>
            </div>
            <div class="box-form">
                <div class="legend-title"><span @click="clickShowHideTB"  class="fa icon" :class="{'fa-angle-up':expanded.thuebao, 'fa-angle-down':!expanded.thuebao,}"></span>   Danh sách thuê bao</div>
                <DataGrid
                    :columns="columnsTB"
                    :dataSource="dataSourcesTB"
                    :showColumnCheckbox="false"
                    :enable-paging-server="false"
                    v-show="expanded.thuebao"
                    :allowPaging="true"
                    :showFilter="true"
                    @selectedRowChanged="selectedRowChanged"
                    :enabledSelectFirstRow="true"/>
             </div>
        </div>
        <!-- Modal -->
        <!-- <InHoSo ref="inHoSo" :params="paramInHoSo"/>
        <InPhieuThu ref="inPhieuThu" :ma_gd="ma_gd_selected"/> -->
    </div>
  </b-modal>
</template>
<script>
import Footer from '@/components/FooterStyle.vue'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import ThongTinTimKiem from './ThongTinTimKiem.vue'
import SearchContractAPI from './SearchContractAPI'
import moment from 'moment'
import EventBus from '@/utils/eventBus'
// import InHoSo from '../../../../../print/InHopDong/InHopDong.vue'
// import InPhieuThu from '../../../../../print/InPhieuThu/InPhieuThu.vue'
export default {
  name: 'SearchContractModal',
  // mặc định loại hợp đồng, dịch vụ, ngày YC, trạng thái HĐ/PL và mã GD
  props: {
    // không truyền -1, disable check loaiHD/PL lấy row đầu và ngược lại
    loai_hd_id: {
      type: Number,
      default: -1
    },
    // Dịch vụ
    dichvu_id: {
      type: Number,
      default: 0
    },
    // ngày yêu cầu string format DD/MM/YYYY default '' checkbox disable
    ngay_yc: {
      type: String,
      default: ''
    },
    // ngày kết thúc string format DD/MM/YYYY default '' checkbox disable
    ngay_kt: {
      type: String,
      default: ''
    },
    // tiêu chí tra cứu id, trắng thì lấy mặc định đầu: tb.ma_tb = ':values' (Số máy)
    tieuchi_id: {
      type: String,
      default: ''
    },
    // thông tin tra cứu có thể: Số máy, mã giao dịch, tên miền phụ thuộc vào tieuchi_id
    thongtin_tracuu: {
      type: String,
      default: ''
    },
    // id trạng thái hợp đồng
    trangthai_hd: {
      type: Number,
      default: 0
    },
    // Bổ sung
    donvi_dl_id: {
      type: Number,
      default: -1
    },
    donvi_id: {
      type: Number,
      default: -1
    },
    nhanvien_id: {
      type: Number,
      default: -1
    }
  },
  components: {
    breadcrumb,
    Footer,
    ActionTop,
    ThongTinTimKiem
    // InHoSo,
    // InPhieuThu
  },
  data () {
    return {
      actions: [
        {
          id: 'timkiem',
          name: 'Tìm kiếm',
          active: true,
          icon_class: 'one-search'
        },
        {
          id: 'chapnhan',
          name: 'Chấp nhận',
          active: false,
          icon_class: 'nc-icon-glyph ui-1_check-circle-08'
        }
        // {
        //   id: 'hoso',
        //   name: 'Hồ sơ',
        //   active: true,
        //   icon_class: 'nc-icon-glyph files_single-content-02'
        // },
        // {
        //   id: 'inphieu_tt',
        //   name: 'In phiếu TT',
        //   active: true,
        //   icon_class: 'one-print'
        // },
        // {
        //   id: 'khuyenmai',
        //   name: 'Khuyến mại',
        //   active: true,
        //   icon_class: 'one-sale'
        // }
      ],
      columnsHD: [
        {
          fieldName: 'ma_gd',
          headerText: 'Mã giao dịch',
          allowFiltering: true,
          allowSorting: false,
          freeze: 'left'
        },
        {
          fieldName: 'ngay_yc',
          headerText: 'Ngày yêu cầu',
          allowFiltering: true,
          allowSorting: false,
          textAlign: 'left',
          freeze: 'left'
        },
        {
          fieldName: 'ma_kh',
          headerText: 'Mã khách hàng',
          allowFiltering: true,
          allowSorting: false,
          freeze: 'left'
        },
        {
          fieldName: 'ten_kh',
          headerText: 'Tên khách hàng',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'diachi_kh',
          headerText: 'Địa chỉ khách hàng',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'so_dt',
          headerText: 'Số liên hệ',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'ten_loaihd',
          headerText: 'Loại HĐ/PL',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'nguoi_cn',
          headerText: 'Người cập nhật',
          allowFiltering: true,
          allowSorting: false
        }
      ],
      dataSourcesHD: [],
      columnsTT: [
        {
          fieldName: 'ma_tt',
          headerText: 'Mã thanh toán',
          allowFiltering: true,
          allowSorting: false,
          freeze: 'left'

        },
        {
          fieldName: 'matb_dd',
          headerText: 'Mã đại diện',
          allowFiltering: true,
          allowSorting: false,
          textAlign: 'left',
          freeze: 'left'
        },
        {
          fieldName: 'ten_tt',
          headerText: 'Tên thanh toán',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'diachi_tt',
          headerText: 'Địa chỉ thanh toán',
          allowFiltering: true,
          allowSorting: false
        }

      ],
      dataSourcesTT: [],
      columnsTB: [
        {
          fieldName: 'ma_tb',
          headerText: 'Số máy/Acc',
          allowFiltering: true,
          allowSorting: false,
          textAlign: 'left',
          freeze: 'left'
        },
        {
          fieldName: 'ten_tb',
          headerText: 'Tên thuê bao',
          allowFiltering: true,
          allowSorting: false,
          freeze: 'left'
        },
        {
          fieldName: 'diachi_ld',
          headerText: 'Địa chỉ lắp đặt',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'diachi_tb',
          headerText: 'Địa chỉ thuê bao',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'ten_dvvt',
          headerText: 'Dịch vụ',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'loaihinh_tb',
          headerText: 'Loại hình',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'trangthai_hd',
          headerText: 'Trạng thái',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'site_id',
          headerText: 'Site ID',
          allowFiltering: true,
          allowSorting: false
        }

      ],
      dataSourcesTB: [],
      itemSelected: null,
      rowSelectedId: -1,
      expanded: {
        hop_dong: true,
        thanhtoan: true,
        thuebao: true
      },
      paramInHoSo: {
        hdkh_id: 0,
        hdtb_id: 0,
        ma_gd: ''
      }

    }
  },
  computed: {
    ma_gd_selected () {
      return this.itemSelected ? this.itemSelected.ma_gd : ''
    }
  },
  methods: {
    onActionClick (action) {
      if (action.id == 'timkiem') {
        this.dataSourcesHD = []
        this.dataSourcesTT = []
        this.dataSourcesTB = []
        var tt = this.$refs.thongtintimkiem.layThongTinTimKiem()

        if (tt.id == 'so_ao') {
          if (this.donvi_dl_id != -1) {
            tt.data.donviDLId = this.donvi_dl_id
          }
          if (this.donvi_id != -1) {
            tt.data.donviId = this.donvi_id
          }
          if (this.nhanvien_id != -1) {
            tt.data.nhanvienId = this.nhanvien_id
          }
          this.hopdong_theo_ma_lt(tt.data)
        } else if (tt.id == 'site_id') {
          this.hopdong_theo_site_id(tt.data)
        } else if (tt.id == 'so_hd_khdn') {
          this.hopdong_theo_so_hd(tt.data)
        } else if (tt.id == 'so_pl_khdn') {
          this.hopdong_theo_so_pl(tt.data)
        } else if (tt.id == 'ten_mien') {
          this.hopdong_theo_tenmien(tt.data)
        } else {
          if (this.donvi_dl_id != -1) {
            tt.data.donviDLId = this.donvi_dl_id
          }
          if (this.donvi_id != -1) {
            tt.data.donviId = this.donvi_id
          }
          if (this.nhanvien_id != -1) {
            tt.data.nhanvienId = this.nhanvien_id
          }
          this.hopdong_khac(tt.data)
        }
      } else if (action.id == 'chapnhan') {
        if (this.itemSelected == null || this.dataSourcesHD.length <= 0) {

        } else {
          this.$emit('accept', this.itemSelected)
          this.hideModal()
        }
      } else if (action.id == 'hoso') {
        this.paramInHoSo.hdkh_id = this.itemSelected ? this.itemSelected.hdkh_id : 0
        this.paramInHoSo.ma_gd = this.itemSelected ? this.itemSelected.ma_gd : ''
        this.paramInHoSo.hdtb_id = this.itemSelected ? this.itemSelected.hdtb_id : 0

        setTimeout(() => {
          this.$refs.inHoSo.showModal()
        }, 200)
      } else if (action.id == 'inphieu_tt') {
        if (this.dataSourcesTT.length == 0) {
          this.$toast.error('Hợp đồng chưa có thông tin thanh toán!')
          return
        }
        this.$refs.inPhieuThu.showModal()
      }
    },
    async hienThiThongTin (item) {
      this.itemSelected = Object.assign({}, item)
      this.actions[this.actions.findIndex(x => x.id == 'chapnhan')].active = true
      EventBus.$emit('search_contract', item)
      this.dataSourcesTT = []
      this.dataSourcesTB = []
      await this.ds_hopdong_tt_theo_hdkh_id(item.hdkh_id)
      await this.ds_hopdong_tb_theo_hdkh_id(item.hdkh_id)
    },
    gridRowsSelected (ds) {
      if (ds.length <= 0) {
        return
      }
      this.hienThiThongTin(ds[0])
    },
    clickShowHideHD () {
      this.expanded.hop_dong = !this.expanded.hop_dong
    },
    clickShowHideTB () {
      this.expanded.thuebao = !this.expanded.thuebao
    },
    clickShowHideTT () {
      this.expanded.thanhtoan = !this.expanded.thanhtoan
    },
    async hopdong_theo_ma_lt (data) {
      this.dataSourcesHD = []
      try {
        this.loading(true)
        let response = await SearchContractAPI.hopdong_theo_ma_lt(this.axios, data)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          response.data.data.forEach(element => {
            element.ngay_yc = moment(element.ngay_yc).format('DD/MM/YYYY')
          })
          this.dataSourcesHD = response.data.data
        } else {
          this.dataSourcesHD = []
        }
      } catch (e) {
        this.$toast.error('Đã xảy ra lỗi')
      } finally {
        this.loading(false)
      }
    },
    selectedRowChanged (row) {
      if (row === null) return
      console.log('🚀 ~ file: SearchContractModal.vue ~ line 436 ~ selectedRowChanged ~ row', row)
      this.itemSelected = {...this.itemSelected, ma_tb: row.ma_tb}
      console.log('🚀 ~ file: SearchContractModal.vue ~ line 438 ~ selectedRowChanged ~ this.itemSelected', this.itemSelected)
    },
    async hopdong_theo_site_id (siteId) {
      this.dataSourcesHD = []
      try {
        this.loading(true)
        let response = await SearchContractAPI.hopdong_theo_site_id(this.axios, siteId)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          response.data.data.forEach(element => {
            element.ngay_yc = moment(element.ngay_yc).format('DD/MM/YYYY')
          })
          this.dataSourcesHD = response.data.data
        } else {
          this.dataSourcesHD = []
        }
      } catch (e) {
        this.$toast.error('Đã xảy ra lỗi')
      } finally {
        this.loading(false)
      }
    },
    async hopdong_theo_tenmien (tenMien) {
      this.dataSourcesHD = []
      try {
        this.loading(true)
        let response = await SearchContractAPI.hopdong_theo_tenmien(this.axios, tenMien)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          response.data.data.forEach(element => {
            element.ngay_yc = moment(element.ngay_yc).format('DD/MM/YYYY')
          })
          this.dataSourcesHD = response.data.data
        } else {
          this.dataSourcesHD = []
        }
      } catch (e) {
        this.$toast.error('Đã xảy ra lỗi')
      } finally {
        this.loading(false)
      }
    },
    async hopdong_theo_so_hd (soHD) {
      this.dataSourcesHD = []
      try {
        this.loading(true)
        let response = await SearchContractAPI.hopdong_theo_tenmien(this.axios, soHD)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          response.data.data.forEach(element => {
            element.ngay_yc = moment(element.ngay_yc).format('DD/MM/YYYY')
          })
          this.dataSourcesHD = response.data.data
        } else {
          this.dataSourcesHD = []
        }
      } catch (e) {
        this.$toast.error('Đã xảy ra lỗi')
      } finally {
        this.loading(false)
      }
    },
    async hopdong_theo_so_pl (soPL) {
      this.dataSourcesHD = []
      try {
        this.loading(true)
        let response = await SearchContractAPI.hopdong_theo_so_pl(this.axios, soPL)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          response.data.data.forEach(element => {
            element.ngay_yc = moment(element.ngay_yc).format('DD/MM/YYYY')
          })
          this.dataSourcesHD = response.data.data
        } else {
          this.dataSourcesHD = []
        }
      } catch (e) {
        this.$toast.error('Đã xảy ra lỗi')
      } finally {
        this.loading(false)
      }
    },
    async hopdong_khac (data) {
      this.dataSourcesHD = []
      try {
        this.loading(true)
        let response = await SearchContractAPI.hopdong_khac(this.axios, data)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          response.data.data.forEach(element => {
            element.ngay_yc = moment(element.ngay_yc).format('DD/MM/YYYY')
          })
          this.dataSourcesHD = response.data.data
        } else {
          this.dataSourcesHD = []
        }
      } catch (e) {
        this.$toast.error('Đã xảy ra lỗi')
      } finally {
        this.loading(false)
      }
    },
    async ds_hopdong_tt_theo_hdkh_id (hdkh_id) {
      try {
        this.loading(true)
        this.dataSourcesTT = []
        let response = await SearchContractAPI.ds_hopdong_tt_theo_hdkh_id(this.axios, hdkh_id)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          this.dataSourcesTT = response.data.data
        } else {
          this.dataSourcesTT = []
        }
      } catch (e) {
        this.$toast.error('Không load được thông tin thanh toán')
      } finally {
        this.loading(false)
      }
    },
    async ds_hopdong_tb_theo_hdkh_id (hdkh_id) {
      try {
        this.loading(true)
        this.dataSourcesTB = []
        let response = await SearchContractAPI.ds_hopdong_tb_theo_hdkh_id(this.axios, hdkh_id)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          this.dataSourcesTB = response.data.data
        } else {
          this.dataSourcesTB = []
        }
      } catch (e) {
        this.$toast.error('Không load được thông tin thuê bao')
      } finally {
        this.loading(false)
      }
    },
    showModal () {
      this.$refs['popupSearchContract'].show()
    },
    hideModal () {
      this.$refs['popupSearchContract'].hide()
    },
    handleShowModal () {
      this.expanded = {
        hop_dong: true,
        thanhtoan: true,
        thuebao: true
      }
      this.dataSourcesTT = []
      this.dataSourcesTB = []
      this.dataSourcesHD = []
      this.actions[this.actions.findIndex(x => x.id == 'chapnhan')].active = false
      // time out
      setTimeout(() => {
        this.khoiTaoDuLieu()
      }, 500)
    },
    async khoiTaoDuLieu () {
      this.$refs.thongtintimkiem.clearDulieu()
      await this.$refs.thongtintimkiem.initDulieu()
      this.actions[this.actions.findIndex(x => x.id == 'chapnhan')].active = false
      // truyền data thông tin mặc định cho tiêu chí tìm kiếm
      // mặc định loại hợp đồng, dịch vụ, ngày YC, trạng thái HĐ/PL và mã GD
      let object = {
        loai_hd_id: this.loai_hd_id,
        dichvu_id: this.dichvu_id,
        ngay_yc: this.ngay_yc,
        ngay_kt: this.ngay_kt,
        tieuchi_id: this.tieuchi_id,
        thongtin_tracuu: this.thongtin_tracuu,
        trangthai_hd: this.trangthai_hd
      }
      this.$refs.thongtintimkiem.initParamSearch(object)

      var tt = this.$refs.thongtintimkiem.layThongTinTimKiem()
      if (tt.id == 'so_ao') {
        if (this.donvi_dl_id != -1) {
          tt.data.donviDLId = this.donvi_dl_id
        }
        if (this.donvi_id != -1) {
          tt.data.donviId = this.donvi_id
        }
        if (this.nhanvien_id != -1) {
          tt.data.nhanvienId = this.nhanvien_id
        }
        await this.hopdong_theo_ma_lt(tt.data)
      } else if (tt.id == 'site_id') {
        await this.hopdong_theo_site_id(tt.data)
      } else if (tt.id == 'so_hd_khdn') {
        await this.hopdong_theo_so_hd(tt.data)
      } else if (tt.id == 'so_pl_khdn') {
        await this.hopdong_theo_so_pl(tt.data)
      } else if (tt.id == 'ten_mien') {
        await this.hopdong_theo_tenmien(tt.data)
      } else {
        if (this.donvi_dl_id != -1) {
          tt.data.donviDLId = this.donvi_dl_id
        }
        if (this.donvi_id != -1) {
          tt.data.donviId = this.donvi_id
        }
        if (this.nhanvien_id != -1) {
          tt.data.nhanvienId = this.nhanvien_id
        }
        await this.hopdong_khac(tt.data)
      }
    }
  },
  watch: {
    dataSourcesHD (val) {
      if (val.length > 0) {
        this.actions[this.actions.findIndex(x => x.id == 'chapnhan')].active = true
      } else {
        this.actions[this.actions.findIndex(x => x.id == 'chapnhan')].active = false
      }
    }
  },
  mounted () {

  }

}
</script>

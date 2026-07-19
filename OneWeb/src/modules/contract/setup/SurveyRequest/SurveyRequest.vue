<template>
  <div class="main-wrapper">
    <breadcrumb :header="header" />
    <ActionTop :actions="actions" @onActionClick="onActionClick" />
    <div class="page-content">
      <div class="box-form">
        <div class="row">
          <div class="col-sm-6 col-12"></div>
          <div class="col-sm-3 col-12">
            <div class="info-row marb0">
              <div class="key nowrap">
                <div class="check-action fw6" title="Tích chọn để tìm kiếm cộngt tác viên">
                  <input type="checkbox" v-model="checkCongTacVien" @change="changeCheckCTV" class="check" />
                  <span class="name bold">Cộng tác viên</span>
                </div>
              </div>
              <div class="value">
                <input type="text" v-model="ctv.ten_ctv" :disabled="!checkCongTacVien" class="form-control bold" />
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row marb0">
              <div class="key nowrap">
                <div class="check-action fw6" title="Tích chọn để tìm kiếm người giới thiệu hợp đồng">
                  <input type="checkbox" v-model="checkNguoiGT" @change="changeCheckNguoiGT" class="check" />
                  <span class="name bold">Người giới thiệu</span>
                </div>
              </div>
              <div class="value">
                <input type="text" v-model="nguoi_gt.ten_nguoi_gt" :disabled="!checkNguoiGT" class="form-control bold" />
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <ThongTinKhachHang ref="thongtinkhachhang" :hdtb="hdtb" @searchMaGD="searchMaGD" @searchMaKH="searchMaKH" @callbackChonDiChiKH="callbackChonDiChiKH" @onEnterTenKH="onEnterTenKH" @initDiaChiKH="initDiaChiKH" @onChangeNgaySinhKH="onChangeNgaySinhKH" @onBlurSoGT="onBlurSoGT" />
        <ThongTinThueBao ref="thongtinthuebao" :hdtb="hdtb" @successUpdateThayDoiKhuVucQuanLy="successUpdateThayDoiKhuVucQuanLy" :LOC_NVDIADANH_NVPX="LOC_NVDIADANH_NVPX" />
      </div>
      <div class="box-form">
        <div class="legend-title">Danh sách phiếu yêu cầu khảo sát</div>
        <!-- <KTableV3 :columns="columns"
                    :dataSources="dataSources"
                    id="hdtb_id"
                    :rowSelectedId="rowSelectedId"
                    @onSelectedRow="onSelectedRow"
                    :allowFilter="true"/> -->
        <KDataGrid ref="gridDs" :columns="columns" :dataSource="dataSources" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :showColumnCheckbox="false" :enabledSelectFirstRow="true" @selectedItemsChanged="gridPhieuYCChanged" @onRowSelected="onSelectedRow" />
      </div>
    </div>
    <Footer />
    <!-- Modal -->
    <ThongTinHenKhachHangModal ref="thongTinHenKhachHangModal" :hdkh_id="hdkh_id" />
    <ChonCongTacVienModal ref="chonCongTacVienModal" @accept="acceptCTV" />
    <ChonNguoiGTModal ref="chonNguoiGTModal" @accept="acceptNguoiGT" />
    <frmCapnhatCNTT
      ref="frmCapnhatCNTT"
      modal-id="id-modal-capnhat-cntt"
      :tinh-id="gridDanhSach && gridDanhSach.value ? gridDanhSach.value.tinh_id : 0"
      :hdtb-id="gridDanhSach && gridDanhSach.value ? gridDanhSach.value.hdtb_id : 0"
      :huong-giao-id="huonggiao_id"
      :parent="this"
      :huongGiaoId="11111"
      :hdtbId="this.hdtb_id"
      :tinhId="98"
      :loaitb_id="this.loaitb_id"
      @xacnhan="frmCapnhatCNTT_XacNhan"
      @loadGrid="onActionClick('layds')"
    />
    <PopupCapNhatDV ref='popupCapNhatDV' />
  <ejs-dialog
    :isModal="true"
    :enableResize="true"
    :resizeHandles="['All']"
    :visible="false"
    ref="GiaoPhieuView"
    :header='"Giao phiếu điều hành thi công"'
    showCloseIcon="true"
    target=".main-wrapper"
    width="90%"
    allowDragging="true"
    :animationSettings="giaoPhieuViewDialog.animationSettings"
    @close="GiaoPhieuView_OnClose"
    >
    <div>
        <GiaoPhieuView v-if="giaoPhieuViewDialog.isVisiable" type="modal" v-bind="giaoPhieuViewDialog.input" />
    </div>
  </ejs-dialog>

  </div>
</template>
<script>
//frmTiepNhanYCKhaoSat.cs(lấy file code mới) bỏ LoaiHopDong.TUVAN_DV
import Footer from '@/components/FooterStyle.vue'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import ThongTinKhachHang from './ThongTinKhachHang.vue'
import ThongTinThueBao from './ThongTinThueBao.vue'
import { LoaiHopDong, KieuLapDat } from './ThamSo'
import SurveyRequestAPI from './SurveyRequestAPI'
import ThongTinHenKhachHangModal from './popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue'
import ChonCongTacVienModal from './popups/ChonCongTacVien/ChonCongTacVienModal.vue'
import ChonNguoiGTModal from './popups/ChonNguoiGT/ChonNguoiGTModal.vue'
import moment from 'moment'
import { createObjectHDKH, createObjectHDTB } from './Utils'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import frmCapnhatCNTT from '@/modules/contract/setup/SurveyRequest/popups/CTTSCN/index.vue'
import PopupCapNhatDV from '@/modules/contract/setup/Liquidate/components/popupCapNhatDV.vue'
import GiaoPhieuView from '@/modules/contract/setup/HandoverTicket/giao_phieu_view.vue'
import { log } from 'pdfmake/build/pdfmake'

//KRequiredMarker
export default {
  name: 'SurveyRequest',
  components: {
    Footer,
    breadcrumb,
    KTableV3,
    ActionTop,
    ThongTinKhachHang,
    ThongTinThueBao,
    ThongTinHenKhachHangModal,
    ChonCongTacVienModal,
    ChonNguoiGTModal,
    KDataGrid,
    frmCapnhatCNTT,
    PopupCapNhatDV,
    GiaoPhieuView
  },
  data() {
    return {
      header: {
        title: 'Tiếp nhận yêu cầu khảo sát lắp đặt mới',
        list: [
          {
            name: 'Lập hợp đồng',
            link: { name: 'Ui.cards' },
            active: false
          },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' },
            active: true
          }
        ]
      },
      actions: [
        {
          id: 'layds',
          name: 'Lấy DS',
          active: true,
          icon_class: 'one-file-attach1',
          tooltip: 'Lấy danh sách mới'
        },
        {
          id: 'nhapmoi',
          name: 'Nhập mới',
          active: true,
          icon_class: 'one-file-plus',
          tooltip: 'Nhập mới'
        },
        {
          id: 'ghilai',
          name: 'Ghi lại',
          active: true,
          icon_class: 'one-save',
          tooltip: 'Ghi lại'
        },
        {
          id: 'huy',
          name: 'Huỷ',
          active: true,
          icon_class: 'nc-icon-glyph ui-1_circle-remove',
          tooltip: 'Huỷ'
        },
        {
          id: 'xoa_hd',
          name: 'Xoá HĐ',
          active: true,
          icon_class: 'one-trash',
          tooltip: 'Xóa hợp đồng'
        },
        {
          id: 'them_yc',
          name: 'Thêm YC',
          active: true,
          icon_class: 'one-mobile-plus',
          tooltip: 'Thêm yêu cầu'
        },
        {
          id: 'xoa_yc',
          name: 'Xoá YC',
          active: false,
          icon_class: 'one-phone-remove',
          tooltip: 'Xóa yêu cầu'
        },
        {
          id: 'hen_ld',
          name: 'Hẹn LĐ',
          active: true,
          icon_class: 'one-calendar-check',
          tooltip: 'Hẹn lắp đặt'
        },
        {
          id: 'hoanthanh',
          name: 'Hoàn thành',
          active: false,
          icon_class: 'one-file-check',
          tooltip: 'Hoàn thành'
        },
        {
          id: 'tsbtnTest_Click',
          name: 'TT HĐ',
          active: true,
          icon_class: 'one-file-check',
          tooltip: 'Nhập TT hợp đồng'
        },
        {
          id: 'tsbtngiaophieu_Click',
          name: 'Giao phiếu',
          active: true,
          icon_class: 'one-file-check',
          tooltip: 'giao phiếu'
        }
      ],
      columns: [
        {
          fieldName: 'ma_gd',
          headerText: 'Mã giao dịch',
          allowFiltering: true
        },
        {
          fieldName: 'ngay_yc',
          headerText: 'Ngày yêu cầu',
          allowFiltering: true
        },
        {
          fieldName: 'ma_tb',
          headerText: 'Số máy/Account',
          allowFiltering: true
        },
        {
          fieldName: 'ten_tb',
          headerText: 'Tên thuê bao',
          allowFiltering: true
        },
        {
          fieldName: 'diachi_ld',
          headerText: 'Địa chỉ lắp đặt',
          allowFiltering: true
        },
        {
          fieldName: 'loaihinh_tb',
          headerText: 'Loại hình',
          allowFiltering: true
        },
        {
          fieldName: 'ten_kieuld',
          headerText: 'Kiểu yêu cầu',
          allowFiltering: true
        },
        {
          fieldName: 'soluong',
          headerText: 'Số lượng',
          allowFiltering: true
        }
      ],
      dataSources: [],
      checkCongTacVien: false,
      checkNguoiGT: false,
      loaihd_id: 33,
      rowSelected: null,
      //1: Type MaGD enter, 2: Type MaKH enter
      action_type: 1,
      itemMaKHSelected: null,
      LOC_NVDIADANH_NVPX: false,
      ctv: {
        ctv_id: null,
        ten_ctv: '',
        ma_ctv: '',
        loainv_id: -1,
        donvi_id: -1
      },
      nguoi_gt: {
        nguoi_gt_id: null,
        ten_nguoi_gt: '',
        ma_nguoi_gt: '',
        loainv_id: -1,
        donvi_id: -1
      },
      thuebao_id: 0,
      khachhang_id: 0,
      huonggiaotn_id: 0,
      quytrinh_id: 0,
      loaidv_id: 5, //LOAI_DV.TRAM_VT
      hdkh_id: 0,
      hdtb_id: 0,
      rowSelectedId: -1,
      sinhma_tb_ks: false,

      dsHDKH: [],
      dsHDTB: [],
      dsHDTBDV: [],
      dsDiaChi: [],
      dsDiaChiHDKH: [],
      dsDiaChiHDTB: [],
      dsHDTB_KV: [],
      loaitb_id: 0,
      giaoPhieuViewDialog: {
        animationSettings: { effect: 'None' },
        isVisiable: false,
        input: {
          vhuonggiao_id: null,
          vloai_hd: null,
          vdichvuvt_id: null,
          vma_gd: null,
          vquytrinh: null,
          vtrangthaiphieu: 1
        }
      },
    }
  },
  computed: {
    hdtb() {
      return this.rowSelected != null ? this.rowSelected : {}
    }
  },
  methods: {
    onActionClick(action) {
      console.log(123)
      if (action.id == 'layds') {
        this.clear()
        this.hienthi_danhsach_hdtb('')
      } else if (action.id == 'nhapmoi') {
        this.setActiveActions(1)
      } else if (action.id == 'ghilai') {
        this.ghiDuLieu()
      } else if (action.id == 'huy') {
        if (this.rowSelected != null && this.dataSources.length > 0) {
          this.onSelectedRow(this.rowSelected)
        } else {
          this.setActiveActions(0)
        }
        //this.setActiveActions(0)
        //this.setActiveActions(2)
      } else if (action.id == 'xoa_hd') {
        //   if (MessageBox.Show("Bạn thật sự muốn xóa thông tin không ?", "Thông báo", MessageBoxButtons.YesNo, MessageBoxIcon.Information) != DialogResult.Yes) return;
        // new LapHopDongFacade2().XOA_HDKH_V2(hdkh_id, loaihd_id);
        if (this.rowSelected == null || this.dataSources.length == 0) {
          return
        }
        this.$confirm('Bạn thật sự muốn xóa thông tin không ?', 'Thông báo', {
          confirmButtonText: 'Đồng ý',
          cancelButtonText: 'Huỷ'
        }).then(() => {
          //handle
          this.xoa_hdkh_v2(this.rowSelected.hdkh_id, this.loaihd_id)
          //this.xoa_hdkh_v2(1530683, 30)
        })
      } else if (action.id == 'them_yc') {
        this.themYC()
      } else if (action.id == 'xoa_yc') {
        this.xoaYC()
      } else if (action.id == 'hen_ld') {
        this.$refs.thongTinHenKhachHangModal.showModal()
      } else if (action.id == 'hoanthanh') {
        this.onClickHoanThanh()
      } else if (action.id == 'tsbtnTest_Click') {
        const thongtin_tb = this.$refs.thongtinthuebao.getInfoFormThueBao()
        this.loaitb_id = thongtin_tb.loaihinh_selected != null ? thongtin_tb.loaihinh_selected : 0
        console.log(this.loaitb_id)
        this.$refs.frmCapnhatCNTT.showModal()
      }
      else if (action.id == 'tsbtngiaophieu_Click') {
        this.tsbtngiaophieu_Click()
      }
    },
    setActiveActions(kieu) {
      this.actions.forEach((item) => {
        if (item.id != 'hen_ld' && item.id != 'hoanthanh' && item.id != 'layds') {
          item.active = false
        }
      })
      if (kieu == -1) {
        //bắt đầu
        this.actions[this.actions.findIndex((x) => x.id == 'ghilai')].active = true
        this.actions[this.actions.findIndex((x) => x.id == 'huy')].active = true
        this.actions[this.actions.findIndex((x) => x.id == 'tsbtnTest_Click')].active = true
        this.actions[this.actions.findIndex((x) => x.id == 'tsbtngiaophieu_Click')].active = true
      } else if (kieu == 0) {
        //bắt đầu
        this.$refs.thongtinkhachhang.focusMaGD()
        this.actions[this.actions.findIndex((x) => x.id == 'nhapmoi')].active = true
        this.actions[this.actions.findIndex((x) => x.id == 'ghilai')].active = false
        this.actions[this.actions.findIndex((x) => x.id == 'tsbtnTest_Click')].active = true
        this.actions[this.actions.findIndex((x) => x.id == 'tsbtngiaophieu_Click')].active = true
      } else if (kieu == 1) {
        //thêm mới
        this.$refs.thongtinkhachhang.focusTenKH()
        this.actions[this.actions.findIndex((x) => x.id == 'ghilai')].active = true
        this.actions[this.actions.findIndex((x) => x.id == 'huy')].active = true
        this.actions[this.actions.findIndex((x) => x.id == 'tsbtnTest_Click')].active = true
        this.actions[this.actions.findIndex((x) => x.id == 'tsbtngiaophieu_Click')].active = true
        this.clear()
      } else if (kieu == 2) {
        //Huỷ
        this.$refs.thongtinkhachhang.focusMaGD()
        this.actions[this.actions.findIndex((x) => x.id == 'nhapmoi')].active = true
        this.actions[this.actions.findIndex((x) => x.id == 'xoa_hd')].active = true
        this.clear()
      } else if (kieu == 3) {
        //edit
        this.$refs.thongtinkhachhang.focusMaGD()
        this.actions[this.actions.findIndex((x) => x.id == 'nhapmoi')].active = true
        this.actions[this.actions.findIndex((x) => x.id == 'ghilai')].active = true
        this.actions[this.actions.findIndex((x) => x.id == 'tsbtnTest_Click')].active = true
        this.actions[this.actions.findIndex((x) => x.id == 'tsbtngiaophieu_Click')].active = true
        this.actions[this.actions.findIndex((x) => x.id == 'huy')].active = true
        this.actions[this.actions.findIndex((x) => x.id == 'xoa_hd')].active = true

        this.actions[this.actions.findIndex((x) => x.id == 'hoanthanh')].active = true
        this.actions[this.actions.findIndex((x) => x.id == 'them_yc')].active = true
        this.actions[this.actions.findIndex((x) => x.id == 'xoa_yc')].active = true
      }
    },
    clear() {
      this.action_type = 1
      this.thuebao_id = 0
      this.khachhang_id = 0
      this.hdkh_id = 0
      this.hdtb_id = 0
      this.itemMaKHSelected = null

      //
      this.dsHDKH = []
      this.dsHDTB = []
      this.dsHDTBDV = []
      this.dsDiaChi = []
      this.dsDiaChiHDKH = []
      this.dsDiaChiHDTB = []
      this.dsHDTB_KV = []

      //clear dữ liệu form
      this.$refs.thongtinkhachhang.clearDuLieu()
      this.$refs.thongtinthuebao.clearDuLieu()

      this.ctv = {
        ctv_id: null,
        ten_ctv: '',
        ma_ctv: '',
        loainv_id: -1,
        donvi_id: -1
      }
      this.nguoi_gt = {
        nguoi_gt_id: null,
        ten_nguoi_gt: '',
        ma_nguoi_gt: '',
        loainv_id: -1,
        donvi_id: -1
      }

      this.checkNguoiGT = false
      this.checkCongTacVien = false

      this.actions[this.actions.findIndex((x) => x.id == 'hoanthanh')].active = false
      this.actions[this.actions.findIndex((x) => x.id == 'ghilai')].active = true
      this.actions[this.actions.findIndex((x) => x.id == 'xoa_yc')].active = false
    },
    async searchMaGD(ma_gd) {
      if (ma_gd != '') {
        //this.hienthi_danhsach_hdtb(ma_gd)
        //Sài tạm do db store bỏ cho truyền ma_gd
        let nhanvien_id = this.$root.token.getNhanVienID()
        let data = await this.sp_lay_ds_hdtb_tiepnhan_ks(this.loaihd_id, nhanvien_id, ma_gd)
        if (data.length > 0) {
          let result = data.filter((x) => x.ma_gd == ma_gd)
          if (result.length > 0) {
            //focus row đầu tiên
            // this.rowSelectedId=this.dataSources[0].hdtb_id
            // this.onSelectedRow(this.dataSources[0])
            this.actions[this.actions.findIndex((x) => x.id == 'hoanthanh')].active = true
            //active xoa_yc khi row lớn hơn 1, ==1 xoá hd
            if (result.length > 1) {
              this.actions[this.actions.findIndex((x) => x.id == 'xoa_yc')].active = true
            } else {
              this.actions[this.actions.findIndex((x) => x.id == 'xoa_yc')].active = false
            }
            this.dataSources = [].concat(result)
          } else {
            this.$toast.error('Không tìm thấy thông tin yêu cầu')
          }
        } else {
          this.$toast.error('Không tìm thấy thông tin yêu cầu')
        }
      } else {
        this.setActiveActions(0)
      }
    },
    searchMaKH(ma_kh) {
      if (ma_kh != '') {
        this.lay_thongtin_khachhang_nhanquyen(ma_kh)
      }
    },
    onEnterTenKH(tenKh) {
      this.$refs.thongtinthuebao.input_ten_tb = tenKh
    },
    onChangeNgaySinhKH(value) {
      this.$refs.thongtinthuebao.ngay_sinh = value
    },
    async onBlurSoGT(so_gt) {
      await this.KiemTraSoGT(so_gt)
    },
    //Sau này bổ sung, đang thiếu hàm db
    async KiemTraSoGT(so_gt) {
      const regex = RegExp('^[a-zA-Z0-9]*$')
      if (!regex.test(so_gt)) {
        this.$toast.error('Số giấy tờ không hợp lệ.Số giấy tờ gồm các ký tự từ 0->9a->z. Hãy kiểm tra lại')
        this.$refs.thongtinkhachhang.focusSoGT()
        return
      }
      let ds_maKH = await this.sp_lay_ds_makh_theo_sogt(so_gt, this.hdkh_id)
      if (ds_maKH.length == 1) {
        let makh = this.$refs.thongtinkhachhang.input_ma_kh.trim()
        if (makh != ds_maKH[0].ma_kh) {
          let result = await this.confirm('Số giấy tờ ' + so_gt + ' đang được sử dụng bởi mã khách hàng: ' + ds_maKH[0].ma_kh + '\nBạn có muốn sử dụng thông tin khách hàng cũ không?', 'Thông báo')
          if (result == 0) return

          //Handle
          //Lấy thông tin cũ của khách hàng khi chưa lập hợp đồng -
          if (this.hdkh_id == 0) {
            this.$refs.thongtinkhachhang.input_ma_kh = ds_maKH[0].ma_kh
            //lay_thongtin_khachhang_nhanquyen_2
            let result = await this.lay_thongtin_khachhang_nhanquyen_2(ds_maKH[0].ma_kh)
            if (result.length > 0) {
              await this.hienthi_tt_danhba(result)
            } else {
              this.$toast.error('Không tìm thấy thông tin khách hàng')
            }
          } else {
            this.$toast.error('Hợp đồng/phụ lục đã được lập, bạn không được sử dụng số giấy tờ của khách hàng khác!')
          }
        }
      } else if (ds_maKH.length > 1) {
        let str = ds_maKH.map((x) => x.ma_kh).join(',')
        this.$toast.error('Số giấy tờ ' + so_gt + ' đang được sử dụng bởi các mã khách hàng: ' + str + ' .')
      }
    },
    async confirm(message, title) {
      try {
        let result = await this.$confirm(message, title, {
          confirmButtonText: 'Đồng ý',
          cancelButtonText: 'Hủy'
        })
        return 1
      } catch (e) {
        return 0
      }
    },
    gridPhieuYCChanged(data) {
      // if(this.dataSources.length<=0||data.length<=0){
      //     this.clear()
      //     this.rowSelected=null
      //     return
      // }
      // console.log('gridPhieuYCChanged', data[0])
      // this.onSelectedRow(data[0])
    },
    onSelectedRow(item) {
      if (!item) {
        this.clear()
        this.rowSelected = null
        return
      }
      console.log('onSelectedRow', item)
      this.clear()
      this.action_type = 1
      this.rowSelected = item
      this.thuebao_id = this.rowSelected.thuebao_id != null ? this.rowSelected.thuebao_id : 0

      this.hdkh_id = this.rowSelected.hdkh_id != null ? this.rowSelected.hdkh_id : 0
      this.hdtb_id = this.rowSelected.hdtb_id != null ? this.rowSelected.hdtb_id : 0

      this.setActiveActions(3)

      //init dữ liệu
      this.ctv.ctv_id = this.rowSelected.ctv_id
      this.nguoi_gt.nguoi_gt_id = this.rowSelected.nhanviengt_id

      //init người GT, CTV, form Thông tin khách hàng/Thông tin thuê bao
      setTimeout(async () => {
        await this.intNguoiGT_CTV()
        await this.$refs.thongtinkhachhang.initFocusRow()
        await this.$refs.thongtinthuebao.initFocusRow()
      }, 200)
    },
    changeCheckCTV() {
      if (this.checkCongTacVien) {
        this.$refs.chonCongTacVienModal.showModal()
      }
    },
    changeCheckNguoiGT() {
      if (this.checkNguoiGT) {
        this.$refs.chonNguoiGTModal.showModal()
      }
    },
    acceptCTV(data) {
      Object.assign(this.ctv, data)
      this.ctv.ten_ctv = this.ctv.ma_ctv + ' - ' + this.ctv.ten_ctv
    },
    acceptNguoiGT(data) {
      Object.assign(this.nguoi_gt, data)
      this.nguoi_gt.ten_nguoi_gt = this.nguoi_gt.ma_nguoi_gt + ' - ' + this.nguoi_gt.ten_nguoi_gt
    },
    successUpdateThayDoiKhuVucQuanLy() {
      this.hienthi_danhsach_hdtb('')
    },
    async intNguoiGT_CTV() {
      try {
        this.loading(true)
        //người giới thiệu
        if (this.rowSelected.nhanviengt_id != null) {
          let responseNguoiGT = await SurveyRequestAPI.fn_lay_thongtin_nhanvien(this.axios, this.rowSelected.nhanviengt_id)
          if (responseNguoiGT && responseNguoiGT.data && responseNguoiGT.data.error_code && responseNguoiGT.data.error_code == 'BSS-00000000' && responseNguoiGT.data.data) {
            this.nguoi_gt.ten_nguoi_gt = responseNguoiGT.data.data
            this.checkNguoiGT = true
          } else {
            this.nguoi_gt.ten_nguoi_gt = ''
            this.checkNguoiGT = false
          }
        } else {
          this.nguoi_gt.ten_nguoi_gt = ''
          this.checkNguoiGT = false
        }
        //Cộng tác viên
        if (this.rowSelected.ctv_id != null) {
          let responseCTV = await SurveyRequestAPI.fn_lay_thongtin_nhanvien(this.axios, this.rowSelected.ctv_id)
          if (responseCTV && responseCTV.data && responseCTV.data.error_code && responseCTV.data.error_code == 'BSS-00000000' && responseCTV.data.data) {
            this.ctv.ten_ctv = responseCTV.data.data
            this.checkCongTacVien = true
          } else {
            this.ctv.ten_ctv = ''
            this.checkCongTacVien = false
          }
        } else {
          this.ctv.ten_ctv = ''
          this.checkCongTacVien = false
        }
      } catch (e) {
        if (e.data && e.data.message) {
          this.$toast.error(e.data.message)
        } else if (e.response && e.response.data && e.response.data.message) {
          this.$toast.error(e.response.data.message)
        } else {
          this.$toast.error('Không load được thông tin người GT, CTV')
        }
      } finally {
        this.loading(false)
      }
    },
    xoaYC() {
      if (this.rowSelected == null || this.dataSources.length == 0) {
        return
      }
      if (this.dataSources.length == 1) {
        this.$toast.error('Hợp đồng này hiện đang có 1 thuê bao, bạn hãy thực hiện chức năng xóa hợp đồng !')
        return
      }
      this.$confirm('Bạn thật sự muốn xóa thuê bao không ?', 'Thông báo', {
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Huỷ'
      }).then(() => {
        //handle
        this.xoa_hdtb(this.rowSelected.hdtb_id)
        //this.xoa_hdtb(1461637)
      })
    },
    async onClickHoanThanh() {
      let thongtin_kh = this.$refs.thongtinkhachhang.getInfoFormKhachHang()
      let thongtin_tb = this.$refs.thongtinthuebao.getInfoFormThueBao()
      if (!this.actions[this.actions.findIndex((x) => x.id == 'nhapmoi')].active) {
        this.$toast.error('Không thể hoàn thành khi đang ở chế độ tạo mới hợp đồng')
        return
      }

      if (thongtin_kh.input_ma_gd.trim() == '') {
        this.$toast.error('Không có thông tin mã giao dịch')
        return
      }
      //Điều chỉnh theo code mới
      if (this.loaihd_id != LoaiHopDong.KHAOSATDATMOI) {
        if (thongtin_tb.ds_nhanvien.length == 0) {
          this.$toast.error('Hãy chọn khu vực có nhân viên kinh doanh địa bàn')
          return
        }

        let thamso = (await this.getThamSo('KS_LAPHD_CHONNV', [])).length > 0
        if (thamso) {
          if (this.$refs.thongtinthuebao.nhanvien_selected == null || this.$refs.thongtinthuebao.nhanvien_selected.length == 0) {
            this.$toast.error('Bạn hãy chọn nhân viên kinh doanh địa bàn!')
            return
          }
        }
      }

      //Bỏ qua if ONE
      this.$confirm('Bạn có chắc chắn hoàn thành không ?', 'Thông báo', {
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Huỷ'
      })
        .then(() => {
          //handle
          this.thucthi_hoanthanh()
        })
        .catch(() => {})
    },
    //hard giá trị
    async thucthi_hoanthanh() {
      //thực thi
      let thongtin_tb = this.$refs.thongtinthuebao.getInfoFormThueBao()
      let thongtin_kh = this.$refs.thongtinkhachhang.getInfoFormKhachHang()

      //cập nhật trạng thái hợp đồng
      //hard hdtb_id
      let ketqua_capnhat = await this.capnhat_tthd(this.hdtb_id, 4)
      if (ketqua_capnhat) {
        this.$toast.success('Cập nhật trạng thái hợp đồng thành công')
        let resGiaoPhieu = await this.sp_giaophieu_khaosat({
          //hdkh_id:5686780,
          hdkh_id: this.hdkh_id,
          nhanvien_id: this.$root.token.getNhanVienID(),
          may_cn: 'localhost',
          ip_cn: '127.0.0.1'
        })
        if (resGiaoPhieu) {
          this.$toast.success('Giao phiếu khảo sát thành công')

          if (this.loaihd_id == LoaiHopDong.TIEPNHANHD) {
            let noidung = 'XLTTPH: Yeu cau ky HD:' + thongtin_kh.input_ma_gd + ';' + thongtin_tb.ten_loaihinh + ';dtlh:' + thongtin_kh.input_so_dt + ';dc:' + thongtin_tb.input_diachi_ld + ';nd:' + thongtin_tb.input_ghichu
            let ds_nhanvien = thongtin_tb.nhanvien_selected ? thongtin_tb.nhanvien_selected : []
            //Send sms nhân viên
            for (let i = 0; i < ds_nhanvien.length; i++) {
              let item = ds_nhanvien[i]
              if (item.so_dt != null && item.so_dt != '') {
                let result = await this.sendSMS(item.so_dt, noidung)
              }
            }

            //send sms ctv
            if (this.checkCongTacVien && this.ctv.ctv_id != null && this.ctv.ctv_id != 0) {
              let nhanvien = await this.lay_nhanvien_theo_id(this.ctv.ctv_id)
              console.log('nhanvien', nhanvien)
              if (nhanvien.length > 0) {
                let so_dt = nhanvien[0].so_dt
                let content = 'XLTTPH: Yeu cau ky HD: Tên thuê bao ' + thongtin_tb.input_ten_tb + '; Mã GD:' + thongtin_kh.input_ma_gd + ';Loại hình:' + thongtin_tb.ten_loaihinh + ';dtlh:' + thongtin_kh.input_so_dt + ';dc:' + thongtin_tb.input_diachi_ld + ';nd:' + thongtin_tb.input_ghichu
                console.log('so_dt', so_dt)
                console.log('content', content)
                if (so_dt != null && so_dt != '') {
                  let resultSendSMS = await this.sendSMS(so_dt, content)
                  if (resultSendSMS) {
                    this.$toast.success('Gửi tin nhắn đến cộng tác viên thành công')
                  } else {
                    this.$toast.error('Đã xảy ra lỗi gửi tin nhắn ctv không thành công')
                  }
                }
              }
            }

            //Giao việc nhân viên
            if (ds_nhanvien.length > 0) {
              let giaophieu_nv = ds_nhanvien.map((x) => {
                return { NHANVIEN_ID: x.nhanvien_id }
              })

              let data = {
                hdtb_id: this.hdtb_id,
                so_dt: ds_nhanvien[0].so_dt,
                json_giaophieu_nv: JSON.stringify(giaophieu_nv)
              }
              let giaoviec = await this.giaoviec_nv_khaosat(data)
              if (giaoviec) {
                this.$toast.success('Giao việc nhân viên khoán địa bàn đi khảo sát thành công')
              } else {
                this.$toast.error('Giao việc nhân viên khoán địa bàn đi khảo sát không thành công')
              }
            }
          }

          if (this.loaihd_id == LoaiHopDong.KHAOSATDATMOI) {
            //Send sms cho ctv
            if (this.checkCongTacVien && this.ctv.ctv_id != null && this.ctv.ctv_id != 0) {
              let nhanvien = await this.lay_nhanvien_theo_id(this.ctv.ctv_id)
              console.log('nhanvien', nhanvien)
              if (nhanvien.length > 0) {
                let so_dt = nhanvien[0].so_dt
                let content = 'XLTTPH: Yeu cau ky HD: Tên thuê bao ' + thongtin_tb.input_ten_tb + '; Mã GD:' + thongtin_kh.input_ma_gd + ';Loại hình:' + thongtin_tb.ten_loaihinh + ';dtlh:' + thongtin_kh.input_so_dt + ';dc:' + thongtin_tb.input_diachi_ld + ';nd:' + thongtin_tb.input_ghichu
                console.log('so_dt', so_dt)
                console.log('content', content)
                if (so_dt != null && so_dt != '') {
                  let resultSendSMS = await this.sendSMS(so_dt, content)
                  if (resultSendSMS) {
                    this.$toast.success('Gửi tin nhắn đến cộng tác viên thành công')
                  } else {
                    this.$toast.error('Đã xảy ra lỗi gửi tin nhắn ctv không thành công')
                  }
                }
              }
            }
          }
        } else {
          this.$toast.error('Đã xảy ra lỗi giao phiếu khảo sát không thành công')
          let back = await this.capnhat_tthd(this.hdtb_id, 1)
        }
        //refresh dữ liệu
        this.clear()
        await this.hienthi_danhsach_hdtb('')
      } else {
        this.$toast.error('Đã xảy ra lỗi cập nhật trạng thái hợp đồng không thành công')
      }
    },
    btnCapNhatDVClick () {
      let dulieu = {
        hdtb_id: this.hdtb_id
      };
      this.$refs.popupCapNhatDV.openDialog(dulieu)
    },
    async GiaoPhieuView_OnClose() {
      this.giaoPhieuViewDialog.isVisiable = false
    },
    async GiaoPhieuView_OnClick() {
      this.giaoPhieuViewDialog.isVisiable = true
      this.$refs.GiaoPhieuView.show()
    },

    async tsbtngiaophieu_Click() {
      try {
        
        try {
          const KT_DONVI_QUYTRINH_CNTT = (await this.axios.get('/web-hopdong/vbn/KT_DONVI_QUYTRINH_CNTT?vhdtb_id=' + this.hdtb_id)).data.data
          if (KT_DONVI_QUYTRINH_CNTT != 'ok') {
            this.btnCapNhatDVClick()
            return
          }
        } catch (e) {
          console.error(e)
        }
        const quytrinhId = await SurveyRequestAPI.fn_tt_hd_thuebao(this.axios,this.hdtb_id, 3)
        const thongtin_kh = this.$refs.thongtinkhachhang.getInfoFormKhachHang()
        this.giaoPhieuViewDialog.input = {
          vhuonggiao_id: 21238,
          vloai_hd: 33,
          vdichvuvt_id: 15,
          vma_gd: thongtin_kh.input_ma_gd,
          vquytrinh: 39802,
          vtrangthaiphieu: 1
        }
        this.GiaoPhieuView_OnClick()
      } catch (ex) {
        console.error(ex)
        this.$toast.error('Có lỗi xảy ra ' + cmFunc.getErrorMessage(ex))
      }
    },
    async capnhat_tthd(hdtb_id, tthd_id) {
      try {
        this.loading(true)
        let response = await SurveyRequestAPI.capnhat_tthd(this.axios, hdtb_id, tthd_id)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
          return true
        } else {
          return false
        }
      } catch (e) {
        this.loading(false)
        return false
      }
    },
    //chưa check
    async sp_giaophieu_khaosat(data) {
      try {
        this.loading(true)
        let response = await SurveyRequestAPI.sp_giaophieu_khaosat(this.axios, data)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
          return true
        } else {
          return false
        }
      } catch (e) {
        this.loading(false)
        return false
      }
    },
    async giaoviec_nv_khaosat(data) {
      try {
        this.loading(true)
        let response = await SurveyRequestAPI.giaoviec_nv_khaosat(this.axios, data)
        this.loading(false)
        if (response && response.data && response.data.error && response.data.error == '200') {
          return true
        } else {
          return false
        }
      } catch (e) {
        this.loading(false)
        return false
      }
    },
    async lay_nhanvien_theo_id(nhanvien_id) {
      try {
        this.loading(true)
        let response = await SurveyRequestAPI.lay_nhanvien_theo_id(this.axios, nhanvien_id)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async sinh_matb_khaosat(kieu) {
      try {
        this.loading(true)
        let response = await SurveyRequestAPI.sinh_matb_khaosat(this.axios, kieu)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return ''
        }
      } catch (e) {
        this.loading(false)
        return ''
      }
    },
    async sp_layhuonggiao_tiepnhan(loaihd_id, kieuld_id) {
      try {
        this.loading(true)
        let response = await SurveyRequestAPI.sp_layhuonggiao_tiepnhan(this.axios, loaihd_id, kieuld_id)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async sp_lay_ds_makh_theo_sogt(so_gt, hdkh_id) {
      try {
        this.loading(true)
        let response = await SurveyRequestAPI.sp_lay_ds_makh_theo_sogt(this.axios, so_gt, hdkh_id)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async sendSMS(sdt, noidung) {
      try {
        this.loading(true)
        let response = await SurveyRequestAPI.sendSMS(this.axios, sdt, noidung)
        this.loading(false)
        if (response && response.data && response.data.errorCode == 0) {
          return true
        } else {
          return false
        }
      } catch (e) {
        this.loading(false)
        return false
      }
    },

    async ghiDuLieu() {
      //
      let is_insert = this.actions[this.actions.findIndex((x) => x.id == 'nhapmoi')].active ? false : true
      //check dữ liệu
      if (!(await this.kiemtra_dl_khachhang())) return
      if (!(await this.kiemtra_dl_thuebao())) return
      //tạo dữ liệu
      await this.TaoDuLieu_HDKH(is_insert)
      await this.TaoDuLieu_HDTB(is_insert)
      await this.TaoDuLieu_HDTBDV(is_insert)

      await this.TaoDuLieu_DiaChi(is_insert, 4)

      await this.TaoDuLieu_HDTB_KV(is_insert)

      //thực thi
      let data = {
        huonggiao_id: this.huonggiaotn_id,
        js_diachi: JSON.stringify(this.dsDiaChi),
        js_diachi_hdkh: JSON.stringify(this.dsDiaChiHDKH),
        js_diachi_hdtb: JSON.stringify(this.dsDiaChiHDTB),
        js_hd_khachhang: JSON.stringify(this.dsHDKH),
        js_hd_thuebao: JSON.stringify(this.dsHDTB),
        js_hdtb_dv: JSON.stringify(this.dsHDTBDV),
        js_hdtb_kv: JSON.stringify(this.dsHDTB_KV)
      }

      if (is_insert) {
        this.sp_themmoi_hopdong_ks(data)
      } else {
        this.sp_capnhat_hopdong_ks(data)
      }
    },
    async themYC() {
      if (!(await this.kiemtra_dl_thuebao())) return
      //Tạo dữ liệu
      await this.TaoDuLieu_HDTB(true)
      await this.TaoDuLieu_DiaChi(true, 3)

      await this.TaoDuLieu_HDTBDV(true)

      //
      let data = {
        huonggiao_id: this.huonggiaotn_id,
        js_diachi: JSON.stringify(this.dsDiaChi),
        js_diachi_hdtb: JSON.stringify(this.dsDiaChiHDTB),
        js_hd_thuebao: JSON.stringify(this.dsHDTB),
        js_hdtb_dv: JSON.stringify(this.dsHDTBDV)
      }
      console.log('data', data)
      this.sp_themmoi_hopdong_ks_v2(data)
    },
    async TaoDuLieu_HDKH(isInsert) {
      this.dsHDKH = []
      var object = createObjectHDKH()
      let thongtin_kh = this.$refs.thongtinkhachhang.getInfoFormKhachHang()
      if (isInsert) {
        this.hdkh_id = await this.getKey('HD_KHACHHANG')
      }
      var maGD = ''
      if (isInsert) {
        maGD = await this.lay_magd(this.loaihd_id)
        this.$refs.thongtinkhachhang.setInputMaGD(maGD)
      } else {
        maGD = thongtin_kh.input_ma_gd
      }

      object.HDKH_ID = this.hdkh_id
      object.MA_GD = maGD
      object.KHACHHANG_ID = this.khachhang_id != 0 ? this.khachhang_id : null
      object.MA_KH = this.khachhang_id != 0 ? thongtin_kh.input_ma_kh.trim() : null
      object.TEN_KH = thongtin_kh.input_ten_kh.trim()
      object.DIACHI_KH = thongtin_kh.input_diachi_kh
      object.MST = thongtin_kh.input_ma_so_thue
      if (thongtin_kh.ngay_sinh && thongtin_kh.ngay_sinh.trim() != '') {
        object.NGAY_SN = moment(thongtin_kh.ngay_sinh, 'DD/MM/YYYY').format('DD/MM/YYYY HH:mm:ss')
      }
      object.LOAIGT_ID = thongtin_kh.loai_gt_selected != null ? thongtin_kh.loai_gt_selected : 0
      object.SO_GT = thongtin_kh.input_sogiayto.trim()

      if (thongtin_kh.ngay_cap && thongtin_kh.ngay_cap.trim() != '') {
        object.NGAYCAP = moment(thongtin_kh.ngay_cap, 'DD/MM/YYYY').format('DD/MM/YYYY HH:mm:ss')
      }
      object.NOICAP = thongtin_kh.input_noicap.trim()
      object.DONVI_ID = this.$root.token.getDonViID()
      object.NHANVIEN_ID = this.$root.token.getNhanVienID()

      object.KHLON_ID = 1
      object.LOAIHD_ID = this.loaihd_id
      object.NGUOI_CN = this.$root.token.getUserName()
      object.NGAY_CN = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
      object.MAY_CN = 'localhost'
      object.IP_CN = '127.0.0.1'

      object.CTV_ID = this.checkCongTacVien ? (this.ctv.ctv_id != null ? this.ctv.ctv_id : this.$root.token.getNhanVienID()) : this.$root.token.getNhanVienID()
      object.NHANVIENGT_ID = this.checkNguoiGT ? (this.nguoi_gt.nguoi_gt_id != null ? this.nguoi_gt.nguoi_gt_id : 0) : 0
      if (thongtin_kh.ngay_yc && thongtin_kh.ngay_yc.trim() != '') {
        object.NGAYLAP_HD = moment(thongtin_kh.ngay_yc, 'DD/MM/YYYY').format('DD/MM/YYYY HH:mm:ss')
      } else {
        object.NGAYLAP_HD = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
      }
      if (thongtin_kh.ngay_yc && thongtin_kh.ngay_yc.trim() != '') {
        object.NGAY_YC = moment(thongtin_kh.ngay_yc, 'DD/MM/YYYY').format('DD/MM/YYYY HH:mm:ss')
      } else {
        object.NGAY_YC = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
      }
      object.GIOITINH = thongtin_kh.radio_gioitinh == 1 ? 0 : thongtin_kh.radio_gioitinh == 2 ? 1 : 2
      object.SO_DT = thongtin_kh.input_so_dt
      object.QUOCTICH_ID = thongtin_kh.quoctich_selected != null ? thongtin_kh.quoctich_selected : 0
      object.NGANHNGHE_ID = thongtin_kh.nganhnghe_selected != null ? thongtin_kh.nganhnghe_selected : 0
      if (thongtin_kh.ngay_hethan && thongtin_kh.ngay_hethan.trim() != '') {
        object.NGAYHETHAN_GT = moment(thongtin_kh.ngay_hethan, 'DD/MM/YYYY').format('DD/MM/YYYY HH:mm:ss')
      }

      object.TNC1_ID = thongtin_kh.dataTieuNganh.TNC1_ID
      object.TNC2_ID = thongtin_kh.dataTieuNganh.TNC2_ID
      object.TNC3_ID = thongtin_kh.dataTieuNganh.TNC3_ID
      object.TNC4_ID = thongtin_kh.dataTieuNganh.TNC4_ID

      this.dsHDKH.push(object)
    },
    async TaoDuLieu_HDTB(isInsert) {
      this.dsHDTB = []
      var object = createObjectHDTB()
      let thongtin_kh = this.$refs.thongtinkhachhang.getInfoFormKhachHang()
      let thongtin_tb = this.$refs.thongtinthuebao.getInfoFormThueBao()
      if (isInsert) {
        this.hdtb_id = await this.getKey('HD_THUEBAO')
      }
      object.HDTB_ID = this.hdtb_id
      object.HDKH_ID = this.hdkh_id
      if (this.thuebao_id > 0) {
        object.THUEBAO_ID = this.thuebao_id
      }

      //Rà bổ sung theo code mới, sinh mã tb
      if (this.sinhma_tb_ks && isInsert && this.loaihd_id == LoaiHopDong.KHAOSATDATMOI) {
        //sinh_matb_khaosat
        let matb_sinh = await this.sinh_matb_khaosat(1)
        this.$refs.thongtinkhachhang.input_ma_tb = matb_sinh
      }

      object.MA_TB = this.$refs.thongtinkhachhang.input_ma_tb.trim()
      object.TEN_TB = thongtin_tb.input_ten_tb.trim()
      object.DIACHI_TB = thongtin_tb.input_diachi_tb.trim()
      object.GHICHU = thongtin_tb.input_ghichu.trim()
      object.KIEULD_ID = thongtin_tb.kieu_yc_selected != null ? thongtin_tb.kieu_yc_selected : 0
      object.LOAITB_ID = thongtin_tb.loaihinh_selected != null ? thongtin_tb.loaihinh_selected : 0
      object.DONVI_ID = thongtin_tb.donvi_xyly_selected != null ? thongtin_tb.donvi_xyly_selected : 0
      object.DOITUONG_ID = thongtin_tb.doituong_selected != null ? thongtin_tb.doituong_selected : 0
      object.TTHD_ID = 1
      object.DICHVUVT_ID = thongtin_tb.dichvu_selected != null ? thongtin_tb.dichvu_selected : 0
      object.KIEU_TB = '0'
      object.NGUOI_CN = this.$root.token.getUserName()
      object.NGAY_CN = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
      object.MAY_CN = 'localhost'
      object.IP_CN = '127.0.0.1'
      if (thongtin_tb.input_soluong.trim() != '') {
        object.SOLUONG = Number(thongtin_tb.input_soluong.trim())
      } else {
        object.SOLUONG = 0
      }
      object.DIACHI_LD = thongtin_tb.input_diachi_ld.trim()
      //console.log('thongtin_tb.ngay_sinh', thongtin_tb.ngay_sinh)
      if (thongtin_tb.ngay_sinh && thongtin_tb.ngay_sinh.trim() != '') {
        object.NGAY_SN = moment(thongtin_tb.ngay_sinh, 'DD/MM/YYYY').format('DD/MM/YYYY HH:mm:ss')
      }
      //lấy hướng giao, quy trình chưa có khảo sát
      // dset = bangts.LayHuongGiao_TiepNhan(loaihd_id, Convert.ToInt32(cboKieuLD.SelectedValue));

      // if (dset.Tables[0].Rows.Count > 0)
      // {
      //     huonggiaotn_id = Convert.ToInt32(dset.Tables[0].Rows[0]["huonggiao_id"]);
      //     quytrinh_id = Convert.ToInt32(dset.Tables[0].Rows[0]["quytrinh_id"]);
      // }
      // rowHDTB.QUYTRINH_ID = quytrinh_id;
      //Quy trình hướng giao db tự xử lý dưới

      //Viết lại cho chắc
      let kieuld_id = this.$refs.thongtinthuebao.kieu_yc_selected ? this.$refs.thongtinthuebao.kieu_yc_selected : 0
      let dset = await this.sp_layhuonggiao_tiepnhan(this.loaihd_id, kieuld_id)
      if (dset.length > 0) {
        this.huonggiaotn_id = dset[0].huonggiao_id ? dset[0].huonggiao_id : 0
        this.quytrinh_id = dset[0].quytrinh_id ? dset[0].quytrinh_id : 0
      }
      object.QUYTRINH_ID = this.quytrinh_id

      this.dsHDTB.push(object)
    },
    async TaoDuLieu_HDTBDV(isInsert) {
      this.dsHDTBDV = []
      let object = {}
      let thongtin_tb = this.$refs.thongtinthuebao.getInfoFormThueBao()
      object.HDTB_ID = this.hdtb_id
      object.LOAIDV_ID = this.loaidv_id
      object.DONVI_ID = thongtin_tb.donvi_xyly_selected
      object.KIEUDV_ID = 2
      this.dsHDTBDV.push(object)
    },
    async TaoDuLieu_DiaChi(isInsert, kieu) {
      this.dsDiaChi = []
      this.dsDiaChiHDKH = []
      this.dsDiaChiHDTB = []

      let thongtin_kh = this.$refs.thongtinkhachhang.getInfoFormKhachHang()
      let thongtin_tb = this.$refs.thongtinthuebao.getInfoFormThueBao()

      if (kieu == 1 || kieu == 4) {
        var diachi = {}

        var diachikh_id = 0
        if (isInsert) {
          diachikh_id = await this.getKey('DIACHI')
        } else {
          diachikh_id = thongtin_kh.diachi_kh.diachi_id
        }

        diachi.DIACHI_ID = diachikh_id
        diachi.TINH_ID = thongtin_kh.diachi_kh.tinh_id
        diachi.QUAN_ID = thongtin_kh.diachi_kh.quan_id
        diachi.PHUONG_ID = thongtin_kh.diachi_kh.phuong_id
        diachi.PHO_ID = thongtin_kh.diachi_kh.pho_id
        diachi.AP_ID = thongtin_kh.diachi_kh.ap_id
        diachi.KHU_ID = thongtin_kh.diachi_kh.khu_id
        diachi.SONHA = thongtin_kh.diachi_kh.sonha
        diachi.DIACHI = thongtin_kh.diachi_kh.diachi

        this.dsDiaChi.push(diachi)

        if (isInsert) {
          var diachiHDKH = {}
          diachiHDKH.DIACHI_ID = diachikh_id
          diachiHDKH.HDKH_ID = this.hdkh_id

          this.dsDiaChiHDKH.push(diachiHDKH)
        }
      }
      if (kieu == 3 || kieu == 4) {
        var diachild_id = 0
        var diachitb_id = 0
        if (isInsert) {
          diachild_id = await this.getKey('DIACHI')
          diachitb_id = await this.getKey('DIACHI')
        } else {
          diachild_id = thongtin_tb.dataDiaChiLD.diachi_id
          diachitb_id = thongtin_tb.dataDiaChiTB.diachi_id
        }
        //Địa chỉ lắp đặt --> dataset dsdiachi
        var diachiLD = {}
        diachiLD.DIACHI_ID = diachild_id
        diachiLD.TINH_ID = thongtin_tb.dataDiaChiLD.tinh_id
        diachiLD.QUAN_ID = thongtin_tb.dataDiaChiLD.quan_id
        diachiLD.PHUONG_ID = thongtin_tb.dataDiaChiLD.phuong_id
        diachiLD.PHO_ID = thongtin_tb.dataDiaChiLD.pho_id
        diachiLD.AP_ID = thongtin_tb.dataDiaChiLD.ap_id
        diachiLD.KHU_ID = thongtin_tb.dataDiaChiLD.khu_id

        diachiLD.BLOCK = thongtin_tb.dataDiaChiLD.khu_id
        diachiLD.TANG = thongtin_tb.dataDiaChiLD.tang
        diachiLD.PHONG = thongtin_tb.dataDiaChiLD.sophong
        diachiLD.MOTA = thongtin_tb.dataDiaChiLD.motathem

        diachiLD.SONHA = thongtin_tb.dataDiaChiLD.sonha
        diachiLD.DIACHI = thongtin_tb.dataDiaChiLD.diachi

        this.dsDiaChi.push(diachiLD)

        //Địa chỉ thuê bao --> dataset dsdiachi
        var diachiTB = {}
        diachiTB.DIACHI_ID = diachitb_id
        //Hard tạm
        //diachiTB.DIACHI_ID=isInsert?diachitb_id:3243243

        diachiTB.TINH_ID = thongtin_tb.dataDiaChiTB.tinh_id
        diachiTB.QUAN_ID = thongtin_tb.dataDiaChiTB.quan_id
        diachiTB.PHUONG_ID = thongtin_tb.dataDiaChiTB.phuong_id
        diachiTB.PHO_ID = thongtin_tb.dataDiaChiTB.pho_id
        diachiTB.AP_ID = thongtin_tb.dataDiaChiTB.ap_id
        diachiTB.KHU_ID = thongtin_tb.dataDiaChiTB.khu_id

        diachiTB.BLOCK = thongtin_tb.dataDiaChiTB.khu_id
        diachiTB.TANG = thongtin_tb.dataDiaChiTB.tang
        diachiTB.PHONG = thongtin_tb.dataDiaChiTB.sophong
        diachiTB.MOTA = thongtin_tb.dataDiaChiTB.motathem

        diachiTB.SONHA = thongtin_tb.dataDiaChiTB.sonha
        diachiTB.DIACHI = thongtin_tb.dataDiaChiTB.diachi

        this.dsDiaChi.push(diachiTB)

        var diachiHDTB = {}
        diachiHDTB.DIACHILD_ID = diachild_id
        //địa chỉ thuê bao
        diachiHDTB.DIACHI_ID = diachitb_id
        diachiHDTB.HDTB_ID = this.hdtb_id

        this.dsDiaChiHDTB.push(diachiHDTB)
      }
    },
    async TaoDuLieu_HDTB_KV(isInsert) {
      this.dsHDTB_KV = []
      var object = {}
      let thongtin_tb = this.$refs.thongtinthuebao.getInfoFormThueBao()
      object.HDTB_ID = this.hdtb_id
      object.KHUVUC_ID = thongtin_tb.khuvuc_selected != null ? thongtin_tb.khuvuc_selected.khuvuc_id : 0
      //LOAI_KV.KHUVUC_DIABAN=4
      object.LOAIKV_ID = 4
      object.NGUOI_CN = this.$root.token.getUserName()
      object.NGAY_CN = moment(new Date()).format('DD/MM/YYYY')
      object.MAY_CN = 'localhost'
      object.IP_CN = '127.0.0.1'
      //daucuoi_id không truyền qua form mặc định = 2
      object.KIEUKV_ID = 2
      // if (daucuoi_id > 0)
      //         dr.KIEUKV_ID = daucuoi_id;
      this.dsHDTB_KV.push(object)
    },
    callbackChonDiChiKH(data) {
      this.$refs.thongtinthuebao.setDiaChiLD_TB_Theo_KH(data)
    },
    initDiaChiKH(data) {
      this.$refs.thongtinthuebao.initDiaChiKH(data)
    },
    async kiemtra_dl_khachhang() {
      let thongtin_kh = this.$refs.thongtinkhachhang.getInfoFormKhachHang()
      if (thongtin_kh.input_so_dt.trim() == '') {
        this.$toast.error('Bạn chưa nhập số điện thoại liên hệ của khách hàng !')
        this.$refs.thongtinkhachhang.focusSDT()
        return false
      }
      if (thongtin_kh.input_so_dt.trim().length > 20) {
        this.$toast.error('Số điện thoại liên hệ của khách hàng không được quá 10 ký tự.\nBạn hãy bỏ số 0 ở đầu (nếu có)!')
        this.$refs.thongtinkhachhang.focusSDT()
        return false
      }
      if (thongtin_kh.input_so_dt.trim().length < 5) {
        this.$toast.error('Số điện thoại liên hệ của khách hàng không được ít hơn 7 ký tự.')
        this.$refs.thongtinkhachhang.focusSDT()
        return false
      }

      if (thongtin_kh.input_diachi_kh == '' || thongtin_kh.diachi_kh.quan_id == 0 || thongtin_kh.diachi_kh.phuong_id == 0) {
        this.$toast.error('Hãy nhập địa chỉ khách hàng!')
        this.$refs.thongtinkhachhang.focusDiaChiKH()
        return false
      }
      if (thongtin_kh.input_ten_kh.trim() == '') {
        this.$toast.error('Bạn chưa nhập tên khách hàng!')
        this.$refs.thongtinkhachhang.focusTenKH()
        return false
      }
      //check Hải Phòng
      let maTinh = this.$root.token.getMaTinh()
      if (maTinh == 'HPG') {
        if (thongtin_kh.input_ten_kh == '') {
          this.$toast.error('Hãy nhập tên khách hàng !')
          this.$refs.thongtinkhachhang.focusTenKH()
          return false
        }
        //Kiểm tra địa chỉ KH
        // if(thongtin_kh.input_diachi_kh==''||thongtin_kh.diachi_kh.quan_id==0||thongtin_kh.diachi_kh.phuong_id==0){
        //     this.$toast.error('Hãy nhập địa chỉ khách hàng!')
        //     this.$refs.thongtinkhachhang.focusDiaChiKH()
        //     return false
        // }
        if (this.loaihd_id != LoaiHopDong.KHAOSATDATMOI && thongtin_kh.nganhnghe_selected == null) {
          this.$toast.error('Bạn chưa nhập ngành nghề !')
          return false
        }

        if (this.loaihd_id != LoaiHopDong.KHAOSATDATMOI && (thongtin_kh.dataTieuNganh.TNC1_ID == 0 || thongtin_kh.dataTieuNganh.TNC2_ID == 0 || thongtin_kh.dataTieuNganh.TNC3_ID == 0)) {
          this.$toast.error('Bạn chưa chọn chi tiết ngành nghề.Hãy kiểm tra lại !')
          return false
        }
      }
      if (thongtin_kh.input_ma_gd != '') {
        try {
          let response = await SurveyRequestAPI.kiemtra_makh_matt_magd(this.axios, thongtin_kh.input_ma_gd, 3, this.loaihd_id)
          if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data != null && response.data.data == 0) {
            this.$toast.error('Mã giao dịch không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!')
            this.$refs.thongtinkhachhang.focusMaGD()
            return false
          }
        } catch (e) {
          this.$toast.error('Đã xảy ra lỗi trong quá trình kiễm tra dữ liệu.')
          return false
        }
      }
      return true
    },
    async kiemtra_dl_thuebao() {
      let thongtin_tb = this.$refs.thongtinthuebao.getInfoFormThueBao()
      let thongtin_kh = this.$refs.thongtinkhachhang.getInfoFormKhachHang()

      if (thongtin_tb.input_ten_tb.trim() == '') {
        this.$toast.error('Hãy nhập tên thuê bao !')
        this.$refs.thongtinthuebao.focusTenTB()
        return false
      }

    //   if (thongtin_tb.input_diachi_ld.trim() == '' || thongtin_tb.dataDiaChiLD.quan_id == 0 || thongtin_tb.dataDiaChiLD.phuong_id == 0 || thongtin_tb.dataDiaChiLD.pho_id + thongtin_tb.dataDiaChiLD.ap_id + thongtin_tb.dataDiaChiLD.khu_id == 0) {
    //     this.$toast.error('Hãy nhập địa chỉ lắp đặt!')
    //     this.$refs.thongtinthuebao.focusDiaChiLD()
    //     return false
    //   }

    //   if (thongtin_tb.input_diachi_tb.trim() == '' || thongtin_tb.dataDiaChiTB.quan_id == 0 || thongtin_tb.dataDiaChiTB.phuong_id == 0 || thongtin_tb.dataDiaChiTB.pho_id + thongtin_tb.dataDiaChiTB.ap_id + thongtin_tb.dataDiaChiTB.khu_id == 0) {
    //     this.$toast.error('Hãy nhập địa chỉ thuê bao!')
    //     this.$refs.thongtinthuebao.focusDiaChiTB()
    //     return false
    //   }
      if (thongtin_tb.donvi_xyly_selected == null) {
        this.$toast.error('Chưa có đơn vị thi công, bạn hãy kiểm tra lại!')
        return false
      }

      if (this.loaihd_id != LoaiHopDong.KHAOSATDATMOI && this.loaihd_id != LoaiHopDong.TIEPNHANHD && this.loaihd_id != LoaiHopDong.TUVAN_DV) {
        if (thongtin_kh.input_ma_tb.trim() == '') {
          this.$toast.error('Hãy nhập số máy/acc!')
          this.$refs.thongtinkhachhang.focusMaTB()
          return false
        }
      }

      if (thongtin_tb.kieu_yc_selected == KieuLapDat.KHAOSATCDLH) {
        try {
          let response = await SurveyRequestAPI.fn_kiemtra_hd_chuahoanthien(this.axios, this.thuebao_id)
          if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
            let result = Number(response.data.data)
            if (result > 0) {
              this.$toast.error('Thuê bao đang có hợp đồng khác chưa hoàn thiện, bạn không thể lập hợp đồng!')
              return false
            }
          }
        } catch (e) {
          this.$toast.error('Đã xảy ra lỗi trong quá trình kiễm tra dữ liệu.')
          return false
        }
      }

      return true
    },
    async getKey(keyname) {
      try {
        let response = await SurveyRequestAPI.get_keys(this.axios, keyname)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return Number(response.data.data)
        } else {
          return 0
        }
      } catch (e) {
        return 0
      }
    },
    async lay_magd(loaihd_id) {
      try {
        let response = await SurveyRequestAPI.lay_magd(this.axios, loaihd_id)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return ''
        }
      } catch (e) {
        return ''
      }
    },
    initHeader() {
      if (this.loaihd_id == LoaiHopDong.KHAOSATDATMOI) {
        this.header.title = 'TIẾP NHẬN YÊU CẦU KHẢO SÁT LẮP ĐẶT MỚI'
        this.header.list = [
          {
            name: 'Hợp đồng',
            link: { name: 'Ui.cards' },
            active: false
          },
          {
            name: 'Lập hợp đồng',
            link: { name: 'Ui.cards' },
            active: false
          },
          {
            name: 'Tiếp nhận yêu cầu khảo sát lắp đặt',
            link: { name: 'Ui.buttons' },
            active: true
          }
        ]
      } else if (this.loaihd_id == LoaiHopDong.KHAOSATDC) {
        this.header.title = 'TIẾP NHẬN YÊU CẦU KHẢO SÁT DỊCH CHUYỂN'
      } else if (this.loaihd_id == LoaiHopDong.KHAOSATCDLH) {
        this.header.title = 'TIẾP NHẬN YÊU CẦU KHẢO SÁT THAY ĐỔI LOẠI HÌNH'
        this.header.list = [
          {
            name: 'Hợp đồng',
            link: { name: 'Ui.cards' },
            active: false
          },
          {
            name: 'Lập hợp đồng',
            link: { name: 'Ui.cards' },
            active: false
          },
          {
            name: 'Tiếp nhận yêu cầu khảo sát thay đổi loại hình',
            link: { name: 'Ui.buttons' },
            active: true
          }
        ]
      } else if (this.loaihd_id == LoaiHopDong.TIEPNHANHD) {
        this.header.title = 'TIẾP NHẬN YÊU CẦU KÝ HỢP ĐỒNG TẠI ĐỊA CHỈ KHÁCH HÀNG'
        this.header.list = [
          {
            name: 'Lập hợp đồng',
            link: { name: 'Ui.cards' },
            active: false
          },
          {
            name: 'Tiếp nhận yêu cầu',
            link: { name: 'Ui.buttons' },
            active: true
          }
        ]
      }
      // else if(this.loaihd_id==LoaiHopDong.TUVAN_DV){
      //     this.header.title='TỔNG ĐÀI TIẾP NHẬN YÊU CẦU KHÁCH HÀNG'
      // }
    },
    async sp_lay_ds_hdtb_tiepnhan_ks(loaihd_id, nhanvien_id, ma_gd) {
      try {
        this.loading(true)
        //this.dataSources=[]
        let response = await SurveyRequestAPI.sp_lay_ds_hdtb_tiepnhan_ks(this.axios, loaihd_id, nhanvien_id, ma_gd)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data.map((x) => Object.assign(x, { ngay_yc: x.ngay_yc ? moment(x.ngay_yc, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY') : null }))
        } else {
          return []
        }
      } catch (e) {
        console.log(e)
        this.loading(false)
        if (e.data && e.data.message) {
          this.$toast.error(e.data.message)
        } else if (e.response && e.response.data && e.response.data.message) {
          this.$toast.error(e.response.data.message)
        } else {
          this.$toast.error('Không load được danh sách')
        }
        return []
      }
    },
    //tcdanhba.LAY_DB_THEO_MATB(txtMaKH.Text.Trim(), 0, 0);
    //MaKH Key press
    async lay_thongtin_khachhang_nhanquyen(ma_kh) {
      try {
        this.loading(true)
        let response = await SurveyRequestAPI.lay_thongtin_khachhang_nhanquyen(this.axios, ma_kh)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          if (response.data.data.length > 0) {
            this.clear()
            this.setActiveActions(-1)
            this.hienthi_tt_danhba(response.data.data)
            this.$refs.thongtinkhachhang.focusMaKH()
          } else {
            this.$toast.error('Không tìm thấy thông tin khách hàng!')
          }
        } else {
          this.$toast.error('Không tìm thấy thông tin khách hàng!')
        }
      } catch (e) {
        this.loading(false)
        if (e.data && e.data.message) {
          this.$toast.error(e.data.message)
        } else if (e.response && e.response.data && e.response.data.message) {
          this.$toast.error(e.response.data.message)
        } else {
          this.$toast.error('Không load được thông tin khách hàng')
        }
      }
    },
    async lay_thongtin_khachhang_nhanquyen_2(ma_kh) {
      try {
        this.loading(true)
        let response = await SurveyRequestAPI.lay_thongtin_khachhang_nhanquyen(this.axios, ma_kh)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async getThamSo(ma_ts, defaultValue) {
      try {
        this.loading(true)
        let response = await SurveyRequestAPI.lay_ds_thamso_md(this.axios, ma_ts)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return defaultValue
        }
      } catch (e) {
        this.loading(false)
        return defaultValue
      }
    },
    async hienthi_tt_danhba(ds) {
      //
      if (ds.length > 0) {
        //hiển thị thông tin lên form
        this.action_type = 2
        this.itemMaKHSelected = ds[0]
        this.thuebao_id = 0
        if (this.loaihd_id != LoaiHopDong.TIEPNHAN_DATMOI) {
          this.thuebao_id = this.itemMaKHSelected.thuebao_id != null ? this.itemMaKHSelected.thuebao_id : 0
        }
        if (this.itemMaKHSelected.khachhang_id != null) {
          this.khachhang_id = this.itemMaKHSelected.khachhang_id
        } else {
          this.khachhang_id = 0
        }

        await this.$refs.thongtinkhachhang.initInfoMaKH(ds[0])
        await this.$refs.thongtinthuebao.initInfoMaKH(ds[0])
      } else {
        this.setActiveActions(0)
      }
    },
    async hienthi_danhsach_hdtb(ma_gd) {
      this.rowSelected = null
      let nhanvien_id = this.$root.token.getNhanVienID()
      //this.$refs.gridDs.flagSelectedRowIndexes=[0]
      let data = await this.sp_lay_ds_hdtb_tiepnhan_ks(this.loaihd_id, nhanvien_id, ma_gd)
      //#hard
      //await this.sp_lay_ds_hdtb_tiepnhan_ks(38, nhanvien_id, ma_gd)

      if (data.length > 0) {
        // this.$refs.gridDs.selectRowIndex(0)
        //focus row
        // if(this.hdtb_id!=0){
        //     const index=this.dataSources.findIndex(x=>x.hdtb_id==this.hdtb_id)
        //     if(index>-1){
        //         this.rowSelectedId=this.dataSources[index].hdtb_id
        //         this.onSelectedRow(this.dataSources[index])
        //     }else{
        //         this.rowSelectedId=this.dataSources[0].hdtb_id
        //         this.onSelectedRow(this.dataSources[0])
        //     }
        // }else{
        //     this.rowSelectedId=this.dataSources[0].hdtb_id
        //     this.onSelectedRow(this.dataSources[0])
        // }

        this.actions[this.actions.findIndex((x) => x.id == 'hoanthanh')].active = true
        //active xoa_yc khi row lớn hơn 1, ==1 xoá hd
        if (data.length > 1) {
          this.actions[this.actions.findIndex((x) => x.id == 'xoa_yc')].active = true
        } else {
          this.actions[this.actions.findIndex((x) => x.id == 'xoa_yc')].active = false
        }
        this.dataSources = data
      } else {
        this.actions[this.actions.findIndex((x) => x.id == 'nhapmoi')].active = false
        this.actions[this.actions.findIndex((x) => x.id == 'hoanthanh')].active = false
        this.actions[this.actions.findIndex((x) => x.id == 'xoa_hd')].active = false
        this.actions[this.actions.findIndex((x) => x.id == 'them_yc')].active = false
        this.actions[this.actions.findIndex((x) => x.id == 'xoa_yc')].active = false
        this.dataSources = []
        this.rowSelected = null
        this.setActiveActions(1)
      }
    },
    async xoa_hdtb(hdtb_id) {
      try {
        this.loading(true)
        let response = await SurveyRequestAPI.xoa_hdtb(this.axios, hdtb_id)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
          this.$toast.success('Xoá thuê bao thành công!')
          this.clear()
          //hiển thị lại danh sách
          await this.hienthi_danhsach_hdtb('')
        } else {
          if (response && response.data && response.data.message) {
            this.$toast.error(response.data.message)
          } else {
            this.$toast.error('Đã xảy ra lỗi, xoá thuê bao không thành công')
          }
        }
      } catch (e) {
        this.loading(false)
        if (e.data && e.data.message) {
          this.$toast.error(e.data.message)
        } else if (e.response && e.response.data && e.response.data.message) {
          this.$toast.error(e.response.data.message)
        } else {
          this.$toast.error('Đã xảy ra lỗi, xoá thuê bao không thành công')
        }
      }
    },
    async xoa_hdkh_v2(hdtb_id, loaihd_id) {
      try {
        this.loading(true)
        let response = await SurveyRequestAPI.xoa_hdkh_v2(this.axios, hdtb_id, loaihd_id)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
          this.$toast.success('Xoá thành công!')
          //hiển thị lại danh sách
          this.clear()
          this.setActiveActions(0)
          await this.hienthi_danhsach_hdtb('')
        } else {
          if (response && response.data && response.data.message) {
            this.$toast.error(response.data.message)
          } else {
            this.$toast.error('Đã xảy ra lỗi, xoá dữ liệu không thành công')
          }
        }
      } catch (e) {
        this.loading(false)
        if (e.data && e.data.message) {
          this.$toast.error(e.data.message)
        } else if (e.response && e.response.data && e.response.data.message) {
          this.$toast.error(e.response.data.message)
        } else {
          this.$toast.error('Đã xảy ra lỗi, xoá dữ liệu không thành công')
        }
      }
    },
    async sp_capnhat_hopdong_ks(data) {
      try {
        this.loading(true)
        let response = await SurveyRequestAPI.sp_capnhat_hopdong_ks(this.axios, data)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
          this.$toast.success('Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!')
          //hiển thị lại danh sách
          await this.hienthi_danhsach_hdtb('')
        } else {
          if (response && response.data && response.data.message) {
            this.$toast.error(response.data.message)
          } else {
            this.$toast.error('Đã xảy ra lỗi, cập nhật không thành công')
          }
        }
      } catch (e) {
        this.loading(false)
        if (e.data && e.data.message) {
          this.$toast.error(e.data.message)
        } else if (e.response && e.response.data && e.response.data.message) {
          this.$toast.error(e.response.data.message)
        } else {
          this.$toast.error('Đã xảy ra lỗi, cập nhật không thành công')
        }
      }
    },
    async sp_themmoi_hopdong_ks(data) {
      try {
        this.loading(true)
        let response = await SurveyRequestAPI.sp_themmoi_hopdong_ks(this.axios, data)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
          this.$toast.success('Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!')
          this.clear()
          //hiển thị lại danh sách
          await this.hienthi_danhsach_hdtb('')
        } else {
          if (response && response.data && response.data.message) {
            this.$toast.error(response.data.message)
          } else {
            this.$toast.error('Đã xảy ra lỗi, cập nhật không thành công')
          }
        }
      } catch (e) {
        this.loading(false)
        if (e.data && e.data.message) {
          this.$toast.error(e.data.message)
        } else if (e.response && e.response.data && e.response.data.message) {
          this.$toast.error(e.response.data.message)
        } else {
          this.$toast.error('Đã xảy ra lỗi, cập nhật không thành công')
        }
      }
    },
    //Thêm YC (Thêm TB)
    async sp_themmoi_hopdong_ks_v2(data) {
      try {
        this.loading(true)
        let response = await SurveyRequestAPI.sp_themmoi_hopdong_ks_v2(this.axios, data)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
          //hiển thị lại danh sách
          this.clear()
          await this.hienthi_danhsach_hdtb('')
        } else {
          if (response && response.data && response.data.message) {
            this.$toast.error(response.data.message)
          } else {
            this.$toast.error('Đã xảy ra lỗi')
          }
        }
      } catch (e) {
        this.loading(false)
        if (e.data && e.data.message) {
          this.$toast.error(e.data.message)
        } else if (e.response && e.response.data && e.response.data.message) {
          this.$toast.error(e.response.data.message)
        } else {
          this.$toast.error('Đã xảy ra lỗi')
        }
      }
    },
    async initDuLieu() {
      //Handle form trùng
      let endPath = this.$router.currentRoute.path.split('/').pop()
      if (endPath == 'SurveyRequest') {
        //[UR2.1.001] - Tiếp nhận yêu cầu khảo sát lắp đặt
        this.loaihd_id = LoaiHopDong.KHAOSATDATMOI
      } else if (endPath == '800126InquiryWatchList') {
        //[UR2.1.057] - 80126- Tiếp nhận yêu cầu
        this.loaihd_id = LoaiHopDong.TIEPNHANHD
      } else if (endPath == 'ReceiveSurveyRequestChangeType') {
        //[UR2.1.002] - Tiếp nhận yêu cầu khảo sát thay đổi loại hình
        this.loaihd_id = LoaiHopDong.KHAOSATCDLH
      } else if (endPath == 'ReceiveRequest') {
        //[UR2.1.055] - Tiếp nhận yêu cầu
        this.loaihd_id = LoaiHopDong.TIEPNHANHD
      }
      //LoaiDonViXL = LOAI_DV.DONVIQL_TT;
      if (this.loaihd_id == LoaiHopDong.TUVAN_DV || this.loaihd_id == LoaiHopDong.TIEPNHANHD) {
        this.loaidv_id = 2
      }

      this.initHeader()
      // if(this.loaihd_id == LoaiHopDong.TUVAN_DV){
      //     this.checkCongTacVien=false
      //     this.checkNguoiGT=false
      // }
      await this.$refs.thongtinkhachhang.initDuLieu()
      await this.$refs.thongtinthuebao.initDuLieu()
      //Khởi tạo tham số
      this.LOC_NVDIADANH_NVPX = (await this.getThamSo('LOC_NVDIADANH_NVPX', [])).length > 0
      this.sinhma_tb_ks = (await this.getThamSo('SINH_MATB_KS', [])).length > 0

      console.log('sinhma_tb_ks', this.sinhma_tb_ks)
      console.log('LOC_NVDIADANH_NVPX', this.LOC_NVDIADANH_NVPX)

      //Điều  chỉnh code mới them tham so cho hpg co the chon duoc nhan vien
      let ks_laphopdong_taidc = (await this.getThamSo('KS_LAPHD_TAIDIACHI', [])).length > 0
      this.$refs.thongtinthuebao.enabledNhanVien = ks_laphopdong_taidc

      //
      await this.hienthi_danhsach_hdtb('')
    }
  },
  mounted() {
    setTimeout(() => {
      this.initDuLieu()
    }, 200)
  }
}
</script>
<style>
.mx-input:disabled,
.mx-input.disabled {
  color: #555 !important;
}
.select2-selection {
  overflow: hidden !important;
}
.select2-container .select2-selection--single .select2-selection__rendered {
  white-space: normal !important;
  word-break: break-all !important;
}
/* asset css select2 form */
.select2-container {
  width: 100% !important;
}

.select2-container--default .select2-selection--single {
  border-color: #e0e0e0;
  outline: none;
}

.select2-dropdown {
  border: 1px solid #e0e0e0;
}

.select2-container--default .select2-selection--single .select2-selection__arrow b {
  font-family: 'FontAwesome';
  border: 0px;
  top: 0px;
  height: 1.428rem;
  bottom: 0px;
  line-height: 1.428rem;
  left: 5px;
  margin: auto;
}
.select2-container--default .select2-selection--single .select2-selection__arrow b:before {
  content: '';
}

.select2-container--default .select2-search--dropdown .select2-search__field {
  border: 0px;
  outline: none;
}
</style>

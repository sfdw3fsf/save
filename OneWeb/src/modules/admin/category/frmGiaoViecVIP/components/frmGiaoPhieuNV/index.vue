<template>
  <div>
    <div>
      <div class="list-actions-top" style="position: fixed">
        <ul class="list">
          <li>
            <a @click="tsbtnGhiLai_Clicked"> <span class="icon one-save"></span> Ghi lại </a>
          </li>

          <li>
            <a title="Xóa tất cả các nhân viên đã được giao nhiệm vụ" @click="tsbtnXoaGiao_Clicked"> <span class="icon one-trash"></span> Xoá giao </a>
          </li>
          <li>
            <a title="Gửi tin nhắn đến người thực hiện nhiệm vụ" @click="tsbtnGuiSMS_Clicked"> <span class="icon fa fa-comments"></span> Gửi SMS </a>
          </li>
          <li>
            <a title="Cập nhật KQXL" @click="tsbtnCapNhatKQXL_Clicked"> <span class="icon one-reload1"></span>Cập nhật KQXL </a>
          </li>
        </ul>
      </div>
      <div class="popup-body" style="padding-top: 50px">
        <div class="box-form">
          <div class="legend-title">
            <div class="pull-left">Danh sách phiếu đang chọn</div>
            <div class="pull-right"></div>
            <div class="clearfix"></div>
          </div>
          <div>
            <DataGrid
              ref="gridDanhSach"
              locale="vi-VN"
              :columns="gridDanhSach.cols"
              :dataSource="gridDanhSach.list"
              :allowFiltering="true"
              :allowSorting="true"
              :allowPaging="true"
              :allowResizing="true"
              :filterSettings="{ showFilterBarOperator: false, mode: 'Immediate', immediateModeDelay: 500 }"
              :height="300"
              :pageSettings="pageSettings"
              gridLines="Both"
              :allowSelection="true"
              :selectionSettings="{ mode: 'Both', type: 'Mutiple', enableToggle: false, checkboxOnly: false, persistSelection: false, checkboxMode: 'ResetOnRowClick' }"
              @rowSelected="gvDanhSach_FocusedRowChanged"
              @selectedItemsChanged="gridDanhSachSelectedItemsChanged"
            ></DataGrid>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">
            <div class="pull-left">Danh sách nhân viên</div>
            <div class="pull-right">
              <span class="text-main normal">Cấu trúc nhắn tin phản hồi: BH [cách] Mã tỉnh [cách] ID phiếu</span>
            </div>
            <div class="clearfix"></div>
          </div>
          <div class="row">
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w90">
                  <div class="check-action">
                    <input ref="chkDonViQL" @change="chkDonViQL_CheckedChanged" type="checkbox" class="check" />
                    <span class="name">Trung tâm</span>
                  </div>
                </div>
                <div class="value">
                  <div class="select-custom">
                    <Select2Ext disable ref="cboDonVi" @change="cboDonVi_EditValueChanged" :allowFiltering="true"></Select2Ext>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w90">
                  <span class="name">Tổ TC</span>
                </div>
                <div class="value">
                  <div class="select-custom">
                    <Select2Ext disable @change="cboTramTC_EditValueChanged" ref="cboTramTC" :allowFiltering="true"></Select2Ext>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w120">
                  <div class="check-action">
                    <input ref="chk_LoaiNV" @change="chk_LoaiNV_CheckedChanged" type="checkbox" class="check" />
                    <span class="name">Loại nhân viên</span>
                  </div>
                </div>
                <div class="value">
                  <Select2Ext disable ref="cbo_LoaiNV" @change="cbo_LoaiNV_EditValueChanged" :allowFiltering="true"></Select2Ext>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w90">
                  <div class="check-action">
                    <input type="checkbox" class="check" @change="chkPBH_CheckedChanged" ref="chkPBH" />
                    <span class="name">Phòng BH</span>
                  </div>
                </div>
                <div class="value">
                  <div class="select-custom">
                    <Select2Ext @change="cboPhongBH_EditValueChanged" disable ref="cboPhongBH" :allowFiltering="true"></Select2Ext>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="table-content mart10">
            <GiaoViecGrid :vhuonggiao_id="vhuonggiao_id" ref="gridNhanVien"></GiaoViecGrid>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách nhân viên đã giao nhiệm vụ</div>
          <div class="table-content">
            <!-- <DataGridCustom
                ref="gridDaGiao"
                v-bind:columns="collumns_dg"
            ></DataGridCustom> -->
            <DaGiaocGrid @delete_1_row="delete_1_row" ref="gridDaGiao"></DaGiaocGrid>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Select2Ext from './components/Select2Ext'
import DataGridCustom from './components/DataGridCustom'
import GiaoViecGrid from './components/GiaoViecGrid.vue'
import DaGiaocGrid from './components/DaGiaoGrid.vue'
import Vue from 'vue'
const EventBus = new Vue()
import { LOAI_DV } from '@/const/enums'

export default {
  props: {
    vdsphieu: {
      type: [Object, Array],
      defaul: null,
      require: false
    },
    vnhanvien_giao_id: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    vngaygiao: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    vma_tb: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    vhdtb_id: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    vhuonggiao_id: {
      type: [String, Number],
      defaul: null,
      require: false
    }
  },
  components: {
    Select2Ext,
    GiaoViecGrid,
    DataGridCustom,
    DaGiaocGrid
  },
  data() {
    return {
      objgiaophieu: [],
      dsnhanviengp: [],
      tt_nd: {},
      bangts: [],
      ds_nhanvien: [],
      vphieu: 0,
      vdonvi: 0,
      vnhanvienid: 0,
      vLoaiNV_ID: 0,
      vnoidung: 0,
      vghichu: 0,
      kt_load: true,
      cp: null,
      dsma_tb: 0,
      dshdtb_id: [],
      _vloaihd: 0,
      vkieugiao: 0, // Hoangpkn : 03/06/2017 .0 : giao việc bình thường, 1: giao việc thu hồi vật tư
      dt_nvu: 0,
      dsNhiemvu: [],
      gridDanhSach: {
        list: [],
        cols: [
          { fieldName: 'ma_tb', headerText: 'Mã thuê bao bán', allowFiltering: true },
          { fieldName: 'ma_gd', headerText: 'Mã giao dịch bán', allowFiltering: true },
          { fieldName: 'vipxl', headerText: 'Vip xử lý', allowFiltering: true },
          { fieldName: 'ten_tinh', headerText: 'Tỉnh quản lý HĐ', allowFiltering: true },
          { fieldName: 'tinh_ld', headerText: 'Tỉnh LĐ', allowFiltering: true },
          { fieldName: 'ten_loaihd', headerText: 'Loại HĐ', allowFiltering: true },
          { fieldName: 'sohd_khdn', headerText: 'Số hợp đồng KHDN', allowFiltering: true },
          { fieldName: 'ten_dvvt', headerText: 'Dịch vụ', allowFiltering: true },
          { fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true },
          { fieldName: 'ma_vnpt', headerText: 'Mã VNPT', allowFiltering: true }, //thieu
          { fieldName: 'ten_kieuld', headerText: 'Kiểu lắp đặt', allowFiltering: true },
          { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true },
          { fieldName: 'ngaygiao', headerText: 'Ngày giao', allowFiltering: true },
          { fieldName: 'diachi_kh', headerText: 'Địa chỉ khách hàng', allowFiltering: true },
          { fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true },
          { fieldName: 'ngay_cn', headerText: 'Ngày yêu cầu', allowFiltering: true },
          { fieldName: 'ngaygiao', headerText: 'Ngày giao', allowFiltering: true },
          { fieldName: 'so_dt_giao', headerText: 'ĐT NV giao', allowFiltering: true },
          { fieldName: 'ten_donvi_nhan', headerText: 'Đơn vị nhận', allowFiltering: true },
          // { fieldName: 'diachi_ld', headerText: 'Hẹn từ', allowFiltering: true },
          // { fieldName: 'diachi_ld', headerText: 'Hẹn đến', allowFiltering: true },
          // { fieldName: 'diachi_ld', headerText: 'Nội dung hẹn', allowFiltering: true },
          { fieldName: 'huong_kn', headerText: 'Hướng KN', allowFiltering: true }, //thieu
          { fieldName: 'ten_donvi_giao', headerText: 'Đơn vị giao', allowFiltering: true }
        ],
        checked: []
      },
      collumns_dg: [
        {
          fieldName: 'ma_nv',
          headerText: 'Mã NV',
          allowFiltering: true,
          allowSorting: false,
          width: 100
        },
        {
          fieldName: 'ten_nv',
          headerText: 'Tên nhân viên',
          isGroupedColumn: true,
          captionTemplate: '${key} - ${count} nhiệm vụ',
          allowFiltering: true,
          allowSorting: false,
          width: 140
        },
        {
          fieldName: 'so_dt',
          headerText: 'Điện thoại',
          allowFiltering: true,
          allowSorting: false,
          width: 140
        },
        {
          fieldName: 'nhiemvu',
          headerText: 'Nhiệm vụ',
          allowFiltering: true,
          allowSorting: false,
          width: 140
        },
        {
          fieldName: 'kqxl_id',
          headerText: 'Kết quả XL',
          allowFiltering: true,
          allowSorting: false,
          width: 140
        },
        {
          fieldName: 'trangthai',
          headerText: 'Trạng thái',
          allowFiltering: true,
          allowSorting: false,
          width: 140
        },
        {
          fieldName: 'ghichu',
          headerText: 'Ghi chú',
          allowFiltering: true,
          allowSorting: false,
          width: 140
        }
      ],
      dsphieuGV: []
    }
  },
  async mounted() {
    console.log('mounted')
    console.log(this.vdsphieu)
    // if (Array.isArray(this.vdsphieu) && this.vdsphieu.length > 0) {
    this.vphieu = this.vdsphieu[0].phieu_id

    this.gridDanhSach.list = this.vdsphieu
    //   this.vdsphieu = this.vdsphieu
    // } else {
    //   this.vdsphieu = [this.vdsphieu]
    //   this.vphieu = this.vdsphieu
    // }
    this.vLoaiNV_ID = -1

    this.frnGiaoPhieuNV_Load()
  },
  methods: {
    async tsbtnGhiLai_Clicked() {
      this.dsphieuGV = this.vdsphieu.map((e) => e.phieu_id)
      await this.XuLyCapNhat()
    },
    async XuLyCapNhat() {
      try {
        this.loading(true)
        let kt = false
        let lstChon = []
        for (let i = 0; i < this.$refs.gridNhanVien.dataSource.length; i++) {
          if (this.$refs.gridNhanVien.dataSource[i].nhiemvu) {
            kt = true
            lstChon.push(this.$refs.gridNhanVien.dataSource[i])
          }
          if (this.$refs.gridNhanVien.dataSource[i].so_dt && this.$refs.gridNhanVien.dataSource[i].so_dt.Length > 15) {
            this.$toast.error('Số điện thoại không được quá 15 ký tự !')
            return
          }
        }

        if (!kt) {
          this.$toast.error('Hãy nhập nhiệm vụ cho nhân viên!')
          return
        }

        let data = []
        if (this.dsphieuGV && this.dsphieuGV.length > 0) {
          for (let k = 0; k < this.$refs.gridNhanVien.dataSource.length; k++) {
            let item = this.$refs.gridNhanVien.dataSource[k]
            if (item.nhiemvu) {
              let nhiemVuList = item.nhiemvu.split(',')
              for (let i = 0; i < nhiemVuList.length; i++) {
                let nhiemnhu = this.dsNhiemvu.find((nv) => nv.nhiemvu == nhiemVuList[i])

                for (let j = 0; j < this.dsphieuGV.length; j++) {
                  await this.axios.post('/web-thicong/hoinghi_truyenhinh/delete_giaophieu_nv', {
                    phieu_id: this.dsphieuGV[j]
                  })
                  if (nhiemnhu) {
                    console.log('nhiemnhu !=null')
                    data.push({
                      PHIEU_ID: this.dsphieuGV[j],
                      NHANVIEN_TH_ID: item.nhanvien_th_id,
                      NHIEMVU: nhiemnhu.nhiemvu,
                      GHICHU: item.ghichu,
                      NHANVIEN_GIAO_ID: this.vnhanvien_giao_id,
                      NGAYGIAO: this.ngaygiao,
                      SO_DT: item.so_dt,
                      STATUS: 0,
                      NHIEMVU_ID: nhiemnhu.nhiemvu_id,
                      KQXL_ID: null
                    })
                    console.log(data)
                  }
                }
              }
            }
          }
        } else {
          this.$refs.gridNhanVien.dataSource.forEach((item) => {
            if (item.nhiemvu) {
              item.nhiemvu.split(',').forEach((nv_text) => {
                let nhiemnhu = this.dsNhiemvu.find((nv) => nv.nhiemvu == nv_text)
                if (nhiemnhu) {
                  data.push({
                    PHIEU_ID: data.phieu_id,
                    NHANVIEN_TH_ID: item.nhanvien_th_id,
                    NHIEMVU: nhiemnhu.nhiemvu,
                    GHICHU: item.ghichu,
                    NHANVIEN_GIAO_ID: this.vnhanvien_giao_id,
                    NGAYGIAO: this.vngaygiao,
                    SO_DT: item.so_dt,
                    STATUS: 0,
                    NHIEMVU_ID: nhiemnhu.nhiemvu_id,
                    KQXL_ID: null
                  })
                }
              })
            }
          })

          await this.axios.post('/web-thicong/hoinghi_truyenhinh/delete_giaophieu_nv', {
            phieu_id: this.vphieu
          })
        }
        let check = await this.axios.post('/web-thicong/hoinghi_truyenhinh/fn_an_capnhat_giaophieu_nv', {
          json_giaophieu_nv: data
        })
        this.HienThiDanhSach_Dagiao_NV()
        if (check.data && check.data.data && check.data.data == 'OK') this.$toast.success('Cập nhật dữ liệu lên viễn thông tỉnh thành công!')
        else this.$toast.error(check.data.data ? check.data.data : check.data.message)
        this.$emit('giaoviec_success')
      } catch (err) {
        this.$toast.error('Có lỗi trong quá trình giao việc nhân viên ' + err.message)
        return
      } finally {
        this.loading(false)
      }
    },
    async tsbtnXoaGiao_Clicked() {
      try {
        this.$confirm(`Bạn thật sự muốn xóa tất cả các phiếu giao việc nhân viên không?`, {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'warning'
        }).then(async () => {
          try {
            let count = 0
            for (let i = 0; i < this.vdsphieu.length; i++) {
              let kq = await this.axios.post('/web-thicong/hoinghi_truyenhinh/delete_giaophieu_nv', {
                phieu_id: this.vdsphieu[i].phieu_id
              })
              if (kq.data.error_code == 'BSS-00000000') {
                count += 1
              } else {
                this.$toast.error(`Có lỗi xảy ra khi xoá phiếu có Mã TB: ${this.vdsphieu[i].ma_tb}!`)
                break
              }
            }
            if (count == this.vdsphieu.length) {
              this.$toast.success(`Đã xoá giao việc thành công cho ${count} phiếu!`)
              this.$emit('giaoviec_success')
            }
            this.Hienthi_DS_cangiao()
            this.HienThiDanhSach_Dagiao_NV()
          } catch (e) {
            console.log(e)
            this.$toast.error('Xóa giao việc thất bại')
          }
        })
      } catch (err) {
        this.$toast.error('' + err.message)
      }
    },
    async tsbtnGuiSMS_Clicked() {
      try {
        if (this.$refs.gridDaGiao.length <= 0) {
          this.$toast.error('Chưa có thông tin giao việc. Hãy kiểm tra lại!')
          return
        }
        this.$confirm('Bạn có chắc chắn gửi SMS đến các nhân viên thực hiện nhiệm vụ không?').then(async () => {
          var vnoidung = ''
          var vdsnoidung = []
          var vghichu = ''
          var vnhiemvu = ''
          var ds = []

          for (let i = 0; i < this.vdsphieu.length; i++) {
            var kt = false
            try {
              let phieu = this.vdsphieu[i]
              let kq_lay_ds_nhanvien_theo_phieu_id = await this.axios.post('web-thicong/hoinghi_truyenhinh/lay_ds_nhanvien_theo_phieu_id', {
                phieu_id: phieu.phieu_id,
                nhanvien_id: this.$root.token.getNhanVienID(),
                kieu_id: 2
              })
              let dsNhanVien = kq_lay_ds_nhanvien_theo_phieu_id.data ? kq_lay_ds_nhanvien_theo_phieu_id.data.data : []
              for (let j = 0; j < dsNhanVien.length; j++) {
                let nhanVien = dsNhanVien[j]
                let kq_lay_noidung_sms_giaoviec_v2 = await this.axios.post('web-thicong/hoinghi_truyenhinh/lay_noidung_sms_giaoviec_v2', {
                  vkieu: 1,
                  vhdtb_id: this.vdsphieu[i].hdtb_id,
                  vthuebao_id: 0,
                  vma_tb: this.vdsphieu[i].ma_tb,
                  vnhiemvu: nhanVien.nhiemvu,
                  vghichu: nhanVien.ghichu,
                  vphieu_id: this.vdsphieu[i].phieu_id
                })
                let noiDungTinNhan = kq_lay_noidung_sms_giaoviec_v2.data.data
                if ([7777, 9999].includes(this.vhuonggiao_id) && noiDungTinNhan == null) {
                  throw 'loi'
                }
                await this.axios.post('/tichhop/sms/send', { msisdn: nhanVien.so_dt, content: noiDungTinNhan })
                kt = true
              }
            } catch (e) {
              console.log(e)
            }
            if (kt) {
              await this.axios.post('web-thicong/hoinghi_truyenhinh/fn_capnhat_trangthai_giaoviec_nv', {
                vphieu_id: this.vdsphieu[i].phieu_id,
                vstatus: 1
              })
            }
            this.$toast.success('Đã gửi tin nhắn đến các nhân viên thực hiện!')
            this.HienThiDanhSach_Dagiao_NV()
          }

          return
        })
      } catch (err) {
        this.$toast.error('' + err.message)
      }
    },
    async tsbtnCapNhatKQXL_Clicked() {
      try {
        if (this.$refs.gridDaGiao.dataSource.length <= 0) {
          this.$toast.error('Chưa có nhân viên được giao nhiệm vụ !')
          return
        }
        console.log('tsbtnCapNhatKQXL_Clicked')
        console.log(this.vdsphieu)

        for (let i = 0; i < this.vdsphieu.length; i++) {
          let data = []
          for (let i = 0; i < this.$refs.gridDaGiao.dataSource.length; i++) {
            let row = {}
            let item = this.$refs.gridDaGiao.dataSource[i]
            row.PHIEU_ID = this.vdsphieu.phieu_id
            row.NHANVIEN_TH_ID = item.nhanvien_th_id
            row.SO_DT = item.so_dt
            row.NHIEMVU = item.nhiemvu
            row.GHICHU = item.ghichu
            row.NHANVIEN_GIAO_ID = this.vnhanvien_giao_id
            row.NGAYGIAO = item.ngaygiao
            row.STATUS = item.status
            row.NHIEMVU_ID = item.nhiemvu_id
            row.KQXL_ID = item.kqxl ? (item.kqxl == 'Tốt' ? 1 : 3) : null
            data.push(row)
          }
          await this.axios.post('web-thicong/hoinghi_truyenhinh/capnhat_kqxl_giaoviec', {
            phieu_id: this.vphieu,
            json_giaophieu_nv: data
          })
        }
        this.HienThiDanhSach_Dagiao_NV()
        this.$toast.success('Cập nhật dữ liệu lên viễn thông tỉnh thành công!')
      } catch (err) {
        this.$toast.error('Có lỗi trong quá trình cập nhật Kết quả xử lý ' + err.message)
        return
      }
    },
    delete_1_row() {
      this.Hienthi_DS_cangiao()
      this.HienThiDanhSach_Dagiao_NV()
      this.$emit('giaoviec_success')
    },
    async getDsNhiemVu() {
      let dataNhiemvu = await Vue.axios.post('web-thicong/hoinghi_truyenhinh/lay_ds_nhiemvu_hg_v2', {
        vhuonggiao_id: this.vhuonggiao_id
      })
      this.dsNhiemvu = dataNhiemvu.data.data ? dataNhiemvu.data.data.map((item) => ({ ...item, nhiemvu: item.nhiemvu.replaceAll(',', ';') })) : []
    },

    async frnGiaoPhieuNV_Load() {
      this.getDsNhiemVu()
      let rs = await this.axios.post('web-thicong/hoinghi_truyenhinh/lay_ds_loainv')
      if (this.vkieugiao == 1) this.$refs.chk_LoaiNV.disabled = true
      this.$refs.cbo_LoaiNV.dataSource = rs.data ? rs.data.data : []
      this.$refs.cbo_LoaiNV.dataValueField = 'loainv_id'
      this.$refs.cbo_LoaiNV.dataTextField = 'ten'
      this.$refs.cbo_LoaiNV.value = this.$refs.cbo_LoaiNV.dataSource.length > 0 ? this.$refs.cbo_LoaiNV.dataSource[0].loainv_id : null
      //   let rs = await this.axios.post("web-thicong/hoinghi_truyenhinh/sp_lay_ds_nhanvien_giaoviec_frmgiaoviecnv", {
      //     "vphieu_id": this.vphieu,
      //     "vkieugiao": this.vkieugiao,
      //     "vloainv": this.vLoaiNV_ID,
      //     "vnhanvien_id": this.vnhanvien_giao_id
      //   })
      //   this.ds_nhanvien = rs.data ? rs.data.data : []

      //   if (this.ds_nhanvien.length > 0) {
      //     this.$refs.gridNhanVien.dataSource = this.ds_nhanvien
      //   }

      this.Hienthi_DS_cangiao()
      //   } else {
      //     this.$toast.error("Không có nhân viên nào !");
      //     return;
      //   }

      this.HienThiDanhSach_Dagiao_NV()

      //region Combo Donvi
      let rs_cboDonVi = await this.axios.post('web-ccdv/tien_trinh_bao_hong/sp_lay_donvi_quanly_ldv', { p_loaidv_id: LOAI_DV.DONVIQL_LD, p_donvi_id: 0 })
      this.$refs.cboDonVi.HT_COMBOBOX(rs_cboDonVi.data ? rs_cboDonVi.data.data : [], 'ten_dv', 'donvi_id')
      //endregion

      //region Combo Phòng bán hàng
      let rs_cboPhongBH = await this.axios.post('web-ccdv/tien_trinh_bao_hong/sp_lay_donvi_quanly_ldv', { p_loaidv_id: LOAI_DV.PHONG_BANHANG_KV, p_donvi_id: 0 })
      this.$refs.cboPhongBH.HT_COMBOBOX(rs_cboPhongBH.data ? rs_cboPhongBH.data.data : [], 'ten_dv', 'donvi_id')
      //endregion
      this.kt_load = false
    },
    async Hienthi_DS_cangiao() {
      let nv_id = 0
      if (this.$refs.chkDonViQL.checked == true) {
        nv_id = this.$refs.cboTramTC.value
      }
      if (this.$refs.chkPBH.checked == true) nv_id = this.$refs.cboPhongBH.value

      console.log(nv_id)

      let rs_gridNhanVien

      if (this.$refs.chkDonViQL.checked == false && this.$refs.chkPBH.checked == false && this.$refs.chk_LoaiNV.checked == false) {
        rs_gridNhanVien = await this.axios.post('web-thicong/hoinghi_truyenhinh/sp_lay_ds_nhanvien_giaoviec_frmgiaoviecnv', {
          vphieu_id: this.vphieu,
          vkieugiao: this.vkieugiao,
          vloainv: '-1',
          vnhanvien_id: this.vnhanvien_giao_id
        })
      } else {
        rs_gridNhanVien = await this.axios.post('web-thicong/hoinghi_truyenhinh/hienthi_danhsach_cangiao', {
          chk_donviql: this.$refs.chkDonViQL.checked || this.$refs.chkPBH.checked ? 1 : 0,
          kieugiao: this.vkieugiao,
          phieu_id: this.vphieu,
          donvi_id: nv_id,
          loainv_id: this.$refs.chk_LoaiNV.checked ? this.$refs.cbo_LoaiNV.value : '-1',
          nhanvien_id: this.vnhanvien_giao_id
        })
      }

      let data = rs_gridNhanVien.data ? rs_gridNhanVien.data.data : []
      let revert = {}
      data.forEach((item) => {
        if (revert[item.ma_nv]) {
          revert[item.ma_nv].nhiemvu += ',' + item.nhiemvu
          revert[item.ma_nv].ghichu = revert[item.ma_nv].ghichu
        } else {
          revert[item.ma_nv] = item
        }
        revert[item.ma_nv].dsNhiemvu = this.dsNhiemvu
      })
      this.$refs.gridNhanVien.dataSource = Object.values(revert)
    },
    cbo_LoaiNV_EditValueChanged() {
      this.vLoaiNV_ID = -1
      if (!this.$refs.cbo_LoaiNV.value) return
      this.vLoaiNV_ID = this.$refs.cbo_LoaiNV.value
      this.Hienthi_DS_cangiao()
    },
    async HienThiDanhSach_Dagiao_NV() {
      // hoangpkn : 16/06/2017
      //DataSet ds = new TCHopDongFacade().LAY_DS_NHANVIEN_THEO_PHIEU_ID(vphieu, tt_nd.nhanvien_id, 3);
      let ds = await this.axios.post('web-thicong/hoinghi_truyenhinh/lay_ds_nhanvien_theo_phieu_id', {
        phieu_id: this.vphieu,
        kieugiao: 3,
        nhanvien_id: this.vnhanvien_giao_id
      })

      let data = ds.data ? ds.data.data : []
      data.map((item, key) => {
        item.kqxl = item.kqxl_id ? (item.kqxl_id == 1 ? 'Tốt' : 'Không tốt') : null
        item.stt = key + 1
      })
      this.$refs.gridDaGiao.dataSource = ds.data ? ds.data.data : []
    },
    chkDonViQL_CheckedChanged() {
      if (this.$refs.chkDonViQL.checked) {
        this.$refs.chkDonViQL.checked = true
        this.$refs.cboDonVi.disabled = false
        this.$refs.cboTramTC.disabled = false
        this.$refs.chkPBH.checked = false
        this.$refs.cboPhongBH.disabled = true
        this.Hienthi_DS_cangiao()
      } else {
        this.$refs.chkDonViQL.checked = false
        this.$refs.cboDonVi.disabled = true
        this.$refs.cboTramTC.disabled = true
        if (!this.$refs.chkPBH.checked) {
          this.Hienthi_DS_cangiao()
        }
      }
      this.cboDonVi_EditValueChanged()
    },
    async cboDonVi_EditValueChanged() {
      try {
        this.$refs.cboTramTC.dataSource = []
        let ldv = ''
        if (this.vkieugiao == 1 && this.$root.token.getPhanVungID() == '21') ldv += LOAI_DV.TRAM_VT
        else {
          ldv += LOAI_DV.TRAM_VT
        }

        let dt = await this.axios.post('web-thicong/hoinghi_truyenhinh/ht_donvicon_loaidv_combobox', {
          loaidv_id: ldv,
          donvi_id: this.$refs.cboDonVi.value
        })

        this.$refs.cboTramTC.HT_COMBOBOX(dt.data ? dt.data.data : [], 'ten_dv', 'donvi_id')
        //endregion
      } catch (err) {
        console.log(err)
        this.$toast.error('Có lỗi : ' + err.message)
      }
    },
    cboTramTC_EditValueChanged() {
      if (!this.kt_load) {
        this.Hienthi_DS_cangiao()
      }
    },

    chkPBH_CheckedChanged() {
      if (this.$refs.chkPBH.checked) {
        this.$refs.cboPhongBH.disabled = false
        this.$refs.chkDonViQL.checked = false
        this.$refs.cboDonVi.disabled = true
        this.$refs.cboTramTC.disabled = true
        this.Hienthi_DS_cangiao()
      } else {
        this.$refs.cboPhongBH.disabled = true
        if (!this.$refs.chkDonViQL.checked) {
          if (this.vkieugiao == 0) {
            this.Hienthi_DS_cangiao()
          } else {
            this.$refs.gridNhanVien.dataSource = []
          }
        }
      }
    },
    cboPhongBH_EditValueChanged() {
      this.Hienthi_DS_cangiao()
    },
    chk_LoaiNV_CheckedChanged() {
      this.$refs.cbo_LoaiNV.disabled = !this.$refs.chk_LoaiNV.checked
      this.cbo_LoaiNV_EditValueChanged()
    }
  }
}
</script>
<style>
</style>

<template src="./VanHanhThuongXuyen.html">

</template>
<script>
import DateTimeLib from "../../../../../utils/DateTimeLib";
import moment from "moment";
import VanHanhKM from "../../VanHanhKM"
import TraCuuTBi from "../../TraCuuTBi";
export default {
  name: "VanHanhThuongXuyen",
  components: {VanHanhKM, TraCuuTBi},
  props: {
    id: String
  },
  data: function () {
    return {
      vthietbi_id: 0,
      vvattu_id: 0,
      vdonvi_id: 0,
      vvanhanh_id: 0,
      vphieuvh_id: 0,
      vloaiVH_id: 6,
      vmota: "",

      Kieugoi: 0, // Kieu goi = 1 la tu form khac sang,
      ma_ts: "",
      _vthietbi_id: null,

      txtMaTs: "",
      txtdonviql: null,
      dtpNgayYC: new Date(),
      dtpNgayHT: new Date(),
      txtNoiDung: "",
      txtGhichu: "",
      txtKetQua: "",
      txtTongtien: "",
      txtDeXuat: "",

      gridTiepNhan: [],
      grid_Value: [],
      gridlichsu: [],

      btnTongtien: true,
      btnTiepnhan: false,
      tsbtnLaytt: true,
      btnXoa: true,
      btnCapnhap: true,
      btnhoanthanh: true,
      btnNhapmoi: true,
    }
  },
  methods: {
    onHiddenModal() {
      this.$emit('reload')
    },
    async onShownModal() {
      await this.frmVanHanhThuongXuyen_Load()
    },
    async frmVanHanhThuongXuyen_Load() {
      try {
        await this.load_ds_tiepnhan(1, 0)
        if (this.Kieugoi == 1) {
          this.clear()
          this.txtMaTs = this.ma_ts
          await this.tsbtnLaytt_Click()
          this.setbutton(1)
        }
      } catch (e) {

      }
    },
    async tsbtnLaytt_Click() {
      if (!this.tsbtnLaytt) return
      if (!this.txtMaTs) {
        this.$root.toastError("Mã tài sản không được trống!")
        return
      }
      try {
        this.loading(true)
        await this.load_grid(this.txtMaTs)
        await this.grid_lichsu(0, this.vthietbi_id)
      } catch (e) {
        this.$root.toastError(e)
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    btnNhapmoi_Click() {
      if (!this.btnNhapmoi) return
      this.clear();
      this.setbutton(1);
    },
    btnHuy_Click() {
      if (this.gridTiepNhan.length == 0)
        return
      // this.$refs.gridTiepNhan.setCurrentSelectedRow(0)
      this.setbutton(2)
    },
    btnXoa_Click() {
      if (!this.btnXoa) return
      if (this.vvanhanh_id != 0) {
        this.$bvModal.msgBoxConfirm("Bạn có chắc chắn muốn xóa thiết bị! ",
          {
            title: "Xác nhận hành động",
            centered: true,
            size: "sm",
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy',
          }).then(async (value) => {
          if (value) {
            let vdonvi_nhan_id = 0
            try {
              this.loading(true)
              await this.phutro_trangthai_tiepnhan(3, this.vvanhanh_id, "", this.vloaiVH_id, this.vthietbi_id,
                this.txtNoiDung, moment(this.dtpNgayYC).format('DD/MM/YYYY HH:mm:ss'), this.$auth.getNhanVienID(), this.$auth.getDonViID(), "", "",
                1, this.dtpNgayHT, this.txtGhichu, null, this.$auth.getUserName(), null, null,
                "", "", this.vphieuvh_id, vdonvi_nhan_id)
              this.$root.toastSuccess("Xóa thành công")
              this.clear()
              await this.load_ds_tiepnhan(1, 0)
            } catch (e) {
              if (e.response.data.message_detail)
                this.$root.toastError(e.response.data.message_detail)
              console.log(e)
            } finally {
              this.loading(false)
            }
          }
        })
      } else {
        this.$root.toastError("Không có thiết bị để xóa!")
      }
    },
    async btnCapnhap_Click() {
      if (!this.btnCapnhap) return
      try {
        this.loading(true)
        if (this.btnNhapmoi) {
          if (this.vvanhanh_id == 0) {
            this.$root.toastError("Không có thiết bị để cập nhập!")
            return
          }
          let vdonvi_nhan_id = 0
          // update
          await this.phutro_trangthai_tiepnhan(2, this.vvanhanh_id, "", this.vloaiVH_id, this.vthietbi_id,
          this.txtNoiDung, moment(this.dtpNgayYC).format('DD/MM/YYYY HH:mm:ss'), this.$auth.getNhanVienID(), this.$auth.getDonViID(), "", this.txtKetQua,
          1,DateTimeLib.toDateDisplay(this.dtpNgayHT), this.txtGhichu, null, this.$auth.getUserName(), null, null,
            "", "", this.vphieuvh_id, vdonvi_nhan_id)
          let rs = await this.capnhat_donvinhanid_vanhanh(vdonvi_nhan_id, this.vvanhanh_id)
          // if (rs)
          this.$root.toastSuccess("Cập nhập thiết bị thành công!")
          // else this.$root.toastError("Cập nhật thiết bị thất bại!")
          await this.load_ds_tiepnhan(1,0)
        } else {
          if (this.vthietbi_id == 0) {
            this.$root.toastError("Không có thiết bị!")
            return
          }
          if (!this.txtMaTs) {
            this.$root.toastError("Chưa có mã tài sản!")
            return
          }
          let vdonvi_nhan_id = 0
          this.vvanhanh_id = 0
          // Kiểm tra trùng thiết bị và loại vận hành khi tiếp nhận
          let dt6 = this.gridTiepNhan.filter(i => i.LOAIVH_ID == this.vloaiVH_id && i.THIETBI_ID == this.vthietbi_id)
          if (dt6.length > 0) {
            this.$root.toastError("Thiết bị cập nhật bị trùng!")
            return
          }
          // themmoi
          await this.phutro_trangthai_tiepnhan(1, this.vvanhanh_id, "", this.vloaiVH_id, this.vthietbi_id,
          this.txtNoiDung, moment(this.dtpNgayYC).format('DD/MM/YYYY HH:mm:ss'), this.$auth.getNhanVienID(), this.$auth.getDonViID(), "",this.txtKetQua,
          1, DateTimeLib.toDateDisplay(this.dtpNgayHT), this.txtGhichu, null, this.$auth.getUserName(), null, null,
            "", "", this.vphieuvh_id, vdonvi_nhan_id)
          let rs = await this.capnhat_donvinhanid_vanhanh(vdonvi_nhan_id, this.vvanhanh_id)  // update 0 record because vdonvi_nhan_id = 0, vvanhanh_id = 0
          // if (rs)
          this.$root.toastSuccess("Tiếp nhận thiết bị thành công!")
          // else this.$root.toastError("Tiếp nhận thiết bị thất bại!")
          await this.load_ds_tiepnhan(1,0)
          this.setbutton(2)
        }
      } catch (e) {
        if (e.response.data && e.response.data.message_detail)
          this.$root.toastError(e.response.data.message_detail)
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    async btnhoanthanh_Click() {
      if (!this.btnhoanthanh) return
      if (this.vvanhanh_id == 0) {
        this.$root.toastError("Không có thiết bị để hoàn thành!")
        return
      }
      try {
        this.loading(true)
        let vdonvi_nhan_id = 0
        await this.phutro_trangthai_tiepnhan(2, this.vvanhanh_id, "", this.vloaiVH_id, this.vthietbi_id,
          this.txtNoiDung, moment(this.dtpNgayYC).format('DD/MM/YYYY HH:mm:ss'), this.$auth.getNhanVienID(), this.$auth.getDonViID(), "",this.txtKetQua,
          6, DateTimeLib.toDateDisplay(this.dtpNgayHT), this.txtGhichu, null, this.$auth.getUserName(), null, null,
          "", "", this.vphieuvh_id, vdonvi_nhan_id)
        await this.load_ds_tiepnhan(1,0)
        this.$root.toastSuccess("Cập nhập hoàn thành thiết bị thành công!")
      }catch (e) {
        if (e.response.data && e.response.data.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
        console.log(e)
      }finally {
        this.loading(false)
      }
    },
    async txtMaTs_KeyPress() {
      await this.tsbtnLaytt_Click()
    },
    bntLayMaTbi_Click(){
      try {
        this.clear()
        this.$bvModal.show("TraCuuTBi")
      }catch (e) {

      }
    },
    chapNhanTraCuuTBi: async function (obj){
      if(obj!=null){
        this.vthietbi_id = obj.THIETBI_ID
        this.txtdonviql = obj.TEN_DV
        this.txtMaTs =  obj.MA_TBI
        this.vdonvi_id = obj.DONVI_ID
        this.vvattu_id = obj.VATTU_ID
        try {
          this.loading(true)
          await this.load_thuoctinh(this.vvattu_id, this.vthietbi_id);
        }catch (e) {

        }finally {
          this.loading(false)
        }
      }
    },
    async btnTongtien_Click() {
      try {
        this.txtTongtien = ""
        this.$bvModal.show("VanHanhKM")
        // this.txtTongtien = this.$refs.VanHanhKM.txtThanhtien
        // await this.load_ds_tiepnhan(1, 0)
      } catch (e) {

      }
    },
    chapNhanTongTien: async function(tongtien){
      this.txtTongtien = tongtien
      try {
        this.loading(true)
        await  this.load_ds_tiepnhan(1, 0);
      }catch (e) {

      }finally {
        this.loading(false)
      }
    },
    async load_grid(a) {
      try {
        this.grid_Value = []
        let dt1 = await this.phutro_lay_ds_theo_tbi(a, 1, this.$auth.getNhanVienID())
        if (dt1.length > 0) {
          this.txtdonviql = dt1[0].TEN_DV
          this.vthietbi_id = dt1[0].THIETBI_ID
          this.vdonvi_id = dt1[0].DONVI_ID
          this.vvattu_id = dt1[0].VATTU_ID
          await this.load_thuoctinh(this.vvattu_id, this.vthietbi_id)
        }
      } catch (e) {
        this.$root.toastError(e)
        console.log(e)
      }
    },
    async grid_lichsu(vttvh_id, vthietbi_id) {
      try {
        this.gridlichsu = await this.phutro_lay_ds_tnhan_tbi(vttvh_id, vthietbi_id, this.$auth.getNhanVienID())
      }catch (e) {
        this.$root.toastError("Có lỗi lấy danh sách lịch sử "+ e)
      }
    },
    async load_ds_tiepnhan(vttvh_id, vthietbi_id) { //Truyen 1, 0 lay ra danh sach
      try {
        this.gridTiepNhan = await this.phutro_lay_ds_tnhan_tbi_v3(vttvh_id, vthietbi_id, this.vloaiVH_id, this.$auth.getNhanVienID())
        if (this.gridTiepNhan.length == 0) {
          this.clear()
          this.setbutton(1)
        }
      } catch (e) {

      }
    },
    async load_thuoctinh(vattu_id, vthietbi_id) {
      // let lstThuocTinh = []
      // for (let item of dt) {
      //   let vttt = {}
      //   vttt.vattu_id = item.VATTU_ID
      //   vttt.ThuocTinh = item.THUOCTINH
      //   vttt.Ten_loai = item.TEN_LOAI
      //   vttt.CauLenh = item.CAULENH
      //   vttt.kieutt_id = item.KIEUTT_ID
      //   if (item.GiaTri) {
      //     vttt.GiaTri = item.GIATRI
      //     vttt.GiaTriCu = vttt.GiaTri
      //   } else vttt.GiaTri = null
      //   if (vttt.ThuocTinh == "Địa chỉ") {
      //     vttt.Ten_loai = "Button";
      //   }
      //   if (item.TTVT_ID)
      //     vttt.ttvt_id = item.TTVT_ID
      //   if (item.GTTT_ID)
      //     vttt.gttt_id = item.GTTT_ID
      //   if (vttt.Ten_loai == "Anh")
      //     this.load_img(vttt.GiaTri)
      //   lstThuocTinh.push(vttt)
      // }
      this.grid_Value = await this.LayThongTinThuocTinhTheoVatTuThietBi(vattu_id, vthietbi_id)
    },
    load_img(fileanh) {

    },
    async gridTiepNhan_rowSelected(item) {
      if (item) {
        this.vvanhanh_id = item.VANHANH_ID
        this.vphieuvh_id = item.PHIEUVH_ID
        this.txtGhichu = item.GHICHU
        this.txtNoiDung = item.NOIDUNG
        this.dtpNgayYC = DateTimeLib.toDateTimeDisplay(item.NGAY_YC)
        this.txtdonviql = item.TEN_DV
        this.txtMaTs = item.THIETBI_ID
        this.vthietbi_id = item.THIETBI_ID
        this.vvattu_id = item.VATTU_ID
        this.txtTongtien = item.THANHTIEN
        this.txtKetQua = item.KETQUA
        this.dtpNgayHT = DateTimeLib.toDateDisplay(item.NGAY_HT)
        this.vmota = item.MOTA
        try {
          this.loading(true)
          await this.load_thuoctinh(this.vvattu_id, this.vthietbi_id)
          await this.grid_lichsu(0, this.vthietbi_id)
        }catch (e) {

        }finally {
          this.loading(false)
        }
      }
    },
    grid_Value_queryCellInfo: function (args) {
      switch (args.column.field) {
        case 'THUOCTINH':
          args.cell.style.color = 'black'
          args.cell.style.fontWeight = 'bold'
          break
      }
    },
    clear() {
      this.vthietbi_id = 0;
      this.dtpNgayYC = new Date();
      this.dtpNgayHT = new Date();
      this.txtNoiDung = "";
      // txtDeXuat.Text = "";
      this.txtGhichu = "";
      //txtKetQuaTD.Text = "";
      this.txtKetQua = "";
      this.txtMaTs = "";
      this.txtdonviql = "";
      this.txtTongtien = "";
      // txtKetQuaTD.Text = "";
      this.grid_Value = [];
      this.gridlichsu = [];
    },
    setbutton(a) {
      if (a == 1) {
        this.btnTongtien = false;
        this.btnTiepnhan = true;
        this.tsbtnLaytt = true;
        this.btnXoa = false;
        // btnGiaophieu.Enabled = false;
        this.btnCapnhap = true;
        this.btnhoanthanh = false;
        this.btnNhapmoi = false;
        //btnHuy.Enabled = true;
        // gcDSFile.DataSource = (object)null;
      }
      if (a == 2) {
        this.btnTiepnhan = false;
        this.tsbtnLaytt = true;
        this.btnXoa = true;
        // btnGiaophieu.Enabled = true;
        this.btnCapnhap = true;
        this.btnNhapmoi = true;
        this.btnTongtien = true;
        this.btnhoanthanh = true;
        this.btnNhapmoi = true;
        //btnHuy.Enabled = false;
      }
    },
    /**
     * API---------------------------
     */
    async phutro_lay_ds_tnhan_tbi_v3(vttvh_id, vthietbi_id, vloaivh_id, vnhanvien_id) {
      let rs = await this.$root.context.post("web-ecms/vanhanh-thuongxuyen/phutro-lay-ds-tiepnhan-tbi-v3", {
        ttvh_id: vttvh_id,
        thietbi_id: vthietbi_id,
        nhanvien_id: vnhanvien_id,
        loaivh_id: vloaivh_id
      })
      return rs.data
    },
    async phutro_lay_ds_tnhan_tbi(vttvh_id, vthietbi_id, vnhanvien_id) {
      let rs = await this.$root.context.post("web-ecms/vanhanh-thuongxuyen/phutro-lay-ds-tiepnhan-tbi", {
        ttvh_id: vttvh_id,
        thietbi_id: vthietbi_id,
        nhanvien_id: vnhanvien_id,
      })
      return rs.data
    },
    async phutro_lay_ds_theo_tbi(vma_vt, vkieu, vnhanvien_id) {
      let rs = await this.$root.context.get("web-ecms/vanhanh-thuongxuyen/phutro-lay-ds-theo-tbi", {
        ma_vt: vma_vt,
        kieu: vkieu,
        nhanvien_id: vnhanvien_id
      })
      return rs.data
    },
    LayThongTinThuocTinhTheoVatTuThietBi: async function (vattu_id, vthietbi_id) {
      let rs = await this.$root.context.get(
        "/web-ecms/quanlythietbiphutro/Lay_ThongTin_ThuocTinh_Theo_VatTu_ThietBi", {
          vattu_id: vattu_id,
          thietbi_id: vthietbi_id,
        })
      return rs.data
    },
    async phutro_trangthai_tiepnhan(vkieu, vvanhanh_id, vma_vh, vloaivh_id, vthietbi_id, vnoidung, vngay_yc, vnhanvien_id,
                                    vdonvi_id, vketqua_td, vketqua, vttvh_id, vngay_ht, vghichu, vngay_cn, vnguoi_cn,
                                    vmay_cn, vip_cn, vdexuat, vmota, vphieuvh_id, vdonvi_nhan_id) {
      await this.$root.context.post("web-ecms/vanhanh-thuongxuyen/phutro-trangthai-tiepnhan", {
        kieu: vkieu,
        vanhanh_id: vvanhanh_id,
        ma_vh: vma_vh,
        loaivh_id: vloaivh_id,
        thietbi_id: vthietbi_id,
        noidung: vnoidung,
        ngay_yc: vngay_yc,
        nhanvien_id: vnhanvien_id,
        donvi_id: vdonvi_id,
        ketqua_td: vketqua_td,
        ketqua: vketqua,
        ttvh_id: vttvh_id,
        ngay_ht: vngay_ht,
        ghichu: vghichu,
        ngay_cn: vngay_cn,
        nguoi_cn: vnguoi_cn,
        may_cn: vmay_cn,
        ip_cn: vip_cn,
        dexuat: vdexuat,
        mota: vmota,
        phieuvh_id: vphieuvh_id,
        donvi_nhan_id: vdonvi_nhan_id,
      })
    },
    async capnhat_donvinhanid_vanhanh(donvi_nhan_id, vanhanh_id) {
      let rs = await this.$root.context.post(`web-ecms/vanhanh-thuongxuyen/capnhat-donvinhanid-vanhanh?donvi_nhan_id=${donvi_nhan_id}&vanhanh_id=${vanhanh_id}`)
      return rs.data.result
    },
  }
}
</script>

<style>
@media (min-width: 1200px){
  .modal-xl {
    max-width: 90% !important;
  }
}
.disable-btn:hover {
  background-color: white !important;
}

.disable-btn a {
  color: #6c757d !important;
}

.disable-btn a:hover {
  cursor: default;
  color: #6c757d;
}
</style>

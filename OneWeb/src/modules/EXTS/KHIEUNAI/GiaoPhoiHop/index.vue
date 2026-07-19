<template src="./index.html"></template>
<script>
import { required } from "vuelidate/lib/validators";
import DateTimeLib from "../../../../utils/DateTimeLib";
export default {
  name: "GiaoPhieuXacMinhPhoiHop",
  
  data: function () {
    return {
      dsHuongGiao: [],
      dsDonViCanGiao: [],
      dsDonViDaGiao: [],
      currentItem: {
        HUONGGIAO_ID: 0,
        PHIEUKN_ID: 0,
      },
      txtNoiDung: "",
      txtCongVan: "",
      huongGiaoID: 0,
      ttkn_id: 3,
      nhanTinGiaoPhieuXLKN: false,
    };
  },
  validations: {
    txtNoiDung: {
      required,
    },
  },
  computed: {},
  created: async function () {},
  mounted: async function () {},
  
  methods: {    
    getDsPhieuPHKHDN: async function () {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/layDsPhieuPHKHDN",
        {
          phieu_id: this.currentItem.PHIEUKN_ID,
          tentat: this.$root.user.getProperty("tentat_ccbs"),
        }
      );
      return rs.data;
    },
    getTenDonVi: async function (donvi_id) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getTenDonVi",
        {
          id: donvi_id,
        }
      );
      return rs.data;
    },
    getTTNetMedia: async function () {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/layTTNetMedia",
        {
          id: this.$root.token.getPhanVungID(),
        }
      );
      return rs.data;
    },
    getTTDichVuLoaiTB: async function () {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/layTTDichVuLoaiTB",
        {
          ma_tb: this.currentItem.MA_TB,
        }
      );
      return rs.data;
    },
    getTrangThaiPhieu: async function (phieu_id) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getTrangThaiPhieu",
        {
          id: phieu_id,
        }
      );
      return rs.data;
    },
    extractOracleMessage(error) {
      if (!error || typeof error !== "string") return "";

      // tìm dấu ':' đầu tiên
      let idx = error.indexOf(":");
      if (idx === -1) return error.trim();

      // cắt từ sau dấu ':' đến trước xuống dòng
      let afterColon = error.substring(idx + 1).trim();
      let newlineIdx = afterColon.indexOf("\n");
      if (newlineIdx !== -1) {
        return afterColon.substring(0, newlineIdx).trim();
      }
      return afterColon;
    },
    xoaPhieu: async function (phieu_id) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/xoaPhieu",
        {
          id: phieu_id,
        }
      );
      
        return rs.data;
      
    },
    giaoViecNVPHKN: async function (phieu_id) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/giaoViecNVPHKN",
        {
          khieunai_id: this.currentItem.KHIEUNAI_ID,
          phieu_id: phieu_id,
          nhanvien_id: this.$root.token.getNhanVienID(),
          noidung: this.txtNoiDung,
        }
      );
      return rs.data;
    },
    xuLyThongBaoDonVi: async function (donvi_id) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/xuLyThongBaoDonVi",
        {
          khieuNaiID: this.currentItem.KHIEUNAI_ID,
          donViID: donvi_id,
          huongGiaoID: this.huongGiaoID
        }
      );
      return rs.data;
    },
    // guiSms: async function (so_dt, noidung) {
    //   let rs = await this.$root.context.post(
    //     "/web-cabman/traphieukhieunai/off-send-sms",
    //     {
    //       msisdn: so_dt,
    //       content: noidung,
    //       mt_type: "reply",
    //     }
    //   );
    //   return rs.data;
    // },
    // getNoiDungTinNhanTheoDonVi: async function (donvi_id) {
    //   let rs = await this.$root.context.post(
    //     "/web-gqkn/xu-ly-khieu-nai/getNoiDungTinNhanTheoDonVi",
    //     {
    //       khieunai_id: this.currentItem.KHIEUNAI_ID,
    //       donvi_id: donvi_id,
    //     }
    //   );
    //   return rs.data.NOIDUNG;
    // },
    // getSoDienThoaiNhanTin: async function (donvi_id) {
    //   let rs = await this.$root.context.post(
    //     "/web-gqkn/xu-ly-khieu-nai/getSoDienThoaiNhanTin",
    //     {
    //       donvi_id: donvi_id,
    //       huonggiao_id: this.huongGiaoID,
    //     }
    //   );
    //   return rs.data.so_dt;
    // },
    getDsDonViTheoHuongGiaoID: async function () {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/layDsDonViTheoHuongGiaoID",
        {
          id: this.huongGiaoID,
        }
      );
      return rs.data;
    },
    getDSDonViChuaGiaoPhoiHop: async function (pKHIEUNAIID) {
      let rs = await this.$root.context.post('/web-gqkn//khieunai/lay-danhsach-donvi-phoihop',{khieuNaiId:pKHIEUNAIID})
      return rs.data
    },
    getDsPhieuDaGiaoTheoPhieuID: async function () {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/layDsPhieuDaGiaoTheoPhieuID",
        {
          id: this.currentItem.PHIEUKN_ID,
        }
      );
      return rs.data;
    },
    getDsHuongGiao: async function () {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getDsHuongGiao",
        {
          quytrinh_id: this.currentItem.QUYTRINH_ID,
          ttkn_id: this.ttkn_id,
        }
      );
      return rs.data;
    },
    capNhatChiTieuPhieuKN: async function (dsPhieuKNs) {
      let result = false;
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/capNhatChiTieuPhieuKN",
        {
          dsPhieuKNs: dsPhieuKNs,
        }
      );
      if (!(rs.data == null || rs.data == undefined)) {
        result = rs.data.Result;
      }
      return result;
    },
    capNhatGiaoPhieuPH: async function (dsPhieuKNs, vkieu) {
      let rs = await this.$root.context.post(
        "/web-bancheo/tracuu/qltn_capnhat_giaophieu_ph",
        {
          tinhthicong_id: 100,
          vdstt_khdn: dsPhieuKNs,
          vkieu: vkieu,
        }
      );
      return rs.data;
    },
    kiemTraGiaoTrungDonVi: async function (donvi_id) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/kiemTraGiaoTrungDonVi",
        {
          phieukn_id: this.currentItem.PHIEUKN_ID,
          donvi_id: donvi_id,
        }
      );
      return rs.data.Result === true;
    },
    giaoPhieuXacMinh: async function (dsDonVi) {
      let ds = [];
      for (let i = 0; i < dsDonVi.length; i++) {
        ds.push(dsDonVi[i].DONVI_ID);
      }
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/giaoPhieuXacMinh",
        {
          khieunai_id: this.currentItem.KHIEUNAI_ID,
          nd_giao: this.txtNoiDung,
          phieu_cha_id: this.currentItem.PHIEUKN_ID,
          huonggiao_id: this.huongGiaoID,
          ttkn_id: this.ttkn_id,
          socongvan: this.txtCongVan,
          ds_donvi_nhan_id: ds,
        }
      );
      return rs.data;
    },
    // getDsPhieu: function (dsDonVi) {
    //   let dsPhieu = this.createDsPhieuKhieuNai(dsDonVi);
    //   return {
    //     huonggiao_id: this.huongGiaoID,
    //     ngaygiao: DateTimeLib.toDateTimeDisplay(new Date()),
    //     nd_giao: this.txtNoiDung,
    //     socongvan: this.txtCongVan,
    //     ds_phieu: JSON.stringify(dsPhieu),
    //   };
    // },
    // createDsPhieuKhieuNai: function (dsDonVi) {
    //   let result = null;
    //   if (dsDonVi.length > 0) {
    //     result = [];
    //     for (let i = 0; i < dsDonVi.length; i++) {
    //       const item = dsDonVi[i];
    //       result.push(this.createPhieu(item.DONVI_ID));
    //     }
    //   }
    //   return result;
    // },
    // createPhieu: function (donvi_nhan_id) {
    //   return {
    //     KHIEUNAI_ID: this.currentItem.KHIEUNAI_ID,
    //     DONVI_NHAN_ID: donvi_nhan_id,
    //     PHIEU_CHA_ID: this.currentItem.PHIEUKN_ID,
    //     TTKN_ID: this.ttkn_id,
    //   };
    // },
    checkSms: function () {
      let rs = false;
      if (this.dsHuongGiao.length > 0) {
        let item = this.dsHuongGiao.find(
          (x) => x.HUONGGIAO_ID == this.huongGiaoID && x.SMS == 1
        );
        rs = !(item == null || item == undefined);
      }
      return rs;
    },
    checkGiaoViec: function () {
      let rs = false;
      if (this.dsHuongGiao.length > 0) {
        let item = this.dsHuongGiao.find(
          (x) => x.HUONGGIAO_ID == this.huongGiaoID && x.GIAOVIEC == 1
        );
        rs = !(item == null || item == undefined);
      }
      return rs;
    },
    bindData: async function (phieuKhieuNai, nhanTinGiaoPhieuXLKN) {
      this.currentItem = phieuKhieuNai;
      this.nhanTinGiaoPhieuXLKN = nhanTinGiaoPhieuXLKN;
      await this.loadData();
    },
    loadData: async function () {
      await this.bindDonViDaGiao();
      this.dsHuongGiao = await this.getDsHuongGiao();
      if (this.dsHuongGiao.length > 0) {
        this.huongGiaoID = this.dsHuongGiao[0].HUONGGIAO_ID;
      }
      await this.bindDonViCanGiao();
    },
    bindDonViCanGiao: async function () {
      this.dsDonViCanGiao = await this.getDSDonViChuaGiaoPhoiHop(this.currentItem.KHIEUNAI_ID);
    },
    bindDonViDaGiao: async function () {
      this.dsDonViDaGiao = await this.getDsPhieuDaGiaoTheoPhieuID();
    },
    
    comboboxHuongGiao_Change: async function (args) {
      this.$root.showLoading(true);
      try {
        await this.bindDonViCanGiao();
      } finally {
        this.$root.showLoading(false);
      }
    },
    btnGiaoPhieu_Click: async function (args) {
      this.$root.showLoading(true);
      try {
        this.$v.$touch();
        if (this.$v.$invalid) {
          if (this.$v.txtNoiDung.$invalid) {
            this.$root.toastError("Hãy nhập nội dung giao");
          }
        } else {
          let items = this.$refs.gridDonViCanGiao.getSelectedRecords();
          if (items.length > 0) {
            let ck = true;
            for (let i = 0; i < items.length; i++) {
              const item = items[i];
              let kt = await this.kiemTraGiaoTrungDonVi(item.DONVI_ID);
              if (kt) {
                this.$root.toastError(
                  "Trùng đơn vị đã được giao phiếu rồi. Đơn vị " + item.TEN_DV
                );
                ck = false;
                break;
              }
            }
            if (ck) {
              let c = await this.giaoPhieuXacMinh(items);
              if (!this.checkEmpty(c)) {
                if (this.nhanTinGiaoPhieuXLKN) {
                  for (let i = 0; i < items.length; i++) {
                    await this.xuLyThongBaoDonVi(items[i].DONVI_ID);
                    // const item = items[i];
                    // let sms_nd = await this.getNoiDungTinNhanTheoDonVi(
                    //   item.DONVI_ID
                    // );
                    // if (!this.checkEmpty(sms_nd)) {
                    //   let sms_sodt = await this.getSoDienThoaiNhanTin(
                    //     item.DONVI_ID
                    //   );
                    //   if (!this.checkEmpty(sms_sodt)) {
                    //     await this.guiSms(sms_sodt, sms_nd);
                    //   }
                    // }
                  }
                }
                if (this.checkGiaoViec()) {
                  if (c.length > 0) {
                    await this.giaoViecNVPHKN(c[0].PHIEU_GIAO);
                    // for (let i = 0; i < c.length; i++) {
                    //   await this.giaoViecNVPHKN(c[i].PHIEU_GIAO);
                    // }
                  }
                }
                this.$root.toastSuccess("Đã giao phiếu thành công");
              }
              await this.bindDonViDaGiao();
              await this.bindDonViCanGiao()
                this.$refs.gridDonViDaGiao.refresh()
                this.$refs.gridDonViCanGiao.refresh()
            }
          } else {
            this.$root.toastError("Chưa chọn đơn vị nhận");
          }
        }
      } finally {
        this.$root.showLoading(false);
      }
    },
    checkEmpty: function (value) {
      return (
        value == null || value == undefined || value.toString().trim() == ""
      );
    },
    gridDsDonViDaGiao_onCommandClicked: async function (cmdName, args) {
      if (cmdName == "DELETE") {
        let ck = await this.getTrangThaiPhieu(args.PHIEUKN_ID);
        if (ck.TTPH_ID == 1) {
          this.$bvModal
            .msgBoxConfirm("Bạn chắc chắn muốn xóa phiếu đã giao ?", {
              size: "sm",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
              centered: true,
            })
            .then(async (v) => {
              let rs = await this.xoaPhieu(args.PHIEUKN_ID);
              if (rs.Result) this.$root.toastSuccess("Đã xóa phiếu thành công");
              await this.bindDonViDaGiao();
            });
        } else {
          this.$root.toastError(
            "Bạn không được phép xóa phiếu đang ở trạng thái khác MỚI"
          );
        }
      }
    },
    btnChuyenNET_Click: async function (args) {
      await this.chuyenMediaNet(1);
    },
    btnChuyenMedia_Click: async function (args) {
      await this.chuyenMediaNet(2);
    },
    chuyenMediaNet: async function (args) {
      //args: 1: Net; 2: Media; 3: Vip
      let tt_tb = await this.getTTDichVuLoaiTB();
      let msg = "";
      if (args == 1 && tt_tb.DICHVUVT_ID == "4" && tt_tb.LOAITB_ID == "61") {
        msg = "Loại hình MyTV chỉ giao Media";
      }

      if (args == 2 && tt_tb.LOAITB_ID != "61") {
        if (msg == "") msg = "Loại hình này chỉ giao NET";
      }

      if (this.txtNoiDung == "") {
        if (msg == "") msg = "Hãy nhập nội dung giao";
      }
      let items = this.dsDonViCanGiao;
      if (this.currentItem.PHIEUKN_ID == 0 || items.length <= 0) {
        if (msg == "") msg = "Không có phiếu để giao!";
      }
      if (msg == "") {
        this.$bvModal
          .msgBoxConfirm("Bạn chắc chắn muốn chuyển phiếu?", {
            title: "Thông báo",
            size: "sm",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
            centered: true,
          })
          .then(async (v) => {
            await this.chuyenPhieuMediaNet(args);
          });
      } else {
        this.$root.toastError(msg);
      }
    },
    chuyenPhieuMediaNet: async function (args) {
      this.$root.showLoading(true);
      try {
        let donVi = await this.getTTNetMedia();
        let donViNhanID = args == 1 ? donVi.NET_TINH_ID : donVi.MEDIA_ID;
        let kt = await this.kiemTraGiaoTrungDonVi(donViNhanID);
        if (!kt) {
          let items = [];
          items.push({ DONVI_ID: donViNhanID });
          let c = await this.giaoPhieuXacMinh(items);
          if (!this.checkEmpty(c)) {
            let p = [];
            for (let i = 0; i < c.length; i++) {
              p.push({ PHIEUKN_ID: c[i].PHIEU_GIAO });
            }
            await this.capNhatChiTieuPhieuKN(JSON.stringify(p));
            this.$root.toastSuccess(
              "Cập nhật dữ liệu lên viễn thông tỉnh thành công!"
            );
          }
          let dsTT_KHDN = await this.getDsPhieuPHKHDN();
          if (!(dsTT_KHDN == null || dsTT_KHDN.length == 0)) {
            try {
              let d = await this.capNhatGiaoPhieuPH(JSON.stringify(dsTT_KHDN), args);
              //this.$root.toastError("Đang hỏi BA thêm về việc đẩy sang DB KHDN");
            }
            catch(e){
              this.$root.toastError("Lỗi đẩy dữ liệu KHDN!");
            }
          }
          await this.loadData();
        } else {
          let ten = await this.getTenDonVi(donViNhanID);
          this.$root.toastError(
            "Trùng đơn vị đã được giao phiếu rồi. Đơn vị " + ten.TEN_DV
          );
        }
      } finally {
        this.$root.showLoading(false);
      }
    },
    itemSelectedChangedChuaGiao(){},
  },
};
</script>

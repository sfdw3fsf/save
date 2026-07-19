<template src="./Bank.html"></template>

<script>
import gridview from "@/components/Shared/gridview";
import DatabaseConstants from "@/const/enums/DBConstants";
import API from "./API";
import { TRANGTHAI_DONGBO } from "@/constants.js";
export default {
  components: { gridview },
  name: "Bankmodal",
  props: {
    msg: String,
  },

  mounted() {
    API.ds_loai_nh(this.axios, null).then((response) => {
      if (
        response &&
        response.data &&
        response.data.data &&
        response.data.error &&
        response.data.error_code === "BSS-00000000"
      ) {
        this.listbox.ds_loai_nh.list = response.data.data;
      }
    });

    API.layds_nganhang(this.axios, null).then((response) => {
      if (
        response &&
        response.data &&
        response.data.data &&
        response.data.error &&
        response.data.error_code === "BSS-00000000"
      ) {
        this.listbox.ds_nganhang.list = response.data.data.map((x) => {
          return { id: x["nganhang_id"], text: x["ten_nh"] };
        });
      }
    });

    this.load_dulieu();

    this.$refs.txt_manganhang.focus();
  },

  data() {
    return {
      tsbtnNhapMoi: { Enabled: false },
      tsbtnGhiLai: { Enabled: false },
      tsbtnXoa: { Enabled: false },
      tsbtnHuyBo: { Enabled: false },
      current_manh: "",
      current_tennh: "",

      nganhang: {
        DIACHI_NH: "",
        GHICHU: "",
        LOAI_NH: 0,
        MA_NH: "",
        MST: "",
        NGANHANG_ID: 0,
        NGANHANG_ID_VT: 0,
        SO_DVQHNS: "",
        STK: "",
        STK_VT: "",
        TEN_NH: "",
        TINH_ID: 26,
      },
      listbox: {
        ds_loai_nh: {
          list: [],
          value: "",
        },
        ds_nganhang: {
          list: [],
          value: "",
        },
        dulieu_nganhang: {
          list: [],
          header: [
            { fieldName: "ten_nh", headerText: "Tên ngân hàng" },
            { fieldName: "ma_nh", headerText: "Mã ngân hàng" },
            { fieldName: "diachi_nh", headerText: "Địa chỉ" },
            { fieldName: "loainh", headerText: "Loại" },
            { fieldName: "stk_vt", headerText: "Số tài khoản VT" },
            { fieldName: "so_dvqhns", headerText: "Số DVQHNS" },
          ],
          value: "",
        },
        ID_NGANHANG_NEO: 0
      },
    };
  },

  methods: {
    grdChanged(obj) {
      //this.nganhang = obj[0];
      this.nganhang = {
        DIACHI_NH: obj[0].diachi_nh,
        GHICHU: obj[0].ghichu,
        LOAI_NH: obj[0].loai_nh,
        MA_NH: obj[0].ma_nh,
        MST: obj[0].mst,
        NGANHANG_ID: obj[0].nganhang_id,
        NGANHANG_ID_VT: obj[0].nganhang_id_vt,
        SO_DVQHNS: obj[0].so_dvqhns,
        STK: obj[0].stk,
        STK_VT: obj[0].stk_vt,
        TEN_NH: obj[0].ten_nh,
        TINH_ID: 26,
      };
      this.current_manh = obj[0].ma_nh;
      this.current_tennh = obj[0].ten_nh;

      this.listbox.ds_loai_nh.value = obj[0].loai_nh;
      this.listbox.ds_nganhang.value = obj[0].nganhang_id_vt;
      this.SetButton(3);
    },

    SetButton(kieu) {
      this.tsbtnNhapMoi.Enabled = false;
      this.tsbtnGhiLai.Enabled = false;
      this.tsbtnXoa.Enabled = false;
      this.tsbtnHuyBo.Enabled = false;
      if (kieu == -1) {
        //Bat dau
        this.tsbtnGhiLai.Enabled = true;
        this.tsbtnHuyBo.Enabled = true;
      }
      if (kieu == 0) {
        //Bat dau
        //txtMaNganHang.Focus();
        this.tsbtnNhapMoi.Enabled = true;
        //Clear();
      }
      if (kieu == 1) {
        //Them moi
        //txtMaNganHang.Focus();
        this.tsbtnGhiLai.Enabled = true;
        this.tsbtnHuyBo.Enabled = true;
        //Clear();
      }
      if (kieu == 2) {
        //Huy
        this.tsbtnNhapMoi.Enabled = true;
        this.tsbtnXoa.Enabled = true;
        //Clear();
      }
      if (kieu == 3) {
        //Edit
        this.tsbtnNhapMoi.Enabled = true;
        this.tsbtnXoa.Enabled = true;
        this.tsbtnGhiLai.Enabled = true;
        this.tsbtnHuyBo.Enabled = true;
      }
    },

    nhapmoi() {
      this.nganhang = {
        DIACHI_NH: "",
        GHICHU: "",
        LOAI_NH: 0,
        MA_NH: "",
        MST: "",
        NGANHANG_ID: 0,
        NGANHANG_ID_VT: 0,
        SO_DVQHNS: "",
        STK: "",
        STK_VT: "",
        TEN_NH: "",
        TINH_ID: this.$root.token.getProperty("id_tinhthanh")
      };
      this.current_manh = "";
      this.current_tennh = "";
      this.SetButton(1);
      this.$refs.txt_manganhang.focus();
      //console.log(this.nganhang);
    },

    luulai: async function () {
      //check ma ngan hang
      if (
        this.nganhang.MA_NH == "" ||
        this.nganhang.MA_NH == null ||
        this.nganhang.MA_NH == undefined
      ) {
        this.$toast.error("Mã ngân hàng không được để trống !");
        this.$refs.txt_manganhang.focus();
        return;
      }

      if (this.nganhang.MA_NH.length > 50) {
        this.$toast.error("Mã ngân hàng quá số ký tự cho phép !");
        this.$refs.txt_manganhang.focus();
        return;
      }

      //check ten ngan hang
      if (
        this.nganhang.TEN_NH == "" ||
        this.nganhang.TEN_NH == null ||
        this.nganhang.TEN_NH == undefined
      ) {
        this.$toast.error("Tên ngân hàng không được để trống !");
        this.$refs.txt_tennganhang.focus();
        return;
      }

      if (this.nganhang.TEN_NH.length > 200) {
        this.$toast.error("Tên ngân hàng quá số ký tự cho phép !");
        this.$refs.txt_tennganhang.focus();
        return;
      }

      if (this.tsbtnNhapMoi.Enabled == false) {
        //  var data={
        //     "command":"select MA_NH from " + DatabaseConstants.DB2 + ".nganhang where MA_NH = '" + this.nganhang.MA_NH + "' and tinh_id = " + this.$root.token.getPhanVungID()
        //   };
        var data = {
          p_ma_nh: this.nganhang.MA_NH,
          p_tinh_id: this.$root.token.getPhanVungID(),
        };

        var check_unique = 1;

        await API.sp_lay_ma_nh(this.axios, data).then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error &&
            response.data.error_code === "BSS-00000000"
          ) {
            check_unique = 0;
          }
        });
        if (check_unique == 0) {
          this.$toast.error("Đã tồn tại mã ngân hàng này!");
          this.$refs.txt_manganhang.focus();
          return;
        }

        //  data={
        //     "command":"select ten_nh from " + DatabaseConstants.DB2 + ".nganhang where ten_nh = '" + this.nganhang.TEN_NH + "' and tinh_id = " + this.$root.token.getPhanVungID()
        //       };
        data = {
          p_ten_nh: this.nganhang.TEN_NH,
          p_tinh_id: this.$root.token.getPhanVungID(),
        };

        check_unique = 1;

        await API.sp_lay_ten_nh(this.axios, data).then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error &&
            response.data.error_code === "BSS-00000000"
          ) {
            check_unique = 0;
          }
        });
        if (check_unique == 0) {
          this.$toast.error("Đã tồn tại tên ngân hàng này!");
          this.$refs.txt_tennganhang.focus();
          return;
        }
      } else {
        // var data={
        //     "command":"select MA_NH from " + DatabaseConstants.DB2 + ".nganhang where MA_NH = '" + this.nganhang.MA_NH + "' and tinh_id = " + this.$root.token.getPhanVungID() + ' and nganhang_id <>' + this.nganhang.NGANHANG_ID
        //   };

        var data = {
          p_ma_nh: this.nganhang.MA_NH,
          p_tinh_id: this.$root.token.getPhanVungID(),
          p_nganhang_id: this.nganhang.NGANHANG_ID,
        };

        var check_unique = 1;

        await API.sp_lay_ma_nh_v2(this.axios, data).then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error &&
            response.data.error_code === "BSS-00000000"
          ) {
            check_unique = 0;
          }
        });
        if (check_unique == 0) {
          this.$toast.error("Đã tồn tại mã ngân hàng này!");
          this.$refs.txt_manganhang.focus();
          return;
        }

        // data={
        //   "command":"select ten_nh from " + DatabaseConstants.DB2 + ".nganhang where ten_nh = '" + this.nganhang.TEN_NH + "' and tinh_id = " + this.$root.token.getPhanVungID() + ' and nganhang_id <>' + this.nganhang.NGANHANG_ID
        //     };

        data = {
          p_ten_nh: this.nganhang.TEN_NH,
          p_tinh_id: this.$root.token.getPhanVungID(),
          p_nganhang_id: this.nganhang.NGANHANG_ID,
        };

        check_unique = 1;

        await API.sp_lay_ten_nh_v2(this.axios, data).then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error &&
            response.data.error_code === "BSS-00000000"
          ) {
            check_unique = 0;
          }
        });
        if (check_unique == 0) {
          this.$toast.error("Đã tồn tại tên ngân hàng này!");
          this.$refs.txt_tennganhang.focus();
          return;
        }
      }

      //check loai ngan hang
      if (
        this.listbox.ds_loai_nh.value == "" ||
        this.listbox.ds_loai_nh.value == null ||
        this.listbox.ds_loai_nh.value == undefined
      ) {
        this.$toast.error("Loại ngân hàng không được để trống !");
        return;
      }

      //check loai ngan hang  VT
      if (
        this.listbox.ds_nganhang.value == "" ||
        this.listbox.ds_nganhang.value == null ||
        this.listbox.ds_nganhang.value == undefined
      ) {
        this.$toast.error("Ngân hàng Viễn Thông không được để trống !");
        return;
      }

      //check dia chi
      if (
        this.nganhang.DIACHI_NH == "" ||
        this.nganhang.DIACHI_NH == null ||
        this.nganhang.DIACHI_NH == undefined
      ) {
        this.$toast.error("Địa chỉ ngân hàng không được để trống !");
        this.$refs.txt_diachinganhang.focus();
        return;
      }

      if (this.nganhang.DIACHI_NH.length > 100) {
        this.$toast.error("Địa chỉ ngân hàng quá số ký tự cho phép !");
        this.$refs.txt_diachinganhang.focus();
        return;
      }

      //check length
      if (this.nganhang.MST != null && this.nganhang.MST.length > 50) {
        this.$toast.error("Mã số thuế quá số ký tự cho phép !");
        this.$refs.txt_mst.focus();
        return;
      }

      if (this.nganhang.STK != null && this.nganhang.STK.length > 50) {
        this.$toast.error("Số tài khoản quá số ký tự cho phép !");
        this.$refs.txt_stk.focus();
        return;
      }

      if (this.nganhang.STK_VT != null && this.nganhang.STK_VT.length > 100) {
        this.$toast.error("Số tài khoản VT quá số ký tự cho phép !");
        this.$refs.txt_stk_vt.focus();
        return;
      }

      if (
        this.nganhang.SO_DVQHNS != null &&
        this.nganhang.SO_DVQHNS.length > 50
      ) {
        this.$toast.error("Số DVQHNS quá số ký tự cho phép !");
        this.$refs.txt_dvqhns.focus();
        return;
      }

      if (this.nganhang.GHICHU != null && this.nganhang.GHICHU.length > 100) {
        this.$toast.error("Ghi chú quá số ký tự cho phép !");
        this.$refs.txt_ghichu.focus();
        return;
      }
      
      var dulieu = [];
      this.nganhang.NGANHANG_ID_VT = this.listbox.ds_nganhang.value;
      this.nganhang.LOAI_NH = this.listbox.ds_loai_nh.value;
      dulieu.push(this.nganhang);
      //them moi
      if (this.nganhang.NGANHANG_ID == null || this.nganhang.NGANHANG_ID == 0) {
        if (TRANGTHAI_DONGBO.OPEN_SYNC_VINA_TRASAU) {
         await API.ts_dm_nganhang_capnhat(this.axios, {
            nganhangid: "",
            ten_viettat: this.nganhang.MA_NH,
            ten_nganhang: this.nganhang.TEN_NH,
            dia_chi: this.nganhang.DIACHI_NH,
            taikhoan: this.nganhang.STK,
            dien_thoai: "",
            fax: "",
            nguoi_lh: ""
          }).then((response) => {
            if (response.data.error_code === "BSS-00000000") {
              this.$toast.success(
                "Thêm mới ngân hàng trên VinaPhone thành công !"
              );
              this.ID_NGANHANG_NEO = response.data.message_detail;
            } else {
              this.$toast.error(response.data.message);
            }
          });
        }
        data = {
          dulieu: dulieu,
          id_nganhang_neo: this.ID_NGANHANG_NEO,
          isdongbo_vina_trasau: Number(TRANGTHAI_DONGBO.OPEN_SYNC_VINA_TRASAU),
          isthemmoi: 1,
        };
        //  console.log ("them moi");
        //  console.log (data);     
      }
      //cap nhat
      else {
        var data = {
          dulieu: dulieu,
          id_nganhang_neo: 0,
          isdongbo_vina_trasau: Number(TRANGTHAI_DONGBO.OPEN_SYNC_VINA_TRASAU),
          isthemmoi: 0,
        };
        // console.log ("cap nhat");
        // console.log (data);
        if (TRANGTHAI_DONGBO.OPEN_SYNC_VINA_TRASAU) {
          API.ts_dm_nganhang_capnhat(this.axios, {
            nganhangid: this.nganhang.NGANHANG_ID,
            ten_viettat: this.nganhang.MA_NH,
            ten_nganhang: this.nganhang.TEN_NH,
            dia_chi: this.nganhang.DIACHI_NH,
            taikhoan: this.nganhang.STK,
            dien_thoai: "",
            fax: "",
            nguoi_lh: ""
          }).then((response) => {
            if (response.data.error_code === "BSS-00000000") {
              this.$toast.success(
                "Cập nhật ngân hàng trên VinaPhone thành công !"
              );
            } else {
              this.$toast.error(response.data.message);
            }
          });
        }
      }
      this.loading(true);
      API.capnhat_nganhang(this.axios, data)
        .then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error &&
            response.data.error_code === "BSS-00000000"
          ) {
            //neu cap nhat thanh cong thi load lai danh sach ngan hang
            this.load_dulieu();
            if (this.tsbtnNhapMoi.Enabled == false) {
              this.$toast.success(
                "Thêm mới ngân hàng vào cơ sở dữ liệu tỉnh thành công !"
              );
            } else {
              this.$toast.success(
                "Cập nhật ngân hàng vào cơ sở dữ liệu tỉnh thành công !"
              );
            }
          } else this.$toast.error(response.data.message);

          this.SetButton(3);
          this.loading(false);
        })
        .catch((error) => {
          this.$toast.error(error.data.message);
          this.loading(false);
        });
    },

    xoa() {
      this.$confirm("Bạn thật sự muốn xóa dữ liệu không ?", "Thông báo", {
        confirmButtonText: "Có",
        cancelButtonText: "Không",
      }).then(async () => {
        var data = {
          isdongbo_vina_trasau: 0,
          nganhang_id: this.nganhang.NGANHANG_ID,
        };
        if (TRANGTHAI_DONGBO.OPEN_SYNC_VINA_TRASAU) {
          API.ts_dm_nganhang_xoa(this.axios, {
            nganhang_id: this.nganhang.NGANHANG_ID,
          }).then((response) => {
            if (response.data.error_code === "BSS-00000000") {
              API.xoa_nganhang(this.axios, data).then((response) => {
                if (
                  response &&
                  response.data &&
                  response.data.data &&
                  response.data.error &&
                  response.data.error_code === "BSS-00000000"
                ) {
                  //neu xoa thanh cong thi load lai danh sach ngan hang
                  this.load_dulieu();
                  this.SetButton(1);
                  this.$toast.success(
                    "Xóa ngân hàng trên dữ liệu viễn thông tỉnh thành công !"
                  );
                } else this.$toast.success(response.data.message);
              });
              this.$toast.success("Xóa ngân hàng trên VinaPhone thành công !");
            } else {
              this.$toast.error(response.data.message);
            }
          });
        }
      });
    },

    huy() {
      this.nganhang = {
        DIACHI_NH: "",
        GHICHU: "",
        LOAI_NH: 0,
        MA_NH: "",
        MST: "",
        NGANHANG_ID: 0,
        NGANHANG_ID_VT: 0,
        SO_DVQHNS: "",
        STK: "",
        STK_VT: "",
        TEN_NH: "",
        TINH_ID: "",
      };
      this.SetButton(2);
    },

    load_dulieu: async function () {
      this.loading(true);
      await API.laydulieu_nganhang(this.axios, null).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error &&
          response.data.error_code === "BSS-00000000"
        ) {
          this.listbox.dulieu_nganhang.list = response.data.data;
        } else {
          this.listbox.dulieu_nganhang.list = [];
        }
      });
      this.loading(false);
    },
  },
};
</script>
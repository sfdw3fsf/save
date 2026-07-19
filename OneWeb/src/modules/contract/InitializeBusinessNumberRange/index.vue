<template src="./index.html"></template>

<script>
import breadcrumb from "@/components/breadcrumb";
import gridview from "@/components/Shared/gridview";
import api from "./api/index.js";
export default {
  components: { breadcrumb, gridview },
  name: "",

  data() {
    return {
      header: {
        title: "Khởi tạo dải số doanh nghiệp",
        list: [
          { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
          {
            name: "Lắp đặt mới",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      thongTinNguoiDung: {},
      dsDaiSo: [],
      formThongTin: {
        maBiMat: "",
        soMSIN: "",
        loaiSelected: "",
        loai: [],
        ghiChu: "",
      },
      thongTinTBSelected: {},
      soThueBaoSelected: "",
      tableThueBaoConfig: {
        tableCollumns: [
          {
            fieldName: "stt",
            headerText: "STT",
            allowFiltering: true,
            allowSorting: false,
            textAlign: "",
          },
          {
            fieldName: "so_tb",
            headerText: "Số thuê bao",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "ten_dn",
            headerText: "Tên doanh nghiệp",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "ngay_chonso",
            headerText: "Ngày chọn số",
            allowFiltering: true,
            allowSorting: false,
          },
        ],
        tableData: [],
      },
    };
  },
  created() {
    console.log(this.$root.token.getNhanVienID());
    console.log(this.$root.token.getPhanVungID());
    let dataMaTinh = {
      id_neo: "tentat",
      table: "css.TINH",
      dk: "where TINH_ID=" + this.$root.token.getPhanVungID(),
    };
    api.layMaTinh(this.axios, dataMaTinh).then((res) => {
      console.log(res.data.data);
    });

    api.layLoaiThueBao(this.axios).then((res) => {
      if (res.data.data.length > 0) {
        this.formThongTin.loai = res.data.data;
        this.formThongTin.loaiSelected = res.data.data[0].LOAITB_ID;
      } else {
        this.formThongTin.loai = [];
        this.formThongTin.loaiSelected = "";
      }
    });

    api.layThongTinNguoiDung(this.axios, {}).then((res) => {
      if (res.data.data) {
        this.thongTinNguoiDung = res.data.data;
      }
    });
  },
  mounted() {},
  computed: {},
  watch: {
    optionTrangThai() {
      this.layDanhSachHDTB();
    },
  },
  methods: {
    chonThueBao(item){
      console.log(item);
      if(item !== undefined){
        this.soThueBaoSelected = item.data.so_tb
      }else{
        this.soThueBaoSelected = ''
      }
    },

    timKiemThongTin() {
      if(this.formThongTin.maBiMat == ""){
        this.$toast.warning("Bạn chưa nhập mã bí mật!");
        return false;
      }
      let dataDS = {
        ma_tinh: "",
        ma_xt: this.formThongTin.maBiMat.trim(),
      };
      this.tableThueBaoConfig.tableData = [];
      api.layDSDaiSo(this.axios, dataDS).then((res) => {
        console.log(res.data.data);
        if (res.data.data.length > 0) {
          this.dsDaiSo = res.data.data;
          for (let i = 0; i < this.dsDaiSo.length; i++) {
            let obj = {
              stt: i+1,
              so_tb: this.dsDaiSo[i].MSISDN,
              ten_dn: this.dsDaiSo[i].TEN_DN,
              ngay_chonso: this.dsDaiSo[i].NGAY_DK,
            };
            this.tableThueBaoConfig.tableData.push(obj);
          }
        } else {
          this.$toast.error("Không có thông tin tìm kiếm!")
          this.dsDaiSo = [];
        }
      });
    },

    selectItemThueBao(data) {
      // console.log(data.rowData);
      // this.soThueBaoSelected = data.rowData.so_tb;
    },

    async thucHien_Click() {
      if (this.soThueBaoSelected == "") {
        this.$toast.warning("Không có hàng nào được chọn!");
        return false;
      }
      if (this.formThongTin.maBiMat == "") {
        this.$toast.warning("Bạn chưa nhập mã bí mật!");
        return false;
      }
      if (this.formThongTin.soMSIN == "") {
        this.$toast.warning("Bạn chưa nhập số SIM!");
        return false;
      }

      if (
        confirm(
          "Bạn có muốn khởi tạo số " + this.soThueBaoSelected + " không ?"
        )
      ) {
        let data = {
          loai_tb: this.formThongTin.loaiSelected,
          ma_tinh: "",
          ghi_chu: this.formThongTin.ghiChu,
          so_tb: this.soThueBaoSelected,
          so_msin: this.formThongTin.soMSIN,
          ma_xt: this.formThongTin.maBiMat,
          hdtb_id: this.soThueBaoSelected.hdtb_id,
          userid: this.thongTinNguoiDung.nguoidung_id.toString(),
        };
        let res = await api.khoiTaoDSDN(this.axios, data);
        if (res.data && res.data.error_code == "BSS-00000000") {
          let checkInsertOrUpdate = await this.KHOITAO_TS(
            this.soThueBaoSelected
          );
          console.log(checkInsertOrUpdate);
          if (checkInsertOrUpdate) {
            this.$toast.success(
              "Gán thành công cho số " + this.soThueBaoSelected
            );
            this.timKiemThongTin()
          } else {
            this.$toast.warning(
              "Gán thành công cho số " +
                this.soThueBaoSelected +
                " trên Vinaphone, lưu dữ liệu về tỉnh bị lỗi. Vui lòng liên hệ admin để xử lý!"
            );
          }
        } else {
          this.$toast.error("Có lỗi xẩy ra!");
        }
      }
    },

    async KHOITAO_TS(ma_tb) {
      let res = await api.kiemTraThueBaoTT(this.axios, ma_tb);
      if (res.data && res.data.error_code != "BSS-00000000") {
        let dataObj = {
          p_somay: ma_tb,
          p_simcard: this.formThongTin.soMSIN,
          p_donvi_id: this.$root.token.getDonViID(),
          p_ghichu: this.formThongTin.ghiChu,
          p_nguoidung_id: this.$root.token.getNguoiDungID(),
          p_nguoi_cn: this.thongTinNguoiDung.ma_nd,
          p_may_cn: "",
          p_ip_cn: "",
          p_user_neo: this.thongTinNguoiDung.user_neo,
        };

        let res1 = await api.themMoiVaoKhoSo(this.axios, dataObj);

        if (res1.data && res1.data.error_code == "BSS-00000000") {
          return true;
        }
        return false;
      } else {
        let dataObj = {
          p_somay: ma_tb,
          p_simcard: this.formThongTin.soMSIN,
          p_donvi_id: this.$root.token.getDonViID(),
          p_trangthaiso_id: "1",
          p_ghichu: this.formThongTin.ghiChu,
          p_nguoidung_id: this.$root.token.getNguoiDungID(),
          p_nguoi_cn: this.thongTinNguoiDung.ma_nd,
          p_may_cn: "",
          p_ip_cn: "",
          p_user_neo: this.thongTinNguoiDung.user_neo,
          p_goicuoc_id: "20",
        };

        let res2 = await api.capNhatVaoKhoSo(this.axios, dataObj);

        if (res2.data && res2.data.error_code == "BSS-00000000") {
          return true;
        }
        return false;
      }
    },
  },
};
</script>

<style>
</style>

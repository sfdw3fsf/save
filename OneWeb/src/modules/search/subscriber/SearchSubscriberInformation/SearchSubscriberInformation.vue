<template src="./SearchSubscriberInformation.html"></template>
<style scoped src="./SearchSubscriberInformation.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import DataGrid from "@/components/Controls/DataGrid";
import api from "./API.js";
export default {
  name: "SearchSubscriberInformation",
  components: { breadcrumb, DataGrid, api },
  data() {
    return {
      ds_dichvu: [],
      ds_loaihinhtb: [],
      ds_doituong: [],
      ds_trangthai: [],
      ds_thaydoi: [],
      header: {
        title: "TRA CỨU LỊCH SỬ BIẾN ĐỘNG THÔNG TIN THUÊ BAO",
        list: [
          //{ name: 'Trang chủ', link: { name: 'Ui.cards' } },
          { name: "Tra cứu", link: { name: "Ui.cards" } },
          { name: "Phát triển thuê bao", link: { name: "Ui.cards" } },
          {
            name: "Tra cứu lịch sử biến động thông tin thuê bao",
            link: { name: "Ui.cards" },
            active: true,
          },
        ],
      },
      dataSelected: {
        thuebao_id: 0,
        ma_tb: "",
        soao: "",
        ten_tb: "",
        diachi_ld: "",
        dichvuvt_id: 4,
        loaitb_id: 58,
        doituong_id: 1,
        trangthaitb_id: 0,
        ma_kh: "",
        ten_kh: "",
        diachi_kh: "",
        ma_tt: "",
        ten_tt: "",
        diachi_tt: "",
      },
      ds_lichsu_thuebao: {
        list: [],
        header: [
          {
            fieldName: "ma_gd",
            headerText: "Mã GD",
            allowFiltering: true,
          },
          {
            fieldName: "ten_loaihd",
            headerText: "Loại HĐ/PL",
            allowFiltering: true,
          },
          {
            fieldName: "ten_kieuld",
            headerText: "Kiểu lắp đặt",
            allowFiltering: true,
          },
          {
            fieldName: "ngay_yc",
            headerText: "Ngày yêu cầu",
            allowFiltering: true,
            textAlign: "right",
          },
          {
            fieldName: "trangthai_hd",
            headerText: "Trạng thái TH",
            allowFiltering: true,
          },
          {
            fieldName: "ngay_ht",
            headerText: "Ngày HT",
            allowFiltering: true,
            textAlign: "right",
          },
          {
            fieldName: "nguoi_cn",
            headerText: "Người CN",
            allowFiltering: true,
          },
          {
            fieldName: "may_cn",
            headerText: "Máy CN",
            allowFiltering: true,
          },
          {
            fieldName: "ghichu",
            headerText: "Ghi chú",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      ds_thongtin_thaydoi: {
        list: [],
        header: [
          {
            fieldName: "loai",
            headerText: "Nhóm",
            allowFiltering: true,
            isGroupedColumn: true,
          },
          {
            fieldName: "ten_ht",
            headerText: "Tên thông tin",
            allowFiltering: true,
          },
          {
            fieldName: "giatricu",
            headerText: "Giá trị cũ",
            allowFiltering: true,
          },
          {
            fieldName: "giatrimoi",
            headerText: "Giá trị mới",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      ds_lichsu_dungthu: {
        list: [],
        header: [
          {
            fieldName: "ma_tb",
            headerText: "Số máy/Acc",
            allowFiltering: true,
          },
          {
            fieldName: "trangthai",
            headerText: "Trạng thái",
            allowFiltering: true,
          },
          {
            fieldName: "songay",
            headerText: "Số ngày DT",
            allowFiltering: true,
            type: "Number",
            format: "N0",
          },
          {
            fieldName: "ngay_bd",
            headerText: "Ngày bắt đầu",
            allowFiltering: true,
            textAlign: "right",
          },
          {
            fieldName: "ngay_kt",
            headerText: "Ngày kết thúc",
            allowFiltering: true,
            textAlign: "right",
          },
          {
            fieldName: "ngay_chuyen",
            headerText: "Ngày chuyển",
            allowFiltering: true,
            textAlign: "right",
          },
          {
            fieldName: "ngay_sd",
            headerText: "Ngày sử dụng",
            allowFiltering: true,
            textAlign: "right",
          },
          {
            fieldName: "ngay_cat",
            headerText: "Ngày hủy",
            allowFiltering: true,
            textAlign: "right",
          },
          {
            fieldName: "ten_ctkm",
            headerText: "Chi tiết KM",
            allowFiltering: true,
          },
          {
            fieldName: "ngay_cn",
            headerText: "Ngày CN",
            allowFiltering: true,
            textAlign: "right",
          },
          {
            fieldName: "may_cn",
            headerText: "Máy CN",
            allowFiltering: true,
          },
          {
            fieldName: "nguoi_cn",
            headerText: "Người CN",
            allowFiltering: true,
          },
        ],
        
        value: {},
        isEnabled: true,
        checked: [],
      },
      ds_lichsu_tratruoc_trasau : {
          list: [],
          header: [
            {
              fieldName: "thang",
              headerText: "Tháng",
              allowFiltering: true,
            },
            {
              fieldName: "kieu",
              headerText: "Kiểu",
              allowFiltering: true,
            },
            {
              fieldName: "ngay_cn",
              headerText: "Ngày cập nhật",
              allowFiltering: true
            },
            {
              fieldName: "ip_cn",
              headerText: "Máy cập nhật",
              allowFiltering: true,
              textAlign: "center",
            },
            {
              fieldName: "nguoi_cn",
              headerText: "Người cập nhật",
              allowFiltering: true,
              textAlign: "center",
            }
          ],
        },
    };
  },
  computed: {
    cp_lichsu_dungthu: {
      get() {
        return this.ds_lichsu_dungthu;
      },
      set(value) {
        return (this.ds_lichsu_dungthu = value);
      },
    },
    cp_lichsu_thuebao: {
      get() {
        return this.ds_lichsu_thuebao;
      },
      set(value) {
        return (this.ds_lichsu_thuebao = value);
      },
    },
    cp_thongtin_thaydoi: {
      get() {
        return this.ds_thongtin_thaydoi;
      },
      set(value) {
        return (this.ds_thongtin_thaydoi = value);
      },
    },
  },
  async created() {
    this.loading(true);
    try {
      await Promise.all([
        this.getListDichVu(),
        this.getListLoaiHinhTB(this.dataSelected.dichvuvt_id),
        this.getDSDoituong(),
        this.getDSTrangThai(),
      ]);
      if (this.$route.query.ma_tb && this.$route.query.ma_tb.trim() != "") {
        this.dataSelected.ma_tb = this.$route.query.ma_tb;
        await this.handleSearch();
      }
    } catch (ex) {
      this.loading(false);
    }
    this.loading(false);
  },
  methods: {
    getListDichVu: async function () {
      let data = this.GetData(await api.getDSDichVu(this.axios));
      this.ds_dichvu = data;
    },
    getListLoaiHinhTB: async function (value) {
      let data = this.GetData(await api.getDSLoaiHinhTB(this.axios, value));
      this.ds_loaihinhtb = data;
      if (data.length > 0) {
        this.dataSelected.loaitb_id = Number(data[0].ID);
      } else {
        this.$toast.error("Không tìm thấy loại hình thuê bao!");
        this.dataSelected.loaitb_id = -1;
      }
    },
    getDSDoituong: async function () {
      let data = this.GetData(await api.getDSDoiTuong(this.axios));
      this.ds_doituong = data;
      if (data.length > 0) {
        this.dataSelected.doituong_id = Number(data[0].doituong_id);
      } else {
        this.dataSelected.doituong_id = -1;
      }
    },
    getDSTrangThai: async function () {
      let data = this.GetData(await api.getDSTrangThai(this.axios));
      this.ds_trangthai = data;
      if (data.length > 0) {
        this.dataSelected.trangthaitb_id = Number(data[0].TRANGTHAITB_ID);
      } else {
        this.dataSelected.doituong_id = -1;
      }
    },
    getDanhBaMaTB: async function () {
      let input = {
        dichvuvt_id: Number(this.dataSelected.dichvuvt_id),
        ma_tb: this.dataSelected.ma_tb,
        donvi_dl_id: 0,
      };
      let data = this.GetData(await api.getDanhBaMaTB(this.axios, input));
      if (data.length > 0) {
        await this.getListLoaiHinhTB(data[0].dichvuvt_id);
        this.dataSelected.thuebao_id = data[0].thuebao_id;
        this.dataSelected.ma_tb = data[0].ma_tb;
        this.dataSelected.soao = data[0].ma_lt;
        this.dataSelected.ten_tb = data[0].ten_tb;
        this.dataSelected.diachi_ld = data[0].diachi_ld;
        this.dataSelected.dichvuvt_id = data[0].dichvuvt_id;
        this.dataSelected.loaitb_id = data[0].loaitb_id;
        this.dataSelected.doituong_id = data[0].doituong_id;
        this.dataSelected.trangthaitb_id = data[0].trangthaitb_id;
        this.dataSelected.ma_kh = data[0].ma_kh;
        this.dataSelected.ten_kh = data[0].ten_kh;
        this.dataSelected.diachi_kh = data[0].diachi_kh;
        this.dataSelected.ma_tt = data[0].ma_tt;
        this.dataSelected.ten_tt = data[0].ten_tt;
        this.dataSelected.diachi_tt = data[0].diachi_tt;
        await this.getLishSuThuebao(data[0].thuebao_id);
        await this.getLichSuDungThu(data[0].ma_tb);
      } else {
        this.cp_lichsu_thuebao.list = [];
        this.cp_thongtin_thaydoi.list = [];
        this.dataSelected.thuebao_id = 0;
        this.dataSelected.ma_tb = this.dataSelected.ma_tb;
        this.dataSelected.soao = "";
        this.dataSelected.ten_tb = "";
        this.dataSelected.diachi_ld = "";
        this.dataSelected.dichvuvt_id = this.dataSelected.dichvuvt_id;
        this.dataSelected.loaitb_id = this.dataSelected.loaitb_id;
        this.dataSelected.doituong_id = this.dataSelected.doituong_id;
        this.dataSelected.trangthaitb_id = this.dataSelected.trangthaitb_id;
        this.dataSelected.ma_kh = "";
        this.dataSelected.ten_kh = "";
        this.dataSelected.diachi_kh = "";
        this.dataSelected.ma_tt = "";
        this.dataSelected.ten_tt = "";
        this.dataSelected.diachi_tt = "";
        var filter = this.ds_dichvu.filter(
          (e) => e.DICHVUVT_ID == Number(this.dataSelected.dichvuvt_id)
        );
        var dichvu = filter.length > 0 ? filter[0].TEN_DVVT : "";
        this.$toast.error(
          "Không tìm thấy dữ liệu Danh bạ thuê bao với dịch vụ " + dichvu
        );
      }
    },
    getLishSuThuebao: async function (value) {
      try {
        this.cp_lichsu_thuebao.list = [];
        let input = {
          thuebao_id: value,
        };
        let data = this.GetData(await api.getLichSuThueBao(this.axios, input));
        if (data.length > 0) {
          data = data.map((v) => ({ ...v, ghichu: "" }));
          this.cp_lichsu_thuebao.list = data;
        }
      } catch (e) {
        if (e.data && e.data.message_detail)
          this.$toast.error(
            "Lỗi tìm kiếm lịch sử thuê bao" + e.data.message_detail
          );
        else this.$toast.error("Lỗi tìm kiếm lịch sử thuê bao" + e);
      }
    },
    getLichSuThongtinTB: async function (value) {
      try {
        this.cp_thongtin_thaydoi.list = [];
        let input = {
          vhdtb_id: value,
        };
        let data = this.GetData(
          await api.getLichSuThongtinTB(this.axios, input)
        );
        if (data.length > 0) {
          var tb = 0;
          var tt = 0;
          if (data.length == 3) {
            for (var i = 0; i < Object.keys(data[0]).length - 1; i++) {
              if (
                Object.keys(data[0])[i].toString().toLowerCase() != "tt" &&
                tt == 0 &&
                Object.keys(data[0])[i].toString().toLowerCase() != "tb" &&
                tb == 0
              ) {
                if (
                  data[0][Object.keys(data[0])[i]] !=
                  data[1][Object.keys(data[1])[i]]
                ) {
                  var tmp = {
                    loai: "1-Thông tin khách hàng",
                    ten_ht: data[2][Object.keys(data[2])[i]]
                      ? data[2][Object.keys(data[2])[i]].toString()
                      : "",
                    giatricu: data[0][Object.keys(data[0])[i]]
                      ? data[0][Object.keys(data[0])[i]].toString()
                      : "",
                    giatrimoi: data[1][Object.keys(data[1])[i]]
                      ? data[1][Object.keys(data[1])[i]].toString()
                      : "",
                  };
                  this.ds_thongtin_thaydoi.list.push(tmp);
                }
              } else if (
                Object.keys(data[0])[i].toString().toLowerCase() == "tt"
              ) {
                tt = 1;
              } else if (
                Object.keys(data[0])[i].toString().toLowerCase() != "tb" &&
                tb == 0
              ) {
                if (
                  data[0][Object.keys(data[0])[i]] !=
                  data[1][Object.keys(data[1])[i]]
                ) {
                  var tmp = {
                    loai: "2-Thông tin thanh toán",
                    ten_ht: data[2][Object.keys(data[2])[i]]
                      ? data[2][Object.keys(data[2])[i]].toString()
                      : "",
                    giatricu: data[0][Object.keys(data[0])[i]]
                      ? data[0][Object.keys(data[0])[i]].toString()
                      : "",
                    giatrimoi: data[1][Object.keys(data[1])[i]]
                      ? data[1][Object.keys(data[1])[i]].toString()
                      : "",
                  };
                  this.cp_thongtin_thaydoi.list.push(tmp);
                }
              } else if (
                Object.keys(data[0])[i].toString().toLowerCase() == "tb"
              ) {
                tb = 1;
              } else {
                if (
                  data[0][Object.keys(data[0])[i]] !=
                  data[1][Object.keys(data[1])[i]]
                ) {
                  var tmp = {
                    loai: "3-Thông tin thuê bao",
                    ten_ht: data[2][Object.keys(data[2])[i]]
                      ? data[2][Object.keys(data[2])[i]].toString()
                      : "",
                    giatricu: data[0][Object.keys(data[0])[i]]
                      ? data[0][Object.keys(data[0])[i]].toString()
                      : "",
                    giatrimoi: data[1][Object.keys(data[1])[i]]
                      ? data[1][Object.keys(data[1])[i]].toString()
                      : "",
                  };
                  this.cp_thongtin_thaydoi.list.push(tmp);
                }
              }
            }
          }
          if (data.length == 2) {
            for (var i = 0; i < Object.keys(data[0]).length - 1; i++) {
              if (
                Object.keys(data[0])[i].toString().toLowerCase() != "tt" &&
                tt == 0 &&
                Object.keys(data[0])[i].toString().toLowerCase() != "tb" &&
                tb == 0
              ) {
                if (
                  data[0][Object.keys(data[0])[i]] !=
                  data[1][Object.keys(data[1])[i]]
                ) {
                  var tmp = {
                    loai: "1-Thông tin khách hàng",
                    ten_ht: data[1][Object.keys(data[1])[i]]
                      ? data[1][Object.keys(data[1])[i]].toString()
                      : "",
                    giatricu: "",
                    giatrimoi: data[0][Object.keys(data[0])[i]]
                      ? data[0][Object.keys(data[0])[i]].toString()
                      : "",
                  };
                  this.ds_thongtin_thaydoi.list.push(tmp);
                }
              } else if (
                Object.keys(data[0])[i].toString().toLowerCase() == "tt"
              ) {
                tt = 1;
              } else if (
                Object.keys(data[0])[i].toString().toLowerCase() != "tb" &&
                tb == 0
              ) {
                if (
                  data[0][Object.keys(data[0])[i]] !=
                  data[1][Object.keys(data[1])[i]]
                ) {
                  var tmp = {
                    loai: "2-Thông tin thanh toán",
                    ten_ht: data[1][Object.keys(data[1])[i]]
                      ? data[1][Object.keys(data[1])[i]].toString()
                      : "",
                    giatricu: "",
                    giatrimoi: data[0][Object.keys(data[0])[i]]
                      ? data[0][Object.keys(data[0])[i]].toString()
                      : "",
                  };
                  this.cp_thongtin_thaydoi.list.push(tmp);
                }
              } else if (
                Object.keys(data[0])[i].toString().toLowerCase() == "tb"
              ) {
                tb = 1;
              } else {
                if (
                  data[0][Object.keys(data[0])[i]] !=
                  data[1][Object.keys(data[1])[i]]
                ) {
                  var tmp = {
                    loai: "3-Thông tin thuê bao",
                    ten_ht: data[1][Object.keys(data[1])[i]]
                      ? data[1][Object.keys(data[1])[i]].toString()
                      : "",
                    giatricu: "",
                    giatrimoi: data[0][Object.keys(data[0])[i]]
                      ? data[0][Object.keys(data[0])[i]].toString()
                      : "",
                  };
                  this.cp_thongtin_thaydoi.list.push(tmp);
                }
              }
            }
          }
        }
      } catch (e) {
        if (e.data && e.data.message_detail)
          this.$toast.error(
            "Lỗi tìm kiếm lịch sử thông tin thuê bao" + e.data.message_detail
          );
        else this.$toast.error("Lỗi tìm kiếm lịch sử thông tin thuê bao" + e);
      }
    },
    getLichSuDungThu: async function (value) {
      try {
        let input = {
          vma_tb: value,
        };
        let data = this.GetData(await api.getLichSuDungThu(this.axios, input));
        this.cp_lichsu_dungthu.list = data;
      } catch (e) {
        if (e.data && e.data.message_detail)
          this.$toast.error(
            "Lỗi tìm kiếm lịch sử dung thử thuê bao" + e.data.message_detail
          );
        else this.$toast.error("Lỗi tìm kiếm lịch sử dùng thử thuê bao" + e);
      }
    },
    async sp_lay_ds_tratruoc_trasau(){
      let kq = await api.sp_lay_ds_tratruoc_trasau(this.axios, {
                "thuebao_id":this.dataSelected.thuebao_id
            })
      if(kq["data"] && kq["data"]["error_code"] == "BSS-00000000"){
        this.ds_lichsu_tratruoc_trasau.list = kq?.data?.data
        console.log('ds_lichsu_tratruoc_trasau', this.ds_lichsu_tratruoc_trasau.list)
      }else{
        this.ds_lichsu_tratruoc_trasau.list = []
      }
    },
    onChangeDVVT: async function (value) {
      this.loading(true);
      try {
        var dichvuvt_id = value ? Number(value) : 0;
        await this.getListLoaiHinhTB(dichvuvt_id);
      } catch (e) {
        this.loading(false);
      }
      this.loading(false);
    },
    onChangeLoaiHinhTB: async function (value) {
      // console.log(value);
    },
    onChangeDT: async function (value) {
      // console.log(value);
    },
    onChangeTT: async function (value) {
      // console.log(value);
    },
    handleSearch: async function () {
      this.loading(true);
      try {
        var checked = this.validateInput(this.dataSelected);
        if (checked == true) {
          await this.getDanhBaMaTB();
        }
        await this.sp_lay_ds_tratruoc_trasau()// thêm đoạn gọi hàm tra cứu ở phía trên 
      } catch (e) {
        this.loading(false);
      }
      this.loading(false);
    },
    validateInput: function (data) {
      if (!data.ma_tb || data.ma_tb == "") {
        this.$toast.error("Bạn phải nhập Số máy/Acc!");
        return false;
      }
      // if (!data.loaitb_id || data.loaitb_id == -1) {
      //   this.$toast.error("Bạn chưa chọn Loại hình thuê bao!");
      //   return false;
      // }
      return true;
    },
    handleLSTBClick: function (value) {
      this.getLichSuThongtinTB(Number(value[0].hdtb_id));
    },
    GetData: function (response) {
      if (
        response.data.error === 200 ||
        response.data.error === "200"
      ) {
        return response.data.data;
      } else if (
        response.data.error === 204 ||
        response.data.error === "204"
      ) {
        return []
      } else {
        console.log(response.data.error_code);
        return []        
      }
    },
  },
};
</script>
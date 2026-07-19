<template src="./ChamSocThueBaoRoiMang.html"></template>
<style src="./ChamSocThueBaoRoiMang.scss"></style>
<script>
import VueSplitPane from "vue-split-pane";
import ChamSocThueBaoRoiMangApi from "./../api/ChamSocThueBaoRoiMangApi";
import select2 from "@/components/Select2.vue";
export default {
  components: {
    VueSplitPane,
    appSelect2: select2,
  },
  name: "ChamSocThueBaoRoiMang",
  data() {
    return {
      header: {
        title: "CHĂM SÓC THUÊ BAO RỜI MẠNG",
        list: [
          //   { name: "Trang chủ", link: { name: "Ui.cards" } },
          {
            name: "Chăm sóc khách hàng",
            link: { name: "Ui.buttons" }
          },
          {
            name: "Chăm sóc thuê bao rời mạng",
            link: { name: "Ui.buttons" }
          }
        ]
      },
      dsNguyenNhan: [],
      nguyenNhanSelected: -1,
      dsChinhSach: [],
      chinhSachSelected: -1,
      txtGhiChu: "",
      txtMaTB: "",
      dsTBRoiMang: [],
      dsTBRoiMangFields: [
        {
          fieldName: "MA_TB",
          headerText: "Mã TB",
          allowFiltering: true,
          allowSorting: true
        },
        {
          fieldName: "TEN_TB",
          headerText: "Tên TB",
          allowFiltering: true,
          allowSorting: true
        },
        {
          fieldName: "DIACHI_TB",
          headerText: "Địa chỉ TB",
          allowFiltering: true,
          allowSorting: true
        },
        {
          fieldName: "TEN_KIEULD",
          headerText: "Loại HD",
          allowFiltering: true,
          allowSorting: true
        }
      ],
      dsLichSuChamSoc: [],
      dsLichSuChamSocFields: [
        {
          fieldName: "TEN_NV",
          headerText: "Nhân viên",
          allowFiltering: true,
          allowSorting: true
        },
        {
          fieldName: "NOIDUNG",
          headerText: "Nội dung",
          allowFiltering: true,
          allowSorting: true
        },
        {
          fieldName: "NGUYENNHAN",
          headerText: "Nguyên nhân",
          allowFiltering: true,
          allowSorting: true
        },
        {
          fieldName: "NGAY_CN",
          headerText: "Ngày thực hiện",
          allowFiltering: true,
          allowSorting: true
        }
      ],
      hbtdId: 0
    };
  },
  async created() {

  },
  async mounted() {
    this.loading(true);
    await this.getDsNguyenNhan();
    await this.getDsChinhSach();
    this.loading(false);
    if (this.dsNguyenNhan.length > 0) {
      this.nguyenNhanSelected = this.dsNguyenNhan[0].id;
    }
    if (this.dsChinhSach.length > 0) {
      this.chinhSachSelected = this.dsChinhSach[0].id;
    }
  },
  methods: {
    async getDsNguyenNhan() {
      try {
        this.dsNguyenNhan = [];
        let response = await ChamSocThueBaoRoiMangApi.getDsNguyenNhanRoiMang(
          this.axios
        );

        if (response.data.error_code === "BSS-00000000") {
          let ds = response.data.data || [];

          ds.forEach(e => {
            this.dsNguyenNhan.push({
              id: e.nncsId,
              text: e.nguyenNhan
            });
          });
        }
      } catch (e) {
        console.log(e);
      } finally {
      }
    },
    async getDsChinhSach() {
      try {
        this.dsChinhSach = [];
        let response = await ChamSocThueBaoRoiMangApi.getDsChinhSach(
          this.axios
        );
        if (response.data.error_code === "BSS-00000000") {
          let ds = response.data.data || [];

          ds.forEach(e => {
            this.dsChinhSach.push({
              id: e.ndcsId,
              text: e.noiDung
            });
          });
        }
      } catch (e) {
        console.log(e);
      } finally {
      }
    },
    async getDsRoiMangTheoNV() {
      let data = {
        nhanVienId: this.$auth.getNhanVienID(),
        pageNo: 1,
        pageSize: 10000
      };
      console.log("getDsRoiMangTheoNV", data);
      await ChamSocThueBaoRoiMangApi.layDsRoiMangTheoNV(this.axios, data)
        .then(response => {
          console.log(response);
          this.dsTBRoiMang = [];
          this.dsTBRoiMang = response.data.data.data || [];
        })
        .catch(reject => {
          this.$root.toastError(
            "Lấy danh sách TB rời mạng thất bại: " +
              reject.response.data.error || reject.response.data.message
          );
        });
    },
    async getLichSuChamSoc(hdtbId) {
      let data = {
        hdtbId: hdtbId,
        pageNo: 1,
        pageSize: 10000
      };
       this.loading(true);
      await ChamSocThueBaoRoiMangApi.layLichSuChamSoc(this.axios, data)
        .then(response => {
          this.loading(false);
          this.dsLichSuChamSoc = [];
          this.dsLichSuChamSoc = response.data.data.data || [];
        })
        .catch(reject => {
          this.loading(false);
          this.$root.toastError(
            "Lấy danh sách lịch sử chăm sóc thất bại: " +
              reject.response.data.error || reject.response.data.message
          );
        });
    },
    async layThongTin() {
      this.loading(true);
      try {
         await this.getDsRoiMangTheoNV();
      }
      catch (e) {}
      finally {
        this.loading(false);
      }


    },
    async capNhap() {
      if (this.txtGhiChu && this.txtGhiChu.length > 500) {
        this.$root.toastError("Trường ghi chú có độ dài vượt quá 500 ký tự!")
        return
      }
      let postData = {
        hbtdId: this.hbtdId,
        nhanVienCsId: this.$auth.getNhanVienID(),
        noiDungCs: this.txtGhiChu,
        nncsId: this.nguyenNhanSelected,
        ndcsId: this.chinhSachSelected,
        mayCn: "pc_test",
        maNd: this.$auth.getMaNhanVien()
      };
      try {
        this.loading(true);
        let response = await ChamSocThueBaoRoiMangApi.capNhapThongTinChamSoc(
          this.axios,
          postData
        );
        if (response.data.error_code === "BSS-00000000") {
          this.$root.toastSuccess("Cập nhập thành công!");
          await this.layThongTin()
        } else {
          this.$root.toastError("Cập nhập thất bại: " + response.data.message);
        }
      } catch (e) {
        console.log(e);
        this.$root.toastError("Cập nhập thất bại: " + e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async onDsRoiMangRowSelected(dataItem) {
      if (dataItem) {
        this.txtGhiChu = "";
        this.txtMaTB = dataItem.MA_TB;
        this.hbtdId = dataItem.HDTB_ID
        await this.getLichSuChamSoc(dataItem.HDTB_ID)
      }
    }
  }
};
</script>

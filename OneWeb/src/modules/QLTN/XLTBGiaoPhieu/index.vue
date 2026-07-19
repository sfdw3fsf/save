<template src="./index.html"></template>
<style>
.overlay-common .loading {
  display: flex;
  justify-content: center;
  flex-direction: column;
}

.overlay-common.show {
  display: flex;
  justify-content: center;
}

.overlay-common {
  background-color: rgba(0, 0, 0, 0.7);
}

.overlay-common .loading-panel {
  display: flex;
  text-align: center;
  background-color: #fff;
  padding: 10px 15px;
  align-items: center;
  color: #035fcc;
  border: 1px solid #035fcc;
  border-radius: 7px;
}

.overlay-common .loading-panel .loading-content {
  flex-grow: 1;
  padding-left: 10px;
}
</style>

<style src="./style.scss" scoped>
</style>
<script>


import moment from "moment";

// import ModalExport from "./components/modal-export";
import SearchAccountModal from "@/modules/contract/setup/ChangeInstallUnit/popup/SearchContract/SearchContractModal.vue";
import ModalXuatDuLieu from "../TCCuocGoi/XuatFileCuocGoi.vue";


export default {
  components: { appModalXuatDuLieu: ModalXuatDuLieu, SearchAccountModal },
  computed: {

      dsThuocTinh() {
      return this.columns.map((x) => x.fieldName);
    },
  },
  props: ["data"],
  data: function () {
    return {
      header: {
        title: "GIAO PHIẾU ĐIỀU HÀNH THI CÔNG",
        list: [
          { name: "Quản lý đại lý", link: { name: "Ui.cards" } },
          {
            name: "Nhận phiếu",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      columns: [
        {
          headerText: "Số máy/Acc",
          fieldName: "MA_TB",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "Tên thuê bao",
          fieldName: "TEN_TB",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "Địa chỉ lắp đặt",
          fieldName: "DIACHI_LD",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "Thông tin yêu cầu",
          fieldName: "TTYC",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "Số QĐ",
          fieldName: "SOPHIEU",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "Đơn vị nhận",
          fieldName: "DONVINHAN",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "Nội dung trả",
          fieldName: "ND_TRA_CON",
          allowFiltering: true,
          allowSorting: false,
        },
      ],
      searchForm: {
        ngayGiao: new Date(),
        maGd: "",
        trangThaiId: 0,
        ckbLoaiHD: false,
        loaiHDId: "",
        ckbDichVu: false,
        dichVuId: "",
        ckbQuyTrinh: false,
        quyTrinhId: "",
      },
      updateForm: {
        huongGiaoId: "",
        donViNhanId: "",
        ngayGiao: new Date(),
        soPhieu: "",
        maGd: "",
        maTb: "",
        soQd: "",
        noiDungGiao: "",
        diaChiTC: "",
        loaiDvId: "",
      },
      dsLoaiHD: [],
      dsDichVu: [],
      dsQuyTrinh: [],
      dsHuongGiao: [],
      dsDonViNhan: [],
      dsHDTB: [],
      times: "00:00:00",
      selectedId: [],
      kt_daucuoi: 0,
    };
  },
  validations: {},
  watch: {},
  created: async function () {
    this.initForm();
  },
  async mounted() { },
  methods: {
    async initForm() {
      
      await this.getLoaiHD();
      await this.dsDichVuDropDown();
      await this.getHuongGiao();
      await this.dsQuyTrinhDropDown();
      if (this.data.huonggiao_id != null)
        this.updateForm.huongGiaoId = this.data.huonggiao_id;
      if (this.data.dichvuvt_id !== null)
        this.searchForm.dichVuId = this.data.dichvuvt_id;
      this.updateForm.maTb = '';
      
    },
    showPopupSearchContract() {
      this.$refs.searchAccountModal.showModal();
    },
    onChapNhan(value) {
      //console.log(value);
      if (value) {
        this.searchForm.maGd = value.ma_gd;
      }
    },
    onChangeLoaiHD() {
      this.dsQuyTrinh = [];
      this.dsQuyTrinhDropDown();
      this.getHuongGiao();
      // this.onClickSearch();
    },
    onChangeHuongGiao() {
      if (this.updateForm.huongGiaoId) {
        this.getDvNhan();
        this.getChiTietHuongGiao();
        this.onClickSearch();
      }
    },
    onClickSearch() {
      if (this.searchForm.trangThaiId == 0) {
        const param = {
          huongGiaoId: this.updateForm.huongGiaoId, //6830,
          loaiHDId: this.searchForm.ckbLoaiHD ? this.searchForm.loaiHDId : 0, //1,
          maGD: this.searchForm.maGd,
          dichVuVTId: this.searchForm.ckbDichVu ? this.searchForm.dichVuId : 0, //4,
          donViId: this.$auth.getDonViID(), //12,
          nhanVienId: this.$auth.getNhanVienID(), //6958
        };
        this.getDsHDTB(
          "/web-thuno/api/thu-no/css/ds-hop-dong-thue-bao-chua-giao",
          param
        );
      } else {
        const param = {
          huongGiaoId: this.updateForm.huongGiaoId, //207,
          loaiHDId: this.searchForm.ckbLoaiHD ? this.searchForm.loaiHDId : 0, //1,
          maGD: this.searchForm.maGd, //"",
          dichVuVTId: this.searchForm.ckbDichVu ? this.searchForm.dichVuId : 0, //4,
          donViId: this.$auth.getDonViID(), //176,
          nhanVienId: this.$auth.getNhanVienID(), //4241,
          loaiPTId: 4,
          ngayGiao: moment(this.searchForm.ngayGiao).format("DD/MM/YYYY"), //'04/06/2021'
        };
        this.getDsHDTB(
          "/web-thuno/api/thu-no/css/ds-hop-dong-thue-bao-da-giao",
          param
        );
      }
    },
    resetTextForm() {
      this.updateForm.maGd = "";
      this.updateForm.maTb = "";
      this.updateForm.noiDungGiao = "";
      this.updateForm.diaChiTC = "";
    },
    choseRowTable: async function (index, value) {
      // console.log(value);
      if (value) {
        this.updateForm.maGd = value.MA_GD;
        this.updateForm.maTb = value.MA_TB;
        this.updateForm.soPhieu = value.SOPHIEU || "";
        this.updateForm.noiDungGiao = value.ND_GIAO || "";
        this.updateForm.donViNhanId = value.DONVINHAN_ID;
        this.kt_daucuoi = 1;

        //TSL
        if (value.DICHVUVT_ID == 9 && value.DANHDAU != 3) {
          this.kt_daucuoi = 0;
        }

        this.updateForm.loaiDvId = await this.getLoaiDVDich();
        // console.log(this.updateForm.loaiDvId)
        if (this.updateForm.loaiDvId != -1) {
          //   this.getDvNhan(); reload đơn vị nhận
        } else {
          this.dsDonViNhan = [];
        }
      } else {
        this.updateForm.maGd = "";
        this.updateForm.maTb = "";
        this.updateForm.soPhieu = "";
        this.updateForm.noiDungGiao = "";
        this.updateForm.donViNhanId = this.dsDonViNhan[0].DONVI_ID;
      }
    },
    checkRowTable(value) {
      this.selectedId = value;
    },
    onClickGiao() {
      this.$refs.maGd.focus();
      const selected = this.$refs.table.getSelectedRecords();
      if (selected == null || selected.length <= 0) {
        this.$root.toastError("Bạn chưa chọn phiếu!");
        return;
      }
      //   if (this.selectedId.length == 0) {
      //     this.$toast.error("Bạn chưa chọn phiếu!");
      //     return false;
      //   }
      if (this.updateForm.ngayGiao > new Date()) {
        this.$toast.error("Ngày giao không được lớn hơn ngày hiện tại!");
        return false;
      }
      this.giaoPhieu();
    },
    onClickCapNhat() {
      const selected = this.$refs.table.getSelectedRecords();
      if (selected == null || selected.length <= 0) {
        this.$root.toastError("Bạn chưa chọn phiếu!");
        return;
      }
      //   if (this.selectedId.length == 0) {
      //     this.$toast.error("Bạn chưa chọn phiếu!");
      //     return false;
      //   }
      //   const listPhieu = this.dsHDTB.filter((item) =>
      //     this.selectedId.includes(item.PHIEU_ID)
      //   );
      const listPhieu = selected;
      listPhieu.forEach((el) => {
        const body = {
          hdtbId: el.HDTB_ID,
          donViId: this.updateForm.donViNhanId,
          loaiDVId: this.updateForm.loaiDvId,
          kieuDVId: 2,
        };
        this.capNhatDonVi(body);
      });
    },
    onClickHuyGiao() {
      if (this.validateDelete()) {
        const listPhieu = this.$refs.table.getSelectedRecords();
        // const listPhieu = this.dsHDTB.filter((item) =>
        //   this.selectedId.includes(item.PHIEU_ID)
        // );
        const dsPhieu = listPhieu.map((item) => ({
          phieuId: item.PHIEU_ID,
          hdtbId: item.HDTB_ID,
        }));
        const body = {
          dsPhieu: dsPhieu,
          huongGiaoId: this.updateForm.huongGiaoId,
        };
        this.huyGiao(body);
      }
    },
    validateDelete() {
      const selected = this.$refs.table.getSelectedRecords();
      if (selected == null || selected.length <= 0) {
        this.$root.toastError("Bạn chưa chọn phiếu!");
        return;
      }
      //   if (this.selectedId.length == 0) {
      //     this.$toast.error("Bạn chưa chọn phiếu!");
      //     return false;
      //   }
      //   const listPhieu = this.dsHDTB.filter((item) =>
      //     this.selectedId.includes(item.PHIEU_ID)
      //   );
      const listPhieu = selected;
      listPhieu.every((item) => {
        if (item.LOAIPT_ID == 3) {
          this.$toast.error(
            `Phiếu ${item.MA_TB} đang ở trạng thái không thi công được.\n Bạn hãy hủy phiếu trong chức năng thoái trả hợp đồng!`
          );
          return false;
        }

        if (item.TTPH_ID == 2 || item.TTPH_ID == 4) {
          this.$toast.error(
            `Phiếu ${item.MA_TB} đang được xử lý. Không được hủy phiếu này`
          );
          return false;
        }

        if (item.TTPH_ID == 2 || item.TTPH_ID == 4) {
          this.$toast.error(
            `Phiếu ${item.MA_TB} đang được xử lý. Không được hủy phiếu này`
          );
          return false;
        }

        if (item.TTPH_ID == 3) {
          this.$toast.error(
            `Phiếu đã được trả lại do giao sai.\n Bạn hãy vào chức năng nhận phiếu thi công để thực hiện.`
          );
          return false;
        }
      });

      return true;
    },
    onClickExport() {
      if (this.dsHDTB.length > 0) {
        this.$bvModal.show("modal-export");
      } else {
        this.$toast.error("Không có dữ liệu để xuất excel");
      }
    },
    getLoaiHD: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-thuno/api/thu-no/css/ds-loai-hop-dong"
        );
        this.dsLoaiHD = rs.data.sort(function (a, b) {
          return a.LOAIHD_ID - b.LOAIHD_ID;
        });
        if (rs.data.length > 0) {
          this.searchForm.loaiHDId = rs.data[0].LOAIHD_ID;
        }
      } catch (error) { }
    },
    dsDichVuDropDown: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-thuno/api/thu-no/common/dich-vu-vien-thong"
        );
        this.dsDichVu = rs.data.sort(function (a, b) {
          return a.DICHVUVT_ID - b.DICHVUVT_ID;
        });
        if (rs.data.length > 0) {
          this.searchForm.dichVuId = rs.data[0].DICHVUVT_ID;
        }
      } catch (error) { }
    },
    dsQuyTrinhDropDown: async function () {
      const param = {
        loaiHDId: this.searchForm.ckbLoaiHD ? this.searchForm.loaiHDId : 0,
        dichVuVTId: this.searchForm.ckbDichVu ? this.searchForm.dichVuId : 0,
        TTHDId: 1,
      };
      try {
        var rs = await this.$root.context.get(
          "/web-thuno/api/thu-no/xu-ly-thue-bao/ds-quy-trinh",
          param
        );
        this.dsQuyTrinh = rs.data;
        if (rs.data.length > 0) {
          this.searchForm.quyTrinhId = rs.data[0].QUYTRINH_ID;
        }
      } catch (error) { }
    },
    getHuongGiao: async function () {
      const param = {
        nhomQTId: 1,
      };
      if (this.searchForm.ckbQuyTrinh)
        param.quyTrinhId = this.searchForm.quyTrinhId;
      if (this.searchForm.ckbLoaiHD) param.loaiHDId = this.searchForm.loaiHDId;
      if (this.searchForm.ckbDichVu)
        param.dichVuVTId = this.searchForm.dichVuId;
      // console.log(param);
      try {
        var rs = await this.$root.context.get(
          "/web-thuno/api/thu-no/tra-cuu/ds-huong-giao",
          param
        );
        this.dsHuongGiao = rs.data;
        if (rs.data.length > 0) {
          if (this.data.huonggiao_id != null)
            this.updateForm.huongGiaoId = this.data.huonggiao_id;
          else
            this.updateForm.huongGiaoId = rs.data[0].HUONGGIAO_ID;
          this.getDvNhan();
          this.onClickSearch();
        }
      } catch (error) {
        console.log("err", error);
      }
    },
    getDvNhan: async function () {
      try {
        var rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/tra-cuu/don-vi-dich/huong-giao/${this.updateForm.huongGiaoId}`
        );
        this.dsDonViNhan = rs.data;
        if (rs.data.length > 0) {
          this.updateForm.donViNhanId = rs.data[0].DONVI_ID;
        }
      } catch (error) { }
    },
    getChiTietHuongGiao: async function () {
      try {
        var rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/tra-cuu/chi-tiet-huong-giao/${this.updateForm.huongGiaoId}`
        );
        if (rs.data.length > 0) {
          const sp = rs.data[0].SOPHIEU;
          if (sp)
            this.updateForm.soPhieu = `${sp}${moment(
              this.updateForm.ngayGiao
            ).format("dd.MM.yy")}`;
        }
      } catch (error) { }
    },
    getDsHDTB: async function (url, param) {
      this.title = "00:00:00";
      this.$root.showLoading(true);
      let begin = new Date();
      try {
        var rs = await this.$root.context.get(url, param);
        let end = new Date();
        
        this.times = moment.utc(end - begin).format("HH:mm:ss");
        this.dsHDTB = rs.data;
        if (rs.data.length > 0) {
        } else {
          this.resetTextForm();
          //   this.$root.toastError("Không có dữ liệu. Mời thử lại!");
        }
        this.$root.showLoading(false);
      } catch (error) {
        //console.log(error.response.data.message_detail);
        this.$root.toastError(error.response.data.message_detail);
        this.$root.showLoading(false);
      }
    },
    getLoaiDVDich: async function () {
      try {
        var rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/common/loai-dich-vu/huong-giao/${this.updateForm.huongGiaoId}`
        );
        if (rs.data.length > 0) {
          return rs.data[0].LOAIDV_DICH_ID;
        }
        return "";
      } catch (error) { }
    },
    giaoPhieu: async function () {
      const listPhieu = this.$refs.table.getSelectedRecords();
      //   const listPhieu = this.dsHDTB.filter((item) =>
      //     this.selectedId.includes(item.PHIEU_ID)
      //   );
      const ds = listPhieu.map((item) => ({
        phieuId: item.PHIEU_ID,
        hdtbId: item.HDTB_ID,
        tthdId: item.TTHD_ID,
        donViNhanId: item.DONVINHAN_ID,
        dichVuVTId: item.DICHVUVT_ID,
        kieuDVId: item.KIEUDV_ID ? 1 : 0,
        autoGV: item.AUTO_GV ? 1 : 0,
        autoGiaoOMC: item.AUTO_GIAO_OMC ? 1 : 0,
        dcDauCuoi: item.DC_DAUCUOI ? 1 : 0
      }));
      const body = {
        ds: ds,
        donViId: this.$auth.getDonViID(),
        nhanVienId: this.$auth.getNhanVienID(),
        huongGiaoId: this.updateForm.huongGiaoId,
        soPhieu: this.updateForm.soPhieu,
        noiDungGiao: this.updateForm.noiDungGiao,
        ktDauCuoi: this.kt_daucuoi,
        giaoViecTD: 0,
        huongGiaoTruoc: this.updateForm.huongGiaoId,
        // kieuDVId: 0,
        // autoGV: 0,
        // dcDauCuoi: 0,
      };
      console.log(body);
      this.$root.showLoading(true);
      try {
        const { data } = await this.axios.put(
          "web-thuno/api/thu-no/css/giao-phieu-xac-minh-no",
          body
        );
        if (data && data.error == "200") {
          this.$toast.success("Giao phiếu thành công!");
          this.onClickSearch();
          // this.$refs.table.rowClick(0,null);
        } else {
          this.$toast.error("Giao phiếu thất bại!");
        }

      }
      catch (error) {
        if(error.data && error.data.message_detail) {
          this.$toast.error(error.data.message_detail);
        } else {
          this.$toast.error("Giao phiếu thất bại!");
        }
      } finally {
        this.$root.showLoading(false);
      }
    },
    capNhatDonVi: async function (body) {
      //   console.log(body);
      try {
        const { data } = await this.axios.put(
          "web-thuno/api/thu-no/css/cap-nhat-don-vi-nhan",
          body
        );
        if (data && data.error == "200") {
          this.$toast.success("Đã cập nhật lại đơn vị nhận!");
          this.onClickSearch();
        } else {
          this.$toast.error("Cập nhật thông tin thất bại!");
        }
      } catch (error) {
        this.$toast.error("Cập nhật thông tin thất bại!");
      }
    },
    huyGiao: async function (body) {
      this.$root.showLoading(true);
      try {
        const { data } = await this.axios.delete(
          "web-thuno/api/thu-no/css/huy-giao-phieu-xac-nhan-no",
          { data: body }
        );
        if (data && data.error == "200") {
          this.$toast.success("Đã hủy giao phiếu thành công!");
          this.onClickSearch();
        } else {
          this.$toast.error("Hủy giao phiếu thành thất bại!");
        }
      } catch (error) {
        if (error.data.error_code == 'BSS-00009491') {
          this.$toast.error(error.data.message_detail);
        } else {
          this.$toast.error("Hủy giao phiếu thành thất bại!");
        }
      } finally {
        this.$root.showLoading(false);
      }
    },
    getTag() {


      this.TAG = this.$route.query.TAG;
      if (this.TAG !== null && this.TAG !== undefined) {
        console.log(this.TAG);
        let words = this.TAG.split(';')
        if (words.length >= 1)
          this.ds_tthd_id = words[0];
        if (words.length >= 2)
          this.ds_loaihd_id = words[1]
        if (words.length >= 3)
          this.ds_dichvuvt_id = words[2]

      }
      else {
        console.log('ko tag');
        this.ds_tthd_id = '1';
        this.ds_loaihd_id = '7';
        this.ds_dichvuvt_id = '1';
      }


    },
  },
};
</script>

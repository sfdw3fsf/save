<template>
  <b-modal
    ref="popupSearchContract"
    size="xl"
    body-class="p-0"
    hide-header
    hide-header-close
    @show="handleShowModal"
    hide-footer
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>Tra cứu hợp đồng, phụ lục
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <ActionTop :actions="actions" @onActionClick="onActionClick" />
      <div class="popup-body">
        <ThongTinTimKiem ref="thongtintimkiem" />
        <div class="box-form">
          <div class="legend-title">
            <span class="fa fa-angle-up icon"></span> Danh sách hợp đồng/phụ lục
            khách hàng
          </div>
          <k-table
            :columns="columnsHD"
            :dataSources="dataSourcesHD"
            @onSelectedRow="onSelectedRow"
            :rowSelectedIndex="1"
            :allowFilter="true"
          />
        </div>
        <div class="box-form">
          <div class="legend-title">
            <span class="fa fa-angle-up icon"></span> Danh sách thanh toán
          </div>
          <k-table
            :columns="columnsTT"
            :dataSources="dataSourcesTT"
            :rowSelectedIndex="1"
            :allowFilter="true"
          />
        </div>
        <div class="box-form">
          <div class="legend-title">
            <span class="fa fa-angle-up icon"></span> Danh sách thuê bao
          </div>
          <k-table
            :columns="columnsTB"
            :dataSources="dataSourcesTB"
            :rowSelectedIndex="1"
            :allowFilter="true"
          />
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import Footer from "@/components/FooterStyle.vue";
import breadcrumb from "@/components/kylq_components/breadcrumb.vue";
import KTable from "@/components/kylq_components/KTable.vue";
import ActionTop from "@/components/kylq_components/ActionTop.vue";
import ThongTinTimKiem from "./ThongTinTimKiem.vue";
import SearchContractAPI from "./SearchContractAPI";
import moment from "moment";
import EventBus from "@/utils/eventBus";
export default {
  name: "SearchAccountModal",
  //mặc định loại hợp đồng, dịch vụ, ngày YC, trạng thái HĐ/PL và mã GD
  props: {
    //không truyền -1, disable check loaiHD/PL lấy row đầu và ngược lại
    loai_hd_id: {
      type: Number,
      default: -1
    },
    //Dịch vụ
    dichvu_id: {
      type: Number,
      default: 0
    },
    //ngày yêu cầu string format DD/MM/YYYY default '' checkbox disable
    ngay_yc: {
      type: String,
      default: ""
    },
    //ngày kết thúc string format DD/MM/YYYY default '' checkbox disable
    ngay_kt: {
      type: String,
      default: ""
    },
    //tiêu chí tra cứu id, trắng thì lấy mặc định đầu: tb.ma_tb = ':values' (Số máy)
    tieuchi_id: {
      type: String,
      default: ""
    },
    //thông tin tra cứu có thể: Số máy, mã giao dịch, tên miền phụ thuộc vào tieuchi_id
    thongtin_tracuu: {
      type: String,
      default: ""
    },
    //id trạng thái hợp đồng
    trangthai_hd: {
      type: Number,
      default: 0
    }
  },
  components: {
    breadcrumb,
    Footer,
    KTable,
    ActionTop,
    ThongTinTimKiem
  },
  data() {
    return {
      actions: [
        {
          id: "timkiem",
          name: "Tìm kiếm",
          active: true,
          icon_class: "one-search"
        },
        {
          id: "chapnhan",
          name: "Chấp nhận",
          active: true,
          icon_class: "nc-icon-glyph ui-1_check-circle-08"
        },
        {
          id: "hoso",
          name: "Hồ sơ",
          active: true,
          icon_class: "nc-icon-glyph files_single-content-02"
        },
        {
          id: "inphieu",
          name: "In phiếu TT",
          active: true,
          icon_class: "one-print"
        },
        {
          id: "khuyenmai",
          name: "Khuyến mại",
          active: true,
          icon_class: "one-sale"
        }
      ],
      columnsHD: [
        {
          field: "ma_gd",
          label: "Mã giao dịch",
          allowFilter: true
        },
        {
          field: "ngay_yc",
          label: "Ngày yêu cầu",
          allowFilter: true
        },
        {
          field: "ma_kh",
          label: "Mã khách hàng",
          allowFilter: true
        },
        {
          field: "ten_kh",
          label: "Tên khách hàng",
          allowFilter: true
        },
        {
          field: "diachi_kh",
          label: "Địa chỉ khách hàng",
          allowFilter: true
        },
        {
          field: "so_dt",
          label: "Số liên hệ",
          allowFilter: true
        },
        {
          field: "ten_loaihd",
          label: "Loại HĐ/PL",
          allowFilter: true
        },
        {
          field: "nguoi_cn",
          label: "Người cập nhật",
          allowFilter: true
        }
      ],
      dataSourcesHD: [],
      columnsTT: [
        {
          field: "ma_tt",
          label: "Mã thanh toán",
          allowFilter: true
        },
        {
          field: "matb_dd",
          label: "Mã đại diện",
          allowFilter: true
        },
        {
          field: "ten_tt",
          label: "Tên thanh toán",
          allowFilter: true
        },
        {
          field: "diachi_tt",
          label: "Địa chỉ thanh toán",
          allowFilter: true
        }
      ],
      dataSourcesTT: [],
      columnsTB: [
        {
          field: "ma_tb",
          label: "Số máy/Acc",
          allowFilter: true
        },
        {
          field: "ten_tb",
          label: "Tên thuê bao",
          allowFilter: true
        },
        {
          field: "diachi_ld",
          label: "Địa chỉ lắp đặt",
          allowFilter: true
        },
        {
          field: "diachi_tb",
          label: "Địa chỉ thuê bao",
          allowFilter: true
        },
        {
          field: "ten_dvvt",
          label: "Dịch vụ",
          allowFilter: true
        },
        {
          field: "loaihinh_tb",
          label: "Loại hình",
          allowFilter: true
        },
        {
          field: "trangthai_hd",
          label: "Trạng thái",
          allowFilter: true
        },
        {
          field: "site_id",
          label: "Site ID",
          allowFilter: true
        }
      ],
      dataSourcesTB: [],
      itemSelected: null
    };
  },
  methods: {
    onActionClick(action) {
      if (action.id == "timkiem") {
        this.dataSourcesHD = [];
        this.dataSourcesTT = [];
        this.dataSourcesTB = [];
        let tt = this.$refs.thongtintimkiem.layThongTinTimKiem();
        if (tt.id == "so_ao") {
          this.hopdong_theo_ma_lt(tt.data);
        } else if (tt.id == "site_id") {
          this.hopdong_theo_site_id(tt.data);
        } else if (tt.id == "so_hd_khdn") {
          this.hopdong_theo_so_hd(tt.data);
        } else if (tt.id == "so_pl_khdn") {
          this.hopdong_theo_so_pl(tt.data);
        } else if (tt.id == "ten_mien") {
          this.hopdong_theo_tenmien(tt.data);
        } else {
          this.hopdong_khac(tt.data);
        }
      } else if (action.id == "chapnhan") {
        if (this.itemSelected == null) {
          return;
        } else {
          this.$emit("accept", this.itemSelected);
          this.hideModal();
        }
      }
    },
    async hienThiThongTin(item) {
      this.itemSelected = Object.assign({}, item);
      this.actions[
        this.actions.findIndex(x => x.id == "chapnhan")
      ].active = true;
      EventBus.$emit("search_contract", item);
      this.dataSourcesTT = [];
      this.dataSourcesTB = [];
      await this.ds_hopdong_tt_theo_hdkh_id(item.hdkh_id);
      await this.ds_hopdong_tb_theo_hdkh_id(item.hdkh_id);
    },
    onSelectedRow(item) {
      console.log("onSelectedRow:", item);
      this.hienThiThongTin(item);
    },
    async hopdong_theo_ma_lt(data) {
      this.dataSourcesHD = [];
      try {
        this.loading(true);
        let response = await SearchContractAPI.hopdong_theo_ma_lt(
          this.axios,
          data
        );
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data
        ) {
          response.data.data.forEach(element => {
            element.ngay_yc = moment(element.ngay_yc).format("DD/MM/YYYY");
          });
          this.dataSourcesHD = response.data.data;
          if (this.dataSourcesHD.length > 0) {
            this.onSelectedRow(this.dataSourcesHD[0]);
          } else {
            this.actions[
              this.actions.findIndex(x => x.id == "chapnhan")
            ].active = false;
          }
        } else {
          this.dataSourcesHD = [];
          this.actions[
            this.actions.findIndex(x => x.id == "chapnhan")
          ].active = false;
        }
      } catch (e) {
        this.$alert("Đã xảy ra lỗi", "", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "error"
        });
      } finally {
        this.loading(false);
      }
    },
    async hopdong_theo_site_id(siteId) {
      this.dataSourcesHD = [];
      try {
        this.loading(true);
        let response = await SearchContractAPI.hopdong_theo_site_id(
          this.axios,
          siteId
        );
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data
        ) {
          response.data.data.forEach(element => {
            element.ngay_yc = moment(element.ngay_yc).format("DD/MM/YYYY");
          });
          this.dataSourcesHD = response.data.data;
          if (this.dataSourcesHD.length > 0) {
            this.onSelectedRow(this.dataSourcesHD[0]);
          } else {
            this.actions[
              this.actions.findIndex(x => x.id == "chapnhan")
            ].active = false;
          }
        } else {
          this.dataSourcesHD = [];
          this.actions[
            this.actions.findIndex(x => x.id == "chapnhan")
          ].active = false;
        }
      } catch (e) {
        this.$alert("Đã xảy ra lỗi", "", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "error"
        });
      } finally {
        this.loading(false);
      }
    },
    async hopdong_theo_tenmien(tenMien) {
      this.dataSourcesHD = [];
      try {
        this.loading(true);
        let response = await SearchContractAPI.hopdong_theo_tenmien(
          this.axios,
          tenMien
        );
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data
        ) {
          response.data.data.forEach(element => {
            element.ngay_yc = moment(element.ngay_yc).format("DD/MM/YYYY");
          });
          this.dataSourcesHD = response.data.data;
          if (this.dataSourcesHD.length > 0) {
            this.onSelectedRow(this.dataSourcesHD[0]);
          } else {
            this.actions[
              this.actions.findIndex(x => x.id == "chapnhan")
            ].active = false;
          }
        } else {
          this.dataSourcesHD = [];
          this.actions[
            this.actions.findIndex(x => x.id == "chapnhan")
          ].active = false;
        }
      } catch (e) {
        this.$alert("Đã xảy ra lỗi", "", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "error"
        });
      } finally {
        this.loading(false);
      }
    },
    async hopdong_theo_so_hd(soHD) {
      this.dataSourcesHD = [];
      try {
        this.loading(true);
        let response = await SearchContractAPI.hopdong_theo_tenmien(
          this.axios,
          soHD
        );
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data
        ) {
          response.data.data.forEach(element => {
            element.ngay_yc = moment(element.ngay_yc).format("DD/MM/YYYY");
          });
          this.dataSourcesHD = response.data.data;
          if (this.dataSourcesHD.length > 0) {
            this.onSelectedRow(this.dataSourcesHD[0]);
          } else {
            this.actions[
              this.actions.findIndex(x => x.id == "chapnhan")
            ].active = false;
          }
        } else {
          this.dataSourcesHD = [];
          this.actions[
            this.actions.findIndex(x => x.id == "chapnhan")
          ].active = false;
        }
      } catch (e) {
        this.$alert("Đã xảy ra lỗi", "", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "error"
        });
      } finally {
        this.loading(false);
      }
    },
    async hopdong_theo_so_pl(soPL) {
      this.dataSourcesHD = [];
      try {
        this.loading(true);
        let response = await SearchContractAPI.hopdong_theo_so_pl(
          this.axios,
          soPL
        );
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data
        ) {
          response.data.data.forEach(element => {
            element.ngay_yc = moment(element.ngay_yc).format("DD/MM/YYYY");
          });
          this.dataSourcesHD = response.data.data;
          if (this.dataSourcesHD.length > 0) {
            this.onSelectedRow(this.dataSourcesHD[0]);
          } else {
            this.actions[
              this.actions.findIndex(x => x.id == "chapnhan")
            ].active = false;
          }
        } else {
          this.dataSourcesHD = [];
          this.actions[
            this.actions.findIndex(x => x.id == "chapnhan")
          ].active = false;
        }
      } catch (e) {
        this.$alert("Đã xảy ra lỗi", "", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "error"
        });
      } finally {
        this.loading(false);
      }
    },
    async hopdong_khac(data) {
      this.dataSourcesHD = [];
      try {
        this.loading(true);
        let response = await SearchContractAPI.hopdong_khac(this.axios, data);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data
        ) {
          response.data.data.forEach(element => {
            element.ngay_yc = moment(element.ngay_yc).format("DD/MM/YYYY");
          });
          this.dataSourcesHD = response.data.data;
          if (this.dataSourcesHD.length > 0) {
            this.onSelectedRow(this.dataSourcesHD[0]);
          } else {
            this.actions[
              this.actions.findIndex(x => x.id == "chapnhan")
            ].active = false;
          }
        } else {
          this.dataSourcesHD = [];
          this.actions[
            this.actions.findIndex(x => x.id == "chapnhan")
          ].active = false;
        }
      } catch (e) {
        this.$alert("Đã xảy ra lỗi", "", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "error"
        });
      } finally {
        this.loading(false);
      }
    },
    async ds_hopdong_tt_theo_hdkh_id(hdkh_id) {
      console.log("1-------------------");
      try {
        this.loading(true);
        this.dataSourcesTT = [];
        let response = await SearchContractAPI.ds_hopdong_tt_theo_hdkh_id(
          this.axios,
          hdkh_id
        );
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data
        ) {
          this.dataSourcesTT = response.data.data;
        } else {
          this.dataSourcesTT = [];
        }
      } catch (e) {
        this.$toast.error("Không load được thông tin thanh toán");
      } finally {
        this.loading(false);
      }
    },
    async ds_hopdong_tb_theo_hdkh_id(hdkh_id) {
      console.log("2-------------------");
      try {
        this.loading(true);
        this.dataSourcesTB = [];
        let response = await SearchContractAPI.ds_hopdong_tb_theo_hdkh_id(
          this.axios,
          hdkh_id
        );
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data
        ) {
          this.dataSourcesTB = response.data.data;
          this.$emit("dataSelectMaGiaoDich", response.data.data[0]);
        } else {
          this.dataSourcesTB = [];
        }
      } catch (e) {
        this.$toast.error("Không load được thông tin thuê bao");
      } finally {
        this.loading(false);
      }
    },
    showModal() {
      this.$refs["popupSearchContract"].show();
    },
    hideModal() {
      this.$refs["popupSearchContract"].hide();
    },
    handleShowModal() {
      //time out 500ms chờ binding view
      setTimeout(() => {
        this.khoiTaoDuLieu();
      }, 500);
    },
    async khoiTaoDuLieu() {
      this.$refs.thongtintimkiem.clearDulieu();
      await this.$refs.thongtintimkiem.initDulieu();
      this.actions[
        this.actions.findIndex(x => x.id == "chapnhan")
      ].active = false;
      //truyền data thông tin mặc định cho tiêu chí tìm kiếm
      //mặc định loại hợp đồng, dịch vụ, ngày YC, trạng thái HĐ/PL và mã GD
      let object = {
        loai_hd_id: this.loai_hd_id,
        dichvu_id: this.dichvu_id,
        ngay_yc: this.ngay_yc,
        ngay_kt: this.ngay_kt,
        tieuchi_id: this.tieuchi_id,
        thongtin_tracuu: this.thongtin_tracuu,
        trangthai_hd: this.trangthai_hd
      };
      this.$refs.thongtintimkiem.initParamSearch(object);

      this.dataSourcesTT = [];
      this.dataSourcesTB = [];
      this.dataSourcesHD = [];
      let tt = this.$refs.thongtintimkiem.layThongTinTimKiem();
      if (tt.id == "so_ao") {
        await this.hopdong_theo_ma_lt(tt.data);
      } else if (tt.id == "site_id") {
        await this.hopdong_theo_site_id(tt.data);
      } else if (tt.id == "so_hd_khdn") {
        await this.hopdong_theo_so_hd(tt.data);
      } else if (tt.id == "so_pl_khdn") {
        await this.hopdong_theo_so_pl(tt.data);
      } else if (tt.id == "ten_mien") {
        await this.hopdong_theo_tenmien(tt.data);
      } else {
        await this.hopdong_khac(tt.data);
      }
    }
  },
  mounted() {}
};
</script>

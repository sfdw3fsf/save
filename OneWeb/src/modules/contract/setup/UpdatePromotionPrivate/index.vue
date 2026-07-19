<template>
  <div>
    <vue-bread-crumb :header="header" />
    <vue-nav>
      <ul class="list">
        <li>
          <a @click="reset()">
            <span class="icon one-file-plus"></span> Nhập mới
          </a>
        </li>
        <li>
          <a @click="themMoiUd">
            <span class="icon one-save"></span> Ghi lại
          </a>
        </li>
        <li>
          <a @click="reset()">
            <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Hủy
          </a>
        </li>
        <li>
          <a> <span class="icon one-excel"></span> Excel mẫu </a>
        </li>
        <li>
          <a> <span class="icon one-xlsx"></span> Nhập Excel </a>
        </li>
      </ul>
    </vue-nav>
    <div class="page-content">
      <vue-card>
        <b-row>
          <b-col md="6">
            <vue-card
              :noborder="true"
              label="Thông tin thuê bao"
              :subLabel="'Trạng thái dữ liệu: ' + lblTrangThaiDL"
            >
              <b-row>
                <b-col md="6">
                  <div class="info-row">
                    <div class="key w85">Dịch vụ VT</div>
                      <SelectExt
                        v-model="current.dichvuvt_id"
                        :dataSource="options.dvvt"
                        dataTextField="NAME"
                        dataValueField="ID"
                        label="Dịch vụ VT"
                      ></SelectExt>
                  </div>
                </b-col>
                <b-col md="6">
                  <div class="info-row">
                    <div class="key w85">Loại hình TB</div>
                    <SelectExt
                      v-model="current.loaitb_id"
                      :dataSource="options.lhtb"
                      dataTextField="NAME"
                      dataValueField="ID"
                      label="Loại hình TB"
                    ></SelectExt>
                  </div>
                </b-col>
                <b-col md="12">
                  <div class="info-row">
                    <div class="key w85">Số máy/Acc</div>
                    <div class="input-more-button">
                      <button @click="SearchDanhBa" class="btn">
                        <span class="-ap icon-more_horiz"></span>
                      </button>
                      <input
                        @keyup.enter="layds_thongtin_hdtb_dc"
                        v-model="current.ma_tb"
                        class="form-control"
                      />
                    </div>
                  </div>
                </b-col>
                <b-col md="12">
                  <vue-input
                    v-model="current.ten_tb"
                    label="Tên TB"
                  ></vue-input>
                </b-col>
                <b-col md="12">
                  <vue-input
                    v-model="current.diachi_ld"
                    label="Địa chỉ TB"
                  ></vue-input>
                </b-col>
                <b-col md="12">
                  <vue-textarea
                    labelWidth="80"
                    v-model="current.ghichu"
                    height="80"
                    label="Ghi chú"
                  ></vue-textarea>
                </b-col>
              </b-row>
            </vue-card>
          </b-col>
          <b-col md="6">
            <vue-card :noborder="true" label="Thông tin ưu đãi">
              <b-row>
                <b-col md="12">
                  <div class="info-row">
                    <div class="key w130">Công văn</div>
                      <SelectExt
                        v-model="current.congvan_id"
                        :dataSource="options.cv"
                        dataTextField="NAME"
                        dataValueField="ID"
                        labelWidth="130"
                        label="Công văn"
                      ></SelectExt>
                  </div>
                </b-col>
                <b-col md="6">
                  <div class="info-row">
                    <div class="key w130">Nguồn KM</div>
                      <SelectExt
                        v-model="current.nguonkm_id"
                        :dataSource="options.nkm"
                        dataTextField="NAME"
                        dataValueField="ID"
                        labelWidth="130"
                        label="Nguồn KM"
                      ></SelectExt>
                  </div>
                </b-col>
                <b-col md="6">
                  <vue-date
                    type="month"
                    format="MM/YYYY"
                    v-model="current.thang_bdshow"
                    labelWidth="130"
                    label="Tháng BĐ"
                  ></vue-date>
                </b-col>
                <b-col md="6">
                  <vue-input
                    labelWidth="130"
                    v-model="current.muccuoc"
                    label="Mức cước trả thêm"
                  ></vue-input>
                </b-col>
                <b-col md="6">
                  <vue-date
                    type="month"
                    format="MM/YYYY"
                    v-model="current.thang_ktshow"
                    labelWidth="130"
                    label="Tháng KT"
                  ></vue-date>
                </b-col>
                <b-col md="6">
                  <vue-input
                    labelWidth="130"
                    v-model="current.tyle_ctb"
                    label="Mức cước TB(%)"
                  ></vue-input>
                </b-col>
                <b-col md="6">
                  <vue-input
                    v-model="current.cuoc_tb"
                    labelWidth="130"
                    label="Cước TB"
                  ></vue-input>
                </b-col>
                <b-col md="6">
                  <vue-input
                    labelWidth="130"
                    v-model="current.tyle_csd"
                    label="Mức cước SD(%)"
                  ></vue-input>
                </b-col>
                <b-col md="6">
                  <vue-input
                    v-model="current.cuoc_sd"
                    labelWidth="130"
                    label="Cước SD"
                  ></vue-input>
                </b-col>
              </b-row>
            </vue-card>
          </b-col>
        </b-row>
      </vue-card>
      <vue-card label="Thông tin tìm kiếm">
        <b-row>
          <b-col md="6">
            <b-row>
              <b-col md="6">
                <div class="item marb10 mart5">
                  <div class="check-action">
                    <input v-model="tttk.kieu" value="0" name="Status" type="radio" class="check" />
                    <span class="name">Tất cả</span>
                  </div>
                </div>
              </b-col>
              <b-col md="6">
                <vue-input v-model="tttk.thang_bd" label="Tháng BD"></vue-input>
              </b-col>
              <b-col md="6">
                <div class="item marb10 mart5">
                  <div class="check-action">
                    <input v-model="tttk.kieu" value="1" name="Status" type="radio" class="check" />
                    <span class="name">Đang có hiệu lực</span>
                  </div>
                </div>
              </b-col>
              <b-col md="6">
                <vue-input v-model="tttk.thang_kt" label="Tháng KT"></vue-input>
              </b-col>
              <b-col md="6">
                <div class="d-flex">
                  <div class="item marb10 mart5">
                    <div class="check-action">
                      <input v-model="tttk.kieu" value="2" name="Status" type="radio" class="check" />
                      <span class="name">{{label}}</span>
                    </div>
                  </div>
                  <vue-input
                    v-model="tttk.nguoi_cn"
                    label="UserCN"
                  ></vue-input>
                </div>
              </b-col>
              <b-col md="6">
                <vue-input v-model="tttk.matb" label="Số máy/Acc"></vue-input>
              </b-col>
            </b-row>
          </b-col>
        </b-row>
      </vue-card>
      <vue-card
        label="Danh sách thuê bao ưu đãi"
        :subLabel="'Tổng số: ' + options.tbud.length + ' thuê bao'"
      >
        <DataGrid 
        @rowSelected="rowSelected"
        :columns="config" :dataSource="options.tbud" :allowPaging="true"
                            ref="tbud" :enablePagingServer="false" panelDataHeight="150" />
      </vue-card>
    </div>
    <ejs-dialog
      :enableResize="true"
      :visible="false"
      :position="position"
      ref="searchAccountDialog"
      :header="''"
      :showCloseIcon="false"
      target="#app"
      width="80%"
      height="800"
    >
      <div class="SubcriberHistory-popup popupBody">
        <searchAccount :isPopup="true" @form-close="searchAccountDialogClose" />
      </div>
    </ejs-dialog>
  </div>
</template>
<script>
import api from "./api";
import searchAccount from "@/modules/search/subscriber/SearchAccount";
import moment from "moment"
export default {
  components: {
    searchAccount,
  },
  data() {
    return {
      header: {
        title: "BỔ SUNG THUÊ BAO ƯU ĐÃI PHỤC VỤ TÍNH CƯỚC",
      },
      lblTrangThaiDL: "",
      position: { X: "center", Y: "top" },
      options: {
        dvvt: [],
        lhtb: [],
        nkm: [],
        cv: [],
        tbud: [],
      },
      current: {},
      tttk: {
        kieu: 1
      },
      kieu_dl : 1,
      config: [
        {
          width: "auto",
          textAlign: "left",
          headerText: "Số máy/Acc",
          allowFiltering: true,
          fieldName: "ma_tb",
        },
        {
          width: "auto",
          textAlign: "left",
          headerText: "Tên thuê bao",
          allowFiltering: true,
          fieldName: "ten_tb",
        },
        {
          width: "auto",
          textAlign: "left",
          headerText: "Loại hình TB",
          allowFiltering: true,
          fieldName: "loaihinh_tb",
        },
        {
          width: "auto",
          textAlign: "left",
          headerText: "Tháng bắt đầu",
          allowFiltering: true,
          fieldName: "thang_bdshow",
        },
        {
          width: "auto",
          textAlign: "left",
          headerText: "Tháng kết thúc",
          allowFiltering: true,
          fieldName: "thang_ktshow",
        },
        {
          width: "auto",
          textAlign: "left",
          headerText: "Số công văn",
          allowFiltering: true,
          fieldName: "congvan_id",
        },
        {
          width: "auto",
          textAlign: "left",
          headerText: "Tỷ lệ CTB",
          allowFiltering: true,
          fieldName: "tyle_ctb",
        },
        {
          width: "auto",
          textAlign: "left",
          headerText: "Cước TB",
          allowFiltering: true,
          fieldName: "cuoc_tb",
        },
        {
          width: "auto",
          textAlign: "left",
          headerText: "Mức cước",
          allowFiltering: true,
          fieldName: "muccuoc",
        },
        {
          width: "auto",
          textAlign: "left",
          headerText: "Tỷ lệ CSD",
          allowFiltering: true,
          fieldName: "tyle_csd",
        },
        {
          width: "auto",
          textAlign: "left",
          headerText: "Tỷ lệ cước IP",
          allowFiltering: true,
          fieldName: "tyle_cuocip",
        },
        {
          width: "auto",
          textAlign: "left",
          headerText: "Tỷ lệ số đẹp",
          allowFiltering: true,
          fieldName: "tyle_sodep",
        },
        {
          width: "auto",
          textAlign: "left",
          headerText: "Tiền số đẹp",
          allowFiltering: true,
          fieldName: "tien_sodep",
        },
        {
          width: "auto",
          textAlign: "left",
          headerText: "Người CN",
          allowFiltering: true,
          fieldName: "nguoi_cn",
        },
      ],
      thang_bd: 0,
      thang_kt: 0,

    };
  },
  watch: {
    "current.dichvuvt_id"(val) {
      this.getDanhSachLHTB(val);
    },
    tttk: {
      handler() {
        this.getDanhSachTBUD(this.tttk);
      },
      deep: true,
    },
  },
  async mounted() {
    this.loading(true)
      await this.getDanhSachDVVT();
      await this.getDanhSachNKM();
      await this.getDanhSachCV();
      await this.getDanhSachTBUD(this.tttk);
      await this.getDanhSachLHTB(this.current.DICHVU);
      this.vthang_bd = Number(moment().format("yyyyMM"));
      this.vthang_kt = Number(moment().format("yyyyMM"));
    this.loading(false)
  },
  methods: {
    rowSelected() {
      let row = this.$refs.tbud.getSelectedRecords()
      if(row.length > 0)
        this.current = row[0]
    },
    SearchDanhBa: function () {
      this.$refs.searchAccountDialog.show();
    },
    searchAccountDialogClose(data) {
      console.log(data);
      if (data != null) {
        this.current.ma_tb = data.ma_tb;
        this.layds_thongtin_hdtb_dc();
      }
      this.$refs.searchAccountDialog.hide();
    },
    reset() {
      this.current = {};
    },
    getDanhSachDVVT() {
      return api.getDanhSachDVVT(this.axios).then((res) => {
        this.options.dvvt = res.data.data ? res.data.data : [];
      });
    },
    getDanhSachLHTB(dvvt_id) {
      return api.getDanhSachLHTB(this.axios, { dvvt_id }).then((res) => {
        this.options.lhtb = res.data.data ? res.data.data : [];
      });
    },
    getDanhSachNKM() {
      return api.getDanhSachNKM(this.axios).then((res) => {
        this.options.nkm = res.data.data ? res.data.data : [];
      });
    },
    getDanhSachCV() {
      return api.getDanhSachCV(this.axios).then((res) => {
        this.options.cv = res.data.data ? res.data.data : [];
      });
    },
    getDanhSachTBUD(data) {
      let search = {
        vdichvuvt_id : this.current.dichvuvt_id ? this.current.dichvuvt_id : "",
        vmatb: this.tttk.matb ? this.tttk.matb : "",
        vloaitb_id : this.current.loaitb_id ? this.current.loaitb_id : "",
        vthang_bd : this.tttk.thang_bd ? this.tttk.thang_bd : "",
        vthang_kt : this.tttk.thang_kt ? this.tttk.thang_kt : "",
        nguoi_cn : this.tttk.nguoi_cn ? this.tttk.nguoi_cn : "",
        vkieu : this.tttk.kieu ? this.tttk.kieu : ""
      }
      return api
        .getDanhSachTBUD(this.axios, search)
        .then((res) => {
          this.options.tbud = res.data.data ? res.data.data : [];
        })
        .finally(() => {
        });
    },
    themMoiUd() {
      api
        .themMoiUd(this.axios, {
          thuebao_id: this.current.thuebao_id,
          ma_tb: this.current.ma_tb,
          dvvt_id: this.current.dichvuvt_id,
          loaitb_id: this.current.loaitb_id,
          thang_bd: this.current.thang_bdshow.replaceAll("/", ""),
          thang_kt: this.current.thang_ktshow.replaceAll("/", ""),
          tyle_ctb: this.current.tyle_ctb,
          cuoc_tb: this.current.cuoc_tb,
          tyle_csd: this.current.tyle_csd,
          cuoc_sd: this.current.cuoc_sd,
          tyle_sodep: this.current.tyle_sodep,
          tien_sodep: this.current.tien_sodep,
          tyle_cuocip: this.current.tyle_cuocip,
          cuoc_ip: this.current.cuoc_ip,
          muccuoc: this.current.muccuoc,
          congvan_id: this.current.congvan_id,
          ghichu: this.current.ghichu,
          may_cn: this.$root.token.getMaNhanVien(),
          ma_nd: this.$root.token.getNguoiDungID(),
          nguonkm_id: this.current.nguonkm_id,
        })
        .then((res) => {
          this.getDanhSachTBUD(this.tttk);
          if(res.data.error_code == "BSS-00000000") {
            this.$toast.success(res.data.message);
          } else {
            this.$toast.error(res.data.message);
          }
        });
    },
    async layds_thongtin_hdtb_dc() {
      return new Promise(async (resolve, reject) => {
        let data = await this.axios.post("web-hopdong/phattrienthuebao/lay_danhba_theo_matb", {
          ma_tb: this.current.ma_tb,
          dichvuvt_id: this.current.dichvuvt_id
        })

        if(data.data && data.data.data.length > 0) {
          this.kieu_dl = 1
          this.lblTrangThaiDL = data.data.data[0].trangthai_tb;
          this.HienThiTTKH(data.data.data)
        } else {
          data = await api
          .layds_thongtin_hdtb_dc(this.axios, {
            dichvuvt_id: this.current.dichvuvt_id,
            ma_tb: this.current.ma_tb
          })
          if(data.data.data && data.data.data.length <= 0) {
              this.alertMessage("Không tìm thấy thông tin thuê bao " + this.current.ma_tb + " đối với dịch vụ " + (this.current.loaitb_id ? this.options.lhtb.filter(item => item.ID == this.current.loaitb_id)[0].NAME : ''))
          } else {
            this.kieu_dl = 2
            this.lblTrangThaiDL = "Thuê bao đang được lập hợp đồng/phụ lục Lắp đặt mới!";
            this.HienThiTTKH(data.data.data)
          }
        }
        resolve(true)
      })
      
    },
    HienThiTTKH(ds) {
      if(ds.length > 0) {
        if(this.kieu_dl == 1) {
          this.current.thuebao_id = ds[0].thuebao_id
        }
        this.current.ten_tb = ds[0].ten_tb
        this.current.diachi_ld = ds[0].diachi_ld
        this.current.loaitb_id = ds[0].loaitb_id
      }
    },
    alertMessage(message) {
      this.$toast.error(message);
    },
  },
};
</script>
<style>
.toast:not(.showing):not(.show) {
  opacity: 1 !important;
}
</style>

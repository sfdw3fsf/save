<template>
  <div class="box-form">
    <div class="legend-title">
      <span class="icon fa fa-angle-up"></span> Loại hình, tốc độ, mức cước
    </div>
    <div class="box-move-select-table" style="height: calc(100% - 40px)">
      <div class="box-col fullh">
        <div class="title-bg-main">
          <span class="title"> Đã gán (*) </span>
        </div>
        <k-table-custom
          :columns="columnsGan"
          :dataSources="cp_dataSourcesGan"
          @dataCheckeds="onDataCheckedsGan"
          @onSelectedRow="onSelectedRow"
          :rowSelectedIndex="1"
          :allowFilter="true"
          :allowCheckBox="true"
          :tableHeight="400"
        />
      </div>
      <div class="actions">
        <button class="btn" @click="boGan">
          <span class="fa fa-angle-right"></span>
        </button>

        <button class="btn" @click="gan">
          <span class="fa fa-angle-left"></span>
        </button>
      </div>
      <div class="box-col">
        <div class="title-bg-main">
          <span class="title">Chưa gán</span>
        </div>
        <div class="row" style="height: calc(100% - 40px)">
          <div class="col-sm-6 col-12 fullh">
            <k-table
              :columns="columnsChuaGan"
              :dataSources="dataSourcesChuaGan"
              @dataCheckeds="onDataCheckedsChuaGan"
              :allowFilter="true"
              :allowCheckBox="true"
              :tableHeight="400"
            />
          </div>
          <div class="col-sm-6 col-12 fullh ovauto">
            <div class="info-row">
              <div class="key w80">Dịch vụ VT</div>
              <div class="value">
                <ejs-dropdownlist
                  :allowFiltering="true"
                  v-model="dichvu_vt_selected"
                  @change="changeDichVuVT"
                  :filtering="onFilteringDVVT"
                  :dataSource="danhsach_dichvu_vt"
                  :fields="{ text: 'TEN_DVVT', value: 'DICHVUVT_ID' }"
                />
              </div>
            </div>
            <div class="info-row">
              <div class="key w80">Loại hình TB</div>
              <div class="value">
                <ejs-dropdownlist
                  :allowFiltering="true"
                  v-model="loaihinh_tb_selected"
                  @change="changeLoaiHinhTB"
                  :filtering="onFilteringLHTB"
                  :dataSource="danhsach_loaihinhtb"
                  :fields="{ text: 'loaihinh_tb', value: 'loaitb_id' }"
                />
              </div>
            </div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w80">SL Từ (*)</div>
                  <div class="value">
                    <input
                      type="text"
                      v-model="thongtin_chuagan.sl_tu"
                      @keypress="isNumber($event)"
                      class="form-control tright"
                    />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w80">SL đến (*)</div>
                  <div class="value">
                    <input
                      type="text"
                      v-model="thongtin_chuagan.sl_den"
                      @keypress="isNumber($event)"
                      class="form-control tright"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w80">TG từ (*)</div>
                  <div class="value">
                    <input
                      type="text"
                      v-model="thongtin_chuagan.tg_tu"
                      @keypress="isNumber($event)"
                      class="form-control tright"
                    />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w80">TG đến (*)</div>
                  <div class="value">
                    <input
                      type="text"
                      v-model="thongtin_chuagan.tg_den"
                      @keypress="isNumber($event)"
                      class="form-control tright"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w80">Giảm TB (*)</div>
                  <div class="value">
                    <input
                      type="text"
                      v-model="thongtin_chuagan.giam_tb"
                      @keypress="isNumber($event)"
                      class="form-control tright"
                    />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w80">Giảm SD (*)</div>
                  <div class="value">
                    <input
                      type="text"
                      v-model="thongtin_chuagan.giam_sd"
                      @keypress="isNumber($event)"
                      class="form-control tright"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w80">Tiền gói</div>
                  <div class="value">
                    <input
                      type="text"
                      v-model="cp_tien"
                      @keypress="isNumber($event)"
                      class="form-control tright"
                    />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w80">Vat gói</div>
                  <div class="value">
                    <input
                      type="text"
                      v-model="cp_vat"
                      @keypress="isNumber($event)"
                      class="form-control tright"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w80">Cước TB</div>
                  <div class="value">
                    <input
                      type="text"
                      v-model="thongtin_chuagan.cuoc_tb"
                      @keypress="isNumber($event)"
                      class="form-control tright"
                    />
                    <!-- <div class="input-icon-right">
                      <input
                        type="text"
                        v-model="thongtin_chuagan.cuoc_tb"
                        @keypress="isNumber($event)"
                        class="form-control tright"
                      />
                      <span class="icon one-caculator"></span>
                    </div> -->
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w80">Cước SD</div>
                  <div class="value">
                    <input
                      type="text"
                      v-model="thongtin_chuagan.cuoc_sd"
                      @keypress="isNumber($event)"
                      class="form-control tright"
                    />
                    <!-- <div class="input-icon-right">
                      <input
                        type="text"
                        v-model="thongtin_chuagan.cuoc_sd"
                        @keypress="isNumber($event)"
                        class="form-control tright"
                      />
                      <span class="icon one-caculator"></span>
                    </div> -->
                  </div>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w80">Gói đã TG</div>
              <div class="value">
                <ejs-dropdownlist
                  :allowFiltering="true"
                  v-model="goitg_selected"
                  :filtering="onFilteringGoiTG"
                  :dataSource="danhsach_goitg"
                  :fields="{ text: 'ten_goi', value: 'goi_id' }"
                />
              </div>
            </div>
            <div class="info-row">
              <div class="key w80">Gói VNP</div>
              <div class="value">
                <ejs-dropdownlist
                  :allowFiltering="true"
                  v-model="goivnp_selected"
                  :filtering="onFilteringGoiVNP"
                  :dataSource="danhsach_goivnp"
                  :fields="{ text: 'package_name', value: 'post_package_id' }"
                  :enabled="cboPackageId"
                />
              </div>
            </div>
            <div class="list-checks">
              <div class="item">
                <div class="check-action">
                  <input
                    type="checkbox"
                    v-model="thongtin_chuagan.checkDongBoDL"
                    class="check"
                  />
                  <span class="name">Đồng bộ dữ liệu</span>
                </div>
              </div>
              <div class="item">
                <div class="check-action">
                  <input
                    type="checkbox"
                    v-model="thongtin_chuagan.checkChuNhom"
                    class="check"
                  />
                  <span class="name">Chủ nhóm</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import KTable from "@/components/kylq_components/KTable.vue";
import KTableCustom from "./component/KTableCustom.vue";
import MultiServicePackageAPI from "./MultiServicePackageAPI";

import {
  DataManager,
  Query,
  ODataV4Adaptor,
  Predicate,
} from "@syncfusion/ej2-data";
import { formatCurrencyNoVND } from '../../../../utils/format';
export default {
  name: "LoaiHinhTocDoMucCuoc",
  props: {
    goi_id: {
      type: Number,
      default: 0,
    },
  },
  components: {
    KTable,
    KTableCustom,
  },
  data() {
    return {
      columnsChuaGan: [
        {
          field: "thuonghieu",
          label: "Tốc độ",
          allowFilter: true,
        },
        {
          field: "tenmuc",
          label: "Mức cước",
          allowFilter: true,
        },
        {
          field: "rg_id",
          label: "ID",
          allowFilter: true,
        },
      ],
      dataSourcesChuaGan: [],
      columnsGan: [
        {
          field: "rg_id",
          label: "ID",
          allowEdit: false,
          allowFilter: true,
          isCheckColumn: false,
        },
        {
          field: "loaihinh_tb",
          label: "Loại hình",
          allowEdit: false,
          allowFilter: true,
          isCheckColumn: false,
        },
        {
          field: "thuonghieu",
          label: "Tốc độ",
          allowEdit: false,
          allowFilter: true,
          isCheckColumn: false,
        },
        {
          field: "tenmuc",
          label: "Mức cước",
          allowEdit: false,
          allowFilter: true,
          isCheckColumn: false,
        },
        {
          field: "sl_tu",
          label: "SL từ",
          allowEdit: true,
          allowFilter: true,
          isCheckColumn: false,
        },
        {
          field: "sl_den",
          label: "SL đến",
          allowEdit: true,
          allowFilter: true,
          isCheckColumn: false,
        },
        {
          field: "tg_tu",
          label: "TG từ",
          allowEdit: true,
          allowFilter: true,
          isCheckColumn: false,
        },
        {
          field: "tg_den",
          label: "TG đến",
          allowEdit: true,
          allowFilter: true,
          isCheckColumn: false,
        },
        {
          field: "giamcuoc_tb",
          label: "Giảm TB",
          allowEdit: true,
          allowFilter: true,
          isCheckColumn: false,
        },
        {
          field: "giamcuoc_sd",
          label: "Giảm SD",
          allowEdit: true,
          allowFilter: true,
          isCheckColumn: false,
        },        
        {
          field: "chunhom",
          label: "Chủ nhóm",
          allowEdit: true,
          allowFilter: true,
          isCheckColumn: true,
        },
        {
          field: "dongbo_dl",
          label: "Đồng bộ",
          allowEdit: true,
          allowFilter: true,
          isCheckColumn: true,
        },
        {
          field: "tien_goi",
          label: "Tiền gói",
          allowEdit: true,
          allowFilter: true,
          format:formatCurrencyNoVND
        },
        {
          field: "vat_goi",
          label: "VAT",
          allowEdit: true,
          allowFilter: true,
          format:formatCurrencyNoVND
    
        },
        {
          field: "cuoc_tb",
          label: "Cước TB",
          allowEdit: true,
          allowFilter: true,
          format:formatCurrencyNoVND

        },
        {
          field: "cuoc_sd",
          label: "Cước SD",
          allowEdit: true,
          allowFilter: true,
          format:formatCurrencyNoVND
        },
        {
          field: "ten_goi",
          label: "Gói đã TG",
          allowEdit: false,
          allowFilter: true,
        },
      ],
      dsGan_init: [],
      dataSourcesGan: [],
      danhsach_dichvu_vt: [],
      dichvu_vt_selected: null,
      danhsach_loaihinhtb: [],
      loaihinh_tb_selected: null,
      isFirstChangeDVVT: true,
      danhsach_goitg: [],
      goitg_selected: null,
      isFirstChangeLoaiHTB: true,
      cboPackageId: false, //
      danhsach_goivnp: [],
      goivnp_selected: null,
      thongtin_chuagan: {
        sl_tu: "0",
        sl_den: "0",
        tg_tu: "0",
        tg_den: "0",
        giam_tb: "0",
        giam_sd: "0",
        tiengoi: "0",
        vatgoi: "0",
        cuoc_tb: "0",
        cuoc_sd: "0",
        checkDongBoDL: false,
        checkChuNhom: false,
      },
      goitichhop_id: 0, // this.goi_id,
      dataCheckGan: [],
      dataCheckChuaGan: [],
    };
  },
  methods: {
    onDataCheckedsChuaGan(data) {
      this.dataCheckChuaGan = data;
      this.$emit("onCheckedsChuaGan", data);
    },
    onDataCheckedsGan(data) {
      this.dataCheckGan = data;      
      this.$emit("onCheckedsGan", data);
    },
    async boGan() {
      if (this.dataCheckGan.length == 0) {
        return;
      }
      //Kiểm tra xem RG ID xem đã có hướng gọi gán vào.
      var str = this.dataCheckGan[0].rg_id;
      if (this.dataCheckGan.length > 1) {
        this.dataCheckGan.forEach((e) => {
          str += "," + e.rg_id;
        });
      }
      var check = await this.KT_RG_ID_Huonggoi(str);
      if (check) return;

      this.dataCheckGan.forEach((item) => {
        this.dataSourcesChuaGan.push(item);
        var index = this.dataSourcesGan.findIndex((x) => x == item);
        if (index > -1) {
          this.dataSourcesGan.splice(index, 1);
        }
      });
      this.$emit("onSelectedBtnBoGan", {
        dataSourcesGan: this.dataSourcesGan,
        dataCheckGan: this.dataCheckGan,
      });
      this.dataCheckGan = [];
      this.dataCheckChuaGan = [];
    },
    async gan() {
      try {
        if (this.dataCheckChuaGan.length == 0) {
          return;
        }

        if (this.thongtin_chuagan.sl_tu.trim() == "") {
          this.$alert("Chưa nhập số lượng thuê bao từ !", "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "info",
          });
          return;
        }
        if (this.thongtin_chuagan.sl_den.trim() == "") {
          this.$alert("Chưa nhập số lượng thuê bao đến !", "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "info",
          });
          return;
        }
        if (this.thongtin_chuagan.tg_tu.trim() == "") {
          this.$alert("Chưa nhập thời gian từ !", "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "info",
          });
          return;
        }
        if (this.thongtin_chuagan.tg_den.trim() == "") {
          this.$alert("Chưa nhập thời gian đến !", "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "info",
          });
          return;
        }
        if (this.thongtin_chuagan.giam_tb.trim() == "") {
          this.$alert("Chưa nhập thời gian đến !", "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "info",
          });
          return;
        }
        if (this.thongtin_chuagan.giam_sd.trim() == "") {
          this.$alert("Chưa nhập tỷ lệ giảm cước sử dụng !", "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "info",
          });
          return;
        }
        var loaihinh = this.danhsach_loaihinhtb.find(
          (x) => x.loaitb_id == this.loaihinh_tb_selected
        );
        var tengoi = this.danhsach_goitg.findIndex(
          (x) => x.goi_id == this.goitg_selected
        );
        for (var i = 0; i < this.dataCheckChuaGan.length; i++) {
          var item = this.dataCheckChuaGan[i];
          var in_rg_id = Number(await this.getkey("RG_ID"));
          
          var row = Object.assign(item, {
            rg_id: !item.rg_id || item.rg_id == 0 ? in_rg_id : item.rg_id,
            sl_tu: Number(this.thongtin_chuagan.sl_tu),
            tien_goi: Number(this.thongtin_chuagan.tiengoi),
            tg_den: Number(this.thongtin_chuagan.tg_den),
            chunhom: this.thongtin_chuagan.checkChuNhom,
            goi_id_datg: this.goitg_selected,
            giamcuoc_sd: Number(this.thongtin_chuagan.giam_sd),
            cuoc_sd: Number(this.thongtin_chuagan.cuoc_sd),
            dongbo_dl: this.thongtin_chuagan.checkDongBoDL,
            dichvuvt_id: this.dichvu_vt_selected,
            giamcuoc_tb: Number(this.thongtin_chuagan.giam_tb),
            loaihinh_tb: loaihinh != undefined ? loaihinh.loaihinh_tb : "",
            ten_goi: tengoi != undefined ? tengoi.ten_goi : "",
            loaitb_id: this.loaihinh_tb_selected,
            sl_den: Number(this.thongtin_chuagan.sl_den),
            cuoc_tb: Number(this.thongtin_chuagan.cuoc_tb),
            tg_tu: Number(this.thongtin_chuagan.tg_tu),
            vat_goi: Number(this.thongtin_chuagan.vatgoi),
          });
          this.dataSourcesGan.push(row);
          var index = this.dataSourcesChuaGan.findIndex((x) => x == item);
          if (index > -1) {
            this.dataSourcesChuaGan.splice(index, 1);
          }
        }
        this.$emit("onSelectedBtnGan", {
          dataSourcesGan: this.dataSourcesGan,
          dataCheckChuaGan: this.dataCheckChuaGan,
        });
        this.dataCheckGan = [];
        this.dataCheckChuaGan = [];
      } catch (e) {
        
      }
    },
    KT_RG_ID_Huonggoi: async function (strRgID) {
      try {
        var input = {
          typr: 1,
          param: strRgID,
        };
        var response = await MultiServicePackageAPI.KT_RG_ID_Huonggoi(
          this.axios,
          input
        );
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000"
        ) {
          if (response.data.data.length > 0) {
            response.data.data.forEach((e) => {
              this.ShowError(
                "ID: " +
                  e.rg_id +
                  " đã được gán hướng gọi. Bạn hãy kiểm tra lại!"
              );
            });

            return true;
          }
        } else if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000204"
        ) {
          return false;
        } else {
          this.ShowError(response.data.message_detail);
          return true;
        }
      } catch (e) {
        if (e.data) {
          this.ShowError(e.data.message_detail);
        } else {
          this.ShowError(
            "Có lỗi khi kiểm tra ràng buộc hướng gọi loại hình thuê bao!"
          );
        }
        return true;
      }
    },
    changeDichVuVT() {
      if (this.dichvu_vt_selected == null) {
        return;
      }
      if (this.isFirstChangeDVVT) {
        this.isFirstChangeDVVT = false;
        return;
      }
      var tengoi = this.danhsach_dichvu_vt.filter(
        (e) => e.DICHVUVT_ID == this.dichvu_vt_selected
      )[0].TEN_DVVT;
      if (tengoi.toLowerCase() == "di động") {
        this.cboPackageId = true;
      } else {
        this.cboPackageId = false;
        if (this.danhsach_goivnp.length > 0)
          this.goivnp_selected = this.danhsach_goivnp[0].post_package_id;
      }
      this.loaihinh_tb_selected = null;
      this.sp_cbb_lhtb(this.dichvu_vt_selected);
    },
    async changeLoaiHinhTB() {
      if (this.loaihinh_tb_selected == null) {
        return;
      }
      if (this.isFirstChangeLoaiHTB) {
        this.isFirstChangeLoaiHTB = false;
        return;
      }
      this.goitg_selected = null;
      await this.sp_cbb_goidatg(this.loaihinh_tb_selected);
      await this.hienthi_loaihinh();      
    },
    isNumber(evt) {
      evt = evt ? evt : window.event;
      var charCode = evt.which ? evt.which : evt.keyCode;
      if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        evt.preventDefault();
      } else {
        return true;
      }
    },
    getDsLoaiHinhGan() {
      return this.dataSourcesGan;
    },
    onFilteringDVVT(e) {
      var query = new Query();
      var predicate;
      //frame the query based on search string with filter type.
      if (e.text !== "") {
        predicate = new Predicate("TEN_DVVT", "contains", e.text, true);
        //predicate = predicate.or('TEN_KHO', 'contains', e.text);
        query.where(predicate);
      }
      //pass the filter data source, filter query to updateData method.
      e.updateData(this.danhsach_dichvu_vt, query);
    },
    onFilteringLHTB(e) {
      var query = new Query();
      var predicate;
      //frame the query based on search string with filter type.
      if (e.text !== "") {
        predicate = new Predicate("loaihinh_tb", "contains", e.text, true);
        //predicate = predicate.or('TEN_KHO', 'contains', e.text);
        query.where(predicate);
      }
      //pass the filter data source, filter query to updateData method.
      e.updateData(this.danhsach_loaihinhtb, query);
    },
    onFilteringGoiTG(e) {
      var query = new Query();
      var predicate;
      //frame the query based on search string with filter type.
      if (e.text !== "") {
        predicate = new Predicate("ten_goi", "contains", e.text, true);
        //predicate = predicate.or('TEN_KHO', 'contains', e.text);
        query.where(predicate);
      }
      //pass the filter data source, filter query to updateData method.
      e.updateData(this.danhsach_goitg, query);
    },
    onFilteringGoiVNP(e) {
      var query = new Query();
      var predicate;
      //frame the query based on search string with filter type.
      if (e.text !== "") {
        predicate = new Predicate("package_name", "contains", e.text, true);
        //predicate = predicate.or('TEN_KHO', 'contains', e.text);
        query.where(predicate);
      }
      //pass the filter data source, filter query to updateData method.
      e.updateData(this.danhsach_goivnp, query);
    },
    onSelectedRow(item) {
      //chuyển tới form chính xử lý
      this.$emit("onSelectedRowGan", item);
    },
    getGoiVNPID() {
      return this.goivnp_selected;
    },
    async lay_danhsach_dichvu_vt() {
      try {
        this.loading(true);
        this.danhsach_dichvu_vt = [];
        var response = await MultiServicePackageAPI.lay_danhsach_dichvu_vt(
          this.axios
        );
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data
        ) {
          this.danhsach_dichvu_vt = response.data.data;
          if (this.danhsach_dichvu_vt.length > 0) {
            this.dichvu_vt_selected = 1;
          }
        } else {
          this.danhsach_dichvu_vt = [];
        }
      } catch (e) {
        this.$toast.error("Không load được danh sách dịch vụ viễn thông");
      } finally {
        this.loading(false);
      }
    },
    async sp_cbb_lhtb(dichvuvt_id) {
      try {
        this.loading(true);
        this.danhsach_loaihinhtb = [];
        var response = await MultiServicePackageAPI.sp_cbb_lhtb(
          this.axios,
          dichvuvt_id
        );
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data
        ) {
          this.danhsach_loaihinhtb = response.data.data;
          if (this.danhsach_loaihinhtb.length > 0) {
            this.loaihinh_tb_selected = this.danhsach_loaihinhtb[0].loaitb_id;
          }
        } else {
          this.danhsach_loaihinhtb = [];
        }
      } catch (e) {
        this.$toast.error("Không load được danh sách loại hình thuê bao");
      } finally {
        this.loading(false);
      }
    },
    async sp_cbb_goidatg(loaitb_id) {
      try {
        this.loading(true);
        this.danhsach_goitg = [];
        var response = await MultiServicePackageAPI.sp_cbb_goidatg(
          this.axios,
          loaitb_id
        );
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data
        ) {
          this.danhsach_goitg = response.data.data;
          if (this.danhsach_goitg.length > 0) {
            this.goitg_selected = this.danhsach_goitg[0].goi_id;
          }
        } else {
          this.danhsach_goitg = [];
        }
      } catch (e) {
        this.$toast.error("Không load được danh sách gói");
      } finally {
        this.loading(false);
      }
    },
    async sp_cbb_goidatg(loaitb_id) {
      try {
        this.loading(true);
        this.danhsach_goitg = [];
        var response = await MultiServicePackageAPI.sp_cbb_goidatg(
          this.axios,
          loaitb_id
        );
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data
        ) {
          this.danhsach_goitg = response.data.data;
          if (this.danhsach_goitg.length > 0) {
            this.goitg_selected = this.danhsach_goitg[0].goi_id;
          }
        } else {
          this.danhsach_goitg = [];
        }
      } catch (e) {
        this.$toast.error("Không load được danh sách gói");
      } finally {
        this.loading(false);
      }
    },
    async sp_cbb_goivnp(congvan_id) {
      try {
        this.loading(true);
        this.danhsach_goivnp = [];
        this.goivnp_selected = null;
        var response = await MultiServicePackageAPI.sp_cbb_goivnp(
          this.axios,
          congvan_id
        );
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data
        ) {
          this.danhsach_goivnp = response.data.data;
          if (this.danhsach_goivnp.length > 0) {
            this.goivnp_selected = this.danhsach_goivnp[0].post_package_id;
          }
        } else {
          this.danhsach_goivnp = [];
        }
      } catch (e) {
        this.$toast.error("Không load được danh sách gói VNP");
      } finally {
        this.loading(false);
      }
    },
    async sp_grv_loaihinh(goi_id, dichvuvt_id, loaitb_id, type) {
      try {
        this.loading(true);
        if (type == 0) {
          this.dataSourcesChuaGan = [];
        } else {
          this.dataSourcesGan = [];
          // this.dsGan_init = [];
        }
        var response = await MultiServicePackageAPI.sp_grv_loaihinh(
          this.axios,
          goi_id,
          dichvuvt_id,
          loaitb_id,
          type
        );
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data
        ) {
          if (type == 0) {
            this.dataSourcesChuaGan = response.data.data.map((e) =>
              Object.assign(e, { goi_id: goi_id })
            );
          } else {
            this.dataSourcesGan = response.data.data.map((item) => {
              return Object.assign(item, {
                checked: item.chon && item.chon.toString() == "s1" ? true : false,
                chunhom: item.chunhom && item.chunhom.toString() == "s1" ? true : false,
                dongbo_dl: item.dongbo_dl && item.dongbo_dl.toString() == "s1" ? true : false,
                goi_id: goi_id,
              });
            });
            //init data selected first row
            if (this.dataSourcesGan.length > 0) {
              this.onSelectedRow(this.dataSourcesGan[0]);
            } else {
              //clear 3tab
              this.onSelectedRow(null);
            }
            // this.dsGan_init = response.data.data.map((item) => {
            //   return Object.assign(item, {
            //     chunhom: item.chunhom && item.chunhom.toString() == "s1" ? true : false,
            //     dongbo_dl: item.dongbo_dl && item.dongbo_dl.toString() == "s1" ? true : false,
            //     goi_id: goi_id,
            //   });
            // });
          }
        } else {
          if (type == 0) {
            this.dataSourcesChuaGan = [];
          } else {
            this.dataSourcesGan = [];
            // this.dsGan_init = [];
          }
        }
      } catch (e) {
        this.$toast.error(
          "Không load được danh sách loại hình tốc độ mức cước"
        );
      } finally {
        this.loading(false);
      }
    },
    async hienthi_loaihinh() {
      await this.sp_grv_loaihinh(
        this.goitichhop_id,
        this.dichvu_vt_selected,
        this.loaihinh_tb_selected ? this.loaihinh_tb_selected : 0,
        0
      );
      await this.sp_grv_loaihinh(
        this.goitichhop_id,
        this.dichvu_vt_selected,
        this.loaihinh_tb_selected ? this.loaihinh_tb_selected : 0,
        1
      );
    },
    async initDuLieu() {
      await this.lay_danhsach_dichvu_vt();
      if (this.dichvu_vt_selected != null) {
        await this.sp_cbb_lhtb(this.dichvu_vt_selected);
        if (this.loaihinh_tb_selected != null) {
          await this.sp_cbb_goidatg(this.loaihinh_tb_selected);
          await this.hienthi_loaihinh();
        }
      }
    },
    async clearDuLieu() {
      this.thongtin_chuagan.sl_tu = "0";
      this.thongtin_chuagan.sl_den = "0";
      this.thongtin_chuagan.tg_tu = "0";
      this.thongtin_chuagan.tg_den = "0";
      this.thongtin_chuagan.giam_tb = "0";
      this.thongtin_chuagan.giam_sd = "0";
      this.thongtin_chuagan.tiengoi = "0";
      this.thongtin_chuagan.vatgoi = "0";
      this.thongtin_chuagan.cuoc_tb = "0";
      this.thongtin_chuagan.cuoc_sd = "0";
      // this.dataSourcesGan = [];
      // this.dataSourcesChuaGan = [];
      this.dataCheckGan = [];
      this.dataCheckChuaGan = [];
      // await this.sp_grv_loaihinh(
      //   0,
      //   this.dichvu_vt_selected,
      //   this.loaihinh_tb_selected,
      //   0
      // );
      // await this.sp_grv_loaihinh(
      //   0,
      //   this.dichvu_vt_selected,
      //   this.loaihinh_tb_selected,
      //   1
      // );
    },
    getkey: async function (key) {
      var input = {
        keyname: key,
      };
      var response = await MultiServicePackageAPI.getKey(this.axios, input);
      if (
        response &&
        response.data &&
        response.data.error_code &&
        response.data.error_code == "BSS-00000000" &&
        response.data.data
      ) {
        return response.data.data;
      } else {
        this.$toast.error("Có lỗi trong quá trình tạo key mới");
        return;
      }
    },
    numberWithCommas(n) {
      var parts = n.toString().split(".");
      return (
        parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") +
        (parts[1] ? "." + parts[1] : "")
      );
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg);
    },
    ShowError: function (msg) {
      this.$toast.error(msg);
    },
  },
  computed: {
    cp_tien: {
      get() {
        return this.numberWithCommas(this.thongtin_chuagan.tiengoi);
      },
      set(value) {
        var replaced = value.replaceAll(",", "");
        this.thongtin_chuagan.tiengoi = Number(replaced);
      },
    },
    cp_vat: {
      get() {
        return this.numberWithCommas(this.thongtin_chuagan.vatgoi);
      },
      set(value) {
        var replaced = value.replaceAll(",", "");
        this.thongtin_chuagan.vatgoi = Number(replaced);
      },
    },
    cp_dataSourcesGan: {
      get() {
        var ds = this.dataSourcesGan;
        for (var i = 0; i < ds.length; i++) {
          ds[i]["tien_goi"] = ds[i]["tien_goi"] ? this.numberWithCommas(
            ds[i]["tien_goi"].toString().replaceAll(",", "")
          ) : 0;
          ds[i]["vat_goi"] = ds[i]["tien_goi"] ? this.numberWithCommas(
            Math.round((Number(ds[i]["tien_goi"].toString().replaceAll(",", "")) * 10) / 100)
          ) : 0;
          ds[i]["cuoc_tb"] = ds[i]["cuoc_tb"] ? this.numberWithCommas(
            ds[i]["cuoc_tb"].toString().replaceAll(",", "")
          ) : 0;
          ds[i]["cuoc_sd"] = ds[i]["cuoc_sd"] ? this.numberWithCommas(
            ds[i]["cuoc_sd"].toString().replaceAll(",", "")
          ) : 0;
          ds[i]["giamcuoc_tb"] = ds[i]["giamcuoc_tb"] ? this.numberWithCommas(
            ds[i]["giamcuoc_tb"].toString().replaceAll(",", "")
          ) : 0;
          ds[i]["giamcuoc_sd"] = ds[i]["giamcuoc_sd"] ? this.numberWithCommas(
            ds[i]["giamcuoc_sd"].toString().replaceAll(",", "")
          ) : 0;
        }
        return ds;
      },
      set(value) {
        if (value.length > 0) {
          for (var i = 0; i < value.length; i++) {
            var tien_goi = value[i]["tien_goi"] ? value[i]["tien_goi"].toString().replaceAll(",", "") : 0;
            this.dataSourcesGan[i]["tien_goi"] = tien_goi ? Number(tien_goi) : 0;

            var vat_goi = value[i]["vat_goi"] ? value[i]["vat_goi"].toString().replaceAll(",", "") : 0;
            this.dataSourcesGan[i]["vat_goi"] = vat_goi ? Number(vat_goi) : 0;

            var cuoc_tb = value[i]["cuoc_tb"] ? value[i]["cuoc_tb"].toString().replaceAll(",", "") : 0;
            this.dataSourcesGan[i]["cuoc_tb"] = cuoc_tb ? Number(cuoc_tb) : 0;

            var cuoc_sd = value[i]["cuoc_sd"] ? value[i]["cuoc_sd"].toString().replaceAll(",", "") : 0;
            this.dataSourcesGan[i]["cuoc_sd"] = cuoc_sd ? Number(cuoc_sd) : 0;

            var giamcuoc_tb = value[i]["giamcuoc_tb"] ? value[i]["giamcuoc_tb"].toString().replaceAll(",", "") : 0;
            this.dataSourcesGan[i]["giamcuoc_tb"] = giamcuoc_tb ? Number(giamcuoc_tb) : 0;

            var giamcuoc_sd = value[i]["giamcuoc_sd"] ? value[i]["giamcuoc_sd"].toString().replaceAll(",", "") : 0;
            this.dataSourcesGan[i]["giamcuoc_sd"] = giamcuoc_sd ? Number(giamcuoc_sd) : 0;
          }
        }
      },
    },
  },
  watch: {
    goi_id(newval, oldval) {
      if (newval != oldval) {
        this.goitichhop_id = newval;
        this.hienthi_loaihinh();
      }
    },
    "thongtin_chuagan.tiengoi"(val) {
      this.thongtin_chuagan.vatgoi = Math.round((Number(val) * 10) / 100);
    },
  },
};
</script>
<style scoped>
.disabled {
  cursor: not-allowed;
  color: gray !important;
}
</style>
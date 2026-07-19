<template>
  <div>
    <breadcrumb :header="header" />

    <vue-nav>
      <ul class="list">
        <li :class="!visiable.nhapmoi ? 'non-ative' : ''">
          <a @click="tsbtnNhapMoi_Click()">
            <span class="icon one-file-plus"></span> Nhập mới (F5)
          </a>
        </li>
        <li :class="!visiable.ghilai ? 'non-ative' : ''">
          <a @click="tsbtnGhiLai_Click()">
            <span class="icon one-save"></span> Ghi lại (F9)
          </a>
        </li>
        <li :class="!visiable.huy ? 'non-ative' : ''">
          <a @click="tsbtnHuyBo_Click()">
            <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Hủy (F7)
          </a>
        </li>
        <li :class="!visiable.xoa ? 'non-ative' : ''">
          <a @click="tsbtnXoa_Click()"> <span class="icon one-trash"></span> Xóa (F8) </a>
        </li>
        <li :class="!visiable.sinhbienlai ? 'non-ative' : ''">
          <a @click="btnSinhBienLai_Click()">
            <span class="icon nc-icon-glyph shopping_receipt-list-43"></span> Sinh biên
            lai (F6)
          </a>
        </li>
      </ul>
    </vue-nav>
    <div class="page-content">
      <form @submit.prevent="preventSubmit" id="form-element">
        <b-row>
          <div class="col-md-6">
            <b-row>
              <vue-card>
                <div class="legend-title">Thông tin lượt biên lai</div>
                <div class="info-row">
                    <div class="key">Loại biên lai</div>
                    <div class="value">
                        <div class="select-custom">
                            <Select2 v-model="model.loaibienlai_id" :options="lstLoaiBL" />
                        </div>
                    </div>
                </div>
                <div class="info-row">
                    <div class="key">Mã lượt tạo</div>
                    <div class="value">
                        <input type="text" class="form-control" v-model="model.ma_luot_tao" id="tb_maluottao">
                    </div>
                </div>
                <div class="info-row">
                    <div class="key">Số BL/Quyển</div>
                    <div class="value">
                        <ejs-numerictextbox
                            format="###" :min="0" cssClass="font-weight-bold"
                            v-model="model.blQuyen" :enabled="visiable.blQuyen"
                            @blur="CalTongBL_DenSo" id="tb_blQuyen"
                        ></ejs-numerictextbox>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key">Từ số</div>
                            <div class="value">
                                <ejs-numerictextbox
                                    format="###" :min="0" cssClass="font-weight-bold"
                                    v-model="model.tu_so" :enabled="visiable.tu_so"
                                    @blur="CalTongBL_DenSo" id="tb_tuso"
                                ></ejs-numerictextbox>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key">Đến số</div>
                            <div class="value">
                                <ejs-numerictextbox
                                    format="###" :min="0" cssClass="font-weight-bold"
                                    v-model="model.den_so" :enabled="visiable.den_so"
                                    @blur="CalTongBL_DenSo" id="tb_denso"
                                ></ejs-numerictextbox>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key">Tổng số biên lai</div>
                            <div class="value">
                                <ejs-numerictextbox
                                    format="###" :min="0" cssClass="font-weight-bold"
                                    v-model="model.tong_so_bl" :enabled="visiable.tong_so_bl"
                                    id="tb_tongso"
                                ></ejs-numerictextbox>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key">Số quyển</div>
                            <div class="value">
                                <ejs-numerictextbox
                                    format="###" :min="0" cssClass="font-weight-bold"
                                    v-model="model.so_quyen" :enabled="visiable.so_quyen"
                                    @blur="CalTongBL_DenSo"
                                ></ejs-numerictextbox>
                            </div>
                        </div>
                    </div>
                </div>
              </vue-card>
              <vue-card>
                <b-row>
                  <div class="legend-title">
                    <div class="pull-left"><span></span> Danh sách lượt tạo biên lai</div>
                    <div class="clearfix"></div>
                  </div>
                  <DataGridCustom
                      ref="luottaoFocus"
                      v-bind:columns="collumnsDSLTBL"
                      v-bind:dataSource="options.dsLuotTaoBienLai"
                      :showColumnCheckbox="true"
                      :showFilter="true"
                      :enabledSelectFirstRow="true"
                      :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                      panelDataHeight="240"
                      @selectedItemsChanged="select_selectedItemsChangedLuotTao"
                      @rowSelected="gridLuotTao_FocusedRowChanged"
                      @rowDeselected="gridLuotTao_RowDeselected"
                      :allowPaging="true"
                      :enablePagingServer="true"
                      @pageChanged="gridLuotTao_pageChanged" :totalRecords="totalRecords"
                  ></DataGridCustom>
                </b-row>
              </vue-card>
            </b-row>
          </div>
          <div class="col-md-6">
            <vue-card>
              <div class="legend-title">
                <div class="pull-left"><span></span> Danh sách biên lai</div>
                <div class="clearfix"></div>
              </div>
              <b-row>
                <vue-element label="Kho biên lai" class="col-md-12">
                  <ejs-dropdownlist
                      id="cbb_khobienlai"
                      locale="vi-VN"
                      v-model="kho_id"
                      :dataSource="lstKhoBL"
                      popupWidth="auto"
                      :allowFiltering="true"
                      :filtering="onFilterKhoBienLai"
                      :fields="{ value: 'kho_id', text: 'ten_kho' }"
                  />
                </vue-element>
              </b-row>
              <b-row>
                <div>
                  <DataGrid
                      v-bind:columns="collumnsDSBL"
                      v-bind:dataSource="options.dsBienLaiTheoLoai"
                      :showColumnCheckbox="false"
                      :showFilter="false"
                      :allowPaging="true"
                      :enablePagingServer="false"
                      :enabledSelectFirstRow="false"
                      :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                      panelDataHeight="350"
                  ></DataGrid>
                </div>
              </b-row>
            </vue-card>
          </div>
        </b-row>
      </form>
    </div>
  </div>
</template>
<script>
import breadcrumb from '@/components/breadcrumb'
import collums from "./define/collums.js";
import VueElement from "./components/VueElement.vue";
import api from "./api/index.js";
import NE from "./popup/NE.vue";
import DataGridCustom from "./components/DataGrid";
import { Query } from "@syncfusion/ej2-data";
export default {
  components: {
    breadcrumb,
    NE,
    VueElement,
    DataGridCustom,
  },
  data() {
    return {
      ...collums,
      target: ".main-wrapper",
      model: {
        loaibienlai_id: null,
        ma_luot_tao: "",
        blQuyen: "",
        tu_so: "",
        den_so: "",
        tong_so_bl: "",
        so_quyen: "",
      },
      header: {
        title: "Quản lý lượt tạo biên lai",
        list: [
          { name: "Quản lý", link: { name: "Ui.cards" } },
          {
            name: "Quản lý lượt tạo biên lai",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      visiable: {
        tu_so: false,
        den_so: false,
        blQuyen: false,
        tong_so_bl: false,
        so_quyen: false,
        nhapmoi: false,
        ghilai: false,
        huy: false,
        xoa: false,
        sinhbienlai: false,
      },
      isLoad: true,
      vloaibienlai_id: 0,
      vluottao_id: 0,
      group_luot: "",
      vLoaiBL: "",
      ds_luottao: [],
      lstLoaiBL: [],
      lstKhoBL: [],
      options: {
        dsLuotTaoBienLai: [],
        dsBienLaiTheoLoai: [],
      },
      dtBienLaiTheoLoai: [],
      resultUpdate: {},
      totalRecords: 0,
      lastPage: 0,
    };
  },
  mounted() {
    this.init();
  },
  computed: {
    kho_id: {
      get() {
        return Number(this.model.kho_id);
      },
      set(val) {
        if (val) this.$set(this.model, "kho_id", val);
      },
    },
  },
  methods: {
    setButton(kieu) {
      try {
        $("#tb_maluottao").focus();
        this.visiable.nhapmoi = false;
        this.visiable.ghilai = false;
        this.visiable.xoa = false;
        this.visiable.huy = false;
        this.visiable.tu_so = true;
        this.visiable.den_so = true;
        this.visiable.tong_so_bl = false;
        this.visiable.so_quyen = false;

        if (kieu == -1) {
          // Bat dau
          this.visiable.ghilai = true;
          this.visiable.huy = true;
        }

        if (kieu == 0) {
          // Bat dau
          this.visiable.nhapmoi = true;
          this.clear();
        }

        if (kieu == 1) {
          // Them moi
          this.visiable.ghilai = true;
          this.visiable.huy = true;
          this.visiable.blQuyen = true;
          this.visiable.tu_so = true;
          this.visiable.den_so = true;
          this.clear();
        }

        if (kieu == 2) {
          //Huy
          this.visiable.nhapmoi = true;
          this.visiable.xoa = false;
          this.visiable.blQuyen = false;
          this.visiable.tu_so = false;
          this.visiable.den_so = false;
          this.clear();
        }

        if (kieu == 3) {
          // Edit
          this.visiable.nhapmoi = false;
          this.visiable.xoa = true;
          this.visiable.ghilai = true;
          this.visiable.huy = true;
          this.visiable.blQuyen = false;
          this.visiable.tu_so = false;
          this.visiable.den_so = false;
        }
      } catch (e) {
        this.$toast.error("Thất bại");
      }
    },
    clear() {
      this.model.ma_luot_tao = "";
      this.model.tu_so = "0";
      this.model.den_so = "0";
      this.model.blQuyen = "0";
    },
    async init() {
      try {
        this.loading(true)
        this.setButton(1);
        this.visiable.sinhbienlai = false;
        this.visiable.xoa = false;
        this.vluottao_id = 0;
        this.visiable.tu_so = true;
        this.visiable.den_so = true;
        this.visiable.blQuyen = true;
        this.visiable.tong_so_bl = false;
        this.visiable.so_quyen = false;

        await this.getDSLoaiBienLai();
        await this.getDSKhoBienLai();
      } catch (e) {
        console.log(e);
        this.$toast.error(e.data ? e.data.message : "Khởi tạo thất bại");
      }
    },
    async getDSLoaiBienLai() {
      try {
        await api.getDSLoaiBienLai(this.axios, {}).then((res) => {
          let temps = res.data.data ? res.data.data : [];
          if (temps && temps.length > 0){
              this.lstLoaiBL = temps.map((x)=>({id:x.loaibienlai_id, text:x.ten_loaibl}))
              this.model.loaibienlai_id = temps[0].loaibienlai_id
          }
        });
      } catch (err) {
        console.log(err)
      }
    },
    async getDSKhoBienLai() {
      try {
        await api.getDSKhoBienLai(this.axios, {}).then((res) => {
          this.lstKhoBL = res.data.data ? res.data.data : [];
          if (this.lstKhoBL) {
            this.$set(this, "kho_id", this.lstKhoBL[0]["kho_id"]);
          }
        });
      } catch (err) {
        console.log(err)
      }
    },
    setValueFromRowSelect(row) {
      this.vluottao_id = row.luottao_id;
      this.model.ma_luot_tao = row.ma_luottao != undefined ? row.ma_luottao : "";
      this.model.tu_so = row.tu_so;
      this.model.blQuyen = row.tong_so;
      this.model.den_so = row.den_so;
      this.model.tong_so_bl = row.tong_so;
      this.group_luot = row.group_luot;
      if (
        this.model.tong_so_bl > 0 &&
        this.model.blQuyen > 0 &&
        this.model.tong_so_bl % this.model.blQuyen == 0
      ) {
        this.model.so_quyen = this.model.tong_so_bl / this.model.blQuyen;
      }

      this.vLoaiBL = row.loaibienlai_id;
      // this.isLoad = false;
      // this.loaibienlai_id = this.vLoaiBL;
      // this.isLoad = true;

      this.setButton(3);
    },
    async gridLuotTao_FocusedRowChanged(data) {
      console.log("gridLuotTao_FocusedRowChanged")
      try {
        if (data) {
          let row = data.data;
          this.setValueFromRowSelect(row);
          this.options.dsBienLaiTheoLoai = await this.SP_LAY_DS_BIENLAI_THEO_LUOTTAO(row.loaibienlai_id, row.luottao_id)
        }
      } catch (err) {
        console.log(err);
      }
    },
    async gridLuotTao_RowDeselected() {
      try {
        if (this.ds_luottao.length == 0) {
          this.tsbtnHuyBo_Click();
        } else {
          this.setValueFromRowSelect(this.ds_luottao[0]);
        }
      } catch (err) {
        console.log(err);
      }
    },
    async gridBienLai_FocusedRowChanged() {
      try {
      } catch (err) {
        console.log(err);
      }
    },
    select_selectedItemsChangedLuotTao(value) {
      this.ds_luottao = value;
      if (this.ds_luottao.length > 0) {
        this.visiable.sinhbienlai = true;
        this.visiable.xoa = true;
      } else {
        this.visiable.sinhbienlai = false;
        this.visiable.xoa = false;
      }
    },
    async getTuSoFromLuotTao(v_loaibienlai_id) {
      try {
        await api
          .getTuSoFromLuotTao(this.axios, {
            loai_bienlai_id: v_loaibienlai_id,
          })
          .then((res) => {
            this.model.tu_so = res.data.data ? res.data.data : null;
            this.model.den_so = this.model.tu_so;
          });
      } catch (err) {}
    },
    async tsbtnNhapMoi_Click() {
      if (!this.visiable.nhapmoi) return;

      try {
        this.setButton(1);
        this.vluottao_id = 0;
        this.model.ma_luot_tao = "";
        this.model.tong_so_bl = "";
        this.model.so_quyen = "";
        this.model.blQuyen = "0";
        await this.getTuSoFromLuotTao(this.vloaibienlai_id);
      } catch (err) {
        console.log(err);
      }
    },
    tsbtnHuyBo_Click() {
      if (!this.visiable.huy) return;

      this.setButton(2);
      this.model.ma_luot_tao = "";
      this.model.tu_so = "0";
      this.model.den_so = "0";
      this.model.blQuyen = "0";
    },
    async tsbtnXoa_Click() {
      if (!this.visiable.xoa) return;

      if (this.options.dsLuotTaoBienLai.length <= 0) {
        this.$toast.error("Danh sách trống. Bạn không thể xóa !");
        return;
      }
      if (this.ds_luottao.length == 0) {
        this.$toast.error(
          "Chưa có lượt tạo nào được chọn để Xóa. Vui lòng chọn lượt tạo biên lai hoặc nhóm lượt để xóa!"
        );
        return;
      }

      this.$bvModal
        .msgBoxConfirm("Bạn thật sự muốn xóa lượt biên lai?", {
          title: "",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then(async (v) => {
          if (v) {
            try {
              let vstr_list_id = "";
              this.ds_luottao.forEach((item) => {
                vstr_list_id += item.luottao_id + ",";
              });

              vstr_list_id = vstr_list_id.substring(0, vstr_list_id.length - 1);
              this.loading(true);
              await api
                .delete_luot_tao(this.axios, {
                  vstr_list_id: vstr_list_id,
                })
                .then((res) => {
                  console.log(res);
                  let msg = res.data.data;
                  if (msg == "101") {
                    this.$toast.success("Xóa lượt tạo thành công!");
                  } else if (msg == "102") {
                    this.$toast.error(
                      "Xóa lượt tạo không thành công. Do biên lai được sinh ra đang được dùng. Vui lòng kiểm tra lại!"
                    );
                  } else {
                    this.$toast.error("Lỗi : " + msg);
                  }
                });

              let pSize = this.$refs.luottaoFocus.$refs.pagination.pageSizeData
              this.options.dsLuotTaoBienLai = await this.SP_LAY_DS_LUOTTAO_BIENLAI(this.vloaibienlai_id, "", 1, pSize, 1);
              this.setButton(1);

              await this.getTuSoFromLuotTao(this.vloaibienlai_id);
              this.setButton(1);
              this.vluottao_id = 0;
              this.visiable.sinhbienlai = false;
              if (this.options.dsLuotTaoBienLai.length == 0) {
                this.options.dsBienLaiTheoLoai = []
              }
            } catch (e) {
              console.log(e);
              this.$toast.error("Không thể xóa bản ghi này " + e);
            } finally {
              this.loading(false);
            }
          }
        });
    },
    async tsbtnGhiLai_Click() {
      if (!this.visiable.ghilai) return;

      console.log(this.ds_luottao.length);
      if (this.ds_luottao.length == 0) {
        this.vluottao_id = 0;
      } else if (this.ds_luottao.length > 1) {
        this.$toast.error("Chỉ được chọn 1 bản ghi để cập nhật");
        return;
      }

      try {
        this.loading(true)
        let index = this.$refs.luottaoFocus.$refs.grid.getSelectedRowIndexes();
        if (this.vluottao_id > 0) {
          if (!this.KiemTra_DuLieu(2)) return;
          await this.TaoDuLieu_LuotBienLai(false);
          if (this.resultUpdate.error_code == "BSS-00000000") {
            this.$toast.success("Cập nhật lượt biên lai thành công !");
          } else {
            this.$toast.error(this.resultUpdate.data);
          }
        } else {
          if (!this.KiemTra_DuLieu(1)) return;
          await this.TaoDuLieu_LuotBienLai(true);
          if (this.resultUpdate.error_code == "BSS-00000000") {
            this.$toast.success("Thêm mới lượt biên lai thành công !");
          } else {
            this.$toast.error(this.resultUpdate.data);
          }
        }

        let pSize = this.$refs.luottaoFocus.$refs.pagination.pageSizeData
        this.options.dsLuotTaoBienLai = await this.SP_LAY_DS_LUOTTAO_BIENLAI(this.vloaibienlai_id, this.vluottao_id, 1, pSize, 1);
        this.setButton(3);

        this.$refs.luottaoFocus.$refs.grid.selectRow(index[0]);
      } catch (ex) {
        this.$toast.error("Có lỗi : " + ex);
      } finally {
        this.loading(false)
      }
    },
    async Insert_LuotTao_BienLai(
      vloaibienlai_id,
      vma_luottao,
      iSoBLQuyen,
      vtuso,
      vdenso
    ) {
      let result = {};
      await api
        .Insert_LuotTao_BienLai(this.axios, {
          vloaibl_id: vloaibienlai_id,
          vma_luot: vma_luottao,
          vtuso: vtuso,
          vso_bl_quyen: iSoBLQuyen,
          vdenso: vdenso,
        })
        .then((res) => {
          result = res.data;
        });
      return result;
    },
    async Update_LuotTao_BienLai(
      vloaibienlai_id,
      vluottao_id,
      vma_luottao,
      iSoBLQuyen,
      vtuso,
      vdenso,
      vtong_bl
    ) {
      let result = {};
      await api
        .Update_LuotTao_BienLai(this.axios, {
          vloaibl_id: vloaibienlai_id,
          vma_luot: vma_luottao,
          vluottao_id: vluottao_id,
          vtuso: vtuso,
          vtongso: vtong_bl,
          vso_bl_quyen: iSoBLQuyen,
          vdenso: vdenso,
        })
        .then((res) => {
          result = res.data;
        });
      return result;
    },
    async TaoDuLieu_LuotBienLai(themmoi) {
      let vtuso = this.model.tu_so;
      let iSoBLQuyen = this.model.blQuyen;
      let vma_luottao = this.model.ma_luot_tao;
      let vloaibienlai_id = this.model.loaibienlai_id;
      let vdenso = this.model.den_so;
      let vtong_bl = this.model.tong_so_bl;
      if (!themmoi) vma_luottao = this.group_luot + " - " + this.model.ma_luot_tao;
      this.resultUpdate = {};
      if (themmoi) {
        this.resultUpdate = await this.Insert_LuotTao_BienLai(
          vloaibienlai_id,
          vma_luottao,
          iSoBLQuyen,
          vtuso,
          vdenso
        );
      } else {
        this.resultUpdate = await this.Update_LuotTao_BienLai(
          vloaibienlai_id,
          this.vluottao_id,
          vma_luottao,
          iSoBLQuyen,
          vtuso,
          vdenso,
          vtong_bl
        );
      }
    },
    KiemTra_DuLieu(loai) {
      // loai = 1 : Đặt mới, loai = 2 : Cập nhật, loai = 3 : Xóa
      if (loai == 1 || loai == 2) {
        if (this.model.ma_luot_tao == "") {
          this.$toast.warning("Bạn chưa nhập Mã lượt tạo !");
          $("#tb_maluottao").focus();
          return false;
        }
        if (this.model.tu_so == "") {
          this.$toast.warning("Bạn chưa nhập Số biên lai từ !");
          $("#tb_tuso").focus();
          return false;
        }
        if (this.model.den_so == "") {
          this.$toast.warning("Bạn chưa nhập Số biên lai đến !");
          $("#tb_denso").focus();
          return false;
        }
        if (this.model.blQuyen == "") {
          this.$toast.warning("Bạn chưa nhập Số BL/Quyển !");
          $("#tb_blQuyen").focus();
          return false;
        }
        if (this.model.tong_so_bl == "") {
          this.$toast.warning("Bạn chưa nhập Tổng số biên lai !");
          $("#tb_tongso").focus();
          return false;
        }
        if (this.model.den_so == 0) {
          this.$toast.warning("Số biên lai đến phải lớn hơn 0 !");
          $("#tb_denso").focus();
          return false;
        }
        if (this.model.tu_so == 0) {
          this.$toast.warning("Số biên lai từ phải lớn hơn 0 !");
          $("#tb_tuso").focus();
          return false;
        }
        if (this.model.tu_so > this.model.den_so) {
          this.$toast.warning("Số biên lai đến không nhỏ hơn số biên lai từ !");
          $("#tb_denso").focus();
          return false;
        }

        if ((this.model.den_so - this.model.tu_so + 1) % this.model.blQuyen != 0) {
          this.$toast.warning(
            "Bạn nhập số biên lai đến chưa chính xác. Vui lòng kiểm tra lại!"
          );
          $("#tb_denso").focus();
          return false;
        }
      }

      if (loai == 2) {
        if (this.model.loaibienlai_id != this.vLoaiBL) {
          this.$toast.warning(
            "Không thể cập nhật. Vui lòng tạo mới biên lai cho loại biên lai này !" +
              this.vLoaiBL
          );
          $("#tb_denso").focus();
          return false;
        }
      }

      if (loai == 4) {
        if (this.model.tu_so == "") {
          this.$toast.warning("Bạn chưa nhập Số biên lai từ !");
          $("#tb_tuso").focus();
          return false;
        }
        if (this.model.den_so == "") {
          this.$toast.warning("Bạn chưa nhập Số biên lai đến !");
          $("#tb_denso").focus();
          return false;
        }
        if (this.kho_id == "") {
          this.$toast.warning("Hãy chọn loại Kho biên lai");
          $("#cbb_khobienlai").focus();
          return false;
        }
      }
      return true;
    },
    CalTongBL_DenSo() {
      let SoBLQuyen = this.model.blQuyen;
      let TuSo = this.model.tu_so;
      let DenSo = this.model.den_so;
      if (TuSo > 0 && DenSo > 0) {
        let TongSo = DenSo - TuSo + 1;
        this.model.tong_so_bl = TongSo;
        if (SoBLQuyen == 0) {
          SoBLQuyen = 1;
          this.model.blQuyen = 1;
        }
        if (TongSo % SoBLQuyen == 0) {
          this.model.so_quyen = TongSo / SoBLQuyen;
        } else {
          this.model.so_quyen = 0;
        }
      } 
      
      if (this.model.tong_so_bl != undefined && this.model.tong_so_bl != "" && Number(this.model.tong_so_bl) >= 0 &&
            Number(this.model.tong_so_bl) < SoBLQuyen && TuSo > 0 && SoBLQuyen > 0) {
        this.model.den_so = TuSo + SoBLQuyen - 1
        this.CalTongBL_DenSo()
      }
    },
    async Sinh_Bien_Lai_Auto(vds_luot_id, vloaibienlai_id, vnd_giao, vkho_id) {
      console.log(vds_luot_id + " " + vloaibienlai_id + " " + vnd_giao + " " + vkho_id);
      let result = "";
      await api
        .Sinh_Bien_Lai_Auto(this.axios, {
          vds_luot_id: vds_luot_id,
          vloaibienlai_id: vloaibienlai_id,
          vnd_giao: vnd_giao,
          vkho_id: vkho_id,
        })
        .then((res) => {
          result = res.data.data;
        });
      return result;
    },
    async btnSinhBienLai_Click() {
      if (!this.visiable.sinhbienlai) return;
      try {
        if (!this.KiemTra_DuLieu(4)) return;
        if (this.ds_luottao.length == 0) {
          this.$toast.warning(
            "Chưa có lượt tạo nào được chọn để sinh Biên lai. Vui lòng chọn lượt tạo biên lai hoặc nhóm lượt!"
          );
          return;
        }

        let vds_luot_id = "";
        this.ds_luottao.forEach((item) => {
          vds_luot_id += item.luottao_id + ",";
        });

        vds_luot_id = vds_luot_id.substring(0, vds_luot_id.length - 1);
        this.loading(true);

        let vkho_id = this.kho_id;
        let vnd_giao = "";
        let vloaibienlai_id = this.model.loaibienlai_id;

        let strMSG = await this.Sinh_Bien_Lai_Auto(
          vds_luot_id,
          vloaibienlai_id,
          vnd_giao,
          vkho_id
        );
        if (strMSG.toUpperCase() == "OK") {
          let pSize = this.$refs.luottaoFocus.$refs.pagination.pageSizeData
          this.options.dsLuotTaoBienLai = await this.SP_LAY_DS_LUOTTAO_BIENLAI(this.vloaibienlai_id, this.vluottao_id, 1, pSize, 1);
          this.$toast.success("Sinh biên lai thành công!");
        } else if (strMSG == "102") {
          this.$toast.warning(
            "Sinh biên lai không thành công. Bạn vui lòng chọn lượt chưa sinh biên lai để thực hiện !"
          );
        } else {
          this.$toast.error("Có lỗi: " + strMSG);
        }
      } catch (ex) {
        this.$toast.error("Có lỗi : " + ex);
      } finally {
         this.loading(false);
      }
    },
    onFilterLoaiBienLai: function (e) {
      var query = new Query();
      query = e.text !== "" ? query.where("ten_loaibl", "contains", e.text, true) : query;
      e.updateData(this.lstLoaiBL, query);
    },
    onFilterKhoBienLai: function (e) {
      var query = new Query();
      query = e.text !== "" ? query.where("ten_kho", "contains", e.text, true) : query;
      e.updateData(this.lstKhoBL, query);
    },
    async SP_LAY_DS_LUOTTAO_BIENLAI(vloaibienlai_id, vluottao_id, pageNum, pageRec, totalRow) {
        let result = []
        let params = {
            "vloaibienlai_id": vloaibienlai_id,
            "vluottao_id": vluottao_id,
            "pageNum": pageNum,
            "pageRec": pageRec,
            "totalRow": totalRow
        }
        try {
            let response = await api.sp_lay_ds_luottao_bienlai(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data
                if (totalRow == 1) {
                    this.totalRecords = response.data.page_info.record_count
                }
            }
        } catch (e) {
            console.log(e)
        }
        return result
    },
    async SP_LAY_DS_BIENLAI_THEO_LUOTTAO(vloaibienlai_id, vluottao_id) {
        let result = []
        let params = {
            "vloaibienlai_id": vloaibienlai_id,
            "vluottao_id": vluottao_id,
        }
        try {
            this.loading(true)
            let response = await api.sp_lay_ds_bienlai_theo_luottao(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data
            }
        } catch (e) {
            console.log(e)
        } finally {
            this.loading(false)
        }
        return result
    },
    async gridLuotTao_pageChanged() {
        let pIndex = this.$refs.luottaoFocus.$refs.pagination.pageIndexData
        if (this.lastPage == pIndex) return

        console.log("gridLuotTao_pageChanged")
        this.options.dsLuotTaoBienLai = []
        this.options.dsBienLaiTheoLoai = []
        try {
            this.loading(true)
            let pSize = this.$refs.luottaoFocus.$refs.pagination.pageSizeData
            this.options.dsLuotTaoBienLai = await this.SP_LAY_DS_LUOTTAO_BIENLAI(this.vloaibienlai_id, "", pIndex+1, pSize, 0);
            this.lastPage = pIndex
        } catch (exp) {
            this.$toast.error(exp)
        } finally {
            this.loading(false)
        }
    },
    preventSubmit() {

    }
  },
  watch: {
    async "model.loaibienlai_id"(val) {
      console.log("watch")
      this.loading(true);
      try {
        await this.getTuSoFromLuotTao(val);
        let pr_tu_so = this.model.tu_so;

        this.options.dsBienLaiTheoLoai = []
        this.vloaibienlai_id = val;
        if (this.isLoad) {
          let pSize = this.$refs.luottaoFocus.$refs.pagination.pageSizeData
          this.options.dsLuotTaoBienLai = await this.SP_LAY_DS_LUOTTAO_BIENLAI(this.vloaibienlai_id, "", 1, pSize, 1);
          this.$refs.luottaoFocus.$refs.pagination.pageIndexData = 0
        }

        this.isLoad = true;
        this.setButton(1);
        this.vluottao_id = 0;

        this.model.tu_so = pr_tu_so;
        this.model.den_so = this.model.tu_so;
      } catch (err) {
        console.log(err)
      } finally {
        this.loading(false)
      }
    },
  },
};
</script>
<style>
.btn.btn-chon-ne {
  padding: 2px 2px !important;
}
.space-between {
  width: 10px;
}
.none-padding {
  padding: 0 !important;
}
.modal-dialog {
  border-radius: 0px !important;
  width: 100% !important;
  min-width: 720px !important;
}
.popup-box {
  background: #fafafa38;
}
.non-ative a {
  color: #d3d3d3 !important;
}
.e-numeric.e-control-wrapper.e-input-group .e-input-group-icon {
  display: none;
}
</style>

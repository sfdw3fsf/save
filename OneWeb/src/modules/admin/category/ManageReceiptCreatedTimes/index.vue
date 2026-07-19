<template>
  <div>
    <vue-bread-crumb :header="header" />
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
          <a @click="tsbtnXoa_Click()">
            <span class="icon one-trash"></span> Xóa (F8)
          </a>
        </li>
        <li :class="!visiable.sinhbienlai ? 'non-ative' : ''">
          <a @click="btnSinhBienLai_Click()">
            <span class="icon nc-icon-glyph shopping_receipt-list-43"></span> Sinh biên lai (F6)
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
                <b-row>
                  <div class="legend-title">
                    <div class="pull-left">
                      <span></span> Thông tin lượt biên lai
                    </div>
                    <div class="clearfix"></div>
                  </div>
                  <vue-element label="Loại biên lai" class="col-md-12">
                    <ejs-dropdownlist id="cbb_loaibienlai"
                      locale="vi-VN"
                      v-model="loaibienlai_id"
                      :dataSource="lstLoaiBL"
                      popupWidth="auto"
                      :fields="{ value: 'loaibienlai_id', text: 'ten_loaibl' }"
                    />
                  </vue-element>
                </b-row>       
                <b-row>
                  <vue-element label="Mã lượt tạo" class="col-md-12">
                    <ejs-textbox cssClass="font-weight-bold" v-model="model.ma_luot_tao" id="tb_maluottao"></ejs-textbox>
                  </vue-element>
                </b-row>
                <b-row>
                  <vue-element label="Số BL/Quyển" class="col-md-12">
                    <ejs-numerictextbox format='###' :min="0" cssClass="font-weight-bold" v-model="model.blQuyen" :enabled="visiable.blQuyen" @blur="CalTongBL_DenSo" id="tb_blQuyen"></ejs-numerictextbox>
                  </vue-element>
                </b-row>
                <b-row>
                  <vue-element label="Từ số" class="col-md-6">
                    <ejs-numerictextbox format='###' :min="0" cssClass="font-weight-bold" v-model="model.tu_so" :enabled="visiable.tu_so" @blur="CalTongBL_DenSo" id="tb_tuso"></ejs-numerictextbox >
                  </vue-element>
                  <vue-element label="Đến số" class="col-md-6">
                    <ejs-numerictextbox format='###' :min="0" cssClass="font-weight-bold" v-model="model.den_so" :enabled="visiable.den_so" @blur="CalTongBL_DenSo" id="tb_denso"></ejs-numerictextbox>
                  </vue-element>
                </b-row>
                <b-row>
                  <vue-element label="Tổng số biên lai" class="col-md-6">
                    <ejs-numerictextbox format='###' :min="0" cssClass="font-weight-bold" v-model="model.tong_so_bl" :enabled="visiable.tong_so_bl" id="tb_tongso"></ejs-numerictextbox>
                  </vue-element>
                  <vue-element label="Số quyển" class="col-md-6">
                    <ejs-numerictextbox format='###' :min="0" cssClass="font-weight-bold" v-model="model.so_quyen" :enabled="visiable.so_quyen" @blur="CalTongBL_DenSo"></ejs-numerictextbox>
                  </vue-element>
                </b-row>
              </vue-card>
              <vue-card> 
                <b-row>
                  <div class="legend-title">
                    <div class="pull-left">
                      <span></span> Danh sách lượt tạo biên lai
                    </div>
                    <div class="clearfix"></div>
                  </div>
                <DataGridCustom
                    ref="luottaoFocus"
                    v-bind:columns="collumnsDSLTBL"
                    v-bind:dataSource="options.dsLuotTaoBienLai"
                    :showColumnCheckbox="true"
                    :showFilter="true"
                    :enabledSelectFirstRow="false"
                    :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                    panelDataHeight="240"
                    @selectedItemsChanged="select_selectedItemsChangedLuotTao"
                    @rowSelected="gridLuotTao_FocusedRowChanged"
                    @rowDeselected="gridLuotTao_RowDeselected"
                    :allowPaging="true" :enablePagingServer="false"
                ></DataGridCustom>
                </b-row>
              </vue-card>
            </b-row>
          </div>
          <div class="col-md-6">                        
            <vue-card>
              <div class="legend-title">
                <div class="pull-left">
                  <span></span> Danh sách biên lai
                </div>
                <div class="clearfix"></div>
              </div>
              <b-row>
                <vue-element label="Kho biên lai" class="col-md-12">
                  <ejs-dropdownlist id="cbb_khobienlai"
                      locale="vi-VN"
                      v-model="kho_id"
                      :dataSource="lstKhoBL"
                      popupWidth="auto"
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

import collums from "./define/collums.js";
import VueElement from "./components/VueElement.vue";
import api from "./api/index.js";
import NE from "./popup/NE.vue";
import DataGridCustom from "./components/DataGrid";
export default {
  components: {
    NE,
    VueElement,
    DataGridCustom,
  },
  data() {
    return {
      ...collums,
      target: '.main-wrapper',
      model: {
        ma_luot_tao : "",
        blQuyen : "",
        tu_so: "",
        den_so: "",
        tong_so_bl : "",
        so_quyen : ""
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
      lstLoaiBL: {},
      lstKhoBL: {},
      options: {
        dsLuotTaoBienLai: [],
        dsBienLaiTheoLoai: [],
      },
      dtBienLaiTheoLoai: [],
      resultUpdate: {},
    };
  },
  mounted() {
    this.init();
  },
  computed: {
    loaibienlai_id: {
      get() {
        return Number(this.model.loaibienlai_id);
      },
      set(val) {
        if(val)
          this.$set(this.model, "loaibienlai_id", val);
      },
    },
    kho_id: {
      get() {
        return Number(this.model.kho_id);
      },
      set(val) {
        if(val)
          this.$set(this.model, "kho_id", val);
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

        if (kieu == -1) { // Bat dau
            this.visiable.ghilai = true;
            this.visiable.huy = true;
        }

        if (kieu == 0) { // Bat dau
            this.visiable.nhapmoi = true;
            this.clear();
        }

        if (kieu == 1) { // Them moi
            this.visiable.ghilai = true;
            this.visiable.huy = true;
            this.visiable.blQuyen = true;
            this.visiable.tu_so = true;
            this.visiable.den_so = true;
            this.clear();
        }

        if (kieu == 2) { //Huy
            this.visiable.nhapmoi = true;
            this.visiable.xoa = false;
            this.visiable.blQuyen = false;
            this.visiable.tu_so = false;
            this.visiable.den_so = false;
            this.clear();
        }

        if (kieu == 3) { // Edit
            this.visiable.nhapmoi = false;
            this.visiable.xoa = true;
            this.visiable.ghilai = true;
            this.visiable.huy = true;
            this.visiable.blQuyen = false;
            this.visiable.tu_so = false;
            this.visiable.den_so = false;
        }
      } catch (e) {
        this.$toast.error("Thất bại")
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
      this.loading(true);
      try {
        await api.getDSLoaiBienLai(this.axios, {  

          }).then((res) => {              
            this.lstLoaiBL = res.data.data ? res.data.data : [];          
            if (this.lstLoaiBL) {
              this.$set(this, "loaibienlai_id", this.lstLoaiBL[0]["loaibienlai_id"]);
            }
          });
      } catch (err) {
        this.loading(false);
      }
      this.loading(false);
    },
    async getDSKhoBienLai() {
      this.loading(true);
      try {
        await api.getDSKhoBienLai(this.axios, {         

          }).then((res) => {              
            this.lstKhoBL = res.data.data ? res.data.data : [];          
            if (this.lstLoaiBL) {
              this.$set(this, "kho_id", this.lstKhoBL[0]["kho_id"]);
            }
          });
      } catch (err) {
        this.loading(false);
      }
      this.loading(false);
    },
    async getDSLuotTaoBienLai(v_loaibienlai_id, v_luottao_id) {
      let result;
      this.loading(true);
      try {
        await api.getDSLuotTaoBienLai(this.axios, {         
              vloaibienlai_id: v_loaibienlai_id,
              vluottao_id: v_luottao_id
          }).then((res) => {       
            result = res.data.data ? res.data.data : [];          
          });
      } catch (err) {
        this.loading(false);
      }
      this.loading(false);
      return result; 
    },
    setValueFromRowSelect(row) {
      this.vluottao_id = row.luottao_id;
      this.model.ma_luot_tao = row.ma_luottao != undefined ? row.ma_luottao : "";
      this.model.tu_so = row.tu_so;
      this.model.blQuyen = row.tong_so;
      this.model.den_so = row.den_so;
      this.model.tong_so_bl = row.tong_so;
      this.group_luot = row.group_luot;
      if (this.model.tong_so_bl>0  && this.model.blQuyen>0 && this.model.tong_so_bl % this.model.blQuyen == 0) {
          this.model.so_quyen = (this.model.tong_so_bl / this.model.blQuyen);
      }

      this.vLoaiBL = row.loaibienlai_id;
      this.isLoad = false;
      this.loaibienlai_id = this.vLoaiBL;
      this.isLoad = true;

      this.options.dsBienLaiTheoLoai = [];
      for (let index in this.dtBienLaiTheoLoai) {
        if (this.dtBienLaiTheoLoai[index].luottao_id == this.vluottao_id) {
          this.options.dsBienLaiTheoLoai.push(this.dtBienLaiTheoLoai[index]);
        }
      }

      this.gridBienLai_FocusedRowChanged();
      this.setButton(3);
    },
    async gridLuotTao_FocusedRowChanged(data) {
      try {
        if (data) {
          let row = data.data;
          this.setValueFromRowSelect(row);
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
        await api.getTuSoFromLuotTao(this.axios, {
            loai_bienlai_id : v_loaibienlai_id
          }).then((res) => {                   
              this.model.tu_so = res.data.data ? res.data.data : null;    
              this.model.den_so = this.model.tu_so;              
          }); 
      } catch (err) {
  
      } 
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
        this.$toast.error("Chưa có lượt tạo nào được chọn để Xóa. Vui lòng chọn lượt tạo biên lai hoặc nhóm lượt để xóa!");
        return;
      }

      this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xóa lượt biên lai?', {
        title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
      }).then(async v => {
        if (v) {
          try {              
            let vstr_list_id = "";
            this.ds_luottao.forEach((item) => {
              vstr_list_id += item.luottao_id + ",";
            });

            vstr_list_id = vstr_list_id.substring(0, vstr_list_id.length-1);           
            this.loading(true);       
            await api.delete_luot_tao(this.axios, {
              vstr_list_id : vstr_list_id
            }).then((res) => {        
                console.log(res);             
                let msg = res.data.data;
                if (msg == "101") {
                    this.$toast.success("Xóa lượt tạo thành công!");               
                } else if (msg == "102") {
                    this.$toast.error("Xóa lượt tạo không thành công. Do biên lai được sinh ra đang được dùng. Vui lòng kiểm tra lại!");
                } else {
                    this.$toast.error("Lỗi : " + msg);
                }                          
            });

            let result = await this.getDSLuotTaoBienLai(this.vloaibienlai_id, 0);
            if (result) {
                this.options.dsLuotTaoBienLai = result.returnds;
                this.dtBienLaiTheoLoai = result.returnds2;
                this.options.dsBienLaiTheoLoai = this.dtBienLaiTheoLoai;         
            }   
            this.gridBienLai_FocusedRowChanged();
            this.setButton(1);

            await this.getTuSoFromLuotTao(this.vloaibienlai_id);
            this.setButton(1);
            this.vluottao_id = 0;
            this.visiable.sinhbienlai = false;

            this.loading(false);   
          } catch (e) {
            console.log(e);
            this.$toast.error("Không thể xóa bản ghi này " + e);
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

          console.log("vluottao_id: " + this.vluottao_id);
          let result = await this.getDSLuotTaoBienLai(this.vloaibienlai_id, 0);
          if (result) {
              this.options.dsLuotTaoBienLai = result.returnds;
              this.dtBienLaiTheoLoai = result.returnds2;
              this.options.dsBienLaiTheoLoai = [];
              for (let index in this.dtBienLaiTheoLoai) {
                if (this.dtBienLaiTheoLoai[index].luottao_id == this.vluottao_id) {
                  this.options.dsBienLaiTheoLoai.push(this.dtBienLaiTheoLoai[index]);
                }
              }       
          }  
          this.setButton(3);

          this.$refs.luottaoFocus.setCurrentSelectedRow(index[0]);
          this.gridLuotTao_FocusedRowChanged();

          this.options.dsBienLaiTheoLoai = [];
          for (let index in this.dtBienLaiTheoLoai) {
            if (this.dtBienLaiTheoLoai[index].luottao_id == this.vluottao_id) {
              this.options.dsBienLaiTheoLoai.push(this.dtBienLaiTheoLoai[index]);
            }
          }

          this.gridBienLai_FocusedRowChanged();
      } catch (ex) {
          this.$toast.error("Có lỗi : " + ex);
      }
    },
    async Insert_LuotTao_BienLai(vloaibienlai_id, vma_luottao, iSoBLQuyen, vtuso, vdenso) {
      console.log(vloaibienlai_id + " " + vma_luottao + " " +  iSoBLQuyen + " " +  vtuso + " " +  vdenso)
      let result = {};
      await api.Insert_LuotTao_BienLai(this.axios, {
              vloaibl_id: vloaibienlai_id,
              vma_luot: vma_luottao,         
              vtuso: vtuso,         
              vso_bl_quyen: iSoBLQuyen,
              vdenso: vdenso
            }).then((res) => {                                  
                result = res.data;                           
            });
      return result;  
    },
    async Update_LuotTao_BienLai(vloaibienlai_id, vluottao_id, vma_luottao, iSoBLQuyen, vtuso, vdenso, vtong_bl) {
      console.log(vloaibienlai_id + " " + vluottao_id  + " " + vma_luottao + " " +  iSoBLQuyen + " " +  vtuso + " " +  vdenso + " " +   + vtong_bl)
      let result = {};
      await api.Update_LuotTao_BienLai(this.axios, {
              vloaibl_id: vloaibienlai_id,
              vma_luot: vma_luottao,
              vluottao_id: vluottao_id,
              vtuso: vtuso,
              vtongso: vtong_bl,
              vso_bl_quyen: iSoBLQuyen,
              vdenso: vdenso
            }).then((res) => {                 
                result = res.data;                               
            });
      return result;       
    },
    async TaoDuLieu_LuotBienLai(themmoi) {
      let vtuso = this.model.tu_so
      let iSoBLQuyen = this.model.blQuyen;
      let vma_luottao = this.model.ma_luot_tao
      let vloaibienlai_id = this.loaibienlai_id
      let vdenso = this.model.den_so;
      let vtong_bl = this.model.tong_so_bl;
      if (!themmoi) vma_luottao = this.group_luot + " - " + this.model.ma_luot_tao;
      this.resultUpdate = {};
      if (themmoi) {
        this.resultUpdate = await this.Insert_LuotTao_BienLai(vloaibienlai_id, vma_luottao, iSoBLQuyen, vtuso, vdenso);
      } else {
        this.resultUpdate = await this.Update_LuotTao_BienLai(vloaibienlai_id, this.vluottao_id, vma_luottao, iSoBLQuyen, vtuso, vdenso, vtong_bl);
      }
    },
    KiemTra_DuLieu(loai) { // loai = 1 : Đặt mới, loai = 2 : Cập nhật, loai = 3 : Xóa      
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
            this.$toast.warning("Bạn nhập số biên lai đến chưa chính xác. Vui lòng kiểm tra lại!");
            $("#tb_denso").focus();
            return false;
        }
      }

      if (loai == 2) {
          if (this.loaibienlai_id != this.vLoaiBL) {
              this.$toast.warning("Không thể cập nhật. Vui lòng tạo mới biên lai cho loại biên lai này !" + this.vLoaiBL);
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
      if (TuSo>0  && DenSo>0) {
        let TongSo = DenSo - TuSo + 1;
        this.model.tong_so_bl = TongSo;
        if (SoBLQuyen == 0) {
          SoBLQuyen = 1;
          this.model.blQuyen = 1;
        }
        if (TongSo % SoBLQuyen == 0) {
          this.model.so_quyen = (TongSo / SoBLQuyen);
        } else {
          this.model.so_quyen = 0;
        } 
      }
    },
    async Sinh_Bien_Lai_Auto(vds_luot_id, vloaibienlai_id, vnd_giao, vkho_id) {
      console.log(vds_luot_id + " " + vloaibienlai_id + " " +  vnd_giao + " " +  vkho_id);
      let result = "";
      await api.Sinh_Bien_Lai_Auto(this.axios, {
              vds_luot_id: vds_luot_id,
              vloaibienlai_id: vloaibienlai_id,
              vnd_giao: vnd_giao,
              vkho_id: vkho_id
            }).then((res) => {                 
                result = res.data.data;                               
            });
      return result;
    },
    async btnSinhBienLai_Click() {
      if (!this.visiable.sinhbienlai) return;
      try {
          if (!this.KiemTra_DuLieu(4)) return;
          if (this.ds_luottao.length == 0) {
              this.$toast.warning("Chưa có lượt tạo nào được chọn để sinh Biên lai. Vui lòng chọn lượt tạo biên lai hoặc nhóm lượt!");
              return;
          }

          let vds_luot_id = "";
          this.ds_luottao.forEach((item) => {
            vds_luot_id += item.luottao_id + ",";
          });

          vds_luot_id = vds_luot_id.substring(0, vds_luot_id.length-1);           
          this.loading(true);       

          let vkho_id = this.kho_id;
          let vnd_giao = "";
          let vloaibienlai_id = this.loaibienlai_id;

          let strMSG = await this.Sinh_Bien_Lai_Auto(vds_luot_id, vloaibienlai_id, vnd_giao, vkho_id);
          if (strMSG.toUpperCase() == "OK") {
              let result = await this.getDSLuotTaoBienLai(this.vloaibienlai_id, 0);
              if (result) {
                this.options.dsLuotTaoBienLai = result.returnds;
                this.dtBienLaiTheoLoai = result.returnds2;
                this.options.dsBienLaiTheoLoai = [];
                for (let index in this.dtBienLaiTheoLoai) {
                  if (this.dtBienLaiTheoLoai[index].luottao_id == this.vluottao_id) {
                    this.options.dsBienLaiTheoLoai.push(this.dtBienLaiTheoLoai[index]);
                  }
                }        
              }  
              this.$toast.success("Sinh biên lai thành công!");
          }
          else if (strMSG == "102") {
              this.$toast.warning("Sinh biên lai không thành công. Bạn vui lòng chọn lượt chưa sinh biên lai để thực hiện !");
          } else {
              this.$toast.error("Có lỗi: " + strMSG);
          }        
          this.loading(false);    
      }
      catch (ex) {
          this.$toast.error("Có lỗi : " + ex);
          this.loading(false);    
      }
    }
  },
  watch: {
    async "model.loaibienlai_id"(val) {
      this.loading(true);
      try {
        await this.getTuSoFromLuotTao(val)
        let pr_tu_so = this.model.tu_so;
              
        this.vloaibienlai_id = val;
        if (this.isLoad) {
            let result = await this.getDSLuotTaoBienLai(this.vloaibienlai_id, this.vluottao_id);
            if (result) {
                this.options.dsLuotTaoBienLai = result.returnds;
                this.dtBienLaiTheoLoai = result.returnds2;
                this.options.dsBienLaiTheoLoai = this.dtBienLaiTheoLoai;         
            }  
            await this.gridLuotTao_FocusedRowChanged();                 
        }

        this.isLoad = true;
        this.setButton(1);
        this.vluottao_id = 0;
        this.loading(false); 

        this.model.tu_so = pr_tu_so;
        this.model.den_so = this.model.tu_so;
      } catch (err) {
        this.loading(false);
      } 
    }
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

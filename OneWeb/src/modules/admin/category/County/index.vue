<template>
  <div>
    <breadcrumb :header="header" />
    <vue-nav>
      <ul class="list">
        <li :class="!visiable.nhapmoi ? 'non-ative' : ''">
          <a @click="tsbtnNhapMoi_Click()">
            <span class="icon one-file-plus"></span> Nhập mới
          </a>
        </li>
        <li :class="!visiable.ghilai ? 'non-ative' : ''">
          <a @click="tsbtnGhiLai_Click()">
            <span class="icon one-save"></span> Ghi lại
          </a>
        </li>
        <li :class="!visiable.huy ? 'non-ative' : ''">
          <a @click="tsbtnHuy_Click()">
            <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Hủy
          </a>
        </li>
        <li :class="!visiable.xoa ? 'non-ative' : ''">
          <a @click="tsbtnXoa_Click()">
            <span class="icon one-trash"></span> Xóa
          </a>
        </li>
      </ul>
    </vue-nav>
    <div class="page-content">
      <form @submit.prevent="preventSubmit" id="form-element">
        <vue-card>
          <div class="legend-title">
            <div class="pull-left">
              <span></span> Cập nhật thông tin phường xã
            </div>
            <div class="clearfix"></div>
          </div>     
          <b-row>
            <div class="col-md-6">
              <vue-element label="Tỉnh/Tp" class="col-md-12">
                <ejs-dropdownlist id="TINH_ID"
                  locale="vi-VN"
                  v-model="TINH_ID"
                  :dataSource="lstTinhThanh"
                  :allowFiltering="true" :filtering="onFilterTinh"
                  :fields="{ value: 'TINH_ID', text: 'TENTINH' }"
                />
              </vue-element>
            </div>
            <div class="col-md-6">
              <vue-element label="Phường ID Neo" class="col-md-12">
                <ejs-numerictextbox format='###' :min="0" v-model="model.phuong_id_neo" id="phuong_id_neo"></ejs-numerictextbox>
              </vue-element>
            </div>
          </b-row>
          <b-row>
            <div class="col-md-6">
              <vue-element label="Quận - Huyện" class="col-md-12">
                <ejs-dropdownlist id="quan_id"
                  locale="vi-VN"
                  v-model="model.quan_id"
                  :dataSource="lstQuanHuyen"
                  :allowFiltering="true" :filtering="onFilterQuan"
                  :fields="{ value: 'ID', text: 'NAME' }"
                />
              </vue-element>
              <vue-element label="Mã xã - phường" class="col-md-12">
                <ejs-textbox v-model="model.ma_phuong" id="ma_phuong"></ejs-textbox>
              </vue-element>
              <vue-element label="Tên xã - phường" class="col-md-12">
                <ejs-textbox v-model="model.ten_phuong" id="ten_phuong"></ejs-textbox>
              </vue-element>
            </div>
            <div class="col-md-6">
              <vue-textarea class="col-md-12" labelWidth="100" v-model="model.ghi_chu" height="100" label="Ghi chú" id="ghi_chu"></vue-textarea>
            </div>
          </b-row>
        </vue-card>
        <vue-card>
          <div class="legend-title">
            <div class="pull-left">
              <span></span> Danh sách xã - phường
            </div>
            <div class="clearfix"></div>
          </div>
          <b-row>
            <div>    
              <DataGridCustom
                  ref="focusXa"
                  v-bind:columns="collumnsPhuongXa"
                  v-bind:dataSource="dtPhuongXa"
                  :showColumnCheckbox="false"
                  :showFilter="true"
                  :enabledSelectFirstRow="true"
                  :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                  panelDataHeight="300"
                  @rowSelected="grid_FocusedRowChanged"
                  :allowPaging="true" :enablePagingServer="false"
              ></DataGridCustom>                                    
            </div>
          </b-row>
        </vue-card>
      </form>
    </div>
  </div>
</template>
<script>

import breadcrumb from '@/components/breadcrumb'
import collums from "./define/collums.js";
import VueElement from "./components/VueElement.vue";
import api from "./api/index.js";
import DataGridCustom from "./components/DataGrid";
import { Query } from '@syncfusion/ej2-data'

export default {
  components: {
    breadcrumb,
    VueElement,
    DataGridCustom
  },
  data() {
    return {
      ...collums,
      target: '.main-wrapper',
      model: {
        quan_id: "",
        phuong_id_neo: "",
        ma_phuong: "",
        ten_phuong: "",
        ghi_chu: ""
      },
      phuong_id_neo: "",
      phuong_id: "",
      quan_id_neo: "",
      lstTinhThanh: [],
      lstQuanHuyen: [],
      dtPhuongXa: [],
      visiable: {
        nhapmoi: true,
        ghilai: true,
        huy: true,
        xoa: true
      },
      header: {
        title: "Phường/Xã",
        list: [
          { name: "Quản trị hệ thống", link: { name: "Ui.cards" } },
          {
            name: "Phường/Xã",
            link: { name: "Ui.buttons" },
          },
        ],
      },
    };
  },
  mounted() {
    this.init();
  },
  computed: {
    TINH_ID: {
      get() {
        return Number(this.model.TINH_ID);
      },
      set(val) {
        if(val)
          this.$set(this.model, "TINH_ID", val);
      },
    },
  },
  methods: {
    async init() {
      try {     
        this.setButton(-1);
        await this.cboTinhThanh_Load();
        if (this.lstTinhThanh) {
          for (let index in this.lstTinhThanh) {
            if (this.lstTinhThanh[index].TENTAT == this.$root.token.getMaTinh()) {
              this.$set(this, "TINH_ID", this.lstTinhThanh[index].TINH_ID);
              break;
            }
          }   
        }
        
        await this.cboQuanHuyen_Load(this.TINH_ID);
        if (this.lstQuanHuyen != null && this.lstQuanHuyen.length > 0) {
          this.model.quan_id = this.lstQuanHuyen[0].ID;
        }
      } catch (e) {
        console.log(e);
        this.$toast.error(e.data ? e.data.message : "Khởi tạo thất bại");
      }
    },
    setButton(kieu) {
      this.visiable.nhapmoi = false;
      this.visiable.ghilai = false;
      this.visiable.huy = false;
      this.visiable.xoa = false;

      if (kieu == -1) {     //Bat dau
        this.visiable.ghilai = true;
        this.visiable.huy = true;
      }

      if (kieu == 0) {      //Bat dau
        this.visiable.nhapmoi = true;
        this.clear();
      }

      if (kieu == 1) {      //Them moi
        $('#ma_phuong').focus();
        this.visiable.ghilai = true;
        this.visiable.huy = true;
        this.clear();
        this.phuong_id_neo = "";
        this.phuong_id = "";
      }

      if (kieu == 2) {    // Huy
        this.visiable.nhapmoi = true;
        this.visiable.xoa = true;
        this.clear();
      }

      if (kieu == 3) {    //Edit
        this.visiable.nhapmoi = true;
        this.visiable.xoa = true;
        this.visiable.ghilai = true;
        this.visiable.huy = true; 
      }
    },
    clear() {
        this.model.ma_phuong = "";
        this.model.ten_phuong = "";
        this.model.ghi_chu = "";
        this.model.phuong_id_neo = "";
        this.phuong_id_neo = "";
    },
    async cboTinhThanh_Load() {
      this.loading(true);
      try {
        await api.cboTinhThanh_Load(this.axios, {

            }).then((res) => {                 
                this.lstTinhThanh = res.data.data ? res.data.data.filter(x=>x.TINH_ID==this.$root.token.getPhanVungID()) : [];              
            });
      } catch (e) {
        console.log(e);
      }
      this.loading(false);
    },
    async cboQuanHuyen_Load(tinh_id) {
      this.loading(true);
      try {
        await api.cboQuanHuyen_Load(this.axios, {
              params :{"tinh_id": tinh_id}
            }).then((res) => {                 
                this.lstQuanHuyen = res.data.data ? res.data.data : [];                
            });
      } catch (e) {
        console.log(e);
      }
      this.loading(false);
    },
    async Hienthi_danhsach(quan_id) {
      this.loading(true);
      try {
        await api.Hienthi_danhsach(this.axios, {
              p_quan_id: quan_id
            }).then((res) => {                 
                this.dtPhuongXa = res.data.data ? res.data.data : [];     
                if (this.dtPhuongXa.length <= 0) {
                  this.setButton(1);
                } else {
                  this.$refs.focusXa.setCurrentSelectedRow(0);
                }              
            });
      } catch (e) {
        this.dtPhuongXa = [];
        this.setButton(1);
        console.log(e);
      }
      this.loading(false);
    },
    grid_FocusedRowChanged(data) {
      try {
        if (data) {
          let row = data.data;
          if (row != undefined) {
            this.setDataFromGrid(row);        
          }
          this.setButton(3);
        }
      } catch (err) {
        console.log(err);
      } 
    },
    setDataFromGrid(row) {
      if (row.PHUONG_ID == undefined) {
        row = row[0];
      }
      if (row != undefined) {
        this.model.ma_phuong = row.MA_PHUONG;
        this.model.ten_phuong = row.TEN_PHUONG;
        this.model.ghi_chu = row.GHICHU;
        this.model.phuong_id_neo = row.PHUONG_ID_NEO;

        this.phuong_id = row.PHUONG_ID;
        this.phuong_id_neo = row.PHUONG_ID_NEO;
        this.quan_id_neo = row.QUAN_ID_NEO;
      }
    },
    tsbtnNhapMoi_Click() {
      if (!this.visiable.nhapmoi) return;
      this.setButton(1);
    },
    tsbtnHuy_Click() {
      if (!this.visiable.huy) return;
      this.setButton(2);
    },
    async kiemtra_dl_phuong(vPhuongID) {
      let valid = "";
      try {
        await api.kiemtra_dl_phuong(this.axios, {
              p_kieu: "3",
              p_quan_id: this.model.quan_id,
              p_phuong_id: vPhuongID,
              p_ma_phuong: this.model.ma_phuong,
              p_ten_phuong: this.model.ten_phuong
            }).then((res) => {                 
              let result = res.data;
              if (result.error_code != "BSS-00000000") {
                valid = result.message;
              }      
            });
      } catch (err) {
        valid = err.data.message;
      }
      return valid;
    },
    async valid_dl_phuong(vPhuongID) {
      let valid = "";
      try {
        await api.valid_dl_phuong(this.axios, {
              action: 1,
              quan_id: this.model.quan_id,
              phuong_id: vPhuongID,
              ma_phuong: this.model.ma_phuong,
              ten_phuong: this.model.ten_phuong,
              phuong_id_neo: this.model.phuong_id_neo
            }).then((res) => {                     
              let result = res.data;
              if (result.error_code != "BSS-00000000") {
                valid = result.message;
              }      
            });
      } catch (err) {
        valid = err.data.message;
      }
      return valid;
    },
    async tsbtnXoa_Click() {
      if (!this.visiable.xoa) return;
      this.loading(true);
      try {
        if (this.phuong_id == undefined || this.phuong_id == null || this.phuong_id == "") {
          this.loading(false);
          this.$toast.error("Hãy chọn phường");
          return;
        }

        let checkValid = await this.kiemtra_dl_phuong(this.phuong_id);
        if (checkValid != "") {
          this.loading(false);
          this.$toast.error(checkValid);     
          return;
        }

        this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xóa dữ liệu không ?', {
          title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
        }).then(async v => {
          if (v) {
            try {              
              let result = await this.xoa_phuong(this.phuong_id);
              if (result.error_code == "BSS-00000000") {
                this.setButton(1);
                this.Hienthi_danhsach(this.model.quan_id);
                this.$toast.success("Xóa phường thành công !");
              } else {
                this.$toast.error(result.message_detail);  
              }           
            } catch (e) {
              console.log(e);
              this.$toast.error("Không thể xóa bản ghi này " + e);  
            }
          }
        });
      } catch (err) {
        console.log(err);
      } 
      this.loading(false);
    },
    async xoa_phuong(phuong_id) {
      let result;
      try {
        if (await this.dongbo_diadanh_ccbs() == "1") {
          let resCCBS = await this.ts_danhmuc_delete_phuongs()
          if (resCCBS != "1") {
            this.$toast.error(resCCBS);
            return
          }
        }

        await api.xoa_phuong(this.axios, {
              phuong: [{"PHUONG_ID": phuong_id}]
            }).then((res) => {            
              result = res.data;
            });
      } catch (err) {
        console.log(err);
        result = err.data;
      }
      
      return result;
    }, 
    async tsbtnGhiLai_Click() {
      if (!this.visiable.ghilai) return;    
      try {
        if (!this.Kiemtra_DL()) return;

        let result;
        let index = this.$refs.focusXa.$refs.grid.getSelectedRowIndexes();
        let focusUpdate = this.dtPhuongXa[index] ? this.dtPhuongXa[index].PHUONG_ID : -1;
        let kPhuong_id;
        this.loading(true);

        if (!this.visiable.nhapmoi) {
          kPhuong_id = await this.getKey();
          result = await this.Insert(kPhuong_id);        
          if (result) {
            let vDS = [];
            await api.Hienthi_danhsach(this.axios, {
              p_quan_id: this.model.quan_id
            }).then((res) => {           
                vDS = res.data.data ? res.data.data : [];                  
            });  

            if (vDS.length > 0) {
              let idxFocus;        
              for (let idx in vDS) {
                if (vDS[idx].PHUONG_ID == kPhuong_id) {
                  vDS[idx].STT = 1;
                  idxFocus = idx;
                  break;
                }
              }

              let newPhuong = vDS[idxFocus];
              for (let idx in this.dtPhuongXa) {
                this.dtPhuongXa[idx].STT += 1;
              }         
              this.dtPhuongXa.unshift(newPhuong);
              this.$refs.focusXa.setCurrentSelectedRow(0);
              this.setButton(1);
            }  
          }
        } else {
          result = await this.Update();
          if (result) {
            await this.Hienthi_danhsach(this.model.quan_id);
            if (focusUpdate != -1) {
              for (let idx in this.dtPhuongXa) {
                if (this.dtPhuongXa[idx].PHUONG_ID == focusUpdate) {
                  this.$refs.focusXa.setCurrentSelectedRow(idx);
                }            
              } 
            }        
          }         
        }     
      } catch (err) {
        console.log(err);
      }
      this.loading(false);
    },
    async getKey() {
      let key;
      try {
        await api.getKey(this.axios, {
              keyName : "PHUONG",
              numBlockSize : 1,
              numRetry:  10
            }).then((res) => {         
              if (res.data.error_code == "BSS-00000000") {
                key = res.data.data;
              }
            });
      } catch(e) {
        console.log(e)
      }
      return key;
    },
    async Insert(kPhuong_id) {
      try {
        let checkValid = await this.valid_dl_phuong(this.phuong_id);
        if (checkValid != "") {
          this.$toast.error(checkValid);
          return false;
        }

        if (await this.dongbo_diadanh_ccbs() == "1") {
          let resCCBS = await this.ts_danhmuc_insert_phuongs()
          if (resCCBS != "1") {
            this.$toast.error(resCCBS);
            return false;
          }
        }

        let vPhuong = {
          "phuong": [
            {          
              "QUAN_ID": this.model.quan_id,   
              "PHUONG_ID": kPhuong_id,                     
              "MA_PHUONG": this.model.ma_phuong,
              "TEN_PHUONG": this.model.ten_phuong,       
              "PHUONG_ID_NEO": this.model.phuong_id_neo,                          
              "GHICHU": this.model.ghi_chu,     
              "ACTIVED": 1,
              "DANSO": null,
              "SOHO": null,
              "LOAI": null,
              "THUTU": null
            }
          ]
        }

        await api.them_phuong(this.axios, vPhuong).then((res) => {                  
               this.$toast.success("Thêm mới phường lên dữ liệu Viễn thông tỉnh thành công !");       
            });
        return true;

      } catch (err) {
        console.log(err);
        this.$toast.error("Xảy ra lỗi khi thêm mới phường: " + err.data.message_detail);
        return false;
      }
    },
    async Update() {
      try {
        let checkValid = await this.valid_dl_phuong(this.phuong_id);
        if (checkValid != "") {
          this.$toast.error(checkValid);
          return false;
        }

        if (await this.dongbo_diadanh_ccbs() == "1" && this.model.phuong_id_neo != "") {
          let resCCBS = await this.ts_danhmuc_update_phuongs()
          if (resCCBS != "1") {
            this.$toast.error(resCCBS);
            return false;
          }
        }

        let vPhuong = {
          "phuong": [
            {          
              "QUAN_ID": this.model.quan_id,   
              "PHUONG_ID": this.phuong_id,                     
              "MA_PHUONG": this.model.ma_phuong,
              "TEN_PHUONG": this.model.ten_phuong,       
              "PHUONG_ID_NEO": this.model.phuong_id_neo,                          
              "GHICHU": this.model.ghi_chu,     
              "ACTIVED": 1,
              "DANSO": null,
              "SOHO": null,
              "LOAI": null,
              "THUTU": null
            }
          ]
        }

        await api.sua_phuong(this.axios, vPhuong).then((res) => {                   
               this.$toast.success("Cập nhật phường lên dữ liệu Viễn thông tỉnh thành công !");      
            });
        return true;

      } catch (err) {
        console.log(err);
        this.$toast.error("Xảy ra lỗi khi cập nhật phường: " + err.data.message_detail);
        return false;
      }
    },
    Kiemtra_DL() {
      if (this.model.quan_id == null || this.model.quan_id == "") {
        this.$toast.error("Bạn chưa chọn Quận hoặc Huyện!");
        $("#quan_id").focus();
        return false;
      }
      if (this.model.ten_phuong == null || this.model.ten_phuong == "") {
        this.$toast.error("Hãy nhập tên phường!");
        $("#ten_phuong").focus();
        return false;
      }
      if (this.model.ma_phuong != undefined && this.model.ma_phuong != null && this.model.ma_phuong.length > 10) {
        this.$toast.error("Mã phường quá số ký tự cho phép.");
        $("#ma_phuong").focus();
        return false;
      }
      if (this.model.ten_phuong != undefined && this.model.ten_phuong != null && this.model.ten_phuong.length > 50) {
        this.$toast.error("Tên phường quá số ký tự cho phép.");
        $("#ten_phuong").focus();
        return false;
      }
      if (this.model.ghi_chu != undefined && this.model.ghi_chu != null && this.model.ghi_chu.length > 50) {
        this.$toast.error("Ghi chú quá số ký tự cho phép.");
        $("#ghi_chu").focus();
        return false;
      }
      return true;
    },
    onFilterTinh: function(e){
      var query = new Query();
      query = (e.text !== '') ? query.where('TENTINH', 'contains', e.text, true) : query;
      e.updateData(this.lstTinhThanh, query);
    },
    onFilterQuan: function(e){
      var query = new Query();
      query = (e.text !== '') ? query.where('NAME', 'contains', e.text, true) : query;
      e.updateData(this.lstQuanHuyen, query);
    },
    async dongbo_diadanh_ccbs() {
      let valid = "0";
      try {
        await api.dongbo_diadanh_ccbs(this.axios, {}).then((res) => {                     
          let result = res.data;
          if (result && result.error_code == "BSS-00000000") {
            valid = result.data;
          }      
        });
      } catch (err) {
        
      }
      return valid;
    },
    async ts_danhmuc_insert_phuongs() {
      let result = "Có lỗi xảy ra trong quá trình kết nối với Service VinaPhone! "
      try {
        let params = {
          "phuongid": "",
          "tenphuong": this.model.ten_phuong,
          "prefix": "HAIPHONG",
          "quanid": this.quan_id_neo,
          "muccuocid": "0"
        }
        await api.ts_danhmuc_insert_phuongs(this.axios, params).then((res) => {                     
          if (res.data && res.data.error_code == "BSS-00000000" && res.data.data) {
            let kq = res.data.data     
            kq = kq.substring(0, 10);
            if (kq == "THANH_CONG") {
              this.phuong_id_neo = kq.substring(10)
              this.model.phuong_id_neo = this.phuong_id_neo
              result = "1"
            } else {
              result += kq
            }
          }      
        });
      } catch (err) {
        result += err.message
      }
      return result
    },
    async ts_danhmuc_update_phuongs() {
      let result = "Có lỗi xảy ra trong quá trình kết nối với Service VinaPhone! "
      try {
        let params = {
          "phuongid": this.model.phuong_id_neo,
          "tenphuong": this.model.ten_phuong,
          "prefix": "HAIPHONG",
          "quanid": this.quan_id_neo,
          "muccuocid": "0"
        }
        await api.ts_danhmuc_update_phuongs(this.axios, params).then((res) => {                     
          if (res.data && res.data.error_code == "BSS-00000000" && res.data.data) {
            let kq = res.data.data     
            if (kq == "THANH_CONG") {
              result = "1"
            } else {
              result += kq
            }
          }      
        });
      } catch (err) {
        result += err.message
      }
      return result
    },
    async ts_danhmuc_delete_phuongs() {
      let result = "Có lỗi xảy ra trong quá trình kết nối với Service VinaPhone! "
      try {
        let params = {
          "phuongid": this.phuong_id_neo
        }
        await api.ts_danhmuc_delete_phuongs(this.axios, params).then((res) => {                     
          if (res.data && res.data.error_code == "BSS-00000000" && res.data.data) {
            let kq = res.data.data     
            if (kq == "THANH_CONG") {
              result = "1"
            } else {
              result += kq
            }
          }      
        });
      } catch (err) {
        result += err.message
      }
      return result
    },
  },
  watch: {
    async "model.TINH_ID"(val) {
      if (val) {
        await this.cboQuanHuyen_Load(val);       
        this.dtPhuongXa = [];
        this.setButton(1); 
        if (this.lstQuanHuyen != null && this.lstQuanHuyen.length > 0) {
          this.model.quan_id = this.lstQuanHuyen[0].ID;
        }
      }   
    },
    async "model.quan_id"(val) {
      if (val) {
        for (let i=0; i<this.lstQuanHuyen.length; i++) {
          if (this.lstQuanHuyen[i].QUAN_ID == val) {
            this.quan_id_neo = this.lstQuanHuyen[i].QUAN_ID_NEO
          }        
        }
        console.log("quan_id_neo = " + this.quan_id_neo)
        await this.Hienthi_danhsach(val);
      } 
    }
  },
};
</script>
<style>
  .non-ative a {
    color: #d3d3d3 !important;
  }
  .e-numeric.e-control-wrapper.e-input-group .e-input-group-icon {
    display: none;
  }
</style>

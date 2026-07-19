<template>
  <div>
    <breadcrumb :header="header" />
    <vue-nav>
      <ul class="list">
        <li>
          <a @click="btnLamMoi_Click()">
            <span class="icon one-reload"></span> Làm mới (F5)
          </a>
        </li>
      </ul>
    </vue-nav>
    <div class="page-content">
      <form @submit.prevent="preventSubmit" id="form-element">
        <b-row>
          <div class="col-md-12">
            <b-row>
              <vue-card>
                <div class="legend-title">
                  <div class="pull-left">
                    <span></span> Chọn thuộc tính
                  </div>
                  <div class="clearfix"></div>
                </div>
                <vue-element label="Thuộc tính" class="col-md-12">
                  <select2 v-model="thuoctinh_id" :options="lstThuocTinh" @select="onThuocTinhChange($event)"
                           tabindex="1" placeholder="Vui lòng chọn thuộc tính" />
                </vue-element>
              </vue-card>
            </b-row>
          </div>
        </b-row>
        <b-row>
          <b-col md="12">
              <div class="box-move-select-table">
                  <div class="box-col box-form">
                      <vue-card> 
                        <b-row>
                          <div class="legend-title">
                            <div class="pull-left">
                              <span></span> Danh sách loại vật tư đã gán
                            </div>
                            <div class="pull-right">
                              <div class="check-action normal">
                                <input v-model="model.chb_chonnhanh" type="checkbox" class="check" id="chb_chonnhanh" @change="onCheckbox" />
                                <span class="name">Chọn nhanh</span>
                              </div>
                              <div class="clearfix"></div>
                            </div>
                          </div>
                        <DataGridCustom
                            ref="thuocTinhDaGan"
                            v-bind:columns="collumns"
                            v-bind:dataSource="dsThuocTinhDaGan"
                            :showColumnCheckbox="true"
                            :showFilter="true"
                            :enabledSelectFirstRow="false"
                            :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                            panelDataHeight="320"
                            @rowSelected="rowSelected" @rowDeselected="rowDeselected"
                            @pageChanged="pageChanged" @actionComplete="actionComplete"
                            :allowPaging="true" :enablePagingServer="false"
                        ></DataGridCustom>
                        </b-row>
                      </vue-card>
                  </div>
                  <div class="actions">
                      <button @click="GO_THUOCTINH_VATTU" class="btn">
                          <span class="-ap icon-chevron-thin-right"></span>
                      </button>
                      <button @click="GAN_THUOCTINH_VATTU" class="btn">
                          <span class="-ap icon-chevron-thin-left"></span>
                      </button>
                  </div>
                  <div class="box-col box-form">
                      <vue-card> 
                        <b-row>
                          <div class="legend-title">
                            <div class="pull-left">
                              <span></span> Danh sách loại vật tư chưa gán
                            </div>
                            <div class="clearfix"></div>
                          </div>
                        </b-row>
                        <b-row>
                          <div class="control-section tree-check" style="height: 445px;overflow:auto;">
                            <div class="tree-plus tree-area">
                              <ejs-treeview  id='treeview' ref="treeObj" :nodeChecked="this.nodeChecked"  :fields='this.fields' :showCheckBox='true'></ejs-treeview>
                            </div>
                          </div>
                        </b-row>
                      </vue-card>
                  </div>
              </div>
          </b-col>
        </b-row>
      </form>
    </div>
  </div>
</template>
<script>

import breadcrumb from '@/components/breadcrumb'
import collums from "./define/collums.js";
import api from "./api/index.js";
import DataGridCustom from "./components/DataGrid";
export default {
  components: {
    breadcrumb,
    DataGridCustom,
  },
  data() {
    return {
      ...collums,
      target: '.main-wrapper',
      header: {
        title: "Gán thuộc tính - Loại vật tư",
        list: [
          { name: "Quản lý", link: { name: "Ui.cards" } },
          {
            name: "Gán thuộc tính - Loại vật tư",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      model: {},
      lstThuocTinh: [],
      dsThuocTinhDaGan: [],
      checkedThuocTinhDG: [],
      dsThuocTinhChuaGan: [],
      checkedThuocTinhCG: [],   
      fields: '',
      isPageChange: false,
    };
  },
  mounted() {
    this.init();
  },
  computed: {
    thuoctinh_id: {
      get() {
        return Number(this.model.thuoctinh_id);
      },
      set(val) {
        if(val)
          this.$set(this.model, "thuoctinh_id", val);
      },
    }
  },
  methods: {
    async init() {
      try {
        this.loading(true);
        $("#chb_chonnhanh").prop('checked', false);
        await this.getDSThuocTinh();
        await this.NAP_DS_VATTU_DG();
        await this.created();        
      } catch (e) {
        console.log(e);
        this.$toast.error(e.data ? e.data.message : "Khởi tạo thất bại");
      } finally {
        this.loading(false);
      }
    },
    async btnLamMoi_Click() {
      try {
        this.loading(true);
        this.dsThuocTinhDaGan = [];
        this.checkedThuocTinhDG = [];
        this.dsThuocTinhChuaGan = [];
        this.checkedThuocTinhCG = [];
        $("#chb_chonnhanh").prop('checked', false);
        await this.NAP_DS_VATTU_DG();
        await this.created();
      } catch (e) {
        console.log(e);
      } finally {
        this.loading(false);
      }
    },
    async getDSThuocTinh() {
      try {
        let response = await api.getDSThuocTinh(this.axios);
        let items = [];
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
              response.data.data.forEach(function (item) {
                items.push({
                  id: item.thuoctinh_id,
                  text: item.thuoctinh,
                });
              });

              this.lstThuocTinh = items;
              this.thuoctinh_id = items[0].id;
        }
      } catch (err) {
        console.log(err);
      }
    },
    async NAP_DS_VATTU_DG() {
      try {
          let params = {
            thuoctinh_id: this.thuoctinh_id
          }
          let response = await api.NAP_DS_VATTU_DG(this.axios, params);
          if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
              this.dsThuocTinhDaGan = response.data.data ? response.data.data : [];
          }
      } catch(e) {
          console.log(e);           
      }
    },
    async NAP_DS_VATTU_CG() {
      try {
          let params = {
            thuoctinh_id: this.thuoctinh_id
          }
          let response = await api.NAP_DS_VATTU_CG(this.axios, params);
          if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
              this.dsThuocTinhChuaGan = response.data.data ? response.data.data : [];
          }
      } catch(e) {
          console.log(e);           
      }
    },
    async created() {
      try {
        this.loading(true);
        await this.NAP_DS_VATTU_CG();
        let lvtChuaGan = this.createTreeLVTChuaGan(this.dsThuocTinhChuaGan);
        this.fields = { dataSource: lvtChuaGan, id: 'loaivt_id', text: 'loai_vt', parentID: 'loaivt_cha_id', hasChildren: 'hasChild' }
      } catch (e){
      } finally {
        this.loading(false)
      }
    },
    nodeChecked(treeObj){
      var treeObj = document.getElementById('treeview').ej2_instances[0];
      this.checkedThuocTinhCG = treeObj.checkedNodes
    },
    createTreeLVTChuaGan(dsLVT){
      let tree = []
      let lvtChaArray = dsLVT.map(item => (item['loaivt_cha_id']))
      dsLVT.forEach(item => {
        if (lvtChaArray.includes(item['loaivt_id'])){
          tree.push( {...item, hasChild:true, expanded: false})
        } else {
          tree.push(item)
        }
      })
      return tree;
    },
    async gan_thuoctinh_vattu(vtGan) {
        let kt = false;
        let params = {
          ds_loaivt_id: vtGan,
          thuoctinh_id: this.thuoctinh_id
        }
        let response = await api.gan_thuoctinh_vattu(this.axios, params);
        console.log(response)
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
            kt = true;
        } else {
            this.$toast.error("Lỗi khi gán thuộc tính loại vật tư: " + response.data.message);
        }
        return kt;
    },
    async GAN_THUOCTINH_VATTU() {
        try {
            this.loading(true);
            if (this.checkedThuocTinhCG.length != 0) {
                let vtGan = ",";
                for (let tt of this.checkedThuocTinhCG) {
                    vtGan += (tt + ",");
                }
                let kt = await this.gan_thuoctinh_vattu(vtGan);
                if (kt) {
                    this.$toast.success("Gán thành công");
                    await this.btnLamMoi_Click();
                }     
            } else {
                this.$toast.error("Hãy chọn ít nhất 1 loại vật tư trước khi gán");
            }
        } catch (ex) {
            this.$toast.error("Lỗi khi gán thuộc tính loại vật tư: " + ex);
        } finally {
            this.loading(false);
        }
    },
    async go_thuoctinh_vattu(vtGoGan) {
        let kt = false;
        let params = {
          ds_loaivt_id: vtGoGan,
          thuoctinh_id: this.thuoctinh_id
        }
        let response = await api.go_thuoctinh_vattu(this.axios, params);
        console.log(response)
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
            kt = true;
        } else {
            this.$toast.error("Lỗi khi gán thuộc tính loại vật tư: " + response.data.message);
        }
        return kt;
    },
    async GO_THUOCTINH_VATTU() {
        try {
            this.loading(true);
            if (this.checkedThuocTinhDG.length != 0) {
                let vtGoGan = ",";
                for (let tt of this.checkedThuocTinhDG) {
                    vtGoGan += (tt.loaivt_id + ",");
                }
                let kt = await this.go_thuoctinh_vattu(vtGoGan);
                if (kt) {
                    this.$toast.success("Gỡ thành công");
                    await this.btnLamMoi_Click();
                }  
            } else {
                this.$toast.error("Hãy chọn ít nhất 1 loại vật tư trước khi gỡ");
            }
        } catch (ex) {
            this.$toast.error("Lỗi khi gỡ gán thuộc tính loại vật tư: " + ex);
        } finally {
            this.loading(false);
        }
    },
    preventSubmit() {
      return false;
    },
    async onThuocTinhChange(agrs) {
      await this.btnLamMoi_Click();
    },
    rowSelected(data) {
      try {
        if (data.data.length == undefined) {
            this.checkedThuocTinhDG.push(data.data)
        } else {
            let check = document.getElementById("chb_chonnhanh")
            if (check.checked) {
                this.checkedThuocTinhDG = this.dsThuocTinhDaGan.slice()
            } else {
                for (let i=0; i<data.data.length; i++) {
                    let isExists = false
                    for (let ix=0; ix<this.checkedThuocTinhDG.length; ix++) {
                        if (data.data[i].loaivt_id == this.checkedThuocTinhDG[ix].loaivt_id) {
                            isExists = true;
                            break
                        }
                    }
                    if (!isExists) {
                        this.checkedThuocTinhDG.push(data.data[i])
                    }       
                }      
            }
        }
      } catch (err) {
        console.log(err);
      }
    },
    rowDeselected(data) {
        if (this.isPageChange) return
        if (data.data.length == undefined) {
            $("#chb_chonnhanh").prop('checked', false); 
            let iRemove
            for (let i=0; i<this.checkedThuocTinhDG.length; i++) {
              if (this.checkedThuocTinhDG[i].loaivt_id == data.data.loaivt_id) {              
                iRemove = i;
                break;
              }
            }
            this.checkedThuocTinhDG.splice(iRemove, 1)
        } else {
            if (data.data.length != this.checkedThuocTinhDG.length) {
                $("#chb_chonnhanh").prop('checked', false);
                this.onCheckbox()
                return
            }
            
            let check = document.getElementById("chb_chonnhanh")
            if (check.checked) {
                this.checkedThuocTinhDG = this.dsThuocTinhDaGan.slice()
            } else {      
                for (let ix=0; ix<data.data.length; ix++) {
                    let iRemove = -1
                    for (let i=0; i<this.checkedThuocTinhDG.length; i++) {
                        if (this.checkedThuocTinhDG[i].loaivt_id == data.data[ix].loaivt_id) {              
                            iRemove = i;
                            break;
                        }
                    }
                    this.checkedThuocTinhDG.splice(iRemove, 1)
                }
            }         
        }     
    },
    pageChanged() {
        this.isPageChange = true;
        let check = document.getElementById("chb_chonnhanh")
        if (check.checked) {
            this.checkedThuocTinhDG = this.dsThuocTinhDaGan.slice()
        }
        this.dsThuocTinhDaGan = [...this.dsThuocTinhDaGan]
    },
    actionComplete() {
        let lstSelect = []
        let pIndex = this.$refs.thuocTinhDaGan.$refs.pagination.pageIndexData
        let pSize = this.$refs.thuocTinhDaGan.$refs.pagination.pageSizeData
        for (let i=0; i<this.dsThuocTinhDaGan.length; i++) {
            for (let iS=0; iS<this.checkedThuocTinhDG.length; iS++) {
                if (this.dsThuocTinhDaGan[i].loaivt_id == this.checkedThuocTinhDG[iS].loaivt_id) {
                    if (i>=pIndex*pSize && i<(pIndex+1)*pSize) {
                        lstSelect.push(i - pIndex*pSize)             
                    }
                    break;
                } 
            }
        }
        this.$refs.thuocTinhDaGan.$refs.grid.selectRows(lstSelect)
        this.isPageChange = false;
    },
    onCheckbox() {
        this.isPageChange = true
        let check = document.getElementById("chb_chonnhanh")
        if (check.checked) {
              this.checkedThuocTinhDG = this.dsThuocTinhDaGan.slice()
        } else {
            this.checkedThuocTinhDG = [];
        }
        this.dsThuocTinhDaGan = [...this.dsThuocTinhDaGan]
    }
  },
};
</script>
<style>
  
</style>

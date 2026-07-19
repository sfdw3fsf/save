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
                    <span></span> Loại hợp đồng
                  </div>
                  <div class="clearfix"></div>
                </div>
                <vue-element label="Loại HĐ" class="col-md-12">
                  <select2 v-model="LOAIHD_ID" :options="lstLoaiHD" @select="onLoaiHDChange($event)"
                           tabindex="1" placeholder="Vui lòng chọn loại HĐ" />
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
                              <span></span> Danh sách mục đích đã gán
                            </div>
                            <div class="pull-right">
                              <div class="check-action normal">
                                <input v-model="model.chb_chonnhanh" type="checkbox" class="check" id="chb_chonnhanh" @change="onCheckbox" />
                                <span class="name">Chọn nhanh</span>
                              </div>
                              <div class="clearfix"></div>
                            </div>
                          </div>
                        <DataGrid
                            ref="mucDichDaGan"
                            v-bind:columns="collumnsMD"
                            v-bind:dataSource="options.dsMucDichDaGan"
                            :showColumnCheckbox="true"
                            :showFilter="true"
                            :enabledSelectFirstRow="false"
                            :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                            panelDataHeight="320"
                            @rowSelected="rowSelected" @rowDeselected="rowDeselected"
                            @pageChanged="pageChanged" @actionComplete="actionComplete"
                            :allowPaging="true" :enablePagingServer="false"
                        ></DataGrid>
                        </b-row>
                      </vue-card>
                  </div>
                  <div class="actions">
                      <button @click="GO_LOAIHD_MD" class="btn">
                          <span class="-ap icon-chevron-thin-right"></span>
                      </button>
                      <button @click="GAN_LOAIHD_MD" class="btn">
                          <span class="-ap icon-chevron-thin-left"></span>
                      </button>
                  </div>
                  <div class="box-col box-form">
                      <vue-card> 
                        <b-row>
                          <div class="legend-title">
                            <div class="pull-left">
                              <span></span> Danh sách mục đích chưa gán
                            </div>
                            <div class="clearfix"></div>
                          </div>
                        </b-row>
                        <b-row>
                          <div class="table-content">
                            <table class="table-result table-gachle">
                                <thead class="center">
                                    <tr>
                                        <th>Mục đích</th>
                                    </tr>
                                    <tr>
                                        <th>
                                            <div class="input-icon-right">
                                                <input type="text" class="form-control" v-model="filterTenVT" @keypress.enter="filterDSMD">
                                                <span class="icon nc-icon-outline ui-1_zoom"></span>
                                            </div>
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>
                                            <div class="tree-control-wrapper" style="height: 385px;overflow: auto;">
                                                <ejs-treeview id='treeview' ref="treeObj" :nodeChecked="this.nodeChecked"  :fields='this.fields' :showCheckBox='true'
                                                    :keyPress='this.nodeChecked' :selectedNodes="selectedNodes" :expandedNodes="expandedNodes"></ejs-treeview>
                                            </div>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
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
import VueElement from "./components/VueElement.vue";
import api from "./api/index.js";
import DataGridCustom from "./components/DataGrid";
import { Query } from '@syncfusion/ej2-data'
export default {
  components: {
    breadcrumb,
    VueElement,
    DataGridCustom,
  },
  data() {
    return {
      ...collums,
      target: '.main-wrapper',
      model: {},
      lstLoaiHD: [],
      checkedMucDichCG: [],
      fields: '',    
      checkedMucDichDG: [],
      checkedKieuLapDat: [],
      header: {
        title: "Loại HĐ - Mục đích",
        list: [
          { name: "Quản lý", link: { name: "Ui.cards" } },
          {
            name: "Loại HĐ - Mục đích",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      options: {
        dsMucDichDaGan: [],
        allMucDichDaGan: [],
        dsMucDichChuaGan: [],
        dsKieuLapDat: [],
      },
      isPageChange: false,
      vatTuTree: [],
      selectedNodes: [],
      expandedNodes:[-1],  
      VtuWord: "",
      filterTenVT: "",
      VtuIndex: 0,
      VtuCache: [],
      VtuChaCache:[],
    };
  },
  mounted() {
    this.init();
  },
  computed: {
    LOAIHD_ID: {
      get() {
        return Number(this.model.LOAIHD_ID);
      },
      set(val) {
        if(val)
          this.$set(this.model, "LOAIHD_ID", val);
      },
    }
  },
  methods: {
    async init() {
      try {
        $("#chb_chonnhanh").prop('checked', false);
        await this.getDSLoaiHD();
        await this.NAP_DS_MUCDICH_DG();
        await this.created();
      } catch (e) {
        console.log(e);
        this.$toast.error(e.data ? e.data.message : "Khởi tạo thất bại");
      }
    },
    async btnLamMoi_Click() {
      try {
        this.checkedMucDichCG = [];
        this.checkedMucDichDG = [];
        this.dsMucDichDaGan = [];
        this.allMucDichDaGan = [];
        this.dsMucDichChuaGan = [];
        $("#chb_chonnhanh").prop('checked', false);
        await this.NAP_DS_MUCDICH_DG();
        await this.created();
        await this.load_ds_kieuld();
      } catch (e) {
        console.log(e);
      }
    },
    async getDSLoaiHD() {
      this.loading(true);
      try {
        await api.getDSLoaiHD(this.axios, {  

          }).then((response) => {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({
                  id: item.LOAIHD_ID,
                  text: item.TEN_LOAIHD,
                });
              });
              this.lstLoaiHD = items;
              this.LOAIHD_ID = items[0].id;
            }
          });
      } catch (err) {
        console.log(err);
      }
      this.loading(false);
    },
    async NAP_DS_MUCDICH_DG() {
      this.loading(true);
      try {
        await api.NAP_DS_MUCDICH_DG(this.axios, {  
            vloaihd_id: this.LOAIHD_ID
          }).then((res) => {              
            this.options.dsMucDichDaGan = res.data.data ? res.data.data : [];
            this.options.allMucDichDaGan = res.data.data ? res.data.data : [];
          });
      } catch (err) {
        console.log(err);
      }
      this.loading(false);
    },
    async NAP_DS_MUCDICH_CG() {
      this.loading(true);
      try {
        await api.NAP_DS_MUCDICH_CG(this.axios, {  
            vloaihd_id: this.LOAIHD_ID
          }).then((res) => {              
            this.options.dsMucDichChuaGan = res.data.data ? res.data.data : [];
          });
      } catch (err) {
        console.log(err);
      }
      this.loading(false);
    },
    async created() {
      try {
        this.loading(true);
        await this.NAP_DS_MUCDICH_CG();
        let mucDichChuaGan = this.createTreeMucDichChuaGan(this.options.dsMucDichChuaGan);
        this.vatTuTree = mucDichChuaGan
        this.fields = { dataSource: mucDichChuaGan, id: 'mucdich_id', text: 'ten_md', parentID: 'mucdich_cha_id', hasChildren: 'hasChild' }
      } catch (e){
      } finally {
        this.loading(false)
      }
    },
    nodeChecked(treeObj){
      var treeObj = document.getElementById('treeview').ej2_instances[0];
      this.checkedMucDichCG = treeObj.checkedNodes
    },
    createTreeMucDichChuaGan(dsMucDich){
      let tree = []
      let mucDichChaArray = dsMucDich.map(item => (item['mucdich_cha_id']))
      // let mucDichArray = dsMucDich.map(item => (item['mucdich_id']))
      dsMucDich.forEach(item => {
        // check item la parent node
        if (mucDichChaArray.includes(item['mucdich_id'])){
          // check donvi_cha_id khong co trong ds donvi_id thi xoa donvi_cha_id
          // if (!mucDichArray.includes(item['mucdich_cha_id'])){
          //   delete item.mucdich_cha_id
          // }
          tree.push( {...item, hasChild:true, expanded: false})
        } else {
          tree.push(item)
        }
      })
      return tree;
    },
    async load_ds_kieuld() {
      this.loading(true);
      try {
        await api.load_ds_kieuld(this.axios, {  
            vloaihd_id: this.LOAIHD_ID
          }).then((res) => {              
            this.options.dsKieuLapDat = res.data.data ? res.data.data : [];
          });
      } catch (err) {
        console.log(err);
      }
      this.loading(false);
    },
    async fn_gan_loaihd_md(vjson_data, vds_data) {
      let result;
      this.loading(true);
      try {
        await api.fn_gan_loaihd_md(this.axios, {  
            vloaihd_id: this.LOAIHD_ID,
            vmucdich_id: "",
            vjson_data: vjson_data,
            vds_data: vds_data
          }).then((res) => {              
            result = res.data.data;
          });
      } catch (err) {
        result = err.data;
        console.log(err);
      }
      this.loading(false);
      return result;
    },
    async GAN_LOAIHD_MD() {
      try {
        let vds_data; // ds muc dich
        if (this.checkedMucDichCG.length > 0) {
          for (let idxMD in this.checkedMucDichCG) {
            vds_data += ("," + this.checkedMucDichCG[idxMD]);
          }
          vds_data += ",";

          if (this.checkedKieuLapDat.length > 0) {     
            let vjson_data; // ds kieu lap dat
            for (let idxLD in this.checkedKieuLapDat) {
                vjson_data += ("," + this.checkedKieuLapDat[idxLD]);           
            }
            vjson_data += ",";
            await this.fn_gan_loaihd_md(vjson_data, vds_data);    
          } else {
            await this.fn_gan_loaihd_md("", vds_data);          
          }
          
          this.$toast.success("Gán thành công");
          await this.btnLamMoi_Click();
        } else {
            this.$toast.error("Hãy chọn ít nhất 1 mục đích trước khi gán");
        }
      } catch (ex) {
          this.$toast.error("Lỗi khi gán mục đích - loại hình thuê bao: " + ex);
      }
    },
    async GO_LOAIHD_MD() {
      try {
        var chkAll = document.getElementById("chb_chonnhanh");
        if (chkAll.checked) {
            this.checkedMucDichDG = this.options.allMucDichDaGan;
            if (this.$refs.mucDichDaGan.currentFilter && this.$refs.mucDichDaGan.currentFilter.value &&
                this.$refs.mucDichDaGan.currentFilter.value != "") {
                  console.log("Filter: " + this.$refs.mucDichDaGan.currentFilter.value)
                  this.checkedMucDichDG = this.checkedMucDichDG.filter(
                    item => item.ten_md.toUpperCase().includes(this.$refs.mucDichDaGan.currentFilter.value.toUpperCase())
                  )
                } 
        }

        if (this.checkedMucDichDG.length == 0) {
          this.$toast.error("Hãy chọn ít nhất 1 mục đích trước khi gỡ");
          return;
        }

        console.log(this.checkedMucDichDG);
        let lst = [""];       
        for (let i = 0, j = 0; i < this.checkedMucDichDG.length; i++) {
          if (this.checkedMucDichDG[i].chon == 's1') {
            continue;
          }

          let val = this.checkedMucDichDG[i].mucdich_id;
          if ((lst[j] + "," + val).length > 4000) {
            j++;
          }

          if (lst.length == j) {
            lst.push("");
          }

          lst[j] += "," + val;
        }   

        console.log(lst[0]);
        let result = await this.fn_delete_loaihd_md(lst[0]);
        if (result.error_code == "BSS-00000000") {
          this.$toast.success("Gỡ thành công");
        } else {
          this.$toast.error("Gỡ thất bại, lỗi: " + result.message);
        }

        await this.btnLamMoi_Click();

      } catch (ex) {
        this.$toast.error("Lỗi khi gỡ gán loại HĐ mục đích: " + ex);
      }
    },
    async fn_delete_loaihd_md(vds) {
      let result;
      this.loading(true);
      try {
        await api.fn_delete_loaihd_md(this.axios, {  
            vmucdich_id: "",
            vds: vds,
            vloaitb_id: this.LOAIHD_ID
          }).then((res) => {  
            result = res.data;
          });
      } catch (err) {
        result = err.data;
        console.log(err);
      }
      this.loading(false);
      return result;
    },
    async rowSelected(data) {
      try {
        // if (data) {
        //   let row = data.data;
        //   if (row.KIEUTBI_ID != undefined && row.KIEUTBI_ID != null) {
        //     this.loading(true);
        //     await this.fn_update_loaihd_md(row.MUCDICH_ID, row.KIEULD_ID, row.KIEUTBI_ID);
        //     await this.btnLamMoi_Click();
        //   }
        // }
        if (data.data.length == undefined) {
            this.checkedMucDichDG.push(data.data)
        } else {
            let check = document.getElementById("chb_chonnhanh")
            if (check.checked) {
                this.checkedMucDichDG = this.options.dsMucDichDaGan.slice()
            } else {
                for (let i=0; i<data.data.length; i++) {
                    let isExists = false
                    for (let ix=0; ix<this.checkedMucDichDG.length; ix++) {
                        if (data.data[i].mucdich_id == this.checkedMucDichDG[ix].mucdich_id) {
                            isExists = true;
                            break
                        }
                    }
                    if (!isExists) {
                        this.checkedMucDichDG.push(data.data[i])
                    }       
                }      
            }
        }
      }  catch (err) {
        console.log(err);
      }
      this.loading(false);
    },
    rowDeselected(data) {
        if (this.isPageChange) return
        if (data.data.length == undefined) {
            $("#chb_chonnhanh").prop('checked', false); 
            let iRemove
            for (let i=0; i<this.checkedMucDichDG.length; i++) {
              if (this.checkedMucDichDG[i].mucdich_id == data.data.mucdich_id) {              
                iRemove = i;
                break;
              }
            }
            this.checkedMucDichDG.splice(iRemove, 1)
        } else {
            if (data.data.length != this.checkedMucDichDG.length) {
                $("#chb_chonnhanh").prop('checked', false);
                this.onCheckbox()
                return
            }
            
            let check = document.getElementById("chb_chonnhanh")
            if (check.checked) {
                this.checkedMucDichDG = this.options.dsMucDichDaGan.slice()
            } else {      
                for (let ix=0; ix<data.data.length; ix++) {
                    let iRemove = -1
                    for (let i=0; i<this.checkedMucDichDG.length; i++) {
                        if (this.checkedMucDichDG[i].mucdich_id == data.data[ix].mucdich_id) {              
                            iRemove = i;
                            break;
                        }
                    }
                    this.checkedMucDichDG.splice(iRemove, 1)
                }
            }         
        }     
    },
    async fn_update_loaihd_md(vmucdich_id, vkieuld_id, vkieutbi_id) {
      this.loading(true);
      try {
         await api.fn_update_loaihd_md(this.axios, {
            vloaihd_id: this.LOAIHD_ID,
            vmucdich_id: vmucdich_id,
            vkieuld_id: vkieuld_id,
            vkieutbi_id: vkieutbi_id
          }).then((res) => {              
            let result = res.data.data;
          });
      }  catch (err) {
        console.log(err);
      }
      this.loading(false);
    },
    preventSubmit() {
      return false;
    },
    async onLoaiHDChange(agrs) {
      await this.btnLamMoi_Click();
    },
    pageChanged() {
        this.isPageChange = true;
        let check = document.getElementById("chb_chonnhanh")
        if (check.checked) {
            this.checkedMucDichDG = this.options.dsMucDichDaGan.slice()
        }
        this.options.dsMucDichDaGan = [...this.options.dsMucDichDaGan]
    },
    actionComplete() {
        let lstSelect = []
        let pIndex = this.$refs.mucDichDaGan.$refs.pagination.pageIndexData
        let pSize = this.$refs.mucDichDaGan.$refs.pagination.pageSizeData
        for (let i=0; i<this.options.dsMucDichDaGan.length; i++) {
            for (let iS=0; iS<this.checkedMucDichDG.length; iS++) {
                if (this.options.dsMucDichDaGan[i].mucdich_id == this.checkedMucDichDG[iS].mucdich_id) {
                    if (i>=pIndex*pSize && i<(pIndex+1)*pSize) {
                        lstSelect.push(i - pIndex*pSize)             
                    }
                    break;
                } 
            }
        }
        this.$refs.mucDichDaGan.$refs.grid.selectRows(lstSelect)
        this.isPageChange = false;
    },
    onCheckbox() {
        this.isPageChange = true
        let check = document.getElementById("chb_chonnhanh")
        if (check.checked) {
              this.checkedMucDichDG = this.options.dsMucDichDaGan.slice()
        } else {
            this.checkedMucDichDG = [];
        }
        this.options.dsMucDichDaGan = [...this.options.dsMucDichDaGan]
    },
    filterDSMD() {
      if (this.VtuWord == this.filterTenVT.trim()) {
        this.VtuIndex++;
        if (this.VtuIndex >= this.VtuCache.length) {
          this.VtuIndex = 0;
        }
        this.selectedNodes = [this.VtuCache[this.VtuIndex]];
        this.expandedNodes=[-1]
        let ps = this.getParents(this.VtuCache[this.VtuIndex]);
          if (!(ps == null || ps.length == 0)) {
            for (let i = 0; i < ps.length; i++) {
              this.expandedNodes.push(ps[i].mucdich_id);
            }
          }
      } else {
        this.VtuIndex = 0;
        this.VtuWord = this.filterTenVT.trim();
        this.VtuCache = this.vatTuTree
          .filter((a) =>
            a.ten_md
              .toString()
              .toLowerCase()
              .includes(this.filterTenVT.trim().toLowerCase())
          )
          .map((b) => b.mucdich_id);
        this.VtuChaCache = this.vatTuTree
          .filter((a) =>
            a.ten_md
              .toString()
              .toLowerCase()
              .includes(this.filterTenVT.trim().toLowerCase())
          )
          .map((b) => b.mucdich_cha_id);
        if (this.VtuCache.length) {
          this.selectedNodes = [this.VtuCache[0]];
          let ps = this.getParents(this.VtuChaCache[0]);
          if (!(ps == null || ps.length == 0)) {
            for (let i = 0; i < ps.length; i++) {
              this.expandedNodes.push(ps[i].mucdich_id);
            }
          }
        } else {
          this.selectedNodes = ["0"];
          this.expandedNodes=[-1]
        }
      }
    },
    getParents: function (nodeParentID) {
      let rs = [];
      let parent = this.vatTuTree.find((x) => x.mucdich_id == nodeParentID);
      if (parent != null) {
        rs.push(parent);
        if (parent.mucdich_cha_id != null) {
          let ps = this.getParents(parent.mucdich_cha_id);
          if (!(ps == null || ps.length == 0)) {
            for (let i = 0; i < ps.length; i++) {
              rs.push(ps[i]);
            }
          }
        }
      }
      return rs;
    },
  }
}
</script>
<style>
  
</style>

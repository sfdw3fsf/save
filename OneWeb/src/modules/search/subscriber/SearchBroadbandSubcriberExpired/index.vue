<template>
  <div>
    <vue-bread-crumb :header="header" />
    <vue-nav>
      <ul class="list">
        <li
          @click="clickButton('hienthi')"
          :class="this.button.hienthi ? 'active' : 'non-active'"
        >
          <a style="cursor: pointer !important;"> <span class="icon one-reload1"></span>Hiển thị </a>
        </li>
        <li @click="clickButton('xuatfile')">
          <a style="cursor: pointer !important;"> <span class="icon one-search"></span>Xuất file</a>
        </li>
        <li
          @click="clickButton('trogiup')"
          :class="this.button.xemct ? 'active' : 'non-active'"
        >
          <a style="cursor: pointer !important;"> <span class="icon one-download"></span>Trợ giúp </a>
        </li>

      </ul>
    </vue-nav>
    <div class="page-content">
      <div class="row">
                <div class="col-sm-3 col-12">
                    <div class="box-form" style="height:100%">
                      <div class="box-form">
                        <div class="legend-title">Thông tin tra cứu</div>
                        <div class="info-row">
                            <div class="key">Số tháng còn lại</div>
                            <div class="value">
                                <div class="select-custom">
                                    <SelectExt
                                      v-model="ddlSoThangConLai"
                                      :options="this.options.SoThangConLai"
                                    >
                                      <option disabled value="0">Select one</option>
                                    </SelectExt>
                                </div>
                            </div>
                            <div class="value w20 nowrap padt7">
                                (tháng)
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">Loại hình</div>
                            <div class="value">
                                <div class="select-custom">
                                    <ejs-multiselect 
                                    id='multiselect' 
                                    v-model="ddlLoaiHinh"
                                    :dataSource='this.options.DataLoaihinh'  
                                    mode="CheckBox" 
                                    :fields='this.fields_loaihinh' 
                                    :showSelectAll='showSelectAll' 
                                    :isChecked='isChecked'
                                    selectAllText="Chọn tất cả" 
                                    unSelectAllText="Bỏ chọn tất cả"
                                    ></ejs-multiselect>
                                </div>
                            </div>
                            
                        </div>
                        <div class="info-row">
                            <div class="key">Hình thức</div>
                            <div class="value">
                                <div class="select-custom">
                                    <SelectExt
                                      v-model="ddlHinhThuc"
                                      :options="this.options.HinhThuc"
                                    >
                                      <option disabled value="0">Select one</option>
                                    </SelectExt>
                                </div>
                            </div>
                            
                        </div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">Địa bàn</div>
                        <div class="input-icon-right">
                            <input type="text" class="form-control" v-on:keyup="keymonitor" v-model="txtTimKiemKhuVuc">
                            <span class="icon nc-icon-outline ui-2_filter"></span>
                        </div>
                        <div class="tree-plus tree-icon" style="height:100%,overflow: auto">
                          <ejs-treeview
                            id="treeviewGan"
                            ref="treeObjGan"
                            :fields="this.fields"
                            :showCheckBox="true"
                            :allowMultiSelection="true"
                            :search="search_khuvuc"
                            :nodeChecked="nodeChecked"
                          />
                        </div>
                    </div>
                    </div>
                </div>


        <div class="col-sm-9 col-12">
          <div class="box-form" style="height: 100%">
            <div class="legend-title">Danh sách thuê bao</div>

        <div class="table-content">
          <DataGrid
            :columns="this.options.cols_grid"
            v-bind:dataSource="this.options.danhsach_grid"
            :showColumnCheckbox="false"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
            :enabledSelectFirstRow="true"
          >
          </DataGrid>
        </div>


          </div>
        </div>
      </div>
    </div>

        <!-- Modal -->
        <ExportDataModal ref="exportDataModal" :data="dataSources"/>

  </div>
</template>
<script>
import Vue from 'vue';
import api from "./api";
import define from "./define";
import { MultiSelectPlugin } from "@syncfusion/ej2-vue-dropdowns";
import { MultiSelect, CheckBoxSelection } from '@syncfusion/ej2-dropdowns';
MultiSelect.Inject(CheckBoxSelection);
Vue.use(MultiSelectPlugin);
import {DataManager, ODataAdaptor, Predicate, Query} from '@syncfusion/ej2-data';

import ExportDataModal from './ExportDataModal.vue'

export default {
      components:{
        ExportDataModal
    },
  data() {
    return {
      ...define,
      khuvuc_checked:[],
      khuvuc_ht:'',
      dataSources:[],
      danhsachkv_tree:[],
      txtTimKiemKhuVuc:"",
      search_khuvuc:"",
      ddlSoThangConLai:1,
      ddlHinhThuc:1,
      ddlLoaiHinh:[11,58,61,210,274],
      options: {
        danhsach_grid:[],
        SoThangConLai:[],
        HinhThuc:[],
        DataLoaihinh: [
              { Id: 11, Name: 'Mega' },
              { Id: 58, Name: 'Fiber' },
              { Id: 61, Name: 'MyTV' },
              { Id: 210, Name: 'Wifi Mesh'},
              { Id: 274, Name: 'Wifi Mesh TM'}
            ],
        cols_grid: [
          {
            fieldName: "TEN_KV",
            headerText: "Địa bàn",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "TEN_NV_KD",
            headerText: "Nhân viên KD",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "MA_TB",
            headerText: "Số máy/Acc",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "TEN_TB",
            headerText: "Tên thuê bao",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },  
          {
            fieldName: "NGAY_BDDC",
            headerText: "Ngày BĐĐC",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "NGAY_KTDC",
            headerText: "Ngày KTĐC",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "SO_DT",
            headerText: "SĐT liên hệ",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "DIACHI_LD",
            headerText: "Địa chỉ lắp đặt",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },    
          {
            fieldName: "LOAIHINH_TB",
            headerText: "Loại hình",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "TOCDO",
            headerText: "Tốc độ",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          }

        ]
      },
      fields_loaihinh : { text: 'Name', value: 'Id' },
      showSelectAll : true,
      isChecked:true,
      fields: {
        dataSource: null,
        id: "KHUVUC_ID",
        parentID: "KHUVUC_CHA_ID",
        text: "TEN_KV",
        hasChildren: "hasChild",
      },
    };
  },


  created() {
    this.InsertMonth();
    this.Inserthinhthuc();
  },
  watch: {
    ddlLoaiHinh(newvalue)
    {
      this.ddlLoaiHinh = newvalue;
    },
    ddlHinhThuc(newvalue)
    {
      this.ddlHinhThuc = newvalue;
    },
    ddlSoThangConLai(newvalue)
    {
      this.ddlSoThangConLai = newvalue;
    },
    khuvuc_ht(newvalue)
    {
      this.khuvuc_ht = newvalue;
    },

  },


  computed:{
    
  },

  mounted(){
    this.displayTreeView();
  },


  methods: {

        showModal(){
            if(this.dataSources.length==0){
                this.$toast.error('Không có dữ liệu Test')
                return
            }
            this.$refs.exportDataModal.showModal()
        },



    keymonitor: function(event) {
        console.log(event.key);
       if(event.key == "Enter")
       {
         this.searchKhuVuc();
       }
    },

    searchKhuVuc() {
      let predicate
      let predicats = []
      let _array = []
      let _filter = []
      var child='KHUVUC_ID';

      this.loading(true);
      if (this.txtTimKiemKhuVuc.trim() !== '') {
        predicate = new Predicate('TEN_KV', 'contains', this.txtTimKiemKhuVuc.trim(), true)
        let filteredList = new DataManager(this.danhsachkv_tree).executeLocal(new Query().where(predicate))
        for (let j = 0; j < filteredList.length; j++) {
          _filter.push(filteredList[j][child])
          let filters = this.getFilterItems(filteredList[j], this.danhsachkv_tree)
          for (let i = 0; i < filters.length; i++) {
            if (_array.indexOf(filters[i]) === -1 && filters[i] != null) {
              _array.push(filters[i])
              predicats.push(new Predicate(child, 'equal', filters[i], false))
            }
          }
        }
        if (predicats.length === 0) {
          this.changeDataSource([])
          this.loading(false);
        } else {
          let query = new Query().where(Predicate.or(predicats))
          let newList = new DataManager(this.danhsachkv_tree).executeLocal(query)
          this.changeDataSource(newList)
          setTimeout(() => {
            this.$refs.treeObjGan.expandAll()
          }, 100)
          this.loading(false);
        }
      } else {
        this.changeDataSource(this.danhsachkv_tree)
        this.loading(false);
      }
    },

    // Find the Parent Nodes for corresponding childs
    getFilterItems (fList, list) {
      let nodes = []
      var parent='KHUVUC_CHA_ID';
      var child='KHUVUC_ID';

      nodes.push(fList[child])
      let query2 = new Query().where(child, 'equal', fList[parent], false)
      let fList1 = new DataManager(list).executeLocal(query2)
      if (fList1.length !== 0) {
        let pNode = this.getFilterItems(fList1[0], list)
        for (let i = 0; i < pNode.length; i++) {
          if (nodes.indexOf(pNode[i]) === -1 && pNode[i] != null) {
            nodes.push(pNode[i])
          }
        }
        return nodes
      }
      return nodes
    },


    //import số tháng
    InsertMonth()
    {
      let items = [];
      for (let i = -1; i < 48; i++) {
        items.push({ id: i, text: i });
      }
      this.options.SoThangConLai = items;
      this.ddlSoThangConLai = items[0].id;
    },
    //import hình thức
    Inserthinhthuc()
    {
        let items = [];
        items.push({ id: 0, text: 'Khuyến mại' });
        items.push({ id: 1, text: 'Đặt cọc' });
        this.options.HinhThuc = items;
        this.ddlHinhThuc = items[1].id;
    },

    // Change the dataSource for TreeView
    changeDataSource (data) {
      var parent='KHUVUC_CHA_ID';
      var child='KHUVUC_ID';
      this.fields = {
        dataSource: data, id: child, text: 'TEN_KV', parentID: parent, hasChildren: 'hasChild'
      }
    },



    async getDanhSachKV () {
      let ds = [];
      await api.getDanhSach(this.axios, {
          vLoaikv_Id: 4,
          vLoainv_Id: 52
        })
        .then((res) => {
          ds = res.data.data ? res.data.data : []; 
        }); 
      return ds;
    },

    async displayTreeView () {
      try {
        this.loading(true)
        let dsKhuVuc = await this.getDanhSachKV()
        if(dsKhuVuc.length > 0) {
          this.danhsachkv_tree = this.createTree(dsKhuVuc);
          this.changeDataSource(this.danhsachkv_tree);
        }
      } catch(error){
        this.$toast.error('Không load được danh sách khu vực!!!')
      } finally {
        this.loading(false)
      }
    
    },
    createTree (ds) {
      let tree = []
      let newDs = [] 
      var parent='KHUVUC_CHA_ID';
      var child='KHUVUC_ID';

      ds.forEach(item => {
        if(item.KHO_CHA_ID === null) {
          item.KHO_ID = 0
        }
        newDs.push(item)
      })
      let khuvucChaArray = newDs.map((item) => {
        return item[parent]
      })
      let khuvucArray = newDs.map(item => item[child])
      // check item la parent node
      newDs.forEach(item => {
        if (khuvucChaArray.includes(item[child])) {
          // check KHO_CHA_ID khong co trong ds KHO_ID thi xoa KHO_CHA_ID
          if (!khuvucArray.includes(item[parent])) {
            delete item.KHO_CHA_ID
          }
        
          tree.push({...item, hasChild: true, expanded: false})
        } else {
          tree.push(item)
        }
      }
        
      ) 
      
      return tree
    },



    getDanhSach_ThueBao() {
        this.loading(true);
        if(this.ddlLoaiHinh.length < 1)
        {
          this.$toast.error("Chưa chọn loại hình thuê bao!");
        }
        else{
          let lhtb='';
          for (let i = 0; i < this.ddlLoaiHinh.length ; i++) {
            lhtb += this.ddlLoaiHinh[i]+ ',';
          }
          lhtb = lhtb.substring(0,lhtb.length-1);


          console.log("vdskhuvuc_id",this.khuvuc_ht,"vthangconlai",this.ddlSoThangConLai,"vdsloaitb_id",lhtb,"vkieu",this.ddlHinhThuc);

          api.getDanhSach_ThueBao(this.axios, {
              "vnhanvien_id": 1,
              "vdskhuvuc_id": this.khuvuc_ht,
              "vthangconlai": this.ddlSoThangConLai,
              "vdsloaitb_id": lhtb,
              "vkieu": this.ddlHinhThuc
            })
            .then((res) => {
              this.options.danhsach_grid = res.data.data ? res.data.data: [];
              this.dataSources=[];
              this.dataSources=this.options.danhsach_grid;
              console.log(this.options.danhsach_grid);
              
              if (this.options.danhsach_grid.length < 1) {
                let errorMessage = res.data.message_detail ? `Không có dữ liệu hiển thị\n${res.data.message_detail}` : "Không có dữ liệu hiển thị!";
                this.$toast.error(errorMessage);
                // this.$toast.error("Không có dữ liệu hiển thị!");
              }
              this.loading(false);
            });
        }

    },

    nodeChecked(args) {
      var treeObj  = document.getElementById('treeviewGan').ej2_instances[0];
      this.khuvuc_checked =  treeObj.checkedNodes;
      let items = [];
        this.khuvuc_checked.forEach(function (item) {
          items.push({ ID: item});
        });
      this.khuvuc_checked = items;

      this.khuvuc_ht='';
      for (let i = 0; i < this.khuvuc_checked.length ; i++) {
        this.khuvuc_ht += this.khuvuc_checked[i].ID+ ',';
      }
      this.khuvuc_ht = this.khuvuc_ht.substring(0,this.khuvuc_ht.length-1);
    },



    clickButton(key) {
      if (key == "hienthi") {
        this.getDanhSach_ThueBao();
      }
      if (key == "xuatfile") {
        this.showModal();
      }
    },
  },
};
</script>
<style>
li.non-active a {
  color: #d3d3d3 !important;
}

@import "https://cdn.syncfusion.com/ej2/material.css";
</style>

<template>
  <div>
    <vue-bread-crumb :header="header" />
    <vue-nav>
      <ul class="list">
        <li
          @click="clickButton('lamtuoi')"
          :class="this.button.lamtuoi ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-reload1"></span>Làm tươi </a>
        </li>
        <li @click="clickButton('timkiem')">
          <a> <span class="icon one-search"></span>Tìm kiếm</a>
        </li>
        <li
          @click="clickButton('xemct')"
          :class="this.button.xemct ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-download"></span>Xem CT </a>
        </li>

        <li
          @click="clickButton('thulai')"
          :class="this.button.thulai ? 'active' : 'non-active'"
        >
          <a>
            <span class="icon nc-icon-glyph arrows-1_simple-left"></span>Thu lại
          </a>
        </li>
        <li
          @click="clickButton('nhapmoi')"
          :class="this.button.nhapmoi ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-file-plus"></span>Nhập mới</a>
        </li>
        <li
          @click="clickButton('ghilai')"
          :class="this.button.ghilai ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-save"></span>Ghi lại </a>
        </li>

        <li
          @click="clickButton('huongdan')"
          :class="this.button.huongdan ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-book1"></span>Hướng dẫn </a>
        </li>
      </ul>
    </vue-nav>
    <div class="page-content">
      <div class="row">
        <Split style="height: auto;">
          <SplitArea :size="25">
            <div class="box-form" style="height: 100%;">
              <div id="tab_timkiem" class="tab-pane" style="display: none">
                <div class="box-form">
                  <div class="legend-title">Lọc thông tin đối tượng sử dụng</div>
                  <div class="info-row">
                    <div class="key w80">Mã quản lý</div>
                    <div class="value">
                      <div class="input-more-button -right">
                        <button class="btn">
                          <span class="-ap icon-more_horiz"></span>
                        </button>
                        <input v-model="txtMaQuanLy" class="form-control" v-on:keyup.enter="searchDonVi">
                      </div>
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w80">Tên quản lý</div>
                    <div class="value">
                      <div class="input-more-button -right">
                        <button class="btn">
                          <span class="-ap icon-more_horiz"></span>
                        </button>
                        <input v-model="txtTenQuanLy" class="form-control" v-on:keyup.enter="searchDonVi">
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="box-form">
                <div class="legend-title">
                  <div class="pull-left">Danh sách đối tượng</div>
                  <div class="pull-right">
                    <div class="check-action normal red">
                      <input type="checkbox" v-model="chkChonNhanh" :disabled="disabled_chonnhanh"/>
                      <span class="name">Chọn nhanh</span>
                    </div>
                  </div>
                  <div class="clearfix"></div>
                </div>
                <div class="tree-plus tree-icon" style="overflow: auto;">
                  <ejs-treeview
                    id="treeviewGan"
                    ref="treeObjGan"
                    :fields="this.fields"
                    :showCheckBox="true"
                    :allowMultiSelection="true"
                    :search="search_kho_ma"
                    :nodeChecked="nodeChecked"
                    :autoCheck = chkChonNhanh
                    :selectedNodes="selectedNodes"
                    :nodeSelected="nodeSelected"
                  />
                </div>
              </div>
            </div>
          </SplitArea>
          <SplitArea :size="75">
            <div class="box-form" style="height: 100%">
              <div class="legend-title">Hạn số liệu cho cán bộ</div>
              <div class="row">
                <div class="col-sm-4 col-12">
                  <div class="info-row">
                    <div class="key w70">Ngày hạn</div>
                    <div class="value">
                      <div class="select-custom">
                        <vue-date
                          format="DD/MM/YYYY"
                          type="datetime"
                          v-model="txtNgayHan"
                        ></vue-date>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="nav tabs tab-over mart10">
                <a
                  href="#tab1"
                  data-toggle="tab"
                  @click="changetab_hansolieu"
                  class="active"
                  >Danh sách hạn số liệu của cán bộ</a>
                <a href="#tab2" data-toggle="tab" @click="changetab_lichsu"
                  >Lịch sử hạn số liệu của cán bộ</a
                >
              </div>
              <div class="tab-content marb0">
                <div id="tab1" class="tab-pane active">
                  <div class="table-content">
                    <ejs-grid
                      ref="grid_danhsachhan"
                      width="100%"
                      :dataSource="this.danhsach_han_grid"
                      locale="vi-VN"
                      :allowSorting="true"
                      :allowFiltering="true"
                      :allowPaging="true"
                      :pageSettings="{
                        pageSize: 10,
                        pageSizes: [10, 50, 100, 200],
                      }"
                      :gridLines="1"
                      :editSettings="{ allowEditing: false, mode: 'Dialog' }"
                      :commandClick="commandClick"
                    >
                      <e-columns>
                        <e-column
                          textAlign="left"
                          field="ngay_han"
                          headerText="Ngày hạn"
                          allowSorting="true"
                          allowFiltering="true"
                        ></e-column>
                        <e-column
                          textAlign="left"
                          field="nguoi_chan"
                          headerText="Người chặn"
                          allowSorting="true"
                          allowFiltering="true"
                        ></e-column>
                        <e-column
                          textAlign="center"
                          field="Commands"
                          headerText="Xóa"
                          width="120"
                          :commands="commands"
                        ></e-column>
                      </e-columns>
                    </ejs-grid>
                  </div>
                </div>
                <div id="tab2" class="tab-pane">
                  <div class="table-content">
                    <ejs-grid
                      ref="grid_lichsu"
                      width="100%"
                      :dataSource="this.lichsu_han_grid"
                      locale="vi-VN"
                      :allowSorting="true"
                      :allowFiltering="true"
                      :allowPaging="true"
                      :pageSettings="{
                        pageSize: 10,
                        pageSizes: [10, 50, 100, 200],
                      }"
                      :gridLines="1"
                      :editSettings="{ allowEditing: false, mode: 'Dialog' }"
                    >
                      <e-columns>
                        <e-column
                          v-for="col in this.cols_lichsugrid"
                          :width="col.width ? col.width : 'auto'"
                          :key="col.fieldName"
                          :field="col.fieldName"
                          :type="col.type"
                          :allowEditing="col.allowEditing"
                          :allowSorting="col.allowSorting"
                          :allowFiltering="col.allowFiltering"
                          :clipMode="'EllipsisWithTooltip'"
                          :customAttributes="col.customAttributes"
                          :headerText="col.headerText"
                          :textAlign="col.textAlign"
                          headerTextAlign="center"
                          :editType="col.editType"
                          :format="col.format"
                          :isPrimaryKey="col.isPrimaryKey"
                          :visible="col.visible"
                          :displayAsCheckBox="col.displayAsCheckBox"
                        ></e-column>
                      </e-columns>
                    </ejs-grid>
                  </div>
                </div>
              </div>
            </div>
          </SplitArea>
        </Split>
      </div>
    </div>
  </div>
</template>
<script>
import VueInput from "../../../search/subscriber/components/form/VueInput.vue";
import api from "./api";
import define from "./define";
import moment from "moment";
import VueCheckbox from '../../../search/subscriber/components/form/VueCheckbox.vue';
import {DataManager, ODataAdaptor, Predicate, Query} from '@syncfusion/ej2-data';
import {
  Sort,
  Filter,
  Resize,
  Group,
  Freeze,
  CommandColumn,
  ExcelExport,
  Edit,
  Aggregate,
  Page,
  Toolbar,
} from "@syncfusion/ej2-vue-grids";
export default {
  components: { VueInput, VueCheckbox },
  provide: {
    grid: [
      Sort,
      Filter,
      Resize,
      Group,
      Freeze,
      CommandColumn,
      ExcelExport,
      Edit,
      Aggregate,
      Toolbar,
      Page,
      CommandColumn,
    ],
  },

  data() {
    return {
      ...define,
      commands: [
        {
          buttonOption: { content: "Xóa", cssClass: "custombutton e-info" },
        },
      ],
      waterMarkText: "Select a date and time",
      dateFormat: "dd-MMM-yy hh:mm:ss",
      txtNgayHan:moment(new Date()).format("DD/MM/YYYY"),


      ngaycanxoa:"",
      chkChonNhanh : true,
      disabled_chonnhanh:false,
      selectedNodes:[],
      tab_selected:1,
      mt:0,
      kho_selected:0,
      kho_checked:[],
      txtMaQuanLy:"",
      txtTenQuanLy:"",
      search_kho_ma:"",
      danhsach_han_grid:[],
      lichsu_han_grid:[],

      danhsachkho_tree:[],

      cols_lichsugrid: [
          {
            fieldName: "ngay_han",
            headerText: "Ngày hạn",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "nguoi_chan",
            headerText: "Người chặn",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          }
        ], 
      cols_hangrid: [
          {
            fieldName: "ngay_han",
            headerText: "Ngày hạn",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "nguoi_chan",
            headerText: "Người chặn",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "Commands",
            headerText: "",
            width:"150",
            textAlign: "center",
          }
        ],      
      fields: {
        dataSource: null,
        id: "kho_id",
        parentID: "kho_cha_id",
        text: "ten_ht",
        hasChildren: "hasChild",
      },
    };
  },


  created() {
    
  },
  watch: {
    chkChonNhanh(newdata) {
      this.EventChonNhanh();
    },
    txtMaQuanLy(newdata) {
      this.search_kho_ma = newdata;
    },
    txtTenQuanLy(newdata) {
      this.search_kho_ma = newdata;
    },    

    tab_selected(newdata) {
      this.tab_selected=newdata
      if(this.tab_selected==1)
      {
        this.disabled_chonnhanh=false
      }
      else
      {
        this.disabled_chonnhanh=true
      }
    },
  },
  props: {
    value: Object,
  },

  computed:{
    
  },

  mounted(){
    this.displayTreeView();
  },


  methods: {
    
    EventChonNhanh()
    {
      // if(this.chkChonNhanh == true)
      // {
      //   this.$refs.treeObjGan.checkAll();
      // }
      // else
      // {
      //   this.$refs.treeObjGan.uncheckAll();
      // }
    },



   changetab_hansolieu()
    {
      this.tab_selected=1;
      this.getDanhSach_HanChan();
    },
   changetab_lichsu()
    {
      this.tab_selected=2;
      this.getLichSu_HanChan();
    },

   async getDanhSach_HanChan() {
      var matinh = this.$root.token.getMaTinh(); 
      var ma_tinh = 2; 
      if(matinh != 'HNI')
      {
        ma_tinh = 1;
      }
      this.loading(true);
      console.log('JSON.stringify(this.kho_checked),',JSON.stringify(this.kho_checked),this.kho_checked.length)
      await api.getDanhSach(this.axios, {
          "vlength": this.kho_checked.length,
          "vds": JSON.stringify(this.kho_checked),
          "vkieu": ma_tinh
        })
        .then((res) => {
           this.danhsach_han_grid = res.data.data ? res.data.data : [];
           this.loading(false);
        });
    },

  async getLichSu_HanChan() {
      var matinh = this.$root.token.getMaTinh(); 
      this.mt = 4;
      if(matinh != 'HNI')
      {
        this.mt = 5;
      }
      await api.getDanhSach(this.axios, {
          vlength: this.selectedNodes[0],
          vds:"",
          vkieu: this.mt
        })
        .then((res) => {
           this.lichsu_han_grid = res.data.data ? res.data.data : [];
           console.log('this.lichsu_han_grid',this.lichsu_han_grid)
        });
    },




    async getDanhSachKho () {
      let ds = [];
      var matinh = this.$root.token.getMaTinh(); 
      this.mt = 0;
      if(matinh != 'HNI')
      {
        this.mt = 3;
      }
      await api.getDanhSach(this.axios, {
          vlength: 0,
          vds:'',
          vkieu: this.mt
        })
        .then((res) => {
          ds = res.data.data ? res.data.data : []; 
        }); 
      return ds;
    },

    async displayTreeView () {
      try {
        this.loading(true)
        let dsKho = await this.getDanhSachKho()
        if(dsKho.length > 0) {
          this.danhsachkho_tree = this.createTree(dsKho);
          this.changeDataSource(this.danhsachkho_tree);
        }
      } catch(error){
        this.$toast.error('Không load được danh sách kho/đơn vị!!!')
      } finally {
        this.loading(false)
      }
    
    },
    createTree (ds) {
      let tree = []
      let newDs = [] 
      var matinh = this.$root.token.getMaTinh(); 
      var parent='donvi_cha_id';
      var child='donvi_id';
      if(matinh != 'HNI')
      {
        parent='kho_cha_id';
        child='kho_id';
      }
      ds.forEach(item => {
        // if(item.kho_cha_id === null) {
        //   item.kho_cha_id = 0
        // }
        newDs.push(item)
      })
      let khoChaArray = newDs.map((item) => {
        return item[parent]
      })

      let khoArray = newDs.map(item => item[child])
      // check item la parent node
      newDs.forEach(item => {
        if (khoChaArray.includes(item[child])) {
          // check kho_cha_id khong co trong ds kho_id thi xoa kho_cha_id
          // if (!khoArray.includes(item[parent])) {
          //   delete item.kho_cha_id
          // }
          if(item.kho_id == 0)
          {
            delete item.kho_cha_id  
          }
        
          tree.push({...item, hasChild: true, expanded: false})
          
        } else {
          tree.push(item)
        }
      } 
      ) 
      

      return tree
    },


    nodeSelected(args)
    {
      var treeObj  = document.getElementById('treeviewGan').ej2_instances[0];
      this.selectedNodes = treeObj.selectedNodes
        if(this.tab_selected==2)
        {
          this.getLichSu_HanChan();
        }
    },
    nodeChecked(args) {
        var treeObj  = document.getElementById('treeviewGan').ej2_instances[0];
        var dodai = treeObj.checkedNodes.length;
        this.kho_checked =  treeObj.checkedNodes;
        let items = [];
          this.kho_checked.forEach(function (item) {
            items.push({ ID: item});
          });
        this.kho_checked = items;

        if(this.tab_selected==1)
        {
          this.getDanhSach_HanChan();
        }

    },




    commandClick (args) {
      if (args.target.classList.contains("custombutton")) {
          this.ngaycanxoa = moment(args.rowData.ngay_han,"DD/MM/YYYY").format("DD/MM/YYYY");
          this.Xoa();
      }
    },



    searchDonVi () {
      let predicate
      let predicats = []
      let _array = []
      let _filter = []
      var matinh = this.$root.token.getMaTinh(); 
      var child='donvi_id';
      if(matinh != 'HNI')
      {
        child='kho_id';
      }

      this.loading(true);
      if (this.txtMaQuanLy.trim() !== '' || this.txtTenQuanLy.trim() !== '') {
        predicate = new Predicate('ten_ht', 'contains', this.search_kho_ma.trim(), true)
        let filteredList = new DataManager(this.danhsachkho_tree).executeLocal(new Query().where(predicate))
        for (let j = 0; j < filteredList.length; j++) {
          _filter.push(filteredList[j][child])
          let filters = this.getFilterItems(filteredList[j], this.danhsachkho_tree)
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
          let newList = new DataManager(this.danhsachkho_tree).executeLocal(query)
          this.changeDataSource(newList)
          setTimeout(() => {
            this.$refs.treeObjGan.expandAll()
          }, 100)
          this.loading(false);
        }
      } else {
        this.changeDataSource(this.danhsachkho_tree)
        this.loading(false);
      }
    },


    // Find the Parent Nodes for corresponding childs
    getFilterItems (fList, list) {
      let nodes = []
      var matinh = this.$root.token.getMaTinh(); 
      var parent='donvi_cha_id';
      var child='donvi_id';
      if(matinh != 'HNI')
      {
        parent='kho_cha_id';
        child='kho_id';
      }
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

    // Change the dataSource for TreeView
    changeDataSource (data) {
      console.log('data',data)
      var matinh = this.$root.token.getMaTinh(); 
      var parent='donvi_cha_id';
      var child='donvi_id';
      if(matinh != 'HNI')
      {
        parent='kho_cha_id';
        child='kho_id';
      }

      // for(var i = 0; i< data.length; i++)
      // {
      //   if(data[i].kho_cha_id == 0 && data[i].kho_id == 0)
      //   {
      //     data[i].kho_cha_id = ""
      //   }
      // }


      this.fields = {
        dataSource: data, id: child, text: 'ten_ht', parentID: parent, hasChildren: 'hasChild'
      }


      console.log('this.fields',this.fields)
    },

collapseAll()
{
  this.$refs.treeObjGan.collapseAll()
},
expandAll()
{
  this.$refs.treeObjGan.expandAll();
},


   Them() {
      if (!this.KiemTra_DuLieu()) return false;
      var matinh = this.$root.token.getMaTinh(); 
      var ma_tinh = 1;
      if(matinh != 'HNI')
      {
        ma_tinh = 0;
      }

      this.loading(true);

      api.CapNhat_Them_Xoa(this.axios, {
          vngaychot_cu: "",
          vds: this.kho_checked,
          vkieu: ma_tinh,
          vngaychot: moment(this.txtNgayHan,"DD/MM/YYYY").format("DD/MM/YYYY"),
          vthemmoi:1 //thêm mới
        })
        .then((res) => {
           var mes = res.data.data ? res.data.data : [];
           if(mes == '1')
           {
            this.getDanhSach_HanChan();
            this.loading(false);
            this.$toast.success('Cập nhật thành công!');
           }
           else
           {
            this.getDanhSach_HanChan();
            this.loading(false);
            this.$toast.error('Có lỗi xảy ra trong quá trình thêm mới ngày hạn!');
           }

        });
    },

   Xoa() {
      if (!this.KiemTra_DuLieu()) return false;
      var matinh = this.$root.token.getMaTinh(); 
      var ma_tinh = 1;
      if(matinh != 'HNI')
      {
        ma_tinh = 0;
      }
      

this.$confirm(`Bạn thật sự muốn xóa ngày hạn không ?`, {
        confirmButtonText: "Có",
        cancelButtonText: "Không",
        type: "warning",
        }).then(async () => {
        this.loading(true);

       api.CapNhat_Them_Xoa(this.axios, {
          "vngaychot_cu": "",
          "vds":this.kho_checked,
          "vkieu": ma_tinh,
          "vngaychot": this.ngaycanxoa,
          "vthemmoi":2 //xóa
        })
        .then((res) => {
           var mes = res.data.data ? res.data.data : [];
           if(mes == '1')
           {
            this.getDanhSach_HanChan();
            this.loading(false);
            this.$toast.success('Xóa ngày hạn thành công!');
           }
           else
           {
            this.getDanhSach_HanChan();
            this.loading(false);
            this.$toast.success('Có lỗi xảy ra trong quá trình xóa ngày hạn!');
           }
           
        });

        })
    },

    KiemTra_DuLieu(kieu) {
      if (this.kho_checked.length == 0) {
        this.$toast.error('Hãy chọn ít nhất 1 đối tượng trong danh sách để thực hiện nhập mới ngày hạn!');
        return false;
      }      
      if(kieu == 1)
      {
        if (!this.txtNgayHan) {
          this.$toast.error('Bạn chưa nhập ngày hạn!');
          return false;
        }
      }
      return true;
    },



    clickButton(key) {
      if (key == "lamtuoi") {
        this.displayTreeView();
      }

      if (key == "xemct") {
        this.expandAll();
      }

      if (key == "thulai") {
          this.collapseAll();
      }

      if (key == "nhapmoi") {
         this.txtNgayHan="";
      }
      if (key == "ghilai") {      
        this.Them();
      }
      if (key == "huongdan") {

      }
      if(key=="timkiem")
      {
        if(document.getElementById('tab_timkiem').style.display=="block")
        {
          document.getElementById('tab_timkiem').style.display="none";
        }
        else
        {
          document.getElementById('tab_timkiem').style.display="block";
        }
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
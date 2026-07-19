<template>
  <div>
    <vue-bread-crumb :header="header" />
    <vue-nav>
      <ul class="list">
        <li
          @click="clickButton('laytt')" style="cursor: pointer !important;"
          :class="this.button.laytt ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-file-attach1"></span>Lấy thông tin</a>
        </li>
        <li
          @click="clickButton('ghilai')" style="cursor: pointer !important;"
          :class="this.button.ghilai ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-save"></span>Ghi lại </a>
        </li>

      </ul>
    </vue-nav>

        <div class="page-content">
            <div class="box-form">
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w60">Lĩnh vực</div>
                            <div class="value">
                                <div class="select-custom">
                                  <SelectExt v-model="ddlLinhVuc" :options="options.linhvuc" > 
                                    <option disabled value="0">Select one</option> 
                                  </SelectExt>                                  
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w120">Dịch vụ viễn thông</div>
                            <div class="value">
                                <div class="select-custom">
                                  <SelectExt v-model="ddlDichVu" :options="this.options.dichvu" > 
                                    <option disabled value="0">Select one</option> 
                                  </SelectExt>  
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="legend-title mart20">Thông tin phí duy trì SMS Brandname</div>
                <div class="box-move-select-table">
                    <div class="box-col">
                        <div class="legend-title">
                            <div class="pull-left">
                                Danh sách dịch vụ gia tăng đã có phí duy trì
                            </div>
                            <!-- <div class="red pull-right normal">
                                <div class="check-action">
                                    <input type="checkbox" class="check">
                                    <span class="name">Chọn nhanh</span>
                                </div>
                            </div> -->
                            <div class="clearfix"></div>
                        </div>
                        <div class="table-content">

                <DataGridEdit
                  ref="danhsach_left"
                  v-bind:columns="this.left.cols"
                  v-bind:dataSource="this.left.danhsach"
                  :showFilter="true"
                  :showColumnCheckbox="true"
                  :enabledSelectFirstRow="false"
                  :editSettings="{ allowEditing: true,allowAdding: false,allowDeleting: false, mode: 'Normal' }"
                  :showColumnPointer="false"
                  :enable-paging-server="false"
                  :allowPaging="true"
                  @actionComplete='onActionComplete'
                >
                </DataGridEdit>

                        </div>

                    </div>


                    <div class="actions">
                      <button @click="toRight('danhsach')" class="btn">
                        <span class="fa fa-angle-right"></span>
                      </button>
                      <button @click="toAllRight('danhsach')" class="btn">
                        <span class="fa fa-angle-double-right"></span>
                      </button>
                      <button @click="toLeft('danhsach')" class="btn">
                        <span class="fa fa-angle-left"></span>
                      </button>
                      <button @click="toAllLeft('danhsach')" class="btn">
                        <span class="fa fa-angle-double-left"></span>
                      </button>
                        
                    </div>


                    <div class="box-col">
                        <div class="legend-title">
                            <div class="pull-left">
                                Danh sách dịch vụ gia tăng chưa có phí duy trì
                            </div>

                            <div class="clearfix"></div>
                        </div>
                        <div class="table-content">

                <DataGridEdit
                  ref="danhsach_right"
                  v-bind:dataSource="this.right.danhsach"
                  v-bind:columns="this.right.cols"
                  :showFilter="true"
                  :showColumnCheckbox="true"
                  :enabledSelectFirstRow="false"
                  :editSettings="{ allowEditing: true,allowAdding: false,allowDeleting: false, mode: 'Normal' }"
                  :showColumnPointer="false"
                  :enable-paging-server="false"
                  :allowPaging="true"

                >
                </DataGridEdit>

                        </div>
 
                    </div>
                </div>
            </div>
        </div>

  </div>
</template>
<script>
import Vue from "vue";
import api from "./api";
import define from "./define";
import breadcrumb from '@/components/breadcrumb'
import {
  GridPlugin,
  Aggregate,
  CommandColumn,
  Edit,
  Filter,
  Freeze,
  Group,
  Page,
  Resize,
  Sort,
  Toolbar
} from '@syncfusion/ej2-vue-grids'
import DataGridEdit from './components/DataGrid'
Vue.use(GridPlugin);



export default {
    provide: {
    grid: [
      Sort,
      Filter,
      Resize,
      Group,
      Freeze,
      CommandColumn,
      Edit,
      Aggregate,
      Toolbar,
      Page,
    ],
  },
  data() {
    return {
      ...define,
      ddlLinhVuc:"",
      ddlDichVu:"",
      json_update:[],
      json_delete:[],
      options:{
        linhvuc:[],
        dichvu:[]
      },
      
  left: {
    danhsach: [],
    cols: [
    {
      fieldName: "ten_dvgt",
      headerText: "Dịch vụ gia tăng",
      allowFiltering: true,
      textAlign: "left",
      allowSorting: true,
    },
    {
      fieldName: "tien",
      headerText: "Tiền",
      allowFiltering: true,
      textAlign: "left",
      allowSorting: true,
      type:"Number",
      format: "N"   

    }
    ]
  },
  right: {
    danhsach: [],
    cols: [
    {
      fieldName: "ten_dvgt",
      headerText: "Dịch vụ gia tăng",
      allowFiltering: true,
      textAlign: "left",
      allowSorting: true,
    },
    {
      fieldName: "tien",
      headerText: "Tiền",
      allowFiltering: true,
      textAlign: "left",
      allowSorting: true,
      type:"Number",
      format: "N"   

    }
  ],
  },      


    };
  },

components: { breadcrumb, DataGridEdit },
  created() {
    this.getComboLinhVuc();
    this.getComboDichVuVT();
    this.DanhSach_DaCoPhi();
    this.DanhSach_ChuaCoPhi();
  },
  watch: {
    ddlLinhVuc(newdata)
    {
      this.ddlLinhVuc= newdata;
      this.DanhSach_DaCoPhi();
      this.DanhSach_ChuaCoPhi();
    },
    ddlDichVu(newdata)
    {
      this.ddlDichVu= newdata;
      this.DanhSach_DaCoPhi();
      this.DanhSach_ChuaCoPhi();
    },
    json_update(newdata)
    {
      this.json_update= newdata;
    },
},
  provide: {
    grid: [Edit],
  },
  methods: {

      load: function() {
        this.$refs.danhsach_left.ej2Instances.element.addEventListener('mouseup', function(e) {
            var instance = this.ej2_instances[0];
            
            if (e.target.classList.contains("e-rowcell")) {
            if (instance.isEdit)
                instance.endEdit();
                let index = parseInt(e.target.getAttribute("Index"));
                instance.selectRow(index);
                instance.startEdit();
            };
        });
      },

      onActionComplete: function(args) {
        if (args.requestType === 'save') {
         let dv = this.ddlLinhVuc;
         this.json_update.push({ linhvuc_id: dv, dichvugt_id: Object.values(args.data)[2],tien: Object.values(args.data)[3]});
        }
    },





    async getComboLinhVuc() {
      const res = await api.getComboLinhVuc(this.axios, {
      })
      let items = [];
      res.data.data.forEach(function (item) {
        if(item.LINHVUC_ID != null && item.LINHVUC != null)
        {
         items.push({ id: item.LINHVUC_ID, text: item.LINHVUC });
        }
      });
      this.options.linhvuc = items;
      this.ddlLinhVuc = items[0].id;

      console.log('linhvuc',items)

    },
    async getComboDichVuVT() {
      const res = await api.getComboDichVuVT(this.axios, {

      })
      let items = [];
      res.data.data.forEach(function (item) {
        items.push({ id: item.dichvuvt_id, text: item.ten_dvvt });
      });
      this.options.dichvu = items;
      this.ddlDichVu = items[0].id;

    },

    async DanhSach_ChuaCoPhi() {
      this.loading(true);
      
      const res = await api.DanhSach_ChuaCoPhi(this.axios, {
          "linhvuc_id": this.ddlLinhVuc,
          "dichvu_id": this.ddlDichVu 
        })
          this.right.danhsach = res.data.data ? res.data.data : [];
          this.loading(false);

    },

    async DanhSach_DaCoPhi() {
      this.loading(true);
      const res = await api.DanhSach_DaCoPhi(this.axios, {
          "linhvuc_id": this.ddlLinhVuc,
          "dichvu_id": this.ddlDichVu 
        })
          this.left.danhsach = res.data.data ? res.data.data : [];

          this.loading(false);
    },

    
    toLeft(key) {
      let select_records = this.$refs["danhsach_right"].getSelectedRecords();
      //let select_indexs = this.$refs["danhsach_right"].getSelectedRowIndexes();

      if (select_records.length > 0) {
        this.left[key] = select_records.concat(this.left[key]);
        for (var i = 0; i < select_records.length; i++)
          //this.json_delete.push(select_records[i]);
          this.right[key].splice(select_records[i], 1);
          // console.log(this.right[key].splice(select_indexs[i], 1));
          this.right[key] = [...this.right[key]];
          this.left[key] = [...this.left[key]];
      }
      this.EditData(1);

    },
    toRight(key) {
      let select_records = this.$refs[key + "_left"].getSelectedRecords();
      //let select_indexs = this.$refs[key + "_left"].getSelectedRowIndexes();

      //let a = this.$refs[key + "_left"].getSelectedValues()

      if (select_records.length > 0) {
        this.right[key] = select_records.concat(this.right[key]);
        //console.log('this.right[key]',this.right[key]);
        for (var i = 0; i < select_records.length; i++)
        {
          this.json_delete.push(select_records[i]);
          this.left[key].splice(select_records[i], 1); 
          this.right[key] = [...this.right[key]];
          this.left[key] = [...this.left[key]];
        }

      }
        this.EditData(3);
    },
    toAllLeft(key) {
      this.left[key] = this.right[key].concat(this.left[key]);
      this.right[key] = [];
      this.right[key] = [...this.right[key]];
      this.left[key] = [...this.left[key]];
      this.EditData(1);
    },
    toAllRight(key) {
      this.json_delete = this.left.danhsach;
      this.right[key] = this.left[key].concat(this.right[key]);
      this.left[key] = [];
      this.right[key] = [...this.right[key]];
      this.left[key] = [...this.left[key]];
      this.EditData(3);
    },


    async EditData(vkieu)
    {
      let data_array = [];
      data_array = this.left.danhsach;
      let json_data = [];
      let lv = this.ddlLinhVuc;
      if(vkieu == 2)
      {
        data_array = this.json_update;
      }
      if(vkieu == 3)
      {
        data_array = this.json_delete;
      }

        data_array.forEach(function (item) {
          json_data.push({ LINHVUC_ID: lv, DVGT: item.dichvugt_id,TIEN: item.tien});
        });   

      this.json_update=[];
      this.json_delete=[];

      console.log(json_data);
      console.log(lv);
      console.log(vkieu);

        if(json_data.length > 0)
        {
              const res = await api.EditData(this.axios, {
                  "data": json_data,
                  "linhvuc_id": lv,
                  "kieu": vkieu
                })

                let mes = res.data.data ? res.data.data : [];
                if(mes == 0)
                {
                  await this.DanhSach_ChuaCoPhi();
                  await this.DanhSach_DaCoPhi();
                }
                else
                {
                  this.$toast.error('Có lỗi xảy ra trong quá trình cập nhật!!!')
                }

        }

     
    },


    clickButton(key) {

      if (key == "laytt") {    
          this.DanhSach_ChuaCoPhi();
          this.DanhSach_DaCoPhi();
      }
      if (key == "ghilai") {    
        this.EditData(2);
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
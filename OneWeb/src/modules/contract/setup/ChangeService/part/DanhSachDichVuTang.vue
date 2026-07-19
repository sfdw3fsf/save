<template>
  <div id="app">
    <div class="">
      <div class="legend-title">
        <div class="pull-left">Danh sách dịch vụ gia tăng</div>
        <div class="pull-right">
          <div class="check-action normal">
            <input
              :disabled="!thuHoiEnabled"
              type="checkbox"
              class="check"
              v-model="thuHoi"
              @change="changeThuHoi"
            />
            <span class="name">Thu hồi thiết bị tại Giao dịch</span>
          </div>
        </div>
        <div class="clearfix"></div>
      </div>
      <!-- <div
        class="table-content grid-ext grid-ext-freeze"
        style="height: 248px;"
      >
        <ejs-grid
          :dataSource="dataGridAdded.dataSource"
          :editSettings="editSettings"
          :allowSorting="true"
          :allowPaging="true"
          :pageSettings="pageSettings"
          :allowFiltering="true"
           :allowResizing="true" 
          :selectionSettings="dataGridAdded.selectionOptions"
          @recordClick="recordClick"
          :actionComplete="actionComplete"
        >
          <e-columns>
            <e-column
              field="ma_dvgt"
              headerText="Mã DV"
              textAlign="Right"
              width="60"
              :allowEditing="false"              
            >
            </e-column>
            <e-column
              field="ten_dvgt"
              headerText="Tên dịch vụ"
              :allowEditing="false"
              width="90"              
            ></e-column>
            <e-column
              field="tien"
              headerText="Tiền"
              textAlign="Right"
              width="60"
            ></e-column>

            <e-column field="vat_ld" headerText="Vat" width="60"></e-column>

            <e-column
              headerText="Trạng thái"
              field="sudung"
              width="50"
              :template="cTemplateTrangThai"
              :allowEditing="false"
              textAlign="Center"
            ></e-column>

            <e-column
              headerText="Yêu cầu"
              field="kieu_yc"
              width="50"
              :template="cTemplateYeuCau"
              :allowEditing="false"
              textAlign="Center"
            ></e-column>

            <e-column
              headerText="Đăng ký"
              field="dangky"
              width="50"
              :template="this.cTemplateDangKy(this)"
              editType="booleanedit"
              :edit="boolParams"
              textAlign="Center"
            ></e-column>

            <e-column
              field="huy"
              headerText="Hủy"
              width="50"
              :template="this.cTemplateHuy(this)"
              editType="booleanedit"
              :edit="boolParams"
              textAlign="Center"
            ></e-column>
            <e-column
              field="sua"
              headerText="Sửa"
              width="50"
              :template="this.cTemplateSua(this)"
              editType="booleanedit"
              :edit="boolParams"
              textAlign="Center"
            >
            </e-column>
            <e-column
              field="tanglen"
              headerText="Mua thêm"
              width="50"
              :template="this.cTemplateMuathem(this)"
              editType="booleanedit"
              :edit="boolParams"
              textAlign="Center"
            >
            </e-column>
            <e-column
              field="noidung"
              headerText="Nội dung/Số lượng"
              :template="this.cTemplateNoidung(this)"
              width="120"
            ></e-column>
            <e-column
              field="mua_them"
              headerText="Số lượng mua thêm"              
              :template="this.cTemplateSLMuathem(this)"
              width="120"
            ></e-column>
            <e-column
              field="cuoc_sd"
              headerText="Cước SD"              
              textAlign="Right"
              width="100"
            ></e-column>
            <e-column
              field="vat_sd"
              headerText="Vat SD"              
              textAlign="Right"
              width="60"
            ></e-column>
            <e-column field="ghichu" headerText="Ghi chú" width="60" :template="this.cTemplateGhichu(this)"></e-column>
            <e-column
              field="sl_cha"
              headerText="SL cha"
              textAlign="Right"
              width="60"
              :allowEditing="false"
            ></e-column>
            <e-column
              field="block_tiep"
              headerText="Block"
              textAlign="Right"
              width="60"
              :allowEditing="false"
            ></e-column>
            <e-column
              field="gia_block_tiep"
              headerText="Giá Block"
              textAlign="Right"
              width="80"
              :allowEditing="false"
            ></e-column>
            <e-column
              field="vat_block_tiep"
              textAlign="Right"
              headerText="Vat Block"
              width="80"
              :allowEditing="false"
            ></e-column>
          </e-columns>
        </ejs-grid>
      </div> -->
      <DataGrid
      ref="grid_dvgt"
      :dataSource="dataGridAdded.dataSource"      
      :allowEditing="false"
      :allowFiltering="true"
      :allowPaging="true"
      :enablePagingServer="false"
      :columns="columns"
      :enabled-select-first-row="false" 
      @actionComplete="dataBound"
    ></DataGrid>
    </div>
  </div>
</template>
<script>
import Vue from "vue";
import { GridPlugin, Page, Edit,Resize } from "@syncfusion/ej2-vue-grids";
import { mapActions, mapGetters } from "vuex";
import mixinStore from "../mixin/index";
import { ButtonPlugin, CheckBoxPlugin } from "@syncfusion/ej2-vue-buttons";
import { formatDate, formatCurrencyNoVND } from "@/utils/format";
// import Image from

Vue.use(GridPlugin);
Vue.use(CheckBoxPlugin);
Vue.use(ButtonPlugin);

export default {
  data() {
    return {
      thuHoi: false,
      thuHoiEnabled: false,
      columns: [
        
        {
          fieldName: "ma_dvgt",
          headerText: "Mã DV",
          allowFiltering: true,
          allowSorting: true,
          width: "60",
        },
        {
          fieldName: "ten_dvgt",
          headerText: "Tên dịch vụ",
          allowFiltering: true,
          allowSorting: true,
          //width: "90",
        },
        {
          fieldName: "cuoc_ld",
          headerText: "Tiền",
          allowFiltering: true,
          allowSorting: true,
          width: "100",
          template: this.cTemplateCuocLD(this)
        },
        {
          fieldName: "vat_ld",
          headerText: "Vat",
          allowFiltering: true,
          allowSorting: true,
          width: "100",
          template: this.cTemplateVatLD(this)
        },
        {
          fieldName: "sudung",
          headerText: "Trạng thái",          
          width: "120",
          template: this.cTemplateTrangThai()
        },
        {
          fieldName: "kieu_yc",
          headerText: "Yêu cầu",
          allowFiltering: true,
          allowSorting: true,
          width: "50",
          template: this.cTemplateYeuCau()
        },
        {
          fieldName: "dangky",
          headerText: "Đăng ký",
          allowFiltering: true,
          allowSorting: true,
          width: "50",
          template: this.cTemplateDangKy(this),
          textAlign: 'center',
        },
        {
          fieldName: "huy",
          headerText: "Hủy",
          allowFiltering: true,
          allowSorting: true,
          width: "50",
          template: this.cTemplateHuy(this),
          textAlign: 'center',
        },
        {
          fieldName: "sua",
          headerText: "Sửa",
          allowFiltering: true,
          allowSorting: true,
          width: "50",
          template: this.cTemplateSua(this),
          textAlign: 'center',
        },
        {
          fieldName: "tanglen",
          headerText: "Mua thêm",
          allowFiltering: true,
          allowSorting: true,
          width: "50",
          template: this.cTemplateMuathem(this),
          textAlign: 'center',
        },
        {
          fieldName: "noidung",
          headerText: "Nội dung/Số lượng",
          allowFiltering: true,
          allowSorting: true,
          width: "120",
          template: this.cTemplateNoidung(this)
        },
        {
          fieldName: "mua_them",
          headerText: "Số lượng mua thêm",
          allowFiltering: true,
          allowSorting: true,
          width: "100",
          template: this.cTemplateSLMuathem(this)
        },
        {fieldName: "cuoc_sd",headerText: "Cước SD",allowFiltering: true,allowSorting: true,width: "100",},
        {fieldName: "vat_sd",headerText: "Vat SD",allowFiltering: true,allowSorting: true,width: "100",},
        {fieldName: "ghichu",headerText: "Ghi chú",allowFiltering: true,allowSorting: true,width: "100",},
        {fieldName: "sl_cha",headerText: "SL cha",allowFiltering: true,allowSorting: true,width: "60",},
        {fieldName: "block_tiep",headerText: "Block",allowFiltering: true,allowSorting: true,width: "60",},
        {fieldName: "gia_block_tiep",headerText: "Giá Block",allowFiltering: true,allowSorting: true,width: "60",},
        {fieldName: "vat_block_tiep",headerText: "Vat Block",allowFiltering: true,allowSorting: true,width: "60",},
      ],
      dataGridAdded: {
        groupOptions: {
          columns: [
            "ma_dvgt",
            "ten_dvgt",
            "tien",
            "vat_ld",
            "trangthaitb_id",
            "kieu_yc",
            "dangky",
            "huy",
            "sua",
            "mua_them",
            "noidung",
            "tanglen",
            "cuoc_sd",
            "vat_sd",
            "ghichu",
            "sl_cha",
            "block_tiep",
            "gia_block_tiep",
            "vat_block_tiep"
          ]
        },
        selectionOptions: { type: "Multiple" },
        dataSource: [{}]
      },
      checkBoxDangKy: true,
      selectedEmployeesNotAdded: [],

      editSettings: {
        allowEditing: false,
        allowAdding: false,
        allowDeleting: false
        // mode: "Dialog"
      },

      boolParams: {
        params: { change: this.checkBoxChange } // define up the change event to the check box in edit mode.
      },     

      arrayItemDaDangKy: []
    };
  },
  provide: {
    grid: [Page, Edit,Resize]
  },
  async created() {},
  computed: {
    ...mapGetters("mapChangeService", ["dsDichVuGiaTang"])
  },
  watch: {
    dsDichVuGiaTang(val) {
      this.mappingDataDichVuGiaTang(val);
    }
  },
  mixins: [mixinStore],
  methods: {
    dataBound() {
      console.log(' griddatabound')
      this.$refs.grid_dvgt.$refs.grid.autoFitColumns(['sl_cha','gia_block_tiep','huy','sua','ma_dvgt','ten_dvgt','dangky','noidung','mua_them','kieu_yc','tanglen'])
    },
    cTemplateTrangThai() { return function() {
        return {
          template: Vue.component("cTemplateTrangThai", {
            template: `<div v-if="this.data.sudung === null"  class="template_checkbox" >              
            </div>
            <div v-else-if="this.data.sudung !== null" class="template_image" style="text-align:center">
              <div class="checkmark" style="display: inline-block;transform: rotate(45deg);height: 25px;width: 12px; border-bottom: 7px solid #f7f328;border-right: 7px solid #f7f328;"></div>

            </div>
            `,
            data: function() {
              return {
                data: {}
              };
            },
            computed: {
              checkedState: function() {
                return this.data;
              }
            },
            methods: {},
            mounted() {
              // console.log("data:", this.data.trangthaitb_id);
            }
          })
        };
      }
    },

    cTemplateYeuCau() { return function() {
        return {
          template: Vue.component("cTemplateYeuCau", {
            template: `<div v-if="this.data.kieu_yc !== '1'"  class="template_checkbox" >
            </div>
            <div v-else-if="this.data.kieu_yc == '1'" class="template_image">
                <img src="https://pngset.com/images/green-check-mark-clip-art-logo-symbol-trademark-leaf-transparent-png-1879563.png">
            </div>
            `,
            data: function() {
              return {
                data: {}
              };
            },
            computed: {
              checkedState: function() {
                return this.data;
              }
            },
            methods: {},
            mounted() {
              // console.log("data:", this.data.trangthaitb_id);
            }
          })
        };
      }
    },
    cTemplateDangKy(parent) { return function() {
        return {
          template: Vue.component("columnTemplate", {
            template: `<div class="template_checkbox" >                
                <input type="checkbox" style="width:20px;height:20px" v-model="model">
            </div>`,
            data() {
              return {
                model: {}
              };
            },
            computed: {              
            },
            methods: {},
            mounted() {
              this.model = (this.data.dangky==1?true:false)
            },
            watch: {
              model: async function(newval, oldval){
                //handler() {                  
                  if (oldval!=true && oldval!=false) return                  
                  
                  let index = parent.dataGridAdded.dataSource.findIndex(item => item.dichvugt_id == this.data.dichvugt_id)
                  parent.dataGridAdded.dataSource[index].dangky = (this.model==true?1:0)
                  
                  let dataemit={
                    item:parent.dataGridAdded.dataSource[index],
                    list:parent.dataGridAdded.dataSource,
                    column:6,
                    row:index
                  }                  
                  const list = await parent.$parent.UpdateGridDVGT(dataemit)                    
                  if(list) parent.dataGridAdded.dataSource= [...list]
                     
                //},
                //deep: true
              },
              //deep: true
            }
          })
        };
      }
    },
    cTemplateHuy(parent) { return function() {
        return {
          template: Vue.component("columnTemplate", {
            template: `<div class="template_checkbox" >                
                <input type="checkbox" style="width:20px;height:20px" v-model="model">
            </div>`,
            data() {
              return {
                model: {}
              };
            },
            computed: {              
            },
            methods: {},
            mounted() {
              this.model = parseInt(this.data.huy) === 1 ? true : false
            },
            watch: {
              model: async function(newval, oldval){
                //handler() {
                  if (oldval!=true && oldval!=false) return
                  
                  let index = parent.dataGridAdded.dataSource.findIndex(item => item.dichvugt_id == this.data.dichvugt_id)
                  parent.dataGridAdded.dataSource[index].huy = (this.model?1:0)   
                  let dataemit={
                    item:parent.dataGridAdded.dataSource[index],
                    list:parent.dataGridAdded.dataSource,
                    column:7,
                    row:index
                  }                  
                  const list = await parent.$parent.UpdateGridDVGT(dataemit)                    
                  if(list) parent.dataGridAdded.dataSource= [...list]                
                //},
                //deep: true
              }
            }
          })
        };
      }
    },
    cTemplateSua(parent) { return function() {
        return {
          template: Vue.component("columnTemplate", {
            template: `<div class="template_checkbox" >                
                <input type="checkbox" style="width:20px;height:20px" v-model="model">
            </div>`,
            data() {
              return {
                model: {}
              };
            },
            computed: {              
            },
            methods: {},
            mounted() {
              this.model = this.data.sua === 1 ? true : false
            },
            watch: {
              model: async function(newval, oldval){
                //handler() {
                  if (oldval!=true && oldval!=false) return
                  
                  let index = parent.dataGridAdded.dataSource.findIndex(item => item.dichvugt_id == this.data.dichvugt_id)
                  parent.dataGridAdded.dataSource[index].sua = (this.model==true?1:0)  
                  let dataemit={
                    item:parent.dataGridAdded.dataSource[index],
                    list:parent.dataGridAdded.dataSource,
                    column:8,
                    row:index
                  }                  
                  const list = await parent.$parent.UpdateGridDVGT(dataemit)                    
                  if(list) parent.dataGridAdded.dataSource= [...list]                  
                //},
                //deep: true
              }
            }
          })
        };
      }
    },
    cTemplateMuathem(parent) { return function() {
        return {
          template: Vue.component("columnTemplate", {
            template: `<div class="template_checkbox" >                
                <input type="checkbox" style="width:20px;height:20px" v-model="model">
            </div>`,
            data() {
              return {
                model: {}
              };
            },
            computed: {              
            },
            methods: {},
            mounted() {
              this.model = (this.data.tanglen==1?true:false)
            },
            watch: {
              model: async function(newval, oldval){
                //handler() {
                  
                  if (oldval!=true && oldval!=false) return
                  
                  let index = parent.dataGridAdded.dataSource.findIndex(item => item.dichvugt_id == this.data.dichvugt_id)
                  parent.dataGridAdded.dataSource[index].tanglen = (this.model==true?1:0)   
                  let dataemit={
                    item:parent.dataGridAdded.dataSource[index],
                    list:parent.dataGridAdded.dataSource,
                    column:9,
                    row:index
                  }                  
                  //parent.$parent.UpdateGridDVGT(dataemit)    
                  const list = await parent.$parent.UpdateGridDVGT(dataemit)                   
                  if(list) parent.dataGridAdded.dataSource= [...list]           
                //},
                //deep: true
              }
            }
          })
        };
      }
    },
    cTemplateNoidung(parent) { return function() {
        return {
          template: Vue.component("columnTemplate", {
            template: `<input type="text" class="dvgt-input" style="border:none; width:100%;" v-model="model">`,
            data() {
              return {
                model: {}
              };
            },
            computed: {              
            },
            methods: {},
            mounted() {
              this.model = this.data.noidung
            },
            watch: {
              model: async function(newval, oldval){                
                  if (newval==null || newval==undefined) return      
                  let index = parent.dataGridAdded.dataSource.findIndex(item => item.dichvugt_id == this.data.dichvugt_id)
                  parent.dataGridAdded.dataSource[index].noidung = this.model      
                  let dataemit={
                    item:parent.dataGridAdded.dataSource[index],
                    list:parent.dataGridAdded.dataSource,
                    column:10,
                    row:index
                  }
                  
                  await parent.$parent.UpdateGridDVGT_noidung(dataemit)              
                
              }
            }
          })
        };
      }
    },
    cTemplateCuocLD(parent) { return function() {
        return {
          template: Vue.component("columnTemplate", {
            template: `<input type="text" class="dvgt-input" @blur="blur_ld" style="border:none; width:100%;" @click.prevent="click_ld()" v-model="model">`,
            data() {
              return {
                model: {}
              };
            },
            computed: {              
            },
            methods: {
              click_ld(){                
                this.model = this.model.toString().replace(/,/g, "")
              },
              blur_ld(){                
                this.model = formatCurrencyNoVND(this.model)
              }
            },
            mounted() {
              this.model = this.data.cuoc_ld
            },
            watch: {
              model: async function(newval, oldval){                
                  if (newval==null || newval==undefined) return      
                  let index = parent.dataGridAdded.dataSource.findIndex(item => item.dichvugt_id == this.data.dichvugt_id)
                  parent.dataGridAdded.dataSource[index].cuoc_ld = this.model      
                  let dataemit={
                    item:parent.dataGridAdded.dataSource[index],
                    list:parent.dataGridAdded.dataSource,
                    column:2,
                    row:index
                  }
                  
                  await parent.$parent.UpdateGridDVGT_cuocld(dataemit)              
                
              }
            }
          })
        };
      }
    },
    cTemplateVatLD(parent) { return function() {
        return {
          template: Vue.component("columnTemplate", {
            template: `<input type="text" class="dvgt-input" @blur="blur_ld" style="border:none; width:100%;" @click.prevent="click_ld()" v-model="model">`,
            data() {
              return {
                model: {}
              };
            },
            computed: {              
            },
            methods: {
              click_ld(){                
                this.model = this.model.toString().replace(/,/g, "")
              },
              blur_ld(){                
                this.model = formatCurrencyNoVND(this.model)
              }
            },
            mounted() {
              this.model = this.data.vat_ld
            },
            watch: {
              model: async function(newval, oldval){                
                  if (newval==null || newval==undefined) return      
                  let index = parent.dataGridAdded.dataSource.findIndex(item => item.dichvugt_id == this.data.dichvugt_id)
                  parent.dataGridAdded.dataSource[index].vat_ld = this.model      
                  let dataemit={
                    item:parent.dataGridAdded.dataSource[index],
                    list:parent.dataGridAdded.dataSource,
                    column:3,
                    row:index
                  }
                  
                  await parent.$parent.UpdateGridDVGT_vatld(dataemit)              
                
              }
            }
          })
        };
      }
    },
    cTemplateSLMuathem(parent) { return function() {
        return {
          template: Vue.component("columnTemplate", {
            template: `<input type="text" class="dvgt-input" style="border:none; width:100%;" v-model="model">`,
            data() {
              return {
                model: {}
              };
            },
            computed: {              
            },
            methods: {},
            mounted() {
              this.model = this.data.mua_them
            },
            watch: {
              model: {
                handler() {
                  let index = parent.dataGridAdded.dataSource.findIndex(item => item.dichvugt_id == this.data.dichvugt_id)
                  parent.dataGridAdded.dataSource[index].mua_them = this.model                   
                },
                deep: true
              }
            }
          })
        };
      }
    },
    cTemplateGhichu(parent) { return function() {
        return {
          template: Vue.component("columnTemplate", {
            template: `<input type="text" class="dvgt-input" style="border:none; width:100%;" v-model="model">`,
            data() {
              return {
                model: {}
              };
            },
            computed: {              
            },
            methods: {},
            mounted() {
              this.model = this.data.ghichu
            },
            watch: {
              model: {
                handler() {
                  let index = parent.dataGridAdded.dataSource.findIndex(item => item.dichvugt_id == this.data.dichvugt_id)
                  parent.dataGridAdded.dataSource[index].ghichu = this.model                   
                },
                deep: true
              }
            }
          })
        };
      }
    },
    checkBoxChange(args) {      
      var formObj = args.event.target.closest(".e-gridform").ej2_instances[0];
      var emPloyeeIdInput = formObj.element[1].ej2_instances[0]; // get the instance of the input element from form element.
      emPloyeeIdInput.enabled = args.checked; // assaign enable disable to textbox.
    },
    actionComplete(args) {      
      this.$emit("dataEditCompleted", args.data);

      if (args.requestType === "beginEdit") {
        var checkBox = args.form[0].ej2_instances[0]; // get the instance of the checkbox from form element.
        var emPloyeeIdInput = args.form[1].ej2_instances[0]; // get the instance of the input element from form element.
        emPloyeeIdInput.enabled = checkBox.checked; // assaign enable disable to textbox.
      }
    },
    ...mapActions("mapChangeService", [""]),
    mappingDataDichVuGiaTang(array) {
      const list = array.map(item => {
        const res = {
          block_dau: item.block_dau,
          block_tiep: item.block_tiep,
          cuoc_kmld: item.cuoc_kmld,
          cuoc_ld: formatCurrencyNoVND(item.cuoc_ld),
          cuoc_sd: formatCurrencyNoVND(item.cuoc_sd),
          dangky: item.dangky,
          dichvugt_id: item.dichvugt_id,
          flag: item.flag,
          ghichu: item.ghichu,
          gia_block_tiep: formatCurrencyNoVND(item.gia_block_tiep),
          goi_vasc: item.goi_vasc,
          hdtb_id: item.hdtb_id,
          he_so: item.he_so,
          huy: item.huy, 
          kieu_dl: item.kieu_dl,
          kieu_yc: item.kieu_yc,
          ma_dvgt: item.ma_dvgt,
          nhom_goi: item.nhom_goi,
          noidung: item.noidung,
          sl_cha: item.sl_cha,
          stt: item.stt,
          sua: item.sua ,          
          tanglen: item.tanglen,
          mua_them:item.mua_them?item.mua_them:0,
          sudung: item.sudung,
          ten_dvgt: item.ten_dvgt,
          thongbao: item.thongbao,
          thuebao_id: item.thuebao_id,
          tien: formatCurrencyNoVND(item.tien),
          trangthaitb_id: item.trangthaitb_id,
          vat_block_tiep: formatCurrencyNoVND(item.vat_block_tiep),
          vat_kmld: item.vat_kmld,
          vat_ld: formatCurrencyNoVND(item.vat_ld),
          vat_sd: formatCurrencyNoVND(item.vat_sd)
        };
        return res;
      });
      this.dataGridAdded.dataSource = list;      
    },

    changeThuHoi() {
      this.$emit("valueThuHoi", this.thuHoi);
    },
    recordClick(arg) {
      console.log(arg)
      var data = arg.rowData;
      this.$emit("recordClick", data);
    },
    customAggregateFn(event, item) {
      // console.log("data row111:", item);
    },
    updateDataSource(dataSource) {
      this.dataGridAdded.dataSource = dataSource
    },

  }
};
</script>
<style scoped>
/deep/ table .template_image img {
  height: 30px !important;
  width: 30px !important;
}
.dvgt-input {
  border:none;
  width:100%;
}
.template_checkbox input[type=checkbox]{
  width:20px;
  height:20px;
}
</style>

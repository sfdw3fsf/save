<template>
  <div class="giaoviec_grid">
    <DataGrid
      ref="grid"
      :dataSource="dataSource"
      filterTemplate="<span></span>"
      :allowEditing="false"
      :allowFiltering="true"
      :allowPaging="true"
      :enablePagingServer="false"
      :columns="columns"
    ></DataGrid>
  </div>
</template>
<script>
import api from "./GiaoPhieuNhanVien/GiaoPhieuNhanVien.js";

let nhiemvu = [];
var nhiemvu_tree = [];

export default {
  async created() {
    nhiemvu = await this.LAY_DS_NHIEMVU(0)
    nhiemvu_tree = this.list2treeNew(nhiemvu);
  },
  data() {
    return {
      columns: [
        {
          fieldName: "ten_dv",
          headerText: "Đơn vị",
          allowFiltering: true,
          allowSorting: true,
          width: "150",
        },
        {
          fieldName: "ma_nv",
          headerText: "Mã NV",
          allowFiltering: true,
          allowSorting: true,
          width: "150",
        },
        {
          fieldName: "ten_nv",
          headerText: "Tên NV",
          allowFiltering: true,
          allowSorting: true,
          width: "150",
        },
        {
          fieldName: "so_dt",
          headerText: "Điện thoại",
          allowFiltering: true,
          allowSorting: true,
          width: "150",
        },
        {
          fieldName: "nhiemvu",
          headerText: "Nhiệm vụ",
          customAttributes: {class: 'customCollum'},
          clipMode: "Clip",
          width: "300",
          template: this.getColTemplate(this)
        },
        {
          fieldName: "ghichu",
          headerText: "Ghi chú",
          allowFiltering: true,
          allowSorting: true,
          width: "150",
          clipMode: "Clip",
          template: this.getColTemplateGhiChu(this)
        },
      ],
      dataSource: [],
    };
  },
  methods: {
    getColTemplateGhiChu(parent) {
      return function () {
        return {
          template: {
            template: `<input v-model="model" class="form-control"></input>`,
            data() {
              return {
                model: ""
              };
            },
            mounted() {
              this.model = this.data.ghichu
            },
            watch: {
              model: {
                handler() {
                  let index = parent.dataSource.findIndex(item => item.ma_nv == this.data.ma_nv)
                  parent.dataSource[index].ghichu = this.model
                },
                deep: true
              }
            }
          },
        };
      };
    },
    getColTemplate(parent) {
      return function () {
        return {
          template: {
            template: ` <treeselect
                          :multiple="true"
                          v-model="model"
                          :options="nhiemvu_tree"
                          :append-to-body="true"
                          placeholder="Chọn NV"
                          :max-height="200"
                          >
                        </treeselect>
                      `,
            data() {
              return {
                nhiemvu_tree: nhiemvu_tree,
                model: []
              };
            },
            mounted(value) {
              let arrFilter = nhiemvu.filter(item => {if(this.data.nhiemvu) { return this.data.nhiemvu.split(";").includes(item.nhiemvu) }})
              arrFilter.forEach(item => {
                this.model.push(item.nhiemvu_id)
              })
            },
            watch: {
              model: {
                handler() {
                  let index = parent.dataSource.findIndex(item => item.ma_nv == this.data.ma_nv)
                  parent.dataSource[index].nhiemvu = nhiemvu.filter(item => this.model.includes(item.nhiemvu_id)).map(item => item.nhiemvu).join(";")
                },
                deep: true
              }
            }
          },
        };
      };
    },
    list2treeNew(rootList) {
      rootList.forEach((item) => {
        item.id = item.nhiemvu_id;
        item.label = item.nhiemvu ? item.nhiemvu : null;
        item.level = 0;
      });

      return rootList;
    },
    async LAY_DS_NHIEMVU(vhuonggiao_id) {
        let result = []
        try {
            let params = {
                "vhuonggiao_id": vhuonggiao_id
            }
            let response = await api.ur_41003_002_001_3_lay_ds_nhiemvu_hg_vsecond(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data                 
            }
        } catch(e) {
            console.log(e);
        }
        return result
    },
  },
};
</script>
<style>
  .customCollum {
    overflow: visible !important;
  }

  .customCollum .vue-treeselect__control {
    overflow: hidden !important;
  }
</style>
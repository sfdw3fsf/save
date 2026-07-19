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
let nhiemvu = [];
var nhiemvu_tree = [];

export default {
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
          fieldName: "ten_nv",
          headerText: "Tên nhân viên",
          allowFiltering: true,
          allowSorting: true,
          width: "150",
        },
        {
          fieldName: "ma_nv",
          headerText: "Mã nhân viên",
          allowFiltering: true,
          allowSorting: true,
          width: "150",
        },
        {
          fieldName: "so_dt",
          headerText: "Số ĐT",
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
        {
          fieldName: "ma_nd",
          headerText: "Mã ND",
          allowFiltering: true,
          allowSorting: true,
          width: "150",
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
            template: `<treeselect
            :multiple="true"
            v-model="model"
            :options="options"
            :disable-branch-nodes="true"
            :append-to-body="true"
            placeholder="Chọn NV"
            :max-height="200"
            >
            </treeselect>`,
            data() {
              return {
                nhiemvu_tree: [],
                model: []
              };
            },
            mounted() {
              let arrFilter = this.data.dsNhiemvu.filter(item => {if(this.data.nhiemvu) { return this.data.nhiemvu.split(",").includes(item.nhiemvu) }})
              arrFilter.forEach(item => {
                this.model.push(item.nhiemvu_id)
              })
            },
            computed: {
              options() {
                return this.list2treeNew(this.data.dsNhiemvu)
              }
            },
            methods: {
              list2treeNew(list) {
                let data = {}
                list.forEach((item, index) => {
                  if(!data[item.nhiemvu_cha]) {
                    data[item.nhiemvu_cha] = {
                      id       : Number(index) + 1000000,
                      label    : item.nhiemvu_cha,
                      children : []
                    }
                  }

                  data[item.nhiemvu_cha].children.push({
                    id       : item.nhiemvu_id,
                    label    : item.nhiemvu
                  })
                })

                return Object.values(data);
              },
            },
            watch: {
              model: {
                handler() {
                  let index = parent.dataSource.findIndex(item => item.ma_nv == this.data.ma_nv)
                  parent.dataSource[index].nhiemvu = this.data.dsNhiemvu.filter(item => this.model.includes(item.nhiemvu_id)).map(item => item.nhiemvu).join(",")
                },
                deep: true
              }
            }
          },
        };
      };
    },
    list2treeNew(list) {
      console.log(list);
      let rootList = list.filter((x) => x.NHIEMVU_CHA_ID == null);
      let openList = [];
      rootList.forEach((item) => {
        item.id = item.NHIEMVU_ID;
        item.label = item.NHIEMVU ? item.NHIEMVU : null;
        item.level = 0;
        openList.push(item);
      });

      while (openList.length) {
        let item = openList.shift();
        let children = list.filter((x) => x.NHIEMVU_CHA_ID == item.NHIEMVU_ID);
        if (children.length !== 0) {
          item.children = children;
          children.forEach((childItem) => {
            childItem.level = item.level + 1;
            childItem.id = childItem.NHIEMVU_ID;
            childItem.label = childItem.NHIEMVU ? childItem.NHIEMVU : null;
            openList.push(childItem);
          });
        }
      }
      return rootList;
    },
  },
};
</script>
<style>
  .customCollum {
    overflow: visible !important;
  }

  .vue-treeselect--has-value .vue-treeselect__multi-value {
        margin-bottom: 5px;
        height: 50px !important;
        overflow: scroll;
    }

  .giaoviec_grid .e-content {
    /* overflow-x: visible !important; */
  }
</style>
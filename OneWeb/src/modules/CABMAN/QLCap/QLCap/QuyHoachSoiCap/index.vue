<template src="./index.html"></template>
<script>
import { GridPlugin, ContextMenu, Sort, Filter, ForeignKey  } from "@syncfusion/ej2-vue-grids";
import { L10n } from '@syncfusion/ej2-base'

L10n.load({
  'vi-VN': {
    grid: {
      EmptyRecord: 'Không có bản ghi nào để hiển thị'
    }
  }
})
export default {
  provide:{
    grid:[ContextMenu, Sort, Filter, ForeignKey]
  },
  components:{GridPlugin},
  name: "QuyHoachSoiCap",
  props: {
    capId: Number,
    id: String,
  },
  data: function (){
    return {
      key: 1,
      isShown: false,
      showGrid:false,

      dsSoiCap:[],
      soiChecked:[],
      checkAllSoi:false,
      contextMenuItems:[
        {text: 'Chọn hết', target: '.e-content', id: 'checkAll', iconCss: "fa fa-check",},
        {text: 'Bỏ chọn tất cả', target: '.e-content', id: 'unCheckAll', iconCss: "fa fa-remove",},
        {text: 'Đảo chọn', target: '.e-content', id: 'inverse', iconCss: "fa fa-undo",},
      ],
      columnTemplate: function () {
        return {
          template: {
            template: '<span :id="data.index" :style="{display: display }" style="font-size: 12px;" class="col-mark-selected-row fa fa-play text-main"></span>',
            data() {
              return {
                data: { }
              }
            },
            computed: {
              display() {
                return parseInt(this.data.index) == this.$parent.getSelectedRowIndexes()[0] ? 'block' : 'none'
              }
            }
          }
        }
      },
      filterBarTemplate: {
        create: function (args) {

          let div = document.createElement('div')
          div.classList.add('input-icon-right')

          let input = document.createElement('input')
          input.classList.add('form-control')
          input.id = args.column.foreignKeyField

          let span = document.createElement('span')
          span.classList.add('icon', 'nc-icon-outline', 'ui-1_zoom')

          div.append(input)
          div.append(span)

          return div
        },
        write: function (args) {

          args.element.querySelector('input').addEventListener('input', (args) => {

            let value = args.currentTarget.value

            if (!value) {
              this.parent.removeFilteredColsByField(args.currentTarget.id);
            } else {
              this.parent.filterByColumn(args.currentTarget.id, 'contains', value)
            }
          })
        }
      },

      headerTemplateChonNhieuSoi: function() {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.checkAllSoi" class="check"/>
                                <span class="name"></span>
                            </div>
                        `,
            data: function () {
              return { }
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent.$parent
              }
            }
          }
        }
      },
      columnTemplateChonNhieuSoi: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="check"
                                    v-model="parent.soiChecked"
                                    :value="data.SOI"/>
                                <span class="name"></span>
                            </div>
                        `,
            data: function () {
              return {
                data: { }
              }
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent.$parent
              },
            }
          }
        }
      },
    }
  },
  destroyed() {
    this.isShown = false
    this.key++
    this.showGrid = false
    this.clearData()
  },
  watch:{
    "soiChecked": function (value){
      this.checkAllSoi = value.length > 0 && value.length === this.dsSoiCap.length
    },
    "checkAllSoi": function (value){
      if (value){
        this.soiChecked = this.dsSoiCap.map(item => item.SOI)
        return
      }
      if (this.soiChecked.length > 0 && this.soiChecked.length === this.dsSoiCap.length) {
        this.soiChecked = []
      }
    },
  },
  methods: {
    onHiddenModal() {
      this.isShown = false
      this.key++
      this.showGrid = false
      this.clearData()
    },
    async onShownModal() {
      this.isShown = true
      this.showGrid = true
      try {
        this.loading(true)
        await this.getDsSoiCap(this.capId)
        document.addEventListener('keyup', this.logKey);

      }catch (e){
        console.log(e)
      }finally {
        this.loading(false)
      }
    },
    logKey(e) {
      switch (e.code){
        case 'Insert':
          this.soiChecked = this.dsSoiCap.map(item => item.SOI)
          break
        case 'Delete':
          this.soiChecked = []

      }
    },
    onDataBoundSoiCap(){
      this.soiChecked = this.dsSoiCap.filter(item => item.CHON === "s1").map(item => item.SOI)
    },
    async btnXacNhan(){
      // if (this.soiChecked.length === 0){
      //   this.$root.toastError("Chưa chọn sợi cáp")
      //   return
      // }
      // cập nhật danh sách sợi cáp quy hoạch
      let object = new Object()
      object.capId = this.capId
      object.dsSoi = this.soiChecked.toString()
      try {
        this.loading(true)
        await this.$root.context.post("/web-cabman/mang-truyen-dan/capnhat-ds-quyhoach-soicap",object)
          .then(rs => {
            if (rs.error === "200" && rs.error_code === "BSS-00000000" && rs.data.result === true){
              this.$root.toastSuccess("Cập nhật danh sách quy hoạch sợi cáp sử dụng cho thuê bao thành công")
              this.getDsSoiCap(this.capId)
            }
          }).catch(e => this.$root.toastError("Có lỗi xảy ra khi cập nhật danh sách quy hoạch sợi cáp cho thuê bao"))
      }catch (e){
        console.log(e)
      }finally {
        this.loading(false)
      }
    },
    contextMenuClick: function(args) {
      switch (args.item.id){
        case 'checkAll':
          this.soiChecked = this.dsSoiCap.map(item => item.SOI)
          break
        case 'unCheckAll':
          this.soiChecked = []
          break
        case 'inverse':
          this.soiChecked = this.dsSoiCap.filter(item => !this.soiChecked.includes(item.SOI)).map(item => item.SOI)
      }
    },
    async getDsSoiCap(capId){
      await this.$root.context.get('/web-cabman/mang-truyen-dan/lay-ds-soicap-quyhoach',{capId:capId})
        .then(rs =>{
          this.dsSoiCap = rs.data
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách sợi cáp"))
    },
    clearData(){
      this.dsSoiCap = []
      this.soiChecked = []
      this.checkAllSoi = false
    }
  }
}
</script>

<style>
.quyhoach-soicap .e-control.e-numerictextbox.e-lib.e-input {
  text-align: right !important;
  padding-right: 5px !important;
}

.quyhoach-soicap .e-grid {
  font-family: "Roboto", Arial !important;
}

.quyhoach-soicap .e-grid .e-filterbarcell {
  padding: 5px 7px !important;
}

.quyhoach-soicap .e-grid th.e-grouptopleftcell,
.quyhoach-soicap .e-grid th.e-headercell,
.quyhoach-soicap .e-grid th.e-filterbarcell {
  background-color: #BAE7FF !important;
  border-color: white !important;
}

.quyhoach-soicap .e-grid th.e-filterbarcell {
  border-width: 1px 0 0 1px !important;
}

.quyhoach-soicap .e-grid td.e-active {
  background: #F9E1A9 !important;
}

.quyhoach-soicap .e-grid.e-gridhover tr[role='row']:not(.e-editedrow):hover .e-rowcell:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.quyhoach-soicap .e-grid.e-gridhover tr[role='row']:hover .e-detailrowcollapse:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.quyhoach-soicap .e-grid.e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.quyhoach-soicap .e-grid.e-rtl .e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.quyhoach-soicap .e-grid.e-gridhover tr[role='row']:hover .e-detailrowexpand:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell) {
  background-color: #FFF9EB !important;
}

.quyhoach-soicap .e-grid td.e-rowcell {
  line-height: 20px !important;
  padding: 5px 7px !important;
}

.quyhoach-soicap .e-grid .e-filterdiv.e-fltrinputdiv .e-input-group.e-control-wrapper {
  border: 0 !important;
}

.quyhoach-soicap .e-grid .e-filtertext.e-input.e-disable {
  display: none !important;
}

.quyhoach-soicap .e-grid tr.e-emptyrow td {
  text-align: center !important;
}

.quyhoach-soicap .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"],
.quyhoach-soicap .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"] > div,
.quyhoach-soicap .grid-phieu div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="2"],
.quyhoach-soicap .grid-phieu div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="2"] > div,
.quyhoach-soicap .grid-phieu div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="3"],
.quyhoach-soicap .grid-phieu div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="3"] > div {
  padding: 0 !important;
}
</style>

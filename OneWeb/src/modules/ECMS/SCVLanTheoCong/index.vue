<template src="./index.html"></template>
<script>
import BssRequiredMarker from '@/components/BssRequiredMarker'
// import { Edit, Sort, Filter, ForeignKey, Resize } from "@syncfusion/ej2-vue-grids"
import { L10n } from '@syncfusion/ej2-base'

L10n.load({
  'vi-VN': {
    grid: {
      EmptyRecord: 'Không có bản ghi nào để hiển thị'
    }
  }
})
export default {
  components: {
    BssRequiredMarker
  },
  // provide: {
  //   grid: [Edit, Sort, Filter, ForeignKey, Resize ]
  // },
  data : function ()  {
    return {
      dsTramTbi: [],
      cboTramTbi:'',
      dsThietBi: [],
      cboThietBi:'',
      dsQuyHoach:[],
      cboQuyHoach:'',
      dsPortFix:[],
      modalXoa: false,
      rowsChecked:[],

      isDisableBtnXoa: true,
      checkAllPort:true,

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
      headerTemplateChonNhieuPort: function() {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.checkAllPort" class="check"/>
                                <span class="name"></span>
                            </div>
                        `,
            data: function () {
              return { }
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent
              }
            }
          }
        }
      },
      columnTemplateChonNhieuPort: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="check"
                                    v-model="parent.rowsChecked"
                                    :value="data.PORTVL_ID"/>
                                <span class="name"></span>
                            </div>
                        `,
            data() {
              return {
                data: { }
              }
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent
              },
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
    }
  },
  created: async function () {
    try {
      this.loading(true)
      await this.getDsTramTbi()
      if (this.dsTramTbi && this.dsTramTbi.length >0){
        this.cboTramTbi = this.dsTramTbi[0]['DONVI_ID']
      }
    } catch (e){

    } finally {
      this.loading(false)
    }
  },
  watch: {
    //onChange cbo TramTbi
    "cboTramTbi": async function(newTramTbi){
      try {
        this.loading(true)
        await this.getDsThietBi(newTramTbi)
        if (this.dsThietBi && this.dsThietBi.length > 0){
          this.cboThietBi = this.dsThietBi[0]['DSLAM_ID']
          await this.getDsQuyHoach(this.dsThietBi[0]['DSLAM_ID'])
          await this.getDsPortFix(this.dsThietBi[0]['DSLAM_ID'])
        }else {
          await this.getDsQuyHoach(null)
          await this.getDsPortFix(null)
          this.cboThietBi = ''
          this.cboQuyHoach = ''
        }
      }catch (e){

      }finally {
        this.loading(false)
      }

    },
    // onChange cbo ThietBi
    "cboThietBi": async function(newThietBi){
      try {
        this.loading(true)
        await this.getDsQuyHoach(newThietBi)
        if (this.dsQuyHoach.length < 2) { // has 1 option default
          // reset cbo Quyhoach when data not found
          this.cboQuyHoach = ''
        }
        await this.getDsPortFix(newThietBi)
      }catch (e){

      }finally {
        this.loading(false)
      }
    },
    // onChange cbo QuyHoach
    "cboQuyHoach": async function(newQuyHoach){
      if (newQuyHoach['QUYHOACH_ID'] === -1 || newQuyHoach === '' || newQuyHoach === null)
        return
      else {
        // get ds from checked rows
        // if (this.rowsChecked.length === 0){
        //   this.$root.toastError("Chưa chọn cổng !")
        //   return
        // }
        let cvlan = newQuyHoach['CVLAN_TU']
        // this.rowsChecked.forEach(item =>{
        //   let index = this.dsPortFix.findIndex(element => element === item)
        //   if (cvlan < newQuyHoach['CVLAN_DEN']){
        //     this.dsPortFix[index]['QUYHOACH_ID'] = newQuyHoach['QUYHOACH_ID']
        //     this.dsPortFix[index]['SVLAN'] = newQuyHoach['SVLAN']
        //     this.dsPortFix[index]['CVLAN_BD'] = cvlan
        //     cvlan += 100
        //   }
        // })
        this.dsPortFix.forEach(item =>{
          if (cvlan < newQuyHoach['CVLAN_DEN']){
            item.QUYHOACH_ID = newQuyHoach['QUYHOACH_ID']
            item.SVLAN = newQuyHoach['SVLAN']
            item.CVLAN_BD = cvlan
            cvlan += 100
          }
        })
        this.dsPortFix = [... this.dsPortFix]
      }
    },
    "checkAllPort": function (value){
      if (value){
        this.rowsChecked = this.dsPortFix.map(item => item.PORTVL_ID)
        return
      }
      if (this.rowsChecked.length > 0 && this.rowsChecked.length === this.dsPortFix.length) {
        this.rowsChecked = []
      }
    },
    "rowsChecked": function(value){
      this.checkAllPort = value.length > 0 && value.length === this.dsPortFix.length
      if (value.length > 0){
        this.statusBtn(false)
      }else {
        this.statusBtn(true)
      }
    },
    dsPortFix: function (value){
      this.rowsChecked = value.map(item => item.PORTVL_ID)
    }
  },
  destroyed: function() {
    this.clearData()
  },
  methods: {
    // select_selectedItemsChanged(dataItem){
    //   this.rowsChecked = this.dsPortFix.filter(item => dataItem.includes(item.PORTVL_ID))
    //   if (this.rowsChecked.filter(item => item.ID !== null).length > 0) this.statusBtn(false)
    //   else this.statusBtn(true)
    // },
    // grid1_selectedRowChanged(dataItem){
    //   console.log(dataItem)
    // },
    btnGhiLai: async function(){
      //update information of rowchecked
      let dsGhiLai = this.dsPortFix.filter(item => this.rowsChecked.includes(item.PORTVL_ID))
      if (dsGhiLai.length === 0){
        this.$root.toastError("Chưa chọn cổng !")
        return
      }
      let dsPortFixInsert = []
      let dsPortFixUpdate = []
      dsGhiLai.forEach(item =>{
        if (item.QUYHOACH_ID){
          // TH: row checked va co quyhoach
          if (item.ID){
            //for update
            dsPortFixUpdate.push({
              dslamId:item.DSLAM_ID,
              loaitbiId: item.LOAITBI_ID,
              portvlId: item.PORTVL_ID,
              svlan: item.SVLAN,
              cvlanBd: item.CVLAN_BD,
              quyhoachId:item.QUYHOACH_ID,
              id:item.ID})
          }else {
            //for insert
            dsPortFixInsert.push({
              dslamId:item.DSLAM_ID,
              loaitbiId: item.LOAITBI_ID,
              portvlId: item.PORTVL_ID,
              svlan: item.SVLAN,
              cvlanBd: item.CVLAN_BD,
              quyhoachId:item.QUYHOACH_ID})
          }
        }

      })
      if (dsPortFixUpdate.length === 0 && dsPortFixInsert.length === 0){
        this.$root.toastError("Chưa chọn quy hoạch !")
        return
      }
      // insert, update
      let insertFlag = 0
      let updateFlag = 0
      try {
        this.loading(true)
        //insert
        if (dsPortFixInsert.length > 0){
          await this.$root.context.post(
            "/web-ecms/fixscvlan/them-ds-port-quyhoach",dsPortFixInsert
          ).then(res =>{
            if (res.error_code === 'BSS-00000000' && res.error === '200'){
              insertFlag = 1
            }
          }).catch(e => {
            insertFlag = 0
            throw e.response.data.message_detail
          })
        } else insertFlag = 1
        if (dsPortFixUpdate.length > 0){
          await this.$root.context.post(
            "/web-ecms/fixscvlan/capnhat-ds-port-quyhoach",dsPortFixUpdate
          ).then(res =>{
            if (res.error_code === 'BSS-00000000' && res.error === '200'){
              updateFlag = 1
            }
          }).catch(e => {
            updateFlag = 0
            throw e.response.data.message_detail
          })
        } else updateFlag = 1
      } catch (e){
        this.$root.toastError(e)
        await this.getDsPortFix(this.cboThietBi)
      } finally {
        if (insertFlag == 1 && updateFlag == 1){
          //load grid
          await this.getDsPortFix(this.cboThietBi)
          // this.dsPortFix = [...this.dsPortFix]
          this.$toast.success("Ghi lại quy hoạch thành công !")
          this.cboQuyHoach = ""
        }
        this.loading(false)
      }
    },
    btnXoa: async function(){
      if (this.isDisableBtnXoa) return
      this.modalXoa = !this.modalXoa;
    },
    xacNhanXoa: async function() {
      //update information of rowchecked
      let dsXoa = this.dsPortFix.filter(item => this.rowsChecked.includes(item.PORTVL_ID))
      if (dsXoa.length === 0){
        this.$root.toastError("Chưa chọn cổng !")
        return
      }
      try {
        this.loading(true)
        //fix property input name
        let dsPortQuyHoachDelete = dsXoa.filter(item => item.QUYHOACH_ID && item.ID).map(item => ({id: item.ID}))
        if (dsPortQuyHoachDelete.length > 0){
          await this.$root.context.post("/web-ecms/fixscvlan/xoa-ds-port-quyhoach", dsPortQuyHoachDelete)
            .then(res =>{
              if (res.error_code === 'BSS-00000000' && res.error === '200'){
                //xoa cvlan ,scvlan, quyhoach_id
                dsXoa.forEach(item =>{
                  let index = this.dsPortFix.findIndex(element => element === item)
                  this.dsPortFix[index].SVLAN = null
                  this.dsPortFix[index].CVLAN_BD = null
                  this.dsPortFix[index].QUYHOACH_ID = null
                  this.dsPortFix[index].ID = null
                })
                //refresh grid
                this.dsPortFix = [...this.dsPortFix]
                this.$toast.success("Xóa quy hoạch thành công !")
                this.cboQuyHoach = ""
              }
            }).catch(e =>{
              throw e.response.data.message_detail
            })
        }else {
          //chưa có quy hoạch
          throw "Không có thông tin quy hoạch SCVLAN để xóa"
        }
      } catch (e){
        this.$root.toastError(e)
      } finally {
        this.dsPortFix = [...this.dsPortFix]
        this.loading(false)
      }
    },
    btnHuyBo: async function(){
      try {
        this.loading(true)
        await this.getDsPortFix(this.cboThietBi)
        this.cboQuyHoach = ""
        // this.rowsChecked = []
      }catch (e){
        this.$root.toastError(e)
      }finally {
        this.loading(false)
      }
    },
    statusBtn(btnXoa){
      this.isDisableBtnXoa = btnXoa
    },
    onRowSelectedPortFix(args) {
      this.$refs['grid-portfix'].$el.querySelectorAll('.col-mark-selected-row').forEach(item => {
        item.style.display = args.rowIndex == parseInt(item.id) ? 'block' : 'none'
      })
      // this.dsPortFix[args.rowIndex].CHON = 1
    },
    // onRowDeSelectedPortFix(args){
    //   this.dsPortFix[args.rowIndex].CHON = 0
    // },
    async onCellSavedPortFix(args) {
      // let cvlan_bd = parseInt(args.value,10)
      // if (!cvlan_bd){
      //   this.$root.toastError("CVLAN phải là số")
      // }
      // this.dsPortFix.forEach(item =>{
      //   if (item.PORTVL_ID === args.rowData.PORTVL_ID){
      //     item.CVLAN_BD = cvlan_bd
      //     return
      //   }
      // })
      let portFixGrid = this.$refs['grid-portfix']
      let changes = portFixGrid.getBatchChanges()
      portFixGrid.batchUpdate(changes)
    },

    getDsTramTbi: async function () {
      var rs = await this.$root.context.get(
        "/web-ecms/danhmuc/quyhoach_scvlan/dstram_tbi"
      );
      this.dsTramTbi = rs.data;
    },
    getDsThietBi: async function (donviId) {
      var rs = await this.$root.context.post(
        "/web-ecms/danhmuc/quyhoach_scvlan/dmthietbi",{kieu:4, donvi_id:donviId}
      );
      this.dsThietBi = rs.data;
    },
    getDsQuyHoach: async function (dslamId) {
      var rs = await this.$root.context.get(
        "/web-ecms/fixscvlan/dm-quyhoach", {dslamId:dslamId}
      );
      this.dsQuyHoach = rs.data;
    },
    getDsPortFix: async function (dslamId) {
      var rs = await this.$root.context.get(
        "/web-ecms/fixscvlan/lay-ds-port-quyhoach",{dslamId:dslamId}
      );
      this.dsPortFix = rs.data;
    },
    clearData(){
      this.dsTramTbi = []
      this.dsThietBi = []
      this.dsQuyHoach = []
      this.dsPortFix = []
      this.rowsChecked = []
    }
  }
}
</script>
<style>
.scvlan-theocong .e-control.e-numerictextbox.e-lib.e-input {
  text-align: right !important;
  padding-right: 5px !important;
}

.scvlan-theocong .e-grid {
  font-family: "Roboto", Arial !important;
}

.scvlan-theocong .e-grid .e-filterbarcell {
  padding: 5px 7px !important;
}

.scvlan-theocong .e-grid th.e-grouptopleftcell,
.scvlan-theocong .e-grid th.e-headercell,
.scvlan-theocong .e-grid th.e-filterbarcell {
  background-color: #BAE7FF !important;
  border-color: white !important;
}

.scvlan-theocong .e-grid th.e-filterbarcell {
  border-width: 1px 0 0 1px !important;
}

.scvlan-theocong .e-grid td.e-active {
  background: #F9E1A9 !important;
}

.scvlan-theocong .e-grid.e-gridhover tr[role='row']:not(.e-editedrow):hover .e-rowcell:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.scvlan-theocong .e-grid.e-gridhover tr[role='row']:hover .e-detailrowcollapse:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.scvlan-theocong .e-grid.e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.scvlan-theocong .e-grid.e-rtl .e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.scvlan-theocong .e-grid.e-gridhover tr[role='row']:hover .e-detailrowexpand:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell) {
  background-color: #FFF9EB !important;
}

.scvlan-theocong .e-grid td.e-rowcell {
  line-height: 20px !important;
  padding: 5px 7px !important;
}

.scvlan-theocong .e-grid .e-filterdiv.e-fltrinputdiv .e-input-group.e-control-wrapper {
  border: 0 !important;
}

.scvlan-theocong .e-grid .e-filtertext.e-input.e-disable {
  display: none !important;
}

.scvlan-theocong .e-grid tr.e-emptyrow td {
  text-align: center !important;
}

.scvlan-theocong .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"],
.scvlan-theocong .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"] > div,
.scvlan-theocong .grid-phieu div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="2"],
.scvlan-theocong .grid-phieu div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="2"] > div,
.scvlan-theocong .grid-phieu div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="3"],
.scvlan-theocong .grid-phieu div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="3"] > div {
  padding: 0 !important;
}
.required:after {
  content:" *";
  color: red;
}
.disable-btn:hover {
  background-color: white !important;
}

.disable-btn a {
  color: #6c757d !important;
}

.disable-btn a:hover {
  cursor: default;
  color: #6c757d;
}
</style>

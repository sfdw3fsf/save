<template src="./index.html"></template>
<script>
import { Page, Filter, ExcelExport, Sort} from "@syncfusion/ej2-vue-treegrid";
import ModalXuatDuLieu from '@/modules/ECMS/TraCuuSplitterTheoPortOLT/ModalXuatDuLieu/index.vue'
import Pagination from '@/components/Controls/Pagination/index.vue'
import { L10n } from '@syncfusion/ej2-base'
import PhanViTriKetCuoiChoNhanVien from './PhanViTriKetCuoiChoNhanVien'
L10n.load({
  'vi-VN': {
    treegrid: {
      EmptyRecord: 'Không có bản ghi nào để hiển thị'
    }
  }
})
export default {
  components:{ModalXuatDuLieu, Pagination, PhanViTriKetCuoiChoNhanVien},
  data : function ()  {
    return {
      // Paging
      pageSize:10,
      pageIndex:0,
      totalRecords:0, // Tổng số record phân trang server

      dsDonVi: [],
      dsKetCuoiChuaPhanNV: [],
      dataSource: '',
      fields: '',
      checkedDonVi:[],
      data:[],
      rowSpanningArray:[],
      // Vẫn để page local hide vì ko có page local collapse/expanded bị lỗi do rowspan
      pageSettings: { pageSizes: ['10','20','50','100','All'], pageSize: 11 },
      // filterOptions: { type: 'FilterBar', hierarchyMode: 'Parent', mode: 'Immediate', operators: '%', immediateModeDelay: 100, showFilterBarStatus:false},
      // groupOptions: { columns: ['TENTRUNGTAM', 'TEN_DV'] , showDropArea:false}

      isDisableBtnXuatFile:true,
      isDisableBtnPhanKetCuoi:true,
      filterBarTemplate : {
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

      dsThuocTinh:[],
      dsXuatFile:[],

      phanVitriInput:{
        donvi_id:0,
        ketcuoi_id:0,
        capgoc_id:0,
        ktparent:false
      }
    }
  },
  provide: {
    treegrid: [ Page,Filter, ExcelExport, Sort ]
  },
  created: async function () {
    // this.getDsKetCuoiChuaPhanNV()
    try {
      // this.dsDonVi = this.array_to_tree(this.dsDonVi)
      this.loading(true)
      await this.getDsDonVi()
      let donViTree = this.createTreeDonVi(this.dsDonVi)
      this.fields = { dataSource: donViTree, id: 'DONVI_ID', text: 'TEN_DV', parentID: 'DONVI_CHA_ID', hasChildren: 'hasChild' }
    } catch (e){
    } finally {
      this.loading(false)
    }
  },
  computed:{
    getTotalRecords(){
      return this.totalRecords
    }
  },
  watch: {
    pageSize: function (value) {
      console.log(value)
      this.pageSettings = {
        pageSizes: ['10', '20', '50', '100', 'All'],
        pageSize: value + this.dsKetCuoiChuaPhanNV.length
      } // do treegrid đếm cả parent nên + số parent để khỏi mất row
    },
  },
  methods: {
    nodeChecked(treeObj){
      var treeObj = document.getElementById('treeview').ej2_instances[0];
      this.checkedDonVi = treeObj.checkedNodes
      // if (treeObj.action === "check"){
      //   let CheckedObjs = treeObj.data.filter(item => item['isChecked'] === "true")
      //   CheckedObjs.forEach(item => this.checkedDonVi.push(item.id))
      // }else if (treeObj.action === "uncheck"){
      //   let CheckedObjs = treeObj.data.filter(item => item['isChecked'] === "false")
      //   CheckedObjs.forEach(item => {
      //     // console.log(this.checkedDonVi)
      //     let index = this.checkedDonVi.findIndex((element) => element === item.id)
      //     if (index != -1) //index = -1 khong co element trong arr
      //       this.checkedDonVi.splice(this.checkedDonVi.findIndex((element) => element === item.id),1)
      //     // delete this.checkedDonVi[this.checkedDonVi.findIndex((element) => element === item.id)]
      //   })
      //   // console.log("end:",this.checkedDonVi)
      // }
    },
    rowSelected(args){
      if (args.data){
        console.log(args.data)
        if (args.data.level !== 0){
          this.phanVitriInput.donvi_id = args.data.DONVI_ID
          this.phanVitriInput.ketcuoi_id = args.data.KETCUOI_ID
          this.phanVitriInput.capgoc_id = args.data.CAPGOC_ID
        }
      }
    },
    getDsDonVi: async function () {
      var rs = await this.$root.context.get(
        "/web-cabman/tracuu/lay-ds-donvi"
      );
      this.dsDonVi = rs.data;
    },
    getDsKetCuoiChuaPhanNV: async function (danhSachToQuanLyId) {
      var rs = await this.$root.context.get(
        "/web-cabman/tracuu/vitri-ketcuoi-chuaphan-nv", {danhSachToQuanLyId:danhSachToQuanLyId}
      );
      if (rs.data.length > 0){
        // this.dsKetCuoiChuaPhanNV = rs.data;
        this.dsXuatFile = rs.data
        // this.statusBtn(false,false)
      }else {
        this.dsXuatFile = []
        // this.dsKetCuoiChuaPhanNV = []
        // this.statusBtn(true,true)
      }
    },
    async getDsKetCuoiChuaPhanNVPaging(danhSachToQuanLyId, pageIndex, pageSize) {
      try {
        // this.loading(true)
        await this.$root.context.post(
          `/web-cabman/tracuu/vitri-ketcuoi-chuaphan-nv-paging?danhSachToQuanLyId=${danhSachToQuanLyId}`,
          {
            pageIndex: pageIndex,
            pageSize: pageSize,
          }
        ).then(rs =>{
          if (this.totalRecords != rs.data.allItems) {
            this.totalRecords = rs.data.allItems
            return
          }
          if (rs.data.pageItems.length > 0){
            this.dsKetCuoiChuaPhanNV = rs.data.pageItems
            this.dsKetCuoiChuaPhanNV = this.createTreeGrid(this.dsKetCuoiChuaPhanNV)
            this.getRowSpanArray(rs.data.pageItems)
            this.dsXuatFile = rs.data.pageItems
            this.statusBtn(false,false)
          }else {
            this.dsKetCuoiChuaPhanNV = []
            this.$root.toastError("Không có dữ liệu, vui lòng thử lại.")
            this.statusBtn(true,true)
          }
        }).catch(e =>{
          console.log(e)
          this.$root.toastError("Có lỗi xảy ra khi tải danh sách kết cuối")
        })
      }catch (e) {

      }finally {
        // this.loading(false)
      }
    },
    async updateDsKetCuoiChuaPhanNVCount(danhSachToQuanLyId) {
      try {
        this.$root.showLoading(true)
        await this.$root.context.post(
          `/web-cabman/tracuu/vitri-ketcuoi-chuaphan-nv-paging?danhSachToQuanLyId=${danhSachToQuanLyId}`,
          {
            pageIndex: 0,
            pageSize: 1,
          }).then(res => {
          if (this.totalRecords == res.data.allItems) this.$refs.pagination.reloadCurrentPage()
          this.totalRecords = res.data.allItems > 0 ? res.data.allItems : 0;
          if (this.totalRecords == 0) {
            this.$root.showLoading(false)
            this.dsKetCuoiChuaPhanNV = []
            this.$root.toastError("Không có dữ liệu, vui lòng thử lại.")
            this.statusBtn(true, true)
          }
        })
      } catch (e) {

      } finally {
        // this.$root.showLoading(false);
      }
    },
    pagination_PageChanged: async function (args) {
      this.pageSize = args.pageSize;
      this.pageIndex = args.pageIndex;
      try {
        this.loading(true)
        await this.getDsKetCuoiChuaPhanNVPaging(this.checkedDonVi.toString(), args.pageIndex, args.pageSize)
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    async show(){
      // covert arr id to string
      let str = this.checkedDonVi.toString()
      if (str){
        await this.loadDsKetCuoiChuaPhanNV(str)
      }else {
        this.$root.toastError("Chưa chọn đơn vị")
        return
      }
    },
    btnPhanKetCuoi(){
      if (this.isDisableBtnPhanKetCuoi) return
      // window.open("#/cabman/phan-ketcuoi-cho-nhanvien")
      if (this.phanVitriInput.ketcuoi_id === 0){
        this.$root.toastError("Bạn chưa chọn kết cuối nào")
        return
      }
      this.phanVitriInput.ktparent = true
      this.$bvModal.show("PhanViTriKetCuoiChoNhanVien")
    },
    btnXuatFile: async function () {
      if (this.isDisableBtnXuatFile) return
      this.$root.showLoading(true);
      try {
        // conert arr id to string
        let str = this.checkedDonVi.toString()
        if (str) {
          await this.getDsKetCuoiChuaPhanNV(str)
        } else {
          this.$toast.error("Bạn chưa chọn đơn vị")
          return
        }
        if (this.dsXuatFile) {
          // let exportProperties = {
          //   dataSource: this.dsKetCuoiChuaPhanNV
          // }
          // this.$refs.treegrid.excelExport(exportProperties)
          this.dsThuocTinh = []
          for (let key in this.dsXuatFile[0]) {
            this.dsThuocTinh.push(key)
          }
          this.$bvModal.show('modal-xuat-dulieu')
          // let dsXuatExcel = [...this.dsKetCuoiChuaPhanNV]
          // //xoa field : KETCUOI_ID,CAPGOC_ID
          // dsXuatExcel.forEach(item => {
          //   delete item.KETCUOI_ID
          //   delete item.CAPGOC_ID
          // })
          // let ds = XLSX.utils.json_to_sheet(dsXuatExcel);
          // let wb = XLSX.utils.book_new(); // make Workbook of Excel
          // XLSX.utils.book_append_sheet(wb, ds, "KETCUOI_CHUAPHAN_NV"); // sheetAName is name of Worksheet
          // // export Excel file
          // XLSX.writeFile(wb, "ds_ketcuoi_chuaphan_nv.xlsx");
        } else {
          this.$root.toastError("Danh sách không có dữ liệu để xuất!")
        }
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    async reset(){
      let str = this.checkedDonVi.toString()
      if (str === ""){
        this.$root.toastError("Chưa chọn đơn vị")
        return
      }
      await this.loadDsKetCuoiChuaPhanNV(str)
    },
    async loadDsKetCuoiChuaPhanNV (data) {
      try {
        // this.loading(true)
        await this.updateDsKetCuoiChuaPhanNVCount(data)
        // await this.getDsKetCuoiChuaPhanNV(data)
        // if (this.dsKetCuoiChuaPhanNV.length > 0) this.dsKetCuoiChuaPhanNV = this.createTreeGrid(this.dsKetCuoiChuaPhanNV)
      } catch (e){
        console.log(e)
        this.$root.toastError("Có lỗi xảy ra khi tải danh sách kết cuối")
      } finally {
        // this.loading(false)
      }
    },
    createTreeDonVi(dsDonVi) {
      let tree = []
      let donviChaArray = dsDonVi.map(item => (item['DONVI_CHA_ID']))
      donviChaArray = new Set(donviChaArray)
      donviChaArray = Array.from(donviChaArray).sort(function (a, b) {
        return a - b
      })
      let donviArray = dsDonVi.map(item => (item['DONVI_ID'])).sort(function (a, b) {
        return a - b
      })
      dsDonVi.forEach(item => {
        //check item la parent node
        if (donviChaArray.includes(item['DONVI_ID'])) {
          //check donvi_cha_id khong co trong ds donvi_id thi xoa donvi_cha_id
          if (!donviArray.includes(item['DONVI_CHA_ID'])) {
            delete item.DONVI_CHA_ID
          }
          tree.push({...item, hasChild: true, expanded: true})
        } else {
          if (item['DONVI_CHA_ID'] != null && donviArray.includes(item['DONVI_CHA_ID'])) tree.push(item)
          else {
            if (!donviArray.includes(item['DONVI_CHA_ID'])) {
              delete item.DONVI_CHA_ID
            }
            tree.push({...item, hasChild: true, expanded: true}) // DONVI_CHA_ID = null, -> item is parent node
          }
        }
      })
      return tree
    },
    createTreeGrid(list){
      let arr = []
      if (list.length == 0) return arr
      let donviCharArr = list.map(item =>item.DONVI_CHA_ID)
      let uniqueDonviChaArr = donviCharArr.filter(this.onlyUnique)
      uniqueDonviChaArr.forEach(donviChaId =>{
        let children = list.filter(item => item.DONVI_CHA_ID == donviChaId)
        let donvi_cha = this.dsDonVi.filter(item => item.DONVI_ID == donviChaId),
          tenTrungtam = donvi_cha.length > 0 ? donvi_cha[0].TEN_DV : null
        if (children.length >0){
          arr.push({TEN_DV:tenTrungtam, children:children})
        }else arr.push({TEN_DV:tenTrungtam})
      })
      return arr
    },
    getRowSpanArray(danhSach){
      this.rowSpanningArray = []
      if (danhSach.length == 0) return
      let donviIdArr = danhSach.map(item => item.DONVI_ID)
      let uniqueDonviIdArr = donviIdArr.filter(this.onlyUnique)
      uniqueDonviIdArr.forEach(donviId=>{
        let capList = danhSach.filter(item => item.DONVI_ID == donviId)
        let capListSize = capList.length
        if (capListSize > 0){
          this.rowSpanningArray.push(({DONVI_ID:donviId, SIZE:capListSize, KETCUOI_ID:capList[0].KETCUOI_ID}))
        }
      })
    },
    onlyUnique(value, index, self) {
      return self.indexOf(value) === index;
    },
    queryCellInfoEvent: function(args) {
      switch (args.column.field){
        case 'TEN_DV':
          //columns span ten trung tam
          if (args.data.level === 0){
            args.colSpan = 4 // 4 column hien thi
          }
          let item = this.rowSpanningArray.find(i => i.KETCUOI_ID == args.data.KETCUOI_ID)
          if (item) args.rowSpan = item.SIZE
          // this.rowSpanningArray.forEach(item =>{
          //   if (args.data.KETCUOI_ID === item.KETCUOI_ID){
          //     args.rowSpan = item.SIZE
          //     // }
          //   }
          // })
          //bold
          args.cell.style.fontWeight = 'bold'
          break
        case "KYHIEU":
          //bold
          args.cell.style.fontWeight = 'bold'
          break
      }
      // //chi xu ly column TENTO
      // if (args.column.field === 'TEN_DV'){
      //   //columns span ten trung tam
      //   if (args.data.level === 0){
      //     args.colSpan = 4 // 4 column hien thi
      //   }
      //   // let rows = this.$refs.treegrid.getCurrentViewRecords()
      //   // //loai bo nhung parent node
      //   // rows = rows.filter(item => item.KETCUOI_ID)
      //   // //tinh phan tu can span va span size
      //   // this.getRowSpanArray(rows)
      //   this.rowSpanningArray.forEach(item =>{
      //     if (args.data.KETCUOI_ID === item.KETCUOI_ID){
      //       args.rowSpan = item.SIZE
      //       // }
      //     }
      //   })
      // }
    },
    statusBtn(btnPhanKetCuoi, btnXuatFile){
      this.isDisableBtnPhanKetCuoi = btnPhanKetCuoi
      this.isDisableBtnXuatFile = btnXuatFile
    }
  },
  async mounted()  {
    try {
      this.$store.dispatch('setHeader', {
        title: 'Tra cứu kết cuối chưa phân nhân viên'
      })
    } catch (err) {
      console.log(err)
    }
  }
}
</script>
<style>
.tracuu-ketcuoi-chuaphan-nhanvien .e-grid.e-bothlines .e-rowcell {
  border-width: 1px 0px 1px 1px;
}
.tracuu-ketcuoi-chuaphan-nhanvien .e-control.e-numerictextbox.e-lib.e-input {
  text-align: right !important;
  padding-right: 5px !important;
}

.tracuu-ketcuoi-chuaphan-nhanvien .e-grid {
  font-family: "Roboto", Arial !important;
}

.tracuu-ketcuoi-chuaphan-nhanvien .e-grid .e-filterbarcell {
  padding: 5px 7px !important;
}

.tracuu-ketcuoi-chuaphan-nhanvien .e-grid th.e-grouptopleftcell,
.tracuu-ketcuoi-chuaphan-nhanvien .e-grid th.e-headercell,
.tracuu-ketcuoi-chuaphan-nhanvien .e-grid th.e-filterbarcell {
  background-color: #BAE7FF !important;
  border-color: white !important;
}

.tracuu-ketcuoi-chuaphan-nhanvien .e-grid th.e-filterbarcell {
  border-width: 1px 0 0 1px !important;
}

.tracuu-ketcuoi-chuaphan-nhanvien .e-grid td.e-active {
  background: #F9E1A9 !important;
}

.tracuu-ketcuoi-chuaphan-nhanvien .e-grid.e-gridhover tr[role='row']:not(.e-editedrow):hover .e-rowcell:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.tracuu-ketcuoi-chuaphan-nhanvien .e-grid.e-gridhover tr[role='row']:hover .e-detailrowcollapse:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.tracuu-ketcuoi-chuaphan-nhanvien .e-grid.e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.tracuu-ketcuoi-chuaphan-nhanvien .e-grid.e-rtl .e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.tracuu-ketcuoi-chuaphan-nhanvien .e-grid.e-gridhover tr[role='row']:hover .e-detailrowexpand:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell) {
  background-color: #FFF9EB !important;
}

.tracuu-ketcuoi-chuaphan-nhanvien .e-grid td.e-rowcell {
  line-height: 20px !important;
  padding: 5px 7px !important;
}

.tracuu-ketcuoi-chuaphan-nhanvien .e-grid .e-filterdiv.e-fltrinputdiv .e-input-group.e-control-wrapper {
  border: 0 !important;
}

.tracuu-ketcuoi-chuaphan-nhanvien .e-grid .e-filtertext.e-input.e-disable {
  display: none !important;
}

.tracuu-ketcuoi-chuaphan-nhanvien .e-grid tr.e-emptyrow td {
  text-align: center !important;
}

.tracuu-ketcuoi-chuaphan-nhanvien .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"],
.tracuu-ketcuoi-chuaphan-nhanvien .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"] > div,
.tracuu-ketcuoi-chuaphan-nhanvien .grid-phieu div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="2"],
.tracuu-ketcuoi-chuaphan-nhanvien .grid-phieu div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="2"] > div,
.tracuu-ketcuoi-chuaphan-nhanvien .grid-phieu div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="3"],
.tracuu-ketcuoi-chuaphan-nhanvien .grid-phieu div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="3"] > div {
  padding: 0 !important;
}
.tracuu-ketcuoi-chuaphan-nhanvien .e-grid.e-default tr td:first-child, .e-grid.e-default tr th.e-headercell:first-child:not(.e-firstcell), .e-grid.e-default tr th.e-detailheadercell:first-child, .e-grid.e-default tr th.e-filterbarcell:first-child {
  border-left-width: thin !important;
}
.tracuu-ketcuoi-chuaphan-nhanvien .table-result .e-gridpager.e-control.e-pager.e-lib {
  display: none;
}
div#boxLeft1 {
  min-width: 20%;
  max-width: 50%;
  width: auto;
}
.tree-area {
  height: 651px;
  overflow: auto;
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

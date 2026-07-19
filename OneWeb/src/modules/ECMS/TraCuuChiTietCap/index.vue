<template src="./index.html"></template>
<script>
import { Page,Filter, ExcelExport, Sort, Resize} from "@syncfusion/ej2-vue-treegrid";
import ModalXuatDuLieu from '../../ECMS/TraCuuSplitterTheoPortOLT/ModalXuatDuLieu'
import Pagination from '../../../components/Controls/Pagination'
import { L10n } from '@syncfusion/ej2-base'
L10n.load({
  'vi-VN': {
    treegrid: {
      EmptyRecord: 'Không có bản ghi nào để hiển thị'
    }
  }
})
var filterBarTemplate = {
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
        this.parent.filterByColumn(args.currentTarget.id, 'contains', value.trim())
      }
    })
  }
}
export default {
  components:{ModalXuatDuLieu, Pagination},
  data: function () {
    return {
      isDisableBtnXuatFile:true,
      // Paging
      pageSize:10,
      pageIndex:0,
      totalRecords:0, // Tổng số record phân trang server

      dsThuocTinh:[],
      dsXuatFile:[],
      dsChiTietCap:[],
      dsDonVi:[],
      checkedDonVi:[],
      fields: '',
      pageSettings: { pageSizes: ['10','20','50','100','All'], pageSize: 11 },
      // groupOptions: {disablePageWiseAggregates: true, columns: ['TENTRUNGTAM'] , showDropArea:false},
      rowSpanningArray:[],
      filterBarTemplate: filterBarTemplate,
      ketcuoiNguonColumns:[
        {
          field: 'KYHIEU_KCN',
          headerText: 'Ký hiệu',
          headerTextAlign:'Center',
          width: 130,
          textAlign: 'Left',
          minWidth: 10,
          filterBarTemplate:filterBarTemplate
        },
        {
          field: 'LOAI_KC_NGUON',
          headerText: 'Loại kết cuối',
          headerTextAlign:'Center',
          width: 150,
          textAlign: 'Left',
          minWidth: 10,
          filterBarTemplate:filterBarTemplate
        },
        {
          field: 'PHANLOAI_KC_NGUON',
          headerText: 'Phân loại',
          headerTextAlign:'Center',
          width: 120,
          textAlign: 'Left',
          minWidth: 10,
          filterBarTemplate:filterBarTemplate
        }
      ],
      capColumns:[
        {
          field: 'KYHIEU_CAP',
          headerText: 'Ký hiệu',
          headerTextAlign:'Center',
          width: 155,
          textAlign: 'Left',
          minWidth: 10,
          filterBarTemplate:filterBarTemplate
        },
        {
          field: 'NHOM_LOAICAP',
          headerText: 'Nhóm loại cáp',
          headerTextAlign:'Center',
          width: 140,
          textAlign: 'Left',
          minWidth: 10,
          filterBarTemplate:filterBarTemplate
        },
        {
          field: 'KIEU_DICAP',
          headerText: 'Kiểu đi cáp',
          headerTextAlign:'Center',
          width: 120,
          textAlign: 'Left',
          minWidth: 10,
          filterBarTemplate:filterBarTemplate
        },
        {
          field: 'DUNG_LUONG',
          headerText: 'Dung lượng cáp',
          headerTextAlign:'Center',
          width: 150,
          textAlign: 'Left',
          // minWidth: 10
          filterBarTemplate:filterBarTemplate
        },
        {
          field: 'NGAY_SD',
          headerText: 'Ngày SD',
          headerTextAlign:'Center',
          format: 'dMy',
          width: 100,
          textAlign: 'Right',
          minWidth: 10,
          filterBarTemplate:filterBarTemplate
        },
        {
          field: 'MA_DUAN',
          headerText: 'Mã dự án',
          headerTextAlign:'Center',
          width: 130,
          textAlign: 'Left',
          minWidth: 10,
          filterBarTemplate:filterBarTemplate
        },
        {
          field: 'MA_TS',
          headerText: 'Mã tài sản',
          headerTextAlign:'Center',
          width: 130,
          textAlign: 'Left',
          minWidth: 10,
          filterBarTemplate:filterBarTemplate
        }
      ],
      ketcuoiDichColumns:[
        {
          field: 'KYHIEU_KCD',
          headerText: 'Ký hiệu',
          headerTextAlign:'Center',
          width: 130,
          textAlign: 'Left',
          // minWidth: 10
          filterBarTemplate:filterBarTemplate
        },
        {
          field: 'LOAI_KC_DICH',
          headerText: 'Loại kết cuối',
          headerTextAlign:'Center',
          width: 130,
          textAlign: 'Left',
          minWidth: 10,
          filterBarTemplate:filterBarTemplate
        },
        {
          field: 'PHANLOAI_KC_DICH',
          headerText: 'Phân loại',
          headerTextAlign:'Center',
          width: 120,
          textAlign: 'Left',
          minWidth: 10,
          filterBarTemplate:filterBarTemplate
        },
        {
          field: 'MA_DUAN_D',
          headerText: 'Mã dự án',
          headerTextAlign:'Center',
          width: 130,
          textAlign: 'Left',
          minWidth: 10,
          filterBarTemplate:filterBarTemplate
        },
        {
          field: 'MA_TS_D',
          headerText: 'Mã tài sản',
          headerTextAlign:'Center',
          width: 135,
          textAlign: 'Left',
          minWidth: 10,
          filterBarTemplate:filterBarTemplate
        }
      ]
    }
  },
  provide: {
    treegrid: [Page, Filter, ExcelExport,Sort, Resize ],
  },
  async created() {
    try {
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
      this.pageSettings = {
        pageSizes: ['10', '20', '50', '100', 'All'],
        pageSize: value + this.dsChiTietCap.length
      } // do treegrid đếm cả parent nên + số parent để khỏi mất row
    },
    "dsChiTietCap": function (value){
      this.isDisableBtnXuatFile = value.length === 0

    }
  },
  methods:{
    // begin(args) {
    //   if (args.requestType === 'paging') {
    //     // console.log(args)
    //     // console.log(args['rows'])
    //     // this.getRowSpanArray(rows)
    //     let rows = this.$refs.grid.getCurrentViewRecords()
    //     this.getRowSpanArray(rows)
    //   }
    // },
    pagination_PageChanged: async function (args) {
      this.pageSize = args.pageSize;
      this.pageIndex = args.pageIndex;
      try {
        this.loading(true)
        await this.getDsChiTietCapPaging(this.checkedDonVi.toString().replace(/,+$/, ''), args.pageIndex, args.pageSize)
      }catch (e){}
      finally {
        this.loading(false)
      }
      // this.$emit("pageChanged", args);
    },

    btnHienThi(){
      // conert arr id to string
      let str = this.checkedDonVi.toString().replace(/,+$/, '')
      if (str){
        this.loadDsChiTietCap(str)
      }else {
        this.$toast.error("Bạn chưa chọn đơn vị")
        return
      }
    },
    btnXuatFile: async function () {
      if (this.isDisableBtnXuatFile) return
      this.loading(true);
      try {
        // conert arr id to string
        let str = this.checkedDonVi.toString()
        if (str){
          // await this.getDsChiTietCap(str)
          const baseUrl = process.env.API;
          const filename = 'ChiTietCap.xlsx', sheetname = 'DS_KC'
          await this.axios({
            url: `${baseUrl}/web-ecms/tracuu/chitiet-cap/excelExport?dsToQuanLyId=${str}&filename=${filename}&sheetname=${sheetname}`,
            method: "GET",
            responseType: "blob",
          }).then((response) => {
            console.log(response)
            const url = URL.createObjectURL(new Blob([response.data], {
              type: 'application/vnd.ms-excel'
            }))
            const link = document.createElement('a')
            link.href = url
            link.setAttribute('download', filename)
            document.body.appendChild(link)
            link.click()
          })
        }else {
          this.$toast.error("Bạn chưa chọn đơn vị")
          // return
        }
        // if (this.dsXuatFile) {
        //   // let exportProperties = {
        //   //   dataSource: this.dsChiTietCap
        //   // }
        //   // this.$refs.treegrid.excelExport(exportProperties)
        //   this.dsThuocTinh = []
        //   for (let key in this.dsXuatFile[0]) {
        //     this.dsThuocTinh.push(key)
        //   }
        //   this.$bvModal.show('modal-xuat-dulieu')
        // } else {
        //   this.$toast.warning("Danh sách không có dữ liệu để xuất!")
        // }
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
    nodeChecked(treeObj){
      var treeObj = document.getElementById('treeview').ej2_instances[0];
      this.checkedDonVi = treeObj.checkedNodes
      // if (treeObj.action === "check"){
      //   let CheckedObjs = treeObj.data.filter(item => item['isChecked'] === "true")
      //   CheckedObjs.forEach(item => this.checkedDonVi.push(item.id))
      // }else if (treeObj.action === "uncheck"){
      //   let CheckedObjs = treeObj.data.filter(item => item['isChecked'] === "false") // tránh trường hợp nhánh cha isChecked = mixed
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
        //case item is parent node
        if (donviChaArray.includes(item['DONVI_ID'])) {
          //check donvi_cha_id khong co trong ds donvi_id thi xoa donvi_cha_id (case both child, parent)
          if (!donviArray.includes(item['DONVI_CHA_ID'])) {
            delete item.DONVI_CHA_ID
          }
          tree.push({...item, hasChild: true, expanded: true})
        } else {
          // node is only child
          if (item['DONVI_CHA_ID'] != null && donviArray.includes(item['DONVI_CHA_ID'])) tree.push(item)
          else {
            //case both child, parent
            if (!donviArray.includes(item['DONVI_CHA_ID'])) {
              delete item.DONVI_CHA_ID
            }
            tree.push({...item, hasChild: true, expanded: true}) // DONVI_CHA_ID = null, -> item is parent node
          }
        }
      })
      return tree
    },
    async loadDsChiTietCap (data) {
      try {
        // this.loading(true)
        // await this.getDsChiTietCap(data)
        await this.updateDsChiTietCapCount(data)
        // await this.getDsChiTietCapPaging(data, this.pageIndex, this.pageSize)
      } catch (e){
        if (e)
          this.$toast.error(e.response.message_detail)
      } finally {
        // this.loading(false)
      }
    },
    queryCellInfoEvent: function(args) {
      if (!!args.column.field) {
        if (args.column.field === "TENTO"){
          // case lv0 trung tam, tento
          args.cell.title = args.data.TENTO
        }else args.cell.title = args.cell.innerHTML
      }
      switch (args.column.field){
        case 'TENTO':
          //columns span ten trung tam
          if (args.data.level === 0){
            args.colSpan = 16 // 16 column hien thi
          }
          //bold
          args.cell.style.fontWeight = 'bold'
          // let rows = this.$refs.treegrid.getCurrentViewRecords()
          // //loai bo nhung parent node
          // rows = rows.filter(item=> item.CAP_ID)
          //tinh phan tu can span va span size
          // this.getRowSpanArray(rows)

          this.rowSpanningArray.forEach(item =>{
            if (args.data.CAP_ID === item.CAP_ID){
              // if (args.column.field === 'TENTO' && args.data.DONVI_ID === item.DONVI_ID) {
              args.rowSpan = item.SIZE
              // }
            }
          })
        case 'KYHIEU_KCN':
          //bold
          args.cell.style.fontWeight = 'bold'
        case 'KYHIEU_CAP':
          //bold
          args.cell.style.fontWeight = 'bold'
        case 'KYHIEU_KCD':
          //bold
          args.cell.style.fontWeight = 'bold'
      }

    },
    getDsChiTietCap: async function (danhSachToQuanLyId){
      await this.$root.context.get(
        "/web-ecms/tracuu/ChiTietCapTheoToQuanLy",{dsToQuanLyId:danhSachToQuanLyId}
      ).then(res =>{
        this.dsXuatFile = res.data
        if (res.data.length === 0) this.$root.toastError("Không có dữ liệu, vui lòng thử lại.")
      }).catch(e=> {throw e})
    },
    getDsChiTietCapPaging: async function (danhSachToQuanLyId, pageIndex, pageSize){
      await this.$root.context.post(
        `/web-ecms/tracuu/ChiTietCapTheoToQuanLyPaging?dsToQuanLyId=${danhSachToQuanLyId}`,
        {
          pageIndex: pageIndex,
          pageSize: pageSize,
        }
      ).then(res =>{
        if (this.totalRecords != res.data.allItems) {
          this.totalRecords = res.data.allItems
          return
        }
        this.dsChiTietCap = res.data.pageItems
        this.dsChiTietCap = this.createTreeGrid(this.dsChiTietCap)
        this.getRowSpanArray(res.data.pageItems)
        this.dsXuatFile = res.data.pageItems
        if (res.data.pageItems.length === 0) this.$root.toastError("Không có dữ liệu, vui lòng thử lại.")
      }).catch(e=> {throw e})
    },
    updateDsChiTietCapCount: async function (danhSachToQuanLyId) {
      try {
        this.loading(true)
        await this.$root.context.post(
          `/web-ecms/tracuu/ChiTietCapTheoToQuanLyPaging?dsToQuanLyId=${danhSachToQuanLyId}`,
          {
            pageIndex: 1,
            pageSize: 1,
          }).then(res =>{
          if (this.totalRecords == res.data.allItems) this.$refs.pagination.reloadCurrentPage()
          this.totalRecords = res.data.allItems > 0 ? res.data.allItems : 0;
          if (this.totalRecords == 0) {
            this.loading(false)
            this.dsChiTietCap = []
            this.$root.toastError("Không có dữ liệu, vui lòng thử lại.")
          }
        })
      }catch (e) {
        this.loading(false)
        throw  e
      }
    },
    getDsDonVi: async function () {
      var rs = await this.$root.context.get(
        "/web-cabman/tracuu/lay-ds-donvi"
      );
      this.dsDonVi = rs.data;
    },
    getRowSpanArray(danhSach){
      this.rowSpanningArray = []
      let donviIdArr = danhSach.map(item => item.DONVI_ID)
      let uniqueDonviIdArr = donviIdArr.filter(this.onlyUnique)
      uniqueDonviIdArr.forEach(donviId=>{
        let capList = danhSach.filter(item => item.DONVI_ID === donviId)
        let capListSize = capList.length
        if (capListSize > 0){
          this.rowSpanningArray.push(({DONVI_ID:donviId, SIZE:capListSize, CAP_ID:capList[0].CAP_ID}))
        }
      })
    },
    onlyUnique(value, index, self) {
      return self.indexOf(value) === index;
    },
    createTreeGrid(list){
      let arr = []
      let donviCharArr = list.map(item =>item.DONVI_CHA_ID)
      let uniqueDonviChaArr = donviCharArr.filter(this.onlyUnique)
      uniqueDonviChaArr.forEach(donviChaId =>{
        let children = list.filter(item => item.DONVI_CHA_ID === donviChaId)
        let tenTrungtam = this.dsDonVi.filter(item => item.DONVI_ID === donviChaId)[0].TEN_DV
        if (children.length >0){
          arr.push({TENTO:tenTrungtam, children:children})
        }else arr.push({TENTO:tenTrungtam})
      })
      return arr
    }
  },
  async mounted()  {
    try {
      this.$store.dispatch('setHeader', {
        title: 'Tra cứu chi tiết cáp'
      })
    } catch (e){
      console.log(e)
    }
  }
}
</script>
<style>
.tracuu-chitiet-cap .e-control.e-numerictextbox.e-lib.e-input {
  text-align: right !important;
  padding-right: 5px !important;
}

.tracuu-chitiet-cap .e-grid {
  font-family: "Roboto", Arial !important;
}

.tracuu-chitiet-cap .e-grid .e-filterbarcell {
  padding: 5px 7px !important;
}

.tracuu-chitiet-cap .e-grid th.e-grouptopleftcell,
.tracuu-chitiet-cap .e-grid th.e-headercell,
.tracuu-chitiet-cap .e-grid th.e-filterbarcell {
  background-color: #BAE7FF !important;
  border-color: white !important;
}

.tracuu-chitiet-cap .e-grid th.e-filterbarcell {
  border-width: 1px 0 0 1px !important;
}

.tracuu-chitiet-cap .e-grid td.e-active {
  background: #F9E1A9 !important;
}

.tracuu-chitiet-cap .e-grid.e-gridhover tr[role='row']:not(.e-editedrow):hover .e-rowcell:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.tracuu-chitiet-cap .e-grid.e-gridhover tr[role='row']:hover .e-detailrowcollapse:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.tracuu-chitiet-cap .e-grid.e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.tracuu-chitiet-cap .e-grid.e-rtl .e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.tracuu-chitiet-cap .e-grid.e-gridhover tr[role='row']:hover .e-detailrowexpand:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell) {
  background-color: #FFF9EB !important;
}

.tracuu-chitiet-cap .e-grid td.e-rowcell {
  line-height: 20px !important;
  padding: 5px 7px !important;
}

.tracuu-chitiet-cap .e-grid .e-filterdiv.e-fltrinputdiv .e-input-group.e-control-wrapper {
  border: 0 !important;
}

.tracuu-chitiet-cap .e-grid .e-filtertext.e-input.e-disable {
  display: none !important;
}

.tracuu-chitiet-cap .e-grid tr.e-emptyrow td {
  text-align: center !important;
}

.tracuu-chitiet-cap .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"],
.tracuu-chitiet-cap .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"] > div,
.tracuu-chitiet-cap .grid-phieu div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="2"],
.tracuu-chitiet-cap .grid-phieu div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="2"] > div,
.tracuu-chitiet-cap .grid-phieu div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="3"],
.tracuu-chitiet-cap .grid-phieu div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="3"] > div {
  padding: 0 !important;
}

.tracuu-chitiet-cap .e-grid.e-default tr td:first-child, .e-grid.e-default tr th.e-headercell:first-child:not(.e-firstcell), .e-grid.e-default tr th.e-detailheadercell:first-child, .e-grid.e-default tr th.e-filterbarcell:first-child {
  border-left-width: thin !important;
}
.tracuu-chitiet-cap .table-result .e-gridpager.e-control.e-pager.e-lib {
  display: none;
}
.tree-area {
  height: 610px;
  overflow: auto;
}
.tracuu-chitiet-cap li#treeview_active .e-checkboxfilter .e-checkbox-wrapper .e-frame.e-check, .e-css.e-checkbox-wrapper span.e-frame.e-icons.e-check {
  background-color: white !important;
  padding-top: 0px !important;
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

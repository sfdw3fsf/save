<template src="./StaffStock.html"> </template>

<script>
import gridview from '@/components/Shared/gridview'
import treemenu from './treemenu'
// import Vue from "vue";
import API from './API'
import gridcheckbox from './gridcheckbox.vue'
import { L10n } from '@syncfusion/ej2-base'
import { GridPlugin, Page, Filter, GridComponent, Sort, Grid, Aggregate, Edit, Resize, Group, CommandColumn, ExcelExport, Freeze, Toolbar } from '@syncfusion/ej2-vue-grids'
import TableWithSearchVue from '../../../QLTN/components/TableWithSearch.vue'
// import { TreeViewPlugin } from "@syncfusion/ej2-vue-navigations";
// Vue.use(TreeViewPlugin);

L10n.load({
  'vi-VN': {
    grid: {
      EmptyRecord: 'Không có bản ghi nào để hiển thị ',
      Item: 'Bản ghi',
      Items: 'Bản ghi',
      FilterbarTitle: ''
    }
  }
})

export default {
  provide: {
    grid: [Sort, Filter, Resize, Group, Freeze, CommandColumn, ExcelExport, Edit, Page, Aggregate, Toolbar]
  },
  components: { gridview, treemenu, gridcheckbox, L10n, GridPlugin, Page, Sort, Filter, GridComponent, Grid, Aggregate, Edit },
  name: 'StaffStock',

  data() {
    return {
      show_btn: false,
      checkedNodes: null,
      ma_nd: '',
      ma_nv: '',
      ten_nv: '',
      keywordSearchUnit: '',
      khochuagan_search: '',
      pageSettings: { pageSize: 10 },

      fields: {
        dataSource: null,
        id: 'donvi_id',
        parentID: 'donvi_cha_id',
        text: 'ten_dv',
        hasChildren: 'hasChild'
      },
      tree_chuagan: {
        dataSource: null,
        id: 'kho_id',
        parentID: 'kho_cha_id',
        text: 'ten_kho',
        hasChildren: 'hasChild'
      },
      checkedKho: [],
      listbox: {
        tree_donvi: {
          list: {},
          checked: ''
        },
        ds_nhanvien: {
          list: [],
          value: '',
          header: [
            { fieldName: 'STT', headerText: 'STT', width: 90 },
            { fieldName: 'ma_nv', headerText: 'Mã nhân viên' },
            { fieldName: 'ten_nv', headerText: 'Tên nhân viên' },
            { fieldName: 'ma_dv', headerText: 'Mã đơn vị' },
            { fieldName: 'chucdanh', headerText: 'Chức danh' },
            { fieldName: 'vitri', headerText: 'Vị trí' },
            { fieldName: 'so_dt', headerText: 'Điện thoại' },
            { fieldName: 'diachi_nv', headerText: 'Địa chỉ' }
          ],
          isEnabled: false
        },

        tree_dagan: {
          list: [],
          value: '',
          header: [{ fieldName: 'ten_kho', headerText: 'Tên kho' }]
        },
        quyen_xacnhan: {
          list: [],
          value: '',
          header: [
            { fieldName: 'ten_kho', headerText: 'Kho' },
            { fieldName: 'ten_dv', headerText: 'Đơn vị' },
            { fieldName: 'quyen_kg', headerText: 'Quyền gửi' },
            { fieldName: 'quyen_kn', headerText: 'Quyền nhận' }
          ],
          isEnabled: false,
          data_update: []
        }
      },
      //checkedkhochuagan:[]
      templateQuyenGui: {
        create: function(args) {
          const dd = document.createElement('div')
          dd.id = 'quyen_gui'
          dd.className = 'justify-center'
          return dd
        },
        write: function(args) {
          const input = document.createElement('input')
          input.id = 'checkbox_quyen_gui'
          input.type = 'checkbox'
          input.style.width = '20px'
          input.addEventListener('change', (e) => {
            event.currentTarget.checked ? document.getElementById('chonTatCaQuyenGui').click() : document.getElementById('boChonTatCaQuyenGui').click()
          })
          document.getElementById('quyen_gui').appendChild(input)
        }
      },
      templateQuyenNhan: {
        create: function(args) {
          const dd = document.createElement('div')
          dd.id = 'quyen_nhan'
          dd.className = 'justify-center'
          return dd
        },
        write: function(args) {
          const input = document.createElement('input')
          input.id = 'checkbox_quyen_nhan'
          input.type = 'checkbox'
          input.style.width = '20px'
          input.addEventListener('change', (e) => {
            event.currentTarget.checked ? document.getElementById('chonTatCaQuyenNhan').click() : document.getElementById('boChonTatCaQuyenNhan').click()
          })
          document.getElementById('quyen_nhan').appendChild(input)
        }
      },
      checkedNodes: []
    }
  },

  mounted() {
    //console.log('created()');
    this.loading(true)
    API.tree_donvi(this.axios, null).then((response) => {
      if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000')
        // var treeData = response.data.data;
        // this.fields.dataSource = treeData.sort((a,b) => a.ten_dv.localeCompare(b.ten_dv))
        this.fields = {
          dataSource: this.covertDatasource(response.data.data),
          id: 'donvi_id',
          parentID: 'donvi_cha_id',
          text: 'ten_dv',
          hasChildren: 'hasChild',
          selected: 'isSelected'
        }

      // console.log(this.fields);

      this.loading(false)
    })
  },

  methods: {
    search: async function(v_kieu, v_key) {
      this.loading(true)
      var data = {
        kieu: v_kieu,
        key: v_key
      }
      var donvi = ''
      var search_data = {}
      let check_ = false
      await API.sp_loc_ma_nd_nhanvien_gankhonhanvien(this.axios, data).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          donvi = response.data.data[0].donvi_id
          search_data = response.data.data[0]
          check_ = true
        } else {
          if (v_kieu == 0) this.$toast.error('Người dùng với mã ' + v_key + ' không tồn tại')
          else if (v_kieu == 1) this.$toast.error('Nhân viên với mã ' + v_key + ' không tồn tại')
          else if (v_kieu == 2) this.$toast.error('Nhân viên với tên ' + v_key + ' không tồn tại')
          check_ = false
        }
      })

      if (!check_) {
        this.loading(false)
        return
      }

      check_ = false

      //tim don vi va expand tren cay don vi
      await API.tree_donvi(this.axios, null).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000')
          if (!response.data.data.find((x) => x.donvi_id == donvi)) {
            this.$toast.error('Không tìm thấy đơn vị của nhân viên, hãy nhấn nút Làm mới và thử lại')
            check_ = false
            return
          }

        check_ = true
        this.fields = {
          dataSource: this.covertDatasource_search(response.data.data, donvi),
          id: 'donvi_id',
          parentID: 'donvi_cha_id',
          text: 'ten_dv',
          hasChildren: 'hasChild',
          selected: 'isSelected'
        }
      })

      if (!check_) {
        this.loading(false)
        return
      }

      //tim nhan vien tren datagrid va load kho
      var data = {
        p_pagesize: 5000,
        p_donvi_id: donvi,
        p_pagenumber: 1
      }
      await API.grv_nhanvien(this.axios, data).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          this.listbox.ds_nhanvien.list = response.data.data
          for (var i in this.listbox.ds_nhanvien.list) this.listbox.ds_nhanvien.list[i].STT = (parseInt(i) + 1).toString()
        } else {
          this.listbox.ds_nhanvien.list = []
        }
      })

      this.listbox.tree_dagan.list = []
      this.listbox.quyen_xacnhan.list = []
      this.tree_chuagan = {
        dataSource: null,
        id: 'kho_id',
        parentID: 'kho_cha_id',
        text: 'ten_kho',
        hasChildren: 'hasChild'
      }

      let nhanvien = v_kieu == 0 ? this.listbox.ds_nhanvien.list.find((x) => x.ma_nv.toLowerCase() == search_data.ma_nv.toLowerCase()) : this.listbox.ds_nhanvien.list.find((x) => x.ma_nv == v_key)
      console.log(nhanvien)
      if (nhanvien == undefined) {
        console.log('nhanvien')
        this.loading(false)
        return
      }
      this.listbox.ds_nhanvien.list.sort(function(x, y) {
        return x == nhanvien ? -1 : y == nhanvien ? 1 : 0
      })

      setTimeout(() => {
        this.$refs.dgvNhanVien.setCurrentSelectedRow(0)
        this.loading(false)
      }, 1000)
    },

    test() {
      console.log(this.selectedNodessss)
    },

    covertDatasource_search(dataSource, search_id) {
      var list_parent = []
      this.find_parent(dataSource, search_id, list_parent)

      let donViChaList = []
      dataSource.forEach((a) => {
        if (a.donvi_cha_id) {
          donViChaList.push(a.donvi_cha_id)
        }
      })
      dataSource.forEach((a) => {
        if (donViChaList.includes(a.donvi_id)) {
          a.hasChild = true
        }

        a.isSelected = false
        if (a.donvi_id == search_id) a.isSelected = true
        if (list_parent.includes(a.donvi_id)) a.expanded = true
      })
      return dataSource
    },
    find_parent(dataSource, search_id, result) {
      let pr_id = ''
      dataSource.forEach((a) => {
        if (a.donvi_id == search_id) {
          if (a.donvi_cha_id == null || a.donvi_cha_id == '' || a.donvi_cha_id == undefined) result.push(-1)
          else {
            pr_id = a.donvi_cha_id
            result.push(a.donvi_cha_id)
          }
        }
      })

      if (!result.includes(-1)) this.find_parent(dataSource, pr_id, result)

      return result
    },

    covertDatasource(dataSource) {
      let donViChaList = []
      dataSource.forEach((a) => {
        if (a.donvi_cha_id) {
          donViChaList.push(a.donvi_cha_id)
        }
      })
      dataSource.forEach((a) => {
        if (donViChaList.includes(a.donvi_id)) {
          a.hasChild = true
        }

        a.isSelected = false
        // if (a.donvi_id == 292847)
        //    a.isSelected = true;
        // if (a.donvi_id == 292845)
        //    a.expanded = true;
      })
      return dataSource
    },

    covertDatasource_kho(dataSource) {
      let donViChaList = []
      dataSource.forEach((a) => {
        if (a.kho_cha_id) {
          donViChaList.push(a.kho_cha_id)
        }
      })
      dataSource.forEach((a) => {
        if (donViChaList.includes(a.kho_id)) {
          a.hasChild = true
        }

        if (a.kho_cha_id == null) a.hasChild = true
      })
      return dataSource
    },

    createTreeDonVi(dsDonVi) {
      let tree = []
      let donviChaArray = dsDonVi.map((item) => item['donvi_cha_id'])
      let donviArray = dsDonVi.map((item) => item['donvi_id'])
      dsDonVi.forEach((item) => {
        //check item la parent node
        if (donviChaArray.includes(item['donvi_id'])) {
          //check donvi_cha_id khong co trong ds donvi_id thi xoa donvi_cha_id
          if (!donviArray.includes(item['donvi_cha_id'])) {
            delete item.donvi_cha_id
          }
          tree.push({ ...item, hasChild: true, expanded: false })
        } else {
          tree.push(item)
        }
      })
      return tree
    },

    createTreeKho(dsDonvi) {
      let tree = []
      let vattuChaArray = dsDonvi.map((item) => item['kho_cha_id'])
      let vattuArray = dsDonvi.map((item) => item['kho_id'])
      dsDonvi.forEach((item) => {
        //check item la parent node
        if (vattuChaArray.includes(item['kho_id'])) {
          //check vattu_cha_id khong co trong ds vattu_id thi xoa vattu_cha_id
          if (!vattuArray.includes(item['kho_cha_id'])) {
            delete item.kho_cha_id
          }
          tree.push({ ...item, hasChild: true, expanded: false })
        } else {
          tree.push({ ...item, hasChild: false, expanded: false })
        }
      })
      return tree
    },

    tree_menu_donvi_cbClicked(node) {
      this.loading(true)
      // this.TreeCBUpdated(this.listbox.tree_donvi.list,id);
      this.listbox.tree_donvi.checked++

      var data = {
        p_pagesize: 5000,
        p_donvi_id: node.nodeData.id,
        p_pagenumber: 1
      }
      API.grv_nhanvien(this.axios, data).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          this.listbox.ds_nhanvien.list = response.data.data
          for (var i in this.listbox.ds_nhanvien.list) this.listbox.ds_nhanvien.list[i].STT = (parseInt(i) + 1).toString()

          //this.$refs.dgvNhanVien.setCurrentSelectedRow(1);
        } else {
          this.listbox.ds_nhanvien.list = []
        }
        this.loading(false)
      })
      this.listbox.tree_dagan.list = []
      this.listbox.quyen_xacnhan.list = []
      this.tree_chuagan = {
        dataSource: null,
        id: 'kho_id',
        parentID: 'kho_cha_id',
        text: 'ten_kho',
        hasChildren: 'hasChild'
      }
      // console.log('id');
    },

    grd_nhanvien_Changed(obj) {
      if (obj) {
        // console.log(obj);

        this.listbox.ds_nhanvien.value = obj
        this.loading(true)

        //load ds kho da gan
        var data = {
          p_pagesize: 5000,
          p_nhanvien_id: obj.nhanvien_id,
          //"p_nhanvien_id": 3945,
          p_pagenumber: 1
        }

        API.kho_dagan(this.axios, data).then((response) => {
          if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            this.listbox.tree_dagan.list = response.data.data
          } else {
            this.listbox.tree_dagan.list = []
          }
        })

        //load ds kho chua gan
        API.kho_chuagan(this.axios, data).then((response) => {
          if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            response.data.data.forEach(function(element) {
              element.tooltip = 'Expand tree trước khi chọn để chọn các kho con'
            })
            this.tree_chuagan = {
              dataSource: this.covertDatasource_kho(response.data.data),
              id: 'kho_id',
              parentID: 'kho_cha_id',
              text: 'ten_kho',
              hasChildren: 'hasChild'
            }

            // console.log(this.tree_chuagan);
          } else {
            this.tree_chuagan = {
              dataSource: null,
              id: 'kho_id',
              parentID: 'kho_cha_id',
              text: 'ten_kho',
              hasChildren: 'hasChild'
            }
          }
        })

        //load ds quyen xac nhan
        API.quyen_xacnhan(this.axios, data).then((response) => {
          if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            var dsdoituong = response.data.data
            dsdoituong.forEach((item) => {
              item.quyen_kg = item.quyen_kg == 1 ? true : false
              item.quyen_kn = item.quyen_kn == 1 ? true : false
            })
            //console.log(dsdoituong);
            this.listbox.quyen_xacnhan.list = dsdoituong
          } else this.listbox.quyen_xacnhan.list = []

          this.loading(false)
        })
      }
    },

    btn_ghilai() {
      if (confirm('Bạn muốn ghi lại?')) {
        this.loading(true)
        var data = []

        this.listbox.quyen_xacnhan.list.forEach((element) => {
          var item = {
            QUYEN_KN: element.quyen_kn == true ? 1 : 0,
            NHANVIEN_ID: this.listbox.ds_nhanvien.value.nhanvien_id,
            QUYEN_KG: element.quyen_kg == true ? 1 : 0,
            KHO_ID: element.kho_id
          }
          data.push(item)
        })

        var post_data = {
          p_json_quyenxn: data
        }

        // console.log(data);

        API.upd_quyexn(this.axios, post_data).then((response) => {
          this.loading(false)
          this.$toast.success(response.data.message)
        })
      }
    },

    btn_gokho() {
      this.$confirm('Bạn muốn gỡ kho ?', 'Thông báo', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không'
      }).then(async () => {
        this.loading(true)
        //var dsdoituong =  this.listbox.tree_dagan.list.filter(x=>x.ischecked);
        var grid_dagan = this.$refs.DsDaGan

        var dsdoituong = grid_dagan.getSelectedRecords()
        var data = {
          p_kho_id: ',' + dsdoituong.map((e) => e.kho_id).join(','),
          p_nhanvien_id: this.listbox.ds_nhanvien.value.nhanvien_id
        }
        // console.log(data);

        API.gokho_nhanvien(this.axios, data).then((response) => {
          if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            this.$toast.success('Gỡ kho thành công!')
            this.grd_nhanvien_Changed(this.listbox.ds_nhanvien.value)
            this.loading(false)
          } else {
            this.$toast.error(response.data.message)
            //this.grd_nhanvien_Changed([this.listbox.ds_nhanvien.value]);
            this.loading(false)
          }
        })
      })
    },

    btn_gankho() {
      this.$confirm('Bạn muốn gán kho ?', 'Thông báo', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không'
      }).then(async () => {
        this.loading(true)

        var data = {
          p_kho_id: ',' + this.checkedKho.join(','),
          p_nhanvien_id: this.listbox.ds_nhanvien.value.nhanvien_id
        }

        // console.log(data);

        API.gankho_nhanvien(this.axios, data).then((response) => {
          if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            this.$toast.success('gán kho thành công!')
            this.checkedKho = []
            this.grd_nhanvien_Changed(this.listbox.ds_nhanvien.value)
            this.loading(false)
          } else {
            this.$toast.error(response.data.message)
            //this.grd_nhanvien_Changed([this.listbox.ds_nhanvien.value]);
            this.loading(false)
          }
        })
      })
    },

    updatedata_xacnhan(data_update) {
      this.listbox.quyen_xacnhan.data_update = data_update
    },

    display_btn(is_show) {
      this.show_btn = is_show
    },

    refresh() {
      //this.listbox.tree_donvi.list = [];
      this.listbox.ds_nhanvien.list = []
      this.listbox.tree_dagan.list = []
      this.listbox.quyen_xacnhan.list = []
    },

    selectedNodes: function(args) {
      var treeObj = document.getElementById('treeviewchuagan').ej2_instances[0]
      this.checkedKho = treeObj.checkedNodes
    },

    // selectedNodes(treeObj) {
    //   // if (treeObj.action === "check"){
    //   //   let CheckedObjs = treeObj.data.filter(item => item['isChecked'] === "true")
    //   //   CheckedObjs.forEach(item => this.checkedKho.push(item.id))
    //   // }else if (treeObj.action === "uncheck"){
    //   //   let CheckedObjs = treeObj.data.filter(item => item['isChecked'] === "false")
    //   //   CheckedObjs.forEach(item => {
    //   //     let index = this.checkedKho.findIndex((element) => element === item.id)
    //   //     if (index != -1) //index = -1 khong co element trong arr
    //   //       this.checkedKho.splice(this.checkedKho.findIndex((element) => element === item.id),1)
    //   //   })

    //   // }
    //   // let check = treeObj.data.filter(item => item['isChecked'] === "true")
    //   // let uncheck = treeObj.data.filter(item => item['isChecked'] === "false")

    //   //this.checkedKho
    //   console.log(treeObj, this.checkedNodes, treeObj.checkedNodes)
    //   if (treeObj.action === 'check') {
    //     let CheckedObjs = treeObj.data.filter((item) => item['isChecked'] === 'true')
    //     CheckedObjs.forEach((item) => this.checkedKho.push(item.id))
    //   } else if (treeObj.action === 'uncheck') {
    //     let CheckedObjs = treeObj.data.filter((item) => item['isChecked'] === 'false')
    //     // console.log(treeObj);
    //     // var treeview = document.getElementById('treeviewchuagan').ej2_instances[0];
    //     // var checkedNode = [CheckedObjs[0].parentID.toString()];
    //     //       console.log(checkedNode);
    //     // treeview.checkAll(['8846']);

    //     CheckedObjs.forEach((item) => {
    //       let index = this.checkedKho.findIndex((element) => element === item.id)
    //       if (index != -1)
    //         //index = -1 khong co element trong arr
    //         this.checkedKho.splice(
    //           this.checkedKho.findIndex((element) => element === item.id),
    //           1
    //         )
    //     })
    //   }

    //   //  console.log(treeObj.action)
    //   //  console.log(this.checkedKho)
    // },

    searchUnitTreeView: async function() {
      this.loading(true)
      var donvi_search_id = 0

      this.fields.dataSource.forEach((item) => {
        if (item.ten_dv.toLowerCase().includes(this.keywordSearchUnit.trim().toLowerCase())) {
          donvi_search_id = item.id
          return
        }
      })
      if (donvi_search_id == 0) {
        this.$toast.error(`Không tìm thấy đơn vị: ${this.keywordSearchUnit}`)
        this.loading(false)
        return
      }

      await API.tree_donvi(this.axios, null).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000')
          this.fields = {
            dataSource: this.covertDatasource_search(response.data.data, donvi_search_id),
            id: 'donvi_id',
            parentID: 'donvi_cha_id',
            text: 'ten_dv',
            hasChildren: 'hasChild',
            selected: 'isSelected'
          }
      })

      var data = {
        p_pagesize: 5000,
        p_donvi_id: donvi_search_id,
        p_pagenumber: 1
      }
      await API.grv_nhanvien(this.axios, data).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          this.listbox.ds_nhanvien.list = response.data.data
          for (var i in this.listbox.ds_nhanvien.list) this.listbox.ds_nhanvien.list[i].STT = (parseInt(i) + 1).toString()
        } else {
          this.listbox.ds_nhanvien.list = []
        }
      })

      this.listbox.tree_dagan.list = []
      this.listbox.quyen_xacnhan.list = []
      this.tree_chuagan = {
        dataSource: null,
        id: 'kho_id',
        parentID: 'kho_cha_id',
        text: 'ten_kho',
        hasChildren: 'hasChild'
      }

      this.loading(false)
    },

    searchkhochuagan: async function() {
      this.loading(true)

      var kho_search_id = 0

      if (this.tree_chuagan.dataSource && this.tree_chuagan.dataSource.length > 0) {
        this.tree_chuagan.dataSource.forEach((item) => {
          if (item.ten_kho.toLowerCase().includes(this.khochuagan_search.trim().toLowerCase())) {
            kho_search_id = item.kho_id
            return
          }
        })
      }      

      if (kho_search_id == 0) {
        this.$toast.error(`Không tìm thấy kho: ${this.khochuagan_search}`)
        this.loading(false)
        return
      }

      var data = {
        p_pagesize: 5000,
        p_nhanvien_id: this.listbox.ds_nhanvien.value.nhanvien_id,
        //"p_nhanvien_id": 3945,
        p_pagenumber: 1
      }

      await API.kho_chuagan(this.axios, data).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          response.data.data.forEach(function(element) {
            element.tooltip = 'Expand tree trước khi chọn để chọn các kho con'
          })
          this.tree_chuagan = {
            dataSource: this.covertDatasource_kho_search(response.data.data, kho_search_id),
            id: 'kho_id',
            parentID: 'kho_cha_id',
            text: 'ten_kho',
            selected: 'isSelected',
            hasChildren: 'hasChild'
          }
        } else {
          this.tree_chuagan = {
            dataSource: null,
            id: 'kho_id',
            parentID: 'kho_cha_id',
            text: 'ten_kho',
            selected: 'isSelected',
            hasChildren: 'hasChild'
          }
        }
      })

      this.loading(false)
    },

    covertDatasource_kho_search(dataSource, search_id) {
      var list_parent = []
      this.find_parent_kho(dataSource, search_id, list_parent)

      let donViChaList = []
      dataSource.forEach((a) => {
        if (a.kho_cha_id) {
          donViChaList.push(a.kho_cha_id)
        }
      })
      dataSource.forEach((a) => {
        if (donViChaList.includes(a.kho_id)) {
          a.hasChild = true
        }

        a.isSelected = false
        if (a.kho_id == search_id) a.isSelected = true
        if (list_parent.includes(a.kho_id)) a.expanded = true
      })
      return dataSource
    },

    find_parent_kho(dataSource, search_id, result) {
      let pr_id = ''
      dataSource.forEach((a) => {
        if (a.kho_id == search_id) {
          if (a.kho_cha_id == null || a.kho_cha_id == '' || a.kho_cha_id == undefined) result.push(-1)
          else {
            pr_id = a.kho_cha_id
            result.push(a.kho_cha_id)
          }
        }
      })

      if (!result.includes(-1)) this.find_parent_kho(dataSource, pr_id, result)

      return result
    },

    capNhatTatCaQuyenGui(isCheck) {
      this.listbox.quyen_xacnhan.list = this.listbox.quyen_xacnhan.list.map((o) => ({ ...o, quyen_kg: isCheck ? '1' : '0' }))
    },

    capNhatTatCaQuyenNhan(isCheck) {
      this.listbox.quyen_xacnhan.list = this.listbox.quyen_xacnhan.list.map((o) => ({ ...o, quyen_kn: isCheck ? '1' : '0' }))
    },

    onRowClick: function() {
      this.$refs.grid.ej2Instances.element.addEventListener('mouseup', function(e) {
        if (e.target.classList.contains("e-rowcell")) {
          if (this.$refs.grid.ej2Instances.isEdit) {
            this.$refs.grid.ej2Instances.endEdit();
            return
          }
            
          let rowInfo = this.$refs.grid.ej2Instances.getRowInfo(e.target);
          this.$refs.grid.ej2Instances.selectRow(rowInfo.rowIndex);
          this.$refs.grid.ej2Instances.startEdit();
        }
      }.bind(this));
    },
  }
}
</script>
<style>
.justify-center {
  display: flex;
  justify-content: center;
}
</style>

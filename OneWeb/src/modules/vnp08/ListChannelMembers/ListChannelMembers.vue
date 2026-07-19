<template src="./ListChannelMembers.html"></template>
<style src="./ListChannelMembers.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import DatePicker from 'vue2-datepicker'
import VueFlatPickr from 'vue-flatpickr-component'
import API from './API'
import Vue from 'vue'
import moment from 'moment'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
import { getDanhSachViTriTheoNhanVien } from '../../admin/category/Staff/api'

export default {
  components: {breadcrumb,VueFlatPickr,API , DatePicker},
  name: 'ListChannelMembers',
  mounted () {
    this.getListHangkenh()
    this.displayTreeView()
  },
  data () {
    return {
      selectedNodes: [0, 1],
      expandedNodes: [0],
      header: {
        title: 'Danh mục thành viên kênh',
        list: [
          { name: 'vnp08', link: { name: 'Ui.cards' } },
          {
            name: 'Danh mục thành viên kênh',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      settingSelect2: {
        language: 'vi'
      },
      listTable : {
        thanh_vien_kenh_table : {
          list : [],
          header : [
            {fieldName: 'ten_tv_kenh', headerText: 'Tên thành viên kênh', allowFiltering: true, width : 'auto'}, 
            {fieldName: 'ma_hang_kenh' , headerText: 'Tên hạng kênh', allowFiltering: true, width : 'auto'}, 
            {fieldName: 'mo_ta', headerText: 'Mô tả thành viên kênh', allowFiltering: true, width : 'auto'},
            {fieldName: 'trang_thai', headerText: 'Trạng thái thành viên kênh', allowFiltering: true, width : 'auto'},
            {fieldName: 'lien_quan_hh', headerText: 'Liên quan đến hàng hóa', allowFiltering: true, width : 'auto'},
            {fieldName: 'ma_nguoi_tao', headerText: 'Người tạo', allowFiltering: true, width : 'auto'},
            {fieldName: 'ngay_tao', headerText: 'Ngày tạo', allowFiltering: true, width : 'auto'},
            {fieldName: 'ma_nguoi_cap_nhat', headerText: 'Người cập nhật', allowFiltering: true, width : 'auto'},
            {fieldName: 'ngay_cap_nhat', headerText: 'Ngày cập nhật', allowFiltering: true, width : 'auto'},
          ],
          value : {},
          isEnabled : true,
        },
      },
      cboTrangThai : {
        list : [
            {
                'id' : 0,
                'text' : 'Deactive'
            },
            {
                'id' : 1,
                'text' : 'Active'
            },
        ],
        value : 0,
        disable: false,
      },

      cboLienQuanHangHoa : {
        list : [
            {
                'id' : 0,
                'text' : 'Không'
            },
            {
                'id' : 1,
                'text' : 'Có'
            },
        ],
        value : 0,
        disable: false,
      },
      cboLoaiKenh : {
        list : [
            {
                'id' : 0,
                'text' : 'Không'
            },
            {
                'id' : 1,
                'text' : 'Cha'
            },
            {
                'id' : 2,
                'text' : 'Con'
            },
        ],
        value : 0,
        disable: false,
      },
      cboThanhVienKenh : {
        list : [
            {
                'id' : 0,
                'text' : 'Nội bộ'
            },
            {
                'id' : 1,
                'text' : 'Kênh ngoài'
            },
        ],
        value : 0,
        disable: false,
      },
      cboHangKenh : {
        list : [],
        value : '',
        disable: false,
      },
      ma_thanh_vien : '',
      ten_thanh_vien : '',
      mo_ta : '',
      trang_thai : 0,
      is_insert  : 0,
      fields : '',
      dsThanhVienKenh : [],
      currentNode : [],
      disableCboHangKenh : true,
      checkCap : false
    }
  },
  computed: {
  },
  methods : {
    ShowAlert(mesage)
    {
      this.$toast.error(mesage);
    },

    ShowSuccess(mesage) {
      this.$toast.success(mesage);
    },

    nodeClicked (args) {
    },

    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },

    async  getListHangkenh() {
      let input = {}

      let data = this.GetData(await API.sp_lay_ds_hang_kenh(this.axios, input))
      console.log('data is ', data)
      if (data && data.length >0) {
        let arr = data

        if (arr && arr.length > 0) {
            this.cboHangKenh.list = arr.map(a => {
                return {
                    id : a.loaidv_id,
                    text : a.ten_loaidv,
                }
            })

            this.cboHangKenh.value = this.cboHangKenh.list[0].id
        }
        // this.listTable.hang_kenh_table.list = data
        // this.channel_SelectionChanged(0, this.listTable.hang_kenh_table.list[0])
      }
    },
    async displayTreeView () {
      let input = {
        "loaidv_id" : null
      }
      let data = this.GetData(await API.sp_lay_ds_tv_kenh(this.axios, input))

      if (data && data.length >0) {
        this.dsThanhVienKenh = data
      }
      let thanhVienTree = this.createTreeThanhVien(this.dsThanhVienKenh)
      // this.defaultDsDonViTree = donViTree
      // donViTree.sort((a, b) => {
      //   return a.ten_dv.localeCompare(b.ten_dv)
      // })
      // this.danhsachDonViTree = donViTree
      this.fields = {
        dataSource: thanhVienTree,
        id: 'donvi_id',
        text: 'ten_dv',
        parentID: 'donvi_cha_id',
        hasChildren: 'hasChild',
        expanded: 'expanded'
      }
      console.log(JSON.stringify(this.fields))
    },

    createTreeThanhVien (dsDonVi) {
      let tree = []
      let donviChaArray = dsDonVi.map((item) => {
        return item['donvi_cha_id']
      })
      let donviArray = dsDonVi.map(item => item['donvi_id'])
      // check item la parent node
      dsDonVi.forEach(item => {
        if (donviChaArray.includes(item['donvi_id'])) {
          // check donvi_cha_id khong co trong ds donvi_id thi xoa donvi_cha_id
          if (!donviArray.includes(item['donvi_cha_id'])) {
            delete item.donvi_cha_id
          }
          tree.push({...item, hasChild: true, expanded: false})
        } else {
          tree.push(item)
        }
      })
      // let departmentUserId = Number.parseInt(this.$root.token.getDonViID())
      // const usrDonViIdx = tree.findIndex(item => item.donvi_id === departmentUserId)
      // if (usrDonViIdx !== -1) {
      //   tree[usrDonViIdx].isSelected = true
      //   tree[usrDonViIdx].expanded = true
      // }
      return tree
    },

    selectDonViFocusedNodeChange(node) {
      this.is_insert = 0
      if (node) {
        let node_id = node.nodeData.id ? node.nodeData.id : ''
        if (this.fields.dataSource.length > 0) {
          let currentNode = this.fields.dataSource.filter(item => {
            return item.donvi_id == node_id
          })
          console.log('this.ma_thanh_vien is ', this.ma_thanh_vien)
          this.currentNode = currentNode
          if (currentNode) {
            if (currentNode[0].lv_tvkenh == 1) {
              this.disableCboHangKenh = false
            }
            if (currentNode[0].lv_tvkenh == 2 || currentNode[0].lv_tvkenh == 3) {
              this.disableCboHangKenh = true
            }
            this.ma_thanh_vien = currentNode[0].ma_dv
            this.ten_thanh_vien = currentNode[0].ten_dv
            this.mo_ta = currentNode[0].mota
            this.cboHangKenh.value = currentNode[0].loaidv_id
          }
        }
      }
    },

    async  getListThanhVienKenh() {
      let input = {
        "loaidv_id" : null
      }
      let data = this.GetData(await API.sp_lay_ds_tv_kenh(this.axios, input))

      if (data && data.length >0) {
        this.listTable.thanh_vien_kenh_table.list = data
      }
    },

    async NhapMoi() {
      let maThanhVien = this.GetData(await API.sinh_ma_tvk(this.axios, {}))
      this.ma_thanh_vien = maThanhVien
      this.ten_thanh_vien = ''
      this.mo_ta = ''
      this.cboTrangThai.value = 0
      this.is_insert = 1
    },

    async capNhat() {
      if(this.currentNode.length == 0) {
        this.ShowAlert("Chưa chọn thành viên kênh!")
        return
      }
      if (this.ma_thanh_vien == '') {
        this.ShowAlert('Mã thành viên không được để trống')
        return 
      }
      if (this.ten_thanh_vien == '') {
        this.ShowAlert('Tên thành viên không được để trống')
        return
      }

      if (this.is_insert == 1) {

        let input = {
            "is_insert" : 1,
            "donvi_id" : null,
            "donvi_cha_id" : this.currentNode[0].donvi_id,
            // "loaidv_id" : this.cboHangKenh.value,
            "ma_dv" : this.ma_thanh_vien,
            "ten_dv" : this.ten_thanh_vien,
            "mota" : this.mo_ta
        }
        if (this.currentNode[0].lv_tvkenh == 1) {
          input.loaidv_id = this.cboHangKenh.value
        }

        if (this.currentNode[0].lv_tvkenh == 2) {
          input.loaidv_id = this.currentNode[0].loaidv_id
        }

        if (this.currentNode[0].lv_tvkenh == 3) {
          this.ShowAlert('Không được tạo thành viên con!')
          return
        }

        // let data = this.GetData(await API.sp_insert_update_tv_kenh(this.axios, input))
        let res = await this.axios.post("/web-khdn/thanhvienkenh/sp_insert_update_tv_kenh", input)
        console.log('res is ', res)
        if (res) {
          if (res.data.error_code == "BSS-00000000") {
            this.ShowSuccess('Thêm mới thành viên thành công')
            this.displayTreeView()
          } else {
            this.ShowAlert(res.data.message)
          }
        }
      } else {
        // this.disableCboHangKenh = true
        let input = {
          "is_insert" : 0,
          "donvi_id" : this.currentNode[0].donvi_id,
          "donvi_cha_id" : this.currentNode[0].donvi_cha_id,
          "loaidv_id" : this.currentNode[0].loaidv_id,
          "ma_dv" : this.ma_thanh_vien,
          "ten_dv" : this.ten_thanh_vien,
          "mota" : this.mo_ta
        }

        let res = await this.axios.post("/web-quantri/vnp08/dm_thanh_vien_kenh/sp_insert_update_tv_kenh", input)
        if (res) {
          if (res.data.error_code == "BSS-00000000") {
            this.ShowSuccess('Cập nhật thành viên kênh thành công')
            this.displayTreeView()
          } else {
            this.ShowAlert(res.data.message)
          }
        }
      }
    },

    async xoaThanhVienKenh() {
        if (this.ma_thanh_vien == '') return

        let data = await this.axios.post('/web-quantri/vnp08/dm_thanh_vien_kenh/sp_deactive_tv_kenh', {
            "ma_tv_kenh " : this.ma_thanh_vien
        })

        if (data) {
            if (data.data.error_code === 'BSS-00000000') {
                this.showSuccess('Xóa thành viên kênh thành công')
            } else {
                this.showAlert('Có lỗi xảy ra khi xóa thành viên kênh')
            }
        }
    },

    

    channelMembers_SelectionChanged(idx, obj) {
      if (this.listTable.thanh_vien_kenh_table.list.length <= 0) return
      if (this.listTable.thanh_vien_kenh_table.list.length > 0) {
        if (obj) {
          this.ma_thanh_vien = obj.ma_tv_kenh
          this.ten_thanh_vien = obj.ten_tv_kenh
          this.mo_ta = obj.mo_ta
          this.cboTrangThai.value = obj.trang_thai == 'Active' ? 1 : 0
          this.cboLienQuanHangHoa.value = obj.lien_quan_hh == 'Có' ? 1 : 0
          this.cboThanhVienKenh.value = obj.loai_tv_kenh
          this.cboLoaiKenh.value = obj.loai_kenh
        }
      }
    }
  }
};

</script>
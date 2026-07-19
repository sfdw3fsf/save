<template src="./Department.html"></template>
<style src="./Department.scss"></style>
<script>
import DepartmentAPI from './API'
import gridView from '@/components/Shared/gridview.vue'
import breadcrumb from '../../../../components/breadcrumb'
import 'flatpickr/dist/plugins/monthSelect/style.css'
import bssFlatPicker from '@/components/BssDatePicker'
import DonViViTri from './popup/DonViViTri.vue'
import GanLoaiChuongTrinh from './popup/GanLoaiChuongTrinh'
import LoaiDonVi from './components/LoaiDonVi'
import {DataManager, ODataAdaptor, Predicate, Query} from '@syncfusion/ej2-data'
import moment from 'moment'
import DiaChi from './popup/DiaChi/index'
import ModalChonDiaChi from '@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi'
import {maxLength, minLength, required} from 'vuelidate/lib/validators'
import ComboboxGridCustom from './components/ComboboxGrid/index'
var DONVIOBJ = {
  'CHUCDANH': '',
  'DIACHI_DV': '',
  'DONVI_CHA_ID': 0,
  'DONVI_ID': 0,
  'DONVI_ID_NEO': 0,
  'DONVI_QL': 0,
  'EMAIL': '',
  'GHICHU': '',
  'GIAYPHEP_KD': '',
  'GIAY_UQ': '',
  'HTHD_ID': 0,
  'ICON': '',
  'KINHDO': 0,
  'MABC_ID_NEO': 0,
  'MA_DV': '',
  'MOTA': '',
  'MST': '',
  'MUC_ID': 0,
  'NGANHANG_ID': 0,
  'NGAYCAP': '',
  'NGUOI_DAI_DIEN': '',
  'NGUOI_DD': '',
  'NOICAP': '',
  'SO_DT': '',
  'SO_FAX': '',
  'SUDUNG': 1,
  'STK': '',
  'TEN_DV': '',
  'TEN_DVQL': '',
  'TIENTO': '',
  'TINH_ID': 0,
  'TOADO_VPV': '',
  'VIDO': 0,
  'WEBSITE': ''
}
export default {
  name: 'Department',
  components: {
    breadcrumb,
    gridView,
    bssFlatPicker,
    moment,
    DataManager,
    Query,
    ODataAdaptor,
    Predicate,
    DonViViTri,
    GanLoaiChuongTrinh,
    LoaiDonVi,
    DiaChi,
    ModalChonDiaChi,
    ComboboxGridCustom
  },
  data () {
    return {
      selectedNodes: [],
      expandedNodes: [Number(this.$root.token.getDonViID())],
      danhsach_donvi: [],
      sbDanhSachDonVi: [],
      fields: '',
      danhsachTinh: [],
      danhsachNganHang: [],
      tinhSelected: null,
      nganhangSelected: null,
      donViChaSelected: null,
      donviId: -1,
      waterMarkDonViCha: 'Chọn đơn vị cha',
      waterMarkTinh: 'Chọn tỉnh',
      waterMarkNganHang: 'Chọn ngân hàng',
      searchDonViValue: '',
      errorMessage: '',
      currentDonVi: Object.assign(DONVIOBJ),
      modelDonViVT: {
        donviId: 0,
        tendonvi: ''
      },
      displayDonViId: 'ID: ',
      danhsachDonViTree: [],
      danhsachFilterTree: [],
      defaultDsDonViTree: [],
      flagSort: 0,
      isCreate: false,
      maycn: '',
      ipcn: '',
      actions: [
        {
          id: 'nhapmoi',
          name: 'Nhập mới',
          active: true,
          icon_class: 'one-file-plus',
          tooltip: ''
        },
        {
          id: 'ghilai',
          name: 'Ghi lại',
          active: true,
          icon_class: 'one-save',
          tooltip: ''
        },
        {
          id: 'huy',
          name: 'Huỷ',
          active: true,
          icon_class: 'nc-icon-glyph ui-1_circle-remove',
          tooltip: ''
        },
        {
          id: 'xoa',
          name: 'Xoá',
          active: true,
          icon_class: 'one-trash',
          tooltip: ''
        },
        {
          id: 'ganloaict',
          name: 'Gán loại CT',
          active: true,
          icon_class: '-ap icon-pin',
          tooltip: 'Gán quyền thao tác cho đơn vị trên các chương trình khai thác chung CSDL với chương trình PTTB'
        },
        {
          id: 'ganvitri',
          name: 'Gán vị trí',
          active: true,
          icon_class: 'nc-icon-glyph location_crosshair',
          tooltip: 'Thực hiện gán đơn vị cho nhiều vị trí'
        }
      ],
      header: {
        title: 'Đơn vị',
        list: [
          {
            name: 'Lập hợp đồng',
            link: {name: 'Ui.cards'},
            active: false
          },
          {
            name: 'Lắp đặt mới',
            link: {name: 'Ui.buttons'},
            active: true
          }
        ]
      },
      target: '.main-wrapper',
      dataSelected: {
        diachi: null,
        tinh_id: null,
        quan_id: null,
        phuong_id: null,
        pho_id: null,
        ap_id: null,
        khu_id: null,
        sonha: null,
        dac_diem_id: null
      },
      sudung: 1,
      DmDonViCha: {
        data: [],
        textField: 'ten_dv',
        valueField: 'donvi_id',
        panelDataHeight: 'auto',
        headers: [
          {
            fieldName: 'ten_dv',
            headerText: 'Tên đơn vị',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_dvcha',
            headerText: 'Đơn vị cha',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        selectDonViCha: null
      },
      sortOrder: 'Ascending'
    }
  },
  computed: {},
  async created () {
    await this.displayTreeView()
  },
  mounted () {
    this.initData()
    this.setActiveActions(-1)
  },
  validations: {
    currentDonVi: {
      MA_DV: {
        required,
        minLength: minLength(1),
        maxLength: maxLength(50)
      },
      TEN_DV: {
        required,
        minLength: minLength(3),
        maxLength: maxLength(500)
      },
      DIACHI_DV: {
        required,
        minLength: minLength(3),
        maxLength: maxLength(1000)
      }
    }
  },
  methods: {
    showChonDiaChiPopup () {
      this.$root.$emit('bv::show::modal', 'popupDiaChi')
    },
    validatePhoneNumbers() {
      const phoneNumbers = this.currentDonVi.SO_DT.split(';').map(number => number.trim());
      if (phoneNumbers.length > 90) {
        this.errorMessage = 'Số lượng số điện thoại không được vượt quá 90.';
        return;
      }
      for (const number of phoneNumbers) {
        if (!/^0\d{9}$/.test(number)) {
          this.errorMessage = 'Mỗi số điện thoại phải bắt đầu bằng số 0 và có độ dài là 10 ký tự.';
          return;
        }
      }
      this.errorMessage = ''; 
    },
    CheckedChanged_Form(event){
        this.currentDonVi.SUDUNG = event.target.checked? 1: 0
    },
    async CheckedChanged (event){
      try {
        let departmentUserId = Number.parseInt(this.$root.token.getDonViID())
          this.loading(true)
          let root = await this.get_donvi_root(departmentUserId)
          let dsDv = await this.sp_lay_ds_donvi_donvicha_dk(departmentUserId,event.target.checked?0:1)
          if (dsDv.length === 0) {
            delete root.donvi_cha_id
          }
          dsDv.push(root)
          this.danhsach_donvi = dsDv
          let donViTree = this.createTreeDonVi(dsDv)
          this.defaultDsDonViTree = donViTree
          this.danhsachDonViTree = donViTree
          this.fields = {
              dataSource: donViTree,
              id: 'donvi_id',
              text: 'ten_dv',
              parentID: 'donvi_cha_id',
              hasChildren: 'hasChild',
              expanded: 'expanded'
            }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Lỗi khi hiển thị cây đơn vị: ' + error.message)
      } finally {
        this.loading(false)
      }
    },
    getDiaChi: function (data) {
      console.log(222)
      console.log(data)
      this.dataSelected.diachi = data.diachimoi
      this.dataSelected.tinh_id = data.tinh_id
      this.dataSelected.quan_id = data.quan_id
      this.dataSelected.phuong_id = data.phuong_id
      this.dataSelected.pho_id = data.pho_id
      this.dataSelected.ap_id = data.ap_id
      this.dataSelected.khu_id = data.khu_id
      this.dataSelected.dac_diem_id = data.dacdiem_id
      this.dataSelected.so_nha = data.so_nha
      this.dataSelected.kinhdo = data.lng
      this.dataSelected.vido = data.lat
      this.currentDonVi.DIACHI_DV = this.dataSelected.diachi 
    },
    nodeClicked (args) {
    },
    async openModalVT () {
      if (this.currentDonVi) {
        this.modelDonViVT.donviId = this.currentDonVi.DONVI_ID
        this.modelDonViVT.tendonvi = this.currentDonVi.MA_DV + ' - ' + this.currentDonVi.TEN_DV
        this.$refs.popupDVVT.showModal()
      } else {
        this.$toast('Vui lòng chọn đơn vị') 
      }
    },
    async openModalLCT () {
      if (this.currentDonVi.DONVI_ID !== undefined) {
        this.$refs.popupLoaiCT.showModal()
      } else {
        this.$toast('Vui lòng chọn đơn vị')
      }
    },
    onFilteringNganHang (e) {
      let query = new Query()
      let predicate
      // frame the query based on search string with filter type.
      if (e.text.trim() !== '') {
        predicate = new Predicate('TEN_NH', 'contains', e.text.trim(), true)
        // predicate = predicate.or('TEN_KHO', 'contains', e.text);
        query.where(predicate)
      }
      // pass the filter data source, filter query to updateData method.
      e.updateData(this.danhsachNganHang, query)
    },
    async searchDonVi () {
      try {
        this.loading(true)
        let predicate
        let predicats = []
        let _array = []
        let _filter = []
        if (this.searchDonViValue.trim() !== '') {
          predicate = new Predicate('ten_dv', 'contains', this.searchDonViValue.trim(), true)
          let filteredList = new DataManager(this.danhsachDonViTree).executeLocal(new Query().where(predicate))
          for (let j = 0; j < filteredList.length; j++) {
            _filter.push(filteredList[j]['donvi_id'])
            let filters = this.getFilterItems(filteredList[j], this.danhsachDonViTree)
            for (let i = 0; i < filters.length; i++) {
              if (_array.indexOf(filters[i]) === -1 && filters[i] != null) {
                _array.push(filters[i])
                predicats.push(new Predicate('donvi_id', 'equal', filters[i], false))
              }
            }
          }
          if (predicats.length === 0) {
            this.changeDataSource([])
          } else {
            let query = new Query().where(Predicate.or(predicats))
            let newList = new DataManager(this.danhsachDonViTree).executeLocal(query)
            this.changeDataSource(newList)
            setTimeout(() => {
              this.$refs.tree.expandAll()
            }, 100)
          }
        } else {
          this.changeDataSource(this.danhsachDonViTree)
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },
    // Find the Parent Nodes for corresponding childs
    getFilterItems (fList, list) {
      let nodes = []
      nodes.push(fList['donvi_id'])
      let query2 = new Query().where('donvi_id', 'equal', fList['donvi_cha_id'], false)
      let fList1 = new DataManager(list).executeLocal(query2)
      if (fList1.length !== 0) {
        let pNode = this.getFilterItems(fList1[0], list)
        for (let i = 0; i < pNode.length; i++) {
          if (nodes.indexOf(pNode[i]) === -1 && pNode[i] != null) {
            nodes.push(pNode[i])
          }
        }
        return nodes
      }
      return nodes
    },
    onFilteringTinh (e) {
      var query = new Query()
      let predicate
      // frame the query based on search string with filter type.
      if (e.text.trim() !== '') {
        predicate = new Predicate('TENTINH', 'startswith', e.text.trim(), true)
        // predicate = predicate.or('TEN_KHO', 'contains', e.text);
        query.where(predicate)
      }
      // pass the filter data source, filter query to updateData method.
      e.updateData(this.danhsachTinh, query)
    },
    dataBound (args) {
      this.selectedNodes = []
      this.expandedNodes = []
      if (this.currentDonVi && this.currentDonVi.DONVI_ID != 0 && this.currentDonVi.DONVI_CHA_ID) {
        this.selectedNodes = this.selectedNodes.filter(item => item != this.$root.token.getDonViID())
        this.selectedNodes.push(this.currentDonVi.DONVI_ID)
        this.getListDonViCha(this.currentDonVi)
      } else {
        this.selectedNodes.push(this.$root.token.getDonViID())
        this.expandedNodes.push(Number(this.$root.token.getDonViID()))
      }
    },
    getListDonViCha (item) {
      if (item.DONVI_CHA_ID != null) {
        this.expandedNodes.push(item.DONVI_CHA_ID)
        let index = this.danhsach_donvi.findIndex(e => e.donvi_id == item.DONVI_CHA_ID)
        if (index >= 0) {
          let newItem = this.capitalize(this.danhsach_donvi[index])
          this.getListDonViCha(newItem)
        }
      }
    },
    capitalize (data) {
      for (let key in data) {
        if (key.toUpperCase() !== key) {
          data[key.toUpperCase()] = data[key]
          delete data[key]
        }
      }
      return data
    },
    keyPressTree (args) {
      console.log(args)
    },
    sortTreeDonVi () {
      this.sortOrder = this.sortOrder == 'Ascending' ? 'Descending' : 'Ascending'
    },
    async selectDonViFocusedNodeChange (node) {
      let donviId = Number(node.nodeData.id)
      this.$refs.loai_dv.clearSelected()
      await this.displayData(donviId)
      this.setActiveActions(3)
    },
    async displayData (donviId) {
      await DepartmentAPI.get_donvi(this.axios, {
        'p_donvi_id': donviId
      }).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          this.currentDonVi = Object.assign({}, response.data.data[0])
        }
      })
      // console.log('current donvi:', this.currentDonVi)
      this.nganhangSelected = this.currentDonVi.NGANHANG_ID
      if (this.currentDonVi.hasOwnProperty('DONVI_CHA_ID')) {
        this.DmDonViCha.selectDonViCha = this.currentDonVi.DONVI_CHA_ID
      } else {
        this.DmDonViCha.selectDonViCha = -1
      }
      this.tinhSelected = this.currentDonVi.TINH_ID
      this.donviId = donviId
      this.displayDonViId = 'ID: ' + this.donviId
      await this.$refs.loai_dv.hienthi_ds_loaidv(donviId)
    },
    async get_donvi_root (donviId) {
      let donvi = {}
      // console.log('root ', this.donviId)
      await DepartmentAPI.get_donvi(this.axios, {
        'p_donvi_id': donviId
      }).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          let data = response.data.data[0]
          console.log('root data: ', data)
          donvi = {
            ten_dv: data.TEN_DV,
            donvi_id: data.DONVI_ID,
            donvi_cha_id: data.DONVI_CHA_ID,
            unitlevel: 1
          }
        }
      })
      return donvi
    },
    createTreeDonVi (dsDonVi) {
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
          tree.push({...item, hasChild: true, expanded: false, tooltip: item.ten_dv})
        } else {
          tree.push({...item, tooltip: item.ten_dv})
        }
      })
      return tree
    },
    // Change the dataSource for TreeView
    changeDataSource (data) {
      this.fields = {
        dataSource: data, id: 'donvi_id', text: 'ten_dv', parentID: 'donvi_cha_id', hasChildren: 'hasChild'
      }
    },
    async initData () {
      this.$refs.checkSD.checked = true
      this.maycn = await this.$root.token.getMachine()
      this.ipcn = await this.$root.token.getIP()
      await this.getDanhSachTinh()
      await this.getDanhSachNganHang()
      await this.getDsDonViCha()
      await this.displayData(Number.parseInt(this.$root.token.getDonViID()))
    },
    async displayTreeView () {
      try {
        this.loading(true)
        let departmentUserId = Number.parseInt(this.$root.token.getDonViID())
        let root = await this.get_donvi_root(departmentUserId)
        let dsDv = await this.sp_lay_ds_donvi_donvicha_dk(departmentUserId,this.currentDonVi.SUDUNG)
        if (dsDv.length === 0) {
          delete root.donvi_cha_id
        }
        dsDv.push(root)
        this.danhsach_donvi = dsDv
        let donViTree = this.createTreeDonVi(dsDv)
        this.defaultDsDonViTree = donViTree
        this.danhsachDonViTree = donViTree
        this.fields = {
          dataSource: donViTree,
          id: 'donvi_id',
          text: 'ten_dv',
          parentID: 'donvi_cha_id',
          hasChildren: 'hasChild',
          expanded: 'expanded'
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Lỗi khi hiển thị cây đơn vị: ' + error.message)
      } finally {
        this.loading(false)
      }
    },
    async txtMaDV_KeyPress () {
      if (this.currentDonVi.MA_DV) {
        let donviid = await this.FN_TT_DONVI(this.currentDonVi.MA_DV)
        if (donviid != '-1') {
          this.$refs.loai_dv.clearSelected()
          await this.displayData(donviid)
          this.setActiveActions(3)
        } else {
          this.$root.$toast.warning('Không tìm thấy thông tin về mã đơn vị vừa nhập!')
        }
      }
    },
    async FN_TT_DONVI (madv) {
      let req = {
        type: 7,
        param: madv
      }
      return this.GetData(await DepartmentAPI.FN_TT_DONVI(this.axios, req))
    },
    GetData: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return null
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return null
    },
    async getDanhSachTinh () {
      try {
        this.loading(true)
        const response = await DepartmentAPI.get_danhsach_tinh(this.axios)
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          this.danhsachTinh = response.data.data
        }
      } catch (e) {
        this.$toast('Không lấy được danh sách tỉnh')
      } finally {
        this.loading(false)
      }
    },
    async getDanhSachNganHang () {
      try {
        this.loading(true)
        const response = await DepartmentAPI.get_danhsach_nganhang(this.axios)
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          this.danhsachNganHang = response.data.data
        }
      } catch (e) {
        this.$toast('Không lấy được danh sách ngân hàng')
      } finally {
        this.loading(false)
      }
    },
    // Khong dung vi sai nghiep vu
    async SP_GIAOSIM_GET_TREEVIEW (donviChaId) {
      let ds = []
      await DepartmentAPI.SP_GIAOSIM_GET_TREEVIEW(this.axios, {
        p_donvicha_id: donviChaId
      }).then(response => {
        if (response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
          ds = response.data.data
        } else {
          ds = []
        }
      })
      return ds
    },
    async sp_lay_ds_donvi_donvicha_dk (dvchaid,sudung) {
      let ds = []
      await DepartmentAPI.sp_lay_ds_donvi_donvicha_dk(this.axios, {
        p_donvi_cha_id: dvchaid,
        p_sudung: sudung
      }).then(response => {
        if (response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
          ds = response.data.data
        } else {
          ds = []
        }
      })
      return ds
    },
    async sp_lay_ds_donvi_donvicha (dvchaid) {
      let ds = []
      await DepartmentAPI.sp_lay_ds_donvi_donvicha(this.axios, {
        p_donvicha_id: dvchaid
      }).then(response => {
        if (response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
          ds = response.data.data
        } else {
          ds = []
        }
      })
      return ds
    },
    async getDsDonViCha () {
      try {
        this.loading(true)
        const params = {p_don_vi: -1}
        let response = await DepartmentAPI.get_donvi(this.axios, params)
        if (response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
          this.DmDonViCha.data = response.data.data.filter(item => item.donvi_id !== null)
          this.DmDonViCha.data.unshift({'donvi_id': '', 'ten_dv': ''})
        } else {
          this.DmDonViCha.data = []
        }
      } catch (e) {
        console.log(e)
        this.$toast.error('Không load được danh sách đơn vị cha')
      } finally {
        this.loading(false)
      }
    },
    saveData () {
      try {
        this.$v.$touch()
        if (this.$v.currentDonVi.$invalid) {
          let errorMessage = []
          if (this.currentDonVi.MA_DV === '') {
            errorMessage.push(`Chưa nhập mã đơn vị`)
          }
          if (this.currentDonVi.TEN_DV === '') {
            errorMessage.push(`Chưa nhập tên đơn vị`)
          }
          if (this.currentDonVi.DIACHI_DV === '') {
            errorMessage.push(`Chưa nhập địa chỉ đơn vị`)
          }
          if (errorMessage.length > 0) {
            this.$root.$toast.error(errorMessage.join('\n'))
          }
          return
        }
        if (this.DmDonViCha.selectDonViCha == null) {
          this.$root.$toast.error('Vui lòng chọn đơn vị cha')
          return
        }
        if (this.tinhSelected == null) {
          this.$root.$toast.error('Vui lòng chọn tỉnh thành')
          return
        }
        this.$confirm('Bạn có muốn cập nhật dữ liệu không ?', 'Thông báo', {
          confirmButtonText: 'Đồng ý',
          cancelButtonText: 'Hủy'
        }).then(async () => {
          this.currentDonVi.DONVI_QL = this.currentDonVi.DONVI_QL === true ? 1 : 0
          this.currentDonVi.TINH_ID = this.tinhSelected
          this.currentDonVi.NGANHANG_ID = this.nganhangSelected
          this.currentDonVi.DONVI_CHA_ID = this.DmDonViCha.selectDonViCha == '' ? null : this.DmDonViCha.selectDonViCha
          const nhapmoiBtnActive = this.actions[this.actions.findIndex(x => x.id === 'nhapmoi')].active
          try {
            let pIsInsert = 0 // 0 Cap nhat 1 them moi
            if (!nhapmoiBtnActive && this.isCreate) {
              pIsInsert = 1
            } else {
              pIsInsert = 0
            }
            this.currentDonVi.KINHDO = this.dataSelected.kinhdo
            this.currentDonVi.VIDO = this.dataSelected.vido
            const danhsach = []
            danhsach.push(this.currentDonVi)
            const params = {danhsach: danhsach, p_is_insert: pIsInsert}
            const response = await DepartmentAPI.saveOrUpdate(this.axios, params)
            if (response && response.data && response.data.error_code === 'BSS-00000000') {
              this.$root.$toast.success('Cập nhật dữ liệu thành công !')
              let data = response.data.data
              let donviid = this.currentDonVi.DONVI_ID
              if (data) {
                donviid = Number(data)
              }
              await this.$refs.loai_dv.CAPNHAT_LOG_DONVI_LDV_JSON(donviid, this.maycn, this.ipcn)
              if (pIsInsert == 1) {
                this.currentDonVi.DONVI_ID = Number(response.data.data)
              }
              await this.displayTreeView()
            } else {
              this.$root.$toast.error('Cập nhật dữ liệu thất bại:' + response.data.message_detail)
            }
          } catch (error) {
            this.$root.$toast.error('Cập nhật dữ liệu thất bại !: ' + error.data.message_detail)
          }
          this.setActiveActions(3)
        })
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Xảy ra lỗi khi lưu dữ liệu: ' + error.data.message)
      }
    },
    deleteData () {
      this.$confirm('Bạn thật sự muốn xóa dữ liệu không ?', 'Thông báo', {
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Hủy'
      }).then(async () => {
        const params = {'p_donvi_id': this.donviId}
        await DepartmentAPI.deleteDonVi(this.axios, params).then(async (response) => {
          if (response && response.data && response.data.error_code === 'BSS-00000000') {
            this.$root.$toast.success('Xóa dữ liệu đơn vị thành công !')
            this.currentDonVi.DONVI_CHA_ID = 0
            this.currentDonVi.DONVI_ID = 0
            await this.clear()
            await this.displayTreeView()
            await this.getDsDonViCha()
          } else {
            this.$root.$toast.error('Không xoá được đơn vị này vì còn đơn vị phụ thuộc')
          }
        }).catch(error => {
          console.log(error)
          let message = error.data.message_detail
          if (message.includes('phụ thuộc') || message.includes('integrity constraint')) {
            this.$root.$toast.error('Không xoá được đơn vị này vì còn đơn vị phụ thuộc')
          } else {
            this.$root.$toast.error('Có lỗi: ' + error.data.message_detail)
          }
        })
      })
    },
    onActionClick (action) {
      if (action.id === 'nhapmoi') {
        this.setActiveActions(1)
      } else if (action.id === 'ghilai') {
        this.saveData()
      } else if (action.id === 'huy') {
        this.setActiveActions(2)
      } else if (action.id === 'xoa') {
        if (this.donviId === -1) {
          return
        }
        this.deleteData()
      } else if (action.id === 'ganloaict') {
        this.openModalLCT()
      } else if (action.id === 'ganvitri') {
        this.openModalVT()
      }
    },
    setActiveActions (kieu) {
      this.actions.forEach((item) => {
        if (item.id !== 'ganloaict' && item.id !== 'ganvitri') {
          item.active = false
        }
      })
      if (kieu === -1) { // bat dau
        this.actions[this.actions.findIndex(x => x.id === 'ghilai')].active = true
        this.actions[this.actions.findIndex(x => x.id === 'huy')].active = true
      } else if (kieu === 0) { // bắt đầu
        this.actions[this.actions.findIndex(x => x.id === 'nhapmoi')].active = true
        this.clear()
      } else if (kieu === 1) { // thêm mới
        this.$refs.txtMaDV.focus()
        this.actions[this.actions.findIndex(x => x.id === 'ghilai')].active = true
        this.actions[this.actions.findIndex(x => x.id === 'huy')].active = true
        this.isCreate = true
        this.clear()
      } else if (kieu === 2) { // Huỷ
        this.actions[this.actions.findIndex(x => x.id === 'nhapmoi')].active = true
        this.actions[this.actions.findIndex(x => x.id === 'xoa')].active = true
        this.clear()
      } else if (kieu === 3) { // edit
        this.actions[this.actions.findIndex(x => x.id === 'nhapmoi')].active = true
        this.actions[this.actions.findIndex(x => x.id === 'ghilai')].active = true
        this.actions[this.actions.findIndex(x => x.id === 'huy')].active = true
        this.actions[this.actions.findIndex(x => x.id === 'xoa')].active = true
      }
    },
    async clear () {
      // clear cac truong du lieu
      this.currentDonVi.DONVI_ID = 0
      this.currentDonVi.MA_DV = ''
      this.currentDonVi.TEN_DV = ''
      this.currentDonVi.DIACHI_DV = ''
      this.currentDonVi.TINH_ID = 0
      this.currentDonVi.GHICHU = ''
      this.currentDonVi.TIENTO = ''
      this.currentDonVi.NGUOI_DAI_DIEN = ''
      this.currentDonVi.NGUOI_DD = ''
      this.currentDonVi.GIAY_UQ = ''
      this.currentDonVi.NGAYCAP = ''
      this.currentDonVi.SO_DT = ''
      this.currentDonVi.STK = ''
      this.currentDonVi.EMAIL = ''
      this.currentDonVi.NGANHANG_ID = 0
      this.currentDonVi.CHUCDANH = ''
      this.currentDonVi.GIAYPHEP_KD = ''
      this.currentDonVi.NOICAP = ''
      this.currentDonVi.MST = ''
      this.currentDonVi.SO_FAX = ''
      this.currentDonVi.WEBSITE = ''
      this.currentDonVi.SUDUNG = 0
      this.nganhangSelected = null
      this.donviId = -1
      this.displayDonViId = ''
      // clear danh sach loai don vi
      await this.$refs.loai_dv.clearData()
      // clear trang thai validate object them don vi
      this.$nextTick(() => {
        this.$v.currentDonVi.$reset()
      })
      // Clear chon du lieu dia chi
      this.dataSelected.tinh_id = null
      this.dataSelected.quan_id = null
      this.dataSelected.phuong_id = null
      this.dataSelected.pho_id = null
      this.dataSelected.ap_id = null
      this.dataSelected.khu_id = null
      this.dataSelected.sonha = null
      this.dataSelected.dac_diem_id = null
    },
    updateGanLoaiDonVi () {
    }
  }
}
</script>
<style>
.e-input-group .e-input[readonly], .e-input-group.e-control-wrapper .e-input[readonly], .e-float-input input[readonly], .e-float-input.e-control-wrapper input[readonly], .e-float-input textarea[readonly], .e-float-input.e-control-wrapper textarea[readonly] {
  color: #333 !important;
}
.disabled_color {
  color: gray !important;
}
</style>
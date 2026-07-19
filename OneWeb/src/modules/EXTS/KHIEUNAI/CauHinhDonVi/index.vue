<template src="./index.html"></template>
<script>
import Vue from 'vue'
import lodash from 'lodash'
import { DashboardLayoutPlugin } from '@syncfusion/ej2-vue-layouts'
import * as moment from 'moment'
import api from './api/new-api'
import ccbsApi from './api/ccbs-api'
import {DataManager, Predicate, Query} from '@syncfusion/ej2-data'

Vue.use(DashboardLayoutPlugin)

export default {  
  data() {
    return {
      menus: [
        { id: 'mnuLayDS', name: 'Lấy DS', icon: 'icon nc-icon-glyph location_crosshair', enabled: true },
        { id: 'mnuGan', name: 'Gán', icon: 'icon one-save', enabled: true },
        { id: 'mnuHuyGan', name: 'Hủy gán', icon: 'icon nc-icon-glyph ui-1_circle-remove', enabled: true },
      ],
      txtTreeDonViSearch: { enabled: true, value: null },
      cboDichvuVT: { enabled: true, list: [], value: null },
      cboNhomChuDe: { enabled: true, list: [], value: null },
      gridColumn: {
        header: [
          { fieldName: 'STT', headerText: 'STT', allowFiltering: true, width: 150 },
          { fieldName: 'MA_DV', headerText: 'Mã đơn vị', allowFiltering: true, width: 200 },
          { fieldName: 'TEN_DV', headerText: 'Tên đơn vị', allowFiltering: true, allowHtml: true, width: 200 },
          { fieldName: 'DICH_VU', headerText: 'Dịch vụ', allowFiltering: true, allowHtml: true, width: 200 },
          { fieldName: 'LOAI_HINH', headerText: 'Loại hình', allowFiltering: true, allowHtml: true, width: 200 },
          { fieldName: 'TEN_NHOM', headerText: 'Nhóm chủ đề', allowFiltering: true, allowHtml: true, width: 200 },
          { fieldName: 'CHUDE_CAP1', headerText: 'Chủ đề cấp 1', allowFiltering: true, allowHtml: true, width: 200 },
          { fieldName: 'CHUDE_CAP2', headerText: 'Chủ đè cấp 2', allowFiltering: true, allowHtml: true, width: 200 }
        ],
        list: [],
        value: null
      },
      gridDonViDaGan: {
        header: [
          { fieldName: 'MA_DV', headerText: 'Mã đơn vị', allowFiltering: true, width: 100 },
          { fieldName: 'TEN_DV', headerText: 'Tên đơn vị', allowFiltering: true},
        ],
        list: [],
        selected: [],
        value: null
      },
      gridDonViChuaGan: {
        header: [
          { fieldName: 'MA_DV', headerText: 'Mã đơn vị', allowFiltering: true, width: 100 },
          { fieldName: 'TEN_DV', headerText: 'Tên đơn vị', allowFiltering: true},
        ],
        list: [],
        selected: [],
        value: null
      },
      gridChuDeDaGan: {
        header: [
          { fieldName: 'NHOM_CHUDE', headerText: 'Nhóm chủ đề', allowFiltering: true, width: 150 },
          { fieldName: 'CHUDE_CAP1', headerText: 'Chủ đè cấp 1', allowFiltering: true, width: 200 },
          { fieldName: 'CHUDE_CAP2', headerText: 'Chủ đề cấp 2', allowFiltering: true, width: 200 }
        ],
        list: [],
        selected: [],
        value: null
      },
      gridChuDeChuaGan: {
        header: [
          { fieldName: 'NHOM_CHUDE', headerText: 'Nhóm chủ đề', allowFiltering: true, width: 150 },
          { fieldName: 'CHUDE_CAP1', headerText: 'Chủ đè cấp 1', allowFiltering: true, width: 200 },
          { fieldName: 'CHUDE_CAP2', headerText: 'Chủ đề cấp 2', allowFiltering: true, width: 200 }
        ],
        list: [],
        selected: [],
        value: null
      },
      gridLoaiHinhDaGan: {
        header: [
          { fieldName: 'MA_LHTB', headerText: 'Mã loại hình', allowFiltering: true, width: 150 },
          { fieldName: 'LOAIHINH_TB', headerText: 'Loại hình', allowFiltering: true, width: 150 },
          { fieldName: 'TEN_DVVT', headerText: 'Dịch vụ', allowFiltering: true, width: 150 },
        ],
        list: [],
        selected: [],
        value: null
      },
      gridLoaiHinhChuaGan: {
        header: [
          { fieldName: 'MA_LHTB', headerText: 'Mã loại hình', allowFiltering: true, width: 150 },
          { fieldName: 'LOAIHINH_TB', headerText: 'Loại hình', allowFiltering: true, width: 150 },
          { fieldName: 'TEN_DVVT', headerText: 'Dịch vụ', allowFiltering: true, width: 150 },
        ],
        list: [],
        selected: [],
        value: null
      },
      fieldsDonVi: null,
      danhsachDonViTree: [],
    }
  },
  async mounted() {
    this.loading(true)
    try {
      this.cboDichvuVT.list = await this.apiGetDanhMucTheoTen('DICHVU_VT', 0);
      this.cboNhomChuDe.list = await this.apiGetDanhMucTheoTen('NHOM_CHUDE', 0);
      this.danhsachDonViTree = await this.apiTreeDonVi()
      this.fieldsDonVi = {
        dataSource: this.createTreeDonVi(this.danhsachDonViTree),
        id: 'DONVI_ID',
        parentID: 'DONVICHA_ID',
        text: 'TEN_DV',
        hasChildren: 'hasChild',
        expanded: 'expanded',
      }
      this.gridColumn.list = await this.apiDanhSachCauHinh();
    } catch (e) {
      console.log('loadData: ', e);
    } finally {
      this.loading(false)
    }
  },
  methods: {
    async apiGetDanhMucTheoTen(ten, id, id2) {
      try {
        let param = {
          tenDanhMuc: ten,
          id: id,
          id2: id2
        }
        const rs = await this.$root.context.post('/web-gqkn/tiep-nhan-khieu-nai/danhmuc-theo-ten', param);
        return rs.data;
      } catch (error) { return [] }
    },
    async apiTreeDonVi() {
      this.$root.showLoading(true)
      try {
        const rs = await this.$root.context.get('/web-gqkn/khieunai/tree-donvi')
        return rs.data
      }
      catch (ex) {
        const msg = ex?.data?.message
                 || ex?.data?.data?.message
                 || 'Lỗi chức năng lấy danh sách cây đơn vị!';
        this.$root.toastError(msg);
      } finally {
        this.$root.showLoading(false)
      }
    },
    async apiXoaCauHinhDonVi(param) {
      this.loading(true)
      let result = true
      try {
        let rs = await this.$root.context.post('/web-gqkn/khieunai/xoa-nhanvien-donvi-theo-chude-loaitb', param)
        this.$toast.success('Xóa phiếu thành công')
      } catch (ex) {
        console.log('apiXoaCauHinhDonVi: ', ex);
        result = false
        const msg = ex?.data?.message
                 || ex?.data?.data?.message
                 || 'Xóa cấu hình thức bại!';
        this.$root.toastError(msg);
      } finally {
        this.loading(false)
      }
      return result
    },
    async apiDanhSachCauHinh() {
      this.$root.showLoading(true)
      try {
        let rs = await this.$root.context.get('/web-gqkn/khieunai/lay-danhsach-donvi-theo-chude')
        return rs.data
      } catch (ex) {
        const msg = ex?.data?.message
                 || ex?.data?.data?.message
                 || 'Cập nhật cấu hình thức bại!';
        this.$root.toastError(msg);
      } finally {
        this.$root.showLoading(false)
      }
    },
    async apiCapNhatCauHinh(param) {
      this.loading(true)
      let rs = null;
      try {
        const response = await this.$root.context.post('/web-gqkn/khieunai/capnhat-danhsach-cauhinh-chude-lhtb-donvi-nhanvien', param)
        rs = response.data
        this.$root.toastSuccess('Cập nhật cấu hình thành công!')
      } catch (ex) {
        const msg = ex?.data?.message
                 || ex?.data?.data?.message
                 || 'Cập nhật cấu hình thức bại!';
        this.$root.toastError(msg);
      } finally {
        this.loading(false)
      }
      return rs
    },
    createTreeDonVi(dsDonvi) {
      let tree = []
      let donViChaArray = dsDonvi.map((item) => item['DONVICHA_ID'])
      let donViArray = dsDonvi.map((item) => item['DONVI_ID'])
      dsDonvi.forEach((item) => {
        //check item la parent node
        if (donViChaArray.includes(item['DONVI_ID'])) {
          //check donViChaArray khong co trong ds donViArray thi xoa donViChaArray
          if (!donViArray.includes(item['DONVICHA_ID'])) {
            delete item.DONVICHA_ID
          }
          tree.push({ ...item, hasChild: true, expanded: false })
        } else {
          tree.push({ ...item, hasChild: false, expanded: false })
        }
      })
      return tree
    },
    async loadDonVi(nhanVienData, loaidv_id) {
      this.loading(true)
      let dataDonViDaGan = await api.sp_ds_donvi_theo_donvidl_loaidv(this.axios, {
        p_donvidl_id: 0,
        p_loaidv_id: loaidv_id,
        p_nhanvien_id: nhanVienData.nhanvien_id || 0,
        p_kieu: 1
      })
      this.gridDonViDaGan.list = dataDonViDaGan.data.data
      let dataDonViChuaGan = await api.sp_ds_donvi_theo_donvidl_loaidv(this.axios, {
        p_donvidl_id: 0,
        p_loaidv_id: loaidv_id,
        p_nhanvien_id: nhanVienData.nhanvien_id || 0,
        p_kieu: 0
      })
      this.gridDonViChuaGan.list = dataDonViChuaGan.data.data
      this.loading(false)
    },
    onMenuClicked(action) {
      switch (action.id) {
        case 'mnuLayDS':
          this.onBtnLayDSClicked()
          break
        case 'mnuGan':
          this.onBtnGanClicked()
          break
        case 'mnuHuyGan':
          this.onBtnHuyGanClicked()
          break
      }
    },
    async onResetFormCauHinh() {
      this.gridDonViDaGan.list = []
      this.gridDonViChuaGan.list = []

      this.gridLoaiHinhDaGan.list = []
      this.gridLoaiHinhChuaGan.list = []

      this.gridChuDeDaGan.list = []
      this.gridChuDeChuaGan.list = []
      
      this.cboDichvuVT.value = null
      this.cboNhomChuDe.value = null
    },
    async onBtnLayDSClicked() {
      await this.onResetFormCauHinh()
      this.gridColumn.list = await this.apiDanhSachCauHinh()
    },
    async onBtnGanClicked() {
      // valid input
      let valid = true
      if (!this.gridDonViDaGan.list || this.gridDonViDaGan.list.length == 0) {
        this.$root.toastError('Bắt buộc số đơn vị gán phải lơn hơn 0!')
        valid = false
      }
      if (!this.gridChuDeDaGan.list || this.gridChuDeDaGan.list.length == 0) {
        this.$root.toastError('Bắt buộc số chủ đề gán phải lơn hơn 0!')
        valid = false
      }
      if (!this.gridLoaiHinhDaGan.list || this.gridLoaiHinhDaGan.list.length == 0) {
        this.$root.toastError('Bắt buộc số loại hình thuê bao gán phải lơn hơn 0!')
        valid = false
      }
      if (!valid) return;
      
      // confirm
      const confirm = await this.$confirm('Xác nhận gán cấu hình?', 'Xác nhận thực hiện', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'info'
      })
        .then(() => true)
        .catch(() => false)
      if (!confirm) return;

      const param = {
        dsChuDe: this.gridChuDeDaGan.list.map(item => item.CHUDE_CAP2_ID).join(','),   //ds_chude.join(","),
        dsLoaiTB: this.gridLoaiHinhDaGan.list.map(item => item.LOAITB_ID).join(','),   //ds_loaihinh.join(","),
        dsDonVi: this.gridDonViDaGan.list.map(item => item.DONVI_ID).join(','),          //ds_donvi.join(","),
        dsNhanVien: ''
      }
      const response = await this.apiCapNhatCauHinh(param)
      if (response) this.gridColumn.list = await this.apiDanhSachCauHinh()
    },
    async onBtnHuyGanClicked() {
      // valid
      if (!this.gridColumn.value) return this.$root.toastError('Bạn chưa chọn cấu hình cần xóa trên lưới!');

      const confirm = await this.$confirm('Xác nhận xóa cấu hình đang chọn?', 'Xác nhận thực hiện', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'info'
      })
        .then(() => true)
        .catch(() => false)
      if (!confirm) return;
      
      const param = {
          pChuDeKNId: this.gridColumn.value.ID_CAP2,
          pLoaiTBId: this.gridColumn.value.LOAITB_ID,
          pDonViId: this.gridColumn.value.DONVI_ID,
      }
      const response = await this.apiXoaCauHinhDonVi(param)
      //reload page
      if (response) this.gridColumn.list = await this.apiDanhSachCauHinh();
    },
    async onTreeDonViNodeSelected(e) {
      this.gridDonViChuaGan.list = await this.apiGetDanhMucTheoTen('CAY_DONVI_2CAP', e.nodeData.id, 0);
      
      this.cboDichvuVT.list = await this.apiGetDanhMucTheoTen('DICHVU_VT', 0, 0);
      if (this.cboDichvuVT.list.length > 0) {
        this.cboDichvuVT.value = this.cboDichvuVT.list[0].DICHVUVT_ID
        await this.onChangeDichVuVT(this.cboDichvuVT.value)
      }
      this.cboNhomChuDe.list = await this.apiGetDanhMucTheoTen('NHOM_CHUDE', 0, 0);
      if (this.cboNhomChuDe.list.length > 0) {
        this.cboNhomChuDe.value = this.cboNhomChuDe.list[1].NHOM_ID
        await this.onChangeNhomChuDe(this.cboNhomChuDe.value)
      }
    },
    onTxtTreeDonViSearchEntered() {
      this.loading(true)
      try {
        let predicate
        let predicats = []
        let _array = []
        let _filter = []
        if (this.txtTreeDonViSearch.value != null && this.txtTreeDonViSearch.value.trim() !== '') {
          predicate = new Predicate('TEN_DV', 'contains', this.txtTreeDonViSearch.value.trim(), true)
          let filteredList = new DataManager(this.danhsachDonViTree).executeLocal(new Query().where(predicate))
          for (let j = 0; j < filteredList.length; j++) {
            _filter.push(filteredList[j]['DONVI_ID'])
            let filters = this.getFilterItems(filteredList[j], this.danhsachDonViTree)
            for (let i = 0; i < filters.length; i++) {
              if (_array.indexOf(filters[i]) === -1 && filters[i] != null) {
                _array.push(filters[i])
                predicats.push(new Predicate('DONVI_ID', 'equal', filters[i], false))
              }
            }
          }
          if (predicats.length === 0) {
            this.changeDataSource([])
          } else {
            let query = new Query().where(Predicate.or(predicats))
            let newList = new DataManager(this.danhsachDonViTree).executeLocal(query)
            this.changeDataSource(newList)
            
          }
        } else {
          this.changeDataSource(this.danhsachDonViTree)
        }
        //setTimeout(() => { this.$refs.treeDonVi.expandAll() }, 500)
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },
    changeDataSource (data) {
      this.fieldsDonVi = {
          dataSource: this.createTreeDonVi(data),
          id: 'DONVI_ID',
          parentID: 'DONVICHA_ID',
          text: 'TEN_DV',
          hasChildren: 'hasChild',
          expanded: 'expanded',
        }
    },
    getFilterItems (fList, list) {
      let nodes = []
      nodes.push(fList['DONVI_ID'])
      let query2 = new Query().where('DONVI_ID', 'equal', fList['DONVICHA_ID'], false)
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
    donViMultiLeft() {
      console.log('donViMultiLeft');
      const data = this.$refs.gridDonViDaGan.getSelectedRecords()
      if (data.length > 0) {
        const merged = [...this.gridDonViChuaGan.list, ...data]
        this.gridDonViChuaGan.list = Array.from(new Map(merged.map(item => [item.DONVI_ID, item])).values());
        const idList = data.map(item => item.DONVI_ID)
        this.gridDonViDaGan.list = this.gridDonViDaGan.list.filter(item => !idList.includes(item.DONVI_ID))
      }
    },
    donViMultiRight() {
      console.log('donViMultiRight');
      const data = this.$refs.gridDonViChuaGan.getSelectedRecords()
      if (data.length > 0) {
        const merged = [...this.gridDonViDaGan.list, ...data]
        this.gridDonViDaGan.list = Array.from(new Map(merged.map(item => [item.DONVI_ID, item])).values());
        const idList = data.map(item => item.DONVI_ID)
        this.gridDonViChuaGan.list = this.gridDonViChuaGan.list.filter(item => !idList.includes(item.DONVI_ID)) 
      
      }
    },
    donViMultiLeftAll() {
      const merged = [...this.gridDonViChuaGan.list, ...this.gridDonViDaGan.list]
      this.gridDonViChuaGan.list = Array.from(new Map(merged.map(item => [item.DONVI_ID, item])).values());
      this.gridDonViDaGan.list = []
    },
    donViMultiRightAll() {
      const merged = [...this.gridDonViDaGan.list, ...this.gridDonViChuaGan.list]
      this.gridDonViDaGan.list = Array.from(new Map(merged.map(item => [item.DONVI_ID, item])).values());
      this.gridDonViChuaGan.list = []
    },
    chuDeMultiLeft() {
      const data = this.$refs.gridChuDeDaGan.getSelectedRecords()
      if (data.length > 0) {
        const merged = [...this.gridChuDeChuaGan.list, ...data]
        this.gridChuDeChuaGan.list = Array.from(new Map(merged.map(item => [item.CHUDE_CAP2_ID, item])).values());
        const idList = data.map(item => item.CHUDE_CAP2_ID)
        this.gridChuDeDaGan.list = this.gridChuDeDaGan.list.filter(item => !idList.includes(item.CHUDE_CAP2_ID))
      }
    },
    chuDeMultiRight() {
      const data = this.$refs.gridChuDeChuaGan.getSelectedRecords()
      if (data.length > 0) {
        const merged = [...this.gridChuDeDaGan.list, ...data]
        this.gridChuDeDaGan.list = Array.from(new Map(merged.map(item => [item.CHUDE_CAP2_ID, item])).values());
        const idList = data.map(item => item.CHUDE_CAP2_ID)
        this.gridChuDeChuaGan.list = this.gridChuDeChuaGan.list.filter(item => !idList.includes(item.CHUDE_CAP2_ID)) 
      }
    },
    chuDeMultiLeftAll() {
      const merged = [...this.gridChuDeChuaGan.list, ...this.gridChuDeDaGan.list]
      this.gridChuDeChuaGan.list = Array.from(new Map(merged.map(item => [item.CHUDE_CAP2_ID, item])).values());
      this.gridChuDeDaGan.list = []
    },
    chuDeMultiRightAll() {
      const merged = [...this.gridChuDeDaGan.list, ...this.gridChuDeChuaGan.list]
      this.gridChuDeDaGan.list = Array.from(new Map(merged.map(item => [item.CHUDE_CAP2_ID, item])).values());
      this.gridChuDeChuaGan.list = []
    },
    loaiTBMultiLeft() {
      const data = this.$refs.gridLoaiHinhDaGan.getSelectedRecords()
      if (data.length > 0) {
        const merged = [...this.gridLoaiHinhChuaGan.list, ...data]
        this.gridLoaiHinhChuaGan.list = Array.from(new Map(merged.map(item => [item.LOAITB_ID, item])).values());
        const idList = data.map(item => item.LOAITB_ID)
        this.gridLoaiHinhDaGan.list = this.gridLoaiHinhDaGan.list.filter(item => !idList.includes(item.LOAITB_ID))
      }
    },
    loaiTBMultiRight() {
      const data = this.$refs.gridLoaiHinhChuaGan.getSelectedRecords()
      if (data.length > 0) {
        const merged = [...this.gridLoaiHinhDaGan.list, ...data]
        this.gridLoaiHinhDaGan.list = Array.from(new Map(merged.map(item => [item.LOAITB_ID, item])).values());
        const idList = data.map(item => item.LOAITB_ID)
        this.gridLoaiHinhChuaGan.list = this.gridLoaiHinhChuaGan.list.filter(item => !idList.includes(item.LOAITB_ID))
      }
    },
    loaiTBMultiLeftAll() {
      const merged = [...this.gridLoaiHinhChuaGan.list, ...this.gridLoaiHinhDaGan.list]
      this.gridLoaiHinhChuaGan.list = Array.from(new Map(merged.map(item => [item.LOAITB_ID, item])).values());
      this.gridLoaiHinhDaGan.list = []
    },
    loaiTBMultiRightAll() {
      const merged = [...this.gridLoaiHinhDaGan.list, ...this.gridLoaiHinhChuaGan.list]
      this.gridLoaiHinhDaGan.list = Array.from(new Map(merged.map(item => [item.LOAITB_ID, item])).values());
      this.gridLoaiHinhChuaGan.list = []
    },
    async gridColumnOnSelectedRowChanged(e) {
      console.log('gridColumnOnSelectedRowChanged', e);
      this.gridColumn.value = e
      if (!e) this.onResetFormCauHinh()
      
      // ĐƠN VỊ
      this.gridDonViChuaGan.list = await this.apiGetDanhMucTheoTen('CAY_DONVI_2CAP', e.DONVI_ID, 0);
      this.gridDonViDaGan.list = this.gridDonViChuaGan.list.filter(item => item.DONVI_ID == e.DONVI_ID)
      this.gridDonViChuaGan.list = this.gridDonViChuaGan.list.filter(item => item.DONVI_ID != e.DONVI_ID)
      
      // LOẠI TB
      this.cboDichvuVT.value = e.DICHVUVT_ID
      await this.onChangeDichVuVT(e.DICHVUVT_ID)
      this.gridLoaiHinhDaGan.list = this.gridLoaiHinhChuaGan.list.filter(item => item.LOAITB_ID == e.LOAITB_ID)
      this.gridLoaiHinhChuaGan.list = this.gridLoaiHinhChuaGan.list.filter(item => item.LOAITB_ID != e.LOAITB_ID)
      
      // CHỦ ĐỀ
      this.cboNhomChuDe.value = e.ID_NHOM
      await this.onChangeNhomChuDe(e.ID_NHOM)              
      this.gridChuDeDaGan.list = this.gridChuDeChuaGan.list.filter(item => item.CHUDE_CAP2_ID == e.ID_CAP2)
      this.gridChuDeChuaGan.list = this.gridChuDeChuaGan.list.filter(item => item.CHUDE_CAP2_ID != e.ID_CAP2)
    },
    async onChangeDichVuVT(val) {
      this.gridLoaiHinhChuaGan.list = await this.apiGetDanhMucTheoTen('LOAIHINH_TB', val, 0)
    },
    async onChangeNhomChuDe(val) {
      this.gridChuDeChuaGan.list = await this.apiGetDanhMucTheoTen('CAY_CHUDE_NGANG', val, 0)
    } 
  },
}
</script>
<style scoped></style>
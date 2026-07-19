<template src="./EmployeeAssignmentCriteria.html"></template>
<script>
import breadcrumb from '@/components/breadcrumb'
import api from "./api/index.js";
import DepartmentAPI from '@/modules/admin/category/Department/API.js';
import DataGridCustom from "./components/DataGrid";
import collums from "./define/collums.js";
import {DataManager, Predicate, Query} from '@syncfusion/ej2-data'
import TimKiemNhanVien from './TimKiemNhanVien.vue'

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
    name:'EmployeeAssignmentCriteria',
    components:{
        breadcrumb,
        DataGridCustom,
        TimKiemNhanVien
    },
    data() {
        return {
            header: {
                title: 'Gán nhân viên - tiêu chí',
                list: [
                    { name: 'Quản trị hệ thống', link: { name: 'Ui.cards' } },
                    { name: 'Gán nhân viên - tiêu chí', link: { name: 'Ui.cards' } },
                ]
            },
            ...collums,
            selectedNodes: [0, 1],
            expandedNodes: [0],
            danhsach_donvi: [],
            fields: '',
            donviId: -1,
            searchDonViValue: '',
            currentDonVi: Object.assign(DONVIOBJ),
            displayDonViId: 'ID: ',
            danhsachDonViTree: [],
            danhsachFilterTree: [],
            defaultDsDonViTree: [],

            ds_nhanvien: [],
            nhanvien_id: null,

            // loai nhan vien
            ds_lnv_dagan: [],
            ds_lnv_dagan_selected: [],
            ds_lnv_chuagan: [],
            ds_lnv_chuagan_selected: [],
            ds_lnv_selected: [],
            act_lnv: null,

            // loai khach hang
            ds_lkh_dagan: [],
            ds_lkh_dagan_selected: [],
            ds_lkh_chuagan: [],
            ds_lkh_chuagan_selected: [],
            ds_lkh_selected: [],
            act_lkh: null,

            // nganh nghe
            ds_nn_dagan: [],
            ds_nn_dagan_selected: [],
            ds_nn_chuagan: [],
            ds_nn_chuagan_selected: [],
            ds_nn_selected: [],
            act_nn: null,

            // doanh thu
            ds_dt_dagan: [],
            ds_dt_dagan_selected: [],
            doanhthu_tu: "0",
            doanhthu_den: "0",

            // diem tin nhiem
            ds_dtn_dagan: [],
            ds_dtn_dagan_selected: [],
            diemtn_tu: "0",
            diemtn_den: "0",

            sort_type_az: true,
        }
    },
    computed: {

    },
    async created() {
        await this.displayTreeView();
        $("#treeview").find('.e-level-1').eq(0).addClass('e-active e-node-focus');
    },
    methods: {
        async init() {
            try {
                this.loading(true);
                await this.loadListView(this.$root.token.getDonViID(), 1, null);
            } catch (e) {
                this.$toast.error("Khởi tạo thất bại " + e);
            } finally {
                this.loading(false);
            }        
        },
        async SP_GIAOSIM_GET_TREEVIEW(donviChaId) {
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
        async getDanhSachDonVi () {
            try {
                this.loading(true)
                this.danhsach_donvi = await this.SP_GIAOSIM_GET_TREEVIEW(0)
            } catch (e) {
                console.log(e)
                this.$toast.error('Không load được danh sách đơn vị')
            } finally {
                this.loading(false)
            }
        },
        async sortTreeDonVi () {
            this.sort_type_az = !this.sort_type_az
            let donViTree = this.createTreeDonVi(this.danhsach_donvi)
            this.defaultDsDonViTree = donViTree
            if (this.sort_type_az) {
                donViTree.sort((a, b) => {
                    return a.ten_dv.localeCompare(b.ten_dv)
                })
            } else {
                donViTree.sort((a, b) => {
                    return b.ten_dv.localeCompare(a.ten_dv)
                })
            }
    
            this.danhsachDonViTree = donViTree
            this.fields = {
                dataSource: donViTree,
                id: 'donvi_id',
                text: 'ten_dv',
                parentID: 'donvi_cha_id',
                hasChildren: 'hasChild',
                expanded: 'expanded'
            }
        },
        nodeClicked (args) {
        },
        async get_donvi_root (donviId) {
            let donvi = {}
            console.log('root ', this.donviId)
            await DepartmentAPI.get_donvi(this.axios, {
                'p_donvi_id': donviId
            }).then(response => {
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    let data = response.data.data[0]
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
        async displayTreeView () {
            let departmentUserId = Number.parseInt(this.$root.token.getDonViID())
            let root = await this.get_donvi_root(departmentUserId)
            let dsDv = await this.sp_lay_ds_donvi_donvicha(departmentUserId)
            if (dsDv.length === 0) {
                delete root.donvi_cha_id
            }
            dsDv.push(root)
            this.danhsach_donvi = dsDv
            let donViTree = this.createTreeDonVi(dsDv)
            this.defaultDsDonViTree = donViTree
            donViTree.sort((a, b) => {
                return a.ten_dv.localeCompare(b.ten_dv)
            })
            this.danhsachDonViTree = donViTree
            this.fields = {
                dataSource: donViTree,
                id: 'donvi_id',
                text: 'ten_dv',
                parentID: 'donvi_cha_id',
                hasChildren: 'hasChild',
                expanded: 'expanded'
            }
        },
        createTreeDonVi (dsDonVi) {
            let tree = []
            let donviChaArray = dsDonVi.map((item) => {
                return item['donvi_cha_id']
            })
            let donviArray = dsDonVi.map(item => item['donvi_id'])
            dsDonVi.forEach(item => {
                if (donviChaArray.includes(item['donvi_id'])) {
                    if (!donviArray.includes(item['donvi_cha_id'])) {
                        delete item.donvi_cha_id
                    }
                    tree.push({...item, hasChild: true, expanded: true})
                } else {
                    tree.push(item)
                }
            })
            let departmentUserId = Number.parseInt(this.$root.token.getDonViID())
            const usrDonViIdx = tree.findIndex(item => item.donvi_id === departmentUserId)
            if (usrDonViIdx !== -1) {
                tree[usrDonViIdx].isSelected = true
                tree[usrDonViIdx].expanded = true
            }
            return tree
        },
        changeDataSource (data) {
            this.fields = {
                dataSource: data, id: 'donvi_id', text: 'ten_dv', parentID: 'donvi_cha_id', hasChildren: 'hasChild'
            }
        },
        searchDonVi () {
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
                    this.$toast.error("Không tìm thấy đơn vị")
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
        },
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

        // xu ly
        refresh() {
            this.ds_nhanvien = [];
            this.nhanvien_id = null;

            this.ds_lnv_dagan = [];
            this.ds_lnv_dagan_selected = [];
            this.ds_lnv_chuagan = [];
            this.ds_lnv_chuagan_selected = [];
            this.ds_lnv_selected = [];
            this.act_lnv = null;

            this.ds_lkh_dagan = [];
            this.ds_lkh_dagan_selected = [];
            this.ds_lkh_chuagan = [];
            this.ds_lkh_chuagan_selected = [];
            this.ds_lkh_selected = [];
            this.act_lkh = null;

            this.ds_nn_dagan = [];
            this.ds_nn_dagan_selected = [];
            this.ds_nn_chuagan = [];
            this.ds_nn_chuagan_selected = [];
            this.ds_nn_selected = [];
            this.act_nn = null;

            this.ds_dt_dagan = [];
            this.ds_dt_dagan_selected = [];
            this.doanhthu_tu = "0";
            this.doanhthu_den = "0";

            this.ds_dtn_dagan = [];
            this.ds_dtn_dagan_selected = [];
            this.diemtn_tu = "0";
            this.diemtn_den = "0";
        },
        async selectDonViFocusedNodeChange (node) {
            try {
                this.loading(true);
                let donviId = Number(node.nodeData.id);
                await this.loadListView(donviId, 1, null);
            } catch(e) {
                this.$toast.console.error("Có lỗi xảy ra: " + e);
            } finally {
                this.loading(false);
            }
        },
        async loadListView(donviId, kieu, vnhanvien_id) {
            this.refresh();
            try {
                const params = {
                    p_donvi_id: donviId,
                    p_kieu: kieu
                }
                let response = await api.ur_006_sp_ds_nhanvien_donvi(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.ds_nhanvien = response.data.data;
                    if (this.ds_nhanvien) {
                        if (vnhanvien_id != null) {
                            let nvTemp = {};
                            let ixRemove = 0;
                            for (let i=0; i<this.ds_nhanvien.length; i++) {
                                if (this.ds_nhanvien[i].nhanvien_id == vnhanvien_id) {
                                    nvTemp = this.ds_nhanvien[i]
                                    ixRemove = i;
                                    break;
                                }
                            }

                            for (let i=0; i<this.ds_nhanvien.length; i++) {
                                if (i < ixRemove) {
                                    this.ds_nhanvien[i].STT = i+2;
                                } else if (i > ixRemove) {
                                    this.ds_nhanvien[i].STT = i+1;
                                } else {
                                    this.ds_nhanvien[i].STT = 1;
                                }
                            }
                            this.ds_nhanvien.splice(ixRemove, 1);
                            this.ds_nhanvien.unshift(nvTemp);
                        } else {
                            for (let i=0; i<this.ds_nhanvien.length; i++) {
                                this.ds_nhanvien[i].STT = i+1;
                            }
                        }
                    } 
                }       
            } catch (e) {
                console.log(e);
            }        
        },
        async gridNV_rowSelected(row) {
            try {
                this.loading(true);
                if (row.data && row.data.nhanvien_id) {
                    await this.gridviewDanhSach_FocusedRowChanged(row.data.nhanvien_id);
                }        
            } catch(e) {
                this.$toast.console.error("Có lỗi xảy ra: " + e);
            } finally {
                this.loading(false);
            }
        },
        async gridviewDanhSach_FocusedRowChanged(nhanvien_id) {
            this.nhanvien_id = Number(nhanvien_id);

            this.ds_lnv_dagan_selected = [];
            this.ds_lnv_chuagan_selected = [];
            this.ds_lnv_selected = [];
            this.act_lnv = null;
            this.ds_lnv_dagan = await this.LAY_DS_LKH_PHANTAP_NV(1, this.nhanvien_id);
            this.ds_lnv_chuagan = await this.LAY_DS_LKH_PHANTAP_NV(2, this.nhanvien_id);

            this.ds_lkh_dagan_selected = [];
            this.ds_lkh_chuagan_selected = [];
            this.ds_lkh_selected = [];
            this.act_lkh = null;
            this.ds_lkh_dagan = await this.lay_danhsach_lkh_nv_v2(1, this.nhanvien_id);
            this.ds_lkh_chuagan = await this.lay_danhsach_lkh_nv_v2(2, this.nhanvien_id);

            this.ds_nn_dagan_selected = [];
            this.ds_nn_chuagan_selected = [];
            this.ds_nn_selected = [];
            this.act_nn = null;
            this.ds_nn_dagan = await this.lay_danhsach_nn_nv(1, this.nhanvien_id);
            this.ds_nn_chuagan = await this.lay_danhsach_nn_nv(2, this.nhanvien_id);

            this.doanhthu_tu = "0";
            this.doanhthu_den = "0";
            this.ds_dt_dagan_selected = [];
            this.ds_dt_dagan = await this.lay_danhsach_dt_nv(this.nhanvien_id);

            this.diemtn_tu = "0";
            this.diemtn_den = "0";
            this.ds_dtn_dagan_selected = [];
            this.ds_dtn_dagan = await this.lay_danhsach_dtn_nv(this.nhanvien_id);
        },
        async LAY_DS_LKH_PHANTAP_NV(vloai, vnhanvien_id) {
            let result = [];
            try {
                const params = {
                    vloai: vloai,
                    vnhanvien_id: vnhanvien_id
                }
                let response = await api.ur_009_lay_ds_lkh_phantap_nv(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data;
                }       
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        async lay_danhsach_lkh_nv_v2(vloai, vnhanvien_id) {
            let result = [];
            try {
                const params = {
                    vloai: vloai,
                    vnhanvien_id: vnhanvien_id
                }
                let response = await api.ur_010_lay_danhsach_lkh_nv_v2(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data;
                }       
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        async lay_danhsach_nn_nv(vloai, vnhanvien_id) {
            let result = [];
            try {
                const params = {
                    vloai: vloai,
                    vnhanvien_id: vnhanvien_id
                }
                let response = await api.ur_011_lay_danhsach_nn_nv(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data;
                }       
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        async lay_danhsach_dt_nv(vnhanvien_id) {
            let result = [];
            try {
                const params = {
                    vnhanvien_id: vnhanvien_id
                }
                let response = await api.ur_012_lay_danhsach_dt_nv(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data;
                    result.forEach((item) => {
                        item.doanhthu_tu = this.formatNumber(item.doanhthu_tu)
                        item.doanhthu_den = this.formatNumber(item.doanhthu_den)
                    })
                }       
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        async lay_danhsach_dtn_nv(vnhanvien_id) {
            let result = [];
            try {
                const params = {
                    vnhanvien_id: vnhanvien_id
                }
                let response = await api.ur_014_lay_danhsach_dtn_nv(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data;
                }       
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        gridLoaiNVDaGan_SelectRow (val) {
            this.ds_lnv_dagan_selected = [];
            for (let index in val) {
                for (let ix in this.ds_lnv_dagan) {
                    if (val[index].loainv_id == this.ds_lnv_dagan[ix].loainv_id) {
                        this.ds_lnv_dagan_selected.push(val[index]);
                    }
                }
            }
        },
        gridLoaiNVChuaGan_SelectRow (val) {
            this.ds_lnv_chuagan_selected = [];
            for (let index in val) {
                for (let ix in this.ds_lnv_chuagan) {
                    if (val[index].loainv_id == this.ds_lnv_chuagan[ix].loainv_id) {
                        this.ds_lnv_chuagan_selected.push(val[index]);
                    }
                }
            }
        },
        gridLoaiKHDaGan_SelectRow (val) {
            this.ds_lkh_dagan_selected = [];
            for (let index in val) {
                for (let ix in this.ds_lkh_dagan) {
                    if (val[index].loaikh_id == this.ds_lkh_dagan[ix].loaikh_id) {
                        this.ds_lkh_dagan_selected.push(val[index]);
                    }
                }
            }
        },
        gridLoaiKHChuaGan_SelectRow (val) {
            this.ds_lkh_chuagan_selected = [];
            for (let index in val) {
                for (let ix in this.ds_lkh_chuagan) {
                    if (val[index].loaikh_id == this.ds_lkh_chuagan[ix].loaikh_id) {
                        this.ds_lkh_chuagan_selected.push(val[index]);
                    }
                }
            }
        },
        gridNNDaGan_SelectRow (val) {
            this.ds_nn_dagan_selected = [];
            for (let index in val) {
                for (let ix in this.ds_nn_dagan) {
                    if (val[index].nganhnghe_id == this.ds_nn_dagan[ix].nganhnghe_id) {
                        this.ds_nn_dagan_selected.push(val[index]);
                    }
                }
            }
        },
        gridNNChuaGan_SelectRow (val) {
            this.ds_nn_chuagan_selected = [];
            for (let index in val) {
                for (let ix in this.ds_nn_chuagan) {
                    if (val[index].nganhnghe_id == this.ds_nn_chuagan[ix].nganhnghe_id) {
                        this.ds_nn_chuagan_selected.push(val[index]);
                    }
                }
            }
        },
        gridDTDaGan_SelectRow (val) {
            this.ds_dt_dagan_selected = [];
            for (let index in val) {
                for (let ix in this.ds_dt_dagan) {
                    if (val[index].doanhthu_tu == this.ds_dt_dagan[ix].doanhthu_tu &&
                        val[index].doanhthu_den == this.ds_dt_dagan[ix].doanhthu_den) {
                        this.ds_dt_dagan_selected.push(val[index]);
                    }
                }
            }
        },
        gridDTNDaGan_SelectRow (val) {
            this.ds_dtn_dagan_selected = [];
            for (let index in val) {
                for (let ix in this.ds_dtn_dagan) {
                    if (val[index].diemtn_tu == this.ds_dtn_dagan[ix].diemtn_tu &&
                        val[index].diemtn_den == this.ds_dtn_dagan[ix].diemtn_den) {
                        this.ds_dtn_dagan_selected.push(val[index]);
                    }
                }
            }
        },
        gridLoaiNV_complete() {   
            if (this.act_lnv == 1) {  // lnvToRight
                this.$refs.ds_lnv_chuagan.$refs.grid.selectRows(this.ds_lnv_selected);         
            } else if (this.act_lnv == 2) {  // lnvToLeft      
                this.$refs.ds_lnv_dagan.$refs.grid.selectRows(this.ds_lnv_selected);
            }
        },
        lnvToRight() {
            if (this.ds_lnv_dagan_selected.length > 0) {
                let temp = Object.assign({}, this.ds_lnv_dagan_selected);
                this.act_lnv = 1;
                try {
                    if (this.ds_lnv_dagan === undefined) {
                        this.ds_lnv_dagan = []
                    }
                    if (this.ds_lnv_chuagan === undefined) {
                        this.ds_lnv_chuagan = []
                    }

                    this.ds_lnv_chuagan = this.ds_lnv_chuagan.concat(this.ds_lnv_dagan_selected)
                    for (let index in this.ds_lnv_dagan_selected) {
                        let ixRemove
                        for (let ix in this.ds_lnv_dagan) {
                            if (this.ds_lnv_dagan[ix].loainv_id === this.ds_lnv_dagan_selected[index].loainv_id) {
                                ixRemove = ix
                                break
                            }
                        }
                        this.ds_lnv_dagan.splice(ixRemove, 1)
                    }         
                    
                    this.ds_lnv_selected = [];
                    this.ds_lnv_dagan_selected = [];
                    this.ds_lnv_chuagan_selected = Object.assign({}, temp);
                    for (let index in this.ds_lnv_chuagan) {
                        for (let ix in this.ds_lnv_chuagan_selected) {
                            if (this.ds_lnv_chuagan[index].loainv_id == this.ds_lnv_chuagan_selected[ix].loainv_id) {
                                this.ds_lnv_selected.push(index);
                            }
                        }
                    }
                } catch (err) {
                    console.log(err)
                }
            }
        }, 
        lnvToLeft() { 
            if (this.ds_lnv_chuagan_selected.length > 0) {
                let temp = Object.assign({}, this.ds_lnv_chuagan_selected);
                this.act_lnv = 2;
                try {
                    if (this.ds_lnv_dagan === undefined) {
                        this.ds_lnv_dagan = []
                    }
                    if (this.ds_lnv_chuagan === undefined) {
                        this.ds_lnv_chuagan = []
                    }

                    this.ds_lnv_dagan = this.ds_lnv_dagan.concat(this.ds_lnv_chuagan_selected)
                    for (let index in this.ds_lnv_chuagan_selected) {
                        let ixRemove
                        for (let ix in this.ds_lnv_chuagan) {
                            if (this.ds_lnv_chuagan[ix].loainv_id === this.ds_lnv_chuagan_selected[index].loainv_id) {
                                ixRemove = ix
                                break
                            }
                        }
                        this.ds_lnv_chuagan.splice(ixRemove, 1)
                    }
                    this.ds_lnv_chuagan = [...this.ds_lnv_chuagan];

                    this.ds_lnv_selected = [];
                    this.ds_lnv_chuagan_selected = [];
                    this.ds_lnv_dagan_selected = Object.assign({}, temp);
                    for (let index in this.ds_lnv_dagan) {
                        for (let ix in this.ds_lnv_dagan_selected) {
                            if (this.ds_lnv_dagan[index].loainv_id == this.ds_lnv_dagan_selected[ix].loainv_id) {
                                this.ds_lnv_selected.push(index);
                            }
                        }
                    }               
                } catch (err) {
                    console.log(err)
                }
            }
        },
        gridLoaiKH_complete() {   
            if (this.act_lkh == 1) {  // lkhToRight
                this.$refs.ds_lkh_chuagan.$refs.grid.selectRows(this.ds_lkh_selected);         
            } else if (this.act_lkh == 2) {  // lkhToLeft      
                this.$refs.ds_lkh_dagan.$refs.grid.selectRows(this.ds_lkh_selected);
            }
        },
        lkhToRight() {
            if (this.ds_lkh_dagan_selected.length > 0) {
                let temp = Object.assign({}, this.ds_lkh_dagan_selected);
                this.act_lkh = 1;
                try {
                    if (this.ds_lkh_dagan === undefined) {
                        this.ds_lkh_dagan = []
                    }
                    if (this.ds_lkh_chuagan === undefined) {
                        this.ds_lkh_chuagan = []
                    }

                    this.ds_lkh_chuagan = this.ds_lkh_dagan_selected.concat(this.ds_lkh_chuagan)
                    for (let index in this.ds_lkh_dagan_selected) {
                        let ixRemove
                        for (let ix in this.ds_lkh_dagan) {
                            if (this.ds_lkh_dagan[ix].loaikh_id === this.ds_lkh_dagan_selected[index].loaikh_id) {
                                ixRemove = ix
                                break
                            }
                        }
                        this.ds_lkh_dagan.splice(ixRemove, 1)
                    }         
                    
                    this.ds_lkh_selected = [];
                    this.ds_lkh_dagan_selected = [];
                    this.ds_lkh_chuagan_selected = Object.assign({}, temp);
                    for (let index in this.ds_lkh_chuagan) {
                        for (let ix in this.ds_lkh_chuagan_selected) {
                            if (this.ds_lkh_chuagan[index].loaikh_id == this.ds_lkh_chuagan_selected[ix].loaikh_id) {
                                this.ds_lkh_selected.push(index);
                            }
                        }
                    }
                } catch (err) {
                    console.log(err)
                }
            }
        }, 
        lkhToLeft() { 
            if (this.ds_lkh_chuagan_selected.length > 0) {
                let temp = Object.assign({}, this.ds_lkh_chuagan_selected);
                this.act_lkh = 2;
                try {
                    if (this.ds_lkh_dagan === undefined) {
                        this.ds_lkh_dagan = []
                    }
                    if (this.ds_lkh_chuagan === undefined) {
                        this.ds_lkh_chuagan = []
                    }

                    this.ds_lkh_dagan = this.ds_lkh_chuagan_selected.concat(this.ds_lkh_dagan)
                    for (let index in this.ds_lkh_chuagan_selected) {
                        let ixRemove
                        for (let ix in this.ds_lkh_chuagan) {
                            if (this.ds_lkh_chuagan[ix].loaikh_id === this.ds_lkh_chuagan_selected[index].loaikh_id) {
                                ixRemove = ix
                                break
                            }
                        }
                        this.ds_lkh_chuagan.splice(ixRemove, 1)
                    }
                    this.ds_lkh_chuagan = [...this.ds_lkh_chuagan];

                    this.ds_lkh_selected = [];
                    this.ds_lkh_chuagan_selected = [];
                    this.ds_lkh_dagan_selected = Object.assign({}, temp);
                    for (let index in this.ds_lkh_dagan) {
                        for (let ix in this.ds_lkh_dagan_selected) {
                            if (this.ds_lkh_dagan[index].loaikh_id == this.ds_lkh_dagan_selected[ix].loaikh_id) {
                                this.ds_lkh_selected.push(index);
                            }
                        }
                    }               
                } catch (err) {
                    console.log(err)
                }
            }
        },
        gridNN_complete() {   
            if (this.act_nn == 1) {  // nnToRight
                this.$refs.ds_nn_chuagan.$refs.grid.selectRows(this.ds_nn_selected);         
            } else if (this.act_nn == 2) {  // nnToLeft      
                this.$refs.ds_nn_dagan.$refs.grid.selectRows(this.ds_nn_selected);
            }
        },
        nnToRight() {
            if (this.ds_nn_dagan_selected.length > 0) {
                let temp = Object.assign({}, this.ds_nn_dagan_selected);
                this.act_nn = 1;
                try {
                    if (this.ds_nn_dagan === undefined) {
                        this.ds_nn_dagan = []
                    }
                    if (this.ds_nn_chuagan === undefined) {
                        this.ds_nn_chuagan = []
                    }

                    this.ds_nn_chuagan = this.ds_nn_dagan_selected.concat(this.ds_nn_chuagan)
                    for (let index in this.ds_nn_dagan_selected) {
                        let ixRemove
                        for (let ix in this.ds_nn_dagan) {
                            if (this.ds_nn_dagan[ix].nganhnghe_id === this.ds_nn_dagan_selected[index].nganhnghe_id) {
                                ixRemove = ix
                                break
                            }
                        }
                        this.ds_nn_dagan.splice(ixRemove, 1)
                    }         
                    
                    this.ds_nn_selected = [];
                    this.ds_nn_dagan_selected = [];
                    this.ds_nn_chuagan_selected = Object.assign({}, temp);
                    for (let index in this.ds_nn_chuagan) {
                        for (let ix in this.ds_nn_chuagan_selected) {
                            if (this.ds_nn_chuagan[index].nganhnghe_id == this.ds_nn_chuagan_selected[ix].nganhnghe_id) {
                                this.ds_nn_selected.push(index);
                            }
                        }
                    }
                } catch (err) {
                    console.log(err)
                }
            }
        }, 
        nnToLeft() { 
            if (this.ds_nn_chuagan_selected.length > 0) {
                let temp = Object.assign({}, this.ds_nn_chuagan_selected);
                this.act_nn = 2;
                try {
                    if (this.ds_nn_dagan === undefined) {
                        this.ds_nn_dagan = []
                    }
                    if (this.ds_nn_chuagan === undefined) {
                        this.ds_nn_chuagan = []
                    }

                    this.ds_nn_dagan = this.ds_nn_chuagan_selected.concat(this.ds_nn_dagan)
                    for (let index in this.ds_nn_chuagan_selected) {
                        let ixRemove
                        for (let ix in this.ds_nn_chuagan) {
                            if (this.ds_nn_chuagan[ix].nganhnghe_id === this.ds_nn_chuagan_selected[index].nganhnghe_id) {
                                ixRemove = ix
                                break
                            }
                        }
                        this.ds_nn_chuagan.splice(ixRemove, 1)
                    }
                    this.ds_nn_chuagan = [...this.ds_nn_chuagan];

                    this.ds_nn_selected = [];
                    this.ds_nn_chuagan_selected = [];
                    this.ds_nn_dagan_selected = Object.assign({}, temp);
                    for (let index in this.ds_nn_dagan) {
                        for (let ix in this.ds_nn_dagan_selected) {
                            if (this.ds_nn_dagan[index].nganhnghe_id == this.ds_nn_dagan_selected[ix].nganhnghe_id) {
                                this.ds_nn_selected.push(index);
                            }
                        }
                    }               
                } catch (err) {
                    console.log(err)
                }
            }
        },
        checkNumber(event) {
            if ((event.keyCode >= 96 && event.keyCode <= 105) || (event.keyCode >= 48 && event.keyCode <= 57) || event.keyCode == 8 || event.keyCode == 46) {
                return true;
            } else {
                event.preventDefault();    
            }
        },
        formatNumber(value) {
            let val = (value / 1).toFixed(0).replace(',', '.')
            return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
        },
        txtDoanhThuTu_Leave() {
            $("#doanhthu_tu").removeClass("invalid_in");
            $("#doanhthu_den").removeClass("invalid_in");
            this.doanhthu_tu = this.doanhthu_tu.toString().trim();
            this.doanhthu_tu = this.doanhthu_tu.toString().split(",").join("")
            if (this.doanhthu_tu != "") {
                this.doanhthu_tu = this.formatNumber(this.doanhthu_tu);
            } else {
                this.doanhthu_tu = "0";
            }
        },
        txtDoanhThuDen_Leave() {
            $("#doanhthu_tu").removeClass("invalid_in");
            $("#doanhthu_den").removeClass("invalid_in");
            this.doanhthu_den = this.doanhthu_den.toString().trim();
            this.doanhthu_den = this.doanhthu_den.toString().split(",").join("")
            if (this.doanhthu_den != "") {
                this.doanhthu_den = this.formatNumber(this.doanhthu_den);
            } else {
                this.doanhthu_den = "0";
            }
        },
        gridDT_complete() {
            this.$refs.ds_dt_dagan.$refs.grid.selectRow(0);
        },
        gridDT_rowSelected(row) {
            this.doanhthu_tu = row.data.doanhthu_tu;
            this.doanhthu_den = row.data.doanhthu_den;
            this.txtDoanhThuTu_Leave()
            this.txtDoanhThuDen_Leave()
        },
        dtToRight() {
            if (this.ds_dt_dagan_selected.length > 0) {  
                for (let index in this.ds_dt_dagan_selected) {
                    let ixRemove
                    for (let ix in this.ds_dt_dagan) {
                        if (this.ds_dt_dagan[ix].doanhthu_tu === this.ds_dt_dagan_selected[index].doanhthu_tu &&
                            this.ds_dt_dagan[ix].doanhthu_den === this.ds_dt_dagan_selected[index].doanhthu_den) {
                            ixRemove = ix
                            break
                        }
                    }
                    this.ds_dt_dagan.splice(ixRemove, 1)
                }
                this.ds_dt_dagan_selected = [];
            }
        },
        dtToLeft() {
            $("#doanhthu_tu").removeClass("invalid_in");
            $("#doanhthu_den").removeClass("invalid_in");
            if (this.doanhthu_tu.toString().trim().length > 18) {
                this.$toast.error("Giá trị Doanh thu từ quá lớn")
                $("#doanhthu_tu").focus()
                $("#doanhthu_tu").addClass("invalid_in");
                if (this.doanhthu_den.toString().trim().length > 18) {
                    this.$toast.error("Giá trị Doanh thu đến quá lớn")
                    $("#doanhthu_den").addClass("invalid_in");             
                }
                return
            } else {
                if (this.doanhthu_den.toString().trim().length > 18) {
                    this.$toast.error("Giá trị Doanh thu đến quá lớn")
                    $("#doanhthu_den").focus()
                    $("#doanhthu_den").addClass("invalid_in");
                    return
                }
            }     

            let dttu = this.doanhthu_tu.toString().split(",").join("");
            let dtden = this.doanhthu_den.toString().split(",").join("")
            if (Number(dtden) - Number(dttu) < 0) {
                this.$toast.error("Doanh thu đến phải lớn hơn hoặc bằng doanh thu từ");
                $("#doanhthu_den").focus();
                $("#doanhthu_den").addClass("invalid_in");
                return;
            }

            for (let dt of this.ds_dt_dagan) {
                if (dt.doanhthu_tu == this.doanhthu_tu && dt.doanhthu_den == this.doanhthu_den) {
                    this.$toast.error("Đã tồn tại bản ghi!");
                    return;
                }
            }

            let objDT = {doanhthu_tu: this.doanhthu_tu, doanhthu_den: this.doanhthu_den};
            this.ds_dt_dagan.unshift(objDT);
            this.ds_dt_dagan_selected = [];
            this.ds_dt_dagan_selected.push(objDT);       
        },
        txtDiemTNTu_Leave() {
            $("#diemtn_tu").removeClass("invalid_in");
            $("#diemtn_den").removeClass("invalid_in");
            if (this.diemtn_tu.trim() == "") {
                this.diemtn_tu = "0";
            }
        },
        txtDiemTNDen_Leave() {
            $("#diemtn_tu").removeClass("invalid_in");
            $("#diemtn_den").removeClass("invalid_in");
            if (this.diemtn_den.trim() == "") {
                this.diemtn_den = "0";
            }
        },
        gridDTN_complete() {
            this.$refs.ds_dtn_dagan.$refs.grid.selectRow(0);
        },
        gridDTN_rowSelected(row) {
            this.diemtn_tu = row.data.diemtn_tu;
            this.diemtn_den = row.data.diemtn_den;
        },
        dtnToRight() {
            if (this.ds_dtn_dagan_selected.length > 0) {  
                for (let index in this.ds_dtn_dagan_selected) {
                    let ixRemove
                    for (let ix in this.ds_dtn_dagan) {
                        if (this.ds_dtn_dagan[ix].diemtn_tu === this.ds_dtn_dagan_selected[index].diemtn_tu &&
                            this.ds_dtn_dagan[ix].diemtn_den === this.ds_dtn_dagan_selected[index].diemtn_den) {
                            ixRemove = ix
                            break
                        }
                    }
                    this.ds_dtn_dagan.splice(ixRemove, 1)
                }
                this.ds_dtn_dagan_selected = [];
            }
        },
        dtnToLeft() {
            $("#diemtn_tu").removeClass("invalid_in");
            $("#diemtn_den").removeClass("invalid_in");
            if (this.diemtn_tu.toString().trim().length > 18) {
                $("#diemtn_tu").addClass("invalid_in");
                this.$toast.error("Giá trị Điểm tín nhiệm từ quá lớn")
                $("#diemtn_tu").focus()
                if (this.diemtn_den.toString().trim().length > 18) {
                    this.$toast.error("Giá trị Điểm tín nhiệm đến quá lớn")
                    $("#diemtn_den").addClass("invalid_in");
                }
                return        
            } else {
                if (this.diemtn_den.toString().trim().length > 18) {
                    this.$toast.error("Giá trị Điểm tín nhiệm đến quá lớn")
                    $("#diemtn_den").addClass("invalid_in");
                    $("#diemtn_den").focus()
                    return
                }
            }
                   
            if (Number(this.diemtn_den) - Number(this.diemtn_tu) < 0) {
                this.$toast.error("Điểm tín nhiệm từ phải nhỏ hơn hoặc bằng diểm tín nhiệm đến!");
                $("#diemtn_den").focus();
                $("#diemtn_den").addClass("invalid_in");
                return;
            }

            for (let dtn of this.ds_dtn_dagan) {
                if (Number(dtn.diemtn_tu) == Number(this.diemtn_tu) && Number(dtn.diemtn_den) == Number(this.diemtn_den)) {
                    this.$toast.error("Đã tồn tại bản ghi!");
                    return;
                }
            }

            let objDTN = {diemtn_tu: this.diemtn_tu, diemtn_den: this.diemtn_den};
            this.ds_dtn_dagan.unshift(objDTN);
            this.ds_dtn_dagan_selected = [];
            this.ds_dtn_dagan_selected.push(objDTN);       
        },
        async tsbtnGhiLai_Click() {
            try {
                this.loading(true);
                let vdsnhanvien_dt = [], vdsnhanvien_dtn = [], vdsnhanvien_nn = [], vdsnhanvien_lkh = [];
                for (let dt of this.ds_dt_dagan) {
                    for (let lnv of this.ds_lnv_dagan) {
                        let temp = {NHANVIEN_ID: this.nhanvien_id, LOAINV_ID: lnv.loainv_id, DOANHTHU_TU: dt.doanhthu_tu.toString().split(",").join(""), DOANHTHU_DEN: dt.doanhthu_den.toString().split(",").join("")};
                        vdsnhanvien_dt.push(temp);
                    }          
                }
                for (let dtn of this.ds_dtn_dagan) {
                    for (let lnv of this.ds_lnv_dagan) {
                        let temp = {NHANVIEN_ID: this.nhanvien_id, LOAINV_ID: lnv.loainv_id, DIEMTN_TU: dtn.diemtn_tu, DIEMTN_DEN: dtn.diemtn_den};
                        vdsnhanvien_dtn.push(temp);
                    }          
                }
                for (let nn of this.ds_nn_dagan) {
                    for (let lnv of this.ds_lnv_dagan) {
                        let temp = {NHANVIEN_ID: this.nhanvien_id, LOAINV_ID: lnv.loainv_id, NGANHNGHE_ID: nn.nganhnghe_id};
                        vdsnhanvien_nn.push(temp);
                    }          
                }
                for (let lkh of this.ds_lkh_dagan) {
                    for (let lnv of this.ds_lnv_dagan) {
                        let temp = {NHANVIEN_ID: this.nhanvien_id, LOAINV_ID: lnv.loainv_id, LOAIKH_ID: lkh.loaikh_id};
                        vdsnhanvien_lkh.push(temp);
                    }          
                }

                await this.fn_capnhat_nhanvien_ptkh(this.nhanvien_id, vdsnhanvien_dt, vdsnhanvien_dtn, vdsnhanvien_nn, vdsnhanvien_lkh);
            } catch (e) {
                console.log(e)
                this.$toast.error("Có lỗi xảy ra: " + e);
            } finally {
                this.loading(false);
            }
        },
        async fn_capnhat_nhanvien_ptkh(vnhanvien_id, vdsnhanvien_dt, vdsnhanvien_dtn, vdsnhanvien_nn, vdsnhanvien_lkh) {
            try {
                const params = {
                    vnhanvien_id: vnhanvien_id,
                    vdsnhanvien_dt: JSON.stringify(vdsnhanvien_dt),
                    vdsnhanvien_dtn: JSON.stringify(vdsnhanvien_dtn),
                    vdsnhanvien_nn: JSON.stringify(vdsnhanvien_nn),
                    vdsnhanvien_lkh: JSON.stringify(vdsnhanvien_lkh)
                }
                let response = await api.ur_015_fn_capnhat_nhanvien_ptkh(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    this.$toast.success("Cập nhật thành công");
                } else {
                    this.$toast.error(response.data.message);
                }
            } catch (e) {
                console.log(e);
            }    
        },
        tsbtnTimKiem_Click() {
            this.$refs.frmTimKiemNhanVien.show();
            this.$refs.ppTimKiemNhanVien.ma_nv_search = "";
        },
        async onTimKiemNhanVien(val) {
            this.$refs.frmTimKiemNhanVien.hide();
            this.focusNode(val.donvi_id);
            await this.loadListView(val.donvi_id, 1, val.nhanvien_id);           
        },
        focusNode(nodeID) {
            this.removeAllClassSelect();
            let isFind = false;
            for (let i=1; i<10; i++) {
                let lstNodeLv = $("#treeview").find('.e-level-'+i);
                if (lstNodeLv) {
                    for (let index=0; index<lstNodeLv.length; index++) {
                        if (Number(lstNodeLv[index].getAttribute('data-uid')) == nodeID) {
                            $("#treeview").find('.e-level-'+i).eq(index).addClass('e-active e-node-focus');
                            isFind = true;
                            break;
                        };
                    }
                    if (isFind) {
                        break;
                    }
                } 
            }     
        },
        removeAllClassSelect() {
            for (let i=1; i<10; i++) {
                $("#treeview").find('.e-level-'+i).removeClass('e-active e-node-focus');
            } 
        }
    },
    mounted() {
        this.init();
    },
}
</script>
<style>
    li {
        cursor: pointer;
    }
    .invalid_in {
        border-color:red !important;
        background-color: lightcoral !important;
    }
</style>

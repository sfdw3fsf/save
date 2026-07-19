<template src="./index.html"></template>

<script>
import define from "./define/index.js";
import api from "./api/index.js";
import * as moment from 'moment';
import Vue from "vue";
import { L10n, setCulture } from '@syncfusion/ej2-base';
import { GridPlugin, Filter, Page, Group } from "@syncfusion/ej2-vue-grids";
import { DataManager, Query, Predicate } from '@syncfusion/ej2-data';
import DataGridCustom from './components/DataGrid/index.vue';
import { TabPlugin, TabComponent } from '@syncfusion/ej2-vue-navigations';
import tem0 from './components/template0';
import tem1 from './components/template1';

Vue.use(GridPlugin);
Vue.use(TabPlugin);
Vue.component('template0');
Vue.component('template1');
L10n.load({
    'vi': {
        // 'grids': {
        //     'Item': 'Artikel',
        //     'Items': 'Artikel'
        // },
        'pager': {
            'currentPageInfo': 'Bản ghi từ {1} đến số {2} trong tổng số {0}',
            'pagerDropDown': 'Bản ghi trên trang'
        }
    }
});
// import TTKHLeft from './components/TTKHLeft.vue';
// import TTKHRight from './components/TTKHRight.vue';
// import MuaTBI from './components/MuaTBI.vue';
// import ChonTBI from './components/ChonTBI.vue';
// import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue";
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue";
import ModalNormalizeCAinformation from "@/modules/contract/setup/NormalizeCAinformation/ModalNormalizeCAinformation.vue";
import ModalBuyDevices from "@/modules/contract/setup/InstallNewSubs/Popups/popupBuyDevices.vue";
import ModalTienKM from "@/modules/contract/setup/InstallNewSubs/Popups/popupTienKM.vue";
import PayMent from "@/modules/contract/setup/Payment/Payment.vue";
import ModalChooseDevices from './popup/popupChooseDevices.vue';
import ModalChonToaDo from "@/modules/CABMAN/XetDuyetPhanTuMangHoDanThuThap/ChonToaDo";
import {mapState} from 'vuex';
import store from './store/index';

export default {
    provide: {
        grid: [Filter, Page, Group]
    },
    components: {
        api,
        SearchContractModal,
        ModalNormalizeCAinformation,
        ModalBuyDevices,
        ModalTienKM,
        PayMent,
        ModalChooseDevices,
        ModalChonToaDo,
        DataGridCustom,
        tem0,
        tem1
    },
    props: ['Tag'],
    computed: {
        fieldsDonVis(){
            return this.createTreeDonVi(this.dsDonVi) // store.getters.getfieldsDonVi
        }
    },
    created() {
        this.initData();
        // console.log('xxx', store.dispatch('setStoredsDonVi'))
    },
    data() {
        return {
            ...define,
            filterBarMode: { filterBarMode: "Immediate" },
            currentRows: [],
            selectionOptions: { checkboxMode: 'ResetOnRowClick' },
            formatOptions: { type: 'date', format: 'dd/MM/yyyy' },
            hasFreezeLeft: true,
            groupOptions: { columns: ['loai_tb'], showDropArea: false},
            config: {
                thuebao: [
                    {
                        headerText: 'Số máy/Acc',
                        allowSorting: true,
                        fieldName: 'ma_tb'
                        , width: '120',
                    },
                    {
                        headerText: 'Loại hình',
                        allowSorting: true,
                        fieldName: 'loai_tb'
                        , width: '100',
                    },
                    {
                        headerText: 'Trạng thái',
                        allowSorting: true,
                        fieldName: 'trangthai'
                        , width: '120',
                    },
                    {
                        headerText: 'Ngày báo hỏng',
                        allowSorting: true,
                        fieldName: 'ngay_bao_hong'
                        , width: '150',
                    },
                    {
                        headerText: 'Người báo hỏng',
                        allowSorting: true,
                        fieldName: 'nguou_bao_hong'
                        , width: '150',
                    },
                    {
                        headerText: 'Ngày nghiệm thu',
                        allowSorting: true,
                        fieldName: 'ngay_nt'
                        , width: '150',
                    }
                ],
                hoso: [
                    {
                        headerText: 'STT',
                        allowSorting: true,
                        fieldName: 'stt',
                        width: '70',
                        freeze: 'Left'
                    },
                    {
                        headerText: 'Mã GD',
                        allowSorting: true,
                        fieldName: 'ma_gd'
                        , width: '120'
                        , freeze: 'Left'
                    },
                    {
                        headerText: 'Mã KH',
                        allowSorting: true,
                        fieldName: 'ma_kh'
                        , width: '90'
                    },
                    {
                        headerText: 'Tên khách hàng',
                        allowSorting: true,
                        fieldName: 'ten_kh'
                        , width: '150'
                    },
                    {
                        headerText: 'Tuyến thu',
                        allowSorting: true,
                        fieldName: 'tuyen_thu'
                        , width: '120'
                        , freeze: 'Right'
                    },
                    {
                        headerText: 'UpFile',
                        allowSorting: true,
                        fieldName: 'upfile'
                        , width: '80',
                        template: ` <div style="text-align:center;">
                                        <span class="text-main f20 nc-icon-outline files_single-content-02"></span>
                                    </div>`
                        , freeze: 'Right'
                    },
                    {
                        headerText: 'Đã xác nhận',
                        allowSorting: true,
                        fieldName: 'da_xn'
                        , width: '120',
                        template: `<div style="text-align:center;">
                                        <span class="text-main f20 nc-icon-outline ui-1_check-circle-07"></span>
                                    </div>`
                        , freeze: 'Right'
                    }
                ],
                ct_hoso: [
                    {
                        headerText: 'Số máy/Acc',
                        allowSorting: true,
                        fieldName: 'ma_tb'
                        , width: '120',
                        // freeze: 'Left'
                    },
                    {
                        headerText: 'Mã GD',
                        allowSorting: true,
                        fieldName: 'ma_gd'
                        , width: '150'
                    },
                    {
                        headerText: 'Loại file',
                        allowSorting: true,
                        fieldName: 'loaifile'
                        , width: '100',
                    },
                    {
                        headerText: 'Tên file',
                        allowSorting: true,
                        fieldName: 'tenfile'
                        , width: '100',
                    },
                    {
                        headerText: 'Xem/Tải về',
                        allowSorting: true,
                        fieldName: 'xem_taive'
                        , width: '120',
                        freeze: 'Right'
                    },
                    {
                        headerText: 'Loại HĐ/PL',
                        allowSorting: true,
                        fieldName: 'loai_hd'
                        , width: '120',
                    },
                    {
                        headerText: 'Ngày yêu cầu',
                        allowSorting: true,
                        fieldName: 'ngay_yc'
                        , width: '120',
                    },
                    {
                        headerText: 'Người upload',
                        allowSorting: true,
                        fieldName: 'nguoi_upload'
                        , width: '120',
                    },
                    {
                        headerText: 'Ngày upload',
                        allowSorting: true,
                        fieldName: 'ngay_upload'
                        , width: '120',
                    }
                ],
            },

            headerText0: { text: "Danh sách quyền nhóm ND" },
            headerText1: { text: "Danh sách quyền người dùng" },
            content0: function(){
                return{
                    template: Vue.component('template0', tem0)
                }
            },
            content1: function(){
                return{
                    template: Vue.component('template1', tem1)
                }
            },
            tabSelectedIndex: 1,
            dsDonVi: [],
            DonVi: '',
            expandedNodes: [900],
            selectedNodes: []
        }
    },
    methods: {
        async finDV(){
            try {
                // console.log('findDonVi', this.donvi_selected)
                let donvi=this.dsDonVi.find(x=>x.donvi_id==this.DonVi)
                if(donvi){
                    //selected node focus
                    this.selectedNodes=[donvi.donvi_id]
                    //find array parent expandedNodes
                    this.expandedNodes=this.donvi_id==900?[900]:[]
                    this.expandedNodes.push(donvi.donvi_id)
                    var donvi_id=donvi.donvi_cha_id
                    while(donvi_id!=null&&donvi_id!=-1){
                        const index=this.dsDonVi.findIndex(x=>x.donvi_id==donvi_id)
                        if(index>-1){
                            this.expandedNodes.push(donvi_id)
                            donvi_id=this.dsDonVi[index].donvi_cha_id
                        }else{
                            donvi_id=-1
                        }
                    }
                }
            } catch (error) {
                console.log(error);
            }
        },
        async tsbtnGhiLai_Click()
        {
            // debugger
            // tab danh sách quyền nhóm nd
            if (this.tabSelectedIndex == 0)
            {
                console.log(this.tabSelectedIndex);
                try
                {
                    // grvThoiGian.CloseEditor();
                    let ds = store.getters.getThoiGianTemp; //grcThoiGian.DataSource as DataTable;
                    if (!ds || ds.length == 0)
                    {
                        this.$toast.warning("Không có thông tin thời gian thiết lập");
                        return;
                    }
                    // ds.AcceptChanges();
                    let t = ds.filter(item => item['isupdate'] == "1"); //ds.Select("ISUPDATE='1'");
                    if (t.length == 0)
                    {
                        this.$toast.warning("Không có thông tin thời gian thiết lập nào được thay đổi");
                        return;
                    }
                    this.$confirm("Bạn có muốn cập nhật trạng thái của thời gian đã thiết lập không?",'Thông báo', {
                        confirmButtonText: 'Đồng ý',
                        cancelButtonText: 'Không đồng ý'
                    }).then(async () => {
                        let result = await this.axios.post('/web-hopdong/ganquyen-gachno/sp_capnhat_nhomnd_gn',{
                            vkieu: 3, // --1: gan, 2: huy gan, 3: xoa (ở đây truyền 3)
                            vds: JSON.stringify(t).toUpperCase(),
                            vnhom_nd_id: store.state.vnhom_nd_id,
                            vdsquyen_gn: store.state.vkieu,
                            vnhom_id_luoi: store.state.vnhom_id
                        });
                        if(result.data.error_code == 'BSS-00000000'){
                            this.$toast.success("Cập nhật trạng thái thành công");
                            console.log(store.state.vnhom_nd_id, store.state.vkieu);
                            this.NAP_DS_NHOMND_DG(store.state.vnhom_nd_id, store.state.vkieu);
                            this.$alert('Cập nhật trạng thái thành công', 'Thông báo')
                        }else{
                            this.$toast.warning("Cập nhật trạng thái thất bại");
                        }
                        // web-hopdong/ganquyen-gachno/sp_capnhat_nhomnd_gn
                    }).catch((e) => {
                        console.log('Dừng lại !!', e);
                        return;
                    })
                }
                catch(ex)
                {
                    this.$toast.warning("Có lỗi khi cập nhật trạng thái " + ex);
                    console.log(ex);
                }
            }
            // tab danh sách quyền người dùng
            else if (this.tabSelectedIndex == 1)
            {
                console.log(this.tabSelectedIndex)
                try
                {
                    // grvThoiGian_ND.CloseEditor();
                    let ds = store.getters.getThoiGian_ND_Temp; // var ds = grcThoiGian_ND.DataSource as DataTable;
                    console.log('xxxx: ',ds)
                    if (!ds || ds.length == 0)
                    {
                        this.$toast.warning("Không có thông tin thời gian thiết lập");
                        return;
                    }
                    // ds.AcceptChanges();
                    let t = ds.filter(item => item['isupdate'] == "1");  //ds.Select("ISUPDATE='1'");
                    if (t.length == 0)
                    {
                        this.$toast.warning("Không có thông tin thời gian thiết lập nào được thay đổi");
                        return;
                    }
                    this.$confirm("Bạn có muốn cập nhật trạng thái của thời gian đã thiết lập không?",'Thông báo', {
                        confirmButtonText: 'Đồng ý',
                        cancelButtonText: 'Không đồng ý'
                    }).then(async () => {
                        let result = await this.axios.post('/web-hopdong/ganquyen-gachno/sp_capnhat_nguoidung_gn',{
                            vkieu: 3, // --1: gan, 2: huy gan, 3: xoa (ở đây truyền 3)
                            vds: JSON.stringify(t).toUpperCase(),
                            vnguoidung_id: store.state.vnguoidung,
                            vquyen_gn: store.state.vkieu_nd,
                            vnhom_id_nd_luoi: store.state.vnd_id
                        });
                        if(result.data.error_code == 'BSS-00000000'){
                            this.$toast.success("Cập nhật trạng thái thành công");
                            this.NAP_DS_NGUOIDUNG_DG(store.state.vkieu_nd);
                            this.$alert('Cập nhật trạng thái thành công', 'Thông báo')
                        }else{
                            this.$toast.warning("Cập nhật trạng thái thất bại");
                        }
                    }).catch(() => {
                        console.log('Dừng lại !!');
                        return;
                    })

                    // NAP_DS_NGUOIDUNG_DG();
                }
                catch (ex)
                {
                    this.$toast.error("Có lỗi khi cập nhật trạng thái " + ex);
                    console.log(ex);
                }
            }
        },
        async NAP_DS_NGUOIDUNG_DG(vkieu){
        // let vkieu = this.ds_nhomnguoi.quyen_gn;
        await store.dispatch('NAP_DS_NGUOIDUNG_DG', {vkieu});
        // this.options.thoi_gian_thiet_lap = [...store.state.grcThoiGian_ND];
        },
        async NAP_DS_NHOMND_DG(nhom_nd, kieu){
            let vnhomnd_id = nhom_nd;
            let vkieu = kieu ? kieu : 0;
            console.log({vnhomnd_id, vkieu});
            await store.dispatch('NAP_DS_NHOMND_DG', {vnhomnd_id, vkieu});
        },
        selected: function(args) {
            console.log('selected ',args);
            this.tabSelectedIndex = args.selectedIndex;
            // this.getInteractionDetail(args.isInteracted);
        },
        selecting: function(args) {
            console.log('selecting ', args);
            // this.getInteractionDetail(args.isInteracted);
        },
        tvDonVi_FocusedNodeChanged(e){
            console.log('nodeSelected ', e);
            this.NAP_DS_NGUOIDUNG(e);
        },
        async NAP_DS_NGUOIDUNG(e){
            let don_vi_id = e.nodeData.id ? Number(e.nodeData.id) : 0;
            await store.dispatch('NAP_DS_NGUOIDUNG', {don_vi_id});
            console.log(store.state.grcNguoiDung);
            this.options.nguoi_dung = await store.state.grcNguoiDung;
        },
         //Find the Parent Nodes for corresponding childs
        getFilterItems(fList, list) {
            let nodes = [];
            nodes.push(fList["id"]);
            let query2 = new Query().where('id', 'equal', fList["donvi_cha_id"], false);
            let fList1 = new DataManager(list).executeLocal(query2);
            if (fList1.length != 0) {
                let pNode = this.getFilterItems(fList1[0], list);
                for (let i = 0; i < pNode.length; i++) {
                    if (nodes.indexOf(pNode[i]) == -1 && pNode[i] != null)
                        nodes.push(pNode[i]);
                }
                return nodes;
            }
            return nodes;
        },
        //Filtering the TreeNodes
        searchNodes(args) {
            // console.log(args);
            let _text = document.getElementById('txtFilter').value.trim() // mask.element.value;
            console.log(_text);
            let predicats = [], _array = [], _filter = [];
            if (_text == "") {
                // alert()
                this.changeDataSource([...this.dsDonVi]);
            }
            else {
                // let root = this.dsDonVi[0];
                let dsTem = [...this.dsDonVi];
                let predicate = new Predicate('ten_dv', 'contains', _text, true);
                console.log(predicate);
                let filteredList = new DataManager(dsTem).executeLocal(new Query().where(predicate));
                // this.dsDVTem = [root,...filteredList];
                for (let j = 0; j < filteredList.length; j++) {
                    _filter.push(filteredList[j]["donvi_id"]);
                    let filters = this.getFilterItems(filteredList[j], this.dsDonVi);
                    for (let i = 0; i < filters.length; i++) {
                        if (_array.indexOf(filters[i]) == -1 && filters[i] != null) {
                            _array.push(filters[i]);
                            predicats.push(new Predicate('donvi_id', 'equal', filters[i], false));
                        }
                    }
                }
                if (predicats.length == 0) {
                    this.changeDataSource([]);
                } else {
                    let query = new Query().where(new Predicate.or(predicats));
                    let newList = new DataManager(this.dsDonVi).executeLocal(query);
                    this.changeDataSource(newList);
                    // setTimeout(function () {
                    //     listTreeObj.expandAll();
                    // }, 400);
                }
                // console.log(filteredList);
                // this.changeDataSource(filteredList);
            }
        },
        async changeDataSource(data) {
            // console.log(data);
            let tem = this.createTreeDonVi(data);
            // console.log(tem);
            this.tt_doituong_hs.fieldsDonVi = tem
            // {
            //         dataSource: tem, id: 'donvi_id', text: 'ten_dv',
            //         parentID: 'donvi_cha_id', hasChildren: 'hasChild'
            // }
        },
        sortTreeView(){
            try {
                let root = this.dsDVTem[0];
                let dsTem = this.dsDVTem.slice(1);
                dsTem.sort(function(a, b) {
                    const nameA = a.ma_hs_doituong.toUpperCase(); // ignore upper and lowercase
                    const nameB = b.ma_hs_doituong.toUpperCase(); // ignore upper and lowercase
                    if (nameA < nameB) {
                        return -1;
                    }
                    if (nameA > nameB) {
                        return 1;
                    }

                    // names must be equal
                    return 0;
                    });
                this.changeDataSource([root,...dsTem]);
            } catch (error) {
                console.log(error);
            }
        },
        
        gridviewDanhSach_FocusedRowChanged() {
            try {
                if (this.options.hoso.length) {
                    this.CLEAR();
                    this.NAP_DS_LUOI();
                }
            } catch (e) {
                console.log(e)
            }
        },
        
        sortTreeView() {

            // console.log(this.donViTree.sort());
        },
        filterViewTree(e) {
            console.log(e.target.value);
            // if(e.target.value){
            //     let temList = this
            // }
        },
        async getDsDonViTheoNV() {
            try {
                await store.dispatch('getDsDonVi');
                this.dsDonVi = store.state.dsDonVi;
                // let res = await this.axios.get('/web-hopdong/ganquyen-gachno/sp_ds_donvi_gn');
                // if(res.data.error_code == 'BSS-00000000'){
                // }else{
                //     this.$toast.error(res.data.message_detail);
                // }
            } catch (error) {
                console.log(error);
            }
        },
        createTreeDonVi(dsDonVi) {
            if(dsDonVi){
                
                let tree = []
                let donviChaArray = dsDonVi.map(item => (item['donvi_cha_id']))
                let donviArray = dsDonVi.map(item => (item['donvi_id']))
                dsDonVi.forEach(item => {
                    //check item la parent node
                    if (donviChaArray.includes(item['donvi_id'])) {
                        //check donvi_cha_id khong co trong ds donvi_id thi xoa donvi_cha_id
                        if (!donviArray.includes(item['donvi_cha_id'])) {
                            delete item.hoso_cha_id;
                        }
                        tree.push({ ...item, hasChild: true, expanded: false})
                    } else {
                        tree.push(item)
                    }
                });
                return {
                    dataSource: tree, id: 'donvi_id', text: 'ten_dv',
                    parentID: 'donvi_cha_id', hasChildren: 'hasChild'
                }
                // store.commit('SET_DONVI_UPDATE', {
                //     dataSource: tree, id: 'donvi_id', text: 'ten_dv',
                //     parentID: 'donvi_cha_id', hasChildren: 'hasChild'
                // });
                // this.tt_doituong_hs.fieldsDonVi = store.state.fieldsDonVi;
                // return tree
            }
        },
        
        async initData() {
            try {
                // get nguoidung_id
                let res_tt_nd = await this.axios.post('/quantri/user/thongtin_nguoidung2',{});
                let nguoidung_id = 999999;
                if(res_tt_nd.data && res_tt_nd.data.error_code == 'BSS-00000000')
                    nguoidung_id = res_tt_nd.data.data['nguoidung_id'];
                store.commit('SET_NGUOIDUNG_ID', nguoidung_id);
                
                // get ds cay don vi
                await this.getDsDonViTheoNV();
                // this.createTreeDonVi(this.dsDonVi);
                
                // const date = new Date();
                // this.tt_bo_hs.giao_tungay = moment(new Date(Date.now() - (3600*24*10*1000))).format('DD/MM/YYYY');
                // this.tt_bo_hs.denngay = moment(new Date()).format('DD/MM/YYYY');
                // this.nhanvien_id = this.$root.token.getNhanVienID();
                
                // // console.log(this.dsDonVi);
                // await this.NAP_COMB_GIAOPHIEU();
            } catch (error) {
                console.log(error);
            }
        },
        async NAP_DS_DONVI(){
            try {
                // get ds cay don vi
                await this.getDsDonViTheoNV();
                // this.createTreeDonVi(this.dsDonVi);
            } catch (error) {
                console.log(error);
            }
        },
        // async NAP_DS_NHOMND_DG(params){
        //     try {
        //         await store.dispatch('NAP_DS_NHOMND_DG', params);
        //         console.log(store.state.vkieu, store.state.vnhom_id);
        //     } catch (error) {
        //         console.log(error);
        //     }
        // },
        async NAP_DS_NHOMND_CG(params){
            try {
                await store.dispatch('NAP_DS_NHOMND_CG', params);
            } catch (error) {
                console.log(error);
            }
        },
        clickButton(key) {
            if (key == "LAYTT") {
                this.tsbtnLayTT_Click();
            }
            if (key == "CAPNHAT") {
                this.tsbtnGhiLai_Click();
            }
            
        },
        async tsbtnLayTT_Click() {
            // console.log('xxx ', store.state.vkieu, store.state.vnhom_nd_id);
            let vnhomnd_id = store.state.vnhom_nd_id, vkieu = store.state.vkieu, vnhom_id = store.state.vnhom_id;
            await this.NAP_DS_DONVI();
            await this.NAP_DS_NHOMND_DG(vnhomnd_id, vkieu);
            await this.NAP_DS_NHOMND_CG({vnhomnd_id, vkieu, vnhom_id});
        },
        vbohs_id() {
            if (this.model.hoso['BOHS_ID'])
                return Number(this.model.hoso['BOHS_ID']);
            return -1;
        },
        btnThemFile_Click() {
            if (!this.KIEMTRA_QUYEN(0)) return;
            let vbohs_id = this.vbohs_id();
            if (vbohs_id == -1) {
                this.$toast.warning("Bạn chưa chọn bộ hồ sơ để thêm file");
                return;
            }
            // splitContainerControl4.PanelVisibility = SplitPanelVisibility.Both;
            // tsbtnThemFile.Enabled = false;
            this.NAP_DS_HOPDONGTB(this.vhdkh_id_luoi());
        },
        vhdkh_id_luoi() {
            if (this.model.hoso['hdkh_id'])
                return Number(this.model.hoso['hdkh_id'])
            return -1
        },
        async NAP_DS_HOPDONGTB(vhdkh_id_luoi) {
            try {
                this.tt_file.loai_file = -1; // CboLoaiFile.EditValue = -1;
                this.tt_file.nhom_file = -1;//cboNhomFile.EditValue = -1;
                this.tt_file.duong_dan = ""; // txtDuongDan.Text = "";
                this.tt_file.ghichu = ""; // txtGhiChuFile.Text = "";
                // var myDataTables = tdan.GET_DATA_PROC("{?DB1}.Lay_ds_hd_tb_theo_hdkh_id", "vhdkh_id", hdkh_id);
                let res = await this.axios.post("/web-hopdong/hopdong/lay_hd_thuebao_theo_hd_khachhang", {
                    hdkh_id: vhdkh_id_luoi,
                });
                let myDataTables = res.data.data ? res.data.data : []
                this.options.thuebao = []; // gridThueBao.DataSource = null;
                if (myDataTables.length) {
                    this.options.thuebao = myDataTables; // gridThueBao.DataSource = myDataTables;
                    // grvThueBao.BestFitColumns();
                    // this.LOC_LUOI_TB();
                }
            }
            catch (ex) {
                this.$toast.error("Có lỗi khi lấy thông tin hợp đồng " + ex);
            }
        },
        LOC_LUOI_TB() {
            try {

                // var a = gridviewDanhSach.ActiveFilter;
                // a.Remove(gridviewDanhSach.Columns["MALO_HS"]);

                // // #region Nếu ko phải bắt buộc
                // if (cboDVGIAO.EditValue.ToString() != "-1") {
                //     var viewFilterInfo = new ViewColumnFilterInfo(gridviewDanhSach.Columns["MALO_HS"],
                //         new ColumnFilterInfo(
                //             "[DONVI_GIAO_ID]=" + cboDVGIAO.EditValue + " ", ""));
                //     gridviewDanhSach.ActiveFilter.Add(viewFilterInfo);


                // }
                // if (cboLOHS.EditValue.ToString() != "0") {
                //     var viewFilterInfo = new ViewColumnFilterInfo(gridviewDanhSach.Columns["MALO_HS"],
                //         new ColumnFilterInfo(
                //             "[LOHS_ID]=" + cboLOHS.EditValue + " ", ""));
                //     gridviewDanhSach.ActiveFilter.Add(viewFilterInfo);
                // }
                // gridviewDanhSach.BestFitColumns();
            } catch (error) {

            }
        },
        tsbtnHoanCong_Click() {
            this.GIAOPHIEU();
        },
        GIAOPHIEU() {
            try {
                if (!this.KIEMTRA_PHIEU(1)) return;
                this.$confirm('Bạn có muốn lưu danh sách bộ hồ sơ không ?', {
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không'
                }).then(() => {
                    // alert('yes');
                    let dt = [...this.options.hoso];
                    let huonggiao_ht = 0;
                    let ds = [];

                    for (let i = 0; i < dt.length; i++) {
                        let tem = {
                            bhs_id: "",
                            dvgiao_id: "",
                            dvnext_id: "",
                            dvtt_id: "",
                            lhs_id: "",
                            malhs: "",
                            nv_nhan: "",
                            phieu_id: "",
                            phieu_id_cha: "",
                            tthu_id: ""
                        }
                        tem.bhs_id = i['bohs_id'].toString();
                        tem.dvgiao_id = i['dvgiao_id'].toString();
                        tem.dvnext_id = i['dvnext_id'].toString();
                        tem.dvtt_id = i['dvtt_id'].toString();
                        tem.lhs_id = i['lohs_id'].toString();
                        tem.malhs = i['malo_hs'].toString();
                        tem.nv_nhan = i['nv_nhan'].toString();
                        tem.phieu_id = i['phieu_id'].toString();
                        tem.phieu_id_cha = i['phieu_cha_id'].toString();
                        tem.tthu_id = i['tthu_id'].toString();
                        ds.push(tem);
                        huonggiao_ht = Number(dt.Rows[i]["HUONGGIAO_ID"].toString());
                    }

                    // var ketqua = tdan.GET_VALUE_FUNC( "{?DB1}.xacnhan_bohs",
                    //     "vdonvi_id", ttnd.donvi_id,
                    //     "vnhanvien_id", ttnd.nhanvien_id,
                    //     "vhuonggiao_id", huonggiao_ht,
                    //     "vmay_cn", ttnd.may_cn,
                    //     "vnguoi_cn", ttnd.ma_nd,
                    //     "vip_cn", ttnd.ip,
                    //     "vdanhsach", xdoc.OuterXml,
                    //     "vnd_tra", "",
                    //     "vkieu", 2,
                    //     "vxn_gv",0);
                    this.axios.post("/web-hopdong/phattrienthuebao/giaobhs_donvi_tieptheo", {
                        danhsach: JSON.stringify(ds),
                        huonggiao_id: huonggiao_ht,
                        quytrinhId: this.quytrinh_id,
                        kieu: 2,
                        noidung: "",
                        xn_gv: 0
                    }).then(res => {
                        if (!res.data.data)
                            this.$toast.warning("Không có thông tin trả về khi thực hiện trả phiếu");

                        if (res.data.data.toString() != "1")
                            this.$toast.warning(res.data.data);
                        else
                            this.$toast.success('Thực hiện lưu kho thành công');
                    }).catch(e => {
                        console.log(e);
                    })

                }).catch(() => {
                    return;
                });
            } catch (error) {
                console.log(error);
            }
        },
        tsbtnXacNhan_Click() {
            if (!this.KIEMTRA_QUYEN(1)) return;
            this.XACNHAN_DS();
        },
        async getDsQuyenND() {
            try {
                let res = await this.axios.get('/web-quantri/quyen/ds_quyen_nguoidung');
                return res.data.data;
            } catch (error) {
                console.log(error);
            }
        },
        async KIEMTRA_QUYEN(kieu) {
            //kieu=0 la them file
            // if (this.PHAILAM("BOSUNG_FILEHS"))
            // {
            let dsQuyen = await this.getDsQuyenND();
            if (kieu == 0) {
                if (!dsQuyen.includes(2082)) // HOSO_LC.QUYEN_THEMFILE
                {
                    this.$toast.warning("Bạn không có quyền thêm file hồ sơ (QID: " + 2082 + ")");
                    return false;
                }
            }
            if (kieu == 1) {
                if (!dsQuyen.includes(2083)) // HOSO_LC.QUYEN_THAOTAC))
                {
                    this.$toast.warning("Bạn không có quyền thao tác trên bộ hồ sơ(QID: " + 2083 + ")");
                    return false;
                }
            }
            // }
            return true;
        },

        PHAILAM(code) {
            let dr = this.thaotac.filter(i => i.enable == 1 && i.code == code);
            if (dr.length)
                return true
            return false;
        },
        async XACNHAN_DS() {
            try {
                this.$refs.ma_gd.focus();
                // gridviewDanhSach.CloseEditor();
                if (!this.KIEMTRA_PHIEU(0)) return;
                this.$confirm('Bạn chắc chắn muốn xác nhận danh sách bộ hồ sơ không ?', {
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không'
                }).then(() => {
                    // alert('yes');
                    let dt = [...this.options.hoso];
                    let huonggiao_ht = 0;
                    let ds = [];

                    for (let i = 0; i < dt.length; i++) {
                        let tem = {
                            bhs_id: "",
                            dvgiao_id: "",
                            dvnext_id: "",
                            dvtt_id: "",
                            lhs_id: "",
                            malhs: "",
                            nv_nhan: "",
                            phieu_id: "",
                            phieu_id_cha: "",
                            tthu_id: ""
                        }
                        tem.bhs_id = i['bohs_id'].toString();
                        tem.dvgiao_id = i['dvgiao_id'].toString();
                        tem.dvnext_id = i['dvnext_id'].toString();
                        tem.dvtt_id = i['dvtt_id'].toString();
                        tem.lhs_id = i['lohs_id'].toString();
                        tem.malhs = i['malo_hs'].toString();
                        tem.nv_nhan = i['nv_nhan'].toString();
                        tem.phieu_id = i['phieu_id'].toString();
                        tem.phieu_id_cha = i['phieu_cha_id'].toString();
                        tem.tthu_id = i['tthu_id'].toString();
                        ds.push(tem);
                        huonggiao_ht = Number(dt.Rows[i]["HUONGGIAO_ID"].toString());
                    }

                    // var ketqua = tdan.GET_VALUE_FUNC( "{?DB1}.xacnhan_bohs",
                    //     "vdonvi_id", ttnd.donvi_id,
                    //     "vnhanvien_id", ttnd.nhanvien_id,
                    //     "vhuonggiao_id", huonggiao_ht,
                    //     "vmay_cn", ttnd.may_cn,
                    //     "vnguoi_cn", ttnd.ma_nd,
                    //     "vip_cn", ttnd.ip,
                    //     "vdanhsach", xdoc.OuterXml,
                    //     "vnd_tra", "",
                    //     "vkieu", 2,
                    //     "vxn_gv",0);
                    this.axios.post("/web-quantri/hoso/xacnhan_bohoso", {
                        danhsach: JSON.stringify(ds),
                        huonggiao_id: huonggiao_ht,
                        kieu: 2,
                        noidung: "",
                        xn_gv: 0
                    }).then(res => {
                        if (!res.data.data)
                            this.$toast.warning("Không có thông tin trả về khi thực hiện trả phiếu");

                        if (res.data.data.toString() != "1")
                            this.$toast.warning(res.data.data);
                        else
                            this.$toast.success('Thực hiện xác nhận thành công');
                    })
                    // this.TIMKIEM("", 0);
                }).catch(() => {
                    this.$toast.warning("Tiến trình đã bị dừng bởi người dùng")
                    return;
                });

            } catch (error) {
                console.log(error);
            }
        },
        async TIMKIEM(ma_gd, thongbao) {
            try {
                this.$refs.ma_gd.focus();
                this.CLEAR();
                this.options.hoso = []; // gridDanhSach.DataSource = null;
                if (ma_gd == "")
                    ma_gd = "0";
                let res = await this.axios.post('/web-quantri/hoso/layds_bohoso_chuagiao_theo_makh_magd', {
                     ma_gd: ma_gd,
                     tths_id: this.tths_id,
                    // nhanvien_id: this.nhanvien_id,
                     phieu_id: 0,
                     huonggiao_id: 0,
                     quytrinh_id: this.quytrinh_id,
                     kieugiao: this.tt_bo_hs.kieu_giao,
                     ttph_id: this.tt_bo_hs.trang_thai,
                     timngay: this.tt_bo_hs.ischkTungay ? 1 : 0,
                     tungay: this.tt_bo_hs.giao_tungay,
                     denngay: this.tt_bo_hs.denngay
                });

                if ((!res.data.data || !res.data.data.length) && thongbao == 1)
                    this.$toast.warning("Không tìm thấy thông tin phiếu giao đến");
                else {
                    this.options.hoso = res.data.data;
                    this.NAP_COMBO_TIMKIEM();
                    if (this.nuthc)
                        this.NAP_DS_DL_DOITUONG();
                }
            }
            catch (ex) {
                // DROP_COMMAND(gid);
                this.$toast.warning("Có lỗi khi tìm kiếm giao phiếu " + ex);
            }
        },
        NAP_COMBO_TIMKIEM() {
            var dt = [...this.options.hoso];
            if (dt != null && dt.length != 0) {
                var ds_loc = dt.filter(e => e['malo_hs'] != null);
                if (ds_loc.length > 0) {
                    var view = [...ds_loc];
                    var lhs_id = view.map(e => {
                        if (e["lohs_id"] && e["malo_hs"]) {
                            return e;
                        }
                    })
                    var dv = [...lhs_id]
                    this.cbloHS = dv;
                }

                // ĐÃ LẤY DS TỪ BAN ĐẦU XEM XÉT CÓ NÊN CMT 
                // var view_dvi = [...dt];
                // var dv_id = view_dvi.map(e => {
                //     if(e["donvi_giao_id"] && e["dv_giao"])
                //         return e
                // });
                // this.cbdvGiao = dv_id;
            }
        },
        CLEAR() {
            this.options.ct_hoso = [];
            this.tt_bo_hs.solohs = '';
            this.tt_bo_hs.trangthai = "";
            this.tt_bo_hs.dvgiao = "";
            this.tt_bo_hs.nd_tra = "";
            this.tt_bo_hs.makh = "";
            this.tt_bo_hs.tenkh = "";
            this.tt_bo_hs.magd = "";
            this.tt_bo_hs.cbdvGiao = [];
            this.tt_bo_hs.cbloHS = [];
        },
        KIEMTRA_PHIEU(kieu) {
            try {
                var dt = this.options.hoso

                if (dt == null || dt.length == 0) {
                    this.$toast.warning("Không có danh sách phiếu để thực hiện");
                    return false;
                }
                // dt.AcceptChanges();
                if (Object.keys(this.model.hoso).length == 0) {
                    this.$toast.warning("Bạn chưa chọn phiếu để thực hiện");
                    return false;
                }

                if (dt.filter(e => e['ckb_xacnhan'] == 0 && e['ischeck'] == 1).length > 0 && kieu == 1) {
                    this.$toast.warning("Trong danh sách có phiếu chưa được xác nhận bạn không thể thực hiện");
                    return false;
                }

                if (kieu != 1) return true;
                return true;
            }
            catch (ex) {
                this.$toast.warning("Có lỗi khi kiểm tra danh sách phiếu " + ex);
                return false;
            }
        },

        currencyFormater(c) {
            try {
                // Create our number formatter.
                var formatter = new Intl.NumberFormat('en-US', {
                    style: 'currency',
                    currency: 'USD',

                    // These options are needed to round to whole numbers if that's what you want.
                    //minimumFractionDigits: 0, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
                    //maximumFractionDigits: 0, // (causes 2500.99 to be printed as $2,501)
                });
                return formatter.format(c);
            } catch (error) {
                // this.$toast.error(error);
                console.log(error);
            }
        },
    },
    mounted() {
        // this.handleTag()
        // this.gridviewDanhSach_FocusedRowChanged();
        // console.log(this.$root.token.getMaTinh());
    }
}
</script>

<style>
.list a{
    cursor: pointer;
}
.e-grid td.e-selectionbackground {
    background-color: #F9E1A9 !important;
}

.e-grid tr:hover {
    background-color: #FFF9EB !important;
}

.e-checkbox-wrapper .e-check::before,
.e-css.e-checkbox-wrapper .e-check::before {
    content: "\e718";
    background-color: transparent;
    line-height: 16px;
}

.e-checkbox-wrapper .e-stop::before,
.e-css.e-checkbox-wrapper .e-stop::before {
    content: "\e7a5" !important
}

.e-tab .e-tab-header .e-toolbar-item.e-active .e-tab-text {
    color: #000000 !important;
    font-weight: bold !important;
}

/* .e-tab .e-content{
    border: 1px solid rgb(218, 218, 218);
    border-radius: 5px;
} */

.disabled {
    pointer-events:none;
    opacity:0.5;
    background: #ccc;
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

#treeview .e-list-item .e-level-3 > .e-text-content .e-list-text {
    font-weight: bold;
    font-style: italic;
    color: darkmagenta;
}
</style>
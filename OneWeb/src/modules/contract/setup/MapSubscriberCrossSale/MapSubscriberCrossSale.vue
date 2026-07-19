<template>
    <div>
        <div div v-if="hasPermission" class="main-wrapper">
            <breadcrumb :header="header" />
            <div>
                <ActionTop :actions="actions" @onActionClick="onActionClick" />
                <div class="page-content">
                    <div class="box-form">
                        <div class="row">
                            <div class="col-sm-6 col-6">
                                <div class="box-form">
                                    <div class="info-row">
                                        <div class="key w120">Tỉnh bán</div>
                                        <div class="value">
                                            <Select2 v-model="tinhBanId" :options="DsTinhBan" />
                                        </div>
                                    </div>
                                    <div class="info-row">
                                        <div class="key w120">Mã thuê bao bán</div>
                                        <div class="value">
                                            <div class="input">
                                                <input type="text" v-model="txtMaTB_TB.value" class="form-control"
                                                    @keyup.enter="onClickSearch_TB">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-6">
                                <div class="box-form">
                                    <div class="row">
                                        <div class="col-sm-6 col-6">
                                            <div class="info-row">
                                                <div class="key w120">Tỉnh thi công</div>
                                                <div class="value">
                                                    <Select2 v-model="tinhTC_Id" :options="DsTinhThiCong" />
                                                </div>
                                            </div>
                                            <div class="info-row">
                                                <div class="key w120">Mã thuê bao TC</div>
                                                <div class="value">
                                                    <div class="input">
                                                        <input type="text" class="form-control"
                                                            v-model="txtMaTB_TTC.value"
                                                            @keyup.enter="onClickSearch_TTC" />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-6 col-6">
                                            <div class="info-row">
                                                <div class="key w60">Ghi chú</div>
                                                <div class="value">
                                                    <div class="input">
                                                        <textarea type="text" rows="3" class="form-control"
                                                            v-model="txtGhi_chu.value" />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">Thuê bao tỉnh bán
                        </div>
                        <DataGrid ref="gridTinhBan" panelDataHeight="150px" v-bind:columns="gridTinhBan.header"
                            v-bind:dataSource="gridTinhBan.listBan" :enabledSelectFirstRow="true" :showFilter="true"
                            :enablePagingServer="false" :showColumnCheckbox="false" :allowPaging="true"
                            :focused-row-enabled="true" :column-auto-width="true" @rowSelected="change_TB" />
                        <!-- @rowSelected="change_TB" -->
                    </div>
                    <div class="box-form">
                        <div class="legend-title">Thuê bao tỉnh thi công
                        </div>
                        <DataGrid ref="gridTinhThiCong" panelDataHeight="150px" v-bind:columns="gridTinhThiCong.header"
                            v-bind:dataSource="gridTinhThiCong.listTC" :enabledSelectFirstRow="true" :showFilter="true"
                            :enablePagingServer="false" :showColumnCheckbox="false" :allowPaging="true"
                            :column-auto-width="true" :focused-row-enabled="true" @rowSelected="change_TTC" />
                        <!-- @rowSelected="change_TTC" -->
                    </div>
                </div>
            </div>
        </div>
        <password-view style="padding: 10px;" v-else @onPasswordSubmittedSuccessListener="hasPermission = true" />
    </div>
</template>

<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import api from './api'
import apiHelper from "./api.helper";
import breadcrumb from '@/components/breadcrumb'
import PasswordView from '@/modules/admin/category/GrantUserPermission/components/password-view.vue'

export default {
    name: 'MapChuQuanModal',
    props: {
        danhsach: {
            type: Array,
            default: () => [],
        },
    },

    components: {
        ActionTop,
        KDataGrid,
        apiHelper,
        breadcrumb,
        PasswordView
    },
    data() {
        return {
            header: {
                title: 'Map thuê bao bán chéo ',
                list: [
                    { name: 'Map thuê bao bán chéo', link: { name: 'contract' } },
                    {
                        name: 'Map thuê bao bán chéo',
                        link: { name: 'Ui.buttons' },
                        active: true,

                    }
                ]
            },
            hasPermission: false,

            target: '.main-wrapper',
            ma_tb: '',
            tinh: '',
            // data:''
            // ds: [],
            animationSettings: {
                effect: 'Zoom'
            },
            txtMaTB_TB: {
                enabbled: false,
                value: ''
            },
            txtMaTB_TTC: {
                enabbled: false,
                value: ''
            },
            txtGhi_chu: {
                enabbled: false,
                value: ''
            },
            DsTinhBan: [],
            DsTinhThiCong: [],
            tinhBanId: null,
            tinhTC_Id: null,
            rowid: '',
            phanvung_id: '',
            thuebao_kn_id: '',
            thuebaoid: '',
            loaiTBid_Ban: '',
            loaiTBid_TC: '',
            tinh_kn_id: '',
            trangthai: '',
            trangthai_TB: '',
            trangthai_TC: '',
            loaiBC: '',
            ckb_tinhBan: false,
            ckb_tinhTC: false,
            actions: [{
                id: 'tracuu',
                name: 'Tra cứu',
                active: true,
                icon_class: 'icon one-search'
            },
            {
                id: 'mapTB',
                name: 'Map TB',
                active: true,
                icon_class: 'one-save'
            },
            ],
            gridTinhBan: {
                header: [{
                    fieldName: '',
                    headerText: 'Thao tác ',
                    width: 95,
                    allowfiltering: false,
                    template: this.grid_KT_TBB_Template(this),
                    textAlign: 'center'
                },
                {
                    fieldName: 'ma_tb',
                    headerText: 'Mã TB ',
                    width: 150,
                    allowfiltering: true,
                    textAlign: 'center'
                },
                {
                    fieldName: 'ma_tb_tinh',
                    headerText: 'Mã TB KN',
                    width: 150,
                    allowfiltering: true,
                    textAlign: 'center',
                    // width:'auto'
                },
                {
                    fieldName: 'trangthai',
                    headerText: 'Trạng thái',
                    width: 110,
                    allowfiltering: true,
                    textAlign: 'center'
                },
                {
                    fieldName: 'chuquan',
                    headerText: 'Chủ Quản',
                    width: 150,
                    allowfiltering: true,
                    textAlign: 'center'
                },
                {
                    fieldName: 'tentat',
                    headerText: 'Tỉnh KN',
                    width: 100,
                    allowfiltering: true,
                    textAlign: 'center'
                },
                {
                    fieldName: 'loaihinhtb',
                    headerText: 'Loại TB',
                    width: 90,
                    allowfiltering: true,
                    textAlign: 'center'
                },
                {
                    fieldName: 'chuquanld',
                    headerText: 'Chủ Quản LD',
                    allowfiltering: true,
                    width: 125,
                    textAlign: 'center'
                },
                {
                    fieldName: 'thuebao_id',
                    headerText: 'Thuê bao ID',
                    width: 120,
                    allowfiltering: true,
                    textAlign: 'center'
                },
                {
                    fieldName: 'loaitb_id',
                    headerText: 'Loại TB_ID',
                    width: 110,
                    allowfiltering: true,
                    textAlign: 'center'
                },
                {
                    fieldName: 'phanvung_id',
                    headerText: 'Tinh ID',
                    width: 90,
                    allowfiltering: true,
                    textAlign: 'center'
                },
                ],
                listBan: [],
                selected: [],
                value: null,
            },
            gridTinhThiCong: {
                header: [{
                    fieldName: '',
                    headerText: 'Thao tác ',
                    width: 95,
                    allowfiltering: false,
                    template: this.grid_KT_TBTC_Template(this),
                    textAlign: 'center'
                },
                {
                    fieldName: 'ma_tb',
                    headerText: 'Mã TB ',
                    width: 150,
                    allowfiltering: true,
                    textAlign: 'center'
                },
                {
                    fieldName: 'ma_tb_tinh',
                    headerText: 'Mã TB KN',
                    width: 150,
                    allowfiltering: true,
                    textAlign: 'center',
                    // width:'auto'
                },
                {
                    fieldName: 'trangthai',
                    headerText: 'Trạng thái',
                    width: 110,
                    allowfiltering: true,
                    textAlign: 'center'
                },
                {
                    fieldName: 'chuquan',
                    headerText: 'Chủ Quản',
                    width: 150,
                    allowfiltering: true,
                    textAlign: 'center'
                },
                {
                    fieldName: 'tentat',
                    headerText: 'Tỉnh KN',
                    width: 100,
                    allowfiltering: true,
                    textAlign: 'center'
                },
                {
                    fieldName: 'loaihinhtb',
                    headerText: 'Loại TB',
                    width: 90,
                    allowfiltering: true,
                    textAlign: 'center'
                },
                {
                    fieldName: 'chuquanld',
                    headerText: 'Chủ Quản LD',
                    allowfiltering: true,
                    width: 125,
                    textAlign: 'center'
                },
                {
                    fieldName: 'thuebao_id',
                    headerText: 'Thuê bao ID',
                    width: 120,
                    allowfiltering: true,
                    textAlign: 'center'
                },
                {
                    fieldName: 'loaitb_id',
                    headerText: 'Loại TB_ID',
                    width: 110,
                    allowfiltering: true,
                    textAlign: 'center'
                },
                {
                    fieldName: 'phanvung_id',
                    headerText: 'Tinh ID',
                    width: 90,
                    allowfiltering: true,
                    textAlign: 'center'
                },
                ],
                listTC: [],
                selected: [],
                value: null,
            },
            dataSources: [],
            rowSelected: null,
        }
    },
    async mounted() {
        this.LayDSTinh_Ban()
        this.LayDSTinh_TC()
        // const os = require('os');

        // const computerName = os.hostname();

        // console.log(`Computer Name: ${computerName}`);
        // this.txtGhi_chu.value = await this.$root.token.getMachine() //computerName //await this.$root.token.getMachine()
        // console.log('may cn ', this.txtGhi_chu.value);

    },
    methods: {
        grid_KT_TBB_Template: function (parent) {
            return function () {
                return {
                    template: {
                        template: `
            <div>
              <button  class="btn" style="background-color: blue; color: white" @click="tsbtnMap_Clicked">
                <span class="one-eye" style="font-size: 15px; display: inline-block"></span>
              </button> 
              <button class="btn" style="background-color: red; color: white" @click="tsbtnXoa_Clicked">
                <span class="one-trash" style="font-size: 15px; display: inline-block"></span>
              </button>
            </div>`,
                        data() {
                            return {
                                data: {}
                            }
                        },
                        computed: {},
                        async mounted() {
                            console.log(parent)
                        },
                        methods: {
                            async tsbtnXoa_Clicked() {
                                const confirm = await this.$confirm(
                                    'Bạn chắc chắn muốn xóa ',
                                    `Xác nhận xóa `, {
                                    confirmButtonText: 'Đồng ý',
                                    cancelButtonText: 'Hủy',
                                    type: 'warning',
                                },
                                );
                                if (confirm) {
                                    this.rowid = this.data.rowid
                                    this.phanvung_id = this.data.phanvung_id
                                    let input = {
                                        p_rowid: this.rowid,
                                        p_tinh: this.phanvung_id,
                                        p_ghichu: parent.txtGhi_chu.value
                                    }
                                    if (this.data.trangthai == 'OK') {
                                        this.$toast.error('Thuê bao chưa được map không thể xóa')
                                        return

                                    }
                                    await parent.btnXoa(input)
                                }
                            },
                            async tsbtnMap_Clicked() {
                                this.thuebao_kn_id = this.data.thuebao_kn_id
                                this.tinh_kn_id = this.data.tinh_kn_id
                                this.trangthai = this.data.trangthai
                                console.log("trang thai=", this.trangthai);
                                let input = {
                                    p_thuebao_kn_id: this.thuebao_kn_id,
                                    p_tinh_kn_id: this.tinh_kn_id
                                }
                                if (this.trangthai == 'Ban') {
                                    await parent.tracuu_mapthuebao_B(input)
                                } else if (this.trangthai == 'OK') {
                                    this.$toast.warning("Thuê bao chưa được được map")
                                } else this.$toast.warning("Thuê bao đang là thuê bao " + this.trangthai)
                            },
                        }
                    }
                }
            }
        },
        grid_KT_TBTC_Template: function (parent) {
            return function () {
                return {
                    template: {
                        template: `
            <div>
              <button  class="btn" style="background-color: blue; color: white" @click="tsbtnMap_Clicked">
                <span class="one-eye" style="font-size: 15px; display: inline-block"></span>
              </button> 
              <button class="btn" style="background-color: red; color: white" @click="tsbtnXoa_Clicked">
                <span class="one-trash" style="font-size: 15px; display: inline-block"></span>
              </button>
            </div>`,
                        data() {
                            return {
                                data: {}
                            }
                        },
                        computed: {},
                        async mounted() {
                            console.log(parent)
                        },
                        methods: {
                            async tsbtnXoa_Clicked() {
                                const confirm = await this.$confirm(
                                    'Bạn chắc chắn muốn xóa ',
                                    `Xác nhận xóa `, {
                                    confirmButtonText: 'Đồng ý',
                                    cancelButtonText: 'Hủy',
                                    type: 'warning',
                                },
                                );
                                if (confirm) {
                                    this.rowid = this.data.rowid
                                    this.phanvung_id = this.data.phanvung_id
                                    let input = {
                                        p_rowid: this.rowid,
                                        p_tinh: this.phanvung_id,
                                        p_ghichu: parent.txtGhi_chu.value
                                    }
                                    if (this.data.trangthai == 'OK') {
                                        this.$toast.error('Thuê bao chưa được map không thể xóa')
                                        return
                                    }
                                    await parent.btnXoa(input)
                                }
                            },
                            async tsbtnMap_Clicked() {
                                this.thuebao_kn_id = this.data.thuebao_kn_id
                                this.tinh_kn_id = this.data.tinh_kn_id
                                this.trangthai = this.data.trangthai
                                console.log("trang thai=", this.trangthai);
                                let input = {
                                    p_thuebao_kn_id: this.thuebao_kn_id,
                                    p_tinh_kn_id: this.tinh_kn_id
                                }
                                if (this.trangthai == 'TC') {
                                    await parent.tracuu_mapthuebao_TC(input)
                                } else if (this.trangthai == 'OK') {
                                    this.$toast.warning("Thuê bao chưa được được map")
                                } else this.$toast.warning("Thuê bao đang là thuê bao " + this.trangthai)
                            },
                        }
                    }
                }
            }
        },
        showModal() {
            // this.loading(true)
            // this.$refs['popupMapChuQuan'].show()
            // this.LayDSTinh_Ban()
            // this.LayDSTinh_TC()
            // this.loading(false)
        },
        hideModal() {
            // this.$refs['popupMapChuQuan'].hide()
            // this.txtMaTB_TB.value = ''
            // this.txtMaTB_TTC.value = ''
            // this.gridTinhBan.listBan = []
            // this.gridTinhThiCong.listTC = []
            // console.log('xoa');
        },
        kiemTra_ky_tu(str) {
            const specialChars = /[!@#$%^&*()+\=\\{};':"\\|,<>\/?]+/;
            return specialChars.test(str);
        },

        kiemTra_khoangTrang(str) {
            const khoangTrang = /\s/
            return khoangTrang.test(str)
        },
        kiemTra(str) {
            // if (this.kiemTra_ky_tu(str) || this.kiemTra_khoangTrang(str)) return true
            // else return false
            if (this.kiemTra_khoangTrang(str)) return true
            else return false
        },
        async LayDS() {

            if (this.txtMaTB_TB.value) this.onClickSearch_TB()
            if (this.txtMaTB_TTC.value) this.onClickSearch_TTC()
        },
        async btnXoa(input) {
            try {
                this.loading(true)
                // let ghiChu = this.txtGhi_chu.value.trim()

                if (!this.txtGhi_chu.value.trim()) {
                    this.$toast.error('Không có ghi chú')
                    console.log('ghi chu', ghiChu);

                    return
                }
                const response = await api.xoa_maptb(input)
                if ((response.data.error_code == 'BSS-00000000')) {
                    this.$toast.success('Xóa thành công!')
                    console.log("thanh cong");
                    await this.LayDS()
                } else {
                    this.$toast.error('Xóa không thành công!')
                }
            } catch (e) {
                console.log(e.message);

            } finally {


                this.loading(false)
            }
        },
        GetData: function (response) {
            if (response.data.error === 200 || response.data.error === '200') {
                return response.data.data
            } else {
                if (response.data.data) {
                    return response.data.data
                }
                console.log(response.data.error_code)
            }
            return []
        },
        async LayDSTinh_Ban() {
            console.log("tinh ban");
            try {
                this.loading(true)
                let input = {
                    p_phanvung_id: this.$root.token.getPhanVungID()
                }
                const response = await api.Lay_ds_Tinh_v3(input)
                this.DsTinhBan = apiHelper.getDataFromResponseApiReturnArray(response) || []
                this.DsTinhBan = this.DsTinhBan.map((e) => ({
                    id: e.tinh_id,
                    text: e.ten_tinh
                }))
                this.tinhBanId = this.DsTinhBan[0].id
                // console.log("tinh ban", this.DsTinhBan);
            } catch (e) {
                console.log("e", e)
            } finally {
                this.loading(false)
            }
        },
        async LayDSTinh_TC() {
            console.log("tinh thi cong");
            try {
                this.loading(true)
                let input = {
                    p_phanvung_id: this.$root.token.getPhanVungID()
                }
                const response = await api.Lay_ds_Tinh_v3(input)
                this.DsTinhThiCong = apiHelper.getDataFromResponseApiReturnArray(response) || []
                this.DsTinhThiCong = this.DsTinhThiCong.map((e) => ({
                    id: e.tinh_id,
                    text: e.ten_tinh
                }))
                this.tinhTC_Id = this.DsTinhThiCong[0].id
            } catch (e) {
                console.log("e", e)
            } finally {
                this.loading(false)
            }
        },
        async onClickSearch_TB() {
            try {
                this.loading(true)
                if (!this.txtMaTB_TB.value) {
                    this.$toast.error("Chưa nhập mã thuê bao tỉnh bán")
                    return
                }
                if (this.kiemTra(this.txtMaTB_TB.value)) {
                    this.$toast.warning('Mã thuê bao tỉnh bán không hợp lệ!')
                    return
                }
                if (!this.txtMaTB_TTC.value) {
                    this.gridTinhThiCong.listTC = []
                }
                let input = {
                    p_ma_tb: this.txtMaTB_TB.value,
                    p_tinh: this.tinhBanId,
                    p_trangthai: "" //1: tỉnh bán
                }
                let response = this.GetData(await api.tra_cuu_v2(input))
                // this.ds = apiHelper.getDataFromResponseApiReturnArray(response) || []
                // this.gridTinhThiCong.listTC = response.filter(item => item.trangthai == 'TC')
                this.gridTinhBan.listBan = response
                // this.gridTinhBan.listBan = response.filter(item => item.trangthai == 'Ban'|| item.trangthai=='OK')
                console.log(this.gridTinhBan.listBan);
                const TC = this.gridTinhThiCong.listTC.length
                const TB = this.gridTinhBan.listBan.length
                if (TB == 0) {
                    this.$toast.error("Không tìm thấy thuê bao tại tỉnh bán")
                }
            } catch (e) {
                console.log("e", e)
            } finally {
                this.loading(false)
            }
        },
        async onClickSearch_TTC() {
            try {
                this.loading(true)
                if (!this.txtMaTB_TTC.value) {
                    this.$toast.error("Chưa nhập mã thuê bao tinh thi công")
                    return
                }
                if (this.kiemTra(this.txtMaTB_TTC.value)) {
                    this.$toast.warning('Mã thuê bao tỉnh thi công không hợp lệ!')
                    return
                }
                if (!this.txtMaTB_TB.value) {
                    this.gridTinhBan.listBan = []
                }
                let input = {
                    p_ma_tb: this.txtMaTB_TTC.value,
                    p_tinh: this.tinhTC_Id,
                    p_trangthai: "" //2: Tỉnh thi công
                }
                let response = this.GetData(await api.tra_cuu_v2(input))
                // this.ds = apiHelper.getDataFromResponseApiReturnArray(response) || []
                console.log(response);
                // this.gridTinhThiCong.listTC = response.filter(item => item.trangthai == 'TC'||item.trangthai=='OK' )
                this.gridTinhThiCong.listTC = response
                console.log(this.gridTinhBan.listBan)
                const TC = this.gridTinhThiCong.listTC.length
                const TB = this.gridTinhBan.listBan.length
                if (TC == 0) {
                    this.$toast.error("Không tìm thấy thuê bao tại tỉnh thi công")
                }
            } catch (e) {
                console.log("e", e)
            } finally {
                this.loading(false)
            }
        },
        onActionClick(action) {
            if (action.id == 'tracuu') {
                console.log("tra cuu");
                this.LayDS()
            }
            if (action.id == 'mapTB') {
                console.log("Map thuê bao");
                this.mapTB()
            }
        },
        async mapTB() {
            try {
                this.loading(true)
                // const obj = []
                let ghiChu = this.txtGhi_chu.value.trim()
                if (!this.txtGhi_chu.value.trim()) {
                    this.$toast.error('Không có ghi chú')
                    return
                }
                const arr = {
                    TINH_BAN: this.tinhBanId,
                    THUEBAOID_BAN: this.thuebaoid,
                    THUEBAOID_TC: this.thuebao_kn_id,
                    TINH_TC: this.tinhTC_Id,
                    GHICHU: this.txtGhi_chu.value
                }
                // obj.push(arr)
                // console.log(obj);

                let input = {
                    p_matb_ban: this.txtMaTB_TB.value,
                    p_may_cn: await this.$root.token.getMachine(),
                    p_matb_tc: this.txtMaTB_TTC.value,
                    p_data: JSON.stringify(arr)
                }
                console.log(this.gridTinhBan.listBan);
                if (this.gridTinhBan.listBan.length == 0) {
                    this.$toast.error("Chưa có thông tin thuê bao tỉnh bán")
                    return
                } else if (this.gridTinhThiCong.listTC.length == 0) {
                    this.$toast.error("Chưa có thông tin thuê bao tỉnh thi công")
                    return
                } else {
                    if (this.trangthai_TB == 'OK' && this.trangthai_TC == "OK") {
                        if (this.loaiTBid_TC == this.loaiTBid_Ban) {
                            let kq = await api.map_tb_bancheo(input)
                            if (kq.data.error === 200 || kq.data.error === '200') {
                                if (kq.data.data == "OK") {
                                    this.$toast.success("Map thuê bao thành công")
                                    this.LayDS()
                                }
                                else {
                                    this.$toast.error(kq.data.data)
                                }

                                // console.log(response.data.error_code)
                                console.log("test");

                            }
                        } else this.$toast.error("Loại thuê bao không khớp")
                    } else this.$toast.error("Thuê bao đã được map")
                }

            } catch (e) {
                // console.log("err", e)
                if (e.data && e.data.message) {
                    this.$toast.error(e.data.message)
                } else if (e.response && e.response.data && e.response.data.message) {
                    this.$toast.error(e.response.data.message)
                }
            } finally {
                this.loading(false)
            }
        },
        async tracuu_mapthuebao_B(input) {
            try {
                this.loading(true)
                this.gridTinhThiCong.listTC = []
                const response = await api.tra_cuu_tb_map(input)
                this.gridTinhThiCong.listTC = apiHelper.getDataFromResponseApiReturnArray(response) || []
                if (this.gridTinhThiCong.listTC.length == 0) {
                    this.$toast.info("Không có dữ liệu")
                    this.gridTinhThiCong.listTC = []
                }
            } catch (error) { } finally {
                this.loading(false)
            }
        },
        async tracuu_mapthuebao_TC(input) {
            try {
                this.loading(true)
                this.gridTinhBan.listBan = []
                const response = await api.tra_cuu_tb_map(input)
                console.log(response);
                this.gridTinhBan.listBan = apiHelper.getDataFromResponseApiReturnArray(response) || []
                if (this.gridTinhBan.listBan.length == 0) {
                    this.$toast.info("Không có dữ liệu")
                    this.gridTinhBan.listBan = []
                }
            } catch (error) { } finally {
                this.loading(false)
            }
        },
        async change_TTC(e) {
            this.trangthai_TC = e.data.trangthai
            this.thuebao_kn_id = e.data.thuebao_id
            this.loaiTBid_TC = e.data.loaitb_id
            console.log(this.thuebao_kn_id);
        },
        async change_TB(e) {
            this.trangthai_TB = e.data.trangthai
            // this.id = 'Ban'
            this.thuebaoid = e.data.thuebao_id
            this.loaiTBid_Ban = e.data.loaitb_id
            console.log(this.thuebaoid);
        }
    },
}
</script>

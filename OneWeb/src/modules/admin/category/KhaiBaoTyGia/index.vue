<template>
    <div>
        <breadcrumb title_in="KHAI BÁO TỶ GIÁ"/>
        <vue-nav>
            <ul class="list">

                <li :class="!button.taomoi ? 'non-ative' : ''" @click="clickButton('taomoi')">
                    <a>
                        <span class="icon one-file-plus"></span>Tạo mới
                    </a>
                </li>
                <li :class="!button.ghilai ? 'non-ative' : ''" @click="clickButton('ghilai')">
                    <a>
                        <span class="icon one-save"></span>Ghi lại
                    </a>
                </li>
                <li :class="!button.huybo ? 'non-ative' : ''" @click="clickButton('huybo')">
                    <a>
                        <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy
                    </a>
                </li>
                <li :class="!button.xoa ? 'non-ative' : ''" @click="clickButton('xoa')">
                    <a>
                        <span class="icon one-trash"></span>Xóa
                    </a>
                </li>
            </ul>
            <div class="more dropdown">
                <a href="#" data-toggle="dropdown">
                <span class="icon -ap  icon-dots-three-vertical"></span>
                </a>
            </div>
        </vue-nav>
        <div class="page-content">
            <vue-card label="Thông tin tỷ giá">
                <b-row>
                    <b-col md="6">
                        <div class="info-row">
                            <div class="key w105">Ngân hàng (*)</div>
                            <div class="value">
                                <Select2Ext @change="nganHangChange" ref="cboNganHang" v-model="form.NGANHANG_ID"/>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w105">Tỷ giá</div>
                            <div class="value">
                                <div class="tright">
                                <vue-numeric class="form-control tright font-weight-bold"
                                    :class='"txtTyGia"'
                                    @keyup="checkTyGia" 
                                    ref="txtTyGia"
                                    separator="." 
                                    decimal-separator="," 
                                    v-model="form.TYGIA"
                                    :precision="2"
                                    placeholder="0" 
                                    output-type="Number">
                                </vue-numeric>
                                </div>
                            </div>
                        </div>
                    </b-col>
                    <b-col md="6">
                        <div class="info-row">
                            <div class="key w105">Loại ngoại tệ</div>
                            <div class="value">
                                    <Select2Ext @change="loaiNgoaiTeChange" ref="cboLoaiNgoaiTe" v-model="form.LOAINGOAITE_ID"/>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w105 nowrap">Ngày tỷ giá</div>
                            <div class="value">
                                <div class="input-icon-right">
                                    <flat-pickr class="form-control" 
                                    :config="dateconfig" 
                                    v-model="form.NGAYTYGIA"  
                                    ref="dtpNgayTyGia"/>
                                    <span class="icon one-calendar"></span>
                                </div>
                            </div>
                        </div>
                    </b-col>
                </b-row>
            </vue-card>
            <vue-card label="Danh sách cập nhật tỷ giá">
                <div class="row">
                    <div class="col-sm-3 col-12">
                        <div class="info-row">
                            <div class="key w30 nowrap">
                                    Từ ngày
                            </div>
                            <div class="value">
                                <VueDateExt ref="tungay" @change="tungayChanged" type="Date" format="DD/MM/YYYY"/>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-3 col-12">
                        <div class="info-row">
                            <div class="key w30 nowrap">
                                Đến ngày
                            </div>
                            <div class="value">
                                <VueDateExt ref="denngay" @change="denngayChanged" type="Date" format="DD/MM/YYYY"/>
                                    
                            </div>
                        </div>
                    </div>
                </div>
                <DataGrid
                    v-bind:dataSource="dataSourceCapNhatTyGia"
                    v-bind:columns="config.collumnsCapNhatTyGia"
                    :showColumnCheckbox="false"
                    :allowPaging="true"
                    :showFilter="true"
                    ref="danhsachCapNhatTyGia"
                    :enablePagingServer="false"
                    @selectedItemsChanged="grvDanhSachCapNhatTyGia_FocusedRowChanged"
                    @rowClicked="focusItem"
                    :enabledSelectFirstRow="false"
                    @actionComplete="SelectRowChonDanhSachCapNhatTyGia"
                />
            </vue-card>
        </div>
    </div>
</template>
<script>
import define from './define.js'
import breadcrumb from "@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue"
import DataGridCustom from "@/modules/contract/setup/DeclareLandline/components/DataGridCustom"
import Select2Ext from "@/modules/contract/setup/DeclareLandline/components/Select2Ext"
import VueDateExt from '@/modules/contract/setup/DeclareLandline/components/VueDateExt'
import * as moment from 'moment'
import debounce from 'lodash/debounce'
import { parseDate } from '@/utils/format.js'

export default {
    components: {
        breadcrumb,
        DataGridCustom,
        Select2Ext,
        VueDateExt
    },
    mounted : async function() {
        await this.getThongtin();
    },
    data() {
        return {
            ...define,
            dateconfig: {
                altFormat: 'd/m/Y',
                altInput: true,
                dateFormat: 'd/m/Y',
                allowInput:true,
            },
            callCount: 0,
            methodCalled: false,
            dateFormat: "DD/MM/YYYY",
            selectItem: null,
            nganHang: 0,
            loaiNgoaiTe: 0,
            tyGia : 0,
            dsTyGia: [],
            dtDsCapNhatTyGia: [],
            tungay: new Date(),
            denngay: new Date(),

            // popupComponent: null,
            popupComponentData: null,
            popupComponentAttr: {},
            // userComponentModule: null,
            popupComponentEvts: {
                "form-close": this.popupClosed,
                acceptChangeCTV: this.popupClosed,
                acceptChangeNGT: this.popupClosed,
            },
        };
    },
    computed: {
        tygia: {
            get() {
                return this.form.TYGIA ? this.form.TYGIA : 0
            },
            set(value) {
                this.form.TYGIA = value ? value.replaceAll(",", "").replaceAll(".", "") : 0
            }
        },
        dataSourceCapNhatTyGia() {
            return this.data.cnTyGia.filter(item => item.TYGIA_ID == this.TYGIA_ID).sort((a, b) => {
                return b.TYGIA_ID - a.TYGIA_ID
            });
        }
    },
    created() {
        // Debounce the tungayChanged method
        this.tungayChanged = debounce(this.tungayChanged, 300);
        this.denngayChanged = debounce(this.denngayChanged, 300);
    },
    methods: {
        getThongtin: async function () {
            return new Promise(async (resolve, reject) => {
                try {
                    this.loading(true);
                    let rs = await this.axios.get('/web-hopdong/quanlytygia/lay_tt_nganhang' )
                    if(rs && rs.data && rs.data.data && rs.data.error_code && rs.data.error_code === "BSS-00000000") {
                        if (rs.data.data.ERROR_CODE != -1) {
                            this.loading(false);
                            let ds = rs.data.data;
                            this.$refs.cboNganHang.dataSource = ds;
                            this.$refs.cboNganHang.dataTextField = "ten_nh";
                            this.$refs.cboNganHang.dataValueField = "nganhang_id";
                            this.$refs.cboNganHang.value = null
                        } else {
                            this.loading(false);
                            this.$toast.error(rs.data.data.MESSAGE)
                        }
                    } 

                    rs = await this.axios.get('/web-hopdong/quanlytygia/lay_tt_loaitien' )
                    if(rs && rs.data && rs.data.data && rs.data.error_code && rs.data.error_code === "BSS-00000000") {
                        if (rs.data.data.ERROR_CODE != -1) {
                            this.loading(false);
                            let ds = rs.data.data;
                            this.$refs.cboLoaiNgoaiTe.dataSource = ds;
                            this.$refs.cboLoaiNgoaiTe.dataTextField = "ma_loaitien";
                            this.$refs.cboLoaiNgoaiTe.dataValueField = "loaitien_id";
                            this.$refs.cboLoaiNgoaiTe.value = null
                        } else {
                            this.loading(false);
                            this.$toast.error(rs.data.data.MESSAGE)
                        }
                    } 

                    if(!this.button.taomoi) {
                        this.current = 0
                    }
                    rs = await this.axios.post("/web-hopdong/quanlytygia/sp_lay_ds_tygia", 
                    { "vtungay": '0', "vdenngay": '0' });
                    if(rs && rs.data && rs.data.data && rs.data.error_code && rs.data.error_code === "BSS-00000000") {
                        if (rs.data.data.ERROR_CODE != -1) {
                            this.loading(false);
                            let ds = []
                            rs.data.data.forEach((element, index) => {
                                element.tygia = element.tygia.toLocaleString("vi-VN")
                                ds.push(element)
                            })
                            this.$set(this.data, 'cnTyGia', ds)
                            this.SetButton(1);
                            this.$refs.danhsachCapNhatTyGia.setCurrentSelectedRow(this.current);
                            this.$refs.danhsachCapNhatTyGia.flagSelectedRowIndexes = [];
                            this.$refs.danhsachCapNhatTyGia.flagSelectedRowIndexes.push(this.current);
                            return rs.data.statusText
                        } else {
                            this.loading(false);
                            this.$toast.error(rs.data.data.MESSAGE)
                        }
                    } 
                } catch (error) {
                    this.loading(false);
                    this.$toast.error("Lỗi lấy thông tin " + error.message)
                }
                resolve(true)
            })
        },
        async LayDanhSachCapNhatTyGia(tuNgay, denNgay) {
            try {
                if(!this.button.taomoi) {
                    this.current = 0
                }
                this.loading(true);
                let rs = await this.axios.post("/web-hopdong/quanlytygia/sp_lay_ds_tygia", 
                { "vtungay": tuNgay, "vdenngay": denNgay })
                if(rs && rs.data && rs.data.data && rs.data.error_code && rs.data.error_code === "BSS-00000000") {
                    if (rs.data.data.ERROR_CODE != -1) {
                        this.loading(false)
                        let ds = []
                        rs.data.data.forEach((element, index) => {
                            element.tygia = element.tygia.toLocaleString("vi-VN")
                            ds.push(element)
                        })
                        this.$set(this.data, 'cnTyGia', ds)
                        this.SetButton(1)
                        this.$refs.danhsachCapNhatTyGia.setCurrentSelectedRow(this.current)
                        this.$refs.danhsachCapNhatTyGia.flagSelectedRowIndexes = []
                        this.$refs.danhsachCapNhatTyGia.flagSelectedRowIndexes.push(this.current)
                        this.$toast.success("Cập nhật dữ liệu thành công!")
                    } else {
                        this.loading(false)
                        this.$toast.error(rs.data.data.MESSAGE)
                    }
                } 
                this.loading(false)
            }
            catch (err) {
                this.loading(false)
                this.$toast.error(err.message)
            }
        },
        async nganHangChange()
        {
            if(this.$refs.cboNganHang.value > 0) {
                this.form.NGANHANG_ID = this.$refs.cboNganHang.value;
            }
        },
        async loaiNgoaiTeChange()
        {
            if(this.$refs.cboLoaiNgoaiTe.value > 0) {
                this.form.LOAINGOAITE_ID = this.$refs.cboLoaiNgoaiTe.value;
            }
        },
        tungayChanged(value){
            if (parseDate(value) > parseDate(this.denngay)) {
                this.$toast.error("Từ ngày không được lớn hơn đến ngày!");
                this.methodCalled = false;
                this.callCount = 0;
                return;
            }
            if (parseDate(value) === parseDate(this.denngay)){
                this.methodCalled = true;
                this.LayDanhSachCapNhatTyGia(value, parseDate(this.denngay));
                this.callCount++;
                this.tungay = value
            }
            if (!this.methodCalled) {
                this.methodCalled = true;
                this.LayDanhSachCapNhatTyGia(value, parseDate(this.denngay));
                this.callCount++;
                this.tungay = value
            }
            if (this.callCount >= 1) {
                this.methodCalled = false;
                this.callCount = 0;
            }
        },
        denngayChanged(value){
            if (parseDate(value) < parseDate(this.tungay)) {
                this.$toast.error("Đến ngày không được nhỏ hơn từ ngày!" )
                this.methodCalled = false;
                this.callCount = 0;
                return;
            }
            if (parseDate(value) === parseDate(this.tungay)){
                this.methodCalled = true;
                this.LayDanhSachCapNhatTyGia(parseDate(this.tungay), value);
                this.callCount++;
                this.denngay = value
            }
            if (!this.methodCalled) {
                this.methodCalled = true;
                this.LayDanhSachCapNhatTyGia(parseDate(this.tungay), value);
                this.callCount++;
                this.denngay = value
            }
            if (this.callCount >= 1) {
                this.methodCalled = false;
                this.callCount = 0;
            }
        },
        SelectRowChonDanhSachCapNhatTyGia (args) {
            if (this.selectItem != null) {
                let index = null
                if (index >= 0) {
                this.$refs.danhsachCapNhatTyGia.setCurrentSelectedRow(index)
                }
            } else {
                this.$refs.danhsachCapNhatTyGia.setCurrentSelectedRow(0)
            }
        },
        async clickButton(key) {
            if(!this.button[key])
                return false
            if (key == 'taomoi') {
                if (this.$root.token.getPhanVungID() == 97 || this.$root.token.getPhanVungID() == 98){
                    console.log(this.$root.token.getPhanVungID(), "this.$root.token.getPhanVungID()")
                    this.form.NGANHANG_ID = 7
                    this.$refs.cboNganHang.value = 7
                    this.$refs.cboNganHang.SelectedIndex = 7-1
                } else {
                    this.form.NGANHANG_ID = null
                    this.$refs.cboNganHang.value = null
                }
                
                this.form.LOAINGOAITE_ID = 2
                this.$refs.cboLoaiNgoaiTe.value = 2
                this.$refs.cboLoaiNgoaiTe.SelectedIndex = 2-1 
                this.form.NGAYTYGIA = new Date()
                this.form.TYGIA = 0
                this.$refs.txtTyGia.value = 0
                this.SetButton(1)
                this.focusByClass('txtTyGia');
            }
            if (key == 'ghilai') {
                this.GhiLai()
            }
            if (key == 'xoa') {
                this.tsbtnDelete_Click()
            }
            if(key == 'huybo') {
                this.SetButton(0);
                let selected = this.$refs.danhsachCapNhatTyGia.getSelectedRecords()
                this.grvDanhSachCapNhatTyGia_FocusedRowChanged(selected)
                this.LayDanhSachCapNhatTyGia('0','0')
            }
        },
        async tsbtnDelete_Click() {
            this.$confirm(`Bạn thật sự muốn xóa dữ liệu không ?`, {
                confirmButtonText: "Có",
                cancelButtonText: "Không",
                type: "delete",
            }).then(async () => {
                try {
                    this.loading(true)
                    let data = this.$refs.danhsachCapNhatTyGia.getSelectedRecords()
                    this.tygia_id_s = data[0].tygia_id
                    let rs = await this.axios.post("/web-hopdong/quanlytygia/fn_xoa_tygia", 
                        {"p_ds_para": JSON.stringify({ 
                                    "TYGIA_ID": this.tygia_id_s,
                                    "NGUOI_CN": this.$root.token.getUserName(),
                                    "MAY_CN": await this.$root.token.getMachine(),
                                    "IP_CN": await this.$root.token.getIP()
                                })}
                            )
                    if(rs && rs.data && rs.data.data && rs.data.error_code && rs.data.error_code === "BSS-00000000") {
                        if (rs.data.data.ERROR_CODE != -1) {
                            this.$toast.success("Xóa thành công!");
                            this.current = 0
                            this.SetButton(1);
                        } else {
                            this.loading(false);
                            this.$toast.error(rs.data.data.MESSAGE)
                        }
                    } 
                    
                    this.LayDanhSachCapNhatTyGia('0','0');
                    this.loading(false);
                } catch (err) {
                    console.log(err)
                    this.$toast.error( "Không thể xóa bản ghi này!" );
                    this.loading(false);
                }
            });
        },
        numberWithCommas(n) {
            if(!n && n != 0 ) return
            var parts = n.toString().split(",");
            return (
                parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ".") +
                (parts[1] ? "," + parts[1] : "")
            );
        },
        async GhiLai() {
            
            if(!this.KiemTra_DuLieu())
                return false
            this.loading(true)
            try {
                let kieu = 1
                if (!this.button.taomoi) {
                    kieu = 0
                }
                let postData = this.form
                if (kieu == 0) {
                    let rs = await this.axios.post("web-hopdong/khoiphucthanhly/get_keys", {"keyname": "TYGIA_ID"});
                    if(rs && rs.data && rs.data.data && rs.data.error_code && rs.data.error_code === "BSS-00000000") {
                        if (rs.data.data.ERROR_CODE != -1) {
                            this.tygia_id_s = rs.data.data;
                            console.log(this.tygia_id_s, 'this.khoiphucthanhly/get_keys')
                        } else {
                            this.$toast.error(rs.data.data.MESSAGE)
                        }
                    }
                } 
                
                if (kieu == 1) {
                    let data = this.$refs.danhsachCapNhatTyGia.getSelectedRecords()
                    this.tygia_id_s = data[0].tygia_id
                }
                let rs = await this.axios.post("/web-hopdong/quanlytygia/fn_capnhat_tygia", 
                    { "p_ds_para": JSON.stringify(
                        {"KIEU": kieu ,
                            "DS_TYGIA":[
                                {
                                    "PHANVUNG_ID": this.$root.token.getPhanVungID(),
                                    "TYGIA_ID": this.tygia_id_s,
                                    "LOAITIEN_ID": postData.LOAINGOAITE_ID,
                                    "NGAY_TYGIA": postData.NGAYTYGIA,
                                    "TYGIA": postData.TYGIA,
                                    "NGAY_CN": new Date(),
                                    "NGUOI_CN": this.$root.token.getUserName(),
                                    "MAY_CN": await this.$root.token.getMachine(),
                                    "IP_CN": await this.$root.token.getIP(),
                                    "NGANHANG_ID": postData.NGANHANG_ID
                                } 
                            ]
                        } ) 
                    }
                )
                if(rs && rs.data && rs.data.data && rs.data.error_code && rs.data.error_code === "BSS-00000000") {
                    if (rs.data.data.ERROR_CODE != -1) {
                        if(this.button.taomoi) {
                            this.loading(false)
                            this.$toast.success("Cập nhật dữ liệu thành công")
                        } else {
                            if (rs.data.data.ERROR_CODE == 0) {
                                this.loading(false)
                                this.$toast.error(rs.data.data.MESSAGE)
                                return false
                            } else {
                                this.loading(false)
                                this.$toast.success("Thêm mới dữ liệu thành công")
                            }
                        }
                        this.LayDanhSachCapNhatTyGia('0','0')
                        this.selectItem = postData
                    } else {
                        this.loading(false)
                        this.$toast.error(rs.data.data.MESSAGE)
                        return false
                    }
                } 
                this.loading(false)
            } catch (err) {
                this.$toast.error(err)
                this.loading(false)
            }
        },
        checkTyGia(){
            let status = true
            if (this.form.TYGIA == 0)
            {
                this.$toast.error("Tỷ giá phải lớn hơn 0 !");
                this.focusByClass('txtTyGia');
                status = false;
            }

            if(this.form.TYGIA == "") {
                this.form.TYGIA = 0
            }

            if (isNaN(this.form.TYGIA)) {
                this.form.TYGIA = 0;
            }
            return status
        },
        checkNganHang() {
            let status = true
            if (this.$refs.cboNganHang.value == null)
            {
                this.$toast.error("Vui lòng chọn ngân hàng!");
                this.$refs.cboNganHang.focus()
                this.$refs.cboNganHang.classList.add("error")
                status = false;
            }
            return status
        },
        checkLoaiNgoaiTe() {
            let status = true
            if (this.$refs.cboLoaiNgoaiTe.value == null)
            {
                this.$toast.error("Vui lòng chọn loai ngoại tệ!");
                this.$refs.cboLoaiNgoaiTe.focus()
                this.$refs.cboLoaiNgoaiTe.classList.add("error")
                status = false;
            }
            return status
        },
        KiemTra_DuLieu()
        {
            if(!this.checkTyGia()) return false
            if(!this.checkNganHang()) return false
            if(!this.checkLoaiNgoaiTe()) return false
            return true
        },
        grvDanhSachCapNhatTyGia_FocusedRowChanged(data) {
            setTimeout(() => {}, 500);
            if (data && data[0]) {
                this.SetButton(3);
                this.form.NGANHANG_ID = data[0].nganhang_id
                // this.form.TEN_NH = data[0].ten_nh
                this.form.LOAINGOAITE_ID = data[0].loaitien_id
                // this.form.LOAITIEN = data[0].ma_loaitien
                this.form.NGAYTYGIA = moment(data[0].ngay_tygia).format(this.dateFormat)
                this.form.TYGIA = this.numberWithCommas(data[0].tygia)
                this.form.TYGIA_ID = data[0].tygia_id
                this.$refs.cboNganHang.value = data[0].nganhang_id
                this.$refs.cboLoaiNgoaiTe.value = data[0].loaitien_id
                this.tygia_id_s = data[0].tygia_id
            } else {
                this.Clear()
            }
        },
        focusItem(index, data) {
            this.current = index
        },
        SetButton(kieu) {
            this.button.taomoi = false;
            this.button.ghilai = false;
            this.button.xoa = false;
            this.button.huybo = false;
            if (kieu == -1)//Bat dau
            {
                this.button.ghilai = true;
                this.button.huybo = true;
            }
            if (kieu == 0)//Bat dau
            {
                this.button.taomoi = true;
            }
            if (kieu == 1)//Them moi
            {
                this.button.ghilai = true;
                this.button.huybo = true;
            }
            if (kieu == 2)//Huy
            {
                this.button.taomoi = true;
                this.button.xoa = true;
            }
            if (kieu == 3)//Edit
            {
                this.button.taomoi = true;
                this.button.ghilai = true;
                this.button.xoa = true;
                this.button.huybo = true;
            }
        },
        Clear() {
            this.tungay = new Date();
            this.denngay = new Date();
            this.$refs.cboLoaiNgoaiTe.value = null;
            this.$refs.cboNganHang.value = null;
            this.form.NGANHANG_ID = null;
            this.form.LOAINGOAITE_ID = null;
            this.form.TYGIA = 0;
            this.form.NGAYTYGIA = new Date();
        },
        focusByClass(classText){
            const collection = document.getElementsByClassName(classText);
            if(collection!=null && collection.length > 0){
                collection[0].focus()
            }
        },
    }
}
</script>
<style>

.error {
    border: 1px solid red !important;
}
.non-ative a {
    color: #d3d3d3 !important;
}
</style>
<template>
    <div>
        <breadcrumb title_in="Kiểu số đẹp"/>
        <vue-nav>
            <ul class="list">
                <!-- <li>
                    <a @click="btnOpenfrmDangKyGoiDaDVLM">
                        Test Popup
                    </a>
                </li> -->
                <li :class="!button.nhapmoi ? 'non-ative' : ''" @click="clickButton('nhapmoi')">
                    <a>
                        <span class="icon one-file-plus"></span>Nhập mới
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
                <li @click="clickButton('phanloai')">
                    <a>
                        <span class="icon one-sitemap"></span>Phân loại số
                    </a>
                </li>
                <li>
                    <a @click="btnOpenfrmDangKyGoiDaDVLM">
                        <span class="icon one-sitemap"></span>Test form
                    </a>
                </li>
            </ul>
        </vue-nav>
        <div class="page-content">
            <vue-card label="Thông tin kiểu số đẹp">
                <b-row>
                    <b-col md="6">
                        <div class="info-row">
                            <div class="key w100">Nhóm số đẹp</div>
                            <div class="value">
                                <div class="input-more-button -right">
                                    <button @click="cboNhomSo_ButtonClick()" class="btn btn-main">
                                        <span class="-ap icon-plus3"></span>
                                    </button>
                                        <SelectExt
                                            v-model="NHOMSO_ID"
                                            :allowFiltering="true"
                                            dataTextField="NHOM_SO"
                                            dataValueField="NHOMSO_ID"
                                            :dataSource="options.nhom_so_dep"
                                        ></SelectExt>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Tên kiểu số</div>
                            <div class="value">
                                <input
                                    type="text"
                                    v-model="form.KIEU_SO"
                                    ref="txtKieuSo"
                                    class="form-control"
                                    @keyup="checkKieuSo"
                                />
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Tiền hòa mạng</div>
                            <div class="value">
                                <input
                                    class="form-control tright red"
                                    type="text"
                                    ref="txtTienHM"
                                    v-model="tienhoamang"
                                    @keyup="checkTienHM"
                                />
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Ghi chú</div>
                            <div class="value">
                                <ejs-textbox
                                    type="text"
                                    v-model="form.GHICHU"
                                    class="form-control"
                                />
                            </div>
                        </div>
                    </b-col>
                    <b-col md="6">
                        <div class="info-row">
                            <div class="key w50">Mô tả</div>
                            <div class="value">
                                <textarea
                                    name
                                    class="form-control"
                                    style="height: 140px;resize: none;"
                                    ref="txtMoTa"
                                    v-model="form.MOTA"
                                    @keyup="checkMota"
                                ></textarea>
                            </div>
                        </div>
                    </b-col>
                    <b-col md="12">
                        <div class="info-row">
                            <div class="key w100">Định nghĩa</div>
                            <div class="value">
                                <textarea
                                    id="dinhnghia-input"
                                    v-model="form.DINHNGHIA"
                                    name
                                    class="form-control"
                                    style="height: 140px;resize: none;"
                                ></textarea>
                            </div>
                            <b-tooltip target="dinhnghia-input" custom-class="custom-tooltip" triggers="hover">
                                <div v-html="tooltip"></div>
                            </b-tooltip>
                        </div>
                    </b-col>
                </b-row>
            </vue-card>
            <vue-card label="Danh sách kiểu số">
                <DataGrid
                    v-bind:dataSource="kieusoDataSource"
                    v-bind:columns="config.collumnsNV"
                    :showColumnCheckbox="false"
                    :allowPaging="true"
                    :showFilter="true"
                    ref="danhsach"
                    :enablePagingServer="false"
                    @selectedItemsChanged="grvDanhSach_FocusedRowChanged"
                    @rowClicked="focusItem"
                    :enabledSelectFirstRow="false"
                    @actionComplete="SelectRowChon"
                />
            </vue-card>
        </div>
        <frmNhomSo :dsKhieuso="kieusoDataSource" @must_update="LayDanhsachNhomso" ref="frmNhomSo" />
        <frmDangKyGoiDaDVLM ref="frmDangKyGoiDaDVLM" />
        <popupChonSo_CNTT ref="popupChonSo_CNTT" />
        <frmThongSoTSL ref="frmThongSoTSL" />
    </div>
</template>
<script>
import define from './define.js'
import frmNhomSo from './popups/frmNhomSo'
import frmDangKyGoiDaDVLM from '@/modules/admin/category/NiceNumberStyle/popups/frmDangKyGoiDaDVLM.vue'
import popupChonSo_CNTT from '@/modules/contract/setup/InstallNewSubs/Popups/popupChonSo_CNTT_copy.vue'
import breadcrumb from "@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue";
import frmThongSoTSL from "@/modules/contract/setup/InstallNewSubsCrossSale/Popups/frmThongSoTSL/frmThongSoTSL.vue"

export default {
    data() {
        return {
            ...define,
            selectItem: null
        };
    },
    components: {
        frmNhomSo,
        frmDangKyGoiDaDVLM,
        popupChonSo_CNTT,
        breadcrumb,
        frmThongSoTSL
    },
    mounted() {
            this.tooltip += "<font color=red>Định nghĩa để tự động gán số đẹp</font><br>";
            this.tooltip += "<font color=blue>Ví dụ: Số đẹp có 4 số cuối giống nhau</font><br>";
            this.tooltip += "<font color=red>substr(somay,2,1) = substr(somay,3,1),substr(somay,3,1) = substr(somay,4,1),substr(somay,5,1) = substr(somay,6,1),substr(somay,6,1) = substr(somay,7,1)</font><br>";
            this.tooltip += "<font color=blue>Nếu có 2 định nghĩa số đẹp, thì mỗi định nghĩa ở trên 1 dòng</font><br>";
            this.frmKieuSo_Load();
    },
    computed: {
        tienhoamang: {
            get() {
                return this.numberWithCommas(this.form.TIEN_HM ? this.form.TIEN_HM : 0)
            },
            set(value) {
                this.form.TIEN_HM = value ? value.replaceAll(",", "") : 0
            }
        },
        kieusoDataSource() {
            return this.data.kieu_so.filter(item => item.NHOMSO_ID == this.NHOMSO_ID).sort((a, b) => {
                return b.KIEUSO_ID - a.KIEUSO_ID
            });
        }
    },
    methods: {
        testPopup() {
            this.$refs.frmThongSoTSL.showDialog()
        },
        btnOpenfrmDangKyGoiDaDVLM() {
            this.$refs.frmDangKyGoiDaDVLM.showDialog()
        },
        checkTienHoaMang() {
            if(isNaN(this.form.TIEN_HM)) {
                this.form.TIEN_HM = 0
            }
        },
        SelectRowChon (args) {
            if (this.selectItem != null) {
                console.log(this.selectItem)
                let index = this.$refs.danhsach.dataItems.findIndex(item => item.NHOMSO_ID == this.selectItem.NHOMSO_ID && item.MOTA == this.selectItem.MOTA && item.KIEU_SO == this.selectItem.KIEU_SO  )
                console.log(index)
                if (index >= 0) {
                this.$refs.danhsach.setCurrentSelectedRow(index)
                }
            } else {
                this.$refs.danhsach.setCurrentSelectedRow(0)
            }
        },
        cboNhomSo_ButtonClick() {
            this.$refs.frmNhomSo.showModal()
        },
        reload() {
            this.$set(this.options, 'kieu_so', this.data.kieu_so.filter(item => item.NHOMSO_ID == this.NHOMSO_ID))
        },
        async clickButton(key) {
            if(!this.button[key])
                return false
            if (key == 'nhapmoi') {
                this.$refs.txtKieuSo.focus()
                this.SetButton(1);
            }
            if (key == 'ghilai') {
                this.GhiLai()
            }
            if (key == 'xoa') {
                this.tsbtnDelete_Click()
            }
            if(key == 'huybo') {
                this.SetButton(0);
                let selected = this.$refs.danhsach.getSelectedRecords()
                this.grvDanhSach_FocusedRowChanged(selected)

            }
            if(key == 'phanloai') {
                this.tsbtnUpdate_Click()
            }

        },
        tsbtnUpdate_Click()
        {
            this.$toast.error("Chức năng này hiện không khả dụng!")
        },
        async tsbtnDelete_Click() {
            this.$confirm(`Bạn thật sự muốn xóa dữ liệu không ?`, {
                confirmButtonText: "Có",
                cancelButtonText: "Không",
                type: "delete",
            }).then(async () => {
                try {
                    this.loading(true)
                    let data = this.$refs.danhsach.getSelectedRecords()
                    let rs = await this.axios.post("/web-quantri/kieusodep/sp_kieuso_delete", {
                        "kieuso_id": data[0].KIEUSO_ID
                    })
                    if(rs.data.data != 1) {
                        this.$toast.error(
                            rs.data.data
                        );
                    } else {
                        this.$toast.success(
                            "Xóa thành công!"
                        );
                        this.current = 0
                        this.LayDanhSachKieuSo();
                        this.reload()
                        this.SetButton(1);
                    }
                    this.loading(false);
                } catch (err) {
                    console.log(err)
                    this.$toast.error(
                        "Không thể xóa bản ghi này! "
                    );
                    this.loading(false);
                }
            });
        },
        numberWithCommas(n) {
            if(!n && n != 0 ) return
            var parts = n.toString().split(".");
            return (
                parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") +
                (parts[1] ? "." + parts[1] : "")
            );
        },
        async GhiLai() {
            
            if(!this.KiemTra_DuLieu())
                return false
            this.loading(true)
            try {
                let kieu = 0
                if (!this.button.nhapmoi) {
                    kieu = 1
                }
                let data = this.$refs.danhsach.getSelectedRecords()
                let postData = this.form
                if(data.length > 0)
                    postData.KIEUSO_ID = data[0].KIEUSO_ID
                else
                    postData.KIEUSO_ID = 0
                postData.NHOMSO_ID = this.NHOMSO_ID
                let rs = await this.axios.post("/web-quantri/kieusodep/sp_kieuso_update", {
                    "is_insert": kieu,
                    "js_data": JSON.stringify(postData)
                })
                if (rs.data.data == 1) {
                    this.reload()
                    if(this.button.nhapmoi) {
                        this.$toast.success("Cập nhật dữ liệu thành công")
                    } else {
                        this.$toast.success("Thêm mới dữ liệu thành công")
                    }
                    this.selectItem = postData
                    await this.LayDanhSachKieuSo();
                } else
                    this.$toast.error(rs.data.data)
                this.loading(false)
            } catch (err) {
                this.$toast.error(err.message)
                this.loading(false)
            }
        },
        checkKieuSo() {
            let status = true
            this.$refs.txtKieuSo.classList.remove("error")
            if (!this.form.KIEU_SO)
            {
                this.$toast.error("Tên kiểu số không được để trống !");
                this.$refs.txtKieuSo.focus()
                this.$refs.txtKieuSo.classList.add("error")
                status = false;
            }

            if (this.form.KIEU_SO && this.form.KIEU_SO.length > 500)
            {
                this.$toast.error("Tên kiểu số không được vượt quá 500 ký tự !");
                this.$refs.txtKieuSo.focus()
                this.$refs.txtKieuSo.classList.add("error")
                status = false;
            }

            return status
        },
        checkMota() {
            let status = true
            this.$refs.txtMoTa.classList.remove("error")
            if (!this.form.MOTA)
            {
                this.$toast.error("Mô tả về kiểu số không được để trống !");
                this.$refs.txtMoTa.focus()
                this.$refs.txtMoTa.classList.add("error")
                status = false;
            }

            if (this.form.MOTA && this.form.MOTA.length > 600)
            {
                this.$toast.error("Mô tả không được vượt quá 600 ký tự !");
                this.$refs.txtMoTa.focus()
                this.$refs.txtMoTa.classList.add("error")
                status = false;
            }

            return status
        },
        checkTienHM(){
            let status = true
            this.$refs.txtTienHM.classList.remove("error")
            if (this.form.TIEN_HM && this.form.TIEN_HM.length > 12)
            {
                this.$toast.error("Tiền hòa mạng không được vượt quá 12 chữ số !");
                this.$refs.txtTienHM.focus()
                this.$refs.txtTienHM.classList.add("error")
                status = false;
            }

            if(this.form.TIEN_HM == "") {
                this.form.TIEN_HM = 0
            }

            if (isNaN(this.form.TIEN_HM)) {
                this.form.TIEN_HM = 0;
            }


            return status

        },
        KiemTra_DuLieu()
        {
            
            if(!this.checkKieuSo()) return false
            if(!this.checkTienHM()) return false
            if(!this.checkMota()) return false
            
            return true
        },
        async frmKieuSo_Load() {
            try {
                this.loading(true)
                let kieuso = this.LayDanhSachKieuSo()
                let nhomso = this.LayDanhsachNhomso()
                await Promise.all([kieuso, nhomso]);
                if (this.options.nhom_so_dep.length > 0)
                    this.NHOMSO_ID = this.options.nhom_so_dep[0].NHOMSO_ID

                this.SetButton(1)
                this.loading(false)
            }
            catch (err) {
                this.$toast.error(err.message);
            }
        },
        async LayDanhsachNhomso() {
            try {
                let rs = await this.axios.get("/web-quantri/danhmuc-chung/CSS_NHOM_SO");
                this.options.nhom_so_dep = rs.data.data;
                return rs.data.statusText
            }
            catch (err) {
                this.$toast.error(err.message);
            }
        },
        async LayDanhSachKieuSo() {
            try {
                if(!this.button.nhapmoi) {
                    this.current = 0
                }
                let rs = await this.axios.get("/web-quantri/danhmuc-chung/CSS_KIEU_SO");
                this.$set(this.data, 'kieu_so', rs.data.data)
                this.SetButton(1);
                this.$refs.danhsach.setCurrentSelectedRow(this.current);
                this.$refs.danhsach.flagSelectedRowIndexes = [];
                this.$refs.danhsach.flagSelectedRowIndexes.push(this.current);
                return rs.data.statusText
            }
            catch (err) {
                this.$toast.error(err.message);
            }
        },
        grvDanhSach_FocusedRowChanged(data) {
            setTimeout(() => {}, 500);
            if (data && data[0]) {
                this.SetButton(3);
                this.form.KIEU_SO = data[0].KIEU_SO
                this.form.GHICHU = data[0].GHICHU
                this.form.MOTA = data[0].MOTA
                this.form.DINHNGHIA = data[0].DINHNGHIA
                this.form.TIEN_HM = this.numberWithCommas(data[0].TIEN_HM)
            } else {
                this.Clear()
            }
        },
        focusItem(index, data) {
            this.current = index
        },
        SetButton(kieu) {
            this.button.nhapmoi = false;
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
                this.button.nhapmoi = true;
                this.Clear();
            }
            if (kieu == 1)//Them moi
            {
                this.button.ghilai = true;
                this.button.huybo = true;
                this.Clear();
            }
            if (kieu == 2)//Huy
            {
                this.button.nhapmoi = true;
                this.button.xoa = true;
                this.Clear();
            }
            if (kieu == 3)//Edit
            {
                this.button.nhapmoi = true;
                this.button.ghilai = true;
                this.button.xoa = true;
                this.button.huybo = true;
            }
        },
        Clear() {
            this.form.KIEU_SO = null;
            this.form.MOTA = null;
            this.form.GHICHU = null;
            this.form.TIEN_HM = 0;
            this.form.DINHNGHIA = null;
        }
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

.custom-tooltip .tooltip-inner {
    background: #cfcfcf !important;
    text-align: left !important;
    min-width: 600px;
}
.tooltip.b-tooltip .arrow::before {
    border-top-color:#cfcfcf !important;
    border-bottom-color: #cfcfcf !important;
}
</style>
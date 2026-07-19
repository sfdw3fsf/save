<template>
    <div>
        <vue-bread-crumb :header="header" />
        <vue-nav>
            <ul class="list">
                <li
                    @click="clickButton('nhapmoi')"
                    :class="button.nhapmoi ? 'active' : 'non-active'"
                >
                    <a>
                        <span class="icon one-file-plus"></span>Nhập mới
                    </a>
                </li>
                <li @click="clickButton('ghilai')" :class="button.ghilai ? 'active' : 'non-active'">
                    <a>
                        <span class="icon one-save"></span>Ghi lại
                    </a>
                </li>

                <li @click="clickButton('huybo')" :class="button.huybo ? 'active' : 'non-active'">
                    <a>
                        <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy
                    </a>
                </li>
                <li @click="clickButton('xoa')" :class="button.xoa ? 'active' : 'non-active'">
                    <a>
                        <span class="icon one-trash"></span>Xóa
                    </a>
                </li>
            </ul>
        </vue-nav>
        <div class="page-content">
            <div class="row">
                <div class="col-md-6 col-lg-6">
                    <!-- THÔNG TIN THÙ LAO THU NỢ QUÁ HẠN -->
                    <div class="box-form">
                        <div class="legend-title">Thông tin thù lao thu nợ quá hạn</div>
                        <div class="info-row">
                            <div class="key w80">Nội dung (*)</div>
                            <div class="value">
                                <input 
                                    type="text" 
                                    class="form-control" 
                                    v-model.trim="txtNoiDung" 
                                    />
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w80">Tháng nợ từ (*)</div>
                                    <div class="value">
                                        <input
                                            type="number"
                                            class="form-control tright text fw6"
                                            v-model.trim="txtTu"
                                            min="1"
                                            max="12"
                                            maxlength="2"
                                            oninput="javascript: if (this.value.length > this.maxLength) this.value = this.value.slice(0, this.maxLength);"
                                        />
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w80">Đến (*)</div>
                                    <div class="value">
                                        <input
                                            type="number"
                                            class="form-control tright text fw6"
                                            v-model.trim="txtDen"
                                            min="1"
                                            max="12"
                                            maxlength="2"
                                            oninput="javascript: if (this.value.length > this.maxLength) this.value = this.value.slice(0, this.maxLength);"
                                        />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w80">Thù lao (*)</div>
                                    <div class="value">
                                        <input
                                            type="number"
                                            class="form-control tright text fw6"
                                            v-model.trim="txtThuLao"
                                            min="0"
                                        />
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w80">Kiểu nợ QH (*)</div>
                                    <div class="value">
                                        <SelectExt
                                            v-model="selectKieuNo"
                                            :allowFiltering="true"
                                            data-text-field="kieu_nqh"
                                            data-value-field="id"
                                            :dataSource="dsKieuNoQH"
                                        ></SelectExt>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- DANH SÁCH THÙ LAO THU NỢ QUÁ HẠN -->
                    <!-- ref="danh_sach_thu_lao" -->
                    <div class="box-form">
                        <vue-card :noborder="true" label="Danh sách thù lao thu nợ quá hạn">
                            <DataGrid ref="danh_sach_thu_lao" v-bind:columns="config.thulao" v-bind:dataSource="options.thulao" :showFilter="true"
                                    @selectedRowChanged="setCurrent" :showColumnCheckbox="false"
                                    dataKeyField="" :defaultColumnCheckboxChecked="false" :allowPaging="true"
                                    :enablePagingServer="false">
                            </DataGrid>
                            <!-- <vue-table
                                v-model="model.thulao"
                                :loading="loadingtable.thulao"
                                :config="config.thulao"
                                :options="options.thulao"
                                @input="setCurrent($event)"
                            ></vue-table> -->
                        </vue-card>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="box-move-select-table">
                        <div class="box-col box-form">
                            <div class="legend-title">Kỳ hóa đơn đã gán</div>
                            <div class="table-content" style="height: 200px">
                                <ejs-grid
                                    class="no-header"
                                    height="190"
                                    v-bind:dataSource="left.kyhoadon"
                                    ref="kyhoadon_left"
                                >
                                    <e-columns>
                                        <e-column type="checkbox" width="50"></e-column>
                                        <e-column
                                            field="chukyno"
                                            headerText="Chu kỳ nợ"
                                            textAlign="Left"
                                            width="auto"
                                        ></e-column>
                                    </e-columns>
                                </ejs-grid>
                            </div>
                        </div>
                        <div class="actions">
                            <button @click="toRight('kyhoadon')" class="btn">
                                <span class="fa fa-angle-right"></span>
                            </button>
                            <button @click="toAllRight('kyhoadon')" class="btn">
                                <span class="fa fa-angle-double-right"></span>
                            </button>
                            <button @click="toLeft('kyhoadon')" class="btn">
                                <span class="fa fa-angle-left"></span>
                            </button>
                            <button @click="toAllLeft('kyhoadon')" class="btn">
                                <span class="fa fa-angle-double-left"></span>
                            </button>
                        </div>
                        <div class="box-col box-form">
                            <div class="legend-title">Kỳ hóa đơn chưa gán</div>
                            <div class="table-content" style="height: 200px">
                                <ejs-grid
                                    v-bind:dataSource="right.kyhoadon"
                                    class="no-header"
                                    height="190"
                                    ref="kyhoadon_right"
                                >
                                    <e-columns>
                                        <e-column type="checkbox" width="50"></e-column>
                                        <e-column
                                            field="chukyno"
                                            headerText="Chu kỳ nợ"
                                            textAlign="Left"
                                            width="auto"
                                        ></e-column>
                                    </e-columns>
                                </ejs-grid>
                            </div>
                        </div>
                    </div>
                    <div class="box-move-select-table">
                        <div class="box-col box-form">
                            <div class="legend-title">Loại nhân viên đã gán</div>
                            <div class="table-content" style="height: 200px">
                                <ejs-grid
                                    class="no-header"
                                    v-bind:dataSource="left.loainhanvien"
                                    ref="loainhanvien_left"
                                    height="190"
                                >
                                    <e-columns>
                                        <e-column type="checkbox" width="50"></e-column>
                                        <e-column
                                            field="ten"
                                            headerText="loại nhân viên"
                                            textAlign="Left"
                                            width="auto"
                                        ></e-column>
                                    </e-columns>
                                </ejs-grid>
                            </div>
                        </div>
                        <div class="actions">
                            <button @click="toRight('loainhanvien')" class="btn">
                                <span class="fa fa-angle-right"></span>
                            </button>
                            <button @click="toAllRight('loainhanvien')" class="btn">
                                <span class="fa fa-angle-double-right"></span>
                            </button>
                            <button @click="toLeft('loainhanvien')" class="btn">
                                <span class="fa fa-angle-left"></span>
                            </button>
                            <button @click="toAllLeft('loainhanvien')" class="btn">
                                <span class="fa fa-angle-double-left"></span>
                            </button>
                        </div>
                        <div class="box-col box-form">
                            <div class="legend-title">Loại nhân viên chưa gán</div>
                            <div class="table-content" style="height: 200px">
                                <ejs-grid
                                    class="no-header"
                                    v-bind:dataSource="right.loainhanvien"
                                    ref="loainhanvien_right"
                                    height="190"
                                >
                                    <e-columns>
                                        <e-column type="checkbox" width="50"></e-column>
                                        <e-column
                                            field="ten"
                                            headerText="loại nhân viên"
                                            textAlign="Left"
                                            width="auto"
                                        ></e-column>
                                    </e-columns>
                                </ejs-grid>
                            </div>
                        </div>
                    </div>
                    <div class="box-move-select-table">
                        <div class="box-col box-form">
                            <div class="legend-title">Khu vực đã gán</div>
                            <div class="table-content" style="height: 200px">
                                <ejs-grid
                                    class="no-header"
                                    v-bind:dataSource="left.khuvuc"
                                    ref="khuvuc_left"
                                    height="190"
                                >
                                    <e-columns>
                                        <e-column type="checkbox" width="50"></e-column>
                                        <e-column
                                            field="ten_kv"
                                            headerText="Khu vực"
                                            textAlign="Left"
                                            width="auto"
                                        ></e-column>
                                    </e-columns>
                                </ejs-grid>
                            </div>
                        </div>
                        <div class="actions">
                            <button @click="toRight('khuvuc')" class="btn">
                                <span class="fa fa-angle-right"></span>
                            </button>
                            <button @click="toAllRight('khuvuc')" class="btn">
                                <span class="fa fa-angle-double-right"></span>
                            </button>
                            <button @click="toLeft('khuvuc')" class="btn">
                                <span class="fa fa-angle-left"></span>
                            </button>
                            <button @click="toAllLeft('khuvuc')" class="btn">
                                <span class="fa fa-angle-double-left"></span>
                            </button>
                        </div>
                        <div class="box-col box-form">
                            <div class="legend-title">Khu vực chưa gán</div>
                            <div class="table-content" style="height: 200px">
                                <ejs-grid
                                    class="no-header"
                                    v-bind:dataSource="right.khuvuc"
                                    ref="khuvuc_right"
                                    height="190"
                                >
                                    <e-columns>
                                        <e-column type="checkbox" width="50"></e-column>
                                        <e-column
                                            field="ten_kv"
                                            headerText="Khu vực"
                                            textAlign="Left"
                                            width="auto"
                                        ></e-column>
                                    </e-columns>
                                </ejs-grid>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import { required, minLength } from 'vuelidate/lib/validators'
import define from "./define/define";
import api from "./api/index.js";
export default {
    validations: {
        dataSelected:
        {
            noi_dung: {
                required
            },
            thangno_tu: {
                required
            },
            thangno_den: {
                required
            },
            thu_lao: {
                required
            },
            kieu_nqh: {
                required
            }
        }
    },
    data() {
        return {
            ...define,
            rowIndex: 0,
            pageIndex: 0
        };
    },
    computed: {
    },
    mounted() {
        console.log('mouted !!', this.current);
        // this.frmDonGia_TN_Load();
    },
    watch: {
        DONGIA_TN_ID(val) {
            this.HienThi_DonGia_Theo_ChuKyNo();
            this.HienThi_DONGIA_TN_Theo_LoaiNV();
            this.HienThi_DONGIA_TN_Theo_KhuVuc();
        },
    },
    methods: {
        onTLChange(e){
            try {
                console.log(e);
            } catch (error) {
                console.log(error);
            }
        },
        async setCurrent(data) {
            try {
                if(data){
                    // console.log();
                    this.current = data;
                    this.model.thulao = this.current;
                    this.setFormThongTin(this.current);
                    this.DONGIA_TN_ID = this.current.thulao_nqh_id;
                    this.SetButton(3);
                    // console.log('new thu lao', this.$refs.danh_sach_thu_lao);
                }
            } catch (error) {
                console.log(error);
            }
        },
        async tsbtnXoa_Click() {
            this.$confirm(
                `Bạn thật sự muốn xóa dữ liệu không ?`,
                {
                    confirmButtonText: "Có",
                    cancelButtonText: "Không",
                    type: "warning",
                }
            ).then(async () => {
                try {
                    await api.postXoaThuLaoNQH(this.axios,
                        {
                            id: this.DONGIA_TN_ID,
                        }
                    );
                    this.LoadGrid();
                } catch (error) {
                    this.$toast.error("Lỗi khi xóa!");
                }
            });
        },
        clickButton(key) {
            if (!this.button[key]) {
                return false;
            }
            if (key == "ghilai") {
                this.ghilai();
            }
            if (key == "nhapmoi") {
                this.tsbtnNhapMoi_Click();
            }
            if (key == "huybo") {
                this.tsbtnHuyBo_Click();
            }
            if (key == "xoa") {
                this.tsbtnXoa_Click();
            }
        },
        tsbtnNhapMoi_Click() {
            this.SetButton(1);
        },
        tsbtnHuyBo_Click() {

            this.SetButton(0);
        },
        async ghilai() {
            try {
                if (!this.KiemTra_DuLieu())
                    return false;
                this.loading(true);
                let dataKV = [];
                dataKV = this.left.khuvuc.map((item) => {
                    return {KHUVUC_ID: item.khuvuc_id}
                });
                let dataLNV = [];
                dataLNV = this.left.loainhanvien.map((item) => {
                    return {LOAINV_ID: item.loainv_id}
                });
                let dataKHD = [];
                dataKHD = this.left.kyhoadon.map((item) => {return {CKN: item.id}});
                
                let rs = await api.postGhiLaiThuLaoNQH(this.axios,
                    {
                        dongia_nqh: JSON.stringify([
                            {
                                THULAO_NQH_ID: this.current.thulao_nqh_id ? this.current.thulao_nqh_id : '',
                                NOIDUNG_NQH: this.txtNoiDung,
                                THULAO_NQH: this.txtThuLao,
                                KIEU_NQH: this.selectKieuNo,
                                THANGNO_TU: this.txtTu,
                                THANGNO_DEN: this.txtDen
                            },
                        ]),
                        themmoi: !this.button.nhapmoi ? 1 : 0,
                        dongia_nqh_ck: JSON.stringify(dataKHD),
                        dongia_nqh_nv: JSON.stringify(dataLNV),
                        dongia_nqh_kv: JSON.stringify(dataKV),
                    }
                );
                // load danh danh_sach_thu_lao
                // await this.LoadDataTable();
                if (!this.button.nhapmoi) {
                    // thêm mới 
                    // await this.LoadDataTable();
                    let res = await api.getDSThuLaoNoQH(this.axios);
                    
                    // alert('')
                    let newItem = {};
                    if(res.data.data){
                        newItem = {...res.data.data.pop()};
                    }
                    console.log('newItem: ', newItem);
                    this.options.thulao.unshift(newItem);
                    this.pageIndex = Math.ceil(this.options.thulao.length / 10);
                    this.rowIndex = this.options.thulao.length - 1;
                }

                if (rs.data.error_code == 'BSS-00000000') {
                    this.$toast.success('Thực hiện thành công');
                } else {
                    this.$toast.success('Thực hiện không thành công');
                }
                
                this.loading(false);
            } catch (error) {
                this.$toast.error(JSON.stringify(error));
                console.log(error);
                this.loading(false);
            }
        },
        toLeft(key) {
            let select_records = this.$refs[key + "_right"].getSelectedRecords();
            let select_indexs = this.$refs[key + "_right"].getSelectedRowIndexes();
            if (select_records.length > 0) {
                this.left[key] = select_records.concat(this.left[key]);
                for (var i = select_indexs.length - 1; i >= 0; i--)
                    this.right[key].splice(select_indexs[i], 1);
                this.right[key] = [...this.right[key]];
                this.left[key] = [...this.left[key]];
            }
        },
        toRight(key) {
            let select_records = this.$refs[key + "_left"].getSelectedRecords();
            let select_indexs = this.$refs[key + "_left"].getSelectedRowIndexes();
            if (select_records.length > 0) {
                this.right[key] = select_records.concat(this.right[key]);
                for (var i = select_indexs.length - 1; i >= 0; i--)
                    this.left[key].splice(select_indexs[i], 1);
                this.right[key] = [...this.right[key]];
                this.left[key] = [...this.left[key]];
            }
        },
        toAllLeft(key) {
            this.left[key] = this.right[key].concat(this.left[key]);
            this.right[key] = [];
            this.right[key] = [...this.right[key]];
            this.left[key] = [...this.left[key]];
        },
        toAllRight(key) {
            this.right[key] = this.left[key].concat(this.right[key]);
            this.left[key] = [];
            this.right[key] = [...this.right[key]];
            this.left[key] = [...this.left[key]];
        },
        selectedRowChanged(item) {
            // forcus current row
            this.model.thulao = item;
            // set thông tin thù lao nơ quá hạn
            this.setFormThongTin(item);
            // 
            this.DONGIA_TN_ID = item.id;
            this.SetButton(3);
        },
        setFormThongTin(val) {
            this.txtNoiDung = val.noidung_nqh;
            this.txtTu = val.thangno_tu;
            this.txtDen = val.thangno_den;
            this.txtThuLao = val.thulao_nqh;
            this.selectKieuNo = val.kieu_nqh_id;
        },
        frmDonGia_TN_Load() {
            this.SetButton(-1);
            this.LoadGrid();
        },
        SetButton(kieu) {
            this.button.nhapmoi = false;
            this.button.ghilai = false;
            this.button.xoa = false;
            this.button.huybo = false;

            if (kieu == -1) {
                //Bat dau
                this.button.ghilai = true;
                this.button.huybo = true;
            }

            if (kieu == 0) {
                //Bat dau
                //txtMaNV.Focus();
                this.button.nhapmoi = true;
                this.Clear();
            }

            if (kieu == 1) {
                //Them moi
                this.button.ghilai = true;
                this.button.huybo = true;
                this.Clear();
            }

            if (kieu == 2) {
                //Huy
                this.button.nhapmoi = true;
                this.button.xoa = true;
                this.Clear();
            }

            if (kieu == 3) {
                //Edit
                //txtTenNV.Focus();
                this.button.nhapmoi = true;
                this.button.ghilai = true;
                this.button.xoa = true;
                this.button.huybo = true;
            }
        },
        async HienThi_DONGIA_TN_Theo_LoaiNV() {
            try {
                let result = await api.getDSDonGiaNhanVien(this.axios,
                    {
                        id: this.DONGIA_TN_ID ? this.DONGIA_TN_ID : null
                    }
                ).then(res => {
                    return res.data.data;
                })

                this.left.loainhanvien = result.dagan;
                this.right.loainhanvien = result.chuagan;
            } catch (e) {
                this.$toast.error("Đã có lỗi xảy ra!");
            }
        },
        async HienThi_DONGIA_TN_Theo_KhuVuc() {
            try {
                let result = await api.getDSDonGiaKhuVuc(this.axios,
                    {
                        id: this.DONGIA_TN_ID ? this.DONGIA_TN_ID : null
                    }
                ).then(res => {
                    return res.data.data;
                });

                this.left.khuvuc = result.dagan;
                this.right.khuvuc = result.chuagan;
            } catch (e) {
                this.$toast.error("Đã có lỗi xảy ra!");
            }
        },
        async HienThi_DonGia_Theo_ChuKyNo() {
            try {
                let result = await api.getDSDonGiaChuKySo(this.axios,
                    {
                        id: this.DONGIA_TN_ID ? this.DONGIA_TN_ID : null
                    }
                ).then(res => {
                    return res.data.data;
                })

                this.left.kyhoadon = result.dagan;
                this.right.kyhoadon = result.chuagan;
            } catch (e) {
                this.$toast.error("Đã có lỗi xảy ra!");
            }
        },
        Clear() {
            this.txtNoiDung = null;
            this.txtTu = null;
            this.txtDen = null;
            this.txtThuLao = null;
            this.selectKieuNo = null;

            this.DONGIA_TN_ID = null;
        },

        async LoadDataTable() {
            try {
                let res = await api.getDSThuLaoNoQH(this.axios);
                this.options.thulao = res.data.data;
                // console.log('page', Math.ceil(this.options.thulao.length / 10));
            } catch (error) {
                this.$toast.error("Đã có lỗi xảy ra!");
            }
        },

        async LoadGrid() {
            try {
                await this.LoadDataTable();
                this.setCurrent(this.options.thulao[0]);
            } catch (e) {
                console.log(e);
                this.$toast.error("Đã có lỗi xảy ra");
            }
        },
        async getDSKieuNoQH() {
            this.loading(true);
            try {
                let res = await api.getDsKieuNoQH(this.axios);
                this.dsKieuNoQH = res.data.data;
                this.loading(false);
            } catch (err) {
                this.loading(false);
            }
        },
        KiemTra_DuLieu() {
            if (!this.txtNoiDung) {
                this.$toast.error(
                    'Bạn chưa nhập "Nội dung thù lao thu nợ quá hạn"!'
                );
                return false;
            }
            if (!this.txtTu) {
                this.$toast.error('Bạn chưa nhập "Tháng nợ từ"!');
                return false;
            }
            if (!this.txtDen) {
                this.$toast.error('Bạn chưa nhập "Tháng nợ đến"!');
                return false;
            }
            if (Number(this.txtThuLao) <= 0) {
                this.$toast.error('"Mức thù lao nợ quá hạn" phải lớn hơn 0!');
                return false;
            }
            if (!this.selectKieuNo) {
                this.$toast.error('Bạn chưa chọn "Kiểu nợ quá hạn"!');
                return false;
            }

            if (this.left.khuvuc.length <= 0) {
                this.$toast.error("Bạn chưa gán khu vực!");
                return false;
            }

            if (this.left.loainhanvien.length <= 0) {
                this.$toast.error("Bạn chưa gán loại nhân viên!");
                return false;
            }

            if (this.left.kyhoadon.length <= 0) {
                this.$toast.error("Bạn chưa gán kỳ hóa đơn!");
                return false;
            }

            return true
        }
    },
    created() {
        console.log('created !!');
        this.frmDonGia_TN_Load();
        this.getDSKieuNoQH();
    }
};
</script>
<style>
.list a {
    cursor: pointer;
}
.no-header .e-columnheader {
    display: none;
}
li.non-active a {
    color: #d3d3d3 !important;
}
</style>

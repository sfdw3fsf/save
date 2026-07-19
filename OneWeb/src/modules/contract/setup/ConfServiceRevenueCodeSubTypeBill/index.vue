<template>
    <div class="main-wrapper">
        <div class=" breadcrumb-top">
            <div class="main-title">Cấu hình mã doanh thu dịch vụ, loại hình thuê bao, khoản mục tính cước</div>
        </div>
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a @click="thucHien_Click()">
                        <span class="icon one-check"></span>Ghi lại
                    </a>
                </li>
            </ul>
        </div>
        <div class="page-content">
            <div class="row">
                <div class="col-6">
                    <div class="box-form">
                        <div class="legend-title">Thông tin doanh thu</div>
                        <div class="row">
                            <div class="col-sm-6">
                                <div class="info-row">
                                    <div class="key">Mã DT</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model="formDoanhThu.maDt">
                                    </div>
                                </div>
                            </div>
                            <div class="col-6">
                                <div class="info-row">
                                    <div class="key">Mức</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model="formDoanhThu.muc">
                                    </div>
                                </div>
                            </div>
                            <div class=" col-12">
                                <div class="info-row">
                                    <div class="key">Tên chỉ tiêu</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model="formDoanhThu.tenchitieu">
                                    </div>
                                </div>
                            </div>
                            <div class=" col-12">
                                <div class="info-row">
                                    <div class="key">Ghi chú</div>
                                    <div class="value">
                                        <textarea rows="10" class="form-control"
                                            v-model="formDoanhThu.ghiChu"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-6">
                    <div class="box-form">
                        <div class="legend-title">Gán loại hình thuê bao</div>

                        <div class="box-move-select-table">
                            <div class="box-col">
                                <KDataGrid :columns="columnsThueBaoGan" :dataSource="dataTBGan"
                                    :showColumnCheckbox="true" :enable-paging-server="false" :allowPaging="true"
                                    :showFilter="true" ref="gridDaGan" @rowSelected="chonKMTT"
                                    :enabledSelectFirstRow="false" panelDataHeight="234px" />
                            </div>
                            <div class="actions">
                                <button class="btn btn-main" @click="ganQuyenTB">
                                    <span class="-ap icon-plus3"></span>
                                </button>
                                <button class="btn btn-main" @click="goQuyenTB">
                                    <span class="-ap icon-minus3"></span>
                                </button>
                            </div>
                            <div class="box-col">
                                <div class="info-row">
                                    <div class="key ">Dịch vụ VT
                                    </div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <Select2 :options="lsDichVu" v-model="dichVu" @select="lhTBChuaGan()" />
                                        </div>
                                    </div>
                                </div>
                                <KDataGrid :columns="columnsThueBaoChuaGan" :dataSource="dataTBChuaGan"
                                    :showColumnCheckbox="true" :enable-paging-server="false" :allowPaging="true"
                                    :showFilter="true" ref="gridChuaGan" :enabledSelectFirstRow="false"
                                    panelDataHeight="234px" />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-6">
                    <div class="box-form">
                        <div class="legend-title">Danh sách mã doanh thu</div>
                        <div class="table-content">
                            <vue-card label="">
                                <KDataGrid :columns="columnsMaDoanhThu" :dataSource="dataSourcesMaDoanhThu"
                                    :showColumnCheckbox="false" :enable-paging-server="false" :allowResizing="false"
                                    :allowPaging="true" :showFilter="true" ref="gridDanhSachMDT"
                                    :enabledSelectFirstRow="false" @rowSelected="chonMDT" panelDataHeight="234px" />
                            </vue-card>
                        </div>
                    </div>
                </div>
                <div class="col-6">
                    <div class="box-form">
                        <div class="legend-title">Gán khoản mục thanh toán</div>
                        <div class="box-move-select-table">
                            <div class="box-col">
                                <KDataGrid :columns="columnsGan" :dataSource="dataSourcesGan" :showColumnCheckbox="true"
                                    :enable-paging-server="false" :allowPaging="true" :showFilter="true"
                                    ref="gridKMTTDaGan" :enabledSelectFirstRow="false" panelDataHeight="234px" />
                            </div>
                            <div class="actions">
                                <button class="btn btn-main" @click="ganQuyenKMTT">
                                    <span class="-ap icon-plus3"></span>
                                </button>
                                <button class="btn btn-main" @click="goQuyenKMTT">
                                    <span class="-ap icon-minus3"></span>
                                </button>
                            </div>
                            <div class="box-col">
                                <div class="info-row">
                                    <div class="key ">Loại hình TB
                                    </div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <Select2 :options="lsLoaiTB" v-model="loaiTB" />
                                        </div>
                                    </div>
                                </div>
                                <KDataGrid :columns="columnsChuaGan" :dataSource="dataSourcesChuaGan"
                                    :showColumnCheckbox="true" :enable-paging-server="false" :allowPaging="true"
                                    :showFilter="true" ref="gridKMTTChuaGan" :enabledSelectFirstRow="false"
                                    panelDataHeight="234px" />
                            </div>
                        </div>
                    </div>
                </div>
            </div>



        </div>
    </div>
</template>

<script>
import breadcrumb from "@/components/breadcrumb";
import gridview from "@/components/Shared/gridview";
import KDataGrid from '@/components/kylq_components/KDataGrid'
import api from "./api/index.js";
export default {
    components: { breadcrumb, gridview, KDataGrid },
    name: "",

    data() {
        return {
            header: {
                title: "Mã doanh thu - loại hình - khoản mục thanh toán",
                // list: [
                //     { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
                //     {
                //         name: "Lắp đặt mới",
                //         link: { name: "Ui.buttons" },
                //     },
                // ],
            },
            thongTinDoanhThu: {},
            formDoanhThu: {
                id: '',
                maDt: "",
                muc: "",
                tenchitieu: "",
                ghiChu: "",
            },
            commit: {
                loaitb_id: '',
                madt_id: '',
                dskm_id: '',

            },
            columnsGan: [
                {
                    fieldName: 'ten_kmtt',
                    headerText: 'Khoản mục TT đã gán',
                    allowFiltering: true
                }
            ],
            dataSourcesGan: [],
            columnsChuaGan: [
                {
                    fieldName: 'ten_kmtt',
                    headerText: 'Khoản mục TT chưa gán',
                    allowFiltering: true
                }
            ],
            dataSourcesChuaGan: [],
            columnsMaDoanhThu: [
                {
                    fieldName: 'ten_chitieu',
                    headerText: 'Tên chỉ tiêu',
                    allowFiltering: true
                },
                {
                    fieldName: 'ma_dt',
                    headerText: 'Mã doanh thu',
                    allowFiltering: true
                },
                {
                    fieldName: 'ngay_cn',
                    headerText: 'Ngày CN',
                    allowFiltering: true
                },
                {
                    fieldName: 'muc',
                    headerText: 'Mức',
                    allowFiltering: true
                }
            ],
            dataSourcesMaDoanhThu: [

            ],
            columnsThueBaoChuaGan: [
                {
                    fieldName: 'loaihinh_tb',
                    headerText: 'Loại hình chưa gán',
                    allowFiltering: true
                },
                {
                    fieldName: 'ten_dvvt',
                    headerText: 'Dịch vụ',
                    allowFiltering: true
                },
            ],
            dataTBChuaGan: [
            ],
            columnsThueBaoGan: [
                {
                    fieldName: 'loaihinh_tb',
                    headerText: 'Loại hình đã gán',
                    allowFiltering: true
                },
                {
                    fieldName: 'ten_dvvt',
                    headerText: 'Dịch vụ',
                    allowFiltering: true
                }
            ],
            dataTBGan: [
            ],
            dataFilter: [],
            filterTB: "",
            lsDichVu: [],
            lsLoaiTB: [],
            dichVu: "1",
            loaiTB: "1",
            madt_id: ""
        };
    },
    created() {
        this.fetchApi();
        api.layDanhSachMaDoanhThu(this.axios, {}).then((res) => {
            if (res.data.data) {
                this.dataSourcesMaDoanhThu = res.data.data;
            }
        });
    },
    methods: {
        fetchApi: async function () {
            this.lsDichVu = this.GetData(await api.listDVVT(this.axios, {})).map(x => ({
                id: x.dichvuvt_id,
                text: x.ten_dvvt
            }))
        },
        GetData: function (response) {
            if (response.data.error === 200 || response.data.error === '200') {
                return response.data.data
            } else {
                console.log(response.data.error_code)
            }
            return []
        },
        lhTBChuaGan() {
            this.dataTBChuaGan = [];
            var dataChuaGan = {
                "vmadt_id": this.madt_id,
                "vdichvuvt_id": this.dichVu
            }
            api.lhTBChuaGan(this.axios, dataChuaGan).then((res) => {
                if (res.data.data.length > 0) {
                    this.dataTBChuaGan = res.data.data;
                }
            });
        },

        ganQuyenTB() {
            let dataCheckedsChuaGan = this.$refs.gridChuaGan.getSelectedRecords()
            if (this.dataTBChuaGan.length == 0) {
                return
            }
            if (dataCheckedsChuaGan.length == 0) {
                return
            }

            dataCheckedsChuaGan.map((item) => {
                this.dataTBGan.push(item)
            })
            let uniqueArr = this.dataTBGan.filter((item, index) => this.dataTBGan.indexOf(item) === index);
            this.dataTBGan = uniqueArr
            let newArr = this.dataTBChuaGan.filter(item => !dataCheckedsChuaGan.includes(item));
            this.dataTBChuaGan = newArr
        },
        goQuyenTB() {
            if (this.dataTBGan.length == 0) {
                return
            }
            let dataCheckedsGan = this.$refs.gridDaGan.getSelectedRecords()
            if (dataCheckedsGan.length == 0) {
                return
            }
            dataCheckedsGan.map((item) => {
                this.dataTBChuaGan.push(item)
            })
            let uniqueArr = this.dataTBChuaGan.filter((item, index) => this.dataTBChuaGan.indexOf(item) === index);
            this.dataTBChuaGan = uniqueArr
            let newArr = this.dataTBGan.filter(item => !dataCheckedsGan.includes(item));
            this.dataTBGan = newArr
        },
        ganQuyenKMTT() {
            let dataCheckedsChuaGan = this.$refs.gridKMTTChuaGan.getSelectedRecords()
            if (this.dataSourcesChuaGan.length == 0) {
                return
            }
            if (dataCheckedsChuaGan.length == 0) {
                return
            }

            dataCheckedsChuaGan.map((item) => {
                this.dataSourcesGan.push(item)
            })
            let uniqueArr = this.dataSourcesGan.filter((item, index) => this.dataSourcesGan.indexOf(item) === index);
            this.dataSourcesGan = uniqueArr
            let newArr = this.dataSourcesChuaGan.filter(item => !dataCheckedsChuaGan.includes(item));
            this.dataSourcesChuaGan = newArr
        },
        goQuyenKMTT() {
            if (this.dataSourcesGan.length == 0) {
                return
            }
            let dataCheckedsGan = this.$refs.gridKMTTDaGan.getSelectedRecords()
            if (dataCheckedsGan.length == 0) {
                return
            }
            dataCheckedsGan.map((item) => {
                this.dataSourcesChuaGan.push(item)
            })
            let uniqueArr = this.dataSourcesChuaGan.filter((item, index) => this.dataSourcesChuaGan.indexOf(item) === index);
            this.dataSourcesChuaGan = uniqueArr
            let newArr = this.dataSourcesGan.filter(item => !dataCheckedsGan.includes(item));
            this.dataSourcesGan = newArr
        },
        chonMDT: async function (item) {
            this.madt_id = item.data.madt_id
            this.formDoanhThu.id = item.data.madt_id
            this.formDoanhThu.maDt = item.data.ma_dt
            this.formDoanhThu.ghiChu = item.data.ghichu
            this.formDoanhThu.muc = item.data.muc
            this.formDoanhThu.tenchitieu = item.data.ten_chitieu
            this.commit.madt_id = item.data.madt_id
            let dataDaGan = {
                vmadt_id: item.data.madt_id
            }
            this.lsLoaiTB = this.GetData(await api.listLoaiTB(this.axios, dataDaGan)).map(x => ({
                id: x.loaitb_id,
                text: x.loaihinh_tb
            }))
            // api.listLoaiTB(this.axios, dataDaGan).then((res) => {
            //     for (let i = 0; i < res.data.data.length; i++) {
            //         this.lsLoaiTB[i] = {}
            //         this.lsLoaiTB[i].id = res.data.data[i].loaitb_id
            //         this.lsLoaiTB[i].text = res.data.data[i].loaihinh_tb
            //     }
            // });
            api.layDanhSachTBDaGan(this.axios, dataDaGan).then((res) => {
                if (res.data.data) {
                    this.dataTBGan = res.data.data;
                }
            });
            this.lhTBChuaGan();
        },
        chonKMTT(item) {
            let dataDaGan = {
                vmadt_id: item.data.madt_id,
                vloaitb_id: item.data.loaitb_id
            }
            this.commit.loaitb_id = item.data.loaitb_id
            api.layDanhSachKMTTDaGan(this.axios, dataDaGan).then((res) => {
                if (res.data.data) {
                    this.dataSourcesGan = res.data.data;
                }
            });
            api.layDanhSachKMTTChuaGan(this.axios, dataDaGan).then((res) => {
                if (res.data.data) {
                    this.dataSourcesChuaGan = res.data.data;
                }
            });
        },
        async thucHien_Click() {
            if (this.formDoanhThu.maDt == "") {
                this.$toast.warning("Bạn chưa chọn mã doanh thu!");
                return false;
            }

            if (this.formDoanhThu.muc == null) {
                this.$toast.warning("Bạn chưa chọn mã doanh thu!");
                return false;
            }
            if (this.formDoanhThu.tenchitieu == "") {
                this.$toast.warning("Bạn chưa chọn mã doanh thu!");
                return false;
            }
            if (this.formDoanhThu.id == null) {
                this.$toast.warning("Vui lòng chọn lại doanh thu ở danh sách mã doanh thu");
                return false;
            }
            let dskm = [];
            this.dataSourcesGan.map((item) => {
                dskm.push(item.khoanmuctt_id)
            })
            let loaitb_id = []
            this.dataTBGan.map((item) => {
                loaitb_id.push(item.loaitb_id);
            })


            if (
                confirm(
                    "Bạn có muốn khởi doanh thu " + this.formDoanhThu.tenchitieu + " không ?"
                )
            ) {
                var sql = {
                    "LOAITB_ID": loaitb_id.join(','),
                    "MADT_ID": this.commit.madt_id,
                    "DS_KHOANMUC_ID": dskm.join(','),
                    "VDS": {
                        "MADT_ID": this.commit.madt_id,
                        "LOAITB_ID": this.loaiTB
                    }
                }
                let data = {
                    ds_para: JSON.stringify(sql),
                };
                let res = await api.khoitao(this.axios, data);
                if (res.data && res.data.error_code == "BSS-00000000") {
                    this.$toast.success(
                        "Khởi tạo doanh thu thành công"
                    );
                } else {
                    this.$toast.error("Có lỗi xẩy ra!");
                }
            }
        },
    },
};
</script>

<style></style>

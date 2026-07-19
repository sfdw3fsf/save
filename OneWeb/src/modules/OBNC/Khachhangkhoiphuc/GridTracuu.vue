<template>
    <div class="box-form">
        <div class="legend-title">
            <div class="pull-left">Thông tin tra cứu</div>
            <!-- <div class="pull-right red bold">Tổng số: {{grdLichsuOB.dataSources.length}} </div> -->
            <!-- Hiển thị tổng số mẫu tin và số trang -->
            <!-- <div class="pull-right red bold">
                Tổng số mẫu tin: {{ totalRecords }} - Số trang: {{ currentPage }}
            </div> -->
            <div class="pull-right red bold"><button @click="exportToExcel" class="icon one-excel"
                    style="font-size: 14px;">Xuất Excel</button></div>
            <div class="clearfix"></div>
        </div>

        <TracuuGrid ref="refgrdLichsuOB" :columns="grdLichsuOB.columns" :dataSource="grdLichsuOB.dataSources"
            :allowPaging='true' :enable-paging-server="false" :showFilter="true" :allowGrouping="true" :showColumnCheckbox="true"
            :scrollable="{ virtual: true }" :height="400"
            :enabledSelectFirstRow="false" panelDataHeight="420px" @rowSelected="onSelectRow_TTTC" />
    </div>
</template>   

<script>
import api from "./api_ObNhancong.js"
import TracuuGrid from './DataGrid'
import * as XLSX from 'xlsx';

export default {
    name: 'THONGTINTRACUU',
    components: {
        TracuuGrid
    },
    data() {
        return {
            // currentPage: 1, // Trang hiện tại
            // totalRecords: 0, // Tổng số mẫu tin
            // pageSize: 10, // Số mẫu tin trên mỗi trang
            currentComponent: null,
            currentTab: 2,
            MaKH: '',
            tungayTracuu: null,
            denngayTracuu: null,
            showPopup: false,
            btnStatus: {
                btnLayttEnabled: true,
                btnCapnhatEnabled: true,
                btnOutboundEnabled: true,
            },
            grdLichsuOB: {
                columns: [
                    //{ field: "stt", headerText: "STT", width: 50 },
                    { fieldName: 'ma_kh', headerText: '', isGroupedColumn: true, captionTemplate: this.captionTemplateTracuu(this) },
                    { fieldName: 'ma_tt', headerText: '', isGroupedColumn: true, captionTemplate: this.captionTemplateTracuu(this) },
                    { fieldName: "ten_kh", headerText: "Tên KH", textAlign: 'left', allowFiltering: true, allowSorting: false, width: '130px' },
                    { fieldName: "sdt_kh", headerText: "SĐT KH", textAlign: 'left', allowFiltering: true, allowSorting: false, width: '130px' },
                    { fieldName: "diachi_kh", headerText: "Địa chỉ KH", textAlign: 'left', allowFiltering: true, allowSorting: false, width: '130px' },
                    { fieldName: "ten_tb", headerText: "Tên TB", textAlign: 'left', allowFiltering: true, allowSorting: false, width: '130px' },
                    { fieldName: "loaihinh_tb", headerText: "Loại TB", textAlign: 'left', allowFiltering: true, allowSorting: false, width: '130px' },
                    { fieldName: "diachi_ld", headerText: "Địa chỉ LĐ", textAlign: 'left', allowFiltering: true, allowSorting: false, width: '130px' },
                    { fieldName: "ten_tt", headerText: "Tên TT", textAlign: 'left', allowFiltering: true, allowSorting: false, width: '130px' },
                    { fieldName: "sdt_tt", headerText: "SĐT TT", textAlign: 'left', allowFiltering: true, allowSorting: false, width: '130px' },
                    { fieldName: "diachi_tt", headerText: "Địa chỉ TT", textAlign: 'left', allowFiltering: true, allowSorting: false, width: '130px' },
                    { fieldName: "ma_nv", headerText: "Mã NVCS", textAlign: 'left', allowFiltering: true, allowSorting: false, width: '130px' },
                    { fieldName: "ten_nv", headerText: "Tên NVCS", textAlign: 'left', allowFiltering: true, allowSorting: false, width: '130px' },
                    { fieldName: "phong_nv", headerText: "Phòng NVCS", textAlign: 'left', allowFiltering: true, allowSorting: false, width: '130px' },
                    { fieldName: "sdt_ob", headerText: "SĐT OB", textAlign: 'left', allowFiltering: true, allowSorting: false, width: '130px' },
                    { fieldName: "tap_ob", headerText: "Tập OB", textAlign: 'left', allowFiltering: true, allowSorting: false, width: '130px' },
                    { fieldName: "kq_ob", headerText: "Kết quả OB", textAlign: 'left', allowFiltering: true, allowSorting: false, width: '130px' },
                    { fieldName: "ghi_chu", headerText: "Ghi chú", textAlign: 'left', allowFiltering: true, allowSorting: false, width: '130px' },
                    { fieldName: "ngay_goi", headerText: "Ngày gọi OB", textAlign: 'left', allowFiltering: true, allowSorting: false, width: '130px' },
                    { fieldName: "ngay_hen", headerText: "Ngày hẹn liên lạc", textAlign: 'left', allowFiltering: true, allowSorting: false, width: '130px' },
                    { fieldName: "ma_gd", headerText: "Mã GD", textAlign: 'left', allowFiltering: true, allowSorting: false, width: '130px' },
                    { fieldName: "loai_hd", headerText: "Loại HĐ", textAlign: 'left', allowFiltering: true, allowSorting: false, width: '130px' },
                ],
                dataSources: []
            },
        };
    },
    methods: {
        async getListTTTC(vdoituong_id, vthongtin, vtungay, vdenngay) {
            try {
                this.loading(true)
                // Gọi API và đợi kết quả trả về
                let dsTracuu = {
                    dk_tracuu: vdoituong_id,
                    thongtin_tracuu: vthongtin,
                    tu_ngay: vtungay,
                    den_ngay: vdenngay,
                };
                let result = null;
                result = await api.postThongtinTracuu(this.axios, dsTracuu);
                this.grdLichsuOB.dataSources = result.data.data;

                // Cập nhật số mẫu tin và số trang
                // this.totalRecords = result.data.data.length;
                // this.currentPage = Math.ceil(this.totalRecords / this.pageSize);
                //console.log(this.$refs.grdLichsuOB);
                // Gán STT cho mỗi item trong dataSources
                // this.grdLichsuOB.dataSources = result.data.data.map((item, index) => ({
                //     ...item,
                //     stt: index + 1 // Thêm trường STT
                // }));
                // this.$nextTick(() => {
                //     console.log(this.grdLichsuOB.dataSources); 
                // });
            } catch (e) {
                console.log(e)
            } finally {
                this.loading(false)
            }
        },

        captionTemplateTracuu: function (parent) {
            return function () {
                return {
                    template: {
                        template: `<div>{{ captionValue }}</div>`,
                        data: function () {
                            return { data: {} };
                        },
                        computed: {
                            captionValue: function (value) {
                                const key = value.data.key
                                const field = value.data.field
                                if (field == 'ma_kh') {
                                    const { items, count } = value.data
                                    const tbTracuu = items[0]?.items[0]
                                    return `Mã KH: ${tbTracuu.ma_kh} - ${tbTracuu.ten_kh} - ${tbTracuu.sdt_kh} - ${tbTracuu.diachi_kh} - ${count} Bản ghi`
                                }
                                if (field == 'ma_tt') {
                                    const { items, count } = value.data
                                    const tbTracuu = items[0]
                                    return `Mã TT: ${tbTracuu.ma_tt} - ${tbTracuu.ten_tt} - ${tbTracuu.sdt_tt} - ${tbTracuu.diachi_tt} - ${count} Bản ghi`
                                }
                            },
                            computed: {
                                // Tính tổng số trang dựa trên tổng số bản ghi và số bản ghi trên mỗi trang
                                totalPages() {
                                    return Math.ceil(this.totalRecords / this.pageSize);
                                }
                            },
                        }
                    }
                };
            };
        },


        onSelectRow_TTTC(data) {
            if (!data?.data) return
            console.log("selecting item 1 ", data)
        },

        exportToExcel() {
            // Lấy dữ liệu từ dataSource
            const data = this.grdLichsuOB.dataSources;

            if (data.length === 0) {
                alert("Không có dữ liệu để xuất");
                return;
            }
            // Tạo một worksheet từ dữ liệu
            const worksheet = XLSX.utils.json_to_sheet(data);
            // Tạo workbook và thêm worksheet vào
            const workbook = XLSX.utils.book_new();
            XLSX.utils.book_append_sheet(workbook, worksheet, "LichSuOB");
            // Xuất workbook ra file Excel
            XLSX.writeFile(workbook, "lich_su_ob.xlsx");
        },
    },
    mounted() {
        //this.getListTTTC();
    },
    
}
</script> 

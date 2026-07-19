<template src="./index.html">
</template>

<script>
import { Filter, Page, Sort } from "@syncfusion/ej2-vue-grids";
import ModalGhiChuOdf from '../../../GhiChuCongODF/Modal'
import ModalDauNoiOdf from '../../../daunoiodf/Modal'

let filterBarTemplate = {
    create: function (args) {

        let div = document.createElement('div')
        div.classList.add('input-icon-right')
        
        let input = document.createElement('input')
        input.classList.add('form-control')
        input.id = args.column.foreignKeyField

        let span = document.createElement('span')
        span.classList.add('icon', 'nc-icon-outline', 'ui-1_zoom')

        div.append(input)
        div.append(span)

        return div
    },
    write: function (args) {
        
        args.element.querySelector('input').addEventListener('input', (args) => {
            let value = args.currentTarget.value

            if (!value) {
                this.parent.removeFilteredColsByField(args.currentTarget.id);
            } else {
                this.parent.filterByColumn(args.currentTarget.id, 'contains', value)
            }
        })
    }
}

export default {
    components: {
        ModalGhiChuOdf,
        ModalDauNoiOdf
    },
    props: {
        isActive: Boolean,
        tramTb_id: Number
    },
    provide: {
        grid: [Filter, Page, Sort]
    },
    data() {
        return {
            // tramTb_id: 723,
            turack_id: -1,
            odf_id: null,
            ten_odf: null,
            turackDD_id: -1,
            soPort: null,
            portBD: null,
            loaiCong_id: null,
            ghiChu: null,
            duan_id: null,
            taisan_id: null,
            currentIndex: 0,
            dsTuRack: [],
            dsTuRackTemp: [],
            dsOdf: [],
            dsMaDuAn: [],
            dsMaTaiSan: [],
            dsLoaiCong: [
                {
                    LOAICONG_ID: 'FC',
                    LOAICONG: 'Tròn'
                },
                {
                    LOAICONG_ID: 'SC',
                    LOAICONG: 'Vuông'
                }
            ],
            thongTinChung: [
                {
                    field: 'ODF_ID',
                    headerText: 'ODF ID',
                    width: 103,
                    textAlign: 'Right',
                    minWidth: 10,
                    headerTextAlign: 'Center',
                    filter: {operator: 'contains'},
                    filterBarTemplate: filterBarTemplate
                },
                {
                    field: 'GHICHU',
                    headerText: 'Ghi chú',
                    width: 130,
                    textAlign: 'Center',
                    minWidth: 10,
                    filter: {operator: 'contains'},
                    filterBarTemplate: filterBarTemplate
                },
                {
                    field: 'TEN_ODF',
                    headerText: 'Tên ODF',
                    width: 130,
                    textAlign: 'Center',
                    minWidth: 10,
                    filter: {operator: 'contains'},
                    filterBarTemplate: filterBarTemplate
                },
                {
                    field: 'SO_PORT',
                    headerText: 'Số cổng',
                    width: 130,
                    textAlign: 'Center',
                    minWidth: 10,
                    filter: {operator: 'contains'},
                    filterBarTemplate: filterBarTemplate
                },
                {
                    field: 'LOAI_CONG',
                    headerText: 'Loại',
                    width: 130,
                    textAlign: 'Center',
                    minWidth: 10,
                    filter: {operator: 'contains'},
                    filterBarTemplate: filterBarTemplate
                },
                {
                    field: 'VITRI',
                    headerText: 'Vị trí',
                    width: 130,
                    textAlign: 'Center',
                    minWidth: 10,
                    filter: {operator: 'contains'},
                    filterBarTemplate: filterBarTemplate
                },
            ],
            ghiChuCong: [
                {
                    field: 'HUONGDEN',
                    headerText: 'Hướng đến',
                    width: 130,
                    textAlign: 'Center',
                    minWidth: 10,
                    filter: {operator: 'contains'},
                    filterBarTemplate: filterBarTemplate
                },
                {
                    field: 'CULY',
                    headerText: 'Cự ly (m)',
                    width: 130,
                    textAlign: 'Center',
                    minWidth: 10,
                    filter: {operator: 'contains'},
                    filterBarTemplate: filterBarTemplate
                }
            ],
            pageSettings: { pageSizes: ['10','20','50','100','All'], pageCount: 3, pageSize: 10 },
            selectOptions: { type: 'Single', mode: 'Row' },
            isDisableBtnNhapMoi: false,
            isDisableBtnGhiLai: true,
            isDisableBtnHuy: true,
            isDisableBtnXoa: true,
            isDisableBtnDauNoi: true,
            isDisableBtnGhiChu: true,
            isShowDinhDanh: true,
            isShowDacTinh: true,
            isShowKhac: true,
            isShowDuAn: true,
            isShowModalDauNoiOdf: false,
        }
    },
    methods: {
        setDisableBtn: function (isNhapmoi, isGhilai, isHuy, isXoa, isDauNoi, isGhiChu) {
            this.isDisableBtnNhapMoi = isNhapmoi;
            this.isDisableBtnGhiLai = isGhilai;
            this.isDisableBtnHuy = isHuy;
            this.isDisableBtnXoa = isXoa;
            this.isDisableBtnDauNoi = isDauNoi;
            this.isDisableBtnGhiChu = isGhiChu;
        },
        layDsOdf: async function() {
            try {
                let rs = await this.$root.context.post('/web-ecms/danhmuc/odf/ds-odf-theo-turack-va-tramtb', {
                    turack_id: this.turack_id,
                    tramtb_id: this.tramTb_id
                });
                this.dsOdf = rs.data;
            } catch (error) {
                
            }
        },
        layDsMaDuAn: async function() {
            try {
                let rs =  await this.$root.context.get('/web-cabman/duan/layDsDuAn');
                this.dsMaDuAn = rs.data;
            } catch (error) {
                
            }
        },
        layDsMaTaiSan: async function() {
            try {
                let rs =  await this.$root.context.get(`/web-cabman/taisan-duan/lay-ds-taisan-by-duanid/${this.duan_id}`);
                this.dsMaTaiSan = rs.data;
            } catch (error) {
                
            }
        },
        selectingEvent: function(e) {
            this.currentIndex = e.rowIndex;
            this.odf_id = e.data.ODF_ID;
            this.ten_odf = e.data.TEN_ODF;
            this.turackDD_id = e.data.TURACK_ID;
            this.soPort = e.data.SO_PORT;
            this.portBD = e.data.PORT_BD;
            this.loaiCong_id = e.data.LOAI_CONG
            this.ghiChu = e.data.GHICHU;
            this.duan_id = e.data.DUAN_ID;
            this.taisan_id = e.data.TAISAN_ID;
            this.setDisableBtn(false, false, false, false, false, false);
        },
        setData: function(odf_id, turackDD_id, ten_odf, soPort, portDB, loaiCong_id, ghiChu, duan_id, taisan_id) {
            this.odf_id = odf_id;
            this.turackDD_id = turackDD_id;
            this.ten_odf = ten_odf;
            this.soPort = soPort;
            this.portBD = portDB;
            this.loaiCong_id = loaiCong_id;
            this.ghiChu = ghiChu;
            this.duan_id = duan_id;
            this.taisan_id = taisan_id;
        },
        dataBound(args){
            if(this.dsOdf.length > 0) {
                // this.$refs.grid.selectRow(0);
            }
            else {
                this.setData(null, null, null, null, null, null, null, null, null);
                this.setDisableBtn(false, true, true, true, true , true);
            }
        },
        nhapMoi: function() {
            if(this.isDisableBtnNhapMoi) {
                return;
            }
            this.setData(-1, this.turack_id, null, 0, 0, 'SC', null, null, null);
            this.setDisableBtn(true, false, false, true, true, true);
        },
        checkForm: function() {
            let check = true;
            let error = '';
            if(this.portBD == null || this.portBD === '') {
                check = false;
                error = 'Cổng bắt đầu không được để trống';
            }
            else if(parseInt(this.portBD) < 0 || parseInt(this.portBD) > 65535) {
                check = false;
                error = 'Cổng bắt đầu phải nằm trong khoảng từ 0 đến 65535';
            }

            if(this.soPort == null || this.soPort === '') {
                check = false;
                error = 'Số cổng không được để trống\n'.concat(error);
            }
            else if(parseInt(this.soPort) < 1 || parseInt(this.soPort) > 65535) {
                check = false;
                error = 'Số cổng phải nằm trong khoảng từ 1 đến 65535\n'.concat(error);
            }

            if(!this.ten_odf) {
                check = false;
                error = 'Tên ODF không được để trống\n'.concat(error);
            }
            else if(this.ten_odf.length > 50) {
                check = false;
                error = 'Trường tên odf nhập dữ liệu vượt quá độ dài cho phép là 50 ký tự!\n'.concat(error);
            }

            if(this.turackDD_id <= 0) {
                check = false;
                error = 'Tủ rack không được để trống\n'.concat(error);
            }

            if(!check) {
                this.$root.toastError(error);
            }
            return check;
        },
        taoDuLieuOdf: function() {
            return {
                odfId: this.odf_id,
                turackId: this.turackDD_id,
                tenOdf: this.ten_odf,
                soPort: this.soPort,
                portBd: this.portBD,
                ghichu: this.ghiChu,
                loaicong: this.loaiCong_id,
                duanId:  this.duan_id,
                taisanId:  this.taisan_id
            }
        },
        ghiLai: async function() {
            if(this.isDisableBtnGhiLai) {
                return;
            }
            if(this.checkForm()) {
                if(this.isDisableBtnNhapMoi) {
                    try {
                        this.loading(true);
                        let rs = await this.$root.context.post('/web-ecms/danhmuc/odf/tao_odf', this.taoDuLieuOdf());
                        if(rs.data) {
                            this.$root.toastSuccess("Thêm ODF thành công.");
                            this.layDsOdf();
                            // this.setDisableBtn(false, true, true, true);
                        }
                    } catch (error) {
                        this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : "Thêm ODF thất bại.");
                    } finally {
                        this.loading(false);
                    }
                }
                else {
                    try {
                        this.loading(true);
                        let rs = await this.$root.context.post('/web-ecms/danhmuc/odf/capnhat_odf', this.taoDuLieuOdf());
                        if(rs.data) {
                            this.$root.toastSuccess("Cập nhật ODF thành công.");
                            this.layDsOdf();
                            // this.setDisableBtn(false, true, true, true);
                        }
                    } catch (error) {
                        this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : "Cập nhật ODF thất bại.");
                    } finally {
                        this.loading(false);
                    }
                }
            }
        },
        huyBo: function() {
            if(this.isDisableBtnHuy) {
                return;
            }
            if(this.dsOdf.length == 0) {
                this.setData(null, null, null, null, null, null, null, null, null);
                this.setDisableBtn(false, true, true, true, true , true);
                return;
            }
            if(this.isDisableBtnNhapMoi) {
                // this.setData(null, null, null, null, null, null, null, null, null);
                this.$refs.grid.selectRow(this.currentIndex);
            }
            else {
                this.$refs.grid.selectRow(this.currentIndex);
            }
        },
        xoa: function() {
            if(this.isDisableBtnXoa) {
                return;
            }
            this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xóa dữ liệu không?', {
                size: 'sm',
                centered: true,
                okTitle: 'Đồng ý',
                cancelTitle: 'Hủy',
            })
            .then(async val => {
                if(val) {
                    if(this.odf_id) {
                        try {
                            this.loading(true);
                            let isCheck = await this.checkData(this.odf_id);
                            if(isCheck) {
                                var rs = await this.$root.context.post(`/web-ecms/danhmuc/odf/xoa_odf?odfId=${this.odf_id}`);
                                if(rs.data.result) {
                                    this.$root.toastSuccess('Xóa ODF thành công.');
                                    this.setData(null, null, null, null, null, null, null, null, null);
                                    this.setDisableBtn(false, true, true, true, true, true);
                                    await this.layDsOdf();
                                }
                            }
                        } catch (error) {
                            this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : 'Xóa ODF thất bại.');
                        } finally {
                            this.loading(false);
                        }
                    }
                }
            })
            .catch(err => {
                this.$root.toastError('Xóa ODF thất bại.');
            })
        },
        closeModalDauNoiOdf: function() {
            this.isShowModalDauNoiOdf = !this.isShowModalDauNoiOdf;
        },
        ghiLaiGhiChuOdf: async function() {
            this.$bvModal.hide('popupGhiChuOdf');
            await this.layDsOdf();
        },
        checkData: async function(id) {
            let rs = await this.$root.context.get(`/web-cabman/dau-noi-odf/kiem-tra-dau-noi?odf_id=${id}`);
            if(rs.data.result) {
                this.$root.toastError("Không thể xóa do có dữ liệu liên quan");
                return false;
            }
            return true;
        }
    },
    mounted: async function() {
        try {
            this.loading(true);
            this.dsLoaiCong = [
                {
                    LOAICONG_ID: 'FC',
                    LOAICONG: 'Tròn'
                },
                {
                    LOAICONG_ID: 'SC',
                    LOAICONG: 'Vuông'
                }
            ]
            let rs = await this.$root.context.post('/web-ecms/danhmuc/turack/ds-turack-theo-donvi', {
                donvi_id: this.tramTb_id
            });
            this.dsTuRack = rs.data;
            this.dsTuRackTemp = JSON.parse(JSON.stringify(rs.data));
            this.dsTuRackTemp.unshift({TURACK_ID: -1, TEN_TU: '---Tất cả---'});
            await this.layDsOdf();
            await this.layDsMaDuAn();
        } catch (error) {
            
        } finally {
            this.loading(false);
        }
    },
    watch: {
        duan_id: async function(val) {
            if(val) {
                try {
                    this.loading(true);
                    await this.layDsMaTaiSan();
                } catch (error) {
                    
                } finally {
                    this.loading(false);
                }
            }
            else {
                this.dsMaTaiSan = [];
            }
        },
        turack_id: async function() {
            await this.layDsOdf();
            
        }
    }
}
</script>

<style scoped>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>

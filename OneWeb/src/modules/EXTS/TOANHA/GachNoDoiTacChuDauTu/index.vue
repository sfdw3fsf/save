<template src="./index.template.html"></template>
<style scoped src="./style.scss"></style>
<script>
import ComboboxGrid from "../../../../components/Controls/ComboboxGrid"
import Calculator from '@/components/Calculator.vue'
import KyCuoc from '@/components/KyCuoc'
import EventBus from '@/utils/eventBus'
import PopupPhieuHuy from '../PhieuHuy/Modal/index.vue'
import PopupPhieuTraTCQLDA from '../QuanLyPhieuTraTCQLDA/index.vue'
import { Query } from '@syncfusion/ej2-data'
import moment from 'moment'
import Vue from 'vue'
import { 
    required,
    minValue
} from "vuelidate/lib/validators"
export default {
    components: { 
        appCombobox: ComboboxGrid, 
        appKyCuoc: KyCuoc,
        Calculator,
        PopupPhieuHuy,
        PopupPhieuTraTCQLDA
    },

    name: 'GachNoDoiTacChuDauTu',

    async created() {
        EventBus.$on('chukycuocChange', this.chukycuocChangeHandler);
        this.clear();
        // await this.getDsNganHang();
        // await this.getDsHTTT();
        // await this.getDsThuNgan();
        // await this.getDsLoaiTien();
        // await this.getKyCuocHienHanh();
        this.cbo_Loai.items = [
            { loai_id: 1, ten_loai: 'Đối tác'},
            { loai_id: 2, ten_loai: 'Chủ đầu tư'}
        ];
        this.cbo_Loai.selected = 1;
        await Promise.all([
            this.getKyCuocHienHanh(),
            this.getDsThuNgan(),
            this.getDsLoaiTien(),
            this.getDsHTTT(),
            this.getDsNganHang()
        ]);
    },

    beforeUpdate() {
        this.hideComboboxTableHeader();
    },

    destroyed () {
        EventBus.$off('chukycuocChange', this.chukycuocChangeHandler)
    },

    mounted() {
        this.$refs.maTB.focus();
    },

    data () {
        return {
            thongtinKyHoaDon: {
                kyHoaDonSelected : null,
                chuKySelected : null
            },
            txt_MaTB: null,
            txt_DiaChi: null,
            txt_TaiKhoan: null,
            txt_GhiChu: null,
            txt_SoHD: 0,
            txt_ChungTu: null,
            txt_TienTra: {
                value: 0,
                readOnly: false
            },
            txt_TienDC: 0,
            chb_DTPC: true,
            cbo_Loai: {
                items: [],
                selected: 0
            },
            cbo_DTCDT: {
                items: [],
                selected: 0
            },
            cbo_ToaNha: {
                items: [],
                selected: null
            },
            cbo_NganHang: {
                items: [],
                selected: null
            },
            cbo_HinhThucTT: {
                items: [],
                selected: null,
                fields: {
                    text: 'hinhthuc', 
                    value: 'httt_id' 
                },
            },
            cbo_MauSo: {
                items: [],
                selected: null,
                fields: {
                    text: 'ten_loaihd', 
                    value: 'loaihoadon_id' 
                }
            },
            cbo_KyHieu: {
                items: [],
                selected: null,
                fields: {
                    text: 'seri', 
                    value: 'seri_id' 
                }
            },
            cbo_ThuQuy: {
                items: [],
                selected: null,
                fields: {
                    text: 'TEN_NV', 
                    value: 'NHANVIEN_ID' 
                }
            },
            cbo_LoaiTien: {
                items: [],
                selected: null,
                fields: {
                    text: 'viettat', 
                    value: 'loaitien_id' 
                }
            },
            btn_Enabled: {
                huy: false,
                gachNo: false,
                dieuChinh: false
            },
            btn_Visible: {
                gachNo: true,
                dieuChinh: false
            },
            chb_HDDT: {
                value: false,
                enabled: true
            },
            date_NgayHoaDon: new Date(),
            date_NgayTT: new Date(),
            today: new Date(),
            isCalcShow: false,
            tab_Index: 0,
            grid_ThangNo: {
                items: [],
                checked: [],
                key: 1
            },
            grid_DieuChinh: {
                items: [],
                key: 1
            },
            phieuId: 0,
            customError: {
                txt_SoHD: false
            },
            sumTemplate: function() {
                return { template: Vue.component('sumTemplate', {
                    template: `<input type="text" class="form-control tright" :value="data.Sum" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
                    data: function () {return {data: {data: {}}};}
                })
                }
            } 
        }
    },

    validations: {
        cbo_ThuQuy: {
            selected: {
                required
            }
        },
        cbo_HinhThucTT: {
            selected: {
                required
            }
        },
        cbo_LoaiTien: {
            selected: {
                required
            }
        },
        cbo_NganHang: {
            selected: {
                required
            }
        },
        txt_TienTra: {
            value: {
                minValue: minValue(1)
            }
        },
        txt_SoHD: {
            requiredSoHD: (txt_SoHD, vm) => (vm.chb_HDDT.value && txt_SoHD > 0) || (!vm.chb_HDDT.value)
        },
        date_NgayHoaDon: {
            requiredDateHD: (date_NgayHoaDon, vm) => (vm.chb_HDDT.value && date_NgayHoaDon != null) || (!vm.chb_HDDT.value)
        }
    },

    methods: {
        hideComboboxTableHeader() {
            let cbbRef = ['dtCbo', 'toaNhaCbo', 'nganHangCbo'];
            cbbRef.forEach(item => {
                let elemts = this.$refs[item].$refs.grid.$el.querySelectorAll('.e-headercell');
                elemts.forEach(elment => {
                    elment.style.display = 'none';
                });
            });
        },

        chukycuocChangeHandler(e) {
            this.thongtinKyHoaDon.kyHoaDonSelected =moment(e.p_kyhoadon).format('YYYYMM') + '01';
            this.thongtinKyHoaDon.chuKySelected = e.p_chuky_no;
        },
        
        onDTSelected(args) {
            if (args)
                this.cbo_DTCDT.selected = args.dt_id;
        },

        onToaNhaSelected(args) {
            if (args)
                this.cbo_ToaNha.selected = args.toanha_id;
        },

        onNganHangSelected(args) {
            if (args)
                this.cbo_NganHang.selected = args.nganhang_id;
        },

        onFilteringHTTT(e){
            var query = new Query();
            query = (e.text !== '') ? query.where('hinhthuc', 'contains', e.text, true) : query;
            e.updateData(this.cbo_HinhThucTT.items, query);
        },

        onFilteringPattern(e) {
            var query = new Query();
            query = (e.text !== '') ? query.where('ten_loaihd', 'contains', e.text, true) : query;
            e.updateData(this.cbo_MauSo.items, query);
        },

        onFilteringSerial(e) {
            var query = new Query();
            query = (e.text !== '') ? query.where('seri', 'contains', e.text, true) : query;
            e.updateData(this.cbo_KyHieu.items, query);
        },

        onFilteringThuQuy(e) {
            var query = new Query();
            query = (e.text !== '') ? query.where('TEN_NV', 'contains', e.text, true) : query;
            e.updateData(this.cbo_ThuQuy.items, query);
        },

        onFilteringLoaiTien(e) {
            var query = new Query();
            query = (e.text !== '') ? query.where('viettat', 'contains', e.text, true) : query;
            e.updateData(this.cbo_LoaiTien.items, query);
        },

        async changeTab(index) {
            this.tab_Index = index;
            if (index === 0) {  //Gach no
                this.btn_Visible.dieuChinh = false;
                this.btn_Visible.gachNo = true;
                this.txt_TienTra.readOnly = false;
                this.chb_HDDT.enabled = true;
                await this.getDsHTTT();
            } else {            //Dieu chinh no
                this.btn_Visible.dieuChinh = true;
                this.btn_Visible.gachNo = false;
                this.txt_TienTra.readOnly = true;
                this.chb_HDDT.enabled = false;
                await this.getDsHTTT(83);
            }
        },

        selectCboRow(nganHangId) {
            let idx1 = nganHangId ? this.cbo_NganHang.items.findIndex(e => e.nganhang_id === nganHangId) : 0;
            this.$refs.nganHangCbo.$refs.grid.setCurrentSelectedRow(idx1 !== -1 ? idx1 : 0);
        },

        clear() {
            this.txt_DiaChi = '';
            this.txt_ChungTu = '';
            this.txt_GhiChu = '';
            this.txt_TaiKhoan = '';
            //cboNganHang.EditValue = 0;
            this.date_NgayTT = this.today;
            this.txt_TienDC = 0;
            this.txt_TienTra.value = 0;
            this.txt_MaTB = '';
        },

        setButton(kieu) {
            this.btn_Enabled.huy = false;
            this.$v.$reset();
            this.customError.txt_SoHD = false;
            if (kieu === -1) 
                this.btn_Enabled.huy = true;
            else if (kieu === 0) {
                this.btn_Enabled.gachNo = false;
                this.clear();
            } else if (kieu === 1) {
                this.btn_Enabled.gachNo = false;
                this.btn_Enabled.dieuChinh = false;
                this.btn_Enabled.huy = true;
                this.clear();
            } else if (kieu === 2) {
                this.clear();
                this.btn_Enabled.huy = true;
            } else this.btn_Enabled.huy = true;
        },

        onSelectThangNo(args) {
            if (args != null)
                this.txt_MaTB = args.data.ma_tb;
        },

        onValidateGachNo() {
            this.$v.$touch();
            let errorMessage = [];

            if (!this.$v.cbo_HinhThucTT.selected.required)
                errorMessage.push("Hình thức thanh toán không được để trống!");
            if (!this.$v.txt_SoHD.requiredSoHD) {
                errorMessage.push("Số hóa đơn phải khác 0!");
                this.$refs.soHD.focus();
            }
            if (!this.$v.date_NgayHoaDon.requiredDateHD)
                errorMessage.push("Ngày hóa đơn không được để trống!");
            if (!this.$v.cbo_ThuQuy.selected.required)
                errorMessage.push("Thủ quỹ không được để trống!");
            if (!this.$v.cbo_LoaiTien.selected.required)
                errorMessage.push("Loại tiền gạch nợ không được để trống!");
            if (!this.$v.txt_TienTra.value.minValue) {
                errorMessage.push("Số tiền trả phải khác 0!");
                // this.$refs.tienTra.focus();
            }
            
            
            if (errorMessage.length > 0) {
                this.$toast.error(errorMessage.join("\r\n"));
                return false;
            } else return true;
        },

        onValidateDieuChinh() {
            if (this.chb_HDDT.value) {
                let errorMessage = [];
                if (this.txt_SoHD === 0) {
                    this.customError.txt_SoHD = true;
                    errorMessage.push("Số hóa đơn phải khác 0!");
                    this.$refs.soHD.focus();
                } else this.customError.txt_SoHD = false;
                if (this.date_NgayHoaDon == null) {
                    errorMessage.push("Ngày hóa đơn không được để trống!");
                }
                if (errorMessage.length > 0) {
                    this.$toast.error(errorMessage.join("\r\n"));
                    return false;
                } else return true;
            } else return true;
        },

        async onLayTT(isMessage = false) {
            this.setButton(2);
            let dtThongTin = [];
            if (this.cbo_Loai.selected === 1) //Doi tac
                dtThongTin = await this.getTTDoiTac(this.cbo_DTCDT.selected);
            else    //Chu dau tu
                dtThongTin = await this.getTTChuDauTu(this.cbo_DTCDT.selected);
            
            if (dtThongTin.length > 0) {
                this.txt_DiaChi = dtThongTin[0].diachi;
                this.txt_TaiKhoan = dtThongTin[0].taikhoan;
                this.selectCboRow(dtThongTin[0].nganhang_id);
            }
            
            if (this.tab_Index === 0) {
                let dsThangNo = await this.getDsNoQLDA(1);
                this.grid_ThangNo.items = [...dsThangNo];
                if (dsThangNo.length > 0) {
                    this.btn_Enabled.gachNo = true;
                    this.txt_TienTra.value = this.grid_ThangNo.items.reduce((accumulator, object) => {
                        return accumulator + object.conno;
                    }, 0);
                } else {
                    this.btn_Enabled.gachNo = false;
                    if (isMessage) this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
                }
            } else {
                let dsDieuChinh = await this.getDsNoQLDA(2);
                this.grid_DieuChinh.items = [...dsDieuChinh];
                if (dsDieuChinh.length > 0)
                    this.btn_Enabled.dieuChinh = true;
                else { 
                    this.btn_Enabled.dieuChinh = false;
                    if (isMessage) this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
                }
            }
        },

        async onGachNo() {
            if (this.grid_ThangNo.items.length <= 0)
                return this.$toast.error('Chưa có thông tin để gạch nợ!');
            if (this.onValidateGachNo()) {
                try {                
                    await this.$bvModal.msgBoxConfirm('Bạn thật sự muốn gạch nợ?', {
                        "title": 'Thông báo',
                        "size": 'sm',
                        "buttonSize": 'md',
                        "okVariant": 'primary',
                        "okTitle": 'Đồng ý',
                        "cancelTitle": 'Không đồng ý',
                        "hideHeaderClose": false,
                        "centered": true,
                        "modal-class": ["f18","text-center"],
                    })
                    .then(async(value) => {
                        if (value){
                            //Tạo dữ liệu bảng phiếu trả
                            let rs = await this.addBangPhieuTraDT();
                            //Tạo dữ liệu chi tiết trả
                            await this.onGachChiTiet();
                            this.$toast.success('Gạch nợ thành công!');
                            await this.onLayTT();
                        }
                    })
                    .catch(err => {
                        console.log(err);
                    })
                } catch (error) {
                    console.log(error);
                    this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
                }
            }
        },

        async onDieuChinh() {
            if (this.onValidateDieuChinh()) {
                let ds_temp = this.grid_DieuChinh.items.filter(e => e.dc_tong !== 0);
                if (ds_temp.length === 0)
                    this.$toast.error('Không có dữ liệu nào cần điều chỉnh. Vui lòng kiểm tra lại!');
                else {
                    try {                
                        await this.$bvModal.msgBoxConfirm('Bạn thật sự muốn điều chỉnh doanh thu?', {
                            "title": 'Thông báo',
                            "size": 'sm',
                            "buttonSize": 'md',
                            "okVariant": 'primary',
                            "okTitle": 'Đồng ý',
                            "cancelTitle": 'Không đồng ý',
                            "hideHeaderClose": false,
                            "centered": true,
                            "modal-class": ["f18","text-center"],
                        })
                        .then(async(value) => {
                            if (value){
                                let rs = await this.addBangPhieuTraDT();
                                let in_chuky = moment(this.today.setMonth(this.today.getMonth() - 1)).format('YYYYMM') + '01';
                                for (let item of ds_temp) {
                                    await this.addCTTraDT(
                                        item.toanha_id, item.ma_toanha, item.thuebao_id, item.ma_tb,
                                        item.dichvuvt_id, item.loaitb_id, 1, this.$auth.getDonViID(),
                                        item.dc_nogoc, item.dc_thue, in_chuky, 5, 999
                                    );
                                }
                                this.$toast.success('Điều chỉnh nợ thành công!');
                                await this.onLayTT();
                            }
                        })
                        .catch(err => {
                            console.log(err);
                        })
                    } catch (error) {
                        console.log(error);
                        this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
                    }
                }
            }
        },

        onHuy() {
            this.setButton(1);
        },

        onPhieuTra() {
            if (this.cbo_ThuQuy.selected == null)
                this.$toast.error('Bạn chưa chọn thu ngân. Hãy kiểm tra lại!');
            else
                this.$bvModal.show("frmPhieuTraTCQLDA");
        },

        onPhieuHuy() {
            let ma_dt = this.cbo_DTCDT.items.find(e => e.dt_id === this.cbo_DTCDT.selected).ma_dt;
            this.$refs.dlgPhieuHuy.ma_dt = ma_dt;
            this.$refs.dlgPhieuHuy.showModal();
        },

        async onGachChiTiet() {
            let in_tragoc = 0, in_trathue = 0, in_nogoc = 0, in_nothue = 0, in_conno = 0, in_tongno = 0;
            let dtDs = await this.getDsCTNoDT();
            for (let i = 0; i < this.grid_ThangNo.items.length; i++) {
                if (this.grid_ThangNo.items[i].conno != null && Number(this.grid_ThangNo.items[i].conno) !== 0) {
                    in_tongno = Number(this.grid_ThangNo.items[i].conno);
                    let dtSub = dtDs.filter(e => 
                        e.chukyno === this.grid_ThangNo.items[i].chukyno 
                        && e.toanha_id === this.grid_ThangNo.items[i].toanha_id 
                        && e.thuebao_id === this.grid_ThangNo.items[i].thuebao_id
                    );

                    for (let j = 0; j < dtSub.length; j++) {
                        if (in_tongno === 0) break;
                        let in_donvi_id = dtSub[j].donvi_id != null && dtSub[j].donvi_id !== '' ? dtSub[j].donvi_id : null;
                        in_nogoc = dtSub[j].nogoc != null ? Number(dtSub[j].nogoc) : 0;
                        in_nothue = dtSub[j].thue != null ? Number(dtSub[j].thue) : 0;
                        in_conno = in_nogoc + in_nothue;

                        if (j < dtSub.length - 1) {
                            in_tongno -= in_conno;
                            in_tragoc = in_nogoc;
                            in_trathue = in_nothue;
                            await this.addCTTraDT(
                                this.grid_ThangNo.items[i].toanha_id, this.grid_ThangNo.items[i].ma_toanha, this.grid_ThangNo.items[i].thuebao_id, this.grid_ThangNo.items[i].ma_tb,
                                this.grid_ThangNo.items[i].dichvuvt_id, this.grid_ThangNo.items[i].loaitb_id, this.grid_ThangNo.items[i].loai_chiphi, in_donvi_id,
                                -in_tragoc, -in_trathue, this.grid_ThangNo.items[i].chukyno, dtSub[j].kieuno, dtSub[j].khoanmuctt_id
                            );
                        } else { // Dòng cuối
                            if (in_conno === in_tongno) {
                                in_tongno -= in_conno;
                                in_tragoc = in_nogoc;
                                in_trathue = in_nothue;
                                await this.addCTTraDT(
                                    this.grid_ThangNo.items[i].toanha_id, this.grid_ThangNo.items[i].ma_toanha, this.grid_ThangNo.items[i].thuebao_id, this.grid_ThangNo.items[i].ma_tb,
                                    this.grid_ThangNo.items[i].dichvuvt_id, this.grid_ThangNo.items[i].loaitb_id, this.grid_ThangNo.items[i].loai_chiphi, in_donvi_id,
                                    -in_tragoc, -in_trathue, this.grid_ThangNo.items[i].chukyno, dtSub[j].kieuno, dtSub[j].khoanmuctt_id
                                );
                            } else {
                                if (in_tongno > in_conno && in_conno > 0) {
                                    in_tongno -= in_conno;
                                    in_tragoc = Math.round((in_conno) / 1.1);
                                    in_trathue = in_conno - in_tragoc;

                                    if (in_tragoc !== 0 || in_trathue !== 0)
                                        await this.addCTTraDT(
                                            this.grid_ThangNo.items[i].toanha_id, this.grid_ThangNo.items[i].ma_toanha, this.grid_ThangNo.items[i].thuebao_id, this.grid_ThangNo.items[i].ma_tb,
                                            this.grid_ThangNo.items[i].dichvuvt_id, this.grid_ThangNo.items[i].loaitb_id, this.grid_ThangNo.items[i].loai_chiphi, in_donvi_id,
                                            -in_tragoc, -in_trathue, this.grid_ThangNo.items[i].chukyno, dtSub[j].kieuno, dtSub[j].khoanmuctt_id
                                        );
                                }

                                //Nếu (in_tongno < in_conno) || Tiền dư (in_tongno > in_conno)
                                in_tragoc = Math.round(in_tongno / 1.1);
                                in_trathue = in_tongno - in_tragoc;
                                if (in_tragoc !== 0 || in_trathue !== 0)
                                    await this.addCTTraDT(
                                        this.grid_ThangNo.items[i].toanha_id, this.grid_ThangNo.items[i].ma_toanha, this.grid_ThangNo.items[i].thuebao_id, this.grid_ThangNo.items[i].ma_tb,
                                        this.grid_ThangNo.items[i].dichvuvt_id, this.grid_ThangNo.items[i].loaitb_id, this.grid_ThangNo.items[i].loai_chiphi, in_donvi_id,
                                        -in_tragoc, -in_trathue, this.grid_ThangNo.items[i].chukyno, dtSub[j].kieuno, dtSub[j].khoanmuctt_id
                                    );
                            }
                        }
                    }
                }
            }
        },

        onCellEdit(args) {
            if (args.columnName === 'dc_nogoc' || args.columnName === 'dc_thue') {
                let key = args.rowData.thuebao_id;
                let index = this.grid_DieuChinh.items.findIndex(e => e.thuebao_id === key);
                if (index !== -1) {
                    if (args.columnName === 'dc_nogoc') {
                        this.grid_DieuChinh.items[index].dc_nogoc = args.value;
                        this.grid_DieuChinh.items[index].dc_thue = this.grid_DieuChinh.items[index].dc_nogoc * 10 / 100;
                    } else 
                        this.grid_DieuChinh.items[index].dc_thue = args.value;

                    this.grid_DieuChinh.items[index].dc_tong = this.grid_DieuChinh.items[index].dc_nogoc + this.grid_DieuChinh.items[index].dc_thue;
                    this.grid_DieuChinh.items = [...this.grid_DieuChinh.items];
                    this.txt_TienDC = this.grid_DieuChinh.items.reduce((accumulator, object) => {
                        return accumulator + object.dc_tong;
                    }, 0);
                }
            }
        },

        onMaTBKeyPress() {
            this.txt_MaTB = this.txt_MaTB.trim();
            if (this.tab_Index === 0) {
                let index = this.grid_ThangNo.items.findIndex(e => e.ma_tb === this.txt_MaTB);
                if (index !== -1)
                    this.$refs.thangNoGrid.setCurrentSelectedRow(index);
            } else {
                let index = this.grid_DieuChinh.items.findIndex(e => e.ma_tb === this.txt_MaTB);
                if (index !== -1)
                    this.$refs.grid_DieuChinh.setCurrentSelectedRow(index);
            }
        },

        onChangeTienTra(e) {
            let newVal = e.target._value;
            if (this.grid_ThangNo.items.length <= 0) {
                this.txt_TienTra.value = 0;
            } else {
                let dsTemp = [...this.grid_ThangNo.items];
                let tongtra = 0, vtongno = 0;
                let kiemtra = true;
                let length = this.grid_ThangNo.items.length;
                vtongno = Number(newVal);
                for (let i = 0; i < length; i++) {
                    tongtra = Number(dsTemp[i].conno);
                    if (vtongno === 0 || !kiemtra)
                        this.grid_ThangNo.items[i].conno = 0;
                    else {
                        if (tongtra >= vtongno) {
                            this.grid_ThangNo.items[i].conno = vtongno;
                            kiemtra = false;
                            vtongno = 0;
                        } else {
                            this.grid_ThangNo.items[i].conno = dsTemp[i].conno;
                            vtongno = vtongno - tongtra;
                        }
                    }
                }
                if (vtongno !== 0)
                    this.grid_ThangNo.items[length - 1].conno += vtongno;
                this.grid_ThangNo.items = [...this.grid_ThangNo.items];
            }
        },

        async getKyCuocHienHanh() {
            try {
                this.loading(true);
                let url = `/web-thuno/api/thu-no/common/ky-cuoc-hien-hanh`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    this.thongtinKyHoaDon.kyHoaDonSelected = response.data[0].KYCUOC.toString();
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDsNganHang() {
            try {
                this.loading(true);
                this.cbo_NganHang.items = [];
                let url = `/web-toanha/danhmuc-tochuc-qlda/ds-nganhang`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    this.cbo_NganHang.items = response.data;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDsHTTT(id = null) {
            try {
                this.loading(true);
                this.cbo_HinhThucTT.items = [];
                let url = `/web-toanha/gachno-doitac-chudautu/ds-hinhthuc-thanhtoan`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    if (response.data.length > 0) {
                        this.cbo_HinhThucTT.items = id != null ? response.data.filter(e => e.httt_id === id) : response.data;
                        this.cbo_HinhThucTT.selected = this.cbo_HinhThucTT.items[0].httt_id;
                    }
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDsLoaiTien() {
            try {
                this.loading(true);
                this.cbo_LoaiTien.items = [];
                let url = `/web-toanha/gachno-doitac-chudautu/ds-loaitien`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    if (response.data.length > 0) {
                        this.cbo_LoaiTien.items = response.data;
                        this.cbo_LoaiTien.selected = this.cbo_LoaiTien.items[0].loaitien_id;
                    }
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDsThuNgan() {
            try {
                this.loading(true);
                this.cbo_ThuQuy.items = [];
                let url = `/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-danh-sach-nhan-vien-thu-ngan`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    if (response.data.length > 0) {
                        this.cbo_ThuQuy.items = response.data;
                        this.cbo_ThuQuy.selected = this.cbo_ThuQuy.items[0].NHANVIEN_ID;
                    }
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDsDoiTac() {
            try {
                this.loading(true);
                this.cbo_DTCDT.items = [];
                let url = `/web-toanha/gachno-doitac-chudautu/ds-thamso-doitac`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    this.cbo_DTCDT.items = response.data;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDsChuDauTu() {
            try {
                this.loading(true);
                this.cbo_DTCDT.items = [];
                let url = `/web-toanha/gachno-doitac-chudautu/ds-thamso-chudautu`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    this.cbo_DTCDT.items = response.data;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getMauSoHoaDon() {
            try {
                this.loading(true);
                this.cbo_MauSo.items = [];
                let url = `/web-toanha/gachno-doitac-chudautu/ds-loai-hoadon?kieu=2`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    if (response.data.length > 0) {
                        this.cbo_MauSo.items = response.data;
                        this.cbo_MauSo.selected = this.cbo_MauSo.items[0].loaihoadon_id;
                    }
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getKyHieuHoaDon(loaiHDId) {
            try {
                this.loading(true);
                this.cbo_KyHieu.items = [];
                let url = `/web-toanha/gachno-doitac-chudautu/ds-seri-dientu?kieu=1&loaiHDId=${loaiHDId}`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    if (response.data.length > 0) {
                        this.cbo_KyHieu.items = response.data;
                        this.cbo_KyHieu.selected = this.cbo_KyHieu.items[0].seri_id;
                    }
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDsNoQLDA(kieu) {
            try {
                this.loading(true);
                let params = {
                    kieu: kieu,
                    kyHD: this.thongtinKyHoaDon.kyHoaDonSelected,
                    loai: this.cbo_Loai.selected,
                    dtId: this.cbo_DTCDT.selected,
                    toaNhaId: this.cbo_ToaNha.selected,
                    dtpcCuocPS: this.chb_DTPC ? 1 : 0
                };
                let url = `/web-toanha/gachno-doitac-chudautu/ds-no-qlda`;
                let response = await this.$root.context.get(url, params);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    return response.data;
                } return [];
            }
            catch (error) {
                console.log(error);
                return [];
            }
            finally {
                this.loading(false);
            }
        },

        async getTTDoiTac(id) {
            try {
                this.loading(true);
                let url = `/web-toanha/gachno-doitac-chudautu/thongtin-doitac/${id}`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    return response.data;
                } else return [];
            }
            catch (error) {
                console.log(error);
                return [];
            }
            finally {
                this.loading(false);
            }
        },

        async getTTChuDauTu(id) {
            try {
                this.loading(true);
                let url = `/web-toanha/gachno-doitac-chudautu/thongtin-chudautu/${id}`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    return response.data;
                } else return [];
            }
            catch (error) {
                console.log(error);
                return [];
            }
            finally {
                this.loading(false);
            }
        },

        async getDsToaNhaQLDA() {
            try {
                this.loading(true);
                this.cbo_ToaNha.items = [];
                let params = {
                    loai: this.cbo_Loai.selected,
                    dtId: this.cbo_DTCDT.selected
                };
                let url = `/web-toanha/gachno-doitac-chudautu/ds-toanha-qlda`;
                let response = await this.$root.context.get(url, params);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    this.cbo_ToaNha.items = response.data;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDsCTNoDT() {
            try {
                this.loading(true);
                let params = {
                    kyHoaDon: this.thongtinKyHoaDon.kyHoaDonSelected,
                    loai: this.cbo_Loai.selected,
                    dtId: this.cbo_DTCDT.selected
                };
                let url = `/web-toanha/gachno-doitac-chudautu/ds-ct-no-dt`;
                let response = await this.$root.context.get(url, params);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    return response.data;
                } return [];
            }
            catch (error) {
                console.log(error);
                return [];
            }
            finally {
                this.loading(false);
            }
        },

        async addBangPhieuTraDT() {
            try {
                this.loading(true);
                let maTN = this.cbo_ThuQuy.selected != null ? this.cbo_ThuQuy.items.find(e => e.NHANVIEN_ID === this.cbo_ThuQuy.selected).MA_NV : '';
                let maDT = this.cbo_DTCDT.selected != null ? this.cbo_DTCDT.items.find(e => e.dt_id === this.cbo_DTCDT.selected).ma_dt : '';
                let mauSo = this.cbo_MauSo.selected != null && this.chb_HDDT.value 
                            ? this.cbo_MauSo.items.find(e => e.loaihoadon_id === this.cbo_MauSo.selected).text 
                            : '';
                let kyHieu = this.cbo_KyHieu.selected != null && this.chb_HDDT.value 
                            ? this.cbo_KyHieu.items.find(e => e.seri_id === this.cbo_KyHieu.selected).text 
                            : '';
                let apiBody = {
                    kyHoaDon: this.thongtinKyHoaDon.kyHoaDonSelected,
                    thuNganId: this.cbo_ThuQuy.selected,
                    maTN: maTN,
                    loaTienId: this.cbo_LoaiTien.selected,
                    loai: this.cbo_Loai.selected,
                    dtId: this.cbo_DTCDT.selected,
                    maDT: maDT,
                    nganHangId: this.cbo_NganHang.selected,
                    soHoaDon: this.chb_HDDT.value ? this.txt_SoHD : null,
                    ngayHD: this.chb_HDDT.value ? moment(this.date_NgayHoaDon).format('DD/MM/YYYY') : null,
                    mauSo: mauSo,
                    kyHieu: kyHieu,
                    ngayTT: moment(this.date_NgayTT).format('DD/MM/YYYY'),
                    htttId: this.cbo_HinhThucTT.selected,
                    trangThai: 1,
                    ghiChu: this.txt_GhiChu,
                    chungTu: this.txt_ChungTu
                };
                let url = `/web-toanha/gachno-doitac-chudautu/them-bang-phieutra-dt`;
                let response = await this.$root.context.post(url, apiBody);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    if (response.data.startsWith('OK')) {
                        this.phieuId = response.data.split('-')[1];
                        return true;
                    }
                    else return false;
                } return false;
            }
            catch (error) {
                console.log(error);
                return false;
            }
            finally {
                this.loading(false);
            }
        },

        async addCTTraDT(
            in_toanha_id, in_ma_toanha, in_thuebao_id, in_ma_tb,
            in_dichvuvt_id, in_loaitb_id, in_loai_chiphi, in_donvi_id,
            in_tragoc, in_trathue, in_chuky, in_kieutra, in_kmtt_id
        ) {
            try {
                this.loading(true);
                let maDT = this.cbo_DTCDT.items.find(e => e.dt_id === this.cbo_DTCDT.selected).ma_dt;
                let apiBody = {
                    kyHoaDon: this.thongtinKyHoaDon.kyHoaDonSelected,
                    phieuId: this.phieuId,
                    toaNhaId: in_toanha_id,
                    maToaNha: in_ma_toanha,
                    thueBaoId: in_thuebao_id,
                    maTB: in_ma_tb,
                    dichVuVTId: in_dichvuvt_id,
                    loaiTBId: in_loaitb_id,
                    loai: this.cbo_Loai.selected,
                    dtId: this.cbo_DTCDT.selected,
                    maDT: maDT,
                    loaiChiPhi: in_loai_chiphi,
                    donViId: in_donvi_id,
                    traGoc: in_tragoc,
                    traThue: in_trathue,
                    khoanMucTTId: in_kmtt_id,
                    kieuTra: in_kieutra,
                    chuKyNo: in_chuky
                };
                let url = `/web-toanha/gachno-doitac-chudautu/them-ct-tra-dt`;
                let response = await this.$root.context.post(url, apiBody);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    return response.data.startsWith('OK');
                } return false;
            }
            catch (error) {
                console.log(error);
                return false;
            }
            finally {
                this.loading(false);
            }
        }
    },

    watch: {
        'cbo_Loai.selected': async function(newVal, oldVal) {
            if (newVal === 1)
                await this.getDsDoiTac();
            else if (newVal === 2)
                await this.getDsChuDauTu();
            
            await this.getMauSoHoaDon();
        },

        'cbo_MauSo.selected': async function(newVal, oldVal) {
            if (newVal)
                await this.getKyHieuHoaDon(newVal);
        },

        'cbo_DTCDT.selected': async function(newVal, oldVal) {
            if (newVal && newVal !== 0) {
                await this.getDsToaNhaQLDA();
                await this.onLayTT();
            }
        },

        'chb_HDDT.value': function(newVal, oldVal) {
            if (newVal) {
                this.txt_SoHD = 0;
                this.date_NgayHoaDon = null;
            }
        },

        'chb_DTPC': async function(newVal, oldVal) {
            await this.onLayTT();
        }
    }
}
</script>

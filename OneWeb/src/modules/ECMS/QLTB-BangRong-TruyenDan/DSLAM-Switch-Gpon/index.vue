<template src='./index.html'>
</template>

<script>
import EventBus from '../../../../utils/eventBus'
import { CheckBoxSelection } from "@syncfusion/ej2-vue-dropdowns"
export default {

    components: {
    },
    provide: {
        multiselect: [CheckBoxSelection]
    },
    computed: {
        requiredFields_soPort: function(){
            let result = true;
            if(this.dslam.loaiTb_id == this.LOAI_TBI.GPON || this.dslam.loaiTb_id == this.LOAI_TBI.DSLAM){
                result = false;
            }
            return result;
        },
        requiredFields_portBD: function(){
            let result = true;
            if(this.dslam.loaiTb_id == this.LOAI_TBI.GPON || this.dslam.loaiTb_id == this.LOAI_TBI.DSLAM){
                result = false;
            }
            return result;
        },
        requiredFields_soSlot: function(){
            let result = true;
            if(this.dslam.loaiTb_id == this.LOAI_TBI.SWITCH || this.dslam.loaiTb_id == this.LOAI_TBI.DSLAM){
                result = false;
            }
            return result;
        },
        requiredFields_cardBD: function(){
            let result = true;
            if(this.dslam.loaiTb_id == this.LOAI_TBI.SWITCH || this.dslam.loaiTb_id == this.LOAI_TBI.DSLAM){
                result = false;
            }
            return result;
        },
        requiredFields_portBras: function() {
            let result = false;
            if(this.maTinh == 'HCM'){
                result = true;
            }
            return result;
        },
        requiredFields_slotBras: function() {
            let result = false;
            if(this.maTinh == 'HCM'){
                result = true;
            }
            return result;
        },
        requiredFields_system: function() {
            let result = true;
            // if(this.maTinh == 'HCM'){
            //     result = true;
            // }
            //hieunph điều chỉnh bắt buộc cho tất cả các tỉnh
            return result;
        }
    },
    data() {
        return {
            enableCheckL2SW: false,
            ttdslam : {},
            // dslam_id: 39602,
            dslam_id: 0,
            turack_id: 0,
            kiemTraIp: 0,
            dslam: {
                nhaTram_id: 0,
                loaiTb_id: null,
                tbVDC: null,
                hangsx_id: null,
                bras_id: null,
                ip: null,
                system: null,
                tenTb: null,
                loaiDslam_id: null,
                kieuDslam_id: null,
                tbCha_id: null,
                thamSo: null,
                ghiChu: null,
                soSlot: null,
                soSlot_cu: 0,
                soPort: null,
                portBD: null,
                vlanMyTv: 0,
                vlanInt: 0,
                vlanIms: 0,
                cardBD: null,
                svlan: 0,
                vlanGNMS: 0,
                vciBD: 0,
                sttOlt: null,
                subSlot: 0,
                doThu: null,
                doitac_id: null,
                toanha_id: null,
                slotBras: null,
                portBras: null,
                MDFBD: 0,
                thongTinAE: null,
                rack_id: [],
            },
            LOAI_TBI: {
                DSLAM: 1,
                SWITCH: 2,
                GPON: 3,
                MANE: 17
            },
            chk_toanha: false,
            chk_doitac: false,
            chk_subSlot: false,
            chk_dothu: true,
            chk_dslamVDC: false,
            dsTuRack: [],
            dsLoaiThietBi: [],
            dsHangSanXuat: [],
            dsLoaiDslam: [],
            dsBras: [],
            dsKieuDslam: [],
            dsDslamCha: [],
            dsDoiTac: [],
            dsToaNha: [],
            dsPortTheoSwtich: [],
            isEnableCardBD: true,
            isEnableSoSlot: true,
            isEnableSoPort: true,
            isEnablePortBD: true,
            isEnableSubSlot: true,
            isEnableTbVDC: false,
            isEnableChk_subSlot: true,
            isEnableLoaiTb: false,
            isEnableBras: false,
            lb_card_slot: 'Card BD',
            isThemMoi: false,
            currentItem: {},
            isLoading: true,
            title: null,
            maTinh: this.$root.token.getMaTinh()
        }
    },
    methods: {
        getTuRack: async function (turack_id) {
            try {
                let rs = await this.$root.context.get(`/web-ecms/danhmuc/turack/chitiet?turack_id=${turack_id}`);
                return rs.data;
            } catch (error) {

            }
        },
        loadDsTuRack: async function() {
            try {
                var rs = await this.$root.context.get(`/web-ecms/danhmuc/turack/chitiet?turack_id=${this.turack_id}`);
                if(rs.data) {
                    this.dslam.nhaTram_id = rs.data.DONVI_ID;
                }
                var rs = await this.$root.context.post('/web-ecms/danhmuc/turack/ds-turack-theo-donvi-va-kieutu', {
                    kieutu: 2,
                    donvi_id: this.dslam.nhaTram_id
                });
                this.dsTuRack = rs.data;
            } catch (error) {

            }
        },
        loadDsLoaiThietBi: async function() {
            try {
                var rs = await this.$root.context.get('/web-ecms/danhmuc/loaitbi/danhsach');
                this.dsLoaiThietBi = rs.data;
            } catch (error) {

            }
        },
        loadDsHangSanXuat: async function() {
            try {
                var rs = await this.$root.context.get('/web-ecms/danhmuc/dongthietbi/ds-hangsx');
                this.dsHangSanXuat = rs.data;
            } catch (error) {

            }
        },
        loaddsLoaiDslam: async function() {
            try {
                var rs = await this.$root.context.get('/web-ecms/danhmuc/dongthietbi/lietKeDs');
                this.dsLoaiDslam = rs.data;
            } catch (error) {

            }
        },
        loadDsBras: async function() {
            try {
                var rs = await this.$root.context.get(`/web-ecms/danhmuc/quanlybras/danhsach/${this.turack_id}`);
                this.dsBras = rs.data;
                // if(this.dsBras) {
                //     this.dslam.bras_id = this.dsBras[0].BRAS_ID;
                // }
            } catch (error) {

            }
        },
        loaddsKieuDslam: async function() {
            try {
                var rs = await this.$root.context.get('/web-ecms/kieuDslam/danhsach');
                this.dsKieuDslam = rs.data;
            } catch (error) {

            }
        },
        loadDsDslamCha: async function() {
            try {
                if(this.dslam.bras_id) {
                    var rs = await this.$root.context.get(`/web-ecms/danhmuc/dongthietbi/lay_ds_dslam_cha/${this.dslam.bras_id}`);
                    this.dsDslamCha = rs.data;
                    if(this.dsDslamCha.length > 0) {
                        this.dslam.tbCha_id = this.dsDslamCha.length > 0 ? this.dsDslamCha[0].DSLAM_ID : null;
                    }
                }
            } catch (error) {

            }
        },
        loadDsDoiTac: async function() {
            try {
                var rs = await this.$root.context.get('/web-ecms/danhmuc/dongthietbi/lay_ds_doitac');
                this.dsDoiTac = rs.data;
                if(this.dslam.doitac_id == null) {
                    this.dslam.doitac_id = this.dsDoiTac.length ? this.dsDoiTac[0].DOITAC_ID : null;
                }
            } catch (error) {

            }
        },
        loadDsToaNha: async function() {
            try {
                var rs = await this.$root.context.get('/web-ecms/danhmuc/dongthietbi/lay_ds_toanha');
                this.dsToaNha = rs.data;
            } catch (error) {

            }
        },
        loadDsPortTheoSwitch: async function() {
            try {
                var rs = await this.$root.context.get(`/web-ecms/danhmuc/dongthietbi/lay_port_theo_switch/${this.dslam_id}`);
                this.dsPortTheoSwtich = rs.data;
            } catch (error) {

            }
        },
        clearForm: function() {
            this.dslam.ip = null;
            this.dslam.system = null;
            this.dslam.tenTb = null;
            this.dslam.thamSo = null;
            // this.dslam.tbVDC = null;
            this.dslam.ghiChu = null;
            this.dslam.thongTinAE = null;
            // this.isEnableCardBD = true;
            // this.isEnableSoPort = true;
            // this.isEnableSoSlot = true;
            // this.isEnablePortBD = true;
            if(this.dslam.loaiTb_id == this.LOAI_TBI.DSLAM) {
                this.isEnableCardBD = false;
                this.isEnableSoSlot = false;
                this.isEnableSoPort = false;
                this.isEnablePortBD = false;
                this.isEnableSubSlot = false;
                this.isEnableChk_subSlot = false;
                this.dslam.soSlot = null;
                this.dslam.soPort = null;
                this.dslam.portBD = null;
            }
            else if(this.dslam.loaiTb_id == this.LOAI_TBI.SWITCH) {
                this.isEnableCardBD = false;
                this.isEnableSoSlot = false;
                this.isEnableSoPort = true;
                this.isEnablePortBD = true;
                this.isEnableSubSlot = false;
                this.isEnableChk_subSlot = true;
                this.chk_subSlot = false;
                this.dslam.soSlot = null;
                this.dslam.portBD = null;
            }
            else if(this.dslam.loaiTb_id == this.LOAI_TBI.GPON || this.dslam.loaiTb_id == this.LOAI_TBI.MANE) {
                this.isEnableCardBD = true;
                this.isEnableSoSlot = true;
                this.isEnableSoPort = false;
                this.isEnablePortBD = false;
                this.isEnableSubSlot = false;
                this.isEnableChk_subSlot = false;
                this.dslam.soPort = null;
            }
        },
        checkForm: async function() {
            var check = true;
            var msg = '';
            if(this.kiemTraIp == 1) {
                var rs = await this.$root.context.post('/web-ecms/danhmuc/dongthietbi/kiemtra-trung-ip', {
                    dslam_id: this.dslam_id,
                    ip: this.dslam.ip,
                    ten_dslam: this.dslam.tenTb
                });
                if(rs.data.RESULT) {
                    check = false;
                    msg = 'Đã có thiết bị sử dụng IP/Tên này rồi, bạn hãy kiểm tra lại!'
                }
            }
            if(this.maTinh == 'HCM') {
                if(!this.dslam.system) {
                    check = false;
                    msg = 'System thiết bị không được để trống !\n'.concat(msg);
                }
                if(this.dslam.slotBras == null || this.dslam.slotBras === '') {
                    check = false;
                    msg = 'Slot Bras không được để trống !\n'.concat(msg);
                }
                if(this.dslam.portBras == null || this.dslam.portBras === '') {
                    check = false;
                    msg = 'Port Bras không được để trống !\n'.concat(msg);
                }
            }
            if((this.dslam.loaiTb_id == this.LOAI_TBI.GPON || this.dslam.loaiTb_id == this.LOAI_TBI.MANE) && this.dslam.cardBD == null) {
                check = false;
                msg = 'Chưa nhập vị trí bắt đầu khi lắp card Gpon/Mane!\n'.concat(msg);
            }
            if(!this.isThemMoi && this.dslam.loaiTb_id == this.LOAI_TBI.GPON && this.dslam.soSlot < this.dslam.soSlot_cu) {
                check = false;
                msg = `Số slot mới của thiết bị Gpon phải >= ${this.dslam.soSlot_cu} (số slot cũ) !\n`.concat(msg);
            }
            if((this.dslam.loaiTb_id == this.LOAI_TBI.GPON || this.dslam.loaiTb_id == this.LOAI_TBI.MANE) && this.dslam.soSlot == null) {
                check = false;
                msg = 'Chưa nhập số slot cho Gpon/Mane!\n'.concat(msg);
            }
            if(this.dslam.loaiTb_id == this.LOAI_TBI.SWITCH && this.chk_subSlot) {
                if(this.dslam.soSlot == null) {
                    check = false;
                    msg = 'Switch có subslot, bạn phải nhập số slot của switch !\n'.concat(msg);
                }
                if(this.dslam.cardBD == null) {
                    check = false;
                    msg = 'Switch có subslot, bạn phải nhập vị trí bắt đầu lắp card của switch !\n'.concat(msg);
                }
                if(this.dslam.subSlot == null) {
                    check = false;
                    msg = 'Bạn chưa nhập subslot của switch !\n'.concat(msg);
                }
            }
            if(this.dslam.loaiTb_id == this.LOAI_TBI.SWITCH && !this.chk_subSlot) {
                if(this.dslam.soPort == null && this.isEnableSoPort) {
                    check = false;
                    msg = 'Chưa nhập số port cho switch!\n'.concat(msg);
                }
                if(this.dslam.portBD == null && this.isEnablePortBD) {
                    check = false;
                    msg = 'Chưa nhập port bắt đầu cho switch!\n'.concat(msg);
                }
            }
            if(this.chk_dslamVDC) {
                if(this.dslam.tbVDC == null) {
                    check = false;
                    msg = 'Chưa nhập ID thiết bị (lấy từ VDC)\n'.concat(msg);
                }
            }
            else {
                if(!this.dslam.bras_id && !this.dslam.tbCha_id) {
                    check = false;
                    msg = 'Chưa chọn thiêt bị cha!\n'.concat(msg);
                }
            }
            if(!this.dslam.bras_id) {
                check = false;
                msg = 'Chưa chọn Bras !\n'.concat(msg);
            }
            if(!this.dslam.hangsx_id) {
                check = false;
                msg = 'Chưa chọn hãng sản xuất !\n'.concat(msg);
            }
            if(this.dslam.rack_id.length == 0) {
                check = false;
                msg = 'Chưa chọn tủ rack !\n'.concat(msg);
            }
            if(!this.dslam.tenTb) {
                check = false;
                msg = 'Tên thiết bị không được để trống !\n'.concat(msg);
            }
            if(this.enableCheckL2SW) {
                if(this.dslam.loaiTb_id  == null) {
                    check = false;
                    msg = 'Loại thiết bị không được để trống !\n'.concat(msg);
                }
                if(this.dslam.kieuDslam_id == null) {
                    check = false;
                    msg = 'Kiểu thiết bị không được để trống !\n'.concat(msg);
                }
                if(this.dslam.ip == null) {
                    check = false;
                    msg = 'IP thiết bị không được để trống !\n'.concat(msg);
                }
            }
            if(!check) {
                this.$root.toastError(msg);
            }
            return check;
        },
        taoDuLieuDslam: function() {
            return {
                dslam_id: this.chk_dslamVDC ? this.dslam.tbVDC : this.dslam_id,
                tendslam: this.dslam.tenTb,
                bras_id: this.dslam.bras_id,
                thamso: this.dslam.thamSo,
                donvi_id: this.dslam.nhaTram_id,
                dslam_cha_id: this.dslam.tbCha_id,
                ip: this.dslam.ip,
                system: this.dslam.system,
                svlan: this.dslam.svlan ? this.dslam.svlan : 0,
                loai_dslam_id: this.dslam.loaiDslam_id,
                card_bd: this.dslam.cardBD,
                vci_bd: this.dslam.vciBD ? this.dslam.vciBD : 0,
                sttOlt: this.dslam.sttOlt,
                ghichu: this.dslam.ghiChu,
                dslam_vdc_id: this.dslam.tbVDC,
                kieudslam_id: this.dslam.kieuDslam_id,
                loaitbi_id: this.dslam.loaiTb_id,
                hangsx_id: this.dslam.hangsx_id,
                so_port: this.dslam.soPort,
                so_slot: this.dslam.soSlot,
                port_bd: this.dslam.portBD,
                vlanmytv: this.dslam.vlanMyTv,
                vlan_int: this.dslam.vlanInt,
                vlan_gnms: this.dslam.vlanGNMS,
                vlan_ims: this.dslam.vlanIms,
                slot_bras: this.dslam.slotBras,
                port_bras: this.dslam.portBras,
                dothu: this.chk_dothu ? 1 : 0,
                toanha_id: this.chk_toanha ? this.dslam.toanha_id : null,
                doitac_id: this.chk_doitac ? this.dslam.doitac_id : null,
                sub_slot: this.chk_subSlot ? this.dslam.subSlot : null,
                mdf_bd: this.dslam.MDFBD,
                thongtin_ae: this.dslam.thongTinAE,
                tu_dslam: this.dslam.rack_id.join(',')
            }
        },
        themMoi: function() {
            this.clearForm();
            this.isThemMoi = true;
        },
        ghiLai: async function() {
            var kq = await this.checkForm();
            if(kq) {
                if (this.enableCheckL2SW && !this.chk_doitac && this.title.toUpperCase() == 'SWITCH') {
                    var kqL2SW;
                    try {
                        this.loading(true);
                        kqL2SW = await this.$root.context.post('/tichhop/rims/checkExists', {
                            managerIp: this.dslam.ip,
                            systemName: this.dslam.system,
                            productCode: this.dsKieuDslam.find((item) => item.KIEUDSLAM_ID == this.dslam.kieuDslam_id).KIEU_DSLAM
                        }, { timeout: 20000 }); //lớn hơn 20s thì catch
                    } catch (error) {
                        if (error.code === 'ECONNABORTED') {
                            this.$root.toastError('Hệ thống đang bận. Vui lòng thử lại sau hoặc liên hệ Admin');
                        }
                        return;
                    } finally {
                        this.loading(false);
                    }
                    if (kqL2SW.error_code != 'BSS-00000000') {
                        this.$root.toastError(
                           kqL2SW.message ?? 'Xảy ra lỗi trong quá trình kiểm tra!'
                        );
                        return;
                    }
                    if (!kqL2SW.data || kqL2SW.data == false) {
                        this.$root.toastError(
                           kqL2SW.data.message ?? 'Thông tin khai báo chưa chính xác. Vui lòng kiểm tra và đồng nhất dữ liệu với hệ thống RIMS trước khi thực hiện'
                        );
                        return;
                    }
                }
                this.currentItem = {}
                if(this.isThemMoi) {
                    try {
                        this.loading(true);
                        var rs = await this.$root.context.post('/web-ecms/danhmuc/dongthietbi/them_dslam', this.taoDuLieuDslam());
                        if(rs.data.dslam) {
                            let dsPort = [];
                            if(this.dslam.loaiTb_id == 2 && rs.data.port) {
                                dsPort = rs.data.port.map(v => {
                                    return {
                                        ID: v.PORTVL_ID,
                                        viTri: v.VITRI,
                                        trangThai: v.TT_PORT_ID
                                    }
                                });
                            }
                            this.isThemMoi = false;
                            this.currentItem = {
                                dslam_id: rs.data.dslam[0].DSLAM_ID,
                                ten_dslam: this.dslam.tenTb,
                                turack_id: rs.data.dslam[0].TURACK_ID.split(','),
                                so_port: this.dslam.soPort,
                                so_slot: this.dslam.soSlot,
                                port_bd: this.dslam.portBD,
                                slot_bd: this.dslam.cardBD,
                                dsPort
                            }
                            this.$root.toastSuccess("Thêm mới thành công");
                            this.dslam_id = rs.data.DSLAM_ID
                            return true;
                        }
                    } catch (error) {
                        this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail: 'Thêm mới thất bại');
                    } finally {
                        this.loading(false);
                    }
                }
                else {
                    try {
                        this.loading(true);
                        var rs = await this.$root.context.post('/web-ecms/danhmuc/dongthietbi/capnhat_dslam', this.taoDuLieuDslam());
                        if(rs.data.result) {
                            this.$root.toastSuccess("Cập nhật thành công");
                            this.currentItem = {
                                dslam_id: this.dslam_id,
                                ten_dslam: this.dslam.tenTb,
                                turack_id: this.dslam.rack_id,
                                so_port: this.dslam.soPort,
                                so_slot: this.dslam.soSlot,
                                port_bd: this.dslam.portBD,
                                slot_bd: this.dslam.cardBD
                            }
                            return true;
                        }
                    } catch (error) {
                        this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : error.response.data.message);
                    } finally {
                        this.loading(false);
                    }
                }
            }
        },
        xoa: async function() {
            // this.$bvModal.msgBoxConfirm('Bạn chắc chắn muốn xóa?', {
            //     size: 'sm',
            //     okTitle: 'Đồng ý',
            //     cancelTitle: 'Hủy',
            // }).then(async v => {
            //     if(v) {

            //     }
            // }).catch(err => {
            //     this.$toast.error('Xóa thất bại');
            // })
             try {
                this.loading(true);
                var rs = await this.$root.context.post('/web-ecms/danhmuc/dongthietbi/xoa_dslam', {
                    dslam_id: this.dslam_id,
                    loaitbi_id: this.dslam.loaiTb_id
                });
                if(rs.data.result) {
                    this.$root.toastSuccess("Xóa dữ liệu thành công!");
                    return true;
                }
            } catch (error) {
                this.$root.toastError(error.response.data.message_detail);
            } finally {
                this.loading(false);
            }
        },
        loadDsLamSwitchGpon: async function() {
            try {
                this.loading(true);
                let turack = await this.getTuRack(this.turack_id);
                if(turack) {
                    this.dslam.nhaTram_id = turack.DONVI_ID;
                }

                var rsThamSo = await this.$root.context.post('/web-ecms/danhmuc/dongthietbi/lay_tham_so_md', {
                    kieu_id: 0,
                    ma_ts: "KIEMTRA_IP"
                })
                if(rsThamSo.data.length > 0) {
                    this.kiemTraIp = 1;
                }
                else {
                    this.kiemTraIp = 0;
                }
                await this.loadDsTuRack();
                await this.loadDsLoaiThietBi();
                await this.loadDsHangSanXuat();
                await this.loaddsLoaiDslam();
                await this.loadDsBras();
                await this.loaddsKieuDslam();
                if(this.dslam_id) {
                    var rsDslam = await this.$root.context.get(`/web-ecms/danhmuc/dongthietbi/thongtin_dslam/${this.dslam_id}`);
                    if( rsDslam.data.length > 0) {
                        this.ttdslam = rsDslam.data[0];
                        this.dslam.loaiTb_id = this.ttdslam.LOAITBI_ID;
                        if(this.dslam.loaiTb_id == this.LOAI_TBI.SWITCH) {
                            await this.loadDsPortTheoSwitch();
                        }
                        this.dslam = {
                            nhaTram_id : this.ttdslam.DONVI_ID,
                            rack_id : this.ttdslam.TURACK_ID.split(',').map(val => parseInt(val)),
                            loaiTb_id : this.ttdslam.LOAITBI_ID,
                            tbVDC : this.ttdslam.DSLAM_VDC_ID,
                            hangsx_id : this.ttdslam.HANGSX_ID,
                            bras_id : this.ttdslam.BRAS_ID,
                            ip : this.ttdslam.IP,
                            system : this.ttdslam.SYSTEM,
                            tenTb : this.ttdslam.TENDSLAM,
                            loaiDslam_id : this.ttdslam.LOAI_DSLAM_ID,
                            kieuDslam_id : this.ttdslam.KIEUDSLAM_ID,
                            tbCha_id : null,
                            thamSo : this.ttdslam.THAMSO,
                            ghiChu : this.ttdslam.GHICHU,
                            soSlot : this.ttdslam.SO_SLOT,
                            soSlot_cu : this.ttdslam.SO_SLOT ? this.ttdslam.SO_SLOT : 0,
                            soPort : this.ttdslam.SO_PORT,
                            portBD : this.ttdslam.PORT_BD,
                            vlanMyTv : this.ttdslam.VLANMYTV,
                            vlanInt : this.ttdslam.VLAN_INT,
                            vlanIms : this.ttdslam.VLAN_IMS,
                            cardBD : this.ttdslam.CARD_BD,
                            svlan : parseInt(this.ttdslam.SVLAN),
                            vlanGNMS : this.ttdslam.VLAN_GNMS,
                            vciBD : this.ttdslam.VCI_BD,
                            sttOlt : this.ttdslam.STT_OLT,
                            subSlot : this.ttdslam.SUB_SLOT,
                            doThu : this.ttdslam.DOTHU,
                            doitac_id : this.ttdslam.DOITAC_ID,
                            toanha_id : this.ttdslam.TOANHA_ID,
                            slotBras : this.ttdslam.SLOT_BRAS,
                            portBras : this.ttdslam.PORT_BRAS,
                            MDFBD : this.ttdslam.MDF_BD,
                            thongTinAE : this.ttdslam.THONGTIN_AE
                        }
                        if(this.dslam.subSlot != null && this.dslam.subSlot !== '') {
                            this.chk_subSlot = true;
                        }
                        this.chk_dothu = this.dslam.doThu ? true : false;
                        this.chk_doitac = this.dslam.doitac_id != null ? true : false;
                        this.chk_toanha = this.dslam.toanha_id != null  ? true : false;
                        this.isEnableBras = false;
                        this.isEnableCardBD = false;
                        this.isEnableSoPort = false;
                        this.isEnablePortBD = false;
                    }
                }
            } catch (error) {

            } finally {
                this.loading(false);
            }
        },
        resetData: function() {
            this.dslam = {
                nhaTram_id: 0,
                loaiTb_id: null,
                tbVDC: null,
                hangsx_id: null,
                bras_id: null,
                ip: null,
                system: null,
                tenTb: null,
                loaiDslam_id: null,
                kieuDslam_id: null,
                tbCha_id: null,
                thamSo: null,
                ghiChu: null,
                soSlot: null,
                soSlot_cu: 0,
                soPort: null,
                portBD: null,
                vlanMyTv: 0,
                vlanInt: 0,
                vlanIms: 0,
                cardBD: null,
                svlan: 0,
                vlanGNMS: 0,
                vciBD: 0,
                sttOlt: null,
                subSlot: 0,
                doThu: null,
                doitac_id: null,
                toanha_id: null,
                slotBras: null,
                portBras: null,
                MDFBD: 0,
                thongTinAE: null,
                rack_id: [],
            },
            this.chk_toanha = false;
            this.chk_doitac = false;
            this.chk_subSlot = false;
            this.chk_dothu = true;
            this.chk_dslamVDC = false;
        },
        setTuRack_id: async function(dataObj) {
            this.resetData();
            this.title = dataObj.itemType;
            this.ttdslam = null;
            this.turack_id = dataObj.turack_id;
            this.dslam.rack_id = [parseInt(dataObj.turack_id)];
            this.dslam_id = null;
            this.dsPortTheoSwtich = [];
            this.isLoading = false;
            this.isThemMoi = true;
            await this.loadDsLamSwitchGpon();
            this.dslam.loaiTb_id = dataObj.loaiTb_id;
            this.dslam.ip = null;
            this.dslam.system = null;
            this.dslam.tenTb = null;
            this.dslam.thamSo = null;
            this.dslam.ghiChu = null;
            this.dslam.thongTinAE = null;
            this.isEnableBras = true;
        },
        bindData: async function(data) {
            try {
                this.loading(true);
                this.resetData();
                this.isLoading = true;
                this.isThemMoi = false;
                this.title = data.itemType;
                this.dslam_id = data.itemID;
                this.turack_id = data.turack_id;
                this.dslam.loaiTb_id = data.loaiTb_id;
                this.dsPortTheoSwtich = [];
                await this.loadDsLamSwitchGpon();
            } catch (error) {

            } finally {
                this.loading(false);
            }
        },
        checkEnableCheckL2SW: async function () {
            let result = await this.$root.context.get(
                "/web-ecms/quanlythietbi/kiemTraTinhNangThietBiL2SW/"
                );
            if (result.data[0].KETQUA == 1) {
                this.enableCheckL2SW = true;
            } else {
                this.enableCheckL2SW = false;
            }
        }
    },
    watch: {
        chk_doitac: function(val) {
            try {
                this.loading(true);
                if(val) {
                    this.loadDsDoiTac();
                }
                else {
                    this.dslam.doitac_id = null;
                }
            } catch (error) {

            } finally {
                this.loading(false);
            }
        },
        chk_toanha: function(val) {
            if(val) {
                try {
                    this.loading(true);
                    this.loadDsToaNha();
                } catch (error) {

                } finally {
                    this.loading(false);
                }
            }
        },
        chk_dslamVDC: function(val) {
            this.isEnableTbVDC = val;
        },
        chk_subSlot: function(val) {
            this.isEnableCardBD = val;
            this.isEnableSoSlot = val;
            this.isEnableSubSlot = val;
            this.isEnableSoPort = !val;
            this.isEnablePortBD = !val;
            if(val) {
                this.lb_card_slot = 'Slot BD';
            }
            else {
                this.lb_card_slot = 'Card BD';
            }
        },
        'dslam.loaiTb_id': function(val) {
            if(val == this.LOAI_TBI.DSLAM) {
                this.isEnableCardBD = false;
                this.isEnableSoSlot = false;
                this.isEnableSoPort = false;
                this.isEnablePortBD = false;
                this.isEnableSubSlot = false;
                this.isEnableChk_subSlot = false;
                this.dslam.soSlot = null;
                this.dslam.soPort = null;
                this.dslam.portBD = null;
            }
            else if(val == this.LOAI_TBI.SWITCH) {
                this.isEnableCardBD = false;
                this.isEnableSoSlot = false;
                this.isEnableSoPort = true;
                this.isEnablePortBD = true;
                this.isEnableSubSlot = false;
                this.isEnableChk_subSlot = true;
                this.chk_subSlot = false;
                this.dslam.soSlot = null;
                this.dslam.portBD = null;
            }
            else if(val == this.LOAI_TBI.GPON || val == this.LOAI_TBI.MANE) {
                this.isEnableCardBD = true;
                this.isEnableSoSlot = true;
                this.isEnableSoPort = false;
                this.isEnablePortBD = false;
                this.isEnableSubSlot = false;
                this.isEnableChk_subSlot = false;
                this.dslam.soPort = null;
            }
        },
        'dslam.loaiDslam_id': function(val) {
            if(val) {
                var obj = this.dsLoaiDslam.find(v => v.LOAI_DSLAM_ID == val);
                if(obj && obj.THAMSO) {
                    this.dslam.thamSo = obj.THAMSO;
                }
            }
        },
        'dslam.tbCha_id': function(val) {
            if(val) {
                var obj = this.dsDslamCha.find(v => v.DSLAM_ID == val);
                if(obj) {
                    this.dslam.tbVDC = obj.DSLAM_VDC_ID;
                }
                else {
                    this.dslam.tbVDC = null;
                }
            }
            else {
                this.dslam.tbVDC = null;
            }
        },
        'dslam.bras_id': async function(val) {
            if(val) {
                var obj = this.dsBras.find(v => v.BRAS_ID == val);
                await this.loadDsDslamCha();
                if(this.isLoading) {
                    this.dslam.tbCha_id = this.ttdslam.DSLAM_CHA_ID;
                    this.isLoading = false;
                }
                else {
                    this.dslam.slotBras = obj ? obj.SLOT_VISA : null;
                    this.dslam.portBras = obj ? obj.PORT_VISA : null;
                }
            }
        },

    },
    created: function() {
    },
    mounted: async function() {
        try {
            let vm = this;
            await this.checkEnableCheckL2SW();
            EventBus.$on('thongTinDslam', async data => {
                this.dslam_id = data.dslam_id;
                this.turack_id = data.turack_id;
                this.dslam.nhaTram_id = data.nhatram_id;
                this.dslam.loaiTb_id = data.loaitbi_id;
                await this.loadDsLamSwitchGpon();
            })

            EventBus.$on('themMoi', () => {
                vm.clearForm();
                vm.isThemMoi = true;
            })

            EventBus.$on('ghiLai', () => {
                vm.ghiLai();
            })

            EventBus.$on('xoa', () => {
                vm.xoa();
            })

            EventBus.$on('huyBo', () => {
                vm.clearForm();
            })
        } catch (error) {

        }
    },
    destroyed() {
        EventBus.$off("themMoi");
        EventBus.$off("ghiLai");
        EventBus.$off("huyBo");
        EventBus.$off("xoa");
    }
}
</script>

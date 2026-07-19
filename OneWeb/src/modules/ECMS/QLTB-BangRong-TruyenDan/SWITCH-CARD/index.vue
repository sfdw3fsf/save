<template src="./index.html">
</template>

<script>
export default {
    props: {
        
    },
    data: function() {
        return {
            dslam_id: 0,
            cardsw_id: 0,
            vitri: 0,
            slot: 0,
            dsNhomCard: [],
            dsLoaiCard: [],
            dsThietBi: [],
            cbo_nhomCard_id: null,
            cbo_loaiCard_id: null,
            txtSlot: null,
            txt_soPort: null,
            txt_serial: null,
            txt_serial_cu: null,
            txt_soport_cu: null,
            txt_tenCard: null,
            txt_partNumber: null,
            txt_dangSD: null,
            isThemMoi: false,
            currentItem: {},
            isLoading: false,
            dsTinhTaoPort: ['CTO', 'HNI']
        }
    },
    methods: {
        HT_NHOM_CARD_THEO_LOAITBI: async function() {
            try {
                let rs = await this.$root.context.get('/web-ecms/danhmuc/dslam-card/get-nhomcard-by-loaitb_id/40');
                this.dsNhomCard = rs.data;
                if(!this.isLoading) {
                    this.cbo_nhomCard_id = this.dsNhomCard.length > 0 ? this.dsNhomCard[0].NHOMCARD_ID : null;
                }
            } catch (error) {

            }
        },
        HT_LOAI_CARD: async function() {
            try {
                let rs = await this.$root.context.get(`/web-ecms/danhmuc/dslam-card/get-loaicard-by-nhomcardid?nhomcard_id=${this.cbo_nhomCard_id}&loaitb_id=40`);
                this.dsLoaiCard = rs.data;
                if(!this.isLoading) {
                    this.cbo_loaiCard_id = this.dsLoaiCard.length > 0 ? this.dsLoaiCard[0].LOAICARD_ID : null;
                }
            } catch (error) {

            }
        },
        loadLoaiCard: async function() {
            try {
                let rs = await this.$root.context.get(`/web-ecms/danhmuc/dslam-card/get-loaicard-by-id/${this.cbo_loaiCard_id}`);
                return rs.data;
            } catch (error) {

            }
        },
        loadSwitchCard: async function(data) {
            try {
                // let rs = await this.$root.context.get(`/web-ecms/thietbi-truyendan/switch-card-theo-id/${this.cardsw_id}`);
                if(data.CARDSW_ID) {
                    this.cbo_nhomCard_id = data.NHOMCARD_ID;
                    this.cbo_loaiCard_id = data.LOAICARD_ID;
                    this.txt_soPort = data.SO_PORT;
                    this.txt_serial = data.SERIAL;
                    this.txt_serial_cu = data.SERIAL;
                    this.txt_soport_cu = data.SO_PORT;
                    this.txt_tenCard = data.TEN_CARD;
                    this.txt_partNumber = data.PARTNUMBER;
                    this.vitri = data.VITRI;
                    let rsDangSD = await this.$root.context.get(`/web-ecms/card-switch/port-switch-card-sd/${this.cardsw_id}`);
                    this.txt_dangSD = rsDangSD.data ? rsDangSD.data.RESULT : null;
                    let rsThietBi = await this.$root.context.get(`/web-ecms/card-switch/ds-thietbi-theo-switch-card/${this.cardsw_id}`);
                    this.dsThietBi = rsThietBi.data;
                }
            } catch (error) {
                
            }
        },
        selectedItemsPort: function(data) {

        },
        clearForm: function() {
            this.txt_tenCard = null;
            this.txt_serial = null;
            this.txt_serial_cu = null;
            this.txt_partNumber = null;
        },
        checkForm: function() {
            let check = true;
            let err = '';

            if(this.txt_soPort == null || this.txt_soPort === '') {
                err = 'Card này chưa được thiết lập số port !\n'.concat(err);
                check = false;
            }
            if(!this.cbo_loaiCard_id) {
                err = 'Chưa chọn loại card!\n'.concat(err);
                check = false;
            }
            if(!this.cbo_nhomCard_id) {
                err = 'Chưa chọn nhóm card!\n'.concat(err);
                check = false;
            }

            if(!this.txt_tenCard) {
                err = 'Chưa nhập tên card!\n'.concat(err);
                check = false;
            }

            if(!check) {
                this.$root.toastError(err);
            }
            return check;
        },
        taoDuLieuSwitchCard: function() {
            return {
                switch_id: this.dslam_id,
                loaicard_id: this.cbo_loaiCard_id,
                slot: this.slot,
                vitri: this.vitri,
                ten_card: this.txt_tenCard,
                serial: this.txt_serial,
                partnumber: this.txt_partNumber
            }
        },
        themMoi: function() {
            this.clearForm();
            this.isThemMoi = true;
        },
        ghiLai: async function() {
            let kq = this.checkForm();
            if(kq) {
                if(this.isThemMoi) {
                    try {
                        this.loading(true);
                        let obj = this.taoDuLieuSwitchCard();
                        let rs = await this.$root.context.post('/web-ecms/card-switch/them', obj);
                        if(rs.data) {
                            let maTinh = this.$root.token.getMaTinh();
                            await this.$root.context.post('/web-ecms/card-switch/tao-port-card-switch', {
                                cardsw_id: rs.data.CARDSW_ID,
                                istaoportlg: this.dsTinhTaoPort.includes(maTinh)
                            });
                            this.$root.toastSuccess("Thêm mới card switch thành công");
                            this.isThemMoi = false;
                            this.currentItem = {
                                cardsw_id: rs.data.CARDSW_ID
                            }
                            return true;
                        }
                    } catch (error) {
                        this.$root.toastError("Thêm mới thất bại");
                    } finally {
                        this.loading(false);
                    }
                }
                else {
                    try {
                        this.loading(true);
                        let obj = this.taoDuLieuSwitchCard();
                        obj.cardsw_id = this.cardsw_id;
                        let rs = await this.$root.context.post('/web-ecms/card-switch/capnhat', obj);
                        if(rs.data.result) {
                            let maTinh = this.$root.token.getMaTinh();
                            await this.$root.context.post('/web-ecms/card-switch/tao-port-card-switch', {
                                cardsw_id: this.cardsw_id,
                                istaoportlg: this.dsTinhTaoPort.includes(maTinh)
                            });
                            this.$root.toastSuccess("Cập nhật card switch thành công");
                            this.currentItem = {
                                cardsw_id: this.cardsw_id
                            }
                            return true;
                        }
                    } catch (error) {
                        this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : "Cập nhật thất bại");
                    } finally {
                        this.loading(false);
                    }
                }
            }
        },
        huyBo: function() {
            this.clearForm();
        },
        xoa: async function() {
            try {
                this.loading(true);
                var rs = await this.$root.context.post(`/web-ecms/card-switch/xoa/${this.cardsw_id}`);
                if(rs.data.result) {
                    this.$root.toastSuccess("Xóa dữ liệu thành công!");
                    return true;
                }
            } catch (error) {
                this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : "Xóa thất bại !");
            } finally {
                this.loading(false);
            }
        },
        bindData: async function(args) {
            try {
                this.loading(true);
                this.isLoading = true;
                await this.HT_NHOM_CARD_THEO_LOAITBI();
                this.dsThietBi = [];
                this.cardsw_id = args.itemID;
                this.dslam_id = args.dslam_id;
                this.vitri = args.vitri;
                this.slot = args.slot;
                if(this.cardsw_id != 0) {
                    this.isThemMoi = false;
                    await this.loadSwitchCard(args.data);
                    this.isLoading = false;
                }
                else {
                    this.isLoading = false;
                    this.isThemMoi = true;
                    this.clearForm();
                    this.cbo_nhomCard_id = this.dsNhomCard.length > 0 ? this.dsNhomCard[0].NHOMCARD_ID : null;
                }
                if(this.vitri != -1) {
                    this.txtSlot = `Slot #${this.slot} - SubSlot #${this.vitri}`
                }
                else {
                    this.txtSlot = `Slot #${this.slot}`;
                }
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        }
    },
    watch: {
        cbo_nhomCard_id: function(val) {
            if(val) {
                this.HT_LOAI_CARD();
            }
            else {
                this.cbo_loaiCard_id = null;
                this.dsLoaiCard = [];
            }
        },
        cbo_loaiCard_id: async function(val) {
            if(val) {
                let rs = await this.loadLoaiCard();
                if(rs) {
                    this.txt_soport_cu = this.txt_soPort;
                    this.txt_soPort = rs.SO_PORT;
                }
            }
            else {
                this.txt_soport_cu = null;
                this.txt_soPort = null;
            }
        }
    }
}
</script>

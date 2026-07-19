<template src="./index.html">
</template>

<script>
export default {
    data: function() {
        return {
            cardbras_id: 0,
            bras_id: 0,
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
            isLoading: false
        }
    },
    methods: {
        HT_NHOM_CARD_THEO_LOAITBI: async function() {
            try {
                let rs = await this.$root.context.get('/web-ecms/danhmuc/dslam-card/get-nhomcard-by-loaitb_id/24');
                this.dsNhomCard = rs.data;
                if(!this.isLoading) {
                    this.cbo_nhomCard_id = this.dsNhomCard.length > 0 ? this.dsNhomCard[0].NHOMCARD_ID : null;
                }
            } catch (error) {

            }
        },
        HT_LOAI_CARD: async function() {
            try {
                let rs = await this.$root.context.get(`/web-ecms/danhmuc/dslam-card/get-loaicard-by-nhomcardid?nhomcard_id=${this.cbo_nhomCard_id}&loaitb_id=24`);
                this.dsLoaiCard = rs.data;
                if(!this.isLoading) {
                    this.cbo_loaiCard_id = this.dsLoaiCard.length > 0 ? this.dsLoaiCard[0].LOAICARD_ID : null;
                }
            } catch (error) {

            }
        },
        loadBrasCard: async function(data) {
            try {
                if(data.CARDBRAS_ID) {
                    this.cbo_nhomCard_id = data.NHOMCARD_ID;
                    this.cbo_loaiCard_id = data.NHOMCARD_ID;
                    this.txt_soPort = data.SO_PORT;
                    this.txt_serial = data.SERIAL;
                    this.txt_serial_cu = data.SERIAL;
                    this.txt_soport_cu = data.SO_PORT;
                    this.txt_tenCard = data.TEN_CARD;
                    this.vitri = data.VITRI;
                    let rsDangSD = await this.$root.context.get(`/web-ecms/thietbi-truyendan/port-bras-card-sd/${this.cardbras_id}`);
                    this.txt_dangSD = rsDangSD.data ? rsDangSD.data.RESULT : null;
                    let rsThietBi = await this.$root.context.get(`/web-ecms/thietbi-truyendan/ds-thietbi-theo-bras-card/${this.cardbras_id}`);
                    this.dsThietBi = rsThietBi.data;
                }
            } catch (error) {
                
            }
        },
        clearForm: function() {

        },
        selectedItemsPort: function() {

        },
        bindData: async function(args) {
            try {
                this.loading(true);
                this.isLoading = true;
                this.cardbras_id = args.itemID;
                this.bras_id = args.bras_id;
                this.vitri = args.vitri;
                this.slot = args.slot;
                await this.HT_NHOM_CARD_THEO_LOAITBI();
                if(this.vitri != -1) {
                    this.txtSlot = `Slot #${this.slot} - SubSlot #${this.vitri}`
                }
                else {
                    this.txtSlot = `Slot #${this.slot}`;
                }
                if(this.cardbras_id != 0) {
                    this.isThemMoi = false;
                    await this.loadBrasCard(args.data);
                    this.isLoading = false;
                }
                else {
                    this.isLoading = false;
                    this.isThemMoi = true;
                    this.clearForm();
                    this.cbo_nhomCard_id = this.dsNhomCard.length > 0 ? this.dsNhomCard[0].NHOMCARD_ID : null;
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

<template src="./index.html"></template>
<script>
export default {
    data() {
        return {
            resolvePromise: null,
            kieu: 0,
            hdkh_id: 0,         // 141569
            khachhang_id: 0,    // 52
            kieuld_id: 0,       // 206
            loaitb_id: 0,       // 1
            thueBaoDuocChon: null,
            gridThueBao: {
                DataSource: []
            },
            tsbtnChapNhan: {
                Enabled: false
            },
            KIEULAP: {
                LAP_KEM: 1
            },
            KieuLapDat: {
                TACH_VLAN: 581
            }
        }
    },
    methods: {
        show() {
            this.$refs['ref-modal'].show()
            return new Promise((resolve, reject) => {
                this.resolvePromise = resolve
            })
        },
        hide(data) {
            this.resolvePromise(!data || !data.chapnhan ? { chapnhan: false } : data)
            this.$refs['ref-modal'].hide()
        },
        onModalHidden() {
            this.gridThueBao.DataSource = []
            this.tsbtnChapNhan.Enabled = false
            this.resolvePromise = null
            this.hdkh_id = 0
            this.khachhang_id = 0
            this.kieuld_id = 0
            this.loaitb_id = 0
            this.kieu = 0
            this.thueBaoDuocChon = null
        },
        onModalShown() {
            this.frmDSTBLapKem_Load()
        },
        async frmDSTBLapKem_Load() {

            if (this.kieu == 3) {
                this.gridThueBao.DataSource = await this.lay_ds_somay_tn_theo_hdkhid_v2({
                    hdkh_id: this.hdkh_id,
                    khachhang_id: this.khachhang_id,
                    kieuld_id: this.kieuld_id
                })
            } else if (this.kieu == 5) {
                this.gridThueBao.DataSource = await this.lay_ds_somay_tn_theo_hdkhid_v3({
                    hdkh_id: this.hdkh_id,
                    khachhang_id: this.khachhang_id,
                    kieuld_id: this.kieuld_id
                })
            } else {
                this.gridThueBao.DataSource = await this.lay_ds_somay_tn_theo_hdkhid({
                    hdkh_id: this.hdkh_id,
                    kieuld_id: this.kieuld_id
                })
            }
        },
        onSelectedRowChanged(data) {
            this.thueBaoDuocChon = data
            this.tsbtnChapNhan.Enabled = !!data
        },
        async onClickBtnChapNhan() {

            if (!this.tsbtnChapNhan.Enabled) {
                return
            }

            if (!this.thueBaoDuocChon) {
                this.$root.toastError("Hãy chọn mã thuê bao!")
                return
            }

            let kiemtra_matb_truynhap = await this.kiemtra_matb_truynhap({
                madoicap: this.thueBaoDuocChon.madoicap,
                loaitb_id: this.loaitb_id,
                kieu: this.KIEULAP.LAP_KEM
            })

            if (kiemtra_matb_truynhap == 0) {
                this.hide({
                    chapnhan: true,
                    ma_tn: this.thueBaoDuocChon.ma_tb,
                    ma_doicap: this.thueBaoDuocChon.madoicap,
                })
                return
            }
            
            let thongbao = "Mã thuê bao " + this.thueBaoDuocChon["ma_tb"] + " đang có cùng đôi cáp với thuê bao khác. Bạn có muốn chọn không?"

            let confirmation = await this.$bvModal.msgBoxConfirm(thongbao, {
                title: 'Thông báo',
                size: 'sm',
                okTitle: 'Đồng ý',
                cancelTitle: 'Hủy',
                footerClass: 'p-2',
                hideHeaderClose: false,
                centered: true
            })

            if (confirmation) {
                this.hide({
                    chapnhan: true,
                    ma_tn: this.thueBaoDuocChon.ma_tb,
                    ma_doicap: this.thueBaoDuocChon.madoicap,
                })
            }
        },
        async kiemtra_matb_truynhap(data) {
            try {
                let response = await this.$root.context.post(`/web-hopdong/chuyendich-codinh/kiemtra_matb_truynhap`, data)
                return response.data.kq
            } catch (err) {
                return null
            }
        },
        async lay_ds_somay_tn_theo_hdkhid_v2(data) {
            try {
                let response = await this.$root.context.post(`/web-hopdong/chuyendich-codinh/lay_ds_somay_tn_theo_hdkhid_v2`, data)
                return response.data
            } catch (err) {
                return []
            }
        },
        async lay_ds_somay_tn_theo_hdkhid_v3(data) {
            try {
                let response = await this.$root.context.post(`/web-hopdong/chuyendich-codinh/lay_ds_somay_tn_theo_hdkhid_v3`, data)
                return response.data
            } catch (err) {
                return []
            }
        },
        async lay_ds_somay_tn_theo_hdkhid(data) {
            try {
                let response = await this.$root.context.post(`/web-hopdong/chuyendich-codinh/lay_ds_somay_tn_theo_hdkhid`, data)
                return response.data
            } catch (err) {
                return []
            }
        },
    }
}
</script>
<style>
    .frm-ds-thuebao-cung-doicap-module-hopdong .disable-btn:hover {
        background-color: white !important;
    }

    .frm-ds-thuebao-cung-doicap-module-hopdong .disable-btn a {
        color: #6c757d !important;
    }

    .frm-ds-thuebao-cung-doicap-module-hopdong .disable-btn a:hover {
        cursor: default;
        color: #6c757d;
    }
</style>
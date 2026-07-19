<template src="./index.html"></template>
<script>
export default {
    data() {
        return {
            resolvePromise: null,
            khachhang_id: 0,    // 52
            kieuld_id: 0,       // 26
            loaitb_id: 0,       // 1
            gridThueBao: {
                DataSource: []
            },
            thueBaoDuocChon: null,
            tsbtnChapNhan: {
                Enabled: false
            },
            lblThueBaoChon: {
                Text: "Thuê bao chọn:"
            },
            KIEULAP: {
                LAP_LINE_SAN: 2
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
            this.thueBaoDuocChon = null
            this.lblThueBaoChon.Text = "Thuê bao chọn:"
            this.tsbtnChapNhan.Enabled = false
            this.resolvePromise = null
            this.khachhang_id = 0
            this.kieuld_id = 0
            this.loaitb_id = 0
        },
        onModalShown() {
            this.frmDSTBCungDoiCap_Load()
        },
        async frmDSTBCungDoiCap_Load() {
            this.gridThueBao.DataSource = await this.lay_ds_tb_cungdoicap_theo_khid({
                khachhang_id: this.khachhang_id,
                kieuld_id: this.kieuld_id
            })
        },
        onSelectedRowChanged(data) {

            this.thueBaoDuocChon = data

            if (!data) {
                this.tsbtnChapNhan.Enabled = false
                this.lblThueBaoChon.Text = ""
            } else {
                this.tsbtnChapNhan.Enabled = true
                this.lblThueBaoChon.Text = data.ma_tb
            }
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
                kieu: this.KIEULAP.LAP_LINE_SAN
            })

            if (kiemtra_matb_truynhap == 0 || this.kieuld_id == this.KieuLapDat.TACH_VLAN) {
                
                if (this.thueBaoDuocChon.trangthaitb_id != 1) {
                    this.$root.toastError("Mã thuê bao " + this.thueBaoDuocChon.ma_tb + " đang ở trạng thái " + this.thueBaoDuocChon.trangthai_tb)
                }

                this.hide({
                    chapnhan: true,
                    ma_tn: this.thueBaoDuocChon.ma_tb,
                    ma_doicap: this.thueBaoDuocChon.madoicap,
                    loaitb_id: this.thueBaoDuocChon.loaitb_id,
                    thuebao_id: this.thueBaoDuocChon.thuebao_id
                })

                return
            }

            if (this.kieuld_id != this.KieuLapDat.TACH_VLAN) {

                let thongbao = "Mã thuê bao " + this.thueBaoDuocChon.ma_tb + " đang có cùng đôi cáp với thuê bao khác. Bạn có muốn chọn không?";

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
                        loaitb_id: this.thueBaoDuocChon.loaitb_id,
                        thuebao_id: this.thueBaoDuocChon.thuebao_id
                    })
                }
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
        async lay_ds_tb_cungdoicap_theo_khid(data) {
            try {
                let response = await this.$root.context.post(`/web-hopdong/chuyendich-codinh/lay_ds_tb_cungdoicap_theo_khid`, data)
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
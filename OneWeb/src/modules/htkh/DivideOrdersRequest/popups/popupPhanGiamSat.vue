<template src="./popupPhanGiamSat.html"></template>
<script>
import Select2Ext from "@/modules/contract/setup/DeclareLandline/components/Select2Ext";
    export default {
        components: {
            Select2Ext
        },
        data() {
            return {
                phieu_ids: [],
                dataSources: [],
                headers: [
                    { fieldName: 'ma_gd', headerText: 'Mã đơn hàng', allowFiltering: true, visible: true },
                    { fieldName: 'trangthai_hd', headerText: 'Kết quả xử lý đơn hàng', allowFiltering: true, visible: true },
                    { fieldName: 'ma_nv', headerText: 'Mã nhân viên', allowFiltering: true, visible: true },
                    { fieldName: 'ten_nv', headerText: 'Tên nhân viên', allowFiltering: true, visible: true },
                    { fieldName: 'ngay_giao', headerText: 'Ngày giao giám sát', allowFiltering: true, visible: true },
                    { fieldName: 'nhanvien_gs', headerText: 'Nhân viên giám sát', allowFiltering: true, visible: true },
                ]
            }
        },
        methods: {
            async tsbtnGhiLai_Click() {
                this.loading(true)
                return new Promise(async (resolve, reject) => {
                    try {
                        for(let i = 0; i < this.phieu_ids.length; i++) {
                            let res = await this.axios.post('web-hopdong/quanlydonhang/fn_phan_giamsat', {
                                "p_nhanvien_id": this.$root.token.getNhanVienID(),
                                "p_nhanvien_sc_id": this.$refs.cboNhanvien.value,
                                "p_phieu_id": this.phieu_ids[i].phieu_id
                            })

                            if(res.data.data == "OK")
                                this.$toast.success(`Phân giám sát ${this.phieu_ids[i].ma_gd} cho ${this.$refs.cboNhanvien.text} thành công!`)
                            else
                                this.$toast.error(`Phân giám sát ${this.phieu_ids[i].ma_gd} cho ${this.$refs.cboNhanvien.text} không thành công!` + res.data.message)
                        }
                        this.$refs.popupPhanGiamSat.hide()
                        await this.getDonHang()
                        this.$emit('success')
                    } catch (error) {
                        this.$toast.error("Lỗi lưu thông tin " + error.message)
                    }
                    resolve(true)
                })
                this.loading(false)
            },
            async showDialog() {
                this.loading(true)
                this.$refs.cboDonvi.dataTextField = "ten_dv"
                this.$refs.cboDonvi.dataValueField = "donvi_id"
                this.$refs.cboNhanvien.dataTextField = "ten_nv"
                this.$refs.cboNhanvien.dataValueField = "nhanvien_id"
                await this.getDonvi()
                await this.getNhanVien()
                await this.getDonHang()
                this.$refs.popupPhanGiamSat.show()
                this.loading(false)
            },
            async getDonHang() {
                return new Promise(async (resolve, reject) => {
                    try {
                        let res = await this.axios.post('web-hopdong/quanlydonhang/sp_lay_ds_phan_giamsat', {
                            p_nhanvien_id: this.$root.token.getNhanVienID()
                        })
                        this.dataSources = res.data.data
                    } catch (error) {
                        this.$toast.error("Lỗi lấy danh sách giám sát " + error.message)
                    }
                    resolve(true)
                })
            },
            async getNhanVien() {
                return new Promise(async (resolve, reject) => {
                    try {
                        let res = await this.axios.post('web-hopdong/quanlydonhang/sp_lay_nhanvien_sc', {
                            p_donvi_id: this.$refs.cboDonvi.value
                        })
                        this.$refs.cboNhanvien.dataSource = res.data.data
                        this.$refs.cboNhanvien.value = (res.data.data && res.data.data.length > 0) ? res.data.data[0].nhanvien_id : ""
                    } catch (error) {
                        this.$toast.error("Lỗi lấy danh sách đơn vị " + error.message)
                    }
                    resolve(true)
                })
            },
            async getDonvi() {
                return new Promise(async (resolve, reject) => {
                    try {
                        let res = await this.axios.get('/web-hopdong/quanlydonhang/sp_lay_donvi_sc')

                        this.$refs.cboDonvi.dataSource = res.data.data
                        this.$refs.cboDonvi.value = (res.data.data && res.data.data.length > 0) ? res.data.data[0].donvi_id : ""
                    } catch (error) {
                        this.$toast.error("Lỗi lấy danh sách đơn vị " + error.message)
                    }
                    resolve(true)
                })
            }
        }
    }
</script>

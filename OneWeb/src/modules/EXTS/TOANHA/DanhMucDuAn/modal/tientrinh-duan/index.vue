<template src="./index.html"></template>
<script>
import { required } from "vuelidate/lib/validators"
import moment from 'moment'
import ModalUploadFile from '../../../DanhMucHopDongDauTu/modal/ModalUploadFile.vue'
export default {
    props: {
        // duan_id: {
        //     type: Number,
        //     default: 0
        // },
        // ten_duan: {
        //     type: String,
        //     default: ""
        // }
    },
    components: {
        ModalUploadFile
    },
    data() {
        return {
            dtLoai_TT: [
                {
                    id: 0,
                    name: ""
                },
                {
                    id: 1,
                    name: "Thông tin cập nhật"
                },
                {
                    id: 2,
                    name: "Tiếp xúc làm việc"
                },
                {
                    id: 3,
                    name: "Khảo sát thực tế"
                },
                {
                    id: 4,
                    name: "Phương án kỹ thuật"
                },
                {
                    id: 5,
                    name: "Đàm phán hợp tác"
                },
                {
                    id: 6,
                    name: "Hợp đồng đã ký"
                }
            ],
            btnEnable: {
                nhapMoi: false,
                ghiLai: false,
                huy: false,
                xoa: false
            },
            duan_id: 0,
            ten_duan: "",
            ttda_id: 0,
            loai_tt_id: 0,
            noidung_th: "",
            ngay_th: "",
            ds_tientrinh: [],
            index: null
        }
    },
    validations: {
        loai_tt_id: {
            required
        },
        ngay_th: {
            required
        }
    },
    // async mounted() {
    //     await this.getTienTrinhDA(this.ds_tientrinh.id)
    //     if (this.$props.ds_tientrinh.items.length <= 0) {
    //         this.setButton(1)
    //     }
    // },
    methods: {
        async getTienTrinhDA(duan_id) {
            try {
                this.$root.loading(true)
                let response = await this.$root.context.get(`web-toanha/tientrinh-duan/tt_tientrinh/${duan_id}`)
                this.ds_tientrinh = []
                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.ds_tientrinh = response.data
                }
            } catch(err) {
                this.ds_tientrinh = []
            } finally {
                this.$root.loading(false)
            }
        },
        async capNhatTienTrinhDA(kieu) {
            try {
                this.$root.loading(true)
                let apiBody = {
                    vkieu: kieu,
                    vttda_id: this.ttda_id,
                    vduan_id: this.duan_id,
                    vloai_tt_id: this.loai_tt_id,
                    vnoidung_th: this.noidung_th,
                    vngay_th: this.ngay_th && moment(this.ngay_th).format("yyyy-MM-DD HH:mm:ss").toString()
                }
                let url =`web-toanha/tientrinh-duan/capnhat-tientrinh`
                let response = await this.$root.context.post(url, apiBody)
                if (response.error_code === 'BSS-00000000' && response.data !== undefined && response.data.includes("OK")) {
                    return true
                } else {
                    return false
                }
            } catch(err) {
                this.$root.toastError(err.message)
                return false
            } finally {
                this.$root.loading(false)
            }
        },
        clean() {
            this.loai_tt_id = 0
            this.noidung_th = ""
            this.ngay_th = null
        },
        setButton(kieu) {
            this.btnEnable.nhapMoi = false
            this.btnEnable.ghiLai = false
            this.btnEnable.huy = false
            this.btnEnable.xoa = false

            switch (kieu) {
                case -1: //Begin
                    this.btnEnable.ghiLai = true
                    this.btnEnable.huy = true
                    break
                case 1: //Insert
                    this.clean()
                    this.btnEnable.ghiLai = true
                    this.btnEnable.huy = true
                    break
                case 2: //Cancel
                    this.btnEnable.nhapMoi = true
                    this.clean()
                    break
                case 3: //Edit
                    this.btnEnable.nhapMoi = true
                    this.btnEnable.ghiLai = true
                    this.btnEnable.huy = true
                    this.btnEnable.xoa = true
                    break
            }
        },
        validateData() {
            this.$v.$touch();
            let errorMessage = [];
            let focusInput = false;

            if (this.loai_tt_id == 0) {
                errorMessage.push("Hãy chọn Loại tiến trình");
                if (!focusInput) {
                    this.$refs['loai_tt_id'].focus();
                    focusInput = true
                }
            }

            if (!this.$v.ngay_th.required) {
                errorMessage.push("Hãy nhập Ngày thực hiện");
                if (!focusInput) {
                    this.$refs['ngay_th'].focusIn();
                    focusInput = true
                }
            }

            if (errorMessage.length > 0) {
                this.$toast.error(errorMessage.join("\r\n"));
                return false;
            } else return true;
        },
        async onLayTT() {
            await this.getTienTrinhDA(this.duan_id)
            if (this.ds_tientrinh.length <= 0) {
                this.setButton(1)
            }
        },
        onNhapMoi() {
            this.setButton(1)
        },
        async onGhiLai() {
            if (this.validateData()) {
                if (this.btnEnable.nhapMoi) {
                    let response = await this.capNhatTienTrinhDA(2)
                    if (response) {
                        await this.getTienTrinhDA(this.duan_id)
                        this.$root.toastSuccess("Cập nhật thành công")
                    }
                } else {
                    let response = await this.capNhatTienTrinhDA(1)
                    if (response) {
                        await this.getTienTrinhDA(this.duan_id)
                        this.$root.toastSuccess("Nhập mới thành công")
                    }
                }
            }
        },
        onHuy() {
            if (this.ds_tientrinh.length > 0) {
                this.ttda_id = this.index.ttda_id
                this.loai_tt_id = this.index.loai_tt_id
                this.noidung_th = this.index.noidung_th
                this.ngay_th = this.index.ngay_th
                this.setButton(3)
            } else {
                this.setButton(1)
            }
            // this.setButton(2)
        },
        async onXoa() {
            try {
                await this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xoá dữ liệu không?', {
                    "title": 'Thông báo',
                    "size": 'md',
                    "buttonSize": 'md',
                    "okVariant": 'primary',
                    "okTitle": 'Đồng ý',
                    "cancelTitle": 'Không đồng ý',
                    "hideHeaderClose": false,
                    "centered": true,
                    "modal-class": ["f18","text-center"],
                }).then(async (result) => {
                    if (result) {
                        let response = await this.capNhatTienTrinhDA(3)
                        if (response) {
                            await this.getTienTrinhDA(this.duan_id)
                            if (this.ds_tientrinh.length == 0) {
                                this.clean()
                                this.setButton(1)
                            }
                            this.$root.toastSuccess("Xóa dữ liệu thành công")
                        }
                    }
                })
            } catch(err) {
                console.log(err.message)
                this.$root.toastError("Có lỗi khi xóa dữ liệu")
            }
        },
        onSelectedRowChanged: function(val, index) {
            if (this.ds_tientrinh.length > 0) {
                this.ttda_id = val.ttda_id
                this.loai_tt_id = val.loai_tt_id
                this.noidung_th = val.noidung_th
                this.ngay_th = val.ngay_th
                this.index = val
                this.setButton(3)
            }
        },
        gridViewControll_QueryCellInfo(args) {
            if (args.column.field === "ngay_th") {
                args.cell.innerText = moment(args.data.ngay_th).format("DD/MM/YYYY")
            }
            if (args.column.field === "ngay_cn") {
                args.cell.innerText = moment(args.data.ngay_cn).format("DD/MM/YYYY")
            }
        },
        onFileHS() {
            if (this.ds_tientrinh.length == 0) {
                this.ttda_id = 0
            } 
            this.$refs.popupUploadFile.vhopdong_id = this.ttda_id;
            this.$refs.popupUploadFile.vloai_hd = 2;
            this.$refs.popupUploadFile.showModal();
            // this.$refs.popupUploadFile.vServerName = 'HD_DAUTU';
        },
        async showModal() {
            // await this.getTienTrinhDA(this.duan_id)
            await this.onLayTT()
            this.$refs['tientrinh-duan'].show()
        },
        close() {
            this.clean()
            this.setButton(-1)
            this.$refs['tientrinh-duan'].hide()
        }
    }
}
</script>
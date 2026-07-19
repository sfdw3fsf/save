<template src="./index.html"></template>
<script>
import ModalCapNhat from './modal/index'
export default {
    components: {
        modalCapNhat: ModalCapNhat,
    },
    data() {
        return {
            dviChuQuanId: 1, //1: NET; 2:MEDIA; 3:DHIT
            dviChuQuan: "NET", //1: NET; 2:MEDIA; 3:DHIT
            dsHeThongThietBi: [],
            actionType: 0,
            capNhatDto: {
                hethong_id: 0,
                dvi_chuquan: null,
                ma_hethong: null,
                ten_thietbi: null,
                ten_hethong: null,
                linh_vuc: null,
                email_nv: null,
                so_dt_nv: null,
            },
            tag: null
        }
    },
    async mounted() {
        this.$root.loading(true)
        await this.loadForm()
        await this.getDanhSachHeThongThietBi(this.dviChuQuan)
        this.$root.loading(false)
    },
    methods: {
        async getTag(id){
            try {
                let response = await this.$root.context.post(`web-gqkn/xu-ly-khieu-nai/getTagValue`, {id: id})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.tag = response.data.TAGVALUE
                } 
            } catch (err) {
                
            } finally {

            }
        },
        async getDanhSachHeThongThietBi(dviChuQuan) {
            try {
                let response = await this.$root.context.get(`/web-suco/danhmuchethongthietbi/hethong-thietbi?dviChuQuan=${dviChuQuan}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsHeThongThietBi = response.data
                } 
            } catch (err) {
                this.dsHeThongThietBi = []
            } finally {

            }
        },
        async getChiTietHeThongDichVu(heThongId) {
            try {
                let response = await this.$root.context.get(`/web-suco/danhmuchethongthietbi/chitiet-hethong-dichvu?heThongId=${heThongId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.actionType = 1
                    this.capNhatDto.hethong_id = response.data[0].HETHONG_ID
                    this.capNhatDto.dvi_chuquan = response.data[0].DVI_CHUQUAN
                    this.capNhatDto.ma_hethong = response.data[0].MA_HETHONG
                    this.capNhatDto.ten_thietbi = response.data[0].TEN_THIETBI
                    this.capNhatDto.ten_hethong = response.data[0].TEN_HETHONG
                    this.capNhatDto.linh_vuc = response.data[0].LINH_VUC
                    this.capNhatDto.email_nv = response.data[0].EMAIL_NV
                    this.capNhatDto.so_dt_nv = response.data[0].SO_DT_NV
                } 
            } catch (err) {
                
            } finally {

            }
        },
        async xoaHeThongDichVu(heThongId) {
            try {
                this.loading(true)
                let response = await this.$root.context.post(`/web-suco/danhmuchethongthietbi/xoa-hethong-dichvu?heThongId=${heThongId}`)

                if (response.error_code === 'BSS-00000000') {
                    return true
                }
                return false
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        async loadForm() {
            // await this.getTag(37099)
            const tag = this.$route.query.tag

            let str = ""
            if (tag) {
                if (tag.toString() != "") {
                    str = tag.toString()
                    let words = str.split('+')
                    if (words.length >= 1) {
                        console.log("dviChuQuanId", parseInt(words[0]))
                        this.dviChuQuanId = parseInt(words[0])
                    }
                    if (this.dviChuQuanId == 1) {
                        this.dviChuQuan = 'NET'
                    } else if (this.dviChuQuanId == 2) {
                        this.dviChuQuan = 'MEDIA'
                    } else if (this.dviChuQuanId == 3) {
                        this.dviChuQuan = 'DHIT'
                    }else if (this.dviChuQuanId == 5) {
                        this.dviChuQuan = 'ATS'
                    }
                }
            }
        },
        onSelectedRowChanged: function (val, index) {
            this.capNhatDto.hethong_id = val.HETHONG_ID
        },
        async layDS() {
            this.$root.loading(true)
            await this.getDanhSachHeThongThietBi(this.dviChuQuan)
            this.$root.loading(false)  
        },
        async capNhat() {
            await this.getChiTietHeThongDichVu(this.capNhatDto.hethong_id)
            this.$bvModal.show("popup-capnhat")
        },
        nhapMoi() {
            this.actionType = 0
            this.capNhatDto.hethong_id = 0
            this.capNhatDto.dvi_chuquan= this.dviChuQuan
        },
        async xoa() {
            await this.$bvModal.msgBoxConfirm('Bạn chắc chắn muốn xóa?', {
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
                    let rs = await this.xoaHeThongDichVu(this.capNhatDto.hethong_id)
                    if (rs) {
                        this.$toast.success('Xóa dữ liệu thành công!')
                        await this.layDS()
                        await this.getDanhSachHeThongThietBi(this.dviChuQuan)
                    } else {
                        this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
                    }
                }
            })
        },
    }
}
</script>
<template src="./popupSegment.html"></template>
<script>
import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'
import Vue from "vue";
import { TabPlugin } from '@syncfusion/ej2-vue-navigations';
import schemaVPN from './schemaVPN'
import schemaVeTinh from './schemaVeTinh'
import schemaKTR from './schemaKTR'
import schemaTrungKe from './schemaTrungKe'
import schemaTrungKeIP from './schemaTrungKeIP'
import FormComponent from './formComponent.vue'

Vue.use(TabPlugin);

export default {
    components: {
        breadcrumb,
        FormComponent
    },
    computed: {
        textTrangThaiAn() {
            if (this.formData.trang_thai_an == 1) {
                return "Ẩn phân đoạn"
            } else if (this.formData.trang_thai_an == 0) {
                return "Hiện phân đoạn"
            } else {
                return ""
            }
        }
    },
    data() {
        return {
            loai_kl_id: 1,
            loai_kl: {
                1: 'Kênh VPN',
                2: 'Kênh KTR',
                3: 'Kênh Trung kế truyền dẫn',
                4: 'Kênh Trung kế IP',
                5: 'Kênh Vệ Tinh'
            },
            current_tab: 'domestic',
            panels: null,
            tabindex: '0',
            defaultTabIndex: 0,
            action: '',
            formData: {
                hdtb_id: 0,
                hdtb_ct_id: 0,
                phieu_id: 0,
                trang_thai_an: null,
                loai_phandoan: 0,
                loai_kl_id: 0
            },
            requireE: {},
            danhmuc: {},
            resizeHandles: ['All'],
            draggable: true,
            phanvung_id: 0,
            disable_button: false
        }
    },
    mounted() {
        // Gọi phương thức để đặt lại tab được kích hoạt khi cần thiết
        this.defaultTabIndex = this.formData.loai_phandoan
    },
    methods: {
        closePopup() {
            this.$emit('closePopup')
        },
        hideModal() {
            this.$refs['popupSegment'].hide()
        },
        async showDialog(formData, loai_kl_id, phanvung_id = this.$root.token.getPhanVungID(),action) {
            this.loading(true)
            
            this.phanvung_id = phanvung_id
            this.loai_kl_id = loai_kl_id
            this.formData.loai_kl_id = loai_kl_id
            this.formData.hdtb_id = formData.hdtb_id
            this.action = action
            this.formData.loai_phandoan = formData.loai_phandoan
            await this.layDanhMuc()
            switch (Number(loai_kl_id)) {
                case 1:
                    this.panels = schemaVPN
                    break;
                case 2:
                    this.panels = schemaKTR
                    break;
                case 3:
                    this.panels = schemaTrungKe
                    break;
                case 4:
                    this.panels = schemaTrungKeIP
                    break;
                case 5:
                    this.panels = schemaVeTinh
                    break;
                default:
                    break;
            }
            console.log(formData.loai_phandoan, "Loại phân đoạn")
            if(action == 'view'){
                this.disable_button = true
                this.defaultTabIndex = Number(formData.loai_phandoan)
                    // Sử dụng $nextTick để đảm bảo rằng các thành phần con đã được render
                    //this.$refs.myTabInstance.selectedItem =this.formData.loai_phandoan;
               
            }
            if(action == 'edit'){
                this.disable_button= false
                this.defaultTabIndex = Number(formData.loai_phandoan)
                    // Sử dụng $nextTick để đảm bảo rằng các thành phần con đã được render
                    //this.$refs.myTabInstance.selectedItem =this.formData.loai_phandoan;
            }  
            if(action == 'add'){
                this.formData.hdtb_ct_id = 0
                this.disable_button= false
                this.btnHuy();
                
            }  

            this.$nextTick(() => {
                this.formData = {
                    hdtb_id: 0,
                    hdtb_ct_id: 0,
                    phieu_id: 0,
                    thuebao_id: 0,
                    thuebao_ct_id: 0,
                    trang_thai_an: null,
                    loai_phandoan: 0,
                    loai_kl_id: 0
                },
                this.$set(this.formData, 'ghichu', '')
                this.setData(formData)
                let obj = this.$refs.tab[0].ej2Instances
                obj.select(this.defaultTabIndex)
                this.$refs.popupSegment.show()
            })
            this.loading(false)
        },
        setData(formData) {
            
            Object.keys(formData).forEach(key => {
                this.$set(this.formData, key, formData[key])
            });

        },
        btnHuy() {
            this.formData.hdtb_ct_id = 0
            this.formData.loai_phandoan = this.tabindex
            this.formData.loai_kl_id = this.loai_kl_id
            Object.keys(this.formData).forEach(key => {
                if (key != "hdtb_id" && key != "hdtb_ct_id") {
                    this.$set(this.formData, key, "")
                }
            });
        },
        init(element) {
            this.$set(this.formData, element.name.toLowerCase(), "")
            this.$set(this.requireE, element.name.toLowerCase(), element.required ? true : false)
        },
        changeInput(name, value) {
            this.$set(this.formData,name.toLowerCase(),value)
            if(name.toLowerCase() == 'thietbi_id_a'){
                let data_filter = this.danhmuc.TBI_DAUCUOI.filter(x =>x.thietbidc_id == parseInt(value));
                this.formData.tinh_a_id = data_filter[0].tinh_id
                this.formData.hethong_a = data_filter[0].he_thong_id
                this.formData.site_a =  data_filter[0].site_id
                this.formData.phong_tbi_a =  data_filter[0].phong_may_id
            }
             
            if(name.toLowerCase() == 'thietbi_id_z'){
                console.log(value)
                let data_filter = this.danhmuc.TBI_DAUCUOI.filter(x =>x.thietbidc_id == parseInt(value));
                 console.log(this.danhmuc.TBI_DAUCUOI[0])
                this.formData.tinh_z_id = data_filter[0].tinh_id
                this.formData.hethong_z = data_filter[0].he_thong_id
                this.formData.site_z =  data_filter[0].site_id
                this.formData.phong_tbi_z =  data_filter[0].phong_may_id
            }
            console.log(value, name.toLowerCase(), this.formData)
        },
        async layDanhMuc() {
            return new Promise(async (rs, rj) => {
                let data = await this.axios.post("web-net/thongtinchung/dsDanhMucChungPD", { "loai_kl_id": this.loai_kl_id, phanvung_id: this.phanvung_id })
                this.danhmuc = data.data.data
                rs(true)
            })
        },
        async btnAnHien() {
            this.loading(true)
            try {
                if (!this.formData.hdtb_ct_id && !this.formData.thuebao_ct_id) {
                    this.$toast.error("Chưa có thông tin phân đoạn!")
                    return
                }

                let data = await this.axios.post("web-net/thongtinchung/anHienPhanDoan", {
                    "hdtb_ct_id": this.formData.hdtb_ct_id,
                    "thuebao_ct_id": this.formData.thuebao_ct_id,
                    "phieu_id": this.formData.phieu_id,
                    "trang_thai_an": this.formData.trang_thai_an == 1 ? 0 : 1,
                    phanvung_id: this.phanvung_id
                })
                if (data.data.error_code != "BSS-00000000") {
                    this.$toast.error(data.data.message)
                } else {
                    if (data.data.data != 'ok') {
                        this.$toast.error(data.data.data)
                    } else {
                        this.$toast.success((this.formData.trang_thai_an == 1 ? "Ẩn phân đoạn" : "Hiện phân đoạn") + " thành công!")
                        this.formData.trang_thai_an = this.formData.trang_thai_an == 1 ? 0 : 1
                        this.$emit('success', true)
                    }
                }
            } catch (error) {
                this.$toast.error("Có lỗi xảy ra!" + error.message)
                console.log(error)
            }
            this.loading(false)
        },
         onTabClick(tab) {
        this.tabindex = tab.selectedIndex.toString(); 
        this.formData.loai_phandoan = tab.selectedIndex
        
      
        console.log("Tab ID:", tab); 
        },
        async saveForm() {
            this.formData.loai_kl_id = this.loai_kl_id
            this.formData.loai_phandoan = this.tabindex
            if(this.tabindex == '0'){
                 this.formData.loai_phandoan = 0
            }
            this.loading(true)
            var validate = false 
            this.requireE = this.panels[0].tabs.filter(x => x.id === this.tabindex)[0].elements;
            Object.keys(this.requireE).forEach(key => {
                console.log(3445)
                if (this.requireE[key] && this.requireE[key] == true && (this.formData[key] == "" || this.formData[key] == null || typeof (this.formData[key]) == "undefined")) {
                    validate = true
                }
            });
 
            if (validate) {
                this.loading(false)
                this.$toast.error("Những trường có dấy * là bắt buộc.Vui lòng nhập đầy đủ thông tin!"); return
            } 

            try {
                 console.log({
                    ...this.formData, 
                    phanvung_id: this.phanvung_id
                })
                delete this.formData.column;
                let data = await this.axios.post("web-net/thongtinchung/capNhatThemMoiPhanDoan", {
                    ...this.formData,
                    phanvung_id: this.phanvung_id
                })
                
                 console.log(data)
                if (data.data.error_code != "BSS-00000000") {
                    this.$toast.error(data.data.message)
                } else {
                    if (!data.data.data.message.includes("ok")) {
                        this.$toast.error(data.data.data.data)
                    } else {
                        this.$toast.success("Ghi lại thành công!")
                        if (data.data.data.data && data.data.data.data.length > 0) {
                            this.setData(data.data.data.data[0])
                        }
                        this.$emit('success', true)
                    }
                }
            } catch (error) {
                console.log(error)
                this.$toast.error("Có lỗi xảy ra!" + error.message)
                console.log(error)
            }

            this.loading(false)
        },
        async btnHoanThanh() {
            this.loading(true)
            console.log(this.phanvung_id)
            try {
                if (!this.formData.hdtb_ct_id && !this.formData.thuebao_ct_id) {
                    this.$toast.error("Chưa có thông tin phân đoạn!")
                    this.loading(false)
                    return
                }

                let data = await this.axios.post("web-net/thongtinchung/hoanThanhPhanDoan", {
                    "hdtb_ct_id": this.formData.hdtb_ct_id,
                    "thuebao_ct_id": this.formData.thuebao_ct_id,
                    "phieu_id": this.formData.phieu_id,
                    "trangthai_pd_id": 1,
                    phanvung_id: this.phanvung_id
                })
                if (data.data.error_code != "BSS-00000000") {
                    this.$toast.error(data.data.message)
                } else {
                    if (data.data.data != 'ok') {
                        this.$toast.error(data.data.data)
                    } else {
                        this.$toast.success("Hoàn thành thành công!")
                        this.$emit('success', true)
                        this.hideModal();
                    }
                }
            } catch (error) {
                this.$toast.error("Có lỗi xảy ra!" + error.message)
                console.log(error)
            }
            this.loading(false)
        },
        async btnXoa() {
            this.$confirm('Bạn có chắc chắn muốn xóa phân đoạn này?', 'Xác nhận', {
                confirmButtonText: 'Đồng ý',
                cancelButtonText: 'Hủy',
                type: 'warning'
            }).then(async () => {
                this.loading(true)
                try {
                    if (!this.formData.hdtb_ct_id && !this.formData.thuebao_ct_id) {
                        this.$toast.error("Chưa có thông tin phân đoạn!")
                        return
                    }

                    let data = await this.axios.post("web-net/thongtinchung/xoaPhanDoan", {
                        "hdtb_ct_id": this.formData.hdtb_ct_id,
                        "thuebao_ct_id": this.formData.thuebao_ct_id,
                        "phieu_id": this.formData.phieu_id,
                        "phanvung_id": this.phanvung_id
                    })
                    if (data.data.error_code != "BSS-00000000") {
                        this.$toast.error(data.data.message)
                    } else {
                        if (data.data.data != 'ok') {
                            this.$toast.error(data.data.data)
                        } else {
                            this.$toast.success("Xóa thành công!")
                            this.$emit('success', true)
                            this.btnHuy()
                        }
                    }
                } catch (error) {
                    this.$toast.error("Có lỗi xảy ra!" + error.message)
                    console.log(error)
                }
                this.loading(false)
            }).catch(() => {
            });

        }
    }
}
</script>
<style lang="scss" scoped>
.box-title {
    background: #b5dbf254;
    color: #333;
    font-weight: bold;
    padding: 15px;
    margin-bottom: 10px;
    border-radius: 5px
}
</style>
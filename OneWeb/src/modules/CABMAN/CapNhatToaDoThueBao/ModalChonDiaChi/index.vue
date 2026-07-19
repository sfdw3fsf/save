<template src="./index.html">
</template>

<script>
export default {
    props: {
        tramtb_id: Number,
        diaChi: String,
        isShow: Boolean,
        chiTietDiaChi: {
            type: Object,
            default: function () {
                return { 
                    tinhThanh_id: null,
                    quanHuyen_id: 0,
                    phuongXa_id: 0,
                    duongPho_id: 0,
                    ap_id: 0,
                    khu_id: 0,
                    dacDiem_id: 0,
                    soNha: null,
                }
            }
        }
    },
    data() {
        return {
            tinhThanh_id: null,
            quanHuyen_id: 0,
            phuongXa_id: 0,
            duongPho_id: 0,
            ap_id: 0,
            khu_id: 0,
            dacDiem_id: 0,
            soNha: null,
            diaChiForm: null,
            dsThanhPho: [],
            dsQuanHuyen: [],
            dsPhuongXa: [],
            dsDuongPho: [],
            dsNgoAp: [],
            dsKhu: [],
            dsDacDiem: [],
            isChk_duongpho: false,
            isChk_ngoap: false,
            isChk_khu: false,
            isChk_dacdiem: false,
            isLoading: true
        }
    },
    methods: {
        layDsDacDiem: async function() {
            try {
                let rs = await this.$root.context.post('/web-cabman/mang-truyen-dan/lay-ds-dacdiem', {
                    phuong_id: this.phuongXa_id,
                    pho_id: this.duongPho_id,
                    ap_id: this.ap_id,
                    khu_id: this.khu_id
                });
                this.dsDacDiem = rs.data;
                this.dacDiem_id = 0;
            } catch (error) {
                
            }
        },
        thayDoiDiaChi: function() {
            if(this.isLoading) {
                this.isLoading = false;
                return;
            }
            this.diaChiForm = this.tinhThanh_id ? $( "#tinhThanh option:selected" ).text() : this.diaChiForm;
            this.diaChiForm = this.quanHuyen_id ? $( "#quanHuyen option:selected" ).text() + ', ' + this.diaChiForm  : this.diaChiForm;
            this.diaChiForm = this.phuongXa_id ? $( "#phuongXa option:selected" ).text() + ', ' + this.diaChiForm : this.diaChiForm;
            if(this.isChk_duongpho && this.duongPho_id) {
                this.diaChiForm = $( "#duongPho option:selected" ).text() + ', ' + this.diaChiForm;
            }
            if(this.isChk_ngoap && this.ap_id) {
                this.diaChiForm = $( "#ngoAp option:selected" ).text() + ', ' + this.diaChiForm;
            }
            if(this.isChk_khu && this.khu_id) {
                this.diaChiForm = $( "#khu option:selected" ).text() + ', ' + this.diaChiForm;
            }
            this.diaChiForm = this.soNha ? this.soNha  + ', ' + this.diaChiForm : this.diaChiForm;
           
        },
        chapNhan: async function() {
            this.closeModal({isUpdated: true, diaChi: this.diaChiForm, chiTietDiaChi: {
                tinhThanh_id: this.tinhThanh_id,
                quanHuyen_id: this.quanHuyen_id,
                phuongXa_id: this.phuongXa_id,
                duongPho_id: this.duongPho_id,
                ap_id: this.ap_id,
                khu_id: this.khu_id,
                dacDiem_id: this.dacDiem_id,
                soNha: this.soNha
            }});
        },
        keyDown: function(e) {
            if (e.key === "Backspace" || e.key === "Delete") {
                return e.preventDefault(); // Don't do anything to the input value
            }
        },
        closeModal: function(data = {isUpdated: false, diachi: this.diaChi}) {
            this.$emit('closeModalDiaChi', data);
        }
    },
    mounted: async function() {
        try {
            this.loading(true);
            let rs = await this.$root.context.get('/web-cabman/mang-truyen-dan/lay-ds-tinhthanh');
            this.dsThanhPho = rs.data;
            this.diaChiForm = this.diaChi;
            this.tinhThanh_id = parseInt(this.$root.token.getPhanVungID());
            this.quanHuyen_id = this.chiTietDiaChi.quanHuyen_id;
            this.phuongXa_id = this.chiTietDiaChi.phuongXa_id;
            this.duongPho_id = this.chiTietDiaChi.duongPho_id;
            this.ap_id = this.chiTietDiaChi.ap_id;
            this.khu_id = this.chiTietDiaChi.khu_id;
            this.dacDiem_id = this.chiTietDiaChi.dacDiem_id;
            this.soNha = this.chiTietDiaChi.soNha;
        } catch (error) {
            
        } finally {
            this.loading(false);
        }
    },
    watch: {
        tinhThanh_id: async function(val) {
            if(val) {
                try {
                    this.loading(true);
                    let rs = await this.$root.context.get(`/web-cabman/mang-truyen-dan/lay-ds-quanhuyen-by-tinhthanh/${val}`);
                    this.dsQuanHuyen = rs.data;
                    this.quanHuyen_id = this.quanHuyen_id ? this.quanHuyen_id : 0;
                    this.thayDoiDiaChi();
                } catch (error) {
                    
                } finally {
                    this.loading(false);
                }
            }
        },
        quanHuyen_id: async function(val) {
            if(val) {
                try {
                    this.loading(true);
                    let rs = await this.$root.context.get(`/web-cabman/mang-truyen-dan/lay-ds-phuongxa-by-quanhuyen/${val}`);
                    this.dsPhuongXa = rs.data;
                    this.phuongXa_id = this.phuongXa_id ? this.phuongXa_id : 0;
                    this.thayDoiDiaChi();
                } catch (error) {
                    
                } finally {
                    this.loading(false);
                }
            }
        },
        phuongXa_id: async function(val) {
            if(val) {
                try {
                    this.loading(true);
                    let rs = await this.$root.context.get(`/web-cabman/mang-truyen-dan/lay-ds-duongpho-ngoap-khu-by-phuongxa/${val}`);
                    this.dsDuongPho = rs.data.dsPho;
                    this.dsNgoAp = rs.data.dsAp;
                    this.dsKhu = rs.data.dsKhu;
                    this.duongPho_id = this.duongPho_id ? this.duongPho_id : 0;
                    this.ap_id = this.ap_id ? this.ap_id : 0;
                    this.khu_id = this.khu_id ? this.khu_id : 0;
                    this.thayDoiDiaChi();
                } catch (error) {
                    
                } finally {
                    this.loading(false);
                }
            }
        },
        duongPho_id: function(val) {
            if(val) {
                try {
                    this.loading(true);
                    this.layDsDacDiem();
                    this.thayDoiDiaChi();
                } catch (error) {
                    
                } finally {
                    this.loading(false);
                }
            }
        },
        ap_id: function(val) {
            if(val) {
                try {
                    this.loading(true);
                    this.layDsDacDiem();
                    this.thayDoiDiaChi();
                } catch (error) {
                    
                } finally {
                    this.loading(false);
                }
            }
        },
        khu_id: function(val) {
            if(val) {
                try {
                    this.loading(true);
                    this.layDsDacDiem();
                    this.thayDoiDiaChi();
                } catch (error) {
                    
                } finally {
                    this.loading(false);
                }
            }
        },
        soNha: function() {
            this.thayDoiDiaChi();
        }
    }
}
</script>

<template src="./OnOffCallOutCallInOnDemand.html"></template>
<style scoped src="./OnOffCallOutCallInOnDemand.scss"></style>
<script>
import API from './API.js'
import breadcrumb from '@/components/breadcrumb'
import DataGrid3 from '@/components/Controls/DataGrid3'
import Vue from 'vue'

export default {
    name: 'OnOffCallOutCallInOnDemand',
    components: {
        breadcrumb,
        DataGrid3
    },
    watch: {
        'frm.goiDi': function(newVal){
            if ((newVal == true && this.frm.currentOC == 0) || (newVal == false && this.frm.currentOC == 1)){
                this.btnCatMo = true
            }
            else{
                this.btnCatMo = false
            }
        }
    },
    computed: {
        lyDoHuy(){
            const lyDo = this.options.dsLyDoHuy.filter(obj => {
                return this.frm.lyDoHuyId == obj.MA
            })
            if(lyDo.length > 0)
                return lyDo[0].LYDO
            else
                return ""
        }
    },
    data() {
        return {
            dateFormat: "DD/MM/YYYY HH:mm:ss",
            animationSettings: { effect: 'Zoom' },
            header: {
                title: "Cắt mở gọi đi/ gọi đến",
                list: [
                    //{ name: 'Trang chủ', link: { name: 'Ui.cards' } },
                    { name: 'Quản lý trả trước', link: { name: 'Ui.cards' } },
                    { name: 'Cắt mở gọi đi, gọi đến theo yêu cầu', link: { name: 'Ui.cards' } },
                ]
            },
            options: {
                dsLyDoHuy: []
            },
            frm: {
                soTb: "",
                goiDi: false,
                goiDen: false,
                currentOC: 0,
                lyDoHuyId: "0",
                pin: "",
                pin2: "",
                loaiTb: "",
                ngayKh: "",
                msin: "",
                puk: "",
                puk2: "",
                maTinh: "",
                tenTb: "",
                diaChi: "",
                ghiChu: "",
                maNeo: ""
            },
            dsSuDungDV: [],
            dsLichSuTb: [],
            dsLichSu3G: [],
            btnCatMo: false,
            enableGoiDen: false,
            cNguoiDungTemplate: function () {
                return { template : Vue.component('columnTemplate',{
                    template: `<div style="color: red;">{{cData}}</div>`,
                    data: function() {
                        return {
                            data: {}
                        }
                    },
                    computed: {
                        cData: function() {
                            return this.data.NGUOI_DUNG
                        }
                    }
                })}
            },
        }
    },
    async mounted(){
        this.frmCatMoIC_OC_FormLoad()
    },
    methods: {
        async frmCatMoIC_OC_FormLoad(){
            try{
                const res = await API.gphone_reset_huy_lydo(this.axios, {})
                let dt = res.data.data
                dt = dt.filter(obj => {
                    return ["1","2","3","4","5","M"].includes(String(obj.MA))
                })
                dt.unshift({
                    MA: "0",
                    LYDO: "Không chọn"
                })
                this.options.dsLyDoHuy = dt
            }
            catch (e){
                this.$toast.error("Có lỗi xảy ra trong quá trình kết nối với Sercvice VinaPhone!\n" + e);
            }
        },
        txtSoTB_KeyPress(){
            if(this.frm.soTb != "")
                this.TraCuu()
        },
        tsbtnTraCuu_Click(){
            this.TraCuu()
        },
        async TraCuu(){
            try{
                this.loading(true)
                this.btnCatMo = false

                const res = await API.ts_tracuu_laytt_tb(this.axios, {so_tb: '84' + this.frm.soTb})
                let dtLichSuTb = res.data.data
                if(dtLichSuTb.length > 0){
                    if(Object.keys(dtLichSuTb[0]).length == 1){
                        this.$toast.error('Không tìm thấy thông tin yêu cầu !')
                        return
                    }
                    if(dtLichSuTb[0].LOAI_TB == 'Gphone' || dtLichSuTb[0].TRA_SAU == 1){
                        this.$toast.error('Nghiệp vụ chỉ thực hiện với thuê bao di động trả trước !')
                        return
                    }
                    if(dtLichSuTb[0].GOI_DI == 1)
                        this.frm.goiDi = true
                    else
                        this.frm.goiDi = false
                    this.frm.currentOC = Number(dtLichSuTb[0].GOI_DI)

                    if(dtLichSuTb[0].GOI_DEN == 1)
                        this.frm.goiDen = true
                    else
                        this.frm.goiDen = false

                    if(dtLichSuTb[0].TRA_SAU == 0)
                        this.enableGoiDen = false
                    else
                        this.enableGoiDen = true

                    this.frm.msin = dtLichSuTb[0].SO_MSIN
                    this.frm.pin = dtLichSuTb[0].PIN
                    this.frm.pin2 = dtLichSuTb[0].PIN2
                    this.frm.puk = dtLichSuTb[0].PUK
                    this.frm.puk2 = dtLichSuTb[0].PUK2
                    this.frm.ngayKh = dtLichSuTb[0].NGAY_KH
                    this.frm.tenTb = dtLichSuTb[0].TEN_KH
                    this.frm.diaChi = dtLichSuTb[0].DCHI_KH
                    this.frm.loaiTb = dtLichSuTb[0].LOAI_TB
                    this.frm.maTinh = dtLichSuTb[0].MA_TINH

                    const resSuDungDv = await API.ts_tracuu_laytt_tb_dv(this.axios, {so_tb: '84' + this.frm.soTb})
                    this.dsSuDungDV = resSuDungDv.data.data

                    const resLichSuThaoTac = await API.gphone_reset_huy_ls_tb(this.axios, {so_tb: '84' + this.frm.soTb})
                    this.dsLichSuTb = resLichSuThaoTac.data.data

                    const resLichSu3G = await API.ts_tracuu_laytt_ls_3g(this.axios, {so_tb: '84' + this.frm.soTb})
                    this.dsLichSu3G = resLichSu3G.data.data
                }
            } catch(e){
                this.$toast.error('Có lỗi xảy ra trong quá trình kết nối với Sercvice VinaPhone!\n' + e)
            } finally{
                this.loading(false)
            }
        },
        async btnCatMo_Click(){
            try{
                this.loading(true)
                let success = true
                if(this.$root.token.getMaCCBS() == ""){
                    this.$toast.error('Người dùng chưa được gán user tác động hệ thống gphone.')
                    return
                }

                if(this.frm.goiDi == true)
                    this.frm.maNeo = 'SPS_PRODUCT_HLR_OC'
                else
                    this.frm.maNeo = 'SPS_PRODUCT_HLR_OC_DEACTIVE'
                let params = {
                    "ma_ldo": "",
                    "note": this.lyDoHuy + ': ' + this.frm.ghiChu,
                    "product": this.frm.maNeo,
                    "so_tb": '84' + this.frm.soTb,
                    "hdtb_id": this.frm.hdtb_id
                }
                const res = await API.khoamo_ic_oc(this.axios, params)
                if(res.data.error_code === 'BSS-00000000'){
                    // if(res.data.data != 'OK'){
                    //     this.$toast.error('Có lỗi khi đóng mở dịch vụ ' + res.data.data)
                    //     return
                    // }
                }
                else{
                    success = false
                    throw res.data.message
                }

                if(this.enableGoiDen == true){
                    if(this.frm.goiDen == true)
                        this.frm.maNeo = 'SPS_PRODUCT_HLR_IC'
                    else
                        this.frm.maNeo = 'SPS_PRODUCT_HLR_IC_DEACTIVE'

                    let params = {
                        "ma_ldo": "",
                        "note": this.lyDoHuy + ': ' + this.frm.ghiChu,
                        "product": this.frm.maNeo,
                        "so_tb": '84' + this.frm.soTb,
                        "hdtb_id": this.frm.hdtb_id
                    }
                    const res = await API.khoamo_ic_oc(this.axios, params)
                    if(res.data.error_code === 'BSS-00000000'){
                        // if(res.data.data != 'OK'){
                        //     this.$toast.error('Có lỗi khi đóng mở dịch vụ ' + res.data.data)
                        //     return
                        // }
                    }
                    else{
                        success = false
                        throw res.data.message
                    }
                }

                if(success == true)
                    this.$toast.success('Thực hiện đóng mở dịch vụ thành công!')

                await this.TraCuu()
            } catch(e){
                // this.$toast.error('Có lỗi xảy ra trong quá trình kết nối với Sercvice VinaPhone!\n' + e)
                this.$toast.error('Có lỗi khi đóng mở dịch vụ! \n' + e)
            } finally{
                this.loading(false)
            }
        }
    }
}
</script>

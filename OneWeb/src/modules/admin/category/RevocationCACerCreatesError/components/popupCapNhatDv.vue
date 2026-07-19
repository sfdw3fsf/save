<template>
    <b-modal 
        :id="modalId"
        size="md"
        no-close-on-backdrop
        hide-footer 
        hide-header 
        hide-header-close 
        body-class="modal-body p-0"
        @shown="getData">
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title"><span class="icon one-notepad"></span> Cập nhật đơn vị nhận phiếu</div>
                <div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide(modalId)">
                </div>
            </div>
            <div class="list-actions-top">
                <ul class="list">
                    <li>
                        <a href="#" @click.prevent="luuLai">
                            <span class="icon one-save"></span>Lưu lại
                        </a>
                    </li>
                </ul>
            </div>
            <div class="popup-body">
                <div v-for="item in layoutDV" class="info-row" :key="item.name">
                    <div class="key w150">{{item.label}}</div>
                    <div class="value">
                        <div class="select-custom">
                            <select2 :ref="item.name" v-model="item.value" class="select-custom" :options="item.options"></select2>
                            <!-- <SelectExt :allowFiltering="true" dataTextField="id"
                                        dataValueField="text" :dataSource="item.options" v-model="layoutDV[index].value"></SelectExt> -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </b-modal>
</template>
<script>
import API from './../API.js'
import { LoaiHopDong, DichVuVienThong } from '@/utils/Enum'

export default {
    name: "ModalCapNhatDv",
    props: ["modalId", "hdtbId"],
    data() {
        return {
            layoutDV: []
        }
    },
    methods: {
        async HT_GIAODIEN_DONVI(hd_id){
            this.loading(true)
            try{
                const res1 = await API.lay_hdtb_cntt_theo_hdtb_id(this.axios, {
                    "hdtb_id": hd_id
                })
                var dt = []
                if (res1 && res1.data && res1.data.error_code === 'BSS-00000000')
                    dt = res1.data.data
                else
                    this.$toast.error(res1.data.message)
                
                var tocdotn_id = 0
                var muccuoctn_id = 0
                var vdichvuvt_id_cbo = 0
                var vkieuld_id_cbo = 0
                var vloaitb_id_cbo = 0

                if(dt.length > 0){
                    tocdotn_id = dt[0].tocdo_id
                    muccuoctn_id = dt[0].muccuoc_id
                    vdichvuvt_id_cbo = dt[0].dichvuvt_id
                    vkieuld_id_cbo = dt[0].kieuld_id
                    vloaitb_id_cbo = dt[0].loaitb_id
                }
                var layoutDV = []

                if (vdichvuvt_id_cbo == DichVuVienThong.DICHVU_CNTT || vdichvuvt_id_cbo == DichVuVienThong.ANTOAN_BAOMAT_TT
                || vdichvuvt_id_cbo == DichVuVienThong.TRUNGTAM_DULIEU ||
                vdichvuvt_id_cbo == DichVuVienThong.HOINGHI_TRUYENHINH){
                    const res2 = await API.sp_lay_ds_donvi_quytrinh(this.axios, {
                        "dichvuvt_id": vdichvuvt_id_cbo,
                        "kieuld_id": vkieuld_id_cbo,
                        "loaihd_id": LoaiHopDong.CHAMDUT_SD,
                        "loaihinh_id": vloaitb_id_cbo,
                        "muccuoc_id": muccuoctn_id,
                        "tocdo_id": tocdotn_id
                    })
                    var dtControl = []
                    if (res2 && res2.data && res2.data.error_code === 'BSS-00000000')
                        dtControl = res2.data.data
                    else
                        this.$toast.error(res2.data.message)
                    
                    for(let i = 0; i < dtControl.length; i++){
                        layoutDV.push({
                            name: "cbo" + dtControl[i].loaidv_dich_id + "." + dtControl[i].thutu_id,
                            label: dtControl[i].ten_loaidv,
                            loaidv_id: dtControl[i].loaidv_dich_id 
                        })
                    }
                    
                    for (let i = 0; i < dtControl.length; i++){
                        const res3 = await API.sp_lay_ds_donvi_theo_loaidv(this.axios, {
                            "loaidv_id": dtControl[i].loaidv_dich_id
                        })
                        if (res3 && res3.data && res3.data.error_code === 'BSS-00000000')
                            layoutDV[i].options = res3.data.data.map(x=>({id:x.donvi_id,text:x.ten_dv}))
                        else
                            layoutDV[i].options = []

                    }

                    if (hd_id > 0){
                        for(let i = 0; i < dtControl.length; i++){
                            if(dtControl[i].loaidv_dich_id == 65)
                                layoutDV[i].value = this.$root.token.getDonViID()
                        }

                        const res4 = await API.sp_lay_ds_hdtb_dv_ca(this.axios, {
                            "hdtb_id": hd_id
                        })
                        var dbtb_dv = []
                        if (res4 && res4.data && res4.data.error_code === 'BSS-00000000')
                            dbtb_dv = res4.data.data
                        else
                            this.$toast.error(res4.data.message)

                        if(dbtb_dv.length > 0){
                            for(let i = 0; i < dtControl.length; i++){
                                let kiemtra = dbtb_dv.filter(x => x.loaidv_id == dtControl[i].loaidv_dich_id)
                                if(kiemtra.length > 0){
                                    for(let j = 0; j < dtControl.length; j++){
                                        if(layoutDV[j].name == "cbo" + dtControl[i].loaidv_dich_id + "." + dtControl[i].thutu_id){
                                            layoutDV[j].value = kiemtra[0].donvi_id
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return layoutDV
            }catch(e){
                console.log('Có lỗi ' + e)
                return []
            }finally{
                this.loading(false)
            }
        },
        async getData(){
            try{
                const kq = await this.HT_GIAODIEN_DONVI(this.hdtbId)
                this.layoutDV = kq
                // console.log(this.layoutDV)
            }catch(e){
                this.$toast.error('Có lỗi '+e)
            }
        },
        async luuLai(){
            try{
                var data = JSON.stringify(this.layoutDV.map(x=>({
                    HDTB_ID: this.hdtbId,
                    LOAIDV_ID: x.loaidv_id,
                    DONVI_ID: x.value,
                    KIEUDV_ID: 2
                })))

                const res = await API.capnhat_hdtb_dv_cntt(this.axios, {
                    "data": data,
                    "hdtb_id": this.hdtbId
                })
                if (res && res.data && res.data.error_code === 'BSS-00000000')
                    if(res.data.data == 'ok')
                        this.$toast.success('Cập nhật thành công!')
                    else
                        this.$toast.error(res.data.data)
                else
                    this.$toast.error(res.data.message)
                    // console.log(res.data.data)
            }catch(e){
                console.log('Có lỗi ' + e)
            }finally{
                this.loading(false)
            }
        }
    }
}
</script>
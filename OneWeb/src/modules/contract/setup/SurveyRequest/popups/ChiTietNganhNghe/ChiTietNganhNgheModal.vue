<template>
  <b-modal
    ref="popupChiTietNganhNghe"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="handleShowModal"
  >
  <div class="modal-content popup-box">
    <div class="popup-header">
      <div class="title">
        <span class="icon one-notepad"></span> Chi tiết ngành nghề
      </div>
      <div class="close -ap icon-close" @click="hideModal"></div>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="info-row">
          <div class="key w20 nowrap">
            Tìm kiếm theo mã tiểu ngành (rê chuột vào ô tìm kiếm để xem HD => Enter để tìm kiếm )
          </div>
          <div class="value">
            <input type="text" @keypress.enter="onEnterTimKiem" v-model="input_timkiem" class="form-control" title="2 ký tự tìm theo tiểu ngành cấp 1, 3 ký tự  tìm theo tiểu ngành cấp 2, 4 ký tự tìm theo tiểu ngành cấp 3, 5 ký tự tìm theo tiểu ngành cấp 4" />
          </div>
        </div>
        <div class="info-row">
          <div class="key w120 nowrap">Tiểu ngành cấp 1</div>
          <div class="value">
            <multiselect
                :options="danhsach_tnc1"
                placeholder="Chọn tiểu ngành cấp 1"
                @input="changeTNC1"
                :showLabels="false"
                :allowEmpty="false"
                :value="tnc1_selected"
                label="ten_tnc1"
                track-by="tnc1_id">
                <template #noOptions>
                     Không tìm thấy thông tin
                </template>
                <template slot="singleLabel" slot-scope="{ option }">
                    <strong>{{ option.ten_tnc1 }}</strong>
                </template>
             </multiselect>
          </div>
        </div>
        <div class="info-row">
          <div class="key w120 nowrap">Tiểu ngành cấp 2</div>
          <div class="value">
            <multiselect
                :options="danhsach_tnc2"
                placeholder="Chọn tiểu ngành cấp 2"
                @input="changeTNC2"
                :showLabels="false"
                :allowEmpty="false"
                :value="tnc2_selected"
                label="ten_tnc2"
                track-by="tnc2_id">
                <template #noOptions>
                     Không tìm thấy thông tin
                </template>
                <template slot="singleLabel" slot-scope="{ option }">
                    <strong>{{ option.ten_tnc2 }}</strong>
                </template>
            </multiselect>
          </div>
        </div>
        <div class="info-row">
          <div class="key w120 nowrap">Tiểu ngành cấp 3</div>
          <div class="value">
            <multiselect
                :options="danhsach_tnc3"
                placeholder="Chọn tiểu ngành cấp 3"
                @input="changeTNC3"
                :showLabels="false"
                :allowEmpty="false"
                :value="tnc3_selected"
                label="ten_tnc3"
                track-by="tnc3_id">
                <template #noOptions>
                     Không tìm thấy thông tin
                </template>
                <template slot="singleLabel" slot-scope="{ option }">
                    <strong>{{ option.ten_tnc3 }}</strong>
                </template>
            </multiselect>
          </div>
        </div>
        <div class="info-row">
          <div class="key w120 nowrap">Tiểu ngành cấp 4</div>
          <div class="value">
            <multiselect
                :options="danhsach_tnc4"
                placeholder="Chọn tiểu ngành cấp 4"
                @input="changeTNC4"
                :showLabels="false"
                :allowEmpty="false"
                :value="tnc4_selected"
                label="ten_tnc4"
                track-by="tnc4_id">
                <template #noOptions>
                     Không tìm thấy thông tin
                </template>
                <template slot="singleLabel" slot-scope="{ option }">
                    <strong>{{ option.ten_tnc4 }}</strong>
                </template>
            </multiselect>
          </div>
        </div>
        <div class="info-row">
          <div class="key w120 nowrap">Mô tả chi tiết</div>
          <div class="value">
            <textarea
              name=""
              class="form-control"
              v-model="input_mota"
              style="height: 100px; resize: none"
            ></textarea>
          </div>
        </div>
      </div>
      <div class="group-buttons -bottom center">
        <button class="btn btn-second nocorner" @click="accept" style="width: 120px">
          <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp
          nhận
        </button>
      </div>
    </div>
  </div>
  </b-modal>
</template>
<script>
import API from './API'
// data:{
//     TNC1_ID:0,
//     TNC2_ID:0,
//     TNC3_ID:0,
//     TNC4_ID:0,
//     nganhnghe_id:0
// }
// Chưa mô tả, chưa có api tìm kiếm trong này. API sài từ  
// module: https://scm.devops.vnpt.vn/erp.ptpm1.onebss/OneWeb/-/tree/develop-erp/src/modules/contract/setup/InstallNewSubs
//Link: https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=1906551485&range=D266

export default {
    name:'ChiTietNganhNgheModal',
    props:{
        data:{
            type:Object,
            default:()=>{}
        }
    },
    data(){
        return {
            danhsach_tnc1:[],
            danhsach_tnc2:[],
            danhsach_tnc3:[],
            danhsach_tnc4:[],
            input_mota:'',
            input_timkiem:''
        }
    },
    computed:{
        tnc1_selected(){
            const index=this.danhsach_tnc1.findIndex(x=>x.tnc1_id==this.data.TNC1_ID)
            if(index>-1){
                return this.danhsach_tnc1[index]
            }
            return null
        },
        tnc2_selected(){
            const index=this.danhsach_tnc2.findIndex(x=>x.tnc2_id==this.data.TNC2_ID)
            if(index>-1){
                return this.danhsach_tnc2[index]
            }
            return null
        },
        tnc3_selected(){
            const index=this.danhsach_tnc3.findIndex(x=>x.tnc3_id==this.data.TNC3_ID)
            if(index>-1){
                return this.danhsach_tnc3[index]
            }
            return null
        },
        tnc4_selected(){
            const index=this.danhsach_tnc4.findIndex(x=>x.tnc4_id==this.data.TNC4_ID)
            if(index>-1){
                return this.danhsach_tnc4[index]
            }
            return null
        }
    },
    methods:{
        showModal() {
            this.$refs["popupChiTietNganhNghe"].show()
        },
        hideModal() {
            this.$refs["popupChiTietNganhNghe"].hide()
        },
        handleShowModal(){
            this.danhsach_tnc1=[]
            this.danhsach_tnc2=[]
            this.danhsach_tnc3=[]
            this.danhsach_tnc4=[]
            this.input_mota=''
            this.input_timkiem=''
            setTimeout(()=>{
                this.lay_ds_tieunganh(1, this.data.nganhnghe_id, 0)
            },200)
        },
        accept(){
            this.$emit('accept', Object.assign({}, this.data))
            this.hideModal()
        },
        async onEnterTimKiem(){
            if(this.input_timkiem.trim()==''){
                return
            }
            //handle tìm kiếm
            if(this.input_timkiem.trim().length<2||this.input_timkiem.trim().length>5){
                return
            }
            var result
            if(this.input_timkiem.trim().length==2){
                result=await this.timkiem_tieunganh(this.input_timkiem.trim(), 1)
            }else if(this.input_timkiem.trim().length==3){
                result=await this.timkiem_tieunganh(this.input_timkiem.trim(), 2)
            }else if(this.input_timkiem.trim().length==4){
                result=await this.timkiem_tieunganh(this.input_timkiem.trim(), 3)
            }else if(this.input_timkiem.trim().length==5){
                result=await this.timkiem_tieunganh(this.input_timkiem.trim(), 4)
            }

            if(result&&result.length>0){
                this.data.nganhnghe_id=result[0].nganhnghe_id
                this.data.TNC1_ID=result[0].tnc1_id?result[0].tnc1_id:0
                this.data.TNC2_ID=result[0].tnc2_id?result[0].tnc2_id:0
                this.data.TNC3_ID=result[0].tnc3_id?result[0].tnc3_id:0
                this.data.TNC4_ID=result[0].tnc4_id?result[0].tnc4_id:0
                this.lay_ds_tieunganh(1, this.data.nganhnghe_id, 0)
            }

        },
        changeTNC1(data){
            this.data.TNC1_ID=data.tnc1_id
            this.data.TNC2_ID=0
            this.data.TNC3_ID=0
            this.data.TNC4_ID=0
            this.danhsach_tnc2=[]
            this.danhsach_tnc3=[]
            this.danhsach_tnc4=[]
            this.lay_ds_tieunganh(2, this.data.nganhnghe_id, this.data.TNC1_ID)
        },
        changeTNC2(data){
            this.data.TNC2_ID=data.tnc2_id
            this.data.TNC3_ID=0
            this.data.TNC4_ID=0
            this.danhsach_tnc3=[]
            this.danhsach_tnc4=[]
            this.lay_ds_tieunganh(3, this.data.nganhnghe_id, this.data.TNC2_ID)
        },
        changeTNC3(data){
            this.data.TNC3_ID=data.tnc3_id
            this.data.TNC4_ID=0
            this.danhsach_tnc4=[]
            this.lay_ds_tieunganh(4, this.data.nganhnghe_id, this.data.TNC3_ID)
        },
        changeTNC4(data){
            this.data.TNC4_ID=data.tnc4_id
            this.input_mota=''
            this.lay_ds_tieunganh(5, this.data.nganhnghe_id, this.data.TNC4_ID)
        },
        async lay_ds_tieunganh_search(kieu, nganhnghe_id, vtc_id){
            try{
                this.loading(true)
                let response = await API.lay_ds_tieunganh(this.axios, kieu, nganhnghe_id, vtc_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async timkiem_tieunganh(timkiem, kieu){
            try{
                this.loading(true)
                let response = await API.timkiem_tieunganh(this.axios, timkiem, kieu)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async lay_ds_tieunganh(kieu, nganhnghe_id, vtc_id){
            try{
                this.loading(true)
                if(kieu==1){
                    this.danhsach_tnc1=[]
                }else if(kieu==2){
                    this.danhsach_tnc2=[]
                }else if(kieu==3){
                    this.danhsach_tnc3=[]
                }else if(kieu==4){
                    this.danhsach_tnc4=[]
                }else if(kieu==5){
                    this.input_mota=''
                }
                let response = await API.lay_ds_tieunganh(this.axios, kieu, nganhnghe_id, vtc_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    if(kieu==1){
                        this.danhsach_tnc1=response.data.data
                        if(this.danhsach_tnc1.length>0&&this.data.TNC1_ID!=0){
                            this.lay_ds_tieunganh(2, this.data.nganhnghe_id, this.data.TNC1_ID)
                        }else if(this.danhsach_tnc1.length>0&&this.data.TNC1_ID==0){
                            this.data.TNC1_ID=this.danhsach_tnc1[0].tnc1_id
                            this.lay_ds_tieunganh(2, this.data.nganhnghe_id, this.data.TNC1_ID)
                        }else{
                            this.data.TNC1_ID=0
                        }
                    }else if(kieu==2){
                        this.danhsach_tnc2=response.data.data
                        if(this.danhsach_tnc2.length>0&&this.data.TNC2_ID!=0){
                            this.lay_ds_tieunganh(3, this.data.nganhnghe_id, this.data.TNC2_ID)
                        }else if(this.danhsach_tnc2.length>0&&this.data.TNC2_ID==0){
                            this.data.TNC2_ID=this.danhsach_tnc2[0].tnc2_id
                            this.lay_ds_tieunganh(3, this.data.nganhnghe_id, this.data.TNC2_ID)
                        }else{
                            this.data.TNC2_ID=0
                        }
                    }else if(kieu==3){
                        this.danhsach_tnc3=response.data.data
                        if(this.danhsach_tnc3.length>0&&this.data.TNC3_ID!=0){
                            this.lay_ds_tieunganh(4, this.data.nganhnghe_id, this.data.TNC3_ID)
                        }else if(this.danhsach_tnc3.length>0&&this.data.TNC3_ID==0){
                            this.data.TNC3_ID=this.danhsach_tnc3[0].tnc3_id
                            this.lay_ds_tieunganh(4, this.data.nganhnghe_id, this.data.TNC3_ID)
                        }else{
                            this.data.TNC3_ID=0
                        }
                    }else if(kieu==4){
                        this.danhsach_tnc4=response.data.data
                        if(this.danhsach_tnc4.length>0&&this.data.TNC4_ID!=0){
                            this.lay_ds_tieunganh(5, this.data.nganhnghe_id, this.data.TNC4_ID)
                        }else if(this.danhsach_tnc4.length>0&&this.data.TNC4_ID==0){
                            this.data.TNC4_ID=this.danhsach_tnc4[0].tnc4_id
                            this.lay_ds_tieunganh(5, this.data.nganhnghe_id, this.data.TNC4_ID)
                        }else{
                            this.data.TNC4_ID=0
                        }
                    }else if(kieu==5){
                        let result=response.data.data
                        if(result.length>0){
                            this.input_mota=result[0].mota!=null?result[0].mota:''
                        }
                    }
                }else{
                    if(kieu==1){
                        this.danhsach_tnc1=[]
                        this.data.TNC1_ID=0
                    }else if(kieu==2){
                        this.danhsach_tnc2=[]
                        this.data.TNC2_ID=0
                    }else if(kieu==3){
                        this.danhsach_tnc3=[]
                        this.data.TNC3_ID=0
                    }else if(kieu==4){
                        this.danhsach_tnc4=[]
                        this.data.TNC4_ID=0
                    }else if(kieu==5){
                        this.input_mota=''    
                    }
                }
            }catch(e){
                this.loading(false)
                this.$toast.error('Không load được danh sách tiểu ngành')
            }      
        },
        

    }
}
</script>
<style>
.btn-second {
    background-color: #fff;
    border-color: #0176FF;
    color: #0176FF;
    font-weight: 600;
}
.btn-second:hover {
    background-color: #0176FF;
    border-color: #0176FF;
    color: #fff;
    font-weight: 600;
}
.btn-outline-secondary {
    background-color: #fff;
    color: #6c757d;
    border-color: #6c757d;
}
.btn-outline-secondary:hover {
    background-color: #6c757d;
    color: #fff;
    border-color: #6c757d;
}
.multiselect, .multiselect__input, .multiselect__single {
    font-size: 1rem !important;
}
.multiselect__tags{
    min-height: 32px !important;
    border: 1px solid #ced4da !important;
    padding: 6px 32px 0 6px;
    border-radius: 4px;
}
.multiselect__placeholder {
    margin-bottom: 5px!important;
    padding-top: 0px!important;
}
.multiselect__select{
    top: 0px!important;
    padding: 10px 9px!important;
}
</style>

 
<template>
    <vue-card :noborder="true" class="legend-title" label="Thông tin tìm kiếm">
        <div class="row">
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w90">
                                <div class="check-action">
                                    <input type="checkbox" class="check" disabled v-model="thongtintimkiem.chkLoaiHd">
                                    <span class="name">Loại HĐ</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="select-custom">
                                    <SelectExt v-model="thongtintimkiem.LOAIHD_ID"
                                    :allowFiltering="true"
                                    :dataSource="option.lstLoaiHopDong"
                                    @change="onChangeHopDong($event)"
                                    :disabled="!thongtintimkiem.chkLoaiHd"
                                    dataValueField="LOAIHD_ID"
                                    dataTextField="TEN_LOAIHD"
                                     >
                                     </SelectExt>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w90">
                                <div class="check-action">
                                    <input type="checkbox" class="check" v-model="thongtintimkiem.chkKieuHD">
                                    <span class="name">Kiểu LĐ</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="select-custom">

                                    <SelectExt v-model="thongtintimkiem.kieuld_id"
                                    :allowFiltering="true"
                                    :dataSource="option.lstKieuLD"
                                    :disabled="!thongtintimkiem.chkKieuHD"
                                    dataValueField="kieuld_id"
                                    dataTextField="ten_kieuld"
                                     >
                                     </SelectExt>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-7 col-12">
                                <div class="info-row">
                                    <div class="key w85">
                                        <div class="check-action">
                                            <input type="checkbox" class="check" v-model="thongtintimkiem.chkDate">
                                            <span class="name">Từ ngày</span>
                                        </div>
                                    </div>
                                    <div class="value">
                                     <div class="input-icon-right">
                                        <vue-date :disable="!thongtintimkiem.chkDate" :format="'DD/MM/YYYY'" v-model="thongtintimkiem.ngay_bd" />
                                    </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key w10 center">
                                        -
                                    </div>
                                    <div class="value">
                                        <div class="input-icon-right">
                                             <vue-date :disable="!thongtintimkiem.chkDate" :format="'DD/MM/YYYY'" v-model="thongtintimkiem.ngay_kt" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="col-sm-3 col-12">
                        <div class="info-row">
                            <div class="key">
                                <div class="check-action">
                                    <input type="checkbox" class="check" v-model="thongtintimkiem.chkKetQua">
                                    <span class="name">Kết quả KS</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="select-custom">

                                    <SelectExt v-model="thongtintimkiem.kqks"
                                    :allowFiltering="true"
                                    :dataSource="option.lstKQKS"
                                    :disabled="!thongtintimkiem.chkKetQua"
                                    dataValueField="id"
                                    dataTextField="name"
                                     >
                                     </SelectExt>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">
                                <div class="check-action">
                                    <input type="checkbox" class="check" @change="onChangeChkDichVu($event)" v-model="thongtintimkiem.chkDichVu">
                                    <span class="name">Dịch vụ</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="select-custom">
                                     <SelectExt v-model="thongtintimkiem.dichvuvt_id"
                                    :allowFiltering="true"
                                    :dataSource="option.lstDichVu"
                                    @change="onChangeDichVu($event)"
                                    :disabled="!thongtintimkiem.chkDichVu"
                                    dataValueField="dichvuvt_id"
                                    dataTextField="ten_dvvt"
                                     >
                                     </SelectExt>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">
                                <div class="check-action">
                                    <input type="checkbox" class="check" :disabled="thongtintimkiem.isDisChkLoaiHinh" @change="onChangeChkLoaiHinh($event)" v-model="thongtintimkiem.chkLoaiHinhTB">
                                    <span class="name">Loại hình TB</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="select-custom">
                                    <SelectExt v-model="thongtintimkiem.loaitb_id" 
                                    :allowFiltering="true" 
                                    :dataSource="option.lstLoaiHinhTB" 
                                    @change="onChangeLoaiTB($event)" 
                                    :disabled="thongtintimkiem.isDiscboLoaihinh"
                                    dataValueField="loaitb_id"
                                    dataTextField="loaihinh_tb"
                                >

                                    </SelectExt>
                                </div>
                            </div>
                        </div>


                    </div>
                    <div class="col-sm-5 col-12">
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Mã giao dịch</div>
                                    <div class="value">
                                        <input type="text" class="form-control highlight" v-on:keyup.enter="searchByMaGD" v-model="thongtintimkiem.ma_gd">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Số điện thoại</div>
                                    <div class="value">
                                        <input type="text" readonly v-model="thongtintimkiem.so_dt" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Tên thuê bao</div>
                                    <div class="value">
                                        <input type="text" readonly v-model="thongtintimkiem.ten_tb" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Địa chỉ LĐ</div>
                                    <div class="value">
                                        <input type="text" readonly v-model="thongtintimkiem.diachi_ld" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w90">Ghi chú</div>
                            <div class="value">
                                <input type="text" readonly v-model="thongtintimkiem.ghichu" class="form-control">
                            </div>
                        </div>
                    </div>
                </div>
    </vue-card>
</template>

<script>
import api from '../api'
import moment from "moment";
export default {
    data() {
        return {
            thongtintimkiem: {
                ma_gd: '',
                ngay_bd: '',
                ngay_kt: '',
                chkLoaiHd: true,
                chkKieuHD:false,
                chkDate:false,
                chkKetQua:false,
                chkDichVu:false,
                chkLoaiHinhTB:false,
                isDisChkLoaiHinh :true,
                isDiscboLoaihinh :true,
                kqks: 0,
            },
            option:{
                lstDichVu:[],
                lstLoaiHopDong:[],
                lstLoaiHinhTB:[],
                lstKieuLD:[],
                lstKQKS:[

                ]
            },


        }

    },
    props: {
        value: Object,
        loai: {
          type:String},
        checkHuy : Boolean,
        flag: Number,

    },
    watch: {
        thongtintimkiem: {
            handler: function(value) {
                this.$emit('input', value)
            },
            deep: true
        },

    },
    methods:{
        lay_dichvu(){
            api.lay_dichvu(this.axios,{

            }).then(res=>{
                try{
                    if(res && res.data && res.data.error_code && res.data.error_code == 'BSS-00000000' && res.data.data) {
                    this.option.lstDichVu=res.data.data;
                    if(this.option.lstDichVu.length>0){
                        this.thongtintimkiem.dichvuvt_id=this.option.lstDichVu[0].dichvuvt_id;
                        this.lay_loaihinhTB(this.thongtintimkiem.dichvuvt_id);

                        }
                    else{
                        this.option.lstDichVu=[]
                        }
                    }
                }
                catch(e){
                    console.log(e);
                        this.$toast.error('Không load được danh sách dịch vụ')
                }

            })
        },
        lay_loaihinhTB(dichvuvt_id){
            if(!dichvuvt_id || dichvuvt_id == 0 ){
                this.$toast.error('Không có thông tin dịch vụ');
            }
            else{
                 api.lay_loaiHinhTB(this.axios,{
                    "vdichvuvt_id":dichvuvt_id
                }).then(res=>{
                try{

                    if(res && res.data && res.data.error_code && res.data.error_code == 'BSS-00000000' && res.data.data) {
                    this.option.lstLoaiHinhTB=res.data.data;
                    if(this.option.lstLoaiHinhTB.length>0){
                        this.thongtintimkiem.loaitb_id=this.option.lstLoaiHinhTB[0].loaitb_id;
                        this.lay_kieuLD(this.thongtintimkiem.LOAIHD_ID, this.thongtintimkiem.loaitb_id);
                        }
                    else{
                        this.option.lstLoaiHinhTB=[]
                        }
                    }
                }
                catch(e){
                    this.$toast.error('Không load được danh sách loại hình thuê bao')
                }
            })
            }

        },
        lay_kieuLD(loaihd_id, loaitb_id){
            if(loaihd_id && loaitb_id){
                api.lay_kieuLD(this.axios, {
                    "vloaihd_id":loaihd_id,
                    "vloaitb_id":loaitb_id,
                }).then(res=>{
                    try{
                        if(res && res.data && res.data.error_code && res.data.error_code == 'BSS-00000000' && res.data.data) {
                        this.option.lstKieuLD = res.data.data;
                        if(this.option.lstKieuLD.length > 0){
                            this.thongtintimkiem.kieuld_id = this.option.lstKieuLD[0].kieuld_id;
                        }
                        else{
                            this.option.lstKieuLD = [];
                             this.thongtintimkiem.kieuld_id = null;
                        }
                        
                    }
                    else{
                            this.option.lstKieuLD = [];
                             this.thongtintimkiem.kieuld_id = null;
                    }
                    }
                    catch(e){
                        this.option.lstKieuLD = [];
                        console.log(e);
                        this.$toast.error('Không load được danh sách kiểu lắp đặt');
                    }

                })
            }
        },
        lay_loai_hd(dataLoai){
            api.lay_loaihopdong(this.axios).then(res=>{
                try{
                    if(res && res.data && res.data.error_code && res.data.error_code == 'BSS-00000000' && res.data.data) {
                    let dataRes =res.data.data;

                    if(dataRes.length>0){
                        if(dataLoai == 1){
                            dataRes.forEach((value,index) => {
                                if(value.LOAIHD_ID == 33 || value.LOAIHD_ID == 34 || value.LOAIHD_ID == 35){
                                    this.option.lstLoaiHopDong.push(value);

                            }
                            })
                        }
                        else{
                             dataRes.forEach((value,index) =>{
                                if(value.LOAIHD_ID == 36){
                                    this.option.lstLoaiHopDong.push(value);

                            }
                        })
                        }
                        this.thongtintimkiem.LOAIHD_ID=this.option.lstLoaiHopDong[0].LOAIHD_ID;
                        }
                    else{
                        this.option.lstLoaiHopDong=[];
                         this.thongtintimkiem.LOAIHD_ID = null;
                        }

                    }
                    else{
                        this.option.lstLoaiHopDong=[];
                         this.thongtintimkiem.LOAIHD_ID = null;
                    }
                }
                catch(e){
                        this.$toast.error('Không load được danh sách loại hợp đồng')
                }

            })
        },


        onChangeDichVu: function(event){
            this.lay_loaihinhTB(this.thongtintimkiem.dichvuvt_id);
           
        },
        onChangeHopDong: function(event){
            this.lay_kieuLD(this.thongtintimkiem.LOAIHD_ID, this.thongtintimkiem.loaitb_id);
        },
        onChangeLoaiTB: function(event){
            this.lay_kieuLD(this.thongtintimkiem.LOAIHD_ID, this.thongtintimkiem.loaitb_id);
        },
        onChangeChkDichVu: function(event){
             if (this.thongtintimkiem.chkDichVu)
            {
                
                this.thongtintimkiem.isDisChkLoaiHinh = false;
                this.thongtintimkiem.isDiscboLoaihinh = false;
            }
            else
            {
                
                this.thongtintimkiem.isDisChkLoaiHinh = true;
                this.thongtintimkiem.isDiscboLoaihinh = true;
            }
            //this.thongtintimkiem.chkLoaiHinhTB = this.thongtintimkiem.chkDichVu;
        },
        onChangeChkLoaiHinh(){
            if (this.thongtintimkiem.chkLoaiHinhTB)
            {
                
                this.thongtintimkiem.isDiscboLoaihinh = false;
            }
            else
            {
                
                this.thongtintimkiem.isDiscboLoaihinh = true;
            }
        },
        searchByMaGD: function(){
            var flag = 0;
            if(this.thongtintimkiem.ma_gd){
                flag = 1;
            }
            else{
                flag = 0;
            }

           this.$emit("searchByMaGD", flag);
        }
    },
    mounted(){
        this.lay_loai_hd(this.loai);
        this.lay_dichvu();
        this.$set(this.thongtintimkiem);
        this.option.lstKQKS = 
        [ 
                    {id:0, name: "Không đủ điều kiện thi công"} ,
                    {id: 1, name: "Đủ điều kiện thi công"},
                    {id: 2, name: "Đang khảo sát"},
        ],
        this.thongtintimkiem.ngay_kt = moment().format("DD/MM/YYYY");
        this.thongtintimkiem.ngay_bd = "01/"+ moment().format("MM/YYYY");
    }
}
</script>

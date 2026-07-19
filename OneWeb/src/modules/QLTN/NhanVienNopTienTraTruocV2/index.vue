<template src="./index.html">
</template>
<script>
import moment from 'moment'
import nhanVien from './components/nhanvien'
import hopDong from './components/hopdong'
import EventBus from '@/utils/eventBus'
import API from '../api/QuanLyDaiLy'
import Vue from 'vue'

export default {
    components: { nhanVien,hopDong},
    created:async function(){
        await this.getThuNgan();
        await this.getKieuNop();
        EventBus.$on('onViewDetail', this.onViewDetailHandler);
    },

    destroyed () {
        EventBus.$off('onViewDetail', this.onViewDetailHandler);
    },
    data: function () {
    return {
        thuNgan:[],
        kieuNop:[],
        dsDaNop:[],
        chiTietHD:[],
        nopNHid:null,
        form:{
            ctv_id:0,
            ctv_ten:'',
            ctv_ma:'',
            thungan_id:'',
            kieunop_id:'',
            kyhoadon: moment(new Date()).subtract(1, "months").endOf("month").toDate(),
            ngaynop:new Date(),
            kieu:0,
            tongtien:0,
            lydo:'',
        },
        thang: {
        dateVal: new Date(),
        dateFormat: "MM/yyyy",
      },
      ngay: {
        dateVal: new Date(),
        dateFormat: "dd/MM/yyyy",
      },
       btnTemplate: function () {
                return { template : Vue.component('columnTemplate', {
                    template: `<div><button class="btn btn-primary lh14 pad3" @click="onDownloadPdf" title="Chi tiết nộp">
                                    <span class="-ap icon-more_horiz"></span>
                                </button></div>`,
                    data () {
                        return { data: {} };
                    },
                    methods: {
                        onDownloadPdf() {
                            EventBus.$emit('onViewDetail', this.data);
                        }
                    }})
                }
            },
      isDisableLayTT:false,
      isDisableNhapMoi:true,
      isDisableGhiLai:true,
      isDisableHuy:false,
      isDisableXoa:true,
      isDisableDocFile:true,
      isDisablePhieuThu:true,

    }},

    methods:{

    onViewDetailHandler: async function(item) {
            if (this.nopNHid===null)
            {
                this.$toast.error('Bạn chưa chọn thôn tin để xem');
                return;
            }
            this.form.kieu=2
            this.onClickShowModal('mdHopDong')
        },
    getThuNgan: async function(){
        try {
                const rs = await this.$root.context.get(
                `/web-thuno/api/thu-no/common/nhan-vien-thu-ngan/nguoi-dung-hien-tai`
                );
                //if (rs.data.length>0)
                this.thuNgan = rs.data;
            } catch (error) {}
    },
     getKieuNop: async function(){
        try {
                const rs = await this.$root.context.get(
                `/web-thuno/api/thu-no/common/danh-muc/kieu-nop`
                );
                //if (rs.data.length>0)
                this.kieuNop = rs.data.filter(item=>item.KIEUNOP_ID===3||item.KIEUNOP_ID ==4);
            } catch (error) {}
    },
    getDSDaNop: async function (kyCuoc,maNV,quayThu){
        this.loading(true)
        try {
            const rs = await this.$root.context.get(
            `/web-thuno/api/thu-no/quan-ly-dai-ly/ds-nhan-vien-kt-nop-tien?kyCuoc=${this.fn_kycuoc(kyCuoc)}&maNV=${maNV}&quayThuId=${quayThu}`
            );
            //if (rs.data.length>0)
            this.dsDaNop = rs.data;
        } catch (error) {
            console.log('err',error)
        } finally {
            this.loading(false)
        }

    },
    onNhanVien(){
        if (!this.isDisableNhapMoi)
            this.$toast.error('Bạn chưa chọn nút Nhập mới để tìm kiếm nhân viên');
        else
            this.onClickShowModal('mdNhanVien');
    },
    onHopDong(){
        this.form.kieu=1;
        this.onClickShowModal('mdHopDong')
    },
    onClickShowModal(value) {
      this.$bvModal.show(value);
    },
    btnNhanvien(value){
        this.form.ctv_id=value.NHANVIEN_ID;
        this.form.ctv_ten=value.TEN_HT;
        this.form.ctv_ma=value.MA_NV;
        this.dsDaNop=[];
        if(this.isDisableNhapMoi){
            this.onHopDong()
        }
    },
    btnHopDong(value) {
        this.chiTietHD=value
        let tongtien=0;
        let matb='';

                    if (this.chiTietHD.length>0)
                    this.chiTietHD.forEach((el) => {
                        tongtien=tongtien+el.TONGTIEN;
                        matb=matb+el.MA_TB+',';
                    });


                    this.form.tongtien=tongtien;
                    this.form.lydo='Nộp tiền trả trước :'+matb+'('+tongtien+')';
    if (this.chiTietHD.length>0)
        this.isDisableGhiLai=false;
    },
    RowGridChange(...data){
        console.log(data[1]);
        this.nopNHid=data[1].NOPNH_ID;
        this.form.kieunop_id=data[1].KIEUNOP_ID;
        this.form.tongtien=data[1].TIENNOP;
        this.form.ngaynop=data[1].NGAYNOP;
        this.form.lydo=data[1].GHICHU;
          this.isDisableXoa=false;
    },
    kiemtra_dulieu(){
        if (this.form.ctv_ma==='')
        {
            this.$toast.error('Bạn chưa chọn nhân viên');
            return 0;
        }
        if (this.form.thungan_id==='')
        {
            this.$toast.error('Dữ liệu không theo thu ngân');
            return 1;
        }
        return 1;
    },
    async onLayTT(){
        let donvi='';
        if (this.form.thungan_id!='')
            {
            let thongtin_thungan=this.thuNgan.filter(x=>x.NHANVIEN_ID===Number(this.form.thungan_id));
            donvi=thongtin_thungan[0].DONVI_ID
            }
        else
            donvi='';
        if (await this.kiemtra_dulieu()===1)
            this.getDSDaNop(this.form.kyhoadon,this.form.ctv_ma,donvi);
        if (this.dsDaNop.legnth>0)
            this.isDisableXoa=false;
    },
    onNhapMoi(){

            this.form.ctv_id=0;
            this.form.ctv_ten='';
            this.form.ctv_ma='';
            this.form.thungan_id='';
            this.isDisableLayTT=false,
            this.isDisableNhapMoi=true,
            this.isDisableGhiLai=true,
            this.isDisableHuy=false,
            this.isDisableXoa=true,
            this.isDisableDocFile=true,
            this.isDisablePhieuThu=true,


         this.reset_form();
    },
    kiemtra_capnhat(){
        if (this.form.ctv_ma==='')
        {
            this.$toast.error('Bạn chưa chọn nhân viên');
            return 0;
        };
        if (this.form.kyhoadon==='')
        {
            this.$toast.error('Bạn chưa chọn kỳ hóa đơn');
            return 0;
        };
        if (this.form.thungan_id==='')
        {
            this.$toast.error('Bạn chưa chọn thu ngân');
            return 0;
        };
        if (this.form.kieunop_id==='')
        {
            this.$toast.error('Bạn chưa chọn kiểu nộp');
            return 0;
        };
        if (this.chiTietHD.legnth===0)
        {
            this.$toast.error('Bạn chưa chọn hợp đồng để thanh toán');
            return 0;
        };
        return 1;
    },
    onGhiLai: async function (){
        if (this.kiemtra_capnhat()==1)
        {
        this.isDisableLayTT=true;
        this.isDisableNhapMoi=false;
        this.isDisableGhiLai=true;
        this.isDisableHuy=true;
        this.isDisableXoa=true;
        this.isDisableDocFile=true;
        this.isDisablePhieuThu=true;

        let thongtin_thungan=this.thuNgan.filter(x=>x.NHANVIEN_ID===Number(this.form.thungan_id));
        let thongtin_capnhat={};
        let nopnh_id=0;
        let thamso={
            donViId: thongtin_thungan[0].DONVI_ID,
            ghiChu: this.form.lydo,
            nganHangId:0,
            kieuNopId: this.form.kieunop_id,
            kyHoaDon: this.fn_kycuoc(this.form.kyhoadon),
            maNVTC: this.form.ctv_ma,
            maTN: thongtin_thungan[0].MA_NV,
            ngayINS: moment(new Date()).format('DD/MM/yyyy'),
            ngayNop: moment(this.form.ngaynop).format('DD/MM/yyyy'),
            nhanVienId: this.form.ctv_id,
            tienNop: this.form.tongtien
        }

          let dshd=[];
                this.chiTietHD.forEach((item)=>{
                    dshd.push({"hdkhId": item.HDKH_ID,
                                "hdtbId": item.HDTB_ID,
                                "tienHD": item.TONGTIEN})
                });

          this.loading(true);
          await API.CapNhatNopNganHang(this.axios,thamso)
                .then(response => {
                    this.loading(false);
                    if (response.data.error_code === 'BSS-00000000') {
                        //this.$toast.success('Cập nhật thành công');
                        //this.getDSDaNop(moment(this.form.kyhoadon).format('yyyyMM')+'01',this.form.ctv_ma,this.form.thungan_id);
                        thongtin_capnhat = response.data.data;
                        nopnh_id=Number(thongtin_capnhat);
                    }
                })
                .catch(error => {
                    this.loading(false);
                    console.log(error);
                    this.$toast.error(error.data.message_detail);
                });

            if (nopnh_id>0)
            {

                let thamso2={
                    nopNHId:nopnh_id,
                    ds:dshd
                }
                this.loading(true);
                await API.CapNhatChiTietHD(this.axios,thamso2)
                    .then(response => {
                        this.loading(false);
                        if (response.data.error_code === 'BSS-00000000') {
                            this.$toast.success('Cập nhật thành công');
                            this.onLayTT();
                        }
                    })
                    .catch(error => {
                        this.loading(false);
                        console.log(error);
                        this.$toast.error(error.data.message_detail);
                    });
            }
        this.onLayTT();
        this.reset_form();
        }
    },
    reset_form(){

            this.form.tongtien=0,
            this.form.lydo='',
            this.chiTietHD=[]
    },
    fn_kycuoc(kyhoadon){
        return moment(kyhoadon).format("YYYYMM") + '01';
    },
    onHuy(){
        this.isDisableLayTT=true;
        this.isDisableNhapMoi=false;
        this.isDisableGhiLai=true;
        this.isDisableHuy=true;
        this.isDisableXoa=true;
        this.isDisableDocFile=true;
        this.isDisablePhieuThu=true;
        this.reset_form();
    },
    onXoa(){

        this.isDisableLayTT=true;
        this.isDisableNhapMoi=true;
        this.isDisableGhiLai=false;
        this.isDisableHuy=false;
        this.isDisableXoa=false;
        this.isDisableDocFile=true;
        this.isDisablePhieuThu=true;
        if (this.nopNHid===null)
        {
            this.$toast.error('Bạn chưa chọn phiếu để xóa');
        }
        else
        {
            this.$bvModal.msgBoxConfirm(
                            "Bạn có chắc chắn xóa thu tiền hợp đòng này không?",
                            {
                                title: "Thông báo",
                                centered: true,
                                size: "sm",
                                okTitle: 'Có',
                                cancelTitle: 'không',
                            }
                            )
                            .then((value) => {
                                 if (value) {
                                            this.loading(true);
                                            API.xoaPhieuNopNH(this.axios,this.nopNHid)
                                                                            .then(response => {
                                                                                this.loading(false);
                                                                                if (response.data.error_code === 'BSS-00000000') {
                                                                                    this.$toast.success('Xóa phiếu thành công');
                                                                                   this.onLayTT();
                                                                                }
                                                                            })
                                                                            .catch(error => {
                                                                                this.loading(false);
                                                                                console.log(error);
                                                                            })
                                    } else {
                                    return;
                                    }

                            });


        }
    },
    onPhieuThu(){
      console.log('axd');
    },
    onDocFile(){
      console.log('axd');
    },
    }
}
</script>
<style scoped>
a.disabled { color:lightgray !important; }
a.disabled:hover { color:lightgray !important; }
.disabled { pointer-events: none; color:lightgray; border-color:lightgray; }
</style>

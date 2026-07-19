<template src='./index.html'>

</template><style scope>
.menu {
    background: white;
    border-bottom: 1px solid #9090;
    box-shadow: 0 0 3px #808080;
    height: 40px;
    padding-top: 5px;
    font-weight: 600;
    line-height: 1;
}

.menu .nav-link {
    border-radius: 0;
    border-right: 1px solid #A5C1D0;
}

.menu .nav-link:active {
    color: white;
    background: #007BFF;
}

.menu .nav-item:last-child .nav-link {
    border-right: 0;
}

.hide-header thead {
    display: none;
}

.form-group {
    margin-bottom: 0.333rem;
}

.e-grid.e-responsive .e-headercelldiv {
    text-overflow: clip;
}

.c-red {
    color: red;
    font-weight: bold;
}

.overflow-hidden {
    overflow: hidden;
    flex-wrap: none;
}

label, legend {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.w100px {
    -ms-flex: 0 0 100px;
    flex: 0 0 100px;
    max-width: 100px;
}
</style><script>
import breadcrumb from "@/components/breadcrumb";
import api from "./api/index.js";
import moment from "moment";
import InHoSo from "@/modules/print/InHopDong/InHopDong";
export default {
    components : {breadcrumb, moment, InHoSo},  
    name: "FrmHoaDon0Dong",
    created: function() {
        // Fix css breaking layout
        this.removeCssRule("/static/css/custom.css", ".row");
        this.removeCssRule("/static/css/custom.css", ".custom-select");
        this.removeCssRule("/static/css/custom.css", ".custom-control");
        this.removeCssRule("/static/css/style.css", ".input-group");
        this.removeCssRule("/static/css/style.css", ".form-control");
    },
    data: function() {
        return {
            header: {
                title: "CẬP NHẬT HÓA ĐƠN 0 ĐỒNG",
                list: [{
                    name: "Phát triển thuê bao",
                    link: "/pttb",
                }, {
                    name: "Cập nhật hóa đơn 0 đồng",
                    link: "/pttb/hd0d",
                }, ],
            },
            dsGirdChon: [],
            show: true,
            combo_selected: "1",
            thongTinTimKiem: {
               phongBH: [],
               phongBHChon: "",
               cuaHang: [],
               cuaHangChon: "",
               thangHT: "",

            },
            popupInHopDong_Param: {},
            isDisabled: true,
            thongTinHoaDon: {
                thuNgan: [],
                thuNganChon: "",
                chkLoaiHD: false,
                loaiHD: [],
                loaiHDChon : "",
                seri: [],
                seriChon: "",
                hoaDon: "",
                loaiTien: [],
                loaiTienChon: "",
                ngayHD: moment(new Date()).format("DD/MM/YYYY hh:mm:ss"),
                ngayTT: moment(new Date()).format("DD/MM/YYYY hh:mm:ss"),
                tyGia: "",
                phieuThu: "",
                thongTinPT: ""
            },

            combo_datasource: [{
                value: 1,
                text: "Đơn vị 1"
            }, {
                value: 2,
                text: "Đơn vị 2"
            }],

            grid_datasource: [{
                GR: 1,
                TIENTRINHCC_ID: 1,
            }],
            tableConfig: {
                tableCollumns: [
                    {
                        fieldName: "ma_gd",
                        headerText: "Mã giao dịch",
                        allowFiltering: true,
                        allowSorting: false,
                        textAlign: "",
                    },
                    {
                        fieldName: "ten_loaihd",
                        headerText: "Loại HĐ/PL",
                        allowFiltering: true,
                        allowSorting: false,
                    },
                    {
                        fieldName: "ten_kh",
                        headerText: "Tên khách hàng",
                        allowFiltering: true,
                        allowSorting: false,
                    },
                    {
                        fieldName: "diachi_kh",
                        headerText: "Địa chỉ khách hàng",
                        allowFiltering: true,
                        allowSorting: false,
                    }
                ],
                tableData: [],
            }
        };
    },

    async created(){
        console.log(this.$root.token.getNhanVienID())
        console.log(this.$root.token.getDonViID())
        // lay ds phòng bán hàng
        let dataPhongBan = {
            "donvi_id": this.$root.token.getDonViID().toString()
        }
        let res = await api.dsPhongBanHang(this.axios, dataPhongBan)
        this.thongTinTimKiem.phongBH = res.data.data ? res.data.data : []
        this.thongTinTimKiem.phongBHChon = res.data.data[0].donvi_id

        // lay ds cửa hàng
        // let dataCuaHang = {
        //     "donvi_id": this.$root.token.getDonViID().toString()
        // }
        // let res1 = await api.dsCuaHang(this.axios, dataCuaHang)
        // this.thongTinTimKiem.cuaHang = res1.data.data ? res1.data.data : []

        // lay ds thu ngân
        let dataThuNgan = {
            "loainv_id": 17,
            "nhanvien_id":  this.$root.token.getNhanVienID()
        }
        let res2 = await api.dsThuNgan(this.axios, dataThuNgan)
        this.thongTinHoaDon.thuNgan = res2.data.data ? res2.data.data : []
        this.thongTinHoaDon.thuNganChon = this.thongTinHoaDon.thuNgan.length > 0 ? this.thongTinHoaDon.thuNgan[0].nhanvien_id : "";

        // lay ds loại hóa đơn
        let dataLoaiHoaDon = {
            "id": "DS_LOAIHOADON", 
            "para1":  null,   
            "para2":  null,  
            "para3":  null,   
            "para4":  null,    
            "para5":  null
        }
        let res3 = await api.dsHoaDon(this.axios, dataLoaiHoaDon)
        console.log(res3.data)
        this.thongTinHoaDon.loaiHD = res3.data.data ? res3.data.data : []

        // lay ds seri
        let dataSeri = {
            "id": "DS_SERI_HD", 
            "para1":  null,   
            "para2":  null,  
            "para3":  null,   
            "para4":  null,    
            "para5":  null
        }
        let res4 = await api.dsSeri(this.axios, dataSeri)
        this.thongTinHoaDon.seri = res.data.data ? res4.data.data : []

        // lay ds loại tiền
        let dataLoaiTien = {
            "id": "DS_LOAITIEN", 
            "para1":  null,   
            "para2":  null,  
            "para3":  null,   
            "para4":  null,    
            "para5":  null
        }
        let res5 = await api.dsLoaiTien(this.axios, dataLoaiTien)
        this.thongTinHoaDon.loaiTien = res5.data.data ? res5.data.data : []
        this.thongTinHoaDon.loaiTienChon = res5.data.data ? res5.data.data[0].loaitien_id : ""

        this.loading(false)
    },

    watch : {
        "thongTinHoaDon.chkLoaiHD" : function (newval){
            console.log(newval)
            if(newval){
                this.isDisabled = false
            }else{
                this.isDisabled = true
            }

        },

        "thongTinTimKiem.phongBHChon": async function (newval){
            console.log(newval)
            // lay ds cửa hàng
            let dataCuaHang = {
                "donvi_id": newval
            }
            let res1 = await api.dsCuaHang(this.axios, dataCuaHang)
            console.log(res1.data.data);
            this.thongTinTimKiem.cuaHang = res1.data.data ? res1.data.data : []
            
            this.thongTinTimKiem.cuaHangChon = this.thongTinTimKiem.cuaHang.length > 0 ? this.thongTinTimKiem.cuaHang[0].donvi_id : ""
        }


    },
    methods: {
        removeCssRule: function(file, rule) {
            
        },

        async layThongTin(){
            if(!this.thongTinTimKiem.thangHT){
                this.$toast.warning('Bạn chưa chọn tháng năm!');
                return false;
            }
            let vthang = this.thongTinTimKiem.thangHT.replace("/", "");
            console.log(vthang)
            let dataObj = {
                vdonvi_id: this.thongTinTimKiem.cuaHangChon,
                vthang:  vthang
            };

            let res = await api.dsHDKH(this.axios, dataObj);
            this.tableConfig.tableData = res.data.data ? res.data.data : [];

        },

        async tsbtnChapNhan_Click(){
            if(!this.KiemTraDL_ThanhToan()) return;
            if(this.dsGirdChon.length == 0){
                this.$toast.warning("Bạn chưa hợp đồng để cập nhật hóa đơn 0 đồng !");
                return false;
            }
            try {
                if (confirm('Bạn có muốn cập nhật hóa đơn 0 đồng cho các hợp đồng này không ?')){
                    for(let i = 0; i < this.dsGirdChon.length; i++){
                        let obj = this.dsGirdChon[i]
                        let dataCapNhat = {
                            phieutt_id: obj.phieutt_id,  
                            loaihoadon_id:  this.thongTinHoaDon.loaiHDChon,   
                            seri_id: this.thongTinHoaDon.seriChon,   
                            seri: this.thongTinHoaDon.seri.find((ele) => ele.seri_id == this.thongTinHoaDon.seriChon).seri,   
                            thungan_hd_id: this.thongTinHoaDon.thuNganChon,  
                            donvi_hd_id: this.$root.token.getDonViID(),   
                            sophieu: this.thongTinHoaDon.phieuThu,   
                            soseri: this.thongTinHoaDon.hoaDon,   
                            thongtin_pt: this.thongTinHoaDon.thongTinPT, 
                            thungan_tt_id: this.thongTinHoaDon.thuNganChon,  
                            donvi_tt_id: this.$root.token.getDonViID(),   
                            ngay_hd: this.thongTinHoaDon.ngayHD,  
                            ngay_tt: this.thongTinHoaDon.ngayTT,
                            ungdung_id: ""
                        }
                        console.log(dataCapNhat);
                        let res = await api.capNhat(this.axios, dataCapNhat);
                        console.log(await res.data);
                    }   
                }
                this.$toast.success("Cập nhật thành công!")
            } catch (error) {
                this.$toast.error("Có lỗi xẩy ra!")
            }
            

            
        },


        async inDS(){
            let dataObj = {
                vloaihoadon_id: this.thongTinHoaDon.loaiHDChon,   
                vseri_id: this.thongTinHoaDon.seriChon,   
                vhoadon: this.thongTinHoaDon.hoaDon,
                vdonvi_id : this.$root.token.getDonViID(),   
            }
            let res = await api.LAY_DS_HDKH_THEO_HOADON(this.axios, dataObj);
            if(res.data.data && res.data.data.length > 0){
                let obj = res.data.data[0];
                this.popupInHopDong_Param.hdkh_id  = obj.hdkh_id ; 
                this.popupInHopDong_Param.ma_gd  = obj.ma_gd ; 
                this.$refs.popupInHoSo.showModal(); 
            }else{
                this.$toast.warning("không lấy được thông tin hợp đồng theo hóa đơn!")
            }
            
        },


        async gridDS_selectedRowChanged(data){
            console.log(data)
            this.dsGirdChon = data;
        },

        async KiemTraDL_ThanhToan(){
            if(this.thongTinHoaDon.chkLoaiHD == false){
                this.$toast.warning("Bạn chưa chọn loại hóa đơn !");
                return false;
            }

            if(this.thongTinHoaDon.seriChon == ""){
                this.$toast.warning("Bạn chưa chọn Seri !");
                return false
            }

            if(this.thongTinHoaDon.hoaDon == "" && this.thongTinHoaDon.phieuThu == ""){
                this.$toast.warning("Bạn chưa nhập hóa đơn hoặc phiếu thu !");
                return false
            }

            if(this.thongTinHoaDon.hoaDon != "" && this.thongTinHoaDon.phieuThu != ""){
                this.$toast.warning("Bạn không được nhập cả Hóa đơn và Phiếu thu !");
                return false
            }

            if(this.thongTinHoaDon.thuNganChon == ""){
                this.$toast.warning("Bạn chưa chọn thu ngân!");
                return false
            }

            if(this.thongTinHoaDon.hoaDon != ""){
                let dataSend = {     
                    "loaihoadon_id": this.thongTinHoaDon.loaiHDChon,   
                    "seri_id":  this.thongTinHoaDon.seriChon, 
                    "soseri": this.thongTinHoaDon.hoaDon
                }

                let res = await api.layDuLieuHD(this.axios, dataSend);
                let kq = await res.data
                if(kq.data && kq.data.length > 0){
                    let item = kq.data[0];
                    let nguoi_cn = item.nguoi_cn ? item.nguoi_cn : "";
                    let ngay_cn = item.ngay_cn ? item.ngay_cn : "";
                    // let donvi_cn = item.nguoi_cn ? item.nguoi_cn : "";
                    this.$toast.warning("Hóa đơn đã được sử dụng. Người sử dụng : '" + nguoi_cn +  "', ngày '" + ngay_cn + "'");
                    return false
                }
            }

            return true


        },

        formatDateNew(value_){
            if (moment(value_).isValid()) {
                return moment(value_).format("DD/MM/YYYY");
            } else {
                return value_;
            }
        },
    },
};
</script>
<style>
.disabled {
  /* pointer-events: none; */
  cursor: no-drop;
  opacity: 0.7;
}

.disabled-color {
  color: gray !important;
}

</style>
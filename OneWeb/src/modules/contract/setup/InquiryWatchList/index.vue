<template>
    <div>
        <breadcrumb :header="header" />
        <vue-nav>
            <ul class="list">
            <li>
                <a  @click="search">
                     <span class="icon one-file-attach"></span>Lấy TT
                </a>
            </li>
            <li>
                <a @click="tsbtnHenDC_Click">
                     <span class="icon one-calendar-check"></span>Hẹn LĐ
                </a>
            </li>
            <li v-if="checkHuy == true">
                <a @click ="tsbHuy_Click">
                    <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy
                </a>
            </li>
            <li>
                <a @click="tsbtnExcel_Click">
                    <span class="icon one-download"></span>Xuất excel
                </a>
            </li>
            </ul>
        </vue-nav>
        <div class="page-content">
            <ThongTinTimKiem v-model="thongtintimkiem" 
            v-bind:loai="loai"
            v-bind:flag="flag" 
            v-on:searchByMaGD="searchByMaGD" />
            <vue-card label="Danh sách yêu cầu">

                <DataGrid
                    v-bind:dataSource="table.options"
                    v-bind:columns="table.config"
                    :allowPaging="true"
                    ref="danhsach"
                    :showFilter="true"
                    :enablePagingServer="false"
                    @selectedItemsChanged="grvDanhSach_FocusedRowChanged"
                    :enabledSelectFirstRow="true"
                    @queryCellInfo='queryCellInfo'
                    :allow-excel-export="true"
                />
            </vue-card>
        </div>
        <ThongTinHenKhachHang  ref="popupThongTinHenKhachHang" :hdkh_id="hdkh_id" />
    </div>
</template>
<script>
import breadcrumb from '@/components/breadcrumb'
import ThongTinTimKiem from './part/ThongTinTimkiem'
import api from './api'
import Vue from "vue"
import XLSX from "xlsx"
import moment from "moment";
import {LoaiHopDong} from './ThamSo';
import { DialogPlugin } from "@syncfusion/ej2-vue-popups";
import ThongTinHenKhachHang from "@/modules/contract/setup/SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue";
Vue.use(DialogPlugin);
export default {
  components: {
    breadcrumb,
    ThongTinTimKiem,
    ThongTinHenKhachHang,
    LoaiHopDong
  },
  data () {
    return {
        header: {
        title: 'DANH SÁCH THEO DÕI YÊU CẦU KÝ HỢP ĐỒNG TẠI ĐỊA CHỈ KHÁCH HÀNG',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: 'Theo dõi yêu cầu',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      checkHuy:true,
      loai:1,
      flag:0,
      hdkh_id: 0,
        thongtintimkiem: {
                ma_gd: '',
                ngay_bd: '',
                ngay_kt: '',
                chkLoaiHd: false,
                chkKieuHD:false,
                chkDate:false,
                chkKetQua:false,
                chkDichVu:false,
                chkLoaiHinhTB:false,
                isDisChkLoaiHinh :true,
                isDiscboLoaihinh :true,
            },  
        table: {
                loading: false,
                options: [],
                config: [
                {
                   headerText: 'Hẹn lắp đặt',
                    fieldName: 'henLD',
                     allowFiltering: true,
                    allowSorting: true,
                },
                {
                    headerText: 'Kết quả KS',
                    fieldName: 'ketqua',
                     allowFiltering: true,
                    allowSorting: true,

                },
                {
                    headerText: 'Số máy/Account',
                    fieldName: 'account',
                     allowFiltering: true,
                    allowSorting: true,

                },
                {
                    headerText: 'Dịch vụ',
                    fieldName: 'ten_dvvt',
                    allowFiltering: true,
                    allowSorting: true,

                },
                {
                    headerText: 'Loại hình TB',
                    fieldName: 'loaihinh_tb',
                     allowFiltering: true,
                    allowSorting: true,
                },
                {
                    headerText: 'Mã GD',
                    fieldName: 'ma_gd',
                     allowFiltering: true,
                    allowSorting: true,
                },
                {
                    headerText: 'Kiểu yêu cầu',
                    fieldName: 'ten_kieuld',
                    allowFiltering: true,
                    allowSorting: true,
                },
                {
                    headerText: 'Tên thuê bao',
                    fieldName: 'ten_tb',
                     allowFiltering: true,
                    allowSorting: true,
                },
                {
                    headerText: 'Ngày yêu cầu',
                    fieldName: 'ngay_yc',
                    allowFiltering: true,
                    allowSorting: true,
                },
                {
                    headerText: 'Đơn vị TC',
                    fieldName: 'donviTC',
                    allowFiltering: true,
                    allowSorting: true,
                },
                {
                    headerText: 'Số điện thoại',
                    fieldName: 'so_dt',
                    textAlign: "right",
                   allowFiltering: true,
                    allowSorting: true,
                },
                {
                    headerText: 'Mã số thuế',
                    fieldName: 'maSoThue',
                    allowFiltering: true,
                    allowSorting: true,
                },
                {
                    headerText: 'Địa chỉ LĐ',
                    fieldName: 'diachi_ld',
                     allowFiltering: true,
                    allowSorting: true,
                },
                {
                    headerText: 'Nhân viên lập HĐ',
                    fieldName: 'ten_nv',
                    allowFiltering: true,
                    allowSorting: true,
                },
                {
                    headerText: 'Số lượng',
                    fieldName: 'soluong',
                     allowFiltering: true,
                    allowSorting: true,
                },
                {
                    headerText: 'Ghi chú',
                    fieldName: 'ghichu',
                    allowFiltering: true,
                    allowSorting: true,
                },

                 {
                    headerText: 'Đơn vị giao',
                    fieldName: 'donViGiao',
                     allowFiltering: true,
                    allowSorting: true,
                },
                 {
                    headerText: 'Thời gian giao',
                    fieldName: 'thoiGianGiao',
                     allowFiltering: true,
                    allowSorting: true,
                },
                 {
                    headerText: 'TG khảo sát',
                    fieldName: 'thoiGianKS',
                     allowFiltering: true,
                    allowSorting: true,
                },
                 {
                    headerText: 'Ngày trả',
                    fieldName: 'ngayTra',
                     allowFiltering: true,
                    allowSorting: true,
                },
                 {
                    headerText: 'Lý do trả',
                    fieldName: 'lyDoTra',
                    allowFiltering: true,
                    allowSorting: true,
                },
                 {
                    headerText: 'Ghi chú trạm',
                    fieldName: 'ghiChuTram',
                    allowFiltering: true,
                    allowSorting: true,
                },
                 {
                    headerText: 'Tên đơn vị',
                    fieldName: 'tenDonVi',
                    allowFiltering: true,
                    allowSorting: true,
                },
                {
                    headerText: 'Ngày hoàn thành',
                    fieldName: 'ngayHoanThanh',
                     allowFiltering: true,
                    allowSorting: true,
                },
                {
                    headerText: 'Nhân viên PBH',
                    fieldName: 'nhanVienPBH',
                    allowFiltering: true,
                    allowSorting: true,
                },
                {
                    headerText: 'Loại giấy tờ',
                    fieldName: 'loaiGiayTo',
                     allowFiltering: true,
                    allowSorting: true,
                },
                {
                    headerText: 'Số giấy tờ',
                    fieldName: 'soGiayTo',
                     allowFiltering: true,
                    allowSorting: true,
                }
                ],
            }
         }
    },
    methods: {
        queryCellInfo: function (args) {
            var loaiHD = this.thongtintimkiem.loaihd_id;
            if (args.column.field === "ketqua"){
                var result = args.data.ketqua;
                if(result == 0){
                    var setVal = loaiHD != LoaiHopDong.TIEPNHANHD ? "Không đủ điều kiện thi công" : "Không ký được hợp đồng";
                    args.data.ketqua = setVal;
                    args.cell.innerText = setVal;
                }
                else if(result == 1){
                    var setVal = loaiHD != LoaiHopDong.TIEPNHANHD ? "Đủ điều kiện thi công" : "Đã ký hợp đồng";
                    args.data.ketqua = setVal;
                    args.cell.innerText = setVal;
                }
                else{
                    var setVal = loaiHD != LoaiHopDong.TIEPNHANHD ? "Đang khảo sát" : "Đang liên hệ khách hàng";
                    args.data.ketqua = setVal;
                    args.cell.innerText = setVal;
                }
            }
         },
        onExcelExport: function () {
            try{
               //ẩn cột checkbox nếu allowCheckBox
                    let cols = this.$refs.danhsach.grid.getColumns();
                    cols[0].visible = false;
                    let excelExportProperties = {
                        dataSource: this.table.options,
                        enableFilter: true,
                        fileName: "DanhSachTheoDoiYeuCau.xlsx"
                    };
                    this.$refs.danhsach.grid.excelExport(excelExportProperties).then((e) => cols[0].visible = true);//hiện lại khi export xong   
            }
            catch (ex) {
                 this.$toast.error("Có lỗi trong quá trình xuất file Excel !" + ex)
             }
        },
         lay_ts_btn_huy(){
            api.lay_tsBtnHuy(this.axios,{
                "ma_ts": "NUT_HUY_KHAOSAT"
            }).then(res=>{
                 try{
                    if(res && res.data && res.data.error_code && res.data.error_code == 'BSS-00000000' && res.data.data) {
                        if(res.data.data == 0){
                            this.checkHuy = false;
                        }
                        else{
                            this.checkHuy = true;
                        }
                        this.$emit("check", this.checkHuy);
                    }
                 }
                catch(e){
                    console.log(e);
                        this.$toast.error('Không load được dữ liệu')
                }
            })
        },
        search(){
           this.flag = 0;
           this.clearData(this.flag);
           this.loading(true);
            api.lay_dsYCKS(this.axios,this.getValDataPost(0)).then(res =>{
                    this.$set(this.table, 'options' ,res.data.data);
                    this.loading(false);
                });

        },
        searchByMaGD(val){
            this.flag = val;
            this.clearData(this.flag);
            this.loading(true);
            api.lay_dsYCKS(this.axios,this.getValDataPost(this.flag)).then(res =>{
                    this.$set(this.table, 'options' ,res.data.data);
                    this.loading(false);
            })
        },
        tsbtnExcel_Click(){
            this.onExcelExport();
        },
        getValDataPost(flag){
            let donviId = this.$root.token.getDonViID();
            let dataPost = {};
            if(flag == 0){
               var vdieukien = "";
                if (this.thongtintimkiem.chkKetQua)
                {
                    vdieukien = vdieukien + " " + " and nvl(a.ketquaks,2) =" + this.thongtintimkiem.kqks; // neu ketqua null thi để là đang khảo sát
                    if (this.thongtintimkiem.kqks == 2)
                        vdieukien += " and a.tthd_id=4 and  a.ketquaks is null";//nhapt
                }
                if (this.thongtintimkiem.chkKieuHD)
                {
                    vdieukien = vdieukien + " " + " and a.kieuld_id=" + (this.thongtintimkiem.kieuld_id? this.thongtintimkiem.kieuld_id : "-1");
                }
                if (this.thongtintimkiem.chkLoaiHd)
                {
                     vdieukien = vdieukien + " " + " and c.loaihd_id = " + (this.thongtintimkiem.LOAIHD_ID?this.thongtintimkiem.LOAIHD_ID: "-1");
                }
                if (this.thongtintimkiem.chkDichVu)
                {
                    vdieukien = vdieukien + " " + " and a.dichvuvt_id = " + (this.thongtintimkiem.dichvuvt_id?this.thongtintimkiem.dichvuvt_id: "-1");
                }
                if (this.thongtintimkiem.chkLoaiHinhTB)
                {
                    vdieukien = vdieukien + " " + " and a.loaitb_id =" + (this.thongtintimkiem.loaitb_id?this.thongtintimkiem.loaitb_id: "-1");
                }
                console.log(vdieukien);
                    dataPost = {
                        "ma_gd":0,
                        "donvi_id":donviId,
                        "dieukien":vdieukien,
                        "tungay":this.thongtintimkiem.chkDate?this.thongtintimkiem.ngay_bd:"",
                        "denngay":this.thongtintimkiem.chkDate?this.thongtintimkiem.ngay_kt:"",
                    }
            }
            else if(flag == 1){
                 dataPost = {
                       "ma_gd":this.thongtintimkiem.ma_gd,
                        "donvi_id":donviId,
                        "dieukien":"",
                        "tungay":"",
                        "denngay":"",
                    }
            }

            //console.log(dataPost);
            return dataPost;
        },
        clearData(flag){
            if(flag == 0){
                this.thongtintimkiem.ma_gd = "";
            }

                this.thongtintimkiem.ten_tb = "";
                this.thongtintimkiem.so_dt = "";
                this.thongtintimkiem.diachi_ld = "";
                this.thongtintimkiem.ghichu = "";
                this.$set(this.thongtintimkiem);
        },
        tsbtnHenDC_Click(){
            this.$refs.popupThongTinHenKhachHang.showModal();
        },
        tsbHuy_Click(){
            api.sp_capnhat_huyphieu_tn(this.axios,{ "hdkh_id": this.hdkh_id})
            .then((response) => {
            if (response.data.error_code === "BSS-00000000" && response.data.data !== undefined) {
                this.$toast.success("Hủy giao phiếu thành công!");
                this.search();
            }
            else{
                this.$toast.success("Hủy giao phiếu không thành công!");
            }

            })
            .catch((error) => {
            console.log(error);
            })
            .finally(() => {});
        },
        grvDanhSach_FocusedRowChanged(data) {
            this.clearData(this.flag);
            if (data[0]) {
                this.thongtintimkiem.ma_gd = data[0].ma_gd;
                this.thongtintimkiem.ten_tb = data[0].ten_tb;
                this.thongtintimkiem.so_dt = data[0].so_dt;
                this.thongtintimkiem.diachi_ld = data[0].diachi_ld;
                this.thongtintimkiem.ghichu = data[0].ghichu;
                this.hdkh_id = data[0].hdkh_id;
            }
        },
    },
    created(){
        if(this.$route.query.tag != null && this.$route.query.tag != '' && this.$route.query.tag.length > 0){
            this.loai = this.$route.query.tag;
        }
        this.lay_ts_btn_huy();
    },
}
</script>

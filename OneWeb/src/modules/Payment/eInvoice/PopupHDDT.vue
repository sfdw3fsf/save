<template>
    <div>
        <!-- <button @click="openHDDT" class="btn btn-success m-5">Hóa đơn điện tử</button> -->
        

  <b-modal
    id="hoadondt"
    ref="popup_HDDT"
    modal-class="popup-box"
    header-class="popup-header"
    body-class="none-padding"
    size="xl"
    hide-footer
  >
    <template #modal-header="{ close }">
      <div class="title">
        <span class="icon nc-icon-outline ui-1_settings"></span> Hóa đơn điện tử
      </div>
      <div
        @click="close"
        class="close -ap icon-close"
        data-dismiss="modal"
      ></div>
    </template>

    <div class="list-actions-top">
      <ul class="list">
        <li
          @click="clickButton('timkiem')"
          :class="this.button.timkiem ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-file-plus"></span>Tìm kiếm </a>
        </li>
        <li
          @click="clickButton('chapnhan')"
          :class="this.button.chapnhan ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-file-plus"></span>Chấp nhận </a>
        </li>

      </ul>
    </div>

                <div class="popup-body">
                    <div class="row">
                        <div class="col-sm-6 col-12">
                            <div class="box-form">
                                <div class="legend-title">Thông tin tìm kiếm</div>
                                <div class="row">
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <div class="key w120">
                                                <div class="check-action">
                                                    <input type="checkbox" v-model="chkLoaiHopDong" class="check">
                                                    <span class="name">Loại hợp đồng</span>
                                                </div>
                                            </div>
                                            <div class="value">
                                                <div class="select-custom">
                                                <SelectExt v-model="ddlLoaiHopDong" :options="this.options.loaihopdong" :disabled="!this.chkLoaiHopDong"> 
                                                    <option disabled value="0">Select one</option> 
                                                </SelectExt>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="info-row">
                                            <div class="key w120">
                                                <div class="check-action">
                                                    <input type="checkbox" v-model="chkDichVu" class="check">
                                                    <span class="name">Dịch vụ</span>
                                                </div>
                                            </div>
                                            <div class="value">
                                                <div class="select-custom">
                                                <SelectExt v-model="ddlDichVu" :options="this.options.dichvu" :disabled="!this.chkDichVu"> 
                                                    <option disabled value="0">Select one</option> 
                                                </SelectExt>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="info-row">
                                            <div class="key w120">
                                                <div class="check-action">
                                                    <input type="checkbox" v-model="chkNgayYC" class="check">
                                                    <span class="name">Ngày YC</span>
                                                </div>
                                            </div>
                                            <div class="value">
                                                <div class="select-custom">
                                                    <vue-date
                                                        format="DD/MM/YYYY"
                                                        type="datetime"
                                                        v-model="txtNgayYC"
                                                        :disable="!this.chkNgayYC"
                                                    ></vue-date>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="info-row">
                                            <div class="key w120">
                                                Tra cứu theo
                                            </div>
                                            <div class="value">
                                                <div class="select-custom">
                                                <SelectExt v-model="ddlTraCuuTheo" :options="this.options.tracuutheo"> 
                                                    <option disabled value="0">Select one</option> 
                                                </SelectExt>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <div class="key w120">
                                                Mã giao dịch
                                            </div>
                                            <div class="value">
                                                <input type="text" v-model="txtMaGD" class="form-control">
                                            </div>
                                        </div>
                                        <div class="info-row">
                                            <div class="key w120">
                                                <div class="check-action">
                                                    <input type="checkbox" v-model="chkTrangThaiHD" class="check">
                                                    <span class="name">Trạng thái HĐ</span>
                                                </div>
                                            </div>
                                            <div class="value">
                                                <div class="select-custom">
                                                <SelectExt v-model="ddlTrangThaiHD" :options="this.options.trangthaihd" :disabled="!this.chkTrangThaiHD"> 
                                                    <option disabled value="0">Select one</option> 
                                                </SelectExt>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="info-row">
                                            <div class="key w120">
                                                Đến ngày
                                            </div>
                                            <div class="value">
                                                <div class="select-custom">
                                                    <vue-date
                                                        format="DD/MM/YYYY"
                                                        type="datetime"
                                                        v-model="txtDenNgay"
                                                        :disable="!this.chkNgayYC"
                                                    ></vue-date>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="info-row">
                                            <div class="key w120">
                                                Thông tin tra cứu
                                            </div>
                                            <div class="value">
                                                <input type="text" v-model="txtThongTinTraCuu" class="form-control highlight">
                                            </div>
                                        </div>
                                        
                                    </div>
                                </div>
                                <div class="legend-title mart20">
                                    Thông tin hợp đồng

                                </div>
                                <div class="row">
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <div class="key w120">Mã KH</div>
                                            <div class="value">
                                                <input type="text" v-model="txtMaKH" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <div class="key w120">Loại KH</div>
                                            <div class="value">
                                                <div class="select-custom">
                                                <SelectExt v-model="ddlLoaiKH" :options="this.options.loaikh"> 
                                                    <option disabled value="0">Select one</option> 
                                                </SelectExt>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div> 
                                <div class="info-row">
                                    <div class="key w120">Tên KH</div>
                                    <div class="value">
                                        <input type="text" v-model="txtTenKH" class="form-control">
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w120">Địa chỉ KH</div>
                                    <div class="value">
                                        <input type="text" v-model="txtDiaChiKH" class="form-control">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <div class="key w120">Hình thức TT</div>
                                            <div class="value">
                                                <div class="select-custom">
                                                <SelectExt v-model="ddlHinhThucTT" :options="this.options.hinhthuctt"> 
                                                    <option disabled value="0">Select one</option> 
                                                </SelectExt>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <div class="key w120">Kênh thu</div>
                                            <div class="value">
                                                <div class="select-custom">
                                                <SelectExt v-model="ddlKenhThu" :options="this.options.kenhthu"> 
                                                    <option disabled value="0">Select one</option> 
                                                </SelectExt>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <div class="key w120">Ngân hàng</div>
                                            <div class="value">
                                                <div class="select-custom">
                                                <SelectExt v-model="ddlNganHang" :options="this.options.nganhang"> 
                                                    <option disabled value="0">Select one</option> 
                                                </SelectExt>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <div class="key w120">Số TK</div>
                                            <div class="value">
                                                <input type="text" v-model="txtSoTK" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <div class="key w120">MST</div>
                                            <div class="value">
                                                <input type="text" v-model="txtMST" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <div class="key w120">Số ĐT</div>
                                            <div class="value">
                                                <input type="text" v-model="txtSDT"  class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <div class="key w120">Tổng tiền</div>
                                            <div class="value">

                                                <vue-numeric class="form-control" 
                                                separator="," v-model="txtTongTien" 
                                                placeholder="0" output-type="Number"></vue-numeric>

                                                <!-- <input type="text" v-model="txtTongTien" class="form-control"> -->
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <div class="key w120">Tiền bằng chữ</div>
                                            <div class="value">
                                                <input type="text" v-model="txtTienBangChu" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="box-form">
                                <div class="legend-title">Thông tin thao tác</div>
                                <div class="row">
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <div class="key w90">Thao tác</div>
                                            <div class="value">
                                                <div class="select-custom">
                                                <SelectExt v-model="ddlThaoTac" :options="this.options.thaotac"> 
                                                    <option disabled value="0">Select one</option> 
                                                </SelectExt>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <div class="key w120">Mẫu số:</div>
                                            <div class="value">
                                                <div class="select-custom">
                                                <SelectExt v-model="ddlMauSo" :options="this.options.mauso" :disabled="true"> 
                                                    <option disabled value="0">Select one</option> 
                                                </SelectExt>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <div class="key w90">Số hóa đơn:</div>
                                            <div class="value">
                                                <input type="text" v-model="txtSoHoaDon" class="form-control" :disabled="true">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <div class="key w120">Ký hiệu (Serial No):</div>
                                            <div class="value">
                                                <div class="select-custom">
                                                <SelectExt v-model="ddlKyHieu" :options="this.options.kyhieu" :disabled="true"> 
                                                    <option disabled value="0">Select one</option> 
                                                </SelectExt>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="legend-title mart20">Danh sách hợp đồng thuê bao</div>
                                <div class="table-content" style="height: 100%">
                                    <DataGrid
                                        :columns="options.col_dstb"
                                        v-bind:dataSource="options.dstb"
                                        :showColumnCheckbox="false"
                                        :enable-paging-server="false"
                                        :allowPaging="true"
                                        :showFilter="true"
                                        :enabledSelectFirstRow="false"
                                    >
                                    </DataGrid>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">Danh sách hợp đồng</div>
                        <div class="table-content">
                            <DataGrid
                                :columns="options.col_dshd"
                                v-bind:dataSource="options.dshd"
                                :showColumnCheckbox="false"
                                :enable-paging-server="false"
                                :allowPaging="true"
                                :showFilter="true"
                                :enabledSelectFirstRow="true"
                                @rowSelected="rowSelected"
                            >
                            </DataGrid>
                        </div>

                    </div>
                </div>
            


  </b-modal>


    </div>
</template>
<script>
import api from "./api";
import moment from "moment";


export default {
name:'PopupHDDT',
  data() {
    return {

    ddlLoaiHopDong:"",
    ddlDichVu:"",
    ddlTraCuuTheo:"",
    txtMaGD:"",
    ddlTrangThaiHD:"",
    txtNgayYC:moment(new Date()).format('MM/DD/YYYY'),
    txtDenNgay:moment(new Date()).format('MM/DD/YYYY'),
    txtThongTinTraCuu:"",
    txtMaKH:"",
    ddlLoaiKH:"",
    txtTenKH:"",
    txtDiaChiKH:"",
    ddlHinhThucTT:"",
    ddlKenhThu:"",
    ddlNganHang:"",
    txtSoTK:"",
    txtMST:"",
    txtSDT:"",
    txtTongTien:"",
    txtTienBangChu:"",
    ddlThaoTac:"",  
    ddlMauSo:"",
    txtSoHoaDon:"0",
    ddlKyHieu:"",
    chkLoaiHopDong:false,
    chkDichVu:false,
    chkNgayYC:false,
    chkTrangThaiHD:false,

    button:{
        timkiem:true,
        chapnhan:true
    },

    frm:{
        hdkh_id:0,
        loaihd_id:0,
        vma_gd:""
    },

      options: {
        loaihopdong: [],
        dichvu: [],
        tracuutheo:[],
        trangthaihd:[],
        loaikh:[],
        hinhthuctt:[],
        kenhthu:[],
        nganhang:[],
        thaotac:[],
        mauso:[],
        kyhieu:[],


        dshd:[],
        col_dshd: [
          {
            fieldName: "MA_GD",
            headerText: "Mã giao dịch",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "MA_KH",
            headerText: "Mã khách hàng",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "TEN_KH",
            headerText: "Tên khách hàng",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "DIACHI_KH",
            headerText: "Địa chỉ khách hàng",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "NGAY_YC",
            headerText: "Ngày yêu cầu",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },          
        ],

        dstb:[],
        col_dstb: [
          {
            fieldName: "PRODNAME",
            headerText: "Khoản mục thanh toán",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "TIEN",
            headerText: "Tiền",
            type: "Number",
            format: "N",
            textAlign: "right",
            allowSorting: true,
            autoFit: true,

          },
          {
            fieldName: "VAT",
            headerText: "Vat",
            type: "Number",
            format: "N",
            textAlign: "right",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "TONGTIEN",
            headerText: "Tổng tiền",
            type: "Number",
            format: "N",
            textAlign: "right",
            allowSorting: true,
            autoFit: true,
          }         
        ],


      },
    };
  },
  created() {
      this.hoadon_dientu_load()
  },

  watch: {
      ddlMauSo(newval)
      {
          this.hoadon_dientu_change_mauso()
      }
  },

  methods: {

    async hoadon_dientu_load() {
      const res =  await api.hoadon_dientu_load(this.axios, {
        })
            try
            {
                let ds = res.data.data ? res.data.data : [];

                let dvvt = JSON.parse(ds.js_returnds).DS_DICHVU_VT
                let ar_dvvt=[]
                dvvt.forEach(function (it) {
                ar_dvvt.push({ id: it.DICHVUVT_ID, text: it.TEN_DVVT });
                });
                this.options.dichvu = ar_dvvt;
                this.ddlDichVu = ar_dvvt[0].id;


                let httt = JSON.parse(ds.js_returnds).DS_HINHTHUC_TRA
                let ar_httt=[]
                httt.forEach(function (it) {
                ar_httt.push({ id: it.HT_TRA_ID, text: it.HT_TRA });
                });
                this.options.hinhthuctt = ar_httt;
                this.ddlHinhThucTT = ar_httt[0].id;


                let dsms = JSON.parse(ds.js_returnds).DS_INV_PATTERN
                let ar_dsms=[]
                dsms.forEach(function (it) {
                ar_dsms.push({ id: it.PATTERN_ID, text: it.PATTERN });
                });
                this.options.mauso = ar_dsms;
                this.ddlMauSo = ar_dsms[0].id;


                let dskt = JSON.parse(ds.js_returnds).DS_KENHTHU
                let ar_dskt=[]
                dskt.forEach(function (it) {
                ar_dskt.push({ id: it.KENHTHU_ID, text: it.KENHTHU });
                });
                this.options.kenhthu = ar_dskt;
                this.ddlKenhThu = ar_dskt[0].id;

                let dstc = JSON.parse(ds.js_returnds).DS_KIEU_TK
                let ar_dstc=[]
                dstc.forEach(function (it) {
                ar_dstc.push({ id: it.DIEUKIEN, text: it.KIEU_TK });
                });
                this.options.tracuutheo = ar_dstc;
                this.ddlTraCuuTheo = ar_dstc[0].id;


                let dslhd = JSON.parse(ds.js_returnds).DS_LOAI_HD
                let ar_dslhd=[]
                dslhd.forEach(function (it) {
                ar_dslhd.push({ id: it.LOAIHD_ID, text: it.TEN_LOAIHD });
                });
                this.options.loaihopdong = ar_dslhd;
                this.ddlLoaiHopDong = ar_dslhd[0].id;


                let dslkh = JSON.parse(ds.js_returnds).DS_LOAI_KH_2
                let ar_dslkh=[]
                dslkh.forEach(function (it) {
                ar_dslkh.push({ id: it.LOAIKH_ID, text: it.TEN_LOAIKH });
                });
                this.options.loaikh = ar_dslkh;
                this.ddlLoaiKH = ar_dslkh[0].id;


                let dsnh = JSON.parse(ds.js_returnds).DS_NGANHANG
                let ar_dsnh=[]
                dsnh.forEach(function (it) {
                ar_dsnh.push({ id: it.NGANHANG_ID, text: it.TEN_NH });
                });
                this.options.nganhang = ar_dsnh;
                this.ddlNganHang = ar_dsnh[0].id;


                let dstt = JSON.parse(ds.js_returnds).DS_THAOTAC
                let ar_dstt=[]
                dstt.forEach(function (it) {
                ar_dstt.push({ id: it.THAOTAC_ID, text: it.THAOTAC });
                });
                this.options.thaotac = ar_dstt;
                this.ddlThaoTac = ar_dstt[0].id;


                let dstthd = JSON.parse(ds.js_returnds).DS_TRANGTHAI_HD
                let ar_dstthd=[]
                dstthd.forEach(function (it) {
                ar_dstthd.push({ id: it.TTHD_ID, text: it.TRANGTHAI_HD });
                });
                this.options.trangthaihd = ar_dstthd;
                this.ddlTrangThaiHD = ar_dstthd[0].id;
            }
            catch(e)
            {

            }
    },

    async hoadon_dientu_change_mauso() {
       this.loading(true);
      let json = {
          "pattern_id":this.ddlMauSo
      }
  
      const res = await api.hoadon_dientu_change_mauso(this.axios, {
            "p_ds_para": JSON.stringify(json)
        })
          let dsach = res.data.data ? res.data.data : [];
          let danhsach =   JSON.parse(dsach.js_returnds).DS_EINVOICE_SERI

          let item=[]
            danhsach.forEach(function (ds) {
            item.push({ id: ds.SERI_ID, text: ds.SERI });
            }); 
          
          this.options.kyhieu = item
          this.ddlKyHieu = item[0].id
          this.loading(false);
    },


    async HienThiDanhSacHDKH()
    {
        let dieukien = "";
        let tthd_id = 0;
        let dichvuvt_id = 0;
        let vtungay_yc;
        let vdenngay_yc;
        let str = "";

        if (this.txtThongTinTraCuu == "")
            dieukien = "0";
        else
            dieukien = this.ddlTraCuuTheo.replace(":values", this.txtThongTinTraCuu);

        if (this.chkLoaiHopDong == true)
            this.frm.loaihd_id = this.ddlLoaiHopDong
        else
            this.frm.loaihd_id = 0;

        if (this.chkDichVu == true)
            dichvuvt_id = this.ddlDichVu
        else
            dichvuvt_id = 0;

        if (this.chkTrangThaiHD == true)
            tthd_id = this.ddlTrangThaiHD
        else
            tthd_id = 0;

        if (this.chkNgayYC == true)
        {
            vtungay_yc = moment(this.txtNgayYC).format('MM/DD/YYYY')
            vdenngay_yc = moment(this.txtDenNgay).format('MM/DD/YYYY')
        }
        else
        {
            vtungay_yc = "";
            vdenngay_yc = "";
        }

      let json = {
          "dktracuu":dieukien,
          "thongtintc":this.txtThongTinTraCuu,
          "loaihd_id": this.frm.loaihd_id,
          "tthd_id": tthd_id,
          "dichvuvt_id":dichvuvt_id,
          "tungay_yc":vtungay_yc,
          "denngay_yc": vdenngay_yc
      }
    this.loading(true);

      console.log('json',JSON.stringify(json))

     const res = await api.hienthidanhsach_hdkh(this.axios, {
            "p_ds_para": JSON.stringify(json)
        })
        let danhsach = res.data.data ? res.data.data : [];
        this.options.dshd = JSON.parse(danhsach.js_returnds).DS_HDKH
        console.log('danhsach',danhsach,this.options.dshd,JSON.stringify(json))
        if(this.options.dshd.length > 0)
        {
            this.frm.hdkh_id = this.options.dshd[0].HDKH_ID
            this.frm.loaihd_id = this.options.dshd[0].LOAIHD_ID
            this.txtMaGD = this.options.dshd[0].MA_GD 
            this.frm.vma_gd = this.options.dshd[0].MA_GD 
            this.txtDiaChiKH = this.options.dshd[0].DIACHI_KH
            if (this.options.dshd[0].LOAIKH_ID !== null) 
            {
                this.ddlLoaiKH = this.options.dshd[0].LOAIKH_ID
            }
            else
            {
                this.ddlLoaiKH = 0  
            }
            this.txtMaKH = this.options.dshd[0].MA_KH
            this.txtSDT = this.options.dshd[0].SO_DT
            this.txtTenKH = this.options.dshd[0].TEN_KH
            if (this.options.dshd[0].HT_TRA_ID !== null) 
            {
                this.ddlHinhThucTT = this.options.dshd[0].HT_TRA_ID
            }
            else
            {
                this.ddlHinhThucTT = 0  
            }  

            if (this.options.dshd[0].KENHTHU_ID !== null) 
            {
                this.ddlKenhThu = this.options.dshd[0].KENHTHU_ID
            }
            else
            {
                this.ddlKenhThu = 0  
            }       
            this.txtMST = this.options.dshd[0].MST_TT
            if (this.options.dshd[0].NGANHANG_ID !== null) 
            {
                this.ddlNganHang = this.options.dshd[0].NGANHANG_ID
            }
            else
            {
                this.ddlNganHang = 0  
            }     
            this.txtSoTK = this.options.dshd[0].STK_TT

            this.hienthidanhsach_hdtb()

            this.loading(false);
        }
        else{
            this.loading(false);
            this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
        }

    },


    rowSelected(subject) {
        this.frm.hdkh_id = subject.data.HDKH_ID
        this.frm.loaihd_id = subject.data.LOAIHD_ID
        this.txtMaGD = subject.data.MA_GD 
        this.frm.vma_gd = subject.data.MA_GD 
        this.txtDiaChiKH = subject.data.DIACHI_KH
        if (subject.data.LOAIKH_ID !== null) 
        {
            this.ddlLoaiKH = subject.data.LOAIKH_ID
        }
        else
        {
            this.ddlLoaiKH = 0  
        }
        this.txtMaKH = subject.data.MA_KH
        this.txtSDT = subject.data.SO_DT
        this.txtTenKH = subject.data.TEN_KH
        if (subject.data.HT_TRA_ID !== null) 
        {
            this.ddlHinhThucTT = subject.data.HT_TRA_ID
        }
        else
        {
            this.ddlHinhThucTT = 0  
        }  

        if (subject.data.KENHTHU_ID !== null) 
        {
            this.ddlKenhThu = subject.data.KENHTHU_ID
        }
        else
        {
            this.ddlKenhThu = 0  
        }       
        this.txtMST = subject.data.MST_TT
        if (subject.data.NGANHANG_ID !== null) 
        {
            this.ddlNganHang = subject.data.NGANHANG_ID
        }
        else
        {
            this.ddlNganHang = 0  
        }     
        this.txtSoTK = subject.data.STK_TT


        this.hienthidanhsach_hdtb()

    },

    async hienthidanhsach_hdtb() {
       this.loading(true);
      let json = {
          "hdkh_id":this.frm.hdkh_id
      }
      const res = await api.hienthidanhsach_hdtb(this.axios, {
            "p_ds_para": JSON.stringify(json)
        })
          let dsach = res.data.data ? res.data.data : [];
          let danhsach = JSON.parse(dsach.js_returnds).DS_HDTB
          this.options.dstb = danhsach

          if(this.options.dstb.length > 0)
          {
              this.txtTongTien = this.options.dstb[0].TONGTIEN
              const res = await api.doi_so_sang_chu(this.axios, {
                "money": Number(this.txtTongTien)
                })
                 this.txtTienBangChu = res.data.data ? res.data.data : [];
              
          }
          else
          {
              this.txtTongTien = 0
              this.txtTienBangChu= "Không đồng"
          }
          


          this.loading(false);
    },


        showModal() {
            this.$bvModal.show("hoadondt")
        },
        hideModal() {
            this.$bvModal.hide("hoadondt")
        },


    clickButton(key) {
      if (key == "timkiem") {
          this.HienThiDanhSacHDKH()
      }


      if (key == "chapnhan") {
            let item = {
                "vchapnhan": true,
                "vmagd":this.txtMaGD,
                "vhdkh_id": this.frm.hdkh_id
            }
            this.$emit('chapnhan',item)
            this.$bvModal.hide("hoadondt");
      }
    },



  },
};
</script>
<style>
/* @import "https://cdn.syncfusion.com/ej2/material.css"; */
.none-padding {
  padding: 0 !important;
}
.modal-dialog {
  border-radius: 0px !important;
  width: 100% !important;
  max-width: 1220px !important;
}

li.non-active a {
  color: #d3d3d3 !important;
}

/* .e-row:active, e-row:focus{background: #8d8b8b !important} */


.e-grid td.e-active{ 
  background-color: #8d8b8b !important}

</style>
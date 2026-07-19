<template>
  <div>
    <vue-bread-crumb :header="header" />
    <vue-nav>
      <ul class="list">
        <li
          @click="clickButton('nhapmoi')"
          :class="this.button.tsbtnNhapMoi ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-file-attach"></span>Nhập mới </a>
        </li>
        <li
          @click="clickButton('dangky')"
          :class="this.button.btnDangky ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-reload1"></span>{{ lblDangKy }}</a>
        </li>

        <li
          @click="clickButton('huy')"
          :class="this.button.tsbtnHuy ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-file-arrow-right1"></span>Hủy</a>
        </li>

      </ul>

    </vue-nav>

        <div class="page-content">
            <div class="box-form">
                <div class="legend-title">Thông tin khách hàng</div>
                <div class="row">
                    <div class="col-sm-7 col-12">
                        <div class="row">
                            <div class="col-sm-7 col-12">
                                <div class="info-row">
                                    <div class="key w120">Số máy (*)</div>
                                    <div class="value w20">
                                        <span class="form-control padt6 bold">84</span>
                                    </div>
                                    <div class="value">
                                        <input type="text" v-model="txtSoMay" v-on:keyup="txtSoMay_KeyPress" class="form-control highlight" />
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key w130">Loại khách hàng (*)</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <SelectExt
                                                v-model="cboLoaiKH"
                                                :options="this.options.loaiKH">
                                                <option disabled value="0">Select one</option>
                                            </SelectExt>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-7 col-12">
                                <div class="info-row">
                                    <div class="key w120">Người đại diện (*)</div>
                                    
                                    <div class="value">
                                        <input type="text" v-model="txtDaidien" class="form-control highlight" />
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key w130">Call Forward (*)</div>
                                    <div class="value">
                                        <input type="text" v-model="txtcall" class="form-control highlight" />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w120">Tên KH (*)</div>
                            <div class="value">
                                <input type="text" v-model="txttenkh" class="form-control highlight" />
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w120">Địa chỉ (*)</div>
                            <div class="value">
                                <input type="text" v-model="txtDiachiKH" class="form-control highlight" />
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-7 col-12">
                                <div class="info-row">
                                    <div class="key w120">Ngân hàng</div>
                        
                                    <div class="value">
                                        <div class="select-custom">
                                            <SelectExt
                                                v-model="cboNganH"
                                                :options="this.options.nganhang"
                                            >
                                                <option disabled value="0">Select one</option>
                                            </SelectExt>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key w130">{{lbltaikhoan }}</div>
                                    <div class="value">
                                        <input type="text" v-model="txtTaikhoan" :disabled="disabledTaikhoan" class="form-control highlight" />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-5 col-12">
                        
                        <div class="info-row">
                            <div class="key w160">Loại (*)</div>
                            <div class="value">
                                <div class="select-custom">
                                    <SelectExt
                                            v-model="cboLoai"
                                            :options="this.options.Loai">
                                            <option disabled value="0">Select one</option>
                                    </SelectExt>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w160">Mã số thuế (*)</div>
                            <div class="value">
                                <input type="text" v-model="txtmst" class="form-control highlight" />
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-7 col-12">
                                <div class="info-row">
                                    <div class="key w160">Điện thoại liên hệ (*)</div>
                                    <div class="value">
                                        <input type="text" v-model="txtdienthoai" class="form-control highlight" />
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key w70">Người GT</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <SelectExt
                                                v-model="cboNguoiGT"
                                                :options="this.options.nguoigioithieu"
                                            >
                                                <option disabled value="0">Select one</option>
                                            </SelectExt>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w160">Giấy phép kinh doanh (*)</div>
                            <div class="value">
                                <input type="text" v-model="txtGPKD" class="form-control highlight" />
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w160">Ghi chú</div>
                            <div class="value">
                                <input type="text" v-model="txtghichu" class="form-control highlight" />
                            </div>
                        </div>
                    </div>
                </div>
                <div class="legend-title mart20">Tra cứu thuê bao Sip Trunking</div>
                <div class="row">
                    <div class="col-sm-7 col-12">
                        <div class="info-row">
                            <div class="key w120">Số máy</div>
                            <div class="value w20">
                                <span class="form-control padt6 bold">84</span>
                            </div>
                            <div class="value">
                                <input type="text" v-model="txtsomaytc" class="form-control highlight" />
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w120">Từ ngày</div>
                            <div class="value">
                                <div class="input-icon-right">
                                    <vue-date
                                            format="DD/MM/YYYY h:mm:ss"
                                            type="datetime"
                                            v-model="dtpTungay"
                                    ></vue-date>   
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w120">Loại</div>
                            <div class="value">
                                <div class="select-custom">
                                    <SelectExt
                                            v-model="cboLoaitc"
                                            :options="this.options.Loaitc">
                                            <option disabled value="0">Select one</option>
                                    </SelectExt>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-5 col-12">
                        <div class="info-row">
                            <div class="key w160">Call Forward</div>
                            <div class="value">
                                <input type="text" v-model="txtcalltc" class="form-control highlight" />
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w160">Đến ngày</div>
                            <div class="value">
                                <div class="input-icon-right">
                                    <vue-date
                                            format="DD/MM/YYYY h:mm:ss"
                                            type="datetime"
                                            v-model="dtpDenngay"
                                    ></vue-date>  
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w160">User thực hiện</div>
                            <div class="value">
                                <input type="text" v-model="txtuser" class="form-control highlight" />
                            </div>
                        </div>
                    </div>
                </div>
                <div class="group-buttons -bottom tright">
                    <button class="btn btn-second normal" @click.prevent="btnTracuu_Click">Tra cứu</button>
                </div>
            </div>
            <div class="nav tabs tab-over">
                <a href="#" class="active">Thông tin tra cứu Sip Trunking</a>
            </div>
            <div class="tab-content">
                <div class="table-content">
                    <ejs-grid
                        ref="grdDKSipTrunking"
                        :allowResizing="true"
                        width="100%"
                        :dataSource="this.grdDKSipTrunking"
                        locale="vi-VN"
                        :allowSorting="true"
                        :allowFiltering="true"
                        :allowPaging="true"
                        :pageSettings="{
                        pageSize: 10,
                        pageSizes: [10, 50, 100, 200],
                        }"
                        :gridLines="1"
                        :editSettings="{ allowEditing: true, mode: 'Normal' }"
                    >
                        <e-columns>
                        <e-column
                            headerTextAlign="center"
                            textAlign="left"
                            field="ProdName"
                            headerText="Số TB"
                            allowSorting="true"
                            allowFiltering="true"
                        ></e-column>
                        <e-column
                            headerTextAlign="center"
                            textAlign="left"
                            field="ProdUnit"
                            headerText="Loại khách hàng"
                            allowSorting="true"
                            allowFiltering="true"
                        ></e-column>
                        <e-column
                            headerTextAlign="center"
                            textAlign="left"
                            field="ProdName"
                            headerText="Tên khách hàng"
                            allowSorting="true"
                            allowFiltering="true"
                        ></e-column>
                        <e-column
                            headerTextAlign="center"
                            textAlign="left"
                            field="ProdUnit"
                            headerText="Người đại diện"
                            allowSorting="true"
                            allowFiltering="true"
                        ></e-column>
                        <e-column
                            headerTextAlign="center"
                            textAlign="left"
                            field="ProdUnit"
                            headerText="Điện thoại LH"
                            allowSorting="true"
                            allowFiltering="true"
                        ></e-column>
                        <e-column
                            headerTextAlign="center"
                            textAlign="left"
                            field="ProdUnit"
                            headerText="Địa chỉ"
                            allowSorting="true"
                            allowFiltering="true"
                        ></e-column> 
                        <e-column
                            headerTextAlign="center"
                            textAlign="left"
                            field="ProdUnit"
                            headerText="Mã số thuế"
                            allowSorting="true"
                            allowFiltering="true"
                        ></e-column>     
                        <e-column
                            headerTextAlign="center"
                            textAlign="left"
                            field="ProdUnit"
                            headerText="Số CF"
                            allowSorting="true"
                            allowFiltering="true"
                        ></e-column>       
                        <e-column
                            headerTextAlign="center"
                            textAlign="left"
                            field="ProdUnit"
                            headerText="Loại"
                            allowSorting="true"
                            allowFiltering="true"
                        ></e-column>     
                        <e-column
                            headerTextAlign="center"
                            textAlign="left"
                            field="ProdUnit"
                            headerText="Ngày TH"
                            allowSorting="true"
                            allowFiltering="true"
                        ></e-column>     
                        <e-column
                            headerTextAlign="center"
                            textAlign="left"
                            field="ProdUnit"
                            headerText="Ghi chú"
                            allowSorting="true"
                            allowFiltering="true"
                        ></e-column>                                                                                                             
                        </e-columns>
                    </ejs-grid>
                </div>
            </div>
        </div>


    <frmDSTBCungMa ref="frmDSTBCungMa"></frmDSTBCungMa>

  </div>
</template>
<script>

import api from "./api";
import define from "./define";
import Enum from "./Enum";
import moment from "moment";
import frmDSTBCungMa from '@/modules/EXTS/HOPDONG/ThayDoiThongTinKhuyenMai/frmDSTBCungMa/frmDSTBCungMa.vue'

import {
  Sort,
  Filter,
  Resize,
  Group,
  Freeze,
  CommandColumn,
  ExcelExport,
  Edit,
  Aggregate,
  Page,
  Toolbar,
} from "@syncfusion/ej2-vue-grids";
import { L10n } from "@syncfusion/ej2-base";


L10n.load({
  "vi-VN": {
    grid: {
      EmptyRecord: "Không có bản ghi nào để hiển thị",
      Item: "Bản ghi",
      Items: "Bản ghi",
      FilterbarTitle: "",
    },
  },
});


export default {

  data() {
    return {
        ...define,
        // ...Enum,
        txtSoMay:"",
        cboLoaiKH:"",
        txtDaidien:"",
        txtcall:"",
        txttenkh:"",
        txtDiachiKH:"",
        cboNganH:"",
        cboLoai:"",
        txtmst:"",
        txtdienthoai:"",
        cboNguoiGT:"",
        txtGPKD:"",
        txtghichu:"",
        txtsomaytc:"",
        dtpTungay:"",
        dtpDenngay:"",
        cboLoaitc:"",
        txtcalltc:"",
        txtuser:"",
        grdDKSipTrunking:[],
        lblDangKy:"Đăng ký",
        lbltaikhoan:"",
        txtTaikhoan:"",
        ds:[],
        dichvuvt_id: Enum.DichVuVienThong.DI_DONG,
        disabledTaikhoan:true,
        loi_sinh_magd:false,
        dsHDKH:[],
        dsHDTB:[],
        hdkh_id:"",
        hdtb_id:"",
        huonggiao_id:5601,


        options: {
        loaiKH: [],
        nganhang:[],
        nguoigioithieu:[],
        Loai:[],
        Loaitc:[],
        }
    };
  },

  created() {

        console.log('this.Enum',Enum.DichVuVienThong.DI_DONG)

        this.cbLoai();
        this.siptrungking_dichvu_loaikh();
        this.siptrungking_dichvu_nganhang();
        this.siptrungking_dichvu_nguoi_gt();
  },
  watch: {
    cboNganH(newval) {
      if (newval != 0) 
      {
        this.lbltaikhoan = "Tài khoản (*)";
        this.disabledTaikhoan = false;
      }
      else
      {
        this.lbltaikhoan = "Tài khoản";
        this.disabledTaikhoan = true;
        this.txtTaikhoan = "";
      }

    },

  },

  components: {
    frmDSTBCungMa
  },


  methods: {

    //load Combo Loại
    async cbLoai() {
        let ar_ds = []
        ar_ds.push({ id: 0, text: 'Không chọn' });
        ar_ds.push({ id: 1, text: 'Hòa mạng' });
        ar_ds.push({ id: 2, text: 'Chuyển loại' });
        this.options.Loai = ar_ds;
        this.cboLoai = ar_ds[0].id;

        this.options.Loaitc = ar_ds;
        this.cboLoaitc = ar_ds[0].id;

    },

    //load loại KH
    async siptrungking_dichvu_loaikh() {
      const res = await api.siptrungking_dichvu_loaikh(this.axios, {

      });
      let danhsach = res.data.data ? res.data.data : [];

      let ar_ds = []
      ar_ds.push({ id: 0, text: 'Không chọn' });
      danhsach.forEach(function (dc) {
        ar_ds.push({ id: dc.LOAIKH_ID, text: dc.LOAI_KH });
      });
      
      this.options.loaiKH = ar_ds;
      this.cboLoaiKH = ar_ds[0].id;
    },

    //load ngân hàng
    async siptrungking_dichvu_nganhang() {
      const res = await api.siptrungking_dichvu_nganhang(this.axios, {

      });
      let danhsach = res.data.data ? res.data.data : [];

      let ar_ds = []
      ar_ds.push({ id: 0, text: 'Không chọn' });
      danhsach.forEach(function (dc) {
        ar_ds.push({ id: dc.LOAIKH_ID, text: dc.LOAI_KH });
      });
      
      this.options.nganhang = ar_ds;
      this.cboNganH = ar_ds[0].id;
    },

    //load ngân hàng
    async siptrungking_dichvu_nganhang() {
      const res = await api.siptrungking_dichvu_nganhang(this.axios, {
      });
      let danhsach = res.data.data ? res.data.data : [];

      let ar_ds = []
      ar_ds.push({ id: 0, text: 'Không chọn' });
      danhsach.forEach(function (dc) {
        ar_ds.push({ id: dc.NGANHANG_ID, text: dc.TEN });
      });
      
      this.options.nganhang = ar_ds;
      this.cboNganH = ar_ds[0].id;
    },
    //load người giới thiệu
    async siptrungking_dichvu_nguoi_gt() {
      const res = await api.siptrungking_dichvu_nguoi_gt(this.axios, {
        donviql_id : this.$root.token.getDonViDuLieuID()
      });
      let danhsach = res.data.data ? res.data.data : [];

      let ar_ds = []
      ar_ds.push({ id: 0, text: 'Không chọn' });
      danhsach.forEach(function (dc) {
        ar_ds.push({ id: dc.MA_NGUOI, text: dc.TEN_NGUOI });
      });
      
      this.options.nguoigioithieu = ar_ds;
      this.cboNguoiGT = ar_ds[0].id;
    },
    
    async Get_TTND() {
      const res = await api.thongtin_nguoidung2(this.axios, {});
      return res.data.data ? res.data.data : [];
    },

    SetButton(status)
    {
        switch (status)
            {
                case 0: // nhap moi
                    this.button.tsbtnNhapMoi= true;
                    this.button.btnDangky= true;
                    this.button.tsbtnHuy= false;
                    this.Clear();
                    break;
            }
    },
    Clear()
    {
            // Form đăng ký - Hủy
            
            this.txtDaidien.Text = "";
            this.txttenkh.Text = "";
            this.txtDiachiKH.Text = "";
            this.txtcall.Text = "";
            this.siptrungking_dichvu_loaikh();
            this.siptrungking_dichvu_nganhang();

            this.txtmst.Text = "";
            this.txtdienthoai.Text = "";
            this.txtGPKD.Text = "";
            this.txtghichu.Text = "";

            this.cboLoai = 0;
            this.siptrungking_dichvu_nguoi_gt();

            // Form tra cứu
            this.txtsomaytc.Text = "";
            this.txtcalltc.Text = "";
            this.dtpTungay.EditValue = "";
            this.dtpDenngay.EditValue = "";
            this.cboLoaitc.ItemIndex = 0;
            this.txtuser.Text = "";

            this.grdDKSipTrunking = [];
            this.lblDangKy = "Đăng ký";
            this.button.tsbtnHuy= false;
    },


    async KIEMTRA_MA_TB_LAP_HOPDONG_KHAC() {
        const res = await api.kiemtra_thuebao_lap_hopdong_khac(this.axios, {
            maTb : this.txtSoMay.trim(),
            maGd : "0",
            dichVuVtId : Enum.DichVuVienThong.DI_DONG,
            loaiHdId : Enum.LoaiHopDong.DD_SIPTRUNKING
        });

        console.log('maTb' , this.txtSoMay.trim(),'maGd' , "0",'dichVuVtId' , Enum.DichVuVienThong.DI_DONG, 'loaiHdId' , Enum.LoaiHopDong.DD_SIPTRUNKING)

        let danhsach = res.data.data ? res.data.data : [];
        if(danhsach.length > 0)
        {
            return true
        }
        else
        {
            return false
        }
    },

    txtSoMay_KeyPress: function (event) {
      if (event.key == "Enter") {
        let ktra =  this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC()
        if(ktra == true)
        {
            return
        }
        this.SetButton(0);
        this.HienThiTT_DanhBa(this.txtSoMay.trim())

      }
    },

    async HienThiTT_DanhBa(ma_tb)
    {
        const res = await api.siptrungking_dichvu_nguoi_gt(this.axios, {
            so_tb : "84" + this.txtSoMay.trim()
        });
        let tracuu_ccbs = res.data.data ? res.data.data : [];

        const res1 = await api.lay_danhba_theo_matb(this.axios, {
            maTb : ma_tb,
            dichVuVtId : dichvuvt_id,
            donViDlId : 0
        });
        this.ds = res1.data.data ? res1.data.data : [];
        if(ds.length > 0)
        {
            if(ds.length > 1)
            {
                this.loading(false)
                let kq = await this.$refs['frmDSTBCungMa'].show(dsDB)
                if (kq.ok == true && kq.data)
                {
                    this.ds = kq.data
                }
                this.loading(true)
            }

            if ( this.ds[0].trangthaitb_id  == Enum.TrangThaiTB.THANHLY 
                 || this.ds[0].trangthaitb_id  == Enum.TrangThaiTB.THANHLY_NO 
                 || this.ds[0].trangthaitb_id  == Enum.TrangThaiTB.DOISO )
		        {
			        this.$toast.error("Thuê bao có mã: " + this.ds[0].ma_tb
								        + " đang ở trạng thái: " + this.ds[0].trangthai_tb
								        + "\nBạn không thể thực hiện cập nhật TT thuê bao này");
			        return;
		        }

            // Đổ dữ liệu tra cứu lên lưới hiển thị:    
            if(tracuu_ccbs.length > 0)
            {
                this.txtDaidien = tracuu_ccbs[0].dai_dien
                this.txttenkh = tracuu_ccbs[0].ten_kh
                this.txtDiachiKH = tracuu_ccbs[0].dia_chi
                this.txtmst = tracuu_ccbs[0].ma_so_thue
                this.txtdienthoai = tracuu_ccbs[0].sdt_lienhe
                this.txtGPKD = tracuu_ccbs[0].giay_dkkd
                this.txtghichu = tracuu_ccbs[0].ghi_chu
                this.txtcall = tracuu_ccbs[0].so_cf
                this.cboNganH = tracuu_ccbs[0].nganhang_id

                const res2 = await api.siptrungking_dichvu_nguoi_gt(this.axios, {
                    donviql_id : this.$root.token.getDonViDuLieuID()
                });
                let nguoi_gtmap = res2.data.data ? res2.data.data : [];
                for(var i=0; i < nguoi_gtmap.length;i++)
                {
                    if (nguoi_gtmap[i].ORDS == tracuu_ccbs[0].nguoi_gt)
                    {
                        this.cboNguoiGT = nguoi_gtmap[i].MA_NGUOI
                    }
                }
                if (cboNganH.EditValue.ToString() == "0")
                {
                    this.lbltaikhoan = "Tài khoản";
                    this.disabledTaikhoan = false;
                    this.txtTaikhoan = "";
                }
                else
                {
                    this.lbltaikhoan = "Tài khoản (*)";
                    this.disabledTaikhoan = true;
                    this.txtTaikhoan = tracuu_ccbs[0].tai_khoan
                }
                this.cboLoaiKH.EditValue = tracuu_ccbs[0].loaikh_id
                this.cboLoai.EditValue = tracuu_ccbs[0].loai
                this.lblDangKy = "Cập nhật";
                this.tsbtnHuy = true;
            }
            else
            {
                this.txtDaidien.Text = this.ds[0].nguoi_dd
                this.txttenkh.Text = this.ds[0].ten_kh
                this.txtDiachiKH.Text = this.ds[0].diachi_kh
                this.txtmst.Text = this.ds[0].mst_tt
                this.txtdienthoai.Text = this.ds[0].so_dt
                this.lblDangKy = "Đăng ký";
                this.tsbtnHuy = false;
                
            }
        }
        else
        {
            this.lblDangKy = "Đăng ký";
            this.tsbtnHuy = false;
            this.$toast.error("Không tìm thấy thông tin về mã thuê bao " + this.txtSoMay.trim());
            return;
        }
    },

    async btnDangky_Click()
    {
        try
        {
            let nganhang = this.cboNganH
            let nguoi_gt = this.cboNguoiGT
            if(this.txtDaidien.trim() == "" || this.txttenkh.trim() == "" || this.txtDiachiKH.trim() == "" || 
               this.txtcall.trim() == "" || this.txtmst.trim() == "" || this.txtdienthoai.trim() == "" || 
               this.txtGPKD.trim() == "" || this.cboLoai == 0 || this.cboLoaiKH == 0
            )
            {
                this.$toast.error("Chưa nhập đủ thông tin các trường có dấu (*) theo yêu cầu!");
                return;
            }
            if (this.cboNganH != 0 && this.txtTaikhoan.trim() == "")
            {
                this.$toast.error("Chưa nhập số tài khoản khách hàng!");
                return;
            }              
            if (this.cboNganH == 0)
            {
                nganhang = ""
            }    
            if (this.cboNguoiGT == 0)
            {
                nguoi_gt = ""
            }      
            // Check loại KH ở DHSX và CCBS
            const res = await api.sp_lay_loaikh_id_neo(this.axios, {}, this.txtSoMay.trim());
            let loaikh_dhsx = res.data.data ? res.data.data : [];

            if (loaikh_dhsx.length > 0)
            {
                if (loaikh_dhsx[0].loaikh_id_neo != this.cboLoaiKH)
                {
                    this.$toast.error("Loại khách hàng ĐHSXKD không phù hợp, hãy kiểm tra lại!");
                    return;
                }
            }
            // Đăng ký - cập nhật STK
            // Đăng ký thành công thì tạo dữ liệu.
            this.$confirm(`Bạn thật sự muốn ` + this.lblDangKy.tolower() + ` dịch vụ SipTrungking của thuê bao 84` + this.txtSoMay + ` ?` , {
                confirmButtonText: "Có",
                cancelButtonText: "Không",
                type: "warning",
            }).then(async () => {
                const res1 = await api.siptrungking_dichvu_themmoi(this.axios, {
                    nguoi_dai_dien : this.txtDaidien.trim(),
                    loai_kh : this.cboLoaiKH,
                    call_fw_number : this.txtcall.trim(),
                    ten_kh : this.txttenkh.trim(),
                    nganhang_id : nganhang,
                    tai_khoan : this.txtTaikhoan.trim(),
                    ma_nguoi_gt : nguoi_gt,
                    ma_so_thue : this.txtmst.trim(),
                    dienthoai_ld : this.txtdienthoai.trim(),
                    ghi_chu : this.txtghichu.trim(),
                    so_tb : this.txtSoMay.trim(),
                    loai : this.cboLoai,
                    dia_chi : this.txtDiachiKH.trim(),
                    giaydkkd : this.txtGPKD.trim() 
                });
                let stk_thuchien = res1.data.error_code ? res1.data.error_code : 0;
                if(stk_thuchien == "BSS-00000000")
                {
                    this.$toast.success(this.lblDangKy + " dịch vụ SipTrunking cho thuê bao " + this.txtSoMay.trim() + " thành công!");
                }
                else
                {
                    this.$toast.error("Lỗi CCBS: " + res1.data.message);
                    return;
                }
                if (ds.Tables[0].Rows.Count > 0)
                {
                    this.TaoDuLieu(true, Enum.LoaiHopDong.DD_SIPTRUNKING);
                    const res2 = await api.themmoi_hopdong_siptrunking(this.axios, {
                        "p_js_hdkh": this.dsHDKH,
                        "p_js_hdtb": this.dsHDTB,
                        "p_huonggiao_id": this.huonggiao_id
                    });

                    bangts.ExeSQL("update " + DatabaseConstants.DB2 + ".giaophieu set ttph_id = 4 where hdtb_id =" + hdtb_id);
                }
                this.SetButton(0);



            })


        }
        catch(e)
        {

        }
    },
    async TaoDuLieu(themmoi,loaihd)
    {
        this.TaoDuLieu_HDKH(themmoi, loaihd);
        if (this.loi_sinh_magd) return;
        this.TaoDuLieu_HDTB(themmoi);
    },

    //Tao du lieu cho bang HD_KHACHHANG
    async TaoDuLieu_HDKH (themmoi,loaihd)
    {
        let vmagd_temp = "";
        let ar_ds = []

        if (themmoi == true)
        {
            const res = await api.get_keys(this.axios, {
                keyname: "HD_KHACHHANG"
            });
            this.hdkh_id = res.data.data ? res.data.data : '';

            ar_ds.push({ HDKH_ID: this.hdkh_id});

            if (themmoi == true)
            {
                const res1 = await api.sinh_magd(this.axios, {
                    loaihd_id: loaihd
                });

                vmagd_temp = res1.data.data ? res1.data.data : '';
                if (vmagd_temp.contains("ERROR:"))
                {
                    this.$toast.error(vmagd_temp.split(':')[1]);
                    this.loi_sinh_magd = true;
                } 
                ar_ds.push({ MA_GD: vmagd_temp, MA_HD:""});
            }


            ar_ds.push({ MA_KH: this.ds[0].ma_kh,
                         KHACHHANG_ID : this.ds[0].khachhang_id,
                         TEN_KH : this.txttenkh,
                         DIACHI_KH : this.txtDiachiKH,
                         NGAYLAP_HD : moment(new Date()).format('DD/MM/YYYY') ,
                         NGAY_YC : moment(new Date()).format('DD/MM/YYYY') ,
                         DONVI_ID : this.$root.token.getDonViID(),
                         NHANVIEN_ID : this.$root.token.getNhanVienID(),
                         LOAIHD_ID : loaihd,
                         GHICHU : this.txtghichu.trim(),
                         NGUOI_CN : await this.$root.token.getUserName(),
                         NGAY_CN : moment(new Date()).format('DD/MM/YYYY') ,
                         MAY_CN : await this.$root.token.getMachine()
                        });

            this.dsHDKH = ar_ds

        }
    },


//TaoDuLieu_HD_THUEBAO
async TaoDuLieu_HDTB (themmoi)
{
    let ar_ds = []
    if (themmoi == true)
    {
        const res = await api.get_keys(this.axios, {
                keyname: "HD_THUEBAO"
            });
        this.hdtb_id = res.data.data ? res.data.data : '';
    }
    ar_ds.push({ 
        HDTB_ID : this.hdtb_id,
        HDKH_ID : this.hdkh_id,
        THUEBAO_ID : this.ds[0].thuebao_id,
        MA_TB : this.txtSoMay.trim(),
        TEN_TB : this.txttenkh.trim(),
        DIACHI_TB : this.txtDiachiKH.trim(),
        KIEULD_ID : Enum.KieuLapDat.THAYDOI_DVGT_DD,
        DOITUONG_ID : this.ds[0].doituong_id,
        TTHD_ID : Enum.TrangThaiHD.HOAN_THANH,
        LOAITB_ID : this.ds[0].loaitb_id,
        DICHVUVT_ID :Enum.DichVuVienThong.DI_DONG,
        DONVI_ID : this.ds[0].donvi_id,
        NGUOI_CN : await this.$root.token.getUserName(),
        NGAY_CN : moment(new Date()).format('DD/MM/YYYY') ,
        MAY_CN : await this.$root.token.getMachine(),
        IP_CN : await this.$root.token.getIP(),
        GHICHU : this.txtghichu.trim(),
        STATUS : 5
                });

    this.dsHDTB = ar_ds        
},

async btnTracuu_Click()
{
    try
    {
        let tracuu = "";
        let tungay = "";
        let denngay = "";
        this.grdDKSipTrunking = []
        if (this.cboLoaitc == 0)
        {
            tracuu = "";
        }
        else
        {
            tracuu = this.cboLoaitc;
        }
        if(this.dtpTungay != '' )
        {
            tungay =  moment(this.dtpTungay,"DD/MM/YYYY").format("DD/MM/YYYY")
        }
        if(this.dtpDenngay != '' )
        {
            denngay =  moment(this.dtpDenngay,"DD/MM/YYYY").format("DD/MM/YYYY")
        }

        const res = await api.siptrungking_dichvu_search(this.axios, {
            call_fw_number: this.txtcalltc.trim(),
            tu_ngay: tungay,
            so_tb: "84" + this.txtsomaytc.trim(),
            loai: tracuu,
            nguoi_th: this.txtuser.trim(),
            den_ngay: denngay
            });
        this.grdDKSipTrunking = res.data.data ? res.data.data : [];


    }
    catch(e)
    {

    }
},

async tsbtnHuy_Click()
{
    try
    {
        this.$confirm(`Bạn thật sự hủy dịch vụ SipTrungking của thuê bao 84` + this.txtSoMay + ` ?` , {
                confirmButtonText: "Có",
                cancelButtonText: "Không",
                type: "warning",
            }).then(async () => {

                const res = await api.siptrungking_dichvu_huy(this.axios, {
                    so_tb: "84" + this.txtSoMay
                });

                let stk_huy = res.data.error_code ? res.data.error_code : 0;
                if(stk_huy == "BSS-00000000")
                {
                    this.$toast.success("Hủy dịch vụ SipTrunking cho thuê bao " + this.txtSoMay.trim() + " thành công!");
                }
                else
                {
                    this.$toast.error("Lỗi CCBS: " + res.data.message);
                    return;
                }
                if(this.ds.length > 0)
                {
                    this.TaoDuLieu(true, Enum.LoaiHopDong.DD_HUYSTKING);

                    const res2 = await api.themmoi_hopdong_siptrunking(this.axios, {
                        "p_js_hdkh": this.dsHDKH,
                        "p_js_hdtb": this.dsHDTB,
                        "p_huonggiao_id": this.huonggiao_id
                    });

                    const res1 = await api.capnhat_giaophieu_ttph(this.axios, {
                        hdtb_id: this.hdtb_id
                    });

                }
                this.SetButton(0);
            })
    }
    catch(e)
    {

    }
},




    clickButton(key) {
        if (key == "nhapmoi") {
            this.SetButton(0);
        }

        if (key == "dangky") {
            this.btnDangky_Click();
        }

        if (key == "huy") {
            this.tsbtnHuy_Click()
        }
    }


  },
};
</script>
<style>
li.non-active a {
  color: #d3d3d3 !important;
}

.traphieu .page-content {
  position: unset;
}

.modal-dialog {
  border-radius: 0px !important;
  width: 100% !important;
  min-width: 720px !important;
}
</style>
<template src="./frmTraCuuTBKhieuNai.html"></template>
<script>
import Select2Ext from "@/modules/contract/setup/DeclareLandline/components/Select2Ext";
import DataGridCustom from "@/modules/contract/setup/DeclareLandline/components/DataGridCustom";
import VueDateExt from "@/modules/contract/setup/DeclareLandline/components/VueDateExt";
export default {
    components: {
        Select2Ext,
        DataGridCustom,
        VueDateExt
    },
    data() {
        return {
            colums: [
                {
                    headerText: "Số máy/Accc",
                    autoFit: true,
                    allowSorting: false,
                    fieldName: "ma_tb",
                },
                {
                    headerText: "Tên thuê bao",
                    autoFit: true,
                    allowSorting: false,
                    fieldName: "ten_tb",
                },
                {
                    headerText: "Địa chỉ thuê bao",
                    autoFit: true,
                    allowSorting: false,
                    fieldName: "diachi_tb",
                },
                {
                    headerText: "Dịch vụ VT" ,
                    allowSorting: false,
                    fieldName: "ten_dvvt",
                },
                {
                    headerText: "Trạng thái",
                    autoFit: true,
                    allowSorting: false,
                    fieldName: "ten_trangthai",
                },
            ],
            bangts : {},
            tchopdong : {},
            tcdanhba : {},
            tt_nd : {},
            gq_kn : {},
            hdtb_id: 0,
            hdkh_id: 0,
             ma_tb : 0,
             dichvu_vt: 0,
            chapnhan: 0,
             vkyhoadon: "",

            donvi_id : 0,
            nhanvien_id : 0,
            loaihd_id : 0,
            ttkn_id : 0,
            ngay_tn : "0",
            kieu : 0,
            donvi_dl_id : 0,
            dichvuvt_id : 0,
        }  
    },
    methods: {
        show() {
            this.$nextTick(() => {
                this.$refs.frmTraCuuTBKhieuNai.show()
                this.frmTraCuuTBKhieuNai_Load()
            })
        },
        Close() {
            let seleted = this.$refs.lvThueBao.getSelectedRecords()
            this.$emit("closePopup", seleted.length > 0 ? seleted[0] : {})
            this.$refs.frmTraCuuTBKhieuNai.hide()
        },
        async frmTraCuuTBKhieuNai_Load()
        {
            try
            {
                //txtNgayLapHD.value = ;//tt_nd.ngay_cn.ToString("dd/MM/yyyy");
                
                this.$refs.dtpDenNgay.disabled = true
                this.$refs.dtpNgayTN.disabled = true
                let rs
                rs = await this.axios.post("web-gqkn/TraCuuPhieuKN/sp_lay_ds_trangthai_khieunai")
                this.$refs.cboTrangThai.HT_COMBOBOX(rs.data.data, "trangthai_kn", "ttkn_id")

                rs = await this.axios.post("web-gqkn/TraCuuPhieuKN/sp_lay_ds_dichvu_vt")
                this.$refs.cboDichVuVT.HT_COMBOBOX(rs.data.data, "ten_dvvt", "dichvuvt_id")

                rs = await this.axios.post("web-gqkn/TraCuuPhieuKN/sp_ht_kieu_tk", {
                    "p_nhom_id":1
                })
                this.$refs.cboDKTraCuu.HT_COMBOBOX(rs.data.data, "kieu_tk", "dieukien")

                if (this.dichvuvt_id != 0)
                {
                    this.$refs.chkDVVT.checked = true;
                    this.$refs.cboDichVuVT.value = this.dichvuvt_id;
                }

                if (this.ttkn_id != 0)
                {
                    this.$refs.chkTrangThai.checked = true;
                    this.$refs.cboTrangThai.value = this.ttkn_id;
                }

                if (this.ngay_tn != "0")
                {
                    this.$refs.chkNgayLap.checked = true;                    
                }

                if (this.kieu == 1)
                    this.HienThiDanhSacHDKH();
                else
                {
                    if (this.kieu == 0)
                    {
                        this.$refs.tsbtnCapNhan.style.display = "none"
                    }
                }

            }
            catch (err)
            {
                console.log(err)
            }
        },

        frmTraCuuHopDong_Activated()
        {
            this.$refs.cboDKTraCuu.focus
        },

        frmTraCuuTBKhieuNai(_loaihd_id, _ttkn_id, _donvi_id, _nhanvien_id, _ngay_tn, _kieu, _donvi_dl_id, _dichvuvt_id)
            //SonNMP fix nhiều constructor 01/03/2018
        {
            this.loaihd_id = _loaihd_id;
            this.ttkn_id = _ttkn_id;
            this.donvi_id = _donvi_id;
            this.nhanvien_id = _nhanvien_id;
            this.ngay_tn = _ngay_tn;
            this.kieu = _kieu;
            this.donvi_dl_id = _donvi_dl_id;
            this.dichvuvt_id = _dichvuvt_id;
        },
        //endregion

        //region Checked
        chkTrangThai_CheckedChanged()
        {
            this.$refs.cboTrangThai.Enabled = this.$refs.chkTrangThai.checked;
        },

        chkNgayLap_CheckedChanged()
        {
            this.$refs.dtpNgayTN.disabled = !this.$refs.chkNgayLap.checked;
            this.$refs.dtpDenNgay.disabled = !this.$refs.chkNgayLap.checked;
        },

        

        chkDVVT_CheckedChanged()
        {
            this.$refs.cboDichVuVT.disabled = !this.$refs.chkDVVT.checked;
        },
        //endregion

        //region KeyPress
        txTextBoxTC_KeyPress()
        {
            console.log("txTextBoxTC_KeyPress")
            this.HienThiDanhSacHDKH();
        },

        cboLoaiHD_KeyPress()
        {
            console.log("cboLoaiHD_KeyPress")
            this.HienThiDanhSacHDKH();
        },

        cboTrangThai_KeyPress()
        {
            if(!this.$refs.chkTrangThai.checked)
                 return;
            this.HienThiDanhSacHDKH();
        },

        txtNgayYC_KeyPress()
        {
            console.log("txtNgayYC_KeyPress")
            this.HienThiDanhSacHDKH();
        },
        //endregion
        formatDate(value_, format) {
            if (moment(value_).isValid()) {
                return moment(value_, "DD/MM/YYYY HH:mm:ss").format(format);
            } else {
                return value_;
            }
        },
        //region HienThiDanhSacHDKH
        async HienThiDanhSacHDKH()
        {
            try
            {
                this.loading(true)
                let dieukien;
                //loaihd_id;
                let ttkn_id;
                let ngay_tn;
                let den_ngay;
                let dichvuvt_id
                let rs

                if (this.$refs.txTextBoxTC.value == "")
                    dieukien = "0";
                else
                {
                    dieukien = this.$refs.cboDKTraCuu.value.toString().replaceAll(":values", this.$refs.txTextBoxTC.value);
                }     

                if (this.$refs.chkDVVT.checked)
                    dichvuvt_id = Number(this.$refs.cboDichVuVT.value);
                else
                    dichvuvt_id = 0;

                if (this.$refs.chkTrangThai.checked)
                    ttkn_id = Number(this.$refs.cboTrangThai.value);
                else
                    ttkn_id = 0;

                if (this.$refs.chkNgayLap.checked)
                {
                    ngay_tn = this.formatDate(this.$refs.dtpNgayTN.value, "yyyyMMdd")
                    den_ngay = this.formatDate(this.$refs.dtpDenNgay.value, "yyyyMMdd")
                }
                else
                {
                    ngay_tn = "0";
                    den_ngay = "0";
                }

                rs = await this.axios.post("web-gqkn/TraCuuPhieuKN/tra_cuu_tb_kn", {
                    "vdenngay": den_ngay,
                    "vdichvuvt_id": dichvuvt_id,
                    "vdieukien": dieukien,
                    "vdonvi_dl_id": this.donvi_dl_id,
                    "vdonvi_id": this.donvi_id,
                    "vngay_tn": ngay_tn,
                    "vnhanvien_id": this.nhanvien_id,
                    "vttkn_id": ttkn_id
                })


                let ds = rs.data.data
                if (ds.length > 0)
                {
                    this.$refs.lvThueBao.dataSource = ds
                    
                    this.ma_tb = ds[0]["ma_tb"].toString();
                    this.dichvu_vt = ds[0]["ten_dvvt"].toString();
                    this.$refs.txtMaTB.value = this.ma_tb;
                    
                }
                this.loading(false)

            }
            catch (err)
            {
                this.loading(false)
                console.log(err)
            }
        },
        //endregion
        btnChapnhan_Click() {
            let selected = this.$refs.lvThueBao.getSelectedRecords()
            this.$emit("closePopup", selected.length > 0 ? selected[0] : {})
            this.$refs.frmTraCuuTBKhieuNai.hide()
        },

        //region HienThiDanhSacHDTT
        HienThiDanhSacHDTT()
        {
            
            
        },
        //endregion

        //region HienThiDanhSacHDTB
        HienThiDanhSacHDTB()
        {
            
        },
        //endregion

        

        //region Click
        btnChapNhan_Click()
        {
            this.chapnhan = true;
            this.Close();
        },

        lsvHopdong_DoubleClick()
        {
            this.chapnhan = true;
            this.Close();
        },
        //endregion

        //region
        tsbtnTimKiem_Click()
        {
            this.HienThiDanhSacHDKH();
        },
        //endregion

        // hoangpkn : 06/07/2012
        // thêm sự kiện SelectedIndexChanged
        lvThueBao_SelectedIndexChanged()
        {
            try
            {
                if (this.$refs.lvThueBao.dataSource.length > 0)
                {
                    let seleted = this.$refs.lvThueBao.getSelectedRecords()
                    this.ma_tb = seleted[0].ma_tb
                    this.dichvu_vt = seleted[0].dichvu_vt
                    this.$refs.txtMaTB.value = this.ma_tb;
                }
            }
            catch (err)
            {
                console.log(err)
            }
        }
    }
}
</script>
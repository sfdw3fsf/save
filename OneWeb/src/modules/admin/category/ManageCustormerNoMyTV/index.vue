<template src="./ManageCustormerNoMyTV.html"></template>
<script>
import moment from "moment";
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
      kieu_id: 0,
      bangts: {},
      cm: {},
      tt_nd: {
          ngay_cn: moment(new Date()).format("DD/MM/YYYY HH:mm:ss")
      },
      giaophieutc: {},
      cmUlt: {},
      tchopdong: {},
      huonggiao_id: 0,
      dt: {},
      ds: {},
      current: 0,
      trangthai: 0,
      hg_id: 0,
      khieunai_id: 0,
      ttph_id: 0,
      Khieunai: {},
      getkey: {},
      donvi_nhan_id: 0,
      ds_dvn: {},
      phieukn_id: 0,
      thuebao_id: 0,
      bGiaoPhieu: 0,
      dvvt_id: 0,
      loaidv_nhan_id: "",
      huonggiao: {},
      checkdata: {},
      load: false,

      da_xuathd_dt: false,
      vemail: "",
      vso_fax: "",
      vloaikh_id: "",
      ds_tb_hddt: {},
      Fkey: "",
      objHoaDonDienTu: {},
      objHoaDonDienTuEdit: {},
      dsBDHDDT: {},
      tongtien_hddt: 0,
      tongvat_hddt: 0,
      tong_hddt: 0,
      invoice: {},

      _dsSoDT_Dvi: {},
      vnoidung: "",
      vnoidung_ghichu: "",
      vthuebao_id: 0,
      TUDONG_GIAHAN_DATCOC : -1,
      dt_huonggiao: []
    };
  },
  mounted() {
      this.frmSmartMac_Load()
  },
  methods: {
      frmSmartMac(_kieu_id, _huonggiao_id, ma_tb,ttph, ngaygiao)
        {
            kieu_id = _kieu_id;
            hg_id = _huonggiao_id;
            ttph_id = ttph;
            //if (ngaygiao == "0")
            //    dtpNgayGiaoTK.Value = this.tt_nd.ngay_cn;
            //else
            //    dtpNgayGiaoTK.Value = Convert.ToDateTime(ngaygiao);
            //if (ma_tb != "0")
            //{
            //    txtMaTB.value = ma_tb;
            //}

        }

        ,async frmSmartMac_Load()
        {
            // if (!cm.KiemTra_ThamSo_Registry("chuky", "Chu kỳ"))
            // {
            //     this.BeginInvoke(new MethodInvoker(this.Close));
            //     return;
            // }

            //region Kênh tt
            this.$refs.cboKenhTT.dataSource = [  {kenhtt_id : 1, kenh_tt: 'Trực tiếp'},
                                                            {kenhtt_id : 2, kenh_tt: 'Outbound'},
                                                            {kenhtt_id : 3, kenh_tt: 'Online'},
                                                            {kenhtt_id : 4, kenh_tt: 'Khác'}]
            this.$refs.cboKenhTT.dataValueField = "kenhtt_id";
            this.$refs.cboKenhTT.dataTextField = "kenh_tt";
            this.$refs.cboKenhTT.value = 1;
            //endregion

            //region Trạng thái TT
            this.$refs.cboTrangThaiTT.dataSource = [
                                                       {trangthaitt_id : 1, trangthai_tt: 'Thành công'},
                                                       {trangthaitt_id : 0, trangthai_tt: 'Không thành công'}
                                                    ]

            this.$refs.cboTrangThaiTT.dataValueField = "trangthaitt_id";
            this.$refs.cboTrangThaiTT.dataTextField = "trangthai_tt";
            this.$refs.cboTrangThaiTT.value = 1;
            //endregion

            //region Kiểu sd
            this.$refs.cboKieuSD.dataSource = [
                                                       {id : 1, trangthai: 'Dùng thử'},
                                                       {id : 2, trangthai: 'Chính thức'}
                                                    ]
            this.$refs.cboKieuSD.dataValueField = "id";
            this.$refs.cboKieuSD.dataTextField = "trangthai";
            this.$refs.cboKieuSD.value = 1;
            //endregion

            //region Nguyên nhân
            let rs = await this.axios.get("web-quantri/danhmuc-chung/CSS_NGUYENNHAN_TT")
            this.$refs.cboNguyenNhanTT.dataSource = rs.data.data
            this.$refs.cboNguyenNhanTT.dataValueField = "NGUYENNHAN_ID";
            this.$refs.cboNguyenNhanTT.dataTextField = "NGUYENNHAN";
            this.$refs.cboNguyenNhanTT.value = this.$refs.cboNguyenNhanTT.dataSource[0].NGUYENNHAN_ID
            //endregion

            //region Chủ quản
            rs = await this.axios.get("web-quantri/danhmuc-chung/CSS_CHUQUAN")
            this.$refs.cboChuQuan.dataSource = rs.data.data
            this.$refs.cboChuQuan.dataValueField = "CHUQUAN_ID";
            this.$refs.cboChuQuan.dataTextField = "TENCHUQUAN";
            this.$refs.cboChuQuan.value = this.$refs.cboChuQuan.dataSource[0].CHUQUAN_ID
            //endregion

            this.$refs.dtpNgayTT.value = this.tt_nd.ngay_cn;

            //dgvList.Columns["colDonVi"].ReadOnly = true;
            this.load = true;

        }

        ,Lay_Thamso_MD()
        {

        }

        ,HienThi_GiaoDien()
        {

        },
        rdoChuaTT_CheckedChanged() {
            this.$refs.grdDatCoc.dataSource = []
        },
        rdoDaTT_CheckedChanged() {
            this.$refs.grdDatCoc.dataSource = []
        }


        //region HienThi_DanhSach
        ,async HienThi_DanhSach()
        {
            try
            {
                this.loading(true)
                let rs = await this.axios.post("web-danhba/quanly_kh/lay_ds_tiepthi_smartmac", {
                    "tiepthi": this.$refs.rdoChuaTT.checked ? 0 : 1
                })
                let dt = rs.data.data
                dt = [...new Map(dt.map((item) => [item["thuebao_id"], item])).values()]
                this.$refs.grdDatCoc.dataSource = dt;
                this.$refs.grdDatCoc.setCurrentSelectedRow(this.current)
                this.$refs.grdDatCoc.flagSelectedRowIndexes = [];
                this.$refs.grdDatCoc.flagSelectedRowIndexes.push(this.current);
                this.loading(false)
            }
            catch (err)
            {
                console.log(err)
                this.$toast.error(err.message);
            }
        }

        /// <summary>
        /// Hàm này ko có timer
        /// </summary>
        ,HienThi_DanhSach2()
        {
            try
            {
                this.HienThi_DanhSach()
            }
            catch (err)
            {
                //
                this.$toast.error(err.message);
            }
        }
        //endregion

        ,tsbtnGiaoPhieu_Click()
        {
            //GiaoPhieu();
        }

        ,tsbtnTimKiem_Click()
        {
            this.HienThi_DanhSach();
        }

        ,dtpNgayGiaoTK_ValueChanged()
        {
            this.HienThi_DanhSach();
        }

        ,tsbtnHuyGiao_Click()
        {
            
        }

        ,tsbtnCapNhatVT_Click()
        {
            this.CapNhat_DonViNhan();
        }

        ,CapNhat_DonViNhan()
        {
            //try
            //{
            //    if (grvDSCongNo.RowCount <= 0)
            //    {
            //        this.$toast.error("Không có dữ liệu. Bạn hãy kiểm tra lại!");
            //        return;
            //    }
            //    int donvi_id;
            //    donvi_id = Convert.ToInt32(cboDonVi2.EditValue);
            //    int index = Convert.ToInt32(grvDSCongNo.FocusedRowHandle);
            //    long vkhieunai_id = Convert.ToInt64(grvDSCongNo.GetRowCellValue(index, "KHIEUNAI_ID").ToString());
            //    if (loaidv_nhan_id != "-1")
            //        Khieunai.CAPNHAT_DONVINHAN_XLKN(vkhieunai_id, donvi_id, Convert.ToInt32(loaidv_nhan_id));

            //    Message_Box.ShowTB("Đã cập nhật lại đơn vị nhận !");
            //    HienThi_DanhSach();
            //}
            //catch (err)
            //{
            //    this.$toast.error(ex.ToString());
            //}
        }

        ,cboHuongGiao_EditValueChanged()
        {

        }

        ,cboTrangThai_EditValueChanged()
        {

        }

        ,ConditionsAdjustment()
        {
        }

        ,gridviewDS_Phieu_CellValueChanging()
        {
            try
            {
            }
            catch (err)
            {
                this.$toast.error(err.message);
            }
        },
        formatDate(value_) {
            if (moment(value_).isValid()) {
                return moment(value_).format("DD/MM/YYYY HH:mm:ss");
            } else {
                return value_;
            }
        },
        tsbtnXuatExcel_Click() {
            this.$toast.error("Chức năng sẽ được bổ sung sau!");
        },
        tsbtnGhiLai_Click()
        {
            try
            {
                if (this.$refs.grdDatCoc.dataSource.length <= 0)
                {
                    this.$toast.error("Hãy lấy danh sách trước khi ghi lại!");
                    return;
                }

                if (this.$refs.grdDatCoc.getSelectedRecords().length <= 0)
                {
                    Message_Box.ShowTB("Bạn chưa chọn dữ liệu trên lưới để cập nhật!");
                    return;
                }

                this.$confirm("Bạn thật sự muốn cập nhật không?", {
                    confirmButtonText: "Có",
                    cancelButtonText: "Không",
                    type: "warning",
                }).then(async () => {
                    let arr_tb = []
                    this.$refs.grdDatCoc.getSelectedRecords().forEach(dr =>
                    {
                        arr_tb.push(dr.thuebao_id)
                    })

                let rs = await this.axios.post("web-danhba/quanly_kh/capnhat_tiepthi_smartmac", {
                    "thuebao_id_arr": arr_tb,
                    "ngay_tt": this.$refs.dtpNgayTT.value.split(" ")[0],
                    "kenh_tt": this.$refs.cboKenhTT.value,
                    "trangthai_tt": this.$refs.cboTrangThaiTT.value,
                    "ghichu_tt": this.$refs.txtGhiChu.value,
                    "kieu_sd": this.$refs.cboKieuSD.value,
                    "matb_mytv": this.$refs.txtMaTB_MyTV.value,
                    "nguyennhan_id": this.$refs.cboNguyenNhanTT.value,
                    "chuquan_id": this.$refs.cboChuQuan.value

                })

                this.HienThi_DanhSach2();
                if(rs.data && ( rs.data.data == "0" || rs.data.data == "1" ))
                    this.$toast.success("Cập nhật dữ liệu thành công!");
                else
                    this.$toast.error(rs.data.data);
                });

                
            }
            catch (err)
            {
                this.$toast.error("Cập nhật không thành công!");
                this.$toast.error(err.message);
            }
        },
        rowClicked(index, data) {
            this.current = index
        }
        ,gridviewDS_Phieu_FocusedRowChanged()
        {
            try
            {

                if (this.$refs.rdoDaTT.checked)
                {
                    let selected = this.$refs.grdDatCoc.getSelectedRecords()
                    if(selected.length == 0) return
                    let itemSelected = selected[0]
                    console.log(itemSelected)
                    this.$refs.txtMaTB_MyTV.value   = itemSelected.matb_mytv
                    this.$refs.cboTrangThaiTT.value = itemSelected.trangthai_tt
                    this.$refs.dtpNgayTT.value = this.formatDate(itemSelected.ngay_tt)
                    this.$refs.cboKenhTT.value = itemSelected.kenh_tt
                    this.$refs.txtGhiChu.value = itemSelected.ghichu_tt
                    this.$refs.cboKieuSD.value = itemSelected.kieu_sd
                    this.$refs.cboNguyenNhanTT.value = itemSelected.nguyennhan_id
                    this.$refs.cboChuQuan.value      = itemSelected.chuquan_id
                }
            }
            catch (err)
            {
                this.$toast.error("Có lỗi: " + err.message);
            }
        }
  }
};
</script>
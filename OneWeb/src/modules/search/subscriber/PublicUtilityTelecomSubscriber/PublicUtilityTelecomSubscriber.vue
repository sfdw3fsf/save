<template src="./template.html"></template>

<script>
import breadcrumb from '@/components/breadcrumb'
import api from "../../../search/subscriber/PublicUtilityTelecomSubscriber/api/index";
import moment from "moment";
import SearchAccount from '@/modules/search/subscriber/SearchAccount'

export default {
  name: "PublicUtilityTelecomSubscriber",
  components: {
    breadcrumb, SearchAccount
  },
  computed: {
    donViId() {
      return +this.$root.token.getDonViID()
    },
    nhanvienId() {
      return +this.$root.token.getNhanVienID()
    },
    username() {
      return this.$root.token.getUserName()
    },
    donViDLId() {
      return this.$root.token.getDonViDuLieuID()
    },
    phanvungId() {
      return this.$root.token.getPhanVungID()
    }
  },
  data() {
    return {
      header: {
        title: 'Thuê bao viễn thông công ích',
        list: [
          {name: 'Lập hợp đồng', link: {name: 'Ui.cards'}},
          {name: 'Lắp đặt mới', link: {name: 'Ui.cards'}},
        ]
      },

      dateFormat: "MM/YYYY",

      tsbtnNhapMoi: false,
      tsbtnGhiLai: false,
      tsbtnXoa: false,
      tsbtnHuy: false,
      tsbtnHoanThien: false,
      tsbtnInPhieu: true,
      tsbtnHuyVTCI: true,
      txtTenTB: "",
      cboLoaiHinhTB: {
        option: [],
        value: null,
      },
      cboDichVuVT: {
        option: [],
        value: null,
      },
      txtMaTB: "",
      txtDiaChiTB: "",
      txtTienHoTro: "",
      deThangKT: moment().format('MM/YYYY'),
      deThangBD: moment().format('MM/YYYY'),
      cboGoiVTCI: {
        option: [],
        value: null,
      },
      txtMaCH: "",
      txtTenCH: "",
      chkHieuLuc: false,
      chkTatCa: false,
      deThangKT_TK: moment().format('MM/YYYY'),
      chkThangKT: false,
      deThangBD_TK: moment().format('MM/YYYY'),
      chkThangBD: false,
      chkTrangThai: false,
      txtTenKH: "",
      txtMaKH: "",
      txtNganhNghe: "",
      txtLoaiKH: "",
      cboTrangThai: {
        option: [],
        value: null,
      },
      txtGhiChu: "",
      chkGoiTK: false,
      cboGoi_TK: {
        option: [],
        value: null,
      },
      gcDs: [],
      rowSelected: [],
      gcDSFile: [],
      dsFileUpload: [],

      dichvuvt_id: 0,
      loaitb_id: 0,
      loaikh_id: 0,
      thuebao_id: 0,
      tb_vtci_id: 0,
      ma_tb: "",
      sDuongDanDinhKemFTP: "",
      selectfirstrow: false,
      current: 0,
      ttnd: null,
    }
  },
  async created() {
    try {
      const response = await api.getTTND(this.axios)
      const data = response.data
      this.ttnd = data.data
    } catch (e) {
      console.log(e)
    } finally {
    }
    await this.frmTB_VTCI_FormLoad();
    this.setIndex(0);
    // this.selectfirstrow = false
  },
  methods: {
    async frmTB_VTCI_FormLoad() {
      try {
        // cbo.option = []
        this.cboDichVuVT.option = [];
        this.cboTrangThai.option = [];
        this.cboGoiVTCI.option = [];
        this.cboGoi_TK.option = [];
        const response = await api.LAY_DANHMUC_VTCI(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          this.cboDichVuVT.option = data.data.p_ds_dvvt;
          this.cboTrangThai.option = data.data.p_ds_trangthai;
          this.cboGoiVTCI.option = data.data.p_ds_goi_vtci;
          this.cboGoi_TK.option = data.data.p_ds_goi_vtci;
          // cbo.option = data.data
          if (this.cboDichVuVT.option.length > 0) {
            this.cboDichVuVT.value = this.cboDichVuVT.option[0].dichvuvt_id
            await this.cboDichVuVT_EditValueChanged()
          }
          if (this.cboTrangThai.option.length > 0) {
            this.cboTrangThai.value = this.cboTrangThai.option[0].status_id
          }
          if (this.cboGoiVTCI.option.length > 0) {
            this.cboGoiVTCI.value = this.cboGoiVTCI.option[0].goi_id
          }
          if (this.cboGoi_TK.option.length > 0) {
            this.cboGoi_TK.value = this.cboGoi_TK.option[0].goi_id
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }

      await this.HT_TBVTCI("")

      // this.$refs.dataGrid.grid.selectRow(2)
    },
    async HT_TBVTCI(dk) {

      try {
        const response = await api.LAY_DS_TBVTCI(this.axios, {"vdk": dk})
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          this.gcDs = data.data

          if (this.gcDs.length > 0) {
            await this.SetButton(3);
            // DataTable _dt = (DataTable)gcDs.DataSource;
            if (this.rowSelected === null) {
              return;
            }
            if (this.rowSelected.length > 0) {
              let data = this.rowSelected[0]
              // let _index = this.rowSelected.rowIndex;
              this.tb_vtci_id = data.tb_vtci_id;
              this.thuebao_id = data.thuebao_id;
              this.cboDichVuVT.value = data.dichvuvt_id;
              this.cboGoiVTCI.value = data.goi_id;
              this.cboLoaiHinhTB.value = data.loaitb_id;
              this.loaitb_id = data.loaitb_id;
              this.dichvuvt_id = data.dichvuvt_id;
              this.txtMaTB = data.ma_tb;
              this.ma_tb = data.ma_tb;
              this.txtTenTB = data.ten_tb
              this.txtDiaChiTB = data.diachi_tb
              this.txtGhiChu = data.ghichu
              this.txtMaCH = data.ma_ch
              this.txtTenCH = data.ten_ch
              this.txtTienHoTro = data.tien_ht
              this.deThangBD = data.thang_bd
              this.deThangKT = data.thang_kt
              this.txtTenKH = data.ten_kh
              this.txtMaKH = data.ma_kh
              this.txtLoaiKH = data.ten_loaikh
              this.txtNganhNghe = data.tennganhnghe
              this.gcDSFile = [];
              let files = data.mota
              if (files.trim() !== "") {
                // let a = files.split(';').ToList();
                // DataTable
                // _dt1 = new DataTable();
                // _dt1.Columns.Add(new DataColumn("DUONGDAN", typeof (string)));
                // _dt1.Columns.Add(new DataColumn("TENFILE", typeof (string)));
                // for (int i = 0;i < a.Count;i++){
                //   DataRow
                //   r = _dt1.NewRow();
                //   r["DUONGDAN"] = a[i];
                //   r["TENFILE"] = a[i].Substring(a[i].LastIndexOf(@"\") + 1);
                //   _dt1.Rows.Add(r);
                // }
                // gcDSFile.DataSource = _dt1;
              }
              this.loaikh_id = data.loaikh_id
              if (data.status.toString() === "1")
                await this.SetButton(3);
              else {
                await this.SetButton(4);
                if (data.status.toString() === "1") {
                  this.tsbtnHuy = true;
                }
              }
            }
          }
          else {
            await this.SetButton(1)
          }
        } else {
          this.gcDs = data.data
          await this.SetButton(1)
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log(e)
        console.log('Lấy danh sách không thành công')
      } finally {
      }

    },
    async SetButton(kieu) {
      this.tsbtnNhapMoi = false;
      this.tsbtnGhiLai = false;
      this.tsbtnXoa = false;
      this.tsbtnHuy = false;
      this.tsbtnHoanThien = false;

      if (kieu === -1)//Bat dau
      {
        this.tsbtnGhiLai = true;
        this.tsbtnHuy = true;
      }
      if (kieu === 0)//Bat dau
      {
        // this.txtMaTB.Focus();
        this.tsbtnNhapMoi = true;
        await this.Clear();
      }
      if (kieu === 1)//Them moi
      {
        // this.txtMaTB.Focus();
        this.tsbtnGhiLai = true;
        this.tsbtnHuy = true;
        await this.Clear();
      }
      if (kieu === 2)//Huy
      {
        this.tsbtnNhapMoi = true;
        this.tsbtnXoa = true;
        await this.Clear();
      }
      if (kieu === 3)//Edit
      {
        this.tsbtnNhapMoi = true;
        this.tsbtnXoa = true;
        this.tsbtnGhiLai = true;
        this.tsbtnHuy = true;
        this.tsbtnHoanThien = true;
      }

      if (kieu === 4)//Đã duyệt/ ko được duyệt
      {
        this.tsbtnNhapMoi = true;
        this.tsbtnXoa = false;
        this.tsbtnGhiLai = false;
        this.tsbtnHuy = false;
        this.tsbtnHoanThien = false;
      }
    },
    async Clear() {
      this.deThangBD = moment().format('MM/YYYY');
      this.deThangKT = moment().format('MM/YYYY');
      this.txtMaTB = "";
      this.txtTenTB = "";
      this.txtDiaChiTB = "";
      this.txtGhiChu = "";
      this.txtMaCH = "";
      this.txtTenCH = "";
      this.txtNganhNghe = "";
      this.txtMaKH = "";
      this.txtTenKH = "";
      this.txtLoaiKH = "";
      if (this.cboGoiVTCI.value != null) {
        for (let i of this.cboGoiVTCI.option) {
          if (i.goi_id.toString() === this.cboGoiVTCI.value.toString()) {
            this.txtTienHoTro = i.tien_goi.toString();
          }
        }
      } else {
        this.txtTienHoTro = "0";
      }
      this.ma_tb = "";
      this.gcDSFile = [];
    },
    async tsbtnThoat_Click() {

    },
    async cboDichVuVT_EditValueChanged() {
      try {
        this.cboLoaiHinhTB.option = []
        const response = await api.CSS_LOAIHINH_TB(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000') {
          this.cboLoaiHinhTB.option = data.data
          if (this.cboLoaiHinhTB.option.length > 0) {
            this.cboLoaiHinhTB.value = this.cboLoaiHinhTB.option[0].LOAITB_ID
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async lineLabel2_Load() {

    },
    async tsbtnNhapMoi_Click() {
      await this.SetButton(1);
    },
    async tsbtnGhiLai_Click() {
      await this.Capnhat();
    },
    async DatatableToXml() {
      // if (dt == null) return "";
      // MemoryStream str = new MemoryStream();
      // dt.WriteXml(str, true);
      // str.Seek(0, SeekOrigin.Begin);
      // StreamReader sr = new StreamReader(str);
      // string xmlstr;
      // xmlstr = sr.ReadToEnd();
      // return xmlstr;
    },
    async Capnhat() {
      let ip = await this.$root.token.getIP();
      let machine = await this.$root.token.getMachine();
      console.log(this.ttnd, ip, machine)

      if (!await this.KT_DL()) return;
      let thaotac;
      let _index;
      let vtemp = 0;
      _index = 0;

      if (this.tsbtnNhapMoi && this.gcDs.length > 0) {
        thaotac = 2;
        _index = this.$refs.dataGrid.grid.getSelectedRowIndexes()[0];
        console.log(_index)
      } else {
        thaotac = 1;
        try {
          const response = await api.get_key(this.axios,"TB_VTCI_ID")
          const data = response.data
          this.tb_vtci_id = data.data
        } catch (e) {
          console.log(e)
        } finally {
        }
        // this.tb_vtci_id = getkey.GetKey("TB_VTCI_ID");
        vtemp = this.tb_vtci_id;
      }

      try {
        const str_deThangBD_TK = this.deThangBD.toString().split('/');
        const deThangBD_TK = str_deThangBD_TK[1] + str_deThangBD_TK[0] + '';

        const str_deThangKT_TK = this.deThangKT.toString().split('/');
        const deThangKT_TK = str_deThangKT_TK[1] + str_deThangKT_TK[0] + '';

        let mota = ""
        for (let i of this.gcDSFile) {
          mota += i.file_name + ','
        }
        if (this.txtTenCH === null) {
          this.txtTenCH = ""
        }
        if (this.txtMaCH === null) {
          this.txtMaCH = ""
        }
        console.log("MoTA: " + mota)
        const response = await api.CAPNHAT_VTCI(this.axios,
          {
            "vtci": `<DocumentElement>
                        <danhsach>
                            <TB_VTCI_ID>${this.tb_vtci_id}</TB_VTCI_ID>
                            <MA_TB>${this.txtMaTB.trim()}</MA_TB>
                            <MA_CH>${this.txtMaCH}</MA_CH>
                            <TEN_CH>${this.txtTenCH}</TEN_CH>
                            <DICHVUVT_ID>${this.dichvuvt_id}</DICHVUVT_ID>
                            <LOAITB_ID>${this.loaitb_id}</LOAITB_ID>
                            <TIEN_HT>${this.txtTienHoTro}</TIEN_HT>
                            <GOI_ID>${this.cboGoiVTCI.value}</GOI_ID>
                            <GHICHU>${this.txtGhiChu}</GHICHU>
                            <THUEBAO_ID>${this.thuebao_id}</THUEBAO_ID>
                            <MAY_CN>${machine}</MAY_CN>
                            <NGUOI_CN>${this.ttnd.ma_nd}</NGUOI_CN>
                            <IP_CN>${ip}</IP_CN>
                            <THANG_KT>${deThangKT_TK}</THANG_KT>
                            <THANG_BD>${deThangBD_TK}</THANG_BD>
                            <STATUS>1</STATUS>
                            <MOTA>${mota}</MOTA>
                        </danhsach>
                    </DocumentElement>`,
            "vthaotac": thaotac
          }
        )
        const data = await response.data
        if (data.data !== "1") {
          // Message_Box.ShowTB(kq);
          this.$toast.info(kq);
        } else {
          // Message_Box.ShowTB("Cập nhật thành công");
          this.$toast.success("Cập nhật thành công");
        }

        // let i = this.gcDs.indexOf(this.rowSelected);

        await this.HT_TBVTCI("");
        if (thaotac === 2) {
          this.setIndex(+_index)
        }
        // else
        // {
        //   grvDs.ClearColumnsFilter();
        //   _index = grvDs.LocateByValue(0, gcTB_VTCI_ID, vtemp);
        //   grvDs.FocusedRowHandle = _index;
        //   grvDs.SelectRow(_index);
        // }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },

    async KT_DL() {
      const str_deThangBD_TK = this.deThangBD.toString().split('/');
      const deThangBD_TK = str_deThangBD_TK[1] + str_deThangBD_TK[0] + '';

      const str_deThangKT_TK = this.deThangKT.toString().split('/');
      const deThangKT_TK = str_deThangKT_TK[1] + str_deThangKT_TK[0] + '';

      if (this.ma_tb === "") {
        // Message_Box.ShowWarning("Bạn chưa load thông tin thuê bao");
        this.$toast.warning("Bạn chưa load thông tin thuê bao");
        return false;
      }
      if (this.loaikh_id === 8 || this.loaikh_id === 68) {
        if (this.txtMaCH.trim() === "") {
          // Message_Box.ShowWarning("Bạn chưa nhập mã chủ hộ");
          this.$toast.warning("Bạn chưa nhập mã chủ hộ");
          return false;
        }

        if (this.txtTenCH.trim() === "") {
          // Message_Box.ShowWarning("Bạn chưa nhập tên chủ hộ");
          this.$toast.warning("Bạn chưa nhập tên chủ hộ");
          return false;
        }
      }
      if (this.txtTienHoTro.toString().trim() === "") {
        // Message_Box.ShowWarning("Bạn chưa nhập tiền hỗ trợ");
        this.$toast.warning("Bạn chưa nhập tiền hỗ trợ");
        return false;
      } else {
        // long a;
        // if (!long.TryParse(this.txtTienHoTro.value.ToString(), out a))
        // {
        //   // Message_Box.ShowWarning("Tiền chưa đúng định dạng.");
        //   return false;
        // }
        // if (a <= 0)
        // {
        //   // Message_Box.ShowWarning("Tiền phải > 0.");
        //   return false;
        // }

      }

      if (Number(deThangBD_TK) > Number(deThangKT_TK)) {
        // Message_Box.ShowWarning("Tháng bắt đầu không được lớn hơn tháng kết thúc.");
        this.$toast.warning("Tháng bắt đầu không được lớn hơn tháng kết thúc.");
        return false;
      }

      if (this.cboGoiVTCI.value === null) {
        // Message_Box.ShowWarning("Bạn chưa chọn gói viễn thông công ích.");
        this.$toast.warning("Bạn chưa chọn gói viễn thông công ích.");
        return false;
      } else {

        try {
          const response = await api.LAY_DS_GOI_VTCI(this.axios,
            {
              "vthuebao_id": this.thuebao_id,
              "vloaitb_id": this.loaitb_id
            }
          )
          const data = await response.data
          if (data.error_code === 'BSS-00000000' && response.status === 200) {
            let query = data.data.filter(item => item.goi_id === this.cboGoiVTCI.value).length;
            if (query === 0) {
              // Message_Box.ShowWarning("Gói VTCI ko đc áp dụng cho thuê bao này.");
              this.$toast.warning("Gói VTCI ko đc áp dụng cho thuê bao này.");
              return false;
            }
          } else {
            console.log('Không có dữ liệu')
          }
        } catch (e) {
          console.log('Lấy danh sách không thành công')
        } finally {
        }
      }

      let str = "";

      try {
        let tbvtct_id = 0;
        let loai_hd = 1;
        if (this.tsbtnNhapMoi === true) {
          tbvtct_id = this.tb_vtci_id;
          loai_hd = 2;
        }
        const response = await api.KT_DL(this.axios, {
          "p_thuebao_id": this.thuebao_id,
          "p_tb_vtci_id": tbvtct_id,
          "p_loai_hd": loai_hd,
          "p_thang_bd": deThangBD_TK,
          "p_thang_kt": deThangKT_TK
        })
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          str = data.data;
          if (str !== "OK") {
            // Message_Box.ShowWarning(str);
            this.$toast.warning(str);
            return false;
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }

      return true;
    },
    async tsbtnHuy_Click() {
      await this.SetButton(0);
    },
    async tsbtnXoa_Click() {
      if (this.rowSelected.length <= 0) {
        return;
      }
      // if (MessageBox.Show("Bạn chắc chắn muốn hủy đăng ký gói viễn thông công ích cho thuê bao này ko?", "Thông báo", MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes)
      // {

      this.$confirm("Bạn chắc chắn muốn hủy đăng ký gói viễn thông công ích cho thuê bao này ko?",
        "Thông báo",
        {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(async () => {

        let ttnd = await this.$root.token.getThongTinNguoiDung();
        let ip = await this.$root.token.getIP();
        let machine = await this.$root.token.getMachine();
        try {
          if (this.rowSelected[0].status.toString() === "1") {
            const str_deThangBD_TK = this.deThangBD.toString().split('/');
            const deThangBD_TK = str_deThangBD_TK[1] + str_deThangBD_TK[0] + '';

            const str_deThangKT_TK = this.deThangKT.toString().split('/');
            const deThangKT_TK = str_deThangKT_TK[1] + str_deThangKT_TK[0] + '';

            const response = await api.CAPNHAT_VTCI(this.axios,
              {
                "vtci": `<DocumentElement>
                        <danhsach>
                            <TB_VTCI_ID>${this.tb_vtci_id}</TB_VTCI_ID>
                            <MA_TB>${this.txtMaTB.trim()}</MA_TB>
                            <MA_CH>${this.txtMaCH}</MA_CH>
                            <TEN_CH>${this.txtTenCH}</TEN_CH>
                            <DICHVUVT_ID>${this.dichvuvt_id}</DICHVUVT_ID>
                            <LOAITB_ID>${this.loaitb_id}</LOAITB_ID>
                            <TIEN_HT>${this.txtTienHoTro}</TIEN_HT>
                            <GOI_ID>${this.cboGoiVTCI.value}</GOI_ID>
                            <GHICHU>${this.txtGhiChu}</GHICHU>
                            <THUEBAO_ID>${this.thuebao_id}</THUEBAO_ID>
                            <MAY_CN>${machine}</MAY_CN>
                            <NGUOI_CN>${ttnd.ma_nd}</NGUOI_CN>
                            <IP_CN>${ip}</IP_CN>
                            <THANG_KT>${deThangKT_TK}</THANG_KT>
                            <THANG_BD>${deThangBD_TK}</THANG_BD>
                            <STATUS>1</STATUS>
                        </danhsach>
                    </DocumentElement>`,
                "vthaotac": 3
              }
            )
            const data = await response.data

            if (data.data !== "1") {
              this.$toast.info(data.data);
            } else {
              this.$toast.success("Đã xóa dữ liệu viễn thông tỉnh thành công");
            }
            await this.HT_TBVTCI("");
          } else {
            this.$toast.info("Thuê bao đang ở trạng thái " + (this.rowSelected[0].status.toString() === "6" ? "Đã duyệt" : "Không được duyệt") + ". Bạn không thể xóa được.");
          }
        } catch (e) {
          console.log('Lấy danh sách không thành công')
        } finally {
        }

      }).catch((e) => {
      })
    },
    async acceptSearchAccount(item) {
      console.log(item)
      this.txtMaTB = item.ma_tb
      // this.txtThuebao_KeyPress()
      this.$refs['popupSearchAccount'].hide()
    },
    async btnTraCuuTB_Click() {
      // frmTraCuuDanhBa f = new frmTraCuuDanhBa(0, 1);
      // f.ShowDialog();
      // if (f.chapnhan)
      // {
      //   this.txtMaTB.Text = f.ma_tb;
      //   this.txtMaTB.Focus();
      // }
      this.$refs['popupSearchAccount'].show()
    },
    async btnTimKiem_Click() {
      let _strTK = "";
      if (this.chkTrangThai) {
        _strTK += " and a.status = " + this.cboTrangThai.value.toString();
      }

      if (this.chkGoiTK) {
        _strTK += " and a.goi_id = " + this.cboGoi_TK.value.toString();
      }

      if (this.chkThangBD) {
        const str = this.deThangBD_TK.toString().split('/');
        const result = str[1] + str[0] + '';
        _strTK += " and thang_bd >= " + result;
      }

      if (this.chkThangKT) {
        const str = this.deThangKT_TK.toString().split('/');
        const result = str[1] + str[0] + '';
        _strTK += " and thang_kt <= " + result;
      }
      await this.HT_TBVTCI(_strTK);
    },
    async deThangKT_TK_EditValueChanged() {
      this.chkThangKT = true;
    },
    async deThangBD_TK_EditValueChanged() {
      this.chkThangBD = true;
    },
    async txtMaTB_TK_EditValueChanged() {
      this.chkTrangThai = true;
    },
    focusItem(index, data) {
      this.current = index
    },
    setIndex(index) {
      this.$refs.dataGrid.flagSelectedRowIndexes = [];
      this.$refs.dataGrid.flagSelectedRowIndexes.push(index);
      this.$refs.dataGrid.setCurrentSelectedRow(index)
    },
    async grvDs_FocusedRowChanged(item) {
      console.log(item)
      // console.log(this.current)
      this.rowSelected = item;

      if (this.rowSelected === null) {
        return;
      }

      if (this.rowSelected.length > 0) {
        let data = this.rowSelected[0]
        // let _index = this.rowSelected.rowIndex;
        this.tb_vtci_id = data.tb_vtci_id;
        this.thuebao_id = data.thuebao_id;
        this.cboDichVuVT.value = data.dichvuvt_id;
        this.cboGoiVTCI.value = data.goi_id;
        this.cboLoaiHinhTB.value = data.loaitb_id;
        this.loaitb_id = data.loaitb_id;
        this.dichvuvt_id = data.dichvuvt_id;
        this.txtMaTB = data.ma_tb;
        this.ma_tb = data.ma_tb;
        this.txtTenTB = data.ten_tb
        this.txtDiaChiTB = data.diachi_tb
        this.txtGhiChu = data.ghichu
        this.txtMaCH = data.ma_ch
        this.txtTenCH = data.ten_ch
        this.txtTienHoTro = data.tien_ht
        this.deThangBD = data.thang_bd
        this.deThangKT = data.thang_kt
        this.txtTenKH = data.ten_kh
        this.txtMaKH = data.ma_kh
        this.txtLoaiKH = data.ten_loaikh
        this.txtNganhNghe = data.tennganhnghe
        this.gcDSFile = [];
        let files = data.mota
        // console.log(files)
        if (files.trim() !== "") {
          try {
            this.gcDSFile = []
            const response = await api.view_file(this.axios, {
              "list_file": files
            })
            const data = await response.data
            if (data.error_code === 'BSS-00000000' && response.status === 200) {
              this.gcDSFile = data.data
            } else {
              console.log('Không có dữ liệu')
            }
          } catch (e) {
            console.log('Lấy danh sách không thành công')
          } finally {
          }
        }
        this.loaikh_id = data.loaikh_id
        if (data.status.toString() === "1")
          await this.SetButton(3);
        else {
          await this.SetButton(4);
          if (data.status.toString() === "1") {
            this.tsbtnHuy = true;
          }
        }
      }
    },
    async txtMaTB_KeyPress(e) {
      if (e.keyCode === 13) {
        if (this.tsbtnNhapMoi === true) {
          // Message_Box.ShowTB("Bạn không được thay đổi mã thuê bao của thuê bao công ích trên lưới");
          this.$toast.info("Bạn không được thay đổi mã thuê bao của thuê bao công ích trên lưới");
          return;
        }

        try {
          if (this.txtMaTB !== "") {
            // this.$toast.info("await this.HienThiTT_DanhBa(this.txtMaTB);");
            await this.HienThiTT_DanhBa(this.txtMaTB);
          }
        } catch (e) {
          // Message_Box.ShowError("" + e.toString());
          this.$toast.error("" + e.toString());
        }

      } else {
      }

    },
    async HienThiTT_DanhBa(vma_tb) {
      this.dichvuvt_id = this.cboDichVuVT.value;

      try {
        const response = await api.lay_danhba_theo_matb(this.axios, {
          "in_dichvuvt_id": this.dichvuvt_id,
          "in_donvi_dl_id": 0,
          "in_ma_tb": vma_tb
        })
        const data = await response.data
        if (data.data.length > 0) {
          let res = data.data[0];

          this.thuebao_id = res.thuebao_id;
          this.txtTenTB = res.ten_tb;
          this.txtDiaChiTB = res.diachi_tb;
          this.loaitb_id = res.loaitb_id;
          this.dichvuvt_id = res.dichvuvt_id;
          this.cboLoaiHinhTB.value = this.loaitb_id;
          this.txtTenKH = res.ten_kh;
          this.txtMaKH = res.ma_kh;
          this.ma_tb = this.txtMaTB.trim();
          this.txtLoaiKH = res.ten_loaikh;
          this.loaikh_id = res.loaikh_id;
          this.txtNganhNghe = res.tennganhnghe;


          const response1 = await api.LAY_DS_GOI_VTCI(this.axios,
            {
              "vthuebao_id": this.thuebao_id,
              "vloaitb_id": this.loaitb_id
            }
          )
          // this.cboGoiVTCI.option = [];
          const data1 = await response1.data
          if (data1.error_code === 'BSS-00000000' && response1.status === 200) {
            if(data1.data != null){
              if (data1.data.length > 0) {
                this.cboGoiVTCI.value = data1.data[0].goi_id
                await this.cboGoiVTCI_EditValueChanged()
              }
            }
          } else {
            console.log('Không có dữ liệu')
          }
        } else {
          this.$toast.info("Không tìm thấy thông tin thuê bao");
        }
      } catch (e) {
        // Message_Box.ShowError(ex.Message);
        this.$toast.error(e.message);
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async chkTatCa_CheckedChanged() {
      // console.log(this.chkTatCa)
      if (this.chkTatCa) {
        this.chkHieuLuc = false;
        this.chkTrangThai = false;
        this.chkThangBD = false;
        this.chkThangKT = false;
        await this.HT_TBVTCI("");
      }
    },
    async chkHieuLuc_CheckedChanged() {
      if (this.chkHieuLuc) {
        this.chkTatCa = false;
        this.chkTrangThai = false;
        this.chkThangBD = false;
        this.chkThangKT = false;
        await this.HT_TBVTCI(" and thang_kt >= to_char(sysdate, 'yyyyMM') and status = 6 ");
      }
    },
    async tsbtnInPhieu_Click() {

    },
    async cboGoiVTCI_EditValueChanged() {
      // console.log(this.cboGoiVTCI.option)
      if (this.cboGoiVTCI.value != null) {
        for (let i of this.cboGoiVTCI.option) {
          if (i.goi_id.toString() === this.cboGoiVTCI.value.toString()) {
            this.txtTienHoTro = i.tien_goi.toString();
          }
        }
      } else {
        this.txtTienHoTro = "0";
      }
      // console.log(this.txtTienHoTro)
    },
    async cboTrangThai_EditValueChanged() {
      this.chkTrangThai = true;
    },
    async btnDuyet_Click() {
      this.$confirm("Bạn có muốn duyệt đăng ký gói viễn thông công ích cho thuê bao này không?",
        "Thông báo",
        {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(async () => {
        console.log(this.rowSelected[0].status.toString() === "1")
        if (this.rowSelected[0].status.toString() === "1") {

          if (this.loaikh_id !== 8 && this.loaikh_id !== 68) {
            if (this.gcDSFile.length === 0) {
              this.$toast.info("Thuê bao chưa có file upload.");
              return;
            }
          }
          try {
            const response = await api.fn_xet_duyet_tb_vtci(this.axios,
              {
                "p_tb_vtci_id": this.tb_vtci_id,
                "p_status": 6,
                "p_thang_huy": 0
              }
            )
            const data = await response.data
            if (data.error_code === 'BSS-00000000' && response.status === 200) {
              if (data.data === "OK") {
                this.$toast.success("Thuê bao đã được duyệt thành công.");
                await this.HT_TBVTCI("");
              } else {
                this.$toast.info(data.data);
              }
            } else {
              this.$toast.info(data.data);
            }
          } catch (e) {
            console.log('Lấy danh sách không thành công')
          } finally {
          }
        } else {
          this.$toast.info("Thuê bao đang ở trạng thái " + (this.rowSelected[0].status.toString() === "6" ? "Đã duyệt" : "Không được duyệt") + ". Bạn không thể duyệt được.");
        }
      }).catch((e) => {
      })
    },
    async btnKoDuyet_Click() {
      this.$confirm("Bạn có muốn không phê duyệt đăng ký gói viễn thông công ích cho thuê bao này không?",
        "Thông báo",
        {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(async () => {
        console.log(this.rowSelected[0].status.toString() === "1")
        if (this.rowSelected[0].status.toString() === "1") {

          if (this.loaikh_id !== 8 && this.loaikh_id !== 68) {
            if (this.gcDSFile.length === 0) {
              this.$toast.info("Thuê bao chưa có file upload.");
              return;
            }
          }
          try {
            const response = await api.fn_xet_duyet_tb_vtci(this.axios,
              {
                "p_tb_vtci_id": this.tb_vtci_id,
                "p_status": 7,
                "p_thang_huy": 0
              }
            )
            const data = await response.data
            if (data.error_code === 'BSS-00000000' && response.status === 200) {
              if (data.data === "OK") {
                this.$toast.success("Thuê bao đã được duyệt thành công.");
                await this.HT_TBVTCI("");
              } else {
                this.$toast.info(data.data);
              }
            } else {
              this.$toast.info(data.data);
            }
          } catch (e) {
            console.log('Lấy danh sách không thành công')
          } finally {
          }
        } else {
          this.$toast.info("Thuê bao đang ở trạng thái " + (this.rowSelected[0].status.toString() === "6" ? "Đã duyệt" : "Không được duyệt") + ". Bạn không thể duyệt được.");
        }
      }).catch((e) => {
      })
    },
    async lnkFileUpload_LinkClicked() {

    },
    async repositoryItemButtonEdit1_ButtonClick() {

    },
    async cboGoi_TK_EditValueChanged() {
      this.chkGoiTK = true;
    },
    async chkGoiTK_CheckedChanged() {
    },
    async repositoryItemButtonEdit2_ButtonClick() {

    },
    async tsbtnHuyVTCI_Click() {
      if (this.rowSelected[0].status.toString() !== "6") {
        this.$toast.warning("Đây không phải thuê bao viễn thông công ích. Không thể hủy!");
        return;
      }
      this.$confirm("Bạn có muốn hủy gói viễn thông công ích cho thuê bao hay không?",
        "Thông báo",
        {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(async () => {

        try {

          let date = new Date();
          let thanghuy = date.getFullYear() * 100 + (date.getMonth() + 1);

          const response = await api.fn_xet_duyet_tb_vtci(this.axios,
            {
              "p_tb_vtci_id": this.tb_vtci_id,
              "p_status": 0,
              "p_thang_huy": thanghuy
            }
          )
          const data = await response.data
          if (data.error_code === 'BSS-00000000' && response.status === 200) {
            if (data.data === "OK") {
              this.$toast.success("Hủy thuê bao viễn thông công ích thành công");
              await this.HT_TBVTCI("");
            } else {
              this.$toast.info(data.data);
            }
          } else {
            this.$toast.info(data.data);
          }
        } catch (e) {
          console.log('Lấy danh sách không thành công')
        } finally {
        }
      }).catch((e) => {
      })
    },
    myFunction() {
      document.getElementById("myDropdown").classList.toggle("show");
    },

    async uploadFile() {
      console.log('upload file')
      await this.$refs.inputFile.click();
    },

    async importFile(e) {
      const files = e.target.files;
      let formData = new FormData();
      // console.log(files[0]);
      // this.gcDSFile.push(files[0]);
      formData.append("files", files[0]);
      // console.log(formData);
      // console.log(this.gcDSFile);
      let resFileUpload = await api
        .upload_file(this.axios, formData, {
          headers: {"Content-Type": "multipart/form-data"},
        })
      // console.log(resFileUpload)
      let strFile = "";
      if (resFileUpload.data.error_code === "BSS-00000000") {
        // this.$toast.success("Upload thành công!")
        strFile = resFileUpload.data.data[0].toString().trim();
        try {
          const response = await api.view_file(this.axios, {
            "list_file": strFile
          })
          const data = await response.data
          if (data.error_code === 'BSS-00000000' && response.status === 200) {
            this.gcDSFile.push(data.data[0])
          } else {
            console.log('Không có dữ liệu')
          }
        } catch (e) {
          console.log('Lấy danh sách không thành công')
        } finally {
        }
      }
    },
    getColumnAction(parent) {
      return function () {
        return {
          template: {
            template: `
              <div class="center">
              <a class="act" @click="parent.removeFile(data)" style="cursor: pointer; margin-right: 2px">
                <span class="icon nc fa fa-remove"></span>
              </a>
              <a id="link" class="act" @click="parent.downloadFile(data)" style="cursor: pointer; margin-left: 2px">
                <span class="icon nc fa fa-download"></span>
              </a>
              </div>
            `,
            data() {
              return {
                parent: parent,
                data: {}
              }
            }
          }
        }
      }
    },
    async removeFile(value) {
      // console.log(value)
      let name = this.gcDSFile[value.index].file_name
      this.gcDSFile = this.gcDSFile.filter(item => item.file_name !== name)
    },
    async downloadFile(value) {
      // console.log(value)
      // console.log(this.gcDSFile[+value.index])
      let file = this.gcDSFile[+value.index];
      var link = document.createElement('a');
      link.href = file.file_url;
      link.download = file.file_name;
      document.body.appendChild(link);
      link.click();
      link.remove();
    },
    getColumnHieuLuc(parent) {
      return function () {
        return {
          template: {
            template: `
              <div class="center">
              <input type="checkbox" disabled :checked="checked"/>
              </div>
            `,
            data() {
              return {
                parent: parent,
                data: {}
              }
            },
            computed:{
              checked() {
                return this.data.hieuluc === 1
              }
            }
          }
        }
      }
    },
  }
}
</script>

<style>
.disabled {
  pointer-events: none;
  opacity: 0.7;
}

.disabled-color {
  color: gray !important;
}

.frm-chon-port .page-content {
  position: inherit !important;
}

.dropbtn {
  color: white;
  padding: 16px;
  font-size: 16px;
  border: none;
  cursor: pointer;
}


.dropdown {
  position: relative;
  display: inline-block;
}

.dropdown-content {
  margin-top: 10px;
  display: none;
  position: absolute;
  background-color: white;
  min-width: 155px;
  overflow: auto;
  box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2);
  z-index: 1;
}

.dropdown-content a {
  color: #007bff;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
}

.dropdown-content a:hover {
  background-color: rgba(1, 118, 255, 0.1);
  color: #007bff;
}

.show {
  display: block;
}
</style>

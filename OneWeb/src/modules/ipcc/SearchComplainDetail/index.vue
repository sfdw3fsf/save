<template src="./template.html"></template>
<script>
  import moment from "moment";
  import Select2Ext from "@/modules/contract/setup/DeclareLandline/components/Select2Ext";
  import DataGridCustom from "@/modules/contract/setup/DeclareLandline/components/DataGridCustom";
  import VueDateExt from "@/modules/contract/setup/DeclareLandline/components/VueDateExt";
  import frmTraCuuTBKhieuNai from "./popups/frmTraCuuTBKhieuNai.vue"
  import frmNhanVienTH from "./popups/frmNhanVienTH.vue"
  import frmHoSoKhieuNai from "./popups/frmHoSoKhieuNai.vue"
  import frmTraCuu_ThueBao from "./popups/frmTraCuu_ThueBao.vue"
  import EventBus from "@/utils/eventBus";

  export default {
    components: {
      Select2Ext,
      DataGridCustom,
      VueDateExt,
      frmTraCuuTBKhieuNai,
      frmNhanVienTH,
      frmHoSoKhieuNai,
      frmTraCuu_ThueBao
    },
    created() {
      EventBus.$on('emitTimKiemLichSuKhieuNai', this.emitTimKiemLichSuKhieuNai)
    },
    data() {
      return {
        dskn: [
          {
            headerText: "Mã khiếu nại",
            autoFit: true,
            allowSorting: false,
            fieldName: "ma_kn",
            freeze:"left"
          },
          {
            headerText: "Số máy/Acc",
            autoFit: true,
            allowSorting: false,
            fieldName: "ma_tb",
            freeze:"left"
          },
          {
            headerText: "Loại hình",
            autoFit: true,
            allowSorting: false,
            fieldName: "loaihinh_tb",
            freeze:"left"
          },
          {
            headerText: "Ngày tiếp nhận",
            autoFit: true,
            allowSorting: false,
            fieldName: "ngay_tn",
          },
          {
            headerText: "Người khiếu nại",
            autoFit: true,
            allowSorting: false,
            fieldName: "nguoi_kn",
          },
          {
            headerText: "Điện thoại LH",
            autoFit: true,
            allowSorting: false,
            fieldName: "dienthoai_lh",
          },
          {
            headerText: "Địa chỉ LH",
            autoFit: true,
            allowSorting: false,
            fieldName: "diachi_lh",
          },
          {
            headerText: "Trạng thái",
            autoFit: true,
            allowSorting: false,
            customAttributes: {class: 'red'},
            fieldName: "trangthai_kn",
          },
          {
            headerText: "Chủ đề khiếu nại",
            autoFit: true,
            allowSorting: false,
            fieldName: "chude_kn",
          },
          {
            headerText: "Hình thức",
            autoFit: true,
            allowSorting: false,
            customAttributes: {class: 'blue'},
            fieldName: "hinhthuc_kn",
          },
          {
            headerText: "Nội dung khiếu nại",
            autoFit: true,
            allowSorting: false,
            fieldName: "nd_khieunai",
          },
          {
            headerText: "Nội dung GQ",
            autoFit: true,
            allowSorting: false,
            fieldName: "noidung_gq",
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
        ],
        ctkn: [
          {
            headerText: "Đơn vị giao",
            autoFit: true,
            allowSorting: false,
            fieldName: "dvg",
          },
          {
            headerText: "Nhân viên giao",
            autoFit: true,
            allowSorting: false,
            fieldName: "nv_giao",
          },
          {
            headerText: "ĐTNV giao",
            autoFit: true,
            allowSorting: false,
            fieldName: "sodt_nvgiao",
          },
          {
            headerText: "Ngày giao",
            autoFit: true,
            allowSorting: false,
            fieldName: "ngay_giao",
          },{
            headerText: "",
            fieldName: "huonggiao",
            isGroupedColumn: true,
          },
          {
            headerText: "Nội dung giao",
            fieldName: "nd_giao",
          },
          {
            headerText: "Đơn vị nhận",
            fieldName: "dv_nhan"
          },
          {
            headerText: "NV trả",
            fieldName: "nhanvien_tra",
          },
          {
            headerText: "Ngày trả",
            fieldName: "ngay_tra",
          },
          {
            headerText: "Nội dung trả",
            fieldName: "nd_tra",
          },
          {
            headerText: "Trạng thái phiếu",
            fieldName: "trangthai_ph",
          },
          {
            headerText: "Nhân viên thực hiện",
            fieldName: "nv_th",
          },
          {
            headerText: "ĐTNV thực hiện",
            fieldName: "sodt_nvth",
          },
          {
            headerText: "Ngày TH",
            fieldName: "ngay_th",
          },
          {
            headerText: "Nội dung TH",
            fieldName: "nd_th",
          },
          {
            headerText: "Ghi chú TH",
            fieldName: "ghichu_th",
          },
          {
            headerText: "ND xác minh",
            fieldName: "nd_xacminh",
          },
          {
            headerText: "Người CN",
            fieldName: "nguoi_cn",
          },
          {
            headerText: "Ngày CN",
            fieldName: "ngay_cn",
          }
        ],
        bangts: [],
        tc_hd: {},
        gq_kn: {},
        tt_nd: {},
        tcdanhba: {},
        tcthuno: {},
        cm: {},
        khieunai_id: 0,
        hdtb_id: 0,
        ma_tk: 0,
        ngay_cn: moment().format("DD/MM/YYYY HH:mm:ss"),
        dt_chuky: {},
        XACMINH_HEN_BAOHONG: -1,
        Quyen_Duoc_hen: 0,
        phieukn_id: 0,
        kieu_frm: 0,
        // Determine whether Windows XP or a later
        // operating system is present.

        // Declare a Hashtable array in which to store the groups.
        Hashtable: [],

        // Declare a variable to store the current grouping column.
        groupColumn: 0,
        tmp_tinh_id: '',
      };
    },
    props: ['p_ma_tb','p_tinh_id'],
    watch: {
      p_ma_tb: function (val) {
        this.$refs.txtMaTB.value = val
      },
      p_tinh_id: function (val) {
        this.tmp_tinh_id = val
      },
    },
    mounted() {
      //this.frmTraCuuPhieuKN_Load();
      //this.frmTraCuuPhieuTH_Activated()
    },
    methods: {
      emitTimKiemLichSuKhieuNai() {
        this.btnTimKiem_Click();
      },
      frmTraCuuTBKhieuNai_Close(frm) {
        if(frm.ma_tb) {
          console.log(frm)
          this.$refs.txtMaTB.value = frm.ma_tb;
          this.$refs.cboDichVuVT.value = frm.dichvuvt_id;
          if (this.$refs.txtMaTB.value == "") return;
          this.ma_tk = this.$refs.txtMaTB.value;
          this.Timkiem_Theo_MaTB();
        }
      },
      frmTraCuuPhieuKN(matb_tk) {
        this.ma_tk = matb_tk;
        this.$refs.txtMaTB.value = matb_tk;
      },
      async LoadDS_PhieuKN(khieunai_id) {
        try {
          let rs = await this.axios.post("/web-gqkn/khieunai/ds_phieu_khieunai", {
            khieunai_id: khieunai_id,
          });
          this.$refs.dgvChiTiet.dataSource = null;
          this.$refs.dgvChiTiet.dataSource = rs.data.data;
        } catch (err) {
          console.log(err);
        }
      },
      async LoadDS_KhieuNai(vthuebao_id, vmatb, kyhoadon) {
        try {
          this.loading(true)
          let rs;
          this.$refs.dgvChiTiet.dataSource = null;
          var d = this.bangts.filter(item => item.ma_ts == 'TRACUU_PHIEUKN_THEOMATB' && item.ten_ts == '1');

          if (d.length > 0) {
            rs = await this.axios.post(
              "web-gqkn/TraCuuPhieuKN/lay_danhsach_khieunai_theotb",
              {
                vkyhoadon: kyhoadon,
                vma_tb: vmatb,
              }
            );
            this.$refs.gridDanhSach.dataSource = rs.data.data;
            // gridColumn39.style.display = null
          } else {
            if (vthuebao_id != 0) {
              rs = await this.axios.post(
                "web-gqkn/TraCuuPhieuKN/lay_ds_khieunai_theo_thuebao_id",
                { vthuebao_id: vthuebao_id }
              );
              this.$refs.gridDanhSach.dataSource = rs.data.data;
            } else {
              rs = await this.axios.post(
                "web-gqkn/TraCuuPhieuKN/lay_ds_khieunai_theo_matb",
                {
                  vma_tb: vmatb,
                }
              );
              this.$refs.gridDanhSach.dataSource = rs.data.data;
            }
          }

          this.loading(false)
        } catch (err) {
          this.loading(false)
          console.log(err);
        }
      },
      async LoadDS_KhieuNai_Theo_MaKN(vma_kn) {
        try {
          this.loading(true)
          let rs;
          this.$refs.dgvChiTiet.dataSource = null;
          rs = await this.axios.post(
            "web-gqkn/TraCuuPhieuKN/lay_ds_khieunai_theo_ma_kn",
            {
              vma_kn: vma_kn,
            }
          );
          this.$refs.gridDanhSach.dataSource = rs.data.data;
          this.loading(false)
        } catch (err) {
          this.loading(false)
          console.log(err);
        }
      },
      async txtDienThoaiLH_KeyPress() {
        try {
          this.loading(true)
          let rs;
          this.$refs.dgvChiTiet.dataSource = null;
          rs = await this.axios.get(
            "web-quantri/khieunai/lay_ds_khieunai_theo_dtlh",
            {params: {
                vdienthoai_lh: this.$refs.txtDienThoaiLH.value,
              }}
          );
          this.$refs.gridDanhSach.dataSource = rs.data.data;
          this.loading(false)
        } catch (err) {
          this.loading(false)
          console.log(err);
        }
      },
      async frmTraCuuPhieuKN_Load() {
        //this.$refs.tsbtnNhanTin.style.display = "none"; //Hieutc tắt, ko cho sms trong lúc tra cứu
        let rs;
        rs = await this.axios.post(
          "web-baohong/TraCuuPhieuGiaoDenBH/sp_ht_dichvuvt_cbobox_lm",
          {
            p_nguoidung_id: this.$root.token.getNguoiDungID(),
          }
        );
        let str_vtemp;

        this.$refs.cboDichVuVT.HT_COMBOBOX(
          rs.data.data,
          "ten_dvvt",
          "dichvuvt_id"
        );

        rs = await this.axios.post(
          "web-gqkn/TraCuuPhieuKN/sp_lay_ds_trangthai_tb"
        );

        this.$refs.cboTrangThai.HT_COMBOBOX(
          rs.data.data,
          "trangthai_tb",
          "trangthaitb_id"
        );

        rs = await this.axios.post(
          "web-gqkn/TraCuuPhieuKN/lay_ds_thamso_macdinh_dv",
          {
            vkieu_id: 4,
          }
        );
        let ds = rs.data.data;
        this.bangts = ds

        if (this.Tag != null) {
          if (this.Tag.toString() == "1") {
            this.$refs.tsbtnHenKH.style.display = null;
            str_vtemp = "TTKD_CAPNHAT_HEN";
            let det = ds.filter(
              (x) =>
                x["ma_ts"].toString() == str_vtemp &&
                x["donvi_id"].toString() == this.$root.token.getDonViID()
            );
            if (det.length > 0) {
              let row = det[0];
              if (row["ten_ts"].toString().trim() == "1") this.Quyen_Duoc_hen = 1;
            }
          } else if (this.Tag.toString() == "2") {
            this.$refs.tsbtnHenKH.style.display = null;
            str_vtemp = "TTKD_CAPNHAT_HEN";
            let det = ds.filter(
              (x) =>
                x["ma_ts"].toString() == str_vtemp &&
                x["donvi_id"].toString() == this.$root.token.getDonViID()
            );
            if (det.length > 0) {
              row = det[0];
              if (row["ten_ts"].toString().trim() == "1") this.Quyen_Duoc_hen = 2;
            }
          } else {
            this.$refs.tsbtnHenKH.style.display = "none";
          }
        }

        rs = await this.axios.get("web-quantri/danhmuc/ds_thamso_md/0");
        this.ds_kt1 = rs.data.data;
        if (this.ds_kt1.length > 0) {
          str_vtemp = "XACMINH_HEN_BAOHONG"; //Hẹn báo hỏng có xác minh
          let det = this.ds_kt1.filter((x) => x["ma_ts"].toString() == str_vtemp);

          if (det.length > 0) {
            let row = det[0];
            if (row["ten_ts"].toString().trim() == "1")
              this.XACMINH_HEN_BAOHONG = 1;
          }
        }

        // let admin = localStorage.getItem("admin")
        // let data = JSON.parse(admin)

        rs = await this.axios.get('web-quantri/thamso-quanly-thuno/laythangno')
        // if ((rs.data && !rs.data.data.result)) {

        //   let date = moment(new Date()).add(-1, 'months')
        //   this.$refs.dtpKyHD.value = date
        // } else {
        this.$refs.dtpKyHD.value = moment(rs.data.data.result, "YYYYDDMM");
        // }

        if (this.kieu_frm == 1) this.Timkiem_Theo_MaTB();

        try {
          let ma_tb  = this.$route.query.ma_tb;
          if(ma_tb) {
            this.$refs.txtMaTB.value = ma_tb
            setTimeout(() => {
              this.txtMaTB_KeyUp()
            }, 200)
          }

        } catch(err) {
          console.log(err)
        }
      },
      txtMaTB_KeyUp() {
        if (!this.$refs.txtMaTB.value) return;
        if (!this.$refs.cboChuKy.value) {
          this.$toast.error(
            "Kỳ hóa đơn : " +
            this.$refs.dtpKyHD.value +
            " không có chu kỳ tính cước ! Bạn hãy xem lại"
          );
          return;
        }
        this.ma_tk = this.$refs.txtMaTB.value;
        this.Timkiem_Theo_MaTB();
      },

      async Timkiem_Theo_MaTB() {
        try {
          let vdvvt_id = 0;
          this.thuebao_id = 0;
          let rs;
          rs = await this.axios.post(
            "web-gqkn/TraCuuPhieuKN/lay_danhba_theo_matb",
            {
              in_dichvuvt_id: 0,
              in_ma_tb: this.$refs.txtMaTB.value.trim(),
            }
          );
          let ds_tb = rs.data.data;
          if (ds_tb.length > 0) {
            if (ds_tb.length == 1) {
              this.vdvvt_id = Number(ds_tb[0]["dichvuvt_id"].toString());
              this.thuebao_id = Number(ds_tb[0]["thuebao_id"].toString());
              this.ma_tk = ds_tb[0]["ma_tb"].toString();
            } else {
              this.$refs.frmTraCuu_ThueBao.frmTraCuu_ThueBao(ds_tb)
              this.$refs.frmTraCuu_ThueBao.show()
              return
              // frmTraCuu_ThueBao frm = new frmTraCuu_ThueBao(ds_tb);
              // frm.ShowDialog();
              // if (frm.chapnhan)
              // {
              //     int idex = frm.index;
              //     vdvvt_id = Number(ds_tb[idex]["dichvuvt_id"].toString());
              //     thuebao_id = Number(ds_tb[idex]["thuebao_id"].toString());
              //     ma_tk = ds_tb[idex]["ma_tb"].toString();
              // }
              // else
              // {
              //     return;
              // }
            }
            this.LoadDS_KhieuNai(this.thuebao_id, this.ma_tk, this.$refs.dtpKyHD.value);
          } else {
            this.LoadDS_KhieuNai(0, this.ma_tk, this.$refs.dtpKyHD.value);

            if (this.$refs.gridDanhSach.dataSource.length <= 0) {
              this.$refs.dgvChiTiet.dataSource = null;
              this.$refs.gridDanhSach.dataSource = null;
              this.$toast.error(
                "Không tìm thấy thông tin khiếu nại của thuê bao " +
                this.$refs.txtMaTB.value.trim()
              );
            }
          }
        } catch (err) {
          console.log(err);
        }
      },

      frmTraCuu_ThueBao_Close(item) {
        this.vdvvt_id = item.dichvuvt_id
        this.thuebao_id = item.thuebao_id
        this.ma_tk = item.ma_tb
        this.LoadDS_KhieuNai(this.thuebao_id, this.ma_tk, this.$refs.dtpKyHD.value);
      },

      async Timkiem_Theo_MaKN() {
        try {
          await this.LoadDS_KhieuNai_Theo_MaKN(this.$refs.txtMaKN.value.trim());

          if (this.$refs.gridDanhSach.dataSource.length <= 0) {
            this.$refs.dgvChiTiet.dataSource = null;
            this.$refs.gridDanhSach.dataSource = null;
            this.$toast.error(
              "Không tìm thấy thông tin khiếu nại của thuê bao " +
              this.$refs.txtMaTB.value.trim()
            );
          }
        } catch (err) {
          console.log(err);
        }
      },

      frmTraCuuPhieuTH_Activated() {
        this.$refs.txtMaTB.focus();
      },

      btnLayMaHD_Click() {
        this.vkyhoadon = "";
        this.ck = this.$refs.cboChuKy.value.toString();
        if (this.ck.length < 2) {
          this.ck = "0" + this.ck;
        }
        this.vkyhoadon += moment(this.$refs.dtpKyHD.value, 'MM/YYYY').format("YYYYMM")
        this.vkyhoadon += this.ck;
        this.$refs.frmTraCuuTBKhieuNai.frmTraCuuTBKhieuNai(0, 0, 0, 0, "0", 1, 0, 0);
        this.$refs.frmTraCuuTBKhieuNai.vkyhoadon = this.vkyhoadon;
        this.$refs.frmTraCuuTBKhieuNai.show()

        //frmTraCuuTBKhieuNai frm = new frmTraCuuTBKhieuNai(0, 0, 0, 0, "0", 1, (int)tt_nd.donvi_dl_id, 0);
        // frmTraCuuTBKhieuNai frm = new frmTraCuuTBKhieuNai(0, 0, 0, 0, "0", 1, 0, 0);
        // frm.vkyhoadon = vkyhoadon;
        // frm.ShowDialog();
        // if (frm.chapnhan)
        // {
        //     this.this.$refs.txtMaTB.value = frm.ma_tb;
        //     this.cboDichVuVT.value = frm.dichvu_vt;
        //     if (this.$refs.txtMaTB.value == "") return;
        //     ma_tk = this.$refs.txtMaTB.value;
        //     Timkiem_Theo_MaTB();
        // }
        this.$refs.txtMaTB.focus();
      },

      async dtpKyHD_ValueChanged() {
        /*try {
          //bangts.HT_Cbo_ChuKyTC_Theo_Thang_Nam(cboChuKy, Number(dtpKyHD.Value.ToString("MM")), Number(dtpKyHD.Value.ToString("yyyy")));
          let rs = await this.axios.post("web-gqkn/TraCuuPhieuKN/fn_lay_chukyno_theo_kyhoadon", {
            "p_namthang": moment(this.$refs.dtpKyHD.value, "MM/YYYY").format("YYYYMM")
          })
          let data = [{
            id: rs.data.data,
            text: rs.data.data
          }]
          this.$refs.cboChuKy.HT_COMBOBOX(data, "text", "id")
        } catch(err) {
          console.log(err)
        }*/
      },

      lvwThuebao_DoubleClick() {
        this.$refs.frmHoSoKhieuNai.khieunai_id = this.khieunai_id
        this.$refs.frmHoSoKhieuNai.show()
      },

      tsbtnNhanTin_Click() {
        // frmChonNV_SMS frm = new frmChonNV_SMS();
        // frm.loaidv_nhan_id = "0";
        // frm.khieunai_id = khieunai_id;
        // frm.ShowDialog();
      },

      tsbtnHenKH_Click() {
        this.$toast.error("Chức năng này hiện không còn khả dụng!")
      },

      tsbtnLichSuHen_Click() {
        if (this.khieunai_id != 0 && this.khieunai_id != -1) {
          // frmLS_HenKN frm = new frmLS_HenKN();
          // frm.khieunai_id = khieunai_id;
          // frm.ShowDialog();
        }
      },

      btnTimKiem_Click() {
        if (this.$refs.txtMaTB.value == "") return;
        /*if (this.$refs.cboChuKy.value == null) {
          this.$toast.error(
            "Kỳ hóa đơn : " +
            this.$refs.dtpKyHD.value +
            " không có chu kỳ tính cước ! Bạn hãy xem lại"
          );
          return;
        }*/

        this.ma_tk = this.$refs.txtMaTB.value;
        this.Timkiem_Theo_MaTB();
      },
      btnTimKiem_Click_V2() {
        try {
          if (this.$refs.txtMaTB.value != "" && this.tmp_tinh_id != '') {
            this.loading(true)

            EventBus.$emit('emitChuyenTinh', {id: String(this.tmp_tinh_id), reload : false, recall : 'emitTimKiemLichSuKhieuNai'});
          }
        } catch (err) {
          this.$toast.error(err.message)
        } finally {
          this.loading(false)
        }
      },
      gridChitiet_DoubleClick() {
        try {
          // khieunai_id = Number(this.$refs.gridviewDanhSach.GetRowCellValue(this.$refs.gridviewDanhSach.FocusedRowHandle, "KHIEUNAI_ID").toString());
          // frmHoSoKhieuNai frm = new frmHoSoKhieuNai();
          // frm.khieunai_id = khieunai_id;
          // frm.ShowDialog();
        } catch (err) {
          this.$toast.error("Có lỗi: " + err.message);
        }
      },

      gridChitiet_FocusedRowChanged() {
        //txtNguoiGiao.value = lvwTrangThaiTH.SelectedItems[0].SubItems[10].value;
        //txtNoiNhan.value = lvwTrangThaiTH.SelectedItems[0].SubItems[4].value;
        //txtNoiGiao.value = lvwTrangThaiTH.SelectedItems[0].SubItems[1].value;
        //phieukn_id = Number(lvwTrangThaiTH.SelectedItems[0].SubItems[13].value);
        try {
          let selected = this.$refs.gridChitiet.getSelectedRecords();
          if (selected.length > 0)
            this.phieukn_id = Number(selected[0]["PHIEU_ID"]);
        } catch (err) {}
      },

      gridviewDanhSach_FocusedRowChanged() {
        let selected = this.$refs.gridDanhSach.getSelectedRecords();
        if (selected.length > 0) {
          this.khieunai_id = Number(selected[0].khieunai_id);
          this.$refs.txtMaKN.value = selected[0].ma_kn;
          this.$refs.txtMaTB.value = selected[0].ma_tb;
          this.LoadDS_PhieuKN(this.khieunai_id);
        }
      },

      tsbtnTTGiaoViec_Click() {
        // phieu_id = Number(this.$refs.gridChitiet.GetRowCellValue(this.$refs.gridChitiet.FocusedRowHandle, "PHIEU_ID").toString());
        // frmNhanVienTH frm = new frmNhanVienTH();
        // frm.vphieu_kn_id = phieu_id;
        // frm.ShowDialog();
        let selected = this.$refs.dgvChiTiet.getSelectedRecords()
        if(selected.length > 0) {
          this.phieu_id = selected[0].phieu_id
          this.$refs.frmNhanVienTH.vphieu_kn_id = this.phieu_id
          this.$refs.frmNhanVienTH.show()
        }
      },

      btnInPhieuKN_Click() {
        try {
          if (this.khieunai_id != 0) {
            this.$toast.error("Chức năng này sẽ được bổ sung sau!");
            // frmInPhieuKN f = new frmInPhieuKN();
            // f.khieunai_id_t = khieunai_id.toString();
            // f.phieukn_id_t = phieukn_id.toString();
            // f.ShowDialog();
          } else {
            this.$toast.error("Chưa có dữ liệu khiếu nại!");
          }
        } catch (err) {
          this.$toast.error("" + err.message);
        }
      },

      txtMaKN_KeyPress() {
        if (this.$refs.txtMaKN.value == "") return;
        this.Timkiem_Theo_MaKN();
      },
    },
    destroyed() {
      // Stop listening the event hello with handler
      EventBus.$off('emitTimKiemLichSuKhieuNai', this.emitTimKiemLichSuKhieuNai)
    }
  };
</script>
<style>
  .blue {
    color: blue !important;
  }
</style>

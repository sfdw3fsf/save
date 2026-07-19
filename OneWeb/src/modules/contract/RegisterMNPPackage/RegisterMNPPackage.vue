<template src="./RegisterMNPPackage.html">

</template>

<script>
import breadcrumb from "@/components/breadcrumb";
import '@/assets/vendor/jquery/split'
import API from "./API";

export default {
  name: "RegisterMNPPackage",
  components: {
    breadcrumb
  },
  watch: {
    ckbIsDN: function (val) {
      this.ckbIsDN_CheckedChanged(val);
    },
  },
  data() {
    return {
      header: {
        title: "Đăng ký gói cước MNP",
        list: [
          {name: "Lập hợp đồng", link: {name: "Ui.cards"}},
          {name: "Chuyển mạng giữ số MNP", link: {name: "Ui.cards"}},
          {name: "Đăng ký gói cước MNP", link: {name: "Ui.cards"}},
        ],
      },
      soTB: '',
      ckbIsDN: false,
      //ttcn
      txtSoThueBao_CN: '',
      cbbGoiCuoc_CN: '',
      cbbGoiCuoc_CN_DS: [],
      cbbPortINOUT_CN: '',
      cbbPortINOUT_CN_DS: [],
      cbbChietKhau_CN: '',
      cbbChietKhau_CN_DS: [],
      cbbPhuongAn_CN: '',
      cbbPhuongAn_CN_DS: [],
      txtGhiChu_CN: '',
      ckbHieuLucNgay_CN: false,

      //ttdn
      txtSoThueBao_DN: '',
      cbbPortINOUT_DN: '',
      cbbPortINOUT_DN_DS: [],
      txtGhiChu_DN: '',
      cbbGoiCuoc_DN_TB: '',
      cbbGoiCuoc_DN_TB_DS: [],
      cbbPhuongAn_DN_TB: '',
      cbbPhuongAn_DN_TB_DS: [],
      cbbGoiCuoc_DN_DN: '',
      cbbGoiCuoc_DN_DN_DS: [],
      cbbPhuongAn_DN_DN: '',
      cbbPhuongAn_DN_DN_DS: [],

      //datagrid
      columns: [
        {fieldName: "stt", headerText: "STT", allowFiltering: true, allowSorting: false,},
        {fieldName: "ma_tb", headerText: "Số thuê bao", allowFiltering: true, allowSorting: false,},
        {fieldName: "goi_id", headerText: "Gói ID", allowFiltering: true, allowSorting: false,},
        {fieldName: "ten_goi", headerText: "Tên gói", allowFiltering: true, allowSorting: false,},
        {fieldName: "ngay_dk", headerText: "Ngày ĐK/Hủy", allowFiltering: true, allowSorting: false,},
        {fieldName: "ngay_kt", headerText: "Ngày kết thúc", allowFiltering: true, allowSorting: false,},
        {fieldName: "ten_ctkm", headerText: "Tên khuyến mại", allowFiltering: true, allowSorting: false,},
      ],
      grdTTChiTiet: [],
      ckbHieuLucNgay_CN_Enabled: false,
      cbbPortINOUT_CN_Enabled: false,
      cbbPortINOUT_DN_Enabled: false,

      userInfo: null,
      vma_tinh: '',
      portINOUT_DN: 0,

      dsThongTinChiTiet_CN: [],
      dsThongTinChiTiet_DN: [],

      vnhomtb_id_cn_huy: 0,
      vnhomtb_id_dn_huy: 0
    }
  },
  created() {
    window.addEventListener('keyup', (e) => {
      if (e.key == 'F3') {
        this.tsbtnTimKiem_Click();
      } else if (e.key == 'F6') {
        this.tsbtnHuyBo_Click();
      } else if (e.key == 'F9') {
        this.tsbtnDangKy_Click();
      }
    });
  },
  async mounted() {
    await API.thongtin_nguoidung2(this.axios).then(rs => {
      this.userInfo = rs.data.data;
      this.vma_tinh = this.userInfo.tentat_ccbs;
    })

    this.ckbHieuLucNgay_CN_Enabled = false;
    this.cbbPortINOUT_CN_Enabled = false;
    this.cbbPortINOUT_DN_Enabled = false;

    ////////////////////////////////////////////////////////////////////////////////
    this.cbbPortINOUT_CN_DS = [
      {"value": 1, "text": "PORT IN"},
      {"value": 2, "text": "PORT OUT"}
    ]

    ////////////////////////////////////////////////////////////////////////////////
    this.cbbPortINOUT_DN_DS = [
      {"value": 1, "text": "PORT IN"},
      {"value": 2, "text": "PORT OUT"}
    ]
  },
  methods: {
    ckbIsDN_CheckedChanged(ckbIsDN) {
      if (!ckbIsDN) {
        this.txtSoThueBao_CN = "";
        this.soTB = "";
        this.txtGhiChu_CN = "";
        this.ckbHieuLucNgay_CN = false;
        this.grdTTChiTiet = [];
        this.ckbHieuLucNgay_CN_Enabled = false;
        this.cbbPhuongAn_CN_DS = [];
        this.cbbGoiCuoc_CN_DS = [];
        this.cbbChietKhau_CN_DS = [];
      } else {
        this.txtSoThueBao_DN = "";
        this.soTB = "";
        this.txtGhiChu_DN = "";
        this.grdTTChiTiet = [];
        this.cbbGoiCuoc_DN_TB_DS = [];
        this.cbbGoiCuoc_DN_DN_DS = [];
        this.cbbPhuongAn_DN_TB_DS = [];
        this.cbbPhuongAn_DN_DN_DS = [];
      }
    },

    async check_CN(event) {
      try {
        if (this.txtSoThueBao_CN.trim().length > 0)
          this.soTB = "84" + this.txtSoThueBao_CN.trim();
        else
          this.soTB = "";
        let strCheck = await this.ts_mnp_check_tb(this.soTB);
        if (strCheck == null) {
          this.$toast.error("Lỗi khi kiểm tra thuê bao trên CCBS.");
          this.ckbIsDN_CheckedChanged(this.ckbIsDN);
          return;
        }

        if (strCheck == "0") {
          this.$toast.error("Thuê bao không tồn tại hoặc không phải trả sau");
          this.ckbIsDN_CheckedChanged(this.ckbIsDN);
          return;
        } else if (strCheck == "-1") {
          this.$toast.error("Thuê bao không thuộc đối tượng đăng ký gói port in/ port out");
          this.ckbIsDN_CheckedChanged(this.ckbIsDN);
          return;
        } else {
          if (strCheck == "1") {
            this.cbbPortINOUT_CN = "1";
            this.cbbPhuongAn_CN_DS = [
              {"value": "1", "text": "Phương án 1"},
              {"value": "2", "text": "Phương án 2"}
            ]
          } else if (strCheck == "2") {
            this.cbbPortINOUT_CN = "2";
            this.cbbPhuongAn_CN_DS = [
              {"value": "1", "text": "Phương án 1"},
              {"value": "2", "text": "Phương án 2"},
              {"value": "3", "text": "Phương án 3"}
            ]
          }

          this.TimKiem_CN();
        }
      } catch (ex) {
        this.$toast.error("Lỗi check_CN: ", ex);
        return;
      }
    },

    async check_DN(event) {
      try {
        let {txtSoThueBao_DN, soTB, vma_tinh} = this;
        if (txtSoThueBao_DN.trim().length > 0)
          soTB = "84" + txtSoThueBao_DN.trim();
        else
          soTB = "";

        let strCheck = await this.ts_mnp_check_tb_khdn(vma_tinh, soTB);
        if (strCheck == null) {
          this.$toast.error("Lỗi khi kiểm tra thuê bao trên Vinaphone.");
          this.ckbIsDN_CheckedChanged(this.ckbIsDN);
          return;
        }

        if (strCheck == "0") {
          this.$toast.error("Thuê bao không tồn tại hoặc không phải trả sau");
          this.ckbIsDN_CheckedChanged(this.ckbIsDN);
          return;
        } else if (strCheck == "-1") {
          this.$toast.error("Thuê bao không thuộc đối tượng đăng ký gói port in/ port out");
          this.ckbIsDN_CheckedChanged(this.ckbIsDN);
          return;
        } else if (strCheck == "-2") {
          this.$toast.error("Thuê bao không thuộc đối tượng KHDN");
          this.ckbIsDN_CheckedChanged(this.ckbIsDN);
          return;
        } else {
          if (strCheck == "1") {
            this.portINOUT_DN = 1;
            this.cbbPortINOUT_DN = "1";
            this.cbbGoiCuoc_DN_TB_DS = [];
            this.cbbGoiCuoc_DN_DN_DS = [];
            ///////////////tuanla 21032020 ///////////////
            let ds_get = await this.lay_ds_goi_mnp(soTB.substring(2, soTB.length - 2), "MNP_DN_PA1_", "MNP_TB_PA1", this.portINOUT_DN);
            let dtDSGoiCuoc_tb_dhsx = ds_get[0];
            this.dsThongTinChiTiet_DN = ds_get[1];
            let dtDSGoiCuoc_dn_dhsx = ds_get[2];

            if (dtDSGoiCuoc_tb_dhsx.length > 0) {
              let dsGoiCuoc_ccbs = await this.ts_mnp_ds_khdn_goicuoc("1", soTB, "1");
              let dtDSGoiCuoc_tb = [];
              for (let goiCuocDhsx of dtDSGoiCuoc_tb_dhsx) {
                for (let goiCuocCCBS of dsGoiCuoc_ccbs) {
                  if (goiCuocDhsx.GOI_VNP_ID == goiCuocCCBS.PACKAGE_ID) {
                    dtDSGoiCuoc_tb.push(goiCuocDhsx);
                  }
                }
              }

              this.cbbGoiCuoc_DN_TB_DS = dtDSGoiCuoc_tb;
            }

            if (dtDSGoiCuoc_dn_dhsx.length > 0) {
              let dsGoiCuoc_ccbs = await this.ts_mnp_ds_khdn_goicuoc("1", soTB, "3");
              let dtDSGoiCuoc_dn = [];
              for (let goiCuocDhsx of dtDSGoiCuoc_dn_dhsx) {
                for (let goiCuocCCBS of dsGoiCuoc_ccbs) {
                  if (goiCuocDhsx.GOI_VNP_ID == goiCuocCCBS.PACKAGE_ID) {
                    dtDSGoiCuoc_dn.push(goiCuocDhsx);
                  }
                }
              }

              this.cbbGoiCuoc_DN_DN_DS = dtDSGoiCuoc_dn;
            }

            this.TimKiem_DN();
          } else if (strCheck == "2") {
            this.portINOUT_DN = 2;
            this.cbbPortINOUT_DN = "2";
            this.cbbPhuongAn_DN_TB_DS = [
              {"text": "Phương án 1", "value": "1"},
              {"text": "Phương án 2", "value": "2"}
            ]

            this.cbbPhuongAn_DN_DN_DS = [
              {"text": "Phương án 1", "value": "1"},
              {"text": "Phương án 2", "value": "2"},
              {"text": "Phương án 3", "value": "3"},
            ]

            this.TimKiem_DN();
          }
        }
      } catch (ex) {
        this.$toast.error("Lỗi hàm check_DN: ", ex);
      }
    },

    tsbtnTimKiem_Click() {
      this.grdTTChiTiet = [];
      if (!this.ckbIsDN) {
        this.check_CN();
      } else {
        this.check_DN();
      }
    },

    tsbtnDangKy_Click() {
      if (!this.ckbIsDN) {
        this.dkyGoiCuocMNPCaNhan();
      } else {
        this.dkyGoiCuocMNPDoanhNghiep();
      }
    },

    tsbtnHuyBo_Click() {
      if (!this.ckbIsDN) {
        this.huyGoiCuocMNPCaNhan();
      } else {
        this.huyGoiCuocMNPDoanhNghiep();
      }
    },

    async ts_mnp_check_tb(so_tb) {
      try {
        let rs = await API.ts_mnp_check_tb(this.axios, so_tb);
        let data = this.getDataFromAPI(rs);
        return data.result;
      } catch (e) {
        this.$toast.error('Lỗi khi gọi CCBS API ts_mnp_check_tb: ', e);
      }
    },

    async ts_mnp_check_tb_khdn(vma_tinh, so_tb) {
      try {
        let rs = await API.ts_mnp_check_tb_khdn(this.axios, so_tb);
        let data = this.getDataFromAPI(rs);
        return data.result;
      } catch (e) {
        this.$toast.error('Lỗi khi gọi CCBS API ts_mnp_check_tb_khdn: ', e);
      }
    },

    TimKiem_CN() {
      if (this.soTB !== "") {
        this.grdTTChiTiet = this.dsThongTinChiTiet_CN;
      } else {
        this.$toast.error("Nhập thông tin tìm kiếm !");
      }
    },

    getDataFromAPI(rs) {
      if (rs.data != null && rs.data.data != null) {
        return rs.data.data;
      }

      return null;
    },

    async lay_ds_goi_mnp(vma_tb, vmnp, vmnp_tb, vinout) {
      try {
        let rs = await API.lay_ds_goi_mnp(this.axios, {
          vma_tb,
          vmnp,
          vmnp_tb,
          vinout
        });
        let data = this.getDataFromAPI(rs);
        console.log(data);
        return data;
      } catch (e) {
        this.$toast.error('Lỗi khi gọi CCBS API lay_ds_goi_mnp: ', e);
      }
    },

    async ts_mnp_ds_khdn_goicuoc(porttype, so_tb, plantype) {
      try {
        let rs = await API.ts_mnp_ds_khdn_goicuoc(this.axios, {
          porttype,
          so_tb,
          plantype
        });
        let data = this.getDataFromAPI(rs);
        console.log(data);
        return data;
      } catch (e) {
        this.$toast.error('Lỗi khi gọi CCBS API ts_mnp_ds_khdn_goicuoc: ', e);
      }
    },

    TimKiem_DN() {
      if (this.soTB !== "") {
        this.grdTTChiTiet = this.dsThongTinChiTiet_DN;
      } else {
        this.$toast.error("Nhập thông tin tìm kiếm !");
      }
    },

    async cbbPhuongAn_CN_EditValueChanged() {
      try {
        let {soTB} = this;
        ///////////////tuanla 21032020 ///////////////
        if (this.cbbPortINOUT_CN == "1") {
          this.ckbHieuLucNgay_CN = false;
          this.ckbHieuLucNgay_CN_Enabled = false;
        } else {
          if (this.cbbPhuongAn_CN == "3") {
            this.ckbHieuLucNgay_CN = true;
            this.ckbHieuLucNgay_CN_Enabled = true;
          } else {
            this.ckbHieuLucNgay_CN = false;
            this.ckbHieuLucNgay_CN_Enabled = false;
          }
        }
        this.cbbGoiCuoc_CN_DS = null;
        this.cbbChietKhau_CN_DS = null;
        let portINOUT = Number(this.cbbPortINOUT_CN);
        let ds = await this.lay_ds_goi_mnp(
          soTB.substring(2, soTB.length - 2),
          "MNP_CN_PA" + this.cbbPhuongAn_CN + "_",
          null,
          portINOUT
        );

        let dsGoiCuoc_dhsx = ds.vds_dk_goi;
        this.dsThongTinChiTiet_CN = ds.vds_goi_tb;

        let dsGoiCuoc_ccbs = await this.ts_mnp_ds_goicuoc(portINOUT, soTB, this.cbbPhuongAn_CN);
        let dsGoiCuoc = [];
        for (let goiCuocDhsx of dsGoiCuoc_dhsx) {
          for (let goiCuocCCBS of dsGoiCuoc_ccbs) {
            if (goiCuocDhsx.GOI_VNP_ID == goiCuocCCBS.PACKAGE_ID) {
              dsGoiCuoc.push(goiCuocDhsx);
            }
          }
        }

        this.cbbGoiCuoc_CN_DS = dsGoiCuoc;
      } catch (e) {
        this.$toast.error("Lỗi hàm cbbPhuongAn_CN_EditValueChanged");
        console.log("Lỗi hàm cbbPhuongAn_CN_EditValueChanged: ", e);
      }
    },

    async ts_mnp_ds_goicuoc(porttype, so_tb, plantype) {
      try {
        let rs = await API.ts_mnp_ds_goicuoc(this.axios, {
          porttype,
          so_tb,
          plantype
        });
        let data = this.getDataFromAPI(rs);
        console.log(data);
        return data;
      } catch (e) {
        this.$toast.error('Lỗi khi gọi CCBS API ts_mnp_ds_goicuoc: ', e);
      }
    },

    async cbbPhuongAn_DN_TB_EditValueChanged() {
      ///////////////tuanla 21032020 ///////////////
      let {soTB} = this;
      this.cbbGoiCuoc_DN_TB_DS = null;
      let ds = await this.lay_ds_goi_mnp(soTB.substring(2, soTB.length - 2), "MNP_DN_PA" + this.cbbPhuongAn_DN_TB + "_", "MNP_TB_PA" + this.cbbPhuongAn_DN_TB, this.portINOUT_DN);
      let dtDSGoiCuoc_DN_dhsx = ds.vds_goi_tb;
      this.dsThongTinChiTiet_DN = ds.vds_dk_goi;

      let dtDSGoiCuoc_DN_ccbs = await this.ts_mnp_ds_khdn_goicuoc(this.portINOUT_DN, soTB, this.cbbPhuongAn_DN_TB);
      let dtDSGoiCuoc_DN = [];
      for (let goiCuocDhsx of dtDSGoiCuoc_DN_dhsx) {
        for (let goiCuocCCBS of dtDSGoiCuoc_DN_ccbs) {
          if (goiCuocDhsx.GOI_VNP_ID == goiCuocCCBS.PACKAGE_ID) {
            dtDSGoiCuoc_DN.push(goiCuocDhsx);
          }
        }
      }

      this.cbbGoiCuoc_DN_TB_DS = dtDSGoiCuoc_DN;
    },

    async cbbPhuongAn_DN_DN_EditValueChanged() {
      ///////////////tuanla 21032020 ///////////////
      let {soTB} = this;
      this.cbbGoiCuoc_DN_DN_DS = null;
      let ds = await this.lay_ds_goi_mnp(soTB.substring(2, soTB.length - 2), "MNP_DN_PA" + this.cbbPhuongAn_DN_DN + "_", "MNP_TB_PA" + this.cbbPhuongAn_DN_DN, this.portINOUT_DN);
      let dtDSGoiCuoc_DN_dhsx = ds[2];
      this.dsThongTinChiTiet_DN = ds[1];

      let dtDSGoiCuoc_DN_ccbs = await this.ts_mnp_ds_khdn_goicuoc(
        this.portINOUT_DN == 1 ? this.portINOUT_DN : "3",
        soTB,
        this.portINOUT_DN == 1 ? "3" : this.cbbPhuongAn_DN_DN
      );
      let dtDSGoiCuoc_DN = [];
      for (let goiCuocDhsx of dtDSGoiCuoc_DN_dhsx) {
        for (let goiCuocCCBS of dtDSGoiCuoc_DN_ccbs) {
          if (goiCuocDhsx.GOI_VNP_ID == goiCuocCCBS.PACKAGE_ID) {
            dtDSGoiCuoc_DN.push(goiCuocDhsx);
          }
        }
      }

      this.cbbGoiCuoc_DN_DN_DS = dtDSGoiCuoc_DN;
    },

    async cbbGoiCuoc_CN_EditValueChanged() {
      try {
        let goi_id = "";
        for (const cbbGoiCuoc of this.cbbGoiCuoc_CN_DS) {
          if (cbbGoiCuoc.GOI_VNP_ID == this.cbbGoiCuoc_CN) {
            goi_id = cbbGoiCuoc.GOI_ID;
          }
        }

        let dsChietKhau_dhsx = await this.lay_ds_km_mnp(goi_id);
        dsChietKhau_dhsx = dsChietKhau_dhsx.vds_km;
        let dsChietKhau_ccbs = await this.ts_mnp_ds_goicuoc(this.cbbPortINOUT_CN, this.soTB, this.cbbPhuongAn_CN == "2" && this.cbbPortINOUT_CN == "1" ? "99" : "0");
        let dsChietKhau = [];
        for (let goiCuocDhsx of dsChietKhau_dhsx) {
          for (let goiCuocCCBS of dsChietKhau_ccbs) {
            if (goiCuocDhsx.CTKM_NEO_ID == goiCuocCCBS.PACKAGE_ID) {
              dsChietKhau.push(goiCuocDhsx);
            }
          }
        }

        this.cbbChietKhau_CN_DS = dsChietKhau;
      } catch (ex) {
      }
    },

    async lay_ds_km_mnp(vgoi_id) {
      try {
        let rs = await API.lay_ds_km_mnp(this.axios, {
          vgoi_id
        });
        let data = this.getDataFromAPI(rs);
        console.log(data);
        return data;
      } catch (e) {
        this.$toast.error('Lỗi khi gọi API lay_ds_km_mnp: ', e);
      }
    },

    async huyGoiCuocMNPCaNhan() {
      try {
        let congvan_id_neo = "";
        let goi_id = "";
        let dtTTChiTiet = this.grdTTChiTiet;
        if (dtTTChiTiet == null) {
          this.$toast.error("Vui lòng liệt kê danh sách gói!");
          return;
        }

        dtTTChiTiet = this.$refs.grdTTChiTiet.getSelectedRecords();
        for (let i = 0; i < dtTTChiTiet.length; i++) {
          congvan_id_neo = dtTTChiTiet[i]["congvan_neo_id"];
          goi_id = dtTTChiTiet[i]["goi_id"];
          this.vnhomtb_id_cn_huy = Number(dtTTChiTiet[i]["nhomtb_id"]);
          break;
        }

        if (congvan_id_neo == "" || goi_id == "") {
          this.$toast.error("Không có gói nào được chọn!");
          return;
        }

        let dtGetData_id = await this.ts_mnp_ds_timkiem(this.soTB);
        if (dtGetData_id == null || dtGetData_id.length == 0) {
          this.$toast.error("Không lấy được data id của số cần hủy!");
          return;
        }

        let table = [];
        for (const dt of dtGetData_id) {
          if (dt.PRO_ID == congvan_id_neo) {
            table.push(dt);
          }
        }

        if (table[0]["check_huy"] !== "") {
          this.$toast.error("Gói này không được hủy!");
          return;
        }

        let confirm = await this.$confirm('Bạn thật sự muốn hủy gói được chọn không ?', 'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(() => true).catch(() => false);
        if (!confirm)
          return;

        let kq = await this.ts_mnp_huy_goi(congvan_id_neo, this.txtGhiChu_CN.trim(), table[0]["data_id"]);
        if (kq !== "1" && kq.toLowerCase() !== "success") {
          this.$toast.error("Lỗi khi hủy gói trên CCBS: " + kq + ".");
          return;
        }

        ////////////////////// CẬP NHẬT DỮ LIỆU VÀO ĐHSX //////////////////////
        let getthuebao_id = await this.sp_lay_thuebao_id_huygoicuocmnp(this.soTB.substring(2, this.soTB.length - 2));
        if (getthuebao_id.length == 0) {
          this.$toast.error("Hủy gói thành công trên CCBS, lỗi khi hủy gói trên ĐHSX!");
          return;
        }

        await this.check_CN();
        this.$toast.success("Hủy gói thành công!");
      } catch (ex) {
        this.$toast.error("Có lỗi xảy ra: " + ex);
        console.log("Có lỗi xảy ra huyGoiCuocMNPCaNhan: " + ex);
      }
    },

    async ts_mnp_ds_timkiem(so_tb) {
      try {
        let rs = await API.ts_mnp_ds_timkiem(this.axios, {
          so_tb
        });
        let data = this.getDataFromAPI(rs) || [];
        console.log(data);
        return data;
      } catch (e) {
        this.$toast.error('Lỗi khi gọi API ts_mnp_ds_timkiem: ', e);
      }
    },

    async dkyGoiCuocMNPCaNhan() {
      try {
        if (this.cbbGoiCuoc_CN == null) {
          this.$toast.error("Chọn gói cước.");
          return;
        }
        let goi_id_dhsx = "";
        let congvan_id_neo = "";
        for (const cbbGoiCuocCN of this.cbbGoiCuoc_CN_DS) {
          if (cbbGoiCuocCN.GOI_VNP_ID == this.cbbGoiCuoc_CN) {
            congvan_id_neo = cbbGoiCuocCN.congvan_neo_id;
            goi_id_dhsx = cbbGoiCuocCN.goi_id;
            break;
          }
        }

        let getthuebao_id = await this.sp_lay_thuebao_id_dkygoicuocmnpcanhan(this.soTB.substring(2, this.soTB.length - 2));
        if (getthuebao_id.length == 0) {
          this.$toast.error("Không tìm thấy ID của thuê bao!");
          return;
        }
        let thuebao_id = getthuebao_id[0][0];

        let checkDKGoi = await this.sp_lay_biendong_goidadv_id_dkygoicuocmnpcanhan(thuebao_id, goi_id_dhsx);
        if (checkDKGoi.length > 0) {
          this.$toast.error("Gói này đã được đăng ký cho thuê bao!");
          return;
        }

        let confirm = await this.$confirm('Bạn thật sự muốn đăng ký gói được chọn không ?', 'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(() => true).catch(() => false);
        if (!confirm)
          return;

        ////////////////////////
        let goi_id = this.cbbGoiCuoc_CN;
        let dsmay = goi_id + ":" + this.soTB;
        let hieuluc = this.ckbHieuLucNgay_CN ? "1" : "0";
        let kq = await this.ts_mnp_dangky_goi(congvan_id_neo, dsmay, hieuluc, this.txtGhiChu_CN.trim());
        if (kq != "1" && kq != "Success") {
          this.$toast.error("Lỗi khi đăng ký gói MNP lên CCBS " + kq + "");
          return;
        }
        //////////////////////// ĐĂNG KÝ KHUYẾN MẠI ////////////////////////
        let isSuccessDKKM = false;
        let congvan_id_km = "";
        let km_id_dhsx = "";
        let isDaDKKM = false;
        if (this.cbbChietKhau_CN) {
          for (const ck of this.cbbChietKhau_CN_DS) {
            if (ck.CTKM_NEO_ID == this.cbbChietKhau_CN) {
              congvan_id_km = ck.CONGVAN_NEO_ID;
              km_id_dhsx = ck.chitietkm_id;
            }
          }

          ////////////////////////
          let checkDKKM = await this.sp_lay_thuebao_id_khuyenmai_dbtb_dkygoicuocmnpcanhan(thuebao_id, km_id_dhsx);
          isDaDKKM = checkDKKM.length != 0;
          if (!isDaDKKM) {
            dsmay = this.cbbChietKhau_CN + ":" + this.soTB;
            kq = await this.ts_mnp_dangky_goi(congvan_id_km, dsmay, hieuluc, this.txtGhiChu_CN);
            if (kq == "1" || kq == "Success")
              isSuccessDKKM = true;
          }
        }

        ////////////////////// CẬP NHẬT DỮ LIỆU VÀO ĐHSX //////////////////////
        let nhomtb_id = 0;
        let nhomtb_id_km = 0;

        let ds_temp = await this.cn_goi_mnp(Number(thuebao_id), goi_id_dhsx, null, isSuccessDKKM ? Number(km_id_dhsx) : null, null, null, null, null, 1);
        if (ds_temp.length > 0) {
          let table_ds_temp = [];
          for (const temp of ds_temp) {
            if (temp.LOAI == "NHOMTB_ID_CN") {
              table_ds_temp.push(temp);
            }
          }

          if (table_ds_temp.length > 0) {
            nhomtb_id = Number(table_ds_temp[0]["NHOMTB_ID"]);
          }

          if (isSuccessDKKM) {
            let table_ds_temp_km = [];
            for (const temp of ds_temp) {
              if (temp.LOAI == "RKM_ID") {
                table_ds_temp_km.push(temp);
              }
            }

            if (table_ds_temp_km.length > 0) {
              nhomtb_id_km = Number(table_ds_temp_km[0]["NHOMTB_ID"]);
            }
          }
        }

        if (nhomtb_id == 0) {
          this.$toast.success("Đăng ký gói cước và khuyến mại MNP lên CCBS thành công, lỗi khi lưu dữ liệu lên vùng");
          return;
        }

        let _dt_nhomtb = await this.ts_mnp_ds_timkiem(this.soTB);
        if (_dt_nhomtb.length > 0) {
          let table = [];
          for (const dt of _dt_nhomtb) {
            if (dt.PRO_ID == congvan_id_neo) {
              table.push(dt);
            }
          }

          if (table.length > 0) {
            await this.cn_nhomtb_id_neo(nhomtb_id, Number(table[0]["data_id"]));
          }
        }

        if (isSuccessDKKM) {
          let dtKM = await this.ts_get_package_info(this.soTB, this.cbbChietKhau_CN);
          if (dtKM.length > 0) {
            await this.cn_nhomkm_id_neo(nhomtb_id_km, dtKM[0]["id"]);
          }
        }

        if (isDaDKKM)
          this.$toast.success("Đăng ký gói cước MNP thành công, khuyến mại được chọn đã được đăng ký cho thuê bao!");
        else if (!isSuccessDKKM && (cbbChietKhau_CN.EditValue != null ? cbbChietKhau_CN.EditValue.ToString() != "" : false))
          this.$toast.error("Đăng ký gói MNP lên CCBS thành công. Lỗi khi đăng ký khuyến mại lên CCBS " + kq + "");
        else
          this.$toast.success("Đăng ký gói cước MNP thành công !");
        await this.check_CN();
      } catch (ex) {
        this.$toast.error("Có lỗi xảy ra: ", ex);
        console.log("Có lỗi xảy ra: ", ex);
      }
    },

    async ts_mnp_huy_goi(congvan_id, ghichu, so_tb) {
      try {
        let rs = await API.ts_mnp_huy_goi(this.axios, {
          congvan_id, ghichu, so_tb
        });
        let data = this.getDataFromAPI(rs);
        console.log(data);
        return data;
      } catch (e) {
        this.$toast.error('Lỗi khi gọi API ts_mnp_huy_goi: ', e);
      }
    },

    async sp_lay_thuebao_id_huygoicuocmnp(vma_tb) {
      try {
        let rs = await API.sp_lay_thuebao_id_huygoicuocmnp(this.axios, {
          vma_tb
        });
        let data = this.getDataFromAPI(rs);
        console.log(data);
        return data || [];
      } catch (e) {
        this.$toast.error('Lỗi khi gọi API sp_lay_thuebao_id_huygoicuocmnp: ', e);
      }
    },

    async huyGoiCuocMNPDoanhNghiep() {
      try {
        let congvan_id_neo = "";
        let goi_id = "";
        let dtTTChiTiet = this.grdTTChiTiet;
        if (dtTTChiTiet == null) {
          this.$toast.error("Vui lòng liệt kê danh sách gói!");
          return;
        }

        dtTTChiTiet = this.$refs.grdTTChiTiet.getSelectedRecords();
        for (let i = 0; i < dtTTChiTiet.length; i++) {
          congvan_id_neo = dtTTChiTiet[i]["congvan_neo_id"];
          goi_id = dtTTChiTiet[i]["goi_id"];
          if (dtTTChiTiet[i]["loaigoi"] == "CN")
            this.vnhomtb_id_cn_huy = Number(dtTTChiTiet[i]["nhomtb_id"]);
          else
            this.vnhomtb_id_dn_huy = Number(dtTTChiTiet[i]["nhomtb_id"]);
          break;
        }

        if (congvan_id_neo == "" || goi_id == "") {
          this.$toast.error("Không có gói nào được chọn!");
          return;
        }

        let dtGetData_id = await this.ts_mnp_ds_timkiem(this.soTB);
        if (dtGetData_id == null || dtGetData_id.length == 0) {
          this.$toast.error("Không lấy được data id của số cần hủy!");
          return;
        }


        let table = [];
        for (const dt of dtGetData_id) {
          if (dt.PRO_ID == congvan_id_neo) {
            table.push(dt);
          }
        }

        if (table[0]["check_huy"] !== "") {
          this.$toast.error("Gói này không được hủy!");
          return;
        }

        let confirm = await this.$confirm('Bạn thật sự muốn hủy gói được chọn không ?', 'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(() => true).catch(() => false);
        if (!confirm)
          return;

        let kq = await this.ts_mnp_khdn_huy_goi(congvan_id_neo, this.txtGhiChu_DN.trim(), table[0]["data_id"]);
        if (kq !== "1" && kq.toLowerCase() !== "success") {
          this.$toast.error("Lỗi khi hủy gói trên CCBS: " + kq + ".");
          return;
        }
        ////////////////////// CẬP NHẬT DỮ LIỆU VÀO ĐHSX //////////////////////
        let getthuebao_id = await this.sp_lay_thuebao_id_huygoicuocmnp(this.soTB.substring(2, this.soTB.length - 2));
        if (getthuebao_id.length == 0) {
          this.$toast.error("Hủy gói thành công trên CCBS, lỗi khi hủy gói trên ĐHSX!");
          return;
        }

        await this.check_DN();
        this.$toast.success("Hủy gói thành công!");

      } catch (ex) {
        this.$toast.error("Có lỗi xảy ra: " + ex);
        console.log("Có lỗi xảy ra huyGoiCuocMNPDoanhNghiep: " + ex);
      }
    },

    async ts_mnp_khdn_huy_goi(congvan_id, ghichu, so_tb) {
      try {
        let rs = await API.ts_mnp_khdn_huy_goi(this.axios, {
          congvan_id, ghichu, so_tb
        });
        let data = this.getDataFromAPI(rs);
        console.log(data);
        return data;
      } catch (e) {
        this.$toast.error('Lỗi khi gọi API ts_mnp_khdn_huy_goi: ', e);
      }
    },

    async sp_lay_thuebao_id_dkygoicuocmnpcanhan(vma_tb) {
      try {
        let rs = await API.sp_lay_thuebao_id_dkygoicuocmnpcanhan(this.axios, {
          vma_tb
        });
        let data = this.getDataFromAPI(rs) || [];
        console.log(data);
        return data;
      } catch (e) {
        this.$toast.error('Lỗi khi gọi API sp_lay_thuebao_id_dkygoicuocmnpcanhan: ', e);
      }
    },

    async sp_lay_biendong_goidadv_id_dkygoicuocmnpcanhan(vthuebao_id, vgoi_id_dhsx) {
      try {
        let rs = await API.sp_lay_biendong_goidadv_id_dkygoicuocmnpcanhan(this.axios, {
          vthuebao_id, vgoi_id_dhsx
        });
        let data = this.getDataFromAPI(rs) || [];
        console.log(data);
        return data;
      } catch (e) {
        this.$toast.error('Lỗi khi gọi API sp_lay_biendong_goidadv_id_dkygoicuocmnpcanhan: ', e);
      }
    },

    async ts_mnp_dangky_goi(congvan_id, dsmay, hieuluc, ghichu) {
      try {
        let rs = await API.ts_mnp_dangky_goi(this.axios, {
          congvan_id, dsmay, hieuluc, ghichu
        });
        let data = this.getDataFromAPI(rs);
        console.log(data);
        return data;
      } catch (e) {
        this.$toast.error('Lỗi khi gọi API ts_mnp_dangky_goi: ', e);
      }
    },

    async sp_lay_thuebao_id_khuyenmai_dbtb_dkygoicuocmnpcanhan(vthuebao_id, vkm_id_dhsx) {
      try {
        let rs = await API.sp_lay_thuebao_id_khuyenmai_dbtb_dkygoicuocmnpcanhan(this.axios, {
          vthuebao_id, vkm_id_dhsx
        });
        let data = this.getDataFromAPI(rs) || [];
        console.log(data);
        return data;
      } catch (e) {
        this.$toast.error('Lỗi khi gọi API sp_lay_thuebao_id_khuyenmai_dbtb_dkygoicuocmnpcanhan: ', e);
      }
    },

    async cn_goi_mnp(
      vthuebao_id,
      vgoi_cn_id,
      vgoi_dn_id,
      vctkm_id,
      vgoi_tb_id,
      vnhomtb_id_cn_huy,
      vnhomtb_id_dn_huy,
      vnhomtb_id_tb_huy,
      vloai,
    ) {
      try {
        let rs = await API.cn_goi_mnp(this.axios, {
          vthuebao_id,
          vgoi_cn_id,
          vgoi_dn_id,
          vctkm_id,
          vgoi_tb_id,
          vnhomtb_id_cn_huy,
          vnhomtb_id_dn_huy,
          vnhomtb_id_tb_huy,
          vloai,
        });
        let data = this.getDataFromAPI(rs) || [];
        console.log(data);
        return data;
      } catch (e) {
        this.$toast.error('Lỗi khi gọi API cn_goi_mnp: ', e);
      }
    },

    async cn_nhomkm_id_neo(vrkm_id, vnhomkm_id_neo) {
      try {
        let rs = await API.cn_nhomkm_id_neo(this.axios, {
          vrkm_id, vnhomkm_id_neo
        });
        let data = this.getDataFromAPI(rs);
        console.log(data);
        return data;
      } catch (e) {
        this.$toast.error('Lỗi khi gọi API cn_nhomkm_id_neo: ', e);
      }
    },

    async ts_get_package_info(so_tb, package_id) {
      try {
        let rs = await API.ts_get_package_info(this.axios, {
          so_tb, package_id
        });
        let data = this.getDataFromAPI(rs) || [];
        console.log(data);
        return data;
      } catch (e) {
        this.$toast.error('Lỗi khi gọi API ts_get_package_info: ', e);
      }
    },

    async dkyGoiCuocMNPDoanhNghiep() {
      try {
        if (!this.cbbGoiCuoc_DN_TB && !this.cbbGoiCuoc_DN_DN) {
          this.$toast.error("Chọn ít nhất 1 gói cước.");
          return;
        }

        //check acccount có phải là giao dịch viên không
        let getIsGDV = await this.sp_check_acccount_gdv_dkygoicuocmnpdoanhnghiep(this.$root.token.getNhanVienID());
        let isGDV = getIsGDV.length > 0;

        //Đăng ký chính sách cho thuê bao
        if (this.cbbGoiCuoc_DN_TB) {
          let congvan_id = "";
          let goi_id_dhsx = "";

          for (const tb of this.cbbGoiCuoc_DN_TB_DS) {
            if (tb.GOI_VNP_ID == this.cbbGoiCuoc_DN_TB) {
              congvan_id = tb.congvan_neo_id;
              goi_id_dhsx = tb.goi_id;
              break;
            }
          }

          ////////////////////
          let getthuebao_id = await this.sp_lay_thuebao_id_dkygoicuocmnpdoanhnghiep(this.soTB.substring(2, this.soTB.length - 2));
          if (getthuebao_id.length == 0) {
            this.$toast.error("Không tìm thấy ID của thuê bao!");
            return;
          }
          let thuebao_id = getthuebao_id[0][0];

          let checkDKGoi = await this.sp_lay_biendong_goidadv_id_dkygoicuocmnpcanhan(thuebao_id, goi_id_dhsx);
          if (checkDKGoi.length > 0) {
            this.$toast.error("Gói này đã được đăng ký cho thuê bao!");
            return;
          }

          let confirm = await this.$confirm('Bạn muốn đăng ký gói cước chính sách cho thuê bao không ?', 'Thông báo', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
          }).then(() => true).catch(() => false);
          if (!confirm)
            return;

          ////////////////////
          let goi_id = this.cbbGoiCuoc_DN_TB;
          let dsmay = goi_id + ":" + this.soTB;
          let kq = await this.ts_mnp_khdn_dangky_goi(congvan_id, dsmay, "0", this.txtGhiChu_DN.trim());
          if (kq != "1" && kq != "Success") {
            this.$toast.error("Lỗi khi đăng ký gói chính sách cho thuê bao MNP lên CCBS " + kq + "");
            return;
          }

          ////////////////////// CẬP NHẬT DỮ LIỆU VÀO ĐHSX //////////////////////
          let nhomtb_id = 0;
          let ds_temp = await this.cn_goi_mnp(Number(thuebao_id), null, null, null, Number(goi_id_dhsx), null, null, null, 1);
          if (ds_temp.length > 0) {
            let table_ds_temp = [];
            for (const tmp of ds_temp) {
              if (tmp.LOAI == "NHOMTB_ID_TB") {
                table_ds_temp.push(tmp)
              }
            }

            if (table_ds_temp.length > 0) {
              nhomtb_id = Number(table_ds_temp[0]["NHOMTB_ID"]);
            }
          }

          if (nhomtb_id == 0) {
            this.$toast.error("Đăng ký gói cước chính sách cho thuê bao lên CCBS thành công, lỗi khi lưu dữ liệu lên vùng");
            return;
          }

          let _dt_nhomtb = await this.ts_mnp_ds_timkiem(this.soTB);
          if (_dt_nhomtb.length > 0) {
            let table = [];
            for (const tmp of _dt_nhomtb) {
              if (tmp.PRO_ID == congvan_id) {
                table.push(tmp);
              }
            }

            if (table.length > 0) {
              await this.cn_nhomtb_id_neo(nhomtb_id, table[0]["data_id"]);
            }
          }
          await this.check_DN();
          this.$toast.success("Đăng ký gói cước chính sách cho thuê bao MNP thành công !");
          if (isGDV) return;
        }

        //Đăng ký chính sách cho doanh nghiệp
        if (this.cbbGoiCuoc_DN_DN) {
          let congvan_id = "";
          let goi_id_dhsx = "";
          for (const row of this.cbbGoiCuoc_DN_DN_DS) {
            if (row.GOI_VNP_ID == this.cbbGoiCuoc_DN_DN) {
              congvan_id = row["congvan_neo_id"];
              goi_id_dhsx = row["goi_id"];
              break;
            }
          }

          ////////////////////
          let getthuebao_id = await this.sp_lay_thuebao_id_dkygoicuocmnpdoanhnghiep(this.soTB.substring(2, this.soTB.length - 2));
          if (getthuebao_id.length == 0) {
            this.$toast.error("Không tìm thấy ID của thuê bao!");
            return;
          }

          let thuebao_id = getthuebao_id[0][0];

          let checkDKGoi = this.sp_lay_biendong_goidadv_id_dkygoicuocmnpcanhan(thuebao_id, goi_id_dhsx);
          if (checkDKGoi.length > 0) {
            this.$toast.error("Gói này đã được đăng ký cho thuê bao!");
            return;
          }

          if (!this.cbbGoiCuoc_DN_TB) {
            let confirm = await this.$confirm('Bạn muốn đăng ký gói cước chính sách cho doanh nghiệp không ?', 'Thông báo', {
              confirmButtonText: 'Có',
              cancelButtonText: 'Không'
            }).then(() => true).catch(() => false);
            if (!confirm)
              return;
          }

          ////////////////////
          let goi_id = this.cbbGoiCuoc_DN_DN;
          let dsmay = goi_id + ":" + this.soTB;
          let kq = await this.ts_mnp_khdn_dangky_goi(congvan_id, dsmay, "0", this.txtGhiChu_DN.trim());
          if (kq != "1" && kq != "Success") {
            this.$toast.error("Lỗi khi đăng ký gói chính sách cho doanh nghiệp MNP lên CCBS " + kq + "");
            return;
          }

          ////////////////////// CẬP NHẬT DỮ LIỆU VÀO ĐHSX //////////////////////
          let nhomtb_id = 0;
          let ds_temp = await this.cn_goi_mnp(Number(thuebao_id), null, Number(goi_id_dhsx), null, null, null, null, null, 1);
          if (ds_temp.length > 0) {
            let table_ds_temp = [];
            for (const temp of ds_temp) {
              if (temp.LOAI == "NHOMTB_ID_DN") {
                table_ds_temp.push(temp);
              }
            }

            if (table_ds_temp.length > 0) {
              nhomtb_id = Number(table_ds_temp[0]["NHOMTB_ID"]);
            }
          }

          if (nhomtb_id == 0) {
            this.$toast.error("Đăng ký gói cước chính sách cho doanh nghiệp MNP lên CCBS thành công, lỗi khi lưu dữ liệu lên vùng");
            return;
          }

          let _dt_nhomtb = await this.ts_mnp_ds_timkiem(this.soTB);
          if (_dt_nhomtb.length > 0) {
            let table = [];
            for (const dt of _dt_nhomtb) {
              if (dt.PRO_ID == congvan_id) {
                table.push(dt);
              }
            }

            if (table.length > 0) {
              await this.cn_nhomtb_id_neo(nhomtb_id, table[0]["data_id"]);
            }
          }
          await this.check_DN();
          this.$toast.success("Đăng ký gói cước chính sách cho doanh nghiệp MNP thành công !");
        }
      } catch (ex) {
        this.$toast.error("Có lỗi xảy ra: ", ex);
        console.log("Có lỗi xảy ra: ", ex);
      }
    },

    async sp_check_acccount_gdv_dkygoicuocmnpdoanhnghiep(vnhanvien_id) {
      try {
        let rs = await API.sp_check_acccount_gdv_dkygoicuocmnpdoanhnghiep(this.axios, {
          vnhanvien_id
        });
        let data = this.getDataFromAPI(rs) || [];
        console.log(data);
        return data;
      } catch (e) {
        this.$toast.error('Lỗi khi gọi API sp_check_acccount_gdv_dkygoicuocmnpdoanhnghiep: ', e);
      }
    },

    async sp_lay_thuebao_id_dkygoicuocmnpdoanhnghiep(vma_tb) {
      try {
        let rs = await API.sp_lay_thuebao_id_dkygoicuocmnpdoanhnghiep(this.axios, {
          vnhanvien_id: vma_tb
        });
        let data = this.getDataFromAPI(rs) || [];
        console.log(data);
        return data;
      } catch (e) {
        this.$toast.error('Lỗi khi gọi API sp_lay_thuebao_id_dkygoicuocmnpdoanhnghiep: ', e);
      }
    },

    async ts_mnp_khdn_dangky_goi(congvan_id, dsmay, hieuluc, ghichu) {
      try {
        let rs = await API.ts_mnp_khdn_dangky_goi(this.axios, {
          congvan_id, dsmay, hieuluc, ghichu
        });
        let data = this.getDataFromAPI(rs);
        console.log(data);
        return data;
      } catch (e) {
        this.$toast.error('Lỗi khi gọi API ts_mnp_khdn_dangky_goi: ', e);
      }
    },

    async cn_nhomtb_id_neo(vnhomtb_id, vnhomtb_id_neo) {
      try {
        let rs = await API.cn_nhomtb_id_neo(this.axios, {
          vnhomtb_id, vnhomtb_id_neo
        });
        let data = this.getDataFromAPI(rs);
        console.log(data);
        return data;
      } catch (e) {
        this.$toast.error('Lỗi khi gọi API cn_nhomtb_id_neo: ', e);
      }
    }
  }//end methods
}
</script>

<style scoped>

</style>

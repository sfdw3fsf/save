<template src='./frmCapNhat_CKS_QLVT.html'></template>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from "moment";
import { Group, Page, Filter, Sort, Resize } from "@syncfusion/ej2-vue-grids";
import api from "../api/frmCapNhat_CKS_QLVTAPI";
import PopupChonNhanVienXacNhan from "../../QLTN/components/PopupChonNhanVienXacNhan/PopupChonNhanVienXacNhan.vue";
import EventBus from "@/utils/eventBus";

export default {
  components: { PopupChonNhanVienXacNhan },

  name: "frmCapNhat_CKS_QLVT",
  provide: {
    grid: [Group, Page, Filter, Sort, Resize],
  },
  mounted() {},
  computed: {},
  data() {
    return {
      imageChuKySo: "",
      tenLink: "Chọn ảnh",

      dsChuKySo: [],
      dsLoaiPhieu: [],
      dsLoaiPhieuBC: [],

      isTab: false,
      isLink: true,
      vnhanvien_id: 0,
      vma_nv: "",
      step1: 0,
      step2: 0,
      cks_id: 0,
      loaiphieubc_id: 0,
      tempSelectedLoaiPhieu: [],

      isEnabledNhapMoi: true,
      isEnabledGhiLai: true,
      isEnabledXoa: true,

      txtSoDT: "",
      txtTenNV: "",
      txtTaiKhoanKS : "",
      cboLoaiPhieu: [],
      txtLoaiPhieu: "",
      cboQuyTrinh: [],
      txtQuyTrinh: "",
      cboBaoCao: [],
      txtBaoCao: "",
      cboHuongGiao: [],
      txtHuongGiao: "",
      cboTuKhoa: [],
      txtTuKhoa: "",
      cboKieu: [],
      txtKieu: "",
      txtFile: "",

      checkboxColumnTBTemplate: function () {
        return {
          template: {
            template: `<div class="check-action">
                            <input
                                type="checkbox"
                                class="uncheck"                                    
                                v-model="parent.tempSelectedLoaiPhieu"
                                :value="data.LOAIPHIEU_ID"/>
                            <span class="name"></span>
                        </div>`,
            data() {
              return { data: {} };
            },
            computed: {
              parent() {             
                return this.$parent.$parent.$parent;
              },
            },
            methods: {
              onChanged() {
                EventBus.$emit("onTBCheckboxChanged", this.data);
              },
            },
          },
        };
      },
    };
  },
  methods: {
    async LoadDS_QuyTrinh() {
      let res = await api.layDSQuyTrinh(this.axios);
      if (res.data.error_code == "BSS-00000000")
        this.cboQuyTrinh = res.data.data;
    },
    async LoadDS_BaoCao() {
      let res = await api.layDSBaoCao(this.axios);
      this.cboBaoCao = res.data.data;
    },
    async LoadDS_HuongGiao() {
      let res = await api.layDSHuongGiao(this.axios);
      this.cboHuongGiao = res.data.data;
    },
    LoadDS_Kieu() {
      this.cboKieu = [
        {
          KIEU_ID: 1,
          KIEU_TEN: "Kiểu theo quy trình",
        },
        {
          KIEU_ID: 2,
          KIEU_TEN: "Kiểu bổ sung",
        },
      ];
    },
    async LoadDS_TuKhoa() {
      let res = await api.layDSTuKhoa(this.axios);
      this.cboTuKhoa = res.data.data;
    },
    async HienThi_DanhSachChuKySo() {
      let res = await api.layDSChuKySo(this.axios);
      this.dsChuKySo = res.data.data;
    },
    async HienThi_DanhSachLoaiPhieu() {
      this.tempSelectedLoaiPhieu = []
      let res = await api.layDSLoaiPhieu(this.axios);
      this.dsLoaiPhieu = res.data.data;
   
      for(let e of this.dsLoaiPhieu){
        if(e.CHON == 1 && !this.tempSelectedLoaiPhieu.includes(e.LOAIPHIEU_ID)){
          this.tempSelectedLoaiPhieu.push(e.LOAIPHIEU_ID)
        } 
      }          
      
    },
    async HienThi_DS_ThietLapPhieuBC() {
      let res = await api.layDSThietLapPhieuBC(this.axios);
      this.dsLoaiPhieuBC = res.data.data;
    },
    async layDSDanhMucLoaiPhieu() {
      let res = await api.layDSDanhMucLoaiPhieu(this.axios);
      this.cboLoaiPhieu = res.data.data;
    },

    changeTab(data) {
      if (data == 1) {
        this.isTab = true;
        this.SetButton(this.step1);
      } else {
        this.isTab = false;
        this.SetButton(this.step2);
      }
    },

    Clean() {
      if (!this.isTab) {
        this.txtSoDT = "";
        this.vnhanvien_id = 0;
        this.vma_nv = "";
        this.txtTenNV = "";

        this.txtFile = "";
        this.isLink = true;
        this.imageChuKySo = "";
      } else {
        this.txtLoaiPhieu = "";
        this.txtHuongGiao = "";
        this.txtKieu = "";
        this.txtTuKhoa = "";
        this.txtQuyTrinh = "";
        this.txtBaoCao = "";
      }
    },

    SetButton(kieu) {
      this.isEnabledNhapMoi = false;
      this.isEnabledGhiLai = false;
      this.isEnabledXoa = false;
      if (kieu == -1) {
        this.isEnabledGhiLai = true;
      }
      if (kieu == 0) {
        this.isEnabledNhapMoi = true;
        this.Clean();
      }
      if (kieu == 1) {
        this.isEnabledGhiLai = true;
        this.Clean();
      }
      if (kieu == 2) {
        this.isEnabledNhapMoi = true;
        this.isEnabledXoa = true;
        this.Clean();
      }
      if (kieu == 3) {
        this.isEnabledNhapMoi = true;
        this.isEnabledGhiLai = true;
        this.isEnabledXoa = true;
      }
      if (!this.isTab) {
        this.step1 = kieu;
      } else {
        this.step2 = kieu;
      }
    },

    async Check_LoaiPhieu_TheoCKS(cksId) {
      this.tempSelectedLoaiPhieu = []
      let res = await api.kiemTraLoaiPhieuTheoCKS(this.axios, cksId);
      this.dsLoaiPhieu = res.data.data;
      
      for(let e of this.dsLoaiPhieu){
        if(e.CHON == 1){
          this.tempSelectedLoaiPhieu.push(e.LOAIPHIEU_ID)
        }         
      }
    },

    async onClickXoa() {
      try{
        if (!this.isTab) {
          var isBoxConfirm = false;
          await this.$bvModal.msgBoxConfirm("Bạn có đồng ý xóa Chữ ký này không?", {
            title: "Thông báo",
            centered: true,
            size: "md",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          })
          .then((value) => {
            if (value) {
              isBoxConfirm = true;
            } else {
              return;
            }
          });

          if (!isBoxConfirm) {
            return;
          }
          if (this.cks_id == 0) return;
          var res = await api.xoaCKS(this.axios, this.cks_id);
          this.$toast.success("Xóa chữ ký số thành công!");
          await this.HienThi_DanhSachChuKySo();

        } else {
          var isBoxConfirm = false;
          await this.$bvModal.msgBoxConfirm("Bạn có đồng ý xóa thiết lập phiếu báo cáo này không?", {
            title: "Thông báo",
            centered: true,
            size: "md",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          })
          .then((value) => {
            if (value) {
              isBoxConfirm = true;
            } else {
              return;
            }
          });

          if (!isBoxConfirm) {
            return;
          }


          if (this.xoaLoaiPhieuBC == 0) return;
          var res = await api.xoaLoaiPhieuBC(
            this.axios,
            this.loaiphieubc_id
          );
          this.$toast.success("Xóa thiết lập báo cáo thành công !");
          await this.HienThi_DS_ThietLapPhieuBC();
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },

    async onClickNhapMoi() {
      try{
        this.loading(true)
        if (!this.isTab) {
          this.isLink = true;
          this.txtFile = "";
          this.imageChuKySo = "";
          this.cks_id = 0;
        } else {
          this.loaiphieubc_id = 0;
        }
        this.SetButton(1);
        await this.Check_LoaiPhieu_TheoCKS(0);
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },

    onClickHuyBo() {
      this.SetButton(2);
      this.Clean();
    },

    async gridChuKySo_onSelectedRowChanged(data) {
      try{
        if (data) {
          this.loading(true)
          this.cks_id = data.CHUKYSO_ID;
          let imageData = data.ANH_KS;
          this.txtFile = "";
          this.isLink = false;
          if (data.ANH_KS.length > 0) {
            let str = imageData.replaceAll("data:image/png;base64,", "");
            str = str.replaceAll("data:image/jpeg;base64,", "");
            this.imageChuKySo = "data:image/png;base64," + str;
          } else {
            this.isLink = true;
            this.txtFile = "";
            this.imageChuKySo = "";
          }
          await this.Check_LoaiPhieu_TheoCKS(this.cks_id);
          
          for(let e of this.dsLoaiPhieu){
            if(e.CHON == 1 && !this.tempSelectedLoaiPhieu.includes(e.LOAIPHIEU_ID)){
              this.tempSelectedLoaiPhieu.push(e.LOAIPHIEU_ID)
            }
          }
          this.txtTenNV = data.TEN_NV;
          this.vnhanvien_id = data.NHANVIEN_ID;
          this.txtSoDT = data.SODT_KS;
          this.txtTaiKhoanKS = data.TAIKHOAN_KS == "null" ? "" : data.TAIKHOAN_KS
          this.SetButton(3);
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },

    gridLoaiPhieuBC_onSelectedRowChanged(data) {
      if (data) {
        this.loaiphieubc_id = data.LOAIPHIEUBC_ID;
        let loaiphieu_id = data.LOAIPHIEU_ID;
        let huonggiao_id = data.HUONGGIAO_ID;
        let quytrinh_id = 0;
        if (this.cboHuongGiao.length > 0) {
          let temp = this.cboHuongGiao.filter(
            (item) => item.HUONGGIAO_ID == huonggiao_id
          );
          if (temp.length > 0) {
            quytrinh_id = temp[0].QUYTRINH_ID;
          }
        }
        let kieu_id = data.KIEU;
        let baocao_id = data.BAOCAO_ID;

        this.txtQuyTrinh = quytrinh_id;
        this.txtHuongGiao = huonggiao_id;
        this.txtKieu = kieu_id;
        this.txtLoaiPhieu = loaiphieu_id;

        let listTempTuKhoa = data.TUKHOA_KS.split(";");
        let dsTuKhoaChon = [];
        listTempTuKhoa.forEach((item) => {
          let temp = this.cboTuKhoa.filter(
            (cboItem) => item == cboItem.TUKHOA_KS
          );
          if (temp.length > 0) {
            dsTuKhoaChon = [...dsTuKhoaChon, ...temp];
          }
        });       
        this.$refs.selectedDSTuKhoa.selectedItems = dsTuKhoaChon;
        

        this.txtBaoCao = baocao_id;
        this.SetButton(3);
      }
    },

    onClickXemAnh(arg) {
      this.$bvModal.show("PopUp");
    },

    onChonNVXL() {
      this.$refs.dlgChonNhanVienXacNhan.openDialog();
    },

    onChonNVXLProcess(e) {
      if (e != null && Object.keys(e).length !== 0) {
        this.vnhanvien_id = e.NHANVIEN_ID;
        this.txtTenNV = e.TEN_NV;
        this.txtSoDT = e.SO_DT;
      }
    },

    KiemTraDieuKien() {
      if (!this.isTab) {
        if (!this.txtTenNV || this.txtTenNV.trim() == "") {
          this.$toast.error("Chưa chọn nhân viên!");
          return false;
        }
        if (!this.txtSoDT || this.txtSoDT.trim() == "") {
          this.$toast.error("Chưa nhập số điện thoại!");
          return false;
        }
        if (this.tempSelectedLoaiPhieu.length == 0) {
          this.$toast.error(
            "Bạn chưa chọn loại phiếu trong danh sách loại phiếu!"
          );
          return false;
        }
      } else {
        if (this.txtLoaiPhieu == "") {
          this.$toast.error("Chưa chọn loại phiếu!");
          return false;
        }
        if (this.txtHuongGiao == "") {
          this.$toast.error("Chưa chọn hướng giao!");
          return false;
        }
        if (this.txtBaoCao == "") {
          this.$toast.error("Chưa chọn báo cáo!");
          return false;
        }
        if (this.txtKieu == "") {
          this.$toast.error("Chưa chọn kiểu!");
          return false;
        }
        if (this.$refs.selectedDSTuKhoa.selectedItems.length == 0) {
          this.$toast.error("Chưa chọn từ khóa khảo sát!");
          return false;
        }
      }
      return true;
    },

    async CapNhat() {
      try{     
        if (! await this.KiemTraDieuKien()) {          
          return;
        }
        if (!this.isTab) {
          var isBoxConfirm = false;
          await this.$bvModal.msgBoxConfirm("Bạn có đồng ý lưu lại những thông tin Chữ ký không?", {
            title: "Thông báo",
            centered: true,
            size: "md",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          })
          .then((value) => {
            if (value) {
              isBoxConfirm = true;
            } 
          });

          if (!isBoxConfirm) {
            return;
          }
          this.loading(true);
          let listLoaiPhieu = [];
          this.tempSelectedLoaiPhieu.forEach((item) => {
            listLoaiPhieu.push({
              loaiPhieuId: item,
            });
          });
          
          var res = await api.capNhatCKS(this.axios, {
            chuKySoId: this.cks_id,
            soDtKs: this.txtSoDT,
            anhKySo: this.imageChuKySo,
            nhanVienId: this.vnhanvien_id,
            listLoaiPhieu: listLoaiPhieu,
            taiKhoanKs : this.txtTaiKhoanKS,
          });
          this.$toast.success("Cập nhật chữ ký số thành công!");
          await this.HienThi_DanhSachChuKySo();
        } else {

          var isBoxConfirm = false;
          await this.$bvModal.msgBoxConfirm("Bạn có đồng ý lưu lại những thông tin thay đổi thiết lập báo cáo không?", {
            title: "Thông báo",
            centered: true,
            size: "md",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          })
          .then((value) => {
            if (value) {
              isBoxConfirm = true;
            } 
          });

          if (!isBoxConfirm) {
            return;
          }
          
          let tuKhoa = "";
          this.$refs.selectedDSTuKhoa.selectedItems.forEach((item) => {
            tuKhoa += item.TUKHOA_KS + ";";
          });

          var res = await api.capNhatThietLapBaoCao(this.axios, {
            loaiPhieuId: this.txtLoaiPhieu,
            huongGiaoId: this.txtHuongGiao,
            baoCaoId: this.txtBaoCao,
            tuKhoaKs: tuKhoa,
            kieu: this.txtKieu,
            loaiPhieuBcId: this.loaiphieubc_id,
          });
          this.$toast.success("Cập nhật tham số báo cáo thành công !");
          await this.HienThi_DS_ThietLapPhieuBC();          
        }        
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },

    changeFile(data) {
      this.txtFile = data.target.files[0];
      var reader = new FileReader();
      reader.readAsDataURL(this.txtFile);
      reader.onload = () => {
        this.imageChuKySo = reader.result;
      };
      reader.onerror = function (error) {
        console.log("Error: ", error);
      };
      this.isLink = true;
    },

    onTBCheckboxChangedHandler(data) {    
      this.tempSelectedLoaiPhieu = []
      for(let e of this.dsLoaiPhieu){
        if(e.CHON == 1){
          this.tempSelectedLoaiPhieu.push(e.LOAIPHIEU_ID)
        } 
      }
      
    },
  },

  created: async function () {
    try{
      this.loading(true);
      EventBus.$on("onTBCheckboxChanged", this.onTBCheckboxChangedHandler);

      await this.LoadDS_QuyTrinh();
      await this.LoadDS_BaoCao();
      await this.LoadDS_HuongGiao();
      this.LoadDS_Kieu();
      await this.LoadDS_TuKhoa();
      await this.HienThi_DanhSachChuKySo();
      await this.HienThi_DanhSachLoaiPhieu();
      await this.HienThi_DS_ThietLapPhieuBC();
      await this.layDSDanhMucLoaiPhieu();      
    }catch(e){
      this.$toast.error(e.data.message)
    }finally{
      this.loading(false);
    }
  },

  watch: {
    tempSelectedLoaiPhieu: function (value) { 
      this.tempSelectedLoaiPhieu = value;      
    },
  },
  destroyed() {
    EventBus.$off("onTBCheckboxChanged", this.onTBCheckboxChangedHandler);
  },
};
</script>

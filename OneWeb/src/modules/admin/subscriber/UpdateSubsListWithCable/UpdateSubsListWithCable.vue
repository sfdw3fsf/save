<template src="./UpdateSubsListWithCable.html"></template>
<style src="./UpdateSubsListWithCable.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb';
import select2 from "@/components/Select2.vue";
import UpdateSubsListWithCableAPI from './UpdateSubsListWithCableAPI';
import gridview from './gridview';
import SearchAccountModal from './popups/SearchAccountModal.vue'
//import VueElement from '../../DeclareLandline/components/VueElement.vue'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
//
export default {
  components: { breadcrumb,gridview, select2,SearchAccountModal },
  name: 'UpdateSubsListWithCableAPI',
  mounted () {
    
  },
  destroyed () {},
  data () {
    return {
      header: {
        title: 'Cập nhật danh sách thuê bao cùng đôi cáp',
        list: [
          { name: 'Nghiệm thu báo hỏng', link: { name: 'Ui.cards' } },
          { name: 'Cập nhật danh sách thuê bao cùng đôi cáp', link: { name: 'Ui.cards' } },
        ]
      },
      matn: "",
      txtTraCuu: "",
      TxtMaTBTN: "",
      TxtTenTB: "",
      TxtLoaiHinh: "",
      TxtTrangThai: "",
      TxtDiaChi: "",
      TxtToaDo: "",
      TxtToQuanLy: "",
      TxtTramThietBi: "",
      statusSuaTN: false,
      statusTaoDC: false,
      statusGhepma: false,
      check_ma_dc: false,
      check_ma_kh: true,
      listbox: {
        ketquatimkiem: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
          columns: [
          { fieldName: 'ma_tb', headerText: 'Số máy/Account', allowFiltering: true, cssClass: 'text-right'},
          { fieldName: 'matb_tn', headerText: 'Mã truy nhập', allowFiltering: true },
          { fieldName: 'madoicap', headerText: 'Mã đối cáp', allowFiltering: true },
          { fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true },
          { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true },
          { fieldName: 'diachi_ld', headerText: 'Địa chỉ LĐ', allowFiltering: true },                   
        ],
        },
        ketquatimkiem1: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
        },
      },
      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      from_date: new Date(),
      to_date: new Date()
    }
  },
  methods: {
    // Set kieu nút
      SetButton(kieu) {
        if (kieu == 1) {
          this.statusSuaTN = true;
          this.statusTaoDC = true;
          this.statusGhepma = false;
        }
        if (kieu == 2) {
          this.statusGhepma = true;
          this.statusSuaTN = true;
          this.statusTaoDC = true;
        }
        if (kieu == 3) {
          this.statusGhepma = false;
          this.statusSuaTN = false;
          this.statusTaoDC = false;
        }
      },
    // Click tìm kiếm 
     btnSearchClicked() {
      this.SetButton(3);
      this.Clear();
      this.listbox.ketquatimkiem.list = null;
      if (this.txtTraCuu == "") {
        this.$toast.error("Vui lòng nhập số máy/Account");
      }
      else {
      var pdata = {};
      this.txtTraCuu = this.txtTraCuu.trim();
      pdata.dichvuvt_id = null;
      pdata.kieu = 0;
      pdata.ma_tb = this.txtTraCuu;
    //  pdata.vkieu = 1;
      UpdateSubsListWithCableAPI.tracuu(this.axios, pdata).then((response) => {
            if(response.data.error_code == "BSS-00000204"){
              this.$toast.error("Không tìm thấy thông tin thuê bao " + this.txtTraCuu);
            }
            if(response && response.data && response.data.data){
              this.listbox.ketquatimkiem1.list = response.data.data;
              this.TxtTrangThai = this.listbox.ketquatimkiem1.list[0].trangthai_tb;
              this.TxtDiaChi = this.listbox.ketquatimkiem1.list[0].diachi_ld;
              this.TxtToaDo = this.listbox.ketquatimkiem1.list[0].toado_ld;
              this.TxtToQuanLy = this.listbox.ketquatimkiem1.list[0].ten_toql;
              this.TxtTramThietBi = this.listbox.ketquatimkiem1.list[0].ten_tramtbi;
              this.SetButton(1);
              var input = {};
                input.vthuebao_id = this.listbox.ketquatimkiem1.list[0].thuebao_id;
                if(this.check_ma_dc == true){
                  input.vkieu = 1;
                }
                else input.vkieu = 0;
                UpdateSubsListWithCableAPI.lay_ds_tb(this.axios, input).then((response) => {
                      if(response.data.error_code == "BSS-00000500"){
                        this.$toast.error("Không tìm thấy thông tin thuê bao " + this.txtTraCuu);
                      }
                  if(response && response.data && response.data.data){
                    this.listbox.ketquatimkiem.list = response.data.data;
                    this.SetButton(1);
                }
            });
          }
      });
      }
    },
    Clear() {
      this.listbox.ketquatimkiem1.list = null;
      this.TxtTenTB = "";
      this.TxtMaTBTN = "";
      this.TxtLoaiHinh = "";
      this.TxtDiaChi = "";
      this.matn = "";
      this.TxtToQuanLy = "";
      this.TxtTramThietBi = "";
      this.TxtTrangThai = "";
      this.TxtToaDo = "";
    },
    grdKhachHangChanged(obj) {
      var data = this.$refs.gridDSThueBao.getSelectedRecords();
     // console.log(data.length);
      if(data.length > 1){
        this.SetButton(2);
      }
      if(data.length == 1){
        this.SetButton(1);
      }
      if(data.length < 1){
        this.SetButton(3);
      }
     // this.listbox.ketquatimkiem1.list = obj;
    //  this.matn = obj.matb_tn;
      this.TxtTenTB = obj.ten_tb;
      this.TxtMaTBTN = obj.matb_tn;
      this.TxtLoaiHinh = obj.loaihinh_tb;
    },
    // Tạo đôi cáp mới
    AddDc(){
      var pdata = {};
      var data = this.$refs.gridDSThueBao.getSelectedRecords();
      let thuebao_id;
      let thuebao_id1 = "";
      for(var i=0 ; i<data.length; i++){
        thuebao_id = data[i].thuebao_id;
        thuebao_id1 = thuebao_id1.concat(",", thuebao_id);
      }
        thuebao_id1 = thuebao_id1.slice(1);
      if(thuebao_id1==""){
        this.$toast.error("Bạn phải chọn ít nhất một thuê bao");
        this.hideModalTaoDC();
      }
      else {
      pdata.vdsthuebao_id = thuebao_id1;
      console.log(pdata);
      UpdateSubsListWithCableAPI.tao_cap_moi(this.axios, pdata).then((response) => {  
        if(response && response.data && response.data.data) {
            console.log(response.data.data);
            this.$toast.success("Cập nhật thông tin vào danh bạ thành công!");
            this.hideModalTaoDC();
            this.btnSearchClicked();
        }
      });
      }
    },
    // Sửa mã TN
    UpdateTB(){
      if(this.TxtMaTBTN == null || this.TxtMaTBTN == ""){
        this.$toast.error("Mã truy nhập không được để trống.");
      }
      else {
      var pdata = {};
      pdata.vthuebao_id = this.listbox.ketquatimkiem1.list[0].thuebao_id;
      pdata.vmatb_tn = this.TxtMaTBTN.trim();
    //  console.log(pdata);
      UpdateSubsListWithCableAPI.sua_ma_truy_nhap(this.axios, pdata).then((response) => {
        if(response.data.data != 1){
          this.$toast.error("Mã thuê bao truy nhập không đúng. Bạn hãy kiểm tra lại");
        }
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data == 1) {
            this.btnSearchClicked();
            this.$toast.success("Cập nhật thông tin vào danh bạ thành công!");
            this.hideModal();
        }
      });
      }
    },
    async acceptSearchAccount(item) {
            this.txtTraCuu = item.ma_tb;
    },
     async txtMaTB_KeyPress() {
            if (this.txtMaTB != "") {
                let ds = this.GetData(await api.Lay_ds_hopdong_theo_ma_tb(this.axios, 
                 {
                     "ma_tb":this.txtMaTB,//hpgmytv128442
                     "in_loaihd_id":LoaiHopDong.VATTU_THUEBAO,
                     "in_donvi_id":this.tt_nd.donvi_id,
                     "in_tthd_id":TrangThaiHD.MOI,
                     "in_nhanvien_id":this.tt_nd.nhanvien_id,
                     "in_donvi_dl_id":0,
                     "in_dichvuvt_id":this.dataSelected.dichvuvt_id,
                     "in_loaitb_id":0,
                }));
                if (ds.length > 0)
                {
                    this.thuebao_id = parseInt(ds[0].thuebao_id.toString());
                    this.hdtb_id = ds[0].hdtb_id;
                    this.hdkh_id= ds[0].hdkh_id;
                    await this.HienThiTTHopDongKH(ds);
                    this.HienThiTT_DanhBa(ds[0].ma_tb);
                }
                else
                {
                    this.HienThiTT_DanhBa(this.txtMaTB);
                }
            }
            
        },
    // Ghép mã đôi cáp
    GhepDC(){
      var pdata = {};
      var data = this.$refs.gridDSThueBao.getSelectedRecords();
      let thuebao_id;
      let thuebao_id1 = "";
      for(var i=0 ; i<data.length; i++){
        thuebao_id = data[i].thuebao_id;
        thuebao_id1 = thuebao_id1.concat(",", thuebao_id);
      }
       thuebao_id1 = thuebao_id1.slice(1);
      if(data.length < 2){
        this.$toast.error("Bạn phải chọn hai thuê bao trở lên");
      }
      else {
      pdata.vdsthuebao_id = thuebao_id1;
      //console.log(pdata);
      UpdateSubsListWithCableAPI.ghep_doi_ma_cap(this.axios, pdata).then((response) => {  
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
            console.log(111111);
            console.log(response.data.data);
            this.hideModalGhepDC();
            if(response.data.data.out == "1"){
              this.$toast.success("Ghép đôi mã cáp thành công");
            }else{
              console.log("Lỗi ghép mã ĐC")
              console.log(response.data.data.out)
              this.$toast.error(response.data.data.out);
            }
            this.btnSearchClicked();
        }
      });
      }
    },
    showModal() {
        this.$bvModal.show('ModalSuaMaTN');
        this.matn = this.listbox.ketquatimkiem1.list.matb_tn;
      //  console.log(this.$refs.gridDSThueBao.getSelectedRecords());
      },
    showModalGhepDC() {
        this.$bvModal.show('ModalGhepDC');
    },
    showModalTaoDC() {
        this.$bvModal.show('ModalXacNhan');
    },
    hideModalGhepDC() {
        this.$bvModal.hide('ModalGhepDC');
    },
    hideModal() {
        this.$bvModal.hide('ModalSuaMaTN');
    },
    hideModalTaoDC() {
        this.$bvModal.hide('ModalXacNhan');
    },
    showPopupSearchAccount() {
           this.$refs.searchAccountModal.showModal();
    },
  },
};
</script>
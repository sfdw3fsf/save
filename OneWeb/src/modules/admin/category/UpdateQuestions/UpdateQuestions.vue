<template src="./UpdateQuestions.html"></template>
<style scoped src="./UpdateQuestions.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import API from "./API";
import Vue from "vue";
import { DropDownListPlugin } from "@syncfusion/ej2-vue-dropdowns";
import KTable from "@/components/KTable.vue";
import ActionTop from "@/components/ActionTop.vue";
import UpdateAnswers from '@/modules/admin/category/UpdateAnswers'
import moment from 'moment'

import PopupCauHoiPhu from "./PopupCauHoiPhu.vue"; 
import PopupGanTrachNhiemDonVi from "./PopupGanTrachNhiemDonVi.vue"; 

Vue.use(DropDownListPlugin);  
export default {
  components: { 
    breadcrumb, 
    KTable, 
    ActionTop,
    PopupCauHoiPhu,
    PopupGanTrachNhiemDonVi,
    UpdateAnswers
     },
  data: function () {
    return {
      header: {
        title: 'CẬP NHẬT CÂU HỎI',
        list: [
          { 
            name: 'Quản trị', 
            link: { name: 'Ui.cards' } 
          },
          {
            name: 'Cập nhật câu hỏi',
            link: { name: 'Ui.buttons' }
          },
        ]
      },
      params: {
        p_tieuchuan_option: [],
        p_tieuchuan: null,
        p_hinhthuc_option: [],
        p_hinhthuc: null,
        p_loaicauhoi_option: [],
        p_loaicauhoi: null,
        p_kieucauhoi_option: [],
        p_kieucauhoi: null,
        p_noidung: null,
        ds_cautraloidagan_table: [],
        p_cautraloidagan: null,
        //table cau TL
        p_rch_id:null,
        p_thutu: null,
        p_traloi_id:null,
        p_loaidv_id: null,
        p_dieulai: null,
        p_ma_hl: null,
        p_tndv_id:null,
        p_hienthi:null,
        p_dieu_xn:null,
        p_dieu_cs:null
      },
      ds_cauhoi_table: [],
      cauhoi_id: "",
      rch_id:0,
      vrch_id:0,
      ds_traloi_selected: [],
       button:{
        nhapmoi: true,
        ghilai:true,
        xoa:true,
        huybo:true,
        btnRight: false,
        btnLeft: false,
      },
      DSCauTraLoi_chuagan: {
        list: [],
        header: [
         {
          fieldName: "traloi_id",
          headerText: "ID",
          allowFiltering: true,
        },
        {
          fieldName: "noidung",
          headerText: "Nội dung",
          allowFiltering: true,
        },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
     
      DSCauTraLoi_dagan: {
        list: [],
        header: [
          {
            fieldName: "traloi_id",
            headerText: "ID",
            allowFiltering: true,
          },
          {
            fieldName: "noidung",
            headerText: "Nội dung",
            allowFiltering: true,
          },
          {
            fieldName: "tenloai",
            headerText: "Tên loại",
            allowFiltering: true,
          },
        {
          fieldName: "thutu",
          headerText: "Thứ tự",
          allowFiltering: true,
          allowEditing:true,

        },
        {
          fieldName: "dieulai",
          headerText: "Điều hỏng",
          allowFiltering: true,
        },
        {
          fieldName: "ma_hl",
          headerText: "Độ HL",
          allowFiltering: true,
          allowSorting: true,
          width: 'auto' 
        },
        {
          fieldName: "hienthi",
          headerText: "Hiển thị",
          allowFiltering: false,
          textAlign:'center',
          template: this.columnTemplateCheckBox(this),
        },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      editSettings:{ allowEditing: true, mode:'Batch'},


    };
  },
  validations: {},
  computed: {
     cp_dsDagan: {
      get() {
        return this.DSCauTraLoi_dagan;
      },
      set(value) {
        return (this.DSCauTraLoi_dagan = value);
      },
    },
    cp_dsChuagan: {
      get() {
        return this.DSCauTraLoi_chuagan;
      },
      set(value) {
        return (this.DSCauTraLoi_chuagan = value);
      },
    },

    
  },
  watch: {
  },
  created() {
      this.loadDSTieuChuan();
      this.loadDSHinhThuc();
      this.loadDSLoaiCauHoi();
      this.LoadKieuCauHoi();
  },
  async mounted() {},
  methods: {
      async loadDSTieuChuan() {
      API.getDSTieuChuan(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.tieuchuan_id, text: item.tieuchuan });
              });
              this.params.p_tieuchuan_option = items;
              this.params.p_tieuchuan = items[0].id;
              this.loadDSCauHoi(
                items[0].id
              );
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
     async loadDSHinhThuc() {
      API.getDSHinhThuc(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.hinhthuc, text: item.tenhinhthuc });
              });
              this.params.p_hinhthuc_option = items;
              this.params.p_hinhthuc = items[0].id;
              this.loadDSCauHoi(
                items[0].id
              );
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    async loadDSLoaiCauHoi() {
      API.getDSLoaiCauHoi(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.loaich_id, text: item.loai_ch });
              });
              this.params.p_loaicauhoi_option = items;
              this.params.p_loaicauhoi = items[0].id;
              this.loadDSCauHoi(
                items[0].id
              );
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
     async LoadKieuCauHoi() {
      let items = [];
      
        items = [
          { id: 1, text: "Câu hỏi chính" },
          { id: 2, text: "Câu hỏi phụ" },
        ];
      this.params.p_kieucauhoi_option = items;
      this.params.p_kieucauhoi = items[0].id;
      this.loadDSCauHoi(items[0].id );
    },
    
    
    async loadDSCauHoi( ) {
      this.ds_cauhoi_table = [];
      this.loading(true);
      try {
        const response = await API.getDSCauHoi(this.axios);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          this.ds_cauhoi_table = response.data.data;
        }else{
          this.params.p_noidung = null;
        }
      } catch (error) {
        console.log(error);
      } finally {
         this.loading(false);
      }
    },
     grdCauHoiChanged(subject) {
      this.params.p_noidung =subject.noidung != null ? subject.noidung : "";
      this.cauhoi_id = subject.cauhoi_id;
      this.params.p_hinhthuc = subject.hinhthuc;
      this.params.p_loaicauhoi = subject.loaich_id;
      this.params.p_kieucauhoi = subject.kieu;
      this.getDsDaGan(this.cauhoi_id);
      this.getDsChuaGan(this.cauhoi_id);
    
    },
      grdCauTLCapNhatChanged(subject) {
      this.params.p_thutu = subject.thutu;
      this.params.p_traloi_id = subject.traloi_id;
      this.params.p_loaidv_id = subject.loaidv_id;
      this.params.p_dieulai = subject.dieulai;
      this.params.p_ma_hl = subject.ma_hl;
      this.params.p_tndv_id = subject.tndv_id;
      this.params.p_hienthi = subject.hienthi;
      this.params.p_dieu_xn = subject.dieu_xn;
      this.params.p_dieu_cs = subject.dieu_cs;
    },
    
     SetButton(kieu){
      this.button.nhapmoi =false;
      this.button.xoa = false;
      this.button.ghilai = false;
      this.button.huybo = false;
      switch (kieu){
        case -1: //batdau
          this.button.nhapmoi =true;
          this.button.ghilai =true;
          this.button.xoa = true;
          this.$refs.p_noidung.focus();
        break;
        case 0: //Bat dau
          this.button.nhapmoi =true;
          this.$refs.p_noidung.focus();
          this.clear();
          break;
        case 1: //Them moi
          this.button.ghilai = true;
          this.button.huybo = true;
          this.$refs.p_noidung.focus();
          this.clear();
          this.DSCauTraLoi_dagan.list=[]
          break;
        case 2: //huy
          this.button.nhapmoi =true;
          this.button.xoa = true;
          this.button.ghilai =true;
          this.$refs.p_noidung.focus();
          this.clear();
          break;
        case 3: //edit
          this.button.nhapmoi =true;
          this.button.xoa = true;
          this.button.ghilai = true;
          this.button.huybo = true;
          this.$refs.p_noidung.focus();
          break;
      }
    },
     clear(){
      this.$refs.p_noidung.focus();
      this.params.p_noidung = "";
    },
     async tsbtnNhapMoi_Click() {
       this.SetButton(1);
    }, 
     async tsbtnHuy_Click() {
        this.loadDSCauHoi()
        this.SetButton(2);
    }, 
    async tsbtnXoa_Click() {
        this.$alert("Bạn thật sự muốn xóa dữ liệu không ?", "Thông báo", {
            dangerouslyUseHTMLString: true,
            showCancelButton: true,
            confirmButtonText: 'Đồng ý',
            cancelButtonText: 'Hủy bỏ',
            showCloseButton: true,
            type: "warning",
      }).then(async (v) => {
          if (!v) {return;}
        var b = this.cauhoi_id;
        API.XoaCauHoi(this.axios, { "cauhoi_id": this.cauhoi_id})
        .then((response) => {
            if (
              response.data.error_code === "BSS-00000000"
            ) {
              this.loadDSCauHoi()
              this.$toast.success("Xóa thành công !");
              this.SetButton(3);
            }
            else
            {
              this.$toast.error("Câu hỏi đã được gán trong dữ liệu trả lời. Hãy kiểm tra lại! ");  
            }   
        })
        .catch((error) => {
          console.log(error);
           this.$toast.error("Không thể xóa bản ghi này " + error);  
        })
        .finally(() => {});
        });
    },
    tsbtnTraLoi_Click()
    {
        this.$refs.UpdateAnswers;
    },
     btnShowCauHoiPhu()
    {
      this.$refs.cau_hoi_phu.showModal();
    },
    btnshowTrachNhiemDV()
    {
      this.$refs.trach_nhiem_donvi.showModal();
    },
 
    popupClosed(val) {
      this.$bvModal.hide('popupComponents');
    },
    cellSavedTraLoiGan(args){
      console.log('cellSavedTraLoiGan', args)
      if(args.columnName=='thutu'){
        const index=this.DSCauTraLoi_dagan.list.findIndex(x=>x.traloi_id==args.rowData.traloi_id)
        if(index>-1){
          this.DSCauTraLoi_dagan.list[index].thutu=args.value
          this.DSCauTraLoi_dagan.list=this.DSCauTraLoi_dagan.list.concat([])
        }
      }
    },

     async tsbtnGhiLai_Click() {
      try {
      if(this.params.p_noidung.length<= 4000)
        {
           let data_traloi_capnhat = []
           let data_traloi=[]
            var ds_cauhoi = {
                noidung : this.params.p_noidung,
                hinhthuc : this.params.p_hinhthuc,
                tieuchuan_id: this.params.p_tieuchuan!=null?this.params.p_tieuchuan: 1 ,
                loaich_id: this.params.p_loaicauhoi,
                kieu:this.params.p_kieucauhoi,
                ma_ch:"",
            };
            this.DSCauTraLoi_dagan.list.forEach((item, index) => {
              if(item.rch_id!=0){
                data_traloi_capnhat.push({
                    traloi_id : item.traloi_id,
                    thutu : item.thutu,
                    rch_id :item.rch_id != null ? item.rch_id : 0,
                    loaidv_id: item.loaidv_id != null ? item.loaidv_id : 1,
                    dieulai: item.dieulai != null ? item.dieulai : 1,
                    ma_hl: item.ma_hl != null ? item.ma_hl : "",
                    tndv_id: item.tndv_id != null ? item.tndv_id : 1,
                    hienthi:item.hienthi?item.hienthi:0,
                    dieu_xn: item.dieu_xn != null ? item.dieu_xn : 1,
                    dieu_cs:item.dieu_cs != null ? item.dieu_cs : 1,
                })
              }else{
                data_traloi.push({
                    traloi_id : item.traloi_id,
                    thutu : item.thutu,
                    rch_id :null,
                    loaidv_id: item.loaidv_id != null ? item.loaidv_id : 1,
                    dieulai: item.dieulai != null ? item.dieulai : 1,
                    ma_hl: item.ma_hl != null ? item.ma_hl : "",
                    tndv_id: item.tndv_id != null ? item.tndv_id : 1,
                    hienthi:item.hienthi?item.hienthi:0,
                    dieu_xn: item.dieu_xn != null ? item.dieu_xn : 1,
                    dieu_cs:item.dieu_cs != null ? item.dieu_cs : 1,
                })
              }
            });
           
            let kieu =0;
            if(this.button.nhapmoi == false){
                //"thêm mới"
                kieu=1;
            }else{
                kieu=0;
                ds_cauhoi.cauhoi_id=this.cauhoi_id;

            }

            let res = await this.axios.post("/web-quantri/capnhatcauhoi/sp_capnhat_cauhoi", {
                          "danhsach_cauhoi": JSON.stringify(ds_cauhoi),
                          "danhsach_traloi": JSON.stringify(data_traloi),
                          "is_insert": kieu,
                          "danhsach_traloi_update": JSON.stringify(data_traloi_capnhat),
                        })
                        if (res.data.error_code === "BSS-00000000") {
                           // await this.loadDSCauTraLoi();
                            this.$toast.success("Cập nhật thành công!")
                            this.loadDSCauHoi()
                            this.getDsDaGan(this.cauhoi_id);
                            this.getDsChuaGan(this.cauhoi_id);
                            this.SetButton(3);
                        } else
                            this.$toast.error(res.data.message)
              
        }
        else
        {
           this.$toast.error("Trường nội dung nhập dữ liệu vượt quá độ dài cho phép là 4000 ký tự!")
        }
        
      } catch(err) {
        console.log(err)
        this.loading(false)
      }
    },
    
    onChangeTieuChuan(agrs){
      this.params.p_tieuchuan=agrs.id;
    },
    onChangeHinhThuc(agrs){
      this.params.p_hinhthuc=agrs.id;
    },
    onChangeLoaiCauHoi(agrs){
      this.params.p_loaicauhoi=agrs.id;
    },
    onChangeKieuCauHoi(agrs){
      this.params.p_kieucauhoi=agrs.id;
    },

    getDsDaGan: async function (cauhoi_id) {
       try {
        this.loading(true)
        let res = await this.axios.post("web-quantri/capnhatcauhoi/sp_lay_cau_traloi_dagan", {
          "cauhoi_id": cauhoi_id
        })
          this.DSCauTraLoi_dagan.list = res.data.data.map(x=>{
            x.hienthi=(x.hienthi&&x.hienthi=='s1')?1:0
            return x
          })

        this.loading(false)
      } catch(err) {
        this.loading(false)
        this.$toast.error("Lỗi!")
      }
    },
    changeCheck(item, checked){
        console.log('changeCheck checked', checked)
        console.log('changeCheck item', item)
        const index=this.DSCauTraLoi_dagan.list.findIndex(x=>x.traloi_id==item.traloi_id)
        if(index>-1){
          this.DSCauTraLoi_dagan.list[index].hienthi=checked?1:0
        }
    },
    columnTemplateCheckBox(parent) {
            return function() {
                return { 
                    template: {
                        template: `
                          <div class="check-action center">
                            <ejs-checkbox :checked="isChecked" @change="changeCheck">
                            </ejs-checkbox>
                          </div>
                        `,
                        data() {
                            return {
                              data: {},
                              parent:parent
                            }
                        },
                        computed: {
                            isChecked(){
                              return this.data.hienthi==1
                            },
                            
                        },
                        methods:{
                          changeCheck(value){
                                this.parent.changeCheck(this.data, value.checked)
                                
                            }
                        }
                        
                    }
                }
            }
        },
    getDsChuaGan: async function (cauhoi_id) {
       try {
        this.loading(true)
        let res = await this.axios.post("web-quantri/capnhatcauhoi/sp_lay_cau_traloi_chuagan", {
          "cauhoi_id": cauhoi_id
        })
          this.DSCauTraLoi_chuagan.list = res.data.data

        this.loading(false)
      } catch(err) {
        this.loading(false)
        this.$toast.error("Lỗi!")
      }
    },
    handleBtnRight: function () {
      this.Loading = true;
      this.button.btnRight = true;
      var dagan = this.DSCauTraLoi_dagan.list;
      var chuagan = this.DSCauTraLoi_chuagan.list;
      var selected = this.ds_traloi_selected;
      dagan = dagan
        .concat(selected)
        .sort((a, b) => parseFloat(b.traloi_id) - parseFloat(a.traloi_id));
      chuagan = chuagan
        .filter((item) => !selected.includes(item))
        .sort((a, b) => parseFloat(b.traloi_id) - parseFloat(a.traloi_id));
      this.DSCauTraLoi_dagan.list = dagan;
      this.DSCauTraLoi_chuagan.list = chuagan;
      this.ds_traloi_selected = [];
      this.Loading = false;
    },
    resetPR(value) {
      this.button.btnRight = false;
    },
    resetPL(value) {
      this.button.btnLeft = false;
    },
    
    selectedPhoDaGan_click: function (value) {
      if (value && value.length > 0) {
        this.ds_traloi_selected = value;
      }
    },
     selectedPhoChuaGan_click: function (value) {
      if (value && value.length > 0) {
        this.ds_traloi_selected = value;
      }
    },
     handleBtnLeft: function () {
      this.Loading = true;
      this.button.btnLeft = true;
      var dagan = this.DSCauTraLoi_dagan.list;
      var chuagan = this.DSCauTraLoi_chuagan.list;
      var selected = this.ds_traloi_selected;
        chuagan = chuagan
        .concat(selected)
        .sort((a, b) => parseFloat(b.traloi_id) - parseFloat(a.traloi_id));
     dagan = dagan
        .filter((item) => !selected.includes(item))
        .sort((a, b) => parseFloat(b.traloi_id) - parseFloat(a.traloi_id));
      this.DSCauTraLoi_dagan.list = dagan;
      this.DSCauTraLoi_chuagan.list = chuagan;
      this.ds_traloi_selected = [];
      this.Loading = false;
    },
  },
};
function compare( a, b ) {
  if ( a.traloi_id < b.traloi_id ){
    return -1;
  }
  if ( a.traloi_id > b.traloi_id ){
    return 1;
  }
  return 0;
}
</script>

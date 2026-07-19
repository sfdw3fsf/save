<template src='./ChonChungTuQT.html'></template>

<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/chonchungtuqt";
import moment from "moment";
import Vue from "vue";
import {
  GridPlugin,  
} from "@syncfusion/ej2-vue-grids";
import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'
import { DropDownListPlugin,MultiSelectPlugin } from "@syncfusion/ej2-vue-dropdowns";
import { MultiSelect, CheckBoxSelection } from '@syncfusion/ej2-dropdowns';
import { Query, Predicate } from "@syncfusion/ej2-data";
Vue.use(DatePickerPlugin,GridPlugin,DropDownListPlugin,MultiSelectPlugin)
MultiSelect.Inject(CheckBoxSelection)


export default {
  components: { breadcrumb },
  name: "ChonChungTuQT",

  data() {
    return {
      dtpTuNgay: new Date((new Date()).getTime() - 7*24*60*60*1000),
      dtpDenNgay: new Date(),
      DonViSelected: [],
      DSDonVi: [],
      DSChungTu: [],
      vdenghi_id: 0,
      nhapmoi: false,
      listChungTuChecked :[],
      dt_chungtu_ : [],
      DSVatTu: [],
      json_chungtu_denghi: [],
      animationSettings: { effect: 'Zoom' },      
      target: '.main-wrapper',
      dateConfig: {
        default: new Date(),
        formatDay: "dd/MM/yyyy",
        waterMark: "Chọn ngày",
      },
      onFilteringDonVi: function (e) {
        var query = new Query();
        let predicate;

        if (e.text !== "") {
          predicate = new Predicate("TEN_DV", 'contains', e.text.trim(), true);
          query.where(predicate);
        }

        e.updateData(this.dataSource, query);
      },
    };
  },
  
  computed: {
    ...mapState("chonchungtuqt", statePropertyName),
    ...mapGetters("chonchungtuqt", getterName),
    
  },
  watch: {
   
  },
  mounted: async function () {
    
  },

  methods: {
    ...mapActions("chonchungtuqt", actionName),
    ...mapMutations("chonchungtuqt", mutationName),

    async loadData() {
      this.frmChonChungTu_QT_Load()
    },
    f_DateToString: function (value,format='DD/MM/YYYY'){
      return moment(value).format(format)
    },
    async Open(){      
      this.$refs.popupChonChungTuQT.show();
    },
    async frmChonChungTu_QT_Load(){
      this.DSDonVi = await this.getDSDonVi({loaiVt: [5]})
    },
    async tsbtnTraCuu_Click(){
      try{    
        this.loading(true)   
        this.DSChungTu = await this.getDSChungTuHoanUng({
          kieu: 1,
          donViId: this.DonViSelected.length == 0 ? "-1" :this.DonViSelected.toString(),
          tuNgay: this.f_DateToString(this.dtpTuNgay),
          denNgay: this.f_DateToString(this.dtpDenNgay),
          deNghiId: this.vdenghi_id,
          dsCt: this.json_chungtu_denghi.length == 0 ? [{}] : this.json_chungtu_denghi
        })
        this.listChungTuChecked = []
        if(this.DSChungTu.length == 0){
          this.DSVatTu = []
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async gridviewChungTu_FocusedRowChanged(row){
      this.DSVatTu = await this.getDSVatTu(row.CHUNGTU_ID)
    },
    gridDSChungTuCheckBox: function (dataItem) { 
      console.log(dataItem , " = dataItem")   
      this.listChungTuChecked = dataItem   
    },
    async btnCapNhat_Click(){
      if(this.DSChungTu.length == 0){
        this.$toast.error("Chưa có dữ liệu chứng từ không thể chọn")
        return
      }
      if(this.listChungTuChecked.length == 0){
        this.$toast.error("Chưa chọn chứng từ không thể xác nhận")
        return
      }
      var ds_chungtu = []
      this.listChungTuChecked.forEach(e =>
        ds_chungtu.push({CHUNGTU_ID : e.CHUNGTU_ID})
      )
      this.dt_chungtu_ = this.listChungTuChecked
      if(!this.nhapmoi){
        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm("Bạn có chắc chắn muốn tổng hợp chứng từ vào đề nghị không? ", {
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

        var kt_thucthi = await this.themDeNghiChungTu({
          deNghiId: this.vdenghi_id,
          ds: ds_chungtu
        })
        if(kt_thucthi == "OK"){
          this.$toast.success("Đã gán chứng từ thành công");
        }else{
          this.$toast.error("Có lỗi khi gán " + kt_thucthi);
        }
      }
      this.xacnhan = true
      this.Close();
    },
    Close(){      
      this.$parent.XacNhan_frmChonChungTuQT()
      this.$refs.popupChonChungTuQT.hide();     
    }
  },
 

  created: async function () {
   
  },
  
};
</script>

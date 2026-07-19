<template src="./ApproveBlockPrepaidSub.html"></template>
<style src="./ApproveBlockPrepaidSub.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import api from './API'
import Vue from 'vue'
import EventBus from '@/utils/eventBus'
import moment from 'moment'
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
export default {
  name: 'ApproveBlockPrepaidSub',
  components: {
    breadcrumb,
    KRequiredMarker,
    KDatePicker,
  },
  data() {
    return {
      selectedRow:false,
      popupComponentName: '',
      popupComponentHeader: '',
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {},
      dulieu:'',
      hangthuebaoduocchon:[],
      checkbox_chuaduyet: false,
      isDisabled:true,
      dataTruyenPopup:{
        ma_tb:'',
        loaitb_id:'',
        ten_loaitb:''
      },
      thongtintracuu:{
        dk_locphieu:-1,
        tungay: moment(new Date()).subtract(30, 'days').format('DD/MM/YYYY'),
        denngay: moment(new Date()).format('DD/MM/YYYY')

      },
      dklocphieu:{
        list:[
          { id: -1, text: 'Tất cả' },
          { id: 1, text: 'Đồng ý' },
          { id: 2, text: 'Không đồng ý' },
          { id: 0, text: 'Chưa duyệt' },
        ]
      },
      capnhat_kq:{
        kq_duyet:29,
        ghichu:''
      },
      kqduyet:{
        list:[]
      },
      gridTitle:'',
      gridDSphieuyeucau:[],
      viewInvTemplate: function () {
					return { template : Vue.component('columnTemplate', {
						template: `<div><a href="javascript: void(0)" @click="onViewInv" title="Xem chi tiết"><span></span>Xem chi tiết</a></div>`,
						data () {
							return { data: {} };
						},
						methods: {
							onViewInv() {
								EventBus.$emit('onViewInv', this.data);
							}
						}})
					}
			},
      tt_nd:[],
      ip:'',
      selectedRow:false,
    }
  },
  created: async function () {
    EventBus.$on('onViewInv', this.onViewInvHandler);
  },
  destroyed () {      
    EventBus.$off('onViewInv', this.onViewInvHandler);
  },
  mounted :async function() {
    this.loading(true)
    console.log('Đã gọi vòng đời mounted');
    this.tt_nd = await this.$root.token.getThongTinNguoiDung()
    this.ip = await this.$root.token.getIP()
    console.log('thông tin người dùng:',this.tt_nd, this.ip)
    this.gridTitle="Phiếu yêu cầu khóa cưỡng bức thuê bao trả trước"
    await this.laydsKQduyet()
    await this.layTT_Click()
    this.loading(false)
  },
  watch: {
    'thongtintracuu.dk_locphieu'(newVal){
      console.log('Lọc phiếu thay đổi:', newVal);
      this.updateGridTitle(newVal);
    }
  },
  methods: {
    updateGridTitle(value){
      if (value == 1){
        this.gridTitle = 'Phiếu yêu đã duyệt - Đồng ý';
      }else if(value == 2){
        this.gridTitle = 'Phiếu yêu đã duyệt - Không đồng ý';
      }else if (value == 0){
        this.gridTitle = 'Phiếu yêu cầu - Chưa duyệt'
      }else{
        this.gridTitle = 'Phiếu yêu cầu khóa cưỡng bức thuê bao trả trước'
      }
    },
    async laydsKQduyet(){
      let response = await api.layds_KQduyet(this.axios);
      if (response &&response.data && response.data.data &&response.data.error &&response.data.error_code === 'BSS-00000000') {
        this.kqduyet.list = response.data.data.map((x) =>{
        return { id: x['kqxl_id'], text: x['ketqua_xl'] }
        });
      }
    },
    selectedItemsChanged(item){
      this.hangthuebaoduocchon = item
      console.log(' Hàng thuê bao được chọn',this.hangthuebaoduocchon)
    },
    checkBoxChange(item){
      // console.log('a Khánh qq',item)
      // var data = []
      // const checkList = item.selectedRowIndexes
      // for (let i = 0; i < checkList.length; i++) {
      //   var index_checkList = checkList[i]
      //   var data_checkList = this.gridDSphieuyeucau[index_checkList]
      //   data.push(data_checkList)
      // }
      // this.hangthuebaoduocchon = data
      // console.log(' Hàng thuê bao được chọn',this.hangthuebaoduocchon)
    },
    async onViewInvHandler(item){
      console.log('Thiện test nhấn popup',item)
      this.dataTruyenPopup.ma_tb = item.ma_tb
      this.dataTruyenPopup.loaitb_id = item.loaitb_id
      this.dataTruyenPopup.ten_loaitb = item.loaihinh_tb
      
      if (item.loaitb_id == 61 || item.loaitb_id == 58 || item.loaitb_id == 171 || item.loaitb_id == 271 ){
         this.showPopup_Chitiet()
      } else {
        this.$toast.error('Trạng thái chỉ hiển thị đối với các thuê bao Fiber và MyTV ')
      }
    },
    async ghilai_Click_1(index){
      if (this.hangthuebaoduocchon.length<=0){
        this.$toast.error('Bạn phải tích chọn thuê bao cần phê duyệt')
        return
      }
      if (this.capnhat_kq.kq_duyet == 28){
        this.loading(true);
        await this.ghilai_Click()
        this.loading(false);
        return
      }
      this.$bvModal
        .msgBoxConfirm("Bạn chắc chắn muốn tạm dừng dịch vụ của các thuê bao đã chọn ?", {
          title: "Xác nhận",
          size: "sm",
          okTitle: "Có",
          cancelTitle: "Không",
        })
        .then(async (v) => {
          if (v) {
            this.loading(true);
            await this.ghilai_Click()
            this.loading(false);
          }
        })
        .catch((e) => {
          console.log(e);
          this.loading(false);
        });
    },
    async ghilai_Click(){
      try{
         // Tạo mảng ds_thuebao từ dữ liệu hàng thuê bao được chọn
        const params = this.hangthuebaoduocchon.map(item => ({
          BAOHONG_ID: item.baohong_id,
          PHIEU_ID: item.phieu_id,
          THUEBAO_ID: item.thuebao_id,
          LSOB_ID: item.lsob_id
        }));
        const dataInput = {
          ds_thuebao: JSON.stringify(params),
          kq_duyet: this.capnhat_kq.kq_duyet,
          ghichu: this.capnhat_kq.ghichu
        };
        const response = await api.ghilai_DuyetYC(this.axios, dataInput)
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data === 'ok' ) {
          this.$toast.success('Cập nhật kết quả phê duyệt thành công')
          await this.layTT_Click()
        }else{
          this.$toast.error(response?.data?.data || 'Có lỗi xảy ra');
        }
      }catch(error){
        this.$toast.error('Xảy ra lỗi khi cập nhật kết quả phê duyệt')
      }finally{
        this.loading(false);
      }
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
   async ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
      this.popupComponent = null;
    },
    async popupClosed(val,val2,val3) {
      this.loading(true);
      try {
        switch (this.popupComponentName) {
          case "xemChitiet": 
            console.log("Thiện check đóng popup")

          break;
        }
      } catch (ex) {
        if (ex.data && ex.data.message_detail) {
          this.$toast.error("" + ex.data.message_detail);
        } else {
          this.$toast.error("" + ex);
        }
      } finally {
        this.ClosePopup("popupComponents");
        this.loading(false);
      }
    },
    showPopup_Chitiet : function () {
      this.popupComponent = () =>
        import(
          "@/modules/contract/setup/ApproveBlockPrepaidSub/popup/xemChitiet.vue"
        );
      this.popupComponentName = "xemChitiet";
      this.popupComponentHeader = 'TRA CỨU TRẠNG THÁI SỬ DỤNG DỊCH VỤ';
      this.popupComponentData = {
        isPopup: true,
        ma_tb: this.dataTruyenPopup.ma_tb,
        loaitb_id: this.dataTruyenPopup.loaitb_id,
        ten_loaitb: this.dataTruyenPopup.ten_loaitb
      }; 
      console.log('Dữ liệu file cha truyền cho form XEM CHI TIẾT:',this.popupComponentData)
      this.Popup("popupComponents");
    },
    async layTT_Click(){
      this.loading(true)
      if (this.thongtintracuu.dk_locphieu == 0){
        this.checkbox_chuaduyet = true
        this.isDisabled = false
      } else {
        this.checkbox_chuaduyet = false // lát sửa
        this.isDisabled = true
      }
      console.log('Thông tin tra cứu', this.thongtintracuu.dk_locphieu, this.thongtintracuu.denngay, this.thongtintracuu.tungay)
      if (moment(this.thongtintracuu.tungay, 'DD/MM/YYYY').isAfter(moment(this.thongtintracuu.denngay, 'DD/MM/YYYY'))) {
        this.$toast.error('Từ ngày không được lớn hơn Đến ngày')
        this.loading(false)
        return
      }
      this.clearAll()
      try {
        // Format từ ngày đến ngày
        const formatDate = (dateString) => {
          if (!dateString) return "";
          const [day, month, year] = dateString.split('/');
          return `${year}${month}${day}`;
        };
        //
        const Input = {
          kq_duyet: Number(this.thongtintracuu.dk_locphieu),
          tu_ngay: this.thongtintracuu.tungay ? formatDate(this.thongtintracuu.tungay) : "",
          den_ngay: this.thongtintracuu.denngay ? formatDate(this.thongtintracuu.denngay) : ""
        };
        const response = await api.layds_PhieuYC(this.axios, Input);
        if (response && response.data && response.data.error_code === "BSS-00000000"){
          console.log('có data trả về',response.data.data)
          this.gridDSphieuyeucau = response.data.data;

          // Group khi có cùng thông tin khachhang_id 
          this.gridDSphieuyeucau.forEach(item => {
            if (item.KHACHHANG_ID !== null) {
              item.group = 'Mã KH: ' + item.ma_kh + ' - ' + item.ten_kh + ' - ' + item.sdt_kh + ' - ' + item.diachi_kh;
            }
          });
          console.log(' Danh sách phiếu yêu cầu có vấn đề',this.gridDSphieuyeucau)
        }else{
          if (this.thongtintracuu.dk_locphieu == 0){
            this.$toast.error('Không có phiếu chờ duyệt')
          }
        }
      }catch(error){
        this.$toast.error('Xảy ra lỗi khi lấy danh sách thuê bao trả trước')
      }finally{
        this.loading(false);
      }
    },
    async onSelectedRow(item){
      if(item){
        this.selectedRow = true
      }else{
        this.selectedRow = false
      }
    },
    clearAll(){
      this.gridDSphieuyeucau = []
      this.capnhat_kq.ghichu = ''
      this.capnhat_kq.kq_duyet = 29
      this.hangthuebaoduocchon = []
    },
  },
}
</script>

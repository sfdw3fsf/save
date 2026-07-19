<template>
  <div class="box-form">
    <div class="legend-title">Danh Sách Kết quả</div>
    <div class="tab-checkbox">
      <div class="table-content manual-fill">
        <DataGrid ref="refDsKetQua" :columns="danhSachKetQua.headers" :dataSource="danhSachKetQua.dataSources" :showColumnCheckbox="false"
          :enable-paging-server="false" :allowPaging="true" :showFilter="true"
          :contextMenuItems="danhSachKetQua.contextMenuItems"
          @contextMenuClick="contextMenu_Click" 
          @selectedRowChanged="onSelectedRowChanged" />
      </div>
    </div>

  </div>
</template>

<script>

import SockJS from 'sockjs-client' // Import SockJS
import { Stomp } from '@stomp/stompjs' // Import Stomp
import moment from 'moment';
import _ from 'lodash'
import helper from '../helper';
import API from '../api';
import apiHelper from '../api.helper';
import EventBus from "@/utils/eventBus";
export default {

  name: 'DanhSachKetQua',
  props: {


  },
  data() {
    return {
      danhSachKetQua: {
        headers: [
          { fieldName: 'tentinh', headerText: 'Tỉnh', allowFiltering: true, visible: true },          
          { fieldName: 'ten_dv', headerText: 'Đơn vị', allowFiltering: true, visible: true },          
          { fieldName: 'ma_tb', headerText: 'Mã thuê bao', allowFiltering: true, visible: true },
          { fieldName: 'ma_kh', headerText: 'Mã khách hàng', allowFiltering: true, visible: true },          
          { fieldName: 'ten_dvvt', headerText: 'Dịch vụ', allowFiltering: true, visible: true },
          { fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true, visible: true },
          { fieldName: 'ngay_tn', headerText: 'Ngày tiếp nhận', allowFiltering: true, visible: true },
          { fieldName: 'nguoi_kn', headerText: 'Người khiếu nại', allowFiltering: true, visible: true },
          { fieldName: 'dienthoai_lh', headerText: 'ĐT liên hệ', allowFiltering: true, visible: true },
          { fieldName: 'chudekn_id', headerText: 'Chủ đề khiếu nại', allowFiltering: true, visible: true },
          { fieldName: 'ma_kn', headerText: 'Mã khiếu nại', allowFiltering: true, visible: true },
          { fieldName: 'nd_khieunai', headerText: 'Nội dung khiếu nại', allowFiltering: true, visible: true },
          { fieldName: 'trangthai_kn', headerText: 'Trạng thái', allowFiltering: true, visible: true },
          { fieldName: 'nguoi_cn', headerText: 'Nhân viên', allowFiltering: true, visible: true },
          { fieldName: 'ngay_gq', headerText: 'Ngày giải quyết', allowFiltering: true, visible: true },
          { fieldName: 'noidung_ton', headerText: 'Nội dung tồn', allowFiltering: true, visible: true },
          { fieldName: 'ghichu_kn', headerText: 'Ghi chú khiếu nại', allowFiltering: true, visible: true },
          { fieldName: 'may_cn', headerText: 'Máy cập nhật', allowFiltering: true, visible: true },
          { fieldName: 'ngay_cn', headerText: 'Ngày cập nhật', allowFiltering: true, visible: true },
          { fieldName: 'ten_nv', headerText: 'Người cập nhật', allowFiltering: true, visible: true },
          { fieldName: 'cmt', headerText: 'CMND', allowFiltering: true, visible: true },
          { fieldName: 'nguyennhan_kn', headerText: 'Nguyên nhân', allowFiltering: true, visible: true },
          { fieldName: 'diachi_lh', headerText: 'Địa chỉ', allowFiltering: true, visible: true },
          { fieldName: 'quytrinh', headerText: 'Quy trình', allowFiltering: true, visible: true },
        ],
        dataSources: [],
        selectedRow: null,
        contextMenuItems: [
          
           {
            id: 'frmTracuuchitiet',
            tag: '',
            text: 'Tra cứu chi tiết',
            path: 'https://onebss.vnpt.vn/#/complaint/SearchComplainDetail?ma_tb=input_matb',
          }, 
          {
            id: 'frmChitietxuli',
            tag: '',
            text: 'Chi tiết xử lí',
            path: 'https://onebss.vnpt.vn/#/ext-khieunai/tiep-nhan-khieu-nai?ma_tb=input_matb',
          },
          {
            id: 'frmGiaophieu',
            tag: '',
            text: 'Giao phiếu',
            path: 'https://onebss.vnpt.vn/#/ext-khieunai/giaophieu-khieunai?ma_tb=input_matb',
          },
          {
            id: 'frmChitietthuebao',
            tag: '',
            text: 'Chi tiết thuê bao',
            path: 'https://onebss.vnpt.vn/#/search/SearchGeneral?ma_tb=input_matb&taikhoan=input_matb',
          }
        ],
      },
      stompClient: null,
      domainlocal: process.env.API + "/web-ipcc",
      socket: null,

    }
  },
  computed: {
    cptHeader() {
      return 'Bearer ' + JSON.parse(localStorage.getItem('OneBSS-Token')).access_token
    },
    userCode() {
      return this.$root.token.getUserName();
    },


  },

  methods: {
    onSelectedRowChanged(item) {
      this.danhSachKetQua.selectedRow = null
      if (!item) return;
      this.danhSachKetQua.selectedRow = item;
      this.$emit('on-row-selected', item)

    },

    getDanhSachKhieuNai(param) {
      try {
        this.$parent.onSetButtonEnable("tsbtnStop", true);


        this.danhSachKetQua.dataSources = [];
        const headers = { Authorization: this.cptHeader };

        console.log(this.domainlocal)
        this.socket = new SockJS(this.domainlocal + "/ipcc-endpoint"); // Adjust the URL
        this.stompClient = Stomp.over(this.socket);

        this.stompClient.connect(headers, () => {
          this.reconnect = true;
          console.log("Connected");

          this.socket.onclose = () => {
            if (!this.reconnect) {
              return;
            }

            console.error("WebSocket connection closed or encountered an error");
            setTimeout(() => {
              if (this.reconnect) {
                this.connect();
              }
            }, 5000);
          };

          var topic = this.userCode + "_" + moment().format("DDMMYYHHmmss") + String(Math.floor(Math.random() * 1000))
          this.stompClient.subscribe("/topic/" + topic, (message) => {
            console.log("body", message.body);

            let obj = JSON.parse(message.body)
            if (obj["Status"] == "1") {
              this.disconnect()
            } else {
              const result = obj.Data;
              // Data se tra ve o day, xu ly de show len man hinh
              if (result?.length)
                this.danhSachKetQua.dataSources = _.concat(this.danhSachKetQua.dataSources,result)
            }   
          });

          const data = {
            UserCode: topic,
            service: "SP_TRACUUKHIEUNAI",  
            input: param
          };
        
          
          // Gửi dữ liệu tới server
          console.log("dữ liệu: " ,JSON.stringify(data))
          this.stompClient.send(topic, headers, JSON.stringify(data));
        });
      } catch (err) {
        console.log("lay danh sach khieu nai loi: ", err)
        this.$parent.onSetButtonEnable("tsbtnStop", false)
      }

    },
    async contextMenu_Click(args) {
      var selected = this.$refs.refDsKetQua.$refs.grid.getSelectedRecords()
      console.log(args)
      var matb = ''
      var makh = ''
      var sdt = ''
      var path = args.item.path
      var id_menu = args.item.id
      if (selected && selected.length > 0) {
        if(id_menu == 'frmChitietxuli' && this.$parent.statusOption.value == 5){
          console.log('da xu ly xong')
          let tinh_id = selected[0]['tinh_id']
          EventBus.$emit('emitChuyenTinh', {id: String(tinh_id), reload : false});
          path = 'https://onebss.vnpt.vn/#/ext-khieunai/nghiemthu-khdn'

          if (process.env.NODE_ENV == 'testing') {
            path = path.replace('https://onebss.vnpt.vn', 'https://dev-onebss.vnpt.vn')
          }      
          window.open(path, '_blank')
        }        
        else {  
          let tinh_id = selected[0]['tinh_id']
          EventBus.$emit('emitChuyenTinh', {id: String(tinh_id), reload : false});

          matb = selected[0]['ma_tb']
          if (matb) path = path.replaceAll('input_matb', matb)
          else path = path.replaceAll('input_matb','')

          sdt = this.so_tb ? this.so_tb : (selected[0]['so_dt'] ? selected[0]['so_dt'] : selected[0]['ma_tb'])
          if (sdt) path = path.replaceAll('input_sdt', sdt)
          else path = path.replaceAll('input_sdt','')

          makh = selected[0]['ma_kh']
          if (makh) path = path.replaceAll('input_makh', makh)
          else path = path.replaceAll('input_makh','') 

          if (process.env.NODE_ENV == 'testing') {
            path = path.replace('https://onebss.vnpt.vn', 'https://dev-onebss.vnpt.vn')
          }      
          window.open(path, '_blank')
        }

      } else {
        if (path.includes('input_matb')) path = path.replaceAll('input_matb','')
        else if (path.includes('input_makh')) path = path.replaceAll('input_makh','')
        else if (path.includes('input_sdt')) path = path.replaceAll('input_sdt','')

        if (process.env.NODE_ENV == 'testing') {
          path = path.replace('https://onebss.vnpt.vn', 'https://dev-onebss.vnpt.vn')
        }      
        window.open(path, '_blank')
      }
     
    },
    disconnect() {
      this.reconnect = false
      if (this.socket) this.socket.close()
      console.log('Disconnected')
      this.$parent.onSetButtonEnable("tsbtnStop", false)
    },

    async xoaPhieu(){
      
      if(!this.danhSachKetQua.selectedRow){
        this.$toast.warning("Không có dữ liệu !")
        return;
      }

      try {
        this.loading(true)
        let { tinh_id,khieunai_id } = this.danhSachKetQua.selectedRow        
        let param = {tinh_id 
          , khieunai_id  
        }
          const response = await  API.deletePhieu(this.axios,param );
          //const errMsg = apiHelper.getSrtCodeError(response);
          this.loading(false)
          if (response && response.data && response.data.error_code === 'BSS-00000000' ){
            if(response.data.message != "Success"){
              this.$toast.error(response.data.message)
              return;
            }
          }
          console.log("errMsg", response.data.message)
          
          
          this.danhSachKetQua.dataSources = this._removeItemFormArray(this.danhSachKetQua.dataSources, this.danhSachKetQua.selectedRow)
          this.$toast.success("Xoá phiếu thành công")
      } catch (error) {
        this.$toast.error("Có lỗi xảy ra: ", error)
        this.loading(false)
        console.log("err:", error)
      }
    },
    searchDetail(){

      if (!this.danhSachKetQua.selectedRow)
        return this.$toast.error("Không có dữ liệu để tìm kiếm");

      const {ma_tb  } = this.danhSachKetQua.selectedRow
        this.$router.push({ path: '/search/SearchInstallDetail', query: { ma_tb: ma_tb}})
    },

    ExportExcel() {

      if (!this.danhSachKetQua.dataSources.length)
        return this.$toast.error("Không có dữ liệu để xuất Excel !");

      helper.exportExcel(this.danhSachKetQua.dataSources, "Ton_Khieu_Nai");
    },

    _removeItemFormArray(array, {tinh_id,khieunai_id}){      
    //   return  _.remove(array, function (item) {
    //     return (tinh_id != item.tinh_id && khieunai_id != item.khieunai_id)
    //  });
     let index = array.findIndex(
          (x) => x.tinh_id == x.tinh_id && khieunai_id == x.khieunai_id
        );      
      if (index >= 0) {        
        array.splice(index, 1)        
      }
      return array
    }

  },
  watch: {
    
  }


}
</script>
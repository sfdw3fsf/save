<template>
  <div class="box-form">
    <div class="legend-title">Danh Sách Kết quả</div>
    <div class="tab-checkbox">
      <div class="table-content manual-fill">
        <DataGrid id="refDsKetQua" ref="refDsKetQua" :columns="danhSachKetQua.headers" :dataSource="danhSachKetQua.dataSources" :showColumnCheckbox="false"
          :enable-paging-server="false" :allowPaging="true" :showFilter="true"
          @selectedRowChanged="onSelectedRowChanged"
          :contextMenuItems="danhSachKetQua.contextMenuItems"
           @contextMenuClick="contextMenu_Click" @rowDoubleClicked="Lichsu_Click"
           @queryCellInfo="gridviewDanhSach_queryCellInfo"
          />
      </div>
    </div>
    <Lichsuphieugiao
        ref="lichsugiaophieuModal"
        :baohong_id = "baohong_id"
        :phanvung_id = "phanvung_id"
      />
     <GiaoviecNV
        ref="giaoviecNVModal"
        :phanvung_id = "phanvung_id"
        :donvi_id = "donvi_id"
        @xacnhan="GiaoviecNV"
      />
      <ejs-dialog :enableResize='true' :visible="false"  id="popupfrnGiaoPhieuNV" ref="popupfrnGiaoPhieuNV" :animationSettings="animationSettings"
    :header='"Giao phiếu nhân viên"' showCloseIcon=true target=".main-wrapper" width="90%">
  </ejs-dialog>
  </div>
</template>

<script>

import SockJS from 'sockjs-client' // Import SockJS
import { Stomp } from '@stomp/stompjs'
import moment from 'moment';
import _ from 'lodash'
import helper from '../helper';
import API from '../api';
import apiHelper from '../api.helper';
import EventBus from "@/utils/eventBus";
import Lichsuphieugiao from "../popups/Lichsuphieugiao/Lichsuphieugiao.vue";
import GiaoviecNV from "../popups/GiaoviecNV/GiaoviecNV.vue";

import frnGiaoPhieuNV from '@/modules/contract/setup/DeclareLandline/popups/frnGiaoPhieuNV_V2'
export default {

  name: 'DanhSachKetQua',
  props: {


  },
  components: {Lichsuphieugiao,frnGiaoPhieuNV,GiaoviecNV },
  data() {
    return {
      danhSachKetQua: {
        contextMenuItems: [
          {
            id: 'frmGiaophieu',
            tag: '',
            text: 'Giao phiếu',
            path: 'https://onebss.vnpt.vn/#/htkh/Order?ma_tb=input_matb&taikhoan=input_matb',
          },
          {
            id: 'frmBoquaphieu',
            tag: '',
            text: 'Bỏ qua phiếu',
            path: '',
          },
          {
            id: 'frmGiuphieu',
            tag: '',
            text: 'Giữ phiếu',
            path: '',
          },
           {
            id: 'frmTracuuchitiet',
            tag: '',
            text: 'Tra cứu chi tiết',
            path: 'https://onebss.vnpt.vn/#/incident/SearchIncident?ma_tb=input_matb&taikhoan=input_matb',
          }, {
            id: 'frmShowport',
            tag: '',
            text: 'Show port',
            path: 'https://onebss.vnpt.vn/#/search/checkport?ma_tb=input_matb&taikhoan=input_matb',
          },{
            id: 'frmChitietxuli',
            tag: '',
            text: 'Chi tiết xử lí',
            path: '',
          },{
            id: 'frmGiaoviec',
            tag: '',
            text: 'Giao việc',
            path: '',
          },{
            id: 'frmChitietthuebao',
            tag: '',
            text: 'Chi tiết thuê bao',
            path: 'https://onebss.vnpt.vn/#/search/SearchGeneral?ma_tb=input_matb&taikhoan=input_matb',
          }
        ],
        headers: [
          { fieldName: 'tentinh', headerText: 'Tỉnh', allowFiltering: true, visible: true },
          { fieldName: 'ma_tb', headerText: 'Mã thuê bao', allowFiltering: true, visible: true }, // thieu
          { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, visible: true },
          { fieldName: 'loaihinh_tb', headerText: 'Loại hình TB', allowFiltering: true, visible: true },
          { fieldName: 'ten_dv', headerText: 'Đơn vị nhận', allowFiltering: true, visible: true },
          { fieldName: 'ten_dv_xl', headerText: 'Đơn vị đang xử lý', allowFiltering: true, visible: true },
          { fieldName: 'ngay_bh', headerText: 'Ngày báo hỏng', allowFiltering: true, visible: true }, // thieu
          { fieldName: 'ma_nd', headerText: 'Người giữ phiếu', allowFiltering: true, visible: true },
          { fieldName: 'nguoi_cn', headerText: 'Người báo hỏng', allowFiltering: true, visible: true },
          { fieldName: 'dienthoai_bh', headerText: 'SĐT BH', allowFiltering: true, visible: true },
          { fieldName: 'dienthoai_lh', headerText: 'Điện thoại liên hệ', allowFiltering: true, visible: true },
          { fieldName: 'trangthai_bh', headerText: 'Trạng thái bảo hỏng', allowFiltering: true, visible: true },
          { fieldName: 'ten_nv', headerText: 'Nhân viên', allowFiltering: true, visible: true },
          { fieldName: 'ghichu_hong', headerText: 'Ghi chú hỏng', allowFiltering: true, visible: true },
          { fieldName: 'so_ao', headerText: 'Số ảo', allowFiltering: true, visible: true },
          { fieldName: 'ma_bh', headerText: 'Mã báo hỏng', allowFiltering: true, visible: true },
          { fieldName: 'kenh_tn', headerText: 'Kênh tiếp nhận', allowFiltering: true, visible: true },
          { fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true, visible: true },
          { fieldName: 'may_cn', headerText: 'Máy cập nhật', allowFiltering: true, visible: true },
          { fieldName: 'ngay_cn', headerText: 'Ngày cập nhật', allowFiltering: true, visible: true },
          { fieldName: 'nguoi_cn', headerText: 'Người cập nhật', allowFiltering: true, visible: true },
          { fieldName: 'ten_quytrinh', headerText: 'Quy trình', allowFiltering: true, visible: true },
          { fieldName: 'ten_trangthai', headerText: 'Trạng thái xử lý', allowFiltering: true, visible: true },

        ],
        dataSources: [],
        selectedRow: null,

      },
      ttGiaoViec: {
        ngayGiao : "",
        nguoiGiaoId: 0,
        phieuId: 0
      },
      tinh_id: 0,
      baohong_id: 0,
      phanvung_id: 0,
      donvi_id: 0,

      stompClient: null,
      domainlocal: process.env.API + "/web-ipcc",
      socket: null,
      NHANHONG_TB_KHOAMAY: false
    }
  },
  async mounted() {
        this.phanvung_id = this.$root.token.getPhanVungID();
        this.donvi_id = this.$root.token.getDonViID();
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
      this.baohong_id = item.baohong_id
      this.tinh_id = item.tinh_id
      if (!item) return;
      this.danhSachKetQua.selectedRow = item;
      this.$emit('on-row-selected', item)

    },
       gridviewDanhSach_queryCellInfo(args) {
        console.log(5555)
        console.log(args.column.field)
          if (args.column.field == 'ngay_bh') {
              console.log(args.cell.innerHTML)
              const ngay_bh_value = moment(args.cell.innerHTML, 'DD/MM/YYYY HH:mm:ss');
              console.log(ngay_bh_value)
              const thoi_diem_hien_tai = new Date();
              console.log(thoi_diem_hien_tai)
              const khoang_cach_thoi_gian = thoi_diem_hien_tai - ngay_bh_value;
              console.log('khoang_cach_thoi_gian')

              console.log(khoang_cach_thoi_gian)
              if (Math.floor(khoang_cach_thoi_gian / (1000 * 60)) >30) {
                  args.cell.setAttribute('style', 'background-color:green;');
              }
          }
        },
    async GiaoviecNV(data) {
      const response = await  API.giuphieu_bh_nhanhkhac(this.axios,{
          "vphanvung_id":this.danhSachKetQua.selectedRow.tinh_id,
          "vbaohong_id":this.danhSachKetQua.selectedRow.baohong_id,
          "vma_nd": data[0]["ma_nv"]
      });

      if (response.data.error == "200"&&response.data.message == "Success")
      {
          this.$root.toastSuccess("Giao việc thành công!");
          const updatedDataSources = this.danhSachKetQua.dataSources.map(item => {
            if (item.baohong_id === this.danhSachKetQua.selectedRow.baohong_id) {
                item.ma_nd = data[0]["ma_nv"]
            }
            return item;
          });
          this.danhSachKetQua.dataSources = updatedDataSources;
      }
    },
    Lichsu_Click(index, rowData) {
            this.baohong_id = rowData.baohong_id
            this.phanvung_id = rowData.tinh_id
            this.$refs.lichsugiaophieuModal.showModal();
    },
    getDanhSachBaohong(param) {
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
            service: "SP_TRACUUBAOHONGTOANQUOC_V2",  // Fix
            input: param
          };


          // Gửi dữ liệu tới server
          console.log("dữ liệu: " ,JSON.stringify(data))
          this.stompClient.send(topic, headers, JSON.stringify(data));
        });
      } catch (err) {
        console.log("lay danh sach báo hỏng loi: ", err)
        this.$parent.onSetButtonEnable("tsbtnStop", false)
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
            this.$bvModal.msgBoxConfirm('Bạn có muốn xóa phiếu này không ?', {
                    title: 'Xác nhận',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }).then(async v => {
                    if (v) {
                        this.loading(true)
                        const { tinh_id,baohong_id } = this.danhSachKetQua.selectedRow
                        const param = {tinh_id , baohong_id }
                        const response = await  API.xoaphieu_baohong(this.axios,param );

                        console.log("errMsg", response.data.error_code)
                        this.loading(false)
                        if(response.data.error_code != "BSS-00000000"){
                          this.$toast.error(errMsg)
                          return;
                        }
                        this.danhSachKetQua.dataSources = this._removeItemFormArray(this.danhSachKetQua.dataSources, tinh_id,baohong_id)
                        this.$toast.success("Xoá phiếu thành công");
                        this.loading(false);
                    }
                });

      } catch (error) {
        this.$toast.error("Có lỗi xảy ra: ", error)
        this.loading(false)
        console.log("err:", error)
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
        if(id_menu == 'frmGiaoviec'){
          this.$nextTick(() => {
            this.$refs.giaoviecNVModal.showModal()
          })
        } else if (id_menu == 'frmChitietxuli'){
           await API.giuphieu_bh_nhanhkhac(this.axios, {
              "vphanvung_id":selected[0]['tinh_id'],
              "vbaohong_id":selected[0]['baohong_id'],
              "vma_nd":this.$auth.getMaNhanVien()
            })
            .then((response) => {
              if (response.data.error == "200"&&response.data.message == "Success")
              {
                this.$root.toastSuccess("Giao việc thành công!");
                const updatedDataSources = this.danhSachKetQua.dataSources.map(item => {
                    if (item.baohong_id === selected[0]['baohong_id']) {
                        item.ma_nd = this.$root.token.getUserName();
                    }
                    return item;
                });
                this.danhSachKetQua.dataSources = updatedDataSources;
              }
            })
            .catch((error) => {
              this.$root.toastSuccess(error)
              console.log(error);
            })
            .finally(() => {
              this.loading(false);
          });
          if (selected[0]['ttbh_id'] == 1)
          {
           //path = "https://onebss.vnpt.vn/#/incident/ReceiveIncident?ma_tb=" + selected[0]['ma_tb'];
           path = "https://onebss.vnpt.vn/#/incident/ReceiveIncident?tag=3&thuebao_id="+ selected[0]['thuebao_id']+"&baohong_id="+ selected[0]['baohong_id']+"&ma_tb="+ selected[0]['ma_tb'];
          } else if (selected[0]['ttbh_id'] == 3)
          {
            path = "https://onebss.vnpt.vn/#/incident/SearchIncident?ma_tb="+ selected[0]['ma_tb'];
          }
          else
          {
            path = "https://onebss.vnpt.vn/#/incident/ProcessAcceptanceIncidentByTag?tag="+ selected[0]['ttbh_id']+"&kieu_Load=1&matb_tk="+ selected[0]['ma_tb']+"&dichvt_tk_id="+ selected[0]['dichvuvt_id']+"&quytrinh_tk_id="+ selected[0]['quytrinh_id']
          }
          let tinh_id = selected[0]['tinh_id']
          EventBus.$emit('emitChuyenTinh', {id: String(tinh_id), reload : false});
          if (process.env.NODE_ENV == 'testing') {
            path = path.replace('https://onebss.vnpt.vn', 'https://dev-onebss.vnpt.vn')
          }
          window.open(path, '_blank')
        } else if (id_menu == 'frmGiuphieu'){
            this.loading(true);
            await API.giuphieu_bh_nhanhkhac(this.axios, {
              "vphanvung_id":selected[0]['tinh_id'],
              "vbaohong_id":selected[0]['baohong_id'],
              "vma_nd":this.$auth.getMaNhanVien()
            })
            .then((response) => {
              if (response.data.error == "200"&&response.data.message == "Success")
              {
                this.$root.toastSuccess("Giữ phiếu thành công!");
                const updatedDataSources = this.danhSachKetQua.dataSources.map(item => {
                    if (item.baohong_id === selected[0]['baohong_id']) {
                        item.ma_nd = this.$root.token.getUserName();
                    }
                    return item;
                });
                this.danhSachKetQua.dataSources = updatedDataSources;
                console.log(this.danhSachKetQua.dataSources)
              }
            })
            .catch((error) => {
              this.$root.toastSuccess(error)
              console.log(error);
            })
            .finally(() => {
              this.loading(false);
            });
        }else if (id_menu == 'frmBoquaphieu'){
            await API.fn_boqua_phieu_tien_xuly(this.axios, {
              p_phanvung_id: selected[0]['tinh_id'],
              p_baohong_id: selected[0]['baohong_id']
            })
            .then((response) => {
              if (response.data.error_code === "BSS-00000000")
              {
                this.$root.toastSuccess(response.data.data);
                const updatedDataSources = this.danhSachKetQua.dataSources.map(item => {
                    if (item.baohong_id === selected[0]['baohong_id']) {
                        item.ma_nd = ""
                    }
                    return item;
                });
                this.danhSachKetQua.dataSources = updatedDataSources;
              }
            })
            .catch((error) => {
              this.$root.toastSuccess(error)
              console.log(error);
            })
            .finally(() => {
              this.loading(false); frmBoquaphieu
            });
        } else if (id_menu == 'frmGiaophieu'){
            if (selected[0]['ttbh_id'] == 1)
            {
                this.$bvModal.msgBoxConfirm('Bạn có muốn xử lý phiếu này không ?', {
                    title: 'Đánh dấu đang xử lý',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }).then(async v => {
                    if(v) {
                        let input = {
                          p_phanvung_id: selected[0]['tinh_id'],
                          p_ma_ts: "NHANHONG_TB_KHOAMAY"
                        }
                        const thamso = await API.fn_kiemtra_thamso_md(this.axios,input);
                        if (thamso.data.error_code === 'BSS-00000000') {
                          this.NHANHONG_TB_KHOAMAY = true;
                        }
                        if (this.NHANHONG_TB_KHOAMAY && selected[0]['trangthaitb_id'] != 1)
                        {
                            this.$toast.error("Trạng thái hiện tại của thuê bao không thể giao phiếu")
                        }
                        let ttnd = {
                            "NHANVIEN_ID": this.danhSachKetQua.selectedRow.nhanvien_id,
                            "DONVI_ID": this.danhSachKetQua.selectedRow.donvi_id,
                            "NGUOI_CN": this.$root.token.getUserName(),
                            "MAY_CN": this.$root.token.getMachine(),
                            "IP_CN": this.$root.token.getIP()
                        }
                        let param = {
                            p_phanvung_id: this.danhSachKetQua.selectedRow.tinh_id,
                            p_ds_para: JSON.stringify({
                                "BAOHONG_ID": this.danhSachKetQua.selectedRow.baohong_id,
                                "TINH_ID": this.danhSachKetQua.selectedRow.tinh_id
                            }),
                            p_ds_ttnd: JSON.stringify(ttnd)
                        }
                        const { tinh_id,baohong_id } = this.danhSachKetQua.selectedRow
                        const giaonhanh = await API.fn_tudong_giaophieu_baohong_dai(this.axios,param);
                        if (giaonhanh.data.error_code === "BSS-00000000")
                        {
                            this.$root.toastSuccess("Giao phiếu thành công!");
                            const {
                                tinh_id,
                                baohong_id
                            } = this.danhSachKetQua.selectedRow
                            this.danhSachKetQua.dataSources = this._removeItemFormArray(this.danhSachKetQua.dataSources, tinh_id,baohong_id)
                        }else{
                            this.$toast.error("Lỗi giao phiếu: "+ giaonhanh.data.message);
                        }
                    }
                });
            }else
            {
                path = "https://onebss.vnpt.vn/#/incident/ProcessAcceptanceIncidentByTag?tag="+ selected[0]['ttbh_id']+"&kieu_Load=1&matb_tk="+ selected[0]['ma_tb']+"&dichvt_tk_id="+ selected[0]['dichvuvt_id']+"&quytrinh_tk_id="+ selected[0]['quytrinh_id']
                if (process.env.NODE_ENV == 'testing') {
                  path = path.replace('https://onebss.vnpt.vn', 'https://dev-onebss.vnpt.vn')
                }
                window.open(path, '_blank')
            }

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
    _removeItemFormArray_(dsKetqua, { tinh_id, baohong_id }) {
            return dsKetqua.filter(item => {
                return item.tinh_id !== tinh_id || item.baohong_id !== baohong_id;
            });
    },
      _removeItemFormArray(dsKetqua, tinh_id, baohong_id) {
            const indexToRemove = dsKetqua.findIndex(item => item.tinh_id === tinh_id && item.baohong_id === baohong_id);
            if (indexToRemove !== -1) {
                dsKetqua.splice(indexToRemove, 1);
            }
            return dsKetqua;
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

      helper.exportExcel(this.danhSachKetQua.dataSources, "Bao_hong_ton");
    },

  },
  watch: {

  }


}
</script>

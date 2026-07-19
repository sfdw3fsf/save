<template src="./L_AssignCrossSellSubscriberMechanism.html"></template>
<style src="./L_AssignCrossSellSubscriberMechanism.css"></style>
<script>
import _ from "lodash";
import  moment from "moment";
import breadcrumb from '@/components/breadcrumb';
import DichVuVienThong from "@/const/enums/DichVuVienThong";
import DanhSachBienDongTB from "./popups/DanhSachBienDongTB";
import SearchContractModal from '../../setup/HandoverTicket/components/SearchContractModal';
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import KTable from "@/components/kylq_components/KTable.vue";
import KTableCustom from "./components/KTableCustom.vue";
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
import api from "./api";
export default {
  name: "L_AssignCrossSellSubscriberMechanism",
  components: {
    breadcrumb,
    DanhSachBienDongTB,
    SearchContractModal,
    KTable,
    KTableCustom,
    KDatePicker,
  },
  mounted() {
    this.loadDanhMuc();
  },
  data() {
    return {
      thientest:'',
      list: [],
      header: {
        title: 'Gán cơ chế bán chéo cho thuê bao',
        list: []
      },
      hdtb_coche_id:[],
      thiennn:0,
      tu:0,
      ma_kh:'',
      loaihd_id: 0,
      isHuyButtonDisabled:true,
      isSaveButtonDisabled: true,
      ma_coche:'0',
      socongvan:'',
      rowsToSave: [],
      rowsToSave_1:[],
      hdtb_idd:'',
      hdtb_id_select:[],
      isPopup: false,
      checkTinhTraCuu: false,
      cboTinhTraCuu: [],
      tinhId: null,
      formThongTinPhieuYeuCau: {
        hdkh_id: null,
        xxxx:'',
        khachhang_id: null,
        dichVu: "",
        ngayYeuCau: "",
        maGiaoDich: "",
        ngayLapHD: "",
        loaiHD: "",
        kieuLD: "",
        huongKNMoi: "",
        soMayMoi: "",
        huongKNCu: "",
        soMayCu: "",
        tocDoCirOptions: [],
        tocDoCirId: null,
        checkTocDoPir: false,
        tocDoPirOptions: [],
        tocDoPirId: null,
        checkTocDoNix: false,
        tocDoNixOptions: [],
        tocDoNixId: null,
        checkTocDoIsp: false,
        tocDoIspOptions: [],
        tocDoIspId: null,
        tenKh: "",
        dienThoai: "",
        diaChiKh: "",
        checkNCCKhacChuyenSang: false,
        nguoiGiao: "",
        ngayYCHT: "",
        noiNhan: "",
        maTBTinh: "",
        nguoiLapHD: "",
        trangThaiTC: "",
        lyDoThoaiBC: "",
        ghiChu: "",
        maGdTinh: "",
      },
      ds_biendong: [],
      gridDanhSachTB: [],
      gridDanhSachTB_gan: [],
      gridDanhSachPCDT: [],
      dsThueBao_gan: {
        headers: [
          {fieldName: 'ma_gd', headerText: 'Mã giao dịch', allowfiltering: true},
          {fieldName: 'ten_loaihd', headerText: 'Loại hợp đồng', allowfiltering: true},
          {fieldName: 'ngay_yc', headerText: 'Ngày yêu cầu', allowfiltering: true},
          {fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowfiltering: true},
          {fieldName: 'ten_dvvt', headerText: 'Dịch vụ', allowfiltering: true},
          {fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowfiltering: true},
          {fieldName: 'donvi', headerText: 'Đơn vị', allowfiltering: true},
          {fieldName: 'ten_loaipt', headerText: 'Loại phát triển', allowfiltering: true},
          {fieldName: 'congdoan', headerText: 'Công đoạn', allowfiltering: true},
          {fieldName: 'tyle', headerText: 'Tỷ lệ PCDT', allowfiltering: true},
          {fieldName: 'tungay', headerText: 'Từ ngày', allowfiltering: true},
          {fieldName: 'denngay', headerText: 'Đến ngày', allowfiltering: true},
        ],
        selectedIndex: 0,
        selectedItem: null,
        checked: []
      },
      dsThueBao: {
        headers: [
          {fieldName: 'ma_tb', headerText: 'Số máy', allowfiltering: true},
          {fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowfiltering: true},
          {fieldName: 'ten_dvvt', headerText: 'Dịch vụ', allowfiltering: true},
          {fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowfiltering: true},
          {fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowfiltering: true},
          {fieldName: 'diachi_tb', headerText: 'Địa chỉ thuê bao', allowfiltering: true},
          {fieldName: 'trangthai_hd', headerText: 'Trạng thái', allowfiltering: true},
          {fieldName: 'ten_dt', headerText: 'Đối tượng', allowfiltering: true},
          {fieldName: 'hdtb_id', headerText: 'Hợp đồng TB', allowfiltering: true},
          {fieldName: 'ngay_kt', headerText: 'Ngày hết hạn', allowfiltering: true},
          {fieldName: 'ngay_ht', headerText: 'Ngày HT', allowfiltering: true},
        ],
        selectedIndex: 0,
        selectedItem: null,
        checked: []
      },
      columns2: [{
        field: 'ten_dvvt',
        label: 'Dịch vụ',
        allowEdit: false,
        allowFilter: true,
        allowEditDate: false,
        isCheckColumn: false,
      },{
        field: 'loaihinh_tb',
        label: 'Loại hình',
        allowEdit: false,
        allowFilter: true,
        allowEditDate: false,
        isCheckColumn: false,
      },{
        field: 'loai_pt',
        label: 'Loại phát triển',
        allowEdit: false,
        allowFilter: true,
        allowEditDate: false,
        isCheckColumn: false,
      },{
        field: 'ten_dv',
        label: 'Đơn vị',
        allowEdit: false,
        allowFilter: true,
        allowEditDate: false,
        isCheckColumn: false,
      },{
        field: 'congdoan',
        label: 'Công đoạn',
        allowEdit: false,
        allowFilter: true,
        allowEditDate: false,
        isCheckColumn: false,
      },{
        field: 'tyle',
        label: 'Tỉ lệ Phân chia doanh thu',
        allowEdit: false,
        allowFilter: true,
        allowEditDate: false,
        isCheckColumn: false,
      }, {
        field: 'tungay',
        label: 'Từ ngày',
        allowEditDate: true,
        allowFilter: true,
        allowEdit: false,
        isCheckColumn: false,

      }, {
        field: 'denngay',
        label: 'Đến ngày',
        allowEditDate: true,
        allowFilter: true,
        allowEdit: false,
        isCheckColumn: false,
      },],
      grcThanhVien: {
        list: [],},
      table: {
        loading: false,
        options: [],
        config: [
          {
            headerText: 'STT',
            allowFiltering: true,width: '100',
            fieldName: 'stt'
          },
          {
            headerText: 'Năm',
            allowFiltering: true,width: '100',
            fieldName: 'nam'
          },
          {
            headerText: 'Ngày quyết định',
            allowFiltering: true,width: '100',
            fieldName: 'ngay_qd'
          },
          {
            headerText: 'Công văn',
            allowFiltering: true,width: '100',
            fieldName: 'socongvan'
          },
          {
            headerText: 'Hiệu lực',
            allowFiltering: true,width: '100',
            fieldName: 'hieuluc'
          },
          {
            headerText: 'Nội dung',
            allowFiltering: true,width: '100',
            fieldName: 'noidung'
          },
          {
            headerText: 'Công văn VNP',
            allowFiltering: true,width: '100',
            fieldName: 'cv_vnp_id'
          },
          {
            headerText: 'Đơn vị phát hành',
            allowFiltering: true,width: '100',
            fieldName: 'ten_dv'
          },
          {
            headerText: 'Người CN',
            allowFiltering: true,width: '100',
            fieldName: 'nguoi_cn'
          },
          {
            headerText: 'Ngày nhập',
            allowFiltering: true,width: '100',
            fieldName: 'ngay_cn'
          }
        ],
        all_data: []
      },
      popupComponent: null,
      popupComponentName: "",
      popupComponentHeader: "",
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {
        "form-close": this.popupClosed,
      },
    };
  },
  methods: {
    gridDsThueBao_gan_SelectedRowChanged(selectedItem) {
    console.log('Hàng được chọn:', selectedItem);
  },
    onRowCheckChange(event, item) {
      // Xử lý sự kiện khi hàng được chọn hoặc bỏ chọn
      console.log('Row check changed:', event, item);
      if (event.target.checked) {

      } else {

      }
    },
    onDataCheckeds_1(data){
      console.log('Các hàng PCDT được chọn:', data);
      this.isSaveButtonDisabled=true
      for (let row of data){
        if ( !row.tungay || !row.denngay){
          this.$toast.error('Điền đầy đủ từ ngày, đến ngày trước khi tích chọn ! '); 
          return;
        }
        if (row.denngay < row.tungay){
          this.$toast.error('Đến ngày không được nhỏ hơn từ ngày ! '); 
          this.isSaveButtonDisabled = true
          return
        }else {
          this.isSaveButtonDisabled = false
        }
        row.tyle = row.tyle.toString();
        console.log('Tỷ lệ:', typeof row.tyle)
        // this.isSaveButtonDisabled = data.length === 0;
      }
          if (data.length >0){
            this.tu = 1
          }else{
            this.tu = 0
          }
          if(this.thiennn==1 && this.tu==1)
          {
            this.isSaveButtonDisabled=false
          }
           else this.isSaveButtonDisabled=true

        const rowsToSave = data.map(row => {
        const { checked,congdoan,ghichu,loai_pt,loaihinh_tb,ten_dv,ten_dvvt, ...newRow } = row;
        const rid =row.rid;
        const formattedTuNgay = this.formatDateX(row.tungay);
        const formattedDenNgay = this.formatDateX(row.denngay);
        return { ...newRow, tungay: formattedTuNgay, denngay: formattedDenNgay, rid};
      });
      console.log('Data sau khi chuẩn hóa',rowsToSave)
      this.rowsToSave = rowsToSave;
      
    },
   async saveData_1(hdtbid,rowsToSave){
      try{
        this.loading(true);

        // Tú thêm
        console.log("123456789",hdtbid);
        let result =this.gridDanhSachTB_gan.filter(obj => obj.hdtb_id == hdtbid);
        console.log("123456789",result);
        let newArray = result.map(obj => ({ 
          tyle: obj.tyle,
          tungay: this.parseDate(obj.tungay),
          denngay: this.parseDate(obj.denngay),
          rid: obj.rid,
         }));
        console.log("987654321",newArray);
        // newArray.push(rowsToSave);
        newArray = newArray.concat(rowsToSave)

        const dataToSend = {
          p_hdtb_id: hdtbid,
          p_js_coche: JSON.stringify(newArray)
        };

        console.log('Mệt:',dataToSend )
        const response = await api.gan_coche_thuebao(this.axios, dataToSend);
        console.log(response.data); 
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          this.$toast.success('Dữ liệu đã được lưu vào cơ sở dữ liệu thành công.');
        } else {
          this.$toast.error('Đã xảy ra lỗi khi lưu dữ liệu vào cơ sở dữ liệu.');
        }
      }catch(error){
        this.$toast.error('Đã xảy ra lỗi khi gửi yêu cầu gán dữ liệu.');
      }finally{
        this.loading(false);
      }
    },
    // saveData_Click() {
    //   this.saveData_1(this.rowsToSave);

    //   this.lay_ds_tb_da_gan_coche_bancheo(this.formThongTinPhieuYeuCau.hdkh_id);
    // },
    async saveData_Click() {
      const allowedIds = ['1','2','5','6','7','8','16','24','25','30','41'];
      console.log('Tú qq yêu dấu',this.loaihd_id)
      if (allowedIds.includes(this.loaihd_id)) {
        try {
          for (let hdtb_id of this.hdtb_id_select) {
            await this.saveData_1(hdtb_id, this.rowsToSave);
          }
          this.lay_ds_tb_da_gan_coche_bancheo(this.formThongTinPhieuYeuCau.hdkh_id);
        } catch (error) {
          console.error("Error in saveData_Click:", error);
        }
      } else {
        this.$toast.error("Thuê bao thuộc loại HĐ không được phép gán cơ chế PCDT");
      }
    },
    clearDates(data) {
      data.forEach(item => {
        item.tungay = "";
        item.denngay = "";
      });
    },
   async huyData_1(hdtb_coche_id){
      try{
        this.loading(true);
        const dataToSend = {
          p_js_coche: JSON.stringify(hdtb_coche_id)
        };
        console.log('Mệtx:',hdtb_coche_id )
        const response = await api.bogan_coche_thuebao(this.axios, dataToSend);
        console.log(response.data); 
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          this.$toast.success('Đã bỏ gán, dữ liệu đã được lưu vào cơ sở dữ liệu thành công.');
        } else {
          this.$toast.error('Đã xảy ra lỗi khi lưu dữ liệu vào cơ sở dữ liệu.');
        }
      }catch(error){
        this.$toast.error('Đã xảy ra lỗi khi gửi yêu cầu bỏ gán dữ liệu.');
      }finally{
        this.loading(false);
      }
    },
    async huyData_Click(){
      try {
        await this.huyData_1(this.hdtb_coche_id);
        this.lay_ds_tb_da_gan_coche_bancheo(this.formThongTinPhieuYeuCau.hdkh_id);
        this.isHuyButtonDisabled=true
      } catch (error) {
        console.error("Error in huyData_Click:", error);
      }
    },
    formatDateX(dateString) {
      const [year, month, day] = dateString.split('-');
      return `${day}/${month}/${year}`;
    },
    checkSelectedRows() {
      const selectedRows = this.$refs.gridDanhSachTB.getSelectedRows();
      console.log('Selected Rows:', selectedRows);
    },
    popupChonThanhVien() {
      this.popupComponent = () =>
        import("./popups/popupChonThanhVien");
      this.popupComponentName = "popupChonThanhVien";
      this.popupComponentHeader = "Chọn số công văn";
      this.popupComponentData = { socongvan: this.$refs.socongvan_select }
      this.Popup("popupComponents");
    },
    handleFormClose(socongvan_select) {
  // Xử lý giá trị socongvan_select từ component con
      console.log('Giá trị socongvan_select từ component con:', socongvan_select);
      this.socongvan = socongvan_select;
      this.onClickSearch_1()
      this.ClosePopup("popupComponents");
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
    btnThemThanhVien_Click: function () {
      this.popupChonThanhVien()
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
      this.popupComponent = null;
    },
    async popupClosed(val) {
      if (!val) {
        this.ClosePopup("popupComponents");
        return;
      }
      this.loading(true);
      try {
        switch (this.popupComponentName) {
          case "popupChonDonVi":
            this.grcTinh.list = val
            break;             
        }
      } catch (ex) {
        if (ex.data && ex.data.message_detail) {
          this.$toast.error("" + ex.data.message_detail);
        } else {
          this.$toast.error("" + ex);
        }
        
        if (this.debug) console.error(ex);
      } finally {
        this.ClosePopup("popupComponents");
        this.loading(false);
      }
    },
    async loadDanhMuc() {
      api.lay_ds_tinh(this.axios).then(ret => {
        this.cboTinhTraCuu = ret.data.data;
        this.tinhId = this.$root.token.getPhanVungID();
      });
      // api.sp_lay_trangthai_hd(this.axios, {});
      api.lay_dm_tocdo_mgwan(this.axios, {}).then(ret => {
        const data = ret.data.data;
        this.formThongTinPhieuYeuCau.tocDoCirOptions = data;
        this.formThongTinPhieuYeuCau.tocDoPirOptions = data;
        this.formThongTinPhieuYeuCau.tocDoNixOptions = data;
        this.formThongTinPhieuYeuCau.tocDoIspOptions = data;
      });
    },
    async lay_ds_PCDT(ma_kh, socongvan, ma_coche) {
      this.gridDanhSachPCDT = [];
      this.loading(true);

      try {
        const response = await api.lay_ds_PCDT(this.axios, {
          p_ma_kh: this.ma_kh,
          p_so_cv: this.socongvan.trim(),
          p_ma_coche: this.ma_coche.trim(),
        });

        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          if (response.data.data && response.data.data.length > 0) {
            let danhsachPCDT = response.data.data.map(item => {
              if (item.ten_dvvt) {
                return {
                  ten_dvvt: item.ten_dvvt,
                  loaihinh_tb: item.loaihinh_tb,
                  loai_pt: item.loai_pt,
                  ten_dv: item.ten_dv,
                  congdoan: item.congdoan,
                  tyle: item.tyle,
                  tungay: this.dtpTuNgay,
                  denngay: this.dtpDenNgay,
                  ghichu: '',
                  rid: item.rid,
                  checked: false
                };
              }
            });
            this.gridDanhSachPCDT = danhsachPCDT;
          } else {
            this.$toast('Không có dữ liệu để hiển thị!');
          }
        } else {
          this.$toast('Không có dữ liệu để hiển thị!');
        }
      } catch (e) {
        this.$toast('Không lấy được danh sách PCDT');
      } finally {
        this.loading(false);
      }
    },
    async lay_ds_tb_da_gan_coche_bancheo(hdkh_id){
      this.gridDanhSachTB_gan = [];
      try{
        console.log('Vào đây',hdkh_id)
        const response = await api.lay_ds_tb_da_gan_coche_bancheo(this.axios,hdkh_id)
        console.log('Thiện',response.data.data)
        if(response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data){
          let ds_tbGan = response.data.data.map(item => {
            if(item.ma_tb){
              return {
                ten_dvvt:item.ten_dvvt,
                ten_loaihd:item.ten_loaihd,
                loai_pt:item.loai_pt,
                ma_tb:item.ma_tb,
                congdoan:item.congdoan,
                tyle:item.tyle,
                ten_loaipt:item.ten_loaipt,
                ma_gd:item.ma_gd,
                ngay_yc:item.ngay_yc,
                ten_dvvt:item.ten_dvvt,
                loaihinh_tb:item.loaihinh_tb,
                donvi:item.tentinh,
                tungay:item.tungay,
                denngay:item.denngay,
                rid:item.rid,
                hdtb_coche_id:item.hdtb_coche_id,
                hdtb_id:item.hdtb_id
              }
            }
          });
          this.gridDanhSachTB_gan = ds_tbGan;
          console.log('gridDanhSachTB_gan',this.gridDanhSachTB_gan)
        }

      }catch (e) {
        // this.$toast('Không lấy được danh sách thuê bao đã gán cơ chế')
      } finally {
        this.loading(false)
      }
    },
    checkBoxChange(item){
      console.log('Tú qq',item)
      var data =[]
      const hdtbid = []
      const checkList = item.selectedRowIndexes
      for (let i = 0; i < checkList.length; i++) {
        var index_checkList=checkList[i]
        var data_checkList= this.gridDanhSachTB[index_checkList]
        data.push(data_checkList)
        }
      console.log('Các hàng thuê bao được chọn:',data)
      for (let row of data){
        hdtbid.push(row.hdtb_id)
      }
      this.hdtb_id_select = hdtbid
      console.log('Mảng hdtb_id được chọn:',this.hdtb_id_select)
      console.log('HDTB_ID:',hdtbid)
      this.hdtb_idd = hdtbid[0]
      // this.thientest = hdtbid[0]
      console.log('HDTB_ID:',this.hdtb_idd)
      if (data.length>0){
        this.thiennn = 1
      }else{
        this.thiennn = 0
      }
      if(this.thiennn==1 && this.tu==1)
      {
        this.isSaveButtonDisabled=false
      }
      else this.isSaveButtonDisabled=true
      // this.isSaveButtonDisabled = hdtbid.length === 0;
      // if (this.hdtb_idd >0){
      //   this.lay_ds_tb_da_gan_coche_bancheo(this.formThongTinPhieuYeuCau.hdkh_id);
      // } else{
      //   this.lay_ds_tb_da_gan_coche_bancheo('');
      // }
    },
  checkBoxChange_1(item){
    console.log('a Khánh qq',item)
    var data = []
    
    // Lấy danh sách các hàng được chọn
    const checkList = item.selectedRowIndexes
    for (let i = 0; i < checkList.length; i++) {
        var index_checkList=checkList[i]
        var data_checkList= this.gridDanhSachTB_gan[index_checkList]
        data.push(data_checkList)
        }
    console.log('Dữ liệu của các hàng gán được chọn:',data)
    const hdtbcocheid = data.map(row => {
      const {congdoan,denngay,donvi,loai_pt,loaihinh_tb,ma_gd,ma_tb,ngay_yc,rid,ten_dvvt,ten_loaihd,ten_loaipt,tungay,tyle, ...newRow}=row;
      return { ...newRow};
    })
    console.log('Data bỏ gán sau khi chuẩn hóa',hdtbcocheid)
    this.hdtb_coche_id = hdtbcocheid
    //     console.log('thiennn', this.thiennn)
    //   if (data.length >0){
    //     this.tu = 1
    //   }else{
    //     this.tu = 0
    //   }
    //   if(this.thiennn==1 && this.tu==1)
    //   {
    //     this.isHuyButtonDisabled=false
    //   }
    //   else this.isHuyButtonDisabled=true
    this.isHuyButtonDisabled = checkList.length === 0;
    // this.isSaveButtonDisabled=true
    // // Lấy toàn bộ dữ liệu của các hàng thuê bao
    // const allRows = this.gridDanhSachTB_gan;
    // // Khởi tạo mảng chứa dữ liệu của các hàng không được chọn
    // const unselectedRows = [];
    // // Duyệt qua tất cả các hàng
    // for (let row of allRows) {
    //   // Nếu hàng không có trong danh sách được chọn, thêm nó vào mảng unselectedRows
    //   if (!data.includes(row)) {
    //     unselectedRows.push(row);
    //   }
    // }
    // console.log('Dữ liệu của các hàng không được chọn:', unselectedRows);
    // for (let row of unselectedRows) {
    //   row.ty_le = row.ty_le.toString();  
    // }
    //   const rowsToSave_1 = unselectedRows.map(row =>{
    //   const { ty_le,ten_loaipt,ten_loaihd,ten_dvvt,ngay_yc,ma_tb,ma_gd,loaihinh_tb,loai_pt,donvi,congdoan, ...newRow} = row;
    //   const rid =row.rid;
    //   const tyle =row.ty_le;
    //   const formattedTuNgay = this.parseDate(row.tungay);
    //     const formattedDenNgay = this.parseDate(row.denngay); 
    //     return { ...newRow, tungay: formattedTuNgay, denngay: formattedDenNgay, rid,tyle};
    //   });
    // console.log('Data sau khi chuẩn hóa bỏ gán', rowsToSave_1)
    // this.rowsToSave_1 = [];


  },
  parseDate(dateString) {
    const datePart = dateString.split(' ')[0];
    return datePart;
  },

    showPopupSearchContract() {
      this.$refs.popupSearchContract.showModal();
    },
    showPopupNoDocument() {
      this.$refs.popupNoDocument.showModal();
      console.log(' Anh Khánh qq')
    },
    showModal() {
      this.$refs.modalCon.show();
    },
    handleModalClosed() {
      console.log("Modal con đã đóng");
    },
    async acceptSearchContract(item) {
      console.log("LookupDetailConstructionCrossSale acceptSearchContract", JSON.parse(JSON.stringify(item)));
      this.clearKetQuaTimKiem();
      this.formThongTinPhieuYeuCau.maGiaoDich = item.ma_gd;
      this.$refs.txtMaGD.focus();
      this.onClickSearch()
    },
    async formCloseNoDocument(item) {
      console.log("A Khánh", JSON.parse(JSON.stringify(item)));
      this.clearKetQuaTimKiem();
      this.ten_cv = item.ten_cv;
    },
    onClickClearAll() {
      this.clearAll();
    },
    clearAll() {
      this.checkTinhTraCuu = false;
      this.tinhId = this.$root.token.getPhanVungID().toString();
      this.clearKetQuaTimKiem();
    },
    clearKetQuaTimKiem() {
      this.formThongTinPhieuYeuCau.hdkh_id = null;
      this.formThongTinPhieuYeuCau.khachhang_id = null;
      this.formThongTinPhieuYeuCau.dichVu = "";
      this.formThongTinPhieuYeuCau.ngayYeuCau = "";
      // this.formThongTinPhieuYeuCau.maGiaoDich = "";
      this.formThongTinPhieuYeuCau.ngayLapHD = "";
      this.formThongTinPhieuYeuCau.loaiHD = "";
      this.formThongTinPhieuYeuCau.kieuLD = "";
      this.formThongTinPhieuYeuCau.huongKNMoi = "";
      this.formThongTinPhieuYeuCau.soMayMoi = "";
      this.formThongTinPhieuYeuCau.huongKNCu = "";
      this.formThongTinPhieuYeuCau.soMayCu = "";
      this.formThongTinPhieuYeuCau.tocDoCirId = null;
      this.formThongTinPhieuYeuCau.checkTocDoPir = false;
      this.formThongTinPhieuYeuCau.tocDoPirId = null;
      this.formThongTinPhieuYeuCau.checkTocDoIsp = null;
      this.formThongTinPhieuYeuCau.tocDoIspId = null;
      this.formThongTinPhieuYeuCau.tenKh = "";
      this.formThongTinPhieuYeuCau.dienThoai = "";
      this.formThongTinPhieuYeuCau.diaChiKh = "";
      this.formThongTinPhieuYeuCau.checkNCCKhacChuyenSang = false;
      this.formThongTinPhieuYeuCau.nguoiGiao = "";
      this.formThongTinPhieuYeuCau.ngayYCHT = "";
      this.formThongTinPhieuYeuCau.noiNhan = "";
      this.formThongTinPhieuYeuCau.maTBTinh = "";
      this.formThongTinPhieuYeuCau.nguoiLapHD = "";
      this.formThongTinPhieuYeuCau.trangThaiTC = "";
      this.formThongTinPhieuYeuCau.lyDoThoaiBC = "";
      this.formThongTinPhieuYeuCau.ghiChu = "";
      this.formThongTinPhieuYeuCau.maGdTinh = "";
      this.ds_biendong = [];
      // this.gridDanhSachTB = [];
      this.gridDanhSachTB_gan = [];
      this.gridDanhSachPCDT = [];
      this.dsThueBao.selectedIndex = 0;
      this.dsThueBao.selectedItem = 0;
      this.dsThueBao.checked = [];
      this.socongvan="";
      this.ma_coche="";
    },
    async onClickSearch() {
      this.formThongTinPhieuYeuCau.maGiaoDich = this.formThongTinPhieuYeuCau.maGiaoDich.trim()
      const ma_gd = this.formThongTinPhieuYeuCau.maGiaoDich;
      if (!ma_gd) {
        alert("Vui lòng điền mã giao dịch");
        return;
      }
      if (this.checkTinhTraCuu && !this.tinhId) {
        alert("Vui lòng chọn tỉnh tra cứu");
        return;
      }
      try {
        this.loading(true);
        if (this.checkTinhTraCuu) {
          this.ds_biendong = (await api.bancheo_lay_ds_hd_theo_ma_tb(this.axios, {
            ma_tb: ma_gd,
            tinhthicong_id: this.tinhId
          })).data.data;
        } else {
          this.tinhId = this.$root.token.getPhanVungID().toString();
          this.ds_biendong = (await api.lay_ds_hd_theo_ma_tb(this.axios, { ma_tb: ma_gd })).data.data;
          console.log('ma_kh',this.ds_biendong[0].ma_kh)
          this.ma_kh = this.ds_biendong[0].ma_kh
          this.loaihd_id = this.ds_biendong[0].loaihd_id
          if (this.ds_biendong.length === 0 && +this.tinhId === 97 /* KHDN-Vinaphone */) {
            const phieu = (await api.giaophieu_ph(this.axios, {
              vphanvung_id: +this.$root.token.getPhanVungID(),
              ma_gd,
              ma_tb: ma_gd,
            })).data.data[0];
            if (phieu && phieu.tinh_id !== -1) {
              this.checkTinhTraCuu = true;
              this.tinhId = phieu.tinh_id.toString();
              this.ds_biendong = (await api.bancheo_lay_ds_hd_theo_ma_tb(this.axios, {
                ma_tb: ma_gd,
                tinhthicong_id: phieu.tinh_id
              })).data.data;
            }
          }
        }
        this.loading(false);
        if (this.ds_biendong.length > 0) {
          this.acceptBienDong(this.ds_biendong[0]);
          
        } else {
          this.$toast("Không tìm thấy hợp đồng");
        }
        // this.lay_ds_PCDT(this.formThongTinPhieuYeuCau.hdkh_id,this.socongvan);
        this.lay_ds_tb_da_gan_coche_bancheo(this.formThongTinPhieuYeuCau.hdkh_id);
        this.xxxx = this.formThongTinPhieuYeuCau.hdkh_id;
        console.log('xxxx',this.xxxx)
        this.thientest = this.formThongTinPhieuYeuCau.hdkh_id;
      } catch (e) {
        this.loading(false);
        console.log()
      }
    },
    async onClickSearch_1() {
      if (!this.socongvan || this.socongvan.trim() == ""){
        this.socongvan='0'
        // return;
        console.log('Số công văn không được để trống');
      }
      if (!this.ma_coche || this.ma_coche.trim() == ""){
        this.ma_coche='0'
      }
      console.log("Số công văn nhập vào: ", this.socongvan);
      console.log("Mã cơ nhập vào: ", this.ma_coche);
      // lấy ds PCDT theo số công văn
      this.lay_ds_PCDT(this.ma_kh,this.socongvan, this.ma_coche);
    },
    exportExcel(){
      let data=[]
      console.log('vào đây nha',this.thientest)
      if (this.thientest > 0){
        this.lay_file_ds_tb_da_gan_coche_bancheo(this.formThongTinPhieuYeuCau.hdkh_id);
      }else{
         this.lay_file_ds_tb_da_gan_coche_bancheo(null);
      }
    },
    async lay_file_ds_tb_da_gan_coche_bancheo(hdkh_id) {
      try {
        console.log('Vào đây', hdkh_id);
        const response = await api.lay_ds_tb_da_gan_coche_bancheo(this.axios, hdkh_id);

        console.log('Thiện', response.data.data);
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          let ds_tbGan = response.data.data.map(item => {
            if (item.ma_tb) {
              return {
                ten_dvvt: item.ten_dvvt,
                ten_loaihd: item.ten_loaihd,
                loai_pt: item.loai_pt,
                ma_tb: item.ma_tb,
                congdoan: item.congdoan,
                tyle: item.tyle,
                ten_loaipt: item.ten_loaipt,
                ma_gd: item.ma_gd,
                ngay_yc: item.ngay_yc,
                loaihinh_tb: item.loaihinh_tb,
                donvi: item.tentinh,
                tungay: item.tungay,
                denngay: item.denngay,
                rid: item.rid,
              };
            }
          }).filter(item => item !== undefined);
          this.exportToExcelFile(ds_tbGan);
        } else {
          this.$toast('Không có dữ liệu để xuất Excel');
        }
      } catch (e) {
        this.$toast('Không thể xuất Excel');
        console.error(e);
      } finally {
        this.loading(false);
      }
    },
    exportToExcelFile(data) {
      const XLSX = require('xlsx');
      const worksheet = XLSX.utils.json_to_sheet(data);
      const workbook = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(workbook, worksheet, 'Data');
      const excelBuffer = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
      const blob = new Blob([excelBuffer], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', 'Bảng gán cơ chế bán chéo cho thuê bao.xlsx');
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
  },
    async acceptBienDong(item) {
      this.$bvModal.hide("popupDanhSachBienDongTB");
      console.log("LookupDetailConstructionCrossSale acceptBienDong", JSON.parse(JSON.stringify(item)));
      const searchText = this.formThongTinPhieuYeuCau.maGiaoDich;
      this.clearKetQuaTimKiem();
      this.formThongTinPhieuYeuCau.hdkh_id = +item.hdkh_id || 0;
      this.formThongTinPhieuYeuCau.maGiaoDich = item.ma_gd;
      try {
        this.loading(true);
        let dsThueBao;
        if (this.checkTinhTraCuu) {
          dsThueBao = (await api.bancheo_lay_ds_hd_tb_theo_hdkh_id(this.axios, {
            tinhthicong_id: this.tinhId,
            hdkh_id: +item.hdkh_id
          })).data.data;
        } else {
          dsThueBao = (await api.lay_ds_hd_tb_theo_hdkh_id(this.axios, {
            hdkh_id: +item.hdkh_id,
          })).data.data;
        }
        const matchSearchTextIndex = dsThueBao.findIndex(it => it.ma_tb === searchText);
        if (matchSearchTextIndex >= 0) {
          this.dsThueBao.selectedIndex = matchSearchTextIndex;
        }
        this.gridDanhSachTB = dsThueBao;

      } catch (e) {
        this.$toast("Có lỗi khi lấy danh sách thuê bao");
      } finally {
        this.loading(false);
      }
    },
    async gridDsThueBao_SelectedRowChanged(item) {
      console.log('Các hàng thuê bao được chọn:',item)
      this.dsThueBao.selectedItem = item;
      if (!item) {
        this.formThongTinPhieuYeuCau.kieuLD = "";
        this.formThongTinPhieuYeuCau.dichVu = "";
        this.formThongTinPhieuYeuCau.ghiChu = "";
        this.formThongTinPhieuYeuCau.maTBTinh = "";
        this.formThongTinPhieuYeuCau.trangThaiTC = "";
        return;
      }
      this.formThongTinPhieuYeuCau.soMayMoi = item.ma_tb;
      this.formThongTinPhieuYeuCau.kieuLD = item.ten_kieuld;
      this.formThongTinPhieuYeuCau.dichVu = item.ten_dvvt; 
      this.formThongTinPhieuYeuCau.ghiChu = item.ghichu;
      const hdtb_id = this.dsThueBao.selectedItem.hdtb_id;
    },
  }
}
</script>
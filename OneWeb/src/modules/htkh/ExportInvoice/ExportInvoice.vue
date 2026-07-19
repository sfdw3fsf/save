<template src="./ExportInvoice.html"></template>

<script>
import breadcrumb from '@/components/breadcrumb'
import API from '../../htkh/ExportInvoice/API'
import DateTimeLib from '../../../utils/DateTimeLib'
//import report from './report';

const report = () => import('./report')

export default {
  name: 'Department',
  components: {
    breadcrumb,
    report
  },
  data () {
    return {
      doRenderPDF: false,
      thangnam: '',
      showPanelReport: false,
      fName: '',
      thongTinThueBao: [],
      thongTinThueBao2: [],
      thongTinThueBao3: [],
      thongTinThueBao4: [],
      thongTinThueBao5: [],
      thongTinBaoHong: [],
      txtMaNVTC: '',
      khachhang:'',
      header: {
        title: 'XUẤT DỮ LIỆU HÓA ĐƠN CHI TIẾT SANG FILE PDF',
        list: []
      },
      DSTrangthai: [],
      rowSelected: {},
      arrRowSelected: [],
      donviVaule: [],
      listItemDonVi: [],
      listItemDonViGoc: [],
      today: new Date(),
      dtpStartDate: new Date(),
      dtpEndDateSearch: new Date(),
      bangke: {
        value: '',
        list: []
      }
    }
  },
  computed: {

  },
  async mounted() {
    this.loading(true);

    let nhanvien_id = this.$root.token.getNhanVienID();
    await Promise.all([
      this.getDonVi(),
      this.getDsBangKe(),
    ])
    .then(() => {
      this.loading(false);
    })
    .catch(() => {
      this.loading(false);
    });
  },
  methods: {
    async getDonVi() {
      try {
        let list_tinh = await API.sp_lay_thongtin_ds_don_vi(this.axios);

        if (list_tinh.data.error_code === "BSS-00000000" && list_tinh.data.data !== undefined && list_tinh.data.data.length > 0) {
          this.listItemDonViGoc = list_tinh.data.data.map(a => {
            return {
              id : a.donvi_id,
              label : a.ten_dv,
            }
          })
        }

      } catch (e) {
        console.log(e);
      }
    },
    async getDsBangKe() {
      try {
        let ds_bangke = await API.sp_layds_banke(this.axios);

        if (ds_bangke.data.error_code === "BSS-00000000" && ds_bangke.data.data !== undefined && ds_bangke.data.data.length > 0) {
          this.bangke.list = ds_bangke.data.data;
          this.bangke.value = this.bangke.list[0].banke_id
        }

      } catch (e) {
        console.log(e);
      }
    },
    onChangeTrangThai: function(arr) {
      console.log(arr);
    },
    onChangeTreeSelect: function (dataitem) {
      console.log('on change');
    },
    removeNoChildren (node) {
      return {
        children: node.children && node.children.length ? node.children : 0
      }
    } ,
    isEmptyObj (Obj) {
      var isEmptyObj = !Object.keys(Obj).length
      return isEmptyObj
    },
    async getData() {
      try {
        this.loading(true)
        let startDate = null;
        let endDate = null;

        let arrDonvi = this.donviVaule.join();

        if (arrDonvi.length <= 0) {
          this.$root.toastError("Đơn vị không được để trống.")
          return false;
        }

        if (this.txtMaNVTC == null || this.txtMaNVTC == '') {
          //this.$root.toastError("Mã nhân viên thu không được đê trống.")
          //return false;
        }

        if (this.khachhang == null || this.khachhang == '') {
          this.$root.toastError("Mã khách hàng không được đê trống.")
          return false;
        }

        if (this.dtpStartDate == null) {
          //this.$root.toastError("Bạn chưa chọn thời gian Từ ngày.")
          //return false;
        } else {
          startDate = DateTimeLib.toDateDisplay(this.dtpStartDate);
        }

        if (this.dtpEndDateSearch == null) {
          //this.$root.toastError("Bạn chưa chọn thời gian Đến ngày.")
          //return false;
        } else {
          endDate = DateTimeLib.toDateDisplayWithFormat(this.dtpEndDateSearch, 'YYYYMM')
        }

        let fileExt = "";
        let dichvuvt_id = "";

        if (this.bangke.value == 78) { //Cố định
          fileExt = "CD";
          dichvuvt_id = "1"
        } else if (this.bangke.value == 79) { //Di động
          fileExt = "DD";
          dichvuvt_id = "2"
        } else {
          fileExt = "CT";
        }

        const params1 = {
          "p_namthang": endDate,
          "p_ds_manv_tc": this.txtMaNVTC,
          "p_ds_matt": this.khachhang,
          "p_ds_donvi" : arrDonvi,
          "dichvuvt_id": dichvuvt_id
        };

        let response1 = await API.sp_lay_thongtin_section_1_khachhang_banke_79(this.axios, params1)
        if (response1.data.error_code === 'BSS-00000000' && response1.data.data) {

          //this.thongTinThueBao = response1.data.data
          let data1 = []
          let data2 = []
          let data3 = []
          let data5 = []

          data1 = response1.data.data

          let arr_ma_tt = this.khachhang.split(',');
          for (let i = 0; i < arr_ma_tt.length; ++i) {

            const params2 = {
              "p_namthang": endDate,
              "p_matt": arr_ma_tt[i],
              "dichvuvt_id": dichvuvt_id
            };

            let response2 = await API.sp_lay_thongtin_section_2_ds_somay_banke_79(this.axios, params2)
            if (response2.data.error_code === 'BSS-00000000' && response2.data.data) {
              //this.thongTinThueBao2[arr_ma_tt[i]] = response2.data.data;
              data2[arr_ma_tt[i]] = response2.data.data;
            }

            let response3 = await API.sp_lay_thongtin_section_3_ds_somay_banke_79(this.axios, params2)
            if (response3.data.error_code === 'BSS-00000000' && response3.data.data) {
              let res = response3.data.data;
              //this.thongTinThueBao3[arr_ma_tt[i]] = res[0];
              data3[arr_ma_tt[i]] = res[0];
            }

            let response4 = await API.sp_lay_thongtin_section_4_didong_banke_79(this.axios, params2)
            let ds_thuebao = [];
            if (response4.data.error_code === 'BSS-00000000' && response4.data.data) {
              ds_thuebao = response4.data.data;
            }

            let response5 = await API.sp_lay_thongtin_section_5_ct_didong_banke_79(this.axios, params2)
            let chi_tiet_goi = [];
            if (response5.data.error_code === 'BSS-00000000' && response5.data.data) {
              chi_tiet_goi = response5.data.data;
            }

            if (ds_thuebao.length > 0) {
              let obj = []

              $.each(ds_thuebao, function( index, item1 ) {
                let gprs = 0;
                let ringtone = 0;
                let wap_data = 0;
                let tong_tien_dich_vu = 0;
                let chi_tiet_dich_vu = [];
                let tong_tien_goi = 0;
                let chi_tiet_cuoc_goi = [];
                let tong_tien_goi_quoc_te = 0;
                let chi_tiet_cuoc_goi_quoc_te = [];
                let nhan_tin = 0;
                let tien_goi_cuoc = 0;

                $.each(chi_tiet_goi, function( index, item2 ) {
                  if (item1.ma_tb == item2.chugoi) {
                    if (item2.huong_id == 200) {
                      tong_tien_goi += parseFloat(item2.tien)
                      item2.tien = Math.round(item2.tien)
                      chi_tiet_cuoc_goi.push(item2);
                    } else if (item2.huong_id == 202) {
                      tong_tien_dich_vu += parseFloat(item2.tien)
                      item2.tien = Math.round(item2.tien)
                      chi_tiet_dich_vu.push(item2);
                    } else if (item2.huong_id == 203) {
                      tong_tien_goi_quoc_te += parseFloat(item2.tien)
                      item2.tien = Math.round(item2.tien)
                      chi_tiet_cuoc_goi_quoc_te.push(item2);
                    } else if (item2.huong_id == 201) {
                      nhan_tin += item2.tien;
                    } else if (item2.huong_id == 205) {
                      wap_data = item2.tien;
                    } else if (item2.huong_id == 206) {
                      gprs += parseFloat(item2.tien);
                    } else if (item2.huong_id == 208) {
                      ringtone += parseFloat(item2.tien);
                    } else if (item2.huong_id == 400) {
                      tien_goi_cuoc = item2.tien;
                    }
                    else if (item2.huong_id == '' || item2.huong_id == null){
                      tong_tien_goi += parseFloat(item2.tien)
                      item2.tien = Math.round(item2.tien)
                      tong_tien_dich_vu += parseFloat(item2.tien)
                      item2.tien = Math.round(item2.tien)
                      tong_tien_goi_quoc_te += parseFloat(item2.tien)
                      item2.tien = Math.round(item2.tien)
                      // nhan_tin += item2.tien;
                      // wap_data = item2.tien;
                      // gprs += parseFloat(item2.tien);
                      // ringtone += parseFloat(item2.tien);
                      // tien_goi_cuoc = item2.tien;
                      chi_tiet_cuoc_goi.push(item2);
                    }
                  }
                });

                obj.push({
                  "ma_tb": item1.ma_tb,
                  "trong_nuoc_1": tong_tien_goi > 0 ? Math.round(tong_tien_goi) : 0,
                  "trong_nuoc_2": chi_tiet_cuoc_goi,
                  "quoc_te_1": tong_tien_goi_quoc_te > 0 ? Math.round(tong_tien_goi_quoc_te) : 0,
                  "quoc_te_2": chi_tiet_cuoc_goi_quoc_te,
                  "nhan_tin": nhan_tin > 0 ? Math.round(nhan_tin) : 0,
                  "dich_vu_1": tong_tien_dich_vu > 0 ? Math.round(tong_tien_dich_vu) : 0,
                  "dich_vu_2": chi_tiet_dich_vu,
                  "wap_data": wap_data > 0 ? Math.round(wap_data) : 0,
                  "gprs": gprs > 0 ? Math.round(gprs) : 0,
                  "ringtone": ringtone > 0 ? Math.round(ringtone) : 0,
                  "tien_goi_cuoc": tien_goi_cuoc > 0 ? Math.round(tien_goi_cuoc) : 0,
                });

              });

              data5[arr_ma_tt[i]] = obj
            }
          }

          this.thongTinThueBao = data1;
          this.thongTinThueBao2 = data2;
          this.thongTinThueBao3 = data3;
          this.thongTinThueBao5 = data5;

          let tmp  = arr_ma_tt[0].replaceAll("-", "");
          tmp  = tmp.replaceAll("HGG", "");
          this.fName = DateTimeLib.toDateDisplayWithFormat(this.dtpEndDateSearch, 'YYYY') + "_" + DateTimeLib.toDateDisplayWithFormat(this.dtpEndDateSearch, 'MM') + "_" + tmp + "_" + fileExt + ".pdf";

          document.querySelector('#report').scrollIntoView();
          this.doRenderPDF = !this.doRenderPDF
          //this.showPanelReport = true

        } else {
          this.$toast.error('Không có dữ liệu')
        }

      } catch (e) {
        console.log(e)
        this.$toast.error('lỗi : ' + e)
      } finally {
        this.loading(false)
      }
    },
    async tim_kiem() {
      //this.showPanelReport = false

      await this.getData();
    },
  },
}
</script>

<style scoped src="./ExportInvoice.css"></style>

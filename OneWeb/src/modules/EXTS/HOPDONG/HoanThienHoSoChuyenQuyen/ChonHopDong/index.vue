<template src="./index.html">
</template>

<script>
import Enum from "../Enum";
export default {
    components: { Enum },
    name: 'Modal',
    props: {
        id: String,
        dataHD: Object
    },
    data: function() {
        return {
            key: 1,
            loaihd_id: 0,
            dichvuvt_id: 0,
            tthd_id: 0,
            isDisableBtn: true,
            isShown: false,
            show: false,
            chkLoaiHD: false,
            chkDVVT: false,
            txtAcc: '',
            txtMaGD: '',
             modelCbo: {
                cboDVVT: 0,
                vcboDVVT: [],
                cboLoaiHD: 0,
                vcboLoaiHD: []
            },
            dtList: [],
            selectedRow: {},
            // totalRowDsHopDong: 0,
            // paging_dsHopDong: {
            //   pNo: 1,
            //   pSize: 10,
            // },
        }
    },
    computed: {
        // dieukienTK: function() {
        //     let obj = this.dsKieuTK.find(v => v.kieutk_id == this.kieuTKId);
        //     return obj.dieukien;
        // }
    },
    methods: {
       onLoad: function(){
           this.loaihd_id = this.dataHD.loaihd_id
           this.dichvuvt_id = this.dataHD.dichvuvt_id
           this.tthd_id = this.dataHD.tthd_id
       },
       frmTraCuuHDTB_TTHD_Load: async function(){
           await this.HT_DichVuVT_Combobox();
           await this.HT_Loai_HD_Combobox();
           if (this.loaihd_id != 0){
               this.modelCbo.cboLoaiHD = this.loaihd_id
               this.chkLoaiHD = true
           }
           else {
               this.chkLoaiHD = false
           }

           if (this.dichvuvt_id != 0){
                this.modelCbo.cboDVVT = this.dichvuvt_id
               this.chkDVVT = true
           }
           else {
                this.chkDVVT = false
                this.modelCbo.cboDVVT = this.modelCbo.vcboDVVT[0].dichvuvt_id
           }
           await this.HienThiDanhSach();
       },
        changeCBO: async function(){
            if  (this.chkLoaiHD && this.modelCbo.vcboLoaiHD.length > 0 )
                await this.timkiem()
        },
        changeCBO2: async function(){
            if  (this.chkDVVT && this.modelCbo.vcboDVVT.length > 0 )
                await this.timkiem()
        },
      lay_dk_bang_dichvuvt: async function () {
            try {
                let rs = await this.$root.context.get("/web-ccdv/hoanthien-hs-tachnhap-tbao/lay_dk_bang_dichvuvt");
                return rs.data;
            } catch (error) {
                return "";
            } finally {
            }
        },
      lay_dk_bang_doituong: async function () {
            try {
                let rs = await this.$root.context.get("/web-ccdv/hoanthien-hs-tachnhap-tbao/lay_dk_bang_doituong");
                return rs.data;
            } catch (error) {
                return "";
            } finally {
            }
        },
       // HienThiDanhSach: async function(){
       //   try {
       //     if (this.modelCbo.vcboLoaiHD.length == 0) return;
       //
       //     let loaihd_id = 0;
       //     if (this.chkLoaiHD)
       //       loaihd_id = this.modelCbo.cboLoaiHD
       //
       //     let dichvuvt_id = 0;
       //     if (this.chkDVVT)
       //       dichvuvt_id = this.modelCbo.cboDVVT
       //
       //     //let dk_dvvt = fcm.Lay_DK_Cho_Bang("DICHVU_VT", tt_nd.nguoidung_id);
       //     //let tag = await this.$root.context.post('/web-ccdv/hoanthienhschuyenquyen/lay-dangky-cho-bang', {tenbang: 'DICHVU_VT' })
       //     let tag = await this.lay_dk_bang("DICHVU_VT");
       //     function onlyUnique(value, index, self) {
       //       return self.indexOf(value) === index;
       //     }
       //     if (tag) {
       //       tag = tag.split(",")
       //       tag = tag.map(i => parseInt((i+'').trim()))
       //       tag = tag.filter(onlyUnique)
       //       tag.sort(function(a, b) {
       //         return a - b;
       //       })
       //     }
       //     let dk_dvvt = tag != null ? tag.toString() : '';
       //     //code cũ          //dtList = tchopdong.LAY_DS_HDTB_TTHD(loaihd_id, dichvuvt_id, tt_nd.donvi_dl_id, tthd_id, dk_dvvt).Tables[0];
       //     //Hiếu - 20.10.2010: Chỉ cho hiển thị tất cả đối với loại hợp đồng chuyển quyền
       //     //Chỉ user nào lập hợp đồng mới được hoàn thiện hồ sơ chuyển quyền
       //     if (loaihd_id == Enum.LoaiHopDong.CHUYEN_QUYEN)
       //       await this.updateDsHopDongCount(loaihd_id, dichvuvt_id, 0, this.tthd_id, dk_dvvt)
       //     else
       //       await this.updateDsHopDongCount(loaihd_id, dichvuvt_id, this.$auth.getDonViID(), this.tthd_id, dk_dvvt)
       //     //Đối với
       //
       //     // if (dichvuvt_id == Enum.DichVuVienThong.CO_DINH)
       //     //     dtgDanhSach.Columns["ne"].Visible = true;
       //     // else
       //     //     dtgDanhSach.Columns["ne"].Visible = false;
       //
       //     // if (dichvuvt_id == DichVuVienThong.ADSL)
       //     //     dtgDanhSach.Columns["port"].Visible = true;
       //     // else
       //     //     dtgDanhSach.Columns["port"].Visible = false;
       //
       //     if (this.dtList.length > 0)
       //       this.isDisableBtn = false;
       //     else
       //       this.isDisableBtn = true;
       //   }catch (e) {
       //
       //   }
       // },
      HienThiDanhSach: async function () {
         try {
           if (this.modelCbo.vcboLoaiHD.length == 0) return;

           let loaihd_id = 0;
           if (this.chkLoaiHD)
             loaihd_id = this.modelCbo.cboLoaiHD

           let dichvuvt_id = 0;
           if (this.chkDVVT)
             dichvuvt_id = this.modelCbo.cboDVVT

           //let dk_dvvt = fcm.Lay_DK_Cho_Bang("DICHVU_VT", tt_nd.nguoidung_id);
           //let tag = await this.$root.context.post('/web-ccdv/hoanthienhschuyenquyen/lay-dangky-cho-bang', {tenbang: 'DICHVU_VT' })
           let tag = await this.lay_dk_bang_dichvuvt();

           function onlyUnique(value, index, self) {
             return self.indexOf(value) === index;
           }

           if (tag) {
             tag = tag.split(",")
             tag = tag.map(i => parseInt((i + '').trim()))
             tag = tag.filter(onlyUnique)
             tag.sort(function (a, b) {
               return a - b;
             })
           }
           let dk_dvvt = tag != null ? tag.toString() : '';
           //code cũ          //dtList = tchopdong.LAY_DS_HDTB_TTHD(loaihd_id, dichvuvt_id, tt_nd.donvi_dl_id, tthd_id, dk_dvvt).Tables[0];
           //Hiếu - 20.10.2010: Chỉ cho hiển thị tất cả đối với loại hợp đồng chuyển quyền
           //Chỉ user nào lập hợp đồng mới được hoàn thiện hồ sơ chuyển quyền
           if (loaihd_id == Enum.LoaiHopDong.CHUYEN_QUYEN)
             this.dtList = await this.LAY_DS_HDTB_TTHD(loaihd_id, dichvuvt_id, 0, this.tthd_id, dk_dvvt)
           else
             this.dtList = await this.LAY_DS_HDTB_TTHD(loaihd_id, dichvuvt_id, this.$auth.getDonViID(), this.tthd_id, dk_dvvt)
           //Đối với

           // if (dichvuvt_id == Enum.DichVuVienThong.CO_DINH)
           //     dtgDanhSach.Columns["ne"].Visible = true;
           // else
           //     dtgDanhSach.Columns["ne"].Visible = false;

           // if (dichvuvt_id == DichVuVienThong.ADSL)
           //     dtgDanhSach.Columns["port"].Visible = true;
           // else
           //     dtgDanhSach.Columns["port"].Visible = false;

           if (this.dtList.length > 0)
             this.isDisableBtn = false;
           else
             this.isDisableBtn = true;
         }catch (e) {
           console.log(e)
         }
        },
       LAY_DS_HDTB_TTHD: async function(loaihd_id, dichvuvt_id, donvi_dl_id, tthd_id, dk_dvvt){
         try {
           this.loading(true)
           // let data = {
           //   P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
           //   IN_LOAIHD_ID: loaihd_id,
           //   IN_DICHVUVT_ID: dichvuvt_id,
           //   IN_DONVI_ID: donvi_dl_id,
           //   IN_TTHD_ID: tthd_id,
           //   IN_DK_DVVT: dk_dvvt,
           //   PROCEDURE_NAME: 'CABMAN.PKG_GIANDO_CAP.LAY_DS_HDTB_TTHD'
           // }
           // let res = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
           let res = await this.$root.context.post('web-ccdv/hoanthienhschuyenquyen/lay_ds_hdtb_tthd', {
             loaihd_id: loaihd_id,
             dichvuvt_id: dichvuvt_id,
             donvi_id: donvi_dl_id,
             tthd_id: tthd_id,
             dk_dvvt: dk_dvvt
           })
           return res.data;
         }catch (e) {

         }finally {
           this.loading(false)
         }
       },
      // async LAY_DS_HDTB_TTHD_PAGING(loaihd_id, dichvuvt_id, donvi_dl_id, tthd_id, dk_dvvt, pageIndex, pageSize) {
      //   try {
      //     this.loading(true)
      //     await this.$root.context.post(
      //       "/web-ccdv/hoanthienhschuyenquyen/lay-ds-hdtb-tthd-paging",
      //       {
      //         loaihd_id: loaihd_id,
      //         dichvuvt_id: dichvuvt_id,
      //         donvi_id: donvi_dl_id,
      //         tthd_id: tthd_id,
      //         dk_dvvt: dk_dvvt,
      //         page_num: pageIndex,
      //         page_size: pageSize,
      //       }).then(res => {
      //       if (this.totalRowDsHopDong != res.data.allItems) {
      //         this.totalRowDsHopDong = res.data.allItems
      //         return
      //       }
      //       if (res.data.pageItems.length > 0) {
      //         this.dtList = res.data.pageItems
      //       } else {
      //         this.dtList = []
      //         this.$root.toastError("Không có dữ liệu, vui lòng thử lại.")
      //       }
      //     })
      //   } catch (e) {
      //     console.log(e)
      //     this.$root.toastError("Có lỗi xảy ra khi tải danh sách hợp đồng")
      //   } finally {
      //     this.loading(false)
      //   }
      // },
      // async updateDsHopDongCount(loaihd_id, dichvuvt_id, donvi_dl_id, tthd_id, dk_dvvt) {
      //   try {
      //     this.loading(true)
      //     await this.$root.context.post(
      //       "/web-ccdv/hoanthienhschuyenquyen/lay-ds-hdtb-tthd-paging",
      //       {
      //         loaihd_id: loaihd_id,
      //         dichvuvt_id: dichvuvt_id,
      //         donvi_id: donvi_dl_id,
      //         tthd_id: tthd_id,
      //         dk_dvvt: dk_dvvt,
      //         page_num: 0,
      //         page_size: 1,
      //       }).then(res => {
      //       if (this.totalRowDsHopDong == res.data.allItems) this.$refs.gridDsHD.reloadCurrentPage()
      //       this.totalRowDsHopDong = res.data.allItems > 0 ? res.data.allItems : 0;
      //       if (this.totalRowDsHopDong == 0) {
      //         console.log(123123)
      //         this.loading(false)
      //         this.dtList = []
      //         this.$root.toastError("Không có dữ liệu, vui lòng thử lại.")
      //       }
      //     })
      //   } catch (e) {
      //     this.loading(false)
      //   } finally {
      //   }
      // },
      // dsHopDong_PageChanged: async function (args) {
      //   this.paging_dsHopDong.pSize = args.pageSize;
      //   this.paging_dsHopDong.pNo = args.pageIndex;
      //   await this.HienThiDanhSach_PageChange()
      // },
       onHiddenModal() {
            this.isShown = false
            this.show = false
            this.key++
        },
        HT_DichVuVT_Combobox: async function (){
            //let tag = await this.$root.context.post('/web-ccdv/hoanthienhschuyenquyen/lay-dangky-cho-bang', {tenbang: 'DICHVU_VT' })
            let tag = await this.lay_dk_bang_dichvuvt();
            let input = tag != null ? tag : '';
            await this.$root.context.post('/web-ccdv/hoanthienhschuyenquyen/lay-ds-dvvt', {thamso: input }).then(
                res=> {
                    this.modelCbo.vcboDVVT = res.data;
                    //this.modelCbo.cboDichVuVT = res.data[0].dichvuvt_id
                }
            )
        },
        HT_Loai_HD_Combobox: async function(){
            let res = await this.$root.context.get('web-ccdv/hoanthienhschuyenquyen/lay_ds_loai_hd')
            this.modelCbo.vcboLoaiHD = res.data
        },
        timkiem: async function(){
         try {
           // this.loading(true)
           await this.HienThiDanhSach()
         }catch (e) {

         }finally {
           // this.loading(false)
         }
        },
        chapnhan: async function(){
         if (this.isDisableBtn) return
            this.$emit('chon', this.txtMaGD)
            this.$bvModal.hide(this.id)
        },
        onDoubleClickRow: async function(){
            await this.chapnhan();
        },
        selectedItemsDsHoSo: async function(data){
         if (data) {
           this.selectedRow = data
           this.txtAcc = data.MA_TB
           this.txtMaGD = data.MA_GD
         }
        },
        async onShownModal() {
            this.isShown = true
            this.show = true
            try {
                this.$root.showLoading(true)
                this.onLoad();
                await this.frmTraCuuHDTB_TTHD_Load();
            } catch (error) {
                this.$root.toastError(error.message)
            }
            finally{
                this.$root.showLoading(false)
            }
        },
    },
    // mounted: async function() {
    //     try {
    //         this.loading(true);
    //
    //     } catch (error) {
    //
    //     } finally {
    //         this.loading(false);
    //     }
    // },
    watch: {
      chkLoaiHD: async function (val) {
        await this.timkiem()
      },
      chkDVVT: async function (val) {
        await this.timkiem()
      }
    }
}
</script>

<style scoped>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>

<template src="./TraCuuKhoaMoMay.html"></template>
<script>
import breadcrumb from '@/components/breadcrumb'
import TraCuuAPI from '../api/TraCuuKhoaMoMay'
import CommonAPI from '../api/Commons'
import { mapActions, mapGetters } from 'vuex'
import PopupDanhSachThueBao from './component/PopupDanhSachThueBao'
import XuatFile from "../../CSKH/utils/XuatFile.vue"

export default {
    components: { breadcrumb, PopupDanhSachThueBao, appModalXuatDuLieu: XuatFile },
    name: 'TraCuuKhoaMoMay',
    async created () {                
        if (this.danhSachDichVuVienThongDKGetter == null || this.danhSachDichVuVienThongDKGetter.length === 0)
            await this.getDanhSachDichVuVienThongDangKy();        
        if (this.danhSachDichVuVienThongDKGetter.length > 0) {
            this.danhSachDichVuVienThongDKGetter.forEach(item => {
                this.dsDichVu.push({ id: item.DICHVUVT_ID, text: item.TEN_DVVT })
            });
            this.dichVuSelected = this.dsDichVu[0].id;
        }        
        
        if (this.dichVuSelected !== null)
            await this.getLoaiHinhTB(this.dichVuSelected);
    },
    mounted () {        
        this.$refs.maTT.focus();
    },
    computed: {
        ...mapGetters('qltnCommon', ['danhSachDichVuVienThongDKGetter'])
    },
    data () {
        return {
            maTT: '',
            soMayACC: '',
            dichVuSelected: null,
            dichVuChecked: false,
            dsDichVu: [],
            dsLoaiHinh: [],
            loaiHinhSelected: null,
            loaiHinhChecked: false,
            dsDanhBa: [],
            thueBaoId: 0,
            lsKhoaMoMay: [],
            dsExport: {
                items: [],
                fields: [
                    {fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowFiltering: true, allowSorting: false, width: 120, cssClass: 'text-left'},
                    {fieldName: 'MA_TT', headerText: 'Mã TT', allowFiltering: true, allowSorting: false, width: 120},
                    {fieldName: 'DICHVU_VT', headerText: 'Dịch vụ', allowFiltering: true, allowSorting: false, width: 120},
                    {fieldName: 'TEN_TT', headerText: 'Tên thanh toán', allowFiltering: true, allowSorting: false, width: 150},
                    {fieldName: 'NGAYKHOA', headerText: 'Ngày khóa', allowFiltering: true, allowSorting: false, width: 120, cssClass: 'text-right'},
                    {fieldName: 'CHIEUKHOA', headerText: 'Chiều khóa', allowFiltering: true, allowSorting: false, width: 120},
                    {fieldName: 'KIEUKHOA', headerText: 'Kiểu khóa', allowFiltering: true, allowSorting: false, width: 120},
                    {fieldName: 'CACHKHOA', headerText: 'Hình thức khóa', allowFiltering: true, allowSorting: false, width: 150},
                    {fieldName: 'LYDOKHOA', headerText: 'Lý do khóa', allowFiltering: true, allowSorting: false, width: 150},
                    {fieldName: 'NOIDUNG', headerText: 'Nội dung', allowFiltering: true, allowSorting: false, width: 150},
                    {fieldName: 'MAYKHOA', headerText: 'Máy khóa', allowFiltering: true, allowSorting: false, width: 120},
                    {fieldName: 'NGUOIKHOA', headerText: 'Người khóa', allowFiltering: true, allowSorting: false, width: 120},
                    {fieldName: 'NGUOIMO', headerText: 'Người mở', allowFiltering: true, allowSorting: false, width: 120},
                    {fieldName: 'NGAYMO', headerText: 'Ngày mở', allowFiltering: true, allowSorting: false, width: 120, cssClass: 'text-right'},
                    {fieldName: 'CACHMO', headerText: 'Hình thức mở', allowFiltering: true, allowSorting: false, width: 150},
                    {fieldName: 'CHIEUMO', headerText: 'Chiều mở', allowFiltering: true, allowSorting: false, width: 120},
                    {fieldName: 'LYDOMO', headerText: 'Lý do mở', allowFiltering: true, allowSorting: false, width: 120},
                    {fieldName: 'LANMO', headerText: 'Lần mở', allowFiltering: true, allowSorting: false, width: 100},
                    {fieldName: 'LOAI', headerText: 'Loại', allowFiltering: true, allowSorting: false, width: 100},
                    {fieldName: 'LYDO_KC', headerText: 'Lý do KC', allowFiltering: true, allowSorting: false, width: 120}
                ]
            },
            tabs: [],
            tabCounter: 0,            
            currentTabIndex: 0,
            //0: TRACUU button; 1: MA_TT keypress; 2: SOMAY/ACC keypress
            kieuTK: 0
        }        
    },
    methods: {
        ...mapActions('qltnCommon', ['getDanhSachDichVuVienThongDangKy']),        

        async onSearch(type) {
            this.maTT = this.maTT.trim();
            this.soMayACC = this.soMayACC.trim();
            this.kieuTK = type;
            
            //MA_TT keypress
            if (type === 1) {
                if (this.maTT !== '')
                    await this.hienThiDanhSach(this.maTT, '');
            } else if (type === 2) {
            //SOMAY/ACC keypress
                if (this.soMayACC !== '')
                    await this.hienThiDanhSach('', this.soMayACC);
            } else {
            //TRACUU button
                if (this.maTT === '' && this.soMayACC === '' && !this.dichVuChecked && !this.loaiHinhChecked) {
                    this.$toast.error('Bạn chưa nhập mã thanh toán hoặc mã thuê bao!');
                    this.$refs.maTT.focus();
                } else 
                    await this.hienThiDanhSach(this.maTT, this.soMayACC);
            }
        },
        
        async hienThiDanhSach(maTT, maTB) {
            if (maTT !== '' || maTB !== '') {
                if (maTT !== '')
                    await this.getDanhBaTheoMaThanhToan();
                else
                    await this.getDanhBaTheoMaThueBao();
                
                if (this.dsDanhBa.length === 1) {
                    this.thueBaoId = this.dsDanhBa[0].THUEBAO_ID;
                    this.maTT = this.dsDanhBa[0].MA_TT;
                } else if (this.dsDanhBa.length > 1) {
                    this.$refs.dlgDanhsachthuebao.openDialog();
                    return;
                } else
                    return this.$toast.error('Không có dữ liệu, vui lòng thử lại.');
            }
            this.newTab();
            await this.getLichSuKhoaMoMay();
            this.currentTabIndex = this.tabs.length - 1;
        },

        closeTab(x) {
            for (let i = 0; i < this.tabs.length; i++) {
                if (this.tabs[i] === x) {
                    this.tabs.splice(i, 1);
                    this.lsKhoaMoMay.splice(i, 1);
                }
            }
        },

        newTab() {
            this.tabs.push(this.tabCounter++);            
        },        

        async selectThueBao(index) {            
            if (index >= 0) {
                this.thueBaoId = this.dsDanhBa[index].THUEBAO_ID;
                this.maTT = this.dsDanhBa[index].MA_TT;                
                this.newTab();
                await this.getLichSuKhoaMoMay();
                this.currentTabIndex = this.tabs.length - 1;
            }
        },

        changeTab(value) {            
            this.currentTabIndex = value;            
        },

        onExcelExport() {
            if (this.tabs.length === 0 || this.lsKhoaMoMay[this.currentTabIndex].length === 0)
                this.$toast.error("Không có dữ liệu để xuất excel!");
            else {
                this.dsExport.items = this.lsKhoaMoMay[this.currentTabIndex];
                this.$bvModal.show("modal-xuat-file");
            }            
        },

        async getLoaiHinhTB(id) {
            try {
                this.loading(true);
                this.dsLoaiHinh = [];
                let response = await CommonAPI.getDanhSachLoaiHinhThueBao(this.axios, id);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {                    
                    if (response.data.data.length > 0) {
                        response.data.data.forEach(item => {
                            this.dsLoaiHinh.push({ id: item.LOAITB_ID, text: item.TEN_LOAIHINH });
                        });
                        this.loaiHinhSelected = this.dsLoaiHinh[0].id;
                    }
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDanhBaTheoMaThanhToan() {
            try{
                this.loading(true); 
                let apiParams = {                    
                    maTT: this.maTT                
                };
                let response = await TraCuuAPI.getDanhBaTheoMaThanhToan(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {          
                    this.dsDanhBa = response.data.data;              
                }
            }
            catch(error) {
                console.log(error);
            }
            finally
            {
                this.loading(false);
            }
        },

        async getDanhBaTheoMaThueBao() {
            try {
                this.loading(true); 
                let apiParams = {                    
                    maTB: this.soMayACC
                };
                let response = await TraCuuAPI.getDanhBaTheoMaThueBao(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {          
                    this.dsDanhBa = response.data.data;              
                }
            }
            catch(error) {
                console.log(error);
            }
            finally
            {
                this.loading(false);
            }
        },

        async getLichSuKhoaMoMay() {
            try {
                this.loading(true); 
                let apiParams = {
                    dichVuVTId: this.dichVuChecked ? this.dichVuSelected : 0,
                    loaiTBId: this.loaiHinhChecked ? this.loaiHinhSelected : 0,
                    maTT: this.kieuTK !== 2 ? this.maTT : '',
                    thueBaoId: this.kieuTK !== 1 ? this.thueBaoId : 0,
                    kieu: 2        //Lay danh sach khoa may
                };
                let response = await TraCuuAPI.getLichSuKhoaMoMay(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {          
                    this.lsKhoaMoMay.push(response.data.data);
                }
            }
            catch(error) {
                console.log(error);
            }
            finally
            {
                this.loading(false);
            }
        }
    },
    watch: {
        dichVuSelected: {
            handler: async function (value) {                
                await this.getLoaiHinhTB(value);
            }
        }
    }
}
</script>
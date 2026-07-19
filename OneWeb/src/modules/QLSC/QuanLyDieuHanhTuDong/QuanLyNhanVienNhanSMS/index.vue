<template src='./index.html'>
</template>

<script>
import { CheckBoxSelection } from "@syncfusion/ej2-vue-dropdowns"

export default {
    data() {
        return {
            dsLoaiTn: [],
            dsCapTn: [],
            dsDonvi: [],
            dsNVDaGan: [],
            dsNVChuaGan: [],
            dsSelectedDaGan: [],
            dsSelectedChuaGan: [],
            capTnId: null,
            loaiTnId: null,
            donviId: [],
            nhanVienId: null,
            popHeight: '350px',
            multiMode: 'CheckBox',
            totalRowDsNvDaGan: 0,
            totalRowDsNvChuaGan: 0,
            paging_nvdagan:{
                pNo: 0,
                pSize: 10
            },
            paging_nvchuagan:{
                pNo: 0,
                pSize: 10
            }
        }
    },
    provide: {
        multiselect: [CheckBoxSelection]
    },
    methods: {
        layDsNVDaGan: async function(pageNo=1, pageSize=10) {
            try {
                this.dsNVDaGan = []
                var rs = await this.$root.context.post('/web-suco/danhmuc/dieuhanhtudong/ds-nv-dagan', {
                    tinnhan_id: this.tinnhan_id,
                    // page_num: pageNo,
                    // page_size: pageSize
                });
                this.dsNVDaGan = rs.data;
                let idx = this.dsNVDaGan.findIndex(v => v.NHANVIEN_ID == this.nhanVienId);
                if(idx >= 0) {
                    this.$refs.gridNvdg.setCurrentSelectedRow(idx);
                }
                else {
                    this.$refs.gridNvdg.setCurrentSelectedRow(0);
                }

                // if(this.dsNVDaGan.length == 0 && rs.data.paging[0].RECORD_COUNT < 0) {
                //     this.totalRowDsNvDaGan = 0;
                // }
                // else {
                //     this.totalRowDsNvDaGan = rs.data.paging[0].RECORD_COUNT;
                // }
            } catch (error) {
                
            }
        },
        layDsNVChuaGan: async function(pageNo=1, pageSize=10) {
            try {
                var rs = await this.$root.context.post('/web-suco/danhmuc/dieuhanhtudong/ds-nv-chuagan', {
                    tinnhan_id: this.tinnhan_id,
                    // page_num: pageNo,
                    // page_size: pageSize
                });
                this.dsNVChuaGan = rs.data;
                // if(this.dsNVChuaGan.length == 0 && rs.data.paging[0].RECORD_COUNT) {
                //     this.totalRowDsNvChuaGan = 0;
                // }
                // else {
                //     this.totalRowDsNvChuaGan = rs.data.paging[0].RECORD_COUNT;
                // }
            } catch (error) {
                
            }
        },
        gridDSNVDG_rowSelected: function(item) {
            let data = item.data;
            if(data) {
                if(Array.isArray(data)) {
                    data = data.length > 0 ?  data[0] : {};
                }
                this.nhanVienId = data.NHANVIEN_ID;
                this.donviId = data.DVDL_ID ? data.DVDL_ID.split(',').map(val => parseInt(val)) : [];
            }
            else {
                this.nhanVienId = null;
                this.donviId = [];
            }
        },
        dsNvDaGan_PageChanged: async function(args) {
            try {
                this.loading(true);
                this.paging_nvdagan = {
                    pNo: args.pageIndex +1,
                    pSize: args.pageSize 
                }
                await this.layDsNVDaGan(this.paging_nvdagan.pNo, this.paging_nvdagan.pSize);
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        dsNvChuaGan_PageChanged: async function(args) {
            try {
                this.loading(true);
                this.paging_nvchuagan = {
                    pNo: args.pageIndex +1,
                    pSize: args.pageSize 
                }
                await this.layDsNVChuaGan();
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        gridDSNVCG_selectedRowChanged: function(data) {
            
        },
        selected_NVDAGAN: function(dataItem) {
            this.dsSelectedDaGan = dataItem;
        },
        selected_NVCHUAGAN: function(dataItem) {
            this.dsSelectedChuaGan = dataItem;
        },
        addToDsChuaGan: function() {
            var arr = [];
            this.dsSelectedDaGan.map(val => {
                var idx = this.dsNVDaGan.findIndex(v => v.NHANVIEN_ID == val);
                if(idx >= 0) {
                    arr.push(this.dsNVDaGan[idx]);
                    this.dsNVDaGan = this.dsNVDaGan.filter(v => v.NHANVIEN_ID != val);
                }
            })
            this.dsNVChuaGan = this.dsNVChuaGan.concat(arr);
            this.dsSelectedDaGan = [];
        },
        addAllToDsChuaGan: function() {
            this.dsNVChuaGan = this.dsNVChuaGan.concat(this.dsNVDaGan);
            this.dsNVDaGan = [];
            this.dsSelectedDaGan = [];
        },
        addToDsDaGan: function() {
            var arr = [];
            this.dsSelectedChuaGan.map(val => {
                var idx = this.dsNVChuaGan.findIndex(v => v.NHANVIEN_ID == val);
                if(idx >= 0) {
                    arr.push(this.dsNVChuaGan[idx]);
                    this.dsNVChuaGan = this.dsNVChuaGan.filter(v => v.NHANVIEN_ID != val);
                }
            })
            this.dsNVDaGan = this.dsNVDaGan.concat(arr);
            this.dsSelectedChuaGan = [];
        },
        addAllToDsDaGan: function() {
            this.dsNVDaGan = this.dsNVDaGan.concat(this.dsNVChuaGan);
            this.dsNVChuaGan = [];
            this.dsSelectedChuaGan = [];
        },
        lamMoi: async function() {
            if(this.capTnId) {
                try {
                    this.loading(true);
                    await this.layDsNVDaGan();
                    await this.layDsNVChuaGan();
                } catch (error) {
                    this.$toast.error('Xảy ra lỗi');
                } finally {
                    this.loading(false);
                }
            }
        },
        ghiLai: async function() {
            try {
                this.loading(true);
                var dsnv = this.dsNVDaGan.map(val => val.NHANVIEN_ID).join(',');
                var rs = await this.$root.context.post('/web-suco/danhmuc/dieuhanhtudong/capnhat-nv-sms', {
                    tinnhan_id: this.tinnhan_id,
                    dsnv_id: dsnv
                });
                if(rs.data.KETQUA) {
                    this.$toast.success('Cập nhật thành công');
                    this.nhanVienId = null;
                    this.donviId = [];
                    await this.layDsNVDaGan();
                    await this.layDsNVChuaGan();
                }
                else {
                    this.$toast.error('Cập nhật thất bại');
                }
            } catch (error) {
                this.$toast.error('Cập nhật thất bại');
            } finally {
                this.loading(false);
            }
        },
        gan: async function() {
            if(!this.nhanVienId) {
                this.$root.toastError("Chưa chọn nhân viên bên danh sách đã gán");
            }
            else if(this.donviId.length > 0) {
                try {
                    this.loading(true);
                    var rs = await this.$root.context.post('/web-suco/danhmuc/dieuhanhtudong/capnhat-donvi-sms', {
                        tinnhan_id: this.tinnhan_id,
                        nhanvien_id: this.nhanVienId,
                        donvi_id: this.donviId.join(',')
                    });
                    if(rs.data.KETQUA) {
                        await this.layDsNVDaGan();
                        this.$toast.success('Cập nhật thành công');
                    }
                    else {
                        this.$toast.error('Cập nhật thất bại');
                    }
                } catch (error) {
                    this.$toast.error('Cập nhật thất bại');
                } finally {
                    this.loading(false);
                }
            }
            else {
                this.$root.toastError("Chưa chọn đơn vị để gán");
            }
        },
        clickTab: function(data) {
            this.$emit('clickTab', data);
        }
    },
    async mounted() {
        try {
            this.loading(true);
            var rsLoaiTn = await this.$root.context.get('/web-suco/danhmuc/dieuhanhtudong/ds-loai-tn');
            this.dsLoaiTn = rsLoaiTn.data;
            this.loaiTnId = this.dsLoaiTn.length > 0 ? this.dsLoaiTn[0].LOAITN_ID : null;

            var rsDonvi = await this.$root.context.get('/web-suco/danhmuc/dieuhanhtudong/ds-donvi');
            this.dsDonvi = rsDonvi.data;

        } catch (error) {
            this.$toast.error('Xảy ra lỗi');
        } finally {
            this.loading(false);
        }
    },
    watch: {
        loaiTnId: async function(newVal) {
            if(newVal) {
                try {
                    this.loading(true);
                    var rs = await this.$root.context.post('/web-suco/danhmuc/dieuhanhtudong/ds-cap-tn', {loaitn_id: newVal});
                    this.dsCapTn = rs.data;
                    this.capTnId = this.dsCapTn.length > 0 ? this.dsCapTn[0].CAPTN_ID : null;
                } catch (error) {
                    this.$toast.error('Xảy ra lỗi');
                } finally {
                    this.loading(false);
                }
            }
        },
        capTnId: async function(newVal) {
            if(newVal) {
                try {
                    this.loading(true);
                    await this.layDsNVDaGan();
                    await this.layDsNVChuaGan();
                } catch (error) {
                    this.$toast.error('Xảy ra lỗi');
                } finally {
                    this.loading(false);
                }
            }
        }
    },
    computed: {
        tinnhan_id: function() {
            var arr = this.dsCapTn.filter(val => val.CAPTN_ID == this.capTnId);
            if(arr.length > 0) {
                return arr[0].TINNHAN_ID;
            }
            return null;
        }
    }
}
</script>

<style scoped>
.foot {
    text-indent: 1.2em;
    display: block;
    font-size: 15px;
    line-height: 40px;
    border-top: 1px solid #e0e0e0;
}
</style>

<template src='./index.html'>
</template>

<script>
export default {
    props: {
        modalId: String
    },
    data() {
        return {
            brasId: null,
            dslamId: null,
            dsBras: [],
            dsDslam: [],
            dsDaGan: [],
            dsChuaGan: [],
            dsSelectedDaGan: [],
            dsSelectedChuaGan: []
        }
    },
    methods: {
        Init: function() {
            this.brasId = null;
            this.dslamId = null;
            this.dsBras = [];
            this.dsDslam = [];
            this.dsDaGan = [];
            this.dsChuaGan = [];
            this.dsSelectedDaGan = [];
            this.dsSelectedChuaGan = [];
        },
        showModal: async function() {
            // this.$bvModal.show('modal-dslam-rack');
            try {
                this.Init();
                this.loading(true);
                var rsBras = await this.$root.context.get('/web-ecms/DongBoPortThueBao/danh-sach-bras');
                this.dsBras = rsBras.data;
                this.brasId = this.dsBras.length > 0 ? this.dsBras[0].BRAS_ID : null;
                if(this.brasId) {
                    var rsDslam = await this.$root.context.get(`/web-ecms/DongBoPortThueBao/danh-sach-dslam-theo-bras?brasId=${this.brasId}`);
                    this.dsDslam = rsDslam.data;
                    this.dslamId = this.dsDslam.length > 0 ? this.dsDslam[0].DSLAM_ID : null;
                }
            } catch (error) {
                this.$toast.error('Xảy ra lỗi');
            } finally {
                this.loading(false);
            }
        },
        gridDsDaGan_selectedRowChanged: function() {

        },
        gridDsChuaGan_selectedRowChanged: function() {
            
        },
        selectedDsDaGan: function(data) {
            this.dsSelectedDaGan = data;
        },
        selectedDsChuaGan: function(data) {
            this.dsSelectedChuaGan = data;
        },
        addToDsChuaGan: function() {
            var arr = [];
            if(this.dsSelectedDaGan.length == 0) {
                this.$root.toastWarning('Bạn phải chọn bản ghi cần chuyển');
            }
            else {
                this.dsSelectedDaGan.map(val => {
                    var idx = this.dsDaGan.findIndex(v => v.RACK_ID == val);
                    if(idx >= 0) {
                        arr.push(this.dsDaGan[idx]);
                        this.dsDaGan = this.dsDaGan.filter(v => v.RACK_ID != val);
                    }
                })
                this.dsChuaGan = this.dsChuaGan.concat(arr);
                this.dsSelectedDaGan = [];
            }
        },
        addAllToDsChuaGan: function() {
            this.dsChuaGan = this.dsChuaGan.concat(this.dsDaGan);
            this.dsDaGan = [];
            this.dsSelectedDaGan = [];
        },
        addToDsDaGan: function() {
            var arr = [];
            if(this.dsSelectedChuaGan.length == 0) {
                this.$root.toastWarning('Bạn phải chọn bản ghi cần chuyển');
            }
            else {
                this.dsSelectedChuaGan.map(val => {
                    var idx = this.dsChuaGan.findIndex(v => v.RACK_ID == val);
                    if(idx >= 0) {
                        arr.push(this.dsChuaGan[idx]);
                        this.dsChuaGan = this.dsChuaGan.filter(v => v.RACK_ID != val);
                    }
                })
                this.dsDaGan = this.dsDaGan.concat(arr);
                this.dsSelectedChuaGan = [];
            }
        },
        addAllToDsDaGan: function() {
            this.dsDaGan = this.dsDaGan.concat(this.dsChuaGan);
            this.dsChuaGan = [];
            this.dsSelectedChuaGan = [];
        },
        ghiLai: async function() {
            var arr = this.dsDaGan.map(val => val.RACK_ID);
            if(this.dslamId) {
                if(arr.length > 0) {
                    try {
                        await this.$root.context.post('/web-ecms/quanlythietbi/capNhatRacksDslam', {
                            dslam_id: this.dslamId,
                            racks_id: arr
                        });
                        this.$toast.success('Cập nhật thành công');
                    } catch (error) {
                        console.log(error.response.data);
                        this.$toast.error('Cập nhật thất bại');
                    }
                }
                else {
                    this.$root.toastError('Danh sách Rack không được để trống');
                }
            }
            else {
                this.$root.toastError('Đầu vào DSLAM đang null')
            }
        },
        closeModal: function() {
            this.$bvModal.hide('modal-dslam-rack');
        }
    },
    watch: {
        brasId: async function(newVal) {
            try {
                this.loading(true);
                if(newVal) {
                    var rsDslam = await this.$root.context.get(`/web-ecms/DongBoPortThueBao/danh-sach-dslam-theo-bras?brasId=${newVal}`);
                    this.dsDslam = rsDslam.data;
                    this.dslamId = this.dsDslam.length > 0 ? this.dsDslam[0].DSLAM_ID : null;
                }
            } catch (error) {
                this.$toast.error('Xảy ra lỗi');
            } finally {
                this.loading(false);
            }
        },
        dslamId: async function(newVal) {
            try {
                this.loading(true);
                if(newVal) {
                    var rsDsDaGan = await this.$root.context.post('/web-ecms/quanlythietbi/getDsRackDaGan', {id: newVal});
                    this.dsDaGan = rsDsDaGan.data;
                    var rsDsChuaGan = await this.$root.context.post('/web-ecms/quanlythietbi/getDsRackChuaGan', {id: newVal});
                    this.dsChuaGan = rsDsChuaGan.data;
                }
                else {
                    this.dsChuaGan = [];
                    this.dsDaGan = [];
                }
            } catch (error) {
                this.$toast.error('Xảy ra lỗi');
            } finally {
                this.loading(false);
            }
        }
    }
}
</script>

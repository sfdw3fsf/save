<template src="./ModalDsBe.html"></template>
<script>
    export default {
        name: "ModalDsBe",
        props: ["modalId"],
        computed: {},
        created: async function () {
            //await this.getTTVT();
        },
        watch:{
            isTuyen: function(val){
                this.dsBe = []
                if(val)
                {
                    this.tieuDe = 'Danh sách bể thuộc tuyến: '+ this.tenTuyen != null ? this.tieuDe : ''
                    this.onChangeToQL()
                }
                else {
                    this.tieuDe = 'Danh sách bể chưa xác định tuyến'
                }
            },
            beId: function (val) {
                this.isDisableBtnChon = val != null ? false : true
            }
        },
        methods: {
            showModal: async function () {
                try{
                    this.$root.showLoading(true)
                    this.dataSelected.ttvt = null
                    this.dataSelected.toql = null
                    this.dataSelected.tuyen_id = null
                    this.dsBe = null
                    this.tieuDe = 'Danh sách bể thuộc tuyến: '
                    this.isTuyen = true
                    await this.getTTVT()
                }catch(er){}
                finally{
                    this.$root.showLoading(false)
                }
            },
            getTTVT: async function () {
                try {
                    let rs = await this.$root.context.get(
                        "/web-cabman/BanDoTuyenCong/ds-ttvt-theo-nhanvien"
                    );
                    this.dsTTVT = rs.data;
                } catch (error) {
                } finally {
                }
            },
            getToQL: async function () {
                try {
                    let rs = await this.$root.context.get(
                        "/web-cabman/BanDoTuyenCong/ds-to-kythuat-theo-nhanvien/" +
                        this.dataSelected.ttvt
                    );
                    this.dsToQL = rs.data;
                } catch (error) {
                } finally {
                }
            },
            getdsTuyenCong: async function () {
                try {
                    let rs = await this.$root.context.get(
                        "/web-cabman/BanDoTuyenCong/ds-tuyen-cong/" +
                        this.dataSelected.toql
                    );
                    this.dsTuyenCong = rs.data
                } catch (error) {
                } finally {
                }
            },
            onChangeTTVT: async function () {
                await this.getToQL();
            },
            onChangeToQL: async function () {
                await this.getdsTuyenCong();
            },
            grid_selectedRowChanged: async function(dataItem){
                this.beId= dataItem.BECAP_ID
                this.beSelected =  dataItem
            },
            timKiem:async function(){
                try {
                    let searchText = this.querys.trim()
                    if (searchText == null || searchText =='') {
                        return  false
                    }
                    this.$root.showLoading(true)
                    let data={
                        "tuyencb_id":null,
                        "to_quanly_id":null,
                        "chuoi_timkiem":searchText
                    }
                    let rs = await this.$root.context.post(
                        "/web-cabman/tracuu/ds-be-cap", data
                    );
                    this.dsBe = rs.data
                    this.tieuDe = 'Danh sách bể thỏa mãn điều kiện tìm kiếm'
                } catch (error) {
                } finally {
                    this.$root.showLoading(false)
                }
            },
            tracuuBe:async function(){
                try {
                    this.$root.showLoading(true)
                    let tuyenCongBeId = this.isTuyen == false ? null : this.dataSelected.tuyen_id
                    let data={
                        "tuyencb_id":tuyenCongBeId,
                        "to_quanly_id":this.dataSelected.toql,
                        "chuoi_timkiem":""
                    }
                    let rs = await this.$root.context.post(
                        "/web-cabman/tracuu/ds-be-cap", data
                    );
                    this.dsBe = rs.data
                } catch (error) {
                } finally {
                    this.$root.showLoading(false)
                }
            },
            onChangeTuyen:async function(id){
                this.dsBe = []
                this.tenTuyen = this.dsTuyenCong.find(x => x.TUYENCB_ID == id).TUYEN_CB
                this.tieuDe = this.isTuyen ? 'Danh sách bể thuộc tuyến: '+ this.tenTuyen : this.tieuDe
            },
            chonBe:async function(){
                this.$emit('xacnhan',this.beSelected);
                this.$bvModal.hide(this.modalId)
            },
            onKeyEnter: function(){
                this.timKiem()
            },
            huybo:async function(){
                this.beId = null
            }

        },
        data:function() {
            return {
                dsTTVT: [],
                dsToQL: [],
                dsTuyenCong:[],
                dataSelected:{
                    ttvt:0,
                    toql:null,
                    tuyen_id:null
                },
                dsBe:[],
                isTuyen:true,
                querys:"",
                beId:0,
                tieuDe:'Danh sách bể thuộc tuyến: ',
                tenTuyen:null,
                isDisableBtnChon:true,
                beSelected:null
            };
        }
    };
</script>
<style>
  /* div#popupDsCot___BV_modal_outer_{
    z-index: 10 !important;
    margin-top: 100px;
  } */
  .dashboard-header {
    z-index: 9 !important;
  }
  button.disabled {
    pointer-events: none;
    cursor: default;
    color: #6c757d !important;
  }
</style>

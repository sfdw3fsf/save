<template src="./ModalDsCot.html"></template>
<script>
    export default {
        name: "ModalDsCot",
        props: ["modalId"],
        computed: {},
        created: async function () {

        },
        watch:{
            isTuyen: async function (val) {
              this.dscot = []
              if (val) {
                this.tieuDe = 'Danh sách cột thuộc tuyến: ' + this.tenTuyen != null ? this.tieuDe : ''
                await this.onChangeToQL()
              } else {
                this.tieuDe = 'Danh sách cột chưa xác định tuyến'
                await this.tracuuCot()
              }
            },
            cot_id: function (val) {
                this.isDisableBtnChon = val != null ? false : true
            },
          dscot: function (val) {
              if (val.length == 0) {
                this.cot_id = null
                this.cotSelected = null
              }
          }
        },
        methods: {
            focusMyElement: async function () {
                try{
                    this.$root.showLoading(true)
                    this.dataSelected.ttvt = null
                    this.dataSelected.toql = null
                    this.dataSelected.tuyen_id = null
                    this.dscot = []
                    this.tieuDe = 'Danh sách cột thuộc tuyến: '
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
                        "/web-cabman/bando_tuyencot/dsttvt_theonv"
                    );
                    this.dsTTVT = rs.data;
                    if (rs.data.length > 0)
                      this.dataSelected.ttvt = rs.data[0].DONVI_ID
                    else this.dataSelected.ttvt = null
                    await this.onChangeTTVT()
                } catch (error) {
                } finally {
                }
            },
            getToQL: async function () {
                try {
                    let rs = await this.$root.context.get(
                        "/web-cabman/bando_tuyencot/ds_to_kythuat_theonv/" +
                        this.dataSelected.ttvt
                    );
                    this.dsToQL = rs.data;
                    if (rs.data.length > 0)
                      this.dataSelected.toql = rs.data[0].DONVI_ID
                    else this.dataSelected.toql = null
                    await this.onChangeToQL()
                } catch (error) {
                } finally {
                }
            },
            getdsTuyenCot: async function () {
                try {
                    let rs = await this.$root.context.get(
                        "/web-cabman/bando_tuyencot/ds_tuyencot/" +
                        this.dataSelected.toql
                    );
                    this.dsTuyenCot = rs.data
                  if (rs.data.length > 0)
                    this.dataSelected.tuyen_id = rs.data[0].TUYENCOT_ID
                  else this.dataSelected.tuyen_id = null
                  await this.onChangeTuyen(this.dataSelected.tuyen_id)
                } catch (error) {
                } finally {
                }
            },
            onChangeTTVT: async function () {
                await this.getToQL();
            },
            onChangeToQL: async function () {
                await this.getdsTuyenCot();
                if (this.isTuyen == false)
                  await this.tracuuCot()
            },
            grid_selectedRowChanged: async function(dataItem){
              if (dataItem) {
                this.cot_id= dataItem.COT_ID;
                this.cotSelected =  dataItem;
              }
            },
            onKeyEnter: function(){
                this.timKiem()
            },
            timKiem:async function(){
                try {
                    let searchText = this.querys.trim()
                    if (searchText == null || searchText =='') {
                        return  false
                    }
                    this.$root.showLoading(true)
                    let data={
                        "tuyen_cot_id":null,
                        "to_kt_id":null,
                        "query":searchText
                    }
                    let rs = await this.$root.context.post(
                        "/web-cabman/bando_tuyencot/tra_cuu_ds_cot", data
                    )
                    this.dscot = rs.data
                    this.tieuDe = 'Danh sách cột thỏa mãn điều kiện tìm kiếm'
                } catch (error) {
                } finally {
                    this.$root.showLoading(false)
                }
            },
            tracuuCot:async function(){
                try {
                    this.$root.showLoading(true)
                    let tuyenCotId = this.isTuyen == false ? null : this.dataSelected.tuyen_id
                    let data={
                        "tuyen_cot_id": tuyenCotId,
                        "to_kt_id":this.dataSelected.toql,
                        "query":""
                    }
                    let rs = await this.$root.context.post(
                        "/web-cabman/bando_tuyencot/tra_cuu_ds_cot", data
                    )
                    this.dscot = rs.data
                } catch (error) {
                } finally {
                    this.$root.showLoading(false)
                }
            },
            onChangeTuyen:async function(id){
                this.dscot = []
                this.tenTuyen = this.dsTuyenCot.find(x => x.TUYENCOT_ID == id).TUYEN_COT
                this.tieuDe = this.isTuyen ? 'Danh sách cột thuộc tuyến: '+ this.tenTuyen : this.tieuDe
                await this.tracuuCot()
            },
            chonCot:async function(){
                this.$emit('xacnhan',this.cotSelected);
                this.$bvModal.hide(this.modalId)
            },

            huybo:async function(){
                this.cot_id = null
            }

        },
        data:function() {
            return {
                dsTTVT: [],
                dsToQL: [],
                dsTuyenCot:[],
                dataSelected:{
                    ttvt:0,
                    toql:null,
                    tuyen_id:null
                },
                dscot:[],
                isTuyen:true,
                tuyencot_id:null,
                querys:"",
                cot_id:0,
                tieuDe:'Danh sách cột thuộc tuyến: ',
                tenTuyen:null,
                isDisableBtnChon:true,
                cotSelected:null
            };
        }
    };
</script>
<style>
  /* div#popupDsCot___BV_modal_outer_{
    z-index: 10 !important;
    margin-top: 100px;
  } */
  /* .dashboard-header {
    z-index: 9 !important;
  } */
  button.disabled {
    pointer-events: none;
    cursor: default;
    color: #6c757d !important;
  }
</style>

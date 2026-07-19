<template>
<div>
    <div class="popup-header"> 
        <div class="title"><span class="icon one-notepad"></span> Chi tiết ngành nghề</div> 
        <div class="close -ap icon-close" data-dismiss="modal" @click="closeForm"> 
        </div> 
    </div> 
    <div class="popup-body">
        <div class="box-form" style="overflow: hidden;">
            <div class="info-row">
                <div class="key nowrap">Tìm kiếm theo mã tiểu ngành (rê chuột vào ô tìm kiếm để xem HD =&gt; Enter để tìm kiếm)</div>
                <div class="value">
                    <input type="text" class="form-control" v-model="tieunganh.search" title="2 ký tự tìm theo tiểu ngành cấp 1, 3 ký tự  tìm theo tiểu ngành cấp 2, 4 ký tự tìm theo tiểu ngành cấp 3, 5 ký tự tìm theo tiểu ngành cấp 4">
                </div>
            </div>
            <div class="info-row nowrap">
                <div class="key">Tiểu ngành cấp 1</div>
                <div class="value">
                    <select2 v-model="tieunganh.TNC1_ID" class="select-custom" :options="tieunganh.dsTNC1.map(nn=>({id:nn.tnc1_id,text:nn.ten_tnc1}))"></select2>
                </div>
            </div>
            <div class="info-row nowrap">
                <div class="key">Tiểu ngành cấp 2</div>
                <div class="value">
                    <select2 v-model="tieunganh.TNC2_ID" class="select-custom" :options="tieunganh.dsTNC2.map(nn=>({id:nn.tnc2_id,text:nn.ten_tnc2}))"></select2>
                </div>
            </div>
            <div class="info-row nowrap">
                <div class="key">Tiểu ngành cấp 3</div>
                <div class="value">
                    <select2 v-model="tieunganh.TNC3_ID" class="select-custom" :options="tieunganh.dsTNC3.map(nn=>({id:nn.tnc3_id,text:nn.ten_tnc3}))"></select2>
                </div>
            </div>
            <div class="info-row nowrap">
                <div class="key">Tiểu ngành cấp 4</div>
                <div class="value">
                    <select2 v-model="tieunganh.TNC4_ID" class="select-custom" :options="tieunganh.dsTNC4.map(nn=>({id:nn.tnc4_id,text:nn.ten_tnc4}))"></select2>
                </div>
            </div>
            <div class="info-row nowrap">
                <div class="key">Mô tả chi tiết</div>
                <div class="value">
                    <textarea v-model="tieunganh.TNCMOTA" class="form-control" style="height: 100px;resize: none;"></textarea>
                </div>
            </div>
            <div class="group-buttons -bottom center">
                <button class="btn btn-second nocorner" style="width: 120px;" @click.prevent="acceptForm">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận
                </button>
                <button class="btn btn-second nocorner" style="width: 120px;" @click.prevent="closeForm">
                    <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát
                </button>
            </div>
        </div>
    </div> 
</div>
</template>
<style scoped>

</style>
<script>
import API from '../InstallNewSubsAPI'
export default {
    name: 'popupTieuNganh',
    props: [ 'dulieu' ],
    watch: {
        'tieunganh.search': function(val,oldval) {
            try {
                this.loading(true);
                var found = false;
                if(!val && oldval) {
                    if(this.tieunganh.dsTNC1 && this.tieunganh.dsTNC1.length>0 && this.tieunganh.TNC1_ID!=this.tieunganh.dsTNC1[0].tnc1_id) {
                        this.tieunganh.TNC1_ID=this.tieunganh.dsTNC1[0].tnc1_id
                        found = true;
                    }
                    if(!found && this.tieunganh.dsTNC2 && this.tieunganh.dsTNC2.length>0 && this.tieunganh.TNC2_ID!=this.tieunganh.dsTNC2[0].tnc2_id) {
                        this.tieunganh.TNC2_ID=this.tieunganh.dsTNC2[0].tnc2_id
                        found = true;
                    }
                    if(!found && this.tieunganh.dsTNC3 && this.tieunganh.dsTNC3.length>0 && this.tieunganh.TNC3_ID!=this.tieunganh.dsTNC3[0].tnc3_id) {
                        this.tieunganh.TNC3_ID=this.tieunganh.dsTNC3[0].tnc3_id
                        found = true;
                    }
                    if(!found && this.tieunganh.dsTNC4 && this.tieunganh.dsTNC4.length>0 && this.tieunganh.TNC4_ID!=this.tieunganh.dsTNC4[0].tnc4_id) {
                        this.tieunganh.TNC4_ID=this.tieunganh.dsTNC4[0].tnc4_id
                        found = true;
                    }
                }
                else {
                    var dsDL = [];
                    var cid = -1;
                    if(this.tieunganh.dsTNC1 && this.tieunganh.dsTNC1.length>0) {
                        cid = this.tieunganh.TNC1_ID;
                        dsDL = this.tieunganh.dsTNC1.filter(x=>((x.tnc1_id==val || x.ten_tnc1.indexOf(val)>=0) && x.tnc1_id!=cid));
                        console.log(val,dsDL);
                        if(dsDL && dsDL.length>0) { 
                            this.tieunganh.TNC1_ID = dsDL[0].tnc1_id; 
                            found = true;
                        }
                    }
                    if(!found && this.tieunganh.dsTNC2 && this.tieunganh.dsTNC2.length>0) {
                        cid = this.tieunganh.TNC2_ID;
                        dsDL = this.tieunganh.dsTNC2.filter(x=>((x.tnc2_id==val || x.ten_tnc2.indexOf(val)>=0) && x.tnc2_id!=cid));
                        if(dsDL && dsDL.length>0) { 
                            this.tieunganh.TNC2_ID = dsDL[0].tnc2_id; 
                            found = true;
                        }
                    }
                    if(!found && this.tieunganh.dsTNC3 && this.tieunganh.dsTNC3.length>0) {
                        cid = this.tieunganh.TNC3_ID;
                        dsDL = this.tieunganh.dsTNC3.filter(x=>((x.tnc3_id==val || x.ten_tnc3.indexOf(val)>=0) && x.tnc3_id!=cid));
                        if(dsDL && dsDL.length>0) { 
                            this.tieunganh.TNC3_ID = dsDL[0].tnc3_id; 
                            found = true;
                        }
                    }
                    if(!found && this.tieunganh.dsTNC4 && this.tieunganh.dsTNC4.length>0) {
                        cid = this.tieunganh.TNC4_ID;
                        dsDL = this.tieunganh.dsTNC4.filter(x=>((x.tnc4_id==val || x.ten_tnc4.indexOf(val)>=0) && x.tnc4_id!=cid));
                        if(dsDL && dsDL.length>0) { 
                            this.tieunganh.TNC4_ID = dsDL[0].tnc4_id; 
                            found = true;
                        }
                    }
                }
            } catch(ex) {} finally { this.loading(false); }
        },

        'tieunganh.TNC1_ID':function(newval) {
            this.LoadTNC2(newval);
        },
        'tieunganh.TNC2_ID':function(newval) {
            this.LoadTNC3(newval);
        },
        'tieunganh.TNC3_ID':function(newval) {
            this.LoadTNC4(newval);
        },
        'tieunganh.TNC4_ID':function(newval) {
            this.LoadMoTa(newval);
        },

    },
    data() {
        return {
            page: 0, //Page number
            tieunganh: {
                dsTNC1: [],
                dsTNC2: [],
                dsTNC3: [],
                dsTNC4: [],
                TNC1_ID: 0,
                TNC2_ID: 0,
                TNC3_ID: 0,
                TNC4_ID: 0,
                TNCMOTA: '',
            },
        }
    },
    mounted() {
        if(this.dulieu.tieunganh) this.tieunganh = this.dulieu.tieunganh;
        // if(this.dulieu.vnganhnghe_id) this.vnganhnghe_id = this.dulieu.vnganhnghe_id;
        if(!this.dulieu.vnganhnghe_id || this.dulieu.vnganhnghe_id<=0) { this.$toast.error('Bạn cần chọn ngành nghề trước khi chọn tiểu ngànhh! '); this.closeForm(); }
        API.post_lay_ds_tieunganh(this.axios, { "vkieu":1, "vnganhnghe_id": this.dulieu.vnganhnghe_id, "vtc_id": 0 }).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                this.tieunganh.dsTNC1 = response.data.data;
                if(this.tieunganh.dsTNC1 && this.tieunganh.dsTNC1.length>0) {
                    if(this.tieunganh.TNC1_ID>0) {
                        var list = this.tieunganh.dsTNC1.filter(x=>x.tnc1_id==this.tieunganh.TNC1_ID);
                        if(!list || list.length<=0) this.tieunganh.TNC1_ID = this.tieunganh.dsTNC1[0].tnc1_id;
                        else if(!this.tieunganh.dsTNC2 || this.tieunganh.dsTNC2.length<=0) this.LoadTNC2(this.tieunganh.TNC1_ID);
                    }
                    else this.tieunganh.TNC1_ID = this.tieunganh.dsTNC1[0].tnc1_id;
                } else { this.tieunganh.dsTNC1 = []; this.tieunganh.TNC1_ID = 0; }
            }
            else { this.tieunganh.dsTNC1 = []; this.tieunganh.TNC1_ID = 0; }
        });
    },
    computed: {
    },
    methods: {
        closeForm() {
            this.$emit('form-close',null);
        },
        acceptForm() {
            this.$emit('form-close',this.tieunganh);
        },
        LoadTNC2(newval) {
            API.post_lay_ds_tieunganh(this.axios, { "vkieu":2, "vnganhnghe_id": this.dulieu.vnganhnghe_id, "vtc_id": newval }).then((response) => {
                if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    this.tieunganh.dsTNC2 = response.data.data;
                    if(this.tieunganh.dsTNC2 && this.tieunganh.dsTNC2.length>0)
                    {
                        if(this.tieunganh.TNC2_ID>0) {
                            var list = this.tieunganh.dsTNC2.filter(x=>x.tnc2_id==this.tieunganh.TNC2_ID);
                            if(!list || list.length<=0) this.tieunganh.TNC2_ID = this.tieunganh.dsTNC2[0].tnc2_id;
                            else if(!this.tieunganh.dsTNC3 || this.tieunganh.dsTNC3.length<=0) this.LoadTNC3(this.tieunganh.TNC2_ID);
                        } else this.tieunganh.TNC2_ID=this.tieunganh.dsTNC2[0].tnc2_id;
                    }
                    else { this.tieunganh.dsTNC2 = []; this.tieunganh.TNC2_ID = 0; }
                } 
                else { this.tieunganh.dsTNC2 = []; this.tieunganh.TNC2_ID = 0; }
            });
        },
        LoadTNC3(newval) {
            API.post_lay_ds_tieunganh(this.axios, { "vkieu":3, "vnganhnghe_id": this.dulieu.vnganhnghe_id, "vtc_id": newval }).then((response) => {
                if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    this.tieunganh.dsTNC3 = response.data.data;
                    if(this.tieunganh.dsTNC3 && this.tieunganh.dsTNC3.length>0)
                    {
                        if(this.tieunganh.TNC3_ID>0) {
                            var list = this.tieunganh.dsTNC3.filter(x=>x.tnc3_id==this.tieunganh.TNC3_ID);
                            if(!list || list.length<=0) this.tieunganh.TNC3_ID = this.tieunganh.dsTNC3[0].tnc3_id;
                            else if(!this.tieunganh.dsTNC4 || this.tieunganh.dsTNC4.length<=0) this.LoadTNC4(this.tieunganh.TNC3_ID);
                        } else this.tieunganh.TNC3_ID=this.tieunganh.dsTNC3[0].tnc3_id;
                    }
                    else { this.tieunganh.dsTNC3 = []; this.tieunganh.TNC3_ID = 0; }
                }
                else { this.tieunganh.dsTNC3 = []; this.tieunganh.TNC3_ID = 0; }
            });
        },
        LoadTNC4(newval) {
            API.post_lay_ds_tieunganh(this.axios, { "vkieu":4, "vnganhnghe_id": this.dulieu.vnganhnghe_id, "vtc_id": newval }).then((response) => {
                if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    this.tieunganh.dsTNC4 = response.data.data;
                    // if(this.tieunganh.dsTNC4 && this.tieunganh.dsTNC4.length>0) this.tieunganh.TNC4_ID=this.tieunganh.dsTNC4[0].tnc4_id;
                    // else this.tieunganh.TNC4_ID;
                    if(this.tieunganh.dsTNC4 && this.tieunganh.dsTNC4.length>0)
                    {
                        if(this.tieunganh.TNC4_ID>0) {
                            var list = this.tieunganh.dsTNC4.filter(x=>x.tnc4_id==this.tieunganh.TNC4_ID);
                            if(!list || list.length<=0) this.tieunganh.TNC4_ID = this.tieunganh.dsTNC4[0].tnc4_id;
                            // else if(!this.tieunganh.dsTNC4 || this.tieunganh.dsTNC4.length<=0) this.LoadTNC4(this.tieunganh.TNC3_ID);
                        } else this.tieunganh.TNC4_ID=this.tieunganh.dsTNC4[0].tnc4_id;
                    }
                    else { this.tieunganh.dsTNC4 = []; this.tieunganh.TNC4_ID = 0; }
                }
                else { this.tieunganh.dsTNC4 = []; this.tieunganh.TNC4_ID = 0; }
            });
        },
        LoadMoTa(newval) {
            API.post_lay_ds_tieunganh(this.axios, { "vkieu":5, "vnganhnghe_id": this.dulieu.vnganhnghe_id, "vtc_id": newval }).then((response) => {
                if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                this.tieunganh.TNCMOTA = response.data.data.map(x=>x.mota).join("\n");
                } else this.tieunganh.TNCMOTA = "";
            });
        }
    }
}
</script>
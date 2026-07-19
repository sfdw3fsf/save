<template>
    <div class="row">
        <div class="col-sm-8 col-12">
            <div class="info-row">
                <div class="key w80">Cell1</div>
                <div class="value">
                    <input type="text" v-model="input_cell1" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w80">Toạ độ</div>
                <div class="value">
                    <input type="text" v-model="input_toado" class="form-control">
                </div>
            </div>
        </div>
        <div class="col-sm-4 col-12">
            <div class="info-row">
                <div class="key w90">Cell2</div>
                <div class="value">
                    <input type="text" v-model="input_cell2" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w90">Simcard</div>
                <div class="value">
                    <input type="text" v-model="input_simcard" class="form-control">
                </div>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    name:'Gphone',
    data(){
        return{
            input_cell1:'',
            input_cell2:'',
            input_toado:'',
            input_simcard:''
        }
    },
    methods:{
        async initDuLieu(){

        },
        async hienthi_dulieu_hdtb(item, hdtb_id){
            this.input_cell1=item.cell1!=null?item.cell1.toString():''
            this.input_cell2=item.cell2!=null?item.cell2.toString():''
            this.input_toado=item.toado!=null?item.toado.toString():''
            this.input_simcard=item.simcard!=null?item.simcard.toString():''
        },
        async hienthi_dulieu_thuebao(item, thuebao_id){
            this.input_cell1=item.cell1!=null?item.cell1.toString():''
            this.input_cell2=item.cell2!=null?item.cell2.toString():''
            this.input_toado=item.toado!=null?item.toado.toString():''
            this.input_simcard=item.simcard!=null?item.simcard.toString():''
        },
        async clearDuLieu(){
            this.input_cell1=''
            this.input_cell2=''
            this.input_toado=''
            this.input_simcard=''
        }
    }
}
</script>
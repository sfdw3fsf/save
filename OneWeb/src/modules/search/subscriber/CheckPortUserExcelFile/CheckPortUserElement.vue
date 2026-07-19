
<template >
    <div>
        <tr>
    
            <td>{{maTB}}</td>
            <td>
                <button @click=doKiemTB> xem</button>
            </td>
            <td>
                <button @click=clear> Clear</button>
            </td>  
        </tr>
        <tr v-if="txtOutPut!=''">
            <textarea   class="form-control" v-model="txtOutPut" style="height: 250px; width: 800px"></textarea>
        </tr>
    </div>
</template>
<script>
import api from './api'

export default {
    name: 'CheckPortUserElement',
    props: {
    maTB: null,
    status: null ,
    
  },
    data(){
        return {
            txtOutPut: "",
        }
    },
    methods: {
        doKiemTB(){
            const data = {};
            data.vma_tb = this.maTB
            api.post_do_kiem(this.axios, data).then(response =>{
                console.log("response==>", response)
                if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
                this.txtOutPut = response.data.data;
          }
            }).catch(e=>{
                throw e;
            })
        },
        clear(){
            this.txtOutPut = "";
        }
    }
}
</script>


export default function (args,color) {
  return {
    key: `${args.FIELD_NAME}`,
    template: `

      <div class="row" style="width: 100%">
      <div class="text-body col-6" :style="color" >
            {{ caption }}
        </div>
        <div class="text-body mr-0 pr-0 col-6" >
          <div style="position: relative; width: 100%; padding-right: 30px;">
            <input type="text" v-model="value" :ref="'COMPO-'+FIELD_NAME" class="form-control">
            <button class="btn btn-second lh12 pad2"  style="position: absolute; right: 0; top: 7px" @click="openForm" >
              <span class="-ap icon-more_horiz"></span>
            </button>
<!--            <button class="btn btn-sm btn-second" @click="onClick" style="position: absolute; right: 0; top: 0">...</button>-->
          </div>
        </div>


      </div>
    `,
    data() {
      return {
        caption : args.TEN_HIENTHI,
        id : args.FIELD_VALUE,
        value: args.FIELD_VALUE,
        data:[],
        KEY_FIELD :'',
        KEY_VALUE :'',
        FIELD_NAME : args.FIELD_NAME,
        popupComponent: null,
        popupComponentData: null,
        popupComponentAttr: {},
        popupComponentEvts: {
          'form-close': this.popupClosed,
          acceptChangeCTV: this.popupClosed,
          acceptChangeNGT: this.popupClosed,
          PopUpCongTacVien_Thoat: this.popupClosed
        },
        popupComponentName: '',
        popupComponentHeader: '',
      }
    },methods:{
      getData : async function(){

      },openForm(){
        console.log("args",args);
        this.$parent.popupComponentData = {
          vloaitb_id: this.$parent.loaitb_id,
          vhdtb_id: this.$parent.hdtb_id,
          vappend_name: args.FIELD_NAME
        }
        this.$parent.popupComponent = ()=> import('@/modules/contract/setup/InstallNewSubs/Popups/popupHDTB_CNTT_SUB')
        this.$parent.popupComponentName = 'popupHDTB_CNTT_SUB'
        this.$parent.popupComponentTitle = ''
        this.$parent.$bvModal.show('popupComponents')
      },popupClosed(){
        this.$bvModal.hide('popupComponents')
      }

    } ,mounted() {
      try {

        this.getData().then(r => {

        })
      } catch (error) {

      } finally {
        this.loading(false);
      }
    },watch : {
      'value' (val) {
        args.FIELD_VALUE = val
      }
    },computed :{
      color() {
        return{
          background : color ===1 ? '#fff' : '#F1F8FF'
        }
      }
    }
  }
}

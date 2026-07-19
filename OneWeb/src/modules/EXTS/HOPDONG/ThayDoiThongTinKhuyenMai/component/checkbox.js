
export default function (args,color) {

  return {
    key: `${args.FIELD_NAME}`,
    template: `
      <div class="row" style="height: 32px">
        <div class="text-body col-6":style="color" >
            {{ caption }}
        </div>
        <div class="text-body mr-0 pr-0 col-6" style="display: inline-flex"  >
          <input type="checkbox" v-model="value" :ref="'COMPO-'+FIELD_NAME" class="">
        </div>
      </div>
    `,
    data() {
      return {
        caption: args.TEN_HIENTHI,
        value: args.FIELD_VALUE,
        isActive : true,
        FIELD_NAME : args.FIELD_NAME,
      }
    },watch : {
      'value' (val) {
        args.FIELD_VALUE = val ? 1 : 0
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

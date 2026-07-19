
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'

import { required } from 'vuelidate/lib/validators'

export default function(args, parent) {

  return {
    args: {...args},
    components: {
    },
    template: `
      <div>
      <tr class="" style="width: 100%">
        <td class="text-body" style="width: 100%">
          {{ caption   }}
        </td>
      </tr>
      </div>
        `,
    validations: {
      data: {
      }
    },
    data() {
      return {
        caption: args,
      }
    },
    computed: {
    },
    methods: {

    }
  }
}

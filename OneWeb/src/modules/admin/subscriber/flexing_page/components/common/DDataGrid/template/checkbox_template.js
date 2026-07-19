export default function(parent) {
  return function() {
    return {
      template: {
        template: `
        <input type="checkbox" id="vehicle1" name="vehicle1" value="Bike">
              `,
        data() {
          return {
            data: {}
          }
        },
        async mounted() {},
        methods: {
          cboOnChanged(target) {}
        }
      }
    }
  }
}

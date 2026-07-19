export function createLookupTemplate({
  field,
  list = [],
  valueKey = 'ID',
  labelKey = 'TEN'
} = {}) {
  function getText(value) {
    const found = list.find(i => i[valueKey] == value)
    return found ? found[labelKey] : ''
  }

  return () => {
    return {
      template: {
        template: `<span>{{ getText() }}</span>`,
        methods: {
          getText() {
            return getText(this.data[field])
          }
        }
      }
    }
  }
}

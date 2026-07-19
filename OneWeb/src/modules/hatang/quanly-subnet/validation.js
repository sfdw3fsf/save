const isEmpty = (v) => v === null || v === undefined || (typeof v === 'string' && v.trim() === '')

const createField = () => {
  const validators = []

  const condition = {
    validators,

    required(message = 'required') {
      validators.push({ message, test: (v) => !isEmpty(v) })
      return condition
    },

    string(message = 'Dữ liệu nhập vào phải ở dạng string') {
      validators.push({
        message,
        test: (v) => isEmpty(v) || typeof v === 'string'
      })
      return condition
    },

    networkAddress(message = 'Subnet không hợp lệ') {
      const ruleText = `
      IPv4:
      • Định dạng: x.x.x.x
      • Mỗi octet phải nằm trong khoảng 0-255
      • Ví dụ hợp lệ: 192.168.1.0, 10.0.0.1

IPv6:
      • Định dạng: xxxx:xxxx:xxxx:xxxx:xxxx:xxxx:xxxx:xxxx
      • Hỗ trợ dạng rút gọn (::)
      • Ví dụ hợp lệ: 2001:db8::1, ::1
  `.trim();

      return this.custom((value) => {
        if (!value) return false;


        if (value.includes('/')) return false;

        // IPv4
        const ipv4Regex =
          /^(25[0-5]|2[0-4]\d|1?\d?\d)\.(25[0-5]|2[0-4]\d|1?\d?\d)\.(25[0-5]|2[0-4]\d|1?\d?\d)\.(25[0-5]|2[0-4]\d|1?\d?\d)$/;

        if (ipv4Regex.test(value)) return true;

        // IPv6 (full + shorthand)
        const ipv6Regex =
          /^(([0-9A-Fa-f]{1,4}:){7}[0-9A-Fa-f]{1,4}|([0-9A-Fa-f]{1,4}:){1,7}:|:((:[0-9A-Fa-f]{1,4}){1,7})|([0-9A-Fa-f]{1,4}:){1,6}:[0-9A-Fa-f]{1,4}|([0-9A-Fa-f]{1,4}:){1,5}(:[0-9A-Fa-f]{1,4}){1,2}|([0-9A-Fa-f]{1,4}:){1,4}(:[0-9A-Fa-f]{1,4}){1,3}|([0-9A-Fa-f]{1,4}:){1,3}(:[0-9A-Fa-f]{1,4}){1,4}|([0-9A-Fa-f]{1,4}:){1,2}(:[0-9A-Fa-f]{1,4}){1,5}|[0-9A-Fa-f]{1,4}((:[0-9A-Fa-f]{1,4}){1,6})|:((:[0-9A-Fa-f]{1,4}){1,7}|:))$/;

        if (ipv6Regex.test(value)) return true;

        return false;
      }, `${message}\n\n${ruleText}`);
    },



    number(message = 'Dữ liệu nhập vào phải là số') {
      validators.push({
        message,
        test: (v) => isEmpty(v) || !isNaN(Number(v))
      })
      return condition
    },

    custom(fn, message = 'invalid') {
      validators.push({ message, test: fn })
      return condition
    }
  }

  return condition
}

const createSchema = (shape) => ({
  validate(model) {
    const errors = {}
    let valid = true

    for (const key in shape) {
      const field = shape[key]
      const value = model[key]

      field.validators.forEach(({ test, message }) => {
        if (!test(value)) {
          valid = false
          if (!errors[key]) errors[key] = []
          errors[key].push(message)
        }
      })
    }

    return { valid, errors }
  }
})

export const yValidation = {
  string: () => createField().string(),
  number: () => createField().number(),
  boolean: () => createField(),
  mixed: () => createField(),
  object: (shape) => createSchema(shape),

  required: (msg = 'required') => createField().required(msg),
  netWorkAddress: (msg) => createField().networkAddress(msg)
}

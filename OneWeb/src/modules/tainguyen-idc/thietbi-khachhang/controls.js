export const rules = {
  required: (message = 'Bắt buộc nhập') => ({ type: 'required', message }),
  number: (message = 'Phải là số') => ({ type: 'number', message }),
  integer: (message = 'Phải là số nguyên') => ({ type: 'integer', message }),
  min: (min, message) => ({ type: 'min', min, message: message || `>= ${min}` }),
  max: (max, message) => ({ type: 'max', max, message: message || `<= ${max}` }),
  maxLength: (len, message) => ({ type: 'maxLength', len, message: message || `Tối đa ${len} ký tự` }),
  date: (message = 'Sai định dạng ngày') => ({ type: 'date', message }),
  custom: (fn, message = 'Không hợp lệ') => ({ type: 'custom', fn, message })
}

function isEmpty(value) {
  return value === null || value === undefined || (typeof value === 'string' && value.trim() === '')
}

function validateByRule(value, rule) {
  switch (rule.type) {
    case 'required':
      return !isEmpty(value)
    case 'number':
      return isEmpty(value) || (!isNaN(value) && isFinite(Number(value)))
    case 'integer':
      return isEmpty(value) || Number.isInteger(Number(value))
    case 'min':
      return isEmpty(value) || Number(value) >= rule.min
    case 'max':
      return isEmpty(value) || Number(value) <= rule.max
    case 'maxLength':
      return isEmpty(value) || String(value).length <= rule.len
    case 'date': {
      if (isEmpty(value)) return true
      // Accept Date object or parseable date string (dd/MM/yyyy or ISO)
      if (value instanceof Date && !isNaN(value.getTime())) return true
      // naive dd/MM/yyyy check
      if (typeof value === 'string') {
        const parts = value.split('/')
        if (parts.length === 3) {
          const [dd, mm, yyyy] = parts.map((p) => Number(p))
          const d = new Date(yyyy, mm - 1, dd)
          if (d && d.getFullYear() === yyyy && d.getMonth() === mm - 1 && d.getDate() === dd) return true
        }
      }
      // fallback parse
      return !isNaN(new Date(value).getTime())
    }
    case 'custom':
      return !!rule.fn(value)
    default:
      return true
  }
}

export function buildController(controlsRef, schemaRulesMap) {
  // controlsRef: existing controls object from component (mutated in-place)
  // schemaRulesMap: { fieldName: [rules.required(), rules.number(), ...] }

  function setEnabledAll(enabled) {
    if (!controlsRef) return
    Object.keys(controlsRef).forEach((k) => {
      controlsRef[k].enabled = !!enabled
    })
  }

  function setEnabled(fields, enabled) {
    if (!controlsRef || !Array.isArray(fields)) return
    fields.forEach((k) => {
      if (controlsRef[k]) controlsRef[k].enabled = !!enabled
    })
  }

  function resetInvalid() {
    if (!controlsRef) return
    Object.keys(controlsRef).forEach((k) => {
      controlsRef[k].invalid = false
    })
  }

  function validate(model) {
    resetInvalid()
    const errors = {}
    let isValid = true

    Object.keys(schemaRulesMap || {}).forEach((field) => {
      console.log('field ', field);
      
      const value = model ? model[field] : undefined
      const fieldRules = schemaRulesMap[field] || []
      for (const rule of fieldRules) {
        const ok = validateByRule(value, rule)
        if (!ok) {
          isValid = false
          if (controlsRef[field]) controlsRef[field].invalid = true
          if (!errors[field]) errors[field] = []
          errors[field].push(rule.message)
        }
      }
    })

    console.log('is valid ', isValid, ' and errors: ', errors)

    return { isValid, errors }
  }

  return {
    setEnabledAll,
    setEnabled,
    validate
  }
}

export function createControls(fields, initial = { enabled: false, invalid: false }) {
  const map = {}
  ;(fields || []).forEach((f) => {
    map[f] = {
      enabled: !!initial.enabled,
      invalid: !!initial.invalid
    }
  })
  return map
}


